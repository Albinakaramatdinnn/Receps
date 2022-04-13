package MUIT.Recept.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "countries")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Countries extends BaseEntity{

    @Column(name = "country_name")
    private String name;

    @Column(name = "short_code")
    private String code;

}
