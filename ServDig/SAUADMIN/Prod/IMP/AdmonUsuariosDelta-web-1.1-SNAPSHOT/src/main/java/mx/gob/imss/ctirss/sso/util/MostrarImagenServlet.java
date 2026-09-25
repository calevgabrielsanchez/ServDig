package mx.gob.imss.ctirss.sso.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;

import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.octo.captcha.service.image.ImageCaptchaService;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

public class MostrarImagenServlet extends HttpServlet {
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(MostrarImagenServlet.class);
	private static final long serialVersionUID = 1L;
	
	private ImageCaptchaService captchaService = new DefaultManageableImageCaptchaService();
	/**
	 * Inicializa el servlet.
	 * 
	 * @param config
	 *            - Un objeto ServletConfig
	 * @throws ServletException
	 *             - Una excepcion general que un servlet lanza cuando encuentra
	 *             dificultades.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

	/**
	 * Destruye el servlet.
	 */
	public void destroy() {

	}

	/**
	 * Procesa los requests para los metodos HTTP <code>GET</code> y
	 * <code>POST</code> .
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             - Una excepcion general que un servlet lanza cuando encuentra
	 *             dificultades.
	 * @throws IOException
	 *             - Producida por operaciones de entrada y salida fallidas o
	 *             interrumpidas.
	 */
	@SuppressWarnings("unchecked")
	protected void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("***************************si entro");
		
		byte[] captchaChallengeAsJpeg = null;
		// the output stream to render the captcha image as jpeg into
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        
        
    	// get the session id that will identify the generated captcha. 
    	//the same id must be used to validate the response, the session id is a good candidate!
    	String captchaId = request.getSession().getId();
    	
    	// call the ImageCaptchaService getChallenge method
       	BufferedImage challenge = captchaService.getImageChallengeForID(captchaId);
        
//        // a jpeg encoder
//        JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
//        jpegEncoder.encode(challenge);
     

        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

        // flush it in the response
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();

	}


	/**
	 * Maneja el metodo HTTP <code>GET</code> .
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             - Una excepcion general que un servlet lanza cuando encuentra
	 *             dificultades.
	 * @throws IOException
	 *             - Producida por operaciones de entrada y salida fallidas o
	 *             interrumpidas.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Maneja el metodo HTTP <code>POST</code> .
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             - Una excepcion general que un servlet lanza cuando encuentra
	 *             dificultades.
	 * @throws IOException
	 *             - Producida por operaciones de entrada y salida fallidas o
	 *             interrumpidas.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Regresa una descripcion corta del servlet.
	 * 
	 * @return una descripcion corta del servlet.
	 */
	public String getServletInfo() {
		return "Envia un archivo al browser";
	}

    public javax.sql.DataSource getDataSource() throws NamingException,SQLException{
        Context ctx = new InitialContext();
        //Tomar la conexión del data source definido en weblogic
        javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("ds_ora_denuncia_view");
        return ds;
    }	
	

    /**
     * Obtiene la conexión a base de datos de un datasource de weblogic
     */
    public java.sql.Connection getConexion() throws NamingException, SQLException {
        javax.sql.DataSource ds = getDataSource();
        Connection con = ds.getConnection(); 
     return con;
   } 


}
