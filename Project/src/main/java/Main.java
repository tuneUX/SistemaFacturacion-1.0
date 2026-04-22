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

        // Probar conexion  MySQL
        try (Connection conn = Connect.getConnection()) {
            System.out.println("Conexión exitosa a MySQL.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.\n" + e.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // salir si no hay conexi0n
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
