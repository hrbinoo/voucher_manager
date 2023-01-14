package te.hrbac.voucher_manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import te.hrbac.voucher_manager.model.Capture;
import te.hrbac.voucher_manager.model.CaptureItem;
import te.hrbac.voucher_manager.model.Voucher;
import te.hrbac.voucher_manager.services.CaptureService;
import te.hrbac.voucher_manager.services.VoucherService;

import javax.transaction.TransactionRolledbackException;
import java.util.List;

@RestController @Slf4j
@RequestMapping("/api/v1/voucher/")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @PostMapping("create/")
    public Voucher createVoucher(@RequestBody int amount) {
        Voucher voucher = voucherService.createVoucher(amount);
        log.info("New Voucher: [{}], Amount: [{}]", voucher.getIdCode(), amount);
        return voucher;
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Voucher getVoucher(@PathVariable String id){
        Voucher voucher = voucherService.findVoucherByIdCode(id);
        log.info("Voucher: [{}] was fetched",id);
        return voucher;
    }

    @PatchMapping("activate/{id}")
    public Voucher activateVoucher(@PathVariable String id, @RequestBody boolean activationFlag){
        Voucher voucher = voucherService.activateVoucher(id, activationFlag);
        log.info("Voucher: [{}] was activated: [{}]", id, activationFlag);
        return voucher;
    }

    @GetMapping("captures/{id}")
    public List<CaptureItem> getCaptureItems(@PathVariable String id){
        List<CaptureItem> captureItems = voucherService.getAllCaptureItems(id);
        log.info("Captured vouchers: [{}]", captureItems.toString());
        return captureItems;
    }

}


