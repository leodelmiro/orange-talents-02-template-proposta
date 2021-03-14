package com.leodelmiro.proposal.proposal;

import com.leodelmiro.proposal.builders.ProposalBuilder;
import com.leodelmiro.proposal.cards.CardApiResponse;
import com.leodelmiro.proposal.cards.CardsClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ProposalCardAssociationTest {

    @InjectMocks
    private ProposalCardAssociation proposalCardAssociation;

    @Mock
    private ProposalRepository repository;

    @Mock
    private CardsClient cardsClient;

    @Test
    void shouldAddCardToEligibleProposal() {
        Proposal eligibleProposal = new ProposalBuilder().defaultValues().build();
        ReflectionTestUtils.setField(eligibleProposal, "id", 1L);
        List<Proposal> list = new ArrayList<>();
        list.add(eligibleProposal);

        Mockito.when(repository.findTop10ByStatusAndCardOrderByCreatedAtAsc(ProposalStatus.ELIGIBLE, null))
                .thenReturn(list);

        Mockito.when(cardsClient.getCard(any()))
                .thenReturn(new CardApiResponse("5555-5555-5555-5555", LocalDateTime.now(), eligibleProposal.getName(), BigDecimal.TEN, "1"));

        proposalCardAssociation.associateCard();

        Assertions.assertNotNull(eligibleProposal.getCard());
    }
}
