import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateInfoFiles {

    private static final String[] NOMBRES = {"Juan", "Alexa", "Andres", "Jimmy", "Luisa"};
    private static final String[] APELLIDOS = {"Orozco", "Cabrera", "Gallego", "Martinez", "Rodriguez"};
    private static final String[] PRODUCTOS = {"Computador", "Celular", "Tablet", "TV", "Teclado"};

    public static void main(String[] args) {
        try {
            // Genera archivos de prueba
            System.out.println("Directorio de trabajo: " + System.getProperty("user.dir"));
            createProductsFile(5); // Crea el archivo productos.txt
            createSalesManInfoFile(5); // Crea el archivo vendedores.txt y archivos de ventas para cada vendedor
            System.out.println("Archivos generados exitosamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al generar los archivos: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error de tipo de dato: " + e.getMessage());
        }
    }

    // Método para crear el archivo de productos
    public static void createProductsFile(int productsCount) throws IOException {
        FileWriter writer = new FileWriter("./productos.txt");
        Random random = new Random();
        for (int i = 0; i < productsCount; i++) {
            String idProducto = "P" + (i + 1);
            String nombreProducto = PRODUCTOS[random.nextInt(PRODUCTOS.length)];
            double precioProducto = 300000 + random.nextDouble() * 700000;
            writer.write(idProducto + ";" + nombreProducto + ";" + String.format("%.2f", precioProducto) + "\n");
        }
        writer.close();
    }

    // Método para crear el archivo de vendedores y archivos de ventas correspondientes
    public static void createSalesManInfoFile(int salesmanCount) throws IOException {
        FileWriter writer = new FileWriter("./vendedores.txt");
        Random random = new Random();
        for (int i = 0; i < salesmanCount; i++) {
            String tipoDocumento = "CC";
            long numeroDocumento = 10000000 + random.nextInt(90000000); // Genera un ID aleatorio para el vendedor
            String nombre = NOMBRES[random.nextInt(NOMBRES.length)];
            String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];
            
            // Escribe la información del vendedor en vendedores.txt
            writer.write(tipoDocumento + ";" + numeroDocumento + ";" + nombre + ";" + apellido + "\n");
            
            // Crea un archivo de ventas para este vendedor
            createSalesMenFile(10, nombre + " " + apellido, numeroDocumento);
        }
        writer.close();
    }

    // Método para crear el archivo de ventas de un vendedor específico
    public static void createSalesMenFile(int randomSalesCount, String name, long id) throws IOException {
        FileWriter writer = new FileWriter("./vendedor_" + id + ".txt");
        Random random = new Random();
        writer.write("CC;" + id + "\n"); // Escribe el encabezado del archivo de ventas
        for (int i = 0; i < randomSalesCount; i++) {
            String idProducto = "P" + (random.nextInt(PRODUCTOS.length) + 1); // ID del producto
            int cantidad = random.nextInt(10) + 1; // Cantidad entre 1 y 10
            writer.write(idProducto + ";" + cantidad + "\n");
        }
        writer.close();
    }
}
