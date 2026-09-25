/**
 * Nos permite generar la tabla visual en formato HTML de la cédula A
 * la cual fue ingresada por el patrón.
 */
package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.ConsultasEstudioCorreccion;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.CedulaAVO;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.ConsultaEstudioCorreccionVO;

/**
 * Genera una tabla dinámica utilizando los datos obtenidos a
 * través de consultas ANSI SQL de la cédula A.
 * 
 * @author Marco Antonio Nieto Plett
 * @author Gerardo Salazar Vega
 * @version 1.0.4
 */
@Component
public class GeneraTablaCedulaA {
	
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
	 * @author Marco Antonio Nieto Plett
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
	 * una lista de tipo CedulaAVO.
	 * @see CedulaAVO
	 * @param lista
	 * @author Marco Antonio Nieto Plett
	 * @return List<CedulaAVO>
	 */
	private List<CedulaAVO> transformOBJListTo(List<?> lista){
		
		List<CedulaAVO> listCedulaA = new ArrayList<CedulaAVO>();
		
		if(lista!=null && !lista.isEmpty()){
			Iterator<?> iter = lista.iterator();
			Object[] currentObj = null;
			
			
			while(iter.hasNext()){
				currentObj = (Object[])iter.next();
					
				listCedulaA.add(new CedulaAVO(currentObj));
			}
		}
			return listCedulaA;
	
	}
	
	/**
	 * Obtiene a todos los registros patronales
	 * asociados al folio de corrección y al
	 * periodo ingresados.
	 * 
	 * @param cec
	 * @param registroPatronal
	 * @see ConsultasEstudioCorreccion
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	private List<CedulaAVO>  obtenerDatosCedulaAByRP(ConsultaEstudioCorreccionVO cec, String registroPatronal){
		String SQL;
		if(cec.getIdSubDelegacion()==null){
			SQL = ConsultasEstudioCorreccion.CONSULTA_CEDULA_A_NORMATIVO;
		}else{
			SQL = ConsultasEstudioCorreccion.CONSULTA_CEDULA_A;
		}
		
		SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),registroPatronal,cec.getIdSubDelegacion());
		System.out.println("SQL consulta ced "+SQL);
		List<CedulaAVO> listaByRPCedulaA = ( (List<CedulaAVO>) transformOBJListTo(catalogoServiceBean.consultaSQL(SQL)));
		System.out.println("Tam "+listaByRPCedulaA.size());
		return listaByRPCedulaA;
		
	}
	
	/**
	 * Procesa todos los datos obtenidos de la cédula A
	 * y los transforma en una tabla de HTML con CSS
	 *  
	 * @param cec
	 * @see ConsultasEstudioCorreccion
	 * @author Marco Antonio Nieto Plett
	 * @return Tabla cédula A
	 */
	public StringBuffer generarVistaCedula(ConsultaEstudioCorreccionVO cec){
		
		StringBuffer sb   = new StringBuffer();
		/*Obtenemos a los RPs involucrados en el periodo específico*/
		String SQL = ConsultasEstudioCorreccion.CONSULTA_RP_PERIODO_FOLIO_CEDULA_A;
		SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),null, null);
		
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
			
			CedulaAVO currentCedula = null;
			
			Hashtable<String,Double> sumaColumGastos = null;
			Hashtable<String,Double> sumaFilaGastos = null;
			Hashtable<String,Double> sumaAuxNomina = null;
			Hashtable<String,List<CedulaAVO>> tablaCedulaA = null;
			
			Double suma = 0.0;
			int colSpanGastos = 0;
		
			Double totalNomAuxiliar = 0.0;
			
			while(iterListaRPAsociados.hasNext()){
				 colSpanGastos = 0;
				 totalNomAuxiliar = 0.0;
				 sumaColumGastos = new Hashtable<String,Double>();
				 sumaFilaGastos = new Hashtable<String,Double>();
				 sumaAuxNomina = new Hashtable<String,Double>();
				 tablaCedulaA = new Hashtable<String,List<CedulaAVO>>();
				  	
				registroPatronal = 	(String)iterListaRPAsociados.next();		
				List<CedulaAVO> listDatosCedulaAByRP = obtenerDatosCedulaAByRP(cec,registroPatronal);
				
				if(!listDatosCedulaAByRP.isEmpty()){
					Iterator<CedulaAVO> iterCedula = listDatosCedulaAByRP.iterator();
					Iterator<CedulaAVO> iterRow = null;
					
					//Genera Sumatorias y Organiza los datos por Percepción
					while(iterCedula.hasNext()){
						currentCedula = iterCedula.next();
						
						if(sumaColumGastos.containsKey(currentCedula.getTxGastos())){							
							suma = sumaColumGastos.get(currentCedula.getTxGastos());
							suma+= Double.parseDouble(currentCedula.getImRemuneracion());
							sumaColumGastos.put(currentCedula.getTxGastos(),suma);
						}else{
							sumaColumGastos.put(currentCedula.getTxGastos(), Double.parseDouble(currentCedula.getImRemuneracion()));
							colSpanGastos++;
						}
						
						if(sumaFilaGastos.containsKey(currentCedula.getTxRemuneracion())){							
							suma = sumaFilaGastos.get(currentCedula.getTxRemuneracion());
							suma+= Double.parseDouble(currentCedula.getImRemuneracion());
							sumaFilaGastos.put(currentCedula.getTxRemuneracion(),suma);
							
						}else{
							sumaFilaGastos.put(currentCedula.getTxRemuneracion(), Double.parseDouble(currentCedula.getImRemuneracion()));
						}
						
						if(!sumaAuxNomina.containsKey(currentCedula.getTxRemuneracion())){							
							sumaAuxNomina.put(currentCedula.getTxRemuneracion(), Double.parseDouble(currentCedula.getImAuxiliarNomina()));
							totalNomAuxiliar+= Double.parseDouble(currentCedula.getImAuxiliarNomina());
						}
						
						if(tablaCedulaA.containsKey(currentCedula.getTxRemuneracion())){
							tablaCedulaA.get(currentCedula.getTxRemuneracion()).add(currentCedula);
						}else{
							List<CedulaAVO> newList = new ArrayList<CedulaAVO>();
							newList.add(currentCedula);
							tablaCedulaA.put(currentCedula.getTxRemuneracion(),newList);
						}
						
						
						
					}//Fin de sumatoras y organización
					
					
					sb.append("<table  border='0' cellspacing='0' cellpadding='0'>\n").
					append("<tr>\n").
					append("<td class='fondoGeneralTabla'><table width='100%' border='0' cellspacing='1' cellpadding='1'>\n").
					append("<tr class='header'>\n").
					append("<td width='150'>Registro Patronal </td>\n").
					append("<td colspan='"+(colSpanGastos+1)+"' align='center'><div align='left'>"+registroPatronal+"</div></td>\n").
					append("<td>&nbsp;</td>\n").
					append("</tr>\n").
					append("<tr>\n").
					append("<tr class='header'>\n").
					append("<td width='200'><div align='center'>Concepto</div></td>\n").
					append("<td colspan='"+(colSpanGastos+1)+"' align='center'>Balanza de Comprobaci&oacute;n </td>\n").
					append("<td width='200'><div align='center'>Auxiliar de Nominas </div></td>\n").
					append("</tr>\n");
					      
					sb.append("<tr class='subHeader'>\n").
					append("<td>&nbsp;</td>\n");
					
					//ciclo para procesar títulos
					Enumeration<String> rowKey = tablaCedulaA.keys();
					String remuneracion = "";
				
					List<String> lsTitleGastos = new ArrayList<String>();
					while(rowKey.hasMoreElements()){
						remuneracion = rowKey.nextElement();
						iterRow = tablaCedulaA.get(remuneracion).iterator();
						while(iterRow.hasNext()){
							currentCedula = iterRow.next();
							if(!lsTitleGastos.contains(currentCedula.getTxGastos())){
								sb.append("<td>"+currentCedula.getTxGastos()+"</td>\n");
								lsTitleGastos.add(currentCedula.getTxGastos());
							}
							
						}
						
						
					}
					
					//fijo 
					sb.append("<td>Total</td>\n").
					append("<td>&nbsp;</td>\n").
					append("</tr>\n");
					
					
					//ciclo para procesar datos
					rowKey = tablaCedulaA.keys();
					remuneracion = "";
					int typeClassIndex = 0;
					String typeClass ="";
					Boolean firstTime = true;
					
					while(rowKey.hasMoreElements()){
						firstTime = true;
						remuneracion = rowKey.nextElement();
						iterRow = tablaCedulaA.get(remuneracion).iterator();
						while(iterRow.hasNext()){
							typeClass = (typeClassIndex%2!=0) ? "txtTablaNone" : "txtTablaPar";
							currentCedula = iterRow.next();
							
							if(firstTime){
								sb.append("<tr class='"+typeClass+"'>\n");
								sb.append("<td width='150'>"+currentCedula.getTxRemuneracion()+"</td>\n");
								firstTime  = false;
							}
							
							sb.append("<td width='150' align='right'>"+Functions.currencyMask(currentCedula.getImRemuneracion())+"</td>\n");
							typeClassIndex++;
						}
						sb.append("<td width='150' align='right'>"+Functions.currencyMask(sumaFilaGastos.get(remuneracion))+"</td>\n");
						sb.append("<td width='150' align='right'>"+Functions.currencyMask(sumaAuxNomina.get(remuneracion))+"</td>\n");
						
						sb.append("</tr>\n");
						
						
					}
					
					//ciclo para pintar datos
					rowKey = tablaCedulaA.keys();
					remuneracion = "";
					typeClassIndex = 0;
					typeClass ="";
					firstTime = true;
					
					sb.append("<tr class='subHeader'>\n").
					append("<td>Total</td>\n");
					
					Iterator<String> iterTitleGastos = lsTitleGastos.iterator();
					Double gasto = null;
					Double totalDeTotales = 0.0;
					while(iterTitleGastos.hasNext()){
						gasto = sumaColumGastos.get(iterTitleGastos.next());
						totalDeTotales+=gasto;
						sb.append("<td width='120'>"+Functions.currencyMask(gasto)+"</td>\n");
					}
					sb.append("<td width='120'>"+Functions.currencyMask(totalDeTotales)+"</td>\n");
					sb.append("<td width='120'>"+Functions.currencyMask(totalNomAuxiliar)+"</td>\n");
					
					sb.append("</tr>");
					
					//sb.append(footer);
					sb.append("</table></td>\n").
					append("</tr>\n").
					append("</table>\n");
					
				} else { // Cierra IF verificador de contenido datos cédula
					sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);
				}
				
			} // Termina Ciclo de RPS
			
		} else { // Cierra IF verificador de contenido RPS
			sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);
		}		
		return sb;		
	} // Termina función generarVistaCedulaA

}
