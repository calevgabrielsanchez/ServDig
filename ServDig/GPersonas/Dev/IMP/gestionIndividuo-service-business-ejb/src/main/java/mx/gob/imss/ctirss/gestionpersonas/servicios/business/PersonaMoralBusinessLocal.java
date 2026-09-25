package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.individuo.PersonaMoralNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.NumeroMaximoResultadosSuperadoException;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

@Local
public interface PersonaMoralBusinessLocal {

    List<Moral> getPersonaMoral(Moral personaMoral);

    /**
     * Si el id de la persona a guardar no es nulo, no puede ejecutar el save y
     * regresa null como ID.
     * 
     * @param personaMoral
     * @return
     */
    Moral altaPersonaMoral(Moral personaMoral);

    /**
     * Samuel R G puse este metodo para que no me fallara el
     * RegistroPersonaMoralController pero obviamente se puede quitar si asi lo
     * consideran
     * 
     * @param rfc
     * @return
     * @throws NumeroMaximoResultadosSuperadoException
     */
    List<Moral> buscarPersonaMoralPorRfcEnImss(String rfc);

    DatosSalidaPaginador<Moral> getPersonaMoralFiltro(DatosEntradaPaginador<Moral> parametrosPaginador) throws NumeroMaximoResultadosSuperadoException;

    Moral getPersonaMoral(Long idPersonaMoral);
    
    /**
	 * Servicio para modificar una persona fisica, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 08/03/2013
	 * @param datosPersona - incluye la persona moral, as� como las banderas 
	 * 		que indican la informaci�n a modificar
	 */
	void afectarDatosPersonaMoral(AfectarDatosPersonaWrapper datosPersona)
			throws PersonaMoralNoEncontradaException;
	
	/**
	 * Servicio para modificar las calificaciones de una persona moral, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 28/02/2013
	 * @param datosPersona - incluye la persona moral, as� como las banderas 
	 * 		que indican la informaci�n a modificar
	 */
	void afectarCalificacionesPersona(AfectarDatosPersonaWrapper datosPersona);
	
	/**
	 * Servicio para modificar los identificadores de una persona, especialmente creado para el
	 * caso de uso DST - 04 Afectar Datos de Persona
	 * 
	 * @author Marco Sanchez
	 * @fecha 28/02/2013
	 * @param datosPersona - incluye la persona fisica, as� como las banderas 
	 * 		que indican la informaci�n a modificar
	 * 
	 * @param datosPersona
	 */
	void afectarIdentificadoresPersona(AfectarDatosPersonaWrapper datosPersona);

	Moral getPersonaMoral_AP(Long idPersona);
}
