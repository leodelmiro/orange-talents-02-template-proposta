package com.leodelmiro.proposal.cards;

import com.leodelmiro.proposal.block.CardBlockRequest;
import com.leodelmiro.proposal.block.CardBlockResponse;
import com.leodelmiro.proposal.travelnotice.TravelNoticesApiRequest;
import com.leodelmiro.proposal.travelnotice.TravelNoticesApiResponse;
import com.leodelmiro.proposal.wallet.WalletRequest;
import com.leodelmiro.proposal.wallet.WalletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "cards", url = "${cards.api}")
public interface CardsClient {

    @PostMapping
    CardApiResponse getCard(CardRequest request);

    @PostMapping("/{id}/bloqueios")
    CardBlockResponse blockCard(@PathVariable String id, CardBlockRequest request);

    @PostMapping("/{id}/avisos")
    TravelNoticesApiResponse notices(@PathVariable String id, TravelNoticesApiRequest request);

    @PostMapping("/{id}/carteiras")
    WalletResponse walletAssociation(@PathVariable String id, WalletRequest request);
}
