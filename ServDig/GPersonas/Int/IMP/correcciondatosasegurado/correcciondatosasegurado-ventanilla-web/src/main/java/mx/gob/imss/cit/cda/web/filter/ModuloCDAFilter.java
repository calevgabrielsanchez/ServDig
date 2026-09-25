package mx.gob.imss.cit.cda.web.filter;

import java.io.IOException;
import java.util.Arrays;

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

public class ModuloCDAFilter implements Filter {

	private static final String MODULO_CDA = "CORRECCION DATOS ASEGURADO";
	protected static final String SSO_USER_SISTEMAS = "IMSS_SISTEMAS";
	private Logger log = LoggerFactory.getLogger(getClass());
	private String[] excludedURIs = new String[]{"/welcome", "/login"};

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

		UserProfile userProfile = (UserProfile) request.getSession().getAttribute(SessionConstants.USER_PROFILE);
		String sso_list_sistemas = "";
		if(userProfile != null ){
			sso_list_sistemas = Arrays.deepToString(userProfile.getSistemas());
			log.debug("---CDA--- Se encontraron modulos asociados {} ", sso_list_sistemas);
		}
		
		log.debug("---CDA--- filtro CDA modulos {} ", sso_list_sistemas);
		// validar variable
		if (!isAProtectedPage(requestURI) 
				|| (sso_list_sistemas != null
					&& StringUtils.isNotBlank(sso_list_sistemas)
					&& sso_list_sistemas.toUpperCase().contains(MODULO_CDA))) {
			log.debug("---CDA--- modulo encontrado continuar cadena de filtros ");
			chain.doFilter(request, response);
		} else {
			// si no esta mandar a la raiz
			log.debug("---CDA--- volver al inicio del modulo ");
			request.getSession().invalidate();
			request.setAttribute("error", "No cuenta con el modulo CDA");
			response.sendRedirect(request.getSession().getServletContext().getContextPath()+"/"+SessionConstants.LOGOUT_URL);
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