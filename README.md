#  Sistema de Gerenciamento de Clientes

Este projeto demonstra a implementação de um serviço de gerenciamento de clientes em Java, utilizando os princípios de persistência de dados com Hibernate (como provedor JPA) e boas práticas de arquitetura. O objetivo é simular as operações CRUD (Criar, Ler, Atualizar, Deletar) de clientes, com foco na separação de responsabilidades e na validação de entrada de dados.

###
##  Visão Geral

O sistema é estruturado em camadas distintas (Controller, Service/Repository, Entity, Form) para promover a organização, manutenibilidade e testabilidade do código. Ele utiliza o Hibernate para mapeamento objeto-relacional (ORM) e interação com o banco de dados, e Jakarta Validation para garantir a integridade dos dados de entrada.

###
##  Tecnologias Utilizadas

* **Java 17+**
* **Hibernate 6+** (como provedor JPA)
* **JDBC** (para conexão com o banco de dados)
* **MySQL** (banco de dados)
* **Jakarta Validation** (para validação de dados)
* **Maven** (ferramenta de build e gerenciamento de dependências)

###
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


###
## Passos para Configurar o Maven

### Acessar o Site e Pesquisar a Dependência

* Abra seu navegador e vá para https://mvnrepository.com/.

####
* Na barra de pesquisa central, digite o nome da biblioteca deseja adicionar ao seu projeto (por exemplo,"JUnit", Jakarta Persistence).


### Selecionar a Dependência Correta

####
* Nos resultados da pesquisa, clique na dependência que melhor corresponde ao que você procura. Geralmente, você vai querer a que tem o groupId e artifactId mais comuns ou oficiais (ex: jakarta.persistence-api, junit para JUnit).

####
Após clicar na dependência, você verá uma lista das versões disponíveis. É crucial escolher a versão correta. Recomenda-se:

* Versões Estáveis: Prefira as versões estáveis (sem -SNAPSHOT, -BETA, -RC no final), a menos que você tenha um motivo específico para usar uma versão de pré-lançamento.

####
* Versão Mais Recente: Na maioria dos casos, a versão mais recente e estável é a melhor escolha, pois geralmente contém correções de bugs e novos recursos.

####
* Clique no número da versão que você deseja usar.


### Copiar o Snippet de Dependência

####
* Ao clicar na versão, você será levado a uma página com os detalhes dessa versão específica.

####
* Nessa página, você encontrará vários "snippets" (trechos de código) para diferentes gerenciadores de dependência. Por padrão, o Maven é o primeiro e mais proeminente.

####
* Copie o código XML completo que está dentro da caixa de texto (ele começa com <dependency> e termina com </dependency>).


### Adicionar a Dependência ao seu Arquivo pom.xml

####
* Abra seu projeto Maven na sua IDE (IntelliJ IDEA, Eclipse, VS Code, etc.).

####
* Localize o arquivo pom.xml na raiz do seu projeto.

####
* Procure pela seção <dependencies>. Se ela não existir, você precisará criá-la logo abaixo da tag <project> principal, mas antes de quaisquer outras tags de nível superior como <build>.
  Exemplo de estrutura básica do pom.xml com a seção <dependencies>:

 ```pom.xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.seusprojetos</groupId>
    <artifactId>meu-app-maven</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- COLE AQUI O SNIPPET DA DEPENDÊNCIA -->
    </dependencies>

</project>
```

* Cole o snippet <dependency> que você copiou do mvnrepository.com dentro da tag <dependencies>.
  Exemplo após colar uma dependência do Jakarta Persistence:

```pom
<!-- https://mvnrepository.com/artifact/jakarta.persistence/jakarta.persistence-api -->
<dependency>
    <groupId>jakarta.persistence</groupId>
    <artifactId>jakarta.persistence-api</artifactId>
    <version>3.2.0</version>
</dependency>
```

### Salvar e Atualizar o Projeto Maven

* Salve o arquivo pom.xml.

####
* Sua IDE geralmente detectará a mudança no pom.xml e perguntará se você deseja "Importar mudanças" ou "Sincronizar Projeto Maven". Confirme esta ação.

####
* Se a IDE não perguntar, procure uma opção como "Maven" -> "Reload Project" ou "Update Project" no menu de contexto do seu projeto ou na janela de ferramentas Maven da sua IDE.

Ao fazer isso, o Maven irá:

* Verificar seu repositório local.

####
* Se a dependência não estiver lá, ele a baixará do Maven Central (ou de outros repositórios configurados).

####
* A dependência será adicionada ao classpath do seu projeto, tornando suas classes e funcionalidades disponíveis para o seu código.

Agora você está pronto para usar a biblioteca em seu código!

###
##  Passos para Configurar o Hibernate (MySQL)

O Hibernate é configurado através do arquivo `hibernate.cfg.xml`, localizado em `src/main/resources/`. Siga os passos para garantir a conexão correta com seu banco de dados MySQL:

1.  **Crie o Banco de Dados e a Tabela:**
    ####
    * No seu servidor MySQL, conecte-se a um cliente (ex: MySQL Workbench, linha de comando `mysql -u root -p`).
    ####
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

###
2.  **Ajuste as Credenciais no `hibernate.cfg.xml`:**
    ####
    * Abra o arquivo `src/main/resources/hibernate.cfg.xml`.
    ####
    * Localize as propriedades de conexão e atualize-as com os detalhes do seu ambiente e o nome do banco de dados `cadastro`
    ####
    * O conteúdo do seu arquivo hibernate.cfg.xml será:

```xml
       <?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <!-- Configuração do banco -->
        <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
        <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/cadastro</property>
        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password"></property> <!-- Deixe em branco se não tiver senha, ou coloque sua senha -->

        <property name="hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>
        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.hbm2ddl.auto">update</property>

        <!-- Mapeamento das entidades -->
        <mapping class="org.example.Entity.Cliente"/>
    </session-factory>
</hibernate-configuration>
```

### Explicação das Propriedades:

* **<property name=hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>:** Define o driver JDBC a ser usado para conectar ao MySQL. com.mysql.cj.jdbc.Driver é o driver para MySQL Connector/J 8.x.

###
####
* **<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/cadastro</property>:** A URL de conexão com o banco de dados.

###
####
* **localhost:3306:** Onde seu servidor MySQL está rodando e a porta padrão. Ajuste se for diferente.

###
####
* **cadastro:** O nome do banco de dados que você criou no passo 1.

###
####
* **<property name=hibernate.connection.username">root</property:** O nome de usuário do seu MySQL. Ajuste se for diferente.

###
####
* **<property name=hibernate.connection.password"></property>:** A senha do seu usuário MySQL. Ajuste se for diferente. Se o usuário root não tiver senha, deixe as aspas vazias "".

###
####
* **<property name= hibernate.dialect">org.hibernate.dialect.MySQLDialect</property>:** Indica ao Hibernate qual dialeto SQL usar para interagir com o MySQL.

###
####
* **<property name= hibernate.show_sql">true</property>:** Faz com que o Hibernate imprima as instruções SQL geradas no console. Muito útil para depuração.

###
####
* **<property name= hibernate.hbm2ddl.auto">update</property>:** Extremamente importante e deve ser usado com cautela!

###
####
* **<mapping class= org.example.Entity.Cliente"/>:** Informa ao Hibernate qual classe de entidade Java ele deve mapear para uma tabela no banco de dados.

###
##  Como Entender este Código:

####
`Controller/ClienteController.java`

**Passo 1:** Declaração de Dependência. Observe a linha que declara private final ClienteRepository service;. Isso significa que ClienteController precisa de uma implementação de ClienteRepository para funcionar.
###

**Passo 2:** Injeção de Dependência. Veja o construtor public ClienteController(ClienteRepository service). Aqui, o ClienteController não cria seu próprio ClienteRepository, mas o recebe de fora. Isso permite trocar facilmente a forma como o banco de dados é acessado (por exemplo, usar um DAO diferente ou um mock para testes).
###

**Passo 3:** Encapsulamento das Operações. Cada método (create, get, update, delete) no ClienteController corresponde a uma operação CRUD (Criar, Ler, Atualizar, Deletar) que você faria em um sistema real. Eles são a interface pública para as ações relacionadas a clientes.
###

**Passo 4:** Delegação de Responsabilidade. Dentro de cada método, a única coisa que o ClienteController faz é chamar o método correspondente no service (que é o ClienteRepository). Por exemplo, service.create(form) significa que a lógica real de salvar no banco de dados está no ClienteDAO (a implementação do ClienteRepository), não aqui. O Controller apenas orquestra, não executa a lógica de persistência.

###

```java
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
 ```

###

###
`Entity/Cliente.java`

**Passo 1:** Anotação @Entity. Esta anotação, no topo da classe, diz ao Hibernate (e a qualquer provedor JPA) que Cliente é uma entidade que deve ser mapeada para uma tabela no banco de dados.
###

**Passo 2:** Anotação @Table(name = "cliente"). Esta anotação é opcional se o nome da tabela for o mesmo nome da classe em minúsculas (neste caso, cliente). Ela serve para especificar explicitamente o nome da tabela no banco de dados, caso seja diferente do nome da classe.
###

**Passo 3:** Anotação @Id. Indica que o campo idCliente é a chave primária da tabela. É essencial para identificar unicamente cada registro.
###

**Passo 4:** Anotação @GeneratedValue(strategy = GenerationType.IDENTITY). Esta anotação instrui o banco de dados a gerar automaticamente o valor da chave primária (auto-incremento) cada vez que um novo registro é inserido. IDENTITY é ideal para MySQL.
###

**Passo 5:** Anotação @Column. Usada para mapear um atributo da classe para uma coluna da tabela. Você pode usar parâmetros como nullable = false para indicar que a coluna não pode ter valores nulos e unique = true para garantir que os valores na coluna sejam únicos (como o email).

###

```java
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
```
###

###
`Entity/Form/ClienteForm.java`

**Passo 1:** Propósito do ClienteForm. Esta classe não é uma entidade do banco de dados (não tem @Entity). Ela serve como um "formulário" ou DTO (Data Transfer Object) para receber e validar os dados de entrada do usuário antes que eles sejam usados para criar ou atualizar um objeto Cliente.
###

**Passo 2:** Anotações de Validação (jakarta.validation.constraints).
####
@NotEmpty: Garante que o campo (string) não seja nulo nem vazio.

@Size: Define os limites mínimo e máximo de caracteres para uma string.

@Email: Valida se a string tem um formato de e-mail válido.

Cada anotação pode ter um parâmetro message para personalizar a mensagem de erro que será exibida se a validação falhar.
###

**Passo 3:** Separação de Preocupações. O uso de ClienteForm separa a validação dos dados de entrada da lógica de persistência da entidade Cliente. Isso torna o código mais limpo e modular. Se as regras de validação mudarem, você só precisará mexer nesta classe, não na entidade.
###

```java

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
```

###
`Repository/ClienteRepository.java`

**Passo 1:** Interface Pura. Esta é uma interface Java. Isso significa que ela define um "contrato": uma lista de métodos que qualquer classe que implemente ClienteRepository deve ter. Ela não contém nenhuma lógica de implementação.
###

**Passo 2:** Abstração da Camada de Dados. O objetivo principal desta interface é abstrair os detalhes de como os dados são realmente armazenados ou recuperados. Quem usa ClienteRepository (como o ClienteController) não precisa saber se você está usando Hibernate, JDBC puro, ou qualquer outra tecnologia.
###
**Passo 3:** Operações CRUD. Os métodos definidos (create, get, getAll, update, delete, listar) correspondem diretamente às operações básicas de gerenciamento de dados (Criar, Ler, Atualizar, Deletar).
###

```java
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
```

###
`Service/ClienteDAO.java`

**Passo 1**: Implementação da Interface. A linha implements ClienteRepository indica que a classe ClienteDAO é a implementação concreta da interface ClienteRepository. Isso significa que ela deve fornecer o código para todos os métodos definidos em ClienteRepository.


###
**Passo 2**: SessionFactory (Ponto de Entrada do Hibernate).

private static final SessionFactory sessionFactory = buildSessionFactory(); A SessionFactory é uma fábrica de sessões do Hibernate. É um objeto "pesado" (caro de criar), por isso é criada apenas uma vez (como um static final) quando a classe é carregada.

buildSessionFactory(): Este método encapsula a lógica de inicialização do Hibernate, lendo o arquivo hibernate.cfg.xml para configurar a conexão com o banco de dados.


###
**Passo 3**: Session (Conexão ao Banco).

try (Session session = sessionFactory.openSession()): Uma Session é uma conexão de curta duração ao banco de dados. É por meio dela que todas as operações de persistência (salvar, buscar, atualizar, deletar) são realizadas. O try-with-resources garante que a sessão seja fechada automaticamente após o uso.


###
**Passo 4:** Transaction (Controle de Atomicidade).

transaction = session.beginTransaction(); e transaction.commit();: Uma transação é um conjunto de operações que devem ser executadas como uma única unidade lógica. Se qualquer parte falhar, toda a transação é desfeita (transaction.rollback();), garantindo a integridade dos dados.

###
**Passo 5**: Operações CRUD com Hibernate.

create(ClienteForm form): Cria um objeto Cliente a partir dos dados do ClienteForm. session.persist(cliente); diz ao Hibernate para salvar o objeto cliente no banco de dados.

get(Long id): session.find(Cliente.class, id); busca um objeto Cliente pelo seu ID.

getAll(String filtro): session.createQuery("FROM Cliente", Cliente.class).list(); recupera todos os objetos Cliente do banco de dados. A lógica de filtragem é feita em memória, o que pode não ser ideal para grandes volumes de dados, mas é simples para este exemplo.

update(Long id, ClienteForm form): Primeiro, busca o cliente existente pelo ID. Se encontrado, atualiza seus atributos com os dados do formulário. O Hibernate usa o "dirty checking": ele detecta automaticamente que o objeto cliente foi modificado e salva as alterações no banco de dados quando a transação é commitada.

delete(Long id): Busca o cliente pelo ID. session.remove(cliente); remove o objeto cliente do banco de dados.

###
```java

package org.example.Service;

import org.example.Entity.Cliente;
import org.example.Entity.Form.ClienteForm;
import org.example.Repository.ClienteRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements ClienteRepository {

    // SessionFactory é um objeto caro de criar, usado para abrir sessões com o banco
    private static final SessionFactory sessionFactory = buildSessionFactory();

    // Método que inicializa o SessionFactory a partir do arquivo de configuração hibernate.cfg.xml
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Erro ao criar o SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    // Cria um novo cliente no banco usando os dados do ClienteForm
    @Override
    public Cliente create(ClienteForm form) {
        Cliente cliente = new Cliente();
        cliente.setNome(form.getNome());
        cliente.setTelefone(form.getTelefone());
        cliente.setEmail(form.getEmail());

        Transaction transaction = null;

        // Tenta abrir uma sessão, iniciar uma transação, salvar o cliente e commitar
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.persist(cliente);  // Persiste o objeto cliente no banco
            transaction.commit();
            return cliente;
        } catch (Exception e) {
            // Se der erro, desfaz a transação
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Busca um cliente no banco pelo ID
    @Override
    public Cliente get(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.find(Cliente.class, id);
        }
    }

    // Busca todos os clientes, podendo filtrar por nome, email ou telefone
    @Override
    public List<Cliente> getAll(String filtro) {
        try (Session session = sessionFactory.openSession()) {

            // Busca todos os clientes (sem filtro na consulta)
            List<Cliente> clientes = session.createQuery("FROM Cliente", Cliente.class).list();

            // Se filtro estiver vazio ou nulo, retorna todos os clientes
            if (filtro == null || filtro.trim().isEmpty()) {
                return clientes;
            }

            // Se existir filtro, cria uma lista para os clientes filtrados
            List<Cliente> clientesFiltrados = new ArrayList<>();

            // Para cada cliente, verifica se algum campo é igual ao filtro (exato, sem lowercase nem contains)
            for (Cliente cliente : clientes) {
                if (cliente.getNome().equals(filtro) ||
                        cliente.getEmail().equals(filtro) ||
                        cliente.getTelefone().equals(filtro)) {

                    // Adiciona o cliente que passou no filtro à lista filtrada
                    clientesFiltrados.add(cliente);
                }
            }

            // Retorna os clientes que passaram no filtro
            return clientesFiltrados;
        }
    }

    // Atualiza os dados de um cliente já existente
    @Override
    public Cliente update(Long id, ClienteForm form) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Busca o cliente no banco pelo ID
            Cliente cliente = session.find(Cliente.class, id);

            if (cliente != null) {
                // Atualiza os campos com os dados do form
                cliente.setNome(form.getNome());
                cliente.setTelefone(form.getTelefone());
                cliente.setEmail(form.getEmail());

                // Hibernate detecta mudanças automaticamente (dirty checking) e salva ao commitar
                transaction.commit();
                return cliente;
            } else {
                // Se cliente não encontrado, desfaz a transação e retorna null
                System.out.println("Cliente com ID " + id + " não encontrado.");
                transaction.rollback();
                return null;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Remove um cliente do banco pelo ID
    @Override
    public void delete(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Busca o cliente pelo ID
            Cliente cliente = session.find(Cliente.class, id);

            if (cliente != null) {
                // Remove o cliente e confirma a transação
                session.remove(cliente);
                transaction.commit();
                System.out.println("Cliente removido com sucesso!");
            } else {
                // Se não encontrado, desfaz a transação e lança exceção
                transaction.rollback();
                throw new RuntimeException("Cliente com ID " + id + " não encontrado.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    // Método que lista todos os clientes sem filtro (implementação simples chamando getAll(null))
    @Override
    public Iterable<Cliente> listar() {
        return getAll(null);
    }
}
```



###
`Main.java`

**Passo 1:** Ponto de Entrada (main). Este é o método que será executado quando você rodar a aplicação Java. É aqui que tudo começa.

###
**Passo 2:** Inicialização de Ferramentas.

Scanner: Usado para ler as entradas que você digita no console (nome, email, opções do menu).

ClienteRepository e ClienteController: São instanciados. Perceba que ClienteDAO (a implementação concreta) é injetada no ClienteController, seguindo o princípio de injeção de dependência.

Validator: Configurado para que possamos validar os ClienteForms com base nas anotações que definimos neles.

###
**Passo 3:** Loop do Menu Principal (do-while). O coração da interação com o usuário.

Ele continua executando (e exibindo o menu) até que você digite 0 para sair.

Ele tenta ler um número inteiro para a opção e lida com entradas inválidas (não numéricas).

###
**Passo 4:** switch (opcao) (Lógica do Menu).

case 1: Cadastrar Cliente: Solicita e lê o nome, telefone e email do console. Cria um ClienteForm com esses dados. Validação: Usa validator.validate(form) para verificar se os dados estão corretos (com base nas anotações em ClienteForm). Se houver erros, eles são impressos. Criação: Se a validação passar, controller.create(form) é chamado para salvar o cliente no banco de dados.

case 2: Listar Clientes: Chama controller.getAll("") para obter todos os clientes cadastrados. Itera sobre a lista de clientes e imprime os detalhes de cada um.

case 3: Atualizar Cliente: Solicita o ID do cliente a ser atualizado. Solicita os novos dados (nome, telefone, email). Cria um ClienteForm com os novos dados e o valida. Se válido, chama controller.update(idAtualiza, atualizaForm) para persistir as mudanças.

case 4: Deletar Cliente: Solicita o ID do cliente a ser deletado. Chama controller.delete(idDelete) para remover o cliente do banco. Inclui tratamento de erro caso o ID seja inválido ou o cliente não seja encontrado.

case 0: Sair: Encerra o loop e o programa.

###
**Passo 5:** Tratamento de Erros (try-catch). O código usa try-catch em pontos críticos (como a conversão do ID para Long ou a deleção do cliente) para lidar com possíveis erros e evitar que o programa trave, exibindo mensagens úteis ao usuário.


```java
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
```

###
###

##  Conceitos Chave Demonstrados

* **Padrão DAO (Data Access Object):** Separa a lógica de persistência da lógica de negócios, promovendo abstração e facilidade de troca de tecnologias de acesso a dados.
####
* **Injeção Manual de Dependência:** O `ClienteController` recebe sua dependência (`ClienteRepository`) via construtor, um princípio fundamental da Inversão de Controle.
####
* **Mapeamento Objeto-Relacional (ORM) com Hibernate:** Uso de anotações JPA para mapear classes Java para tabelas de banco de dados, simplificando a interação com o SGBD.
####
* **Transações:** Gerenciamento explícito de transações (iniciar, commitar, rollback) para garantir a integridade e consistência dos dados.
####
* **Validação de Dados:** Utilização de `jakarta.validation` para validar os dados de entrada no `ClienteForm` antes da persistência, assegurando a qualidade dos dados.
####
* **Separação de Responsabilidades:** Divisão clara do código em camadas (Controller para requisições, Repository para contrato de dados, Service/DAO para implementação de dados, Entity para modelo, Form para DTO de entrada).


###

##  Principais Aprendizados e Dificuldades


### Aprendizados:

* Reforçou a importância de dividir o sistema em camadas lógicas (Controller, Service/DAO, Entity/Form) para melhorar a organização, a manutenibilidade e a testabilidade do código. Cada camada tem uma responsabilidade bem definida, o que reduz o acoplamento.
####
* Compreensão dos conceitos de Mapeamento Objeto-Relacional (ORM) e como o Hibernate, como provedor JPA, facilita a interação com o banco de dados através de objetos Java, eliminando a necessidade de SQL manual para operações CRUD básicas.
####
* Apreensão sobre as anotações JPA (@Entity, @Table, @Id, @@GeneratedValue, @Column) e seu papel no mapeamento de entidades para tabelas.
####
* Entendimento do ciclo de vida da SessionFactory (única e cara) e da Session (curta duração e específica para uma unidade de trabalho).
####
* Aprendizado sobre como usar anotações (@NotEmpty, @Size, @Email) para implementar validação declarativa de dados de entrada. Isso simplifica a validação, centraliza as regras e melhora a qualidade dos dados antes que cheguem ao banco.
####
* Reforço da importância do Maven para gerenciar bibliotecas externas e o processo de build do projeto, garantindo que todas as dependências necessárias estejam disponíveis.

### Dificuldades:

* A etapa mais desafiadora foi a configuração correta do hibernate.cfg.xml trocando o MySQL para MariaDB nas configurações apresentou desafios específicos relacionados à compatibilidade de drivers e dialetos, exigindo ajustes no pom.xml e no hibernate.cfg.xml













