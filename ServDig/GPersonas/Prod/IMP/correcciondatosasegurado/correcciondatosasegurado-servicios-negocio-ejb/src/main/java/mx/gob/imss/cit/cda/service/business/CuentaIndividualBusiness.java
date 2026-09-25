package mx.gob.imss.cit.cda.service.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.service.utility.CorreccionDatosAseguradoUtilityLocal;
import mx.gob.imss.cit.cda.service.utility.FiltrosUtilityLocal;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.RespuestaCuentaIndividual;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.WSNssCuentaIndividual;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.WSNssCuentaIndividual_Service;
import mx.gob.imss.cit.ws.cuentaindividual.implementacion.PeriodoNSSMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.enums.TipoMovtoAseguradoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CuentaIndividualVO;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;

@Stateless(name = "cuentaIndividualBusiness", mappedName = "cuentaIndividualBusiness")
public class CuentaIndividualBusiness extends AbstractServiceUtility implements CuentaIndividualRemote{
	
	private final Logger LOGGER = LoggerFactory.getLogger(CuentaIndividualBusiness.class);
	private static final int ANIO_FINAL=9999;
	private static final int MES_FINAL=11;
	private static final int DIA_FINAL=31;
	
	@EJB
	private CorreccionDatosAseguradoUtilityLocal correccionAseguradoUtilityLocal;
	
	@EJB
	private FiltrosUtilityLocal<PeriodoMovimientoAfiliatorio> filtrosUtilityLocal;

	@Override
	public List<PeriodoMovimientoAfiliatorio> consultarMovimientosCuentaIndividual(String nss) {
		
		PeriodoNSSMovimientoAfiliatorio periodoNSSMovimientoAfiliatorio = new PeriodoNSSMovimientoAfiliatorio();
		
		List<PeriodoMovimientoAfiliatorio> periodos = periodoNSSMovimientoAfiliatorio.obtenerPeriodosMovimientoAfiliatorioNSS(nss);
		
		List<PeriodoMovimientoAfiliatorio> periodosFormateados = new ArrayList<PeriodoMovimientoAfiliatorio>();
		
		if(periodos != null && !periodos.isEmpty()){
			ordenarCuentasIndividuales(periodos);
			periodosFormateados = filtrarMovimientoAfiliatorios(agruparPeriodosPorPatron(periodos));
			ordenarCuentasIndividualesFormato(periodosFormateados);
		}
		
		return periodosFormateados;
	}
	
	private HashMap<String, List<PeriodoMovimientoAfiliatorio>> agruparPeriodosPorPatron(List<PeriodoMovimientoAfiliatorio> cuentas){
		HashMap<String, List<PeriodoMovimientoAfiliatorio>> hashMap = new HashMap<String, List<PeriodoMovimientoAfiliatorio>>();
		for(PeriodoMovimientoAfiliatorio periodo: cuentas){
			if (!hashMap.containsKey(periodo.getNrp())) {
			    List<PeriodoMovimientoAfiliatorio> list = new ArrayList<PeriodoMovimientoAfiliatorio>();
			    list.add(periodo);
			    hashMap.put(periodo.getNrp(), list);
			} else {
				hashMap.get(periodo.getNrp()).add(periodo);
			}
		}		
		return hashMap;		
	}
	
	
	private List<PeriodoMovimientoAfiliatorio> filtrarMovimientoAfiliatorios(HashMap<String, List<PeriodoMovimientoAfiliatorio>> cuentas){
		List<PeriodoMovimientoAfiliatorio> periodoMovimientoAfiliatorios = new ArrayList<PeriodoMovimientoAfiliatorio>();
		
		for (Map.Entry<String, List<PeriodoMovimientoAfiliatorio>> entry : cuentas.entrySet()) {
	        List<PeriodoMovimientoAfiliatorio> periodos = new ArrayList<PeriodoMovimientoAfiliatorio>();
	        periodos.addAll(entry.getValue());
			
			PeriodoMovimientoAfiliatorio primerMovimientoCuentaIndividual= periodos.get(0);
			PeriodoMovimientoAfiliatorio ultimoMovimientoCuentaIndividual= periodos.get(periodos.size()-1);
			correccionAseguradoUtilityLocal.filtraCuentasCDA(periodos,filtrosUtilityLocal);
			if(!periodos.isEmpty()){
				if(TipoMovtoAseguradoEnum.MODIF_SALARIO.getIdTipoMovtoAsegurado().equals(periodos.get(0).getTipoMovimientoInicial().getIdTipoMvtoAsegurado() )){
					periodos.add(0, primerMovimientoCuentaIndividual);
				}
				if(filtrosUtilityLocal.shouldRemoveLast(ultimoMovimientoCuentaIndividual)){
					periodos.add(ultimoMovimientoCuentaIndividual);
				}
				periodoMovimientoAfiliatorios.addAll(prepararListaRespuesta(periodos));
			}
	    }
		return periodoMovimientoAfiliatorios;
	}
	
	private List<PeriodoMovimientoAfiliatorio> prepararListaRespuesta(List<PeriodoMovimientoAfiliatorio> cuentas){
		List<PeriodoMovimientoAfiliatorio> cuentasFormato = new ArrayList<PeriodoMovimientoAfiliatorio>();
		Iterator<PeriodoMovimientoAfiliatorio> cuentasIterator = cuentas.iterator();
	    while (cuentasIterator.hasNext()) {
	    	PeriodoMovimientoAfiliatorio cuentaInicial = cuentasIterator.next();
	    	PeriodoMovimientoAfiliatorio cuentaFinal = null;
	    	
	    	if(TipoMovtoAseguradoEnum.BAJAS.getIdTipoMovtoAsegurado().equals(cuentaInicial.getTipoMovimientoFinal().getIdTipoMvtoAsegurado())
	    			|| TipoMovtoAseguradoEnum.ACTUAL.getIdTipoMovtoAsegurado().equals(cuentaInicial.getTipoMovimientoFinal().getIdTipoMvtoAsegurado())){
	    		cuentaFinal = cuentaInicial;
	    	}
	    	else if(cuentasIterator.hasNext()){
	        	 cuentaFinal = cuentasIterator.next();
	    	}
	    	
	        PeriodoMovimientoAfiliatorio periodoIndividual = new PeriodoMovimientoAfiliatorio();
	        periodoIndividual.setFechaInicioMovimiento(cuentaInicial.getFechaInicioMovimiento());			
	        periodoIndividual.setTipoMovimientoInicial(cuentaInicial.getTipoMovimientoInicial());
			if (cuentaFinal != null){
				periodoIndividual.setFechaFinalMovimiento(cuentaFinal.getFechaFinalMovimiento());
				periodoIndividual.setTipoMovimientoFinal(cuentaFinal.getTipoMovimientoFinal());
				periodoIndividual.setNrp(generarRegistroPatronalCompleto(cuentaFinal));
				periodoIndividual.setNss(cuentaFinal.getNss());
			}else{
				periodoIndividual.setNrp(generarRegistroPatronalCompleto(cuentaInicial));
				periodoIndividual.setNss(cuentaInicial.getNss());
			}
			Calendar fechaFinal = Calendar.getInstance();
			fechaFinal.setTime(new Date());
			fechaFinal.set(ANIO_FINAL, MES_FINAL, DIA_FINAL);
			
			if(periodoIndividual.getFechaFinalMovimiento() == null ||DateUtils.isSameDay(fechaFinal.getTime(), periodoIndividual.getFechaFinalMovimiento())){
				periodoIndividual.setFechaFinalMovimiento(null);
			}
			
	        cuentasFormato.add(periodoIndividual);	        
	    }	    
		return cuentasFormato;
	}
	
	@Override
	public List<CuentaIndividualVO> consultarInformacionMovimientosCuentaIndividual(String nss) {

		WSNssCuentaIndividual_Service service = new WSNssCuentaIndividual_Service();
		WSNssCuentaIndividual port = service.getWSNssCuentaIndividualPort();
		List<CuentaIndividualVO> cuentas = new ArrayList<CuentaIndividualVO>();
		RespuestaCuentaIndividual respuesta = null;
		try {
			LOGGER.error("WebserviceCurp. Thread. El proceso inicia consulta a RENAPO. " + new Date());
			respuesta = port.getCuentaIndividual(nss);
			cuentas = obtenerCuentaIndividualVO(respuesta);
			LOGGER.error("WebserviceCurp. Thread. Se finaliza la consulta satisfactoriamente a RENAPO. " + new Date());
		} catch (Exception e) {
			LOGGER.error("WebserviceCurp. Thread. Se genero un error al accesar el webservice", e);
		}
		LOGGER.debug("PeriodoNSSMovimientoAfiliatorio.consultarInformacionMovimientosCuentaIndividual(String) {} ", new Date());

		return cuentas;
	}

	public List<CuentaIndividualVO> obtenerCuentaIndividualVO(RespuestaCuentaIndividual respuesta) {

		List<CuentaIndividualVO> cuentas = new ArrayList<CuentaIndividualVO>();

		for (mx.gob.imss.cit.ws.cuentaindividual.cliente.CuentaIndividualVo cuenta : respuesta.getCuentaIndividual()) {
			CuentaIndividualVO cuentaVO = new CuentaIndividualVO();
			try {
				cuentaVO.setNss(cuenta.getNss());
				cuentaVO.setRegistroPatronal(cuenta.getRegistroPatronal());
				cuentaVO.setClaveModalidad(cuenta.getClaveModalidad());
				cuentaVO.setFechaInicioMovimiento(cuenta.getFechaInicioMovimiento());
				cuentaVO.setNumeroConsecutivoPeriodos(cuenta.getNumeroConsecutivoPeriodos());
				cuentaVO.setCurp(cuenta.getCurp());
				cuentaVO.setFechaFinalMovimiento(cuenta.getFechaFinalMovimiento());
				cuentaVO.setOrigenMovimientoInicial(cuenta.getOrigenMovimientoInicial());
				cuentaVO.setOrigenMovimientoFinal(cuenta.getOrigenMovimientoFinal());
				cuentaVO.setTipoMovimientoIniintcial(cuenta.getTipoMovimientoIniintcial());
				cuentaVO.setTipoMovimientoFinal(cuenta.getTipoMovimientoFinal());
				cuentaVO.setFechaRecepcionMovimiento(cuenta.getFechaRecepcionMovimiento());
				cuentaVO.setSalarioBase(cuenta.getSalarioBase());
				cuentaVO.setTipoSalario(cuenta.getTipoSalario());
				cuentaVO.setJornadaSemanal(cuenta.getJornadaSemanal());
				cuentaVO.setEventual(cuenta.getEventual());
				cuentaVO.setSubrogacionServicio(cuenta.getSubrogacionServicio());
				cuentaVO.setHuelga(cuenta.getHuelga());
				cuentaVO.setExtemporaneoConvenioSuspencion(cuenta.getExtemporaneoConvenioSuspencion());
				cuentaVO.setFechaActualizacion(cuenta.getFechaActualizacion());
				cuentaVO.setFechaActualizacion(cuenta.getFechaActualizacion());
				cuentaVO.setClaveDelegacionOrigen(cuenta.getClaveDelegacionOrigen());
				cuentaVO.setClaveCiz(cuenta.getClaveCiz());
				cuentaVO.setFechaCarga(cuenta.getFechaCarga());				
				cuentas.add(cuentaVO);
			} catch (Exception e) {
				LOGGER.debug("Error al transformar las fechas del periodo, se excuye de la lista", e);
			}
		}

		return cuentas;
	}
	
	private void ordenarCuentasIndividuales(List<PeriodoMovimientoAfiliatorio>periodos){	
		Collections.sort(periodos,new Comparator<PeriodoMovimientoAfiliatorio>() {

			@Override
			public int compare(PeriodoMovimientoAfiliatorio o1,
					PeriodoMovimientoAfiliatorio o2) {
				return compareByDate(o1.getFechaFinalMovimiento(), o2.getFechaFinalMovimiento())==0?compareByDate(o1.getFechaInicioMovimiento(), o2.getFechaInicioMovimiento()):compareByDate(o1.getFechaFinalMovimiento(), o2.getFechaFinalMovimiento());				
			}
		});
	}
	
	private void ordenarCuentasIndividualesFormato(List<PeriodoMovimientoAfiliatorio>periodos){	
		Collections.sort(periodos,new Comparator<PeriodoMovimientoAfiliatorio>() {

			@Override
			public int compare(PeriodoMovimientoAfiliatorio o1,
					PeriodoMovimientoAfiliatorio o2) {
				return -1*(compareByDate(o1.getFechaFinalMovimiento(), o2.getFechaFinalMovimiento())==0?compareByDate(o1.getFechaInicioMovimiento(), o2.getFechaInicioMovimiento()):compareByDate(o1.getFechaFinalMovimiento(), o2.getFechaFinalMovimiento()));				
			}
		});
	}
	
	private int compareByDate(Date date1, Date date2){
		int fecha = date1 == null?1:0;
		int fecha2 = date2 == null?1:0;		
		return (fecha + fecha2) == 0 ? date1.compareTo(date2): fecha -fecha2;
	}

	@Override
	public List<PeriodoMovimientoAfiliatorio> consultarUltimoMovimientoCuentaIndividual(
			String nss) {		
		PeriodoNSSMovimientoAfiliatorio periodoNSSMovimientoAfiliatorio = new PeriodoNSSMovimientoAfiliatorio();
		List<PeriodoMovimientoAfiliatorio> periodosMovimientoFinal = null;
		
		List<PeriodoMovimientoAfiliatorio> periodos = periodoNSSMovimientoAfiliatorio.obtenerPeriodosMovimientoAfiliatorioNSS(nss);
		if(periodos != null && !periodos.isEmpty()){
			ordenarCuentasIndividuales(periodos);
			correccionAseguradoUtilityLocal.filtraCuentasCDAUltimoMovimiento(periodos, filtrosUtilityLocal);
			if(!periodos.isEmpty()){
				periodosMovimientoFinal = prepararRespuestaMovimientoFinal(agruparPeriodosPorFechaFinal(periodos));
			}
		}
		return periodosMovimientoFinal;
	}
	
	private List<PeriodoMovimientoAfiliatorio> agruparPeriodosPorFechaFinal(List<PeriodoMovimientoAfiliatorio> cuentas){
		List<PeriodoMovimientoAfiliatorio> periodosMovimientoFinal = new ArrayList<PeriodoMovimientoAfiliatorio>();
		periodosMovimientoFinal.add(cuentas.get(cuentas.size()-1));
		boolean fechaMenor = false;
		int i = cuentas.size()-2;
		
		while(i>=0 && !fechaMenor){
			if(DateUtils.truncate(cuentas.get(i).getFechaFinalMovimiento(), Calendar.DATE)
					.compareTo(DateUtils.truncate(periodosMovimientoFinal.get(0).getFechaFinalMovimiento(), Calendar.DATE))<0){
				fechaMenor = true;
			}else{
				periodosMovimientoFinal.add(cuentas.get(i));
			}
			i--;
		}
	    	    
		return periodosMovimientoFinal;	
	}
	
	
	private List<PeriodoMovimientoAfiliatorio> prepararRespuestaMovimientoFinal(List<PeriodoMovimientoAfiliatorio> ultimosMovimientos){
		List<PeriodoMovimientoAfiliatorio> periodosMovimientoAfiliatorio = new ArrayList<PeriodoMovimientoAfiliatorio>();
		
		for(PeriodoMovimientoAfiliatorio ultimoPeriodoMovimientoAfiliatorio:ultimosMovimientos){
			PeriodoMovimientoAfiliatorio periodoMovimientoAfiliatorio = new PeriodoMovimientoAfiliatorio();
		if(TipoMovtoAseguradoEnum.ACTUAL.getIdTipoMovtoAsegurado().equals(ultimoPeriodoMovimientoAfiliatorio.getTipoMovimientoFinal().getIdTipoMvtoAsegurado())){
			periodoMovimientoAfiliatorio.setTipoMovimientoFinal(ultimoPeriodoMovimientoAfiliatorio.getTipoMovimientoInicial());
			periodoMovimientoAfiliatorio.setFechaFinalMovimiento(ultimoPeriodoMovimientoAfiliatorio.getFechaInicioMovimiento());
		}
		
		if(TipoMovtoAseguradoEnum.BAJAS.getIdTipoMovtoAsegurado().equals(ultimoPeriodoMovimientoAfiliatorio.getTipoMovimientoFinal().getIdTipoMvtoAsegurado())){
			periodoMovimientoAfiliatorio.setTipoMovimientoFinal(ultimoPeriodoMovimientoAfiliatorio.getTipoMovimientoFinal());
			periodoMovimientoAfiliatorio.setFechaFinalMovimiento(ultimoPeriodoMovimientoAfiliatorio.getFechaFinalMovimiento());
		}
		
		periodoMovimientoAfiliatorio.setCveModalidad(ultimoPeriodoMovimientoAfiliatorio.getCveModalidad());
		periodoMovimientoAfiliatorio.setNrp(ultimoPeriodoMovimientoAfiliatorio.getNrp());
		periodoMovimientoAfiliatorio.setNrp(generarRegistroPatronalCompleto(periodoMovimientoAfiliatorio));
		periodoMovimientoAfiliatorio.setNss(ultimoPeriodoMovimientoAfiliatorio.getNss());
		
		periodosMovimientoAfiliatorio.add(periodoMovimientoAfiliatorio);
		}
		
		return periodosMovimientoAfiliatorio;
				
	}
	
	private String generarRegistroPatronalCompleto(PeriodoMovimientoAfiliatorio periodoMovimientoAfiliatorio){
		StringBuilder nrp = new StringBuilder();
		nrp.append(periodoMovimientoAfiliatorio.getNrp());
		nrp.append(periodoMovimientoAfiliatorio.getCveModalidad().getDesCorta());
		LOGGER.info("---CDA--- NRP sin registro verificador {}",nrp.toString());
		nrp.append(correccionAseguradoUtilityLocal.generaDigitoVerificadorRP(nrp.toString()));
		return nrp.toString();
	}
}
