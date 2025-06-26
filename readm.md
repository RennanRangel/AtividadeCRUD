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

                System.out.println("--- Criando Cliente ---");
                ClienteForm newClientForm = new ClienteForm();
                newClientForm.setNome("Fulano de Tal");
                newClientForm.setEmail("fulano@example.com");
                newClientForm.setTelefone("11987654321");
                Cliente createdCliente = controller.create(newClientForm);
                System.out.println("Cliente criado: " + createdCliente.getNome() + " (ID: " + createdCliente.getIdCliente() + ")");

                System.out.println("\n--- Buscando Cliente por ID ---");
                Cliente foundCliente = controller.get(createdCliente.getIdCliente());
                if (foundCliente != null) {
                    System.out.println("Cliente encontrado: " + foundCliente.getNome());
                } else {
                    System.out.println("Cliente não encontrado.");
                }

                System.out.println("\n--- Atualizando Cliente ---");
                ClienteForm updateForm = new ClienteForm();
                updateForm.setNome("Fulano Atualizado");
                updateForm.setEmail("fulano.novo@example.com");
                updateForm.setTelefone("11999998888");
                Cliente updatedCliente = controller.update(createdCliente.getIdCliente(), updateForm);
                if (updatedCliente != null) {
                    System.out.println("Cliente atualizado: " + updatedCliente.getNome());
                }

                System.out.println("\n--- Listando todos os Clientes ---");
                List<Cliente> allClients = controller.getAll(null);
                allClients.forEach(c -> System.out.println("ID: " + c.getIdCliente() + ", Nome: " + c.getNome()));

                System.out.println("\n--- Deletando Cliente ---");
                controller.delete(createdCliente.getIdCliente());
                System.out.println("Verificando se o cliente foi deletado...");
                System.out.println("Cliente: " + controller.get(createdCliente.getIdCliente()));

                // Nota: Em uma aplicação real, a SessionFactory deve ser fechada na finalização da aplicação.
                // Isso normalmente é feito em um hook de shutdown ou gerenciado por um container como Spring.
                // No ClienteDAO, a SessionFactory é estática, então ela persistirá enquanto a JVM estiver ativa.
            }
        }
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







