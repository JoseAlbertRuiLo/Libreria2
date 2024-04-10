package libreria;
public class MainController {
    public static void main(String[] args) {
        Seeder.initialize();
        runLibraryManagementSystem();
    }

    public static void runLibraryManagementSystem() {
        int option;
        do {
            displayMainMenu();
            option = ConsoleReader.readInt("Seleccione una opción: ");
            switch (option) {
                case 1:
                    loginAsAdministrator();
                    break;
                case 2:
                    loginAsClient();
                    break;
                case 3:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        } while (option != 3);
    }

    private static void displayMainMenu() {
        System.out.println("1. Iniciar como Administrador");
        System.out.println("2. Iniciar como Cliente");
        System.out.println("3. Salir del programa");
    }

    private static void loginAsAdministrator() {
        String username = ConsoleReader.readString("Ingrese su nombre de usuario: ");
        String password = ConsoleReader.readString("Ingrese su contraseña: ");
        LibraryController.loginAsAdministrator(username, password);
    }

    private static void loginAsClient() {
        String username = ConsoleReader.readString("Ingrese su nombre de usuario: ");
        String password = ConsoleReader.readString("Ingrese su contraseña: ");
        LibraryController.loginAsClient(username, password);
    }
}

