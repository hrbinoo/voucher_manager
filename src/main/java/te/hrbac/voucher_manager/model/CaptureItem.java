package te.hrbac.voucher_manager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CaptureItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String voucherCode;
    private int amount;

    public Long getId() { return this.id;}

    public String getVoucherCode() {
        return this.voucherCode;
    }
    public int getAmount() {
        return this.amount;
    }

    public void setIdCode(String idCode) {
        this.voucherCode = idCode;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CaptureItem{" +
                "id=" + id +
                ", voucherCode='" + voucherCode + '\'' +
                ", amount=" + amount +
                '}';
    }
}
