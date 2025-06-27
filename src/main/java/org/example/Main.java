package org.example; // Define o pacote da classe Main

// Importa classes para validação de dados (jakarta.validation)
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

// Importa classes do seu projeto
import org.example.Controller.ClienteController;
import org.example.Entity.Cliente;
import org.example.Entity.Form.ClienteForm;
import org.example.Repository.ClienteRepository;
import org.example.Service.ClienteDAO;

// Importa classes utilitárias do Java
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main { // Declara a classe principal

    public static void main(String[] args) { // Método principal, ponto de entrada da aplicação
        Scanner scanner = new Scanner(System.in); // Cria um Scanner para ler entradas do usuário via console

        ClienteRepository repository = new ClienteDAO(); // Cria um repositório com a implementação DAO
        ClienteController controller = new ClienteController(repository); // Instancia o controller e injeta o repositório

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); // Cria uma fábrica de validadores
        Validator validator = factory.getValidator(); // Obtém uma instância de validador

        int opcao; // Declara uma variável para armazenar a opção do menu

        do { // Início do laço de repetição do menu
            System.out.println("\n========== MENU =========="); // Exibe o menu
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Listar clientes");
            System.out.println("3 - Atualizar cliente");
            System.out.println("4 - Deletar cliente");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: "); // Solicita a entrada da opção

            if (scanner.hasNextInt()) { // Verifica se a entrada é um número inteiro
                opcao = scanner.nextInt(); // Lê a opção
            } else { // Se a entrada não for válida
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine(); // Limpa o buffer
                opcao = -1; // Define opção inválida
            }
            scanner.nextLine(); // Limpa o buffer após o nextInt

            switch (opcao) { // Início do switch com base na opção escolhida
                case 1: // Caso 1 - Cadastrar cliente
                    System.out.print("Nome: "); // Solicita nome
                    String nome = scanner.nextLine(); // Lê o nome digitado
                    System.out.print("Telefone: "); // Solicita telefone
                    String telefone = scanner.nextLine(); // Lê o telefone digitado
                    System.out.print("Email: "); // Solicita email
                    String email = scanner.nextLine(); // Lê o email digitado

                    ClienteForm form = new ClienteForm(); // Cria instância do formulário
                    form.setNome(nome); // Define nome
                    form.setTelefone(telefone); // Define telefone
                    form.setEmail(email); // Define email

                    Set<ConstraintViolation<ClienteForm>> erros = validator.validate(form); // Valida o formulário
                    if (!erros.isEmpty()) { // Verifica se há erros
                        System.out.println("Erros de validação:"); // Exibe mensagem
                        for (ConstraintViolation<ClienteForm> erro : erros) { // Itera sobre os erros
                            System.out.println("- " + erro.getPropertyPath() + ": " + erro.getMessage()); // Exibe erro
                        }
                    } else { // Se não houver erros
                        Cliente novoCliente = controller.create(form); // Cria o cliente
                        System.out.println("Cliente cadastrado com ID: " + novoCliente.getIdCliente()); // Exibe ID
                    }
                    break;

                case 2: // Caso 2 - Listar clientes
                    List<Cliente> clientes = controller.getAll(""); // Obtém lista de clientes
                    if (clientes.isEmpty()) { // Se lista estiver vazia
                        System.out.println("Nenhum cliente encontrado."); // Informa ausência
                    } else { // Se houver clientes
                        for (Cliente cliente : clientes) { // Itera sobre a lista
                            System.out.printf("ID: %d | Nome: %s | Telefone: %s | Email: %s\n",
                                    cliente.getIdCliente(), cliente.getNome(), cliente.getTelefone(), cliente.getEmail()); // Exibe os dados
                        }
                    }
                    break;

                case 3: // Caso 3 - Atualizar cliente
                    System.out.print("ID do cliente para atualizar: "); // Solicita o ID
                    Long idAtualiza = null; // Declara a variável do ID
                    try {
                        idAtualiza = Long.parseLong(scanner.nextLine()); // Lê e converte o ID
                    } catch (NumberFormatException e) { // Trata erro de conversão
                        System.out.println("ID inválido."); // Informa erro
                        break;
                    }

                    // Solicita e lê novos dados
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Novo telefone: ");
                    String novoTelefone = scanner.nextLine();
                    System.out.print("Novo email: ");
                    String novoEmail = scanner.nextLine();

                    // Cria formulário de atualização
                    ClienteForm atualizaForm = new ClienteForm();
                    atualizaForm.setNome(novoNome);
                    atualizaForm.setTelefone(novoTelefone);
                    atualizaForm.setEmail(novoEmail);

                    // Valida os novos dados
                    Set<ConstraintViolation<ClienteForm>> errosUpdate = validator.validate(atualizaForm);
                    if (!errosUpdate.isEmpty()) { // Se houver erros
                        System.out.println("Erros de validação:");
                        for (ConstraintViolation<ClienteForm> erro : errosUpdate) {
                            System.out.println("- " + erro.getPropertyPath() + ": " + erro.getMessage());
                        }
                    } else { // Se válido
                        Cliente atualizado = controller.update(idAtualiza, atualizaForm); // Atualiza
                        if (atualizado != null) { // Verifica sucesso
                            System.out.println("Cliente atualizado com sucesso!");
                        } else {
                            System.out.println("Cliente com ID " + idAtualiza + " não encontrado.");
                        }
                    }
                    break;

                case 4: // Caso 4 - Deletar cliente
                    System.out.print("ID do cliente para deletar: "); // Solicita ID
                    try {
                        Long idDelete = Long.parseLong(scanner.nextLine()); // Converte ID
                        controller.delete(idDelete); // Chama método de deletar
                    } catch (NumberFormatException es) { // Trata erro de conversão
                        System.out.println("ID inválido.");
                    } catch (RuntimeException run) { // Trata erro do DAO
                        System.out.println(run.getMessage()); // Exibe mensagem
                    }
                    break;

                case 0: // Caso 0 - Sair
                    System.out.println("Encerrando..."); // Mensagem de saída
                    break;

                default: // Opção inválida
                    System.out.println("Opção inválida!"); // Informa erro
                    break;
            }

        } while (opcao != 0); // Executa enquanto a opção for diferente de 0
    }
}
