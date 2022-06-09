package com.example.transactionerror;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinDTO {
    private String localIdentifier;
    private List<Long> fooCountList;
    private Long barCount;
}
