/**
 * Nos permite generar la tabla visual en formato HTML de la cédula Q
 * la cual fue ingresada por el patrón.
 */
package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos;


import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.ConsultasEstudioCorreccion;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.utils.Functions;

import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.CedulaQVO;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.ConsultaEstudioCorreccionVO;

/**
 * Genera una tabla dinámica utilizando los datos obtenidos a
 * través de consultas ANSI SQL de la cédula Q.
 * 
 * @author Gerardo Salazar Vega
 * @version 1.0.1
 *
 */
@Component
public class GeneraTablaCedulaQ {
	
	/**
	 * Servicio genérico de consulta
	 */
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
	 * @author Gerardo Salazar Vega
	 * @return SQL parametrizado
	 */
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
	 * una lista de tipo CedulaQVO.
	 * @see CedulaQVO
	 * @param lista
	 * @author Gerardo Salazar Vega
	 * @return List<CedulaQVO>
	 */
	private List<CedulaQVO> transformOBJListTo(List<?> lista){
		
		
		List<CedulaQVO> listCedulaQ = new ArrayList<CedulaQVO>();
		
		if(lista!=null && !lista.isEmpty()){
			Iterator<?> iter = lista.iterator();
			Object[] currentObj = null;
			while(iter.hasNext()){
				currentObj = (Object[])iter.next();
					
				listCedulaQ.add(new CedulaQVO(currentObj));
			}
		}
			return listCedulaQ;
	
	}
	
	/**
	 * Obtiene a todos los registros patronales
	 * asociados al folio de corrección y al
	 * periodo ingresados.
	 * 
	 * @param cec
	 * @param registroPatronal
	 * @see ConsultasEstudioCorreccion
	 * @author Gerardo Salazar Vega
	 * @return
	 */
	private List<CedulaQVO>  obtenerDatosCedulaQ(ConsultaEstudioCorreccionVO cec, String registroPatronal){
		
		String SQL ;
		
		if(cec.getIdSubDelegacion()==null){
			SQL = ConsultasEstudioCorreccion.CONSULTA_CEDULA_Q_NORMATIVO;
		}else{
			SQL = ConsultasEstudioCorreccion.CONSULTA_CEDULA_Q;
		}
		SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),registroPatronal, cec.getIdSubDelegacion());
		
		List<CedulaQVO> listaCedulaQ = ( (List<CedulaQVO>) transformOBJListTo(catalogoServiceBean.consultaSQL(SQL)));
		
		return listaCedulaQ;
		
	}
	
	/**
	 * Procesa todos los datos obtenidos de la cédula Q
	 * y los transforma en una tabla de HTML con CSS
	 *  
	 * @param cec
	 * @see ConsultasEstudioCorreccion
	 * @author Gerardo Salazar Vega
	 * @return Tabla cédula Q
	 */
	public StringBuffer generarVistaCedula(ConsultaEstudioCorreccionVO cec){
		
		StringBuffer sb   = new StringBuffer();
		/*Obtenemos a los RPs involucrados en el periodo específico*/
		String SQL = ConsultasEstudioCorreccion.CONSULTA_RP_PERIODO_FOLIO_CEDULA_Q;
		SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),null, null);
		
		Double honorariosEnero = 0.0;
		Double honorariosFebrero = 0.0;
		Double honorariosMarzo = 0.0;
		Double honorariosAbril = 0.0;
		Double honorariosMayo = 0.0;
		Double honorariosJunio = 0.0;
		Double honorariosJulio = 0.0;
		Double honorariosAgosto = 0.0;
		Double honorariosSeptiembre = 0.0;
		Double honorariosOctubre = 0.0;
		Double honorariosNoviembre = 0.0;
		Double honorariosDiciembre = 0.0;
		Double honorariosTotal = 0.0;
		Double totalHonorariosTotal=0.0;
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
			
			CedulaQVO cedulaQ = null;
			List<CedulaQVO> currentList;
			Iterator<CedulaQVO> iterCurrentList;
			
			int typeClassIndex = 0;
			String typeClass ="";
			
			while(iterListaRPAsociados.hasNext()){
				registroPatronal = String.valueOf(iterListaRPAsociados.next());
				
				typeClassIndex = 0;
				typeClass ="";
				
				currentList = obtenerDatosCedulaQ(cec, registroPatronal);
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
					append("<td colspan='2' align='center'>An&aacute;lisis de Honorarios </td>\n").
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
						cedulaQ = iterCurrentList.next();
				
						typeClass = (typeClassIndex%2!=0) ? "txtTablaNone" : "txtTablaPar";
						 honorariosEnero += cedulaQ.getEnero() == "null" ? 0.0 : cedulaQ.getEneroDbl();
						 honorariosFebrero += cedulaQ.getFebrero() == "null" ? 0.0 : cedulaQ.getFebreroDbl();
						 honorariosMarzo += cedulaQ.getMarzo() == "null" ? 0.0 : cedulaQ.getMarzoDbl();
						 honorariosAbril += cedulaQ.getAbril() == "null" ? 0.0 : cedulaQ.getAbrilDbl();
						 honorariosMayo += cedulaQ.getMayo() == "null" ? 0.0 : cedulaQ.getMayoDbl();
						 honorariosJunio += cedulaQ.getJunio() == "null" ? 0.0 : cedulaQ.getJunioDbl();
						 honorariosJulio += cedulaQ.getJulio() == "null" ? 0.0 : cedulaQ.getJulioDbl();
						 honorariosAgosto += cedulaQ.getAgosto() == "null" ? 0.0 : cedulaQ.getAgostoDbl();
						 honorariosSeptiembre += cedulaQ.getSeptiembre() == "null" ? 0.0 : cedulaQ.getSeptiembreDbl();
						 honorariosOctubre += cedulaQ.getOctubre() == "null" ? 0.0 : cedulaQ.getOctubreDbl();
						 honorariosNoviembre += cedulaQ.getNoviembre() == "null" ? 0.0 : cedulaQ.getNoviembreDbl();
						 honorariosDiciembre += cedulaQ.getDiciembre() == "null" ? 0.0 : cedulaQ.getDiciembreDbl();
						// honorariosTotal += cedulaQ.getTotalAnio() == null ? 0.0 : cedulaQ.getTotalAnio();
						
						 honorariosTotal= (cedulaQ.getEnero() == "null" ? 0.0 : cedulaQ.getEneroDbl())+
								 		   (cedulaQ.getFebrero() == "null" ? 0.0 : cedulaQ.getFebreroDbl())+
								 		   (cedulaQ.getMarzo() == "null" ? 0.0 : cedulaQ.getMarzoDbl())+
								 		   (cedulaQ.getAbril() == "null" ? 0.0 : cedulaQ.getAbrilDbl())+
								 		   (cedulaQ.getMayo() == "null" ? 0.0 : cedulaQ.getMayoDbl())+
								 		   (cedulaQ.getJunio() == "null" ? 0.0 : cedulaQ.getJunioDbl())+
								 		   (cedulaQ.getJulio() == "null" ? 0.0 : cedulaQ.getJulioDbl())+
								 		   (cedulaQ.getAgosto() == "null" ? 0.0 : cedulaQ.getAgostoDbl())+
								 		   (cedulaQ.getSeptiembre() == "null" ? 0.0 : cedulaQ.getSeptiembreDbl())+
								 		   (cedulaQ.getOctubre() == "null" ? 0.0 : cedulaQ.getOctubreDbl())+
								 		   (cedulaQ.getNoviembre() == "null" ? 0.0 : cedulaQ.getNoviembreDbl())+
								 		   (cedulaQ.getDiciembre() == "null" ? 0.0 : cedulaQ.getDiciembreDbl())
								 		   ;	
						 totalHonorariosTotal+=honorariosTotal;
						sb.append("<tr class='"+typeClass+"'>\n").
						append("<td class='"+typeClass+"'>"+cedulaQ.getNombre()+"</td>\n").
						append("<td align='left' class='"+typeClass+"'>"+cedulaQ.getApellidoPaterno() +"</td>\n").
						append("<td align='left' class='"+typeClass+"'>"+cedulaQ.getApellidoMaterno() +"</td>\n").
						append("<td align='left' class='"+typeClass+"'>"+cedulaQ.getRfc() +"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getEnero() == "null" ? "0" : cedulaQ.getEnero())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getFebrero() == "null" ? "0" : cedulaQ.getFebrero())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getMarzo() == "null" ? "0" : cedulaQ.getMarzo())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getAbril() == "null" ? "0" : cedulaQ.getAbril())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getMayo() == "null" ? "0" : cedulaQ.getMayo())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getJunio() == "null" ? "0" : cedulaQ.getJunio())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getJulio() == "null" ? "0" : cedulaQ.getJulio())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getAgosto() == "null" ? "0" : cedulaQ.getAgosto())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getSeptiembre() == "null" ? "0" : cedulaQ.getSeptiembre())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getOctubre() == "null" ? "0" : cedulaQ.getOctubre())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getNoviembre() == "null" ? "0" : cedulaQ.getNoviembre())+"</td>\n").						
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getDiciembre() == "null" ? "0" : cedulaQ.getDiciembre())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(honorariosTotal)+"</td>\n").
						//append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cedulaQ.getTotalAnio() == null ? "0" : cedulaQ.getTotalAnio().toString())+"</td>\n").
						append("</tr>\n");
							
						typeClassIndex++;						
						
					}					
					
					sb.append("<tr>").
					append("<td class='subHeader' colspan='4'>Totales</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(honorariosEnero)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(honorariosFebrero)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(honorariosMarzo)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(honorariosAbril)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(honorariosMayo)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(honorariosJunio)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(honorariosJulio)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(honorariosAgosto)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(honorariosSeptiembre)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(honorariosOctubre)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(honorariosNoviembre)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(honorariosDiciembre)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(totalHonorariosTotal)+"</td>\n").
					append("</tr>\n");
					
					sb.append("</table></td>\n").
					append("</tr>\n").
					append("</table>\n");

					sb.append("<table border='0'><tr><td></td></tr></table>\n");
				} else {
					sb.append( ConsultaEstudioCorreccionVO.tablaSinDatos);
				}

			}
			// Cierra IF verificador de contenido RPS
		} else {
			sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);
		}	
		
		return sb;
		
	} // Termina función generarVistaCedulaQ	

}
