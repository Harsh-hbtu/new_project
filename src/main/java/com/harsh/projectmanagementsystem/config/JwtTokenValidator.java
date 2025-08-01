package com.harsh.projectmanagementsystem.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        // Bearer jwt

        if (jwt != null) {
            jwt = jwt.substring(7);

            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECREATE_KEY.getBytes());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt) // FIX: it was `.parserClaimsJws()` which is incorrect
                        .getBody();

                        String email = String.valueOf(claims.get("email"));
                        String authorities = String.valueOf( claims.get("authorities"));

                        List<GrantedAuthority>auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                      Authentication authentication = new UsernamePasswordAuthenticationToken(email,null, auths);

                      SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {

                throw new BadCredentialsException("Invalid credential...");

            }
        }


        filterChain.doFilter(request,response);
        
    }

}
