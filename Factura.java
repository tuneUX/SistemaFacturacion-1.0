import java.util.ArrayList;

public class Factura {
    private int id;
    private Cliente cliente;
    private ArrayList<DetalleFactura> detalles;

    // Constructor
    public Factura(int id, Cliente cliente) {
        this.id = id;
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
        // Agregar esta factura al historial del cliente
        cliente.agregarFactura(this);
    }

    // Getters
    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    // Agregar detalle de factura
    public void agregarDetalle(DetalleFactura d) {
        detalles.add(d);
    }

    // Calcular total de la factura
    public double calcularTotal() {
        double total = 0;
        for(DetalleFactura d : detalles) {
            total += d.calcularSubtotal();
        }
        return total;
    }

    // Mostrar factura
    public void mostrarFactura() {
        System.out.println("Factura ID: " + id);
        System.out.println("Cliente: " + cliente.getNombre());
        for(DetalleFactura d : detalles) {
            System.out.println(d.getProducto().getNombre() + " x" + d.getCantidad() + " = " + d.calcularSubtotal());
        }
        System.out.println("Total: " + calcularTotal());
    }
	public ArrayList<DetalleFactura> getDetalles() {
    return detalles;
}
}