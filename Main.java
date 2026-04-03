import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Cliente> clientes = new ArrayList<>();
    static ArrayList<Producto> productos = new ArrayList<>();
    static int idFactura = 1;

    public static void main(String[] args) {
        int opcion;
        do {
            System.out.println("\n=== SISTEMA DE FACTURACION E INVENTARIO ===");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Agregar producto");
            System.out.println("3. Generar factura");
            System.out.println("4. Ver historial de cliente");
            System.out.println("5. Ver inventario");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch(opcion) {
                case 1 -> agregarCliente();
                case 2 -> agregarProducto();
                case 3 -> generarFactura();
                case 4 -> verHistorialCliente();
                case 5 -> verInventario();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opción inválida.");
            }
        } while(opcion != 0);
    }

    // ===== CLIENTES =====
    public static void agregarCliente() {
        System.out.print("ID cliente: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();

        clientes.add(new Cliente(id, nombre, telefono));
        System.out.println("Cliente agregado correctamente.");
    }

    public static Cliente buscarCliente(int id) {
        for(Cliente c : clientes) {
            if(c.getId() == id) return c;
        }
        return null;
    }

    // ===== PRODUCTOS =====
    public static void agregarProducto() {
        System.out.print("ID producto: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("Precio: ");
        double precio = sc.nextDouble();
        System.out.print("Stock: ");
        int stock = sc.nextInt();
        sc.nextLine();

        productos.add(new Producto(id, nombre, precio, stock));
        System.out.println("Producto agregado correctamente.");
    }

    public static Producto buscarProducto(int id) {
        for(Producto p : productos) {
            if(p.getId() == id) return p;
        }
        return null;
    }

    // ===== FACTURA MEJORADA =====
    public static void generarFactura() {
        System.out.print("ID del cliente: ");
        int idCliente = sc.nextInt();
        sc.nextLine();

        Cliente cliente = buscarCliente(idCliente);
        if(cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        Factura factura = new Factura(idFactura++, cliente);

        String opcion = "";
        double total = 0;

        do {
            System.out.print("ID producto a comprar: ");
            int idProd = sc.nextInt();
            sc.nextLine();

            Producto prod = buscarProducto(idProd);
            if(prod == null) {
                System.out.println("Producto no encontrado.");
                continue;
            }

            System.out.print("Cantidad: ");
            int cantidad = sc.nextInt();
            sc.nextLine();

            if(cantidad > prod.getStock()) {
                System.out.println("Stock insuficiente. Disponible: " + prod.getStock());
                continue;
            }

            DetalleFactura detalle = new DetalleFactura(prod, cantidad);
            factura.agregarDetalle(detalle);

            double subtotal = detalle.calcularSubtotal();
            total += subtotal;

            System.out.println("\nProducto: " + prod.getNombre());
            System.out.println("Precio unitario: " + prod.getPrecio());
            System.out.println("Cantidad: " + cantidad);
            System.out.println("Subtotal: " + subtotal);

            System.out.print("\n¿Agregar otro producto? (s/n): ");
            opcion = sc.nextLine();

        } while(opcion.equalsIgnoreCase("s"));

        // ===== CONFIRMAR =====
        System.out.println("\nTOTAL A PAGAR: " + total);
        System.out.print("¿Confirmar compra? (s/n): ");
        String confirmar = sc.nextLine();

        if(confirmar.equalsIgnoreCase("s")) {

            // Descontar stock
            for(DetalleFactura d : factura.getDetalles()) {
                d.getProducto().actualizarStock(d.getCantidad());
            }

            System.out.println("\n=== COMPRA CONFIRMADA ===");
            factura.mostrarFactura();

        } else {
            System.out.println("Compra cancelada.");
        }
    }

    // ===== HISTORIAL =====
    public static void verHistorialCliente() {
        System.out.print("ID cliente: ");
        int idCliente = sc.nextInt();
        sc.nextLine();

        Cliente cliente = buscarCliente(idCliente);
        if(cliente != null) {
            cliente.mostrarHistorial();
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    // ===== INVENTARIO =====
    public static void verInventario() {
        System.out.println("=== INVENTARIO ===");
        for(Producto p : productos) {
            System.out.println("ID: " + p.getId() +
                               ", Nombre: " + p.getNombre() +
                               ", Precio: " + p.getPrecio() +
                               ", Stock: " + p.getStock());
        }
    }
}