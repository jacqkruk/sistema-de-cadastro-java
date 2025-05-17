
package data;

import model.Filme;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

    /**
    * Classe responsável pela inserção de filmes no banco de dados.
    * 
    * Contém métodos para validar formatos, verificar a existência de campos vazios e inserir registros na tabela de filmes. Utiliza JDBC para interagir com o banco de dados.
    * @author Jake mk
    */
public class CadastroFilmeDao {
    
    private Conexao conexao;
    private Connection conn;
    
    public CadastroFilmeDao() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    /**
     *  Método para inserir objeto filme no banco de dados
     * @param filme 
     */
    public void inserirFilme(Filme filme) {
        String sql = "INSERT INTO filmes(nome, datalancamento, categoria) VALUES (?,?,?)";
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1,filme.getNome());
            st.setString(2,sdf.format(filme.getDatalancamento()));
            st.setString(3,filme.getCategoria());
            st.execute();
           
        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar filme: " + ex.getMessage());
        } 
    }
    
    /**
     * Método para verificar se há campos vazios no formulário de cadastro de filmes
     * 
     * @param nome
     * @param data
     * @param categoria
     * @return 
     */
    public boolean camposVazios(String nome, String data, String categoria) {
       boolean vazio = false;
       if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO! Nome não pode ser vazio.");
            vazio = true;
        }
       if (categoria.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO! Categoria não pode ser vazio.");
            vazio = true;
        }
       if (data.isEmpty()) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO! Data de lançamento não pode ser vazio.");
            vazio = true;
        }
       
       return vazio;
    }
    
    /**
     * Método para verificar se há formatos inválidos inseridos pelo usuário no formulário de cadastro de filmes
     * @param nome
     * @param data
     * @param categoria
     * @return 
     */
    public boolean formatosInvalidos(String nome, String data, String categoria) {
        boolean invalido = false;
        
        boolean validaNome = nome.matches("^[A-Za-zÀ-ÿ\\s]+$");
        boolean validaCategoria = nome.matches("^[A-Za-zÀ-ÿ\\s]+$");
        boolean validaData = data.matches("^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$");
        
        if (!validaNome) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO! Nome deve conter somente letras.");
            invalido = true;
        }
        if (!validaCategoria) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO! Categoria deve conter somente letras.");
            invalido = true;
        }
        if (!validaData) {
            JOptionPane.showMessageDialog(null, "ATENÇÃO! Data de lançamento deve ser no formato DD/MM/AAAA");
            invalido = true;
        }
        
        return invalido;  
    }
    
}
