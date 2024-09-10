package com.example.util;

import com.example.model.Vote;
import com.example.to.VoteTo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class VoteUtil {
    public static VoteTo createNewFromVote(Vote vote) {
        return new VoteTo(vote.getId(), vote.getCreated(), vote.getRestaurant().getId());
    }
}
