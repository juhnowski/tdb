package ru.simplgroupp.webapp.terrorist.filter;

import org.apache.commons.codec.binary.Base64;
import ru.simplgroupp.webapp.terrorist.model.UserEntity;
import ru.simplgroupp.webapp.terrorist.service.UserService;

import javax.ejb.EJB;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 10.08.2015
 * 14:45
 */

public class AuthenticationFilter implements Filter {
    private final String SESSION_USER_PARAM = "authenticated-user";

    @EJB
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        UserEntity user = (UserEntity) request.getSession().getAttribute(SESSION_USER_PARAM);

        String authHeader = request.getHeader("Authorization");
        if (user == null && authHeader != null && authHeader.startsWith("Basic")) {
            String[] credentials = new String(
                    Base64.decodeBase64(authHeader.substring("Basic".length()).trim()),
                    Charset.forName("UTF-8")
            ).split(":", 2);
            user = userService.getUserByLoginAndPassword(credentials[0], credentials[1]);
        }

        if (user == null) {
            response.setHeader("WWW-Authenticate", "Basic realm=\"Access allowed only for registered users.\"");
            response.setStatus(401);
        } else {
            request.getSession().setAttribute(SESSION_USER_PARAM, user);
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {
    }
}
