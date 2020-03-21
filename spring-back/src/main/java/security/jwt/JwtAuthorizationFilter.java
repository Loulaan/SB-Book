package security.jwt;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import security.jwt.service.JwtTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Setter
@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {

    private JwtTokenService jwtTokenService;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        String token = jwtTokenService.handleToken((HttpServletRequest) servletRequest);

        if (token == null) {
            throw new AuthenticationServiceException(StringUtils.EMPTY);
        }

        if (jwtTokenService.isTokenValid(token)) {
            Authentication authentication = jwtTokenService.getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
