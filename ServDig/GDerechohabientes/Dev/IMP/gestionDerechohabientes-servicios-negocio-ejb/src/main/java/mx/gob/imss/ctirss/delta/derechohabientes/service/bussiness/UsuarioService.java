package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.UsuarioDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UsuarioServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioOrdinario;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;

import org.apache.log4j.Logger;

/**
 * @author Juan Manuel Marquez 
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 11/04/2012
 */
@Stateless( name = "usuarioService", mappedName = "usuarioService")
public class UsuarioService implements UsuarioServiceRemote {


	private static final Logger logger = Logger.getLogger(UsuarioService.class);
	
	@EJB
	private UsuarioDaoLocal usuarioDao;
	
	@EJB GrupoFamiliarServiceRemote grupoFamiliarService;

	
	private UsuarioFuncionario getUsuarioFuncionario(long idUsuario) throws DerechohabientesBusinessException,Exception {
		UsuarioFuncionario miUsuarioFuncionario = usuarioDao.getUsuarioFuncionario(idUsuario);
		return miUsuarioFuncionario;
	}

	
	private UsuarioOrdinario getUsuarioOrdinario(long idUsuario) throws DerechohabientesBusinessException,Exception {
		UsuarioOrdinario miUsuarioOrdinario = usuarioDao.getUsuarioOrdinario(idUsuario);
		return miUsuarioOrdinario;
	}

	@Override
	public Usuario getUsuario(String nomUsuario, String password) throws DerechohabientesBusinessException,Exception {		
		long perfil;	
		long idPersona;
		Usuario miUsuario =null;
		CabezaGrupoFamiliar miCGF = null;
		GrupoFamiliar miGrupoFamiliar = null;
		AsignacionNSS asigNss = null;

		boolean vigente = false;
		
		
		miUsuario = usuarioDao.getUsuario(nomUsuario);

		if(miUsuario != null){
			if(miUsuario.getPassword().trim().equals(password)){
								
				perfil =  miUsuario.getPerfilUsuario().getIdPerfilUsuario();
				idPersona = miUsuario.getFisica().getIdPersona();
				asigNss = usuarioDao.getAsignacionNss(idPersona);
				
				if(perfil == PerfilesEnum.TRAMITADOR.getId() || perfil == PerfilesEnum.AUTORIZADOR.getId()){

					UsuarioFuncionario miUsuarioFun = getUsuarioFuncionario(new Long(miUsuario.getCveIdUsuario()).longValue());
					if(miUsuarioFun != null){
						miUsuario.setUsuarioFuncionario(miUsuarioFun);
						miUsuario.setIdUmf(miUsuarioFun.getUnidadMedicaFamiliar() != null ? miUsuarioFun.getUnidadMedicaFamiliar().getIdUMF() : null);
					}else{
						throw new DerechohabientesBusinessException(ExceptionMessages.USUARIO_INVALIDO);
					}					
				}else if(perfil == PerfilesEnum.CONYUGE.getId() || perfil == PerfilesEnum.DESCENDIENTE.getId()
						|| perfil == PerfilesEnum.ASEGURADO.getId() || perfil == PerfilesEnum.PENSIONADO.getId()){
										
					
					if(perfil == PerfilesEnum.ASEGURADO.getId() || perfil == PerfilesEnum.PENSIONADO.getId()){								
						
						if(asigNss != null){																		
							miCGF = grupoFamiliarService.cabezaGrupoFamiliar(asigNss.getIdAsignacionNSS());
							
							if(miCGF != null){
								
								miGrupoFamiliar = grupoFamiliarService.getCabezaGrupoFamiliar(asigNss.getIdAsignacionNSS());
								if(miGrupoFamiliar != null){
									if(miGrupoFamiliar.getDomicilio() == null) {
										throw new DerechohabientesBusinessException(ExceptionMessages.PERSONA_SIN_DOMICILIO);
									}
								}
								
								long estadoD = miCGF.getEstadoDerechohabiente().getIdEstadoDerechohabiente();
								if(perfil == PerfilesEnum.ASEGURADO.getId() 
										&& (estadoD == EstadoDerechohabienteEnum.VIGENTE.getId() 
										|| estadoD == EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId())){							
									vigente = true;
								}
								if(perfil == PerfilesEnum.PENSIONADO.getId() 
										&& estadoD == EstadoDerechohabienteEnum.CON_DERECHO.getId()){
									vigente = true;
								}
							}else{
								throw new DerechohabientesBusinessException(ExceptionMessages.DERECHOHABIENTE_SIN_VIGENCIA);
							}
							
							if(vigente){
								UsuarioOrdinario miUsuarioOrd = getUsuarioOrdinario(new Long(miUsuario.getCveIdUsuario()).longValue());
								miUsuario.setUsuarioOrdinario(miUsuarioOrd);
							}else{
								throw new DerechohabientesBusinessException(ExceptionMessages.DERECHOHABIENTE_SIN_VIGENCIA);
							}
						}
					} else {
						miGrupoFamiliar = grupoFamiliarService.getCabezaGrupoFamiliar(asigNss.getIdAsignacionNSS());
						if(miGrupoFamiliar != null){
							
							if(miGrupoFamiliar.getDomicilio() == null) {
								throw new DerechohabientesBusinessException(ExceptionMessages.PERSONA_SIN_DOMICILIO);
							}
							
							miUsuario.setIdUmf(miGrupoFamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
							long estadoD = miGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente();
							if(perfil == PerfilesEnum.ASEGURADO.getId() && (estadoD == EstadoDerechohabienteEnum.VIGENTE.getId() 
									|| estadoD == EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId())){							
								vigente = true;
							}
							if(perfil == PerfilesEnum.PENSIONADO.getId() && estadoD == EstadoDerechohabienteEnum.CON_DERECHO.getId()){
								vigente = true;
							}
							if(perfil == PerfilesEnum.CONYUGE.getId() && estadoD != EstadoDerechohabienteEnum.VIGENTE.getId()){
								vigente = true;
							}
							
							if(vigente){
								UsuarioOrdinario miUsuarioOrd = getUsuarioOrdinario(new Long(miUsuario.getCveIdUsuario()).longValue());
								miUsuario.setUsuarioOrdinario(miUsuarioOrd);
							}else{
								throw new DerechohabientesBusinessException(ExceptionMessages.DERECHOHABIENTE_SIN_VIGENCIA);
							}
						}
					}
					
										
				}else{					
					throw new DerechohabientesBusinessException(ExceptionMessages.USUARIO_INVALIDO);
				}
			}else{
				logger.debug("password invalido");
				throw new DerechohabientesBusinessException(ExceptionMessages.PASSWORD_INVALIDO);
			}
		}else{					
			throw new DerechohabientesBusinessException(ExceptionMessages.USUARIO_INVALIDO);
		}
		return miUsuario;
	}
}
