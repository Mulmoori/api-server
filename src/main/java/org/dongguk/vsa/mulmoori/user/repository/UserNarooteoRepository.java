package org.dongguk.vsa.mulmoori.user.repository;

import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.dongguk.vsa.mulmoori.user.domain.mysql.UserNarooteo;
import org.dongguk.vsa.mulmoori.user.domain.type.ENarooteoRole;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserNarooteoRepository extends JpaRepository<UserNarooteo, Long> {

    @EntityGraph(attributePaths = {"user"})
    Optional<UserNarooteo> findByNarooteoAndRole(Narooteo narooteo, ENarooteoRole role);

    List<UserNarooteo> findByUser(User user);


}
