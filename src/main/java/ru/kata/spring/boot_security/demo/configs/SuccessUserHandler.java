package ru.kata.spring.boot_security.demo.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        // Изменение: Сначала проверяем ROLE_USER
        if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/user"); // Перенаправление на /user для всех с ROLE_USER
        }
        // Добавлено: Отдельная проверка для только ROLE_ADMIN
        else if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/admin/all-users"); // Только ROLE_ADMIN идёт на /admin/all-users
        }
        // Без изменений: Если нет ролей
        else {
            httpServletResponse.sendRedirect("/login?error=no_roles");
        }
    }
}