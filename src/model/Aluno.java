package model;

import java.util.List;

public class Aluno extends Pessoa {
    private static final long serialVersionUID = 1L;

    private int idAluno;
    private List<Materia> materias;

    public Aluno(int idAluno, String nome, String anoNascimentoString, List<Materia> materias) {
        super(nome, anoNascimentoString);
        this.idAluno = idAluno;
        this.materias = materias;
    }

    public int getIdAluno() {
        return idAluno;
    }

    private void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    @Override
    public String toString() {
        return "Aluno [idAluno=" + idAluno + ", nome=" + getNome() + ", " + materias.get(idAluno) + "]";
    }
}
