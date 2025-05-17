
package model;

import java.util.Date;

/**
 * Classe modelo de filmes.
 * 
 * Contém os atributos nome, data de lançamento e categoria.
 * 
 * @author Jake mk
 */
public class Filme {
    // ATRIBUTOS
    private int id;
    private String nome;
    private Date datalancamento;
    private String categoria;
    
    // CONSTRUTOR 
    public Filme() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDatalancamento() {
        return datalancamento;
    }

    public void setDatalancamento(Date datalancamento) {
        this.datalancamento = datalancamento;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
}
