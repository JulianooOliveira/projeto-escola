package model;

import java.util.List;

public class Professor extends Pessoa {
    private static final long serialVersionUID = 1L;

    private int idProfessor;
    private List<Materia> materias;

    public Professor(int idProfessor, String nome, String anoNascimentoString, List<Materia> materias) {
        super(nome, anoNascimentoString);
        this.idProfessor = idProfessor;
        this.materias = materias;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    private void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    @Override
    public String getIdentificacao() {
        return "Professor ID: " + idProfessor;
    }
}