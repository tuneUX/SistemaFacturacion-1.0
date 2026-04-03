public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int stock;

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    // Método para actualizar stock
    public void actualizarStock(int cantidadVendida) {
        if(cantidadVendida <= stock) {
            stock -= cantidadVendida;
        } else {
            System.out.println("Stock insuficiente para " + nombre);
        }
    }
}