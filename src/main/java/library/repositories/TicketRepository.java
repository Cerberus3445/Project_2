package library.repositories;

import library.models.ReaderTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<ReaderTicket, Integer> {

}
