package com.github.banido_pixel.restaurant_voting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "menu_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"restaurant_id", "menu_date", "name"},
                name = "uk_restaurant_menu_datetime"))
public class MenuItem extends NamedEntity {

    @Column(name = "price", nullable = false)
    @Range()
    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    @Column(name = "menu_date", nullable = false, columnDefinition = "date default now()", updatable = false)
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate menuDate = LocalDate.now();

    public MenuItem(Integer id, String name, Integer price, LocalDate menuDate) {
        super(id, name);
        this.price = price;
        this.menuDate = menuDate;
    }
}
