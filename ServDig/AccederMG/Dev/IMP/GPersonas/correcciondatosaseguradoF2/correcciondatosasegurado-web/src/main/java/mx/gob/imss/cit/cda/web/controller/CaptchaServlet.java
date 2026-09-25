package mx.gob.imss.cit.cda.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.dacvass.utils.CaptchaUtilSD;
import mx.gob.imss.cit.dacvass.utils.model.CaptchaSD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaptchaServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final Logger log = LoggerFactory.getLogger(CaptchaServlet.class);

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            HttpSession session = request.getSession();
            CaptchaSD captchaSD = new CaptchaSD(request, response);
            if (session != null) {
                CaptchaUtilSD.generaCaptchaToSesion(captchaSD).getCaptchaValue();
                response.setContentType("image/jpg");
                response.getOutputStream().flush();
                response.getOutputStream().close();
            }
        } catch (Exception e) {
            log.debug("Exception: {}", e);
        }
    }
}
