package mx.gob.imss.ctirss.delta.derechohabientes.service.parser;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness.GrupoFamiliarServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DeltaUtils;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Stateless(name = "agregadoMedicoService", mappedName = "agregadoMedicoService")
public class AgregadoMedicoService extends AbstractServiceBusiness implements AgregadoMedicoServiceLocal {

	@EJB
	private GrupoFamiliarServiceLocal grupoFamiliarServiceLocal;
	
	/**
	 * Metodo para generarf el agregado medico, recibe los siguientes parametros
	 * @param cabeza - Debe contener al menos los siguientes atributos: asignacionNSS, patronSujetoObligado
	 * @param calidad - La calidad de la persona a registrar o modificar dentro del grupo familiar
	 * @param persona - La persona a la que se le genera el agregado, debe contener al menos lo siguiente: sexo, fechaNacimiento, numAnioRegistro
	 * @param patronesVigentes - La lista de patrones vigentes, en caso de mandarla nula, se buscaran los patrones vigentes de acuerdo al nss
	 * @return String - agregado medico
	 */
	@Override
	public String getAgregadoMedico(CabezaGrupoFamiliar cabeza, int calidad, Fisica persona,
			List<SujetoObligado> patronesVigentes) {
		
		if(patronesVigentes == null || patronesVigentes.isEmpty()) {
			AsignacionNSS nss = new AsignacionNSS(cabeza.getAsignacionNSS());
			try {
				patronesVigentes = grupoFamiliarServiceLocal.getPatronesAsegurado(nss);
			} catch (DerechohabientesBusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(patronesVigentes == null || patronesVigentes.isEmpty()) {
				patronesVigentes = new ArrayList<SujetoObligado>();
				patronesVigentes.add(cabeza.getPatronSujetoObligado());
			}
			
		}
		
		return DeltaUtils.getAgregadoMedico(cabeza.getCalidadParentesco().getIdParentesco(), calidad, persona.getSexo().getIdSexo(), persona.getFechaNacimiento(), persona.getAnioRegistroNac(), patronesVigentes);
	}

}
