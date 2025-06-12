package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import dao.ProfessorDAO;
import factory.ProfessorFactory;
import model.Materia;
import model.Professor;
import util.Salvavel;

public class ProfessorController implements Salvavel {
    private List<Professor> professores;

    public ProfessorController(List<Professor> professores) {
        this.professores = professores;
    }

    public static List<Professor> carregar() throws IOException, ClassNotFoundException {
        return ProfessorDAO.carregar();
    }

    @Override
    public void salvar() throws IOException {
        if (professores == null || professores.isEmpty()) {
            System.out.println("⚠️ Nenhum professor para salvar.");
            return;
        }
        if (professores.stream().anyMatch(p -> p == null)) {
            throw new IllegalStateException("A lista de professores contém elementos nulos!");
        }
        ProfessorDAO.salvar(professores);
        System.out.println("✅ Professores salvos com sucesso.");
    }

    public void cadastrarProfessor(String nome, String anoNascimento, List<Materia> materias) {
        int id = gerarId();
        professores.add(ProfessorFactory.criar(id, nome, anoNascimento, materias));
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

    // ✅ Polimorfismo de Sobrecarga
    public void editarProfessor(int id, String novoNome, List<Materia> novasMaterias) {
        buscarProfessorPorId(id).ifPresent(p -> {
            p.setNome(novoNome);
            p.setMaterias(novasMaterias);
        });
    }

    public void deletarProfessor(int id) {
        professores.removeIf(p -> p.getIdProfessor() == id);
    }

    public List<Professor> getProfessores() {
        return professores;
    }
}