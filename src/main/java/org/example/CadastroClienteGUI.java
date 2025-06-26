package org.example;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Classe principal para a aplicação GUI de Cadastro de Clientes.
 * Esta aplicação permite cadastrar, visualizar, editar e excluir informações de clientes
 * armazenadas em um banco de dados MySQL.
 */
public class CadastroClienteGUI {

    // --- Configurações do Banco de Dados ---
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/cadastro"; // URL de conexão com o banco de dados MySQL
    private static final String USER = "root";                               // Nome de usuário para acesso ao banco de dados
    private static final String PASSWORD = "";                               // Senha para acesso ao banco de dados

    // --- Componentes da Interface Gráfica (UI) ---
    private static JTextField nomeField, telefoneField, emailField; // Campos de texto para entrada de dados do cliente
    private static JButton cadastrarButton, editarButton, excluirButton; // Botões para as operações CRUD
    private static JTable tabelaClientes;                                // Tabela para exibir os clientes cadastrados
    private static DefaultTableModel tabelaModel;                       // Modelo de dados para a JTable
    private static JFrame frame;                                        // Janela principal da aplicação

    private static int idSelecionado = -1; // Variável para armazenar o ID do cliente selecionado para edição/exclusão.
    // -1 indica que nenhum cliente está selecionado.

    /**
     * Método principal que inicia a aplicação GUI.
     * @param args Argumentos de linha de comando (não utilizados nesta aplicação).
     */
    public static void main(String[] args) {
        // --- Configuração de Estilos e Cores da UI ---
        // Define as cores para a barra de rolagem (ScrollBar) para combinar com o tema escuro.
        UIManager.put("ScrollBar.thumb", new ColorUIResource(new Color(80, 80, 80)));     // "Polegar" da scrollbar (parte que se move)
        UIManager.put("ScrollBar.track", new ColorUIResource(new Color(50, 50, 50)));     // Fundo/trilha da scrollbar
        UIManager.put("ScrollBar.thumbHighlight", new ColorUIResource(new Color(100, 100, 100))); // Destaque do polegar
        UIManager.put("ScrollBar.thumbDarkShadow", new ColorUIResource(new Color(60, 60, 60))); // Sombra escura do polegar
        UIManager.put("ScrollBar.thumbShadow", new ColorUIResource(new Color(70, 70, 70)));   // Sombra do polegar

        // --- Configuração da Janela Principal (JFrame) ---
        frame = new JFrame("Cadastro de Cliente");            // Cria uma nova janela com o título "Cadastro de Cliente"
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Define a operação padrão ao fechar a janela (sair da aplicação)
        frame.setSize(700, 600);                              // Define o tamanho da janela (largura, altura)
        frame.setLocationRelativeTo(null);                    // Centraliza a janela na tela

        // --- Configuração do Painel Principal (JPanel) ---
        JPanel panel = new JPanel(new GridBagLayout()); // Cria um painel com GridBagLayout para organizar os componentes
        panel.setBackground(Color.DARK_GRAY);          // Define a cor de fundo do painel como cinza escuro
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adiciona uma borda vazia para espaçamento interno

        GridBagConstraints gbc = new GridBagConstraints(); // Objeto para controlar o layout dos componentes no GridBagLayout
        gbc.insets = new Insets(10, 10, 10, 10);           // Define o espaçamento (padding) entre os componentes

        // --- Fontes Padrão ---
        Font fonteLabel = new Font("Arial", Font.BOLD, 18); // Fonte para os rótulos (labels)
        Font fonteField = new Font("Arial", Font.PLAIN, 16); // Fonte para os campos de texto (text fields)

        // --- Componentes de Entrada de Dados (Nome, Telefone, Email) ---

        // Rótulo e Campo de Texto para Nome
        JLabel nomeLabel = new JLabel("Nome");        // Cria um rótulo "Nome"
        nomeLabel.setFont(fonteLabel);                // Define a fonte do rótulo
        nomeLabel.setForeground(Color.WHITE);         // Define a cor do texto do rótulo como branco
        gbc.gridx = 0;                                // Define a coluna inicial no GridBagLayout
        gbc.gridy = 0;                                // Define a linha inicial no GridBagLayout
        gbc.gridwidth = 2;                            // Ocupa 2 colunas
        gbc.anchor = GridBagConstraints.CENTER;       // Centraliza o componente na célula
        panel.add(nomeLabel, gbc);                    // Adiciona o rótulo ao painel

        nomeField = new JTextField(25);               // Cria um campo de texto com 25 colunas
        nomeField.setFont(fonteField);                // Define a fonte do campo de texto
        nomeField.setBackground(Color.GRAY);          // Define a cor de fundo do campo de texto como cinza
        nomeField.setForeground(Color.WHITE);         // Define a cor do texto do campo de texto como branco
        nomeField.setCaretColor(Color.WHITE);         // Define a cor do cursor de texto como branco
        gbc.gridy = 1;                                // Move para a próxima linha
        gbc.fill = GridBagConstraints.HORIZONTAL;     // Ocupa todo o espaço horizontal disponível na célula
        panel.add(nomeField, gbc);                    // Adiciona o campo de texto ao painel

        // Rótulo e Campo de Texto para Telefone
        JLabel telefoneLabel = new JLabel("Telefone");
        telefoneLabel.setFont(fonteLabel);
        telefoneLabel.setForeground(Color.WHITE);
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;           // Não ocupa todo o espaço horizontal
        panel.add(telefoneLabel, gbc);

        telefoneField = new JTextField(25);
        telefoneField.setFont(fonteField);
        telefoneField.setBackground(Color.GRAY);
        telefoneField.setForeground(Color.WHITE);
        telefoneField.setCaretColor(Color.WHITE);
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(telefoneField, gbc);

        // Rótulo e Campo de Texto para Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(fonteLabel);
        emailLabel.setForeground(Color.WHITE);
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(emailLabel, gbc);

        emailField = new JTextField(25);
        emailField.setFont(fonteField);
        emailField.setBackground(Color.GRAY);
        emailField.setForeground(Color.WHITE);
        emailField.setCaretColor(Color.WHITE);
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(emailField, gbc);

        // --- Botões de Ação (Cadastrar, Editar, Excluir) ---
        cadastrarButton = new JButton("Cadastrar"); // Cria o botão "Cadastrar"
        editarButton = new JButton("Editar");       // Cria o botão "Editar"
        excluirButton = new JButton("Excluir");     // Cria o botão "Excluir"

        Font fonteBtn = new Font("Arial", Font.BOLD, 20); // Fonte para os botões
        cadastrarButton.setFont(fonteBtn);
        editarButton.setFont(fonteBtn);
        excluirButton.setFont(fonteBtn);

        // Cores personalizadas para os botões
        Color btnFundoEscuro = new Color(60, 63, 65); // Cor de fundo escura
        Color btnTextoClaro = Color.WHITE;           // Cor do texto clara

        cadastrarButton.setBackground(btnFundoEscuro); // Define a cor de fundo do botão Cadastrar
        cadastrarButton.setForeground(btnTextoClaro);  // Define a cor do texto do botão Cadastrar
        editarButton.setBackground(btnFundoEscuro);    // Define a cor de fundo do botão Editar
        editarButton.setForeground(btnTextoClaro);     // Define a cor do texto do botão Editar
        excluirButton.setBackground(btnFundoEscuro);   // Define a cor de fundo do botão Excluir
        excluirButton.setForeground(btnTextoClaro);    // Define a cor do texto do botão Excluir

        // Painel para agrupar os botões
        JPanel botoesPanel = new JPanel();
        botoesPanel.setBackground(Color.DARK_GRAY); // Cor de fundo do painel de botões
        botoesPanel.add(cadastrarButton);           // Adiciona o botão Cadastrar
        botoesPanel.add(editarButton);              // Adiciona o botão Editar
        botoesPanel.add(excluirButton);             // Adiciona o botão Excluir

        gbc.gridy = 6;                                // Move para a próxima linha no GridBagLayout
        gbc.fill = GridBagConstraints.NONE;           // Não ocupa todo o espaço
        gbc.anchor = GridBagConstraints.CENTER;       // Centraliza o painel de botões
        panel.add(botoesPanel, gbc);                  // Adiciona o painel de botões ao painel principal

        // --- Tabela para Exibir Clientes ---
        // Cria o modelo de tabela com as colunas "ID", "Nome", "Telefone", "Email"
        tabelaModel = new DefaultTableModel(new Object[]{"ID", "Nome", "Telefone", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna todas as células da tabela não editáveis (somente leitura)
            }
        };
        tabelaClientes = new JTable(tabelaModel);               // Cria a JTable usando o modelo de tabela
        tabelaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Permite selecionar apenas uma linha por vez
        tabelaClientes.getColumnModel().getColumn(0).setPreferredWidth(50); // Define a largura preferida para a coluna "ID"

        // Cores personalizadas para a tabela
        Color fundoEscuro = new Color(43, 43, 43);      // Cor de fundo escura para as células
        Color textoClaro = Color.WHITE;                 // Cor do texto das células
        Color fundoSelecao = new Color(75, 110, 175);   // Cor de fundo da linha selecionada

        tabelaClientes.setBackground(fundoEscuro);      // Define a cor de fundo da tabela
        tabelaClientes.setForeground(textoClaro);       // Define a cor do texto da tabela
        tabelaClientes.setGridColor(new Color(60, 63, 65)); // Define a cor das linhas da grade da tabela
        tabelaClientes.setSelectionBackground(fundoSelecao); // Define a cor de fundo da seleção
        tabelaClientes.setSelectionForeground(Color.WHITE);  // Define a cor do texto da seleção
        tabelaClientes.setFillsViewportHeight(true);         // Faz com que a tabela preencha a altura do seu viewport
        tabelaClientes.setBorder(new LineBorder(new Color(30, 30, 30), 2)); // Adiciona uma borda à tabela

        // --- Cabeçalho da Tabela ---
        JTableHeader header = tabelaClientes.getTableHeader(); // Obtém o cabeçalho da tabela
        header.setBackground(new Color(60, 63, 65));         // Define a cor de fundo do cabeçalho
        header.setForeground(Color.WHITE);                   // Define a cor do texto do cabeçalho
        header.setFont(new Font("Arial", Font.BOLD, 16));    // Define a fonte do cabeçalho
        header.setBorder(new LineBorder(new Color(30, 30, 30), 2)); // Adiciona uma borda ao cabeçalho

        // Painel de rolagem para a tabela (permite rolar se houver muitos dados)
        JScrollPane scrollPane = new JScrollPane(tabelaClientes); // Cria um JScrollPane e adiciona a tabela a ele
        scrollPane.setPreferredSize(new Dimension(650, 250));  // Define o tamanho preferido do scroll pane
        scrollPane.setBorder(new LineBorder(new Color(30, 30, 30), 3)); // Adiciona uma borda ao scroll pane
        gbc.gridy = 7;                                          // Move para a próxima linha
        gbc.fill = GridBagConstraints.BOTH;                     // Ocupa todo o espaço disponível horizontal e verticalmente
        panel.add(scrollPane, gbc);                             // Adiciona o scroll pane ao painel principal

        // --- Adicionar Listeners de Eventos aos Botões ---
        cadastrarButton.addActionListener(e -> cadastrarCliente());  // Adiciona um listener para o botão Cadastrar
        editarButton.addActionListener(e -> carregarParaEdicao());   // Adiciona um listener para o botão Editar
        excluirButton.addActionListener(e -> excluirCliente());      // Adiciona um listener para o botão Excluir

        // --- Carregar Dados Iniciais ---
        carregarClientes(); // Chama o método para carregar os clientes existentes do banco de dados na inicialização

        // --- Finalização da Janela ---
        frame.add(panel);         // Adiciona o painel principal à janela
        frame.setVisible(true);   // Torna a janela visível
    }

    /**
     * Método para cadastrar um novo cliente no banco de dados.
     * Valida os campos de entrada e, se válidos, insere os dados na tabela 'cliente'.
     */
    private static void cadastrarCliente() {
        String nome = nomeField.getText().trim();       // Obtém o texto do campo Nome e remove espaços em branco
        String telefone = telefoneField.getText().trim(); // Obtém o texto do campo Telefone e remove espaços em branco
        String email = emailField.getText().trim();     // Obtém o texto do campo Email e remove espaços em branco

        // Validação: verifica se todos os campos estão preenchidos
        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame,         // Exibe uma mensagem de erro
                    "Todos os campos são obrigatórios!",
                    "Erro", JOptionPane.ERROR_MESSAGE);
            return; // Sai do método se houver campos vazios
        }

        // Bloco try-with-resources para garantir que a conexão seja fechada automaticamente
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // SQL para inserção de um novo cliente
            String sql = "INSERT INTO cliente (nome, telefone, email) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql); // Cria um PreparedStatement para evitar SQL Injection
            stmt.setString(1, nome);      // Define o valor do primeiro placeholder (?) como o nome
            stmt.setString(2, telefone);  // Define o valor do segundo placeholder (?) como o telefone
            stmt.setString(3, email);     // Define o valor do terceiro placeholder (?) como o email
            stmt.executeUpdate();         // Executa a instrução SQL de inserção

            JOptionPane.showMessageDialog(frame, // Exibe uma mensagem de sucesso
                    "Cliente cadastrado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            limparCampos();     // Limpa os campos de texto após o cadastro
            carregarClientes(); // Recarrega a tabela de clientes para exibir o novo registro

        } catch (SQLException ex) {
            // Captura e exibe qualquer erro de SQL que ocorra
            JOptionPane.showMessageDialog(frame,
                    "Erro ao salvar no banco de dados:\n" + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para carregar todos os clientes do banco de dados e exibi-los na tabela.
     */
    private static void carregarClientes() {
        tabelaModel.setRowCount(0); // Limpa todas as linhas existentes na tabela antes de recarregar

        // Bloco try-with-resources para garantir que a conexão seja fechada automaticamente
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // SQL para selecionar todos os clientes
            String sql = "SELECT id_cliente, nome, telefone, email FROM cliente";
            Statement stmt = conn.createStatement();      // Cria um Statement para executar a consulta
            ResultSet rs = stmt.executeQuery(sql);        // Executa a consulta e obtém o ResultSet

            // Itera sobre o ResultSet e adiciona cada cliente ao modelo da tabela
            while (rs.next()) {
                int id = rs.getInt("id_cliente");           // Obtém o ID do cliente
                String nome = rs.getString("nome");         // Obtém o nome do cliente
                String telefone = rs.getString("telefone"); // Obtém o telefone do cliente
                String email = rs.getString("email");       // Obtém o email do cliente
                tabelaModel.addRow(new Object[]{id, nome, telefone, email}); // Adiciona uma nova linha à tabela
            }
        } catch (SQLException ex) {
            // Captura e exibe qualquer erro de SQL que ocorra
            JOptionPane.showMessageDialog(frame,
                    "Erro ao carregar clientes:\n" + ex.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para carregar os dados do cliente selecionado na tabela para os campos de edição.
     */
    private static void carregarParaEdicao() {
        int linhaSelecionada = tabelaClientes.getSelectedRow(); // Obtém o índice da linha selecionada na tabela
        if (linhaSelecionada == -1) { // Verifica se nenhuma linha foi selecionada
            JOptionPane.showMessageDialog(frame, "Selecione um cliente para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // Sai do método se nenhuma linha estiver selecionada
        }

        // Obtém os dados da linha selecionada
        idSelecionado = (int) tabelaModel.getValueAt(linhaSelecionada, 0);       // ID do cliente (coluna 0)
        String nome = (String) tabelaModel.getValueAt(linhaSelecionada, 1);     // Nome do cliente (coluna 1)
        String telefone = (String) tabelaModel.getValueAt(linhaSelecionada, 2); // Telefone do cliente (coluna 2)
        String email = (String) tabelaModel.getValueAt(linhaSelecionada, 3);    // Email do cliente (coluna 3)

        // Preenche os campos de texto com os dados do cliente selecionado
        nomeField.setText(nome);
        telefoneField.setText(telefone);
        emailField.setText(email);

        // Desabilita o botão Cadastrar e habilita os botões Editar e Excluir
        cadastrarButton.setEnabled(false);
        editarButton.setEnabled(true);
        excluirButton.setEnabled(true);

        // Remove qualquer ActionListener anterior do botão editar para evitar que a ação seja duplicada
        // quando carregarParaEdicao for chamado múltiplas vezes.
        for (ActionListener al : editarButton.getActionListeners()) {
            editarButton.removeActionListener(al);
        }

        // Adiciona um novo ActionListener ao botão editar que chama o método editarCliente()
        // Isso muda a funcionalidade do botão "Editar" para "Salvar Alterações" quando um cliente está em edição.
        editarButton.addActionListener(e -> editarCliente());
    }

    /**
     * Método para atualizar as informações de um cliente existente no banco de dados.
     * Este método é chamado após os dados serem carregados para edição e o usuário clicar em "Editar" novamente.
     */
    private static void editarCliente() {
        // Verifica se um cliente foi selecionado para edição
        if (idSelecionado == -1) {
            JOptionPane.showMessageDialog(frame, "Nenhum cliente selecionado para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nome = nomeField.getText().trim();
        String telefone = telefoneField.getText().trim();
        String email = emailField.getText().trim();

        // Validação: verifica se todos os campos estão preenchidos
        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Todos os campos são obrigatórios!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Bloco try-with-resources para garantir que a conexão seja fechada automaticamente
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // SQL para atualização de um cliente
            String sql = "UPDATE cliente SET nome = ?, telefone = ?, email = ? WHERE id_cliente = ?";
            PreparedStatement stmt = conn.prepareStatement(sql); // Cria um PreparedStatement
            stmt.setString(1, nome);      // Define o novo nome
            stmt.setString(2, telefone);  // Define o novo telefone
            stmt.setString(3, email);     // Define o novo email
            stmt.setInt(4, idSelecionado); // Usa o ID do cliente selecionado para a cláusula WHERE
            stmt.executeUpdate();         // Executa a instrução SQL de atualização

            JOptionPane.showMessageDialog(frame, "Cliente atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();     // Limpa os campos de texto
            carregarClientes(); // Recarrega a tabela para exibir as alterações
            idSelecionado = -1; // Reseta o ID selecionado

            cadastrarButton.setEnabled(true); // Reabilita o botão Cadastrar

            // Remove o listener do botão editar para que ele volte à sua função original (carregarParaEdicao)
            for (ActionListener al : editarButton.getActionListeners()) {
                editarButton.removeActionListener(al);
            }
            // OBS: O listener para carregarParaEdicao é adicionado no método main, então ele será o padrão novamente.

        } catch (SQLException ex) {
            // Captura e exibe qualquer erro de SQL que ocorra
            JOptionPane.showMessageDialog(frame, "Erro ao atualizar cliente:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método para excluir um cliente do banco de dados.
     * Solicita confirmação ao usuário antes de realizar a exclusão.
     */
    private static void excluirCliente() {
        int linhaSelecionada = tabelaClientes.getSelectedRow(); // Obtém o índice da linha selecionada
        if (linhaSelecionada == -1) { // Verifica se nenhuma linha foi selecionada
            JOptionPane.showMessageDialog(frame, "Selecione um cliente para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return; // Sai do método se nenhuma linha estiver selecionada
        }

        // Solicita confirmação ao usuário antes de excluir
        int resposta = JOptionPane.showConfirmDialog(frame, "Deseja realmente excluir o cliente selecionado?", "Confirmação", JOptionPane.YES_NO_OPTION);
        if (resposta != JOptionPane.YES_OPTION) { // Se o usuário não confirmar, sai do método
            return;
        }

        int id = (int) tabelaModel.getValueAt(linhaSelecionada, 0); // Obtém o ID do cliente da linha selecionada

        // Bloco try-with-resources para garantir que a conexão seja fechada automaticamente
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // SQL para exclusão de um cliente
            String sql = "DELETE FROM cliente WHERE id_cliente = ?";
            PreparedStatement stmt = conn.prepareStatement(sql); // Cria um PreparedStatement
            stmt.setInt(1, id); // Define o ID do cliente a ser excluído
            stmt.executeUpdate(); // Executa a instrução SQL de exclusão

            JOptionPane.showMessageDialog(frame, "Cliente excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarClientes(); // Recarrega a tabela para refletir a exclusão
            limparCampos();     // Limpa os campos de texto

        } catch (SQLException ex) {
            // Captura e exibe qualquer erro de SQL que ocorra
            JOptionPane.showMessageDialog(frame, "Erro ao excluir cliente:\n" + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método utilitário para limpar os campos de texto da interface.
     * Também reseta o idSelecionado e reabilita o botão Cadastrar.
     */
    private static void limparCampos() {
        nomeField.setText("");      // Limpa o campo Nome
        telefoneField.setText("");  // Limpa o campo Telefone
        emailField.setText("");     // Limpa o campo Email
        idSelecionado = -1;         // Reseta o ID selecionado para -1 (nenhum cliente em edição)
        cadastrarButton.setEnabled(true); // Garante que o botão Cadastrar esteja habilitado
    }
}
