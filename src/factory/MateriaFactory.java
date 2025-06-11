package factory;

import model.Materia;
import model.Escola;

public class MateriaFactory {
    public static Materia criar(int id, String nomeMateria, Escola escola) {
        if (nomeMateria == null || nomeMateria.isBlank())
            throw new IllegalArgumentException("Nome da matéria não pode ser vazio.");
        if (escola == null)
            throw new IllegalArgumentException("Escola vinculada não pode ser nula.");
        return new Materia(id, nomeMateria.trim(), escola);
    }
}
