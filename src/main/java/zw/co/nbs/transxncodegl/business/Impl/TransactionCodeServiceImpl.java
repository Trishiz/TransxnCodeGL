package zw.co.nbs.transxncodegl.business.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import zw.co.nbs.transxncodegl.business.api.TransactionCodeService;
import zw.co.nbs.transxncodegl.connection.api.FbeqConn;
import zw.co.nbs.transxncodegl.connection.api.IHSConn;
import zw.co.nbs.transxncodegl.model.SAPF;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
@Configuration
@Service
public class TransactionCodeServiceImpl implements TransactionCodeService {
    private final AtomicBoolean busyProgram = new AtomicBoolean(false);
    private final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());

    @Value("${Fbeq.query.transCode}")
    private String transCode;

    @Value("${Ihs.query.dataTransfer}")
    private String dataTransfer;

    @Value("${Ihs.query.SQL_INSERT}")
    private String sql_insert;

    @Value("${Fbeq.query.lastRunTime}")
    private String lastRunTime;

    private final FbeqConn fbeqConn;
    private final IHSConn ihsConn;

    public TransactionCodeServiceImpl(ApplicationContext context) {
        this.fbeqConn = context.getBean(FbeqConn.class);
        this.ihsConn = context.getBean(IHSConn.class);

    }
    @Scheduled(cron = "*/30 * * * * *")
    public void manageTranscode() {
        LOGGER.info(".......");
        {
            try {
                if (!busyProgram.compareAndSet(false, true)) {
                    LOGGER.info("Another scheduler still executing.......");
                    Thread.currentThread().interrupt();
                    return;
                }
                busyProgram.set(true);
                List<SAPF> transcode = retrieveNewTransactionCode();

                updateTransactionTypeAccount(transcode);

                getCurrentTimestamp();

            } catch (Exception ex) {
                LOGGER.error("Program extracting transactionCodes{}>>>{}", Thread.currentThread().getId(), ex.toString());
            } finally {
                busyProgram.set(false);
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public List<SAPF> retrieveNewTransactionCode() {
        List<SAPF> transcd = new ArrayList<>();
        String lastTime = getLastRunTime();
        LOGGER.info("last run time is " + lastTime);

        String query = transCode.replace("{lastRunTime}", lastTime);
        try {
            ResultSet rs = fbeqConn.executeQuery(query);

            if (rs == null) {
                LOGGER.info("query returned no new transaction codes");
            } else {
                LOGGER.info("query found new transaction codes");
                while (rs.next()) {
                    SAPF transactionCodeAccount = new SAPF();

                    transactionCodeAccount.setTransactionCode(rs.getString("transactionCode"));
                    transactionCodeAccount.setTransactionCodeDescription(rs.getString("{}}"));
                    transactionCodeAccount.setAdvise(rs.getString("advise"));
                    transactionCodeAccount.setDrCr(rs.getString("drCr"));
                    transactionCodeAccount.setMaximum(rs.getString("{}}"));
                    transactionCodeAccount.setMinimum(rs.getString("{}}"));
                    transcd.add(transactionCodeAccount);
                    System.out.println(transactionCodeAccount);
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return transcd;

    }

    private String getLastRunTime() {
        String lastTime = "";
        try {

            String query = lastRunTime;
            ResultSet rs = ihsConn.executeQuery(query);

            if (rs == null) {
                LOGGER.info("query returned no new transaction codes");
            } else {
                System.out.println(rs);
                LOGGER.info("query found new transaction codes");
                while (rs.next()) {

                    lastTime = rs.getString("lastTrans");
                    LOGGER.info("New record found {}" + lastTime);
                }
            }

        } catch (SQLException e) {
            return null;
        }
        return lastTime;
    }

    @Override
    public void getCurrentTimestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String SQL_INSERT = sql_insert;

        try {
            Connection  conn = ihsConn.openConn();
            PreparedStatement preparedStatement = conn.prepareStatement(SQL_INSERT,Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,timestamp.toString());


            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                LOGGER.info("lastTime table: {}",affectedRows);
            }
        }catch (Exception ex){
            LOGGER.info(ex.toString());

        }
    }


    public void updateTransactionTypeAccount(List<SAPF> transactionCodeAccount) {

        for (SAPF transcode : transactionCodeAccount) {
            try {

                String SQL = dataTransfer;
                Connection conn = ihsConn.openConn();
                PreparedStatement preparedStatement = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

                preparedStatement.setString(1, transcode.getTransactionCode());
                preparedStatement.setString(2, transcode.getTransactionCodeDescription());
                preparedStatement.setString(3, transcode.getAdvise());
                preparedStatement.setString(4, transcode.getDrCr());
                preparedStatement.setString(5, transcode.getMaximum());
                preparedStatement.setString(6, transcode.getMinimum());

                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                    LOGGER.info("New Accounts: {}");
                }
            } catch (Exception ex) {
                LOGGER.info(ex.toString());

            }
        }
    }

}
