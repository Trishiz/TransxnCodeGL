package zw.co.nbs.transxncodegl.connection.api;

import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;

public interface FbeqConn {
    ResultSet executeQuery(String sql);
    DataSource dataSource();
}
