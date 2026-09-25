package mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaVO;

@Remote
public interface HldaClientServiceRemote {
    HldaVO getHldaVO(String nss);
}
