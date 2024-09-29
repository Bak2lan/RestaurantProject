package java14.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
    generator = "category_gen")
    @SequenceGenerator(name = "category_gen",
    sequenceName = "category_seq",
    allocationSize = 1)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    private List<SubCategory>subCategories;

}
