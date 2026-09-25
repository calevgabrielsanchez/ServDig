/**
 * Nos permite generar la tabla visual en formato HTML de la cédula G [C.O.P Mensuales]
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

import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.CedulaGVO;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.ConsultaEstudioCorreccionVO;

/**
 * Genera una tabla dinámica utilizando los datos obtenidos a
 * través de consultas ANSI SQL de la cédula G.
 * 
 * @author Marco Antonio Nieto Plett
 * @author Gerardo Salazar Vega
 * @version 1.0.5
 *
 */
@Component
public class GeneraTablaCedulaG {
	
	/**
	 * Servicio genérico de consulta
	 */
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	
	
	private Double imCuotaGuarderiasTotalColumna;
	
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
	 * una lista de tipo CedulaGVO.
	 * @see CedulaGVO
	 * @param lista
	 * @author Marco Antonio Nieto Plett
	 * @return List<CedulaGVO>
	 */
	private List<CedulaGVO> transformOBJListTo(List<?> lista){
		
		
		List<CedulaGVO> listCedulaG = new ArrayList<CedulaGVO>();
		
		if(lista!=null && !lista.isEmpty()){
			Iterator<?> iter = lista.iterator();
			Object[] currentObj = null;
			while(iter.hasNext()){
				currentObj = (Object[])iter.next();
					
				listCedulaG.add(new CedulaGVO(currentObj));
			}
		}
			return listCedulaG;
	
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
	private List<CedulaGVO>  obtenerDatosCedulaAByRP(ConsultaEstudioCorreccionVO cec, String registroPatronal){
		String SQL;
		if(cec.getIdSubDelegacion()==null){
			SQL = ConsultasEstudioCorreccion.CONSULTA_CEDULA_G_NORMATIVO;	
		}else{
			 SQL = ConsultasEstudioCorreccion.CONSULTA_CEDULA_G;	
		}
		
		
		
		
		SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),registroPatronal, cec.getIdSubDelegacion());
		
		List<CedulaGVO> listaByRPCedulaG = ( (List<CedulaGVO>) transformOBJListTo(catalogoServiceBean.consultaSQL(SQL)));
		
		return listaByRPCedulaG;
		
	}
	
	/**
	 * Procesa todos los datos obtenidos de la cédula G
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
		String SQL = ConsultasEstudioCorreccion.CONSULTA_RP_PERIODO_FOLIO_CEDULA_G;
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
			
			CedulaGVO currentCedula = null;
			List<CedulaGVO> currentList;
			Iterator<CedulaGVO> iterCurrentList;
			
			int typeClassIndex = 0;
			String typeClass ="";
			
			Double imCuotaFijaTotal;
			Double imCuotaExcedente;
			Double imCuotaPrestaciones;
			Double imCuotaGastosMedicos;
			Double imCuotaRiesgoTrabajo;
			Double imCuotaInvalidez;
			Double imCuotaGuarderias;
			Double imCuotaRCVRetiro;
			Double imCuotaRCVCesantia;
			
			Double imTotalRenglonCOPS;
			Double imTotalRCVRenglon;
			
			
			Double imCuotaFijaTotalColumna;
			Double imCuotaExcedenteTotalColumna;
			Double imCuotaPrestacionesTotalColumna;
			Double imCuotaGastosMedicosTotalColumna;
			Double imCuotaRiesgoTrabajoTotalColumna;
			Double imCuotaInvalidezTotalColumna;
			Double imCuotaGuarderiasTotalColumna;
			Double imCuotaRCVRetiroTotalColumna;
			Double imCuotaRCVCesantiaTotalColumna;
			
			while(iterListaRPAsociados.hasNext()){
				registroPatronal = String.valueOf(iterListaRPAsociados.next());
				
				typeClassIndex = 0;
				typeClass ="";
				
				imCuotaFijaTotal = 0.0 ;
				imCuotaExcedente = 0.0 ;
				imCuotaPrestaciones = 0.0 ;
				imCuotaGastosMedicos = 0.0 ;
				imCuotaRiesgoTrabajo = 0.0 ;
				imCuotaInvalidez = 0.0 ;
				imCuotaGuarderias = 0.0 ;
				imCuotaRCVRetiro = 0.0 ;
				imCuotaRCVCesantia = 0.0 ;
				
				imTotalRenglonCOPS = 0.0;
				imTotalRCVRenglon = 0.0;
				
				imCuotaFijaTotalColumna = 0.0 ;
				imCuotaExcedenteTotalColumna = 0.0 ;
				imCuotaPrestacionesTotalColumna= 0.0 ;
				imCuotaGastosMedicosTotalColumna= 0.0 ;
				imCuotaRiesgoTrabajoTotalColumna= 0.0 ;
				imCuotaInvalidezTotalColumna= 0.0 ;
				imCuotaGuarderiasTotalColumna= 0.0 ;
				imCuotaRCVRetiroTotalColumna= 0.0 ;
				imCuotaRCVCesantiaTotalColumna= 0.0 ;
				
				currentList = obtenerDatosCedulaAByRP(cec, registroPatronal);
				iterCurrentList = currentList.iterator();
				
				if (iterCurrentList.hasNext()){
					
					sb.append("<table width='900'  border='0' cellspacing='0' cellpadding='0'>\n").
					append("<tr>\n").
					append("<td class='fondoGeneralTabla'><table width='900' border='0' cellspacing='1' cellpadding='1'>\n").
					append("<tr>\n").
					append("<td colspan='12'><table width='100%'  border='0' cellspacing='0' cellpadding='0'>\n").
					append("<tr class='header'>\n").
					append("<td width='15%'>Registro Patronal </td>\n").
					append("<td width='85%' align='left'>"+registroPatronal+"</td>\n").
					append("</tr>\n").
					append("</table></td>\n").
					append("</tr>\n");
					
					sb.append("<tr>\n").
					append("<td width='65' rowspan='2' class='subHeader'>Mes</td>\n").
					append("<td colspan='5' class='header'>Enfermedades y Maternidad </td>\n").
					append("<td width='92' rowspan='2' class='subHeader'>Invalidez y Vida </td>\n").
					append("<td width='95' rowspan='2' class='subHeader'>Guarder&iacute;as y Prestaciones Sociales </td>\n").
					append("<td width='64' rowspan='2' class='subHeader'>Total C.O.P IMSS </td>\n").
					append("<td width='59' rowspan='2' class='subHeader'>Retiro</td>\n").
					append("<td width='75' rowspan='2' class='subHeader'>Cesant&iacute;a en Edad Avanzada y Vejez </td>\n").
					append("<td width='71' rowspan='2' class='subHeader'>Total RCV </td>\n").
					append("</tr>\n");
					      
					sb.append("<tr>\n").
					append("<td width='64' class='subHeader'>Cuota Fija </td>\n").
					append("<td width='61' class='subHeader'>EXC 3V S.M.G. D.F. </td>\n").
					append("<td width='85' class='subHeader'>Prestaciones en Dinero </td>\n").
					append("<td width='64' class='subHeader'>Gastos M&eacute;dicos</td>\n").
					append("<td width='68' class='subHeader'>Riesgo de Trabajo </td>\n").
					append("</tr>");
				
					while(iterCurrentList.hasNext()){
						currentCedula = iterCurrentList.next();
				
						typeClass = (typeClassIndex%2!=0) ? "txtTablaNone" : "txtTablaPar";
						
						imCuotaFijaTotal = Double.parseDouble(currentCedula.getImCuotaFija());
						imCuotaExcedente = Double.parseDouble(currentCedula.getImCuotaExced3SMGDF()) ;
						imCuotaPrestaciones = Double.parseDouble(currentCedula.getImCuotaPrestDinero()) ;
						imCuotaGastosMedicos = Double.parseDouble(currentCedula.getImCuotaGtosMedPen()) ;
						imCuotaRiesgoTrabajo = Double.parseDouble(currentCedula.getImCuotaRiesgosTrabajo());
						imCuotaInvalidez = Double.parseDouble(currentCedula.getImCuotaInvalidezVida()) ;
						imCuotaGuarderias = Double.parseDouble(currentCedula.getImCuotaGuardPrest());
						
						imTotalRenglonCOPS = imCuotaFijaTotal +  imCuotaExcedente + imCuotaPrestaciones +imCuotaGastosMedicos
								             + imCuotaRiesgoTrabajo + imCuotaInvalidez + imCuotaGuarderias;
						
						
						
						imCuotaFijaTotalColumna+=imCuotaFijaTotal ;
						imCuotaExcedenteTotalColumna+=imCuotaExcedente;
						imCuotaPrestacionesTotalColumna+=imCuotaPrestaciones;
						imCuotaGastosMedicosTotalColumna+=imCuotaGastosMedicos;
						imCuotaRiesgoTrabajoTotalColumna+=imCuotaRiesgoTrabajo;
						imCuotaInvalidezTotalColumna+=imCuotaInvalidez ;
						imCuotaGuarderiasTotalColumna+=imCuotaGuarderias;
						
						
						imCuotaRCVRetiro = Double.parseDouble(currentCedula.getImCuotaRCVRetiro()) ;
						imCuotaRCVCesantia = Double.parseDouble(currentCedula.getImCuotaRCVCesantia());
												
						imTotalRCVRenglon = imCuotaRCVRetiro + imCuotaRCVCesantia;
						
						if(currentCedula.getCveMes()%2==0){
							imCuotaRCVRetiroTotalColumna+=imCuotaRCVRetiro;
							imCuotaRCVCesantiaTotalColumna+=imCuotaRCVCesantia ;
						}
						
											
						
						sb.append("<tr class='"+typeClass+"'>\n").
						append("<td class='"+typeClass+"'>"+currentCedula.getTxMes()+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(imCuotaFijaTotal)+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(imCuotaExcedente)+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(imCuotaPrestaciones)+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(imCuotaGastosMedicos)+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(imCuotaRiesgoTrabajo)+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(imCuotaInvalidez)+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(imCuotaGuarderias)+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(imTotalRenglonCOPS)+"</td>\n");
						
						if(currentCedula.getCveMes()%2==0){
							sb.append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(imCuotaRCVRetiro)+"</td>\n").						
							append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(imCuotaRCVCesantia)+"</td>\n").
							append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(imTotalRCVRenglon)+"</td>\n").
							append("</tr>");
						}else{
							
							sb.append("<td class='fondoGris'>&nbsp;</td>\n").						
							append("<td class='fondoGris'>&nbsp;</td>\n").
							append("<td class='fondoGris'>&nbsp;</td>\n").
							append("</tr>");
							
							
						}
						
						typeClassIndex++;
						
						
					}
					
					sb.append("<tr class='subHeader'>\n").
					append("<td class='txt'>Total C.O.P </td>\n").
					append("<td>"+Functions.currencyMask(imCuotaFijaTotalColumna)+"</td>\n").
					append("<td>"+Functions.currencyMask(imCuotaExcedenteTotalColumna)+"</td>\n").
					append("<td>"+Functions.currencyMask(imCuotaPrestacionesTotalColumna)+"</td>\n").
					append("<td>"+Functions.currencyMask(imCuotaGastosMedicosTotalColumna)+"</td>\n").
					append("<td>"+Functions.currencyMask(imCuotaRiesgoTrabajoTotalColumna)+"</td>\n").
					append("<td>"+Functions.currencyMask(imCuotaInvalidezTotalColumna)+"</td>\n").
					append("<td>"+Functions.currencyMask(imCuotaGuarderiasTotalColumna)+"</td>\n").
					append("<td>"+Functions.currencyMask((imCuotaFijaTotalColumna+
							       imCuotaExcedenteTotalColumna+
							       imCuotaPrestacionesTotalColumna+
							       imCuotaGastosMedicosTotalColumna+
							       imCuotaRiesgoTrabajoTotalColumna+
							       imCuotaInvalidezTotalColumna+
							       imCuotaGuarderiasTotalColumna))+"</td>\n").
					append("<td>"+Functions.currencyMask(imCuotaRCVRetiroTotalColumna)+"</td>\n").
					append("<td>"+Functions.currencyMask(imCuotaRCVCesantiaTotalColumna)+"</td>\n").
					append("<td>"+Functions.currencyMask((imCuotaRCVRetiroTotalColumna+imCuotaRCVCesantiaTotalColumna))+"</td>\n").
					append("</tr>\n");
					
					sb.append("<tr>").
					append("<td class='subHeader'>Factor</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td align='center' class='subHeader'>1%</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("</tr>\n");
					
					sb.append("<tr>\n").
					append("<td class='subHeader'>Base</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='subHeader'>"+Functions.currencyMask((imCuotaGuarderiasTotalColumna/.01))+"</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("<td class='fondoGris'>&nbsp;</td>\n").
					append("</tr>\n");
					
					sb.append("</table></td>\n").
					append("</tr>\n").
					append("</table>\n");

					sb.append("<table border='0'><tr><td></td></tr></table>\n");
					this.setImCuotaGuarderiasTotalColumna(imCuotaGuarderiasTotalColumna/.01);
				} else {
					sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);
				}

			}

			// Cierra IF verificador de contenido RPS
		} else {
			sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);
		}

		return sb;
		
	} // Termina función generarVistaCedulaA

	/**
	 * @return the imCuotaGuarderiasTotalColumna
	 */
	public Double getImCuotaGuarderiasTotalColumna() {
		return imCuotaGuarderiasTotalColumna;
	}

	/**
	 * @param imCuotaGuarderiasTotalColumna the imCuotaGuarderiasTotalColumna to set
	 */
	public void setImCuotaGuarderiasTotalColumna(
			Double imCuotaGuarderiasTotalColumna) {
		this.imCuotaGuarderiasTotalColumna = imCuotaGuarderiasTotalColumna;
	}
	

}
