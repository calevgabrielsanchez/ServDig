/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.individuo.service.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.CalculoPorcentajeProbabilidadUtility;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.CalculoPorcentajeProbabilidadUtilityLocal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Candidato;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessLocal;

import org.apache.commons.lang.StringUtils;

/**
 * 130912
 * @author Samuel Rodr�guez Grajeda
 *
 */
@Stateless(name = "consultaPersonaMoralServiceBusiness", mappedName = "consultaPersonaMoralServiceBusiness")
public class ConsultaPersonaMoralServiceBusiness extends AbstractServiceBusiness implements ConsultaPersonaMoralServiceBusinessRemote {
	
	@EJB
	private PersonaMoralBusinessLocal personaMoralBusiness;
	
	@EJB
	private CalculoPorcentajeProbabilidadUtilityLocal calculoPorcentajeProbabilidadUtility;

	@EJB
	private LocalizarPersonaMoralEnEntidadesExternasServiceBusinessRemote localizarPersonaMoralEnEntidadesExternasServiceBusiness;
	
	@EJB
	private PersonaBusinessRemote personaBusiness;
	
	@EJB
	IndividuoServiceBusinessRemote individuoServiceBusiness;
	/**
	 * 
	 * @param moral
	 * @return
	 */
	@Override
	public List<Candidato> consultarPersonaMoral(Moral moral){
		LinkedList<Candidato> candidatos = new LinkedList<Candidato>();
		Map<Long , Candidato> mapCandidatos = new HashMap<Long, Candidato>();
		
		
		//Consultamos segundo por RFC en el IMSS
		List<Candidato> c2 = this.consultarPorRFCEnIMSS(moral);
		candidatos.addAll(c2);
		
		//Consultamos tercero por DB en el IMSS.
		/*
		 * Parche para que funcione la consulta por datos basicos 
		 * solo la razon social
		 */
		
		if(StringUtils.isNotEmpty(moral.getRazonSocial())){
			Moral mdummy= new Moral();
			mdummy.setRazonSocial(moral.getRazonSocial());
			
			List<Candidato> c3 = this.consultarPorDatosBasicosEnIMSS(mdummy);
			
			
			candidatos.addAll(c3);
		}	
		this.log.debug("Numero de registros de los candidatos :" + candidatos.size());
		
		for( Candidato c : candidatos){
			this.log.debug("Candidatos resultados de las 3 busquedas::" + c.getPersona().getIdPersona());
			Candidato cf = mapCandidatos.get(c.getPersona().getIdPersona());
			
			if(cf == null){
				this.log.debug("El candidato no existe en el mapa , lo agregamos." + c.getPersona().getIdPersona());
				mapCandidatos.put(c.getPersona().getIdPersona(), c);
			}else{
				this.log.debug("El candidato ya existe en el mapa, sumamos probabilidades:" + cf.getProbabilidad());
				Long p = cf.getProbabilidad();
				p = p + c.getProbabilidad();
				cf.setProbabilidad(p);
				this.log.debug("% De probabilidad del candidato " + p + "-"+ cf.getPersona().getIdPersona());
			}
			
		}
		
		candidatos = new LinkedList<Candidato>();
		for (Map.Entry<Long, Candidato> entry : mapCandidatos.entrySet()) {
			candidatos.add(entry.getValue());
		}
		
		return candidatos;
	}
	
		
	/**
	 * 
	 * @param moral
	 * @return
	 */
	private List<Candidato> consultarPorRFCEnIMSS(Moral moral){
		List<Moral> personas = null;
		List<Candidato> candidatos = new ArrayList<Candidato>();
		String rfc = moral.getRfc();
		personas = this.personaMoralBusiness.buscarPersonaMoralPorRfcEnImss(rfc);
		if(personas != null){
			for(Moral m : personas){
				Candidato c = new Candidato();
				Long probabilidad = this.calculoPorcentajeProbabilidadUtility.calcularProbabilidadDeCandidato(m, CalculoPorcentajeProbabilidadUtility.PESO_RFC);
				c.setPersona(m);
				c.setProbabilidad(probabilidad);
				candidatos.add(c);
			}
		}
		return candidatos;
	}
	
	/**
	 * 
	 * @param moral
	 * @return
	 */
	private List<Candidato> consultarPorDatosBasicosEnIMSS(Moral moral){
		List<Moral> personas = null;
		List<Candidato> candidatos = new ArrayList<Candidato>();
		personas = this.personaMoralBusiness.getPersonaMoral(moral);
		if(personas != null){
			for(Moral m : personas){
				Candidato c = new Candidato();
				Long probabilidad = this.calculoPorcentajeProbabilidadUtility.calcularProbabilidadDeCandidato(m, CalculoPorcentajeProbabilidadUtility.PESO_IMSS);
				c.setPersona(m);
				c.setProbabilidad(probabilidad);
				candidatos.add(c);
			}
		}
		return candidatos;
	}


	@Override
	public Moral consultarPersonaMoralPorRFCEnIMSSySAT(Moral moral) 
			throws ClienteWebserviceSatRfcException, PersonasNoLocalizadasException
			 {
		Moral personaMejorCalif=null;
		try {
			personaMejorCalif = individuoServiceBusiness.consultarPersonaMoralIMSSPorRFC(moral);
		} catch (PersonasNoLocalizadasException e) {
			personaMejorCalif=personaBusiness.buscarPersonaMoralPorRfcEnSat(moral.getRfc());
		}
		
		if(personaMejorCalif==null)
			throw new PersonasNoLocalizadasException("La persona no fue encontrada en la entidad externa SAT");
		
		return personaMejorCalif;
	}
	
	@Override
	public Moral consultarPersonaMoralPorRFCEnIMSSySAT_AP(Moral moral) 
			throws ClienteWebserviceSatRfcException, PersonasNoLocalizadasException
			 {
		Moral personaMejorCalif=null;
		try {
			personaMejorCalif = individuoServiceBusiness.consultarPersonaMoralIMSSPorRFC_AP(moral);
		} catch (PersonasNoLocalizadasException e) {
			personaMejorCalif=personaBusiness.buscarPersonaMoralPorRfcEnSat(moral.getRfc());
		}
		
		if(personaMejorCalif==null)
			throw new PersonasNoLocalizadasException("La persona no fue encontrada en la entidad externa SAT");
		
		return personaMejorCalif;
	}
	
		
}
