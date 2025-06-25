package org.example.Service;

import org.example.Entity.Cliente;
import org.example.Entity.Form.ClienteForm;
import org.example.Repository.ClienteRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements ClienteRepository {

    // SessionFactory é um objeto caro de criar, usado para abrir sessões com o banco
    private static final SessionFactory sessionFactory = buildSessionFactory();

    // Método que inicializa o SessionFactory a partir do arquivo de configuração hibernate.cfg.xml
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Erro ao criar o SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Cria um novo cliente no banco usando os dados do ClienteForm
    @Override
    public Cliente create(ClienteForm form) {
        Cliente cliente = new Cliente();
        cliente.setNome(form.getNome());
        cliente.setTelefone(form.getTelefone());
        cliente.setEmail(form.getEmail());

        Transaction transaction = null;

        // Tenta abrir uma sessão, iniciar uma transação, salvar o cliente e commitar
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(cliente);  // Persiste o objeto cliente no banco
            transaction.commit();
            return cliente;
        } catch (Exception e) {
            // Se der erro, desfaz a transação
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Busca um cliente no banco pelo ID
    @Override
    public Cliente get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Cliente.class, id);
        }
    }

    // Busca todos os clientes, podendo filtrar por nome, email ou telefone
    @Override
    public List<Cliente> getAll(String filtro) {
        try (Session session = sessionFactory.openSession()) {

            // Busca todos os clientes (sem filtro na consulta)
            List<Cliente> clientes = session.createQuery("FROM Cliente", Cliente.class).list();

            // Se filtro estiver vazio ou nulo, retorna todos os clientes
            if (filtro == null || filtro.trim().isEmpty()) {
                return clientes;
            }

            // Se existir filtro, cria uma lista para os clientes filtrados
            List<Cliente> clientesFiltrados = new ArrayList<>();

            // Para cada cliente, verifica se algum campo é igual ao filtro (exato, sem lowercase nem contains)
            for (Cliente cliente : clientes) {
                if (cliente.getNome().equals(filtro) ||
                        cliente.getEmail().equals(filtro) ||
                        cliente.getTelefone().equals(filtro)) {

                    // Adiciona o cliente que passou no filtro à lista filtrada
                    clientesFiltrados.add(cliente);
                }
            }

            // Retorna os clientes que passaram no filtro
            return clientesFiltrados;
        }
    }

    // Atualiza os dados de um cliente já existente
    @Override
    public Cliente update(Long id, ClienteForm form) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Busca o cliente no banco pelo ID
            Cliente cliente = session.find(Cliente.class, id);

            if (cliente != null) {
                // Atualiza os campos com os dados do form
                cliente.setNome(form.getNome());
                cliente.setTelefone(form.getTelefone());
                cliente.setEmail(form.getEmail());

                // Hibernate detecta mudanças automaticamente (dirty checking) e salva ao commitar
                transaction.commit();
                return cliente;
            } else {
                // Se cliente não encontrado, desfaz a transação e retorna null
                System.out.println("Cliente com ID " + id + " não encontrado.");
                transaction.rollback();
                return null;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Remove um cliente do banco pelo ID
    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Busca o cliente pelo ID
            Cliente cliente = session.find(Cliente.class, id);

            if (cliente != null) {
                // Remove o cliente e confirma a transação
                session.remove(cliente);
                transaction.commit();
                System.out.println("Cliente removido com sucesso!");
            } else {
                // Se não encontrado, desfaz a transação e lança exceção
                transaction.rollback();
                throw new RuntimeException("Cliente com ID " + id + " não encontrado.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Método que lista todos os clientes sem filtro (implementação simples chamando getAll(null))
    @Override
    public Iterable<Cliente> listar() {
        return getAll(null);
    }
}
