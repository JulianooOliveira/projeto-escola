package model;

public class Professor extends Pessoa {
    private int idProfessor;

    public Professor(int idProfessor, String nome, String anoNascimentoString, int idade) {
        super(nome, anoNascimentoString, idade);
        this.idProfessor = idProfessor;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    @Override
    public String toString() {
        return "Professor [idProfessor=" + idProfessor + "]";
    }

}
