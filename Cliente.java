import java.util.ArrayList;

public class Cliente {
    private int id;
    private String nombre;
    private String telefono;
    private ArrayList<Factura> historialCompras;

    // Constructor
    public Cliente(int id, String nombre, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.historialCompras = new ArrayList<>();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    // Agregar factura al historial
    public void agregarFactura(Factura f) {
        historialCompras.add(f);
    }

    // Mostrar historial de compras
    public void mostrarHistorial() {
        System.out.println("Historial de compras de " + nombre + ":");
        if(historialCompras.isEmpty()) {
            System.out.println("No tiene compras registradas.");
        } else {
            for(Factura f : historialCompras) {
                System.out.println("- Factura ID: " + f.getId() + ", Total: " + f.calcularTotal());
            }
        }
    }
}