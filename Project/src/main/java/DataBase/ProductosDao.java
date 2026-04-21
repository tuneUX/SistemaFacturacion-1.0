package DataBase;

import def.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductosDao {

    // Insertar producto
    public static void insertarProducto(Connection conn, Producto p) {
        String sql = "INSERT INTO productos(nombre, stock, precio) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getNombre());
            pstmt.setInt(2, p.getStock());
            pstmt.setDouble(3, p.getPrecio());
            pstmt.executeUpdate();
            System.out.println("Producto insertado: " + p.getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Buscar producto por ID
    public static Producto buscarProducto(Connection conn, int id) {
        String sql = "SELECT id, nombre, stock, precio FROM productos WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("stock"),
                    rs.getDouble("precio")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Listar todos los productos
    public static List<Producto> listarProductos(Connection conn) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, stock, precio FROM productos";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("stock"),
                    rs.getDouble("precio")
                );
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Actualizar stock (ejemplo: restar al vender o sumar al reabastecer)
    public static void actualizarStock(Connection conn, int id, int cantidad) {
        String sql = "UPDATE productos SET stock = stock + ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cantidad);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Stock actualizado para producto ID: " + id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Actualizar producto completo (nombre, stock, precio)
    public static void actualizarProducto(Connection conn, Producto p) {
        String sql = "UPDATE productos SET nombre = ?, stock = ?, precio = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getNombre());
            pstmt.setInt(2, p.getStock());
            pstmt.setDouble(3, p.getPrecio());
            pstmt.setInt(4, p.getId());
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Producto actualizado: " + p.getNombre());
            } else {
                System.out.println("No se encontró producto con ID: " + p.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Eliminar producto
    public static void eliminarProducto(Connection conn, int id) {
        String sql = "DELETE FROM productos WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Producto eliminado con ID: " + id);
            } else {
                System.out.println("No se encontró producto con ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
