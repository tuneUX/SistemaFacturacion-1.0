package def;
import java.util.*;

public class ListaFacturas {
    private List<Factura> facturas = new ArrayList<>();

    public void agregarFactura(Factura f) {
        facturas.add(f);
    }

    public Factura buscarFactura(int id) {
        for (Factura f : facturas) {
            if (f.getId() == id) return f;   
        }
        return null;
    }

    public double calcularVentasTotales() {
        double total = 0;
        for (Factura f : facturas) {
            total += f.getTotal();          
        }
        return total;
    }
     public List<Factura> getFacturas() {
        return facturas;
    }
}
