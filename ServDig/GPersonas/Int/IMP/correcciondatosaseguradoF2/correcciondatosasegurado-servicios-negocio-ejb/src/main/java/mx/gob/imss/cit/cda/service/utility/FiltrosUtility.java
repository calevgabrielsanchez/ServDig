package mx.gob.imss.cit.cda.service.utility;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class FiltrosUtility implements FiltrosUtilityLocal<PeriodoMovimientoAfiliatorio> {

    private final Logger log = LoggerFactory.getLogger(FiltrosUtility.class);

    public static final String FECHA_SISTEMA_AHORRO_RETIRO = "01/05/1992";
    private static final Long MOVIMIENTO_PERMITIDOS_ALTA[] = { 1l, 8l };
    private static final Long MOVIMIENTO_PERMITIDOS_BAJA[] = { 2l, 0l };
    private static final String MODALIDADES_NO_PERMITIDAS[] = { "32", "33","00", "99" };

    @EJB
    private CorreccionDatosAseguradoUtilityLocal correccionAseguradoUtilityLocal;

    @Override
    public boolean shouldRemove(PeriodoMovimientoAfiliatorio t) {
        boolean remover = false;
        Date fechaBaja = null;
        try {
            fechaBaja = correccionAseguradoUtilityLocal
                    .convertStringToDate(FECHA_SISTEMA_AHORRO_RETIRO);
        } catch (ParseException e) {
            log.error("Ocurrio un error al parseo");
            fechaBaja = new Date();
        }
        if (validarFechaMovimiento(t.getFechaFinalMovimiento(), fechaBaja)
                || validarMovimientos(t.getTipoMovimientoInicial()
                        .getIdTipoMvtoAsegurado(), t.getTipoMovimientoFinal()
                        .getIdTipoMvtoAsegurado())
                || t.getCveModalidad().getDesCorta() == null
                || Arrays.asList(MODALIDADES_NO_PERMITIDAS).contains(
                        t.getCveModalidad().getDesCorta())) {
            remover = true;
        }
        return remover;
    }

    private boolean validarMovimientos(long tipoInicialMovimiento,long tipoFinalMovimiento) {
        return !(Arrays.asList(MOVIMIENTO_PERMITIDOS_ALTA).contains(tipoInicialMovimiento) || Arrays.asList(MOVIMIENTO_PERMITIDOS_BAJA).contains(tipoFinalMovimiento));
    }

    private boolean validarFechaMovimiento(Date fechaBaja,Date fechaAhorroRetiro) {
        return fechaBaja != null && fechaBaja.compareTo(fechaAhorroRetiro) < 0;

    }

    @Override
    public boolean shouldRemoveLast(PeriodoMovimientoAfiliatorio t) {
        boolean remover = false;

        Date fechaBaja = null;
        try {
            fechaBaja = correccionAseguradoUtilityLocal.convertStringToDate(FECHA_SISTEMA_AHORRO_RETIRO);
        } catch (ParseException e) {
            log.error("Ocurrio un error al parseo");
            fechaBaja = new Date();
        }

        if (validarFechaMovimiento(t.getFechaFinalMovimiento(), fechaBaja)
                || t.getCveModalidad().getDesCorta() == null
                || Arrays.asList(MODALIDADES_NO_PERMITIDAS).contains(
                        t.getCveModalidad().getDesCorta())) {
            remover = true;
        }
        return remover;
    }

}
