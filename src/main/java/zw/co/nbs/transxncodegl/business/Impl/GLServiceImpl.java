//package zw.co.nbs.transxncodegl.business.Impl;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.ApplicationContext;
//import org.springframework.stereotype.Service;
//import zw.co.nbs.transxncodegl.business.api.GLService;
//import zw.co.nbs.transxncodegl.connection.api.FbeqConn;
//import zw.co.nbs.transxncodegl.connection.api.IHSConn;
//import zw.co.nbs.transxncodegl.model.GLAccount;
//
//import java.util.List;
//import java.util.concurrent.atomic.AtomicBoolean;
//
//@Service
//public class GLServiceImpl implements GLService {
//
//    private final AtomicBoolean busyProgram = new AtomicBoolean(false);
//
//    private final Logger LOGGER = LoggerFactory.getLogger(getClass().getName());
//
//
//    @Value("${Fbeq.query.GLs}")
//    private String generalLedger;
//
//
//    @Value("${Ihs.query.dataTransfergl}")
//    private String getGeneralLedgers;
//
//    private final FbeqConn fbeqConn;
//    private final IHSConn ihsConn;
//
//    public GLServiceImpl(ApplicationContext context) {
//        this.fbeqConn = context.getBean(FbeqConn.class);
//        this.ihsConn = context.getBean(IHSConn.class);
//    }
//
//
//    @Override
//    public List<GLAccount> retrieveNewGL() {
//        return null;
//    }
//
//    @Override
//    public void getCurrentTimestamp() {
//
//    }
//}
