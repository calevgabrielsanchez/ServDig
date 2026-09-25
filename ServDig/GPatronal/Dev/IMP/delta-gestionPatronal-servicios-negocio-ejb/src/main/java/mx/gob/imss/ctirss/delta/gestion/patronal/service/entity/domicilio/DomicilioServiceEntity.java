/**
 *  
 *  @Cliente: Institutos Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:DomicilioServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.domicilio
 *  @Fecha:02/03/2012
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.domicilio;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;

/**
 * @author Lucio Duran Silva
 *
 */
@Stateless
public class DomicilioServiceEntity extends AbstractServiceEntity implements
		DomicilioServiceEntityLocal {

	@Override
	public void hello() {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.domicilio.DomicilioServiceEntityLocal#consultaLocalidadAproximada(mx.gob.imss.ctirss.delta.gestion.patronal.model.domicilios.GeocoderDomicilio)
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<GeocoderDomicilio> consultaLocalidadAproximada(
//			GeocoderDomicilio geocoder) throws DomicilioNoLocalizadoException {
//		this.log.debug("consultaLocalidadAproximada [ "+ geocoder +"]");
//		StringBuffer sql = new StringBuffer();
//		sql.append(" select new mx.gob.imss.ctirss.delta.gestion.patronal.model.domicilios.GeocoderDomicilio( ");
//		sql.append("   cp.id.codigo , ast.id.cveAsen, ast.nomAsen , loc.id.cveLoc, loc.nomLoc, mun.id.cveMun, mun.nomMun, estado.cveEnt, estado.nomEnt ");
//		sql.append(") ");
//		sql.append(" from DgCodigosPostale as cp ");
//		sql.append(" join cp.dgAsentamiento as ast ");
//		sql.append(" join ast.dgCatLocalidad as loc ");
//		sql.append(" join loc.dgCatMunicipio as mun ");
//		sql.append(" join mun.dgCatEstado as estado ");
//		sql.append(" where cp.id.codigo = :codigo ");
//		sql.append(" group by cp.id.codigo , ast.id.cveAsen, ast.nomAsen , loc.id.cveLoc, loc.nomLoc, mun.id.cveMun, mun.nomMun, estado.cveEnt, estado.nomEnt");
//		Query query = this.getSession().createQuery(sql.toString());
//		
//		query.setParameter("codigo", geocoder.getCodigoPostal());
//		
//		List<GeocoderDomicilio> list = query.list();
//		
//		
//		
//		if(list == null || list.isEmpty()){
//			throw new DomicilioNoLocalizadoException();
//		}
//		
//		return list;
//	}

}


