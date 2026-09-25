package mx.imss.ctirss.web.servlets;

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

import mx.imss.ctirss.model.DltFormapago;
import mx.imss.ctirss.model.DltPersona;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PRStream;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class MostrarImagenServlet extends HttpServlet {
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(MostrarImagenServlet.class);
	private static final long serialVersionUID = 1L;
	
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
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("EnviaArchivoServlet.processRequest");

		ServletOutputStream out = null;
		try {
		
			response.setContentType("application/download");

			byte[] buffer = null;

			out = response.getOutputStream();
			
			DltPersona dltPersona = (DltPersona)request.getSession().getAttribute("dltPersona");
			DltFormapago dltFormapago = (DltFormapago)request.getSession().getAttribute("dltFormapago");

			
			if(dltFormapago!=null){
				buffer  = dltFormapago.getRefDocumento();
				response.setHeader("Content-disposition", "inline; filename=" + java.net.URLEncoder.encode(dltFormapago.getNomDocumento(),"UTF-8") );
				request.getSession().removeAttribute("dltFormapago");
				
			}else if(dltPersona!=null){
				buffer  = dltPersona.getRefDocumento();
				response.setHeader("Content-disposition", "inline; filename=" + java.net.URLEncoder.encode(dltPersona.getNomDocumento(),"UTF-8") );
				request.getSession().removeAttribute("dltPersona");
				
			} 

			if(buffer != null){
				out.write(buffer);
				out.flush();				
			}
				


		} catch (Exception e) {
			System.out
					.println("EnviaArchivoServlet.processRequest: Excepcion al cerrar ServletOutputStream: "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			// Se cierra el ServletOutputStream
			try {
				if (out != null)
					out.close();
			} catch (IOException ioe) {
				System.out
						.println("EnviaArchivoServlet.processRequest: Excepcion al cerrar ServletOutputStream: "
								+ ioe.getMessage());
				ioe.printStackTrace();
			}

		}

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
