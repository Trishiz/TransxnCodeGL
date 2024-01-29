package zw.co.nbs.transxncodegl.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zw.co.nbs.transxncodegl.business.Impl.TransactionCodeServiceImpl;
import zw.co.nbs.transxncodegl.business.api.TransactionCodeService;
import zw.co.nbs.transxncodegl.connection.api.FbeqConn;
import zw.co.nbs.transxncodegl.connection.api.IHSConn;
import zw.co.nbs.transxncodegl.connection.lmpl.FbeqConnImpl;
import zw.co.nbs.transxncodegl.connection.lmpl.IHSConnImpl;

@Configuration
public class ConnConfig {
    @Bean
    public FbeqConn fbeqConn() {
        return new FbeqConnImpl();
    }

    @Bean
    public IHSConn ihsConn() {return new IHSConnImpl();}
    @Bean
    public TransactionCodeService transactionCodeService(final ApplicationContext context) {return new TransactionCodeServiceImpl(context);}

}
