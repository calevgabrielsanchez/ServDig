/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ws.vo.helper;

import java.util.Iterator;
import java.util.List;
import mx.gob.imss.digital.modelo.sindo.ModalidadTrabajador;
import mx.gob.imss.digital.modelo.sindo.ResultadoVigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
import mx.gob.imss.ws.vo.Modalidad;
import mx.gob.imss.ws.vo.Resultado;
import mx.gob.imss.ws.vo.Return;

/**
 *
 * @author softtekop
 */
public class EstatusVigenciaHelper {
    
    public static void convertirVigenciaSeguroFamiliar(Return ret ,VigenciaSeguroFamiliar vigencia){
        Resultado res = ret.getResultado().getValue();
        vigencia.setClaveError(ret.getClaveError().getValue());
        vigencia.setMensajeError(ret.getMensajeError().getValue());
        vigencia.setResultado(new ResultadoVigenciaSeguroFamiliar());
        Resultado resul = ret.getResultado().getValue();
        vigencia.getResultado().setEstadoVigencia(resul.getEstadoVigencia().toString());
        vigencia.getResultado().setFecUltimaBajaMod33(resul.getFecUltimaBajaMod33().getValue());
        vigencia.getResultado().setFecUltimaBajaObligatorio(resul.getFecUltimaBajaObligatorio().getValue());
        vigencia.getResultado().setIndPension(resul.getIndPension().toString());
        vigencia.getResultado().setIndTrabajadorIMSS(resul.getIndTrabajadorIMSS().toString());
        vigencia.getResultado().setSemanasCotizadas(resul.getSemanasCotizadas().toString());
        List<Modalidad> listModVigentes1 = resul.getListModVigentes();
        ModalidadTrabajador[] listModVigentes = new ModalidadTrabajador[listModVigentes1.size()];
        int i = 0;
        for (Iterator<Modalidad> iterator = listModVigentes1.iterator(); iterator.hasNext();) {
            ModalidadTrabajador modalidadTrabajador = new ModalidadTrabajador();
            Modalidad mod = iterator.next();
            modalidadTrabajador.setModalidad(mod.getModalidad().getValue());
            listModVigentes[i]=modalidadTrabajador;
        }
        vigencia.getResultado().setListModVigentes(listModVigentes);
        vigencia.getResultado().setSemanasCotizadas(resul.getSemanasCotizadas().toString());
    }

}
