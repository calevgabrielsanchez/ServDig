package mx.gob.imss.cit.cda.web.reportes.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.common.model.Combo;
import mx.gob.imss.cit.cda.web.app.common.model.FiltroCombo;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Component(BeansConstants.RESPONSABLE_COMBO_HELPER)
public class ResponsableComboHelper
        implements ReadHelper<FiltroCombo, List<Combo>> {

    @Autowired
    @Qualifier("responsablesDelegacionBusiness")
    private ResponsablesDelegacionRemote responsablesDelegacionRemote;
    private final Logger log = LoggerFactory.getLogger(OrigenComboHelper.class);

    @SuppressWarnings("unchecked")
    @Override
    public ReadEvent<List<Combo>> requestEvent(
            RequestReadEvent<FiltroCombo> requestReadEvent) {
        log.debug("init responsables [{}]",
                requestReadEvent.getData().getEnumeracion());

        Set<Combo> combos = new TreeSet<Combo>(new Comparator<Combo>() {
            @Override
            public int compare(Combo o1, Combo o2) {
                return o1.getValue().compareToIgnoreCase(o2.getValue());
            }
        });
        Combo comboDefault = new Combo("--Por favor Seleccione--", "-1");
        combos.add(comboDefault);
        List<Fisica> responsables = new ArrayList<Fisica>();
               
        try {
            // responsables =
            // responsablesDelegacionRemote.consultarResponsablesDelegacion(Integer.parseInt(requestReadEvent.getData().getDelegacion()),Integer.parseInt(requestReadEvent.getData().getSubdelegacion()));
            responsables = responsablesDelegacionRemote
                    .consultarResponsablesDelegacion(Integer.valueOf(requestReadEvent.getData().getDelegacion()), 
                    		Integer.valueOf(requestReadEvent.getData().getSubdelegacion()));

            for (Fisica responsable : responsables) {
                // if(!Arrays.asList(ORIGEN_INVALIDOS).contains(origen)){
                // Combo combo = new Combo(responsable.getNombre()+"
                // "+responsable.getPrimerApellido()
                // +"
                // "+responsable.getSegundoApellido(),Long.toString(responsable.getCveFisica()));
                Combo combo = new Combo(
                        responsable.getNombre() + " "
                                + responsable.getPrimerApellido() + " "
                                + responsable.getSegundoApellido(),
                        responsable.getCurp());
                combos.add(combo);
                // }
            }

            return new ReadEvent<List<Combo>>(requestReadEvent.getKey(),
                    new ArrayList<Combo>(combos));
        } catch (Exception e) {
            return ReadEvent.notFound(requestReadEvent.getKey());
        }
    }

}