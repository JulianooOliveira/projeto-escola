package controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import dao.EscolaDAO;
import model.Escola;

public class EscolaController {
    private List<Escola> escolas;

    public EscolaController(List<Escola> escolas) {
        this.escolas = escolas;
    }

    public static List<Escola> carregar() throws IOException, ClassNotFoundException {
        return EscolaDAO.carregar();
    }

    public void salvar() throws IOException {
        EscolaDAO.salvar(escolas);
    }

    public void cadastrarEscola(String nome) {
        escolas.add(new Escola(nome));
    }

    public List<Escola> listarEscolas() {
        return escolas;
    }

    public Optional<Escola> buscarEscolaPorNome(String nome) {
        return escolas.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public void editarEscola(String nomeAntigo, String novoNome) {
        buscarEscolaPorNome(nomeAntigo).ifPresent(e -> e.setNome(novoNome));
    }

    public void deletarEscola(String id) {
        escolas.removeIf(e -> e.getNome().equalsIgnoreCase(id));
    }

    public List<Escola> getEscolas() {
        return escolas;
    }
}
