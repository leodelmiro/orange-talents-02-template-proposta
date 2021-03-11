package com.leodelmiro.proposal.proposal;

import com.leodelmiro.proposal.cards.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

    @Lock(LockModeType.PESSIMISTIC_READ)
    List<Proposal> findTop10ByStatusAndCardOrderByCreatedAtAsc(ProposalStatus status, Card card);

    Boolean existsByDocument(String document);

    Optional<Proposal> findByDocument(String document);
}
