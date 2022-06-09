package com.example.transactionerror;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import reactor.core.publisher.Flux;

public interface BarRepository extends FirestoreReactiveRepository<BarEntity> {
    Flux<BarEntity> findBylocalIdentifier(String localIdentifier);
}
