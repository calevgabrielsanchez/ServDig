package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.identificador.IdentificadoresNoExistentesException;
import mx.gob.imss.ctirss.delta.exception.individuo.identificador.PersonaSinIdentificadoresException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.IdentificadoresPersonaMoralServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.persistence.DitIdentificadorMoral;
import mx.gob.imss.ctirss.gestionpersonas.servicios.entity.IdentificadoresPersonaMoralServiceEntityLocal;

/**
 * 140912
 * 
 * @author ICCSRG
 * 
 */
@Stateless(name = "identificadoresPersonaMoralServiceBusiness", mappedName = "identificadoresPersonaMoralServiceBusiness")
public class IdentificadoresPersonaMoralServiceBusiness extends
		AbstractServiceBusiness implements
		IdentificadoresPersonaMoralServiceBusinessRemote {

	@EJB
	private IdentificadoresPersonaMoralServiceEntityLocal identificadoresPersonaMoralServiceEntity;

	@EJB
	private IdentificadoresPersonaMoralServiceUtilityLocal identificadoresPersonaMoralServiceUtility;

	/**
	 * Metodo encargado de registrar el identificador de una persona moral
	 * 
	 * @param moral
	 * @throws IdentificadoresNoExistentesException
	 */
	public void registrar(Moral moral)
			throws IdentificadoresNoExistentesException {

		List<Identificador> identificadores = moral.getIdentificadores();
		if (identificadores == null) {
			throw new IdentificadoresNoExistentesException();
		}

		// Iteramos la lista de los identificadores, y guardamos en BD una por
		// una
		for (Identificador identificador : identificadores) {
			DitIdentificadorMoral ditIdentificadorMoral = identificadoresPersonaMoralServiceUtility
					.transformarAEntidad(identificador, moral);
			identificadoresPersonaMoralServiceEntity.registrar(ditIdentificadorMoral);
		}

	}

	/**
	 * Metodo encargado de actualizar el identificador de una persona moral
	 * 
	 * @param moral
	 */
	@Override
	public void actualizar(Moral moral)
			throws IdentificadoresNoExistentesException {
		// TODO Auto-generated method stub
	}

	@Override
	public void expirarIdentificador(Identificador identificador) {

		DitIdentificadorMoral ditIdentificadorMoral = new DitIdentificadorMoral();
		ditIdentificadorMoral.setCveIdIdentificadorMoral(identificador
				.getIdIdentificador());

		this.identificadoresPersonaMoralServiceEntity.expirarIdentificador(ditIdentificadorMoral);

	}

	@Override
	public List<Identificador> obtenerIdentificadoresPersona(Long cvePersona)
			throws PersonaSinIdentificadoresException {

		List<DitIdentificadorMoral> ditIdentificadores = this.identificadoresPersonaMoralServiceEntity
				.obtenerIdentificadoresPersona(cvePersona);

		List<Identificador> identificadores = new ArrayList<Identificador>();

		for (DitIdentificadorMoral ditIdentificadorMoral : ditIdentificadores) {
			identificadores.add(this.identificadoresPersonaMoralServiceUtility
					.transformarAModelo(ditIdentificadorMoral));
		}

		return identificadores;

	}

}
