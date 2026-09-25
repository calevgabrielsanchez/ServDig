package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.gob.imss.digital.modelo.cuestionario.Opcion;
import mx.gob.imss.digital.modelo.cuestionario.PersonaCuestionario;
import mx.gob.imss.digital.modelo.cuestionario.Respuesta;
import mx.gob.imss.digital.modelo.cuestionario.RespuestasCuestionario;
import mx.gob.imss.digital.modelo.sindo.ModalidadTrabajador;
import mx.gob.imss.digital.modelo.sindo.ResultadoVigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.ResultadoVigenciaTrabajdor;
import mx.gob.imss.distss.gestion.cuestionario.modelo.TramiteCuestionarioDummy;
import mx.gob.imss.ws.vo.Modalidad;
import mx.gob.imss.ws.vo.Resultado;
import mx.gob.imss.ws.vo.Return;

import mx.gob.imss.ws.estatusvigencia.individual.cliente.ModalidadFecha;
import mx.gob.imss.ws.estatusvigencia.individual.cliente.RespuestaSituacionAseguramiento;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;
import mx.gob.imss.ws.estatusvigencia.individual.cliente.SituacionAseguramientoVO;

/** @author Dj Leo - 06/11/14
 * The Class UtilConvert.
 */
public class UtilConvert {
	    
    public static PersonaCuestionario parseToPersonaCuestionario(TramiteCuestionarioDummy tramiteCuestionario) {
		PersonaCuestionario personaCuestionario = new PersonaCuestionario();
		RespuestasCuestionario cuestionario = new RespuestasCuestionario();
		cuestionario.setSumatoriaRespuestas(tramiteCuestionario.getRespuestas().getSumatoriaRespuestas());
		cuestionario.setTipoCuestionario(tramiteCuestionario.getRespuestas().getTipoCuestionario());
		cuestionario.setErrorFormGeneral(tramiteCuestionario.getRespuestas().getErrorFormGeneral());
		List<Respuesta> respuestas = new ArrayList<Respuesta>();
		for(mx.gob.imss.distss.gestion.cuestionario.modelo.Respuesta r : tramiteCuestionario.getRespuestas().getRespuestas()) {
			Respuesta respuesta = new Respuesta();
			respuesta.setCvePregunta(r.getCvePregunta());
			respuesta.setNumPregunta(r.getNumPregunta());
			respuesta.setNumSeccion(r.getNumSeccion());
			respuesta.setErrorFormGeneral(r.getErrorFormGeneral());
			List<Opcion> opciones = new ArrayList<Opcion>();
			for(mx.gob.imss.distss.gestion.cuestionario.modelo.Opcion o : r.getValores()) {
				Opcion opcion = new Opcion();
				opcion.setClave(o.getClave());
				opcion.setDescripcion(o.getDescripcion());
				opcion.setValor(o.getValor());
				opcion.setErrorFormGeneral(o.getErrorFormGeneral());
				opciones.add(opcion);
			}
			respuesta.setValores(opciones.toArray(new Opcion[0]));
			respuestas.add(respuesta);
		}
		cuestionario.setRespuestas(respuestas.toArray(new Respuesta[0]));
		personaCuestionario.setRespuestasCuestionario(cuestionario);
		return personaCuestionario;
    }
    
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
    
    public static void convertirVigenciaDomestico(RespuestaSituacionAseguramiento ret ,VigenciaTrabajdor vigencia){
    	
         vigencia.setClaveError(ret.getClaveError());
         vigencia.setMensajeError(ret.getMensajeError());
         vigencia.setResultado(new ResultadoVigenciaTrabajdor());
         SituacionAseguramientoVO resul = ret.getResultado();
         vigencia.getResultado().setIndicadorVigente(resul.isIndicadorVigente());
         vigencia.getResultado().setModalidadesFechaVigente (modalidadFechaAModalidadTrabajador(resul.getModalidadesFechaVigente()));
         vigencia.getResultado().setModalidadesFechaBaja (modalidadFechaAModalidadTrabajador(resul.getModalidadesFechaBaja()));
//         vigencia.getResultado().setTipoAseguradoBaja  (resul.getTipoAseguradoBaja());
//         vigencia.getResultado().setNrpBaja  (resul.getNrpBaja());
//         vigencia.getResultado().setNumeroSemanaAseguramientoBaja  (resul.getNumeroSemanaAseguramientoBaja());
//         List<Modalidad> listModVigentes1 = resul.getListModVigentes();
//         ModalidadTrabajador[] listModVigentes = new ModalidadTrabajador[listModVigentes1.size()];
//         int i = 0;
//         for (Iterator<Modalidad> iterator = listModVigentes1.iterator(); iterator.hasNext();) {
//             ModalidadTrabajador modalidadTrabajador = new ModalidadTrabajador();
//             Modalidad mod = iterator.next();
//             modalidadTrabajador.setModalidad(mod.getModalidad().getValue());
//             listModVigentes[i]=modalidadTrabajador;
//         }
//         vigencia.getResultado().setListModVigentes(listModVigentes);
//         vigencia.getResultado().setSemanasCotizadas(resul.getSemanasCotizadas().toString());
     }
    
    private static ModalidadTrabajador[] modalidadFechaAModalidadTrabajador(List<ModalidadFecha>  modalidadFecha){
    	if(modalidadFecha == null || modalidadFecha.isEmpty()){
    		return new ModalidadTrabajador[0];
    	}
    	ModalidadTrabajador[] arregloTrabajador = new ModalidadTrabajador[modalidadFecha.size()];
    	for(int i=0; i < modalidadFecha.size(); i++){
    		ModalidadTrabajador modTrabajador = new ModalidadTrabajador();
    		modTrabajador.setFecha(modalidadFecha.get(i).getFecha());
    		modTrabajador.setModalidad(modalidadFecha.get(i).getModalidad());
    		arregloTrabajador[i] = modTrabajador;
    	}
    	return arregloTrabajador;
    }
}
	
