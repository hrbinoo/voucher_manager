package te.hrbac.voucher_manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import te.hrbac.voucher_manager.model.Role;
import te.hrbac.voucher_manager.services.RoleService;
import te.hrbac.voucher_manager.services.UserService;

@RestController @Slf4j
@RequestMapping("/api/v1/roles/")
public class RoleController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Role saveRole(@RequestBody Role role){
        Role roleTmp =  roleService.saveRole(role);
        log.info("Role: [{}] was added to the database", roleTmp.getName());
        return roleTmp;
    }

    @PostMapping("/toUser")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void provideRoleToUser(@RequestBody RoleToUserForm roleToUserForm){
        userService.provideRoleToUser(roleToUserForm.getUsername(), roleToUserForm.getUsername());
        log.info("Role [{}] added to [{}]", roleToUserForm.getRolename(), roleToUserForm.getUsername());
    }
}
class RoleToUserForm{
    private String username;
    private String rolename;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
