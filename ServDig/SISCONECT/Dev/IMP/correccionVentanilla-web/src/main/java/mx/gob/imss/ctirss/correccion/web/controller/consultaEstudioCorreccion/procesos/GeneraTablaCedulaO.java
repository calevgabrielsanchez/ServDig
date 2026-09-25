package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.ConsultasEstudioCorreccion;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.CedulaOVO;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.CedulaQVO;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.ConsultaEstudioCorreccionVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Genera una tabla dinámica utilizando los datos obtenidos a
 * través de consultas ANSI SQL de la cédula O.
 * 
 * @author Enrique Duran Jimenez
 * @version 1.0.0
 *
 */
@Component
public class GeneraTablaCedulaO {

	/**
	 * Servicio genérico de consulta
	 */
	@SuppressWarnings("unused")
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	/**
	 * Permite ingresar los parámetros solicitados al SQL.
	 * 
	 * @param SQL
	 * @param folioCorreccion
	 * @param periodo
	 * @param registroPatronal
	 * @see ConsultasEstudioCorreccion
	 * @author Enrique Duran JImenez
	 * @return SQL parametrizado
	 */
	@SuppressWarnings("unused")
	private String setParameters(String SQL, String folioCorreccion, String periodo, String registroPatronal, String idSubDelegacion){
		
		SQL = SQL.replace("{1}", folioCorreccion);
		SQL = SQL.replace("{2}", periodo);
		if(registroPatronal!=null) SQL = SQL.replace("{3}", registroPatronal);
		if(idSubDelegacion!=null) SQL = SQL.replace("{4}", idSubDelegacion);
		
		return SQL;
		
	}
		
	/**
	 * Convierte el array de tipo Object (Object[])
	 * el cual fue arrojado por el servicio genérico
	 * a la consulta realizada y lo transforma a
	 * una lista de tipo CedulaOVO.
	 * @see CedulaOVO
	 * @param lista
	 * @author Enrique Duran Jimenez
	 * @return List<CedulaOVO>
	 */
	@SuppressWarnings("unused")
	private List<CedulaOVO> transformOBJListTo(List<?> lista){
		
		
		List<CedulaOVO> listCedulaO = new ArrayList<CedulaOVO>();
		
		if(lista!=null && !lista.isEmpty()){
			Iterator<?> iter = lista.iterator();
			Object[] currentObj = null;
			while(iter.hasNext()){
				currentObj = (Object[])iter.next();
					
				listCedulaO.add(new CedulaOVO(currentObj));
			}
		}
			return listCedulaO;
	
	}
	
	/**
	 * Obtiene a todos los registros patronales
	 * asociados al folio de corrección y al
	 * periodo ingresados.
	 * 
	 * @param cec
	 * @param registroPatronal
	 * @see ConsultasEstudioCorreccion
	 * @author Enrique Duran Jimenez
	 * @return
	 */
	private List<CedulaOVO>  obtenerDatosCedulaO(ConsultaEstudioCorreccionVO cec, String registroPatronal){
		String SQL=null; //O
		if(cec.getIdSubDelegacion()==null){
			SQL = ConsultasEstudioCorreccion.CONSULTA_CEDULA_O_NORMATIVO;
		}else{
			SQL = ConsultasEstudioCorreccion.CONSULTA_CEDULA_O;
		}
	
		SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),registroPatronal, cec.getIdSubDelegacion());
		
		List<CedulaOVO> listaCedulaO = ( (List<CedulaOVO>) transformOBJListTo(catalogoServiceBean.consultaSQL(SQL)));
		
		return listaCedulaO;
		
	}
	
	/**
	 * Procesa todos los datos obtenidos de la cédula O
	 * y los transforma en una tabla de HTML con CSS
	 *  
	 * @param cec
	 * @see ConsultasEstudioCorreccion
	 * @author Enrique Duran Jimenez
	 * @return Tabla cédula O
	 */
	public StringBuffer generarVistaCedula(ConsultaEstudioCorreccionVO cec){
		
		StringBuffer sb   = new StringBuffer();
		/*Obtenemos a los RPs involucrados en el periodo específico*/
		String SQL = ConsultasEstudioCorreccion.CONSULTA_RP_PERIODO_FOLIO_CEDULA_O;
		SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),null, null);
		
		Double tiempoExtraEnero;
		Double tiempoExtraFebrero;
		Double tiempoExtraMarzo;
		Double tiempoExtraAbril;
		Double tiempoExtraMayo;
		Double tiempoExtraJunio;
		Double tiempoExtraJulio;
		Double tiempoExtraAgosto;
		Double tiempoExtraSeptiembre;
		Double tiempoExtraOctubre;
		Double tiempoExtraNoviembre;
		Double tiempoExtraDiciembre;
		Double tiempoExtraTotal;
		
		String registroPatronal = "";
		List<?> listRPsAsociados = null;
		List<String> listRPUsr = null;
		
		if(cec.getRegistroPatronal().equals("")){
			listRPsAsociados = catalogoServiceBean.consultaSQL(SQL);
			
		}else{
			listRPUsr = new ArrayList<String>();
			listRPUsr.add(cec.getRegistroPatronal());
		}		
				
		if((listRPsAsociados!=null && !listRPsAsociados.isEmpty()) || (listRPUsr!=null && !listRPUsr.isEmpty()) ){
			
			Iterator<?> iterListaRPAsociados = (listRPsAsociados!=null) ? listRPsAsociados.iterator() : listRPUsr.iterator();
			
			CedulaOVO cedulaO = null;
			List<CedulaOVO> currentList;
			Iterator<CedulaOVO> iterCurrentList;
			
			int typeClassIndex = 0;
			String typeClass ="";
			
			while(iterListaRPAsociados.hasNext()){
				registroPatronal = String.valueOf(iterListaRPAsociados.next());
				
				typeClassIndex = 0;
				typeClass ="";
				
				 tiempoExtraEnero = 0.0;
				 tiempoExtraFebrero = 0.0;
				 tiempoExtraMarzo = 0.0;
				 tiempoExtraAbril = 0.0;
				 tiempoExtraMayo = 0.0;
				 tiempoExtraJunio = 0.0;
				 tiempoExtraJulio = 0.0;
				 tiempoExtraAgosto = 0.0;
				 tiempoExtraSeptiembre = 0.0;
				 tiempoExtraOctubre = 0.0;
				 tiempoExtraNoviembre = 0.0;
				 tiempoExtraDiciembre = 0.0;
				 tiempoExtraTotal = 0.0;
				 Double totaltiempoExtraTotal = 0.0;
				currentList = obtenerDatosCedulaO(cec, registroPatronal);
				iterCurrentList = currentList.iterator();
				if (iterCurrentList.hasNext()){						
					sb.append("<table width='900'  border='0' cellspacing='0' cellpadding='0'>\n").
					append("<tr>\n").
					append("<td class='fondoGeneralTabla'><table width='900' border='0' cellspacing='1' cellpadding='1'>\n").
					append("<tr>\n").
					append("<td colspan='17'><table width='100%'  border='0' cellspacing='0' cellpadding='0'>\n").
					append("<tr class='header'>\n").
					append("<td width='15%'>Registro Patronal </td>\n").
					append("<td width='85%' align='left'>"+registroPatronal+"</td>\n").
					append("</tr>\n").
					append("<tr class='header'>\n").
					append("<td colspan='2' align='center'>An&aacute;lisis de Tiempo Extra </td>\n").
					append("</tr>\n").					
					append("</table></td>\n").
					append("</tr>\n");
					
					sb.append("<tr>\n").
					append("<td width='150' class='subHeader'>Nombre</td>\n").
					append("<td width='150' class='subHeader'>Paterno </td>\n").
					append("<td width='150' class='subHeader'>Materno </td>\n").
					append("<td width='150' class='subHeader'>RFC </td>\n").
					append("<td width='150' class='subHeader'>Enero </td>\n").
					append("<td width='150' class='subHeader'>Febrero</td>\n").
					append("<td width='150' class='subHeader'>Marzo </td>\n").
					append("<td width='150' class='subHeader'>Abril </td>\n").
					append("<td width='150' class='subHeader'>Mayo </td>\n").
					append("<td width='150' class='subHeader'>Junio </td>\n").
					append("<td width='150' class='subHeader'>Julio </td>\n").
					append("<td width='150' class='subHeader'>Agosto </td>\n").
					append("<td width='150' class='subHeader'>Septiembre </td>\n").
					append("<td width='150' class='subHeader'>Octubre </td>\n").
					append("<td width='150' class='subHeader'>Noviembre </td>\n").
					append("<td width='150' class='subHeader'>Diciembre </td>\n").
					append("<td width='150' class='subHeader'>Total </td>\n").
					append("</tr>\n");
					      
	
					while(iterCurrentList.hasNext()){
						cedulaO = iterCurrentList.next();
						tiempoExtraTotal=0.0;
						typeClass = (typeClassIndex%2!=0) ? "txtTablaNone" : "txtTablaPar";
						 tiempoExtraEnero += cedulaO.getEnero() == "null" ? 0.0 : cedulaO.getEneroDbl();
						 tiempoExtraFebrero += cedulaO.getFebrero() == "null" ? 0.0 : cedulaO.getFebreroDbl();
						 tiempoExtraMarzo += cedulaO.getMarzo() == "null" ? 0.0 : cedulaO.getMarzoDbl();
						 tiempoExtraAbril += cedulaO.getAbril() == "null" ? 0.0 : cedulaO.getAbrilDbl();
						 tiempoExtraMayo += cedulaO.getMayo() == "null" ? 0.0 : cedulaO.getMayoDbl();
						 tiempoExtraJunio += cedulaO.getJunio() == "null" ? 0.0 : cedulaO.getJunioDbl();
						 tiempoExtraJulio += cedulaO.getJulio() == "null" ? 0.0 : cedulaO.getJulioDbl();
						 tiempoExtraAgosto += cedulaO.getAgosto() == "null" ? 0.0 : cedulaO.getAgostoDbl();
						 tiempoExtraSeptiembre += cedulaO.getSeptiembre() == "null" ? 0.0 : cedulaO.getSeptiembreDbl();
						 tiempoExtraOctubre += cedulaO.getOctubre() == "null" ? 0.0 : cedulaO.getOctubreDbl();
						 tiempoExtraNoviembre += cedulaO.getNoviembre() == "null" ? 0.0 : cedulaO.getNoviembreDbl();
						 tiempoExtraDiciembre += cedulaO.getDiciembre() == "null" ? 0.0 : cedulaO.getDiciembreDbl();
						 //tiempoExtraTotal += cedulaO.getTotalAnio() == null ? 0.0 : cedulaO.getTotalAnio();
						 tiempoExtraTotal=(cedulaO.getEnero() == "null" ? 0.0 : cedulaO.getEneroDbl())+
								 	(cedulaO.getFebrero() == "null" ? 0.0 : cedulaO.getFebreroDbl())+
								 	(cedulaO.getMarzo() == "null" ? 0.0 : cedulaO.getMarzoDbl())+
								 	(cedulaO.getAbril() == "null" ? 0.0 : cedulaO.getAbrilDbl())+
								 	(cedulaO.getMayo() == "null" ? 0.0 : cedulaO.getMayoDbl())+
								 	(cedulaO.getJunio() == "null" ? 0.0 : cedulaO.getJunioDbl())+
								 	(cedulaO.getJulio() == "null" ? 0.0 : cedulaO.getJulioDbl())+
								 	(cedulaO.getAgosto() == "null" ? 0.0 : cedulaO.getAgostoDbl())+
								 	(cedulaO.getSeptiembre() == "null" ? 0.0 : cedulaO.getSeptiembreDbl())+
								 	(cedulaO.getOctubre() == "null" ? 0.0 : cedulaO.getOctubreDbl())+
								 	(cedulaO.getNoviembre() == "null" ? 0.0 : cedulaO.getNoviembreDbl())+
								 	(cedulaO.getDiciembre() == "null" ? 0.0 : cedulaO.getDiciembreDbl());
						 totaltiempoExtraTotal+=tiempoExtraTotal;
						 System.out.println("tiempoExtraTotal "+tiempoExtraTotal);
						
						sb.append("<tr class='"+typeClass+"'>\n").
						append("<td class='"+typeClass+"'>"+cedulaO.getNombre()+"</td>\n").
						append("<td align='left' class='"+typeClass+"'>"+cedulaO.getApellidoPaterno() +"</td>\n").
						append("<td align='left' class='"+typeClass+"'>"+(cedulaO.getApellidoMaterno().equalsIgnoreCase("null") ? "&nbsp;" : cedulaO.getApellidoMaterno() )+"</td>\n").
						append("<td align='left' class='"+typeClass+"'>"+(cedulaO.getRfc().equalsIgnoreCase("null") ? "&nbsp;" : cedulaO.getRfc() )+ "</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getEnero() == "null" ? "0" : cedulaO.getEnero())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getFebrero() == "null" ? "0" : cedulaO.getFebrero())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getMarzo() == "null" ? "0" : cedulaO.getMarzo())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getAbril() == "null" ? "0" : cedulaO.getAbril())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getMayo() == "null" ? "0" : cedulaO.getMayo())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getJunio() == "null" ? "0" : cedulaO.getJunio())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getJulio() == "null" ? "0" : cedulaO.getJulio())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getAgosto() == "null" ? "0" : cedulaO.getAgosto())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getSeptiembre() == "null" ? "0" : cedulaO.getSeptiembre())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getOctubre() == "null" ? "0" : cedulaO.getOctubre())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getNoviembre() == "null" ? "0" : cedulaO.getNoviembre())+"</td>\n").						
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getDiciembre() == "null" ? "0" : cedulaO.getDiciembre())+"</td>\n").
						//append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaO.getTotalAnio() == null ? "0" : cedulaO.getTotalAnio().toString())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(tiempoExtraTotal)+"</td>\n").
						append("</tr>\n");
							
						typeClassIndex++;						
						
					}					
					
					sb.append("<tr>").
					append("<td class='subHeader' colspan='4'>Totales</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(tiempoExtraEnero)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(tiempoExtraFebrero)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(tiempoExtraMarzo)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(tiempoExtraAbril)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(tiempoExtraMayo)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(tiempoExtraJunio)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(tiempoExtraJulio)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(tiempoExtraAgosto)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(tiempoExtraSeptiembre)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(tiempoExtraOctubre)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(tiempoExtraNoviembre)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(tiempoExtraDiciembre)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(totaltiempoExtraTotal)+"</td>\n").
					append("</tr>\n");
					
					sb.append("</table></td>\n").
					append("</tr>\n").
					append("</table>\n");
					
					sb.append("<table border='0'><tr><td></td></tr></table>\n");
				} else {
					sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);
				}

			}
			// Cierra IF verificador de contenido RPS
		} else {
			sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);
		}
		return sb;

	} // Termina función generarVistacedulaO	
}
