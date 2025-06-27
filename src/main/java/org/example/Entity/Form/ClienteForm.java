package org.example.Entity.Form; // Define o pacote onde a classe está localizada

// Importações de anotações de validação da especificação Jakarta Bean Validation
import jakarta.validation.constraints.Email;        // Valida se o campo tem formato de e-mail válido
import jakarta.validation.constraints.NotEmpty;     // Valida se o campo não está vazio
import jakarta.validation.constraints.Size;         // Valida o tamanho mínimo e máximo de uma string

/**
 * Classe usada para representar os dados de entrada do cliente.
 * É usada para separar a lógica de entrada de dados (como validação) da entidade Cliente.
 */
public class ClienteForm {

    // O campo "nome" não pode estar vazio e deve ter entre 3 e 50 caracteres
    @NotEmpty(message = "Nome é obrigatório") // Exibe mensagem se o nome estiver vazio
    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres") // Valida o tamanho do nome
    private String nome;

    // O campo "email" não pode estar vazio e deve ser um e-mail válido
    @NotEmpty(message = "Email é obrigatório") // Exibe mensagem se o e-mail estiver vazio
    @Email(message = "Email deve ser válido") // Exibe mensagem se o formato do e-mail for inválido
    private String email;

    // O campo "telefone" não pode estar vazio
    @NotEmpty(message = "Telefone é obrigatório") // Exibe mensagem se o telefone estiver vazio
    private String telefone;


    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
