package te.hrbac.voucher_manager.services.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import te.hrbac.voucher_manager.model.Role;
import te.hrbac.voucher_manager.model.User;
import te.hrbac.voucher_manager.repositories.RoleRepository;
import te.hrbac.voucher_manager.repositories.UserRepository;
import te.hrbac.voucher_manager.services.UserService;

import javax.transaction.Transactional;
import java.util.List;

@Service @Transactional @Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        log.info("User: [{}] was added to the Database",user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Role: [{}] was added to the database", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void provideRoleToUser(String username, String rolename) {
        log.info("Role [{}] added to [{}]", rolename, username);
        User user = userRepository.findByUsername(username).get();
        Role role = roleRepository.findByName(rolename).get();
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String username) {
        log.info("User [{}] was fetched", username);
        return userRepository.findByUsername(username).get();
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
