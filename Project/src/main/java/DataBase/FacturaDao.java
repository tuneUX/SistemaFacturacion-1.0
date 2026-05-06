package DataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import def.Factura;
import def.Cliente;

public class FacturaDao {

    // Insertar factura y devolver el ID generado
    public static int insertarFactura(Connection conn, Factura factura) {
        String sql = "INSERT INTO facturas (cliente_id, fecha, total) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, factura.getCliente().getId());
            pstmt.setDate(2, new java.sql.Date(factura.getFecha().getTime()));
            pstmt.setDouble(3, factura.getTotal());
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1); // devuelve el ID generado
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Buscar factura por ID
    public static Factura buscarFactura(Connection conn, int id) {
        String sql = "SELECT * FROM facturas WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = ClientesDao.buscarCliente(conn, rs.getInt("cliente_id"));
                    return new Factura(
                        rs.getInt("id"),
                        cliente,
                        rs.getDate("fecha"),
                        rs.getDouble("total")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Listar todas las facturas
    public static List<Factura> listarFacturas(Connection conn) {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM facturas";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Cliente cliente = ClientesDao.buscarCliente(conn, rs.getInt("cliente_id"));
                Factura factura = new Factura(
                    rs.getInt("id"),
                    cliente,
                    rs.getDate("fecha"),
                    rs.getDouble("total")
                );
                lista.add(factura);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Eliminar factura con sus detalles
    public static void eliminarFactura(Connection conn, int idFactura) {
        try {
            // Primero borrar los detalles
            String sqlDetalles = "DELETE FROM detalle_factura WHERE factura_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlDetalles)) {
                pstmt.setInt(1, idFactura);
                pstmt.executeUpdate();
            }

            // Luego borrar la factura
            String sqlFactura = "DELETE FROM facturas WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sqlFactura)) {
                pstmt.setInt(1, idFactura);
                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Factura eliminada con id " + idFactura);
                } else {
                    System.out.println("No se encontró factura con id " + idFactura);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
