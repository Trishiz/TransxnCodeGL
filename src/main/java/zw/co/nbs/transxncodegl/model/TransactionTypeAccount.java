package zw.co.nbs.transxncodegl.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "TransactionType")
public class TransactionTypeAccount {

    @Id
    private String transactionCode;
    private String transactionCodeDescription;
    private String advise;
    private String drCr;
    private String maximum;
    private String minimum;
}