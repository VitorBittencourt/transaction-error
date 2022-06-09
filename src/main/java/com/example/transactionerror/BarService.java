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
public class BarService {
    private final BarRepository repository;

    @Transactional
    public Mono<BarEntity> save(String localIdentifier) {
        return repository.findBylocalIdentifier(localIdentifier).count().
                map(count -> BarEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .localIdentifier(localIdentifier)
                        .count(count)
                        .build())
                .flatMap(repository::save);
    }
}
