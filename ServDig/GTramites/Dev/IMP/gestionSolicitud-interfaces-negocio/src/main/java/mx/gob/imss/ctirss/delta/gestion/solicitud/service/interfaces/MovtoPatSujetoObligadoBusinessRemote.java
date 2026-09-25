package mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces;

import javax.ejb.Remote;
import javax.ejb.Timer;
import java.util.List;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.MovtoPatSujetoObligado;

@Remote
public interface MovtoPatSujetoObligadoBusinessRemote {

    List<MovtoPatSujetoObligado> listMovtosCurrentDate();

    MovtoPatSujetoObligado getMovtoPatSujetoObligadoFromRegPatornal(String regPatronal);

    void timeout(Timer timer);

    void schedule(Long timeout);

    void stopTimer();

    void callQueue();
}
