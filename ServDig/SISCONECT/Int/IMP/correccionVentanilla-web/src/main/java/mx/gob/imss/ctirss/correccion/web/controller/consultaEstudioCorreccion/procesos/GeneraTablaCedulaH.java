/**
 * @author Oscar German Beltran Ortega
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 30/04/2012
 */
package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.ConsultasEstudioCorreccion;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.CedulaHVO;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.ConsultaEstudioCorreccionVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;





/**
 * Genera una tabla dinámica utilizando los datos obtenidos a
 * través de consultas ANSI SQL de la cédula H.
 * @author Oscar German Beltran Ortega
 * @author Gerardo Salazar Vega
 * @version 1.0.1
 *
 */
@Component
public class GeneraTablaCedulaH {
	public static final String TIPO_PERCEPCION_FIJA = "1";
	public static final String TIPO_PERCEPCION_VARIABLE = "2";
	
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
	 * Procesa todos los datos obtenidos de la cédula G  y los transforma en una tabla de HTML con CSS
	 * @param cec ConsultaEstudioCorreccionVO
	 * @author Oscar Beltran ORtega
	 * @return Tabla cédula H
	 */
	public StringBuffer generarVistaCedula(ConsultaEstudioCorreccionVO cec){
		
		StringBuffer sb   = new StringBuffer();
		List<?> listRPAsociados = null;
		List<String> listRPUsr = null;
		String registroPatronal = "";
		
		/*Obtenemos a los RPs involucrados en el periodo específico*/
		String querySQL = ConsultasEstudioCorreccion.CONSULTA_RP_PERIODO_FOLIO_CEDULA_H;
		querySQL = setParameters(querySQL, cec.getFolioCorreccion(),cec.getPeriodo(),null, null);
		
		if(cec.getRegistroPatronal().equals("")){
			listRPAsociados = catalogoServiceBean.consultaSQL(querySQL);
			
		}else{
			listRPUsr = new ArrayList<String>();
			listRPUsr.add(cec.getRegistroPatronal());
		}
		
		// para todos los RP encontrados
		if((listRPAsociados!=null && !listRPAsociados.isEmpty()) || (listRPUsr!=null && !listRPUsr.isEmpty()) ){
			Iterator<?> iterListaRPAsociados = (listRPAsociados!=null) ? listRPAsociados.iterator() : listRPUsr.iterator();
			
			
			CedulaHVO cedulaHVO = null;
			List<CedulaHVO> listaDatosCedulaHConsulta= null;
			List<String> lsTitlePercepcionesFijos = new ArrayList<String>();
			List<String> lsTitlePercepcionesVar = new ArrayList<String>();
			Hashtable<String,List<CedulaHVO>> tablaCedulaH = null;
			Hashtable<String,List<CedulaHVO>> percepciones = null;
			Hashtable<String,CedulaHVO> percepcionesXMes = null;
			Hashtable<String, Hashtable<String,List<CedulaHVO>>> percepcionesXPersona = null;
			HashMap<String,String> titleMesDescripcion = new HashMap<String, String>();
			
			//se iteran los RP encontrados
			while(iterListaRPAsociados.hasNext()){
				registroPatronal = String.valueOf(iterListaRPAsociados.next());
				percepcionesXPersona = new Hashtable<String, Hashtable<String,List<CedulaHVO>>>();
				
				tablaCedulaH= new Hashtable<String,List<CedulaHVO>>();
				//consulta de todos los datos de la cedula
				listaDatosCedulaHConsulta = obtenerDatosCedula(cec, registroPatronal);
				
				
				if(!listaDatosCedulaHConsulta.isEmpty()){
					Iterator<CedulaHVO> iterCedula = listaDatosCedulaHConsulta.iterator();
					HashMap<String, CedulaHVO> renglonPersonaMes = new HashMap<String, CedulaHVO>();
				
					//se separan cedulas por persona
					while(iterCedula.hasNext()){
						cedulaHVO = iterCedula.next();
						
						renglonPersonaMes.put(cedulaHVO.getNombreAsegurado()+cedulaHVO.getTxMes(), cedulaHVO);
					
						if(tablaCedulaH.containsKey(cedulaHVO.getNombreAsegurado())){
							tablaCedulaH.get(cedulaHVO.getNombreAsegurado()).add(cedulaHVO);
						}else{
							List<CedulaHVO> newList = new ArrayList<CedulaHVO>();
							newList.add(cedulaHVO);
							tablaCedulaH.put(cedulaHVO.getNombreAsegurado(),newList);
						}
					}
					
					Enumeration<String> rowKey = tablaCedulaH.keys();
					String nomPersona ="";
					percepcionesXMes = new Hashtable<String, CedulaHVO>();
					while(rowKey.hasMoreElements()){
						nomPersona = rowKey.nextElement();
						Iterator<CedulaHVO> iterRow = null;
						iterRow = tablaCedulaH.get(nomPersona).iterator();
						percepciones = new Hashtable<String, List<CedulaHVO>>(); 
						
						while (iterRow.hasNext()) {
							cedulaHVO =  iterRow.next();
							
							//para generar los titulos dinamicos de las percepciones
							if(TIPO_PERCEPCION_FIJA.equals(cedulaHVO.getInTpoPercepcion())){
								if(!lsTitlePercepcionesFijos.contains(cedulaHVO.getTxRemuneracion())){
									lsTitlePercepcionesFijos.add(cedulaHVO.getTxRemuneracion());
								}
							}else if(TIPO_PERCEPCION_VARIABLE.equals(cedulaHVO.getInTpoPercepcion())){
								if(!lsTitlePercepcionesVar.contains(cedulaHVO.getTxRemuneracion())){
									lsTitlePercepcionesVar.add(cedulaHVO.getTxRemuneracion());
								}
							}
							//para recuperar el nombre de las percecociones dinamicas
							if(percepciones.containsKey(cedulaHVO.getTxRemuneracion())){
								percepciones.get(cedulaHVO.getTxRemuneracion()).add(cedulaHVO);
								if(!percepcionesXMes.containsKey(nomPersona+cedulaHVO.getTxRemuneracion()+cedulaHVO.getCveMes())){
									percepcionesXMes.put(nomPersona+cedulaHVO.getTxRemuneracion()+cedulaHVO.getCveMes(), cedulaHVO);
								}
							}else{
								List<CedulaHVO> newList = new ArrayList<CedulaHVO>();
								newList.add(cedulaHVO);
								percepciones.put(cedulaHVO.getTxRemuneracion(),newList);
								percepcionesXMes.put(nomPersona+cedulaHVO.getTxRemuneracion()+cedulaHVO.getCveMes(), cedulaHVO);
								
							}
							//para los titulos de los meses
							if(!titleMesDescripcion.containsKey(cedulaHVO.getTxMes())){
								titleMesDescripcion.put(cedulaHVO.getCveMes().toString(),cedulaHVO.getTxMes());
							}
						}
						percepcionesXPersona.put(nomPersona, percepciones);
					}
					
					//comienza el detalle de la tabla HTML dinamica
					int numeroColumnasHedaer = 14 + lsTitlePercepcionesFijos.size() + lsTitlePercepcionesVar.size();
					sb.append("<table width='910'  border='0' cellspacing='0' cellpadding='0'>\n").
					append("<tr>\n").
					append("<td class='fondoGeneralTabla'><table width='100%' border='0' cellspacing='1' cellpadding='1'>\n").
					append("<tr>\n").
					append("<td colspan='"+numeroColumnasHedaer+"'><table width='100%'  border='0' cellspacing='0' cellpadding='0'>\n").
					append("<tr class='header'>\n").
					append("<td width='15%'>Registro Patronal </td>\n").
					append("<td width='85%' align='left'>"+registroPatronal+"</td>\n").
					append("</tr>\n").
					append("</table></td>\n").
					append("</tr>\n");
					
					
					sb.append("<tr>\n")
					.append("<td width='65px' rowspan='2' class='subHeader'>NSS</td>")
				    .append("<td colspan='3' '65px' align='center' class='header'>Nombre del trabajador</td>")
				    .append("<td rowspan='2' '65px' class='subHeader'>Antiguedad</td>")
				    .append("<td rowspan='2' '65px' class='subHeader' width='50px'>Categoria</td>")
				    .append("<td rowspan='2' class='subHeader' width='60px' >Mes</td>")
				    .append("<td colspan='"+lsTitlePercepcionesFijos.size()+"' class='header' align='center'>Percepciones Fijas</td>")
				    .append("<td rowspan='2' class='subHeader' width=´240px´>Salario Diario integrado fijo</td>");
				    if(lsTitlePercepcionesVar.size()!=0){
				    	sb.append("<td colspan='"+lsTitlePercepcionesVar.size()+"' class='header' align='center'>Percepciones Variables</td>");
				    }
				    sb.append("<td rowspan='2' class='subHeader'>Suman variables del periodo</td>")
				    .append("<td rowspan='2' class='subHeader'>Dias de salario devengado en el periodo</td>")
				    .append("<td rowspan='2' class='subHeader'>Percepcion diaria variable (variables bimestre anterior / dias devengados bimestre anterior)</td>")
				    .append("<td rowspan='2' class='subHeader'>Debio Cotizar</td>")
				    .append("<td rowspan='2' class='subHeader' width='90'>Cotizo</td>")
				    .append("<td rowspan='2' class='subHeader' width='90'>Diferencia</td>")
				    .append("</tr>");
				  
					sb.append("<tr class='header'>")
					.append("<td width='95' align='center' class='subHeader'>Nombre</td>")
					.append("<td width='95' align='center' class='subHeader'>Apellido Paterno </td>")
					.append("<td width='95' align='center' class='subHeader'>Apellido Materno </td>");
					
					//iterar percepciones fijas
					 for(String tituloFijo : lsTitlePercepcionesFijos){
						 sb.append("<td width='90' class='subHeader' ><div align='center'>"+tituloFijo+"</div></td>");
					 }
					 
					//iterar percepciones variables
					 for(String tituloVar : lsTitlePercepcionesVar){
						 sb.append("<td class='subHeader'>"+tituloVar+"</td>");
					 }
					 sb.append("</tr>");
					 
					 
					 //iterar por usuario por mes y por percepcion
					 String persona = "";
					 String typeClass ="";
					 int typeClassIndex = 0;
					 Enumeration<String> keyPersonas = percepcionesXPersona.keys();
					while(keyPersonas.hasMoreElements()){
						
						persona=keyPersonas.nextElement();
						CedulaHVO cedulaInfoGral = ((List<CedulaHVO>)tablaCedulaH.get(persona)).get(0);
						
						//iteracion por cada mes
						for (int i = 1; i < 13; i++) {
							typeClass = (typeClassIndex%2!=0) ? "txtTablaNone" : "txtTablaPar";
							Double impSalDiaIntegradoFijo = 0d;
							Double sumaVariablesPeriodo = 0d;
							Double impDebioCotizar =0d;
							Double impDiferencia = 0d;
							String numDiasSalarioDev = "";
							String percepcionDiariaVal = "0";
							String impCotizo = "0";
							
							
							if(cedulaInfoGral.getApMaternoAsegurado()==null || cedulaInfoGral.getApMaternoAsegurado().equals("null")){
								cedulaInfoGral.setApMaternoAsegurado("");
							}
							sb.append("<tr class='"+typeClass+"'>\n")
							//sb.append("<tr class='subHeader'>")
							.append("<td align='right'>"+cedulaInfoGral.getNuNss()+"</td>")
							.append("<td align='right'>"+cedulaInfoGral.getNombreAsegurado()+"</td>")
							.append("<td align='right'>"+cedulaInfoGral.getApPaternoAsegurado()+"</td>")
							.append("<td align='right'>"+cedulaInfoGral.getApMaternoAsegurado()+"</td>")
							.append("<td align='center'>"+cedulaInfoGral.getNuAntiguedadAnios()+"</td>")
							.append("<td align='right' width='65px'>"+cedulaInfoGral.getTxCategoria()+"</td>")
							.append("<td align='right' width='60px'>"+titleMesDescripcion.get(i+"") +"</td>");
							
							//percepciones fijas
							for (String percepcionNombre :  lsTitlePercepcionesFijos){
								CedulaHVO cedulaHVOTemp = percepcionesXMes.get(persona+ percepcionNombre +i);
								if(cedulaHVOTemp!=null){
									impSalDiaIntegradoFijo = impSalDiaIntegradoFijo + Double.valueOf(cedulaHVOTemp.getImpRemuneracion());
									sb.append("<td align='right'>"+Functions.currencyMask(cedulaHVOTemp.getImpRemuneracion())+"</td>");
									numDiasSalarioDev = cedulaHVOTemp.getNuDiasSalDev();
									percepcionDiariaVal = cedulaHVOTemp.getImpPercepVarDiaria();
									impCotizo = cedulaHVOTemp.getImpCotizo();
								}
								cedulaHVOTemp=null;
							}
							
							sb.append("<td align='right'>"+Functions.currencyMask(impSalDiaIntegradoFijo)+"</td>");

							//percepciones variables
							for (String percepcionNombre :  lsTitlePercepcionesVar){
								cedulaHVO = percepcionesXMes.get(persona+percepcionNombre+i);
								if(cedulaHVO!=null){
									sumaVariablesPeriodo = sumaVariablesPeriodo + Double.valueOf(cedulaHVO.getImpRemuneracion());
									sb.append("<td align='right'>"+Functions.currencyMask(cedulaHVO.getImpRemuneracion())+"</td>");
									numDiasSalarioDev = cedulaHVO.getNuDiasSalDev();
									percepcionDiariaVal = cedulaHVO.getImpPercepVarDiaria();
									impCotizo = cedulaHVO.getImpCotizo();
								}
							}
							
							impDebioCotizar= impSalDiaIntegradoFijo +Double.valueOf(percepcionDiariaVal);
							impDiferencia=impDebioCotizar - Double.valueOf(impCotizo);
							//falta un td
							sb.append("<td >"+Functions.currencyMask(sumaVariablesPeriodo)+"</td>")
							.append("<td align='right'>"+numDiasSalarioDev+"</td>")
							.append("<td align='right'>"+Functions.currencyMask(percepcionDiariaVal)+"</td>")
							.append("<td align='right' width='90'>"+Functions.currencyMask(impDebioCotizar)+"</td>")
							.append("<td align='right' width='90'>"+Functions.currencyMask(impCotizo)+"</td>")
							.append("<td align='right'>"+Functions.currencyMask(impDiferencia)+"</td>")
							
							.append("</tr>");
							typeClassIndex++;
						}
						
					}
					
				  //linea final vacia	
				  sb.append("<tr class='subHeader'>")
				  .append("<td colspan= '"+numeroColumnasHedaer+"'>&nbsp;&nbsp;</td>")
				  .append("</tr>");
					
					
				  //final
	         	  sb.append("</table></td>\n")
				  .append("</tr>\n")
				  .append("</table>");
				  sb.append("<table border='0'><tr><td></td></tr></table>\n");
					
				} else {
					sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);
				}				
				
			}//fin del while de RP
			
		} else { // Cierra IF verificador de contenido RPS
			sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);
		}
		return sb;
	}
	
	
	
	/**
	 * Obtiene a todos los registros patronales
	 * asociados al folio de corrección y al periodo ingresados.
	 * 
	 * @param cec
	 * @param registroPatronal
	 * @author Oscar Beltran ortega
	 * @return Lista de  CedulaHVO
	 */
	private List<CedulaHVO>  obtenerDatosCedula(ConsultaEstudioCorreccionVO cec, String registroPatronal){
		String query;
		if( cec.getIdSubDelegacion()==null){
			query = ConsultasEstudioCorreccion.CONSULTA_CEDULA_H_NORMATIVO;
		}else{
			 query = ConsultasEstudioCorreccion.CONSULTA_CEDULA_H;
		}
		
		query = setParameters(query, cec.getFolioCorreccion(),cec.getPeriodo(),registroPatronal, cec.getIdSubDelegacion());
		
		List<CedulaHVO> listaCedulaH = ( (List<CedulaHVO>) transformOBJListTo(catalogoServiceBean.consultaSQL(query)));
		
		return listaCedulaH;
		
	}
	
	/**
	 * Convierte el array de tipo Object (Object[]) ,el cual fue arrojado por el servicio genérico
	 * a la consulta realizada y lo transforma a una lista de tipo CedulaHVO.
	 * @param lista
	 * @author Oscar Beltran Ortega
	 * @return List<CedulaHVO>
	 */
	private List<CedulaHVO> transformOBJListTo(List<?> lista){
		
		
		List<CedulaHVO> listCedulaH = new ArrayList<CedulaHVO>();
		
		if(lista!=null && !lista.isEmpty()){
			Iterator<?> iter = lista.iterator();
			Object[] currentObj = null;
			while(iter.hasNext()){
				currentObj = (Object[])iter.next();
					
				listCedulaH.add(new CedulaHVO(currentObj));
			}
		}
			return listCedulaH;
	
	}
	

}
