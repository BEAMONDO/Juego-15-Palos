import java.util.Scanner;
public class XORPalos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Solicitar el primer número (entre 0 y 3)
        System.out.print("Número (entre 0 y 3): ");
        int num1 = scanner.nextInt();
        while (num1 < 0 || num1 > 3) {
            System.out.println("Número inválido.");
            System.out.print("Número (entre 0 y 3): ");
            num1 = scanner.nextInt();
        }
        // Solicitar el segundo número (entre 0 y 5)
        System.out.print("Número (entre 0 y 5): ");
        int num2 = scanner.nextInt();
        while (num2 < 0 || num2 > 5) {
            System.out.println("Número inválido.");
            System.out.print("Número (entre 0 y 5): ");
            num2 = scanner.nextInt();
        }
        // Solicitar el tercer número (entre 0 y 7)
        System.out.print("Número (entre 0 y 7): ");
        int num3 = scanner.nextInt();
        while (num3 < 0 || num3 > 7) {
            System.out.println("Número inválido.");
            System.out.print("Número (entre 0 y 7): ");
            num3 = scanner.nextInt();
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Si el rival deja: " + num1 + " " + num2 + " " + num3);
        // Calcular el XOR de los tres números
        int resultado = num1 ^ num2 ^ num3;
        // Mostrar el resultado del XOR
        System.out.println("El resultado del XOR es: " + resultado);
        // Si el resultado no es 0, modificar uno de los números para que el XOR sea 0
        if (resultado != 0) {
            // Intentar modificar el primer número
            int nuevoNum1 = num1 ^ resultado;
            if (nuevoNum1 < num1 && nuevoNum1 >= 0 && nuevoNum1 <= 3) {
                System.out.println("Para yo poder ganar tengo que dejarle al rival: " + nuevoNum1 + " " + num2 + " " + num3);
                return;
            }
            // Intentar modificar el segundo número
            int nuevoNum2 = num2 ^ resultado;
            if (nuevoNum2 < num2 && nuevoNum2 >= 0 && nuevoNum2 <= 5) {
                System.out.println("Para yo poder ganar tengo que dejarle al rival: " + num1 + " " + nuevoNum2 + " " + num3);
                return;
            }
            // Intentar modificar el tercer número
            int nuevoNum3 = num3 ^ resultado;
            if (nuevoNum3 < num3 && nuevoNum3 >= 0 && nuevoNum3 <= 7) {
                System.out.println("Para yo poder ganar tengo que dejarle al rival: " + num1 + " " + num2 + " " + nuevoNum3);
                return;
            }
        } else {
            // Determinar si se gana o se pierde
            System.out.println("Si el rival sabe jugar, ¡Rival Gana!");
        }
        scanner.close();
    }
}