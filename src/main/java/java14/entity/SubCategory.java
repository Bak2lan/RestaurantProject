package java14.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "subcategories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "subcategory_gen")
    @SequenceGenerator(name = "subcategory_gen",
            sequenceName = "subcategory_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    private Category category;
    @OneToMany(mappedBy = "subCategory",cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    private List<MenuItem>menuItems;

}
