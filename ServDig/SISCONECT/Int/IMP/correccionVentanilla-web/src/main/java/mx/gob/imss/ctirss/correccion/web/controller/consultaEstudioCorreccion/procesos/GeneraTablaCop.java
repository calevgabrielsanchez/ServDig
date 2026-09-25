/**
 * Nos permite generar la tabla visual en formato HTML de la consulta COP
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

import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.CopVO;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.ConsultaEstudioCorreccionVO;

/**
 * Genera una tabla dinámica utilizando los datos obtenidos a
 * través de consultas ANSI SQL de la consulta COP.
 * 
 * @author Gerardo Salazar Vega
 * @version 1.0.1
 *
 */
@Component
public class GeneraTablaCop {
	
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
	private String setParameters(String SQL, String folioCorreccion,
			String periodo, String registroPatronal, String idSubDelegacion) {

		SQL = SQL.replace("{1}", folioCorreccion);
		SQL = SQL.replace("{2}", periodo);
		if (registroPatronal != null) {
			SQL = SQL.replace("{3}", registroPatronal);
		} else {
			SQL = SQL.replace(
					" AND SUBSTR(patron.NUM_REGISTROPATRONAL,1,10)='{3}'", "");
		}
		if(idSubDelegacion!=null) SQL = SQL.replace("{4}", idSubDelegacion);

		return SQL;
	}
		
	/**
	 * Convierte el array de tipo Object (Object[])
	 * el cual fue arrojado por el servicio genérico
	 * a la consulta realizada y lo transforma a
	 * una lista de tipo CopVO.
	 * @see CopVO
	 * @param lista
	 * @author Gerardo Salazar Vega
	 * @return List<CopVO>
	 */
	private List<CopVO> transformOBJListTo(List<?> lista){
		
		
		List<CopVO> listCop = new ArrayList<CopVO>();
		
		if(lista!=null && !lista.isEmpty()){
			Iterator<?> iter = lista.iterator();
			Object[] currentObj = null;
			while(iter.hasNext()){
				currentObj = (Object[])iter.next();
					
				listCop.add(new CopVO(currentObj));
			}
		}
			return listCop;
	
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
	private List<CopVO>  obtenerDatosCop(ConsultaEstudioCorreccionVO cec, String registroPatronal){
		
		String SQL;
		if(cec.getIdSubDelegacion()==null){
			SQL = ConsultasEstudioCorreccion.CONSULTA_COP_NORMATIVO;
		}else{
			SQL = ConsultasEstudioCorreccion.CONSULTA_COP;
		}
		
		SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),registroPatronal,cec.getIdSubDelegacion());
		
		List<CopVO> listaCop = ( (List<CopVO>) transformOBJListTo(catalogoServiceBean.consultaSQL(SQL)));
		
		return listaCop;
		
	}
	
	/**
	 * Procesa todos los datos obtenidos de la consulta COP
	 * y los transforma en una tabla de HTML con CSS
	 *  
	 * @param cec
	 * @see ConsultasEstudioCorreccion
	 * @author Gerardo Salazar Vega
	 * @return consulta COP
	 */
	public StringBuffer generarVistaCedula(ConsultaEstudioCorreccionVO cec){
		
		StringBuffer sb = new StringBuffer();

		Double totalTabajadoresR = 0.0;
		Double totalCopSp = 0.0;
		Double totalCopAct = 0.0;
		Double totalCopRec = 0.0;
		Double totalCop = 0.0;
		Double totalRcvSp = 0.0;
		Double totalRcvAct = 0.0;
		Double totalRcvRec = 0.0;
		Double totalRcv = 0.0;

		String registroPatronal = null;

		if (!cec.getRegistroPatronal().equals("")) {
			registroPatronal = cec.getRegistroPatronal();
		}

		CopVO cop = null;
		List<CopVO> currentList;
		Iterator<CopVO> iterCurrentList;

		int typeClassIndex = 0;
		String typeClass = "";
		int totalAlta = 0;
		int totalBaja=0;
		int totalModifica=0;
		currentList = obtenerDatosCop(cec, registroPatronal);
		iterCurrentList = currentList.iterator();
		if (!currentList.isEmpty()){
					
					sb.append("<table width='1000'  border='0' cellspacing='0' cellpadding='0'>\n").
					append("<tr>\n").
					append("<td class='fondoGeneralTabla'><table width='900' border='0' cellspacing='1' cellpadding='1'>\n").
					append("<tr>\n").
					append("<td colspan='20'><table width='100%'  border='0' cellspacing='0' cellpadding='0'>\n").
					append("<tr class='header'>\n").
					append("<td colspan='2' align='center'>Desglose de Cuotas Obrero Patronales Autodeterminadas Pagadas </td>\n").
					append("</tr>\n").					
					append("</table></td>\n").
					append("</tr>\n");
					
					sb.append("<tr>\n").
					append("<td width='150' class='subHeader' rowspan='2'>Registro Patronal </td>\n").
					append("<td width='150' class='subHeader' colspan='2'>Folio </td>\n").
					append("<td width='150' class='subHeader' rowspan='2'>No. Credito </td>\n").
					append("<td width='150' class='subHeader' rowspan='2'>Fecha de Pago </td>\n").
					append("<td width='150' class='subHeader' rowspan='2'>Tipo de Documento</td>\n").
//					append("<td width='150' class='subHeader' rowspan='2'>Numero de Trabajadores Regularizados </td>\n").
					append("<td width='150' class='subHeader' colspan='5'>COP </td>\n").
					append("<td width='150' class='subHeader' colspan='5'>RCV </td>\n").
					append("<td width='150' class='subHeader' colspan='5'>Movimientos Afiliatorios </td>\n").
					append("</tr>\n");	
					sb.append("<tr>\n").
					append("<td width='150' class='subHeader'>SUA </td>\n").
					append("<td width='150' class='subHeader'>Orden Ingreso </td>\n").
					append("<td width='150' class='subHeader'>Periodo </td>\n").
					append("<td width='150' class='subHeader'>SP </td>\n").
					append("<td width='150' class='subHeader'>Act </td>\n").
					append("<td width='150' class='subHeader'>Rec </td>\n").
					append("<td width='150' class='subHeader'>Total </td>\n").
					append("<td width='150' class='subHeader'>Periodo </td>\n").
					append("<td width='150' class='subHeader'>SP </td>\n").
					append("<td width='150' class='subHeader'>Act </td>\n").
					append("<td width='150' class='subHeader'>Rec </td>\n").
					append("<td width='150' class='subHeader'>Total </td>\n").
					append("<td width='150' class='subHeader'>Numero de Trabajadores Regularizados </td>\n").
					append("<td width='150' class='subHeader'>Alta</td>\n").
					append("<td width='150' class='subHeader'>Baja</td>\n").
					append("<td width='150' class='subHeader'>Modificacion de Salario</td>\n").
					append("</tr>\n");						
	
					while(iterCurrentList.hasNext()){
						cop = iterCurrentList.next();
				
						typeClass = (typeClassIndex%2!=0) ? "txtTablaNone" : "txtTablaPar";
						 totalTabajadoresR += cop.getNumTrabajadoresRegularizadosDbl();
						 totalCopSp += cop.getCopSpDbl();
						 totalCopAct += cop.getCopActDbl();
						 totalCopRec += cop.getCopRecDbl();
						 totalCop += cop.getCopTotalDbl();
						 totalRcvSp += cop.getRcvSpDbl();
						 totalRcvAct += cop.getRcvActDbl();
						 totalRcvRec += cop.getRcvRecDbl();
						 totalRcv += cop.getRcvTotalDbl();
						
						sb.append("<tr class='"+typeClass+"'>\n").
						append("<td class='"+typeClass+"'>"+cop.getRegistroPatronal()+"</td>\n").
						append("<td align='left' class='"+typeClass+"'>"+cop.getFolioSua()+"</td>\n").
						append("<td align='left' class='"+typeClass+"'>"+cop.getOrdenIngreso()+"</td>\n").
						append("<td align='left' class='"+typeClass+"'>"+cop.getNumCredito()+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+cop.getFechaPago()+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+cop.getTipoDocumento()+"</td>\n").
//						append("<td align='right' class='"+typeClass+"'>"+cop.getNumTrabajadoresRegularizados()+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+cop.getCopPeriodo()+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cop.getCopSp())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cop.getCopAct())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cop.getCopRec())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cop.getCopTotal())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+cop.getRcvPeriodo()+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cop.getRcvSp())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cop.getRcvAct())+"</td>\n").						
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cop.getRcvRec())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+Functions.currencyMask(cop.getRcvTotal())+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+cop.getNumTrabajadoresRegularizados()+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+cop.getNumeroAlta()+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+cop.getNumeroBaja()+"</td>\n").
						append("<td align='right' class='"+typeClass+"'>"+cop.getModificacionSalario()+"</td>\n").
						//append("<td align='right' class='"+typeClass+"'>"+cop.ge+"</td>\n").
						append("</tr>\n");
						totalAlta+=	Integer.valueOf(cop.getNumeroAlta());
						totalBaja+=Integer.valueOf(cop.getNumeroBaja());
						totalModifica+=Integer.valueOf(cop.getModificacionSalario());
						typeClassIndex++;						
					}					
					
					sb.append("<tr>").
					append("<td class='subHeader' colspan='6'>Totales</td>\n").
//					append("<td class='subHeader' align='right'>"+totalTabajadoresR+"</td>\n").
					append("<td class='subHeader' align='right'>&nbsp;</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(totalCopSp)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(totalCopAct)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(totalCopRec)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(totalCop)+"</td>\n").
					append("<td class='subHeader' align='right'>&nbsp;</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(totalRcvSp)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(totalRcvAct)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(totalRcvRec)+"</td>\n").
					append("<td class='subHeader' align='right'>"+Functions.currencyMask(totalRcv)+"</td>\n").
					append("<td class='subHeader' align='right'>"+totalTabajadoresR.intValue()+"</td>\n").
					append("<td class='subHeader' align='right'>"+totalAlta+"</td>\n").
					append("<td class='subHeader' align='right'>"+totalBaja+"</td>\n").
					append("<td class='subHeader' align='right'>"+totalModifica+"</td>\n").
					append("</tr>\n");
					
					sb.append("</table></td>\n").
					append("</tr>\n").
					append("</table>\n");
					
					sb.append("<table border='0'><tr><td></td></tr></table>\n");
		} else{
			sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);			
		}
		return sb;
		
	} // Termina función generarVistaCedula COP

}
