package com.hurynovich.api_tester.security.filter;

import com.hurynovich.api_tester.security.service.JwtService;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component("jwtFilter")
public class JwtFilter extends GenericFilterBean implements Filter {

    private final JwtService jwtService;

    public JwtFilter(final @NonNull JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(final @NonNull ServletRequest servletRequest,
                         final @NonNull ServletResponse servletResponse,
                         final @NonNull FilterChain filterChain) throws IOException, ServletException {
        final String token = jwtService.fetchToken((HttpServletRequest) servletRequest);

        if (token != null && jwtService.validateToken(token)) {
            final Authentication authentication = jwtService.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

}
