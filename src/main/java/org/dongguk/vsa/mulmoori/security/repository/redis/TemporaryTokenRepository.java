package org.dongguk.vsa.mulmoori.security.repository.redis;

import org.dongguk.vsa.mulmoori.security.domain.redis.TemporaryToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemporaryTokenRepository extends CrudRepository<TemporaryToken, String> {
}
