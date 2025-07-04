package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import dao.EscolaDAO;
import factory.EscolaFactory;
import model.Escola;
import model.EstadoEnum;
import model.Materia;
import util.Salvavel;

public class EscolaController implements Salvavel {
    private List<Escola> escolas;

    public EscolaController(List<Escola> escolas) {
        this.escolas = escolas;
    }

    public static List<Escola> carregar() throws IOException, ClassNotFoundException {
        return EscolaDAO.carregar();
    }

    @Override
    public void salvar() throws IOException {
        if (escolas == null || escolas.isEmpty()) {
            System.out.println("⚠️ Nenhuma escola para salvar.");
            return;
        }
        if (escolas.stream().anyMatch(e -> e == null)) {
            throw new IllegalStateException("A lista de escolas contém elementos nulos!");
        }
        EscolaDAO.salvar(escolas);
    }

    public int gerarId() {
        return escolas.stream()
                .mapToInt(Escola::getId)
                .max()
                .orElse(0) + 1;
    }

    public Escola cadastrarEscola(String nome, EstadoEnum estado) {
        int id = gerarId();
        Escola escola = EscolaFactory.criar(id, nome, estado);
        escolas.add(escola);
        return escola;
    }

    public List<Escola> listarEscolas() {
        return escolas;
    }

    public Optional<Escola> buscarEscolaPorId(int id) {
        return escolas.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
    }

    public Optional<Escola> buscarEscolaPorNome(String nome) {
        return escolas.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public void editarEscola(int id, String novoNome) {
        buscarEscolaPorId(id).ifPresent(e -> e.setNome(novoNome));
    }

    public boolean deletarEscola(int id, List<Materia> materias) {
        Optional<Escola> escolaOpt = buscarEscolaPorId(id);
        if (escolaOpt.isEmpty())
            return false;

        boolean emUso = materias.stream()
                .anyMatch(m -> m.getEscola().getId() == id);

        if (emUso) {
            System.out.println("❌ Não é possível excluir a escola. Existem matérias vinculadas a ela.");
            return false;
        }

        return escolas.removeIf(e -> e.getId() == id);
    }

    public List<Escola> getEscolas() {
        return escolas;
    }
}
