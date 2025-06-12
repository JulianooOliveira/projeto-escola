package factory;

import model.Escola;
import model.EstadoEnum;

public class EscolaFactory {
    public static Escola criar(int id, String nome, EstadoEnum estado) {
        if (nome == null || nome.isBlank())
            throw new IllegalArgumentException("Nome da escola não pode ser vazio.");
        if (estado == null)
            throw new IllegalArgumentException("Estado não pode ser nulo.");
        return new Escola(id, nome.trim(), estado);
    }

}