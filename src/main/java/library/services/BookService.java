package library.services;

import library.models.Book;
import library.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    public List<Book> showAll(){
        return bookRepository.findAll();
    }

    public List<Book> showAllBookWithPagination(int page){
        return bookRepository.findAll(PageRequest.of(page,1)).getContent();
    }

    public List<Book> findByYear(){
        return bookRepository.findAll(Sort.by("year"));
    }


    public Book showOneBook(int id){
        return bookRepository.findById(id).orElse(null);
    }

    public List<Book> theSearchLine(String bookTitle){
        return bookRepository.findByTitleIgnoreCaseStartingWith(bookTitle);
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
