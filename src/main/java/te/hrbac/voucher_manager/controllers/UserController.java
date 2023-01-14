package te.hrbac.voucher_manager.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import te.hrbac.voucher_manager.model.Role;
import te.hrbac.voucher_manager.model.User;
import te.hrbac.voucher_manager.services.UserService;

import java.net.URI;
import java.util.List;

@RestController @Slf4j
@RequestMapping("/api/v1/users/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user){
        User userTmp = userService.saveUser(user);
        log.info("User: [{}] was added to the Database",user.getUsername());
        return userTmp;
    }
}