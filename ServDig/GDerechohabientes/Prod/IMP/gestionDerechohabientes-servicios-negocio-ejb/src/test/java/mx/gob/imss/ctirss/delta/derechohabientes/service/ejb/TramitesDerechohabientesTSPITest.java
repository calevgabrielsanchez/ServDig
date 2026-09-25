package mx.gob.imss.ctirss.delta.derechohabientes.service.ejb;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;

import mx.gob.imss.ctirss.delta.derechohabientes.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.TramitesDerechohabientesTSPIRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.TramiteDerechoabienteTSPIException;
import mx.gob.imss.ctirss.delta.exception.individuo.SolicitudException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.RazonCancelacionEnum;
import mx.gob.imss.ctirss.delta.model.enums.VarianteRegistroEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.RazonResultadoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

public class TramitesDerechohabientesTSPITest {
	
	private static final Logger LOG;
	private static final TramitesDerechohabientesTSPIRemote serviceTSPI;
	private static final DomicilioServiceBusinessRemote  domicilioServiceBusinessRemote;
	private static final SolicitudBusinessRemote solicitudBusinessRemote;
	private static final GrupoFamiliarServiceRemote grupoFamiliarService;
	
	static {
		LOG = Logger.getLogger(TramitesDerechohabientesTSPITest.class);
		serviceTSPI  = EjbLocator.getTramitesDerechohabientesTSPIService();
		domicilioServiceBusinessRemote = EjbLocator.getDomicilioServiceBusinessRemote();
		solicitudBusinessRemote = EjbLocator.getSolicitudBusiness();
		grupoFamiliarService = EjbLocator.getGrupoFamiliarService();
	}
	
	@Test
	public void validaPrerrequisitosTramiteRegistroTest(){
		long idAsignacionNSS = 13820691;
		//long idAsignacionNSS = 1774354;
		
		
       
			try {
				/*
				CabezaGrupoFamiliar cabeza =  serviceTSPI.validaPrerrequisitosAseguradoRegistro(idAsignacionNSS);
				if(cabeza != null){
					System.out.println("la cabeza no es nula " +  cabeza.toString());
					LOG.debug("la cabeza no es nula " + cabeza.getCalidadParentesco().getIdParentesco());
				}*/
				
				  System.out.println("validamos FURM ");
			        Long asignacionNSS = 13820691L;
			        CabezaGrupoFamiliar cabezaGrupoFamiliar = serviceTSPI.validaPrerrequisitosAseguradoRegistro(asignacionNSS);
			        Fisica fisicaValidada;
			        // 2. Buscar la persona a incluir como beneficiario
			        System.out.println("entramos a validar persona con curp");
			        fisicaValidada = serviceTSPI.busquedaFisicaPorCuprTramiteRegistroTCPI("OAGU130118HDFCNLA1");
			        // 2.5 Validar que no exista en el grupo familiar en el grupo familiar en este grupo familiar.
			        Fisica validaPersonaRegistradaEnGrupoFamiliar = serviceTSPI.validaPersonaRegistradaEnGrupoFamiliar(fisicaValidada, asignacionNSS);
			        // 3. Crear el tramite de registro
			        TramiteRegistroDerechohabiente trd = new TramiteRegistroDerechohabiente();
			        //List<RazonRegistro> listaRazonRegistro = serviceTSPI.getListaRazonRegistro(null, ParentescoEnum.HIJOS.getId());
			        Domicilio domicilio = new Domicilio();
			        domicilio.setClave(000001);
			        Domicilio dom = domicilioServiceBusinessRemote.consultarDomicilio(domicilio);

			        trd.setParentesco(new Parentesco());
			        trd.setDatosAsegurado(new AsignacionNSS());
			        trd.getDatosAsegurado().setSexo(new Sexo(1));
			        trd.setDomicilio(dom);
			        trd.setMedicoEnTurno(new MedicoEnTurno());
			        //sacado de la validacion
			        trd.getDatosAsegurado().setCveIdAsignacionNSS(asignacionNSS);
			        trd.getDatosAsegurado().setIdAsignacionNSS(idAsignacionNSS);
			        trd.getDatosAsegurado().setIdPersona(13674239L);
			        trd.setFisica(fisicaValidada);
			        trd.getParentesco().setIdParentesco(ParentescoEnum.HIJOS.getId());
			        trd.getMedicoEnTurno().setIdMedicoContultorioTurno(12409L);
			        trd.setVarianteRegistro(VarianteRegistroEnum.RECONOCIMIENTO.getId());
			        RazonRegistro razon = new RazonRegistro();
			        		
			        razon.setIdRazonRegistro(1L);
			        trd.setRazonRegistro(razon);
			        
			        Solicitud solicitud = serviceTSPI.guardaSolicitudRegisroDerechohabiente(trd, cabezaGrupoFamiliar, OrigenSolicitudEnum.INTERNET.getId());
			} catch (TramiteDerechoabienteTSPIException e) {
				LOG.error("eror de tipo TramiteDerechoabienteTSPIException", e);
				
			} catch (DerechohabientesBusinessException e) {
				LOG.error("eror de tipo DerechohabientesBusinessException", e);
				
			} catch (Exception e) {
				LOG.error("eror de tipo Exception", e);
				
			}
		
		
	}
	
	@Test
	public void validaAntecedentesGrupoFamiliar(){
		LOG.debug("inicia el proceso");
		System.out.println("iniciando");
		Fisica fisica = null;
		Long idAsignacion = new Long(13820691);
		String curp ="MASJ970225HQTRNL01";
		
		try {
			fisica = serviceTSPI.busquedaFisicaPorCuprTramiteRegistroTCPI(curp);
			System.out.println("paso la consulta fisica es " + fisica.toString());
			fisica = serviceTSPI.validaPersonaRegistradaEnGrupoFamiliar(fisica, idAsignacion);
			System.out.println("fisica despues de validar en grupo " + fisica.toString());
			LOG.debug("la fisica es" + fisica.toString());
			
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (TramiteDerechoabienteTSPIException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClienteWebserviceRenapoCurpException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
    public void cancelarSolicitud() {
        System.out.println("cancelar Solicitud");
        Long idSolicitud = 747745020000000L;
        Long idrazonRechazo = RazonResultadoEnum.SOLICITUD_CANCELADA.getCodigo().longValue();
        Long idRazonCancelacion = RazonCancelacionEnum.PETICION_DERECHOHABIENTE.getId();
        String usuario = "FURM620909HGTNMG06";
        String observaciones = null;// "ObservacionesBaja";

        try {
            solicitudBusinessRemote.cancelarSolicitud(idSolicitud, idrazonRechazo, idRazonCancelacion, usuario, observaciones);
            //solicitudBusinessRemote.cancelarSolicitud(null, null, null, null, null);
        } catch (SolicitudException ex) {
            System.out.println("ex " + ex);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

	
	@Test
	public void guardaProrrogaTSPI() {
		try {
            Long idAsignacion = 6139582L;
            Long idPersonaBeneficiario = 249451312L;

            GrupoFamiliar grupoFamiliar =  grupoFamiliarService.getIntegranteGrupoFamiliarPorIdPersona(idAsignacion, idPersonaBeneficiario);
            TipoTramite tipoTramite = new TipoTramite();
            tipoTramite.setIdTipoTramite(TipoTramiteEnum.PRORROGA_POR_ESTUDIOS.getCodigo());

            TramiteProrroga tramiteProrroga = new TramiteProrroga();
            tramiteProrroga.setTipoTramite(tipoTramite);
            tramiteProrroga.setGrupoFamiliar(grupoFamiliar);
            tramiteProrroga.setIdAsignacionNSS(idAsignacion);
            tramiteProrroga.setObservaciones("Prueba");
            tramiteProrroga.setFechaInicioProrroga(new Date());
            tramiteProrroga.setFechaFinProrroga(new Date());

            Usuario usuario = new Usuario();
            usuario.setUsuario("FURM620909HGTNMG06");

            Solicitud solicitud2 = serviceTSPI.guardaProrrogaTSPI(tramiteProrroga, usuario, OrigenSolicitudEnum.VENTANILLA_TSPI.getId());
            
            System.out.println("el tramite quedo como [" + solicitud2.getTramites().get(0).getTipoTramite().getDescripcion() +"]");

        } catch (Exception ex) {
        	 System.out.println("ex " + ex.getMessage());
            ex.printStackTrace();
        }

		
		
	}
	
	

}
