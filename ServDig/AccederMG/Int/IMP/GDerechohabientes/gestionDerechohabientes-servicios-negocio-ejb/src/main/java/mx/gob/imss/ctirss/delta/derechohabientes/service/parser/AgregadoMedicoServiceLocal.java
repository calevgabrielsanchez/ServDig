package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Local
public interface AgregadoMedicoServiceLocal {

	/**
	 * Metodo para generarf el agregado medico, recibe los siguientes parametros
	 * @param cabeza - Debe contener al menos los siguientes atributos: asignacionNSS, patronSujetoObligado
	 * @param calidad - La calidad de la persona a registrar o modificar dentro del grupo familiar
	 * @param persona - La persona a la que se le genera el agregado, debe contener al menos lo siguiente: sexo, fechaNacimiento, numAnioRegistro
	 * @param patronesVigentes - La lista de patrones vigentes, en caso de mandarla nula, se buscaran los patrones vigentes de acuerdo al nss
	 * @return String - agregado medico
	 */
	String getAgregadoMedico(CabezaGrupoFamiliar cabeza, int calidad, Fisica persona, List<SujetoObligado> patronesVigentes);
}
