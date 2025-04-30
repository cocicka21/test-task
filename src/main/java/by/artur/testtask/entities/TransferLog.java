package by.artur.testtask.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferLog {

    @Field(name = "userId", type = FieldType.Text)
    private String userId;
    @Field(name = "toUserId", type = FieldType.Text)
    private String toUserId;
    @Field(name = "amount", type = FieldType.Text)
    private String amount;
}
