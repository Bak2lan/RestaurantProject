package java14.entity;

import java14.enums.RestType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "rest_gen")
    @SequenceGenerator(name = "rest_gen",
            sequenceName = "rest_seq",
            allocationSize = 1)
    private Long id;
    private String name;
    private String location;
    private RestType restType;
    private int numberOfEmployees;
    private final double service=10;
    @OneToMany(mappedBy = "restaurant",cascade = {
            CascadeType.ALL
    })
    @ToString.Exclude
    private List<MenuItem>menuItems;
    @OneToMany(mappedBy = "restaurant",cascade = {
            CascadeType.ALL
    })
    @ToString.Exclude
    private List<User>users=new ArrayList<>();

}
