/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examen2_1ad;

import java.io.Serializable;

/**
 *
 * @author oracle
 */
public class Platos implements Serializable {
    String codigo;
    String nome;
    int graxaTotal;

    public Platos() {
    }

    public Platos(String codigo, String nome, int graxaTotal) {
        this.codigo = codigo;
        this.nome = nome;
        this.graxaTotal = graxaTotal;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getGraxaTotal() {
        return graxaTotal;
    }

    public void setGraxaTotal(int graxaTotal) {
        this.graxaTotal = graxaTotal;
    }

    @Override
    public String toString() {
        return "Platos{" + "codigo=" + codigo + ", nome=" + nome + ", graxaTotal=" + graxaTotal + '}';
    }
    
    
    
}
