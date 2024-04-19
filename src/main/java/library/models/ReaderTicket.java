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

    public ReaderTicket(Person person, Date startData, Date endData) {
        this.person = person;
        this.startData = startData;
        this.endData = endData;
    }

    public ReaderTicket(){

    }

    @Override
    public String toString() {
        return "ReaderTicket{" +
                "id=" + id +
                ", person=" + person +
                ", startData=" + startData +
                ", endData=" + endData +
                '}';
    }
}
