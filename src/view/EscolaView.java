package view;

import controller.EscolaController;
import model.Escola;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public abstract class EscolaView {
    public static void menu(Scanner in) {
        List<Escola> escolas;
        try {
            escolas = EscolaController.carregar();
        } catch (Exception e) {
            System.err.println("Erro ao carregar escolas: " + e.getMessage());
            return;
        }

        EscolaController controller = new EscolaController(escolas);

        boolean loop = true;
        while (loop) {
            System.out.println("\n--- ESCOLAS ---");
            System.out.println("1 - Cadastrar Escola");
            System.out.println("2 - Listar Escolas");
            System.out.println("3 - Editar Escola");
            System.out.println("4 - Deletar Escola");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            String opcao = in.nextLine();

            switch (opcao) {
                case "1" -> {
                    System.out.print("Nome da escola: ");
                    controller.cadastrarEscola(in.nextLine());
                }
                case "2" -> controller.listarEscolas().forEach(System.out::println);
                case "3" -> {
                    System.out.print("Nome atual: ");
                    String antigo = in.nextLine();
                    System.out.print("Novo nome: ");
                    String novo = in.nextLine();
                    controller.editarEscola(antigo, novo);
                }
                case "4" -> {
                    System.out.print("Nome da escola a deletar: ");
                    controller.deletarEscola(in.nextLine());
                }
                case "0" -> loop = false;
                default -> System.out.println("Opção inválida!");
            }

            try {
                controller.salvar();
            } catch (IOException e) {
                System.err.println("Erro ao salvar escolas: " + e.getMessage());
            }
        }
    }
}
