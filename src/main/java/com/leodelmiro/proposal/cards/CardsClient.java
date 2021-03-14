package com.leodelmiro.proposal.cards;

import com.leodelmiro.proposal.block.CardBlockRequest;
import com.leodelmiro.proposal.block.CardBlockResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cards", url = "${cards.api}", fallback = CardsClientFallback.class)
public interface CardsClient {

    @PostMapping
    CardApiResponse getCard(CardRequest request);

    @PostMapping("/{id}/bloqueios")
    CardBlockResponse blockCard(@PathVariable String id, CardBlockRequest request);
}
