import util.Log;
import view.MenuPrincipal;

public class App {
    public static void main(String[] args) {
        Log.configurarErro();

        try {
            System.out.println("🚀 Sistema de Gestão Escolar iniciado com sucesso!");
            MenuPrincipal.exibir();
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
