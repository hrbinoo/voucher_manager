package te.hrbac.voucher_manager.controllers;

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

@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @PostMapping("/create/")
    public ResponseEntity<Voucher> createVoucher(@RequestBody int amount) {
        return ResponseEntity.ok().body(
                voucherService.createVoucher(amount)
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Voucher> getVoucher(@PathVariable String id){
        return ResponseEntity.ok().body(voucherService.findVoucherByIdCode(id));
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<Voucher> activateVoucher(@PathVariable String id, @RequestBody boolean activationFlag){
        return ResponseEntity.ok().body(voucherService.activateVoucher(id, activationFlag)); //chybove HTTP kod
    }

    @GetMapping("/captures/{id}")
    public ResponseEntity<List<CaptureItem>> getCaptureItems(@PathVariable String id){
        return ResponseEntity.ok(voucherService.getAllCaptureItems(id));
    }

}


