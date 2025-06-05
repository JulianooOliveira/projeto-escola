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
                    if (materias.isEmpty()) {
                        System.out.println("⚠️ Nenhuma matéria cadastrada no sistema. Cadastre uma matéria primeiro.");
                        break;
                    }

                    System.out.print("Nome: ");
                    String nome = in.nextLine();
                    System.out.print("Ano de nascimento: ");
                    String ano = in.nextLine();
                    System.out.print("Idade: ");
                    int idade = Integer.parseInt(in.nextLine());

                    System.out.println("📘 Matérias disponíveis:");
                    materias.forEach(
                            m -> System.out.println("ID: " + m.getIdMateria() + " | Nome: " + m.getNomeMateria()));

                    System.out.print("IDs das matérias (separados por vírgula): ");
                    String[] ids = in.nextLine().split(",");

                    List<Materia> materiasAluno = new ArrayList<>();
                    for (String idStr : ids) {
                        try {
                            int id = Integer.parseInt(idStr.trim());
                            materiaController.buscarMateriaPorId(id).ifPresent(materiasAluno::add);
                        } catch (NumberFormatException ignored) {
                        }
                    }

                    if (!materiasAluno.isEmpty()) {
                        alunoController.cadastrarAluno(nome, ano, idade, materiasAluno);
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
                        lista.forEach(System.out::println);
                    }
                }

                case "3" -> {
                    if (materias.isEmpty()) {
                        System.out.println("⚠️ Nenhum aluno cadastrado. Não é possível editar.");
                        break;
                    }

                    System.out.print("ID do aluno: ");
                    int id = Integer.parseInt(in.nextLine());

                    Optional<Aluno> alunoOpt = alunoController.buscarAlunoPorId(id);
                    if (alunoOpt.isPresent()) {
                        System.out.print("Novo nome: ");
                        String novoNome = in.nextLine();
                        alunoController.editarAluno(id, novoNome);
                        System.out.println("✏️ Aluno atualizado com sucesso.");
                    } else {
                        System.out.println("❌ Aluno com ID " + id + " não encontrado.");
                    }
                }

                case "4" -> {
                    if (materias.isEmpty()) {
                        System.out.println("⚠️ Nenhum aluno cadastrado. Não é possível deletar.");
                        break;
                    }

                    System.out.print("ID do aluno: ");
                    int id = Integer.parseInt(in.nextLine());

                    if (alunoController.buscarAlunoPorId(id).isPresent()) {
                        alunoController.deletarAluno(id);
                        System.out.println("🗑️ Aluno removido com sucesso.");
                    } else {
                        System.out.println("❌ Aluno com ID " + id + " não encontrado.");
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
