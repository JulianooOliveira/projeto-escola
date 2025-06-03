package util;

import java.io.File;
import java.io.PrintStream;

public abstract class Log {
    public static void configurarErro() {
        try {
            File logDir = new File("logs");
            if (!logDir.exists()) {
                logDir.mkdirs();
            }

            PrintStream logErro = new PrintStream("logs/log.txt");
            System.setErr(logErro);
        } catch (Exception e) {
            System.err.println("Falha ao redirecionar log de erro: " + e.getMessage());
        }
    }
}
