package com.johnsmith.SpringBootInstagramApi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.johnsmith.SpringBootInstagramApi.constants.SecurityConstant;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationRequestFilter extends OncePerRequestFilter {
	private final JwtUtils jwtUtils;
    private final UserPrincipalService userPrincipalService;
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader(SecurityConstant.AUTHORIZATION_HEADER);

        if (authHeader != null && authHeader.startsWith(SecurityConstant.BEARER_PREFIX)) {
            String token = authHeader.substring(SecurityConstant.TOKEN_INDEX);
            String username = this.jwtUtils.extractUsername(token);
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userPrincipalService.loadUserByUsername(username);
                if (this.jwtUtils.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }

        filterChain.doFilter(request, response);
	}
}
