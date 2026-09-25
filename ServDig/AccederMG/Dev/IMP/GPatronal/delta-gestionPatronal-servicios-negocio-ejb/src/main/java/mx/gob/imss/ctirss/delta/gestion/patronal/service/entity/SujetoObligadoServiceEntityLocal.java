package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.persistence.PatronesTempInc;

/**
 * 
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martï¿½nez Chamï¿½nica
 *  @Proyecto: delta
 *  @Archivo: SujetoObligadoServiceEntityLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.entity
 *  @Fecha: 17:58:54
 */
@Local
public interface SujetoObligadoServiceEntityLocal {
	
	/**
	 * Metodo 
	 * @param idPersona
	 * @return
	 */
	Boolean isRepresentanteLegal(Long idPersona);
	/**
	 * Consulta el detalle de la entidad DIT_PATRON_SUJETO_OBLIGADO con base en su identificador
	 * @param cveIdSujetoObligado Identificador del sujeto obligado
	 */
	SujetoObligado consultarDetalleSujetoObligado(SujetoObligado sujetoObligado);
	
	/**
	 * /**
	 * Obtiene la informaciï¿½n relacionada al acta constitutiva y registro sindicato.
	 * Esto solo aplica para persona moral.
	 * 
	 * Los datos que se obtienen son los siguientes:
	 * 
	 * ActaConstitutiva: Clave escritura, fecha de expediciï¿½n,
	 * nï¿½mero de folio mercantil, lugar de expediciï¿½n (pendiente),
	 * nï¿½mero de la escritura y nï¿½mero de notaria.
	 * 
	 * Registro Sindicato:
	 * Autoridad Laboral, clave registro sindicato, fecha de registro,
	 * nï¿½mero de referencia del registro.
	 * 
	 * @param sujetoObligado
	 * @return
	 * SujetoObligado
	 */
	SujetoObligado obtenerDatosGeneralesPatron(SujetoObligado sujetoObligado);
	
	
	SujetoObligado completarDatosGeneralesPatron(SujetoObligado sujetoObligado);
	
	List<SujetoObligado> consultarDetalleSujetoObligadoRFCFisica(SujetoObligado sujetoObligado);
	List<SujetoObligado> consultarDetalleSujetoObligadoIDFisica(SujetoObligado sujetoObligado);
	
	SujetoObligado consultarPrimerSujetoObligadoByRfc(String rfc,TipoPersonaEnum tipoPersona);
	
	List<SujetoObligado> consultarDetalleSujetoObligadoRFCMoral(SujetoObligado sujetoObligado);
	List<SujetoObligado> consultarDetalleSujetoObligadoIDMoral(SujetoObligado sujetoObligado);
	
	List<Long> consultarIdsPatronesSOPorRfcTipoPersona(String rfc,TipoPersonaEnum tipoPersona);
	
	/**
	 * Onbtiene el patron sujeto obligado a partir de la cve del patron general
	 */
	Long getCveIdSujetoObligadoPorCvePatronGeneral(Long cveIdPatronGeneral);
	
	/**
	 * Onbtiene el patron sujeto obligado a partir de la cve del patron general
	 */
	Long getCveIdSujetoObligadoPorRP(String rp);
	
	Modalidad getModalidadPorNumModalidad(String numModaliad);
	
	/**
	 * Agrega al objeto de sujeto obligado proporcionado la siguiente informaciï¿½n:
	 * Divisiï¿½n, Grupo, Fracciï¿½n, Clase y Prima SRT.
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @return SujetoObligado
	 */
	SujetoObligado obtenerDetallesRegistroPatronal(SujetoObligado sujetoObligado);
	
	/**
	 * 
	 * @param sujetoObligado
	 * @return
	 * @throws Exception 
	 */
	SujetoObligado obtenerDetallesRegistroPatronalDictamen(SujetoObligado sujetoObligado) throws Exception;
	
	/**
	 * Obtiene la siguiente informaci�n de la persona Moral:
	 * IdPersonaMoral, Nombre Comercial, RFC, Raz�n Social,
	 * Tipo de Sociedad adem�s del registro patronal
	 * @author Hugo Armando Mart�nez Cham�nica
	 * @param sujetoObligado
	 * @return
	 * SujetoObligado
	 */
	SujetoObligado consultarDetalleSujetoObligadoIdPersonaMoral(
			SujetoObligado sujetoObligado);
	
	/**
	 * Obtiene la informaciï¿½n de la persona Fisica:
	 * IdPersona, Nombre, Primer Apellido, Segundo Apellido
	 * RFC y Nombre Comercial asi como su registro patronal
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param sujetoObligado
	 * @return
	 * SujetoObligado
	 */
	SujetoObligado consultarDetalleSujetoObligadoIdPersonaFisica(
			SujetoObligado sujetoObligado);
	
	/**
	 * Obtiene la lista de productos asociada a un sujeto obligado
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param idPatronSujetoObligado
	 * @return
	 * List<Producto>
	 */
	List<Producto> consultarListaProductos(Long idPatronSujetoObligado);
	
	/**
	 * Obtiene la lista de materiales asociada a un sujeto obligado
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param idPatronSujetoObligado
	 * @return List<MateriaPrima>
	 */
	List<MateriaPrima> consultarMateriaPrimaMateriales(Long idPatronSujetoObligado);
	
	/**
	 * Obtiene la lista de maquinaria y equipo de transporte asociado al patrï¿½n
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param idPatronSujetoObligado
	 * @return List<MaquinariaEquipo>
	 */
	List<MaquinariaEquipo> consultarMaquinariaEquipo(Long idPatronSujetoObligado);
	
	/**
	 * Obtiene lalista de equipo de transporte aso
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param idPatronSujetoObligado
	 * @return List<EquipoTransporte>
	 */
	List<EquipoTransporte> consultarEquipoTranporte(Long idPatronSujetoObligado);
	
	/**
	 * 
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param idPatronSujetoObligado
	 * @return
	 * List<Proceso>
	 */
	List<Proceso> consultarProcesos(Long idPatronSujetoObligado);
	
	/**
	 * 
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param idPatronSujetoObligado
	 * @return
	 * List<Personal>
	 */
	List<Personal> consultarPersonal(Long idPatronSujetoObligado);
	
	/**
	 * 
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param idSujetoObligado
	 * @param tipoPersona
	 * @return
	 * Long
	 */
	Long consultarClaveDomicilioFiscal(Long idSujetoObligado, TipoPersonaFiscal tipoPersona);
	
	/**
	 * 
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param idPersona
	 * @param nuevaRazonSocial
	 * void
	 */
	void actualizarNombreComercialFisica(TramiteFisica tFisica);
	
	/**
	 * @author El Jugosote
	 * @param tMoral
	 */
	void actualizarNombreComercialMoral(TramiteMoral tMoral);
	
	/**
	 * 
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param registroPatronal
	 * @param tipoPersona
	 * @return
	 * SujetoObligado
	 */
	SujetoObligado consultarPorRegistroPatronal(String registroPatronal, TipoPersonaFiscal tipoPersona);

	SujetoObligado consultarPorRegistroPatronalDictamen(String registroPatronal, TipoPersonaFiscal tipoPersona);
	
	SujetoObligado consultarPorRegistroPatronalBasic(String registroPatronal, TipoPersonaFiscal tipoPersona);

	
	/**
	 * Asocia un nuevo medio de contacto con el sujeto obligado
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param mediosContacto
	 * @param tipoPersona
	 * @param persona
	 * 
	 */
	void asociarMediosContacto(List<MedioContacto> mediosContacto, TipoPersonaFiscal tipoPersona, Persona persona );
	
	/**
	 * Obtiene la subdelegaciï¿½n del patron
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param idSujetoObligado
	 * @return Subdelegacion
	 */
	Subdelegacion consultarSubdelegacion(Long idSujetoObligado);
	
	/**
	 * Obtiene todos los sujetos obligados a los cuales la 
	 * persona con el identificador proporcionado representa
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param cveIdPersona
	 * @return List<SujetoObligado>
	 */
	List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegal(Long cveIdPersona);
	
	/**
	 * 
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param cveIdPersona
	 * @return Persona
	 */
	Persona obtenerPersona(Long cveIdPersona);
	
	/**
	 * 
	 * @param cveIdPersona
	 * @return
	 */
	Persona obtenerPersonaMoral(Long cveIdPersona);
	
	/**
	 * Obtiene el identificador del domicilio con tipo centro de trabajo
	 * asociado al sujeto obligado(rp) proporcionado
	 * @author Hugo Armando Martï¿½nez Chamï¿½nica
	 * @param idSujetoObligado
	 * @return Long
	 */
	Long consultarClaveDomicilioCentroTrabajo(Long idSujetoObligado);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 18/06/2012
	 * @param idPatronSujetoObligado
	 * @return
	 */
	List<Bien> consultarBienes(Long idPatronSujetoObligado);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 23/08/2012
	 * @param fisica
	 */
	void actualizarDatosGeneralesFisca(Fisica fisica);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 23/08/2012
	 * @param moral
	 */
	void actualizarDatosGeneralesMoral(Moral moral);
	
	/**
	 * Elimina los datos de la escritura constitutiva asociados a la persona
	 * con el identificador proporcionado
	 * @author Hugo Martinez
	 * @Date 28/08/2012
	 * @param tipoPersona
	 * @param idPersona
	 */
	void eliminarEscrituraConstitutivaDePersona(Long idPersona);
	
	/**
	 * Elimina los datos del registro sindicato asociados a la persona
	 * con el identificador proporcionado
	 * @author Hugo Martinez
	 * @Date 28/08/2012
	 * @param tipoPersona
	 * @param idPersona
	 */
	void eliminarRegistroSindicatoDePersona(Long idPersona);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 07/09/2012
	 * @param input
	 * @return List<SujetoObligado>
	 */
	DatosSalidaPaginador<SujetoObligado> consultarRegistrosPatronalesPersonaFisica(DatosEntradaPaginador<SujetoObligado> input);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 07/09/2012
	 * @param input
	 * @return List<SujetoObligado>
	 */
	DatosSalidaPaginador<SujetoObligado> consultarRegistrosPatronalesPersonaMoral(DatosEntradaPaginador<SujetoObligado> input);
	
	void actualizarNombreComercial(SujetoObligado sujetoObligado);
	
	/**
	 * Elimina los medios de contacto actuales de la persona y registra y asocia los medios de contacto proporcionados
	 * @author Hugo Martinez
	 * @Date 14/11/2012
	 * @param persona
	 */
	List<MedioContacto> reemplazarMediosContactoDePersona(Persona persona);
	
	/**
	 * Obtiene el domicilio de centro de trabajo migrado como una cadena
	 * @author Hugo Martinez
	 * @Date 15/02/2013
	 * @param cveIdPatronSujetoObligado
	 * @return
	 */
	String obtenerDomicilioMigrado(Long cveIdPatronSujetoObligado);
	
	/**
	 * Obtiene los registros patronales asociados a la persona moral cuyo identificador
	 * es proporcionado como par&aacute;metro
	 * @param idPersona Identificador de la persona moral en la tabla DIT_PERSONA_MORAL
	 * @return List<SujetoObligado>
	 */
	List<SujetoObligado> consultarRegistrosPatronalesPersonaMoral(Long idPersona);
	
	
	/**
	 * Obtiene los registros patronales asociados a la persona f&iacute;sica cuyo identificador
	 * es proporcionado como par&aacute;metro
	 * @param idPersona Identificador de la persona f&iacute;sica en la tabla DIT_PERSONA_FISICA
	 * @return List<SujetoObligado>
	 */
	List<SujetoObligado> consultarRegistrosPatronalesPersonaFisica(Long idPersona);
	
	/**
	 * Obtiene los registros patronales asociados a la persona moral cuyo identificador
	 * es proporcionado como par&aacute;metro, pero solo parsea los datos necesarios como modlidad, nrp, y nombre comercial
	 * @param idPersona Identificador de la persona moral en la tabla DIT_PERSONA_MORAL
	 * @return List<SujetoObligado>
	 */
	List<SujetoObligado> consultarRegistrosPatronalesPersonaMoralSoloDatosBase(Long idPersona);
	
	
	/**
	 * Obtiene los registros patronales asociados a la persona f&iacute;sica cuyo identificador
	 * es proporcionado como par&aacute;metro
	 * @param idPersona Identificador de la persona f&iacute;sica en la tabla DIT_PERSONA_FISICA
	 * @return List<SujetoObligado>
	 */
	List<SujetoObligado> consultarRegistrosPatronalesPersonaFisicaSoloDatosBase(Long idPersona);
	
	List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegalDatosBase(Long idPersona);
	List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegalDatosBaseFisica(Long idPersona);
	List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegalDatosBaseMoral(Long idPersona);
	
	SujetoObligado getSujetoObligadoByDatosPatronyRfc(SujetoObligado obligado);
	
	SujetoObligado getSujetoObligadoByNrpyCvePersonaFisicaMoral(SujetoObligado sujeto);
	
	void updateRelacionSujetoObligadoPersonaMoralFisica(SujetoObligado sujeto); 
	
	/**
	 * Regresa el nï¿½mero de registros patronales existentes en para una persona
	 * dentro del municipio proporcionado y con la misma fraccion proporcionada.
	 * @param idPersona Identificador de la persona (cveFisica o cveMoral)
	 * @param idTipoPersona Fisica o Moral
	 * @param idMunicipioImss Identificador delta del municipio IMSS
	 * @param idFraccion Identificador delta de la fracciï¿½n
	 * @return Nï¿½mero de registros patronales que cumplen con los filtros
	 */
	Integer consultarNumeroDeRegistrosPatronalesPorPersonaMunicipioYFraccion(Long idPersona, Long idTipoPersona, Long idMunicipioImss, Long idFraccion, Long idRegistroPatronalActual);
	
	/**
	 * Metodo Consulta a los sujetos obligados pm o pf que a partir del RFC clase modalidad y municipio
	 * @param SujetoObligado
	 * @return List <SujetoObligado> con los registros que cumplan la condicion de busqueda
	 */
	List<SujetoObligado> getSujetoObligadoByRfcClaseMunicipioModalidad(
			SujetoObligado obligado);
	
	/**
	 * Este metodo es empleado para el alta patronal, se utiliza debido a que en la misma transacci�n de alta
	 * se notifica a sindo la actualizaciond e datos generales de todos los rp asociados a la persona, sin embargo
	 * cuando se da de alta el último registro pertenece al nuevo rp el cual es notificado como movimiento 01
	 * y debe omitirse el movimiento 05 para este RP.
	 * @param sujetoObligado
	 * @return Lista registros Patronales
	 */
	List<SujetoObligado> obtenerRegistrosPatronalesExceptoUltimo(
			SujetoObligado sujetoObligado);
	
	/**
	 * Consulta si existe un registro patronal previo en el municipio proporcionado asociado
	 * a la fracción y persona proporcionadas como parámetros, excluyendo el registro patronal
	 * proporcionado (idRegistroPatronalActual)
	 * @param rfc RFC de la persona sobre la cuál se buscarán los registros patronales asociados en el municipio proporcionado
	 * @param idTipoPersona Tipo de persona (Fisica o Moral) a la cual pertenece el registro patronal
	 * @param idMunicipioImss Identificador del municipio IMSS donde se buscarán registros patronales con la fracción proporcionada
	 * @param idFraccion Identificador de la fracción que se busca.
	 * @param idRegistroPatronalActual Identificador del registro patronal que no se debe tomar en cuanta para la consulta
	 * @return Detalle de registro Patronal.
	 */
	SujetoObligado consultarRegistroPatronalPorRFCMunicipioYFraccion(
			String rfc, Long idTipoPersona, Long idMunicipioImss,
			Long idFraccion, Long idRegistroPatronalActual);
	
	List<Moral> consultarRepresentadosMoralesPorRepresentanteLegal(Long idPersona);
	List<Fisica> consultarRepresentadosFisicosPorRepresentanteLegal(Long idPersona);
	
	/**
	 * Valida si la cveIdPersona tiene la marca activa RPC
	 * 
	 * @param cveIdPersona
	 * @param cveTipoPersona
	 * @return Integer
	 */
	Integer validaCveIdPersonaPorRegistroPatronalClaseActivo(String rfc, Integer cveTipoPersona);
	
	Modalidad getModalidadPorIdSujetoObligado(Long idPatronSujetoObligado);
	
	/**
	 * Consulta la tabla PATRONES_TEMP_INC con los parámetros que recibe para obtener el número de trabajadores asociados al Registro Patronal
	 * que se consulta.
	 * 
	 * @param cveRegistroPatronal
	 * @param cveModalidad
	 * @param digitoVerificador
	 * @return PatronesTempInc
	 */
	PatronesTempInc getPatronesTempInc(String cveRegistroPatronal, String cveModalidad, String digitoVerificador);
	
	/**
	 * Consulta basica para obtener solo los datos basicos del patron
	 * 
	 * Registro Patronal
	 * Modalidad(id, descripcion, num, siglas agregado medico)
	 * Digito Verigicador
	 * Datos de la persona fisica (si aplica)
	 * Razon social (persona modal - Si aplica)
	 * @param idPatronGeneral
	 * @return
	 */
	SujetoObligado getDatosBasicosSOporIdPatronGeneral(Long idPatronGeneral);
	
	SujetoObligado getDatosBasicosSOporIdPatronSO(Long idPatronSujetoObligado);
	
	/**
	 * Metodo que sulta la informacion de registro patronal asociados a un RFC persona fiscia si importar si el RFC esta repetido N veces
	 * @param strRFC
	 * @return
	 */
	List<SujetoObligado> consultarRegistrosPatronalesRFCPersonaFisica(
			String strRFC);
	/**
	 * Metodo que sulta la informacion de registro patronal asociados a un RFC persona moral si importar si el RFC esta repetido N veces
	 * @param strRFC
	 * @return
	 */
	List<SujetoObligado> consultarRegistrosPatronalesRFCPersonaMoral(
			String strRFC);
	
	List<SujetoObligado> consultarRegistrosPatronalesRFC(String strRFC, Long tipoPersona);
	
	/**
	 * Metodo que devuelte las modalidades activas
	 * @return
	 */
	List <Modalidad> getModalidadades();
	
	String getDescDomicilioSubdelegacion(Long cveIdSubdelegacion); 
	
}
