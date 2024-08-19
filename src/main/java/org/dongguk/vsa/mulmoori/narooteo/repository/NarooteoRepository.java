package org.dongguk.vsa.mulmoori.narooteo.repository;

import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NarooteoRepository extends JpaRepository<Narooteo, Long> {

    Optional<Narooteo> findByParticipationCode(String participationCode);
}
