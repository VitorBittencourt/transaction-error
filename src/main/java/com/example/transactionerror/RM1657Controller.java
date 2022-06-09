package com.example.transactionerror;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.stream.IntStream;

@Slf4j
@RestController
@RequestMapping("/rm1657")
@RequiredArgsConstructor
public class RM1657Controller {
    private final RM1657Service rm1657Service;

    @PostMapping("/save/{imei}")
    public Mono<String> save(@PathVariable("imei") final String imei) {
        return rm1657Service.save(imei)
                .map(rm1657Entity -> MessageFormat.format("Saved for imei {0} with count = {1}", rm1657Entity.getImei(), rm1657Entity.getCount()))
                .doOnSuccess(log::info)
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @GetMapping("/save/with-error")
    public ResponseEntity<Object> saveError() {
        IntStream.rangeClosed(0, 3).boxed().forEach(i -> rm1657Service.save("1")
                .map(rm1657Entity -> MessageFormat.format("Saved for imei {0} with count = {1}", rm1657Entity.getImei(), rm1657Entity.getCount()))
                .doOnSuccess(log::info)
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .subscribe());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/save/error-free")
    public ResponseEntity<Object> saveSafe() {
        IntStream.rangeClosed(0, 30).boxed().forEach(i -> rm1657Service.save(i.toString())
                .map(rm1657Entity -> MessageFormat.format("Saved for imei {0} with count = {1}", rm1657Entity.getImei(), rm1657Entity.getCount()))
                .doOnSuccess(log::info)
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .subscribe());
        return ResponseEntity.accepted().build();
    }
}
