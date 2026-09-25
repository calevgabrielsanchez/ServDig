package mx.gob.imss.ctirss.delta.gestion.individuo.web.utils;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class WrapperSessionDatosPersonaMoralSalidaPaginador<T extends Moral> extends AbstractModel {
    private DatosSalidaPaginador<T> dataPager = new DatosSalidaPaginador<T>();

    public DatosSalidaPaginador<T> getDataPager() {
        return dataPager;
    }

    public void setDataPager(final DatosSalidaPaginador<T> dataPager) {
        this.dataPager = dataPager;
    }

}
