import javax.swing.SwingUtilities;

import service.AuthService;
import ui.LoginWindow;

public class Main {
    public static void main(String[] args) {
        // Inicializar algunos datos de prueba
        initSampleData();
        
        // Iniciar aplicación
        SwingUtilities.invokeLater(() -> {
            new LoginWindow();
        });
    }

    private static void initSampleData() {
        // Crear usuarios iniciales si no existen
        if (AuthService.login("admin", "admin123") == null) {
            AuthService.signUp("admin", "admin123", "ADMIN");
        }
        if (AuthService.login("prof1", "prof123") == null) {
            AuthService.signUp("prof1", "prof123", "PROFESOR");
        }
    }
}