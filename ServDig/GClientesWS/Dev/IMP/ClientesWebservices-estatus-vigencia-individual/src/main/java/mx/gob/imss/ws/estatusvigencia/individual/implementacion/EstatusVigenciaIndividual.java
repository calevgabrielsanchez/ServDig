package mx.gob.imss.ws.estatusvigencia.individual.implementacion;

import mx.gob.imss.digital.modelo.sindo.ModalidadTrabajador;
import mx.gob.imss.digital.modelo.sindo.ResultadoVigenciaTrabajdor;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;
import mx.gob.imss.ws.estatusvigencia.individual.cliente.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

public class EstatusVigenciaIndividual {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static VigenciaTrabajdor consultaVigenciaSeguroIndividual(String idAsignacionNSS) {
        WSConsultaSituacionAseguramiento_Service wsConsultaSituacionAseguramiento_service = new WSConsultaSituacionAseguramiento_Service();
        RespuestaSituacionAseguramiento situacionAseguramientoXAsginacionNSS = wsConsultaSituacionAseguramiento_service.getWSConsultaSituacionAseguramientoPort().getSituacionAseguramientoXAsginacionNSS(idAsignacionNSS);

        return convertirSituacionAseguramiento(situacionAseguramientoXAsginacionNSS);
    }

    private static VigenciaTrabajdor convertirSituacionAseguramiento(RespuestaSituacionAseguramiento respuestaSituacionAseguramiento){
        VigenciaTrabajdor vigenciaTrabajdor = new VigenciaTrabajdor();

        vigenciaTrabajdor.setClaveError(respuestaSituacionAseguramiento.getClaveError());
        vigenciaTrabajdor.setMensajeError(respuestaSituacionAseguramiento.getMensajeError());

        SituacionAseguramientoVO resultado = respuestaSituacionAseguramiento.getResultado();

        ResultadoVigenciaTrabajdor resultadoVigenciaTrabajdor = new ResultadoVigenciaTrabajdor();

        resultadoVigenciaTrabajdor.setIndicadorVigente(resultado.isIndicadorVigente());

        List<ModalidadFecha> listModVigentes1 = resultado.getModalidadesFechaVigente();
        ModalidadTrabajador[] listModVigentes = new ModalidadTrabajador[listModVigentes1.size()];
        int i = 0;
        for (Iterator<ModalidadFecha> iterator = listModVigentes1.iterator(); iterator.hasNext();) {
            ModalidadTrabajador modalidadTrabajador = new ModalidadTrabajador();
            ModalidadFecha mod = iterator.next();
            modalidadTrabajador.setModalidad(mod.getModalidad());
            modalidadTrabajador.setFecha(mod.getFecha());
            listModVigentes[i]=modalidadTrabajador;
            i++;
        }

        resultadoVigenciaTrabajdor.setModalidadesFechaVigente(listModVigentes);

        List<ModalidadFecha> listModFechaBaja1 = resultado.getModalidadesFechaBaja();
        ModalidadTrabajador[] listModFechaBaja = new ModalidadTrabajador[listModFechaBaja1.size()];
        i = 0;
        for (Iterator<ModalidadFecha> iterator = listModFechaBaja1.iterator(); iterator.hasNext();) {
            ModalidadTrabajador modalidadTrabajador = new ModalidadTrabajador();
            ModalidadFecha mod = iterator.next();
            modalidadTrabajador.setModalidad(mod.getModalidad());
            modalidadTrabajador.setFecha(mod.getFecha());
            listModFechaBaja[i]=modalidadTrabajador;
            i++;
        }

        resultadoVigenciaTrabajdor.setModalidadesFechaBaja(listModFechaBaja);

        resultadoVigenciaTrabajdor.setNrpBaja(resultado.getNrpBaja().getValue());
        resultadoVigenciaTrabajdor.setNumeroSemanaAseguramientoBaja(resultado.getNumeroSemanaAseguramientoBaja().getValue());
        resultadoVigenciaTrabajdor.setTipoAseguradoBaja(resultado.getTipoAseguradoBaja().getValue());

        vigenciaTrabajdor.setResultado(resultadoVigenciaTrabajdor);

        return vigenciaTrabajdor;
    }
}
