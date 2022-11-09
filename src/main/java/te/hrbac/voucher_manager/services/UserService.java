package te.hrbac.voucher_manager.services;

import te.hrbac.voucher_manager.model.Role;
import te.hrbac.voucher_manager.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void provideRoleToUser(String username, String rolename);
    User getUser(String username);
    List<User> getUsers();
}
