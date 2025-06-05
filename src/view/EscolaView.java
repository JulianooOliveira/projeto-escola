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
                    String nome = in.nextLine();

                    System.out.println("Selecione o estado (PR ou SC):");
                    for (EstadoEnum estado : EstadoEnum.values()) {
                        System.out.println(estado.name() + " - " + estado.getNome());
                    }

                    System.out.print("Sigla do estado: ");
                    String sigla = in.nextLine().trim().toUpperCase();

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
                    System.out.println("📚 Lista de escolas:");
                    List<Escola> lista = controller.listarEscolas();
                    if (lista.isEmpty()) {
                        System.out.println("📭 Nenhuma escola cadastrada.");
                    } else {
                        lista.forEach(e -> System.out.println("ID: " + e.getId() +
                                " | Nome: " + e.getNome() +
                                " | Estado: " + e.getEstado().name()));
                    }
                }

                case "3" -> {
                    if (controller.listarEscolas().isEmpty()) {
                        System.out.println("⚠️ Nenhuma escola cadastrada para editar.");
                        break;
                    }

                    System.out.print("ID da escola a editar: ");
                    int id = Integer.parseInt(in.nextLine());

                    Optional<Escola> escolaOpt = controller.buscarEscolaPorId(id);
                    if (escolaOpt.isPresent()) {
                        System.out.print("Novo nome da escola: ");
                        String novoNome = in.nextLine();
                        controller.editarEscola(id, novoNome);
                        System.out.println("✏️ Escola editada com sucesso!");
                    } else {
                        System.out.println("❌ Escola com ID " + id + " não encontrada.");
                    }
                }

                case "4" -> {
                    if (controller.listarEscolas().isEmpty()) {
                        System.out.println("⚠️ Nenhuma escola cadastrada para deletar.");
                        break;
                    }

                    System.out.print("ID da escola a deletar: ");
                    int id = Integer.parseInt(in.nextLine());

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
                        System.out
                                .println("❌ Escola com ID " + id + " não encontrada ou está vinculada a uma matéria.");
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
