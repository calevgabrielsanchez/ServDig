package mx.gob.imss.ctirss.delta.gestion.beneficio.service.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.entity.BeneficioServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.BeneficioRissServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.interfaces.CancelarBeneficioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.utility.BeneficioServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.utility.BeneficiosConstants;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.EncolarMovimientoRissBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ParametrosServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.CancelacionBeneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.ReglasCancelacionBeneficio;
import mx.gob.imss.ctirss.delta.model.beneficio.RespuestaCancelacionBeneficio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.MotivoCancelacionBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParametroSistemaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;

import org.springframework.util.CollectionUtils;

@Stateless(name="cancelarBeneficioServiceBusiness" ,mappedName="cancelarBeneficioServiceBusiness")
public class CancelarBeneficioServiceBusiness extends AbstractServiceBusiness
	implements CancelarBeneficioServiceBusinessRemote, CancelarBeneficioServiceBusinessLocal {

	@EJB
	private BeneficioServiceEntityLocal beneficioServiceEntity;
	@EJB
	private BeneficioRissServiceBusinessRemote beneficioRissServiceBusiness;
	@EJB
	private PersonaBusinessRemote personaBusiness;
	@EJB
	private BeneficioServiceUtilityLocal beneficioServiceUtility;
	@EJB
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@EJB
	private EncolarMovimientoRissBusinessRemote encolarMovimientoRissBusiness;
    @EJB
    private ParametrosServiceBusinessRemote parametrosServiceBusinessRemote;
    
	@Override
	public RespuestaCancelacionBeneficio cancelarBeneficio(String rfc, String nrp, 
			String nss,	String fechaBajaRIF, int indicadorInstitucion){
		log.debug("RFC["+rfc+"], NRP["+nrp+"], NSS["+nrp+"], " +
			"FechaBaja["+fechaBajaRIF+"], indicadorInstitucion["+indicadorInstitucion+"] ");		
		SimpleDateFormat sdf = new SimpleDateFormat(BeneficiosConstants.FORMAT_DATE_GUINMEDIO_dd_MM_yyyy);		
		Date dtFechaBaja=null;
		RespuestaCancelacionBeneficio respuesta = new RespuestaCancelacionBeneficio();	
				
		try {
			dtFechaBaja = sdf.parse(fechaBajaRIF);
		} catch (ParseException e1) {
			respuesta.setExito(30);
			respuesta.setClaveError(30);
			respuesta.setDescripcion(e1.getMessage());
			return respuesta;
		}
		
		CancelacionBeneficio cancelacionBeneficio = beneficioServiceUtility
			.obtenerTipoMotivoCancelacion(rfc, nrp, nss, indicadorInstitucion);
		
		if(cancelacionBeneficio.getCveOperacion()!=null){
			//No se cumplen las reglas de negocio de cancelacion.
			respuesta.setExito(-1);
			respuesta.setClaveError(cancelacionBeneficio.getCveOperacion());
			respuesta.setDescripcion(cancelacionBeneficio.getDescripcionOperacion());
		}else{
			try{
				List<Beneficio> listaBeneficios = new ArrayList<Beneficio>();
				RespuestaCancelacionBeneficio respuestaSinDatos = new RespuestaCancelacionBeneficio();
				respuestaSinDatos.setExito(-1);
				respuestaSinDatos.setClaveError(04);
				respuestaSinDatos.setDescripcion("No se encontraron beneficios para cancelar con los datos proporcionados");
				
				if(cancelacionBeneficio.getIdRNCancelacion()
					.equals(ReglasCancelacionBeneficio.RN_CANCELA_BENEFICIO_TODO_SAT_RFC.getClave())){
						//SAT
						//RN1 Cancelar beneficios como persona y patron por RFC				
						listaBeneficios.addAll(obtenerBeneficiosAsociadosRFC(rfc, false));
					
				}else if(cancelacionBeneficio.getIdRNCancelacion()
					.equals(ReglasCancelacionBeneficio.RN_CANCELA_BENEFICIO_TODO_INFONAVIT_NRP.getClave()) ||
					cancelacionBeneficio.getIdRNCancelacion()
					.equals(ReglasCancelacionBeneficio.RN_CANCELA_BENEFICIO_PERSONA_INFONAVIT_NSS.getClave())){
					//INFONAVIT
					if(cancelacionBeneficio.getIdRNCancelacion()
						.equals(ReglasCancelacionBeneficio.RN_CANCELA_BENEFICIO_TODO_INFONAVIT_NRP.getClave())){
						
						//RN2 Cancelar beneficios como persona y patron por NRP						
						listaBeneficios.addAll(obtenerBeneficiosAsociadosNRP(nrp, false));
                        Fisica fisica = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss);
                        listaBeneficios.addAll(obtenerBeneficiosAsociadosPersona(fisica.getIdPersona()));
						
					}else{
						//RN3 Cancelar beneficios como persona por NSS
						Fisica fisica = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss);	
						listaBeneficios.addAll(obtenerBeneficiosAsociadosPersona(fisica.getIdPersona()));						
					}
				}else{
					//IMSS
					if(cancelacionBeneficio.getIdRNCancelacion()
						.equals(ReglasCancelacionBeneficio.RN_CANCELA_BENEFICIO_TODO_IMSS_NRP.getClave())){
						
						//RN4 Cancelar beneficios como persona y patron por NRP
						listaBeneficios.addAll(obtenerBeneficiosAsociadosNRP(nrp, false));
						Fisica fisica = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss);
						listaBeneficios.addAll(obtenerBeneficiosAsociadosPersona(fisica.getIdPersona()));

					}else{
						//RN5 Cancelar beneficios como persona por NSS
						Fisica fisica = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss);	
						listaBeneficios.addAll(obtenerBeneficiosAsociadosPersona(fisica.getIdPersona()));						
					}
				}

				if(!CollectionUtils.isEmpty(listaBeneficios)){
					//Cancelar beneficio en BD y toda su relacion.
					beneficioServiceEntity.cancelarBeneficiosRiss(
						listaBeneficios, 
						getEstadosViablesCancelacionBeneficios(), 
						dtFechaBaja, 
						MotivoCancelacionBeneficioEnum.obtenerEnumById(
							cancelacionBeneficio.getMotivoCancelacion()),
						getEstadoBeneficioCancelado());
					
					String patronGeneral = parametrosServiceBusinessRemote
						.obtenerParametroDeConfiguracion(ParametroSistemaEnum.PATRON_RISS_NSS.getCodigo());					
					//Encolar movimientos de baja.
					encolarMovimientoRissBusiness.encolarMovimientosRiss(
						beneficioServiceEntity.prepararLayoutMovimientoBajaRiss(
							listaBeneficios, indicadorInstitucion, dtFechaBaja, patronGeneral));
					
					respuesta.setExito(0);
					respuesta.setClaveError(10);
					respuesta.setDescripcion("El beneficio a sido cancelado satisfactoriamente");
				}else{
					respuesta.setExito(respuestaSinDatos.getExito());
					respuesta.setClaveError(respuestaSinDatos.getClaveError());
					respuesta.setDescripcion(respuestaSinDatos.getDescripcion());
				}
			} catch (AbstractException e) {
				//Error durate el proceso
				respuesta.setExito(-1);
				respuesta.setClaveError(02);
				respuesta.setDescripcion("Error al cancelar beneficio: "+e.getMessage());
			}
		}
		return respuesta;
	}
	
	private List<Beneficio> obtenerBeneficiosAsociadosRFC(String rfc, boolean soloBeneficiosPersona){
		List<Beneficio>  listaBeneficios = new ArrayList<Beneficio>();
		List<Fisica> listaPersonaFisica = personaBusiness
			.buscarPersonaFisicaPorRfcEnImss(rfc);			
		if(!CollectionUtils.isEmpty(listaPersonaFisica)){
			listaBeneficios.addAll(beneficioServiceEntity.obtenerBeneficiosPorIdsPersonas(
				beneficioServiceUtility.getIdsPersonasFisicas(listaPersonaFisica), 
				beneficioServiceUtility.getBeneficioEstadoActivo()));
		}
		if(!soloBeneficiosPersona){
			List<Beneficio> listaBeneficiosSO = obtenerBeneficiosRfcPorSO(rfc);
			if(!CollectionUtils.isEmpty(listaBeneficiosSO)){
				listaBeneficios.addAll(listaBeneficiosSO);
			}
		}		
		return listaBeneficios;
	}
	
	private List<Beneficio> obtenerBeneficiosAsociadosPersona(Long idPersona){
		List<Beneficio>  listaBeneficios = new ArrayList<Beneficio>();
		List<Long> idsPersona = new ArrayList<Long>();
		idsPersona.add(idPersona);
		List<Beneficio>  listaBeneficiosPersona =  beneficioServiceEntity.obtenerBeneficiosPorIdsPersonas(
			idsPersona, beneficioServiceUtility.getBeneficioEstadoActivo());
		if(!CollectionUtils.isEmpty(listaBeneficiosPersona)){
			listaBeneficios.addAll(listaBeneficiosPersona);
		}
		return listaBeneficios;
	}
	
	private List<Beneficio> obtenerBeneficiosAsociadosNRP(String nrp, boolean soloBeneficiosPersona)
		throws BeneficioRissException{
		List<Beneficio>  listaBeneficios = new ArrayList<Beneficio>();		
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(nrp);
		sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);

		if(!soloBeneficiosPersona){
			List<Beneficio> listaBeneficiosPatron = beneficioRissServiceBusiness
				.obtenerBeneficiosPorNRP(nrp, true);
			if(!CollectionUtils.isEmpty(listaBeneficiosPatron)){
				listaBeneficios.addAll(listaBeneficiosPatron);
			}
		}
		return listaBeneficios;
	}
	
	
	private List<Beneficio> obtenerBeneficiosRfcPorSO(String rfc){
		List<SujetoObligado> listaSujetosObligados = new ArrayList<SujetoObligado>();
		try {
			SujetoObligado so = new SujetoObligado();
			so.setFisica(new Fisica());
			so.getFisica().setRfc(rfc);
			so.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
			listaSujetosObligados = sujetoObligadoServiceBusiness.obtenerDetalleSujetoObligado(so);
		} catch (GestionPatronalBusinessException e) {
			log.error(e);
			e.printStackTrace();			
		}
		if(CollectionUtils.isEmpty(listaSujetosObligados)){
			return null;
		}
		return beneficioServiceEntity.obtenerBeneficiosPorIdsSujetosObligados(
			beneficioServiceUtility.getIdsSujetosObligados(listaSujetosObligados), 
				beneficioServiceUtility.getBeneficioEstadoActivo());
	}
	
	private List<Integer> getEstadosViablesCancelacionBeneficios(){
		List<Integer> estadosBeneficios = new ArrayList<Integer>();
		estadosBeneficios.add(EstadoBeneficioEnum.ACTIVO.getClave());
		return estadosBeneficios;
	}
	
	private int getEstadoBeneficioCancelado(){
		return EstadoBeneficioEnum.CANCELADO.getClave();
	}
	
}
