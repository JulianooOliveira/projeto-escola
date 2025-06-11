package view;

import controller.EscolaController;
import controller.MateriaController;
import model.Escola;
import model.EstadoEnum;
import model.Materia;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public abstract class EscolaView {
    public static void menu(Scanner in) {
        System.out.println("\n>>> Entrou no menu de ESCOLAS <<<");

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
            System.out.println("\n🎓 --- GERENCIAMENTO DE ESCOLAS ---");
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
                    String nome = in.nextLine().trim();
                    if (nome.isBlank()) {
                        System.out.println("⚠️ Nome da escola não pode ser vazio.");
                        break;
                    }

                    System.out.println("Selecione o estado (PR ou SC):");
                    for (EstadoEnum estado : EstadoEnum.values()) {
                        System.out.println(estado.name() + " - " + estado.getNome());
                    }

                    System.out.print("Sigla do estado: ");
                    String sigla = in.nextLine().trim().toUpperCase();

                    if (sigla.isBlank()) {
                        System.out.println("⚠️ Sigla do estado não pode ser vazia.");
                        break;
                    }

                    try {
                        EstadoEnum estado = EstadoEnum.fromSigla(sigla);
                        Escola nova = controller.cadastrarEscola(nome, estado);
                        System.out.println("✅ Escola criada com sucesso!");
                        System.out.println("🆔 ID: " + nova.getId() +
                                " | Nome: " + nova.getNome() +
                                " | Estado: " + nova.getEstado().name());
                    } catch (IllegalArgumentException e) {
                        System.out.println("❌ Estado inválido. Use apenas PR ou SC.");
                    }
                }

                case "2" -> {
                    List<Escola> lista = controller.listarEscolas();
                    if (lista.isEmpty()) {
                        System.out.println("📭 Nenhuma escola cadastrada.");
                    } else {
                        System.out.println("📚 Lista de escolas:");
                        lista.forEach(e -> System.out.println("ID: " + e.getId() +
                                " | Nome: " + e.getNome() +
                                " | Estado: " + e.getEstado().name()));
                    }
                }

                case "3" -> {
                    List<Escola> lista = controller.listarEscolas();
                    if (lista.isEmpty()) {
                        System.out.println("📭 Nenhuma escola cadastrada.");
                        break;
                    }

                    System.out.println("📚 Lista de escolas:");
                    lista.forEach(e -> System.out.println("ID: " + e.getId() +
                            " | Nome: " + e.getNome() +
                            " | Estado: " + e.getEstado().name()));

                    System.out.print("ID da escola a editar: ");
                    String idStr = in.nextLine().trim();
                    if (idStr.isBlank()) {
                        System.out.println("⚠️ O ID não pode estar em branco.");
                        break;
                    }

                    try {
                        int id = Integer.parseInt(idStr);

                        Optional<Escola> escolaOpt = controller.buscarEscolaPorId(id);
                        if (escolaOpt.isPresent()) {
                            Escola escola = escolaOpt.get();

                            System.out.print("Novo nome da escola (pressione ENTER para manter o atual): ");
                            String novoNome = in.nextLine().trim();
                            if (!novoNome.isBlank()) {
                                escola.setNome(novoNome);
                            }

                            System.out.print("Deseja alterar o estado da escola? (s/n): ");
                            String resposta = in.nextLine().trim().toLowerCase();
                            if (resposta.equals("s")) {
                                System.out.println("Selecione o novo estado (PR ou SC):");
                                for (EstadoEnum estado : EstadoEnum.values()) {
                                    System.out.println(estado.name() + " - " + estado.getNome());
                                }

                                System.out.print("Sigla do estado: ");
                                String novaSigla = in.nextLine().trim().toUpperCase();
                                if (novaSigla.isBlank()) {
                                    System.out.println("⚠️ Sigla não pode ser vazia.");
                                    break;
                                }

                                try {
                                    EstadoEnum novoEstado = EstadoEnum.fromSigla(novaSigla);
                                    escola.setEstado(novoEstado);
                                    System.out.println("🌎 Estado da escola atualizado com sucesso!");
                                } catch (IllegalArgumentException e) {
                                    System.out.println("❌ Estado inválido. Estado não foi alterado.");
                                }
                            }

                            System.out.println("✏️ Escola editada com sucesso!");
                        } else {
                            System.out.println("❌ Escola com ID " + id + " não encontrada.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID inválido.");
                    }
                }

                case "4" -> {
                    List<Escola> lista = controller.listarEscolas();
                    if (lista.isEmpty()) {
                        System.out.println("📭 Nenhuma escola cadastrada.");
                        break;
                    }

                    System.out.println("📚 Lista de escolas:");
                    lista.forEach(e -> System.out.println("ID: " + e.getId() +
                            " | Nome: " + e.getNome() +
                            " | Estado: " + e.getEstado().name()));

                    System.out.print("ID da escola a deletar: ");
                    String idStr = in.nextLine().trim();
                    if (idStr.isBlank()) {
                        System.out.println("⚠️ O ID não pode estar em branco.");
                        break;
                    }

                    try {
                        int id = Integer.parseInt(idStr);

                        List<Materia> materias;
                        try {
                            materias = MateriaController.carregar();
                        } catch (IOException | ClassNotFoundException e) {
                            System.err.println("Erro ao carregar matérias: " + e.getMessage());
                            break;
                        }

                        if (controller.deletarEscola(id, materias)) {
                            System.out.println("🗑️ Escola deletada com sucesso.");
                        } else {
                            System.out.println(
                                    "❌ Escola com ID " + id + " não encontrada ou está vinculada a uma matéria.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("❌ ID inválido.");
                    }
                }

                case "0" -> loop = false;

                default -> System.out.println("❌ Opção inválida! Tente novamente.");
            }

            try {
                controller.salvar();
            } catch (IOException e) {
                System.err.println("Erro ao salvar escolas: " + e.getMessage());
            }
        }
    }
}
