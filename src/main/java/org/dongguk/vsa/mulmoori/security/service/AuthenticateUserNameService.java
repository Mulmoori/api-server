package org.dongguk.vsa.mulmoori.security.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.security.domain.mysql.Account;
import org.dongguk.vsa.mulmoori.security.domain.type.ESecurityProvider;
import org.dongguk.vsa.mulmoori.security.dto.info.CustomUserPrincipal;
import org.dongguk.vsa.mulmoori.security.repository.mysql.AccountRepository;
import org.dongguk.vsa.mulmoori.security.usecase.AuthenticateUserNameUseCase;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateUserNameService implements AuthenticateUserNameUseCase {

    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String serialId) throws UsernameNotFoundException {
        Account account = accountRepository.findBySerialIdAndProvider(serialId, ESecurityProvider.DEFAULT)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with serialId: " + serialId));

        return CustomUserPrincipal.create(account);
    }
}
