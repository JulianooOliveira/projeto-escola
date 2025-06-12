package model;

import java.io.Serializable;

public abstract class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome, anoNascimentoString;

    public Pessoa(String nome, String anoNascimentoString) {
        this.nome = nome;
        this.anoNascimentoString = anoNascimentoString;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAnoNascimentoString() {
        return anoNascimentoString;
    }

    public void setAnoNascimentoString(String anoNascimentoString) {
        this.anoNascimentoString = anoNascimentoString;
    }

    public abstract String getIdentificacao();

    @Override
    public String toString() {
        return getIdentificacao() + " | Nome: " + nome + " | Nascimento: " + anoNascimentoString;
    }
}