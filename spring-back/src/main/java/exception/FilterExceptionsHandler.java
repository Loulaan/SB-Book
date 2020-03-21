package exception;

import dto.payload.response.ExceptionResponseDTO;
import security.jwt.exception.JwtAuthenticationException;
import util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class FilterExceptionsHandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtAuthenticationException | AuthenticationServiceException exception) {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            String errorMessage = exception.getMessage();
            if (!errorMessage.isEmpty()) {
                response.getWriter().write(Util.convertObjectToJson(new ExceptionResponseDTO(errorMessage)));
                response.getWriter().flush();
            }
        }
    }
}
