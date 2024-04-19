package library.dao;
import jakarta.persistence.EntityManager;
import library.models.Book;
import library.models.Person;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class BookDAO {
    private final EntityManager entityManager;

    @Autowired
    public BookDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void appointBook(int personId, int bookId){
        Session session = entityManager.unwrap(Session.class);
        Book book1 = session.get(Book.class, bookId);
        book1.setStartData(new Date());
        book1.setEndData(endData(book1.getStartData()));
        Person person = session.get(Person.class, personId);
        book1.setPerson(person);
        person.getBookList().add(book1);
    }

    public void releaseBook(int book_id){
        Session session = entityManager.unwrap(Session.class);
        Book book = session.get(Book.class, book_id);
        book.setStartData(null);
        book.setEndData(null);
        Person person = session.get(Person.class, book.getPerson().getId());
        person.getBookList().remove(book);
        book.setPerson(null);
    }


    public Optional<Person> getBookOwner(int id) {
        Session session = entityManager.unwrap(Session.class);
        Book book = session.createQuery("from Book b where b.id=:bookId", Book.class).setParameter("bookId", id).getSingleResult();
        return Optional.ofNullable(book.getPerson());
    }


    public List<Book> showPersonBook(int id){
        Session session = entityManager.unwrap(Session.class);
        List<Book> bookList = session.createQuery("select p.bookList from Person p where p.id=:personId", Book.class).setParameter("personId",id).getResultList();
        return bookList;
    }

    public Date endData(Date date){
        int year = date.getYear();
        int month = date.getMonth();
        int dat = date.getDate();
        int hours = date.getHours();
        int min = date.getMinutes() + 10;
        int sec = date.getSeconds();
        Date date1 = new Date(year, month, dat, hours, min, sec);
        return date1;
    }


    public boolean checkingForOverdueBook(Book book){
        Date date = new Date();
        if(date.after(book.getEndData())){
            return true;
        } else {
            return false;
        }
    }


    public List<Book> overdueBooks(int id){
        List<Book> overdueBooks = new ArrayList<>();
        Date date = new Date();
        Session session = entityManager.unwrap(Session.class);
        Person person = session.get(Person.class, id);
        for(Book book : person.getBookList()){
            if(date.after(book.getEndData())){
                overdueBooks.add(book);
            }
        }
        return overdueBooks;
    }
}
