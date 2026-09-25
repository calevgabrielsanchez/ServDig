package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.FactorCostosEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.ClavesRama;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.digital.modelo.cobranza.RamaCalculo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "factorCostosEntity", mappedName = "factorCostosEntity")
public class FactorCostosEntity extends AbstractServiceEntity implements
		FactorCostosEntityLocal {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FactorCostosEntity.class);

	@Override
	public List<RamaCalculo> buscarCostoSeguro(int edad, long parentesco) {
		List<RamaCalculo> ramaCalculoList = new ArrayList<RamaCalculo>();
		LOGGER.debug("buscando los costos correspondientes a la edad: {}", edad);

		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select new mx.gob.imss.digital.modelo.cobranza.RamaCalculo( ");
		jpaQuery.append("rama.desRama, ");
		jpaQuery.append("tipoAportacion.desTipoAportacion, ");
		jpaQuery.append("factorModalidad.numFactor, ");
		jpaQuery.append("rama.cveIdRama, ");
		jpaQuery.append("tipoAportacion.cveIdTipoAportacion, ");
		if (parentesco == ParentescoEnum.TITULAR.getId()) {
			jpaQuery.append("factor.impCostoRama ");
		} else {
			jpaQuery.append("factor.impCostoTotal ");
		}
		jpaQuery.append(") ");
		jpaQuery.append("from DicFactorCostosMod33 factor ");
		jpaQuery.append("join factor.dicFactorModalidadRama factorModalidad ");
		jpaQuery.append("join factorModalidad.dicTipoAportacion tipoAportacion ");
		jpaQuery.append("join factorModalidad.dicRama rama ");
		jpaQuery.append("where factor.numRangoEdadIni <= :edad ");
		jpaQuery.append("and factor.numRangoEdadFin >= :edad ");
		jpaQuery.append("and factorModalidad.dicModalidad.cveIdModalidad = :idModalidad ");
		if (parentesco != ParentescoEnum.TITULAR.getId()) {
			jpaQuery.append("and rama.cveIdRama = :idRamaFactorRiesgo ");
		}

		try{
			Query query = this.em.createQuery(jpaQuery.toString());
			query.setParameter("edad", edad);
			query.setParameter("idModalidad", ModalidadEnum.TREINTAYTRES.getId());
			if (parentesco != ParentescoEnum.TITULAR.getId()) {
				query.setParameter("idRamaFactorRiesgo", ClavesRama.RIESGOS_TRABAJO);
			}

			ramaCalculoList = query.getResultList();
			LOGGER.debug("Ramas encontradas: {}", ramaCalculoList.size());
		}catch(Exception e){e.printStackTrace();}
		

		return ramaCalculoList;
	}

}
