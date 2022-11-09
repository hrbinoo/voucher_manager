package te.hrbac.voucher_manager.services;

import te.hrbac.voucher_manager.model.Capture;
import te.hrbac.voucher_manager.model.CaptureItem;
import te.hrbac.voucher_manager.model.Voucher;

import javax.transaction.TransactionRolledbackException;
import java.util.List;

public interface VoucherService {
    Voucher createVoucher(int amount);
    Voucher findVoucherByIdCode(String idCode);
    Voucher activateVoucher(String id, boolean activationFlag);
    List<CaptureItem> getAllCaptureItems(String id);
}
