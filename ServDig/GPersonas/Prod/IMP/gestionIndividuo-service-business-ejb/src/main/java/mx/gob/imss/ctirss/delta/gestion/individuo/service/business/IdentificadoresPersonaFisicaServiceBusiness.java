package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.identificador.IdentificadoresNoExistentesException;
import mx.gob.imss.ctirss.delta.exception.individuo.identificador.PersonaSinIdentificadoresException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.IdentificadoresPersonaFisicaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.persistence.DitIdentificador;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.IdentificadoresPersonaFisicaServiceEntityLocal;

/**
 * 140912
 * 
 * @author ICCSRG
 * 
 */
@Stateless(name = "identificadorPersonaFisicaServiceBusiness", mappedName = "identificadorPersonaFisicaServiceBusiness")
public class IdentificadoresPersonaFisicaServiceBusiness extends
		AbstractServiceBusiness implements
		IdentificadoresPersonaFisicaServiceBusinessRemote {

	@EJB
	IdentificadoresPersonaFisicaServiceEntityLocal identificadoresPersonaFisicaServiceEntity;

	@EJB
	IdentificadoresPersonaFisicaServiceUtilityLocal identificadoresPersonaFisicaServiceUtility;

	/**
	 * Metodo encargado de registrar el identificador de una persona fisica
	 * 
	 * @param fisica
	 * @throws IdentificadoresNoExistentesException
	 */
	public void registrar(Fisica fisica)
			throws IdentificadoresNoExistentesException {

		List<Identificador> identificadores = fisica.getIdentificadores();
		if (identificadores == null) {
			throw new IdentificadoresNoExistentesException();
		}

		// Iteramos la lista de los identificadores, y guardamos en BD una por
		// una
		for (Identificador identificador : identificadores) {
			DitIdentificador ditIdentificador = identificadoresPersonaFisicaServiceUtility
					.transformarAEntidad(identificador, fisica);
			identificadoresPersonaFisicaServiceEntity
					.registrar(ditIdentificador);
		}

	}

	/**
	 * Metodo encargado de actualizar el identificador de una persona fisica
	 * 
	 * @param fisica
	 */
	@Override
	public void actualizar(Fisica fisica)
			throws IdentificadoresNoExistentesException {
		// TODO Auto-generated method stub
	}

	@Override
	public void expirarIdentificador(Identificador identificador) {

		DitIdentificador ditIdentificador = new DitIdentificador();
		ditIdentificador.setCveIdIdentificador(identificador
				.getIdIdentificador());

		this.identificadoresPersonaFisicaServiceEntity
				.expirarIdentificador(ditIdentificador);

	}

	@Override
	public List<Identificador> obtenerIdentificadoresPersona(Long cvePersona)
			throws PersonaSinIdentificadoresException {

		List<DitIdentificador> ditIdentificadores = this.identificadoresPersonaFisicaServiceEntity
				.obtenerIdentificadoresPersona(cvePersona);

		List<Identificador> identificadores = new ArrayList<Identificador>();

		for (DitIdentificador ditIdentificador : ditIdentificadores) {
			identificadores.add(this.identificadoresPersonaFisicaServiceUtility
					.transformarAModelo(ditIdentificador));
		}

		return identificadores;

	}

}
