package library.controllers;

import jakarta.validation.Valid;
import library.dao.BookDAO;
import library.models.Person;
import library.services.BookService;
import library.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersonService personService;

    private final BookDAO bookDAO;

    @Autowired
    public PeopleController(PersonService personService, BookDAO bookDAO) {
        this.personService = personService;
        this.bookDAO = bookDAO;
    }

    @GetMapping
    public String showPeople(Model model){
        model.addAttribute("people", personService.showAllPeople());
        return "people/peopleList";
    }

    @GetMapping("/{id}")
    public String aboutPerson(Model model, @PathVariable("id") int id){
        if(!bookDAO.overdueBooks(id).isEmpty()){
            model.addAttribute("overdueBooks", bookDAO.overdueBooks(id));
        } else {
            model.addAttribute("noOverdueBooks", bookDAO.overdueBooks(id));
        }
        model.addAttribute("books", bookDAO.showPersonBook(id));
        model.addAttribute("person", personService.showOnePerson(id));
        return "people/aboutPerson";
    }

    @GetMapping("/new")
    public String showCreatePerson(Model model){
        model.addAttribute("person", new Person());
        return "people/newPerson";
    }

    @PostMapping()
    public String createPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "people/newPerson";
        personService.createPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/update")
    public String updatePage(Model model,@PathVariable("id") int id){
        model.addAttribute("person", personService.showOnePerson(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") @Valid Person person,BindingResult bindingResult, @PathVariable("id") int id){
        if(bindingResult.hasErrors()) return "people/edit";
        personService.updatePerson(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personService.deletePerson(id);
        return "redirect:/people";
    }

    @GetMapping("/find-people")
    public String findPeopleByFilter(@RequestParam("query") String filter, Model model){
        List<Person> personList = personService.findPeopleByFilter(filter);
        if(personList.isEmpty()){
            model.addAttribute("notFoundPeopleByFilter", filter);
        } else {
            model.addAttribute("filter", personList);
        }
        return "people/search";
    }
}
