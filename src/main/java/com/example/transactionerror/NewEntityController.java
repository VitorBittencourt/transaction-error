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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/NewEntity")
@RequiredArgsConstructor
public class NewEntityController {
    private final NewEntityService NewEntityService;
    private final NewEntityRepository newEntityRepository;
    private final RM1657Repository rm1657Repository;

    @PostMapping("/save/{imei}")
    public Mono<String> save(@PathVariable("imei") final String imei) {
        return NewEntityService.save(imei)
                .map(NewEntityEntity -> MessageFormat.format("Saved for imei {0} with count = {1}", NewEntityEntity.getImei(), NewEntityEntity.getCount()))
                .doOnSuccess(log::info)
                .doOnError(throwable -> log.error(throwable.getMessage()));
    }

    @GetMapping("/find/all")
    public Flux<NewEntity> findAll() {
        return newEntityRepository.findAll();
    }

    @GetMapping("/zip/{imei}")
    public Flux<Tuple2<RM1657Entity, NewEntity>> zip(@PathVariable("imei") final String imei) {
        return Flux.zip(
                rm1657Repository.findByImei(imei),
                newEntityRepository.findByImei(imei)
        );
    }

    @GetMapping("/join/{imei}")
    public JoinDTO join(@PathVariable("imei") final String imei) {
        Mono<List<Long>> rmCountList = rm1657Repository.findByImei(imei).map(RM1657Entity::getCount).collectList();
        Mono<List<Long>> neCountList = newEntityRepository.findByImei(imei).map(NewEntity::getCount).collectList();

        return JoinDTO.builder()
                .imei(imei)
                .neCount(neCountList.block())
                .rmCount(rmCountList.block())
                .build();
    }
}
