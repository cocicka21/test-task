package by.artur.testtask.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Builder
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true, onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accounts")
public class Account extends BasicEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Column(name = "balance", nullable = false)
    @DecimalMin(value = "0.0")
    @ToString.Include
    private BigDecimal balance;

    @Column(name = "initial_balance", nullable = false)
    @DecimalMin(value = "0.0")
    @ToString.Exclude
    private BigDecimal initialBalance;

}
