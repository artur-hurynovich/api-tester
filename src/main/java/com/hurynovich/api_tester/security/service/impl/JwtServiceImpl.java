package com.hurynovich.api_tester.security.service.impl;

import com.hurynovich.api_tester.model.dto.impl.UserDTO;
import com.hurynovich.api_tester.model.dto.impl.UserRoleDTO;
import com.hurynovich.api_tester.security.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtServiceImpl implements JwtService {

    private static final String ROLES_KEY = "roles";

    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";

    private static final String BEARER_HEADER_PREFIX = "Bearer_";

    private final UserDetailsService userDetailsService;

    private final String jwtSecret;

    private final long expireInMillis;

    public JwtServiceImpl(final @NonNull UserDetailsService userDetailsService,
                          final @Value("${jwt.token.secret}") String jwtSecret,
                          final @Value("${jwt.token.expire}") long expireInMillis) {
        this.userDetailsService = userDetailsService;
        this.jwtSecret = Base64.getEncoder().encodeToString(jwtSecret.getBytes());
        this.expireInMillis = expireInMillis;
    }

    @Override
    public String buildToken(final @NonNull UserDTO userDTO) {
        final Claims claims = Jwts.claims().setSubject(userDTO.getEmail());

        final List<String> roleNames = userDTO.getRoles().stream().
                map(UserRoleDTO::getName).
                collect(Collectors.toList());

        claims.put(ROLES_KEY, roleNames);

        final Date currentDate = new Date();

        final Date expireDate = new Date(currentDate.getTime() + expireInMillis);

        return Jwts.builder().
                setClaims(claims).
                setIssuedAt(currentDate).
                setExpiration(expireDate).
                signWith(SignatureAlgorithm.HS256, jwtSecret).
                compact();
    }

    @Override
    public Authentication getAuthentication(final @NonNull String token) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(fetchEmail(token));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String fetchEmail(final @NonNull String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }

    @Override
    public String fetchToken(final HttpServletRequest request) {
        final String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER_KEY);

        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_HEADER_PREFIX)) {
            return authorizationHeader.substring(BEARER_HEADER_PREFIX.length());
        } else {
            return null;
        }
    }

    @Override
    public boolean validateToken(final @NonNull String token) {
        final Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

        return !claimsJws.getBody().getExpiration().before(new Date());
    }
}
