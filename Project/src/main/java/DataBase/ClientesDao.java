package DataBase;

import def.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientesDao {

    // Insertar cliente
    public static void insertarCliente(Connection conn, Cliente c) {
        String sql = "INSERT INTO clientes( nombre , telefono , codigo ) VALUES(? , ? , ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, c.getNombre());
            pstmt.setString(2, c.getTelefono());
            pstmt.setString(3, c.getCodigo());
            pstmt.executeUpdate();
            System.out.println("Cliente ingresado: " + c.getNombre());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Buscar cliente por ID
    public static Cliente buscarCliente(Connection conn, int id) {
        String sql = "SELECT id, nombre ,telefono,codigo FROM clientes WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Cliente(rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("codigo"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Listar todos los clientes
    public static List<Cliente> listarClientes(Connection conn) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, nombre , telefono , codigo FROM clientes";
        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("telefono"),
                    rs.getString("codigo"));
                lista.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public static  void ActualizarCliente(Connection conn,Cliente c){
        String sql = "UPDATE clientes SET nombre = ?, telefono = ?, codigo = ? WHERE id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, c.getNombre());
            pstmt.setString(2, c.getTelefono());
            pstmt.setString(3, c.getCodigo());
            pstmt.setInt(4, c.getId());
            int filas  = pstmt.executeUpdate();
            
            if (filas > 0) {
                System.out.println("cliente actualizado"+ c.getNombre());

            }else{
                System.out.println("no se encontro cliente con id"+ c.getId());

            }





        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static  void EliminarCliente(Connection conn,int id){
        String sql = "DELETE FROM clientes  WHERE id = ?";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setInt(1,id);
           
            int filas  = pstmt.executeUpdate();
            
            if (filas > 0) {
                System.out.println("cliente eliminado"+ id);

            }else{
                System.out.println("no se encontro cliente con id"+ id);

            }





        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
