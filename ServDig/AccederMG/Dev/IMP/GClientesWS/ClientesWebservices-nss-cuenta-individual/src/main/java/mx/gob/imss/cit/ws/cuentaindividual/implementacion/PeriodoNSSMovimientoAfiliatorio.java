package mx.gob.imss.cit.ws.cuentaindividual.implementacion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.imss.cit.ws.cuentaindividual.cliente.CuentaIndividualVo;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.RespuestaCuentaIndividual;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.WSNssCuentaIndividual;
import mx.gob.imss.cit.ws.cuentaindividual.cliente.WSNssCuentaIndividual_Service;
import mx.gob.imss.ctirss.delta.model.asegurado.TipoMovtoAsegurado;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoMovtoAseguradoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PeriodoNSSMovimientoAfiliatorio {
 
	private static String DATE_PATTERN = "yyyy-MM-dd";
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public List<PeriodoMovimientoAfiliatorio> obtenerPeriodosMovimientoAfiliatorioNSS(String nss){
		
		final WSNssCuentaIndividual_Service service = new WSNssCuentaIndividual_Service();
		final WSNssCuentaIndividual port = service.getWSNssCuentaIndividualPort();
		
		List<PeriodoMovimientoAfiliatorio> periodos = null;
		
		try {
			logger.error("WebserviceCurp. Thread. El proceso inicia consulta a RENAPO. " + new Date());
			 periodos = obtenerPeriodosMovimientoAfiliatorio(port.getCuentaIndividual(nss));
			logger.error("WebserviceCurp. Thread. Se finaliza la consulta satisfactoriamente a RENAPO. " + new Date());
		} catch (Exception e) {
			logger.error("WebserviceCurp. Thread. Se genero un error al accesar el webservice",e);
		}

		logger.debug("PeriodoNSSMovimientoAfiliatorio.obtenerPeriodosMovimientoAfiliatorioNSS(String) {} ", new Date());
		
		return periodos;
	}
	
	private List<PeriodoMovimientoAfiliatorio> obtenerPeriodosMovimientoAfiliatorio(RespuestaCuentaIndividual respuesta){
		List<PeriodoMovimientoAfiliatorio> periodos = new ArrayList<PeriodoMovimientoAfiliatorio>();
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
		for (CuentaIndividualVo cuenta : respuesta.getCuentaIndividual()) {
			PeriodoMovimientoAfiliatorio periodo = new PeriodoMovimientoAfiliatorio();
			try {
				
				TipoMovtoAsegurado mvtoFinal = new TipoMovtoAsegurado();
				mvtoFinal.setIdTipoMvtoAsegurado(Integer.valueOf(cuenta.getTipoMovimientoFinal()));
				mvtoFinal.setDesTipoMvtoAsegurado(TipoMovtoAseguradoEnum.getById(Integer.valueOf(cuenta.getTipoMovimientoFinal()).longValue()).getDesTipoMvtoAsegurado());
				TipoMovtoAsegurado mvtoInicial = new TipoMovtoAsegurado();
				mvtoInicial.setIdTipoMvtoAsegurado(Integer.valueOf(cuenta.getTipoMovimientoIniintcial()).longValue());
				mvtoFinal.setDesTipoMvtoAsegurado(TipoMovtoAseguradoEnum.getById(Integer.valueOf(cuenta.getTipoMovimientoIniintcial()).longValue()).getDesTipoMvtoAsegurado());
				
				periodo.setFechaFinalMovimiento(dateFormat.parse(cuenta.getFechaFinalMovimiento()));
				periodo.setFechaInicioMovimiento(dateFormat.parse(cuenta.getFechaInicioMovimiento()));
				periodo.setNrp(cuenta.getRegistroPatronal());
				periodo.setNss(cuenta.getNss());
				periodo.setTipoMovimientoFinal(mvtoFinal);
				periodo.setTipoMovimientoInicial(mvtoInicial);
				periodo.setCveModalidad(getModalidad(cuenta));
				periodos.add(periodo);
				
			}catch (ParseException e) {
				logger.debug("Error al transformar las fechas del periodo, se excuye de la lista", e);
			}
		}
		return periodos;
	}
	
	private Modalidad getModalidad(CuentaIndividualVo cuenta){
		Modalidad modalidad = new Modalidad();
		for (ModalidadEnum modalidadEnum : ModalidadEnum.values()) {
			if(modalidadEnum.getNumModalidad().equals(cuenta.getClaveModalidad())){
				modalidad.setDesCorta(modalidadEnum.getNumModalidad());
				modalidad.setIdModalidad(modalidadEnum.getId());
				break;
			}
		}
		return modalidad;
	}
}
