package by.artur.testtask.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailLog {

    @Field(name = "userId", type = FieldType.Text)
    private String userId;
    @Field(name = "email", type = FieldType.Text)
    private String email;
    @Field(name = "createdDate", type = FieldType.Text)
    private LocalDate createdDate = LocalDate.now();
}
