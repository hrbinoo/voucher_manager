package te.hrbac.voucher_manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import te.hrbac.voucher_manager.model.Capture;
import te.hrbac.voucher_manager.model.CaptureItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaptureRepository extends CrudRepository<Capture, Long> {

}
