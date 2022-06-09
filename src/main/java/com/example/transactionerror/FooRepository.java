package com.example.transactionerror;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import reactor.core.publisher.Flux;

public interface FooRepository extends FirestoreReactiveRepository<FooEntity> {
    Flux<FooEntity> findBylocalIdentifier(String localIdentifier);
}
