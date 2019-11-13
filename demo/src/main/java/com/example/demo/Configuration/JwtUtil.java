package com.example.demo.Configuration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

@Service
public class JwtUtil {

 /* static void addAuthentication(HttpServletResponse response, String email){
        String Key = "mysecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(email)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        Key.getBytes()).compact();
        response.addHeader("authorities","Bearer" + token);
    }
    static Authentication getAuthentication(HttpServletRequest request){
        String token = request.getHeader("authorities");
        if(token!= null){
            String user = Jwts.parser()
                    .setSigningKey("mysecretKey")
                    .parseClaimsJws(token.replace("Bearer",""))
                    .getBody()
                    .getSubject();
            return  user != null ?
                    new UsernamePasswordAuthenticationToken(user,null, emptyList()):null;
        }
        return null;
    }*/

  private String secretKey = "mysecretKey";

  public String extractUsername(String token)
  {
      return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token)
  {
      return  extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim (String token, Function <Claims, T > claimsResolver)
  {
      final Claims claims = extractAllClaims(token);return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token)
  {
      return  Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
  }

  private boolean isTokenExpired(String token)
  {
      return  extractExpiration(token).before(new Date());
  }

  public String generateToken(UserDetails userDetails)
  {
      Map<String,Object> claims = new HashMap<>();return createToken(claims, userDetails.getUsername());
  }

  private String createToken(Map<String, Object> claims, String username)
  {
      return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + 60000))
              .signWith(SignatureAlgorithm.HS512,secretKey).compact();
  }

  public boolean validateToken(String token,UserDetails userDetails)
  {
      final String username = extractUsername(token);
      return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
  }

}
