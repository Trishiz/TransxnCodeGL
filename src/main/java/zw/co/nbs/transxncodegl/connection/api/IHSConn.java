package zw.co.nbs.transxncodegl.connection.api;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;

public interface IHSConn {
    ResultSet executeQuery(String sql);
    Connection openConn() throws Exception;
}
