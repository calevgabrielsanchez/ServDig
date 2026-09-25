package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad;

public class TipoSociedadServiceUtility extends AbstractServiceUtility {

    public static TipoSociedad convertirEntityToModel(
            DicTipoSociedad dicTipoSociedad) {
// TODO Faltan mas campos de mapear...
        TipoSociedad tipoSociedad = null;
        if (dicTipoSociedad != null) {
            tipoSociedad = new TipoSociedad();
            tipoSociedad.setDesTipoSociedadAbrev(dicTipoSociedad.getDesTipoSociedadAbrev());
            tipoSociedad.setIdTipoSociedad(new Long(dicTipoSociedad.getCveIdTipoSociedad()).intValue());
        }
        return tipoSociedad;
    }

}
