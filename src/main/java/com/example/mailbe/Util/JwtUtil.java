package com.example.mailbe.Util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JwtUtil {
    private static final String key = "ss@d%dsv!sd#sd&sdv!sdv";
    private static final JwtParser parser = Jwts.
            parserBuilder().setSigningKey(getSecretKey()).build();

    private static SecretKey getSecretKey() {
        SecretKey secretKey = null;
        try {
            secretKey = Keys.hmacShaKeyFor(key.getBytes("UTF-32"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    public static String getAccessToken(String email, Collection<GrantedAuthority>
            authorities) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("authority", authorities.stream().map(GrantedAuthority::getAuthority).toList());
        return buildToken(
                Date.from(Instant.now().plus(30, ChronoUnit.DAYS)),
                map
        );
    }

    public static String getRefreshToken(String email, Collection<GrantedAuthority>
            authorities) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("authority", authorities.stream().map(GrantedAuthority::getAuthority).toList());
        return buildToken(
                Date.from(Instant.now().plus(7, ChronoUnit.DAYS)),
                map
        );
    }

    public static Claims getClaims(String jwt) {
        return parser.parseClaimsJws(jwt).getBody();
    }

    public static Boolean isExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return Instant.now().isAfter(expiration.toInstant());
    }

    private static String buildToken(Date exp, Map<String, Object> claims) {
        return Jwts.builder()
                .addClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(exp)
                .signWith(getSecretKey())
                .compact();
    }

}


