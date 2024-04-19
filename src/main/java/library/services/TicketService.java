package library.services;

import library.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final PersonService personService;

    @Autowired
    public TicketService(TicketRepository ticketRepository, PersonService personService) {
        this.ticketRepository = ticketRepository;
        this.personService = personService;
    }

    
}
