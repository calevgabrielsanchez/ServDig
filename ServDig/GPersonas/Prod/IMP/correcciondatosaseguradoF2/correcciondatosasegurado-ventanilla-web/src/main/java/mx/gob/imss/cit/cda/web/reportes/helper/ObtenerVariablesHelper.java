package mx.gob.imss.cit.cda.web.reportes.helper;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.web.app.common.model.Combo;
import mx.gob.imss.cit.cda.web.app.common.model.FiltroCombo;
import mx.gob.imss.cit.cda.web.app.common.model.UserProfile;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.constants.RolUsuarioEnum;
import mx.gob.imss.cit.cda.web.constants.SessionConstants;
import mx.gob.imss.cit.cda.web.constants.VariableReporteEnum;

@Component(BeansConstants.VARIABLE_COMBO_HELPER)
public class ObtenerVariablesHelper
        implements ReadHelper<FiltroCombo, List<Combo>> {

    @Autowired
    protected HttpSession httpSession;
    
    private final Logger log = LoggerFactory.getLogger(OrigenComboHelper.class);
    
    private static final VariableReporteEnum[] VARIABLES_INVALIDAS ={VariableReporteEnum.DELEGACION, VariableReporteEnum.SUBDELEGACION};
    

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
        
        try {
            UserProfile usuarioActivo = (UserProfile) httpSession.getAttribute(SessionConstants.USER_PROFILE);
            log.info("Perfil entrante ->"+usuarioActivo.getPerfilDescripcion());
            
          if(usuarioActivo.getPerfilDescripcion().equals(RolUsuarioEnum.AUTORIZADOR_DAV.getDescripcion())
                  || usuarioActivo.getPerfilDescripcion().equals(RolUsuarioEnum.AUTORIZADOR_DAV2.getDescripcion())){
              
              for (VariableReporteEnum variable : VariableReporteEnum.values()) {
                  
                  if (!Arrays.asList(VARIABLES_INVALIDAS).contains(variable)){
                  Combo combo = new Combo(variable.getVariable(),
                          variable.getDescripcion());
                  combos.add(combo);
                  }

              }
          }else{
            for (VariableReporteEnum variable : VariableReporteEnum.values()) {

                Combo combo = new Combo(variable.getVariable(),
                        variable.getDescripcion());
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
