package mx.gob.imss.ctirss.sso.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import mx.gob.imss.ctirss.sso.admonusuarios.cte.Constantes;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ActivaCuentaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.EstatusDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.service.ActivaCuentaServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.service.BitacoraServiceLocal;
import mx.gob.imss.ctirss.sso.admonusuarios.services.AdmonUsuariosSessionLocal;

import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.faces.context.FacesContext;



public class ActivaCuentaServlet extends HttpServlet {
	
	
	@EJB
	private ActivaCuentaServiceLocal activaCuentaService;
	@EJB
	private AdmonUsuariosSessionLocal admonUsuarios;
	@EJB
	private BitacoraServiceLocal bitacoraService;

	private String cuenta = "";
	public static long ESTATUS_CUENTA_ACTIVA = 12;
	public static long ESTATUS_CUENTA_INACTIVA = 11;

	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(ActivaCuentaServlet.class);
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
	protected void processRequest(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("***************************process");
		String cuenta= (String)request.getParameter("cuenta");
		System.out.println("La cuenta a activar es: "+cuenta);
		System.out.println("*************************** end process");
		
		String result="";
		try {
			ActivaCuentaDTO activa = activaCuentaService.obtenActivaCuentaByClaveMD5(cuenta);
			if(activa!=null)
			{
				if(activa.getEstatus().getCveSsoestatus()==ESTATUS_CUENTA_INACTIVA)
				{
					Date hoy = new Date();
					if(activa.getFechaVigencia().getTime()>hoy.getTime())
					{
						admonUsuarios.activarUsuario(activa.getSolicitud().getDesUsrCurp());
						activa.setEstatus(new EstatusDTO(ESTATUS_CUENTA_ACTIVA));
						activa.setFechaActiva(hoy);
						activaCuentaService.actualizaEstatusActivaCuenta(activa);
						bitacoraService.guardaSolicitudBitActivacion(activa.getSolicitud().getCveSsosolicitud());
						result = "Su cuenta de usuario fue activada satisfactoriamente.";
					}
					else
						result = "La url para activar su cuenta de usuario ha cadudado.";
				}
				else
					result = "La cuenta de usuario ya ha sido activada con anterioridad.";

			}
			else
				result = "El url no es valido, comuniquese con el administrador del sistema de usuarios."; 
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			result = "Ocurrio un error al activar su cuenta de usuario";
		}

		
		 response.setContentType("text/html");
	     PrintWriter out = response.getWriter();

		 out.println("<html>");
		 out.println("<body>");
		 out.println("<h1>"+result+"</h1;>");
		 out.println("</body>");
		 out.println("</html>");

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
