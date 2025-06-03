package dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import model.Escola;

public class EscolaDAO {
    private static final String CAMINHO = "src/dados/escolas.ser";

    public static void salvar(List<Escola> escolas) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CAMINHO))) {
            oos.writeObject(escolas);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Escola> carregar() throws IOException, ClassNotFoundException {
        File arquivo = new File(CAMINHO);
        if (!arquivo.exists()) return new ArrayList<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (List<Escola>) ois.readObject();
        }
    }
}
