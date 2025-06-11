package view;

import controller.AlunoController;
import controller.MateriaController;
import model.Aluno;
import model.Materia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public abstract class AlunoView {
    public static void menu(Scanner in) {
        System.out.println("\n>>> Entrou no menu de ALUNOS <<<");

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
            System.out.println("\n--- MENU DE ALUNOS ---");
            System.out.println("1 - Cadastrar Aluno");
            System.out.println("2 - Listar Alunos");
            System.out.println("3 - Editar Aluno");
            System.out.println("4 - Deletar Aluno");
            System.out.println("0 - Voltar");
            System.out.print("Opção: ");
            String opcao = in.nextLine().trim();

            switch (opcao) {
                case "1" -> {
                    if (materias.isEmpty()) {
                        System.out.println("⚠️ Nenhuma matéria cadastrada no sistema. Cadastre uma matéria primeiro.");
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
                    materias.forEach(
                            m -> System.out.println("ID: " + m.getIdMateria() + " | Nome: " + m.getNomeMateria()));

                    System.out.print("IDs das matérias (separados por vírgula): ");
                    String linhaIds = in.nextLine().trim();
                    if (linhaIds.isBlank()) {
                        System.out.println("⚠️ Você deve informar ao menos um ID.");
                        break;
                    }

                    String[] ids = linhaIds.split(",");
                    List<Materia> materiasAluno = new ArrayList<>();
                    for (String idStr : ids) {
                        idStr = idStr.trim();
                        if (idStr.isBlank())
                            continue;

                        try {
                            int id = Integer.parseInt(idStr);
                            materiaController.buscarMateriaPorId(id).ifPresent(materiasAluno::add);
                        } catch (NumberFormatException ex) {
                            System.out.println("⚠️ ID inválido ignorado: " + idStr);
                        }
                    }

                    if (!materiasAluno.isEmpty()) {
                        alunoController.cadastrarAluno(nome, ano, materiasAluno);
                        System.out.println("✅ Aluno cadastrado com sucesso!");
                    } else {
                        System.out.println("❌ Nenhuma matéria válida foi selecionada. Aluno não cadastrado.");
                    }
                }

                case "2" -> {
                    List<Aluno> lista = alunoController.listarAlunos();
                    if (lista.isEmpty()) {
                        System.out.println("📭 Nenhum aluno cadastrado.");
                    } else {
                        System.out.println("\n📋 Lista de Alunos:");
                        lista.forEach(System.out::println);
                    }
                }

                case "3" -> {
                    if (alunos.isEmpty()) {
                        System.out.println("⚠️ Nenhum aluno cadastrado. Não é possível editar.");
                        break;
                    }

                    System.out.print("ID do aluno: ");
                    String idStr = in.nextLine().trim();
                    if (idStr.isBlank()) {
                        System.out.println("⚠️ ID não pode estar em branco.");
                        break;
                    }

                    try {
                        int id = Integer.parseInt(idStr);
                        Optional<Aluno> alunoOpt = alunoController.buscarAlunoPorId(id);
                        if (alunoOpt.isPresent()) {
                            System.out.print("Novo nome (pressione ENTER para manter o atual): ");
                            String novoNome = in.nextLine().trim();
                            if (!novoNome.isBlank()) {
                                alunoController.editarAluno(id, novoNome);
                                System.out.println("✏️ Aluno atualizado com sucesso.");
                            } else {
                                System.out.println("ℹ️ Nome mantido.");
                            }
                        } else {
                            System.out.println("❌ Aluno com ID " + id + " não encontrado.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID inválido.");
                    }
                }

                case "4" -> {
                    if (alunos.isEmpty()) {
                        System.out.println("⚠️ Nenhum aluno cadastrado. Não é possível deletar.");
                        break;
                    }

                    System.out.print("ID do aluno: ");
                    String idStr = in.nextLine().trim();
                    if (idStr.isBlank()) {
                        System.out.println("⚠️ ID não pode estar em branco.");
                        break;
                    }

                    try {
                        int id = Integer.parseInt(idStr);
                        if (alunoController.buscarAlunoPorId(id).isPresent()) {
                            alunoController.deletarAluno(id);
                            System.out.println("🗑️ Aluno removido com sucesso.");
                        } else {
                            System.out.println("❌ Aluno com ID " + id + " não encontrado.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID inválido.");
                    }
                }

                case "0" -> loop = false;

                default -> System.out.println("❌ Opção inválida!");
            }

            try {
                alunoController.salvar();
            } catch (IOException e) {
                System.err.println("Erro ao salvar alunos: " + e.getMessage());
            }
        }
    }
}
