package com.leodelmiro.proposal.proposal;

import com.leodelmiro.proposal.cards.CardsClient;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProposalCardAssociation {

    @Autowired
    private ProposalRepository repository;

    @Autowired
    private CardsClient cardsClient;

    private static final Logger log = LoggerFactory.getLogger(ProposalCardAssociation.class);

    @Transactional
    @Scheduled(fixedDelayString = "${timing.fixedDelay}", initialDelayString = "${timing.initialDelay}")
    public void associateCard() {

        List<Proposal> pendentsCard = repository.findTop10ByStatusAndCardOrderByCreatedAtAsc(ProposalStatus.ELIGIBLE, null);

        if (!pendentsCard.isEmpty()) {
            Proposal proposal = pendentsCard.get(0);

            try {
                proposal.associateCard(cardsClient);
                repository.save(proposal);
                pendentsCard.remove(0);
                log.info("Cartão cadastrado com sucesso!");
            } catch (HystrixRuntimeException e) {
                log.error("Erro na Api de cartões!");
            }
        }
    }
}
