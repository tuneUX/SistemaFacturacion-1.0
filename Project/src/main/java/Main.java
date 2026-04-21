import javax.swing.*;
import com.formdev.flatlaf.FlatDarkLaf;
import def.MainController;
import def.MainWindow;
import def.Sistema;
import DataBase.Connect;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        // Configurar look & feel
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Probar conexión a la base MySQL
        try (Connection conn = Connect.getConnection()) {
            System.out.println("✅ Conexión exitosa a MySQL.");
        } catch (Exception e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
        }

        // Crear y mostrar la ventana principal
        SwingUtilities.invokeLater(() -> {
            Sistema sistema = new Sistema();          
            MainWindow ventana = new MainWindow();    
            new MainController(sistema, ventana); 
            ventana.setVisible(true);                 
            System.out.println("Working dir: " + System.getProperty("user.dir"));
        });
    }
}
