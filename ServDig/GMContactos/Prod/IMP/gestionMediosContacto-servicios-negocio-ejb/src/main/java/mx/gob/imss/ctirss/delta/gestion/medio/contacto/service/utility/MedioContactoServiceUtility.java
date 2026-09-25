/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:MedioContactoServiceUtility.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.utility
 *  @Fecha:10/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.ejb.Stateless;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoContacto;
import mx.gob.imss.ctirss.delta.persistence.DicModulo;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitTipoContacto;

/**
 * @author Lucio Duran Silva
 *
 */
@Stateless
public class MedioContactoServiceUtility extends AbstractServiceUtility implements MedioContactoServiceUtilityLocal
		{

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.utility.MedioContactoServiceUtilityLocal#transformarMedioContacto(mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto)
	 */
	@Override
	public List<DitFormaContacto> transformarMedioContacto(List<MedioContacto> medios )
			throws TransformacionException {
		
		
		if(medios == null){
			this.log.error("El parametro modelo de entrada de conversion es nulo. ");
			new TransformacionException();
		}
		
		
		List<DitFormaContacto> entites = new ArrayList<DitFormaContacto>();
		
		DitFormaContacto ditFormaContacto = null;
		DitTipoContacto ditTipoContacto = null;
		
		
		Iterator<MedioContacto> it = medios.iterator();
		while(it.hasNext()){
			
			MedioContacto m = it.next();
			 ditFormaContacto = new DitFormaContacto();
			if (m instanceof CorreoElectronico) {
				
				this.log.debug(" Transformando correo electronico");
				
				CorreoElectronico e = (CorreoElectronico)m;
				ditTipoContacto = new DitTipoContacto();
				ditTipoContacto.setCveIdTipoContacto(TipoMedioContacto.TIPO_CORREO_ELECTRONICO);
				ditFormaContacto.setDitTipoContacto(ditTipoContacto);
				ditFormaContacto.setDesFormaContacto(e.getCorreo());
				if(e.getClave()!= null){
					ditFormaContacto.setCveIdFormaContacto(e.getClave());
				}
				
				
			}else if (m instanceof TelefonoMovil) {
				
				
				this.log.debug(" Transformando telefono movil");
				TelefonoMovil t = (TelefonoMovil) m;
				ditTipoContacto = new DitTipoContacto();
				ditTipoContacto.setCveIdTipoContacto(TipoMedioContacto.TIPO_TELEFONO_MOVIL);
				ditFormaContacto.setDitTipoContacto(ditTipoContacto);
				ditFormaContacto.setDesFormaContacto(t.getNumero());
				if(t.getClave()!= null){
					ditFormaContacto.setCveIdFormaContacto(t.getClave());
				}
				
			} else if (m instanceof TelefonoFijo) {
				this.log.debug(" Transformando telefono fijo");
				TelefonoFijo f = (TelefonoFijo) m;
				ditTipoContacto = new DitTipoContacto();
				ditTipoContacto.setCveIdTipoContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);
				ditFormaContacto.setDitTipoContacto(ditTipoContacto);
				
				StringBuffer formaBfr = new StringBuffer("");
				
				if(f.getClaveLada() != null && !f.getClaveLada().isEmpty()){
					formaBfr.append(f.getClaveLada()); 
					formaBfr.append("|"); 
				}else{
					formaBfr.append(" "); 
					formaBfr.append("|"); 
				}
				
				
				
				if(f.getNumero() != null && !f.getNumero().isEmpty()){
					formaBfr.append(f.getNumero()); 
					formaBfr.append("|"); 
				}else{
					formaBfr.append(" "); 
					formaBfr.append("|"); 
				}
				
				
				if(f.getExtension() != null && !f.getExtension().isEmpty()){
					formaBfr.append(f.getExtension()); 
				}else{
					formaBfr.append(" "); 
				}
				
				
				String desFormaContacto =   formaBfr.toString();
				
//				ditFormaContacto.setDesFormaContacto(f.getNumero() + "-" + f.getExtension());
				ditFormaContacto.setDesFormaContacto(desFormaContacto);
				if(f.getClave()!= null){
					ditFormaContacto.setCveIdFormaContacto(f.getClave());
				}
				
			}else if (m instanceof Facebook) {
				this.log.debug(" Transformando Facebook");
				Facebook f = (Facebook) m;
				ditTipoContacto = new DitTipoContacto();
				ditTipoContacto.setCveIdTipoContacto(TipoMedioContacto.TIPO_FACEBOOK);
				ditFormaContacto.setDitTipoContacto(ditTipoContacto);
				
				String desFormaContacto = f.getCuenta();
				ditFormaContacto.setDesFormaContacto(desFormaContacto);
				if(f.getClave()!= null){
					ditFormaContacto.setCveIdFormaContacto(f.getClave());
				}
				
			}else if (m instanceof Twitter) {
				this.log.debug(" Transformando Twitter");
				Twitter f = (Twitter) m;
				ditTipoContacto = new DitTipoContacto();
				ditTipoContacto.setCveIdTipoContacto(TipoMedioContacto.TIPO_TWITTER);
				ditFormaContacto.setDitTipoContacto(ditTipoContacto);
				
				String desFormaContacto = f.getCuenta();
				
//				ditFormaContacto.setDesFormaContacto(f.getNumero() + "-" + f.getExtension());
				ditFormaContacto.setDesFormaContacto(desFormaContacto);
				if(f.getClave()!= null){
					ditFormaContacto.setCveIdFormaContacto(f.getClave());
				}
				
			}else{
				
				ditTipoContacto = new DitTipoContacto();
				ditTipoContacto.setCveIdTipoContacto(m.getTipoMedioContacto().getIdTipoMedioContacto());
				ditFormaContacto.setDitTipoContacto(ditTipoContacto);
				
				ditFormaContacto.setDesFormaContacto(m.getDesFormaContacto());
				if(m.getClave()!= null){
					ditFormaContacto.setCveIdFormaContacto(m.getClave());
				}
				
			}
			
			/*
			 * Seteamos las fechas de registro...
			 */
			ditFormaContacto.setFecRegistroAlta(new Date());
			entites.add(ditFormaContacto);
			
		}// fin del while
		
		this.log.debug("entities convertidos : " + entites);
		return entites;
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.utility.MedioContactoServiceUtilityLocal#transformarMedioContactoEntities(java.util.List)
	 */
	@Override
	public List<MedioContacto> transformarMedioContactoEntities(
			List<DitFormaContacto> entities) throws TransformacionException {
		
		
		if(entities == null){
			this.log.error("El parametro entidad de entrada de conversion es nulo. ");
			new TransformacionException();
		}
		
		List<MedioContacto> medios = new ArrayList<MedioContacto>();
		
		Iterator<DitFormaContacto> it = entities.iterator();
		while(it.hasNext()){
			DitFormaContacto ditFormaContacto = it.next();
			
			
			DitTipoContacto ditTipo = ditFormaContacto.getDitTipoContacto();
			if(ditTipo.getCveIdTipoContacto().longValue() == TipoMedioContacto.TIPO_CORREO_ELECTRONICO.longValue()){
				this.log.debug("Generando un correo electronico ...");
				
				CorreoElectronico correo = new CorreoElectronico();
				TipoMedioContacto tipo = new TipoMedioContacto();
				tipo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_CORREO_ELECTRONICO);
				tipo.setDescripcion(ditTipo.getDesTipoContacto());
				correo.setDesFormaContacto(ditFormaContacto.getDesFormaContacto());
				correo.setTipoMedioContacto(tipo);
				correo.setCorreo(ditFormaContacto.getDesFormaContacto());
				correo.setClave(ditFormaContacto.getCveIdFormaContacto());
				
				medios.add(correo);
				
			}else if (ditTipo.getCveIdTipoContacto().longValue() == TipoMedioContacto.TIPO_TELEFONO_FIJO.longValue()){
				this.log.debug("Generando un telefono fijo ...");
				
				
				TelefonoFijo telefono = new TelefonoFijo();
				TipoMedioContacto tipo = new TipoMedioContacto();
				tipo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);
				tipo.setDescripcion(ditTipo.getDesTipoContacto());
				
				telefono.setTipoMedioContacto(tipo);

				try{
					StringBuffer desFormaTelefono = new StringBuffer("");
					String desFormaContacto = ditFormaContacto.getDesFormaContacto();
					String[] datosTelefonoFijo = desFormaContacto.split("\\|");
					log.error("Se modifica el stringtokenizer por split");
					
					telefono.setClaveLada("");
					if(StringUtils.isNotEmpty(datosTelefonoFijo[0]) && !datosTelefonoFijo[0].equals("null")){
						telefono.setClaveLada(datosTelefonoFijo[0]);
						desFormaTelefono.append(telefono.getClaveLada()).append(" ");
					}
					telefono.setNumero("");
					if(StringUtils.isNotEmpty(datosTelefonoFijo[1]) && !datosTelefonoFijo[1].equals("null")){
						telefono.setNumero(datosTelefonoFijo[1]);
						desFormaTelefono.append(telefono.getNumero()).append(" ");
					}
					telefono.setExtension("");
					if(!(datosTelefonoFijo.length<3) && StringUtils.isNotEmpty(datosTelefonoFijo[2])
							&& !datosTelefonoFijo[2].equals("null")){
						telefono.setExtension(datosTelefonoFijo[2]);
						desFormaTelefono.append(telefono.getExtension());
					}					
					telefono.setDesFormaContacto(desFormaTelefono.toString());
					
				}catch(Exception e){
					this.log.debug("Error al partir el telefono fijo: " + e.getMessage());
				}
				
				telefono.setClave(ditFormaContacto.getCveIdFormaContacto());
				medios.add(telefono);
				
			}
			else if (ditTipo.getCveIdTipoContacto().longValue() == TipoMedioContacto.TIPO_TELEFONO_MOVIL.longValue()){
				this.log.debug("Generando un telefono movil ...");
				
				TelefonoMovil telefono = new TelefonoMovil();
				TipoMedioContacto tipo = new TipoMedioContacto();
				tipo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_MOVIL);
				tipo.setDescripcion(ditTipo.getDesTipoContacto());
				telefono.setDesFormaContacto(ditFormaContacto.getDesFormaContacto());
				telefono.setTipoMedioContacto(tipo);
				telefono.setNumero(ditFormaContacto.getDesFormaContacto());
				telefono.setClave(ditFormaContacto.getCveIdFormaContacto());
				medios.add(telefono);
			}
			else if (ditTipo.getCveIdTipoContacto().longValue() == TipoMedioContacto.TIPO_FACEBOOK.longValue()){
				this.log.debug("Generando una cuenta de facebook ...");
				
				
				Facebook facebook = new Facebook();
				TipoMedioContacto tipo = new TipoMedioContacto();
				tipo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_FACEBOOK);
				tipo.setDescripcion(ditTipo.getDesTipoContacto());
				
				facebook.setDesFormaContacto(ditFormaContacto.getDesFormaContacto());
				facebook.setTipoMedioContacto(tipo);
				facebook.setCuenta(ditFormaContacto.getDesFormaContacto());
				facebook.setClave(ditFormaContacto.getCveIdFormaContacto());
				medios.add(facebook);
			}
			else if (ditTipo.getCveIdTipoContacto().longValue() == TipoMedioContacto.TIPO_TWITTER.longValue()){
				this.log.debug("Generando una cuenta de twitter ...");

				Twitter twitter = new Twitter();
				TipoMedioContacto tipo = new TipoMedioContacto();
				tipo.setIdTipoMedioContacto(TipoMedioContacto.TIPO_TWITTER);
				tipo.setDescripcion(ditTipo.getDesTipoContacto());
				
				twitter.setDesFormaContacto(ditFormaContacto.getDesFormaContacto());
				twitter.setTipoMedioContacto(tipo);
				twitter.setCuenta(ditFormaContacto.getDesFormaContacto());
				twitter.setClave(ditFormaContacto.getCveIdFormaContacto());
				medios.add(twitter);
			}
			
			this.log.debug("TIPO -> " + ditTipo.getDesTipoContacto());
			
		}
		
		
		return medios;
	}

	@Override
	public List<Modulo> transformarModuloEntities(List<DicModulo> entities)
			throws TransformacionException {
		List<Modulo> modulos = new ArrayList<Modulo>();
		
		for(DicModulo entity: entities){
			Modulo modulo = new Modulo();
			modulo.setIdModulo(entity.getCveIdModulo());
			modulo.setDescripcion(entity.getDesModulo());
			modulos.add(modulo);
		}
		
		return modulos;
	}

	@Override
	public TipoContacto transformarTipoContactoEntity(DitTipoContacto entity) {
		TipoContacto tipo = new TipoContacto();
		tipo.setCveIdTipoContacto(entity.getCveIdTipoContacto());
		tipo.setDesTipoContacto(entity.getDesTipoContacto());
		return tipo;
	}

	@Override
	public List<TipoContacto> transformarTipoContactoEntities(
			List<DitTipoContacto> entities) {
		List<TipoContacto> tipos = new ArrayList<TipoContacto>();
		
		for(DitTipoContacto entity : entities){
			TipoContacto tipo = transformarTipoContactoEntity(entity);
			tipos.add(tipo);
		}
		return tipos;
	}

	@Override
	public List<MedioContacto> transformarEntitiesToModel(
			List<DitFormaContacto> entities) {
		List<MedioContacto> medios = new ArrayList<MedioContacto>();
		
		for(DitFormaContacto entity:entities){
			medios.add( transformarEntityToModel(entity) );
		}
		return medios;
	}

	@Override
	public MedioContacto transformarEntityToModel(DitFormaContacto entity) {
		MedioContacto medio = new MedioContacto();
		medio.setClave(entity.getCveIdFormaContacto());
		medio.setDesFormaContacto(entity.getDesFormaContacto());
		
		TipoMedioContacto tipo = new TipoMedioContacto();
		DitTipoContacto ditTipo = entity.getDitTipoContacto();
		tipo.setIdTipoMedioContacto(ditTipo.getCveIdTipoContacto());
		tipo.setDescripcion(ditTipo.getDesTipoContacto());
		
		medio.setTipoMedioContacto(tipo);
		
		return medio;
	}
	
	@Override
	public List<mx.gob.imss.digital.modelo.medio.contacto.MedioContacto>
	transformarMediosDeltaAImssDigital(List<MedioContacto> mediosDelta){
		List<mx.gob.imss.digital.modelo.medio.contacto.MedioContacto> mediosID 
		= new ArrayList<mx.gob.imss.digital.modelo.medio.contacto.MedioContacto>();
		
		for(MedioContacto medio
				: mediosDelta){
			mediosID.add(transformarMedioDeltaAImssDigital(medio));
		}
		
		return mediosID;
	}
	
	public mx.gob.imss.digital.modelo.medio.contacto.MedioContacto
			transformarMedioDeltaAImssDigital(MedioContacto medioDelta){
		mx.gob.imss.digital.modelo.medio.contacto.MedioContacto medioID = new mx.gob.imss.digital.modelo.medio.contacto.MedioContacto();
		medioID.setClave(medioDelta.getClave());
		medioID.setDesFormaContacto(medioDelta.getDesFormaContacto());
		medioID.setTipoMedioContacto(new mx.gob.imss.digital.modelo.medio.contacto.TipoMedioContacto());
		medioID.getTipoMedioContacto().setIdTipoMedioContacto(medioDelta.getTipoMedioContacto().getIdTipoMedioContacto());
		
		return medioID;
	}
	
	public static void main(String[] args){
		String desFormaContacto = null;
		
		StringTokenizer st = new StringTokenizer(desFormaContacto, "|", false);
		
//		telefono.setNumero(ditFormaContacto.getDesFormaContacto());
		System.out.println(st.hasMoreTokens() == true ? st.nextToken() : "");
		System.out.println(st.hasMoreTokens() == true ? st.nextToken() : "");
		System.out.println(st.hasMoreTokens() == true ? st.nextToken() : "");
		
	}

}




