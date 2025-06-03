package view;

import controller.AlunoController;
import controller.MateriaController;
import model.Aluno;
import model.Materia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class AlunoView {
    public static void menu(Scanner in) {
        List<Aluno> alunos;
        List<Materia> materias;

        try {
            alunos = AlunoController.carregar();
            materias = MateriaController.carregar();
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            return;
        }

        AlunoController alunoController = new AlunoController(alunos);
        MateriaController materiaController = new MateriaController(materias);

        boolean loop = true;
        while (loop) {
            System.out.println("\n--- ALUNOS ---");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Listar Alunos");
            System.out.println("3 - Editar Aluno");
            System.out.println("4 - Deletar Aluno");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            String opcao = in.nextLine();

            switch (opcao) {
                case "1" -> {
                    System.out.print("Nome: ");
                    String nome = in.nextLine();
                    System.out.print("Ano de nascimento: ");
                    String ano = in.nextLine();
                    System.out.print("Idade: ");
                    int idade = Integer.parseInt(in.nextLine());

                    System.out.print("IDs das matérias (separados por vírgula): ");
                    String[] ids = in.nextLine().split(",");

                    List<Materia> materiasAluno = new ArrayList<>();
                    for (String idStr : ids) {
                        try {
                            int id = Integer.parseInt(idStr.trim());
                            materiaController.buscarMateriaPorId(id).ifPresent(materiasAluno::add);
                        } catch (NumberFormatException ignored) {}
                    }

                    if (!materiasAluno.isEmpty()) {
                        alunoController.cadastrarAluno(nome, ano, idade, materiasAluno);
                    } else {
                        System.out.println("Nenhuma matéria válida foi selecionada.");
                    }
                }
                case "2" -> alunoController.listarAlunos().forEach(System.out::println);
                case "3" -> {
                    System.out.print("ID do aluno: ");
                    int id = Integer.parseInt(in.nextLine());
                    System.out.print("Novo nome: ");
                    String novoNome = in.nextLine();
                    alunoController.editarAluno(id, novoNome);
                }
                case "4" -> {
                    System.out.print("ID do aluno: ");
                    int id = Integer.parseInt(in.nextLine());
                    alunoController.deletarAluno(id);
                }
                case "0" -> loop = false;
                default -> System.out.println("Opção inválida!");
            }

            try {
                alunoController.salvar();
            } catch (IOException e) {
                System.err.println("Erro ao salvar alunos: " + e.getMessage());
            }
        }
    }
}
