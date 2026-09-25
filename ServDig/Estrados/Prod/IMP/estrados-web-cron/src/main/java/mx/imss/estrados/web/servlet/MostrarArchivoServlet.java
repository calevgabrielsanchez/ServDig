package mx.imss.estrados.web.servlet;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.imss.estrados.dto.DocumentosAdjuntosDTO;

import org.apache.log4j.Logger;

public class MostrarArchivoServlet extends HttpServlet {
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(MostrarArchivoServlet.class);
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
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DocumentosAdjuntosDTO documentosAdjuntosDTO = (DocumentosAdjuntosDTO)request.getSession().getAttribute("documentosAdjuntosDTO");
		
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(documentosAdjuntosDTO.getDesRefFilesystem());
		
		ServletOutputStream out = null;
		File file = null;
		InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        try {
        	file = new File(stringBuilder.toString());
            inputStream = new FileInputStream(file);
            
            if (documentosAdjuntosDTO.getDesNombreArchivo().contains(".pdf") || documentosAdjuntosDTO.getDesNombreArchivo().contains("PDF")) {
                byte[] byteArray = new byte[(int) file.length()];
                byteArrayOutputStream = new ByteArrayOutputStream();
                
                int bytesRead;
                while ((bytesRead = inputStream.read(byteArray)) != -1) {
                	byteArrayOutputStream.write(byteArray, 0, bytesRead);
                }
                
                if(byteArray != null) {
                	response.setContentType("application/pdf");
            		out = response.getOutputStream();
                	out.write(byteArrayOutputStream.toByteArray());
                	out.flush();
                }
			}
            
    		if (documentosAdjuntosDTO.getDesNombreArchivo().contains(".zip") || documentosAdjuntosDTO.getDesNombreArchivo().contains(".ZIP")) {
    			response.setContentType("application/zip");
        		response.addHeader("Content-Disposition", "attachment; filename=" +documentosAdjuntosDTO.getDesNombreArchivo());
        		response.setContentLength((int) file.length());

        		OutputStream responseOutputStream = response.getOutputStream();
        		
        		int bytes;
        		while ((bytes = inputStream.read()) != -1) {
        			responseOutputStream.write(bytes);
        		}
			}
            
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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

}
