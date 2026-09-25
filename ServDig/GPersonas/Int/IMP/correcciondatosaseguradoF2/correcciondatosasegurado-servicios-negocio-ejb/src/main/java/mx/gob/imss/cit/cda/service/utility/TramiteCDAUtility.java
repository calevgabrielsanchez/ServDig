package mx.gob.imss.cit.cda.service.utility;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionDatosAsegurado;

@Stateless
public class TramiteCDAUtility implements TramiteCDAUtilityLocal {

    @Override
    public TramiteCorreccionCurp convertirEntityToModel(
            DitCorreccionDatosAsegurado entity) {
        TramiteCorreccionCurp tramiteCDA = new TramiteCorreccionCurp();
        tramiteCDA.setTramiteId(entity.getTramite().getCveIdTramite());
        Fisica personaRenapo = new Fisica();
        personaRenapo.setCurp(entity.getRefCurp());
        tramiteCDA.setPersonaRENAPO(personaRenapo);
        tramiteCDA.setIdTramiteCorreccionDatosAseg(entity
                .getCveIdCorreccionDatosAsegurado());
        return tramiteCDA;
    }

}
