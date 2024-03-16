package library.services;

import jakarta.persistence.EntityManager;
import library.models.Book;
import library.models.Person;
import library.repositories.BookRepository;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> showAllBook(){
        return bookRepository.findAll();
    }

    public Book showOneBook(int id){
        return bookRepository.findById(id).orElse(null);
    }
    public List<Book> theSearchLine(String bookTitle){
        return bookRepository.findByTitleStartingWith(bookTitle);
    }
    @Transactional
    public void createBook(Book book){
        bookRepository.save(book);
    }
    @Transactional
    public void updateBook(int id, Book updateBook){
        updateBook.setId(id);
        bookRepository.save(updateBook);
    }
    @Transactional
    public void deleteBook(int id){
        bookRepository.deleteById(id);
    }
}
