package te.hrbac.voucher_manager.controllers;

import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import te.hrbac.voucher_manager.exceptions.VoucherNotFoundException;
import te.hrbac.voucher_manager.model.Capture;
import te.hrbac.voucher_manager.model.CaptureItem;
import te.hrbac.voucher_manager.services.CaptureService;

import javax.transaction.TransactionRolledbackException;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/capture")
public class CaptureController {
    @Autowired
    private CaptureService captureService;

    @PostMapping("/voucher/")
    public ResponseEntity<Long> captureVoucher(@RequestBody List<CaptureItem> captureItems) throws TransactionRolledbackException {
        Capture capture = new Capture(captureItems);
        try{
            this.captureService.captureVoucher(capture);
            return ResponseEntity.ok().body(capture.getId());
        } catch (VoucherNotFoundException e){
            return ResponseEntity.notFound().build();
        } catch (TransactionException e){
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        //return ResponseEntity.internalServerError().build();
    }
}
