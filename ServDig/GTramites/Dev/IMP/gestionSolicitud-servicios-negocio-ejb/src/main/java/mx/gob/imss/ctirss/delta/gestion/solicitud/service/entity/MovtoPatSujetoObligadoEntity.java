package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.MovtoPatSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitMovtoPatSujOblig;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(mappedName = "movtoPatSujetoObligadoEntity")
public class MovtoPatSujetoObligadoEntity extends AbstractServiceEntity implements MovtoPatSujetoObligadoEntityLocal {

    private static final Logger log = LoggerFactory.getLogger(MovtoPatSujetoObligadoEntity.class);

    @Override
    public List<MovtoPatSujetoObligado> listMovtoPatSujetoObligadoCurrentDate() {

        Date yesterday = calcDateYesterday();
        Date today = new Date();

        log.debug("obteniendo registros entre fechas: {} y {}", today, yesterday);

        List<DitMovtoPatSujOblig> movtos = em.createQuery(new StringBuilder()
                .append("select entity from DitMovtoPatSujOblig entity\n")
                .append("where entity.fecRegistroAlta >= :finicio\n")
                .toString() , DitMovtoPatSujOblig.class)
            .setParameter("finicio", yesterday)
            .getResultList();

        List<MovtoPatSujetoObligado> returnList = new ArrayList<MovtoPatSujetoObligado>();
        for (DitMovtoPatSujOblig movimientoPSO : movtos) {
            returnList.add(createMovtoPatSujetoObligado(movimientoPSO));
        }

        return returnList;
    }


    @Override
    public MovtoPatSujetoObligado getMovtoPatSujetoObligadoFromRegPatornal(String regPatronal) {
        log.debug("Regpatronal: {}", regPatronal);

        Date yesterday = calcDateYesterday();

        List<DitMovtoPatSujOblig> movtos = em.createQuery(new StringBuilder()
                .append("select movimiento\n")
                .append("from DitMovtoPatSujOblig movimiento\n")
                .append("join movimiento.ditPatronSujetoObligado patronSujetoObligado\n")
                .append("join patronSujetoObligado.ditPatronGenerals patronGeneral\n")
                .append("where patronGeneral.regPatron = :regpatronal\n")
                .append("and movimiento.fecRegistroAlta >= :finicio\n")
                .append("order by movimiento.fecMovimiento asc")
                .toString())
            .setParameter("regpatronal", regPatronal)
            .setParameter("finicio", yesterday)
            .setMaxResults(1)
            .getResultList();

        MovtoPatSujetoObligado movimiento = null;

        if (!movtos.isEmpty()) {
            movimiento = createMovtoPatSujetoObligado(movtos.get(0));
        }

        return movimiento;
    }
    
    private Date calcDateYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        return calendar.getTime();
    }

    private MovtoPatSujetoObligado createMovtoPatSujetoObligado(DitMovtoPatSujOblig movimiento) {
        MovtoPatSujetoObligado movimientoPatSujetoObligado = new MovtoPatSujetoObligado();

        movimientoPatSujetoObligado.setCveIdMovtoPatSujOblig(movimiento.getCveIdMovtoPatSujOblig());
        movimientoPatSujetoObligado.setCveIdPatronDestino(movimiento.getCveIdPatronDestino());
        movimientoPatSujetoObligado.setFecAvisoSat(movimiento.getFecAvisoSat());
        movimientoPatSujetoObligado.setFecInforme(movimiento.getFecInforme());
        movimientoPatSujetoObligado.setFecMovimiento(movimiento.getFecMovimiento());
        movimientoPatSujetoObligado.setFecOficio(movimiento.getFecOficio());
        movimientoPatSujetoObligado.setFecRegistroActualizado(movimiento.getFecRegistroActualizado());
        movimientoPatSujetoObligado.setFecRegistroAlta(movimiento.getFecRegistroAlta());
        movimientoPatSujetoObligado.setFolioMovimiento(movimiento.getFolioMovimiento());
        movimientoPatSujetoObligado.setNumeroOficio(movimiento.getNumeroOficio());
        movimientoPatSujetoObligado.setObservaciones(movimiento.getObservaciones());

        if (movimiento.getDicCausa() != null) {
            movimientoPatSujetoObligado.setCausa(Long.valueOf(movimiento.getDicCausa().getCveIdCausa()).intValue());
        }

        int idTipoMovimiento = Long.valueOf(movimiento.getDicTipoMovtoPatSujoblig().getCveIdTipoMovtoPatSujoblig()).intValue();
        movimientoPatSujetoObligado.setIdTipoMovimiento(idTipoMovimiento);

        String registroPatronal = buildRegistroPatronal(movimiento);
        movimientoPatSujetoObligado.setRegistroPatronal(registroPatronal);

        return movimientoPatSujetoObligado;
    }

    private String buildRegistroPatronal(DitMovtoPatSujOblig movimiento) {

        DitPatronSujetoObligado patronSujetoObligado = movimiento.getDitPatronSujetoObligado();

        List<DitPatronGeneral> patrones = patronSujetoObligado.getDitPatronGenerals();

        DitPatronGeneral patron = patrones.get(0);
        log.info("Patrones encontrados: {}", patrones.size());

        if (patrones.size() > 1) {
            log.warn("Mas de un registro patronal encontrado");
        }

        String modalidad = patronSujetoObligado.getDicModalidad().getNumModalidad();
        if (modalidad == null) {
            modalidad = "";
        }
        return String.format("%s%s", patron.getRegPatron(), modalidad);
    }


}
