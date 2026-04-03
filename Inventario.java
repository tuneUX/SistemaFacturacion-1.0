import java.util.HashMap;
import java.util.Map;

public class Inventario {

    private Map<Integer, Producto> productos = new HashMap<>();

    public void agregarProducto(Producto p) {
        productos.put(p.getId(), p);
    }

    public Producto buscarProducto(int id) {
        return productos.get(id);
    }

    public void mostrarProductos() {
        for (Producto p : productos.values()) {
            System.out.println(p);
        }
    }

    public void actualizarStock(int id, int cantidad) {
        Producto p = productos.get(id);
        if (p != null) {
            p.actualizarStock(cantidad);
        }
    }
}