package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import dao.ProfessorDAO;
import model.Materia;
import model.Professor;

public class ProfessorController {
    private List<Professor> professores;

    public ProfessorController(List<Professor> professores) {
        this.professores = professores;
    }

    public static List<Professor> carregar() throws IOException, ClassNotFoundException {
        return ProfessorDAO.carregar();
    }

    public void salvar() throws IOException {
        ProfessorDAO.salvar(professores);
    }

    public void cadastrarProfessor(String nome, String anoNascimento, int idade, List<Materia> materias) {
        int id = gerarId();
        professores.add(new Professor(id, nome, anoNascimento, idade, materias));
    }

    private int gerarId() {
        return professores.stream()
                .mapToInt(Professor::getIdProfessor)
                .max()
                .orElse(0) + 1;
    }

    public List<Professor> listarProfessores() {
        return professores;
    }

    public Optional<Professor> buscarProfessorPorId(int id) {
        return professores.stream()
                .filter(p -> p.getIdProfessor() == id)
                .findFirst();
    }

    public void editarProfessor(int id, String novoNome) {
        buscarProfessorPorId(id).ifPresent(p -> p.setNome(novoNome));
    }

    public void deletarProfessor(int id) {
        professores.removeIf(p -> p.getIdProfessor() == id);
    }

    public List<Professor> getProfessores() {
        return professores;
    }
}
