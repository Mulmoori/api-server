package org.dongguk.vsa.mulmoori.narooteo.service;

import lombok.RequiredArgsConstructor;
import org.dongguk.vsa.mulmoori.core.exception.error.ErrorCode;
import org.dongguk.vsa.mulmoori.core.exception.type.CommonException;
import org.dongguk.vsa.mulmoori.narooteo.domain.Narooteo;
import org.dongguk.vsa.mulmoori.narooteo.dto.request.CreateNarooteoRequestDto;
import org.dongguk.vsa.mulmoori.narooteo.dto.response.NarooteoMiniDto;
import org.dongguk.vsa.mulmoori.narooteo.repository.NarooteoRepository;
import org.dongguk.vsa.mulmoori.narooteo.usecase.CreateNarooteoUseCase;
import org.dongguk.vsa.mulmoori.user.domain.mysql.User;
import org.dongguk.vsa.mulmoori.user.domain.mysql.UserNarooteo;
import org.dongguk.vsa.mulmoori.user.domain.type.ENarooteoRole;
import org.dongguk.vsa.mulmoori.user.repository.UserNarooteoRepository;
import org.dongguk.vsa.mulmoori.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateNarooteoService implements CreateNarooteoUseCase {

    private final UserRepository userRepository;
    private final NarooteoRepository narooteoRepository;
    private final UserNarooteoRepository userNarooteoRepository;

    @Override
    @Transactional
    public NarooteoMiniDto execute(UUID accountId, CreateNarooteoRequestDto requestDto) {
        // 1. 사용자 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        // 2. 나루터 생성
        String participationCode = generateParticipationCode();
        Narooteo narooteo = narooteoRepository.save(Narooteo.builder()
                .title(requestDto.title())
                .participationCode(participationCode)
                .build()
        );

        // 3. 권한 생성
        UserNarooteo userNarooteo = userNarooteoRepository.save(UserNarooteo.builder()
                .role(ENarooteoRole.HOST)
                .user(user)
                .narooteo(narooteo)
                .build()
        );

        // 4. 반환
        return NarooteoMiniDto.fromEntity(narooteo);
    }

    private String generateParticipationCode() {
        String uuidStr = UUID.randomUUID().toString().replace("-", "");

        byte[] uuidBytes = uuidStr.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hashBytes = digest.digest(uuidBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

        StringBuilder hashStr = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            hashStr.append(Integer.toHexString(0xff & hashBytes[i]));
        }

        return hashStr.toString().toUpperCase();
    }
}
