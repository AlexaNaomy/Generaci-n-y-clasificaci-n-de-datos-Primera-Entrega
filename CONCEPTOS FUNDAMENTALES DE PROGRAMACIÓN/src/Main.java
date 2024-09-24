import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        try {
            // Ruta del archivo de vendedores
            String vendedoresFilePath = "./vendedores.txt";
            // Leer el archivo de vendedores y calcular las ventas
            Map<String, Double> ventasPorVendedor = calcularVentasPorVendedor(vendedoresFilePath);
            // Ordenar los vendedores por la cantidad de dinero recaudado
            List<Map.Entry<String, Double>> vendedoresOrdenados = ordenarVendedoresPorVentas(ventasPorVendedor);
            // Crear el reporte ordenado
            crearReporteVendedores(vendedoresOrdenados);
            System.out.println("El reporte de vendedores se generó exitosamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al generar el reporte: " + e.getMessage());
        }
    }

    // Método para leer el archivo de vendedores y calcular las ventas totales de cada uno
    public static Map<String, Double> calcularVentasPorVendedor(String vendedoresFilePath) throws IOException {
        Map<String, Double> ventasPorVendedor = new HashMap<>();
        List<String> vendedores = Files.readAllLines(Paths.get(vendedoresFilePath));

        // Leer cada línea del archivo vendedores.txt
        for (String linea : vendedores) {
            String[] datosVendedor = linea.split(";");
            String nombreCompleto = datosVendedor[2] + " " + datosVendedor[3]; // Nombre y Apellido
            long idVendedor = Long.parseLong(datosVendedor[1]); // ID del vendedor

            // Calcular el total de ventas del vendedor leyendo su archivo de ventas
            double totalVentas = calcularTotalVentasVendedor(idVendedor);
            ventasPorVendedor.put(nombreCompleto, totalVentas);
        }

        return ventasPorVendedor;
    }

    // Método para calcular el total de ventas de un vendedor a partir de su archivo de ventas
    public static double calcularTotalVentasVendedor(long idVendedor) throws IOException {
        String ventasFilePath = "./vendedor_" + idVendedor + ".txt";
        List<String> ventas = Files.readAllLines(Paths.get(ventasFilePath));
        double totalVentas = 0.0;

        // Leer cada línea del archivo de ventas del vendedor (omitir la primera línea que contiene el ID)
        for (int i = 1; i < ventas.size(); i++) {
            String[] datosVenta = ventas.get(i).split(";");
            String idProducto = datosVenta[0];
            int cantidadVendida = Integer.parseInt(datosVenta[1]);
            double precioProducto = obtenerPrecioProducto(idProducto);
            totalVentas += cantidadVendida * precioProducto; // Sumar el total por producto vendido
        }

        return totalVentas;
    }

    // Método para obtener el precio de un producto a partir del archivo productos.txt
    public static double obtenerPrecioProducto(String idProducto) throws IOException {
        List<String> productos = Files.readAllLines(Paths.get("./productos.txt"));

        for (String producto : productos) {
            String[] datosProducto = producto.split(";");
            if (datosProducto[0].equals(idProducto)) {
                return Double.parseDouble(datosProducto[2].replace(",", "")); // Precio del producto
            }
        }

        return 0.0; // En caso de que no se encuentre el producto
    }

    // Método para ordenar los vendedores por el total de ventas, de mayor a menor
    public static List<Map.Entry<String, Double>> ordenarVendedoresPorVentas(Map<String, Double> ventasPorVendedor) {
        List<Map.Entry<String, Double>> listaOrdenada = new ArrayList<>(ventasPorVendedor.entrySet());
        listaOrdenada.sort((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue())); // Ordenar de mayor a menor
        return listaOrdenada;
    }

    // Método para crear el archivo de reporte de vendedores
    public static void crearReporteVendedores(List<Map.Entry<String, Double>> vendedoresOrdenados) throws IOException {
        FileWriter writer = new FileWriter("./reporte_vendedores.csv");

        // Escribir los datos en el archivo CSV (Nombre;TotalVentas)
        for (Map.Entry<String, Double> entry : vendedoresOrdenados) {
            writer.write(entry.getKey() + ";" + String.format("%.2f", entry.getValue()) + "\n");
        }

        writer.close();
    }
}

