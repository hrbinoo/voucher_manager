package te.hrbac.voucher_manager.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenUtil tokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String REQUEST_TOKEN_HEADER = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        if(REQUEST_TOKEN_HEADER != null && REQUEST_TOKEN_HEADER.startsWith("Bearer ")) {
            jwtToken = REQUEST_TOKEN_HEADER.substring(7); // osvobozujeme token od stringu "Bearer" => zbyva cisty token
            try{
                username = tokenUtil.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT token has expired");
            }
        } else {
            logger.warn("JWT token not starting with Bearer");
        }

        if(username != null && SecurityContextHolder.getContext().getAuthentication()==null){ // pokud autentikace v security kontextu tak jej musime doplnit
            // Vytahne userdetaily na zaklade tokenu username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); //vytahujeme z databaze uzivatele
            if(tokenUtil.validateToken(jwtToken, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //nastavujeme token s daty

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthToken); // vkladame token to security kontextu
            }
        }
        filterChain.doFilter(request,response);
    }


}
