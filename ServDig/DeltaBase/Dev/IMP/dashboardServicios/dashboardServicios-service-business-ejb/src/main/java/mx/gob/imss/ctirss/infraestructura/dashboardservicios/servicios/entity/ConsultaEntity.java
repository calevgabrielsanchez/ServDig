package mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.entity;
 
import java.io.BufferedReader;
import java.io.Reader;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.LinkedList;
import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.infraestructura.comun.bd.Conexion4;
import com.infraestructura.comun.bd.Registro;

//import mx.gob.imss.ctirss.delta.gestion.patron.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.infraestructura.framework.servicios.AbstractServiceEntity;

import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.BitacoraServicios;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Operacion;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Servicio;

/**
 * @author Joaquin Esteban Ponte Díaz
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 20/01/2012
 */
@Stateless
public class ConsultaEntity extends AbstractServiceEntity implements ConsultaEntityLocal{
	
	public final int TIPO_BITACORA_ENTRADA = 1;
	public final int TIPO_BITACORA_SALIDA = 2;
	
	public static final String DATE_FORMAT = "yyyy-MM-dd H:mm:ss:SSS";
	
	
	
	@Override
	public List<Servicio> getServicios(String sCveSistema){
		LinkedList<Servicio> listaServicios = new LinkedList<Servicio>();
		Conexion4 datos = new Conexion4();
		try{
			datos.abreConexion("jdbc/modificaciones");
			datos.sSql = "\n SELECT * FROM INF_SERVICIO WHERE CVE_SISTEMA='" + sCveSistema + "' ORDER BY CVE_SERVICIO \n ";
			log.debug(datos.sSql);
			datos.ejecutaSql();
			while (datos.rs.next()){
				Servicio servicio = new Servicio();
				servicio.setCveServicio(datos.rs.getString("CVE_SERVICIO"));
				servicio.setDesServicio(datos.rs.getString("CVE_SERVICIO"));
				listaServicios.add(servicio);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			// VERIFICA SI SE INSTANCIO LA CLASE DAO
			if (datos != null) {
				// VERIFICA SI TIENE ABIERTA LA CONEXION A BD
				if (datos.isConectado()) {
					// CIERRA LA CONEXION
					datos.cierraConexion();
				}
			}
		}
		return listaServicios;		
	}
	
	public List<Operacion> getOperaciones(String sCveServicio){
		LinkedList<Operacion> listaOperaciones = new LinkedList<Operacion>();
		Conexion4 datos = new Conexion4();
		try{
			datos.abreConexion("jdbc/modificaciones");
			datos.sSql = "\n SELECT * FROM INF_SERVICIO_OPERACION WHERE CVE_SERVICIO='" + sCveServicio + "' ORDER BY CVE_OPERACION \n ";
			log.debug(datos.sSql);
			datos.ejecutaSql();
			while (datos.rs.next()){
				Operacion operacion = new Operacion();
				operacion.setCveOperacion(datos.rs.getString("CVE_OPERACION"));
				operacion.setDesOperacion(datos.rs.getString("DES_OPERACION"));
				listaOperaciones.add(operacion);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			// VERIFICA SI SE INSTANCIO LA CLASE DAO
			if (datos != null) {
				// VERIFICA SI TIENE ABIERTA LA CONEXION A BD
				if (datos.isConectado()) {
					// CIERRA LA CONEXION
					datos.cierraConexion();
				}
			}
		}
		return listaOperaciones;		
	}
	
	public List<BitacoraServicios> getBitacora(BitacoraServicios filtroBusqueda, int iTipoOrdenamiento){
		LinkedList<BitacoraServicios> listaBitacora = new LinkedList<BitacoraServicios>();
		Conexion4 datos = new Conexion4();
		try{
	    	SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	    	
			datos.abreConexion("jdbc/modificaciones");
			datos.sSql = "\n SELECT BIT.*, SIS.*  \n" +
						"  FROM INF_SERVICIO_BITACORA BIT, INF_SISTEMA SIS, INF_SERVICIO SER \n " +
						"  WHERE BIT.CVE_SERVICIO = SER.CVE_SERVICIO \n " +
						  "    AND SER.CVE_SISTEMA = SIS.CVE_SISTEMA \n " +
						  "    AND BIT.FH_FINAL IS NOT NULL \n ";

			if (filtroBusqueda != null){
				if (filtroBusqueda.getCveSistema() != null){
					datos.sSql = datos.sSql + "    AND SIS.CVE_SISTEMA ='" + filtroBusqueda.getCveSistema() + "'\n ";
				}
				if (filtroBusqueda.getCveServicio() != null){
					datos.sSql = datos.sSql + "    AND SER.CVE_SERVICIO ='" + filtroBusqueda.getCveServicio() + "'\n ";
				}
				if (filtroBusqueda.getCveOperacion() != null){
					datos.sSql = datos.sSql + "    AND BIT.CVE_OPERACION ='" + filtroBusqueda.getCveOperacion() + "'\n ";
				}
				if (filtroBusqueda.getFhInicio() != null){
					datos.sSql = datos.sSql + "    AND BIT.FH_INICIO >= to_timestamp('" +sdf.format(filtroBusqueda.getFhInicio().toGregorianCalendar().getTime()) + "', 'YYYY-MM-DD HH24:MI:SS:FF3') \n ";	
				}
				if (filtroBusqueda.getFhFinal() != null){
					datos.sSql = datos.sSql + "    AND BIT.FH_FINAL <= to_timestamp('" +sdf.format(filtroBusqueda.getFhFinal().toGregorianCalendar().getTime()) + "', 'YYYY-MM-DD HH24:MI:SS:FF3') \n ";	
				}
			}
			
			if (iTipoOrdenamiento == TIPO_ORDENAMIENTO_SERVICIO){
				datos.sSql = datos.sSql + "  ORDER BY BIT.CVE_SERVICIO, BIT.CVE_OPERACION, BIT.ID_BITACORA \n";
			}
			else{
				datos.sSql = datos.sSql + "  ORDER BY BIT.ID_BITACORA, BIT.CVE_SERVICIO, BIT.CVE_OPERACION \n";
			}
			log.debug(datos.sSql);
			datos.ejecutaSql();			
			int iMaxResultados = 0;
			while (datos.rs.next()){
				if (iMaxResultados++ == 500){
					break;
				}
				BitacoraServicios bitacora = new BitacoraServicios();
				bitacora.setIdBitacora(datos.rs.getLong("ID_BITACORA"));
				bitacora.setCveServicio(datos.rs.getString("CVE_SERVICIO"));
				bitacora.setDesServicio(datos.rs.getString("CVE_SERVICIO"));
				bitacora.setCveOperacion(datos.rs.getString("CVE_OPERACION"));
				bitacora.setDesOperacion(datos.rs.getString("CVE_OPERACION"));
				bitacora.setCveSistema(datos.rs.getString("CVE_SISTEMA"));
				bitacora.setDesSistema(datos.rs.getString("DES_SISTEMA"));
				bitacora.setFhInicio(dateToGregorianCalendar(datos.rs.getTimestamp("FH_INICIO")));
				bitacora.setFhFinal(dateToGregorianCalendar(datos.rs.getTimestamp("FH_FINAL")));
				bitacora.setTiempoMilisegundos(datos.rs.getLong("TIEMPO_MS"));
				bitacora.setTiempoSegundos(datos.rs.getLong("TIEMPO_SG"));
				listaBitacora.add(bitacora);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			// VERIFICA SI SE INSTANCIO LA CLASE DAO
			if (datos != null) {
				// VERIFICA SI TIENE ABIERTA LA CONEXION A BD
				if (datos.isConectado()) {
					// CIERRA LA CONEXION
					datos.cierraConexion();
				}
			}
		}
		return listaBitacora;		
	}
	
	public XMLGregorianCalendar dateToGregorianCalendar(java.util.Date fecha){
		XMLGregorianCalendar fechaXml = null;
		try{
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(fecha);
			fechaXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return fechaXml;	
	}
	
	public String getBitacoraEntradaSalida(Long iIdBitacora, int iTipoBitacora){
		Conexion4 datos = new Conexion4();
		String sBitacoraLog = "";
		try{
			datos.abreConexion("jdbc/modificaciones");
			datos.sSql = "\n SELECT * FROM INF_SERVICIO_BITACORA BIT WHERE ID_BITACORA = " + iIdBitacora + " \n";
			log.debug(datos.sSql);
			datos.ejecutaSql();
			Registro registro = datos.getConsultaRegistro(Conexion4.CLOB_PROCESA, Conexion4.CLOB_TRUNCA_DATOS, 30, 140, Conexion4.BLOB_NO_PROCESA);
			
			if (registro != null){
				if (iTipoBitacora == TIPO_BITACORA_SALIDA){
					sBitacoraLog = (String)registro.getDefCampo("SALIDA_HTML");
				}
				else{
					sBitacoraLog = (String)registro.getDefCampo("ENTRADA_HTML");
				}				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			// VERIFICA SI SE INSTANCIO LA CLASE DAO
			if (datos != null) {
				// VERIFICA SI TIENE ABIERTA LA CONEXION A BD
				if (datos.isConectado()) {
					// CIERRA LA CONEXION
					datos.cierraConexion();
				}
			}
		}
		return sBitacoraLog;
	}
	
	public String getBitacoraSalida(Long iIdBitacora){
		return getBitacoraEntradaSalida(iIdBitacora, TIPO_BITACORA_SALIDA);	
	}

	public String getBitacoraEntrada(Long iIdBitacora){
		return getBitacoraEntradaSalida(iIdBitacora, TIPO_BITACORA_ENTRADA);
		
	}
	
	private String clobToString(Clob data) {
	    StringBuilder sb = new StringBuilder();
	    if (data !=null){
		    try {
		        Reader reader = data.getCharacterStream();
		        BufferedReader br = new BufferedReader(reader);

		        String line;
		        while(null != (line = br.readLine())) {
		            sb.append(line);
		        }
		        br.close();
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }	    	
	    }
	    return sb.toString();
	}	
}