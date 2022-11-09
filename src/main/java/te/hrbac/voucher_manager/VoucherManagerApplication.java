package te.hrbac.voucher_manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import te.hrbac.voucher_manager.model.Role;
import te.hrbac.voucher_manager.model.User;
import te.hrbac.voucher_manager.services.UserService;

import java.util.ArrayList;

@SpringBootApplication
public class VoucherManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(VoucherManagerApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
          userService.saveRole(new Role(null, "USER"));
          userService.saveRole(new Role(null, "ADMIN"));


            userService.saveUser(new User(null, "Jan Hrbac", "hrbadjan", passwordEncoder().encode("222"), new ArrayList<>()));
            userService.saveUser(new User(null, "Natalie Raskova", "raskonat", passwordEncoder().encode("222"), new ArrayList<>()));
            userService.saveUser(new User(null, "Tomas Skowronek", "skowrtom", passwordEncoder().encode("222"), new ArrayList<>()));

            userService.provideRoleToUser("hrbadjan", "ADMIN");
            userService.provideRoleToUser("raskonat", "ADMIN");
            userService.provideRoleToUser("skowrtom", "USER");
        };
    }

}
