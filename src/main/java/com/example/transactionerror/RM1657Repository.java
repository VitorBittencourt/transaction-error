package com.example.transactionerror;

import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;
import reactor.core.publisher.Flux;

public interface RM1657Repository extends FirestoreReactiveRepository<RM1657Entity> {
    Flux<RM1657Entity> findByImei(String imei);
}
