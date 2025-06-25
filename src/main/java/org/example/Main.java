package org.example;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.Controller.ClienteController;
import org.example.Entity.Cliente;
import org.example.Entity.Form.ClienteForm;
import org.example.Repository.ClienteRepository;
import org.example.Service.ClienteDAO;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ClienteRepository repository = new ClienteDAO();
        ClienteController controller = new ClienteController(repository);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        int opcao;

        do {
            System.out.println("\n========== MENU ==========");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Listar clientes");
            System.out.println("3 - Atualizar cliente");
            System.out.println("4 - Deletar cliente");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                opcao = -1;
            }
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine();
                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    ClienteForm form = new ClienteForm();
                    form.setNome(nome);
                    form.setTelefone(telefone);
                    form.setEmail(email);

                    Set<ConstraintViolation<ClienteForm>> erros = validator.validate(form);
                    if (!erros.isEmpty()) {
                        System.out.println("Erros de validação:");
                        for (ConstraintViolation<ClienteForm> erro : erros) {
                            System.out.println("- " + erro.getPropertyPath() + ": " + erro.getMessage());
                        }
                    } else {
                        Cliente novoCliente = controller.create(form);
                        System.out.println("Cliente cadastrado com ID: " + novoCliente.getIdCliente());
                    }
                    break;

                case 2:
                    List<Cliente> clientes = controller.getAll("");
                    if (clientes.isEmpty()) {
                        System.out.println("Nenhum cliente encontrado.");
                    } else {
                        for (Cliente cliente : clientes) {
                            System.out.printf("ID: %d | Nome: %s | Telefone: %s | Email: %s\n",
                                    cliente.getIdCliente(), cliente.getNome(), cliente.getTelefone(), cliente.getEmail());
                        }
                    }
                    break;

                case 3:
                    System.out.print("ID do cliente para atualizar: ");
                    Long idAtualiza = null;
                    try {
                        idAtualiza = Long.parseLong(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("ID inválido.");
                        break;
                    }

                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Novo telefone: ");
                    String novoTelefone = scanner.nextLine();
                    System.out.print("Novo email: ");
                    String novoEmail = scanner.nextLine();

                    ClienteForm atualizaForm = new ClienteForm();
                    atualizaForm.setNome(novoNome);
                    atualizaForm.setTelefone(novoTelefone);
                    atualizaForm.setEmail(novoEmail);

                    Set<ConstraintViolation<ClienteForm>> errosUpdate = validator.validate(atualizaForm);
                    if (!errosUpdate.isEmpty()) {
                        System.out.println("Erros de validação:");
                        for (ConstraintViolation<ClienteForm> erro : errosUpdate) {
                            System.out.println("- " + erro.getPropertyPath() + ": " + erro.getMessage());
                        }
                    } else {
                        Cliente atualizado = controller.update(idAtualiza, atualizaForm);
                        if (atualizado != null) {
                            System.out.println("Cliente atualizado com sucesso!");
                        } else {
                            System.out.println("Cliente com ID " + idAtualiza + " não encontrado.");
                        }
                    }
                    break;

                case 4:
                    System.out.print("ID do cliente para deletar: ");
                    try {
                        Long idDelete = Long.parseLong(scanner.nextLine());
                        controller.delete(idDelete); // Mensagem será tratada dentro do método
                    } catch (NumberFormatException es) {
                        System.out.println("ID inválido.");
                    } catch (RuntimeException run) {
                        System.out.println(run.getMessage()); // Exibe a exceção vinda do DAO
                    }
                    break;


                case 0:
                    System.out.println("Encerrando...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (opcao != 0);

 
    }
}
