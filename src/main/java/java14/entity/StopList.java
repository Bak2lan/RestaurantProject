package java14.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Entity
@Table(name = "stoplists")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StopList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "stopList_gen")
    @SequenceGenerator(name = "stopList_gen",
            sequenceName = "stopList_seq",
            allocationSize = 1)
    private Long id;
    private String reason;
    private LocalDate date;
    @OneToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    private MenuItem menuItem;
}
