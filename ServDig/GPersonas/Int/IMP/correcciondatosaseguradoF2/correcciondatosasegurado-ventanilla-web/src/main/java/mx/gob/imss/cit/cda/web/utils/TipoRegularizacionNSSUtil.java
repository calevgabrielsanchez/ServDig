package mx.gob.imss.cit.cda.web.utils;

import java.util.Collection;

import mx.gob.imss.cit.cda.web.app.responsable.model.TipoRegularizacionNSS;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionNSSEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TipoRegularizacionNSSUtil {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @SuppressWarnings("unchecked")
    public TipoRegularizacionNSS getTipoRegularizacionNSS(
            TramiteCorreccionCurp tramite) {

        TipoRegularizacionNSS tipoRegNSS = new TipoRegularizacionNSS();
        log.debug("---CDA Ventanilla--- Tipo de Regularizacion NSS null?: {}",
                tramite.getCertificacionNSS() == null);
        if (tramite.getCertificacionNSS() != null
                && tramite.getCertificacionNSS().getTipoRegularizacionNSS() != null
                && !tramite.getCertificacionNSS().getTipoRegularizacionNSS()
                        .isEmpty()) {
            Collection<Long> tiposRegularizacion = CollectionUtils.collect(
                    tramite.getCertificacionNSS().getTipoRegularizacionNSS(),
                    new BeanToPropertyValueTransformer(
                            "idTipoRegularizacionNSS"));

            log.debug(
                    "---CDA Ventanilla--- ids Tipo de Regularizacion NSS: {}",
                    tiposRegularizacion);

            tipoRegNSS.setNombre(tiposRegularizacion
                    .contains(TipoRegularizacionNSSEnum.CORRECCION_NOMBRE
                            .getId()));
            tipoRegNSS
                    .setDuplicidad(tiposRegularizacion
                            .contains(TipoRegularizacionNSSEnum.CANCELADO_POR_DUPLICIDAD
                                    .getId()));
            tipoRegNSS
                    .setDatosEstadisticos(tiposRegularizacion
                            .contains(TipoRegularizacionNSSEnum.CORRECCION_DATOS_ESTADISTICOS
                                    .getId()));
            tipoRegNSS
                    .setOtroAsegurado(tiposRegularizacion
                            .contains(TipoRegularizacionNSSEnum.CORRESPONA_OTRO_ASEGURADO
                                    .getId()));
            tipoRegNSS.setHomonimio(tiposRegularizacion
                    .contains(TipoRegularizacionNSSEnum.CORRESPONA_UN_HOMONIMO
                            .getId()));
            tipoRegNSS.setNoExisteCanase(tiposRegularizacion
                    .contains(TipoRegularizacionNSSEnum.NO_EXISTE_EN_CANASE
                            .getId()));
            tipoRegNSS
                    .setRegularizarCuentaIndividual(tiposRegularizacion
                            .contains(TipoRegularizacionNSSEnum.REGULARIZAR_CUENTA_INDIVIDUAL
                                    .getId()));

            log.debug("---CDA Ventanilla--- Tipo de Regularizacion NSS: {}",
                    tipoRegNSS);
        }
        return tipoRegNSS;
    }

}
