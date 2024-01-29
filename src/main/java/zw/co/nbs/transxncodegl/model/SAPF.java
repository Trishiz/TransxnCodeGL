package zw.co.nbs.transxncodegl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString

public class SAPF {

        @Id
        private String transactionCode;
        private String transactionCodeDescription;
        private String advise;
        private String drCr;
        private String maximum;
        private String minimum;

        public static String replace(String s, String lastTime) {
                return null;
        }
}
