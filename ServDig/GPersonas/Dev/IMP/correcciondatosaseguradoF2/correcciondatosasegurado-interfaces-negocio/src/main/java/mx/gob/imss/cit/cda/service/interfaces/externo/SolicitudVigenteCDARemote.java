package mx.gob.imss.cit.cda.service.interfaces.externo;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.externo.cda.ValidaSolicitudVigenteCDAResponse;

@Remote
public interface SolicitudVigenteCDARemote {

    ValidaSolicitudVigenteCDAResponse validaSolicitudVigenteCDA(String curp,
            String nss, String correo);

}
