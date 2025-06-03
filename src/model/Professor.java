package model;

import java.util.List;

public class Professor extends Pessoa {
    private int idProfessor;
    private List<Materia> materias; // vínculo obrigatório

    public Professor(int idProfessor, String nome, String anoNascimentoString, int idade, List<Materia> materias) {
        super(nome, anoNascimentoString, idade);
        this.idProfessor = idProfessor;
        this.materias = materias;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    @Override
    public String toString() {
        return "Professor [idProfessor=" + idProfessor + ", nome=" + getNome() + ", matérias=" + materias + "]";
    }
}
