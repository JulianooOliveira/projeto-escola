package view;

import java.util.Scanner;

public abstract class MenuPrincipal {
    public static void exibir() {
        Scanner in = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            System.out.println("\n====== MENU PRINCIPAL ======");
            System.out.println("1 - Gerenciar Escolas");
            System.out.println("2 - Gerenciar Matérias");
            System.out.println("3 - Gerenciar Professores");
            System.out.println("4 - Gerenciar Alunos");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = in.nextLine();

            switch (opcao) {
                case "1" -> EscolaView.menu(in);
                case "2" -> MateriaView.menu(in);
                case "3" -> ProfessorView.menu(in);
                case "4" -> AlunoView.menu(in);
                case "0" -> {
                    System.out.println("Encerrando o sistema...");
                    executando = false;
                }
                default -> System.out.println("Opção inválida!");
            }
        }

        in.close();
    }
}
