package ru.itis.maletskov.restexample.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.itis.maletskov.restexample.models.Token;
import ru.itis.maletskov.restexample.models.User;
import ru.itis.maletskov.restexample.repositories.TokenRepository;
import ru.itis.maletskov.restexample.repositories.UserRepository;
import ru.itis.maletskov.restexample.security.authentication.TokenAuthentication;
import ru.itis.maletskov.restexample.security.details.UserDetailsImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class TokenAuthenticationFilter implements Filter {
    private final static String AUTH_HEADER = "AUTH";

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // вытаскиваем запрос
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // вытаскиваем заголовок с токеном
        String tokenValue = request.getHeader(AUTH_HEADER);
        // если заголовок содержит что-либо
        if (tokenValue != null) {
            // создаем объект токен-аутентификации
            TokenAuthentication authentication = new TokenAuthentication();
            // в него кладем токен
            authentication.setToken(tokenValue);
            Optional<Token> token = tokenRepository.findFirstByValue(tokenValue);
            if (token.isPresent()) {
                authentication.setUserDetails(new UserDetailsImpl(token.get().getUser(), token.get()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(400);
            }
        }
        // отдаем контексту
        // отдаем запрос дальше (его встретит либо другой фильтр, либо что-то еще)
        filterChain.doFilter(servletRequest, response);
    }
}
