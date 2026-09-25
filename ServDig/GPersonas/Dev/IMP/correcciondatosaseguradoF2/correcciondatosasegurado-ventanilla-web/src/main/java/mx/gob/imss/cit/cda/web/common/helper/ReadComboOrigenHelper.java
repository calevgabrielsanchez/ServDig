package mx.gob.imss.cit.cda.web.common.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.model.Combo;
import mx.gob.imss.cit.cda.web.app.common.model.FiltroCombo;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component(BeansConstants.READ_COMBO_ORIGEN_HELPER)
public class ReadComboOrigenHelper implements
        ReadHelper<FiltroCombo, List<Combo>> {

    private final Logger log = LoggerFactory
            .getLogger(ReadComboOrigenHelper.class);

    private static final OrigenSolicitudEnum[] ORIGENES_VALIDOS = {
            OrigenSolicitudEnum.INTERNET, OrigenSolicitudEnum.VENTANILLA };

    @SuppressWarnings("unchecked")
    @Override
    public ReadEvent<List<Combo>> requestEvent(
            RequestReadEvent<FiltroCombo> requestReadEvent) {
        log.debug("init ReadComboOrigenHelper [{}]", requestReadEvent.getData()
                .getEnumeracion());

        Set<Combo> combos = new TreeSet<Combo>(new Comparator<Combo>() {
            @Override
            public int compare(Combo o1, Combo o2) {
                return o1.getValue().compareToIgnoreCase(o2.getValue());
            }
        });
        Combo comboDefault = new Combo("--Por favor Seleccione--", "-1");
        combos.add(comboDefault);

        try {

            for (OrigenSolicitudEnum origen : OrigenSolicitudEnum.values()) {
                if (Arrays.asList(ORIGENES_VALIDOS).contains(origen)) {
                    Combo combo = new Combo(origen.getDesc(), origen.getDesc());
                    combos.add(combo);
                }
            }

            return new ReadEvent<List<Combo>>(requestReadEvent.getKey(),
                    new ArrayList<Combo>(combos));

        } catch (Exception e) {
            return ReadEvent.notFound(requestReadEvent.getKey());
        }
    }

}
