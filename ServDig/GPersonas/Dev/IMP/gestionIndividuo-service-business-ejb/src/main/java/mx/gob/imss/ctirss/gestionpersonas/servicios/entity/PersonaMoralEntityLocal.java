package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.PersonaMoralNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;

@Local
public interface PersonaMoralEntityLocal {

    List<Domicilio> buscarDomiciliosPersonaMoral(Long idPersona);

    DitPersonaMoral altaPersonaMoral(DitPersonaMoral ditPersonaMoral);

    List<Moral> buscarPersonaMoral(Moral personaMoral);

    TipoSociedad getTipoSociedadByDescripcion(String descTipoSociedad);

    Long contarNumPersonasTotal();

    TipoSociedad getTipoSociedad(Long idTipoSociedad);
    
    /**
     * Metodo que realiza la paginacion de acuerdo a los parametros, datos de
     * los filtros de la persona moral.
     * 
     * @param params
     * @return 
     * @throws Exception
     */
    DatosSalidaPaginador<Moral> paginar(
			DatosEntradaPaginador<Moral> params
			) throws Exception;
    
    
    /**
     * Actualiza la informaci�n general de una persona moral.
     * 
     * 
     * 
     * @param moral
     * @throws PersonaNoEncontradaException 
     */
    void actualizarPersonaMoral(Moral moral) throws PersonaNoEncontradaException;

    /**
	 * M�todo para modificar una persona fisica, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 08/03/2013
	 * @param datosPersona - incluye la persona moral, as� como las banderas 
	 * 		que indican la informaci�n a modificar
	 * @throws PersonaNoEncontradaException
	 */
	void afectarDatosPersonaMoral(AfectarDatosPersonaWrapper datosPersona)
			throws PersonaMoralNoEncontradaException;
	
	/**
	 * M�todo para modificar las calificaciones de una persona moral, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 28/02/2013
	 * @param datosPersona - incluye la persona moral, as� como las banderas 
	 * 		que indican la informaci�n a modificar
	 */
	void afectarCalificacionesPersona(AfectarDatosPersonaWrapper datosPersona);
	
	/**
	 * Metodo que consulta si una persona moral tiene un acta constitutiva
	 * 
	 * @param cveIdPersona
	 * @return
	 */
	Integer consultaActaConstitutivaPersonaMoral(Long cveIdPersona);
	
	/**
	 * Metodo que consulta si una persona moral tiene un idSindicato
	 * 
	 * @param cveIdPersona
	 * @return
	 */
	Integer consultaSindicatoPersonaMoral(Long cveIdPersona);
    
	/**
	 * Metodo que valida si una persona moral tiene un indAcreditado
	 * 
	 * @param cveIdPersona
	 * @return
	 */
	Integer validaIndAcreditado(Long cveIdPersona);

	/**
	 * Metodo que actualiza la razon social y tipo de sociedad de una PM
	 * Solo si los parametros no son nulos
	 * Se agrega cambio para version de produccion
	 * 
	 * @param cveIdPersona
	 * @return
	 */
	void actualizaRazonSocialTipoSociedad(Long cveIdPersona, String nombreRazonSocial, TipoSociedad tipoSociedad);

	List<Moral> buscarPersonaMoral_AP(Moral personaMoral);

	List<Moral> buscarPersonaMoral_RFC_AP(Moral personaMoral);
	
}
