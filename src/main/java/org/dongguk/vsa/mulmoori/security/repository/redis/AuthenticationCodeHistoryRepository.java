package org.dongguk.vsa.mulmoori.security.repository.redis;

import org.dongguk.vsa.mulmoori.security.domain.redis.AuthenticationCodeHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationCodeHistoryRepository extends CrudRepository<AuthenticationCodeHistory, String> {
}
