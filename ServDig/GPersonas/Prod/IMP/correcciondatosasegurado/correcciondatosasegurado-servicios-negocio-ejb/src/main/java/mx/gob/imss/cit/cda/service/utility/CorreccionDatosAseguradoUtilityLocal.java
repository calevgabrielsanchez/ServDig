package mx.gob.imss.cit.cda.service.utility;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;

@Local
public interface CorreccionDatosAseguradoUtilityLocal {
	String DATE_MASK= "dd/MM/yyyy";
	
	String convertDateToString(Date date);
	
	Date convertStringToDate(String dateString) throws ParseException;
	
	Map<String, Object> generarJavaDataWf(String json);
	
	String generarJsonDataWf(Map<String, Object> data);

	void filtraCuentasCDA(List<PeriodoMovimientoAfiliatorio> periodos,FiltrosUtilityLocal<PeriodoMovimientoAfiliatorio> filter);
	void filtraCuentasCDAUltimoMovimiento(List<PeriodoMovimientoAfiliatorio> periodos,FiltrosUtilityLocal<PeriodoMovimientoAfiliatorio> filter);
	int generaDigitoVerificadorRP(String nrp);
	Date convertirStringToDateMask(String date, String mask);
}
