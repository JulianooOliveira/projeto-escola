import util.Log;
import view.MenuPrincipal;

public class App {
    public static void main(String[] args) {
        // Inicializa o sistema de log
        Log.configurarErro();

        // Inicia o menu principal do sistema
        MenuPrincipal.exibir();
    }
}
