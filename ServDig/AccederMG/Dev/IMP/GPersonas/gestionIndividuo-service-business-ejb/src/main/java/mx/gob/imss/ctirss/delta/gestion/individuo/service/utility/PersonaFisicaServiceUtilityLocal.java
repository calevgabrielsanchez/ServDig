package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNssCL3;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaView;

/**
 * 161012
 * @author ICCSRG
 *
 */
@Local
public interface PersonaFisicaServiceUtilityLocal {

	AsignacionNSS transformarNssToModel(DitAsignacionNss ditAsignacion);
	/**
	 * Metodo encargado de realizar la transformacion de un objeto de entidad a uno de modelo
	 * @param fisica
	 * @return
	 */
	DitPersona transformarAEntidad(Fisica fisica);
	
	DitPersonaView convertirPersonaFisicaToEntityView(final Fisica personaFisica);
	
	/**
	 * Metodo encargado de realizar la transformacion de un objeto de modelo a uno de entidad
	 * @param ditPersona
	 * @return
	 */
	Fisica transformarAModelo(DitPersona ditPersona);
	
	/**
	 * Metodo encargado de realizar la trasformacion de un entitity a modelo de negocio
	 * copiando unicamente datos personales, para no hacer consultas de mas
	 * @param ditPersona
	 * @return
	 */
	Fisica transformarAModelSoloDatosPersonales(DitPersona ditPersona);
	
	Fisica convertirEntityViewToPersonaFisica(DitPersonaView ditPersonaView);
	
	List<Fisica> covertirListaEntityViewToPersonaFisica(List<DitPersonaView> listaDitPersonaView);
	
    /**
     * 161012
     * Este metodo convierte a mayusculas el contenido String del objeto ditPersona
     * @param ditPersona
     */
    void convertirMayusculas(final DitPersona ditPersona);

    /**
	 * Método que asigna al atributo correspondiente el documento probatorio
	 * dependiendo de su tipo
	 * 
	 * @param fisica: objeto al que se le settean los documentos probatorios
	 * @param documentosProbatorios: lista de los documentos probatorios
	 * @return
	 */
	void asignarDocumentosProbatorios(Fisica fisica,
			List<DocumentoProbatorio> documentosProbatorios);
	
	
	
	/**
	 * 
	 * @param listaAsignaciones
	 * @return
	 */
	List<Fisica> covertirListaAsignacionEntityToPersonaFisica(List<DitAsignacionNss> listaAsignaciones);
	
	/**
	 * Parser para transformar de un asignacion CL3 a pAsingacion
	 * @param ditAsignacion
	 * @return
	 */
	AsignacionNSS transformarNssCL3ToModel(DitAsignacionNssCL3 ditAsignacion);
    
}
