package security;

import exception.InvalidClaimsException;
import exception.NonExistentEntityException;
import io.jsonwebtoken.*;
import model.persistent.User;
import org.orm.PersistentException;
import org.springframework.stereotype.Service;
import service.UserService;

import java.util.Date;

@Service
public class JwtService {

    private static final String signingKey = "Hello :)";
    private static final int expirationTime = 60 * 60 * 1000; // 1 hora

    private UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    public Claims getClaims(String token) throws ExpiredJwtException, MalformedJwtException, SignatureException {
        return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody();
    }

    public String createToken(int userID){
        Date issueDate = new Date();
        Date expirationDate = new Date(issueDate.getTime() + expirationTime);
        return Jwts.builder()
                .setSubject(String.valueOf(userID))
                .setIssuedAt(issueDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, signingKey).compact();
    }

    public User getUser(Claims claims) throws InvalidClaimsException, PersistentException {
        int userID;
        try {
            userID = Integer.parseInt(claims.getSubject());
        }catch (Exception e){
            throw new InvalidClaimsException();
        }
        try{
            return userService.getUserByID(userID);
        } catch (NonExistentEntityException e) {
            throw new InvalidClaimsException();
        }
    }
}
