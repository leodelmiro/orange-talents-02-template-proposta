package com.leodelmiro.proposal.newproposal;

import com.leodelmiro.builders.NewProposalRequesterRequestBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProposalRequesterTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @Test
    @DisplayName("alreadyHasProposal deveria retornar verdadeiro quando documento já existir")
    void alreadyHasProposalShouldReturnTrueWhenDocumentExists() {
        List<?> list = Collections.singletonList(1);
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(list);

        ProposalRequester proposalRequester = new NewProposalRequesterRequestBuilder().defaultValues().build().toModel();
        boolean result = proposalRequester.alreadyHasProposal(entityManager);

        assertTrue(result);
    }

    @Test
    @DisplayName("alreadyHasProposal deveria retornar falso quando documento não existir")
    void alreadyHasProposalShouldReturnFalseWhenDocumentDoesNotExists() {
        List<?> list = Collections.emptyList();
        when(entityManager.createQuery(anyString())).thenReturn(query);
        when(query.getResultList()).thenReturn(list);

        ProposalRequester proposalRequester = new NewProposalRequesterRequestBuilder().defaultValues().build().toModel();
        boolean result = proposalRequester.alreadyHasProposal(entityManager);

        assertFalse(result);
    }
}
