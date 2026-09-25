package mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.procesos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.gob.imss.correccion.commons.sbc.vo.ExcedentesTopadosVO;
import mx.gob.imss.ctirss.correccion.detBaseCotOmitida.service.interfaces.DetBaseCotOmitidaService;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CrtCopPagadasAnual;
import mx.gob.imss.ctirss.correccion.model.CrtDetBaseCotOmDet;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.ConsultasEstudioCorreccion;
import mx.gob.imss.ctirss.correccion.service.interfaces.ICatalogoService;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.CedulaRVO;
import mx.gob.imss.ctirss.correccion.web.controller.consultaEstudioCorreccion.vo.ConsultaEstudioCorreccionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Genera una tabla dinámica utilizando los datos obtenidos a
 * través de consultas ANSI SQL de la cédula R.
 * 
 * @author Jorge Hernandez Almazan
 * @version 1.0.0
 *
 */
@Component
public class GeneraTablaCedulaR {
	

	/**
	 * Servicio genérico de consulta
	 */
	@Autowired
	private ICatalogoService<AbstractModel> catalogoServiceBean;
	

	/**
	 * Servicio para calculo de menos
	 */
	@Autowired
	private DetBaseCotOmitidaService<CrtCopPagadasAnual> detBaseCotOmitidaServiceCopPagadas;
	/**
	 * Permite ingresar los parámetros solicitados al SQL.
	 * 
	 * @param SQL
	 * @param folioCorreccion
	 * @param periodo
	 * @param registroPatronal
	 * @return SQL parametrizado
	 */
	private String setParameters(String SQL, String folioCorreccion, String periodo, String registroPatronal){
		
		SQL = SQL.replace("{1}", folioCorreccion);
		SQL = SQL.replace("{2}", periodo);
		if(registroPatronal!=null) SQL = SQL.replace("{3}", registroPatronal);
		return SQL;
		
	}
	
	
	/**
	 * Procesa todos los datos obtenidos de la cédula R y los transforma en una tabla de HTML con CSS
	 * @param cec ConsultaEstudioCorreccionVO
	 * @return Tabla cédula R
	 */
	public StringBuffer generarVistaCedula(ConsultaEstudioCorreccionVO cec){
		
		StringBuffer sb   = new StringBuffer();
		List<?> listRPsAsociados = null;
		/*Obtenemos a los RPs involucrados en el periodo específico*/
		String SQL = ConsultasEstudioCorreccion.CONSULTA_RP_PERIODO_FOLIO_CEDULA_R;		
		SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),null);
		
		if(cec.getRegistroPatronal().equals("")){
			listRPsAsociados = catalogoServiceBean.consultaSQL(SQL);			
		}	
		//Iteracion de acuerdo a RP
		if(listRPsAsociados!=null && !listRPsAsociados.isEmpty()){
			for(Object rp:listRPsAsociados){
				sb.append(generaFormaRP(rp.toString(),cec).toString());
			}
		}else{
			sb=generaFormaRP(cec.getRegistroPatronal(),cec);
		}
		if(sb.length()==0){
			sb.append(ConsultaEstudioCorreccionVO.tablaSinDatos);
		}
		return sb;
	}
		

	/**
	 * Genera la forma final de la cedula R mediante el registro pantronal correspondiente
	 * @param cec ConsultaEstudioCorreccionVO
	 * @param String registro Patronal
	 * @return Tabla cédula R
	 */
	private StringBuffer generaFormaRP(String registroPatronal,ConsultaEstudioCorreccionVO cec){
	
		StringBuffer form=new StringBuffer();
		CedulaRVO vo=null;
		String typeClass ="";
		int typeClassIndex = 0;		
		double sueldoBalanza=0;
		double sueldoAnualISR=0;
		double baseMayor=0;		
		double importRemuneracion=0.0;
		double importe=0;
		double cotizacionAuto=0;
		double sumaPag=0;
		double cotizacioOmitida=0;
		List<CedulaRVO> lista=obtenerDatosCedula(cec, registroPatronal);
		//Si no existen detalle se devuelve el control
		if(lista.isEmpty()){
			return form;
		}
		
		vo=lista.get(0);	
		
		sueldoBalanza=Double.parseDouble(vo.getSueldoBalanzaComp());
		sueldoAnualISR=Double.parseDouble(vo.getSueldoAnualISR());
		if(sueldoBalanza>=sueldoAnualISR){
			baseMayor=sueldoBalanza;
		}else{
			baseMayor = sueldoAnualISR;
		}
		//Recuperacion de detalles
		
		for(CrtDetBaseCotOmDet detalle:vo.getDetalles()){ 
			if(detalle.getIdConceptoOmitido()==1){
				importe+=(detalle.getImRemuneracion());
			}
		}
		importe+=Double.parseDouble(vo.getMenosSextoBimestre());
		cotizacionAuto=(baseMayor+Double.parseDouble(vo.getMasSextoBimestre()))-importe;
		
		
		CrtCopPagadasAnual po=new CrtCopPagadasAnual();
		po.setCveEjercicio(Long.valueOf(cec.getPeriodo()));
		po.setCveAnexoSolCorrPat(Long.valueOf(vo.getAnexoSolCorrpat()));
		po=detBaseCotOmitidaServiceCopPagadas.calculaMenos(po);		
		
		ExcedentesTopadosVO topado=new ExcedentesTopadosVO();
		topado.setCveAnexoSolCorrPat(Integer.valueOf(vo.getAnexoSolCorrpat()));
		topado.setCveEjercicio(Integer.valueOf(cec.getPeriodo()));
		topado.setFolioCorreccion(cec.getFolioCorreccion());
		
		topado=detBaseCotOmitidaServiceCopPagadas.calculaExcedenteTopado(topado);
		Double excedTopado=0.0;
		if(topado!=null && topado.getExcedenteTopado()!=null ){
			excedTopado=topado.getExcedenteTopado();
		}
		if(po.getSumaImpTotGuadPrest()!=null){
			sumaPag=po.getSumaImpTotGuadPrest();
		}
		cotizacioOmitida=cotizacionAuto-sumaPag-excedTopado.doubleValue();
		
		//Headers

		
		form.append("<table width='896'  border='0' cellspacing='0' cellpadding='0'>\n").
						append("<tr>\n").
							append("<td class='fondoGeneralTabla'>\n").
								append("<table width='896' border='0' cellspacing='1' cellpadding='1'>\n").
									append("<tr>\n").
										append("<td colspan='4'>\n").
											append("<table width='896'  border='0' cellspacing='0' cellpadding='0'>\n").
												append("<tr class='header'>\n").
													append("<td width='135'>Registro Patronal </td>\n").
													append("<td width='765' align='left'>"+registroPatronal+"</td>\n").
												append("</tr>\n").
												append("<tr class='header'>\n").
													append("<td colspan='2' align='center'>Cotizaci&oacute;n Omitida</td>\n").
												append("</tr>\n").					
											append("</table>\n").
							append("</td>\n").
						append("</tr>\n");
			
		
		
		//Encabezado
		form.append("<tr>\n").
		append("<td  width='225' class='txtTablaNone'><div align='left'>Razon Social</div></td>\n").
		append("<td  width='225' class='txtTablaNone'><div align='right'>"+vo.getRazonSocial()+"</div> </td>\n").
		append("<td  width='225' class='txtTablaNone'><div align='left'>Ejercicio</div></td>\n").
		append("<td  width='225' class='txtTablaNone'><div align='right'>"+vo.getEjercicio()+" </div></td>\n").
		append("</tr>\n").
		
		append("<tr>\n").
		append("<td class='txtTablaPar' ><div align='left'>Sueldos y salarios registrados en la balanza de comprobación o auxiliar de nomina</div></td>\n").
		append("<td class='txtTablaPar'><div align='right'>"+Functions.currencyMask(vo.getSueldoBalanzaComp())+"</div></td>\n").
		append("<td class='txtTablaPar' ><div align='left'>Sueldos y salarios manifestados en la declaración del ISR</div></td>\n").
		append("<td class='txtTablaPar'><div align='right'>"+Functions.currencyMask(vo.getSueldoAnualISR())+"</div></td>\n").
		append("</tr>\n").
	
		append("<tr>\n").		
		append("<td class='txtTablaNone' ><div align='left'>Base Mayor</div></td>\n").
		append("<td class='txtTablaNone'><div align='right'>"+Functions.currencyMask(baseMayor)+"</div></td>\n").
		append("<td class='txtTablaNone' colspan='2'></td>").
		append("</tr>\n").
		
		append("<tr>\n").
		append("<td width='150' class='txtTablaPar' ><div align='left'>Variables del 6° bimestre del ejercicio inmediato anterior</div></td>\n").
		append("<td width='150' class='txtTablaPar' ><div align='right'>"+Functions.currencyMask(vo.getMasSextoBimestre())+" </div></td>\n").
		append("<td width='150' class='txtTablaPar' ><div align='left'>Variables del 6° bimestre del ejercicio o del último bimestre</div></td>\n").
		append("<td width='150' class='txtTablaPar' ><div align='right'>"+Functions.currencyMask(vo.getMenosSextoBimestre())+"</div></td>\n").
		append("</tr>\n").
		
		append("<tr>\n").
		append("<td width='150' class='txtTablaNone' ><div align='left'>Fecha Registro </div></td>\n").
		append("<td width='150' class='txtTablaNone' ><div align='right'>"+Functions.dateToString2(vo.getFechaReg())+"</div> </td>\n").
//		append("<td width='150' class='txtTablaNone' ><div align='left'>Clave Usuario</div></td>\n").
//		append("<td width='150' class='txtTablaNone' ><div align='right'>"+vo.getClaveUsuario()+"</div> </td>\n").

		
		append("<td width='150' class='txtTablaNone' ><div align='left'></div></td>\n").
		append("<td width='150' class='txtTablaNone' ><div align='right'></div> </td>\n").

		
		append("<tr>\n").
		append("<td  class='header' colspan='4' >Detalle Cotizacion Omitida </td>\n").
		append("</tr>\n");			
		form.append("</table>\n");
		
		//Encabezados de Detalles de la cotizacion
		
		
		if(!vo.getDetalles().isEmpty()){
			form.append("<table class='fondoGeneralTabla' width='900' border='0' cellspacing='1' cellpadding='1'>");
			form.append("<tr>\n").
			append("<td  class='subHeader' >Percepcion </td>\n").
			append("<td  class='subHeader' >Remuneracion</td>\n").
			append("<td  class='subHeader' >Concepto Omitido</td>\n").
			append("</tr>\n");
			
			for(CrtDetBaseCotOmDet obj:vo.getDetalles()){
				if(obj.getIdConceptoOmitido()>1){
					typeClassIndex++;
					importRemuneracion+=obj.getImRemuneracion();
					typeClass = (typeClassIndex%2!=0) ? "txtTablaNone" : "txtTablaPar";
					form.append("<tr class='"+typeClass+"'>\n").
					append("<td>"+obj.getTxRemuneracion()+" </td>\n").
					append("<td><div align='right'>"+Functions.currencyMask(obj.getImRemuneracion())+"</div></td>\n").
					append("<td><div align='right'>"+obj.getIdConceptoOmitido()+"</div></td>\n").
					append("</tr>\n");
				}
			}		
			form.append("</table>");
			
			
		}
		
		//generacion de lista de percepciones	
		form.append("</td>\n");
		form.append("</tr>\n");
		form.append("</table>");
	
		
		System.out.println("tablahyml\n "+form.toString());
		//Totales e importes
		form.append("<table class='fondoGeneralTabla' width='900' border='0' cellspacing='1' cellpadding='1' >").
		append("<tr>\n").
		append("<td  width='200' class='subHeader' colspan='2' ><div align='center'>Totales</div> </td>\n").
		append("</tr>\n").
		append("<tr>\n").
		append("<td  width='200' class='txtTablaNone' ><div align='left'>Importe</div> </td>\n").
		append("<td  width='185' class='txtTablaNone'><div align='right'>"+Functions.currencyMask(importe)+"</div></td>\n").
		append("</tr>\n").
		append("<tr>\n").
		append("<td width='200' class='txtTablaPar' ><div align='left'>IGUAL A:BASE DE COTIZACIÓN AUTODETERMINADA </div></td>\n").
		append("<td width='185' class='txtTablaPar'><div align='right'>"+Functions.currencyMask(cotizacionAuto)+"</div></td>\n").
		append("</tr>\n").
		append("<tr>\n").
		append("<td width='200' class='txtTablaNone' ><div align='left'>MENOS:BASE DE COTIZACIÓN PAGADA</div> </td>\n").
		append("<td  width='185' class='txtTablaNone'><div align='right'>"+Functions.currencyMask(sumaPag)+"</div></td>\n").
		append("</tr>\n").
		append("<tr>\n").
		append("<td width='200' class='txtTablaPar' ><div align='left'>IGUAL A:BASE DE COTIZACIÓN OMITIDA</div> </td>\n").
		append("<td width='185' class='txtTablaPar' ><div align='right'>"+Functions.currencyMask(cotizacioOmitida)+"</div></td>\n").
		append("</tr>\n").append("</table>");
		
		
		return form;
	}
	
	
	/**
	 * Recupera toda la informacion asociada  un registro patronal ,folio y periodo
	 * @param cec ConsultaEstudioCorreccionVO
	 * @param String registroPatronal
	 * @return CedulaRVO Datos de la cedula R
	 */
	private List<CedulaRVO> obtenerDatosCedula(ConsultaEstudioCorreccionVO cec, String registroPatronal){
		
		String SQL=ConsultasEstudioCorreccion.CONSULTA_CEDULA_R;
		SQL = setParameters(SQL, cec.getFolioCorreccion(),cec.getPeriodo(),registroPatronal);
		List<CedulaRVO> listaRV=null;
		List<?> lista=catalogoServiceBean.consultaSQL(SQL);
		listaRV=transformOBJListTo(lista);		
		return 	listaRV;
	}
	
	/**
	 * Convierte el array de tipo Object (Object[])
	 * el cual fue arrojado por el servicio genérico
	 * a la consulta realizada y lo transforma a
	 * una lista de tipo CedulaRVO.
	 * @see CedulaRVO
	 * @param lista
	 * @author Jorge Hernandesz Almazan
	 * @return List<CedulaRVO>
	 */
	private List<CedulaRVO> transformOBJListTo(List<?> lista){
			
		List<CedulaRVO> listCedulaR = new ArrayList<CedulaRVO>();
		CedulaRVO vo=null;
		if(lista!=null && !lista.isEmpty()){
			Iterator<?> iter = lista.iterator();
			Object[] currentObj = null;	
			while(iter.hasNext()){
				currentObj = (Object[])iter.next();
				vo=	new CedulaRVO(currentObj);
				obtenerDetalleCedulaR(vo);
				listCedulaR.add(vo);
			}
		}
			return listCedulaR;
	
	}
	
	/**
	 *Recupera la lista de percepcion de acuerdo a la cotizacion omitida y se agrega al VO correspondiente
	 * @see CedulaRVO
	 * @param CedulaR
	 * @author Jorge Hernandesz Almazan
	 */
	private void obtenerDetalleCedulaR(CedulaRVO cedulaR){
		//Declaracion y construccion de query
		String SQL=ConsultasEstudioCorreccion.CONSULTA_DETALLE_CEDULA_R.replace("{1}", cedulaR.getClaveDetBaseCot());
		Object[] currentObj = null;
		CrtDetBaseCotOmDet de=null;
		List<?> lista=catalogoServiceBean.consultaSQL(SQL);
		Iterator<?> iter = lista.iterator();
		if(lista!=null && !lista.isEmpty()){
			while(iter.hasNext()){
				int i=0;
				currentObj = (Object[])iter.next();
				de=new CrtDetBaseCotOmDet();
				de.setCveDetBaseCotOmDet(Long.parseLong(String.valueOf(currentObj[i++])));
				de.setCrtDetBaseCotOmitida(new BigInteger(String.valueOf(currentObj[i++])));
				de.setCrcPercepciones(Integer.parseInt(String.valueOf(currentObj[i++])));
				de.setImRemuneracion(Double.parseDouble(String.valueOf(currentObj[i++])));
				de.setIdConceptoOmitido(Integer.parseInt(String.valueOf(currentObj[i++])));
				de.setTxRemuneracion(String.valueOf(currentObj[i++]));
				cedulaR.getDetalles().add(de);
			}
		}		
	}	
}
