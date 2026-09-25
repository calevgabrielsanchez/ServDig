package mx.gob.imss.ctirss.delta.gestion.patronal.service.business;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Local
public interface SujetoObligadoServiceBusinessLocal {
	
	/**
	 * Obtiene el detalle del sujeto obligado con todos los datos
	 * requeridos por el m�dulo de clasificaci�n de empresas
	 * La informaci�n contenida es referente a:
	 * 
	 * Informaci�n general de la persona
	 * RFC, Registro Patronal, Tipo de Sociedad.
	 * 
	 * Domicilio Fiscal - Solo la clave del domicilio, para obtener el domicilio completo
	 * debe emplearse el servicio del m�dulo de domicilios.
	 * 
	 * Escritura Constitutiva
	 * Representante Legal
	 * Clasificaci�n
	 * 
	 * Datos de la Actividad econ�mica como:
	 * 
	 * Lista de productos, materias primas, maquinaria, equipo de transporte, procesos de trabajo
	 * personal y actividades complementarias(Transporte propio o ajeno, es distribuidor,
	 * servicios de instalaci�n o reparaci�n)
	 * 
	 * @param sujetoObligado TipoPersonaFiscal y IdPersona(Fisica/Moral) requeridos
	 * @return SujetoObligado
	 */
	SujetoObligado obtenerDetalleSujetoObligadoActividadEconomica(SujetoObligado sujetoObligado);
	Boolean esRfcPermitido (String rfc);
	/**
	 * Obtiene los datos generales de la persona
	 * @param cveIdPersona
	 * @return Persona
	 */
	Persona obtenerPersonaPorIdentificador(Long cveIdPersona);
	
	/**
	 * Obtiene los datos generales de la persona
	 * @param cveIdPersona
	 * @return Persona
	 */
	Persona obtenerPersonaMoralPorIdentificador(Long cveIdPersona);

	String consultaPrimaHistorica(String nrp, String fecha);

}
