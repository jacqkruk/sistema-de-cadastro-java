/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Filme;

/**
 *Classe responsável pela manipulação de filmes no banco de dados.
 * 
 * COntém métodos para carregar, pesquisar, editar e excluir registros da tabela de filmes. Utiliza JDBC para interagir com o banco de dados.
 * @author Jake mk
 */
public class ListagemDao {
    private Conexao conexao;
    private Connection conn;
    
    /**
     * Construtor da classe.
     * 
     * Inicializa a conexão com o banco de dados, permitindo a execução de consultas e alterações.
     */
    public ListagemDao() {
        this.conexao = new Conexao();
        this.conn = this.conexao.getConexao();
    }
    
    /**
     * Carrega e exibe a lista de filmes em uma tabela.
     * 
     * Esse método executa uma consulta SQL para recuperar informações de filmes do banco de dados e popula a tabela fornecida com os resultados.
     * 
     * @param tblFilmes A tabela onde os filmes serão exibidos.
     */
    public void carregarFilmes (JTable tblFilmes) {
        String sql = "SELECT id, nome, datalancamento, categoria FROM filmes";
        
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            
            // modelo da tabela para manipular os dados
            DefaultTableModel tabelaFilmes = (DefaultTableModel) tblFilmes.getModel();
            tabelaFilmes.setRowCount(0);
            tblFilmes.setRowSorter(new TableRowSorter<>(tabelaFilmes));
            
            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDate("datalancamento"),
                    rs.getString("categoria")
                };
                tabelaFilmes.addRow(linha);
            } 
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     
    /**
     * Cria um objeto Filme selecionado da tabela.
     * 
     * Esse método executa uma consulta SQL para recuperar os dados de um filme e retorna o objeto Filme a partir de um id informado.
     * 
     * @param id O id do filme a ser recuperado.
     * @return Objeto filme buscado por id.
     */
    public Filme getFilme(int id) {
        String sql = "SELECT * FROM filmes WHERE id=?";
         try {
            PreparedStatement st = this.conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            rs.next();
            
            Filme filme = new Filme();
            rs.first();
            
            filme.setNome(rs.getString("nome"));
            filme.setDatalancamento(rs.getDate("datalancamento"));
            filme.setCategoria(rs.getString("categoria"));
            
            return filme;
            
        } catch (Exception e) {
            System.out.println("Erro ao buscar filme no método getFilme(): " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Atualiza informações do filme na tabela.
     * 
     * Esse método executa uma consulta SQL para alterar informações do filme selecionado na linha da tabela para isso abrindo uma nova janela do tipo CadastroFilmeTela
     * 
     * @param filme O filme a ser atualizado.
     * @param id O id para buscar o filme correspondente no banco de dados.
     */
    public void editar (Filme filme, int id) {
        String sql = "UPDATE filmes SET nome=?, datalancamento=?, categoria=? WHERE id=?";
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dataFormatada = sdf.format(filme.getDatalancamento());
            Date dataSql = Date.valueOf(dataFormatada);
            
            PreparedStatement st = this.conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            st.setString(1,filme.getNome());
            st.setString(2,String.valueOf(dataSql));
            st.setString(3,filme.getCategoria());
            st.setString(4,String.valueOf(id));
            st.execute();
        } catch (Exception e) {
            System.out.println("Erro ao editar filme: " + e.getMessage());
        }
    }
    
    /**
     * Pesuisa filme na tabela e exibe resultados na tabela.
     * Esse método executa uma consulta SQL para buscar filmes cujo nome corresponda parcialmente ao parâmetro fornecido e exibe os resultados em uma tabela.
     * 
     * @param tblFilmes A tabela onde os resultados da pesquisa serão exibidos.
     * @param nome O nome do filme a ser pesquisado. O método usa LIKE para buscar ocorrências semelhantes.
     */
    public void pesquisarFilme(JTable tblFilmes, String nome) {
        String sql = "SELECT id, nome, datalancamento, categoria FROM filmes WHERE nome LIKE ?";
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1, "%" + nome + "%");
            ResultSet rs = st.executeQuery();
            
            // modelo da tabela para manipular os dados
            DefaultTableModel tabelaFilmes = (DefaultTableModel) tblFilmes.getModel();
            tabelaFilmes.setRowCount(0);
            tblFilmes.setRowSorter(new TableRowSorter<>(tabelaFilmes));
            
            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDate("datalancamento"),
                    rs.getString("categoria")
                };
                tabelaFilmes.addRow(linha);
            } 
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Pesuisa filmes com filtro dinâmico de categoria e exibe os resultados.
     * 
     * Esse método executa uma consulta SQL para buscar filmes em que a categoria corresponde parcialmente ao parâmetro informado.
     * Faz um filtro dinâmico e exibe os resultados na tabela.
     * 
     * @param tblFilmes A tabela onde serão exibidos os resultados.
     * @param categoria O parêmetro para busca de filmes.
     */
    public void pesquisarCategoria(JTable tblFilmes, String categoria) {
        String sql = "SELECT id, nome, datalancamento, categoria FROM filmes WHERE categoria LIKE ?";
        
        try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setString(1, "%" + categoria + "%");
            ResultSet rs = st.executeQuery();
            
            // modelo da tabela para manipular os dados
            DefaultTableModel tabelaFilmes = (DefaultTableModel) tblFilmes.getModel();
            tabelaFilmes.setRowCount(0);
            tblFilmes.setRowSorter(new TableRowSorter<>(tabelaFilmes));
            
            while (rs.next()) {
                Object[] linha = {
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getDate("datalancamento"),
                    rs.getString("categoria")
                };
                tabelaFilmes.addRow(linha);
            } 
            rs.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        
    /**
     * Exclui filme do banco de dados.
     * Esse método exclui do banco de dados o filme a partir de um id informado.
     * 
     * @param id O id que corresponde ao filme que será excluído.
     */
    public void excluir (int id) {
        String sql = "DELETE FROM filmes WHERE id=?";
         try {
            PreparedStatement st = this.conn.prepareStatement(sql);
            st.setInt(1, id);
            st.execute();
            
        } catch (Exception e) {
            System.out.println("Erro ao excluir filme: " + e.getMessage());
        }  
    }
}
