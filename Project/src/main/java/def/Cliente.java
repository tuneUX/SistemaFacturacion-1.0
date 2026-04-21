package def;

public class Cliente {
    private int id;
    private String nombre;
    private String telefono;
    private String codigo; 

    public Cliente(int id, String nombre, String telefono, String codigo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.codigo = codigo;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTelefono() { return telefono; }
    public String getCodigo() { return codigo; }

    // Setters opcionales si quieres modificar después
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nombre=" + nombre + 
               ", telefono=" + telefono + ", codigo=" + codigo + "]";
    }
}
