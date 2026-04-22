package def;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Factura {
    private int id;
    private Date fecha;
    private Cliente cliente;
    private List<DetalleFactura> items;
    private double total;

    // Constructor
    public Factura(int id, Cliente cliente, Date fecha, double total) {
        this.id = id;
        this.fecha = fecha;
        this.cliente = cliente;
        this.items = new ArrayList<>();
        this.total = total;
    }

    // Getters
    public int getId() { return id; }
    public Date getFecha() { return fecha; }
    public Cliente getCliente() { return cliente; }
    public double getTotal() { return total; }
    public List<DetalleFactura> getItems() { return items; }

    // Setter for ID ( to sync with DB generated ID)
    public void setId(int id) { this.id = id; }

    // metodos de comportamiento
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
        System.out.println("Factura #" + id + 
                           " Cliente: " + cliente.getNombre() + 
                           " Total: " + total);
    }

    @Override
    public String toString() {
        return "Factura #" + id + 
               " | Cliente: " + cliente.getNombre() + 
               " | Fecha: " + fecha + 
               " | Total: " + total;
    }
}
