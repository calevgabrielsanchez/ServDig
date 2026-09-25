package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.resultantes;

import java.io.ByteArrayOutputStream;
import java.util.List;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.SolicitudEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

public abstract class AbstractResultanteBusiness extends AbstractServiceBusiness{

	protected SolicitudEntityLocal solicitudEntity;
	protected GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	protected DocumentosServiceRemote documentosServiceRemote; 
	protected FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	
	
	protected FirmaElectronica _firma;
	
	
	public AbstractResultanteBusiness(SolicitudEntityLocal solicitudEntity, GrupoFamiliarServiceRemote grupoFamiliarServiceRemote,
			 DocumentosServiceRemote documentosServiceRemote, FirmaDigitalBusinessRemote firmaDigitalBusinessRemote){
		
		this.solicitudEntity = solicitudEntity;
		this.grupoFamiliarServiceRemote = grupoFamiliarServiceRemote;
		this.documentosServiceRemote = documentosServiceRemote;
		this.firmaDigitalBusinessRemote = firmaDigitalBusinessRemote;
		
	}
	
	public AbstractResultanteBusiness(){
		
	}
	
	
	
	/**
	 * Obtiene los bytes que representan el documento genereado
	 * 
	 * @param firma 
	 * @param tramite
	 * @return
	 */
	public abstract List<ByteArrayOutputStream> obtenerDocumento(Tramite tramite, Solicitud solicitud);
	
	
	public FirmaElectronica getFirma(Solicitud solicitud) {
		
		if( this._firma == null ){
			FirmaElectronica firma = new FirmaElectronica();
			firma.setCadenaOriginal(solicitud.getCadenaOriginal());
			firma.setSecuenciaNotaria(solicitud.getSecuenciaDeNotaria());
			firma.setRecibo(solicitud.getSelloDigital());
			firma.setSerialCertificado(solicitud.getNumeroSerieCertificado());
			this._firma = firma;
		}
		
		return _firma;
	}
	
	
	// -----------------------------------------------------
	// Getters - Setters
	// -----------------------------------------------------
	
	public FirmaElectronica getFirma() {
		return this._firma;
	}
	
	public void setFirma(FirmaElectronica firma) {
		this._firma = firma;
	}

	
}
