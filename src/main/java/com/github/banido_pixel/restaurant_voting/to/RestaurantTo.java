package com.github.banido_pixel.restaurant_voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends NamedTo {

    Long votesAmount;

    public RestaurantTo(Integer id, String name, Long votesAmount) {
        super(id, name);
        this.votesAmount = votesAmount;
    }
}
