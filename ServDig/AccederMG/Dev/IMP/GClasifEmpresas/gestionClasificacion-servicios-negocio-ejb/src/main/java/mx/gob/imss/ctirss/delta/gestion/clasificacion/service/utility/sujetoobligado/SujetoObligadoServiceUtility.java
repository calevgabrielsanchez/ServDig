/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: SujetoObligadoServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.sujetoobligado
 *  @Fecha: 04/06/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.sujetoobligado;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DgAsentamiento;
import mx.gob.imss.ctirss.delta.persistence.DicDivision;
import mx.gob.imss.ctirss.delta.persistence.DicFraccion;
import mx.gob.imss.ctirss.delta.persistence.DicGrupo;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitPatSujObligDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;


@Stateless(name="sujetoObligadoUtility", mappedName="sujetoObligadoUtility")
public class SujetoObligadoServiceUtility extends AbstractServiceUtility implements SujetoObligadoServiceUtilityLocal {

	@Override
	public SujetoObligado convertEntityToModel(DitPatronSujetoObligado entity, int idTipoPersona)
			throws IllegalAccessException, InvocationTargetException{
		log.info("convertEntityToModel [" + entity +"]");
		SujetoObligado model = new SujetoObligado();
		model.setCveIdSujetoObligado(entity.getCveIdPatronSujetoObligado());

		int tipoPersona = idTipoPersona;
		log.info("Tipo de persona [" + tipoPersona +"]");
		
		if(tipoPersona==TipoPersona.TIPO_PERSONA_FISICA){
			log.info("Persona fisica");
			Fisica fisica=new Fisica();
			//fisica.setNombre(entity.getDitPersonaFisica().getNombreComercial());
			fisica.setCurp(entity.getDitPersonaFisica().getDitPersona().getCurp());
			fisica.setRfc(entity.getDitPersonaFisica().getDitPersona().getRfc());
			model.setFisica(fisica);
		}else{
			log.info("Persona moral");
			Moral moral=new Moral();
			//moral.setNombreComercial(entity.getDitPersonaMoral().getNombreComercial());
			moral.setRfc(entity.getDitPersonaMoral().getRfc());
			model.setMoral(moral);
		}
		model.setNombreComercial(entity.getNombreComercial());
		log.info("::: VALIDA SI ES NULO EL DOMICILIO :::");
		if(entity.getDitPatSujObligDomicilios() != null){
			log.info("No es nulo");
			for (DitPatSujObligDomicilio domicilio : entity.getDitPatSujObligDomicilios()) {
				log.info("domicilio.getDicTipoDomicilio().getCveIdTipoDomicilio() ... " + domicilio.getDicTipoDomicilio().getCveIdTipoDomicilio());
				if(domicilio.getDicTipoDomicilio().getCveIdTipoDomicilio()
						== TipoDomicilioEnum.CENTRO_TRABAJO.getCodigo()){
					DicSubdelegacion dicSubdelegacion = 
						domicilio.getDgDomicilioGeografico().getDgCatLocalidad().getDgCatMunicipio()
							.getDitMunicipioImssInegis().iterator().next().getDicMunicipioImss().getDitMunicipioSubdelegacions()
								.iterator().next().getDicSubdelegacion();
					
					DomicilioFiscal domicilioFiscal=new DomicilioFiscal();
					Asentamiento asentamiento=new Asentamiento();
					Localidad localidad=new Localidad();
					DgAsentamiento dgAsentamiento=new DgAsentamiento();
					dgAsentamiento=domicilio.getDgDomicilioGeografico().getDgAsentamiento();
					asentamiento.setNombre(dgAsentamiento.getNomAsen());
					localidad.setNombre(domicilio.getDgDomicilioGeografico().getDgCatLocalidad().getNomLoc());
					asentamiento.setLocalidad(localidad);
					domicilioFiscal.setAsentamiento(asentamiento);
					model.setDomicilioFiscal(domicilioFiscal);
										
					Subdelegacion subdelegacion = new Subdelegacion();
					subdelegacion.setId(dicSubdelegacion.getCveIdSubdelegacion());
					subdelegacion.setDescripcion(dicSubdelegacion.getDesSubdelegacion());
					Delegacion delegacion = new Delegacion();
					delegacion.setId(dicSubdelegacion.getDicDelegacion().getCveIdDelegacion());
					delegacion.setDescripcion(dicSubdelegacion.getDicDelegacion().getDesDeleg());
					subdelegacion.setDelegacion(delegacion);
					model.setSubdelegacion(subdelegacion);
				}
			}
		}

		Clasificacion clasificacion = new Clasificacion();
		Fraccion fraccion=new Fraccion();
		Grupo grupo=new Grupo();
		Division division=new Division();
		
		clasificacion.setIndRegPatClase(0);
		for (DitClasificacion clasif : entity.getDitClasificacions()) {
//			if(clasif.getIndRegPatClase() != null){
//				if(clasif.getIndRegPatClase().equals(1)){
                    DicFraccion dicFraccion = clasif.getDicFraccionClase().getDicFraccion();
					fraccion.setId(dicFraccion.getCveIdFraccion());
					fraccion.setNumFraccion(dicFraccion.getNumFraccion());
					fraccion.setDescripcion(dicFraccion.getDesFraccion());
					
                    DicGrupo dicGrupo = dicFraccion.getDicGrupo();
					grupo.setId(dicGrupo.getCveIdGrupo());
					grupo.setNumGrupo(dicGrupo.getNumGrupo());
					
                    DicDivision dicDivision = dicGrupo.getDicDivision();
					division.setId(dicDivision.getCveIdDivision());
					division.setNumDivision(dicDivision.getNumDivision());
					
					grupo.setDivision(division);
					fraccion.setGrupo(grupo);
					
					clasificacion.setId(clasif.getCveIdClasificacion());
					clasificacion.setIndRegPatClase(1);
					clasificacion.setFraccion(fraccion);
//				}
//			}
		}
		model.setClasificacion(clasificacion);

		return model;
	}
	
	@Override
	public TramiteSujetoObligado obtenerTramiteSujetoObligado(List<Tramite> tramites, Long tipoSolicitud){

		TramiteSujetoObligado tramiteSO = null;

		//si la solicitud no es de alta patronal
		//se espera que solo venga un solo tramite en la solicitud
		if(tipoSolicitud.intValue() == 60) {//Dictamen
			for(Tramite tramite : tramites) {
				if(tramite.getTipoTramite().getIdTipoTramite().intValue() == 167) {
					tramiteSO = (  (TramiteSujetoObligado) tramite );
					break;
				}
			}
		} else if( tipoSolicitud.intValue()  != TipoSolicitudEnum.ALTA_PATRONAL.getValor().intValue() ){
//			tramiteSO = (  (TramiteSujetoObligado) tramites.get(0)  );
			
		 	Tramite tramite = null;
			if(tramites != null){
				for (int x = 0; x < tramites.size(); x++) {
					tramite = tramites.get(x);
					Long idTipoTramite = tramite.getTipoTramite().getIdTipoTramite().longValue();
					if(idTipoTramite.equals(TipoTramiteEnum.ACTUALIZACION_CENTRO_TRABAJO.getCodigo().longValue())	
							|| idTipoTramite.equals(TipoTramiteEnum.ACTIVIDAD_ECONOMICA.getCodigo().longValue())
							|| idTipoTramite.equals(TipoTramiteEnum.DISPOSICION_DE_LEY.getCodigo().longValue())
							|| idTipoTramite.equals(TipoTramiteEnum.INCORPORACION_DE_ACTIVIDADES.getCodigo().longValue())
							|| idTipoTramite.equals(TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo().longValue())
							|| idTipoTramite.equals(TipoTramiteEnum.COMODATO.getCodigo().longValue())
							|| idTipoTramite.equals(TipoTramiteEnum.ENAJENACION.getCodigo().longValue())
							|| idTipoTramite.equals(TipoTramiteEnum.ARRENDAMIENTO.getCodigo().longValue())
							|| idTipoTramite.equals(TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo().longValue())
							|| idTipoTramite.equals(TipoTramiteEnum.ESCISION.getCodigo().longValue())
							|| idTipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL.getCodigo().longValue())
							|| idTipoTramite.equals(TipoTramiteEnum.FUSION.getCodigo().longValue())
							|| idTipoTramite.equals(TipoTramiteEnum.REANUDACION_DE_ACTIVIDADES.getCodigo().longValue())
							|| idTipoTramite.equals(TipoTramiteEnum.SUSTITUCION_PATRONAL_SUBCONTRATACION.getCodigo().longValue())							
							|| idTipoTramite.equals(TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo().longValue())
					) {					
						tramiteSO = (TramiteSujetoObligado) tramites.get(x);
						break;
					}					
				}
			}					
			
		}else{
			
			//si la solicitud es de alta patronal se espera que venga mas de un tramite
			//se realiza la busqueda del tramite de alta para obtener el sujeto obligado
			
		 	Tramite tramite = null;
			if(tramites != null){
				for (int x = 0; x < tramites.size(); x++) {
					tramite = tramites.get(x);
					if(tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue()
							|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue()){
						tramiteSO = (TramiteSujetoObligado) tramites.get(x);
						break;
					}
				}
			}		
		}
		
		return tramiteSO;
	}
	
	@Override
	public Tramite obtenerTramite(List<Tramite> tramites, Long tipoSolicitud){

		Tramite tramite = null; 
		
		if(tramites != null){
			if( tipoSolicitud.intValue()  != TipoSolicitudEnum.ALTA_PATRONAL.getValor().intValue() ){
				tramite = tramites.get(0);
		 	}else{
				for (int x = 0; x < tramites.size(); x++) {				
					tramite = tramites.get(x);		
					if(tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue()
							|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue())
						break;
					else
						tramite = null;
				}
		 	}		 	
		}
		
		return tramite;
	}
	
	
}
