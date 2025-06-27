package org.example.Service; // Define o pacote onde esta classe está localizada

// Importações das classes necessárias para a funcionalidade do DAO
import org.example.Entity.Cliente;
import org.example.Entity.Form.ClienteForm;
import org.example.Repository.ClienteRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

// Implementação da interface ClienteRepository com operações CRUD
public class ClienteDAO implements ClienteRepository {

    // Cria uma instância única de SessionFactory (objeto pesado) para gerenciar sessões com o banco
    private static final SessionFactory sessionFactory = buildSessionFactory();

    // Método responsável por configurar e construir o SessionFactory a partir do arquivo hibernate.cfg.xml
    private static SessionFactory buildSessionFactory() {
        try {
            // Tenta configurar o Hibernate e retornar a SessionFactory
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            // Em caso de erro na configuração, exibe no console e lança uma exceção
            System.err.println("Erro ao criar o SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex); // Interrompe a inicialização com erro
        }
    }

    // Método para criar um novo cliente no banco de dados a partir de um ClienteForm
    @Override
    public Cliente create(ClienteForm form) {
        // Cria um novo objeto Cliente e preenche com os dados do formulário
        Cliente cliente = new Cliente();
        cliente.setNome(form.getNome());
        cliente.setTelefone(form.getTelefone());
        cliente.setEmail(form.getEmail());

        Transaction transaction = null; // Variável para controlar a transação

        // Bloco try-with-resources para abrir e fechar automaticamente a sessão Hibernate
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction(); // Inicia a transação
            session.persist(cliente); // Persiste (salva) o cliente no banco
            transaction.commit(); // Confirma (commit) a transação
            return cliente; // Retorna o cliente criado
        } catch (Exception e) {
            // Em caso de erro, desfaz a transação se ela tiver sido iniciada
            if (transaction != null) transaction.rollback();
            throw e; // Lança a exceção para ser tratada externamente
        }
    }

    // Método para buscar um cliente pelo seu ID
    @Override
    public Cliente get(Long id) {
        // Abre uma nova sessão e busca o cliente com o método find
        try (Session session = sessionFactory.openSession()) {
            return session.find(Cliente.class, id); // Retorna o cliente ou null se não encontrado
        }
    }

    // Método para buscar todos os clientes ou filtrar pelo nome, email ou telefone
    @Override
    public List<Cliente> getAll(String filtro) {
        // Abre uma nova sessão com o banco
        try (Session session = sessionFactory.openSession()) {

            // Executa uma consulta HQL para obter todos os clientes
            List<Cliente> clientes = session.createQuery("FROM Cliente", Cliente.class).list();

            // Se o filtro for nulo ou vazio, retorna todos os clientes
            if (filtro == null || filtro.trim().isEmpty()) {
                return clientes;
            }

            // Cria uma nova lista para armazenar os clientes que correspondem ao filtro
            List<Cliente> clientesFiltrados = new ArrayList<>();

            // Itera sobre todos os clientes para aplicar o filtro manualmente
            for (Cliente cliente : clientes) {
                // Verifica se algum dos campos do cliente corresponde exatamente ao filtro
                if (cliente.getNome().equals(filtro) ||
                        cliente.getEmail().equals(filtro) ||
                        cliente.getTelefone().equals(filtro)) {

                    // Adiciona o cliente à lista filtrada
                    clientesFiltrados.add(cliente);
                }
            }

            // Retorna a lista de clientes que passaram no filtro
            return clientesFiltrados;
        }
    }

    // Método para atualizar os dados de um cliente existente
    @Override
    public Cliente update(Long id, ClienteForm form) {
        Transaction transaction = null; // Variável da transação

        // Abre uma nova sessão para fazer a atualização
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction(); // Inicia a transação

            // Busca o cliente existente pelo ID
            Cliente cliente = session.find(Cliente.class, id);

            // Se o cliente for encontrado, atualiza os campos com os novos dados
            if (cliente != null) {
                cliente.setNome(form.getNome());
                cliente.setTelefone(form.getTelefone());
                cliente.setEmail(form.getEmail());

                // O Hibernate detecta automaticamente mudanças (dirty checking) e salva ao commitar
                transaction.commit(); // Confirma a transação
                return cliente; // Retorna o cliente atualizado
            } else {
                // Se o cliente não existir, desfaz a transação e retorna null
                System.out.println("Cliente com ID " + id + " não encontrado.");
                transaction.rollback(); // Desfaz a transação
                return null;
            }
        } catch (Exception e) {
            // Em caso de erro, desfaz a transação se existir
            if (transaction != null) transaction.rollback();
            throw e; // Propaga a exceção
        }
    }

    // Método para excluir um cliente pelo ID
    @Override
    public void delete(Long id) {
        Transaction transaction = null; // Variável da transação

        // Abre uma nova sessão para excluir o cliente
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction(); // Inicia a transação

            // Busca o cliente pelo ID
            Cliente cliente = session.find(Cliente.class, id);

            // Se o cliente for encontrado, remove e confirma a transação
            if (cliente != null) {
                session.remove(cliente); // Remove o cliente do banco
                transaction.commit(); // Confirma a exclusão
                System.out.println("Cliente removido com sucesso!");
            } else {
                // Se o cliente não existir, desfaz a transação e lança exceção
                transaction.rollback();
                throw new RuntimeException("Cliente com ID " + id + " não encontrado.");
            }
        } catch (Exception e) {
            // Em caso de erro, desfaz a transação se necessário
            if (transaction != null) transaction.rollback();
            throw e; // Propaga o erro
        }
    }

    // Método auxiliar que retorna todos os clientes sem aplicar filtro (chama getAll com null)
    @Override
    public Iterable<Cliente> listar() {
        return getAll(null); // Retorna todos os clientes
    }
}
