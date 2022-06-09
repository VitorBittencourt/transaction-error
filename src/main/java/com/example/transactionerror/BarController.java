package com.example.transactionerror;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
@RestController
@RequestMapping("/bar")
@RequiredArgsConstructor
public class BarController {
    private final BarService BarService;
    private final BarRepository barRepository;
    private final FooRepository fooRepository;

    @PostMapping("/save/{localIdentifier}")
    public Mono<String> save(@PathVariable("localIdentifier") final String localIdentifier) {
        return BarService.save(localIdentifier)
                .map(barEntityEntity -> MessageFormat.format("Saved for localIdentifier {0} with count = {1}", barEntityEntity.getLocalIdentifier(), barEntityEntity.getCount()))
                .doOnSuccess(log::info)
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @GetMapping("/find/all")
    public Flux<BarEntity> findAll() {
        return barRepository.findAll();
    }

    @GetMapping("/zip/{localIdentifier}")
    public Flux<Tuple2<FooEntity, BarEntity>> zip(@PathVariable("localIdentifier") final String localIdentifier) {
        return Flux.zip(
                fooRepository.findBylocalIdentifier(localIdentifier),
                barRepository.findBylocalIdentifier(localIdentifier)
        );
    }

    @GetMapping("/join/{localIdentifier}")
    public Flux<JoinDTO> join(@PathVariable("localIdentifier") final String localIdentifier) {
        return barRepository.findBylocalIdentifier(localIdentifier)
                .flatMap(barEntity -> fooRepository.findBylocalIdentifier(localIdentifier)
                        .map(FooEntity::getCount)
                        .collectList()
                        .map(fooCountList -> JoinDTO.builder()
                                .localIdentifier(localIdentifier)
                                .fooCountList(fooCountList)
                                .barCount(barEntity.getCount())
                                .build()));
    }

    @GetMapping("/blockjoin/{localIdentifier}")
    public Flux<JoinDTO> blockingJoin(@PathVariable("localIdentifier") final String localIdentifier) {
        return barRepository.findBylocalIdentifier(localIdentifier)
                .map(barEntity -> JoinDTO.builder()
                        .localIdentifier(localIdentifier)
                        .fooCountList(fooRepository.findBylocalIdentifier(localIdentifier)
                                .map(FooEntity::getCount)
                                .collectList()
                                .block(Duration.of(5, ChronoUnit.SECONDS)))
                        .barCount(barEntity.getCount())
                        .build());
    }
}
