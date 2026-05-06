package def;

import java.util.Date;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;

import DataBase.FacturaDao;
import DataBase.Connect;
import DataBase.DetalleFacturaDao;
import DataBase.ProductosDao;
import DataBase.ClientesDao;

public class Sistema {
    private Inventario inventario;
    private List<Cliente> clientes;

    public Sistema() {
        inventario = new Inventario();
        cargarClientesDesdeBD();
        cargarProductosDesdeBD();
    }

    // --- Inventario ---
    public Inventario getInventario() { return inventario; }

    private void cargarProductosDesdeBD() {
        try (Connection conn = Connect.getConnection()) {
            inventario.setProductos(ProductosDao.listarProductos(conn));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Clientes ---
    public void agregarCliente(Cliente c) {
        try (Connection conn = Connect.getConnection()) {
            ClientesDao.insertarCliente(conn, c);
            clientes = ClientesDao.listarClientes(conn);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public List<Cliente> getClientes() { return clientes; }

    public Cliente buscarCliente(int id) {
        try (Connection conn = Connect.getConnection()) {
            return ClientesDao.buscarCliente(conn, id);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void actualizarCliente(Cliente c) {
        try (Connection conn = Connect.getConnection()) {
            ClientesDao.ActualizarCliente(conn, c);
            clientes = ClientesDao.listarClientes(conn);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void eliminarCliente(int id) {
        try (Connection conn = Connect.getConnection()) {
            ClientesDao.EliminarCliente(conn, id);
            clientes = ClientesDao.listarClientes(conn);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void cargarClientesDesdeBD() {
        try (Connection conn = Connect.getConnection()) {
            clientes = ClientesDao.listarClientes(conn);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // --- Registrar Venta ---
    public Factura registrarVenta(int idCliente, int idProducto, int cantidad) {
        try (Connection conn = Connect.getConnection()) {
            Cliente cliente = ClientesDao.buscarCliente(conn, idCliente);
            Producto producto = ProductosDao.buscarProducto(conn, idProducto);

            if (cliente == null || producto == null) {
                throw new IllegalArgumentException("Cliente o producto no encontrado.");
            }

            Factura factura = new Factura(0, cliente, new Date(), 0.0);
            DetalleFactura detalle = new DetalleFactura(producto, cantidad);
            factura.agregarItem(detalle);
            factura.calcularTotal();

            int facturaId = FacturaDao.insertarFactura(conn, factura);
            factura.setId(facturaId);

            DetalleFacturaDao.insertarDetalle(conn, facturaId, detalle);
            ProductosDao.actualizarStock(conn, idProducto, -cantidad);

            inventario.setProductos(ProductosDao.listarProductos(conn));

            return factura;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // --- Productos ---
    public void agregarProducto(Producto p) {
        try (Connection conn = Connect.getConnection()) {
            ProductosDao.insertarProducto(conn, p);
            inventario.setProductos(ProductosDao.listarProductos(conn));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Producto buscarProducto(int id) {
        try (Connection conn = Connect.getConnection()) {
            return ProductosDao.buscarProducto(conn, id);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void actualizarProducto(Producto p) {
        try (Connection conn = Connect.getConnection()) {
            ProductosDao.actualizarProducto(conn, p);
            inventario.setProductos(ProductosDao.listarProductos(conn));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void eliminarProducto(int id) {
        try (Connection conn = Connect.getConnection()) {
            ProductosDao.eliminarProducto(conn, id);
            inventario.setProductos(ProductosDao.listarProductos(conn));
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // --- Facturas ---
    public Factura buscarFactura(int id) {
        try (Connection conn = Connect.getConnection()) {
            return FacturaDao.buscarFactura(conn, id);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void eliminarFactura(int id) {
        try (Connection conn = Connect.getConnection()) {
            FacturaDao.eliminarFactura(conn, id);
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // --- Reporte ---
    public String generarReporte() {
        try (Connection conn = Connect.getConnection()) {
            List<Factura> facturas = FacturaDao.listarFacturas(conn);
            double totalVentas = facturas.stream().mapToDouble(Factura::getTotal).sum();

            return "Total de ventas: " + totalVentas +
                   "\nNúmero de facturas: " + facturas.size() +
                   "\nProductos en inventario: " + inventario.getProductos().size() +
                   "\nClientes registrados: " + clientes.size();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al generar reporte.";
        }
    }

    public void exportarReporte(String rutaArchivo) {
        try (Connection conn = Connect.getConnection();
             java.io.PrintWriter pw = new java.io.PrintWriter(rutaArchivo)) {

            List<Factura> facturas = FacturaDao.listarFacturas(conn);
            double totalVentas = facturas.stream().mapToDouble(Factura::getTotal).sum();

            String reporte = "Total de ventas: " + totalVentas +
                             "\nNúmero de facturas: " + facturas.size() +
                             "\nProductos en inventario: " + inventario.getProductos().size() +
                             "\nClientes registrados: " + clientes.size();

            pw.println(reporte);
            System.out.println("Reporte exportado a " + rutaArchivo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
