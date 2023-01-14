package te.hrbac.voucher_manager.services.impl;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import te.hrbac.voucher_manager.exceptions.NotFoundException;
import te.hrbac.voucher_manager.model.Capture;
import te.hrbac.voucher_manager.model.CaptureItem;
import te.hrbac.voucher_manager.model.Voucher;
import te.hrbac.voucher_manager.repositories.CaptureItemRepository;
import te.hrbac.voucher_manager.repositories.CaptureRepository;
import te.hrbac.voucher_manager.repositories.VoucherRepository;
import te.hrbac.voucher_manager.services.CaptureService;


@Slf4j
@Service
public class CaptureServiceImpl implements CaptureService {

    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private CaptureRepository captureRepository;
    @Autowired
    private CaptureItemRepository captureItemRepository;

    @Override
    @Transactional
    public void captureVoucher(Capture capture) {
        captureRepository.save(capture);
        captureItemRepository.saveAll(capture.getCaptureItems());

        capture.getCaptureItems().forEach(item -> {
            Voucher voucher = voucherRepository.findByIdCode(item.getVoucherCode())
                    .orElseThrow(() -> new NotFoundException(Voucher.class, "voucher", item.getVoucherCode()));

            if (voucher.getActualValue() >= item.getAmount()) {
                voucher.setActualValue(voucher.getActualValue() - item.getAmount());
                voucher.addCaptureItem(item);
                voucherRepository.save(voucher);
                log.info("Successful Capture - Capture ID: [{}], CaptureItem: [{}]", capture.getId(), item);
            } else {
                log.info("Unsuccessful Capture due to small amount of money - Capture ID: [{}], CaptureItem: [{}]", capture.getId(), item);
                throw new TransactionException("Small amount of money on voucher");
            }

        });
    }

}
