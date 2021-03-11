package com.leodelmiro.proposal.cards;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cards", url = "${cards.api}", fallback = FeignClientFallback.class)
public interface CardsClient {

    @PostMapping
    CardApiResponse getCard(CardRequest request);
}
