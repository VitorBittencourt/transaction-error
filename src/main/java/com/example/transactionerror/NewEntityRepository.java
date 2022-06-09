package com.example.transactionerror;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import reactor.core.publisher.Flux;

public interface NewEntityRepository extends FirestoreReactiveRepository<NewEntity> {
    Flux<NewEntity> findByImei(String imei);
}
