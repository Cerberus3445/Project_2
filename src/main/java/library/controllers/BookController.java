package library.controllers;

import jakarta.validation.Valid;
import library.dao.BookDAO;
import library.models.Book;
import library.models.Person;
import library.services.BookService;
import library.services.PersonService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final PersonService personService;
    private final BookService bookService;
    private final BookDAO bookDAO;

    @Autowired
    public BookController(PersonService personService, BookService bookService, BookDAO bookDAO){
        this.personService = personService;
        this.bookService = bookService;
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String BooksPage(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("searchBook", new Book());
        model.addAttribute("books", bookService.showAllBook());
        return "books/booksList";
    }

    @GetMapping("/search/{string}")
    public String theSearchLine(@RequestParam("title") String string, Model model){
        if(bookService.theSearchLine(string).isEmpty()){
            model.addAttribute("noFoundBooks");
        } else {
            model.addAttribute("foundBooks", bookService.theSearchLine(string));
        }
        return "books/search";
    }


    @GetMapping("/new")
    public String newBookPage(Model model){
        model.addAttribute("book", new Book());
        return "books/newBook";
    }

    @GetMapping("/{id}")
    public String bookPage(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person){
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        if (bookOwner.isPresent()) {
            Hibernate.initialize(bookOwner.get().getBookList());
            model.addAttribute("owner", bookOwner.get());

            if(bookDAO.checkingForOverdueBook(bookService.showOneBook(id))){
                model.addAttribute("overdue", bookService.showOneBook(id).getStartData());
            } else {
                model.addAttribute("noOverdue", bookService.showOneBook(id).getEndData());
            }
        }
        else {
            model.addAttribute("people", personService.showAllPeople());
        }

        model.addAttribute("book", bookService.showOneBook(id));
        return "books/aboutBook";
    }

    @PostMapping()
    public String createNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "books/newBook";
        bookService.createBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/update")
    public String updatePage(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookService.showOneBook(id));
        return "books/editBook";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") @Valid Book book,BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()) return "books/editBook";
        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}")
    public String appointBook(@PathVariable("id") int id, @ModelAttribute("person") Person person){
        bookDAO.appointBook(person.getId(),id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id){
        bookDAO.releaseBook(id);
        return "redirect:/books/" + id;
    }
}
