package zw.co.nbs.transxncodegl.connection.lmpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import zw.co.nbs.transxncodegl.connection.api.IHSConn;

import java.sql.*;

@Slf4j
public class IHSConnImpl implements IHSConn {

    public static Connection conn;

    @Value("${spring.datasource-ihs.driver-class-name}")
    private String jdbcClassName;

    @Value("${spring.datasource-ihs.url}")
    private String url;

    @Value("${spring.datasource-ihs.username}")
    private String username;

    @Value("${spring.datasource-ihs.password}")
    private String password;


    public Connection openConn() throws Exception {

        if (conn == null || conn.isClosed()) {
            try {
                log.debug("Connecting to : {},{},{}",url,username,password);
                Class.forName(jdbcClassName);
                conn= DriverManager.getConnection(url, username, password);
                return conn;
            } catch (SQLException | ClassNotFoundException e) {
                log.error("Error Opening Connection to Ihs", e);
                log.error("Failed to establish a connection to Ihs:::  {}", e.getMessage());
                throw new SQLException("Failed to connect to Ihs: " + url);
            }
        }
        return conn;
    }

    public ResultSet executeQuery(String sqlString) {
        ResultSet rs = null;
        try {
            log.debug(sqlString);
            openConn();
            Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            statement.executeQuery(sqlString);
            rs = statement.getResultSet();
        } catch (Exception ex) {
            log.error("SQL Query Error", ex);
        }
        return rs;
    }


}
