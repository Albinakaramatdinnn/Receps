package MUIT.Recept.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "food")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Food extends BaseEntity{

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "amount")
    private int amount;

    @Column(name = "weight")
    private double weight;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "description",length=512)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private Countries country;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Categories> categories;

}
