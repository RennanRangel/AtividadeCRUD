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

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Erro ao criar o SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    @Override
    public Cliente create(ClienteForm form) {
        Cliente cliente = new Cliente();
        cliente.setNome(form.getNome());
        cliente.setTelefone(form.getTelefone());
        cliente.setEmail(form.getEmail());

        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(cliente);
            transaction.commit();
            return cliente;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public Cliente get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            // Usando find() que é JPA padrão
            return session.find(Cliente.class, id);
        }
    }

    @Override
    public List<Cliente> getAll(String filtro) {
        try (Session session = sessionFactory.openSession()) {
            List<Cliente> clientes = session.createQuery("FROM Cliente", Cliente.class).list();

            if (filtro == null || filtro.trim().isEmpty()) {
                return clientes;
            }

            List<Cliente> clientesFiltrados = new ArrayList<>();
            for (Cliente cliente : clientes) {
                if (cliente.getNome().equals(filtro) ||
                        cliente.getEmail().equals(filtro) ||
                        cliente.getTelefone().equals(filtro)) {
                    clientesFiltrados.add(cliente);
                }
            }
            return clientesFiltrados;
        }
    }

    @Override
    public Cliente update(Long id, ClienteForm form) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Cliente cliente = session.find(Cliente.class, id);

            if (cliente != null) {
                cliente.setNome(form.getNome());
                cliente.setTelefone(form.getTelefone());
                cliente.setEmail(form.getEmail());

                // Força atualização no banco
                session.merge(cliente);
                transaction.commit();
                return cliente;
            } else {
                System.out.println("Cliente com ID " + id + " não encontrado.");
                transaction.rollback();
                return null;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Cliente cliente = session.find(Cliente.class, id);

            if (cliente != null) {
                session.remove(cliente);
                transaction.commit();
                System.out.println("Cliente removido com sucesso!");
            } else {
                transaction.rollback();
                throw new RuntimeException("Cliente com ID " + id + " não encontrado.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    @Override
    public Iterable<Cliente> listar() {
        return getAll(null);
    }
}
