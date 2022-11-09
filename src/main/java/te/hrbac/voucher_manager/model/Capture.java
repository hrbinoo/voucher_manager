package te.hrbac.voucher_manager.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Capture {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<CaptureItem> captureItems = new ArrayList<>();

    public Capture(List<CaptureItem> captureItems) { //Pouzit poled
        this.captureItems = captureItems;
    }

    public Capture() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CaptureItem> getCaptureItems() {
        return captureItems;
    }

    public void setCaptureItems(List<CaptureItem> captureItems) {
        this.captureItems = captureItems;
    }
}
