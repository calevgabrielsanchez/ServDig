package mx.gob.imss.cdsss.delta.portal.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mx.gob.imss.cit.dacvass.utils.CaptchaUtilSD;
import mx.gob.imss.cit.dacvass.utils.model.CaptchaSD;
import mx.gob.imss.cit.dacvass.utils.model.exception.CaptchaExceptionSD;



/**
 * Servlet implementation class CaptchaServlet
 */
public class CaptchaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			if (session != null) {
				response.setContentType("image/jpg");
				//BuilderCaptchaImage bci = new BuilderCaptchaImage();
				//session.setAttribute("captcha", bci.getImage(response));
				//session.setAttribute("captcha", CaptchaUtil.getImage(response));
				CaptchaSD captcha = new CaptchaSD(request, response);
				CaptchaUtilSD.generaCaptchaToSesion(captcha);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
		}catch(CaptchaExceptionSD e) {
			System.out.println("Error al generar el captcha: " + e);
		}catch (Exception e) {
			System.out.println("Exception: " + e);
			// do nothing
		}
	}	

}
