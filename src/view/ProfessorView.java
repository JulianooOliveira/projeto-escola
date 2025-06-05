package view;

import controller.MateriaController;
import controller.ProfessorController;
import model.Materia;
import model.Professor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ProfessorView {

    public static void menu(Scanner in) {
        System.out.println("\n>>> Entrou no menu de PROFESSORES <<<");
        List<Professor> professores;
        List<Materia> materias;

        try {
            professores = ProfessorController.carregar();
            materias = MateriaController.carregar();
        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        ProfessorController profController = new ProfessorController(professores);
        MateriaController matController = new MateriaController(materias);

        boolean loop = true;
        while (loop) {
            System.out.println("\n--- PROFESSORES ---");
            System.out.println("1 - Cadastrar Professor");
            System.out.println("2 - Listar Professores");
            System.out.println("3 - Editar Professor");
            System.out.println("4 - Deletar Professor");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            String opcao = in.nextLine();

            switch (opcao) {
                case "1" -> {
                    List<Materia> listaMaterias = matController.listarMaterias();
                    if (listaMaterias.isEmpty()) {
                        System.out.println(
                                "❌ Não há matérias cadastradas. Cadastre ao menos uma matéria antes de criar um professor.");
                        break;
                    }

                    System.out.print("Nome: ");
                    String nome = in.nextLine();
                    System.out.print("Ano de nascimento: ");
                    String ano = in.nextLine();
                    System.out.print("Idade: ");
                    int idade = Integer.parseInt(in.nextLine());

                    System.out.println("📘 Matérias disponíveis:");
                    listaMaterias.forEach(
                            m -> System.out.println("ID: " + m.getIdMateria() + " | Nome: " + m.getNomeMateria()));

                    System.out.print("IDs das matérias (separados por vírgula): ");
                    String[] ids = in.nextLine().split(",");

                    List<Materia> materiasVinculadas = new ArrayList<>();
                    for (String idStr : ids) {
                        try {
                            int id = Integer.parseInt(idStr.trim());
                            matController.buscarMateriaPorId(id).ifPresent(materiasVinculadas::add);
                        } catch (NumberFormatException ignored) {
                        }
                    }

                    if (!materiasVinculadas.isEmpty()) {
                        profController.cadastrarProfessor(nome, ano, idade, materiasVinculadas);
                        System.out.println("✅ Professor cadastrado com sucesso!");
                    } else {
                        System.out.println("❌ Nenhuma matéria válida foi selecionada.");
                    }
                }

                case "2" -> {
                    List<Professor> lista = profController.listarProfessores();
                    if (lista.isEmpty()) {
                        System.out.println("❌ Nenhum professor cadastrado.");
                    } else {
                        lista.forEach(System.out::println);
                    }
                }
                case "3" -> {
                    List<Professor> lista = profController.listarProfessores();
                    if (lista.isEmpty()) {
                        System.out.println("❌ Nenhum professor cadastrado. Não é possível editar.");
                        break;
                    }

                    System.out.print("ID do professor: ");
                    int id = Integer.parseInt(in.nextLine());

                    if (profController.buscarProfessorPorId(id).isEmpty()) {
                        System.out.println("❌ Professor com ID " + id + " não encontrado.");
                        break;
                    }

                    System.out.print("Novo nome: ");
                    String novoNome = in.nextLine();
                    profController.editarProfessor(id, novoNome);
                    System.out.println("✏️ Professor atualizado com sucesso!");
                }
                case "4" -> {
                    List<Professor> lista = profController.listarProfessores();
                    if (lista.isEmpty()) {
                        System.out.println("❌ Nenhum professor cadastrado. Não é possível deletar.");
                        break;
                    }

                    System.out.print("ID do professor: ");
                    int id = Integer.parseInt(in.nextLine());

                    if (profController.buscarProfessorPorId(id).isEmpty()) {
                        System.out.println("❌ Professor com ID " + id + " não encontrado.");
                        break;
                    }

                    profController.deletarProfessor(id);
                    System.out.println("🗑️ Professor removido com sucesso!");
                }

                case "0" -> loop = false;
                default -> System.out.println("Opção inválida!");
            }

            try {
                profController.salvar();
            } catch (IOException e) {
                System.err.println("Erro ao salvar professores: " + e.getMessage());
            }
        }
    }
}
