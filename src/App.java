import util.Log;
import view.MenuPrincipal;

public class App {
    public static void main(String[] args) {
        Log.configurarErro(); // redireciona logs de erro para o arquivo

        try {
            MenuPrincipal.exibir(); // chama o menu principal
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
