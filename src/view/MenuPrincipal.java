package view;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public abstract class MenuPrincipal {
    public static void exibir() {
        try (Scanner in = new Scanner(System.in)) {
            boolean executando = true;

            while (executando) {
                try {
                    System.out.println("\n==============================");
                    System.out.println("       MENU PRINCIPAL");
                    System.out.println("==============================");
                    System.out.println("1 - Gerenciar Escolas");
                    System.out.println("2 - Gerenciar Matérias");
                    System.out.println("3 - Gerenciar Professores");
                    System.out.println("4 - Gerenciar Alunos");
                    System.out.println("5 - Popular dados iniciais (somente para testes)");
                    System.out.println("0 - Sair");
                    System.out.print("Escolha uma opção: ");

                    String opcao = in.nextLine().trim();

                    if (opcao.isBlank()) {
                        System.out.println("⚠️ Entrada vazia. Por favor, escolha uma opção válida.");
                        continue;
                    }

                    switch (opcao) {
                        case "1" -> EscolaView.menu(in);
                        case "2" -> MateriaView.menu(in);
                        case "3" -> {
                            try {
                                ProfessorView.menu(in);
                            } catch (Exception e) {
                                System.err.println("Erro ao acessar menu de professores: " + e.getMessage());
                            }
                        }
                        case "4" -> {
                            try {
                                AlunoView.menu(in);
                            } catch (Exception e) {
                                System.err.println("Erro ao acessar menu de alunos: " + e.getMessage());
                            }
                        }
                        case "5" -> {
                            try {
                                util.Populador.popular();
                            } catch (Exception e) {
                                System.err.println("Erro ao popular dados: " + e.getMessage());
                            }
                        }

                        case "0" -> {
                            System.out.println("👋 Encerrando o sistema. Até logo!");
                            executando = false;
                        }
                        default -> System.out.println("❌ Opção inválida! Digite um número de 0 a 4.");
                    }

                } catch (NoSuchElementException | IllegalStateException e) {
                    System.err.println("❌ Erro de entrada. Encerrando o sistema.");
                    executando = false;
                }
            }
        }
    }
}
