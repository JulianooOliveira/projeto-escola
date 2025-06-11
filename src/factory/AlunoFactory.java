package factory;

import model.Aluno;
import model.Materia;
import java.util.List;

public class AlunoFactory {
    public static Aluno criar(int id, String nome, String anoNascimento, List<Materia> materias) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do aluno não pode ser vazio.");
        if (anoNascimento == null || anoNascimento.isBlank())
            throw new IllegalArgumentException("Ano de nascimento inválido.");
        if (materias == null || materias.isEmpty())
            throw new IllegalArgumentException("Aluno deve estar vinculado a pelo menos uma matéria.");
        return new Aluno(id, nome.trim(), anoNascimento.trim(), materias);
    }
}