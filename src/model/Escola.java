package model;

import java.io.Serializable;

public class Escola implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private EstadoEnum estado;

    public Escola(int id, String nome, EstadoEnum estado) {
        this.id = id;
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
