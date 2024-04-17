package library.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Ticket")
public class ReaderTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Column(name = "start_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startData;

    @Column(name = "end_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endData;
}
