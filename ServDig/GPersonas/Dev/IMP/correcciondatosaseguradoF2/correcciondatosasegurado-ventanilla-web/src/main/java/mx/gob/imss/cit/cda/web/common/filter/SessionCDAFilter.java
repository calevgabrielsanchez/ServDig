package mx.gob.imss.cit.cda.web.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionCDAFilter implements Filter {

    private static final String MODULO_CDA = "CORRECCION DATOS ASEGURADO";
    protected static final String SSO_USER_SISTEMAS = "IMSS_SISTEMAS";
    private Logger log = LoggerFactory.getLogger(getClass());
    private String[] excludedURIs = new String[] { "/welcome", "/login",
            "/loginqa", "/resources", "/static" };

    @Override
    public void init(final FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(final ServletRequest servletRequest,
            final ServletResponse servletResponse, final FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String requestURI = extractRequestURI(request);

        UserProfile userProfile = (UserProfile) request.getSession()
                .getAttribute(SessionConstants.USER_PROFILE);
        String sso_list_sistemas = "";
        log.debug("RequestURI:  {}", requestURI);

        if (!isAProtectedPage(requestURI)
                || (sso_list_sistemas != null
                        && StringUtils.isNotBlank(sso_list_sistemas) && sso_list_sistemas
                        .toUpperCase().contains(MODULO_CDA))) {
            log.debug("---RECURSO NO PROTEGIDO ");
            chain.doFilter(request, response);
        } else {

            if (userProfile == null) {
                log.debug("No existe usuario en session.... ");
                request.getSession().invalidate();
                response.sendRedirect(request.getSession().getServletContext()
                        .getContextPath()
                        + "/loginqa");
            } else {
                log.debug("Usuario en sesion encontrado");
                chain.doFilter(request, response);

            }

        }

    }

    @Override
    public void destroy() {
    }

    private boolean isAProtectedPage(final String requestURI) {
        boolean isProtected = true;

        for (final String excludedURI : excludedURIs) {
            if (requestURI.startsWith(excludedURI)) {
                isProtected = false;
                break;
            }
        }
        return isProtected;
    }

    private String extractRequestURI(final HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        requestURI = requestURI.replace(request.getContextPath(), "");

        return requestURI;
    }

}