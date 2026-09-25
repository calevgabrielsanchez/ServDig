package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.rep.legal;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalInvalidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalLimiteMinRegExcedidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.patronal.RepresentanteLegalYaExisteException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.exception.ParametrosInvalidosException;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitRepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

@Local
public interface RepresentanteLegalServiceEntityLocal {
	
	DatosSalidaPaginador<RepresentanteLegal> paginar(DatosEntradaPaginador<RepresentanteLegal> params);
	
	DatosSalidaPaginador<RepresentanteLegal> paginarParaManejoDeTramite(DatosEntradaPaginador<RepresentanteLegal> params);
	
//	DatosSalidaPaginador<RepresentanteLegal> paginar(DatosEntradaPaginador<RepresentanteLegal> params, DitRepresentanteLegal entity) throws Exception;
	
	RepresentanteLegal get(RepresentanteLegal representanteLegal) throws Exception;
	
	RepresentanteLegal persistir(RepresentanteLegal model) throws Exception;
	
	void borrar (RepresentanteLegal instance) throws Exception;
	void borrar (SujetoObligado representado, Long idPersonaRepresentante, Boolean porIdPersona);

	//void validaLimMaxRegRepresentanteLegal(RepresentanteLegal representanteLegal) throws RepresentanteLegalInvalidoException, RepresentanteLegalLimiteMaxRegExcedidoException;
	
	void validaLimMinRegRepresentanteLegal(RepresentanteLegal representanteLegal) throws RepresentanteLegalLimiteMinRegExcedidoException, Exception;
	
	void validaExisteRepresentanteLegal(RepresentanteLegal representanteLegal) throws RepresentanteLegalInvalidoException, RepresentanteLegalYaExisteException;
	
	void validaBorrarRepresentanteLegal(RepresentanteLegal representanteLegal) throws RepresentanteLegalInvalidoException, RepresentanteLegalLimiteMinRegExcedidoException;
	
	RepresentanteLegal actualizar(RepresentanteLegal instance) throws Exception;
	
	int consultarNumRegistros(Long someId);

	List<DitRepresentanteLegal> consultaPorPatronSujetoObligado(RepresentanteLegal representanteLegal) throws Exception;
	
	/**
	 * 
	 * @author Hugo Armando Martínez Chamónica
	 * @param representanteLegal
	 * @return RepresentanteLegal
	 */
	List<RepresentanteLegal> consultarRepresentanteLegalPorSujetoObligado(RepresentanteLegal representanteLegal);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 26/06/2012
	 * @param representantes
	 */
	void actualizarRepresentantes(List<RepresentanteLegal> representantes);
	
	/**
	 * Recupera todos los representantes legales asociados a alguno de
	 * los RP  del patrón, esto debido a que actualmente al guardar el representante
	 * legal se asocia a todos sus RP.
	 * @author Hugo Martinez
	 * @Date 02/10/2012
	 * @param cveIdPersona
	 * @param tipoPersona
	 * @return
	 */
	List<RepresentanteLegal> obtenerRepresentantesPorPersona(Long cveIdPersona, TipoPersonaEnum tipoPersona);
	
	Fisica getPersonaByCurp(String curp);
	/**
	 * Recupera todos los representantes legales asociados al RFC
	 * @param rfc
	 * @return
	 */	
	List<RepresentanteLegal> obtenerRepresentantesPorRFCMoral(String rfc);
	
	/**
	 * Recupera todos los representantes legales asociados a alguno de
	 * los RP  del patrón, esto debido a que actualmente al guardar el representante
	 * legal se asocia a todos sus RP.
	 * @author Hugo Martinez
	 * @Date 02/10/2012
	 * @param cveIdPersona
	 * @param tipoPersona
	 * @return
	 */
	List<RepresentanteLegal> obtenerRepresentantesConActosAdmonPorPersona(Long cveIdPersona, TipoPersonaEnum tipoPersona);
	
	
	
	/**
	 * Elimina todos los medios de contacto asociados al representante legal
	 * @author Hugo Martinez
	 * @Date 07/11/2012
	 * @param cveIdRepresentanteLegal
	 */
	void eliminarMediosContactoDeRepresentante(Long cveIdRepresentanteLegal);
	
	/**
	 * Asocia los medios de contacto proporcionados al representante legal
	 * @author Hugo Martinez
	 * @Date 07/11/2012
	 * @param medios
	 * @param cveIdRepresentanteLegal
	 */
	void asociarMediosContactoARepresentante(List<MedioContacto> medios, Long cveIdRepresentanteLegal);
	
	List<RepresentanteLegal> obtenerRLsporIdPersonaFisica(Long cveIdPersona) throws Exception;
	
	void actualizarRepresentanteLegal(RepresentanteLegal representante) throws ParametrosInvalidosException;
	
	/**
	 * Verifica si el representante legal aún representa a la persona proporcionada
	 * @author Hugo Martinez
	 * @Date 30/01/2013
	 * @param cveIdPersonaRepresentante
	 * @param cveIdPersonaRepresentada
	 * @param tipoPersonaRepresentada
	 * @return
	 */
	boolean esRepresentanteDeLaPersona(Long cveIdPersonaRepresentante,
			Long cveIdPersonaRepresentada,
			TipoPersonaEnum tipoPersonaRepresentada);
	/**
	 * 
	 * @param representanteLegal
	 */
	void asociarRepresentanteLegalARegistroPatronal(RepresentanteLegal representanteLegal);
	
	/**
	 * 
	 * @param representanteLegal
	 */
	void asociarRepresentantesLegalesARegistroPatronal(List<RepresentanteLegal> representanteLegal);
	
	List<RepresentanteLegal> getRfcRepresentados(Long idPersonaRepresentante);
	RepresentanteLegal getRepresentanteByIdPersonaRepresentanteYRfcRepresentado(Long idPersona, String rfc);
	RepresentanteLegal asociarRepresentanteLegal(RepresentanteLegal model)throws Exception;
	List<RepresentanteLegal> getRepresentadPorIdPersonaRepresentante(Long idPersonaRepresentante);
	void registrarBajaRepresentante(RepresentanteLegal representante);
	
	void altaRepresentanteLegal(RepresentanteLegal representanteLegal);
	DitRepresentanteLegal consultarRelacionRepresentanteLegalPorIdentificadores(RepresentanteLegal representanteLegal);
}
