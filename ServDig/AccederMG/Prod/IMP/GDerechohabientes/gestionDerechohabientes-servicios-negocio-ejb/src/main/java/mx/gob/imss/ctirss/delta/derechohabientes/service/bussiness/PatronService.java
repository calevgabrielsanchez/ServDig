package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.PatronDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.UmfCodigoPostalDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.PatronServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.RegistroDerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.AseguradoPension;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.apache.log4j.Logger;


/**
 * @author Juan Manuel Marquez 
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 11/04/2012
 */
@Stateless( name = "patronService", mappedName = "patronService")
public class PatronService extends AbstractServiceBusiness implements PatronServiceRemote{

	
	@EJB
	private PatronDaoLocal patronDao;
	@EJB (name="sujetoObligadoServiceBusiness" ,mappedName="sujetoObligadoServiceBusiness") SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusinessRemote; 
	@EJB(name = "umfCodigoPostalDAO") UmfCodigoPostalDaoLocal umfCodigoPostalDaoLocal;
	@EJB GrupoFamiliarServiceRemote grupoFamiliarService;
	@EJB RegistroDerechohabienteServiceRemote registroDerechohabienteService;
	
	
	
	private static final Logger logger = Logger.getLogger(PatronService.class);
	
	@Override
	public boolean getPatronIMSS(SujetoObligado unPatronSujetoObligado) throws DerechohabientesBusinessException,Exception {
		boolean imss = false;
		String miModalidad="";
		String unRegistroPatronal = null;						
		
		if(unPatronSujetoObligado != null){
			
			miModalidad = patronDao.getModalidad(unPatronSujetoObligado.getModalidad().getIdModalidad());
			
			unRegistroPatronal = getRegistroPatronalSinDV(unPatronSujetoObligado.getCveIdSujetoObligado()) + "" + miModalidad;
			if(unRegistroPatronal != null && !miModalidad.equals("")){
				if(unRegistroPatronal.equals("0105112900") || 
						unRegistroPatronal.equals("0105112910")){
					imss = true;
				}					
				else if((miModalidad.equals("10") || miModalidad.equals("00")) && 
						unRegistroPatronal.substring(4, 8).equals("99995")){
							imss = true;
				}else if(miModalidad.equals("10") && unRegistroPatronal.substring(4, 8).equals("99998") ) {
							imss = true;
				}
				
			}
				
		}			
		
		
		return imss;
	}

	@Override
	public String getRegistroPatronal(long idPatronSujetoObligado) throws Exception {
		String registroPatronal = patronDao.getRegistroPatronal(idPatronSujetoObligado);
		return registroPatronal;
	}
	
	@Override
	public String getRegistroPatronalSinDV(long idPatronSujetoObligado) throws DerechohabientesBusinessException, Exception {
		String registroPatronal = patronDao.getRegistroPatronalSinDV(idPatronSujetoObligado);
		return registroPatronal;
	}

	@Override
	public boolean getPensionado(long idAsegurado) throws DerechohabientesBusinessException,Exception {
		boolean pension = false;
		AseguradoPension asegPension = null;
		asegPension = patronDao.getPensionado(idAsegurado);			
		
		if(asegPension != null){
			pension = true;
		}		
		return pension;
	}

	@Override
	public List<Asegurado> getAseguradoList(long idAsignacionNss) throws DerechohabientesBusinessException,Exception {
		List<Asegurado> listAseg = null;
		
		listAseg = patronDao.getAseguradoList(idAsignacionNss);
						
		return listAseg;
	}

	private String getDelegacion(String codigoPostal) throws Exception{
		String respuesta = null;		
			List<UnidadMedicaFamiliar> umfs = umfCodigoPostalDaoLocal.getUmfByCodigoPostal(codigoPostal);
			if(umfs != null){
				for(UnidadMedicaFamiliar umf : umfs){
					respuesta = umf.getSubdelegacion().getDelegacion().getClave();
				}
			}				
		return respuesta;
		
	}

	@Override
	public boolean circunscripcionAsegurado(String codigoPostalAseg,
			SujetoObligado unPatronSujetoObligado) throws DerechohabientesBusinessException,Exception {
		boolean correcto = false;	
		String delAsegurado = null;
		String delPatron = null;
		SujetoObligado so = sujetoObligadoServiceBusinessRemote.obtenerDetalleRP(unPatronSujetoObligado);
		if(so == null || so.getCntroTrabajo() == null){
			correcto = false;
		}else{			
			delAsegurado = getDelegacion(codigoPostalAseg);
			delPatron = getDelegacion(so.getCntroTrabajo().getCodigoPostal().getCodigoPostal().toString());				
					
			if(delAsegurado !=null && delPatron != null){
				if(delPatron.equals(Constants.DEL_QUINCE) && delPatron.equals(Constants.DEL_DIECISEIS) &&
					delPatron.equals(Constants.DEL_TREINTAYNUEVE) && delPatron.equals(Constants.DEL_CUARENTA)){
					correcto = true;
				}else
					if(delPatron.equals(delAsegurado)){
						correcto = true;
					}
			}
		}
		return correcto;
	}
	
	@Override
	public Asegurado getAsegurado( Long idAsignaccionNSS, Long cveIdPatronSujeroObligado ) throws DerechohabientesBusinessException, Exception{
		return patronDao.getAsegurado(idAsignaccionNSS, cveIdPatronSujeroObligado);
	}

//	@Override
//	public String calculaAgregadoMedico(AsignacionNSS an, RegistroDto registro)
//			throws DerechohabientesBusinessException, Exception {
//		String agregadoMedico = null;
//		Asegurado asegurado = null;
//	//	boolean pensionado = false;
//		String year = null;
//		String fechaS = null;
//		String valorB = null;
//		String registroPatronal = null;
//		SujetoObligado miPatronSujeto = null;
//		String modalidad = null;
//		
//		asegurado = getAsegurado(an.getIdAsignacionNSS());
//						
//		if(!registro.getRegistro().getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId())){
//			if(registro.getRegistro().getParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId())){
//				agregadoMedico="1";
//			}
//			if(registro.getRegistro().getParentesco().getIdParentesco().equals(ParentescoEnum.CONYUGE.getId())){
//				agregadoMedico="2";
//			}
//			if(registro.getRegistro().getParentesco().getIdParentesco().equals(ParentescoEnum.HIJOS.getId())){
//				agregadoMedico="3";
//			}
//			if(registro.getRegistro().getParentesco().getIdParentesco().equals(ParentescoEnum.PADRES.getId())){
//				agregadoMedico="2";
//			}
//			if(registro.getRegistro().getParentesco().getIdParentesco().equals(ParentescoEnum.CONCUBINARIO.getId())){
//				agregadoMedico="";
//			}
//				
//			registroPatronal = getRegistroPatronal(asegurado.getSujetoObligado().getCveIdSujetoObligado());
//			miPatronSujeto = getPatronSujetoObligado(an.getIdAsignacionNSS());
//			if(miPatronSujeto != null){
//				modalidad = miPatronSujeto.getModalidad().getNumModalidad();
//			}
//			
//			if(registroPatronal.substring(3, 8).equals("99990") || registroPatronal.substring(3, 8).equals("99991") ||
//			   registroPatronal.substring(3, 8).equals("99992") || registroPatronal.substring(3, 8).equals("99993")){
//				if(modalidad == "32"){
//					valorB="SA";
//				}
//			}
//			
//			if(!registroPatronal.substring(3, 8).equals("99990") && !registroPatronal.substring(3, 8).equals("99991") &&
//			   !registroPatronal.substring(3, 8).equals("99992") && !registroPatronal.substring(3, 8).equals("99993")){
//					if(modalidad == "32"){
//						valorB="ES";
//					}else{					
//						if(modalidad.equals("14")){
//							valorB="EC";
//						}
//						if(modalidad.equals("10") || modalidad.equals("13") || modalidad.equals("30")
//						   || modalidad.equals("34") || modalidad.equals("35") || modalidad.equals("36")
//						   || modalidad.equals("37") || modalidad.equals("42") || modalidad.equals("43")
//						   || modalidad.equals("44") || modalidad.equals("38")){
//							valorB="OR";
//						}										
//						
//						if(modalidad.equals("00") || modalidad.equals("11") || modalidad.equals("15") ||
//						   modalidad.equals("17") || modalidad.equals("29") || modalidad.equals("31") ||
//						   modalidad.equals("19") || modalidad.equals("21") || modalidad.equals("16") ||
//						   modalidad.equals("18") || modalidad.equals("20") || modalidad.equals("40") ||
//						   modalidad.equals("27") || modalidad.equals("28")){
//							valorB="PE";
//						}
//						
//						if(modalidad.equals("32")){
//							valorB="SA";
//						}
//						if(modalidad.equals("33")){
//							valorB="SF";
//						}																
//					}
//			}		
//		}else{
//			if(registro.getRegistro().getParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId()) || 
//			   registro.getRegistro().getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId())){
//				agregadoMedico="5";
//			}else{
//				agregadoMedico="6";
//			}
//			valorB = "PE";
//		}
//		if(registro.getRegistro().getFisica().getSexo().getIdSexo().equals(SexoEnum.HOMBRE.getId())){
//			agregadoMedico = agregadoMedico.concat("2");
//		}else{
//			agregadoMedico = agregadoMedico.concat("1");
//		}
//		fechaS = DateUtils.dateToStringConFormato(registro.getRegistro().getFisica().getFechaNacimiento(), "dd/mm/yyyy");
//		year = fechaS.substring(6,10);
//		agregadoMedico = agregadoMedico.concat(year);
//		agregadoMedico = agregadoMedico.concat(valorB);
//				
//		return agregadoMedico;
//	}
	
	/**
	 * Consulta que valida si un patron se encuentra en la tabla de patrones de instituciones educativas
	 * @param cveNRP
	 * @return
	 * @throws DerechohabientesBusinessException
	 */
	@Override
	public boolean isRegistroPatronalnstitucionEducativa(String cveNRP) {
		return patronDao.isRegistroPatronalnstitucionEducativa(cveNRP); 
	}
	
	
}
