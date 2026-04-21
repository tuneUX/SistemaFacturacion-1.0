package DataBase;

import def.Factura;
import def.Cliente;
import def.DetalleFactura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FacturaDao {

    // Insertar factura y devolver el ID generado
    public static int insertarFactura(Connection conn, Factura f) {
        String sql = "INSERT INTO facturas(cliente_id, fecha, total) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, f.getCliente().getId());
            pstmt.setDate(2, new java.sql.Date(f.getFecha().getTime())); // convertir Date a SQL Date
            pstmt.setDouble(3, f.getTotal());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // devuelve el ID generado
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // Buscar factura por ID (incluye detalles)
    public static Factura buscarFactura(Connection conn, int id) {
        String sql = "SELECT id, cliente_id, fecha, total FROM facturas WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = ClientesDao.buscarCliente(conn, clienteId);

                Factura f = new Factura(
                    rs.getInt("id"),
                    cliente,
                    rs.getDate("fecha"),
                    rs.getDouble("total")
                );

                // Cargar detalles desde detalle_factura
                List<DetalleFactura> detalles = DetalleFacturaDao.listarDetallesPorFactura(conn, id);
                for (DetalleFactura d : detalles) {
                    f.agregarItem(d);
                }

                return f;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Listar todas las facturas (incluye detalles)
    public static List<Factura> listarFacturas(Connection conn) {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT id, cliente_id, fecha, total FROM facturas";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int clienteId = rs.getInt("cliente_id");
                Cliente cliente = ClientesDao.buscarCliente(conn, clienteId);

                Factura f = new Factura(
                    rs.getInt("id"),
                    cliente,
                    rs.getDate("fecha"),
                    rs.getDouble("total")
                );

                // Cargar detalles de cada factura
                List<DetalleFactura> detalles = DetalleFacturaDao.listarDetallesPorFactura(conn, f.getId());
                for (DetalleFactura d : detalles) {
                    f.agregarItem(d);
                }

                lista.add(f);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Actualizar factura (sin tocar detalles)
    public static void actualizarFactura(Connection conn, Factura f) {
        String sql = "UPDATE facturas SET cliente_id = ?, fecha = ?, total = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, f.getCliente().getId());
            pstmt.setDate(2, new java.sql.Date(f.getFecha().getTime()));
            pstmt.setDouble(3, f.getTotal());
            pstmt.setInt(4, f.getId());
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Factura actualizada con ID: " + f.getId());
            } else {
                System.out.println("No se encontró factura con ID: " + f.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar factura (y opcionalmente sus detalles)
    public static void eliminarFactura(Connection conn, int id) {
        try {
            // Primero eliminar detalles asociados
            String sqlDetalles = "DELETE FROM detalle_factura WHERE factura_id = ?";
            try (PreparedStatement pstmtDetalles = conn.prepareStatement(sqlDetalles)) {
                pstmtDetalles.setInt(1, id);
                pstmtDetalles.executeUpdate();
            }

            // Luego eliminar la factura
            String sqlFactura = "DELETE FROM facturas WHERE id = ?";
            try (PreparedStatement pstmtFactura = conn.prepareStatement(sqlFactura)) {
                pstmtFactura.setInt(1, id);
                int filas = pstmtFactura.executeUpdate();
                if (filas > 0) {
                    System.out.println("Factura eliminada con ID: " + id);
                } else {
                    System.out.println("No se encontró factura con ID: " + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
