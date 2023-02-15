package com.example.mailbe.Security;

import com.example.mailbe.Util.JwtUtil;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Collection;


@Component
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    public LoginFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        setFilterProcessesUrl("/api/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
        return super.getAuthenticationManager().authenticate(authentication);
    }


    @Override
    @SuppressWarnings("unchecked")
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        String email = String.valueOf(authResult.getPrincipal());
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authResult.getAuthorities();
        String accessToken = JwtUtil.getAccessToken(email, authorities);
        String refreshToken = JwtUtil.getRefreshToken(email, authorities);
        //saveTokenInBody(response,accessToken,refreshToken);

        try {
            JSONObject object = new JSONObject();
            object.put("access_token", accessToken);
            object.put("refresh_token", refreshToken);
            PrintWriter writer = response.getWriter();
            writer.write(object.toString());
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        try {
            super.unsuccessfulAuthentication(request, response, failed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveTokenInBody(HttpServletResponse response, String accessToken, String refreshToken) {
        try {
            JSONObject object = new JSONObject();
            object.put("access_token", accessToken);
            object.put("refresh_token", refreshToken);
            PrintWriter writer = response.getWriter();
            writer.write(object.toString());
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
