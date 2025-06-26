#  Sistema de Gerenciamento de Clientes (API Simulado)

Este projeto demonstra a implementação de um serviço de gerenciamento de clientes em Java, utilizando os princípios de persistência de dados com Hibernate (como provedor JPA) e boas práticas de arquitetura. O objetivo é simular as operações CRUD (Criar, Ler, Atualizar, Deletar) de clientes, com foco na separação de responsabilidades e na validação de entrada de dados.

##  Visão Geral

O sistema é estruturado em camadas distintas (Controller, Service/Repository, Entity, Form) para promover a organização, manutenibilidade e testabilidade do código. Ele utiliza o Hibernate para mapeamento objeto-relacional (ORM) e interação com o banco de dados, e Jakarta Validation para garantir a integridade dos dados de entrada.

##  Tecnologias Utilizadas

* **Java 17+**
* **Hibernate 6+** (como provedor JPA)
* **JDBC** (para conexão com o banco de dados)
* **MySQL** (banco de dados)
* **Jakarta Validation** (para validação de dados)
* **Maven** (ferramenta de build e gerenciamento de dependências)

##  Estrutura do Sistema

O projeto segue uma arquitetura baseada em camadas para promover a clareza, modularidade e separação de responsabilidades. Cada pacote tem uma função específica no fluxo da aplicação:

* `src/main/java/org/example/`:
    ####
    * `Controller/ClienteController.java`: Atua como a camada de **interface/API**. Recebe as requisições (simuladas como chamadas de método) e delega as operações para a camada de serviço. É responsável por orquestrar a interação.
    #### 
    * `Entity/Cliente.java`: Representa o **modelo de dados** da aplicação. É a entidade que será persistida no banco de dados, mapeada para a tabela `cliente` usando anotações JPA (`@Entity`, `@Table`, `@Id`, `@GeneratedValue`, `@Column`).
    ####
    * `Entity/Form/ClienteForm.java`: É um **Data Transfer Object (DTO)** ou formulário de entrada. Utilizado para encapsular e validar os dados recebidos para a criação ou atualização de um cliente. Inclui anotações de validação (`@NotEmpty`, `@Size`, `@Email`) do Jakarta Validation.
   ####
   * `Repository/ClienteRepository.java`: Define o **contrato (interface)** da camada de acesso a dados. Estabelece quais operações (CRUD) podem ser realizadas na entidade `Cliente`, abstraindo a implementação subjacente de persistência.
    #### 
   * `Service/ClienteDAO.java`: É a **implementação concreta da lógica de persistência**. É aqui que a interação direta com o banco de dados ocorre via Hibernate, gerenciando sessões e transações para as operações CRUD definidas na `ClienteRepository`.
  ####  
* `src/main/resources/`:
   ####
    * `hibernate.cfg.xml`: Arquivo de configuração fundamental do Hibernate, definindo as propriedades de conexão com o banco de dados, o dialeto SQL e o comportamento de gerenciamento do schema.

##  Passos para Configurar o Hibernate (MySQL)

O Hibernate é configurado através do arquivo `hibernate.cfg.xml`, localizado em `src/main/resources/`. Siga os passos para garantir a conexão correta com seu banco de dados MySQL:

1.  **Crie o Banco de Dados e a Tabela:**
    * No seu servidor MySQL, conecte-se a um cliente (ex: MySQL Workbench, linha de comando `mysql -u root -p`).
    * Execute os seguintes comandos SQL para criar o banco de dados e a tabela `cliente`:

        ```sql
        CREATE DATABASE cadastro;

        USE cadastro; -- Seleciona o banco de dados recém-criado

        CREATE TABLE cliente (
            id_cliente INT AUTO_INCREMENT PRIMARY KEY,
            nome VARCHAR(255) NOT NULL,
            email VARCHAR(255) NOT NULL UNIQUE,
            telefone VARCHAR(50) NOT NULL
        );
        ```
    * **Nota sobre `hibernate.hbm2ddl.auto`**: O `hibernate.hbm2ddl.auto` configurado como `update` no seu `hibernate.cfg.xml` é capaz de criar ou atualizar a tabela `cliente` automaticamente com base na sua entidade `Cliente.java`. No entanto, é uma boa prática criar o banco de dados e ter o DDL da tabela para referência ou em caso de uso em produção (onde `update` é geralmente evitado).

2.  **Ajuste as Credenciais no `hibernate.cfg.xml`:**
    * Abra o arquivo `src/main/resources/hibernate.cfg.xml`.
    * Localize as propriedades de conexão e atualize-as com os detalhes do seu ambiente e o nome do banco de dados `cadastro`:
        ```xml
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/cadastro?useTimezone=true&amp;serverTimezone=UTC</property>
        <property name="hibernate.connection.username">seu_usuario_mysql</property>
        <property name="hibernate.connection.password">sua_senha_mysql</property>
        ```

---

##  Configuração e Execução do Projeto

Para configurar e executar o projeto, siga os passos abaixo:

1.  **Pré-requisitos:**
    * Certifique-se de ter o **Java Development Kit (JDK) 17 ou superior** instalado.
    * Tenha o **Maven** instalado e configurado.
    * Tenha o servidor de banco de dados **MySQL** em execução.

2.  **Compilação do Projeto:**
    * Abra o terminal na raiz do projeto (onde está o `pom.xml`).
    * Execute o comando Maven para compilar:
        ```bash
        mvn clean install
        ```

3.  **Execução (Exemplo de Uso):**
    * Crie uma classe `Main` (ou use um conjunto de testes JUnit) para interagir com o `ClienteController`. Um exemplo simples está abaixo:
        ```java
        // Exemplo de como usar o Controller em uma Main class (apenas para demonstração)
        package org.example;

        import org.example.Controller.ClienteController;
        import org.example.Entity.Cliente;
        import org.example.Entity.Form.ClienteForm;
        import org.example.Service.ClienteDAO; // A implementação concreta
        import java.util.List; // Importar List

        public class Main {
            public static void main(String[] args) {
                // Instancia o DAO, que implementa ClienteRepository
                ClienteDAO clienteDao = new ClienteDAO();
                // Injeta o DAO no Controller
                ClienteController controller = new ClienteController(clienteDao);

                // Configuração do Bean Validation (Jakarta Validation).
                // Cria uma fábrica de validadores.
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                // Obtém uma instância do validador a partir da fábrica.
                Validator validator = factory.getValidator();

                int opcao; // Variável para armazenar a opção escolhida pelo usuário no menu.

                // Loop principal do menu da aplicação. Continua executando até que o usuário escolha a opção "0 - Sair".
                 do {
                // Exibe o menu de opções para o usuário.
                System.out.println("\n========== MENU ==========");
                System.out.println("1 - Cadastrar cliente");
                System.out.println("2 - Listar clientes");
                System.out.println("3 - Atualizar cliente");
                System.out.println("4 - Deletar cliente");
                System.out.println("0 - Sair");
                System.out.print("Escolha uma opção: ");

                // Verifica se a próxima entrada é um número inteiro.
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt(); // Lê a opção escolhida.
            } else {
                // Se a entrada não for um número, exibe uma mensagem de erro.
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine(); // Consome a linha inválida para evitar loop infinito.
                opcao = -1;         // Define uma opção inválida para repetir o menu.
            }
            scanner.nextLine(); // Consome a quebra de linha pendente após nextInt() para evitar problemas com nextLine() subsequentes.

            // Estrutura switch-case para lidar com a opção escolhida pelo usuário.
            switch (opcao) {
                case 1: // Cadastrar cliente
                    System.out.print("Nome: ");
                    String nome = scanner.nextLine(); // Lê o nome do cliente.
                    System.out.print("Telefone: ");
                    String telefone = scanner.nextLine(); // Lê o telefone do cliente.
                    System.out.print("Email: ");
                    String email = scanner.nextLine(); // Lê o email do cliente.

                    // Cria um objeto ClienteForm para coletar os dados de entrada.
                    ClienteForm form = new ClienteForm();
                    form.setNome(nome);
                    form.setTelefone(telefone);
                    form.setEmail(email);

                    // Realiza a validação do objeto ClienteForm usando o Bean Validation.
                    Set<ConstraintViolation<ClienteForm>> erros = validator.validate(form);
                    if (!erros.isEmpty()) {
                        // Se houver erros de validação, os exibe no console.
                        System.out.println("Erros de validação:");
                        for (ConstraintViolation<ClienteForm> erro : erros) {
                            System.out.println("- " + erro.getPropertyPath() + ": " + erro.getMessage());
                        }
                    } else {
                        // Se não houver erros, chama o método create do controller para cadastrar o cliente.
                        Cliente novoCliente = controller.create(form);
                        System.out.println("Cliente cadastrado com ID: " + novoCliente.getIdCliente());
                    }
                    break;

                case 2: // Listar clientes
                    // Chama o método getAll do controller para obter a lista de todos os clientes.
                    // O parâmetro vazio "" indica que não há filtro de busca.
                    List<Cliente> clientes = controller.getAll("");
                    if (clientes.isEmpty()) {
                        // Se a lista estiver vazia, informa que nenhum cliente foi encontrado.
                        System.out.println("Nenhum cliente encontrado.");
                    } else {
                        // Itera sobre a lista de clientes e exibe suas informações formatadas.
                        for (Cliente cliente : clientes) {
                            System.out.printf("ID: %d | Nome: %s | Telefone: %s | Email: %s\n",
                                    cliente.getIdCliente(), cliente.getNome(), cliente.getTelefone(), cliente.getEmail());
                        }
                    }
                    break;

                case 3: // Atualizar cliente
                    System.out.print("ID do cliente para atualizar: ");
                    Long idAtualiza = null;
                    try {
                        // Tenta converter a entrada do usuário para um Long (ID do cliente).
                        idAtualiza = Long.parseLong(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        // Se a conversão falhar (entrada não é um número), exibe erro e sai do case.
                        System.out.println("ID inválido.");
                        break;
                    }

                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine(); // Lê o novo nome.
                    System.out.print("Novo telefone: ");
                    String novoTelefone = scanner.nextLine(); // Lê o novo telefone.
                    System.out.print("Novo email: ");
                    String novoEmail = scanner.nextLine(); // Lê o novo email.

                    // Cria um novo ClienteForm com os dados para atualização.
                    ClienteForm atualizaForm = new ClienteForm();
                    atualizaForm.setNome(novoNome);
                    atualizaForm.setTelefone(novoTelefone);
                    atualizaForm.setEmail(novoEmail);

                    // Realiza a validação dos dados de atualização.
                    Set<ConstraintViolation<ClienteForm>> errosUpdate = validator.validate(atualizaForm);
                    if (!errosUpdate.isEmpty()) {
                        // Se houver erros de validação, os exibe.
                        System.out.println("Erros de validação:");
                        for (ConstraintViolation<ClienteForm> erro : errosUpdate) {
                            System.out.println("- " + erro.getPropertyPath() + ": " + erro.getMessage());
                        }
                    } else {
                        // Se não houver erros, chama o método update do controller.
                        Cliente atualizado = controller.update(idAtualiza, atualizaForm);
                        if (atualizado != null) {
                            // Se o cliente foi atualizado com sucesso (retornou um objeto), exibe mensagem de sucesso.
                            System.out.println("Cliente atualizado com sucesso!");
                        } else {
                            // Se o controller retornou null, o cliente com o ID especificado não foi encontrado.
                            System.out.println("Cliente com ID " + idAtualiza + " não encontrado.");
                        }
                    }
                    break;

                case 4: // Deletar cliente
                    System.out.print("ID do cliente para deletar: ");
                    try {
                        // Tenta converter a entrada do usuário para um Long (ID do cliente a ser deletado).
                        Long idDelete = Long.parseLong(scanner.nextLine());
                        // Chama o método delete do controller. O tratamento de mensagens de sucesso/erro
                        // para esta operação específica é feito dentro do método delete no controller/DAO.
                        controller.delete(idDelete);
                    } catch (NumberFormatException es) {
                        // Captura erro se o ID não for um número válido.
                        System.out.println("ID inválido.");
                    } catch (RuntimeException run) {
                        // Captura outras exceções de tempo de execução que podem vir do DAO/Controller
                        // (ex: cliente não encontrado, erro de banco de dados).
                        System.out.println(run.getMessage()); // Exibe a mensagem da exceção.
                    }
                    break;

                case 0: // Sair da aplicação
                    System.out.println("Encerrando...");
                    break;

                default: // Opção inválida
                    System.out.println("Opção inválida!");
                    break;
            }

        }  while (opcao != 0); // O loop continua enquanto a opção não for '0'.

    
        
        ```
    * Após criar a classe `Main`, execute-a a partir de sua IDE ou via Maven: `mvn exec:java -Dexec.mainClass="org.example.Main"`.

##  Conceitos Chave Demonstrados

* **Padrão DAO (Data Access Object):** Separa a lógica de persistência da lógica de negócios, promovendo abstração e facilidade de troca de tecnologias de acesso a dados.
* **Injeção Manual de Dependência:** O `ClienteController` recebe sua dependência (`ClienteRepository`) via construtor, um princípio fundamental da Inversão de Controle.
* **Mapeamento Objeto-Relacional (ORM) com Hibernate:** Uso de anotações JPA para mapear classes Java para tabelas de banco de dados, simplificando a interação com o SGBD.
* **Transações:** Gerenciamento explícito de transações (iniciar, commitar, rollback) para garantir a integridade e consistência dos dados.
* **Validação de Dados:** Utilização de `jakarta.validation` para validar os dados de entrada no `ClienteForm` antes da persistência, assegurando a qualidade dos dados.
* **Separação de Responsabilidades:** Divisão clara do código em camadas (Controller para requisições, Repository para contrato de dados, Service/DAO para implementação de dados, Entity para modelo, Form para DTO de entrada).

##  Principais Aprendizados e Dificuldades


### Aprendizados:




### Dificuldades:



