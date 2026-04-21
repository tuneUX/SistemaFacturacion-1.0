package def;

public class DetalleFactura {
    private int id;              // clave primaria en la tabla detalle_factura
    private Producto producto;
    private int cantidad;
    private double subtotal;

    // --- Constructores ---
    public DetalleFactura(int id, Producto producto, int cantidad) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecio() * cantidad;
    }

    public DetalleFactura(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.subtotal = producto.getPrecio() * cantidad;
    }

    // --- Getters y Setters ---
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { 
        this.producto = producto; 
        recalcularSubtotal();
    }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
        recalcularSubtotal();
    }

    public double getSubtotal() { return subtotal; }

    // --- Métodos de comportamiento ---
    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    private void recalcularSubtotal() {
        this.subtotal = calcularSubtotal();
    }

    @Override
    public String toString() {
        return "DetalleFactura{" +
                "id=" + id +
                ", producto=" + producto.getNombre() +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                '}';
    }
}
