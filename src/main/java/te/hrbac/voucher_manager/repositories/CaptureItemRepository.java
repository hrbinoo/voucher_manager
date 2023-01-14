package te.hrbac.voucher_manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import te.hrbac.voucher_manager.model.CaptureItem;

import java.util.List;

@Repository
public interface CaptureItemRepository extends CrudRepository<CaptureItem, Long> {
    List<CaptureItem> findAllByvoucherCode(String id);
}
