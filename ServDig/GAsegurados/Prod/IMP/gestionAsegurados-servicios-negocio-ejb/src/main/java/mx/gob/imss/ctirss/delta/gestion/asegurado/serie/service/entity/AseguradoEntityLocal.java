package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;

@Local
public interface AseguradoEntityLocal {
	Asegurado altaAsegurado(Asegurado asegurado);

	/**
	 * Obtiene los datos de la asignacion a traves de un NSS dado
	 * 
	 * @param nss NSS del asegurado
	 * @return Datos de la asignacion
	 */
	AsignacionNSS consultarAsignacionNSS(String nss);
	
	/**
	 * Obitene los datos de la asignacion en baja logica a traves de un NSS dado
	 * 
	 * @param nss NSS del asegurado
	 * @return Datos de la asignacion
	 */
	AsignacionNSS consultarAsignacionNSSEnBajaLogica(String nss);

	/**
	 * @param asegurado datos del asegurado
	 * @return Asegurado registrado
	 */
	Asegurado getAseguradoByAsegurado(Asegurado asegurado);
	
	DitAsignacionNss consultarDitAsignacionNss(String nss);
	
	/**
	 * Metodo que obtiene solo los atributos de nombre, apellido paterno, apellido materno y curp
	 * necesarios para generar la tarjeta de NSS
	 * @param nss
	 * @return
	 */
	AsignacionNSS getAsignacionNSSParaTarjeta(String nss);
	
	/**
	 * Metodo encargado de buscar el NSS en BDTU  en las tablas de legados
	 * @param nss String con el NSS a 11 posicioens
	 * @return Lista de personas con datos basicos y el NSS se seteara el origen donde se encontró el NSS en los indicadores
	 */
	
	List<Fisica> getAseguradoByNSSLegados(String nss);
	
	String obtenerEstadoPendienteConfirmar(String nss);
}
