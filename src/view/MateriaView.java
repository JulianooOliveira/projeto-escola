package view;

import controller.EscolaController;
import controller.MateriaController;
import model.Escola;
import model.Materia;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public abstract class MateriaView {
    public static void menu(Scanner in) {
        List<Materia> materias;
        List<Escola> escolas;

        try {
            materias = MateriaController.carregar();
            escolas = EscolaController.carregar();
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            return;
        }

        MateriaController materiaController = new MateriaController(materias);
        EscolaController escolaController = new EscolaController(escolas);

        boolean loop = true;
        while (loop) {
            System.out.println("\n--- MATÉRIAS ---");
            System.out.println("1 - Cadastrar Matéria");
            System.out.println("2 - Listar Matérias");
            System.out.println("3 - Editar Matéria");
            System.out.println("4 - Deletar Matéria");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            String opcao = in.nextLine();

            switch (opcao) {
                case "1" -> {
                    System.out.print("Nome da matéria: ");
                    String nomeMateria = in.nextLine();

                    System.out.print("Nome da escola vinculada: ");
                    String nomeEscola = in.nextLine();

                    Escola escola = escolaController.buscarEscolaPorNome(nomeEscola).orElse(null);
                    if (escola != null) {
                        materiaController.cadastrarMateria(nomeMateria, escola);
                    } else {
                        System.out.println("Escola não encontrada. Cadastre a escola primeiro.");
                    }
                }
                case "2" -> materiaController.listarMaterias().forEach(System.out::println);
                case "3" -> {
                    System.out.print("ID da matéria: ");
                    int id = Integer.parseInt(in.nextLine());
                    System.out.print("Novo nome: ");
                    String novoNome = in.nextLine();
                    materiaController.editarMateria(id, novoNome);
                }
                case "4" -> {
                    System.out.print("ID da matéria: ");
                    int id = Integer.parseInt(in.nextLine());
                    materiaController.deletarMateria(id);
                }
                case "0" -> loop = false;
                default -> System.out.println("Opção inválida!");
            }

            try {
                materiaController.salvar();
            } catch (IOException e) {
                System.err.println("Erro ao salvar matérias: " + e.getMessage());
            }
        }
    }
}
