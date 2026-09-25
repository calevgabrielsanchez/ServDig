package mx.gob.imss.cit.cda.web.common.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.cda.web.controller.RegistroSolicitudCorreccionDatosCurp;
import mx.gob.imss.cit.cda.web.utils.BuilderCaptchaImage;

public class CaptchaServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final Logger log = LoggerFactory
            .getLogger(RegistroSolicitudCorreccionDatosCurp.class);

    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            if (session != null) {
                response.setContentType("image/jpg");
                BuilderCaptchaImage bci = new BuilderCaptchaImage();
                session.setAttribute("captcha", bci.getImage(response));
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            log.debug("Exception: {}", e);
        }
    }
}
