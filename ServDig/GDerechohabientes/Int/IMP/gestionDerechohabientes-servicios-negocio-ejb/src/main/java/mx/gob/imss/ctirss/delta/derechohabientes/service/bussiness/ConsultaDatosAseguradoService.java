package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.TramitePersonaFisicaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ConsultaDatosAseguradoServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.DatosAfiliacionBeneficiarioDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.DatosAfiliacionBeneficiarioEstDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.DatosAfiliacionDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.DatosAseguradoDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.MedioContactoDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.AseguradoDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.BeneficiarioDTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.persistence.DitConstanciaEstudio;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

/**
 * Session Bean implementation class ConsultaDatosAseguradoService
 */
@Stateless(name="consultaDatosAseguradoService", mappedName="consultaDatosAseguradoService")
public class ConsultaDatosAseguradoService  extends AbstractServiceBusiness  implements ConsultaDatosAseguradoServiceRemote {

	@EJB
	private GrupoFamiliarServiceRemote grupoFamiliarServiceRemote;
	
	@EJB
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	
	@EJB 
	private ProrrogaDaoLocal prorrogaDao;
	
	@EJB 
	private TramitePersonaFisicaDaoLocal tramitePersonaFisicaDaoLocal;
	
	
	@EJB 
	private PersonaBusinessRemote personaBusinessRemote;
	
	@Override
	public DatosAseguradoDTO consultaDatosAseguradoPorNSS(String nss) throws DerechohabientesBusinessException,Exception {

		DatosAseguradoDTO datosAseguradoDTO = new DatosAseguradoDTO();
		AseguradoDTO aseguradoDTO = null;
		BeneficiarioDTO beneficiarioDTO = null;
		DatosAfiliacionBeneficiarioDTO datosAfiliacionBeneficiarioDTO = null;
		DatosAfiliacionBeneficiarioEstDTO datosAfiliacionBeneficiarioEstDTO = null;
		MedioContactoDTO medioContactoDTO = null;
		DatosAfiliacionDTO datosAfiliacionDTO = new DatosAfiliacionDTO();
		List<BeneficiarioDTO> lstBeneficiariosDTO = new ArrayList<BeneficiarioDTO>();
		List<MedioContactoDTO> listaMediosContacto = new ArrayList<MedioContactoDTO>();


		try{
			List<GrupoFamiliar> grupoFamiliar = grupoFamiliarServiceRemote.findDatosBasicosIntegrantesGrupoByNss(nss);

			if(grupoFamiliar != null && !grupoFamiliar.isEmpty()){
				for (GrupoFamiliar integrante: grupoFamiliar) {
					AsignacionNSS nssAsegurado = integrante.getAsignacionNSS();
					Derechohabiente derechohabiente = integrante.getDerechohabiente();
					if(integrante.getCalidad() != null){
						//Si el integrante tiene la calidad 1 significa que es el asegurado o pensionado
						if(integrante.getCalidad().intValue() == 1){
							log.error("Encuentro al segurado "+integrante.getCalidad()+ " [" + nssAsegurado.getIdAsignacionNSS() + " - " + derechohabiente.getIdPersona() + "]");
							//Datos Asegurado
							aseguradoDTO = new AseguradoDTO();
							aseguradoDTO.setNss(nss);
							aseguradoDTO.setNombreAsegurado(derechohabiente.getNombre());
							aseguradoDTO.setPrimerApellidoAsegurado(derechohabiente.getPrimerApellido());
							aseguradoDTO.setSegundoApellidoAsegurado(derechohabiente.getSegundoApellido());
							aseguradoDTO.setCurp(derechohabiente.getCurp());
							aseguradoDTO.setRfc(derechohabiente.getRfc());
							aseguradoDTO.setFechaNacimientoAsegurado(derechohabiente.getFechaNacimientoFormateada());
							aseguradoDTO.setFechaDefuncionAsegurado(DateUtils.dateToStringConFormato(derechohabiente.getFechaDefuncion(), "dd/MM/yyyy"));
							aseguradoDTO.setCveEntidad(derechohabiente.getLugarNacimiento().getClave());


							aseguradoDTO.setCveLugarNacimientoAsegurado(derechohabiente.getLugarNacimiento().getClave());
							aseguradoDTO.setLugarNacimientoAsegurado(derechohabiente.getLugarNacimiento().getNombre());
							aseguradoDTO.setCveSexoAsegurado(derechohabiente.getSexo().getIdSexo().toString());
							aseguradoDTO.setSexoAsegurado(derechohabiente.getSexo().getDescripcion());
							aseguradoDTO.setIdPersona(derechohabiente.getIdPersona().intValue());

							aseguradoDTO.setDesEstadoCivil(derechohabiente.getEstadoCivil().getDescripcion());
							aseguradoDTO.setCveIdEstadoCivil(derechohabiente.getEstadoCivil().getIdEstadoCivil());


							if(integrante.getMedicoEnTurno() != null && integrante.getMedicoEnTurno().getUnidadMedicaFamiliar() != null){
								datosAfiliacionDTO.setSubdelegacion(String.valueOf(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getId()));
								datosAfiliacionDTO.setDelegacion(String.valueOf(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().getId()));
								datosAfiliacionDTO.setUmf(String.valueOf(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF()));
							}



							datosAseguradoDTO.setAseguradoDTO(aseguradoDTO); 
							datosAseguradoDTO.setDatosAfiliacionDTO(datosAfiliacionDTO);

							try{
								Domicilio domAse = grupoFamiliarServiceRemote.findDomicilioByIdPersona(derechohabiente.getIdPersona()).getDomicilio();
								if(domAse != null){
									datosAseguradoDTO.setDomicilioAsegurado(domAse);
								}else{
									aseguradoDTO.setCodigo("002");
									aseguradoDTO.setMensaje("El asegurado no cuenta con domicilio");
								}
							}catch(Exception e){
								log.error("el asegurado no tiene domicilio" ,e);
								aseguradoDTO.setCodigo("002");
								aseguradoDTO.setMensaje("El asegurado no cuenta con domicilio");
							}

							try {
								Persona persona = new Persona();
								TipoPersona tipoPersona = new TipoPersona();
								tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
								persona.setIdPersona(derechohabiente.getIdPersona());
								persona.setTipoPersona(tipoPersona);

								List<MedioContacto> listaMedios = mediosContactoServiceBusinessRemote.consultarMedioDeContactoPersona(persona);

								for (MedioContacto medio: listaMedios) {
									medioContactoDTO = new MedioContactoDTO();
									medioContactoDTO.setTipoMedioContacto(medio.getTipoMedioContacto());
									medioContactoDTO.setDescMedioContacto(medio.getDesFormaContacto());
									listaMediosContacto.add(medioContactoDTO);
								}
								datosAseguradoDTO.setLstMediosContactoAseguradoDTO(listaMediosContacto);
							} catch (PersonaSinMedioDeContactoException e) {
								e.printStackTrace();
							}
						}else{
							log.error("Encuentro un beneficiario "+integrante.getCalidad()+ " [" + nssAsegurado.getIdAsignacionNSS() + " - " + derechohabiente.getIdPersona() + "]");
							
							//Datos Beneficiarios
							beneficiarioDTO = new BeneficiarioDTO();
							beneficiarioDTO.setNss(nss);
							beneficiarioDTO.setNombreBen(derechohabiente.getNombre());
							beneficiarioDTO.setPrimerApellidoBen(derechohabiente.getPrimerApellido());
							beneficiarioDTO.setSegundoApellidoBen(derechohabiente.getSegundoApellido());
							beneficiarioDTO.setCurpBen(derechohabiente.getCurp());
							beneficiarioDTO.setRfc(derechohabiente.getRfc());
							beneficiarioDTO.setFechaNacimientoBen(derechohabiente.getFechaNacimiento());
							beneficiarioDTO.setCveLugarNacimientoAsegurado(derechohabiente.getLugarNacimiento().getClave());
							beneficiarioDTO.setLugarNacimientoAsegurado(derechohabiente.getLugarNacimiento().getNombre());
							beneficiarioDTO.setCveSexoAsegurado(derechohabiente.getSexo().getIdSexo().toString());
							beneficiarioDTO.setSexoBen(derechohabiente.getSexo().getDescripcion());
							beneficiarioDTO.setIdPersona(derechohabiente.getIdPersona().intValue());
							beneficiarioDTO.setDesEstadoCivil(derechohabiente.getEstadoCivil().getDescripcion());
							beneficiarioDTO.setCveIdEstadoCivil(derechohabiente.getEstadoCivil().getIdEstadoCivil());					
							beneficiarioDTO.setFechaDefuncionAsegurado(DateUtils.dateToStringConFormato(derechohabiente.getFechaDefuncion(), "dd/MM/yyyy"));
							beneficiarioDTO.setCveEntidad(derechohabiente.getLugarNacimiento().getClave());
							
							try{
								Domicilio domBene = grupoFamiliarServiceRemote.findDomicilioByIdPersona(derechohabiente.getIdPersona()).getDomicilio();
								if(domBene != null){
									beneficiarioDTO.setDomicilio(domBene);
								}else{
									beneficiarioDTO.setCodigo("002");
									beneficiarioDTO.setMensaje("El beneficiario no cuenta con domicilio");
								}
							}catch(Exception e){
								log.error("el beneficiario no tiene domicilio" ,e);
								beneficiarioDTO.setCodigo("002");
								beneficiarioDTO.setMensaje("El beneficiario no cuenta con domicilio");
							}



							datosAfiliacionBeneficiarioDTO = new DatosAfiliacionBeneficiarioDTO();

							if(integrante.getParentesco() != null){
								datosAfiliacionBeneficiarioDTO.setCveParentesco(integrante.getParentesco().getIdParentesco());
								datosAfiliacionBeneficiarioDTO.setParentesco(integrante.getParentesco().getDescripcion());
							}

							if(integrante.getFechaFinVigencia() != null){
								datosAfiliacionBeneficiarioDTO.setSituacion("Baja");
							}else{
								datosAfiliacionBeneficiarioDTO.setSituacion("Vigente");
							}

							try {
								log.error("busco la prorroga para " + integrante.getAsignacionNSS().getIdAsignacionNSS() + " - " + integrante.getDerechohabiente().getIdPersona());
								TramiteProrroga tramiteProrroga = prorrogaDao.getProrrogaActiva(integrante.getAsignacionNSS().getIdAsignacionNSS(), 
										integrante.getDerechohabiente().getIdPersona(), null);

								if(tramiteProrroga != null){
									datosAfiliacionBeneficiarioDTO.setProrroga(tramiteProrroga.getTipoTramite().getDescripcion());
									datosAfiliacionBeneficiarioDTO.setFecIniVigenca(tramiteProrroga.getFechaInicioProrroga());
									datosAfiliacionBeneficiarioDTO.setFecFinVigencia(tramiteProrroga.getFechaFinProrroga());

									if(tramiteProrroga.getTipoTramite().getIdTipoTramite() == TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo()){
										DitConstanciaEstudio ditConstanciaEstudio = (DitConstanciaEstudio) tramitePersonaFisicaDaoLocal.getDocumentoProbatorioProrroga(tramiteProrroga.getCveIdTramite(), 
												TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo());
										datosAfiliacionBeneficiarioEstDTO = new DatosAfiliacionBeneficiarioEstDTO();
										datosAfiliacionBeneficiarioEstDTO.setCveInstitucionEducativa(ditConstanciaEstudio.getCveEscuela());
										datosAfiliacionBeneficiarioEstDTO.setDetNivelEstudios(ditConstanciaEstudio.getDetalleNivelEducativo() != null ?
												ditConstanciaEstudio.getDetalleNivelEducativo().getDicNivelEducativo().getDesNivelEducativo() : "");
										datosAfiliacionBeneficiarioEstDTO.setFecExpedicion(ditConstanciaEstudio.getFecInicioPeriodo());
										datosAfiliacionBeneficiarioEstDTO.setFecIniCicloEsc(ditConstanciaEstudio.getFecInicioPeriodo());
										datosAfiliacionBeneficiarioEstDTO.setFecFinCicloEsc(ditConstanciaEstudio.getFecFinPeriodo());
										datosAfiliacionBeneficiarioEstDTO.setGradoEscolar(ditConstanciaEstudio.getRefGradoEscolar());
										datosAfiliacionBeneficiarioEstDTO.setInstitucionEducativa(ditConstanciaEstudio.getNomEscuela());
										datosAfiliacionBeneficiarioEstDTO.setNivelEstudios(ditConstanciaEstudio.getDetalleNivelEducativo() != null ?
												String.valueOf(ditConstanciaEstudio.getDetalleNivelEducativo().getDicNivelEducativo().getCveIdNivelEducativo()) : "");
										datosAfiliacionBeneficiarioEstDTO.setNumIncorporacionSEP(ditConstanciaEstudio.getNumIncorporacion());
										beneficiarioDTO.setDatosAfiliacionBeneficiarioEstDTO(datosAfiliacionBeneficiarioEstDTO);
									}
								}
							} catch (Exception e) {
								log.error("ocurrio un error al setear la informacion de la prorroga del beneficiario", e);
							}
							beneficiarioDTO.setDatosAfiliacionBeneficiarioDTO(datosAfiliacionBeneficiarioDTO);
							lstBeneficiariosDTO.add(beneficiarioDTO);
						}
					}else{
						aseguradoDTO = new AseguradoDTO();
						aseguradoDTO.setNss(nss);
						aseguradoDTO.setNombreAsegurado(integrante.getAsignacionNSS().getNombre());
						aseguradoDTO.setPrimerApellidoAsegurado(integrante.getAsignacionNSS().getPrimerApellido());
						aseguradoDTO.setSegundoApellidoAsegurado(integrante.getAsignacionNSS().getSegundoApellido());
						aseguradoDTO.setCurp(integrante.getAsignacionNSS().getCurp());
						aseguradoDTO.setRfc(integrante.getAsignacionNSS().getRfc());
						aseguradoDTO.setFechaNacimientoAsegurado(integrante.getAsignacionNSS().getFechaNacimientoFormateada());
						aseguradoDTO.setCveLugarNacimientoAsegurado(integrante.getAsignacionNSS().getLugarNacimiento().getClave());
						aseguradoDTO.setLugarNacimientoAsegurado(integrante.getAsignacionNSS().getLugarNacimiento().getNombre());
						aseguradoDTO.setCveSexoAsegurado(integrante.getAsignacionNSS().getSexo().getIdSexo().toString());
						aseguradoDTO.setSexoAsegurado(integrante.getAsignacionNSS().getSexo().getDescripcion());
						aseguradoDTO.setIdPersona(integrante.getAsignacionNSS().getIdPersona().intValue());

						datosAseguradoDTO.setAseguradoDTO(aseguradoDTO); 

						datosAseguradoDTO.setCodigo("003");
						datosAseguradoDTO.setMensaje("El asegurado carece de beneficiarios registrados");

						try{
							Domicilio domAse = grupoFamiliarServiceRemote.findDomicilioByIdPersona(integrante.getAsignacionNSS().getIdPersona()).getDomicilio();
							if(domAse != null){
								datosAseguradoDTO.setDomicilioAsegurado(domAse);
							}else{
								aseguradoDTO.setCodigo("002");
								aseguradoDTO.setMensaje("El asegurado no cuenta con domicilio");
							}
						}catch(Exception e){
							log.error("el asegurado no tiene domicilio" ,e);
							aseguradoDTO.setCodigo("002");
							aseguradoDTO.setMensaje("El asegurado no cuenta con domicilio");
						}

						try {
							Persona persona = new Persona();
							TipoPersona tipoPersona = new TipoPersona();
							tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
							persona.setIdPersona(integrante.getAsignacionNSS().getIdPersona());
							persona.setTipoPersona(tipoPersona);

							List<MedioContacto> listaMedios = mediosContactoServiceBusinessRemote.consultarMedioDeContactoPersona(persona);

							for (MedioContacto medio: listaMedios) {
								medioContactoDTO = new MedioContactoDTO();
								medioContactoDTO.setTipoMedioContacto(medio.getTipoMedioContacto());
								medioContactoDTO.setDescMedioContacto(medio.getDesFormaContacto());
								listaMediosContacto.add(medioContactoDTO);
							}
							datosAseguradoDTO.setLstMediosContactoAseguradoDTO(listaMediosContacto);
						} catch (PersonaSinMedioDeContactoException e) {
							e.printStackTrace();
						}
					}
				}
				
				if(lstBeneficiariosDTO.isEmpty()){
					datosAseguradoDTO.setCodigo("003");
					datosAseguradoDTO.setMensaje("El asegurado carece de beneficiarios registrados");
				}else{
					datosAseguradoDTO.setLstBeneficiariosDTO(lstBeneficiariosDTO);
				}
				
			}else{
				datosAseguradoDTO.setCodigo("001");
				datosAseguradoDTO.setMensaje("No se encontró el NSS solicitado");
			}
		}catch(Exception e){
			log.error("ocurrio un error no cachado en el servicio", e);
			datosAseguradoDTO.setCodigo("004");
			datosAseguradoDTO.setMensaje(e.getMessage());

		}
		return datosAseguradoDTO;
	}

}
