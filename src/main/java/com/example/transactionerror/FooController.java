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
@RequestMapping("/foo")
@RequiredArgsConstructor
public class FooController {
    private final FooService fooService;

    @PostMapping("/save/{localIdentifier}")
    public Mono<String> save(@PathVariable("localIdentifier") final String localIdentifier) {
        return fooService.save(localIdentifier)
                .map(fooEntity -> MessageFormat.format("Saved for localIdentifier {0} with count = {1}", fooEntity.getLocalIdentifier(), fooEntity.getCount()))
                .doOnSuccess(log::info)
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @GetMapping("/save/with-error")
    public ResponseEntity<Object> saveError() {
        IntStream.rangeClosed(0, 3).boxed().forEach(i -> fooService.save("1")
                .map(fooEntity -> MessageFormat.format("Saved for localIdentifier {0} with count = {1}", fooEntity.getLocalIdentifier(), fooEntity.getCount()))
                .doOnSuccess(log::info)
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .subscribe());
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/save/error-free")
    public ResponseEntity<Object> saveSafe() {
        IntStream.rangeClosed(0, 30).boxed().forEach(i -> fooService.save(i.toString())
                .map(fooEntity -> MessageFormat.format("Saved for localIdentifier {0} with count = {1}", fooEntity.getLocalIdentifier(), fooEntity.getCount()))
                .doOnSuccess(log::info)
                .doOnError(throwable -> log.error(throwable.getMessage()))
                .subscribe());
        return ResponseEntity.accepted().build();
    }
}
