package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import dao.AlunoDAO;
import factory.AlunoFactory;
import model.Aluno;
import model.Materia;
import util.Salvavel;

public class AlunoController implements Salvavel {
    private List<Aluno> alunos;

    public AlunoController(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    public static List<Aluno> carregar() throws IOException, ClassNotFoundException {
        return AlunoDAO.carregar();
    }

    @Override
    public void salvar() throws IOException {
        if (alunos == null || alunos.isEmpty()) {
            System.out.println("⚠️ Nenhum aluno para salvar.");
            return;
        }
        if (alunos.stream().anyMatch(a -> a == null)) {
            throw new IllegalStateException("A lista de alunos contém elementos nulos!");
        }
        AlunoDAO.salvar(alunos);
        System.out.println("✅ Alunos salvos com sucesso.");
    }

    public void cadastrarAluno(String nome, String anoNascimento, List<Materia> materias) {
        int id = gerarId();
        alunos.add(AlunoFactory.criar(id, nome, anoNascimento, materias));
    }

    public int gerarId() {
        return alunos.stream()
                .mapToInt(Aluno::getIdAluno)
                .max()
                .orElse(0) + 1;
    }

    public List<Aluno> listarAlunos() {
        return alunos;
    }

    public Optional<Aluno> buscarAlunoPorId(int id) {
        return alunos.stream()
                .filter(a -> a.getIdAluno() == id)
                .findFirst();
    }

    public void editarAluno(int id, String novoNome) {
        buscarAlunoPorId(id).ifPresent(a -> a.setNome(novoNome));
    }

    public void deletarAluno(int id) {
        alunos.removeIf(a -> a.getIdAluno() == id);
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }
}