package te.hrbac.voucher_manager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Request authenticationRequest) throws Exception { // authenticationRequest => ulozene heslo a username

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            ));
        } catch (DisabledException disabledException) {
            throw new Exception("DISABLED USER", disabledException);
        } catch (BadCredentialsException badCredentialsException) {
            System.out.println(
                    authenticationRequest.getUsername() + "\n" + authenticationRequest.getPassword()
            );
            throw new Exception("WRONG CREDENTIALS", badCredentialsException);
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = tokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new Response(token));
    }
}
