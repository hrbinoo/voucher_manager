package te.hrbac.voucher_manager.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import te.hrbac.voucher_manager.model.Role;
import te.hrbac.voucher_manager.repositories.RoleRepository;
import te.hrbac.voucher_manager.services.RoleService;

@Service @Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }
}
