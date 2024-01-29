package zw.co.nbs.transxncodegl.business.api;

import zw.co.nbs.transxncodegl.model.SAPF;

import java.util.List;

public interface TransactionCodeService {
    List<SAPF> retrieveNewTransactionCode();
    void getCurrentTimestamp();

}

