package mx.gob.imss.ctirss.delta.derechohabientes.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.CalidadParentescoParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.enums.SubestadoDerechohabienteEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GrupoFamiliarUtil {
	
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(GrupoFamiliarUtil.class);
	}

	/**
	 * Se quitan las bajas por fallecimiento
	 * @param lista
	 * @return
	 */
	public static List<GrupoFamiliar> quitarDefunciones(List<GrupoFamiliar> lista) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();

		for (GrupoFamiliar integrante : lista) {
			if (!integrante.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().equals(SubestadoDerechohabienteEnum.FALLECIMIENTO.getId())) {
				salida.add(integrante);
			}
		}

		return salida;
	}
	
	/**
	 * Metodo para filtrar los candidatos por umf o por subdelegacion de acuerdo al perfil de usuario
	 * @param candidatos
	 * @param usuario
	 * @return
	 */
	public static List<GrupoFamiliar> filtlarCandidatosPorPerfil(List<GrupoFamiliar> candidatos, Usuario usuario) throws DerechohabientesBusinessException{
		LOG.debug("Se filtraran los candidatos de acuerdo al perfil del usuario");
		//Lista de candidatos finales
		List<GrupoFamiliar> candidatosTramite = new ArrayList<GrupoFamiliar>();
		//si los candidatos no son nulos y no estan vacios
		if(candidatos != null && !candidatos.isEmpty()) {
			LOG.debug("La lista de candidatos no es nula ni vacia");
			//si el usuario no viene nulo
			if(usuario != null) {
				LOG.debug("El usuario no es nulo");
				//si el usuario trae perfil
				if(usuario.getPerfilUsuario() != null && usuario.getPerfilUsuario().getIdPerfilUsuario() != null) {
					LOG.debug("El usuario tiene perfil");
					//se obtiene el perfil de usuario
					Long idPerfilUsuario = usuario.getPerfilUsuario().getIdPerfilUsuario();
					//se compara si es tramitador
					if(idPerfilUsuario.equals(PerfilesEnum.TRAMITADOR.getId())) {
						LOG.debug("Se filtraran los candidatos por umf ya que el usuario es un tramitador");
						//si el perfil es tramitador se filtraran por umf
						candidatosTramite = GrupoFamiliarUtil.filtrarPorUmf(candidatos, usuario.getIdUmf());
					} else if(idPerfilUsuario.equals(PerfilesEnum.JEFE_DEPTO_AFIL_VIGENCIA.getId()) || idPerfilUsuario.equals(PerfilesEnum.JEFE_OFICINA_VIGENCIA.getId())){
						LOG.debug("Se filtraran los candidatos por subdelegacion ya que el usuario es autorizador");
						//si el perfil es autorizador se filtraran por subdelegacion
						candidatosTramite = GrupoFamiliarUtil.filtrarPorSubdelegacion(candidatos, usuario.getCveIdSubdelegacion());
					} else {
						LOG.debug("Se regresara a todos los candidatos ya que el usuario no es ni tramitador ni autorizador");
						//si es cualquier otro perfil se devolveran todos los encontrados
						candidatosTramite = candidatos;
					}
				} else {
					LOG.debug("Se regresara toda la lista de candidatos ya que no se tiene perfil");
					//si no trae perfil se regresa a todos los candidatos
					candidatosTramite = candidatos;
				}
			} else {
				LOG.debug("Se retornara toda la lista de candidatos ya que el objeto usuario viene nulo");
				//si el usuario vviene nulo se regresan a todos los candidatos
				candidatosTramite = candidatos;
			}
		}
		
		return candidatosTramite;
	}
	
	/**
	 * Metodo que filtra a los integrantes de un grupo familiar por la umf en la que se encuentran registrados
	 * @param integrantes
	 * @param idUmf
	 * @return
	 */
	public static List<GrupoFamiliar> filtrarPorUmf(List<GrupoFamiliar> integrantes, Long idUmf) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();
		LOG.debug("Se filtrara el grupo fmailiar por la umf: " + idUmf);
		for(GrupoFamiliar integrante : integrantes) {
			if(integrante.getMedicoEnTurno() != null ){
				if(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar() != null) {
					if(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF().longValue() == idUmf.longValue()) {
						salida.add(integrante);
					}
				}
			}
		}

		return salida;
	}
	
	/**
	 * Metodo que filtra a los integrantes d eun grupo familiar por la subdelegacion en donde se encuentran
	 * @param integrantes
	 * @param idSubdelegacion
	 * @return
	 */
	public static List<GrupoFamiliar> filtrarPorSubdelegacion(List<GrupoFamiliar> integrantes, Long idSubdelegacion) {
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();
		LOG.debug("Se filtrara a los candidatos por la subdelegacion: " + idSubdelegacion);
		for(GrupoFamiliar integrante : integrantes) {
			if(integrante.getMedicoEnTurno() != null ){
				if(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar() != null) {
					if(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getId() == idSubdelegacion.longValue()) {
						salida.add(integrante);
					}
				}
			}
		}

		return salida;
	}
	
	/**
	 * Obtiene al integrante asegurado o pensionado de una lista de integrantes de grupo familiar
	 * @param integrantes
	 * @return
	 */
	public static GrupoFamiliar getAseguradoPensionado(List<GrupoFamiliar> integrantes) {

		for(GrupoFamiliar integrante: integrantes){
			if(integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId())
					|| integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()))
				return integrante;
		}
		return null;
	}
	
	/**
	 * Metodo que valida si la lista de candidatos viene nula o vacia
	 * en caso de que venga de esa forma lanzara una excepcion
	 * @param grupoFamiliar
	 * @throws DerechohabientesBusinessException
	 */
	public static void validarIntegrantesVacios(List<GrupoFamiliar> grupoFamiliar) throws DerechohabientesBusinessException {
		
		if(grupoFamiliar == null || grupoFamiliar.isEmpty()) {
			DerechohabientesBusinessException.throwException("No existen candidatos para el tr&aacute;mite","No existen candidatos para el tr&aacute;mite");
		}
		
	}
	
	public static void validarIntegrantesVacios(List<GrupoFamiliar> grupoFamiliar, String message, String situacion) throws DerechohabientesBusinessException {
		
		if(grupoFamiliar == null || grupoFamiliar.isEmpty()) {
			DerechohabientesBusinessException.throwException(message, situacion);
		}
		
	}
	
	/**
	 * Metodo que elimina al integrante asegurado/pensionado de la lista que recibe del grupo familiar
	 * incluye la validacion de integrantes vacios arrojando una excepcion 
	 * @param lstGrupoFamiliar
	 * @return List<GrupoFamiliar> sin el aseguroado o pensionado
	 * @throws DerechohabientesBusinessException
	 */
	public static  List<GrupoFamiliar> filtraAseguradoPensionado(List<GrupoFamiliar> lstGrupoFamiliar)throws DerechohabientesBusinessException {
		validarIntegrantesVacios(lstGrupoFamiliar);
		List<GrupoFamiliar> salida = new ArrayList<GrupoFamiliar>();
		int posicion = 0;
		for(GrupoFamiliar integrante:lstGrupoFamiliar ){
			if(integrante.getParentesco().getIdParentesco().longValue() == ParentescoEnum.ASEGURADO.getId() ||
					integrante.getParentesco().getIdParentesco().longValue() == ParentescoEnum.PENSIONADO.getId()){
				//lstGrupoFamiliar.remove(integrante);
				lstGrupoFamiliar.remove(posicion);
				break;
			}
			posicion++;
		}
		return lstGrupoFamiliar;
	}


	public static List<GrupoFamiliar> ordenarPorCalidad(List<GrupoFamiliar> lista) {
		Map<Long, GrupoFamiliar> distintos = new HashMap<Long, GrupoFamiliar>();
		
		if(lista != null && !lista.isEmpty()) {
			
			for(GrupoFamiliar grupo: lista) {
				distintos.put(grupo.getDerechohabiente().getIdPersona(), grupo);
			}
			
			lista = new ArrayList<GrupoFamiliar>(distintos.values());
			
			Collections.sort(lista,new Comparator<GrupoFamiliar>() {
				@Override
				public int compare(GrupoFamiliar g1, GrupoFamiliar g2) {
					return new Integer(g1.getCalidad().intValue()).compareTo(new Integer(g2.getCalidad().intValue()));
				}
			}
			);
			
			return lista;
		} else {
			return lista;
		}
	}
}
