package te.hrbac.voucher_manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import te.hrbac.voucher_manager.model.Role;
import te.hrbac.voucher_manager.model.User;
import te.hrbac.voucher_manager.repositories.UserRepository;

import java.util.List;

@Service("userDetailsService")
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            return userRepository.findByUsername(username)
                    .map(user -> new org.springframework.security.core.userdetails.User(
                                    user.getUsername(), user.getPassword(),
                                    user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList()
                            )
                    ).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
 