package zw.co.nbs.transxncodegl.connection.lmpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import zw.co.nbs.transxncodegl.connection.api.FbeqConn;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.ResultSet;
import java.sql.Statement;
@Slf4j
public class FbeqConnImpl implements FbeqConn {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }
    @Override
    public ResultSet executeQuery(String sqlString) {
        ResultSet rs = null;
        Statement statement = null;
        try {
            log.debug(sqlString);
            DataSource env = dataSource();
            statement = env.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.executeQuery(sqlString);
            rs = statement.getResultSet();
        } catch (Exception ex) {
            log.error("SQL Query Error", ex);
        }
        return rs;
    }
}



