package org.dongguk.vsa.mulmoori.narooteo.repository;

import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NarooteoRepository extends JpaRepository<Narooteo, Long> {

    Optional<Narooteo> findByParticipationCode(String participationCode);

    @Query("SELECT n FROM Narooteo n JOIN n.users u WHERE u.user = :user")
    List<Narooteo> findAllByUser(User user);
}
