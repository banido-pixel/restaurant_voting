package ru.javaops.topjava2.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.javaops.topjava2.HasId;

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
