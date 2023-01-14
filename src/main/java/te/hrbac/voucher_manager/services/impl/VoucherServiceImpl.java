package te.hrbac.voucher_manager.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import te.hrbac.voucher_manager.exceptions.NotFoundException;
import te.hrbac.voucher_manager.model.CaptureItem;
import te.hrbac.voucher_manager.model.Voucher;
import te.hrbac.voucher_manager.repositories.CaptureItemRepository;
import te.hrbac.voucher_manager.repositories.VoucherRepository;
import te.hrbac.voucher_manager.services.VoucherService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service @Slf4j
public class VoucherServiceImpl implements VoucherService {
    /* den - mesic - rok - hodina dne */
    private final DateTimeFormatter SHORT_DATE_HOUR_FORMAT = DateTimeFormatter.ofPattern("ddMMyyHH");
    /* den - mesic - rok */
    private final DateTimeFormatter SHORT_DATE_FORMAT = DateTimeFormatter.ofPattern("ddMMyy");
    private final int SUFFIX_LENGTH = 12;
    private final String POOL = "AaBbCcDdEeFfGgHhLMmNnoPpQqRrSsTtUuVvWwXxYyZz0123456789";
    private final String TEST_POOL = "1234567890";

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private CaptureItemRepository captureItemRepository;

    /*
    *  The attempts are logged to ensure that the id code is not generated too many times
    *  which could have impact on performance.
    * */
    @Override
    @Transactional
    public Voucher createVoucher(int amount) {
        int counterCheck = 0;
        String code = idCodeGenerator(amount);
        while(voucherRepository.findByIdCode(code).isPresent()){
            counterCheck++;
            code = idCodeGenerator(amount);
        }
        log.info("Attempt [how many times the id code wa generated]: [{}]", counterCheck);
        if(counterCheck > 60) log.info("WARNING !!! the id code was generated more than 60 times WARNING !!!");
        return voucherRepository.save(
                new Voucher(amount, code)
        );
    }
    @Override
    @Transactional(readOnly = true)
    public Voucher findVoucherByIdCode(String idCode) {
        return this.voucherRepository.findByIdCode(idCode).orElseThrow(() -> new NotFoundException(Voucher.class, "voucher", idCode));
    }

    @Override
    @Transactional
    public Voucher activateVoucher(String id, boolean activationFlag) {
        Voucher voucher = voucherRepository.findByIdCode(id).orElseThrow(() -> new NotFoundException(Voucher.class, "voucher", id));
        voucher.setActive(activationFlag);
        return this.voucherRepository.save(voucher);
    }

    @Override
    @Transactional(readOnly = true)
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
