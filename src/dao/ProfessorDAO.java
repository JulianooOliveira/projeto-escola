package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.Professor;

public class ProfessorDAO {
    private static final String CAMINHO = "src/dados/professores.ser";

    public static void salvar(List<Professor> professores) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO))) {
            oos.writeObject(professores);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Professor> carregar() throws IOException, ClassNotFoundException {
        File arquivo = new File(CAMINHO);
        if (!arquivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Professor>) ois.readObject();
        }
    }
}
