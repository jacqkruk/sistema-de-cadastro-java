
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável pela conexão com o banco de dados.
 * 
 * Possui métodos para conectar e desconectar do banco de dados.
 * 
 * @author Jake mk
 */

public class Conexao {
    
    Connection conn; //criando um objeto do tipo connection
    private String url = "jdbc:mysql://localhost:3306/cenaflix"; //Nome da base de dados
    private String user = "root"; //nome do usuário do MySQL
    private String password = ""; //senha do MySQL 
    
    public Connection getConexao() {
        try {
            conn = DriverManager.getConnection(url, user, password);
            return conn;
            
        } catch (Exception ex) {
            System.out.println("Falha na conexão com o banco " + ex.getMessage());
            return null;
        }
    }
    
    public void desconectar() {
        try {
            conn.close();
        } catch (SQLException ex) {
            
        }
    }
    
}
