package ru.javaops.topjava2.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.javaops.topjava2.HasId;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "restaurants",
        uniqueConstraints =@UniqueConstraint(columnNames = {"name"}, name = "uk_restaurant_name"))
@ToString(callSuper = true)
public class Restaurant extends NamedEntity implements HasId {

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
