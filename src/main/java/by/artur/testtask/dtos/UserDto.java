package by.artur.testtask.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    Long id;
    String name;
    LocalDate dateOfBirth;
    AccountDto account;
    Set<EmailDataDto> emails;
    Set<PhoneDataDto> phones;
}
