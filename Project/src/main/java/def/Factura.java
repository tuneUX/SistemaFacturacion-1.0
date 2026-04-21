package def;
import java.util.*;

public class Factura {
    private int id;
    private Date fecha;
    private Cliente cliente;
    private List<DetalleFactura> items = new ArrayList<>();
    private double total;

    public Factura(int id, Cliente cliente , Date fecha , double total) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.items = new ArrayList<>();
    }

    //  Getters obligatorios
    public int getId() { return id; }
    public Date getFecha() { return fecha; }
    public Cliente getCliente() { return cliente; }
    public double getTotal() { return total; }
    public List<DetalleFactura> getItems() {
    return items;
}

    
    // Metodos de comportamiento
    public void agregarItem(DetalleFactura item) {
        items.add(item);
        calcularTotal();
    }

    public void calcularTotal() {
        total = 0;
        for (DetalleFactura item : items) {
            total += item.calcularSubtotal();
        }
    }
   

    public void generarFactura() {
        System.out.println("Factura #" + id + " Cliente: " + cliente.getNombre() + " Total: " + total);
    }
}
