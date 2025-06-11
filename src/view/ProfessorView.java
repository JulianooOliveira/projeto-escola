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
                        System.out.println("❌ Nenhuma matéria cadastrada no sistema. Cadastre uma matéria primeiro.");
                        break;
                    }

                    System.out.print("Nome: ");
                    String nome = in.nextLine().trim();
                    if (nome.isBlank()) {
                        System.out.println("⚠️ Nome não pode ser vazio.");
                        break;
                    }

                    System.out.print("Ano de nascimento: ");
                    String ano = in.nextLine().trim();
                    if (ano.isBlank()) {
                        System.out.println("⚠️ Ano de nascimento não pode ser vazio.");
                        break;
                    }

                    System.out.println("📘 Matérias disponíveis:");
                    listaMaterias.forEach(
                            m -> System.out.println("ID: " + m.getIdMateria() + " | Nome: " + m.getNomeMateria()));

                    System.out.print("IDs das matérias (separados por vírgula): ");
                    String linhaIds = in.nextLine().trim();
                    if (linhaIds.isBlank()) {
                        System.out.println("⚠️ Você deve informar pelo menos um ID de matéria.");
                        break;
                    }

                    String[] ids = linhaIds.split(",");
                    List<Materia> materiasVinculadas = new ArrayList<>();
                    for (String idStr : ids) {
                        idStr = idStr.trim();
                        if (idStr.isBlank())
                            continue;
                        try {
                            int id = Integer.parseInt(idStr);
                            matController.buscarMateriaPorId(id).ifPresent(materiasVinculadas::add);
                        } catch (NumberFormatException ignored) {
                            System.out.println("⚠️ ID inválido ignorado: " + idStr);
                        }
                    }

                    if (!materiasVinculadas.isEmpty()) {
                        profController.cadastrarProfessor(nome, ano, materiasVinculadas);
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
                        System.out.println("❌ Nenhum professor cadastrado.");
                        break;
                    }

                    lista.forEach(System.out::println);

                    System.out.print("ID do professor para Editar: ");
                    try {
                        int id = Integer.parseInt(in.nextLine().trim());

                        var profOpt = profController.buscarProfessorPorId(id);
                        if (profOpt.isEmpty()) {
                            System.out.println("❌ Professor com ID " + id + " não encontrado.");
                            break;
                        }

                        Professor prof = profOpt.get();

                        System.out.print("Novo nome (pressione ENTER para manter o atual): ");
                        String novoNome = in.nextLine().trim();
                        if (!novoNome.isBlank()) {
                            profController.editarProfessor(id, novoNome);
                        }

                        System.out.println("📎 Matérias atuais:");
                        prof.getMaterias().forEach(m -> System.out.println("- " + m.getNomeMateria()));

                        System.out.print("Deseja alterar as matérias vinculadas? (s/n): ");
                        String alterar = in.nextLine().trim().toLowerCase();

                        if (alterar.equals("s")) {
                            List<Materia> listaMaterias = matController.listarMaterias();
                            if (listaMaterias.isEmpty()) {
                                System.out.println("⚠️ Nenhuma matéria disponível para seleção.");
                                break;
                            }

                            System.out.println("📘 Matérias disponíveis:");
                            listaMaterias.forEach(m -> System.out
                                    .println("ID: " + m.getIdMateria() + " | Nome: " + m.getNomeMateria()));

                            System.out.print("IDs das matérias (separados por vírgula): ");
                            String linhaIds = in.nextLine().trim();
                            if (linhaIds.isBlank()) {
                                System.out.println("⚠️ Nenhum ID informado. As matérias não foram alteradas.");
                                break;
                            }

                            String[] ids = linhaIds.split(",");
                            List<Materia> novasMaterias = new ArrayList<>();
                            for (String idStr : ids) {
                                try {
                                    int idMat = Integer.parseInt(idStr.trim());
                                    matController.buscarMateriaPorId(idMat).ifPresent(novasMaterias::add);
                                } catch (NumberFormatException e) {
                                    System.out.println("⚠️ ID inválido ignorado: " + idStr);
                                }
                            }

                            if (!novasMaterias.isEmpty()) {
                                prof.setMaterias(novasMaterias);
                                System.out.println("📚 Matérias atualizadas com sucesso!");
                            } else {
                                System.out.println(
                                        "⚠️ Nenhuma matéria válida foi selecionada. As matérias não foram alteradas.");
                            }
                        }

                        System.out.println("✏️ Professor atualizado com sucesso!");
                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID inválido.");
                    }
                }

                case "4" -> {
                    List<Professor> lista = profController.listarProfessores();
                    if (lista.isEmpty()) {
                        System.out.println("❌ Nenhum professor cadastrado.");
                        break;
                    }

                    lista.forEach(System.out::println);

                    System.out.print("ID do professor para Deletar: ");
                    String inputId = in.nextLine().trim();

                    if (inputId.isBlank()) {
                        System.out.println("⚠️ O ID não pode ser vazio.");
                        break;
                    }

                    try {
                        int id = Integer.parseInt(inputId);
                        if (profController.buscarProfessorPorId(id).isEmpty()) {
                            System.out.println("❌ Professor com ID " + id + " não encontrado.");
                            break;
                        }

                        profController.deletarProfessor(id);
                        System.out.println("🗑️ Professor removido com sucesso!");
                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID inválido.");
                    }
                }

                case "0" -> loop = false;

                default -> System.out.println("❌ Opção inválida! Digite um número de 0 a 4.");
            }

            try {
                profController.salvar();
            } catch (IOException e) {
                System.err.println("Erro ao salvar professores: " + e.getMessage());
            }
        }
    }
}
