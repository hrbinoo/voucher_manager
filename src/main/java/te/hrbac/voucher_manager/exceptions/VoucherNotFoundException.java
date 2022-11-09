package te.hrbac.voucher_manager.exceptions;


import java.util.NoSuchElementException;

public class VoucherNotFoundException extends NoSuchElementException {
    public VoucherNotFoundException(){};
    public VoucherNotFoundException(String message){
        super(message);
    }
}
