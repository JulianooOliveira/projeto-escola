package view;

import controller.AlunoController;
import controller.EscolaController;
import controller.MateriaController;
import controller.ProfessorController;
import model.Aluno;
import model.Escola;
import model.Materia;
import model.Professor;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public abstract class MateriaView {
    public static void menu(Scanner in) {
        System.out.println("\n>>> Entrou no menu de MATÉRIAS <<<");
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
            System.out.println("\n📘 --- GERENCIAMENTO DE MATÉRIAS ---");
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

                    System.out.println("🔗 Escolas disponíveis:");
                    escolaController.listarEscolas()
                            .forEach(e -> System.out.println("ID: " + e.getId() + " | Nome: " + e.getNome()));

                    System.out.print("Digite o ID da escola vinculada: ");
                    int idEscola = Integer.parseInt(in.nextLine());

                    Optional<Escola> escolaOpt = escolaController.buscarEscolaPorId(idEscola);
                    if (escolaOpt.isPresent()) {
                        materiaController.cadastrarMateria(nomeMateria, escolaOpt.get());
                        System.out.println("✅ Matéria cadastrada com sucesso!");
                    } else {
                        System.out.println("❌ Escola com ID " + idEscola + " não encontrada.");
                    }
                }

                case "2" -> {
                    if (materias.isEmpty()) {
                        System.out.println("📭 Nenhuma matéria cadastrada.");
                    } else {
                        materiaController.listarMaterias().forEach(System.out::println);
                    }
                }

                case "3" -> {
                    if (materias.isEmpty()) {
                        System.out.println("⚠️ Nenhuma matéria cadastrada. Não é possível editar.");
                        break;
                    }

                    System.out.print("ID da matéria: ");
                    int id = Integer.parseInt(in.nextLine());
                    System.out.print("Novo nome: ");
                    String novoNome = in.nextLine();
                    materiaController.editarMateria(id, novoNome);
                    System.out.println("✏️ Matéria editada com sucesso.");
                }

                case "4" -> {
                    if (materias.isEmpty()) {
                        System.out.println("⚠️ Nenhuma matéria cadastrada. Não é possível deletar.");
                        break;
                    }

                    System.out.print("ID da matéria: ");
                    int id = Integer.parseInt(in.nextLine());

                    try {
                        List<Professor> professores = ProfessorController.carregar();
                        List<Aluno> alunos = AlunoController.carregar();

                        boolean deletada = materiaController.deletarMateria(id, professores, alunos);
                        if (deletada) {
                            System.out.println("🗑️ Matéria deletada com sucesso.");
                        } else {
                            System.out.println(
                                    "❌ Não é possível deletar. A matéria está vinculada a algum professor ou aluno.");
                        }

                    } catch (Exception e) {
                        System.err.println("Erro ao carregar professores/alunos: " + e.getMessage());
                    }
                }

                case "0" -> loop = false;

                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }

            try {
                materiaController.salvar();
            } catch (IOException e) {
                System.err.println("Erro ao salvar matérias: " + e.getMessage());
            }
        }
    }
}
