package zw.co.nbs.transxncodegl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import zw.co.nbs.transxncodegl.business.api.TransactionCodeService;
import zw.co.nbs.transxncodegl.config.ConnConfig;

@EnableScheduling
@Import(ConnConfig.class)
@SpringBootApplication


public class TransxnCodeGlApplication {

 public static void main(String[] args) {

        SpringApplication.run(TransxnCodeGlApplication.class, args);
    }

}
