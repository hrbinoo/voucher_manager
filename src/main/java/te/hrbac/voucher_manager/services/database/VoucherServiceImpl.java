package te.hrbac.voucher_manager.services.database;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import te.hrbac.voucher_manager.model.Capture;
import te.hrbac.voucher_manager.model.CaptureItem;
import te.hrbac.voucher_manager.model.Voucher;
import te.hrbac.voucher_manager.repositories.CaptureItemRepository;
import te.hrbac.voucher_manager.repositories.VoucherRepository;
import te.hrbac.voucher_manager.services.VoucherService;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service @Slf4j
public class VoucherServiceImpl implements VoucherService {
    private final DateTimeFormatter SHORT_DATE_HOUR_FORMAT = DateTimeFormatter.ofPattern("ddMMyyHH"); //den - mesic - rok - hodina dne
    private final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMyy"); // den - mesic - rok
    private final int SUFFIX_LENGTH = 12;
    private final String POOL = "AaBbCcDdEeFfGgHhLMmNnoPpQqRrSsTtUuVvWwXxYyZz0123456789";
    private final int TEST_SUFFIX_LENGTH = 1;
    private final String TEST_POOL = "1234567890";

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private CaptureItemRepository captureItemRepository;


    @Override
    public Voucher createVoucher(int amount) {
        int counterCheck = 0;
        String code = idCodeGenerator(amount);
        while(voucherRepository.findVoucherByIdCode(code).isPresent()){
            counterCheck++;
            code = idCodeGenerator(amount);
        }
        log.info("New Voucher: [{}], Amount: [{}], Attempt: [{}]", code, amount, counterCheck);
        return voucherRepository.save(
                new Voucher(amount, code)
        );
    }
    @Override
    public Voucher findVoucherByIdCode(String idCode) {
        log.info("Voucher: [{}] was fetched",idCode);
        return this.voucherRepository.findVoucherByIdCode(idCode).get();
    }

    @Override
    public Voucher activateVoucher(String id, boolean activationFlag) {
        log.info("Voucher: [{}] was activated: [{}]", id, activationFlag);
        Voucher voucher = voucherRepository.findVoucherByIdCode(id).get();
        voucher.setActive(activationFlag);
        return this.voucherRepository.save(
                voucher
        );//.orElse(null); //.orElse() -> prozatimni reseni => chybovy kod
    }

    @Override
    public List<CaptureItem> getAllCaptureItems(String id) {
        return captureItemRepository.findAllByvoucherCode(id);
    }


    // CODE GENERATOR

    private String idCodeGenerator(int amount){
        StringBuilder code = new StringBuilder();
        code
                .append(LocalDateTime.now().format(SHORT_DATE_HOUR_FORMAT)) //datum vznik
                .append(LocalDateTime.now().plusDays(90).format(SHORT_DATE_FORMAT)) // datum expirace
                .append(amount) //hodnota
                .append(randomSuffixGenerator()); //random suffix
        return code.toString();
    }

    private String randomSuffixGenerator(){
        StringBuilder suffix = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < SUFFIX_LENGTH; i++) {
            suffix.append(POOL.charAt(
                    rand.nextInt(TEST_POOL.length())
            ));
        }
        return suffix.toString();
    }
}
