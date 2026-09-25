package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.PatronDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.VigenciaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ConsultarVigenciaServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DateUtils;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.asegurado.AseguradoPension;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Beneficiarios;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta1;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta2;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta3;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Table;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Table2;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Table3;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TurnoEnum;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.apache.log4j.Logger;



/**@author JUAN MANUEL MARQUEZ 
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 16/06/2012
 */

@Stateless( name = "consultaVigenciaService", mappedName = "consultaVigenciaService")
public class ConsultaVigenciaService implements ConsultarVigenciaServiceRemote{
	private final static Logger log = Logger.getLogger(ConsultaVigenciaService.class);
	
	@EJB(name = "grupoFamiliarDao")
	GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB(name = "patronDao")
	PatronDaoLocal patronDaoLocal;	
	@EJB(name = "vigenciaDao")
	VigenciaDaoLocal vigenciaDaoLocal;
	
	@Override
	public Respuesta1 consultaVigencia1(String nss, String umf,
			String delegacion, String cpid) {
		Respuesta1 resp = new Respuesta1();
		
		Table integrante = null;
		List<Table> integrates = new ArrayList<Table>();			
		
		AsignacionNSS asignacionNSS = null;		
		List<GrupoFamiliar> grupoFam = null;								
		String error = validaNss(nss,1);		
		String codCpid = validaCPID(cpid);
		long idUmf = 0;
		long idDelegacion = 0;
		
		if(umf == null || umf.equals("")){
			error = Constants.SINUMF;
		}else{
			try {
				idUmf = Long.parseLong(umf);
			} catch (Exception e) {
				error = Constants.SINUMF;
			}
		}
		if(delegacion == null || delegacion.equals("")){
			error = Constants.SINDELEGACION;
		}else{
			try {
				idDelegacion = Long.parseLong(delegacion);
			} catch (Exception e) {
				error = Constants.SINDELEGACION;
			}
		}
				
		if( error == null && codCpid == null){			
			try {				
				asignacionNSS = buscaAsigNSS(nss+generaDigitoVerificador(nss));
			} catch (DerechohabientesBusinessException e) {
				error = Constants.MSG19;			
			} catch (Exception e){
				log.debug("Error inesperado al buscar datos del nss", e);
				error = Constants.MSG19;
			}
			if(asignacionNSS != null){								
				try {
					grupoFam = buscaIntegrantes(asignacionNSS.getIdAsignacionNSS());					
				} catch (DerechohabientesBusinessException e) {
					error = Constants.MSG19;					
				} catch (Exception e) {
					log.debug("Error inesperado al buscar integrantes del grupo familiar", e);
					error = Constants.MSG19;
				}				
				if(grupoFam == null || grupoFam.size() <= 0){
					integrates.add(error1(codCpid, nss,Constants.MSG19));
				}else{
					for(GrupoFamiliar gf : grupoFam){
						if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF().longValue() == idUmf &&
								gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().getId().longValue() == idDelegacion){
							integrante =  new Table();
							integrante = llenaRespuesta1(gf, integrante, cpid, nss, gf.getDerechohabiente().getCurp(), 
													gf.getAgregadoAfiliacion(), 
													gf.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
							integrates.add(integrante);
						}						
					}
				}				
			}else{				
				integrates.add(error1(cpid, nss,Constants.MSG19));
			}
		}else{			
			if(error != null){
				integrates.add(error1(cpid, nss, error));
			}else{
				cpid ="00";
				integrates.add(error1(cpid, nss, codCpid));				
			}						
		}		
		
		if(integrates.isEmpty()){
			cpid ="00";
			integrates.add(error1(codCpid, nss,Constants.MSG19));
		}
		
		resp.setTable(integrates);
		return resp;
	}
	
	private Table error1(String cpid,String nss, String error){
		Table integrante = new Table();
		integrante.setCODIGO(error);
		integrante.setCPID(cpidGeneric(cpid, nss, "000000000000000000", "00000000", 3));
		return integrante;
	}
	
	
	@Override
	public Respuesta2 consultaVigencia2(String nss, String umf,String delegacion, String cpid) {
		Respuesta2 resp = new Respuesta2();
		Table2 integrante = null;
		
		List<Table2> integrantes = new ArrayList<Table2>();

		AsignacionNSS asignacionNSS = null;
		CabezaGrupoFamiliar miAsegurado = null;
		String registroPatronal = null;
		List<GrupoFamiliar> grupoFam = null;								
		String error = validaNss(nss,1);
		String codCpid = validaCPID(cpid);
		long idUmf = 0;
		long idDelegacion = 0;
		
		if(umf == null || umf.equals("")){
			error = Constants.SINUMF;
		}else{
			try {
				idUmf = Long.parseLong(umf);
			} catch (Exception e) {
				error = Constants.SINUMF;
			}
		}
		
		if(delegacion == null || delegacion.equals("")){
			error = Constants.SINDELEGACION;
		}else{
			try {
				idDelegacion = Long.parseLong(delegacion);
			} catch (Exception e) {
				error = Constants.SINDELEGACION;
			}
		}
		
		if(error == null && codCpid == null){
			try {
				asignacionNSS = buscaAsigNSS(nss+generaDigitoVerificador(nss));
			} catch (DerechohabientesBusinessException e) {
				error = Constants.MSG19;			
			} catch (Exception e) {
				error = Constants.MSG19;	
				log.debug("Error inesperado al buscar datos del nss", e);
			}
			if(asignacionNSS != null){
				try {
					miAsegurado = buscaAsegurado(asignacionNSS.getIdAsignacionNSS());				
				}catch (DerechohabientesBusinessException e) {
					error = Constants.MSG19;			
				}catch (Exception e){
					log.debug("Error inesperado al buscar datos del asegurado", e);
					error = Constants.MSG19;
				}
				if(miAsegurado != null){										
					try {
						registroPatronal = buscaRegistroPat(miAsegurado.getPatronSujetoObligado().getCveIdSujetoObligado());
					} catch (Exception e) {
						log.debug("Error inesperado al buscar datos del registro patronal", e);
						error = Constants.MSG19;
					}						
									
					try {
						grupoFam = buscaIntegrantes(asignacionNSS.getIdAsignacionNSS());					
					} catch (DerechohabientesBusinessException e) {
						error = Constants.MSG19;					
					} catch (Exception e) {
						log.debug("Error inesperado al buscar integrantes del grupo familiar", e);
						error = Constants.MSG19;
					}	
					if(grupoFam != null && registroPatronal != null){
						for(GrupoFamiliar gf : grupoFam){
							if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF().longValue() == idUmf &&
									gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().getId().longValue() == idDelegacion){
								integrante =  new Table2();
								integrante = llenaRespuesta2(gf, integrante, registroPatronal,miAsegurado,cpid, nss, gf.getDerechohabiente().getCurp(), 
														gf.getAgregadoAfiliacion(), 
														gf.getEstadoDerechohabiente().getIdEstadoDerechohabiente());
								integrantes.add(integrante);
							}
						}
					}else{
						integrante =  new Table2();				
						integrante.setCODIGO(Constants.MSG19);				
						integrante.setCPID(cpidGeneric(cpid, nss, "000000000000000000", "00000000", 00));
						integrantes.add(integrante);
					}
				}else{
					integrantes.add(error2(cpid, nss, Constants.MSG19));
				}										
			}else{
				integrantes.add(error2(cpid, nss, Constants.MSG19));
			}									
		}else{			
			if(error!= null){
				integrantes.add(error2(cpid, nss, error));
			}else{
				cpid="00";
				integrantes.add(error2(cpid, nss, codCpid));				
			}			
		}
		
		if(integrantes.isEmpty()){
			integrante =  new Table2();				
			integrante.setCODIGO(Constants.MSG19);				
			integrante.setCPID(cpidGeneric(cpid, nss, "000000000000000000", "00000000", 00));
			integrantes.add(integrante);
		}
		
		resp.setTable(integrantes);	
		return resp;
		
	}		
	
	private Table2 error2(String cpid,String nss, String error){
		Table2 integrante = new Table2();
		integrante.setCODIGO(error);
		integrante.setCPID(cpidGeneric(cpid, nss, "000000000000000000", "00000000", 00));
		return integrante;
	}
		
	@Override
	public Respuesta3 consultaVigencia3(String nss) {
		Respuesta3 resp = new Respuesta3();
		Beneficiarios ben = null;
		Table3 datosAsegurado = new Table3();		
		List<Beneficiarios> benefs = new ArrayList<Beneficiarios>();
		
		long parentesco = 0;
		
		AsignacionNSS asignacionNSS = null;
		CabezaGrupoFamiliar miAsegurado = null;
		String registroPatronal = null;
		List<GrupoFamiliar> grupoFam = null;								
		String error = validaNss(nss,3);		
		
		if(error == null){
			try {
				asignacionNSS = buscaAsigNSS(nss+generaDigitoVerificador(nss));
			} catch (DerechohabientesBusinessException e1) {
				log.error("Error al recuperrar nss", e1);
				error = Constants.MSG04;
			} catch (Exception e) {
				error = Constants.MSG04;
				log.error("Error inesperado al buscar datos del NSS", e);
			}										
			
			if(asignacionNSS == null){				
				datosAsegurado.setCpid(cpidNECE(Constants.MSG04));
				resp.setReturn(datosAsegurado);
			}else{				
				try {
					miAsegurado = buscaAsegurado(asignacionNSS.getIdAsignacionNSS());				
				}catch (DerechohabientesBusinessException e){
					error = Constants.MSG04;
				} catch (Exception e) {
					error = Constants.MSG04;
					log.debug("Error inesperado al buscar datos del asegurado", e);
				}
				if(miAsegurado == null){					
					datosAsegurado.setCpid(cpidNECE(Constants.MSG04));
					resp.setReturn(datosAsegurado);
				}else{
					try {
						registroPatronal = buscaRegistroPat(miAsegurado.getPatronSujetoObligado().getCveIdSujetoObligado());
					} catch (DerechohabientesBusinessException e) {
						error = Constants.MSG04;
					} catch (Exception e) {
						error = Constants.MSG04;
						log.debug("Error inesperado al buscar datos del registro patronal", e);
					}
					try {			
						grupoFam = buscaIntegrantes(asignacionNSS.getIdAsignacionNSS());					
					} catch (DerechohabientesBusinessException e) {
						error = Constants.MSG04;					
					} catch (Exception e) {
						error = Constants.MSG04;
						log.debug("Error inesperado al buscar integrantes del grupo familiar", e);
					}
					if(grupoFam == null){						
						datosAsegurado.setCpid(cpidNECE(Constants.MSG04));
						resp.setReturn(datosAsegurado);
					}else{						
						for(GrupoFamiliar gf : grupoFam){	
							parentesco = gf.getParentesco().getIdParentesco();
							
							if( parentesco == ParentescoEnum.ASEGURADO.getId() || parentesco == ParentescoEnum.PENSIONADO.getId()){
								datosAsegurado = llenaAsegurado(gf, datosAsegurado, registroPatronal,miAsegurado,error);
							}else{
								ben = new Beneficiarios();
								ben = llenaBeneficiario(gf, ben, registroPatronal,miAsegurado,error);
								benefs.add(ben);
								
							}			
						}
						resp.setReturn(datosAsegurado);
						resp.getReturn().setBeneficiarios(benefs);
					}	
				}	
			}		
		}else{
			datosAsegurado.setCpid(cpidNECE(error));
			resp.setReturn(datosAsegurado);
		}												
		return resp;
	}
	
	
	private String validaCPID(String cpid) {
		
		String codigo = null;
		if(cpid == null || cpid.equals("") || cpid.trim().length() < 2){
			codigo = Constants.MSG21;								
		}		
		return codigo;
	}
	
	 /**
     * Genera el digito verificador de un NSS
     * @param nss Un NSS
     * @return El d�gito verificador correspondiente
     * @throws ExcepcionIMSS Si no se puede realizar la operaci�n
     */
    public int generaDigitoVerificador(String nss) {        
        int k = 0;
        int j = 0;
        int digito = 0;
        for(int i=0;i<nss.length();i++){
            if(i%2!=0){
                k=Integer.parseInt(nss.charAt(i)+"")*2;
                if(k>9){
                    k=k-9;
                }
                j=j+k;
            }else{
                j=j+(Integer.parseInt(nss.charAt(i)+""));
            }
        }
        for(int i=1;i<10;i++) {
            if(i*10>j) {
                digito=(i*10)-j;
                break;
            }
        }
        if(digito>=10) {
            digito=digito-9;
        }        
        return digito;        
    }
    
	private String validaNss(String nss, int version) {
	
		String codigo = null;
		if(nss == null || nss.equals("")){
			if(version == 1 || version == 2){
				codigo = Constants.MSG23;
			}else if(version == 3){
				codigo = Constants.MSG09;
			}
		}else{
			if(noNumerico(nss)){	
				if(version == 1 || version == 2){
					codigo = Constants.MSG23;
				}else if(version == 3){
					codigo = Constants.MSG09;
				}
			}else
			if(nss.trim().length() != 10){
				if(version == 1 || version == 2){
					codigo = Constants.MSG20;
				}else if(version == 3){
					codigo = Constants.MSG06;
				}			
			}
		}
		return codigo;
	}
	
	private boolean noNumerico(String cadena){ 
		boolean res = false;
		int caracteres = cadena.length();
		int cont = 0;
		try {
			while(cont < caracteres){				
				Integer.parseInt(cadena.substring(cont,cont+1));				
				cont++;
			}					
		} catch (NumberFormatException nfe){
			res= true;
		}		
		return res;
	}

	private Table llenaRespuesta1(GrupoFamiliar gf, Table resp, String cpidE, String nss, String curp, String agregadoAfil, long edoInt){
		if(gf.getAsignacionNSS().getNssStr() != null){
			resp.setNSS(gf.getAsignacionNSS().getNssStr().substring(0,10));
		}
		if(gf.getDerechohabiente().getExpedienteElectronico() != null){
			resp.setCURPIMSS(gf.getDerechohabiente().getExpedienteElectronico());
		}
		if(gf.getDerechohabiente().getCurp() != null){
			resp.setCURP(gf.getDerechohabiente().getCurp());
		}
		if(gf.getDerechohabiente().getPrimerApellido() != null){
			resp.setPATERNO(gf.getDerechohabiente().getPrimerApellido());
		}
		if(gf.getDerechohabiente().getSegundoApellido() != null){
			resp.setMATERNO(gf.getDerechohabiente().getSegundoApellido());
		}
		if(gf.getDerechohabiente().getNombre() != null){
			resp.setNOMBRE(gf.getDerechohabiente().getNombre());
		}
		if(gf.getAgregadoMedico() != null){
			resp.setAGREGADO_MEDICO(gf.getAgregadoMedico());
		}
		if(gf.getAgregadoAfiliacion() != null){
			resp.setAGREGADO_AFILIACION(gf.getAgregadoAfiliacion());
		}
		if(gf.getDerechohabiente().getFechaNacimiento().toString() != null){
			resp.setFECHA_NACIMIENTO(gf.getDerechohabiente().getFechaNacimiento().toString());
		}
		if(gf.getMedicoEnTurno().getConsultorio().getDescripcion() != null){
			resp.setCONSULTORIO(gf.getMedicoEnTurno().getConsultorio().getDescripcion());
		}
		if(gf.getMedicoEnTurno().getTurno().getIdTurno() == TurnoEnum.MATUTINO.getId()){
			resp.setTURNO("M");
		}else{
			resp.setTURNO("V");
		}	
		if(llenaDomicilio(gf.getDomicilio()) != null){
			resp.setDIRECCION(llenaDomicilio(gf.getDomicilio()));
		}
		if(medioContacto(gf.getDerechohabiente().getIdPersona(), TipoContactoEnum.TELEFONO_FIJO.getId()).size() >= 0){
			for(String telefono :medioContacto(gf.getDerechohabiente().getIdPersona(), TipoContactoEnum.TELEFONO_FIJO.getId())){
				resp.setTELEFONO(telefono);
			}			
		}
		if(gf.getDomicilio().getAsentamiento().getNombre() != null){
			resp.setCOLONIA(gf.getDomicilio().getAsentamiento().getNombre());
		}
		if(gf.getEstadoDerechohabiente().getDescripcion() != null){
			resp.setVIGENCIA(gf.getEstadoDerechohabiente().getDescripcion());
		}		
		if(gf.getFechaFinVigencia() == null){			
			resp.setVIGENTE_HASTA(DateUtils.dateFormat_yyyy_MM_dd(new Date())+" 00:00:00.0");
		}else{			
			resp.setVIGENTE_HASTA(gf.getFechaFinVigencia().toString()+" 00:00:00.0");
		}
		if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico().toString() != null){
			resp.setDH_UMF(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico().toString());
		}
		if(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre() !=null){
			resp.setDH_DELEG(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre());
		}
		if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal() != null) {
			if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal().getClavePresupuestal() != null){
				resp.setDH_CVE_PRESUP(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal().getClavePresupuestal());
			}
		}
		resp.setDH_IP_SERVER("254.254.254.254");
		
		resp.setCPID(cpidGeneric(cpidE, nss, curp, agregadoAfil, edoInt));
			
		return resp;
	}
	
	private Table2 llenaRespuesta2(GrupoFamiliar gf, Table2 resp,String registroPatronal,CabezaGrupoFamiliar asegurado, String cpidE, String nss, String curp, String agregadoAfil, long edoInt){
		long idModalidad = asegurado.getPatronSujetoObligado().getModalidad().getIdModalidad();
		if(gf.getAsignacionNSS().getNssStr() != null){
			resp.setNSS(gf.getAsignacionNSS().getNssStr());
		}
		if(gf.getDerechohabiente().getExpedienteElectronico() != null){
			resp.setIDEE(gf.getDerechohabiente().getExpedienteElectronico());
		}
		if(gf.getDerechohabiente().getCurp() != null){
			resp.setCURP(gf.getDerechohabiente().getCurp());
		}
		if(gf.getDerechohabiente().getPrimerApellido() != null){
			resp.setPATERNO(gf.getDerechohabiente().getPrimerApellido());
		}
		if(gf.getDerechohabiente().getSegundoApellido() != null){
			resp.setMATERNO(gf.getDerechohabiente().getSegundoApellido());
		}
		if(gf.getDerechohabiente().getNombre() != null){
			resp.setNOMBRE(gf.getDerechohabiente().getNombre());
		}
		if(gf.getDerechohabiente().getSexo().getIdSexo() != null){
			if(gf.getDerechohabiente().getSexo().getIdSexo() == SexoEnum.HOMBRE.getId()){
				resp.setSEXO("M");
			}else{
				resp.setSEXO("F");
			}
		}
		if(gf.getAgregadoAfiliacion() != null){
			resp.setAGREGADO_AFILIACION(gf.getAgregadoAfiliacion());
		}
		if(gf.getAgregadoMedico() != null){
			resp.setAGREGADO_MEDICO(gf.getAgregadoMedico());
		}		
		if(gf.getDerechohabiente().getFechaNacimiento().toString() != null){
			resp.setFECHA_NACIMIENTO(gf.getDerechohabiente().getFechaNacimiento().toString());
		}
		if(gf.getMedicoEnTurno().getConsultorio().getDescripcion() != null){
			resp.setCONSULTORIO(gf.getMedicoEnTurno().getConsultorio().getDescripcion());
		}
		if(gf.getMedicoEnTurno().getTurno().getIdTurno() == TurnoEnum.MATUTINO.getId()){
			resp.setTURNO("M");
		}else{
			resp.setTURNO("V");
		}	
		if(llenaDomicilio(gf.getDomicilio()) != null){
			resp.setDIRECCION(llenaDomicilio(gf.getDomicilio()));
		}
		if(medioContacto(gf.getDerechohabiente().getIdPersona(), TipoContactoEnum.TELEFONO_FIJO.getId()).size() >= 0){
			for(String telefono :medioContacto(gf.getDerechohabiente().getIdPersona(), TipoContactoEnum.TELEFONO_FIJO.getId())){
				resp.setTELEFONO(telefono);
			}			
		}
		if(gf.getDomicilio().getAsentamiento().getNombre() != null){
			resp.setCOLONIA(gf.getDomicilio().getAsentamiento().getNombre());
		}
		/**
		 * TODO Es necesario colocar la busqueda de setConDerechoInc, setConDerechoSm 
		 */
		try {
			if(vigenciaDaoLocal.getDerechoINC(idModalidad)){
				resp.setCON_DERECHO_INC("SI");
			}else{
				resp.setCON_DERECHO_INC("NO");
			}
		} catch (Exception e) {
			log.debug("Error inesperado al validar derechos de servicio de incapacidad", e);
			resp.setCON_DERECHO_INC("NO");
		}
		try {
			if(vigenciaDaoLocal.getDerechoSM(idModalidad)){
				resp.setCON_DERECHO_SM("SI");
			}else{
				resp.setCON_DERECHO_SM("NO");
			}
		} catch (Exception e) {
			log.debug("Error inesperado al validar derechos de servicio medico", e);
			resp.setCON_DERECHO_SM("NO");
		}						
		
		if(registroPatronal != null){
			resp.setREGISTRO_PATRONAL(registroPatronal);
		}
		
		if(gf.getParentesco().getIdParentesco() == ParentescoEnum.PENSIONADO.getId()){
			AseguradoPension ap = null;
			try {
				ap = patronDaoLocal.getPensionado(patronDaoLocal.getIdAsegurado(asegurado.getAsignacionNSS(), asegurado.getPatronSujetoObligado().getCveIdSujetoObligado()));				
			} catch (Exception e) {
				log.debug("Error inesperado al buscar datos del pensionado", e);
			}
			if(ap != null){
				resp.setTIPO_PENSION(ap.getTipoPension().getMarcaPension());
			}else{
				resp.setTIPO_PENSION("");
			}
		}else{
			resp.setTIPO_PENSION("");
		}
		
		if(gf.getFechaFinVigencia() == null){			
			resp.setVIGENTE_HASTA(DateUtils.dateFormat_yyyy_MM_dd(new Date())+" 00:00:00.0");
		}else{			
			resp.setVIGENTE_HASTA(gf.getFechaFinVigencia().toString()+" 00:00:00.0");
		}
		if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico().toString() != null){
			resp.setDH_UMF(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico().toString());
		}
		if(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre() !=null){
			resp.setDH_DELEG(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre());
		}
		if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal()!=null) {
		if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal().getClavePresupuestal() != null){
			resp.setCLAVE_PRESUPUESTAL(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal().getClavePresupuestal());
		}
		}
		resp.setDH_IP_SERVER("254.254.254.254");
		
		resp.setCPID(cpidGeneric(cpidE, nss, curp, agregadoAfil, edoInt));
			
		return resp;
	}

	private Table3 llenaAsegurado(GrupoFamiliar gf, Table3 resp, String registroPatronal,CabezaGrupoFamiliar asegurado, String error){		
		long idModalidad = asegurado.getPatronSujetoObligado().getModalidad().getIdModalidad();
		
		if(gf.getAgregadoAfiliacion() != null){
			resp.setAgregadoAfiliacion(gf.getAgregadoAfiliacion());
		}
		
		if(gf.getAgregadoMedico() != null){
			resp.setAgregadoMedico(gf.getAgregadoMedico());
		}
		if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal() != null){
		if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal().getClavePresupuestal() != null){
			resp.setClavePresupuestal(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal().getClavePresupuestal());
		}
		}
		if(gf.getDomicilio().getAsentamiento().getNombre() != null){
			resp.setColonia(gf.getDomicilio().getAsentamiento().getNombre());
		}
		/**
		 * TODO Es necesario colocar la busqueda de setConDerechoInc, setConDerechoSm 
		 */
		try {
			if(vigenciaDaoLocal.getDerechoINC(idModalidad)){
				resp.setConDerechoInc("SI");
			}else{
				resp.setConDerechoInc("NO");
			}
		} catch (Exception e) {
			log.debug("Error al validar derechos de servicios de incapacidad", e);
			resp.setConDerechoInc("NO");
		}
		try {
			if(vigenciaDaoLocal.getDerechoSM(idModalidad)){
				resp.setConDerechoSm("SI");
			}else{
				resp.setConDerechoSm("NO");
			}
		} catch (Exception e) {
			log.debug("Error al validar derechos de servicio medico", e);
			resp.setConDerechoSm("NO");
		}
				
		/**
		 * **************************************************************************
		 */
		if(gf.getMedicoEnTurno().getConsultorio().getDescripcion() != null){
			resp.setConsultorio(gf.getMedicoEnTurno().getConsultorio().getDescripcion());
		}
		
		if(error == null){
			resp.setCpid(cpidNECE(Constants.MSG00));
		}else{
			resp.setCpid(cpidNECE(error));
		}
		
		if(gf.getDerechohabiente().getCurp() != null){
			resp.setCurp(gf.getDerechohabiente().getCurp());
		}
		if(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre() !=null){
			resp.setDhDeleg(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre());
		}
		
		resp.setDhIpServer(Constants.IPSERVER);
		if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico().toString() != null){
			resp.setDhUMF(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico().toString());
		}
		if(llenaDomicilio(gf.getDomicilio()) != null){
			resp.setDireccion(llenaDomicilio(gf.getDomicilio()));
		}
		if(gf.getDerechohabiente().getFechaNacimiento().toString() != null){
			resp.setFechaNacimiento(gf.getDerechohabiente().getFechaNacimiento().toString());
		}
		if(gf.getDerechohabiente().getExpedienteElectronico() != null){
			resp.setIdee(gf.getDerechohabiente().getExpedienteElectronico());
		}
		if(gf.getDerechohabiente().getSegundoApellido() != null){
			resp.setMaterno(gf.getDerechohabiente().getSegundoApellido());
		}
		if(gf.getDerechohabiente().getNombre() != null){
			resp.setNombre(gf.getDerechohabiente().getNombre());
		}
		if(gf.getAsignacionNSS().getNssStr() != null){
			resp.setNss(gf.getAsignacionNSS().getNssStr());
		}
		if(gf.getDerechohabiente().getPrimerApellido() != null){
			resp.setPaterno(gf.getDerechohabiente().getPrimerApellido());
		}
		if(registroPatronal != null){
			resp.setRegistroPatronal(registroPatronal);
		}
		
		if(gf.getDerechohabiente().getSexo().getIdSexo() == SexoEnum.HOMBRE.getId()){
			resp.setSexo("M");
		}else{
			resp.setSexo("F");
		}
		if(medioContacto(gf.getDerechohabiente().getIdPersona(), TipoContactoEnum.TELEFONO_FIJO.getId()).size() >= 0){
			for(String telefono :medioContacto(gf.getDerechohabiente().getIdPersona(), TipoContactoEnum.TELEFONO_FIJO.getId())){
				resp.setTelefono(telefono);
			}			
		}	
		if(gf.getParentesco().getIdParentesco() == ParentescoEnum.PENSIONADO.getId()){
			AseguradoPension ap = null;
			try {
				ap = patronDaoLocal.getPensionado(patronDaoLocal.getIdAsegurado(asegurado.getAsignacionNSS(), asegurado.getPatronSujetoObligado().getCveIdSujetoObligado()));				
			} catch (Exception e) {
				log.debug("Error inesperado al buscar datos del pensionado", e);
			}
			if(ap != null){
				resp.setTipoPension(ap.getTipoPension().getMarcaPension());
			}else{
				resp.setTipoPension("");
			}
		}else{
			resp.setTipoPension("");
		}
		
		
		if(gf.getMedicoEnTurno().getTurno().getIdTurno() == TurnoEnum.MATUTINO.getId()){
			resp.setTurno("M");
		}else{
			resp.setTurno("V");
		}		
		
		if(gf.getFechaFinVigencia() == null){			
			resp.setVigenteHasta(DateUtils.dateFormat_yyyy_MM_dd(new Date())+Constants.FTOFECHA);
		}else{			
			resp.setVigenteHasta(gf.getFechaFinVigencia().toString()+Constants.FTOFECHA);
		}
//		System.out.println("Salgo de llenar Asegurado");
		return resp;
	}
	
	private Beneficiarios llenaBeneficiario(GrupoFamiliar gf, Beneficiarios resp, String registroPatronal,CabezaGrupoFamiliar asegurado, String error){
//		System.out.println("Lleno Beneficiario");
		long idModalidad = asegurado.getPatronSujetoObligado().getModalidad().getIdModalidad();
		
		if(gf.getAgregadoAfiliacion() != null){
			resp.setAgregadoAfiliacion(gf.getAgregadoAfiliacion());
		}
		
		if(gf.getAgregadoMedico() != null){
			resp.setAgregadoMedico(gf.getAgregadoMedico());
		}
		if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal() != null) {
			if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal().getClavePresupuestal() != null){
				resp.setClavePresupuestal(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getClavePresupuestal().getClavePresupuestal());
			}
		}
		if(gf.getDomicilio().getAsentamiento().getNombre() != null){
			resp.setColonia(gf.getDomicilio().getAsentamiento().getNombre());
		}
		
		try {
			if(vigenciaDaoLocal.getDerechoINC(idModalidad)){
				resp.setConDerechoInc("SI");
			}else{
				resp.setConDerechoInc("NO");
			}
		} catch (Exception e) {
			log.debug("Error inesperado al validar derechos a servicio de incapacidad", e);
			resp.setConDerechoInc("NO");
		}
		try {
			if(vigenciaDaoLocal.getDerechoSM(idModalidad)){
				resp.setConDerechoSm("SI");
			}else{
				resp.setConDerechoSm("NO");
			}
		} catch (Exception e) {
			log.debug("Error inesperado al validaro derechos de servicio medico", e);
			resp.setConDerechoSm("NO");
		}
		
		if(gf.getMedicoEnTurno().getConsultorio().getDescripcion() != null){
			resp.setConsultorio(gf.getMedicoEnTurno().getConsultorio().getDescripcion());
		}
		
		if(error == null){
			resp.setCpid(cpidNECE(Constants.MSG00));
		}else{
			resp.setCpid(cpidNECE(error));
		}
		
		if(gf.getDerechohabiente().getCurp() != null){
			resp.setCurp(gf.getDerechohabiente().getCurp());
		}
		if(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre() !=null){
			resp.setDhDeleg(gf.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre());
		}
		
		resp.setDhIpServer(Constants.IPSERVER);
		if(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico().toString() != null){
			resp.setDhUMF(gf.getMedicoEnTurno().getUnidadMedicaFamiliar().getNoEconomico().toString());
		}
		if(llenaDomicilio(gf.getDomicilio()) != null){
			resp.setDireccion(llenaDomicilio(gf.getDomicilio()));
		}
		if(gf.getDerechohabiente().getFechaNacimiento().toString() != null){
			resp.setFechaNacimiento(gf.getDerechohabiente().getFechaNacimiento().toString());
		}
		if(gf.getDerechohabiente().getExpedienteElectronico() != null){
			resp.setIdee(gf.getDerechohabiente().getExpedienteElectronico());
		}
		if(gf.getDerechohabiente().getSegundoApellido() != null){
			resp.setMaterno(gf.getDerechohabiente().getSegundoApellido());
		}
		if(gf.getDerechohabiente().getNombre() != null){
			resp.setNombre(gf.getDerechohabiente().getNombre());
		}
		if(gf.getAsignacionNSS().getNssStr() != null){
			resp.setNss(gf.getAsignacionNSS().getNssStr());
		}
		if(gf.getDerechohabiente().getPrimerApellido() != null){
			resp.setPaterno(gf.getDerechohabiente().getPrimerApellido());
		}
		if(registroPatronal != null){
			resp.setRegistroPatronal(registroPatronal);
		}
		
		if(gf.getDerechohabiente().getSexo().getIdSexo() == SexoEnum.HOMBRE.getId()){
			resp.setSexo("M");
		}else{
			resp.setSexo("F");
		}
		if(medioContacto(gf.getDerechohabiente().getIdPersona(), TipoContactoEnum.TELEFONO_FIJO.getId()).size() >= 0){
			for(String telefono :medioContacto(gf.getDerechohabiente().getIdPersona(), TipoContactoEnum.TELEFONO_FIJO.getId())){
				resp.setTelefono(telefono);
			}			
		}	
				
		resp.setTipoPension("");
		
		if(gf.getMedicoEnTurno().getTurno().getIdTurno() == TurnoEnum.MATUTINO.getId()){
			resp.setTurno("M");
		}else{
			resp.setTurno("V");
		}		
		
		if(gf.getFechaFinVigencia() == null){			
			resp.setVigenteHasta(DateUtils.dateFormat_yyyy_MM_dd(new Date())+Constants.FTOFECHA);
		}else{			
			resp.setVigenteHasta(gf.getFechaFinVigencia().toString()+Constants.FTOFECHA);
		}
//		System.out.println("Salgo de llenar Beneficiario");
		return resp;
	}
	
	private List<String> medioContacto(long idPersona, long tipoContacto){		
		List<String> medios = new ArrayList<String>(); 
		medios.add(Constants.SINDATOS);
		try {
			medios = grupoFamiliarDaoLocal.getMedioContacto(idPersona, tipoContacto);			
		} catch (DerechohabientesBusinessException e) {
			medios.add(Constants.SINDATOS);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return medios;
	}
	
	private String llenaDomicilio(Domicilio dom){
		String vialidadP = "";
		String numExt1 = "";
		String numExt2 = "";
		String numInt = "";
		String localidad ="";
		String municipio = "";
		String entidad = "";
		String cp = "";
		
		if(dom.getVialidadPrimaria().getNombre() != null){
			vialidadP = dom.getVialidadPrimaria().getNombre();
		}
		
		if(dom.getNumExteriorAlf() != null){
			numExt1 = dom.getNumExteriorAlf();
		}
		
		if(dom.getNumExterior2() != null){
			numExt2 = dom.getNumExterior2().toString();
		}
		
		if(dom.getNumInteriorAlf() != null){
			numInt = dom.getNumInteriorAlf();
		}
		
		if(dom.getAsentamiento().getLocalidad().getNombre() != null){
			localidad = dom.getAsentamiento().getLocalidad().getNombre();
		}
		
		if(dom.getAsentamiento().getLocalidad().getMunicipio().getNombre() != null){
			municipio = dom.getAsentamiento().getLocalidad().getMunicipio().getNombre();
		}
		
		if(dom.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre() != null){
			entidad = dom.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre();
		}
		
		if(dom.getAsentamiento().getCodigoPostal() != null){
			cp = dom.getAsentamiento().getCodigoPostal().toString();
		}
		String domStr = vialidadP+" "+ 
		numExt1+" "+
		numExt2+" "+
		numInt+" "+
		localidad+" "+
		municipio+" "+
		entidad+" "+
		cp;
		return domStr;
	}
	private String cpidNECE(String codigo){
		String miCPID = null;
		Date hoy = new Date();
		String fechaActStr = DateUtils.dateFormatCustom(hoy, "dd/mm/yyyy");		
		
		miCPID = "<"+fechaActStr.substring(6,10) + "O>-"+DateUtils.dayOfYear(hoy);		
		miCPID = miCPID.concat("-B"+DateUtils.hora(hoy)+DateUtils.minutos(hoy)+DateUtils.segundos(hoy)+"X-");
		miCPID = miCPID.concat("000000000000000000-");
		miCPID = miCPID.concat(codigo);
		
		return miCPID;		
	}
	
	private String cpidGeneric(String cpidE, String nss, String curp, String agregadoAfil,long edoInt){
		String miCPID = null;
		Date hoy = new Date();
		String fechaActStr = DateUtils.dateFormatCustom(hoy, "dd/mm/yyyy");		
		
		miCPID = cpidE != null && cpidE.length() > 1 ? cpidE.substring(0,1) : "";
		miCPID = miCPID.concat(fechaActStr.substring(6,10));
		miCPID = miCPID.concat(cpidE.length() > 1 ? cpidE.substring(1, 1) : "");
		miCPID = miCPID.concat("-"+DateUtils.dayOfYear(hoy)+"-");
		miCPID = miCPID.concat("B"+DateUtils.hora(hoy)+DateUtils.minutos(hoy)+DateUtils.segundos(hoy)+"D");
		if(agregadoAfil == null){
			agregadoAfil="";
		}
		if(nss.length()> 0 && agregadoAfil.length()>0){
			miCPID = miCPID.concat("N-000000000000000000-");
		}else if(curp.length() > 0){
			miCPID = miCPID.concat("C-000000000000000000-");
		}else{
			miCPID = miCPID.concat("X-000000000000000000-");
		}
		
		if(edoInt == EstadoDerechohabienteEnum.VIGENTE.getId() || edoInt == EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId() ||
			edoInt == EstadoDerechohabienteEnum.PENSION_TRAMITE.getId() || edoInt == EstadoDerechohabienteEnum.CON_DERECHO.getId()){			
			miCPID = miCPID.concat("1");			
		}else if(edoInt == EstadoDerechohabienteEnum.FALLECIDO.getId() || edoInt == EstadoDerechohabienteEnum.BAJA.getId()){
			miCPID = miCPID.concat("0");
		}
		
		return miCPID;		
	}
	
	private AsignacionNSS buscaAsigNSS(String nss) throws Exception{
		AsignacionNSS asignacionNSS;
		asignacionNSS = grupoFamiliarDaoLocal.getAsignacionNss(nss);
		return asignacionNSS;
	}
	private CabezaGrupoFamiliar buscaAsegurado(long idAsignacionNSS) throws Exception{
		return grupoFamiliarDaoLocal.getCabezaGrupoFamiliar(idAsignacionNSS);		
	}
	private String buscaRegistroPat(long idPatronSujetoObligado) throws Exception{
		return patronDaoLocal.getRegistroPatronal(idPatronSujetoObligado);
	}
	private List<GrupoFamiliar> buscaIntegrantes(long idAsignacionNSS) throws Exception{
		List<Long> estados = new ArrayList<Long>();
		estados.add(EstadoDerechohabienteEnum.VIGENTE.getId());
		estados.add(EstadoDerechohabienteEnum.CON_DERECHO.getId());
		estados.add(EstadoDerechohabienteEnum.CONSERVACION_DERECHOS.getId());
		return grupoFamiliarDaoLocal.findGrupoFamiliarByEstado(idAsignacionNSS,estados);	
	}

}
