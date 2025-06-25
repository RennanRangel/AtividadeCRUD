package org.example.Repository;

import org.example.Entity.Cliente;
import org.example.Entity.Form.ClienteForm;

import java.util.List;

// Interface de serviço que define as operações para persistência de Cliente,
// sem nenhuma dependência do Spring
public interface ClienteRepository {

    // Cria e persiste um novo cliente com base nos dados fornecidos pelo formulário
    Cliente create(ClienteForm form);

    // Busca um cliente pelo seu identificador único (ID)
    Cliente get(Long id);

    // Retorna uma lista de clientes, com possibilidade de aplicar um filtro (ex: por nome ou email)
    List<Cliente> getAll(String filtro);

    // Atualiza os dados de um cliente existente a partir do ID e de um formulário com os novos dados
    Cliente update(Long id, ClienteForm form);

    // Remove um cliente do banco de dados com base no seu ID
    void delete(Long id);

    // Retorna todos os clientes cadastrados; útil para iteração com for-each
    Iterable<Cliente> listar();
}
