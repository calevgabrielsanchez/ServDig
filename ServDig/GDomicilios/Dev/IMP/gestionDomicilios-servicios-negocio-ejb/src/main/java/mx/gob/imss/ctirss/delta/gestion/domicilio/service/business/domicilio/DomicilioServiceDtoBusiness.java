/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.domicilio.service.business.domicilio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.domicilio.MunicipioImssNoLocalizadoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.entity.domicilio.DomicilioServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.dto.DomicilioServiceDtoRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.enums.AreaGeograficaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.persistence.DicMunicipioImss;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.domicilio.EntidadFederativa;
import mx.gob.imss.digital.modelo.domicilio.Localidad;
import mx.gob.imss.digital.modelo.domicilio.Municipio;
import mx.gob.imss.digital.modelo.domicilio.Vialidad;

/**
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "domicilioServiceDtoBusiness", mappedName = "domicilioServiceDtoBusiness")
public class DomicilioServiceDtoBusiness implements DomicilioServiceDtoRemote {

    /**
     * Servicio para la consulta de domicilios
     */
    @EJB
    private DomicilioServiceEntityLocal entity;
    /**
     * Bean de cnsultas directas
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    protected EntityManager em;
    
    private Logger LOGGER = LoggerFactory.getLogger(DomicilioServiceDtoBusiness.class);
    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio
     * .DomicilioServiceDtoRemote
     * #consultarDomicilio(mx.gob.imss.digital.modelo.persona.Persona)
     */
    @Override
    public Domicilio consultarDomicilio(Long idPersona) {
        List<Long> tiposDomicilio = new ArrayList<Long>();
        tiposDomicilio.add(TipoDomicilioEnum.PARTICULAR.getId());
        mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona personaModel = new mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona();
        personaModel.setIdPersona(idPersona);
        List<mx.gob.imss.ctirss.delta.model.domicilio.Domicilio> domicilios = null;
        try {
            domicilios = this.entity.consultarDomiciliosPersonaFisicaPorTipo(personaModel,
                    tiposDomicilio);
        } catch (Exception e) {
            domicilios = new ArrayList<mx.gob.imss.ctirss.delta.model.domicilio.Domicilio>();
        }

        Domicilio domicilio = new Domicilio();
        if (!domicilios.isEmpty()) {
            mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioModelo = domicilios.get(0);
                        
            domicilio.setCodigoPostal(domicilioModelo.getCodigoPostal().getCodigoPostal());
            domicilio.setIdDomicilio(domicilioModelo.getClave().longValue());
            domicilio.setCalle(domicilioModelo.getCalle());
            domicilio.setColonia(domicilioModelo.getColonia());
            domicilio.setNumExterior1(domicilioModelo.getNumExterior1());
            domicilio.setNumExterior2(domicilioModelo.getNumExterior2());
            domicilio.setNumExteriorAlf(domicilioModelo.getNumExteriorAlf());
            domicilio.setNumInterior(domicilioModelo.getNumInterior());
            domicilio.setNumInteriorAlf(domicilioModelo.getNumInteriorAlf());
            
            domicilio.setLatitud(domicilioModelo.getLatitud());
            domicilio.setLongitud(domicilioModelo.getLongitud());
            
            if(domicilioModelo.getVialidadPrimaria() != null) {
                domicilio.setVialidadPrimaria(new Vialidad());
                domicilio.getVialidadPrimaria().setNombre(domicilioModelo.getVialidadPrimaria().getNombre());
            }
            if(domicilioModelo.getAsentamiento() != null) {
                domicilio.setColonia(domicilioModelo.getAsentamiento().getNombre());
            }
            mx.gob.imss.ctirss.delta.model.domicilio.Localidad localidad = domicilioModelo
                    .getLocalidad() != null ? domicilioModelo.getLocalidad() : domicilioModelo
                    .getAsentamiento().getLocalidad();                   
            if (localidad != null) {
                domicilio.setLocalidad(new Localidad());
                domicilio.getLocalidad().setClave(localidad.getClave());
                domicilio.getLocalidad().setNombre(localidad.getNombre());
                mx.gob.imss.ctirss.delta.model.domicilio.Municipio municipio = domicilioModelo
                        .getAsentamiento().getMunicipio() != null ? domicilioModelo
                        .getAsentamiento().getMunicipio() : localidad.getMunicipio();

                if (municipio != null) {
                    domicilio.getLocalidad().setMunicipio(new Municipio());
                    domicilio.getLocalidad().getMunicipio().setClave(municipio.getClave());
                    domicilio.getLocalidad().getMunicipio().setNombre(municipio.getNombre());
                    if (municipio.getEntidadFederativa() != null) {
                        domicilio.getLocalidad().getMunicipio()
                                .setEntidadFederativa(new EntidadFederativa());
                        domicilio.getLocalidad().getMunicipio().getEntidadFederativa()
                                .setClave(municipio.getEntidadFederativa().getClave());
						domicilio.getLocalidad().getMunicipio().getEntidadFederativa()
								.setNombre(municipio.getEntidadFederativa().getNombre());
                    }
                    try {
                        List<MunicipioIMSS> municipiosImss = entity.getMunicipioIMSSbyEstadoMunCP(municipio, 
                                domicilioModelo.getCodigoPostal().getCodigoPostal());
                        MunicipioIMSS municipioImss = municipiosImss.get(0);
                        DicMunicipioImss dicMunicipio = em.find(DicMunicipioImss.class, Long.valueOf(
                                municipioImss.getIdMunicipio()));
                        Long idArea = dicMunicipio.getDicAreaGeografica().getCveIdAreaGeografica();
                        domicilio.setDescripcion(AreaGeograficaEnum.geFromId(idArea.intValue()).getClave());
                    } catch (Exception e) {
                    	LOGGER.debug("Ocurrió un error en consultarDomicilio(Long idPersona) : "+e);
                    }
                }

            }
            

        } else {
            throw new RuntimeException("El domicilio no ha sido localizado");
        }
        return domicilio;
    }
    
    /**
	 * Metodo que recupera un lista municipios imss junto con la subdelegacion y delegacion
	 * @param cveEnt
	 * @param cveMun
	 * @return
	 * @throws MunicipioImssNoLocalizadoException
	 */
	@Override
	public String getAreaGeograficaDeMunicipioIMSSbyEstadoMunCP(String cveEnt, String cveMun, String codigoPostal){
		
		if(cveEnt == null || cveMun == null || codigoPostal == null ){
			throw new IllegalArgumentException();
		}
		
//		mx.gob.imss.ctirss.delta.model.domicilio.Municipio mun= new mx.gob.imss.ctirss.delta.model.domicilio.Municipio();
//		mun.setEntidadFederativa(new  mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa() );
//		mun.setClave(cveMun);
//		mun.getEntidadFederativa().setClave(cveEnt);
//
//		String areaGeografica = AreaGeograficaEnum.ZONA_A.getClave();
//		try {
//			MunicipioIMSS municipioImss = this.entity.getMunicipioIMSSbyEstadoMunCP(mun, codigoPostal).get(0);
//			DicMunicipioImss dicMunicipio = em.find(DicMunicipioImss.class, Long.valueOf(
//                    municipioImss.getIdMunicipio()));
//            Long idArea = dicMunicipio.getDicAreaGeografica().getCveIdAreaGeografica();
//            areaGeografica = AreaGeograficaEnum.geFromId(idArea.intValue()).getClave();
//		} catch (MunicipioImssNoLocalizadoException e) {
//			LOGGER.debug("Ocurrió un error en getAreaGeograficaDeMunicipioIMSSbyEstadoMunCP(String cveEnt, String cveMun, String codigoPostal): "+e);
//		}
        String areaGeografica = cveEnt+":"+cveMun;
		
		return areaGeografica;
	}

    @Override
    public String getZonaSalarialByIdSeguro(Long idSeguro) {

        StringBuffer q = new StringBuffer();

        q.append("SELECT DISTINCT TO_CHAR(MII.CVE_ENT,'FM00') || ':' || TO_CHAR(MII.CVE_MUN,'FM000') DUPLA_INEGI ")
                .append("FROM DIT_SEGURO_ESP_MUNICIPIO SEM ")
                .append("INNER JOIN DIT_MUNICIPIO_IMSS_INEGI MII ON SEM.CVE_ID_MUNICIPIO_IMSS = MII.CVE_ID_MUNICIPIO_IMSS ")
                .append("WHERE SEM.CVE_ID_SEGURO_IVRO = :idSeguro ");
        try {

            Query query = em.createNativeQuery(q.toString());
            query.setParameter("idSeguro", idSeguro);
            List zonaSalList = query.getResultList();

            Object resultado  = (Object) zonaSalList.get(0);
            if(resultado instanceof String){
                LOGGER.info("El resultado es "+ resultado);
                return (String) resultado;
            }else{
                LOGGER.info("El resultado no es String");
            }

        }catch (NoResultException e) {
            LOGGER.error("No se encontro Zona Salarial para el idSeguro {}",idSeguro);
        } catch (NonUniqueResultException n) {
            LOGGER.error("Se encontro mas de una Zona Salarial para el idSeguro {}",idSeguro);
        } catch (Exception e) {
            LOGGER.error("ERROR al ejecutar el QUERY: ",e);
        }

        return null;
    }

}
