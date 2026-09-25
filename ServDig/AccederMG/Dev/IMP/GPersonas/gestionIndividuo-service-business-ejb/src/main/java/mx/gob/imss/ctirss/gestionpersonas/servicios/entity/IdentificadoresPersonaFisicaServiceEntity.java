package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.exception.individuo.identificador.PersonaSinIdentificadoresException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DitIdentificador;

@Stateless(name = "identificadorPersonaFisicaServiceEntity", mappedName = "identificadorPersonaFisicaServiceEntity")
public class IdentificadoresPersonaFisicaServiceEntity extends
		AbstractServiceEntity implements
		IdentificadoresPersonaFisicaServiceEntityLocal {

	/**
	 * Metodo encargado de realizar el registro de los identificadores asociados
	 * a una persona fisica en BDU
	 * 
	 * @param ditIdentificador
	 */
	@Override
	public void registrar(DitIdentificador ditIdentificador) {

		Date fechaAlta = new Date();

		ditIdentificador.setFecRegistroActualizado(fechaAlta);
		ditIdentificador.setFecRegistroAlta(fechaAlta);

		em.persist(ditIdentificador);

	}

	/**
	 * Metodo encargado de realizar la actualizacion de los identificadores
	 * asociados a una persona fisica en BDU
	 * 
	 * @param ditIdentificador
	 */
	@Override
	public void actualizar(DitIdentificador ditIdentificador) {
		// TODO Auto-generated method stub
	}

	@Override
	public void expirarIdentificador(DitIdentificador ditIdentificador) {

		ditIdentificador = this.em.find(DitIdentificador.class,
				ditIdentificador.getCveIdIdentificador());

		ditIdentificador.setFecRegistroBaja(new Date());
		ditIdentificador.setIndVigente(BigDecimal.valueOf(0));

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DitIdentificador> obtenerIdentificadoresPersona(Long cvePersona)
			throws PersonaSinIdentificadoresException {

		List<DitIdentificador> identificadores = null;

		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("from DitIdentificador identificador ");
		jpaQuery.append("where identificador.ditPersona.cveIdPersona = :cvePersona ");
		jpaQuery.append("and identificador.indVigente = 1");

		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cvePersona", cvePersona);

		identificadores = query.getResultList();

		if (identificadores == null || identificadores.isEmpty()) {
			throw new PersonaSinIdentificadoresException(cvePersona);
		}

		return identificadores;

	}

}
