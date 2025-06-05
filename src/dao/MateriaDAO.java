package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.Materia;

public class MateriaDAO {
    private static final String CAMINHO = "dados/materias.ser";

    public static void salvar(List<Materia> materias) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO))) {
            oos.writeObject(materias);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Materia> carregar() throws IOException, ClassNotFoundException {
        File arquivo = new File(CAMINHO);
        if (!arquivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Materia>) ois.readObject();
        }
    }
}
