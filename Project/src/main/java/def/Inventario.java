package def;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Inventario {
    private Map<Integer, Producto> productos = new HashMap<>();

    public void agregarProducto(Producto p) {
        productos.put(p.getId(), p);
    }

    public void eliminarProducto(int id) {
        productos.remove(id);
    }

    public Producto buscarProducto(int id) {
        return productos.get(id);
    }

    public void actualizarStock(int id, int cantidad) {
        Producto p = productos.get(id);
        if (p != null) {
            p.actualizarStock(cantidad);
        }
    }

    public Map<Integer,Producto> getProductos() {
        return productos;
    }

    // 🔹 Nuevo: actualizar producto completo
    public void actualizarProducto(Producto p) {
        if (productos.containsKey(p.getId())) {
            productos.put(p.getId(), p);
        }
    }

    // 🔹 Nuevo: refrescar todo el inventario desde una lista
    public void setProductos(List<Producto> lista) {
        productos.clear();
        for (Producto p : lista) {
            productos.put(p.getId(), p);
        }
    }
}
