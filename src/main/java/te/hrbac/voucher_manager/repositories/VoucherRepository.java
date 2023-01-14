package te.hrbac.voucher_manager.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import te.hrbac.voucher_manager.model.Voucher;

import java.util.Optional;

@Repository
public interface VoucherRepository extends CrudRepository<Voucher, Long> {
    Optional<Voucher> findByIdCode(String idCode);

}
