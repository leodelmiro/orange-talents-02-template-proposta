package com.leodelmiro.proposal.proposal;

import com.leodelmiro.proposal.cards.CardsClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProposalCardAssociation {

    @Autowired
    private ProposalRepository repository;

    @Autowired
    private CardsClient cardsClient;

    private static final Logger log = LoggerFactory.getLogger(ProposalCardAssociation.class);


    @Transactional
    @Scheduled(fixedDelayString = "${timing.fixedDelay}", initialDelayString = "${timing.initialDelay}")
    public void associateCard() {

        List<Proposal> cardPendent = repository.findTop10ByStatusAndCardOrderByCreatedAtAsc(ProposalStatus.ELIGIBLE, null);

        if (!cardPendent.isEmpty()) {
            Proposal proposal = cardPendent.get(0);

            try {
                proposal.associateCard(cardsClient);
                repository.save(proposal);
                cardPendent.remove(0);

                log.info("Cartão cadastrado com sucesso!");
            } catch (Exception e) {
                log.error("Erro na api de cartões. " + e.getMessage());
            }
        }
    }
}
