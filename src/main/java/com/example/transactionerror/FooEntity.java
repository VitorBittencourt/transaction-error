package com.example.transactionerror;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collectionName = "FooEntity")
public class FooEntity {
    @DocumentId
    private String id;
    private String localIdentifier;
    private long count;
}
