package def;

import java.util.ArrayList;
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
    private ListaFacturas listaFacturas;
    private List<Cliente> clientes;

    public Sistema() {
        inventario = new Inventario();
        listaFacturas = new ListaFacturas();
        clientes = new ArrayList<>();
        cargarClientesDesdeBD();
    }

    // --- Inventario ---
    public Inventario getInventario() { return inventario; }

    // --- Facturas ---
    public ListaFacturas getListaFacturas() { return listaFacturas; }

    // --- Clientes ---
    public void agregarCliente(Cliente c) {
        try (Connection conn = Connect.getConnection()) {
            ClientesDao.insertarCliente(conn, c);
            clientes = ClientesDao.listarClientes(conn); // refrescar lista
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> getClientes() { return clientes; }

    public Cliente buscarCliente(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) return c;
        }
        try (Connection conn = Connect.getConnection()) {
            return ClientesDao.buscarCliente(conn, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- Registrar Venta ---
    public Factura registrarVenta(int idCliente, int idProducto, int cantidad) {
        Cliente cliente = buscarCliente(idCliente);
        Producto producto = inventario.buscarProducto(idProducto);

        if (cliente == null || producto == null) {
            throw new IllegalArgumentException("Cliente o producto no encontrado.");
        }

        Factura factura = new Factura(listaFacturas.getFacturas().size() + 1, cliente, new Date(), 0.0);
        DetalleFactura detalle = new DetalleFactura(producto, cantidad);
        factura.agregarItem(detalle);

        inventario.actualizarStock(idProducto, -cantidad);
        factura.calcularTotal();
        listaFacturas.agregarFactura(factura);

        try (Connection conn = Connect.getConnection()) {
            int facturaId = FacturaDao.insertarFactura(conn, factura);
            DetalleFacturaDao.insertarDetalle(conn, facturaId, detalle);
            ProductosDao.actualizarStock(conn, idProducto, -cantidad);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return factura;
    }

    // --- Productos ---

    public void agregarProducto(Producto p) {
    try (Connection conn = Connect.getConnection()) {
        ProductosDao.insertarProducto(conn, p);
        inventario.setProductos(ProductosDao.listarProductos(conn)); // refrescar inventario
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

    public Producto buscarProducto(int id) {
        try (Connection conn = Connect.getConnection()) {
        return ProductosDao.buscarProducto(conn, id);
        
    } catch (SQLException e) 
        
        { e.printStackTrace(); }
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
        for (Factura f : listaFacturas.getFacturas()) {
            if (f.getId() == id) {
                return f;
            }
        }
        return null;
    }
   


    // --- Reporte ---
    public String generarReporte() {
        double totalVentas = listaFacturas.calcularVentasTotales();
        return "Total de ventas: " + totalVentas +
               "\nNúmero de facturas: " + listaFacturas.getFacturas().size() +
               "\nProductos en inventario: " + inventario.getProductos().size() +
               "\nClientes registrados: " + clientes.size();
    }

    public void exportarReporte(String rutaArchivo) {
    try (java.io.PrintWriter pw = new java.io.PrintWriter(rutaArchivo)) {
        String reporte = generarReporte(); // ya tienes este metodo
        pw.println(reporte);
        System.out.println("Reporte exportado a " + rutaArchivo);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
}
