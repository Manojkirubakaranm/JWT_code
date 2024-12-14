package com.practice.jwt.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
//
//    public final String  secret_KEY  ;
//
//    public JwtService(){
//        try{
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            keyGen.init(256);
//            SecretKey key = keyGen.generateKey();
//            secret_KEY = Base64.getEncoder().encodeToString(key.getEncoded());//==>convert bytes to string
//            System.out.println("secret key is  "+ secret_KEY);
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    public String genrateToken(String username) {
//        Map<String,Object> claim = new HashMap<>();
//        claim.put("username", username);
//        System.out.println("token genertaing ==================================");
//        String ans =  Jwts.builder()
//                .setClaims(claim)
//                .subject(username)
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis()+100*100*100*60*60*30))
//                .signWith(getKey(), SignatureAlgorithm.HS256) // Sign with the key
//                .compact();
//
//
//        System.out.println("token  ===========================>"+ans);
//        return ans;
//    }
//    public SecretKey getKey(){
//        System.out.println("getkey()  ==================>");
//        byte[] keybytes = Decoders.BASE64.decode(secret_KEY);
//        return Keys.hmacShaKeyFor(keybytes);
//    }
//    public String extractName(String token) {
//        System.out.println("ExtractName  ==================>");
//        return extractclaim(token, Claims::getSubject);
//    }
//
//    private <T>T extractclaim(String token, Function<Claims,T>getSubject) {
//        System.out.println("Extractclaim  ==================>");
//        final Claims claims = extractAllclaim(token);
//        return getSubject.apply(claims);
//    }
//
//    private Claims extractAllclaim(String token) {
//        System.out.println("ExtractAllClaim  ==================>");
//         Claims ans1 =
////         Jwts.parser()
//        Jwts.// Use parserBuilder() to initialize a builder
//        parser()
//                .setSigningKey(getKey()) // Set the signing key
//                .build() // Build the parser
//                .parseClaimsJws(token) // Parse the token
//                .getBody();
//
//
//
//        System.out.println("claims  ===============>"+ans1);
//        return ans1;
//
//    }
//
//    public boolean validtoken(String token, UserDetails userDetails) {
//      return (extractName(token).equals(userDetails.getUsername()) && !TokenExpired(token));
//
//    }
//
//    private boolean TokenExpired(String token) {
//        System.out.println("ExtractExpired  ==================>");
//        return ExtractEpireDate(token).before(new Date());
//    }
//
//    private Date ExtractEpireDate(String token) {
//        System.out.println("ExtractExpired  ==================>");
//        return extractclaim(token,Claims::getExpiration);
//    }
//}



//@Service
//public class JWTService {


    private String secretkey = "";

    public JwtService() {

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 60 * 60 * 30))
                .and()
                .signWith(getKey())
                .compact();

    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}


