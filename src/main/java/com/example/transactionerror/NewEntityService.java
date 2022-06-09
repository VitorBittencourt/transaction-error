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
public class NewEntityService {
    private final NewEntityRepository repository;

    @Transactional
    public Mono<NewEntity> save(String imei) {
        return repository.findByImei(imei).count().
                map(count -> NewEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .imei(imei)
                        .count(count)
                        .build())
                .flatMap(repository::save);
    }
}
