package org.dongguk.vsa.mulmoori.dialogue.repository;

import org.dongguk.vsa.mulmoori.dialogue.domain.Dialogue;
import org.dongguk.vsa.mulmoori.dialogue.domain.type.EDialogueStatus;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogueRepository extends JpaRepository<Dialogue, Long> {

    List<Dialogue> findByUser(User user);

    @EntityGraph(attributePaths = {"user", "narooteo"})
    Optional<Dialogue> findWithUserAndNarooteoById(Long id);

    List<Dialogue> findAllByNarooteoAndStatusNotOrderByAskedAtDesc(Narooteo narooteo, EDialogueStatus status);

    List<Dialogue> findAllByUserAndNarooteoAndStatusNotOrderByAskedAtDesc(User user, Narooteo narooteo, EDialogueStatus status);
}
