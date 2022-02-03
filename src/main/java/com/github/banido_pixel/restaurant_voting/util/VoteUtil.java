package com.github.banido_pixel.restaurant_voting.util;

import com.github.banido_pixel.restaurant_voting.model.Vote;
import com.github.banido_pixel.restaurant_voting.to.VoteTo;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.List;

@UtilityClass
public class VoteUtil {

    public static List<VoteTo> getTos(Collection<Vote> votes) {
        return votes.stream().map(VoteUtil::createTo).toList();
    }

    public static VoteTo createTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getDate(), vote.getRestaurant().id());
    }
}
