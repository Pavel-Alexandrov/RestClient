package client.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class AuthHandler implements AuthenticationSuccessHandler {

    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        boolean isUser = false;
        boolean isAdmin = false;

        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities) {
            if (grantedAuthority.getAuthority().equals("admin")) {
                isAdmin = true;
            } else if (grantedAuthority.getAuthority().equals("user")) {
                isUser = true;
            }
        }

        if (isAdmin) {
            redirectStrategy.sendRedirect(request, response, "/admin/home");
        } else if (isUser) {
            redirectStrategy.sendRedirect(request, response, "/user/home");
        } else {
            throw new IOException("не установлены права");
        }
    }
}
