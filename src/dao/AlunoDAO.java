package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.Aluno;

public class AlunoDAO {
    private static final String CAMINHO = "src/dados/alunos.ser";

    public static void salvar(List<Aluno> alunos) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO))) {
            oos.writeObject(alunos);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Aluno> carregar() throws IOException, ClassNotFoundException {
        File arquivo = new File(CAMINHO);
        if (!arquivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Aluno>) ois.readObject();
        }
    }
}
