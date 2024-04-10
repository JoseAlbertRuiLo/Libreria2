package libreria;
import java.util.ArrayList;

public class LibraryController {

    public static void borrowBook() {
        String[] nameAndLastName = ConsoleReader.readNameAndLastName("");
        String clientName = nameAndLastName[0];
        String clientLastName = nameAndLastName[1];
        Client client = ClientRepository.getClientByName(clientName, clientLastName);

        if (client != null) {
            String isbn = ConsoleReader.readString("Ingrese el ISBN del libro: ");
            Book book = BookRepository.getBookByISBN(isbn);

            if (book != null && book.isAvailable()) {
                client.borrowBook(book);
                book.setAvailable(false);
                TransactionRepository.addTransaction(new Transaction("Prestado", client, book));
                System.out.println("Libro prestado.");
            } else {
                System.out.println("El libro no está disponible para prestamo.");
            }
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    public static void returnBook() {
        String[] nameAndLastName = ConsoleReader.readNameAndLastName("");
        String clientName = nameAndLastName[0];
        String clientLastName = nameAndLastName[1];
        Client client = ClientRepository.getClientByName(clientName, clientLastName);

        if (client != null && !client.getBorrowedBooks().isEmpty()) {
            String isbn = ConsoleReader.readString("Ingrese el ISBN del libro : ");
            Book book = BookRepository.getBookByISBN(isbn);

            if (book != null && client.getBorrowedBooks().contains(book)) {
                client.returnBook(book);
                book.setAvailable(true);
                TransactionRepository.addTransaction(new Transaction("Return", client, book));
                System.out.println("Libro devuelto exitosamente.");
            } else {
                System.out.println("El cliente no tiene este libro prestado.");
            }
        } else {
            System.out.println("Cliente no encontente no tiene libros prestados.");
        }
    }

    public static void displayAllBooks() {
        ArrayList<Book> books = BookRepository.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No hay libros disponibles.");
        } else {
            System.out.printf("%-15s%-40s%-30s%-15s%n", "ISBN", "Título", "Autor", "Disponible");
            for (Book book : books) {
                System.out.printf("%-15s%-40s%-30s%-15s%n", book.getIsbn(), book.getTitle(),
                        getAuthorName(book.getAuthor()), book.isAvailable());
            }
        }
    }

    public static void displayAllClients() {
        ArrayList<Client> clients = ClientRepository.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            System.out.printf("%-20s%-20s%-20s%n", "Nombre", "Apellido", "Libros Prestados");
            for (Client client : clients) {
                System.out.printf("%-20s%-20s%-20s%n", client.getProfile().getName(),
                        client.getProfile().getLastName(), client.getBorrowedBooks().size());
            }
        }
    }

    public static void displayAllAuthors() {
        ArrayList<Author> authors = AuthorRepository.getAllAuthors();
        if (authors.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            System.out.printf("%-20s%-20s%n", "Nombre", "Apellido");
            for (Author author : authors) {
                System.out.printf("%-20s%-20s%n", author.getProfile().getName(), author.getProfile().getLastName());
            }
        }
    }

    public static void displayAllTransactions() {
        ArrayList<Transaction> transactions = TransactionRepository.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No hay transacciones.");
        } else {
            System.out.printf("%-20s%-40s%-20s%-20s%n", "Cliente", "Libro", "Tipo", "Fecha");
            for (Transaction transaction : transactions) {
                System.out.printf("%-20s%-40s%-20s%-20s%n",
                        getClientName(transaction.getClient()), getBookTitle(transaction.getBook()),
                        transaction.getType(), transaction.getDate());
            }
        }
    }

    private static String getAuthorName(Author author) {
        if (author != null && author.getProfile() != null) {
            return author.getProfile().getName() + " " + author.getProfile().getLastName();
        } else {
            return "Autor Desconocido";
        }
    }

    private static String getBookTitle(Book book) {
        return (book != null) ? book.getTitle() : "Libro Desconocido";
    }

    private static String getClientName(Client client) {
        return (client != null && client.getProfile() != null)
                ? client.getProfile().getName() + " " + client.getProfile().getLastName()
                : "Cliente Desconocido";
    }

    public static void loginAsAdministrator(String username, String password) {
        Administrator admin = AdministratorRepository.getAdministratorByUsername(username);
        if (admin != null && admin.getPassword().equals(password)) {
            System.out.println("Inicio de sesión como Administrador exitoso.");
            displayAdminMenu(admin);
        } else {
            System.out.println("Nombre de usuario o contraseña incorrectos.");
        }
    }

    public static void loginAsClient(String username, String password) {
        Client client = ClientRepository.getClientByUsername(username);
        if (client != null && client.getPassword().equals(password)) {
            System.out.println("Inicio de sesión como Cliente exitoso.");
            displayClientMenu(client);
        } else {
            System.out.println("Nombre de usuario o contraseña incorrectos.");
        }
    }

    private static void displayAdminMenu(Administrator admin) {
        System.out.println("Menu del Administrador:");
        System.out.println("1. Ver todos los libros");
        System.out.println("2. Agregar un libro");
        System.out.println("3. Eliminar un libro");
        System.out.println("4. Ver todos los autores");
        System.out.println("5. Agregar un autor");
        System.out.println("6. Eliminar un autor");
        System.out.println("7. Ver todas las transacciones");
        int option = ConsoleReader.readInt("Seleccione una opción: ");
        switch (option) {
            case 1:
                displayAllBooks();
                break;
            case 2:
                addBookFromAdmin();
                break;
            case 3:
                deleteBookFromAdmin();
                break;
            case 4:
                displayAllAuthors();
                break;
            case 5:
                addAuthorFromAdmin();
                break;
            case 6:
                deleteAuthorFromAdmin();
                break;
            case 7:
                displayAllTransactions();
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void displayClientMenu(Client client) {
        System.out.println("Menu del Cliente:");
        System.out.println("1. Ver mis libros prestados");
        int option = ConsoleReader.readInt("Seleccione una opción: ");
        switch (option) {
            case 1:
                displayClientBooks(client);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void displayClientBooks(Client client) {
        ArrayList<Book> borrowedBooks = client.getBorrowedBooks();
        if (borrowedBooks.isEmpty()) {
            System.out.println("No tienes libros prestados.");
        } else {
            System.out.println("Tus libros prestados:");
            System.out.printf("%-15s%-40s%-30s%n", "ISBN", "Título", "Autor");
            for (Book book : borrowedBooks) {
                System.out.printf("%-15s%-40s%-30s%n", book.getIsbn(), book.getTitle(),
                        getAuthorName(book.getAuthor()));
            }
        }
    }

    private static void addBookFromAdmin() {
    String isbn = ConsoleReader.readString("Ingrese el ISBN del libro: ");
    String title = ConsoleReader.readString("Ingrese el título del libro: ");
    String authorName = ConsoleReader.readString("Ingrese el nombre del autor del libro: ");
    String authorLastName = ConsoleReader.readString("Ingrese el apellido del autor del libro: ");
    Author author = AuthorRepository.getAuthorByName(authorName, authorLastName);
    if (author != null) {
        Date publishDate = new Date(); 
        boolean isAvailable = true;
        Book newBook = new Book(isbn, title, author, publishDate, isAvailable);
        LibraryController.addBook(newBook);
    } else {
        System.out.println("No se encontró al autor. Por favor, agregue al autor primero.");
    }
}

private static void deleteBookFromAdmin() {
    String isbn = ConsoleReader.readString("Ingrese el ISBN del libro que desea eliminar: ");
    LibraryController.deleteBook(isbn);
}

private static void addAuthorFromAdmin() {
    String name = ConsoleReader.readString("Ingrese el nombre del autor: ");
    String lastName = ConsoleReader.readString("Ingrese el apellido del autor: ");
    Profile authorProfile = new Profile(name, lastName, new Date()); 
    Author newAuthor = new Author(authorProfile);
    AuthorRepository.addAuthor(newAuthor);
}

private static void deleteAuthorFromAdmin() {
    String name = ConsoleReader.readString("Ingrese el nombre del autor que desea eliminar: ");
    String lastName = ConsoleReader.readString("Ingrese el apellido del autor que desea eliminar: ");
    LibraryController.deleteAuthor(name, lastName);
}
}