package com.github.banido_pixel.restaurant_voting.model;

import com.github.banido_pixel.restaurant_voting.HasId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "restaurants",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name"}, name = "uk_restaurant_name"))
@ToString(callSuper = true)
public class Restaurant extends NamedEntity implements HasId {

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
