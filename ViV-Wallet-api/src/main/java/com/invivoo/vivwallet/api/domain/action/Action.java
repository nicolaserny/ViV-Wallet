package com.invivoo.vivwallet.api.domain.action;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.invivoo.vivwallet.api.domain.payment.Payment;
import com.invivoo.vivwallet.api.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Table(name="actions", uniqueConstraints = @UniqueConstraint(columnNames = {"lynxActivityId", "achiever_id", "type"}))
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private LocalDateTime valueDate;
    private ActionType type;
    private Long lynxActivityId;
    private int vivAmount;
    private String context;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    private User achiever;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    private User creator;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    private Payment payment;
    private boolean isDeleted;

    @JsonIgnore
    public boolean isPayable() {
        if(valueDate == null){
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return valueDate.getYear() < now.getYear() || (valueDate.getMonth().getValue() < now.getMonth().getValue() && valueDate.getYear() == now.getYear());
    }
}
