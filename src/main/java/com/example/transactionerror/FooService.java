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
public class FooService {
    private final FooRepository repository;

    @Transactional
    public Mono<FooEntity> save(String localIdentifier) {
        return repository.findBylocalIdentifier(localIdentifier).count().
                map(count -> FooEntity.builder()
                        .id(UUID.randomUUID().toString())
                        .localIdentifier(localIdentifier)
                        .count(count)
                        .build())
                .flatMap(repository::save);
    }
}
