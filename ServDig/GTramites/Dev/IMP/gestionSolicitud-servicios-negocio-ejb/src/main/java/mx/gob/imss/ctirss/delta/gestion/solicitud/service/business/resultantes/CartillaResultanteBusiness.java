package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.resultantes;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DocumentosServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.SolicitudEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

public class CartillaResultanteBusiness extends AbstractResultanteBusiness {

	/**
	 * Personas a las que se les ha generado la cartilla
	 */
	private Set<Long> personasConCartilla = new HashSet<Long>();

	
	public CartillaResultanteBusiness(){
		
	}
	
	
	public CartillaResultanteBusiness(SolicitudEntityLocal solicitudEntity, GrupoFamiliarServiceRemote grupoFamiliarServiceRemote,
			 DocumentosServiceRemote documentosServiceRemote, FirmaDigitalBusinessRemote firmaDigitalBusinessRemote){
		
		super(solicitudEntity, grupoFamiliarServiceRemote, documentosServiceRemote, firmaDigitalBusinessRemote);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ByteArrayOutputStream> obtenerDocumento(Tramite tramite, Solicitud solicitud) {
		byte[] documento=null;
		
		// -------------------------------------------------
		// Regresamos todas las cartillas en una llamada.
		// Pero guardamos cada una por separado en la base
		// -------------------------------------------------
		ByteArrayOutputStream stream = null;
		ByteArrayOutputStream outputStream = null;
		List<ByteArrayOutputStream> listByteArray = new ArrayList<ByteArrayOutputStream>();
		
		
		log.debug("Estado tramite("+tramite.getTramiteId()+") cartilla "+ tramite.getEstadoTramite().getIdEstadoTramitePersona());
		
		if(!(tramite.getEstadoTramite().getIdEstadoTramitePersona().equals(EstadoTramiteEnum.CANCELADO.getCodigo()))){
			
			try {
				
				documento = (byte[])solicitudEntity.getDocumentoPorTipoIdTramite(tramite.getTramiteId(), DocumentoPorTipoEnum.CARTILLA.getId());
				if(documento == null) {
				
					FirmaElectronica firma = getFirma(solicitud);
					AsignacionNSS nss = null;
					List<Long> idPersonas = null;
					List<Fisica> personas = null;
					Derechohabiente dere = null;
					Long idPersona = null;
					
					if(tramite instanceof TramiteRegistroDerechohabiente) {
						TramiteRegistroDerechohabiente tramiteR = (TramiteRegistroDerechohabiente) tramite;
						idPersona = tramiteR.getFisica().getIdPersona();
						nss = tramiteR.getDatosAsegurado();
					}
					else if( tramite instanceof TramiteFisica ){
						Fisica fisica = ((TramiteFisica) tramite).getFisica();
						//Si no se cuenta con el NSS se debe consulyar en base al idPersona
						List<AsignacionNSS> listaNSS = grupoFamiliarServiceRemote.getAsignacionNss(fisica.getIdPersona());
						nss = listaNSS.get(0);
						idPersona = fisica.getIdPersona();
	//					GrupoFamiliar afectado = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), nss.getIdPersona());
					}
					else if( tramite instanceof TramiteCorreccionDerechohabiente ){
						
						TramiteCorreccionDerechohabiente tramiteC = (TramiteCorreccionDerechohabiente) tramite;
						
						if(tramiteC.getCandidatosCambioClinica() != null && !tramiteC.getCandidatosCambioClinica().isEmpty()) {
							idPersonas = tramiteC.getCandidatosCambioClinica();
							if(tramiteC.getPersonas() != null) {
								personas = tramiteC.getPersonas();
								dere = (Derechohabiente) personas.get(0);
								nss = dere.getAsignacionNSS();
							} else {
								Long idAsignacionNSS = ((TramiteCorreccionDerechohabiente) tramite).getIdAsignacionNss();
								nss = new AsignacionNSS();
								nss.setIdAsignacionNSS(idAsignacionNSS);
							}
						} else {
							if(tramiteC.getPersona() != null && tramite.getPersona() instanceof Derechohabiente) {
								dere = (Derechohabiente) tramiteC.getPersona();
								nss = dere.getAsignacionNSS();
								idPersona = dere.getIdPersona();
							}else {
								Long idAsignacionNSS = ((TramiteCorreccionDerechohabiente) tramite).getIdAsignacionNss();
								nss = new AsignacionNSS();
								nss.setIdAsignacionNSS(idAsignacionNSS);
								idPersona = ((TramiteCorreccionDerechohabiente) tramite).getIdPersona();
							}
						}
					}else if( tramite instanceof TramiteCircunscripcionForanea ){
						Derechohabiente der = (Derechohabiente)((TramiteCircunscripcionForanea) tramite).getPersona();
						nss = der.getAsignacionNSS();
						idPersona = der.getIdPersona();
					}
					
					if(idPersonas == null) {
						if( this.agregarPersonaCartilla(idPersona) ){
								documento = (byte[]) documentosServiceRemote.getCartillaNacionalSalud(idPersona, nss, firma);
								if(documento != null) {
									
									String nombreArchivo = "Cartilla";
									if( dere != null )
										nombreArchivo += dere.getNombre()+dere.getPrimerApellido();
									
									firmaDigitalBusinessRemote.guardarArchivoFirmado(firma.getSecuenciaNotaria(), nombreArchivo+".pdf", documento);
									stream = new ByteArrayOutputStream(documento.length);
									stream.write(documento, 0, documento.length);
									listByteArray.add( stream );
								}
						}
						
					} else {
						if(personas != null && !personas.isEmpty()) {
							Map<Long,Fisica> mapDif = new HashMap<Long, Fisica>();
							//QUITAMOS A LAS PERSONAS DUPLICADAS
							for(Fisica fisica: personas) {
								mapDif.put(fisica.getIdPersona(), fisica);
							}
							
							personas = new ArrayList<Fisica>(mapDif.values());
							for(Fisica fisica: personas) {
								if( this.agregarPersonaCartilla(fisica.getIdPersona()) ){
									documento = (byte[]) documentosServiceRemote.getCartillaNacionalSalud(fisica.getIdPersona(), nss, firma);
									if(documento != null) {
										firmaDigitalBusinessRemote.guardarArchivoFirmado(firma.getSecuenciaNotaria(), "Cartilla"+fisica.getNombre()+fisica.getPrimerApellido()+".pdf", documento);
											
										stream = new ByteArrayOutputStream(documento.length);
										stream.write(documento, 0, documento.length);
										listByteArray.add( stream );
									
									}
								}
									
							}
						} else {
							for(Long id: idPersonas) {
								if( this.agregarPersonaCartilla(id) ){
									documento = (byte[]) documentosServiceRemote.getCartillaNacionalSalud(id, nss, firma);
									if(documento != null) {
										firmaDigitalBusinessRemote.guardarArchivoFirmado(firma.getSecuenciaNotaria(), "Cartilla"+id+".pdf", documento);
										stream = new ByteArrayOutputStream(documento.length);
										stream.write(documento, 0, documento.length);
										listByteArray.add( stream );
									}
								}
								
							}
							
						}
					}
				
				}
				
			}catch(Exception e) {
				log.error("Ocurrio un error al generar cartilla", e);
				log.debug(tramite);
			}	
		
		}
		
		
		return listByteArray;
		
	}


	/**
	 * Agrega el id de la persona a la que se le generara la cartilla.
	 * 
	 * En caso de que la persona ya se encuentre en el arreglo regresa false
	 * 
	 * @param idPersona
	 * @return false en caso de que la persona ya se encuentre en el arreglo
	 */
	private boolean agregarPersonaCartilla(Long idPersona){
		log.debug("persona cartilla "+idPersona);
		return this.personasConCartilla.add(idPersona);
	}
	
	
	
	// ----------------------------------------------
	// Getters - Setters
	// ----------------------------------------------
	
	public Set<Long> getPersonasConCartilla() {
		return personasConCartilla;
	}

	public void setPersonasConCartilla(Set<Long> personasConCartilla) {
		this.personasConCartilla = personasConCartilla;
	}

}
