package org.example.Entity; // Define o pacote onde a classe está localizada

// Importações das anotações do Jakarta Persistence (JPA)
import jakarta.persistence.*;

/**
 * A classe Cliente representa a entidade "cliente" no banco de dados.
 * Cada instância desta classe corresponde a um registro na tabela "cliente".
 */
@Entity // Indica que esta classe é uma entidade JPA
@Table(name = "cliente") // Define o nome da tabela correspondente no banco de dados
public class Cliente {

    @Id // Define que este campo é a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automaticamente (auto-incremento no MySQL)
    @Column(name = "id_cliente") // Define o nome da coluna no banco de dados
    private Long idCliente; // Campo para armazenar o ID do cliente

    @Column(nullable = false) // Campo obrigatório (não pode ser nulo)
    private String nome; // Nome do cliente

    @Column(nullable = false, unique = true) // Campo obrigatório e único (não pode repetir)
    private String email; // Email do cliente

    @Column(nullable = false) // Campo obrigatório (não pode ser nulo)
    private String telefone; // Telefone do cliente

    // Getters e Setters
    public Long getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    // Setters
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


}
