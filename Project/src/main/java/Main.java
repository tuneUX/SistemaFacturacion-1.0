import javax.swing.*;
import com.formdev.flatlaf.FlatDarkLaf;
import def.MainController;
import def.MainWindow;
import def.Sistema;
import DataBase.Connect;

import java.sql.Connection;

// AWT para SystemTray
import java.awt.AWTException;
import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.MenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    public static void main(String[] args) {
        // Configurar look & feel
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Probar conexion MySQL
        try (Connection conn = Connect.getConnection()) {
            System.out.println("Conexion exitosa a MySQL.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar a la base de datos.\n" + e.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); // salir si no hay conexion
        }

        // Crear y mostrar la ventana principal
        SwingUtilities.invokeLater(() -> {
            Sistema sistema = new Sistema();
            MainWindow ventana = new MainWindow();
            new MainController(sistema, ventana);

            // Configurar SystemTray
            if (SystemTray.isSupported()) {
                SystemTray tray = SystemTray.getSystemTray();
                 PopupMenu popup = new PopupMenu();
                MenuItem openItem = new MenuItem("Abrir");
                MenuItem exitItem = new MenuItem("Salir");
                 openItem.addActionListener(e -> ventana.setVisible(true));
                exitItem.addActionListener(e -> System.exit(0));

                popup.add(openItem);
                popup.add(exitItem);

                 ImageIcon icon = new ImageIcon("C:/Users/Facmd.FACM/Desktop/JavaTeoria/Project/src/main/java/def/logo.png");
                Image image = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);


                TrayIcon trayIcon = new TrayIcon(image, "Sistema Inventario", popup);
                trayIcon.setImageAutoSize(true);



               

               

                

                try {
                    tray.add(trayIcon);
                } catch (AWTException e) {
                    e.printStackTrace();
                }
            }

            // Listener para ocultar en vez de cerrar
            ventana.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            ventana.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    ventana.setVisible(false); // se oculta en vez de cerrar
                }
            });

            ventana.setVisible(true);
            System.out.println("Working dir: " + System.getProperty("user.dir"));
        });
    }
}
