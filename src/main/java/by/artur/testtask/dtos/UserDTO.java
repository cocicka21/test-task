package by.artur.testtask.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    Long id;
    String name;
    LocalDate dateOfBirth;
    Set<EmailDataDto> emails;
    Set<PhoneDataDto> phones;
}
