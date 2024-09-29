package java14.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "menuitems")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "menuitem_gen")
    @SequenceGenerator(name = "menuitem_gen",
            sequenceName = "menuitem_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    private String image;
    private int price;
    private String description;
    private boolean isVegetarian;
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @ToString.Exclude
    @JsonBackReference
    private Restaurant restaurant;
    @OneToOne(mappedBy = "menuItem",cascade = {
            CascadeType.ALL
    })
    @JsonBackReference
    private StopList stopList;
    @ManyToOne
    @JsonBackReference
    private SubCategory subCategory;
}
