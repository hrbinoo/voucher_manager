package te.hrbac.voucher_manager.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import te.hrbac.voucher_manager.exceptions.NotFoundException;
import te.hrbac.voucher_manager.model.Role;
import te.hrbac.voucher_manager.model.User;
import te.hrbac.voucher_manager.repositories.RoleRepository;
import te.hrbac.voucher_manager.repositories.UserRepository;
import te.hrbac.voucher_manager.services.UserService;

import java.util.List;

@Service @Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void provideRoleToUser(String username, String rolename) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(User.class, "username",username));
        Role role = roleRepository.findByName(rolename).orElseThrow(() -> new NotFoundException(Role.class, "role", rolename));
        user.getRoles().add(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
