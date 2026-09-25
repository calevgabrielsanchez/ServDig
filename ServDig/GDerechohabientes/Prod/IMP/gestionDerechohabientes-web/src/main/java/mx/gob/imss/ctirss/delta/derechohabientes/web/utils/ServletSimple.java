package mx.gob.imss.ctirss.delta.derechohabientes.web.utils;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.servlet.*;

import javax.servlet.http.*;

import java.io.*;

public class ServletSimple extends HttpServlet

{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest req, HttpServletResponse res)

	throws ServletException, IOException

	{

		ServletOutputStream salida = res.getOutputStream();

		res.setContentType("text/html");

		String cadena = req.getParameter("TEXTO");
		
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();
//		System.out.println("****************************** print sevicr "+service.getName());

		salida.println("<p>Datos capturados : " + cadena + "</p>");

	}

}
