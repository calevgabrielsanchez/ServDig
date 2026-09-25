package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.exception.individuo.identificador.PersonaSinIdentificadoresException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.persistence.DitIdentificadorMoral;

@Stateless(name = "identificadoresPersonaMoralServiceEntity", mappedName = "identificadoresPersonaMoralServiceEntity")
public class IdentificadoresPersonaMoralServiceEntity extends
		AbstractServiceEntity implements
		IdentificadoresPersonaMoralServiceEntityLocal {

	@Override
	public void registrar(DitIdentificadorMoral ditIdentificadorMoral) {

		Date fechaAlta = new Date();

		ditIdentificadorMoral.setFecRegistroActualizado(fechaAlta);
		ditIdentificadorMoral.setFecRegistroAlta(fechaAlta);

		em.persist(ditIdentificadorMoral);

	}

	@Override
	public void actualizar(DitIdentificadorMoral ditIdentificadorMoral) {
		// TODO Auto-generated method stub
	}

	@Override
	public void expirarIdentificador(DitIdentificadorMoral ditIdentificadorMoral) {

		ditIdentificadorMoral = this.em.find(DitIdentificadorMoral.class,
				ditIdentificadorMoral.getCveIdIdentificadorMoral());

		ditIdentificadorMoral.setFecRegistroBaja(new Date());
		ditIdentificadorMoral.setIndVigente(BigDecimal.valueOf(0));

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DitIdentificadorMoral> obtenerIdentificadoresPersona(
			Long cvePersonaMoral) throws PersonaSinIdentificadoresException {

		List<DitIdentificadorMoral> identificadores = null;

		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("from DitIdentificadorMoral identificador ");
		jpaQuery.append("where identificador.ditPersonaMoral.cveIdPersonaMoral = :cvePersonaMoral ");
		jpaQuery.append("and identificador.indVigente = 1");

		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cvePersonaMoral", cvePersonaMoral.longValue());

		identificadores = query.getResultList();

		if (identificadores == null || identificadores.isEmpty()) {
			throw new PersonaSinIdentificadoresException(cvePersonaMoral);
		}
				
		return identificadores;

	}

}
