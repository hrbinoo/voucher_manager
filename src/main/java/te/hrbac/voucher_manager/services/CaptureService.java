package te.hrbac.voucher_manager.services;

import te.hrbac.voucher_manager.model.Capture;

import javax.transaction.TransactionRolledbackException;

public interface CaptureService {
    void captureVoucher(Capture capture) throws TransactionRolledbackException;

}
