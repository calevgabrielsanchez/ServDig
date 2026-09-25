package mx.gob.imss.ctirss.correccion.web.servlets.enviaArchivo;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.gob.imss.ctirss.correccion.session.ConstantesSession;
import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PRStream;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class EnviaArchivoServlet extends HttpServlet {
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(EnviaArchivoServlet.class);
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
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("EnviaArchivoServlet.processRequest");

		ServletOutputStream out = null;
		try {
			/*
			 * if (!super.validaSesionRepresentante(request)) {
			 * request.setAttribute(props.CODIGO_DE_ERROR,
			 * propsCod.SESION_TERMINADA);
			 * getServletConfig().getServletContext()
			 * .getRequestDispatcher("/").forward(request, response); return; }
			 */
			String archivo = request.getParameter(ConstantesSession.DOCUMENTO_PDF) != null ? (String) request.getParameter(ConstantesSession.DOCUMENTO_PDF) : null;
			String tipoDescarga = request.getParameter(ConstantesSession.TIPO_DESCARGA_PDF);
			String nombreArchivo = request.getParameter(ConstantesSession.NOMBRE_ARCHIVO_PDF) != null ? (String) request.getParameter(ConstantesSession.NOMBRE_ARCHIVO_PDF) : null;

			archivo = (String) request.getSession().getAttribute(ConstantesSession.DOCUMENTO_PDF);
			tipoDescarga = tipoDescarga == null ? (String) request.getSession().getAttribute(ConstantesSession.TIPO_DESCARGA_PDF) : tipoDescarga;
			nombreArchivo = (String) request.getSession().getAttribute(ConstantesSession.NOMBRE_ARCHIVO_PDF);

			request.getSession().removeAttribute(ConstantesSession.DOCUMENTO_PDF);
			request.getSession().removeAttribute(ConstantesSession.NOMBRE_ARCHIVO_PDF);

			response.setContentType("application/pdf");
			out = response.getOutputStream();
			if (tipoDescarga.equals(ConstantesSession.DESCARGA_PDF)) {
				response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo);
			} else if (tipoDescarga.equals(ConstantesSession.MUESTRA_PDF)) {
				response.setHeader("Content-disposition", "inline; filename=" + nombreArchivo);
			}

			byte[] bytesArch = Base64.decode(archivo);



			out.write(bytesArch);
			out.close();

		} catch (Exception e) {
			logger.error("EnviaArchivoServlet.processRequest: Excepcion al cerrar ServletOutputStream: "
							+ e.getMessage(), e);
			e.printStackTrace();
		} finally {
			// Se cierra el ServletOutputStream
			try {
				if (out != null)
					out.close();
			} catch (IOException ioe) {
				logger.warn("EnviaArchivoServlet.processRequest: Excepcion al cerrar ServletOutputStream: "
								+ ioe.getMessage());
				ioe.printStackTrace();
			}

		}

	}

	private byte[] agregarWaterMark(byte[] bytesArchivoJasper,
			OutputStream out, String documento) throws DocumentException,
			Exception {
		byte[] bytes2 = null;

		PdfReader pdfReader = new PdfReader(bytesArchivoJasper);

		PdfStamper stamper = new PdfStamper(pdfReader, out);
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI,
				BaseFont.EMBEDDED);
		PdfContentByte over;

		int total = pdfReader.getNumberOfPages() + 1;
		for (int i = 1; i < total; i++) {
			if (i == 1) {
				over = stamper.getOverContent(i);
				over.saveState();
				over.beginText();
				over.setColorFill(Color.CYAN);

				over.setFontAndSize(bf, 2);
				if ("prorroga.pdf".equals(documento)) {
					over.setColorFill(Color.GRAY);
					over.setTextMatrix(5f, 2.5f, -4f, 3.5f, 433f, 230f);
					over.showText("PRESENTADO POR INTERNET");
				}else if("presentacionCorrreccion.pdf".equals(documento)){
					over.setColorFill(Color.GRAY);
					over.setTextMatrix(5f, 2.5f, -4f, 3.5f, 433f, 70f);
					over.showText("PRESENTADO POR INTERNET");
				}else if("aviso.pdf".equals(documento)){
					over.setColorFill(Color.GRAY);
					over.setTextMatrix(5f, 2.5f, -4f, 3.5f, 433f, 70f);
					over.showText("PRESENTADO POR INTERNET");
				}else {
					over.setTextMatrix(12f, 2.5f, -4f, 4f, 40f, 40f);
					over.showText("PRESENTADO POR INTERNET");
					over.setFontAndSize(bf, 2);
				}
				over.endText();
				over.restoreState();
			}
		}
		stamper.close();

		PRStream stream = new PRStream(pdfReader, bytesArchivoJasper);

		bytes2 = stream.getBytes();

		return bytes2;
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

}
