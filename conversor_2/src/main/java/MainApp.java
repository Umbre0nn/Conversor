import java.io.IOException;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        String apiKey = "7c588b7f5215346eca389a25";
        CurrencyConverter converter = new CurrencyConverter(apiKey);
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            try {
                String baseCurrency = obtenerMoneda("Ingresa la moneda de origen (tres letras, por ejemplo, USD): ", scanner);
                String targetCurrency = obtenerMoneda("Ingresa la moneda objetivo (tres letras, por ejemplo, EUR): ", scanner);
                double amountToConvert = obtenerCantidad(scanner);

                double convertedAmount = converter.convertCurrency(baseCurrency, targetCurrency, amountToConvert);

                System.out.printf("%.2f %s equivale a %.2f %s\n", amountToConvert, baseCurrency, convertedAmount, targetCurrency);

                continuar = preguntarContinuar(scanner);
            } catch (IOException e) {
                System.out.println("Error al realizar la conversión: " + e.getMessage());
                System.out.println("Inténtalo de nuevo. Presiona cualquier tecla.");
                scanner.nextLine(); // Limpiar el buffer del scanner
            }
        }

        scanner.close();
    }

    private static String obtenerMoneda(String mensaje, Scanner scanner) {
        String moneda;
        do {
            System.out.print(mensaje);
            moneda = scanner.nextLine().trim().toUpperCase();
        } while (!moneda.matches("[a-zA-Z]{3}"));
        return moneda;
    }

    private static double obtenerCantidad(Scanner scanner) {
        double cantidad;
        do {
            System.out.print("Ingresa la cantidad a convertir: ");
            while (!scanner.hasNextDouble()) {
                System.out.println("Por favor, ingresa un número válido.");
                scanner.next(); // Limpiar el buffer del scanner
            }
            cantidad = scanner.nextDouble();
            scanner.nextLine(); // Consumir el salto de línea después de nextDouble()
        } while (cantidad <= 0);
        return cantidad;
    }

    private static boolean preguntarContinuar(Scanner scanner) {
        String respuesta;
        do {
            System.out.print("¿Deseas realizar otra conversión? (s/n): ");
            respuesta = scanner.nextLine().trim().toLowerCase();
        } while (!respuesta.equals("s") && !respuesta.equals("n"));
        return respuesta.equals("s");
    }
}
