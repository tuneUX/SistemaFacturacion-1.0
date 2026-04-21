package def;
public class Producto {
    private int id;
    private String nombre;
    private int stock;
    private double precio;

    public Producto(int id, String nombre, int stock,double precio) {
        this.id = id;
        this.nombre = nombre;
         this.stock = stock;
        this.precio = precio;
    }


    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getStock() { return stock; }
    public double getPrecio() { return precio; }
    

    // Metodos de comportamiento
    public void actualizarPrecio(double precio) {
        this.precio = precio;
    }

    public void actualizarStock(int cantidad) {
        this.stock += cantidad;
    }

    @Override
    public String toString(){
        return "ID: " + id +
           ", Nombre: " + nombre +
           ", Precio: " + precio +
           ", Stock: " + stock;

    }
}
