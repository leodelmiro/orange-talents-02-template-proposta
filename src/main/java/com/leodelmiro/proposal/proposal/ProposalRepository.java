package com.leodelmiro.proposal.proposal;

import com.leodelmiro.proposal.cards.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    List<Proposal> findTop10ByStatusAndCardOrderByCreatedAtAsc(ProposalStatus status, Card card);
}
