package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.pagos.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Permite controlar los registros patronales
 * durante el proceso de un o varios pagos.
 * 
 * @author Marco Antonio Nieto Plett
 * @version 1.0.0
 *
 */
public class RegistroPatronalVO {
	
	/**Descripcion a 10 caracteres del registro patronal*/
	private String descripcion;
	
	/**ID CVE_ANEXOSOLCORRPAT asignado al registro patronal*/
	private Integer id;
	
	/**Colección de IDs CVE_ANEXOSOLCORRPAT*/
	private Collection<Integer> idsConcatenados;
	
	/**Colección de registros patronales con descripcción y ID*/
	private List<RegistroPatronalVO> listaRPS;

	/**
	 * Transforma una lista de tipo Object[] a 
	 * objetos de tipo RegistroPatronal.<br><br>
	 * 
	 * Se inicializan todas las colecciones de la clase.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param listaRpsTipoObjeto
	 */
	public RegistroPatronalVO(List<?> listaRpsTipoObjeto){
		idsConcatenados = new ArrayList<Integer>();
		
		listaRPS = new ArrayList<RegistroPatronalVO>();
		Object[] currentObject =null;
		String rp;
		Integer cveRp;
		
		Iterator<?> iter = listaRpsTipoObjeto.iterator();
		
		while(iter.hasNext()){
			currentObject = (Object[]) iter.next();
			
			cveRp =  Integer.parseInt(String.valueOf(currentObject[0]));
			rp = String.valueOf(currentObject[1]);
			idsConcatenados.add(cveRp);
			listaRPS.add(new RegistroPatronalVO(rp,cveRp));
		}
		
	}
	
	/**
	 * Construye un objeto sin inicializar 
	 * las colecciones de la clase.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param descripcion
	 * @param id
	 */
	public RegistroPatronalVO(String descripcion,Integer id){
		
		setDescripcion(descripcion);
		setId(id);
		
	}
	
	/**
	 * Otorga la descripción a 10
	 * caracteres del registro patronal.
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * Permite ingresar la descripción a 
	 * 10 caracteres del registo patronal.
	 * @author Marco Antonio Nieto Plett
	 * @param descripcion
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	/**
	 * Otorga el ID del registro patronal (CVE_ANEXOSOLCORRPAT)
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Permite ingresar el ID del registro patronal (CVE_ANEXOSOLCORRPAT)
	 * @author Marco Antonio Nieto Plett
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Permite obtener la lista de los registros
	 * patronales asociados a la solicitud de
	 * la corrección.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public List<RegistroPatronalVO> getListaRPS() {
		return listaRPS;
	}
	
	/**
	 * Permite ingresar la lista de los registros
	 * patronales asociados a la solicitud de
	 * la corrección.
	 * @author Marco Antonio Nieto Plett
	 * @param listaRPS
	 */
	public void setListaRPS(List<RegistroPatronalVO> listaRPS) {
		this.listaRPS = listaRPS;
	}
	
	/**
	 * Permite obtener todos los IDs de registros patronales
	 * los cuales serán utilizados en una consulta IN de SQL.
	 * @author Marco Antonio Nieto Plett
	 * @return
	 */
	public Collection<Integer> getIdsConcatenados() {
		return idsConcatenados;
	}

	/**
	 * Permite Ingresar todos los IDs de registros patronales
	 * los cuales serán utilizados en una consulta IN de SQL.
	 * @author Marco Antonio Nieto Plett
	 * @param idsConcatenados
	 */
	public void setIdsConcatenados(Collection<Integer> idsConcatenados) {
		this.idsConcatenados = idsConcatenados;
	}
	

}
