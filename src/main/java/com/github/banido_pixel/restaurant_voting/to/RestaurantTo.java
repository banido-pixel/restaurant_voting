package com.github.banido_pixel.restaurant_voting.to;

import com.github.banido_pixel.restaurant_voting.HasId;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends NamedTo implements HasId {

    Integer votesAmount;

    public RestaurantTo(Integer id, String name, Integer votesAmount) {
        super(id, name);
        this.votesAmount = votesAmount;
    }

    public RestaurantTo(Integer id, String name, Long votesAmount) {
        this(id,name,votesAmount.intValue());
    }
}
