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
@Document(collectionName = "impact-service-2-RM1657")
public class RM1657Entity {
    @DocumentId
    private String id;
    private String imei;
    private long count;
}
