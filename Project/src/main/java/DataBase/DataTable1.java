/* package DataBase;
import java.sql.Connection;
import java.sql.Statement;

public class DataTable1 {

    // Este método recibe la conexión ya abierta desde otro archivo
    public static void createTable(Connection conn) {
        

        String clientesDb = """
            CREATE TABLE IF NOT EXISTS clientes (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                telefono TEXT,
                codigo TEXT
            );
        """;

        String productosDb = """
            CREATE TABLE IF NOT EXISTS productos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                stock INTEGER NOT NULL,
                precio REAL NOT NULL
            );
        """;

        String facturasDb = """
            CREATE TABLE IF NOT EXISTS facturas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                cliente_id INTEGER NOT NULL,
                fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
                total REAL,
                FOREIGN KEY (cliente_id) REFERENCES clientes(id)
            );
        """;

        String detalleFacturaDb = """
            CREATE TABLE IF NOT EXISTS detalle_factura (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                factura_id INTEGER NOT NULL,
                producto_id INTEGER NOT NULL,
                cantidad INTEGER NOT NULL,
                precio REAL NOT NULL,
                FOREIGN KEY (factura_id) REFERENCES facturas(id),
                FOREIGN KEY (producto_id) REFERENCES productos(id)
            );
        """;

        try (Statement stmt = conn.createStatement()) {
           
        
            stmt.execute(clientesDb);
            stmt.execute(productosDb);
            stmt.execute(facturasDb);
            stmt.execute(detalleFacturaDb);
            System.out.println("Todas las tablas creadas o ya existentes.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 */