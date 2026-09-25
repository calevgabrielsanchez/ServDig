package mx.gob.imss.cit.cda.web.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.web.app.responsable.model.CuentaIndividual;
import mx.gob.imss.cit.cda.web.app.responsable.model.PeriodoCuentaIndividual;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CuentaIndividualVO;

@Component
public class ReadCuentaIndividualUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReadCuentaIndividualUtils.class);

	@SuppressWarnings("unchecked")
	public Page<CuentaIndividual> convertirAmodelo(DataPage dataPage) {

		CuentaIndividual cuentaVO = null;
		Page<CuentaIndividual> page = new Page<CuentaIndividual>();
		List<CuentaIndividual> list = new ArrayList<CuentaIndividual>();
		List<PeriodoCuentaIndividual> periodos = null;

		if (dataPage != null && dataPage.getData() != null) {

			for (CuentaIndividualVO bandeja : (Collection<CuentaIndividualVO>) dataPage.getData()) {				
				if (list.isEmpty() || !contieneRP(list, bandeja.getRegistroPatronal())) {
					cuentaVO = new CuentaIndividual();
					periodos = new ArrayList<PeriodoCuentaIndividual>();
					cuentaVO.setRegistroPatronal(bandeja.getRegistroPatronal());
					cuentaVO.setClaveCiz(bandeja.getClaveCiz());
					cuentaVO.setClaveDelegacionOrigen(bandeja.getClaveDelegacionOrigen());
					cuentaVO.setNssDestino(bandeja.getNss());
					list.add(cuentaVO);
				}
				PeriodoCuentaIndividual periodo = new PeriodoCuentaIndividual();
				periodo.setClaveModalidad(bandeja.getClaveModalidad());
				periodo.setFechaInicioMovimiento(bandeja.getFechaInicioMovimiento());
				periodo.setNumeroConsecutivoPeriodos(bandeja.getNumeroConsecutivoPeriodos());
				periodo.setCurp(bandeja.getCurp());
				periodo.setFechaFinalMovimiento(bandeja.getFechaFinalMovimiento());
				periodo.setOrigenMovimientoInicial(bandeja.getOrigenMovimientoInicial());
				periodo.setOrigenMovimientoFinal(bandeja.getOrigenMovimientoFinal());
				periodo.setTipoMovimientoIniintcial(bandeja.getTipoMovimientoIniintcial());
				periodo.setTipoMovimientoFinal(bandeja.getTipoMovimientoFinal());
				periodo.setFechaRecepcionMovimiento(bandeja.getFechaRecepcionMovimiento());
				periodo.setSalarioBase(bandeja.getSalarioBase());
				periodo.setTipoSalario(bandeja.getTipoSalario());
				periodo.setJornadaSemanal(bandeja.getJornadaSemanal());
				periodo.setEventual(bandeja.getEventual());
				periodo.setSubrogacionServicio(bandeja.getSubrogacionServicio());
				periodo.setHuelga(bandeja.getHuelga());
				periodo.setExtemporaneoConvenioSuspencion(bandeja.getExtemporaneoConvenioSuspencion());
				periodo.setFechaActualizacion(bandeja.getFechaActualizacion());
				periodo.setFechaActualizacion(bandeja.getFechaActualizacion());
				periodo.setFechaCarga(bandeja.getFechaCarga());
				periodo.setNss(bandeja.getNss());
				periodos.add(periodo);
				cuentaVO.setPeriodos(periodos);
			}
		}
		page.setData(list);
		page.setCurrentPage(dataPage.getCurrentPage());
		page.setTotalOfRecords(dataPage.getTotalOfRecords());
		page.setPageSize(dataPage.getPageSize());

		return page;
	}

	private boolean contieneRP(List<CuentaIndividual> list, String rp) {
		boolean contieneRP = false;
		if (!list.isEmpty()) {
			for (CuentaIndividual cuenta : list) {
				if (cuenta.getRegistroPatronal().equals(rp)) {
					contieneRP = true;
					break;
				}
			}
		}
		return contieneRP;
	}
}