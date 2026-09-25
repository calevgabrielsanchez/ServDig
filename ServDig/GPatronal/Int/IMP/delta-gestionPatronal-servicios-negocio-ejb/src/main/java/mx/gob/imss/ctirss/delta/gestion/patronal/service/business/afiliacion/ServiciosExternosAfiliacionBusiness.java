package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.afiliacion;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.reing.patrones.ValidaExistePatronServiceService;



@Stateless(name = "serviciosExternosAfiliacionBusiness", mappedName = "serviciosExternosAfiliacionBusiness")
public class ServiciosExternosAfiliacionBusiness extends AbstractServiceBusiness implements
	ServiciosExternosAfiliacionBusinessLocal {
	
	
	
	/**
	 * Metodo que consume un webservices para consultar si existe un patron con mismos nombre municipio modalidad
	 * y clasificación en la base de datos de REING
	 * @return
	 */
	@Override
	public boolean consultaPatronPorNombreModalidadPrimaMunicipioIMSSenReing(SujetoObligado sujetoObligado){
		boolean existePatron = false;
		ValidaExistePatronServiceService objAdmonUsuariosSession = new ValidaExistePatronServiceService();
		try{
			String nombre = null;
			String apePaterno = null;
			String apeMaterno = null;
			String descSociedad = null;
			int tipoPersona = 0;
			String municipioImss = sujetoObligado.getMunicipioIMSS().getCvecMunicipioSINDO();
			int division = Integer.parseInt(sujetoObligado.getClasificacion().getFraccion().getGrupo().getDivision().getNumDivision().trim());
			int grupo = Integer.parseInt(sujetoObligado.getClasificacion().getFraccion().getGrupo().getNumGrupo().trim());
			int fraccion = Integer.parseInt(sujetoObligado.getClasificacion().getFraccion().getNumFraccion().trim());
			int modalidad = Integer.parseInt(sujetoObligado.getModalidad().getNumModalidad().trim());
			int clase = sujetoObligado.getClasificacion().getFraccion().getClase().getClave().intValue();
			boolean marcalClase =false;
			/*
			if(sujetoObligado.getClasificacion().getIndRegPatClase()==1){
				marcalClase =true;	
			}
			*/
			
			String rfc = null;
			if(sujetoObligado.getFisica() != null){
				Fisica objFisica = sujetoObligado.getFisica();
				nombre = objFisica.getNombre();
				apePaterno = objFisica.getPrimerApellido();
				apeMaterno = objFisica.getSegundoApellido();
				tipoPersona = 2;
				rfc = objFisica.getRfc();
				
			}else{
				Moral objMoral = sujetoObligado.getMoral();
				nombre = objMoral.getRazonSocial();
				tipoPersona = 2;
				descSociedad = objMoral.getTipoSociedad().getDescripcionAbreviada();
				rfc = objMoral.getRfc();
				
			}
			this.log.debug("los datos de la consulta son ["+ nombre
					+"][" +apePaterno+ "][" +apeMaterno +"][" +apeMaterno+ "][" +descSociedad+"]["+
					municipioImss+"]["+tipoPersona+"]["+division+"]["+grupo+"]["+fraccion
					+"]["+modalidad+"]["+clase+"]["+marcalClase+"]["+rfc+"]");
						
			objAdmonUsuariosSession.getValidaExistePatronServicePort().validaExistePatron(nombre, apePaterno, 
					apeMaterno, descSociedad, municipioImss, tipoPersona, division, grupo, 
					fraccion, fraccion, clase, marcalClase, rfc);
			
		}catch(Exception ex){
			this.log.error("estoy en la consulta en REING", ex);
			
		} 
	
		
		return existePatron;
	};

	
	
}