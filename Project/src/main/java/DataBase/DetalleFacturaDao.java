package DataBase;

import def.DetalleFactura;
import def.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleFacturaDao {

    // Insertar detalle de factura
    public static void insertarDetalle(Connection conn, int facturaId, DetalleFactura d) {
        String sql = "INSERT INTO detalle_factura(factura_id, producto_id, cantidad, precio) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, facturaId);
            pstmt.setInt(2, d.getProducto().getId());
            pstmt.setInt(3, d.getCantidad());
            pstmt.setDouble(4, d.getProducto().getPrecio()); // precio unitario
            pstmt.executeUpdate();
            System.out.println("Detalle insertado para factura ID: " + facturaId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Listar detalles por factura
    public static List<DetalleFactura> listarDetallesPorFactura(Connection conn, int facturaId) {
        List<DetalleFactura> lista = new ArrayList<>();
        String sql = "SELECT id, producto_id, cantidad, precio FROM detalle_factura WHERE factura_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, facturaId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Producto producto = ProductosDao.buscarProducto(conn, rs.getInt("producto_id"));
                int cantidad = rs.getInt("cantidad");
                DetalleFactura d = new DetalleFactura(producto, cantidad);
                d.setId(rs.getInt("id")); // importante para actualizar/eliminar
                lista.add(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Actualizar detalle de factura
    public static void actualizarDetalle(Connection conn, DetalleFactura d) {
        String sql = "UPDATE detalle_factura SET producto_id = ?, cantidad = ?, precio = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, d.getProducto().getId());
            pstmt.setInt(2, d.getCantidad());
            pstmt.setDouble(3, d.getProducto().getPrecio());
            pstmt.setInt(4, d.getId());
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Detalle actualizado con ID: " + d.getId());
            } else {
                System.out.println("No se encontró detalle con ID: " + d.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Eliminar detalle de factura
    public static void eliminarDetalle(Connection conn, int id) {
        String sql = "DELETE FROM detalle_factura WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Detalle eliminado con ID: " + id);
            } else {
                System.out.println("No se encontró detalle con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
