package mx.gob.imss.cit.cda.service.certificacion.entity;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DetalleNssCda;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;

import java.util.List;

@Stateless
public class CertificacionEntity extends AbstractServiceEntity
        implements CertificacionLocal {

	@Override
	@SuppressWarnings("unchecked")
	public List<DetalleNssCda> obtenerListaNSS(Long idTramite) {

		log.debug("--CDA-- Obteniendo NSS Certificador y Asociado del tramite: " + idTramite);

		String sql = "SELECT CVE_ID_TIPO_NSS as \"idTipoNss\", NUM_NSS as \"nss\" "
				+ "FROM DIT_DETALLE_NSS_CDA DDNC "
				+ "INNER JOIN DIT_CORRECCION_DATOS_ASEG DCDA ON DCDA.CVE_ID_CORRECCION_DATOS_ASEG = DDNC.CVE_ID_CORRECCION_DATOS_ASEG "
				+ "WHERE DCDA.CVE_ID_TRAMITE = :idTramite AND DDNC.FEC_REGISTRO_BAJA IS NULL ";

		SQLQuery sqlQuery = getSession().createSQLQuery(sql)
				.addScalar("idTipoNss", StandardBasicTypes.LONG)
				.addScalar("nss", StandardBasicTypes.STRING);

		sqlQuery.setParameter("idTramite", idTramite);

		return sqlQuery.setResultTransformer(Transformers.aliasToBean(DetalleNssCda.class)).list();
	}

}
