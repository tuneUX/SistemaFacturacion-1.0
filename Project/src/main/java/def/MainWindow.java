package def;
import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private Style btnCliente;
    private JPopupMenu menuCliente;

    private Style btnProducto;
    private JPopupMenu menuProducto;

    private Style btnVenta;
    private JPopupMenu menuVenta;

    private Style btnReporte;
    private JPopupMenu menuReporte;

    public MainWindow(){ 
        setTitle("Sistema de Ventas");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        Color darkBlue = new Color(0, 0, 139); 
        Color darkGreen = new Color(0, 100, 0); 
        Color darkBrown = new Color(150,75,0);


        // --- Cliente ---
        btnCliente = new Style("Cliente", darkBlue);
        menuCliente = new JPopupMenu();
        menuCliente.add(new JMenuItem("Agregar Cliente"));
        menuCliente.add(new JMenuItem("Buscar Cliente"));
        menuCliente.add(new JMenuItem("Actualizar Cliente"));
        menuCliente.add(new JMenuItem("Eliminar Cliente"));
        btnCliente.addActionListener(e -> menuCliente.show(btnCliente, 0, btnCliente.getHeight()));

        // --- Producto ---
        btnProducto = new Style("Producto", darkGreen);
        menuProducto = new JPopupMenu();
        menuProducto.add(new JMenuItem("Agregar Producto"));
        menuProducto.add(new JMenuItem("Buscar Producto"));
        menuProducto.add(new JMenuItem("Actualizar Producto"));
        menuProducto.add(new JMenuItem("Eliminar Producto"));
        btnProducto.addActionListener(e -> menuProducto.show(btnProducto, 0, btnProducto.getHeight()));

        // --- Venta ---
        btnVenta = new Style("Venta", darkBrown);
        menuVenta = new JPopupMenu();
        menuVenta.add(new JMenuItem("Registrar Venta"));
        menuVenta.add(new JMenuItem("Buscar Venta"));
        menuVenta.add(new JMenuItem("Anular Venta"));
        btnVenta.addActionListener(e -> menuVenta.show(btnVenta, 0, btnVenta.getHeight()));

        // --- Reporte ---
        btnReporte = new Style("Reporte", darkBlue);
        menuReporte = new JPopupMenu();
        menuReporte.add(new JMenuItem("Generar Reporte"));
        menuReporte.add(new JMenuItem("Exportar Reporte"));
        btnReporte.addActionListener(e -> menuReporte.show(btnReporte, 0, btnReporte.getHeight()));

        // Añadir botones al panel
        panel.add(btnCliente);
        panel.add(btnProducto);
        panel.add(btnVenta);
        panel.add(btnReporte);

        add(panel);
    }

    // Getters para que el controlador pueda acceder a los menus
    public JPopupMenu getMenuCliente(){ return menuCliente; }
    public JPopupMenu getMenuProducto(){ return menuProducto; }
    public JPopupMenu getMenuVenta(){ return menuVenta; }
    public JPopupMenu getMenuReporte(){ return menuReporte; }
}
