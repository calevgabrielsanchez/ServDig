package mx.gob.imss.ctirss.gestionpersonas.offlineprocess;


import java.io.IOException;

import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.annotation.WebServlet;

//@WebServlet(name = "TramitePoolingServlet", urlPatterns = {"/TramitePoolingServlet"}, loadOnStartup=1)
public class SolicitudPoolingServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    SolicitudPoolingPendientesThread pendientesThread = null;
	SolicitudPoolingCancelacionThread cancelacionThread = null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=ISO-8859-1");
		PrintWriter out = response.getWriter();
		
		try {
			 
			out.println("<html>");
			out.println("<head>");
			out.println("<title>TramitePoolingServlet TramitePoolingServlet</title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>Servlet TramitePoolingServlet at " + request.getContextPath () + "</h1>");
			out.println("<br>");
			out.println("<br>");
			out.println("<br>");
			if (request.getParameter("start") != null){
				out.println("<p>Se inicia el pooler de solicitudes</p>");
				pendientesThread.bEjecutaProcesoOffline = true;
				cancelacionThread.bEjecutaProcesoOffline = true;
			}
			if (request.getParameter("stop") != null){
				out.println("<p>Se para el pooler de solicitudes</p>");
				pendientesThread.bEjecutaProcesoOffline = false;
				cancelacionThread.bEjecutaProcesoOffline = false;
			}
			if (request.getParameter("debug") != null){
				if (request.getParameter("debug").equals("true")){
					out.println("<p>Se activa el debug del pooler de solicitudes</p>");
					pendientesThread.bDebug = true;
					cancelacionThread.bDebug = true;
				}
				else{
					out.println("<p>Se desactiva el debug del pooler de solicitudes</p>");
					pendientesThread.bDebug = false;
					cancelacionThread.bDebug = false;
				}
			}			
			out.println("</body>");
			out.println("</html>");
		}
		finally {
			out.close();
		}
	}
	
	/*
	public void init(ServletConfig config) throws ServletException {
		System.out.println("*****************************");
		System.out.println("SolicitudPoolingServlet. INICIA POOLING");
		System.out.println("*****************************");

		try {
			System.out.println("SolicitudPoolingServlet. Inicia thread de cancelacion de solicitudes pendientes");
			cancelacionThread = new SolicitudPoolingCancelacionThread();
			cancelacionThread.start();			
			System.out.println("SolicitudPoolingServlet. Inicia thread de procesamiento de solicitudes pendientes");
			pendientesThread = new SolicitudPoolingPendientesThread();
			pendientesThread.start();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy(){
		System.out.println("**************************************");
		System.out.println("SolicitudPoolingServlet. FINALIZA POOLING");
		System.out.println("**************************************");
		pendientesThread.bTerminaHilo = true;
		cancelacionThread.bTerminaHilo = true;
	}
	*/
	
	@Override
	public String getServletInfo() {
		return "SolicitudPoolingServlet";
	}
}
