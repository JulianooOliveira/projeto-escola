package model;

import java.io.Serializable;

public class Escola implements Serializable {
    private static final long serialVersionUID = 1L;
    private static int contador = 1;

    private int id;
    private String nome;
    private EstadoEnum estado;

    public Escola(String nome, EstadoEnum estado) {
        this.id = contador++;
        this.nome = nome;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public EstadoEnum getEstado() {
        return estado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEstado(EstadoEnum estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Nome: " + nome + " | Estado: " + estado.name();
    }
}
