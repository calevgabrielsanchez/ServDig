package mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.MovtoPatSujetoObligado;

@Local
public interface MovtoPatSujetoObligadoEntityLocal {
    List<MovtoPatSujetoObligado> listMovtoPatSujetoObligadoCurrentDate();

    MovtoPatSujetoObligado getMovtoPatSujetoObligadoFromRegPatornal(String regPatronal);
}
