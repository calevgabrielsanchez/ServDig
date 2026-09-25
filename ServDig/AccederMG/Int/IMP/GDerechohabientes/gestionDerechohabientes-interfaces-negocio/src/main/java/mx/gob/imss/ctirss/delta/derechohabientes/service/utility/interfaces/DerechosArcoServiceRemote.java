package mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces;

import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.BloqueoDerechosArco;

import javax.ejb.Remote;

@Remote
public interface DerechosArcoServiceRemote {

    byte [] bloquearDerechosArco(String nss, Usuario usuario, String motivos) throws Exception;

    byte [] desbloquearDerechosArco(String nss, Usuario usuario, String motivos) throws Exception;

    BloqueoDerechosArco consultaBloqueo(String nss, Long idTipoTramite);
}
