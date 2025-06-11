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
            String opcao = in.nextLine().trim();

            switch (opcao) {
                case "1" -> {
                    if (escolaController.listarEscolas().isEmpty()) {
                        System.out.println("⚠️ Nenhuma escola cadastrada. Cadastre uma escola antes.");
                        break;
                    }

                    System.out.print("Nome da matéria: ");
                    String nomeMateria = in.nextLine().trim();
                    if (nomeMateria.isBlank()) {
                        System.out.println("⚠️ O nome da matéria não pode ser vazio.");
                        break;
                    }

                    System.out.println("🔗 Escolas disponíveis:");
                    escolaController.listarEscolas().forEach(
                            e -> System.out.println("ID: " + e.getId() + " | Nome: " + e.getNome()));

                    System.out.print("Digite o ID da escola vinculada: ");
                    String idEscolaStr = in.nextLine().trim();
                    if (idEscolaStr.isBlank()) {
                        System.out.println("⚠️ ID da escola não pode ser vazio.");
                        break;
                    }

                    try {
                        int idEscola = Integer.parseInt(idEscolaStr);
                        Optional<Escola> escolaOpt = escolaController.buscarEscolaPorId(idEscola);

                        if (escolaOpt.isPresent()) {
                            materiaController.cadastrarMateria(nomeMateria, escolaOpt.get());
                            System.out.println("✅ Matéria cadastrada com sucesso!");
                        } else {
                            System.out.println("❌ Escola com ID " + idEscola + " não encontrada.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID inválido.");
                    }
                }

                case "2" -> {
                    List<Materia> lista = materiaController.listarMaterias();
                    if (lista.isEmpty()) {
                        System.out.println("📭 Nenhuma matéria cadastrada.");
                    } else {
                        System.out.println("📚 Lista de Matérias:");
                        lista.forEach(System.out::println);
                    }
                }

                case "3" -> {
                    List<Materia> lista = materiaController.listarMaterias();
                    if (lista.isEmpty()) {
                        System.out.println("⚠️ Nenhuma matéria cadastrada. Não é possível editar.");
                        break;
                    }

                    lista.forEach(System.out::println);

                    System.out.print("ID da matéria: ");
                    String idStr = in.nextLine().trim();
                    if (idStr.isBlank()) {
                        System.out.println("⚠️ ID não pode ser vazio.");
                        break;
                    }

                    try {
                        int id = Integer.parseInt(idStr);
                        Optional<Materia> materiaOpt = materiaController.buscarMateriaPorId(id);

                        if (materiaOpt.isEmpty()) {
                            System.out.println("❌ Matéria com ID " + id + " não encontrada.");
                            break;
                        }

                        Materia materia = materiaOpt.get();

                        System.out.print("Novo nome (pressione ENTER para manter): ");
                        String novoNome = in.nextLine().trim();
                        if (!novoNome.isBlank()) {
                            materia.setNomeMateria(novoNome);
                        }

                        System.out.print("Deseja alterar a escola vinculada? (s/n): ");
                        String resposta = in.nextLine().trim().toLowerCase();
                        if (resposta.equals("s")) {
                            List<Escola> listaEscolas = escolaController.listarEscolas();
                            if (listaEscolas.isEmpty()) {
                                System.out.println("⚠️ Nenhuma escola cadastrada.");
                                break;
                            }

                            listaEscolas.forEach(
                                    e -> System.out.println("ID: " + e.getId() + " | Nome: " + e.getNome()));

                            System.out.print("ID da nova escola: ");
                            String idEscolaStr = in.nextLine().trim();
                            if (idEscolaStr.isBlank()) {
                                System.out.println("⚠️ ID da escola não pode ser vazio.");
                                break;
                            }

                            try {
                                int idEscola = Integer.parseInt(idEscolaStr);
                                Optional<Escola> escolaOpt = escolaController.buscarEscolaPorId(idEscola);
                                if (escolaOpt.isPresent()) {
                                    materia.setEscola(escolaOpt.get());
                                    System.out.println("🏫 Escola vinculada atualizada com sucesso.");
                                } else {
                                    System.out.println("❌ Escola com ID " + idEscola + " não encontrada.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("❌ ID inválido.");
                            }
                        }

                        System.out.println("✏️ Matéria atualizada com sucesso!");
                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID inválido.");
                    }
                }

                case "4" -> {
                    if (materiaController.listarMaterias().isEmpty()) {
                        System.out.println("⚠️ Nenhuma matéria cadastrada. Não é possível deletar.");
                        break;
                    }

                    materiaController.listarMaterias().forEach(System.out::println);

                    System.out.print("ID da matéria: ");
                    String idStr = in.nextLine().trim();
                    if (idStr.isBlank()) {
                        System.out.println("⚠️ ID não pode ser vazio.");
                        break;
                    }

                    try {
                        int id = Integer.parseInt(idStr);
                        List<Professor> professores = ProfessorController.carregar();
                        List<Aluno> alunos = AlunoController.carregar();

                        boolean deletada = materiaController.deletarMateria(id, professores, alunos);
                        if (deletada) {
                            System.out.println("🗑️ Matéria deletada com sucesso.");
                        } else {
                            System.out.println("❌ A matéria está vinculada a algum professor ou aluno.");
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID inválido.");
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
