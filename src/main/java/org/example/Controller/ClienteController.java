package org.example.Controller;

import org.example.Entity.Cliente;
import org.example.Entity.Form.ClienteForm;
import org.example.Repository.ClienteRepository;

import java.util.List;
// Controller: delega para o serviço
public class ClienteController {

    private final ClienteRepository service;

    // Construtor com injeção manual de dependência
    public ClienteController(ClienteRepository service) {
        this.service = service;
    }

    // Simula o endpoint POST /clientes
    public Cliente create(ClienteForm form) {
        return service.create(form);
    }

    // Simula o endpoint GET /clientes/{id}
    public Cliente get(Long id) {
        return service.get(id);
    }

    // Simula o endpoint GET /clientes?filtro=xyz
    public List<Cliente> getAll(String filtro) {
        return service.getAll(filtro);
    }

    // Simula o endpoint PUT /clientes/{id}
    public Cliente update(Long id, ClienteForm form) {
        return service.update(id, form);
    }

    // Simula o endpoint DELETE /clientes/{id}
    public void delete(Long id) {
        service.delete(id);
    }
}
