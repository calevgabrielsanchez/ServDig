/**
 * Nos permite generar la tabla visual en formato HTML de la cédula I
 * la cual fue ingresada por el patrón.
 */
package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos;


import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.ctirss.correccion.constantes.ConstantesConsultaEC;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.ConsultasEstudioCorreccion;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.CedulaIVO;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.ConsultaEstudioCorreccionVO;

/**
 * Genera una tabla dinámica utilizando los datos obtenidos a
 * través de consultas ANSI SQL de la cédula I.
 * 
 * @author Marco Antonio Nieto Plett
 * @author Gerardo Salazar Vega
 * @version 1.0.3
 *
 */
@Component
public class GeneraTablaCedulaI {
	
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
	 * una lista de tipo CedulaIVO.
	 * @see CedulaIVO
	 * @param lista
	 * @author Marco Antonio Nieto Plett
	 * @return List<CedulaIVO>
	 */
	private List<CedulaIVO> transformOBJListTo(List<?> lista){
		
		
		List<CedulaIVO> listCedula = new ArrayList<CedulaIVO>();
		
		if(lista!=null && !lista.isEmpty()){
			Iterator<?> iter = lista.iterator();
			Object[] currentObj = null;
			while(iter.hasNext()){
				currentObj = (Object[])iter.next();
					
				listCedula.add(new CedulaIVO(currentObj));
			}
		}
			return listCedula;
	
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
	private List<CedulaIVO>  obtenerDatosCedulaIByRP(ConsultaEstudioCorreccionVO cec, String registroPatronal){
		String SQL;
		if(cec.getIdSubDelegacion()==null){
			SQL = ConsultasEstudioCorreccion.CONSULTA_CEDULA_I_NORMATIVO;
		}else{
			 SQL = ConsultasEstudioCorreccion.CONSULTA_CEDULA_I;
		}
		
		SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),registroPatronal, cec.getIdSubDelegacion());
		
		List<CedulaIVO> listaByRPCedula = ( (List<CedulaIVO>) transformOBJListTo(catalogoServiceBean.consultaSQL(SQL)));
		
		return listaByRPCedula;
		
	}
	
	/**
	 * Procesa todos los datos obtenidos de la cédula G
	 * y los transforma en una tabla de HTML con CSS
	 *  
	 * @param cec
	 * @see ConsultasEstudioCorreccion
	 * @author Marco Antonio Nieto Plett
	 * @return Tabla cédula I
	 */
	public StringBuffer generarVistaCedula(ConsultaEstudioCorreccionVO cec){
		
		StringBuffer sb   = new StringBuffer();
		/*Obtenemos a los RPs involucrados en el periodo específico*/
		String SQL = ConsultasEstudioCorreccion.CONSULTA_RP_PERIODO_FOLIO_CEDULA_I;
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
			
			CedulaIVO currentCedula = null;
			
			int typeClassIndex;
			int colSpanIntegrable;
			int colSpanNOIntegrable;
			int colSpanRP;
			
			String typeClass;
						
			List<CedulaIVO> currentList;
			Iterator<CedulaIVO> iterCurrentList;
			Iterator<?> iterTitlesList;
			
			Iterator<?> iterListaRPAsociados = (listRPsAsociados!=null) ? listRPsAsociados.iterator() : listRPUsr.iterator();
			
			
			List<?> titlesIntegraList;
			if(cec.getIdSubDelegacion()==null){
				SQL = ConsultasEstudioCorreccion.CONSULTA_TITULOS_VARIABLES_CEDULA_I_NORMATIVO;
			}else {
				SQL = ConsultasEstudioCorreccion.CONSULTA_TITULOS_VARIABLES_CEDULA_I;
			}
			
			SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),ConstantesConsultaEC.INTEGRA, cec.getIdSubDelegacion());
			titlesIntegraList = catalogoServiceBean.consultaSQL(SQL);
			
			List<?> titlesNOIntegraList;
			if(cec.getIdSubDelegacion()==null){
				SQL = ConsultasEstudioCorreccion.CONSULTA_TITULOS_VARIABLES_CEDULA_I_NORMATIVO;
			}else {
				SQL = ConsultasEstudioCorreccion.CONSULTA_TITULOS_VARIABLES_CEDULA_I;
			}
			
			
			SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),ConstantesConsultaEC.NO_INTEGRA, cec.getIdSubDelegacion());
			titlesNOIntegraList = catalogoServiceBean.consultaSQL(SQL);
			
			Float salarioMinimo;
			SQL = ConsultasEstudioCorreccion.CONSULTA_SALARIO_MINIMO;
			List salarios = catalogoServiceBean.consultaSQL(SQL + cec.getPeriodo());
			salarioMinimo = ((BigDecimal) salarios.get(0)).floatValue();
			
			while(iterListaRPAsociados.hasNext()){
				
				registroPatronal = (String) iterListaRPAsociados.next();
				
				currentList = obtenerDatosCedulaIByRP(cec,registroPatronal);
				iterCurrentList = currentList.iterator();
				
				//Se escrinrn los datos de la cedula
				if (iterCurrentList.hasNext()){
				
				typeClassIndex = 0;
				typeClass ="";
				
				
				colSpanIntegrable=  titlesIntegraList.size()+1;
				colSpanNOIntegrable= titlesNOIntegraList.size()+1;
				
				
				colSpanRP = colSpanIntegrable + colSpanNOIntegrable + 5;
				if(titlesNOIntegraList.size()==0){
					colSpanNOIntegrable = 2;
				}			
				 sb.append("<table  border='0' cellspacing='0' cellpadding='0'>\n").
				 append("<tr>\n").
				 append("<td class='fondoGeneralTabla'>");
				 
				 sb.append("<table width='100%'  border='0' cellspacing='1' cellpadding='1'>\n").
					 append("<tr class='header'>\n").
					 append("<td colspan='3'>Registro Patronal</td>\n").
					 append("<td colspan='"+colSpanRP+"'  align='left'>"+registroPatronal+"</td>\n").
					 append("</tr>\n").
					 append("<tr class='header'>\n").
					 append("<td width='5%' rowspan='2'>Mes</td>\n").
					 append("<td colspan='4'>Nombre del trabajador </td>\n").
					 append("<td colspan='"+colSpanIntegrable+"'>Percepciones integrables <br>\n").
					 append(" al salario de cotizaci&oacute;n </td>\n").
					 append("<td colspan='"+colSpanNOIntegrable+"'>Percepciones no integrables <br>\n").
					 append("al salario de cotizaci&oacute;n </td>\n").
					 append("<td colspan='3'>Total</td>\n").
					 append("</tr>\n");
				 
				 sb.append("<tr >\n").
					 append("<td width='8%' class='subHeader'>nombre</td>\n").
					 append("<td width='9%' class='subHeader'>Apellido Paterno </td>\n").
					 append("<td width='9%' class='subHeader'>Apellido Materno </td>\n").
					 append("<td width='6%' class='subHeader'>Dias <br> devengados</td>\n");
				
					
				 //Titulos Integrables (Variables)
				 
				 iterTitlesList = titlesIntegraList.iterator(); 
				 
				 while(iterTitlesList.hasNext()){
					 sb.append("<td width='6%' class='subHeader'>"+iterTitlesList.next()+"</td>\n");

				 }
				 
				 sb.append("<td width='6%' class='subHeader'>Total (A) </td>\n");
				 
					 
				//Titulos No integrables (Variables)
				 
				 iterTitlesList = titlesNOIntegraList.iterator();
				 
				 while(iterTitlesList.hasNext()){
					 sb.append("<td width='6%' class='subHeader'>"+iterTitlesList.next()+"</td>\n");

				 }
				 
				 sb.append("<td width='6%' class='subHeader'>Total(B)</td>\n");
				 
				//Titulos fijos	 
					 sb.append("<td width='5%' class='subHeader'>Percepci&oacute;n del periodo C= A+B </td>\n").
					 append("<td width='5%' class='subHeader'>Percepci&oacute;n Excenta D=25 VSMGDF </td>\n").
					 append("<td width='5%' class='subHeader'>Excedentes y Finiquitos <br> (C-D) </td>\n").
					 append("</tr>");
					 
					 
					 //Terminan Encabezados
					 
					 Boolean agregarRow = true;
					 Boolean seccionIntegraFinalizada = false;
					 Boolean seccionNOIntegraFinalizada = false;
					 Double totalSiIntegra=0.0;
					 Double totalNOIntegra=0.0;
					 float diasDevengados=0;
					 int index =0;
					 int index2=0;
					 float conceptoD=0;
					 String mes = "";
					 boolean finRow = false;
					 CedulaIVO cedulaTemporal=null;
					 int i=0;
					 Double res;
					 while(iterCurrentList.hasNext()){
						 //System.out.println("\n\n"+sb.toString());
						 currentCedula = iterCurrentList.next();
						 currentCedula.setSalarioMinimo(salarioMinimo);
						 typeClass = (typeClassIndex%2!=0) ? "txtTablaNone" : "txtTablaPar";
						 if(i==0){
							 mes = currentCedula.getTxMes();
						 }else{
							 mes = cedulaTemporal.getTxMes();
						 }
						//System.out.println(mes+" "+currentCedula.getTxMes());
						 if(!mes.equals(currentCedula.getTxMes())){
							 finRow=true;
						 }
						 if(finRow && seccionNOIntegraFinalizada){

							
									 sb.append("<td align='right'>\n");
									 sb.append(Functions.currencyMask(totalSiIntegra+totalNOIntegra));
									 sb.append("</td>\n");
									
									 conceptoD=(currentCedula.getSalarioMinimo()*25*diasDevengados)+totalNOIntegra.floatValue();
									 //conceptoD=(currentCedula.getSalarioMinimo()*25*diasDevengados);
									 sb.append("<td align='right'>\n");
									 sb.append(Functions.currencyMask(String.valueOf(conceptoD)));
									 sb.append("</td>\n");

									 
									 sb.append("<td align='right'>\n");
									 System.out.println("Total "+(totalSiIntegra+totalNOIntegra)+" conceptoD "+conceptoD+ " Res "+((totalSiIntegra+totalNOIntegra)-conceptoD));
									 
									 if((totalSiIntegra+totalNOIntegra)-conceptoD<0.0){
										 res=0.0;
									 }else{
										 res=(totalSiIntegra+totalNOIntegra)-conceptoD;
									 }
									 sb.append(Functions.currencyMask(res));
									 sb.append("</td>\n");
									 
									
						 	 sb.append("</tr>\n");
							 agregarRow = true;
							 seccionIntegraFinalizada = false;
							 seccionNOIntegraFinalizada = false;
							 finRow=false;
						 }

						 //Se agregan los datos del trabajador
						 if(agregarRow){					 
							 sb.append("<tr class='"+typeClass+"'>\n");
							 sb.append("<td>").append(currentCedula.getTxMes()).append("</td>\n").
							 append("<td>").append(currentCedula.getNombreAsegurado()).append("</td>\n").
							 append("<td>").append(currentCedula.getApPatAsegurado()).append("</td>\n").
							 append("<td>").append(currentCedula.getApMatAsegurado()).append("</td>\n").
							 append("<td align='center'>").append(currentCedula.getDiasDevengados()).append("</td>\n");
							 diasDevengados=Float.parseFloat(currentCedula.getDiasDevengados()!=null ? currentCedula.getDiasDevengados():"0");
							 agregarRow = false;
							 mes = currentCedula.getTxMes();
							 typeClassIndex++;
						 }

						 if(!seccionIntegraFinalizada ){
							System.out.println("Remuneracion "+currentCedula.getTxRemuneracion() +" importe "+currentCedula.getImpRemuneracion()+" index "+index);
							 if(titlesIntegraList.size()!=0){
								 sb.append("<td align='right'>\n");
								 sb.append(Functions.currencyMask(currentCedula.getImpRemuneracion()));
								 sb.append("</td>\n");
							 }
							 
							 index++;
							 
							 if(colSpanIntegrable==(index+1)){
								// System.out.println("Imprimiendo total Si Integrable");
								 index=0;
								 seccionIntegraFinalizada = true;
								 sb.append("<td align='right'>\n");
								 sb.append(Functions.currencyMask(currentCedula.getTotalSiIntegra()));
								 sb.append("</td>\n");
								 totalSiIntegra=Double.parseDouble(currentCedula.getTotalSiIntegra());
							 }
						 }else{
							 
							 
							 
							 
							 if(!seccionNOIntegraFinalizada && titlesNOIntegraList.size()!=0 ){
								 System.out.println("Remuneracion "+currentCedula.getTxRemuneracion() +" importe "+currentCedula.getImpRemuneracion()+" index "+index2);
								 sb.append("<td align='right'>\n");
								 sb.append(Functions.currencyMask(currentCedula.getImpRemuneracion()));
								 sb.append("</td>\n");
								 index2++;
								 }
							 
							 
							 if(colSpanNOIntegrable==(index2+1)){
								 index2=0;
								 seccionNOIntegraFinalizada = true;
								 sb.append("<td align='right'>\n");
								 sb.append(Functions.currencyMask(currentCedula.getTotalNoIntegrar()));
								 sb.append("</td>\n");
								 totalNOIntegra=Double.parseDouble(currentCedula.getTotalNoIntegrar());
							 }

							 
						 }

						 cedulaTemporal = currentCedula;
						 i++;

					 }
					 
					 
					 sb.append("<td align='right'>\n");
					 sb.append(Functions.currencyMask(totalSiIntegra+totalNOIntegra));
					 sb.append("</td>\n");
					 
					 conceptoD=(currentCedula.getSalarioMinimo()*25*diasDevengados)+Float.parseFloat(currentCedula.getTotalNoIntegrar());
					 sb.append("<td align='right'>\n");
				//	 sb.append(Functions.currencyMask(String.valueOf(conceptoD)));
					 sb.append(Functions.currencyMask(String.valueOf(conceptoD)));
					 sb.append("</td>\n");

					 
					 if((totalSiIntegra+totalNOIntegra)-conceptoD<0.0){
						 res=0.0;
					 }else{
						 res=(totalSiIntegra+totalNOIntegra)-conceptoD;
					 }
					 
					 
					 sb.append("<td align='right'>\n");
					 sb.append(Functions.currencyMask(String.valueOf(res)));
					 sb.append("</td>\n");
					 
					 

			 	sb.append("</tr>\n");
				sb.append("</table>\n");
				
				sb.append("</td>\n"). 
				append("</tr>\n").
				append("</table>\n");
				
				sb.append("<table border='0'><tr><td></td></tr></table>\n");
				} else {
					sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);
				}			
			}//Ciclo RPS
			// Cierra IF verificador de contenido RPS	
		} else {
		sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);
	}				

		
		return sb;
		
	} // Termina función generarVistaCedulaI
	

}
