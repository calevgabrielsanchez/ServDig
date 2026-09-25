package mx.gob.imss.cit.cda.web.reportes.helper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.model.Combo;
import mx.gob.imss.cit.cda.web.app.common.model.FiltroCombo;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.ctirss.delta.framework.select.bean.SelectBean;
import mx.gob.imss.ctirss.delta.service.interfaces.ISelectService;

@Component(BeansConstants.SUBDELEGACION_COMBO_HELPER)
public class SubdelegacionComboHelper
        implements ReadHelper<FiltroCombo, List<Combo>> {

    @Autowired
    private ISelectService componentComboService;
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
        List<SelectBean> subdelegaciones = null;
        try {
            if (requestReadEvent.getData().getEnumeracion() == null
                    || requestReadEvent.getData().getEnumeracion() == "-1") {
                subdelegaciones = this.componentComboService.getActiveOptions(
                        "mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion");
            } else {
                subdelegaciones = this.componentComboService.getOptions(
                        "mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion",
                        "dicDelegacion.cveIdDelegacion",
                        requestReadEvent.getData().getEnumeracion().toString());

            }

            for (SelectBean subdelegacion : subdelegaciones) {
                Combo combo = new Combo(subdelegacion.getDescripcion(),
                        subdelegacion.getId());
                combos.add(combo);
            }
            return new ReadEvent<List<Combo>>(requestReadEvent.getKey(),
                    new ArrayList<Combo>(combos));
        } catch (Exception e) {
            return ReadEvent.notFound(requestReadEvent.getKey());
        }
    }
}