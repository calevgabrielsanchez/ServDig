package mx.gob.imss.ctirss.delta.gestion.patronal.web.utils;


import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import org.apache.soap.encoding.soapenc.Base64;
//import weblogic.utils.encoders.BASE64Decoder;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PRStream;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class EnviaArchivoServlet extends HttpServlet {

	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(EnviaArchivoServlet.class);
	private static final long serialVersionUID = 1L;
	
	public static final String DOCUMENTO_PDF = "documento";
	public static final String NOMBRE_ARCHIVO_PDF = "nombreArchivo";
	public static final String TIPO_DESCARGA_PDF = "tipoDescarga";
	public static final String DESCARGA_PDF = "descarga";
	public static final String MUESTRA_PDF = "muestra";
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
		
			String archivo = (String) request.getSession().getAttribute(this.DOCUMENTO_PDF);
			String tipoDescarga = (String) request.getSession().getAttribute(this.TIPO_DESCARGA_PDF);
			String nombreArchivo = (String) request.getSession().getAttribute(this.NOMBRE_ARCHIVO_PDF);

		

			response.setContentType("application/pdf");
			out = response.getOutputStream();
			if (tipoDescarga.equals(this.DESCARGA_PDF)) {
				response.setHeader("Content-disposition", "attachment; filename=" + nombreArchivo);
			} else if (tipoDescarga.equals(this.MUESTRA_PDF)) {
				response.setHeader("Content-disposition", "inline; filename=" + nombreArchivo);
			}

			byte[] bytesArch = Base64.decode(archivo);

			bytesArch=estampaPDF(out,bytesArch);

			out.write(bytesArch);
			out.close();

			
			request.getSession().removeAttribute(this.DOCUMENTO_PDF);
			request.getSession().removeAttribute(this.TIPO_DESCARGA_PDF);
			request.getSession().removeAttribute(this.NOMBRE_ARCHIVO_PDF);
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
			OutputStream out) throws DocumentException,
			Exception {
		byte[] bytes2 = null;
		System.out.println("Generando Marca de agua");
		
		PdfReader pdfReader = new PdfReader(bytesArchivoJasper);
		PdfStamper stamper = new PdfStamper(pdfReader, out);
		
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI,
				BaseFont.EMBEDDED);
		PdfContentByte over;

		int total = pdfReader.getNumberOfPages() + 1;
		System.out.println("Total Paginas");
		for (int i = 1; i < total; i++) {
			
				over = stamper.getOverContent(i);
				over.saveState();
				over.beginText();
				over.setColorFill(Color.CYAN);
				over.setFontAndSize(bf, 15);
				over.setTextMatrix(12f, 2.5f, -4f, 4f, 100f, 100f);
				over.showText("MUESTRA");
				over.setFontAndSize(bf, 15);			
				over.endText();
				over.restoreState();
			
		}
		stamper.close();

		PRStream stream = new PRStream(pdfReader, bytesArchivoJasper);

		bytes2 = stream.getBytes();

		return bytes2;
	}
	
	
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	
	
	public byte[] estampaPDF(OutputStream outputStream,byte[] bytesArchivoJasper) {
		byte[] bytes2=null;
		try {
		 
				System.out.println("Estampandoo TEXT_RENDER_MODE_FILL");
		 
		     PdfReader pdfReader = new PdfReader(bytesArchivoJasper);
		     PdfStamper pdfStamper = new PdfStamper(pdfReader,outputStream);
		 
		     float alto_pagina = 0;
		     float ancho_pagina = 0;
		     float angulo_marca_agua=0;
		 
		     //Por cada una de las páginas
		     for(int i=1; i<= pdfReader.getNumberOfPages(); i++){
		 
		          
		          PdfContentByte content_fondo = pdfStamper.getUnderContent(i);
		 
		           alto_pagina = pdfReader.getPageSize(i).getHeight();
	               ancho_pagina = pdfReader.getPageSize(i).getWidth();
	               angulo_marca_agua = 60;
		 
		 
		         //Marca de agua
		         String marca_agua= "MUESTRA";

		 
		         //Marca de agua en color gris, rotada y con relleno blanco
		         Phrase frase_marca_agua= new Phrase(marca_agua, FontFactory.getFont(BaseFont.HELVETICA, 110, Font.BOLD));
		         content_fondo.setTextRenderingMode(PdfContentByte.TEXT_RENDER_MODE_FILL_STROKE);
		         content_fondo.setColorStroke(new Color(215,215,215));
		         content_fondo.setColorFill(new Color(215,215,215));
		 
		         ColumnText.showTextAligned(content_fondo, Element.ALIGN_CENTER, frase_marca_agua,ancho_pagina/2 , alto_pagina/2, angulo_marca_agua);
		 
		      }
		 
		      pdfStamper.close();
		      
		      
		      PRStream stream = new PRStream(pdfReader, bytesArchivoJasper);

		      bytes2= stream.getBytes();
		 
		      return bytes2;
		      } catch (IOException e) {
		      // log.severe(e.toString());
		    	  e.printStackTrace();
		      } catch (DocumentException e) {
		    	  e.printStackTrace();
		     // log.severe(e.toString());
		      }
		return bytes2;
		 }
	
}
