package org.dongguk.vsa.mulmoori.user.repository;

import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.dongguk.vsa.mulmoori.user.domain.mysql.UserNarooteo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNarooteoRepository extends JpaRepository<UserNarooteo, Long> {

    List<UserNarooteo> findByUser(User user);
}
