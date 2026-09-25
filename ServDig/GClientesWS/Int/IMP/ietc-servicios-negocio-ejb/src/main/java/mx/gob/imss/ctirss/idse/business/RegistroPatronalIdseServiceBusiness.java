package mx.gob.imss.ctirss.idse.business;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.idse.entity.IDSEServiceEntityLocal;
import mx.gob.imss.ctirss.idse.entity.RegistroPatronalIdseServiceEntityLocal;
import mx.gob.imss.ctirss.idse.exception.RegistroPatronalIdseException;
import mx.gob.imss.ctirss.idse.model.Persona;
import mx.gob.imss.ctirss.idse.model.RegistroPatronal;
import mx.gob.imss.ctirss.idse.model.enums.OrigenMovimientoEnum;
import mx.gob.imss.ctirss.idse.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.idse.persistencia.IdrBloqueRegistro;
import mx.gob.imss.ctirss.idse.persistencia.IdrPersonasFisica;
import mx.gob.imss.ctirss.idse.persistencia.IdrPersonasMorale;
import mx.gob.imss.ctirss.idse.persistencia.IdtRegistrosPatronale;
import mx.gob.imss.ctirss.idse.persistencia.IdtRepresentado;
import mx.gob.imss.ctirss.idse.service.interfaces.RegistroPatronalIdseServiceBusinessRemote;
import mx.gob.imss.ctirss.idse.utility.RegistroPatronalIdseServiceUtilityLocal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

@Stateless(name="registroPatronalIdseServiceBusinessTest", mappedName="registroPatronalIdseServiceBusiness")
public class RegistroPatronalIdseServiceBusiness 
	implements RegistroPatronalIdseServiceBusinessRemote {

	private static final Logger log = LoggerFactory
		.getLogger(RegistroPatronalIdseServiceBusiness.class);
	
	@EJB
	IDSEServiceEntityLocal idseEntity;
	@EJB
    private RegistroPatronalIdseServiceEntityLocal registroPatronalIdseServiceEntity;
	@EJB
    private RegistroPatronalIdseServiceUtilityLocal registroPatronalIdseServiceUtility;

	
	public void altaRegistroPatronal(RegistroPatronal registroPatronal) throws RegistroPatronalIdseException{
		
		log.info("La informacion que pasa como parametro en altaRegistroPatronal ************************************************************"+ registroPatronal);
		//Obtener RL del arreglo
		registroPatronal.setRepresentanteLegal(null);
		recuperarRepresentanteMovimiento(registroPatronal);
		
		//Localizar Personas Fisica/Moral (Patron)
		registroPatronalIdseServiceUtility.validarTipoPersonaPatron(registroPatronal.getPatron().getTipoPersona());
		TipoPersonaEnum tipoPersonaPatron = TipoPersonaEnum.parse(registroPatronal.getPatron().getTipoPersona());		
		if(tipoPersonaPatron.equals(TipoPersonaEnum.FISICA)){
			//Recuperar Patron PF si existe.
			recuperarPatronPF(registroPatronal);
			//Recuperar persona fisica RL si existe.
			if(registroPatronal.getRepresentanteLegal()!=null){				
				recuperarPersonaFisicaRL(registroPatronal);				
			}			
		}else{
			//Recuperar Patron PM si existe.
			recuperarPatronPM(registroPatronal);						
			//Recuperar persona fisica RL si existe (Alta Patronal PM es por medio de RL)
			registroPatronalIdseServiceUtility.validarRepresentanteLegalRequerido(registroPatronal);
			recuperarPersonaFisicaRL(registroPatronal);
		}		
		//Localizar Registro Patronal (nrp-cvePatron) "idt_registros_patronales"
		if(registroPatronal.getPatron().getCvePersona()!=null){
			recuperarRegistroPatronal(registroPatronal);
		}
		//Localizar Representado "idt_representados"
		IdtRepresentado representado=null;
		if(registroPatronal.getRepresentanteLegal()!=null && registroPatronal.getRepresentanteLegal()
				.getCvePersona()!=null && registroPatronal.getPatron().getCvePersona()!=null){
			representado = registroPatronalIdseServiceEntity.obtenerRepresentanteLegal(registroPatronal.getPatron()
				.getCvePersona(), tipoPersonaPatron, registroPatronal.getPatron().getCvePersona());
			log.info("Datos obtenidos al pasar por el metodo obtenerRepresentanteLegal ************************************************************"+ representado);
			
		}
		//Procesar Alta Registro Patronal.
		procesarAltaPatronal(registroPatronal, representado);	
		log.info("Parametros para el metodo procesarAltaPatronal ************************************************************"+ registroPatronal + representado);
		log.info("FIN Alta Registro Patronal - IDSE ************************************************************");

		//Impactar SINDO
		idseEntity.insertarNuevoRegistroPatronal(registroPatronal);	
		log.info("Parametros para el metodo insertarNuevoRegistroPatronal ***********************************************************"+ registroPatronal);
		log.info("FIN Alta Registro Patronal - SINDO ***********************************************************");
		
	}
	
	public void desasociarRegistroPatronal(RegistroPatronal registroPatronal) throws RegistroPatronalIdseException{
		//Validar datos Patron y NRP
		registroPatronalIdseServiceUtility.validarNRPRequerido(registroPatronal);
		registroPatronalIdseServiceUtility.validarDatosPatron(registroPatronal.getPatron());
		TipoPersonaEnum tipoPersonaPatron = TipoPersonaEnum.parse(registroPatronal.getPatron().getTipoPersona());
		
		//Localizar Personas Fisica/Moral (Patron) "idr_personas_fisicas / idr_personas_morales"
		if(tipoPersonaPatron.equals(TipoPersonaEnum.FISICA)){
			//Recuperar Patron PF si existe.
			recuperarPatronPF(registroPatronal);						
		}else{
			//Recuperar Patron PM si existe.
			recuperarPatronPM(registroPatronal);
		}
		registroPatronalIdseServiceUtility
			.validarPersonaFisicaMoralPatronExistenteEnIdse(registroPatronal.getPatron().getCvePersona());		
		//Localizar Registro Patronal (nrp-cvePatron) "idt_registros_patronales"
		recuperarRegistroPatronal(registroPatronal);
		registroPatronalIdseServiceUtility
			.validarRegistroPatronalExistenteEnIdse(registroPatronal.getCveRegistroPatronal());
		//Desasociar RP.
		registroPatronalIdseServiceEntity.desasociarRegistroPatronal(registroPatronal);
		log.info("FIN Desasociar Registro Patronal ************************************************************");
	}
	
	public void asociarRepresentanteLegal(RegistroPatronal registroPatronal) throws RegistroPatronalIdseException{
		//Obtener RL del arreglo
		registroPatronal.setRepresentanteLegal(null);
		recuperarRepresentanteMovimiento(registroPatronal);
		
		//Validar datos Patron y Representante Legal
		registroPatronalIdseServiceUtility.validarDatosRepresentanteLegal(registroPatronal.getRepresentanteLegal());
		registroPatronalIdseServiceUtility.validarDatosPatron(registroPatronal.getPatron());	
		TipoPersonaEnum tipoPersonaPatron = TipoPersonaEnum.parse(registroPatronal.getPatron().getTipoPersona());
		
		//Recuperar persona fisica RL si existe.
		recuperarPersonaFisicaRL(registroPatronal);	
		//Localizar Personas Fisica/Moral (Patron y/o Representante Legal) "idr_personas_fisicas / idr_personas_morales"
		if(tipoPersonaPatron.equals(TipoPersonaEnum.FISICA)){
			//Recuperar Patron PF.
			recuperarPatronPF(registroPatronal);
			IdrPersonasFisica patronPF = registroPatronalIdseServiceUtility
				.prepararAltaPersonaFisica(registroPatronal.getPatron());
			if(patronPF.getCvePersonaFisica()==null){
				registroPatronalIdseServiceEntity.guardarPersonaFisicia(patronPF);
				registroPatronal.getPatron().setCvePersona(patronPF.getCvePersonaFisica());
			}
		}else{
			//Recuperar Patron PM.
			recuperarPatronPM(registroPatronal);
			IdrPersonasMorale patronPM = registroPatronalIdseServiceUtility
				.prepararAltaPersonaMoral(registroPatronal.getPatron());
			if(patronPM.getCvePersonaMoral()==null){
				registroPatronalIdseServiceEntity.guardarPersonaMoral(patronPM);
				registroPatronal.getPatron().setCvePersona(patronPM.getCvePersonaMoral());
			}
		}
	
		if(registroPatronal.getPatron().getCvePersona()!=null){
			//Localizar Representado "idt_representados"
			IdtRepresentado representado=null;
			if(registroPatronal.getRepresentanteLegal()!=null && registroPatronal.getRepresentanteLegal()
					.getCvePersona()!=null && registroPatronal.getPatron().getCvePersona()!=null){
				representado = registroPatronalIdseServiceEntity.obtenerRepresentanteLegal(registroPatronal.getPatron()
					.getCvePersona(), tipoPersonaPatron, registroPatronal.getPatron().getCvePersona());
			}			
			if(representado==null){
				representado=procesarRepresentanteLegal(registroPatronal, representado);
			}
			String usuarioAfecta = registroPatronal.getRepresentanteLegal().getNombreUsuario();
			//Asociar RL
			OrigenMovimientoEnum origen = OrigenMovimientoEnum.parse(registroPatronal.getIdOrigenSolicitud());
			registroPatronalIdseServiceEntity.asociarRepresentanteLegal(tipoPersonaPatron, registroPatronal.getPatron()
				.getCvePersona(), representado.getCveRepresentados(), usuarioAfecta, origen);				
		}else{
			log.info("El patron indicado "+registroPatronal.getPatron().getRfc()+ " no existe o esta inactivo en IDSE.");
			IdrPersonasFisica repLegal = null;
			repLegal = registroPatronalIdseServiceUtility
				.prepararAltaPersonaFisica(registroPatronal.getRepresentanteLegal());
			if(repLegal.getCvePersonaFisica()==null){
				log.info("Se procesa unicamente el alta de persona fisica RL. Para ser Representado se requiere el patron.");
				//Procesar PF (Representante Legal)
				registroPatronalIdseServiceEntity.guardarPersonaFisicia(repLegal);
			}
		}
		log.info("FIN Asociar Representante Legal ************************************************************");
	}
	
	
	public void desasociarRepresentanteLegal(RegistroPatronal registroPatronal) throws RegistroPatronalIdseException{
		//Recuperar Representantes Legales de arreglo.
		List<Persona> representantesLegales = null;
		if(existenRepresentantes(registroPatronal.getRepresentantes())){
			representantesLegales = Arrays.asList(registroPatronal.getRepresentantes());
		}	
		
		//Validar datos Patron y Representante Legal
		registroPatronalIdseServiceUtility.validarDatosPatron(registroPatronal.getPatron());
		registroPatronalIdseServiceUtility.validarRepresentantesLegales(representantesLegales);
		
		//Localizar Personas Fisica/Moral PATRON
		TipoPersonaEnum tipoPersonaPatron = TipoPersonaEnum.parse(registroPatronal.getPatron().getTipoPersona());		
		if(tipoPersonaPatron.equals(TipoPersonaEnum.FISICA)){
			//Recuperar Patron PF si existe.
			recuperarPatronPF(registroPatronal);
		}else{
			//Recuperar Patron PM si existe.
			recuperarPatronPM(registroPatronal);								
		}		
		registroPatronalIdseServiceUtility
			.validarPersonaFisicaMoralPatronExistenteEnIdse(registroPatronal.getPatron().getCvePersona());
		
		//Procesar Representantes Legales
		for(Persona representanteLegal : representantesLegales){
			//Validar datos de cada RL
			registroPatronalIdseServiceUtility.validarDatosRepresentanteLegal(representanteLegal);
			registroPatronal.setRepresentanteLegal(representanteLegal);
			//Recuperar persona fisica RL
			recuperarPersonaFisicaRL(registroPatronal);
			if(registroPatronal.getRepresentanteLegal().getCvePersona()!=null){
				String usuarioAfecta=registroPatronal.getRepresentanteLegal().getNombreUsuario();
				//Desasociar RL
				OrigenMovimientoEnum origen = OrigenMovimientoEnum.parse(registroPatronal.getIdOrigenSolicitud());
				registroPatronalIdseServiceEntity.desasociarRepresentanteLegal(registroPatronal.getRepresentanteLegal()
					.getCvePersona(),tipoPersonaPatron, registroPatronal.getPatron().getCvePersona(), usuarioAfecta, origen);
			}else{
				log.info("El Representante Legal "+registroPatronal.getRepresentanteLegal().getRfc()
					+" no fue localizado o ya esta inactivo. No se procesa su disosiación.");
			}
		}
		log.info("FIN Desasociar Representante Legal ************************************************************");
	}
		
	
	
	
	private void procesarAltaPatronal(RegistroPatronal registroPatronal, IdtRepresentado representado){
		String usuarioAfecta=registroPatronal.getPatron().getNombreUsuario();
		
		if(registroPatronal.getCveRegistroPatronal()!=null){
			log.info("El Registro Patronal ya existe en IDSE ("+registroPatronal.getCveRegistroPatronal()+")");			
			if(representado!=null){
				log.info("El representado "+registroPatronal.getRepresentanteLegal().getRfc()+" ya existe en IDSE.");				
			}else{
				//Procesar RL
				if(registroPatronal.getRepresentanteLegal()!=null){
					representado=procesarRepresentanteLegal(registroPatronal, representado);
				}
			}
			boolean procesarMovimiento=false;
			//Validar relacion "IdrBloqueRegistro"
			if(representado!=null && representado.getCveRepresentados()!=null){
				IdrBloqueRegistro idrBloqueRegistro = registroPatronalIdseServiceEntity.obtenerBloqueRegistro(
						registroPatronal.getCveRegistroPatronal(), representado.getCveRepresentados());
				if(idrBloqueRegistro==null){
					registroPatronalIdseServiceEntity.guardarBloqueRegistro(registroPatronalIdseServiceUtility
						.prepararAltaBloqueRegistro(registroPatronal, representado.getCveRepresentados()));
					procesarMovimiento=true;
				}
			}			
			if(procesarMovimiento){
				//Registrar HistoricoMovimiento Manualmente (Se registro RL o Bloque)
				registroPatronalIdseServiceEntity.guardarHistoricoMovimiento(registroPatronalIdseServiceUtility
					.prepararHistoricoMovimientoManual(registroPatronal, usuarioAfecta));
			}			
		}else{
			log.info("ALTA REGISTRO PATRONAL");			
			//Procesar PATRON (persona fisica/persona moral)
			TipoPersonaEnum tipoPersonaPatron = TipoPersonaEnum.parse(registroPatronal.getPatron().getTipoPersona());
			boolean obtenerRLExistentes=true;
			List<IdtRepresentado> listaRL =null;
			if(tipoPersonaPatron.equals(TipoPersonaEnum.FISICA)){
				IdrPersonasFisica patronPF = registroPatronalIdseServiceUtility
					.prepararAltaPersonaFisica(registroPatronal.getPatron());
				log.info("Preparar alta patronal de persona fisica para registro ("+patronPF+")");	
				if(patronPF.getCvePersonaFisica()==null){
					//Procesar Patron PF
					registroPatronalIdseServiceEntity.guardarPersonaFisicia(patronPF);
					registroPatronal.getPatron().setCvePersona(patronPF.getCvePersonaFisica());
					obtenerRLExistentes=false;
				}
			}else{
				IdrPersonasMorale patronPM = registroPatronalIdseServiceUtility
					.prepararAltaPersonaMoral(registroPatronal.getPatron());
				log.info("Preparar alta patronal de persona moral para registro ("+patronPM+")");			
				if(patronPM.getCvePersonaMoral()==null){
					//Procesar Patron PM
					registroPatronalIdseServiceEntity.guardarPersonaMoral(patronPM);
					registroPatronal.getPatron().setCvePersona(patronPM.getCvePersonaMoral());
					obtenerRLExistentes=false;
				}			
			}
			//Procesar RL
			if(registroPatronal.getRepresentanteLegal()!=null){
				representado=procesarRepresentanteLegal(registroPatronal, representado);
				log.info("Obtener representado procesado ("+representado+")");			

			}
			//Recuperar RLs existentes para asociarl nuevo RP
			if(obtenerRLExistentes){
				Long cvePFRepresentante = null;
				if(representado!=null && representado.getRepresentanteLegal()!=null){
					cvePFRepresentante=representado.getRepresentanteLegal().getCvePersonaFisica();
				}
				listaRL = registroPatronalIdseServiceEntity.obtenerRepresentantesLegalesAsociados(
					tipoPersonaPatron, registroPatronal.getPatron().getCvePersona(), cvePFRepresentante);
				log.info("Obtener representantes legales relacionadas al rp ("+listaRL+")");			

			}
			//Agregar a la lista de RLs al RL que se esta procesando
			if(representado!=null && representado.getCveRepresentados()!=null){
				if(CollectionUtils.isEmpty(listaRL))
					listaRL = new ArrayList<IdtRepresentado>();
				
				listaRL.add(representado);
			}			
			//Procesar Registro Patronal		
			IdtRegistrosPatronale idtRegistroPatronal = registroPatronalIdseServiceEntity.guardarRegistroPatronal(
				registroPatronalIdseServiceUtility.prepararAltaRegistroPatronal(registroPatronal,usuarioAfecta));
			registroPatronal.setCveRegistroPatronal(idtRegistroPatronal.getCveRegistroPatronal());
			log.info("procesar registro patronal ("+registroPatronal+usuarioAfecta+")");			
			log.info("procesar registro patronal ("+idtRegistroPatronal+")");			

			//Procesar Relacion Bloques (RepLegal - Reg.Patronal)
			if(!CollectionUtils.isEmpty(listaRL)){
				for(IdtRepresentado idtRepresentado : listaRL){
					if(idtRepresentado!=null && idtRepresentado.getCveRepresentados()!=null){
						registroPatronalIdseServiceEntity.guardarBloqueRegistro(registroPatronalIdseServiceUtility
							.prepararAltaBloqueRegistro(registroPatronal, idtRepresentado.getCveRepresentados()));			
						log.info("termina de procesar la realcion de bloques");			

					}
				}
			}
			
		}
	}
	
	private IdtRepresentado procesarRepresentanteLegal(RegistroPatronal registroPatronal, IdtRepresentado representado){
		IdrPersonasFisica repLegal = null;
		repLegal = registroPatronalIdseServiceUtility
			.prepararAltaPersonaFisica(registroPatronal.getRepresentanteLegal());
		if(repLegal.getCvePersonaFisica()==null){
			//Procesar PF (Representante Legal)
			registroPatronalIdseServiceEntity.guardarPersonaFisicia(repLegal);
			registroPatronal.getRepresentanteLegal().setCvePersona(repLegal.getCvePersonaFisica());
			//Procesar Representado
			representado = registroPatronalIdseServiceEntity.guardarRepresentado(
				registroPatronalIdseServiceUtility.prepararAltaRepresentado(registroPatronal));
			log.info("Alta representante legal (persona fisica y representado).");
		}else{			
			//Existe persona fisica RL, verificar si ya existe la relación con Patron (Representado).
			TipoPersonaEnum tipoPersonaPatron = TipoPersonaEnum.parse(registroPatronal.getPatron().getTipoPersona());
			representado = registroPatronalIdseServiceEntity.obtenerRepresentanteLegal(repLegal.getCvePersonaFisica(), 
				tipoPersonaPatron, registroPatronal.getPatron().getCvePersona());
			if(representado==null){
				//Procesar Representado
				representado = registroPatronalIdseServiceEntity.guardarRepresentado(
					registroPatronalIdseServiceUtility.prepararAltaRepresentado(registroPatronal));
				log.info("Alta representante legal (representado, ya existe como persona fisica).");
			}	
		}
		return representado;
	}
	
	private void recuperarPatronPM(RegistroPatronal registroPatronal){
		registroPatronal.getPatron().setRfc(
			registroPatronal.getPatron().getRfc().trim().toUpperCase());
		IdrPersonasMorale personaMoral = registroPatronalIdseServiceEntity
			.obtenerPersonaMoral(registroPatronal.getPatron().getRfc(), 
				registroPatronal.getPatron().getCertificado().getClaveSerial());		
		if(personaMoral != null){			
			//Existe Patron PM y tiene mismo serial.
			log.info("PatronPersonaMoral:" + personaMoral.getCvePersonaMoral());
			registroPatronal.setPatron(registroPatronalIdseServiceUtility
				.transformarPersonaMoralEntityToModel(personaMoral));			
		}else{
			log.debug("Puede que exista pero NO con el mismo serial o este inactivo (Se considera NUEVO)");
		}
		
	}
	
	private void recuperarPatronPF(RegistroPatronal registroPatronal){
		registroPatronal.getPatron().setRfc(
			registroPatronal.getPatron().getRfc().trim().toUpperCase());
		IdrPersonasFisica personaFisica = registroPatronalIdseServiceEntity
			.obtenerPersonaFisica(registroPatronal.getPatron().getRfc(), 
				registroPatronal.getPatron().getCertificado().getClaveSerial());
		if(personaFisica != null){
			//Existe Patron PF y tiene mismo serial.		
			log.info("PatronPersonaFisica:" + personaFisica.getCvePersonaFisica());
			registroPatronal.setPatron(registroPatronalIdseServiceUtility
				.transformarPersonaFisicaEntityToModel(personaFisica));			
		}else{
			log.debug("Puede que exista pero NO con el mismo serial o este inactivo (Se considera NUEVO)");
		}			
	}
	
	private void recuperarPersonaFisicaRL(RegistroPatronal registroPatronal){
		registroPatronal.getRepresentanteLegal().setRfc(
			registroPatronal.getRepresentanteLegal().getRfc().trim().toUpperCase());
		IdrPersonasFisica personaFisica = registroPatronalIdseServiceEntity
			.obtenerPersonaFisica(registroPatronal.getRepresentanteLegal().getRfc(), 
				registroPatronal.getRepresentanteLegal().getCertificado().getClaveSerial());
		if(personaFisica != null){			
			//Existe Patron PF y tiene mismo serial.
			log.info("RLPersonaFisica:" + personaFisica.getCvePersonaFisica());
			registroPatronal.setRepresentanteLegal(registroPatronalIdseServiceUtility
				.transformarPersonaFisicaEntityToModel(personaFisica));			
		}else{
			log.debug("Puede que exista pero NO con el mismo serial o este inactivo (Se considera NUEVO)");
		}
	}
	
	private void recuperarRegistroPatronal(RegistroPatronal registroPatronal){
		registroPatronal.setNrp(registroPatronal.getNrp().trim().toUpperCase());
		TipoPersonaEnum tipoPersonaPatron = TipoPersonaEnum.parse(registroPatronal.getPatron().getTipoPersona());
		IdtRegistrosPatronale idtRegistroPatronal = registroPatronalIdseServiceEntity
			.obtenerRegistroPatronal(registroPatronal.getNrp(), tipoPersonaPatron, 
				registroPatronal.getPatron().getCvePersona());
		if(idtRegistroPatronal!=null){
			log.info("RegistroPatronal:" + idtRegistroPatronal.getCveRegistroPatronal());
			registroPatronal.setCveRegistroPatronal(idtRegistroPatronal.getCveRegistroPatronal());
			registroPatronal.setRazonSocial(idtRegistroPatronal.getRefRazonSocial());
			registroPatronal.setDomicilioCentroTrabajo(idtRegistroPatronal.getRefDomicilioCentroTrab());			
		}
	}
	
	private boolean existenRepresentantes(Persona[] personas){
		if(personas==null){
			return false;
		}
		return true;			
	}
	
	private void recuperarRepresentanteMovimiento(RegistroPatronal registroPatronal){
		if(existenRepresentantes(registroPatronal.getRepresentantes())){
			List<Persona> representantesLegales = Arrays.asList(registroPatronal.getRepresentantes());
			if(!CollectionUtils.isEmpty(representantesLegales)){
				registroPatronal.setRepresentanteLegal(representantesLegales.get(0));
			}
		}			
	}
	
}
