package mx.gob.imss.cit.cda.service.utility;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramCorreccionNss;

@Local
public interface CorreccionDatosAseguradoUtilityLocal {
	String DATE_MASK = "dd/MM/yyyy";

	String convertDateToString(Date date);

	Date convertStringToDate(String dateString) throws ParseException;

	Map<String, Object> generarJavaDataWf(String json);

	String generarJsonDataWf(Map<String, Object> data);

	void filtraCuentasCDA(List<PeriodoMovimientoAfiliatorio> periodos,
			FiltrosUtilityLocal<PeriodoMovimientoAfiliatorio> filter);

	void filtraCuentasCDAUltimoMovimiento(
			List<PeriodoMovimientoAfiliatorio> periodos,
			FiltrosUtilityLocal<PeriodoMovimientoAfiliatorio> filter);

	int generaDigitoVerificadorRP(String nrp);

	Date convertirStringToDateMask(String date, String mask);

	/**
	 * Verifica si existe un nss en la solicitud
	 * 
	 * @param nss
	 * @param listaNss
	 * @return
	 */
	boolean existeNssSolictud(String nss, List<CorreccionNSS> listaNss);

	/**
	 * Obtiene  tipo tramite para una aclaracion
	 * 
	 * @param idAclaracion
	 * @return DicTipoTramCorreccionNss
	 */
	DicTipoTramCorreccionNss obtenerTipoTramite(Long idAclaracion);
}
