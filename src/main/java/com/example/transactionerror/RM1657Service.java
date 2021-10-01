package com.example.transactionerror;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RM1657Service {
    private final RM1657Repository repository;

    @Transactional
    public Mono<RM1657Entity> save(String imei) {
        return repository.findByImei(imei).count().
                map(count -> RM1657Entity.builder()
                        .id(UUID.randomUUID().toString())
                        .imei(imei)
                        .count(count)
                        .build())
                .flatMap(repository::save);
    }
}
