package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import dao.MateriaDAO;
import model.Escola;
import model.Materia;

public class MateriaController {
    private List<Materia> materias;

    public MateriaController(List<Materia> materias) {
        this.materias = materias;
    }

    public static List<Materia> carregar() throws IOException, ClassNotFoundException {
        return MateriaDAO.carregar();
    }

    public void salvar() throws IOException {
        MateriaDAO.salvar(materias);
    }

    public void cadastrarMateria(String nomeMateria, Escola escola) {
        int id = gerarId();
        materias.add(new Materia(id, nomeMateria, escola));
    }

    private int gerarId() {
        return materias.stream()
                .mapToInt(Materia::getIdMateria)
                .max()
                .orElse(0) + 1;
    }

    public List<Materia> listarMaterias() {
        return materias;
    }

    public Optional<Materia> buscarMateriaPorId(int id) {
        return materias.stream()
                .filter(m -> m.getIdMateria() == id)
                .findFirst();
    }

    public void editarMateria(int id, String novoNome) {
        buscarMateriaPorId(id).ifPresent(m -> m.setNomeMateria(novoNome));
    }

    public void deletarMateria(int id) {
        materias.removeIf(m -> m.getIdMateria() == id);
    }

    public List<Materia> getMaterias() {
        return materias;
    }
}
