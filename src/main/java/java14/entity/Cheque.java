package java14.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cheques")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "cheque_gen")
    @SequenceGenerator(name = "cheque_gen",
            sequenceName = "cheque_seq",
            allocationSize = 1)
    private Long id;
    private int priceAverage;
    private LocalDate createdAt;
    @ManyToOne
    @JsonManagedReference
    private User user;
    @ManyToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    @JsonManagedReference
    private List<MenuItem>menuItems;

}
