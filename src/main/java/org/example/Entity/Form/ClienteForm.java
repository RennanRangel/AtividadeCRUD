package org.example.Entity.Form;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

/**
 * Classe usada para receber e validar dados de entrada do cliente.
 */
public class ClienteForm {

    // Use jakarta.validation.constraints.NotEmpty e .Size
    @NotEmpty(message = "Nome é obrigatório")
    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
    private String nome;

    // Use jakarta.validation.constraints.NotEmpty e .Email
    @NotEmpty(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotEmpty(message = "Telefone é obrigatório")
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
