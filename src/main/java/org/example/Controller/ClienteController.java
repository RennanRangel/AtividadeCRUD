package org.example.Controller; // Define que esta classe está no pacote de controle (Controller)

import org.example.Entity.Cliente; // Importa a entidade Cliente (representa a tabela no banco)
import org.example.Entity.Form.ClienteForm; // Importa o formulário com os dados de entrada do usuário
import org.example.Repository.ClienteRepository; // Importa o repositório responsável pela comunicação com o banco de dados

import java.util.List; // Importa a classe List para trabalhar com listas de clientes

/**
 * A classe ClienteController funciona como um intermediário entre o usuário (ou interface)
 * e o repositório de dados. Aqui é onde as ações são organizadas.
 */
public class ClienteController {

    private final ClienteRepository service; // Declara a dependência do repositório (DAO)

    /**
     * Construtor da classe. Ele recebe uma implementação de ClienteRepository.
     * Isso é chamado de "injeção de dependência manual".
     */
    public ClienteController(ClienteRepository service) {
        this.service = service;
    }

    /**
     * Simula o endpoint POST /clientes
     * Responsável por cadastrar um novo cliente a partir de um formulário.
     */
    public Cliente create(ClienteForm form) {
        return service.create(form); // Chama o método create do repositório
    }

    /**
     * Simula o endpoint GET /clientes/{id}
     * Busca um cliente pelo ID.
     */
    public Cliente get(Long id) {
        return service.get(id); // Chama o método get do repositório
    }

    /**
     * Simula o endpoint GET /clientes?filtro=xyz
     * Retorna uma lista de clientes com base em um filtro (pode ser vazio).
     */
    public List<Cliente> getAll(String filtro) {
        return service.getAll(filtro); // Chama o método getAll do repositório
    }

    /**
     * Simula o endpoint PUT /clientes/{id}
     * Atualiza os dados de um cliente com base no ID e no formulário recebido.
     */
    public Cliente update(Long id, ClienteForm form) {
        return service.update(id, form); // Chama o método update do repositório
    }

    /**
     * Simula o endpoint DELETE /clientes/{id}
     * Remove um cliente do banco de dados com base no ID.
     */
    public void delete(Long id) {
        service.delete(id); // Chama o método delete do repositório
    }
}
