import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateInfoFiles { //Se establecen las variables que se van a usar 

    private static final String[] NOMBRES = {"Juan", "Alexa", "Andes", "Jimmy", "Luisa"};
    private static final String[] APELLIDOS = {"Orozco", "Cabrera", "Gallego", "Martinez", "Rodriguez"};
    private static final String[] PRODUCTOS = {"Computador", "Celular", "Tablet", "TV", "Teclado"};

    public static void main(String[] args) {
        try {
          
            System.out.println("Directorio de trabajo: " + System.getProperty("user.dir"));   // Mensaje que indica en dónde se están guardando los archivos planos
            
            // Se dan indicaciones de los datos que debe tener cada archivo
            createProductsFile(5);
            createSalesManInfoFile(5);
            createSalesMenFile(10, "Jimmy Cabrera", -80613980);
            System.out.println("El archivo se generó exitosamente."); //Mensaje de éxito en la generación de los archivos planos
            
        } catch (IOException e) {
            System.out.println("Ocurrió un error al generar los archivos: " + e.getMessage()); // Mensaje de error al generar los archivos
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error de tipo de dato: " + e.getMessage()); // Mensaje de error por tipo de dato (ID del vendedor no puede ser negativo o 0)
        }
    }

    // Método para crear el archivo de productos
    public static void createProductsFile(int productsCount) throws IOException {
        FileWriter writer = new FileWriter("./productos.txt"); 
        Random random = new Random(); // Genera números aleatorios para los precios y para seleccionar un producto aleatorio de la lista "PRODUCTOS"

        for (int i = 0; i < productsCount; i++) {
            String idProducto = "P" + (i + 1); // Se establece que el ID del producto debe empezar con una P, por cada iteración aumenta i en 1
            String nombreProducto = PRODUCTOS[random.nextInt(PRODUCTOS.length)]; //Genera el nombre aleatorio para el producto
            double precioProducto = 300000 + random.nextDouble() * 700000; // Se establecen los precios del producto entre $300.000 y $1'000.000
            writer.write(idProducto + ";" + nombreProducto + ";" + String.format("%.2f", precioProducto) + "\n");
        }

        writer.close();
    }

    // Método para crear el archivo de información de los vendedores
    public static void createSalesManInfoFile(int salesmanCount) throws IOException {
        FileWriter writer = new FileWriter("./vendedores.txt"); 
        Random random = new Random();

        for (int i = 0; i < salesmanCount; i++) {
            String tipoDocumento = "CC"; // Tipo de documento
            long numeroDocumento = 10000000 + random.nextInt(90000000); // Número aleatorio para el ID de cada vendedor
            String nombre = NOMBRES[random.nextInt(NOMBRES.length)];
            String apellido = APELLIDOS[random.nextInt(APELLIDOS.length)];
            writer.write(tipoDocumento + ";" + numeroDocumento + ";" + nombre + ";" + apellido + "\n");
        }

        writer.close();
    }

    // Método para crear el archivo de ventas de un vendedor
    public static void createSalesMenFile(int randomSalesCount, String name, long id) throws IOException {
    	
    	if (id <= 0) {
            throw new IllegalArgumentException("ID del vendedor no puede ser negativo o cero: " + id); // Se establece la excepción para el ID del vendedor.
          
    	}
            
        FileWriter writer = new FileWriter("./vendedor_" + id + ".txt"); 
        Random random = new Random();

        // Escribir el encabezado: TipoDocumentoVendedor;NúmeroDocumentoVendedor
        writer.write("CC;" + id + "\n");

        // Generar ventas aleatorias
        for (int i = 0; i < randomSalesCount; i++) {
            String idProducto = "P" + (random.nextInt(PRODUCTOS.length) + 1); // ID del producto
            int cantidad = random.nextInt(10) + 1; // Cantidad entre 1 y 10
            writer.write(idProducto + ";" + cantidad + "\n");
        }

        writer.close();
    }
}

