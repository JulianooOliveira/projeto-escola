package model;

public abstract class Pessoa {
    private String nome, anoNascimentoString;
    private int idade;
    public Pessoa(String nome, String anoNascimentoString, int idade) {
        this.nome = nome;
        this.anoNascimentoString = anoNascimentoString;
        this.idade = idade;
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
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    @Override
    public String toString() {
        return "Pessoa [nome=" + nome + ", anoNascimentoString=" + anoNascimentoString + ", idade=" + idade + "]";
    }

    
}
