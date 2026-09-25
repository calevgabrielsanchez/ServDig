package mx.gob.imss.ctirss.correccion.promocion.service.interfaces;

import java.util.List;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.model.CgtPromocion;
import mx.gob.imss.ctirss.correccion.model.CrtSelector;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;

public interface PromocionExhortoService<T extends AbstractModel> {
	public CrtPromocion promocionarExhortoOrdinario(CrtPromocion promocion, String delegacion, String subDelegacion);
	public CrtPromocion promocionarExhortoOrdinarioIndividual(CrtPromocion promocion, String delegacion, String subDelegacion);
	public CgtPromocion obtenerReplicaPromocion(CrtPromocion promocion);
	public CgtPromocion obtenerReplicaPromocionIndividual(CrtPromocion promocion) ;
	public void replicaPromocionExhortoOrdinario(CgtPromocion obtenerReplicaPromocion);
}
