package mx.gob.imss.ctirss.correccion.ajaxservice;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.gob.imss.ctirss.correccion.web.utils.TableHTMLFactory;

public class AjaxService extends HttpServlet {
	private static final long serialVersionUID = 1342342L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String back = "";
		PrintWriter out = response.getWriter();
		int op = Integer.parseInt(request.getParameter("op"));
		 
		try {
			
			switch (op) {
				case 1:
					back = getHtmlMovTable(request);
					break;
				case 2:	
					back = getHtmlMovTableDetalle(request);
					break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (back == null)
			back = "";
		out.write(back);
		out.flush();
		out.close();
	}
	
	public String getHtmlMovTable(HttpServletRequest request) throws Exception {
		String back = null;
		
		String rp = request.getParameter("rp");
		
		String[] colums = {"RP","Fecha de <br>presentaci&oacute;n", 
					"Fecha de <br>operaci&oacute;n", "Reingreso", 
					"Baja", "Modificaci&oacute;n<br>de Salario", "Suma"};
		
		String sql = "SELECT REG_PATRON, TO_CHAR(FECHA_MOV,'DD-MM-YYYY'),  " +
				"TO_CHAR(FECHA_OPERACION,'DD-MM-YYYY'), REINGRESO, BAJA, MOFIFICACION_SAL,SUMA " +
				"FROM VW_INFERFACE_AFILIACION WHERE REG_PATRON = '"+rp+"'";
		
		TableHTMLFactory thtmlf = TableHTMLFactory.getInstance();
		System.out.println("Query Movimientos: "+ sql);
//		thtmlf.setConnectionType(false);
		back = thtmlf.getTableSQLURL(sql, "Avisos Afiliatorios", colums, rp);
		
		return back;
	}
	
	public String getHtmlMovTableDetalle(HttpServletRequest request) throws Exception {
		String back = null;
		
		String rp = request.getParameter("rp");
		String fecha = request.getParameter("fecha");
		
		System.out.println("rp: "+ rp);
		System.out.println("fecha"+ fecha);
		String sql = "select  case TIPO_MOVIMIENTO when  2 then 'BAJA' when  7 then 'MODIFICACION DE SALARIO' " +
				"when 8 then 'REINGRESO' end as MOVIMIENTO, NSS, NOMBRE_TRAB AS NOMBRE, TO_CHAR(FECHA_MOV, 'DD-mm-YYYY') AS MOVIMIENTO, " +
				"TO_CHAR(FECHA_RECEPCION_LOTE, 'DD-mm-YYYY HH24:MI:SS') AS OPERACION, FOLIO_CORRECCION AS FOLIO, " +
				"CASE TIPO_EXITO when 0 THEN 'Aprobado' else 'Rechazado' end as SITUACION from IDSE_CORRECCION_DICTAMEN " +
				"where reg_patron = '" + rp + "' and fecha_mov = to_date('" + fecha + "', 'dd-mm-yyyy')";
				 		
		
		System.out.println("Query de detalle: "+ sql);
		TableHTMLFactory thtmlf = TableHTMLFactory.getInstance();
//		thtmlf.setConnectionType(false);
		back = thtmlf.getTableSQL(sql, "DETALLE", null);
		
		return back;
	}
}
