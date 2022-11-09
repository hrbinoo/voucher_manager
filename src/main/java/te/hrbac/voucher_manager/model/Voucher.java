package te.hrbac.voucher_manager.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Voucher {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    private String idCode; //Generovano

    private int defaultValue; // Vstup -

    private int actualValue; // Vstup -

    private LocalDateTime expiration; //Automatika -

    private boolean active; // Default (Automatika) -

    @OneToMany(fetch = FetchType.LAZY)
    private List<CaptureItem> captures = new ArrayList<>();

    public Voucher(){
        this.expiration = LocalDateTime.now().plusDays(90);
        this.active = false;
    }

    public Voucher(int amount, String code){
        this();
        this.defaultValue = amount;
        this.actualValue = amount;
        this.idCode = code;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public int getDefaultValue() {
        return defaultValue;
    }

    public int getActualValue() {
        return actualValue;
    }

    public List<CaptureItem> getCaptures() {return captures;}

    public void setCaptures(List<CaptureItem> captures) {this.captures = captures;}

    public void addCaptureItem(CaptureItem captureItem){this.captures.add(captureItem);}

    public void setActualValue(int actualValue) {
        this.actualValue = actualValue;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
