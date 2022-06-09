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
@Document(collectionName = "NewEntity")
public class NewEntity {
    @DocumentId
    private String id;
    private String imei;
    private long count;
}
