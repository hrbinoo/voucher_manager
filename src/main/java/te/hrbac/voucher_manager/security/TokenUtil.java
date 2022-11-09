package te.hrbac.voucher_manager.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TokenUtil implements Serializable {

    @Serial
    private static final long serialVersion = 58345734575485839L;
    private String secret = "heslo";
    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(userDetails.getUsername())
                .addClaims(Map.of("ROLE", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(";"))))
                .setIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        Date expiration = getClaimFromToken(token, Claims::getExpiration);

        if(expiration == null) return false;
        else if(expiration.before(new Date())) return false;

        String username = getUsernameFromToken(token);
        if(userDetails == null)
            return false;
        else if (username == null)
            return false;
        else
            return username.equals(userDetails.getUsername());
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
            Claims claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();
        return claimsResolver.apply(claims);
    }
}
