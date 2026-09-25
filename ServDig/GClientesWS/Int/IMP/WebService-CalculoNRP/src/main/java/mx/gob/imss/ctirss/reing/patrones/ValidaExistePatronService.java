/**
 * 
 */
package mx.gob.imss.ctirss.reing.patrones;

import java.util.Iterator;
import java.util.List;
import javax.persistence.Query;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.reing.patrones.entity.AptRegistroPatronal;
import mx.gob.imss.ctirss.reing.patrones.entity.SspaPatrone;
import mx.gob.imss.ctirss.reing.patrones.util.ArpUtility;
import mx.gob.imss.ctirss.reing.patrones.util.Constantes;

/**
 * @author jonathan sanchez montiel
 *
 */
@WebService
@Stateless(name="ValidaExistePatronService")
public class ValidaExistePatronService implements ValidaExistePatronRemote {
	
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(ValidaExistePatronService.class);
	}

	@PersistenceContext(unitName = "reingPersistenceUnit")
	protected EntityManager em;

	@Override
	public boolean validaExistePatron(String nombreAlta, String apPaternoAlta,
			String apMaternoAlta, String descSociedadAlta,
			String municipioImssAlta, int tipoPersonaAlta, int divisionAlta,
			int grupoAlta, int fraccionAlta, int modalidadAlta, int clase,
			boolean marcaClaseAlta, String rfcAlta) {

		LOG.debug("******************************* Entre a ValidaExistePatronService.validaExistePatron");
				
		try {
			
			if(modalidadAlta == new Integer(Constantes.MODALIDAD_32).intValue()){
				LOG.debug("******************************* Error patron: "+nombreAlta+": " + Constantes.ERROR_MODALIDAD_32);
				return true;
			}
			
			if(!marcaClaseAlta){ // si el patron no trae la marca de rpc busca por nombre para verificar que el patron no exista 
				List<SspaPatrone> listSspaPatrone = null;
				List<AptRegistroPatronal> listArp = null;
				SspaPatrone sspaPatrone = null;
				AptRegistroPatronal arp; 
				String nombreAcomodado = null;
				String patNombre = null;

				//arma el nombre para buscar en la tabla SSPA_PATRONES
				patNombre = ArpUtility.obtieneNombrePatron(tipoPersonaAlta, nombreAlta, apPaternoAlta, apMaternoAlta, descSociedadAlta);
				
				listSspaPatrone = buscaPatronPorNombre(patNombre);

				if(listSspaPatrone != null && listSspaPatrone.size() > 0){
					
					LOG.debug("******************************* Encontre " + listSspaPatrone.size() + " coincidencias en sspa_patrones para " + nombreAlta);
					for (Iterator<SspaPatrone> iterator = listSspaPatrone.iterator(); iterator.hasNext();) {
						sspaPatrone = iterator.next();
						LOG.debug("****** SspaPatrone nombre: " + sspaPatrone.getPaNombre().trim() + ", RegPatron: " + sspaPatrone.getId().getRegPatron() 
								+ ", Modal: " + sspaPatrone.getId().getCveModal() + ", Mpio: " + sspaPatrone.getCveMpio());
						
						listArp = buscaPatronPorModalidadMunicipio(sspaPatrone.getId().getRegPatron(), sspaPatrone.getId().getCveModal(), sspaPatrone.getCveMpio());
						
						if(listArp != null && listArp.size() > 0){

							for (Iterator<AptRegistroPatronal> iterator2 = listArp.iterator(); iterator2.hasNext();) {
								arp = iterator2.next();
								
								LOG.debug("****** AptRegistroPatronal nombre: " + arp.getNomNombeRazonSocial() + ", RegPatron: "
										+ arp.getCveRegPatronal() + ", Modal: " + arp.getCveModalidad() + ", Mpio: " + arp.getCveMunicipio());					
								
								//obtiene el nombre como en SINDO
								nombreAcomodado = ArpUtility.obtieneNombreAcomodado(tipoPersonaAlta, arp.getNomNombeRazonSocial(),
										arp.getNomApPaterno(), arp.getNomApMaterno(), descSociedadAlta);												
								
								if( ArpUtility.validaExistePatron(tipoPersonaAlta, municipioImssAlta, patNombre, nombreAcomodado, arp, divisionAlta, grupoAlta, fraccionAlta, modalidadAlta) )
									return true;
							}

						}else
							LOG.debug("No encontre el patron " + sspaPatrone.getId().getRegPatron() + " en Apt_Registro_Patronal");						
					}
					
				}else
					LOG.debug("No encontre el patron " + nombreAlta + " en Sspa_Patrones");
				
			}else{ //si trae la marca de rpc busca solo en AptRegistroPatronal por rfc y nombre para comprobar que la clase no este ocupada
				
				LOG.debug("El patron " + nombreAlta + " viene con la marca de RPC");
				List<AptRegistroPatronal> listArp =  buscaPatronPorRFC(rfcAlta, nombreAlta, apPaternoAlta, apMaternoAlta,
						descSociedadAlta, clase, tipoPersonaAlta);

				if(listArp != null && listArp.size() > 0){
					LOG.debug("******************************* Encontre " + listArp.size() + " coincidencias en AptRegistroPatronal para " + nombreAlta);
					LOG.debug("El patron " + nombreAlta + " viene con la marca de RPC y tiene la clase "+clase+" ocupada");
					LOG.debug(Constantes.ERROR_PATRON_ENCONTRADO_RPC);
					return true;
				}else
					LOG.debug("******************************* No se encontraron coincidencias de clase "+clase+" ocupada para " + nombreAlta);					
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LOG.debug("******************************* No encontre coincidencias para el patron " + nombreAlta);

		return false;				
	}	
	
	private List<AptRegistroPatronal> buscaPatronPorModalidadMunicipio(String regPatron, long modalidad, String municipio){
		
		String consulta = "from AptRegistroPatronal arp " +
				 "where arp.cveRegPatronal ='"+ regPatron +"'"+
				 " and arp.cveModalidad = " + modalidad +
				 " and arp.cveMunicipio = '"+ municipio+"'";
		
		LOG.debug("******************************* Buscando en AptRegistroPatronal, consulta: " + consulta);
		
		Query query = this.em.createQuery(consulta);
		
		@SuppressWarnings("unchecked")
		List<AptRegistroPatronal> listArp = query.getResultList();
		
		return listArp;		
	}	
	
	private List<SspaPatrone> buscaPatronPorNombre(String nombre){
		
		String consulta = "from SspaPatrone patrones where patrones.paNombre = '"+nombre.replaceAll("'", "''")+"'";
		
		System.out.println("******************************* Buscando "+nombre+" en SspaPatrone, consulta: " + consulta);
		
		Query query = this.em.createQuery(consulta);
		
		@SuppressWarnings("unchecked")
		List<SspaPatrone> listSspaPatrone = query.getResultList();
		
		return listSspaPatrone;		
	}

	private List<AptRegistroPatronal> buscaPatronPorRFC(String rfcAlta, String nombreAlta, String apPaternoAlta, String apMaternoAlta,
			String descSociedadAlta, int clase, int tipoPersonaAlta) {
		
		String claseR = ArpUtility.convierteClaseReing(clase);
		String consulta = "from AptRegistroPatronal arp " +
				 "where arp.refRfc ='"+ rfcAlta +"' "+
				 " and arp.nomNombeRazonSocial = '" + nombreAlta + "' ";
				 
 		if ( tipoPersonaAlta == Constantes.TIPO_PERSONA_FISICA.intValue()){
 			consulta += "and arp.nomApPaterno = '"+apPaternoAlta+"' " +
 					    "and arp.nomApMaterno = '"+apMaternoAlta+"' ";
 		}else
 			consulta += "and arp.apcTipoSociedad.desTipoSociedad = '"+descSociedadAlta+"' ";

 		consulta += "and arp.indMarcaClase = " + Constantes.RPC.intValue() + " " +
 					"and arp.apcFraccion.cveClase = '"+claseR+"' " +
// 		            "and arp.apcFraccion.id.cveDivision = "+divisionAlta+" " +
// 		            "and arp.apcFraccion.id.cveGrupo = "+grupoAlta+" " +
// 		            "and arp.apcFraccion.id.cveFraccion = "+fraccionAlta+" " +				 
			        "and arp.cveRegPatronal is not null " +  
					"and arp.cveModalidad is not null";
		
		LOG.debug("******************************* Buscando clase en AptRegistroPatronal, consulta: " + consulta);
		
		Query query = this.em.createQuery(consulta);
		
		@SuppressWarnings("unchecked")
		List<AptRegistroPatronal> listArp = query.getResultList();
		
		return listArp;		
	}	
}
