package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;

@Local
public interface CompararPersonasServiceUtilityLocal {

	int compararDatosBasicosPersonaFisica(Fisica fisica1, Fisica fisica2,
			Map<String, CambioComparacionEnum> diferencias);

	int compararDocumentosProbatorios(Fisica fisica1, Fisica fisica2,
			Map<String, CambioComparacionEnum> diferencias);

	Map<String, Object> compararDatosBasicosSatPersonaFisica(Persona persona1,
			Persona persona2, Map<String, CambioComparacionEnum> diferencias);

	int compararDomicilioFiscal(Persona persona1, Persona persona2,
			Map<String, CambioComparacionEnum> comparacion);

	int compararMediosFiscales(Persona persona1, Persona persona2,
			Map<String, CambioComparacionEnum> diferencias);

	int compararSituacionesSAT(SituacionSAT situacionSatImss,
			SituacionSAT situacionSatEntidadExterna,
			Map<String, CambioComparacionEnum> diferencias);

}
