/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hï¿½ctor Lara Andrï¿½s
 *  @Proyecto: delta
 *  @Archivo: DatosClemServiceUtility.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem
 *  @Fecha: 17/08/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio.DelegacionServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio.SubDelegacionServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.secuenciasgenerales.SeqGeneralesServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.HistoricoDatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoDatosClemEnum;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoCausaAnalisis;
import mx.gob.imss.ctirss.delta.persistence.DitAnalisisCe;
import mx.gob.imss.ctirss.delta.persistence.DitArticulo;
import mx.gob.imss.ctirss.delta.persistence.DitDatosClem;
import mx.gob.imss.ctirss.delta.persistence.DitHistDatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;

@Stateless
public class DatosClemServiceUtility extends AbstractServiceUtility implements DatosClemServiceUtilityLocal {

	@EJB
	SeqGeneralesServiceEntityLocal seqGeneralesService; 
	
	@EJB
	private DelegacionServiceEntityLocal delegacionService;

	@EJB
	private SubDelegacionServiceEntityLocal subDelegacionService;
	@EJB
	private SujetoObligadoServiceBusinessRemote sujetoObligadoService;

	@Override
	public DatosClem convertirEntityToModel(DitDatosClem entity) throws Exception {
		DatosClem model = new DatosClem();
		try{
			model.setCveTipoClem(entity.getNumBndDelegacional());
			
			if(entity.getNumBndDelegacional() != null && entity.getNumBndDelegacional().intValue() == Constantes.CLEM_SUBDELEGACIONAL
					&& entity.getDesSuplente()!=null && !entity.getDesSuplente().toString().trim().equals(""))
				model.setFirmaAusencia("1");
			
			if(((Long)entity.getCveIdClem())!=null){
		    	model.setCveIdClem(entity.getCveIdClem());
		    }
			
		    if(entity.getDitAnalisisCe() != null){
		    	model.setCveAnalisis(new BigDecimal(entity.getDitAnalisisCe().getCveIdAnalisis()));
	    		model.setCveSolicitud(entity.getDitAnalisisCe().getCveIdSolicitud());
		    }
		    
		    model.setFolioResolucion(entity.getNumFolioResolucion());

		    model.setDesMotivos(entity.getDesMotivos());
		    model.setDesLugarFechaExp(entity.getDesLugarFechaExp());
		    model.setFecRegistroAlta(entity.getFecRegistroAlta());
		    
		    if(entity.getDesTitular()!=null && !entity.getDesTitular().toString().trim().equals("")){
		    	model.setDesTitular(entity.getDesTitular().toString());
		    }
		    if(entity.getDesSuplente()!=null && !entity.getDesSuplente().toString().trim().equals("")){
		    	model.setDesSuplente(entity.getDesSuplente().toString());
		    }

		    if(entity.getPuesto()!=null && !entity.getPuesto().toString().trim().equals("")){
		    	model.setPuesto(entity.getPuesto().toString());
		    }

		    if(entity.getFecRegistroActualizado()!=null && entity.getFecRegistroActualizado().toString().trim().equals("")){
		    	model.setFecRegistroActualizado(entity.getFecRegistroActualizado());
		    }
		    
		    if(entity.getRefDocumento() != null){
		    	model.setRefDocumento(entity.getRefDocumento());
		    }
		    /*if(entity.getCveIdTipoDoc()!=null){
		    	model.setCveTipoDoc(entity.getCveIdTipoDoc().getCveIdTipoDoc());
		    }*/
		    
		    if(entity.getDitArticulos()!=null){
		    	for (DitArticulo articulo : entity.getDitArticulos()) {
			    	if(articulo.getNumArticulo()!=null && articulo.getNumArticulo().equals(155)){
			    		//model.setCveArticulo155(new BigDecimal(articulo.getCveIdArticulo()));
			    		model.setDescFraccion115(articulo.getDesFraccion());
			    		model.setIncisoArticulo155(articulo.getDesInciso());
			    	}
			    	if(articulo.getNumArticulo()!=null && articulo.getNumArticulo().equals(26)){
			    		model.setCveArticulo26(Long.valueOf(articulo.getCveIdArticulo()).toString());
			    	}
			    	if(articulo.getNumArticulo()!=null && articulo.getNumArticulo().equals(20)){
			    		model.setCveArticulo20(Long.valueOf(articulo.getCveIdArticulo()).toString());
			    	}
			    	if(articulo.getNumArticulo()!=null && articulo.getNumArticulo().equals(28)){
			    		model.setCveArticulo28(Long.valueOf(articulo.getCveIdArticulo()).toString());
			    	}
			    	System.out.println("id:: " + articulo.getCveIdArticulo()+ ", num:: " + articulo.getNumArticulo()
			    			+ "\ndes:: " + articulo.getDesFraccion() + ", inc:: " + articulo.getDesInciso());
				}
		    }
		}catch (Exception e) {
				log.error("Error Exception: "+ e.getMessage() , e);
				e.printStackTrace();
				throw e;
		}
		return model;
	}

	@Override
	public DitDatosClem convertirModelToEntity(DatosClem model)	throws Exception{
		DitDatosClem entity = new DitDatosClem();
	    DitAnalisisCe ditAnalisisCe = new DitAnalisisCe();
	    try{
	    	entity.setNumBndDelegacional(model.getCveTipoClem());
	    	
	    	if(model.getCveIdClem()!=null){
	    		entity.setCveIdClem(model.getCveIdClem().longValue());
	    	}
			ditAnalisisCe.setCveIdAnalisis(model.getCveAnalisis().longValue());
			entity.setDitAnalisisCe(ditAnalisisCe);
			entity.setNumFolioResolucion(model.getFolioResolucion());
			
			//if (model.getRefDocumento() != null){
				entity.setRefDocumento(model.getRefDocumento());
			//}
			
			entity.setDesMotivos(model.getDesMotivos());
			entity.setDesLugarFechaExp(model.getDesLugarFechaExp());
			entity.setFecRegistroAlta(model.getFecRegistroAlta());
			
			if(model.getFecRegistroActualizado()!=null && !model.getFecRegistroActualizado().toString().trim().equals("")){
				entity.setFecRegistroActualizado(model.getFecRegistroActualizado());
			}
			
			if(model.getDesTitular()!=null && !model.getDesTitular().trim().toString().equals("")){
				entity.setDesTitular(model.getDesTitular());
			}
			
			if(model.getDesSuplente()!=null && !model.getDesSuplente().trim().toString().equals("") && model.getCveTipoClem().intValue() == Constantes.CLEM_SUBDELEGACIONAL){
				entity.setDesSuplente(model.getDesSuplente());
			}
			
			if(model.getPuesto()!=null && !model.getPuesto().trim().toString().equals("")){
				entity.setPuesto(model.getPuesto());
			}

		} catch (Exception e) {
			log.error(e.getMessage() , e);
			e.printStackTrace();
			throw e;
		}
		return entity;
	}
	
	@Override
	public  String generaFolioClem(DatosClem datosClem) throws DatosClemException{
		//CE-25-60-21/05/2010/ 0001-DM
		String response="";
		StringBuffer strBuffer = new StringBuffer();
		BigInteger secuenciaFolio= null;
		String del = null;
		String subDel = null;
		
		try{
			Delegacion delegacion = new Delegacion();
			delegacion.setId(new Long(datosClem.getCveDelegacion().longValue()));
			delegacion = delegacionService.consultaPorId(delegacion);
			del = delegacion.getClave();
			Subdelegacion subdelegacion = new Subdelegacion();
			subdelegacion.setDelegacion(delegacion);
			subdelegacion.setId(new Long(datosClem.getCveSubdelegacion().longValue()));
			subdelegacion = subDelegacionService.consultaPorId(subdelegacion);
			subDel = subdelegacion.getClave();
		}catch(Exception e){
			log.error("Problema al obtener la clave de la delegacion / subdelegacion");
			e.printStackTrace();
			throw new DatosClemException(); 
		}
		
		try{
			strBuffer.append("CE-");
			strBuffer.append(rellenaCeros(del, 2));
			strBuffer.append("-");
			strBuffer.append(rellenaCeros(subDel, 2));
			strBuffer.append("-");		
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if(datosClem.getFecRegistroAlta()!= null){
				strBuffer.append(sdf.format(datosClem.getFecRegistroAlta()));
			}
			strBuffer.append("/");	
			
			secuenciaFolio = obtieneSecuenciaFolio(datosClem.getTipoTramite());
			
			if(secuenciaFolio!=null){
				strBuffer.append(rellenaCeros(secuenciaFolio.intValue()+"", 
					datosClem.getTipoTramite().equals("0") ? Constantes.TOTAL_CARACTERES_FOLIO_INSCRIPCION 
											: Constantes.TOTAL_CARACTERES_FOLIO));
			}else{
				strBuffer.append(rellenaCeros("", 
					datosClem.getTipoTramite().equals("0") ? Constantes.TOTAL_CARACTERES_FOLIO_INSCRIPCION 
											: Constantes.TOTAL_CARACTERES_FOLIO));
			}
			strBuffer.append("-");		
			if( TipoDatosClemEnum.DELEGACIONAL.getClave()==datosClem.getCveTipoClem().intValue() ){
				strBuffer.append(Constantes.CLEM_POSFIJO_DELEGACIONAL);	
			}else{
				strBuffer.append(Constantes.CLEM_POSFIJO_SUBDELEGACIONAL);
			}
			
			response= strBuffer.toString();
		}catch (Exception exc) {
			log.error("Error al generar el folio del clem");
			exc.printStackTrace();
			throw new DatosClemException(); 
		}
		
		return response;
	}

	@Override
	public String cambioFolio(DatosClem datosClem) throws DatosClemException {
		String response = "";
		StringBuffer strBuffer = new StringBuffer();
		String folio = datosClem.getFolioResolucion();
		strBuffer.append(folio.substring(0,folio.length()-1));
		if (TipoDatosClemEnum.DELEGACIONAL.getClave() == datosClem.getCveTipoClem().intValue()) {
			strBuffer.append(Constantes.CLEM_POSFIJO_DELEGACIONAL);
		} else {
			strBuffer.append(Constantes.CLEM_POSFIJO_SUBDELEGACIONAL);
		}
		response = strBuffer.toString();
		return response;
	}
	@Override
	public Boolean comparacionFraccion (String ultimaFraccion , ReporteClemBean reporteClemBean ) {
		if(ultimaFraccion!= null && reporteClemBean.getIdFraccionPropuesta()!=null) {
			String fraccionActual = reporteClemBean.getIdDivisionPropuesta() + 
					reporteClemBean.getIdGrupoPropuesta() + 
					reporteClemBean.getIdFraccionPropuesta();
			
			if(ultimaFraccion==fraccionActual) {
				return true;
			}else {
				
				return false;
			}
			
		}
	
		return false;
	
	}

	
	
	private BigInteger obtieneSecuenciaFolio(String tipoTramite) throws DatosClemException{
		BigInteger response = null;
		Date aux = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy");
		Boolean exist = Boolean.FALSE;
		String strName = 
			tipoTramite.equals("1") ? Constantes.CLEM_POSFIJO_SECUENCIA_FOLIO_INSCRIPCION + df.format(aux)
								    : Constantes.CLEM_POSFIJO_SECUENCIA_FOLIO + df.format(aux);
		try{
			exist = seqGeneralesService.existeSecuencia(strName);
			if(!exist){
				//Creando la sequencia
				exist = seqGeneralesService.creaSecuencia(strName, 1, 
						tipoTramite.equals("1") ? Constantes.CLEM_DESC_SECUENCIA_FOLIO_INSCRIPCION
												: Constantes.CLEM_DESC_SECUENCIA_FOLIO);
				if(!exist){
					log.debug("Error al crear la secuencia.");
					throw new PersistenceException("La secuencia del folio del clem no pudo ser creada");
				}
			}
			response = seqGeneralesService.obtieneClaveNueva(strName);
			//response=new BigInteger("1");
		}catch (Exception e) {
			log.error("Error al obtener el folio del clem: "+ e.getMessage());
			throw new DatosClemException();
		}		
		return response;
	}
	
	private String rellenaCeros(String datos, int longitud){
		String strCeros="";
		if(datos!=null){
			if(datos.trim().length()<longitud){
				for(int i=0; i<(longitud-datos.trim().length());i++)
					strCeros+="0";
				return strCeros+ datos.trim();
			}			
		}
		return datos;
	}
	
	@Override
	public HistoricoDatosClem convertirEntityToModel(DitHistDatosClem entity) throws Exception {
		HistoricoDatosClem model = new HistoricoDatosClem();
		try{
			model.setCveTipoClem(entity.getNumBndDelegacional());
			
			if(entity.getNumBndDelegacional() != null && entity.getNumBndDelegacional().intValue() == Constantes.CLEM_SUBDELEGACIONAL
					&& entity.getDesSuplente()!=null && !entity.getDesSuplente().toString().trim().equals(""))
				model.setFirmaAusencia("1");

		    model.setDesMotivos(entity.getDesMotivos());
		    model.setDesLugarFechaExp(entity.getDesLugarFechaExp());
		    
		    if(entity.getDesTitular()!=null && !entity.getDesTitular().toString().trim().equals("")){
		    	model.setDesTitular(entity.getDesTitular().toString());
		    }
		    
		    if(entity.getDesSuplente()!=null && !entity.getDesSuplente().toString().trim().equals("")){
		    	model.setDesSuplente(entity.getDesSuplente().toString());
		    }

		    if(entity.getPuesto()!=null && !entity.getPuesto().toString().trim().equals("")){
		    	model.setPuesto(entity.getPuesto().toString());
		    }

		    if(entity.getDicTipoCausaAnalisis() != null){
		    	model.setCveIdTipoCausa(new Long(entity.getDicTipoCausaAnalisis().getCveIdTipoCausa()));
		    	model.setDesCausa(entity.getDicTipoCausaAnalisis().getDesCausa());
		    }
		    
		    if(entity.getStpHistDatosClem() != null){
		    	model.setStpHistDatosClem(entity.getStpHistDatosClem());
		    }
		}catch (Exception e) {
				log.error("Error Exception: "+ e.getMessage() , e);
				e.printStackTrace();
				throw e;
		}
		return model;
	}
	
	@Override
	public DitHistDatosClem convertirModelToEntityHistoricoCLEM(DatosClem model, Long cveIdTipoCausa){
		DitHistDatosClem ditHistDatosClem=new DitHistDatosClem();
		DicSubdelegacion dicSubdelegacion=new DicSubdelegacion();
		DicTipoCausaAnalisis dicTipoCausaAnalisis=new DicTipoCausaAnalisis();
		DitAnalisisCe ditAnalisisCe=new DitAnalisisCe();
		DitDatosClem ditDatosClem=new DitDatosClem();
		ditHistDatosClem.setDesLugarFechaExp(model.getDesLugarFechaExp());
		ditHistDatosClem.setDesMotivos(model.getDesMotivos());
		ditHistDatosClem.setDesSuplente(model.getDesSuplente());
		ditHistDatosClem.setDesTitular(model.getDesTitular());
		ditHistDatosClem.setPuesto(model.getPuesto());
		dicSubdelegacion.setCveIdSubdelegacion(model.getCveSubdelegacion().longValue());
		ditHistDatosClem.setDicSubdelegacion(dicSubdelegacion);
		dicTipoCausaAnalisis.setCveIdTipoCausa(cveIdTipoCausa);
		ditHistDatosClem.setDicTipoCausaAnalisis(dicTipoCausaAnalisis);
		ditAnalisisCe.setCveIdAnalisis(model.getCveAnalisis().longValue());
		ditHistDatosClem.setDitAnalisisCe(ditAnalisisCe);
		ditDatosClem.setCveIdClem(model.getCveIdClem());
		ditHistDatosClem.setDitDatosClem(ditDatosClem);
		ditHistDatosClem.setNumFolioResolucion(model.getFolioResolucion());
		ditHistDatosClem.setRefDocumento(model.getRefDocumento());
		ditHistDatosClem.setStpHistDatosClem(new Timestamp(new Date().getTime()));
		return ditHistDatosClem;
	}

	@Override
	public ReporteClemBean convertirModelsToReporteClem(ReporteClemBean reporteClem,
			AnalisisClasificacionEmpresas analisisClasifEmp, SujetoObligado sujetoObligado) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		reporteClem.setFechaAviso(dateFormat.format(new Date()).toString());

		//modificacion para guardar la clasificacion actual del RP - JSM
		reporteClem.setIdDivisionPatron(
				analisisClasifEmp.getClasificacionActual()!=null && analisisClasifEmp.getClasificacionActual().getFraccion()!=null
				? analisisClasifEmp.getClasificacionActual().getFraccion().getGrupo().getDivision().getNumDivision():"");
		reporteClem.setIdGrupoPatron(
				analisisClasifEmp.getClasificacionActual()!=null && analisisClasifEmp.getClasificacionActual().getFraccion()!=null
				?analisisClasifEmp.getClasificacionActual().getFraccion().getGrupo().getNumGrupo():"");
		reporteClem.setIdFraccionPatron(
				analisisClasifEmp.getClasificacionActual()!=null && analisisClasifEmp.getClasificacionActual().getFraccion()!=null
				?analisisClasifEmp.getClasificacionActual().getFraccion().getNumFraccion():"");		
		reporteClem.setDenominacionFraccion(
				analisisClasifEmp.getClasificacionActual()!=null && analisisClasifEmp.getClasificacionActual().getFraccion()!=null
				?analisisClasifEmp.getClasificacionActual().getFraccion().getDescripcion():"");		
		if(reporteClem.getDenominacionFraccion() == null || reporteClem.getDenominacionFraccion().trim().length() == 0)
			reporteClem.setDenominacionFraccion(
					analisisClasifEmp.getClasificacionActual()!=null && analisisClasifEmp.getClasificacionActual().getFraccion()!=null
					?analisisClasifEmp.getClasificacionActual().getFraccion().getDescripcionDetallada():"");		
		reporteClem.setClase(
				analisisClasifEmp.getClasificacionActual()!=null && analisisClasifEmp.getClasificacionActual().getFraccion()!=null
				?analisisClasifEmp.getClasificacionActual().getFraccion().getClase().getDescripcion():"");
		reporteClem.setPrima(
				analisisClasifEmp.getClasificacionActual()!=null && analisisClasifEmp.getClasificacionActual().getFraccion()!=null
				?analisisClasifEmp.getClasificacionActual().getFraccion().getPrimaSRT().toString():"");

		
		reporteClem.setIdDivisionPropuesta(
				analisisClasifEmp.getClasificacionPropuesta().getFraccion().getGrupo().getDivision().getNumDivision() == null
				?"":analisisClasifEmp.getClasificacionPropuesta().getFraccion().getGrupo().getDivision().getNumDivision());
		reporteClem.setIdFraccionPropuesta(
				analisisClasifEmp.getClasificacionPropuesta().getFraccion().getNumFraccion() == null
				?"": analisisClasifEmp.getClasificacionPropuesta().getFraccion().getNumFraccion());
		reporteClem.setIdGrupoPropuesta(
				analisisClasifEmp.getClasificacionPropuesta().getFraccion().getGrupo().getNumGrupo() == null
				?"":analisisClasifEmp.getClasificacionPropuesta().getFraccion().getGrupo().getNumGrupo());
		reporteClem.setDivisionPropuesta(analisisClasifEmp.getClasificacionPropuesta().getFraccion().getGrupo().getDivision().getDescripcion());
		reporteClem.setGrupoPropuesta(analisisClasifEmp.getClasificacionPropuesta().getFraccion().getGrupo().getDescripcion());
		reporteClem.setFraccionPropuesta(analisisClasifEmp.getClasificacionPropuesta().getFraccion().getDescripcion());
		reporteClem.setClasePropuesta(analisisClasifEmp.getClasificacionPropuesta().getFraccion().getClase().getDescripcion());
		reporteClem.setPrimaPropuesta(analisisClasifEmp.getClasificacionPropuesta().getFraccion().getPrimaSRT().toString());
		if(analisisClasifEmp.getClasificacionPropuesta().getPrimaSugerida() != null) {
			reporteClem.setPrimaSugerida(analisisClasifEmp.getClasificacionPropuesta().getPrimaSugerida().toString());
		}
		
		//reporteClem.setPspArt15A(reporteClem.getPspArt15A().trim().equals("1") ? "15-A, " : "");
		//reporteClem.setPspArt19(reporteClem.getPspArt15A().trim().equals("1") ? "19, " : "");
		
		if(reporteClem.getTipoPersona().trim().equals("1")){
			String nom = sujetoObligado.getFisica().getNombre() != null ? sujetoObligado.getFisica().getNombre().trim() : "----";
			String apP = sujetoObligado.getFisica().getPrimerApellido() != null ? sujetoObligado.getFisica().getPrimerApellido().trim() : "---";
			String apM = sujetoObligado.getFisica().getSegundoApellido() != null ? sujetoObligado.getFisica().getSegundoApellido().trim() : "---";
			
			reporteClem.setRazonSocial(nom + " " + apP + " " + apM);
			
		}else{
			String nombreRazon = sujetoObligado.getMoral().getRazonSocial().trim();
			
			//se agregar el tipo de sociedad
			if(sujetoObligado.getMoral().getTipoSociedad().getDescripcion() != null)
				nombreRazon = nombreRazon + ", "  + sujetoObligado.getMoral().getTipoSociedad().getDescripcionAbreviada().trim();
						
			reporteClem.setRazonSocial(nombreRazon);
		}
		
		reporteClem.setDelegacion(sujetoObligado.getSubdelegacion().getDelegacion().getDescripcion());
		reporteClem.setSubdelegacion(sujetoObligado.getSubdelegacion().getDescripcion().trim());
		
		String numeroExtCompleto = "";
		String numeroInteriorCompleto = "";
		String domicilio = "";
				
		if(sujetoObligado.getCntroTrabajo().getNumExterior1() != null && sujetoObligado.getCntroTrabajo().getNumExterior1().intValue() != 0){
			numeroExtCompleto = sujetoObligado.getCntroTrabajo().getNumExterior1().toString();
		}		
		if(sujetoObligado.getCntroTrabajo().getNumExteriorAlf() != null){
			if(numeroExtCompleto.trim().length() > 0)
				numeroExtCompleto += " ";
			numeroExtCompleto += sujetoObligado.getCntroTrabajo().getNumExteriorAlf();
		}


		if(sujetoObligado.getCntroTrabajo().getNumInterior() != null && sujetoObligado.getCntroTrabajo().getNumInterior().intValue() != 0){
			numeroInteriorCompleto = sujetoObligado.getCntroTrabajo().getNumInterior().toString();
		}
		if(sujetoObligado.getCntroTrabajo().getNumInteriorAlf() != null){
			if(numeroInteriorCompleto.trim().length() > 0)
				numeroInteriorCompleto += " ";
			numeroInteriorCompleto += sujetoObligado.getCntroTrabajo().getNumInteriorAlf();
		}


		if(numeroExtCompleto != null && numeroExtCompleto.trim().length() > 0)
			numeroExtCompleto = ", N° EXT. " + numeroExtCompleto;
		else
			numeroExtCompleto = ", N° EXT. SN";

		if(numeroInteriorCompleto != null && numeroInteriorCompleto.trim().length() > 0)
			numeroInteriorCompleto = ", N° INT. " + numeroInteriorCompleto;
		else
			numeroInteriorCompleto = ", N° INT. SN";

		if(sujetoObligado.getCntroTrabajo().getVialidadPrimaria() != null){
			
			domicilio = sujetoObligado.getCntroTrabajo().getVialidadPrimaria().getNombre() + numeroExtCompleto.toUpperCase() + numeroInteriorCompleto.toUpperCase();
			
			if(sujetoObligado.getCntroTrabajo().getAsentamiento().getNombre() != null)
				domicilio += ", COLONIA " + sujetoObligado.getCntroTrabajo().getAsentamiento().getNombre().toUpperCase();
			
			if(sujetoObligado.getCntroTrabajo().getCodigoPostal().getCodigoPostal() != null)
				domicilio += ", CP " + sujetoObligado.getCntroTrabajo().getCodigoPostal().getCodigoPostal();
			
			if(sujetoObligado.getCntroTrabajo().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre() != null)
				domicilio += ", " + sujetoObligado.getCntroTrabajo().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre().toUpperCase();

		}
		
		boolean domMig = false;
		if(domicilio == null || domicilio.trim().length() == 0) {
			log.debug("no trae domicilio, se obtiene domicilio migrado");
			domicilio = sujetoObligadoService.obtenerDomicilioMigrado(sujetoObligado.getCveIdSujetoObligado());
			domMig = true;
		}

		reporteClem.setDomicilio(domicilio);

		if(sujetoObligado.getCntroTrabajo().getAsentamiento().getLocalidad().getMunicipio().getNombre() != null && !domMig) { // se imprime solo si el domicilio no es migrado para homologar con documento firmado
			reporteClem.setMunicipioDelegacion(sujetoObligado.getCntroTrabajo().getAsentamiento().getLocalidad().getMunicipio().getNombre().toUpperCase());
		}else {
			reporteClem.setMunicipioDelegacion("");
		}
		
		//Si Este Trï¿½mite es de Inscripciï¿½n Inicial omite los procesos de Causas
		if(!reporteClem.getInsMod().trim().equals("0")){
			reporteClem.setCveIdTipoCausa(Long.parseLong(analisisClasifEmp.getTipoCausaAnalisis()));
		}		
		
		reporteClem.setTipoPersona(reporteClem.getTipoPersona().toString().trim().equals("2") ? "moral" : "física");
		return reporteClem;
	}
	
	public boolean validaCaracteresPermitidos(String cadena){
    	log.debug("::: Validando: " + cadena);
    	boolean res = false;
    	cadena = cadena.replaceAll(" ", "");
    	Pattern pat = Pattern.compile("[a-zA-Z0-9,.;:_áÁéÉíÍóÓúÚüñÑ\\n\\s\\r\\v¡¿?!@#$%&+\\x0b{}()/\\\\]*");
    	Matcher mat = pat.matcher(cadena);
    	res = mat.matches();
    	log.debug("::: Resultado: " + res);    	
        return res;
    }
	
}