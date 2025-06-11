package factory;

import model.Professor;
import model.Materia;
import java.util.List;

public class ProfessorFactory {
    public static Professor criar(int id, String nome, String anoNascimento, List<Materia> materias) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome do professor não pode ser vazio.");
        if (anoNascimento == null || anoNascimento.isBlank())
            throw new IllegalArgumentException("Ano de nascimento inválido.");
        if (materias == null || materias.isEmpty())
            throw new IllegalArgumentException("Professor deve lecionar pelo menos uma matéria.");
        return new Professor(id, nome.trim(), anoNascimento.trim(), materias);
    }
}