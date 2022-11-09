package te.hrbac.voucher_manager.services.database;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import te.hrbac.voucher_manager.model.Capture;
import te.hrbac.voucher_manager.model.CaptureItem;
import te.hrbac.voucher_manager.model.Voucher;
import te.hrbac.voucher_manager.repositories.CaptureItemRepository;
import te.hrbac.voucher_manager.repositories.CaptureRepository;
import te.hrbac.voucher_manager.repositories.VoucherRepository;
import te.hrbac.voucher_manager.services.CaptureService;

import javax.transaction.Transactional;

@Slf4j
@Service
public class CaptureServiceImpl implements CaptureService {

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private CaptureRepository captureRepository;

    @Autowired
    private CaptureItemRepository captureItemRepository;

    @Transactional
    @Override // ROLLBACK
    public void captureVoucher(Capture capture) {
        log.info("New Capture, ID: [{}], CaptureItems [{}]",capture.getId(), capture.getCaptureItems().toString());
        captureRepository.save(capture);
        captureItemRepository.saveAll(capture.getCaptureItems());
        for (CaptureItem item : capture.getCaptureItems()) {
            Voucher voucher = voucherRepository.findVoucherByIdCode(item.getVoucherCode()).get();
                if (voucher.getActualValue() >= item.getAmount()) {
                    log.info("Successful Capture - Capture ID: [{}], CaptureItem: [{}]", capture.getId(), item);
                    voucher.setActualValue(voucher.getActualValue() - item.getAmount());
                    voucher.addCaptureItem(item);
                    voucherRepository.save(voucher);
                } else {
                    log.info("Unsuccessful Capture due to small amount of money - Capture ID: [{}], CaptureItem: [{}]", capture.getId(), item);
                    throw new TransactionException("Small amount of money on voucher");
                }
            }
    }

}
