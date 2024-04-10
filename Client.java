
package libreria;
import java.util.ArrayList;

public class Client extends Person {
    private ArrayList<Book> borrowedBooks;

    public Client(Profile profile) {
        super(profile);
        this.borrowedBooks = new ArrayList<>();
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}






