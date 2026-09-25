package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.SolicitudDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ReglasNegocioServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoTramite;

@Stateless( name = "reglasNegocioService", mappedName = "reglasNegocioService")
public class ReglasNegocioService implements
	ReglasNegocioServiceRemote {

	@EJB GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB SolicitudDaoLocal solicitudDaoLocal;
	
	
	/**
	 * Precondiciones
	 */
	public static final long PARENTESCO_ASEGURADO = 1;
	public static final long PARENTESCO_PENSIONADO = 2;
	public static final long PARENTESCO_CONYUGUE = 3;
	public static final long PARENTESCO_CONCUBINA = 4;
	public static final long PARENTESCO_PADRES = 5;
	public static final long PARENTESCO_HIJOS = 6;
	
	public static final long ESTADO_VIGENTE = 1;
	public static final long ESTADO_CON_DERECHO = 2;
	public static final long ESTADO_CONSERVACION_DERECHOS = 3;
	
	public static final long TIPO_TRAMITE_MODIFICACION = 1;
	
	
	/**
	 * Revisa las siguientes precondiciones:
	 * 
	 * * Derechohabiente Vigente
	 * 
	 * * Vigencia de la cabeza del grupo familiar
	 * 
	 * * Tipo de trámite Valido.
	 *  
	 * @throws DerechohabientesBusinessException 
	 */
	
	public Boolean revisaPreCondiciones(GrupoFamiliar derechohabiente, TipoTramite tipoTramite) throws DerechohabientesBusinessException
	{
		
		Boolean cumplePrecondiciones = false;
		
		//Derechohabiente Vigente
				
		if( isDerechohabienteVigente(derechohabiente) ){
			//Vigencia de la cabeza del Grupo Familiar
						
			if(isCabezaGrupoFamiliarVigente(derechohabiente)){
				
				//Tipo de tramite valido
				if(isTipoTramiteValido(tipoTramite)){
					cumplePrecondiciones = true;
				}
				
			}
		}
		if(!cumplePrecondiciones){
			throw new DerechohabientesBusinessException("Derechohabiente no vigente."); 
		}
		
		return cumplePrecondiciones;
	}
	
	
	
	
	/**
	 * Regla de negocio RNGD0001
	 * Los tipos de derechohabientes son:
	 *	Asegurado
	 *	Pensionado
	 *	Beneficiario
	 * @return
	 */
	public Boolean RNGD0001_validaTipoDerechohabiente(GrupoFamiliar derechohabiente) throws DerechohabientesBusinessException{
		Boolean tipoDerechohabienteValido = false;
		if(derechohabiente.getParentesco().getIdParentesco() == PARENTESCO_ASEGURADO){
			tipoDerechohabienteValido =  true;
		}else if(derechohabiente.getParentesco().getIdParentesco() == PARENTESCO_PENSIONADO){
			tipoDerechohabienteValido =  true;
		}else if(derechohabiente.getParentesco().getIdParentesco() == PARENTESCO_CONYUGUE ||
				 derechohabiente.getParentesco().getIdParentesco() == PARENTESCO_CONCUBINA ||
				 derechohabiente.getParentesco().getIdParentesco() == PARENTESCO_HIJOS ||
				 derechohabiente.getParentesco().getIdParentesco() == PARENTESCO_PADRES){
			tipoDerechohabienteValido =  true;
		}
		
		if(!tipoDerechohabienteValido){
			throw new DerechohabientesBusinessException("El tipo de derechohabiente no es válido."); 
		}
		
		return tipoDerechohabienteValido;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Valida que el tipo de tramite sea Modificacion
	 */
	private Boolean isTipoTramiteValido(TipoTramite tipoTramite){
		if(tipoTramite.getIdTipoTramite() == TIPO_TRAMITE_MODIFICACION)
			return true;
		else
			return false;
	}
	
	/**
	 * Valida la vigencia de la cabeza del grupo familiar
	 */
	
	private Boolean isCabezaGrupoFamiliarVigente(GrupoFamiliar derechohabiente){
		Boolean cabezaFamiliarVigente = false;
		if (derechohabiente.getParentesco().getIdParentesco() == PARENTESCO_ASEGURADO &&
			(derechohabiente.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == ESTADO_VIGENTE ||
					derechohabiente.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == ESTADO_CON_DERECHO	||
							derechohabiente.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == ESTADO_CONSERVACION_DERECHOS)	){
			cabezaFamiliarVigente = true;
		}
		
		return cabezaFamiliarVigente;
	}
	
	/**
	 * Valida si el derechohabiente es vigente
	 */
	private Boolean isDerechohabienteVigente(GrupoFamiliar derechohabiente){
		
		Boolean derechohabienteVigente = false;
		//Derechohabiente Asegurdo Vigente
		if(derechohabiente.getParentesco().getIdParentesco() == PARENTESCO_ASEGURADO &&
				derechohabiente.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == ESTADO_VIGENTE	){
			derechohabienteVigente = true;
		}
		
		//Derechohabiente Pensionado Vigente
		if(derechohabiente.getParentesco().getIdParentesco() == PARENTESCO_PENSIONADO &&
				derechohabiente.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == ESTADO_CON_DERECHO	){
			derechohabienteVigente = true;
		}
		
		//Derechohabiente Pensionado Vigente
		if(derechohabiente.getParentesco().getIdParentesco() == PARENTESCO_CONYUGUE &&
				derechohabiente.getEstadoDerechohabiente().getIdEstadoDerechohabiente() == ESTADO_VIGENTE	){
			derechohabienteVigente = true;
		}
		
		return derechohabienteVigente;
		
	}
	
	/**
	 * Valida si el integrante del grupo familiar es candidato a corrección.
	 */
	public Boolean candidatosACorreccionPorPerfil(){
		//TODO
		return null;
	}
	
	
}
