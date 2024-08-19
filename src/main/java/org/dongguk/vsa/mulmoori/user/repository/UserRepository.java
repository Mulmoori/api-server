package org.dongguk.vsa.mulmoori.user.repository;

import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}
