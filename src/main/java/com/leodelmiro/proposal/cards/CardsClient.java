package com.leodelmiro.proposal.cards;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cards", url = "${cards.api}")
public interface CardsClient {

    @PostMapping
    CardResponse getCard(CardRequest request);
}
