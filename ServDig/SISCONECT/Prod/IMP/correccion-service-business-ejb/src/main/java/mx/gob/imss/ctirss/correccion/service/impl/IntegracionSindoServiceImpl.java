/**
 * Proyecto: IMSS - SaTIC
 *
 * archivo: IntegracionSindoServiceImpl.java
 *
 * Creado: 18/08/2008
 *
 * Derechos Reservados de Copia (c) - TCS / Instituto Mexicano del Seguro Social - 2008
 */

package mx.gob.imss.ctirss.correccion.service.impl;

import java.rmi.RemoteException;

import mx.gob.imss.cia.infraestructura.integracion.impl.AdministradorServiciosImpl;
import mx.gob.imss.ctirss.correccion.service.IntegracionSindoService;
import mx.gob.imss.ctirss.satic.integracion.InformacionPatronSalidaVO;
//import mx.gob.imss.ctirss.wsConsultaPatron.WSConsultaPatronServiceProxy;
//import mx.gob.imss.ctirss.wsConsultaPatron.vo.InfoPatronEntrada;
//import mx.gob.imss.ctirss.wsConsultaPatron.vo.InfoPatronSalida;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import vo.InfoPatronEntrada;
import vo.InfoPatronSalida;
import ws.WSConsultaPatronServiceProxy;





/**
 * Proyecto: Sistema de afiliacion de Trabajadores de la Industria de la
 * Construccion. <BR>
 * Objetivo: Servicio integracion datos de SINDO Fecha de creacion: aug 18, 2008 <BR>
 * Copyright (c) IMSS <BR>
 * 
 * @author TCS
 * @version 1.0
 */

public class IntegracionSindoServiceImpl implements IntegracionSindoService {

	/**
	 * Herramienta de depuracion.
	 */
	private static Log log = LogFactory
			.getLog(IntegracionSindoServiceImpl.class);

	AdministradorServiciosImpl administradorServicios = new AdministradorServiciosImpl();

	public AdministradorServiciosImpl getadministradorServicios() {
		return administradorServicios;
	}

	public void setadministradorServicios(
			AdministradorServiciosImpl administradorServicios) {
		this.administradorServicios = administradorServicios;
	}

	/**
	 * 
	 * @param registroPatronal
	 *            String
	 * @param modalidad
	 *            String
	 * @param digitoVerificador
	 *            String
	 * @return InformacionPatronSalidaVO
	 */
	public InformacionPatronSalidaVO obtenerDatosPatron(
			String registroPatronal, String modalidad, String digitoVerificador) {
		InformacionPatronSalidaVO regresa = new InformacionPatronSalidaVO();
		InfoPatronSalida salida = null;
		try {

			WSConsultaPatronServiceProxy vo = new WSConsultaPatronServiceProxy();
			InfoPatronEntrada patIn = new InfoPatronEntrada();
			patIn.setRegistroPatronal(registroPatronal);
			patIn.setModalidad(modalidad);
			patIn.setDigitoVerificador(digitoVerificador);

			try {
				salida = vo.getInformacionPatron(patIn);
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			
			log.debug("IMPRIMIENDO VALORES DE RETORNO WS PATRONES");
			log.debug("Codigo: " + salida.getCodigo());
			regresa.setCodigo(salida.getCodigo());
			log.debug("Codigo Postal: " + salida.getCodigoPostal());
			regresa.setCodigoPostal(salida.getCodigoPostal());
			log.debug("Correo: " + salida.getCorreo());
			regresa.setCorreo(salida.getCorreo());
			log.debug("CURP: " + salida.getCurp());
			regresa.setCURP(salida.getCurp());
			log.debug("CveDeleg: " + salida.getCveDelegacion());
			regresa.setCveDelegacion(Integer
					.parseInt(salida.getCveDelegacion() != null ? salida
							.getCveDelegacion() : "0"));
			log.debug("CveSubdeleg: " + salida.getCveSubDelegacion());
			regresa.setCveSubDelegacion(Integer.parseInt(salida.getCveSubDelegacion() != null ? salida.getCveSubDelegacion() : "0"));
			log.debug("CveMuni: "+ salida.getCveMunicipio());
			regresa.setCveMunicipio(salida.getCveMunicipio());
			//regresa.setCveTipoMov(Integer.parseInt(salida.getCveTipoMov().equals("")));
			regresa.setDescripcion(salida.getDescripcion());
			regresa.setDomicilio(salida.getDomicilio());
			log.debug("Exito : "+ salida.getExito());
			regresa.setExito(salida.getExito());
			regresa.setLocalidad(salida.getLocalidad());
			log.debug("Razon Social: " + salida.getRazonSocial());
			regresa.setRazonSocial(salida.getRazonSocial());
			regresa.setRFC(salida.getRfc());

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			log.info(e.toString());
		}
		return regresa;
	}

}
