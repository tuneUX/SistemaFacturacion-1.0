package def;

import javax.swing.*;

public class MainController {
    private Sistema sistema;
    private MainWindow ventana;

    public MainController(Sistema sistema, MainWindow ventana) {
        this.sistema = sistema;
        this.ventana = ventana;

        // --- Cliente ---
        JMenuItem agregarCliente = (JMenuItem) ventana.getMenuCliente().getComponent(0);
        JMenuItem buscarCliente = (JMenuItem) ventana.getMenuCliente().getComponent(1);
        JMenuItem actualizarCliente = (JMenuItem) ventana.getMenuCliente().getComponent(2);
        JMenuItem eliminarCliente = (JMenuItem) ventana.getMenuCliente().getComponent(3);

        agregarCliente.addActionListener(e -> {
            JTextField txtNombre = new JTextField();
            JTextField txtTelefono = new JTextField();
            JTextField txtCodigo = new JTextField();

            Object[] campos = { "Nombre:", txtNombre, "Telefono:", txtTelefono, "Codigo:", txtCodigo };

            int opcion = JOptionPane.showConfirmDialog(ventana, campos, "Agregar Cliente", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                Cliente nuevo = new Cliente(0, txtNombre.getText(), txtTelefono.getText(), txtCodigo.getText());
                sistema.agregarCliente(nuevo);
                JOptionPane.showMessageDialog(ventana, "Cliente agregado correctamente.");
            }
        });

        buscarCliente.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(ventana, "Ingrese ID del cliente:");
            try {
                int id = Integer.parseInt(idStr);
                Cliente c = sistema.buscarCliente(id);
                JOptionPane.showMessageDialog(ventana, c != null ? "Cliente encontrado:\n" + c : "Cliente no encontrado.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        actualizarCliente.addActionListener(e -> {
            JTextField txtId = new JTextField();
            JTextField txtNombre = new JTextField();
            JTextField txtTelefono = new JTextField();
            JTextField txtCodigo = new JTextField();

            Object[] campos = {"ID:", txtId, "Nombre:", txtNombre, "Teléfono:", txtTelefono, "Código:", txtCodigo};

            int opcion = JOptionPane.showConfirmDialog(ventana, campos, "Actualizar Cliente", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    Cliente c = new Cliente(id, txtNombre.getText(), txtTelefono.getText(), txtCodigo.getText());
                    sistema.actualizarCliente(c);
                    JOptionPane.showMessageDialog(ventana, "Cliente actualizado.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ventana, "ID invalido.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        eliminarCliente.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(ventana, "Ingrese ID del cliente a eliminar:");
            try {
                int id = Integer.parseInt(idStr);
                sistema.eliminarCliente(id);
                JOptionPane.showMessageDialog(ventana, "Cliente eliminado.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "ID invalido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // --- Producto ---
        JMenuItem agregarProducto = (JMenuItem) ventana.getMenuProducto().getComponent(0);
        JMenuItem buscarProducto = (JMenuItem) ventana.getMenuProducto().getComponent(1);
        JMenuItem actualizarProducto = (JMenuItem) ventana.getMenuProducto().getComponent(2);
        JMenuItem eliminarProducto = (JMenuItem) ventana.getMenuProducto().getComponent(3);

        agregarProducto.addActionListener(e -> {
            JTextField txtNombre = new JTextField();
            JTextField txtPrecio = new JTextField();
            JTextField txtStock = new JTextField();

            Object[] campos = {"Nombre:", txtNombre, "Precio:", txtPrecio, "Stock:", txtStock};

            int opcion = JOptionPane.showConfirmDialog(ventana, campos, "Agregar Producto", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                try {
                    double precio = Double.parseDouble(txtPrecio.getText());
                    int stock = Integer.parseInt(txtStock.getText());
                    Producto nuevo = new Producto(0, txtNombre.getText(), stock, precio);
                    sistema.agregarProducto(nuevo);
                    JOptionPane.showMessageDialog(ventana, "Producto agregado correctamente.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ventana, "Datos invalidos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buscarProducto.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(ventana, "Ingrese ID del producto:");
            try {
                int id = Integer.parseInt(idStr);
                Producto p = sistema.buscarProducto(id);
                JOptionPane.showMessageDialog(ventana, p != null ? "Producto encontrado:\n" + p : "Producto no encontrado.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "ID invalido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        actualizarProducto.addActionListener(e -> {
            JTextField txtId = new JTextField();
            JTextField txtNombre = new JTextField();
            JTextField txtPrecio = new JTextField();
            JTextField txtStock = new JTextField();

            Object[] campos = {"ID:", txtId, "Nombre:", txtNombre, "Precio:", txtPrecio, "Stock:", txtStock};

            int opcion = JOptionPane.showConfirmDialog(ventana, campos, "Actualizar Producto", JOptionPane.OK_CANCEL_OPTION);
            if (opcion == JOptionPane.OK_OPTION) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    double precio = Double.parseDouble(txtPrecio.getText());
                    int stock = Integer.parseInt(txtStock.getText());
                    Producto p = new Producto(id, txtNombre.getText(), stock, precio);
                    sistema.actualizarProducto(p);
                    JOptionPane.showMessageDialog(ventana, "Producto actualizado.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ventana, "Datos inválidos.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        eliminarProducto.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(ventana, "Ingrese ID del producto a eliminar:");
            try {
                int id = Integer.parseInt(idStr);
                sistema.eliminarProducto(id);
                JOptionPane.showMessageDialog(ventana, "Producto eliminado.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana, "ID invalido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

      // --- Venta / Factura ---
JMenuItem registrarVenta = (JMenuItem) ventana.getMenuVenta().getComponent(0);
registrarVenta.addActionListener(e -> {
    JTextField txtIdCliente = new JTextField();
    JTextField txtIdProducto = new JTextField();
    JTextField txtCantidad = new JTextField();

    Object[] campos = {"ID Cliente:", txtIdCliente, "ID Producto:", txtIdProducto, "Cantidad:", txtCantidad};

    int opcion = JOptionPane.showConfirmDialog(ventana, campos, "Registrar Venta", JOptionPane.OK_CANCEL_OPTION);
    if (opcion == JOptionPane.OK_OPTION) {
        try {
            int idCliente = Integer.parseInt(txtIdCliente.getText());
            int idProducto = Integer.parseInt(txtIdProducto.getText());
            int cantidad = Integer.parseInt(txtCantidad.getText());
            Factura factura = sistema.registrarVenta(idCliente, idProducto, cantidad);
            JOptionPane.showMessageDialog(ventana, "Venta registrada. Factura #" + factura.getId());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(ventana, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
});

// Buscar Venta
JMenuItem buscarVenta = (JMenuItem) ventana.getMenuVenta().getComponent(1);
buscarVenta.addActionListener(e -> {
    String idStr = JOptionPane.showInputDialog(ventana, "Ingrese ID de la factura:");
    try {
        int id = Integer.parseInt(idStr);
        Factura f = sistema.buscarFactura(id);
        JOptionPane.showMessageDialog(ventana, f != null ? "Factura encontrada:\n" + f : "Factura no encontrada.");
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(ventana, "ID invalido.", "Error", JOptionPane.ERROR_MESSAGE);
    }
});

// Eliminar Venta
JMenuItem eliminarVenta = (JMenuItem) ventana.getMenuVenta().getComponent(2);
eliminarVenta.addActionListener(e -> {
    String idStr = JOptionPane.showInputDialog(ventana, "Ingrese ID de la factura a eliminar:");
    try {
        int id = Integer.parseInt(idStr);
        sistema.eliminarFactura(id);
        JOptionPane.showMessageDialog(ventana, "Factura eliminada.");
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(ventana, "ID inválido.", "Error", JOptionPane.ERROR_MESSAGE);
    }
});

 


        // --- Reporte ---
        JMenuItem generarReporte = (JMenuItem) ventana.getMenuReporte().getComponent(0);
        generarReporte.addActionListener(e -> {
            String reporte = sistema.generarReporte();
            JOptionPane.showMessageDialog(ventana, reporte, "Reporte de Ventas", JOptionPane.INFORMATION_MESSAGE);
        });
        JMenuItem exportarReporte = (JMenuItem) ventana.getMenuReporte().getComponent(1);
exportarReporte.addActionListener(e -> {
    String ruta = JOptionPane.showInputDialog(ventana, "Ingrese nombre de archivo para exportar el reporte:");
    if (ruta != null && !ruta.trim().isEmpty()) {
        sistema.exportarReporte(ruta);
        JOptionPane.showMessageDialog(ventana, "Reporte exportado correctamente.");
    }
});

    }
}
