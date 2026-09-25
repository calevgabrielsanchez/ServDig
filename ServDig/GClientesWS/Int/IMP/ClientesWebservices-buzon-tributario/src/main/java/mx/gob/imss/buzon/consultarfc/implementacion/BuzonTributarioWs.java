package mx.gob.imss.buzon.consultarfc.implementacion;


import mx.gob.imss.buzon.consultarfc.RespuestaBuzonTriburario;
import mx.gob.imss.buzon.consultarfc.WSBuzonTributario_Service;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.UsuarioBuzonRespuesta;

public class BuzonTributarioWs {

    public static UsuarioBuzonRespuesta consultaRfc(String rfc, String rp){
        WSBuzonTributario_Service wsBuzonTributario_service = new WSBuzonTributario_Service();
        RespuestaBuzonTriburario respuestaBuzonTriburario = wsBuzonTributario_service.getWSBuzonTributarioPort().getConsultaRFC(rfc, rp);
        return convertirUsuarioBuzon(respuestaBuzonTriburario);
    }

    private static UsuarioBuzonRespuesta convertirUsuarioBuzon(RespuestaBuzonTriburario respuestaBuzonTriburario){
        UsuarioBuzonRespuesta usuarioBuzonRespuesta = new UsuarioBuzonRespuesta();

        usuarioBuzonRespuesta.setClaveError(respuestaBuzonTriburario.getClaveError());
        usuarioBuzonRespuesta.setMensajeError(respuestaBuzonTriburario.getMensajeError().getValue());

        usuarioBuzonRespuesta.setRfc(respuestaBuzonTriburario.getUsuario().getRfc());
        usuarioBuzonRespuesta.setEstatus(respuestaBuzonTriburario.getUsuario().getEstatus());
        usuarioBuzonRespuesta.setMensaje(respuestaBuzonTriburario.getUsuario().getMensaje());
        usuarioBuzonRespuesta.setRazonSocial(respuestaBuzonTriburario.getUsuario().getRazonSocial());

        return usuarioBuzonRespuesta;

    }

}
