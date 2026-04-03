public class DetalleFactura {
    private Producto producto;
    private int cantidad;

    // Constructor
    public DetalleFactura(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    // Getters necesarios para Factura.java
    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    // Calcular subtotal
    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}