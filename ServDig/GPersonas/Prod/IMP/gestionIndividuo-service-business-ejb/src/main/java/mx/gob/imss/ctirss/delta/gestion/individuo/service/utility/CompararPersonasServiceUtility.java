package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAsentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.enums.CambioComparacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.DatosPersonaSAT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;

import org.apache.commons.lang.StringUtils;

@Stateless(mappedName = "compararPersonasServiceUtility", name = "compararPersonasServiceUtility")
public class CompararPersonasServiceUtility extends AbstractServiceBusiness
		implements CompararPersonasServiceUtilityLocal {

	
	@Override
	public int compararDatosBasicosPersonaFisica(Fisica fisica1,
			Fisica fisica2, Map<String, CambioComparacionEnum> diferencias) {
		
		Map<String, CambioComparacionEnum> diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
		int countDiff = 0;
		
		diferenciasTmp.put("nombre", comparaDosCadenas(fisica1.getNombre(), fisica2.getNombre()));		
		diferenciasTmp.put("primerApellido", comparaDosCadenas(fisica1.getPrimerApellido(), fisica2.getPrimerApellido()));		
		diferenciasTmp.put("segundoApellido", comparaDosCadenas(fisica1.getSegundoApellido(), fisica2.getSegundoApellido()));				
		diferenciasTmp.put("curp", comparaDosCadenas(fisica1.getCurp(), fisica2.getCurp()));
		
		Sexo sexo1 = fisica1.getSexo();
		Sexo sexo2 = fisica2.getSexo();
		
		if (sexo1 != null && sexo2 != null) {
			if(sexo1.getIdSexo() != null && sexo2.getIdSexo() != null){
				diferenciasTmp.put("sexo", comparaDosInteger(sexo1.getIdSexo(), sexo2.getIdSexo()));
			} else if (sexo1.getIdSexo() == null && sexo2 .getIdSexo() != null) {
				
				diferenciasTmp.put("sexo", CambioComparacionEnum.NUEVO);
			} else if (sexo1.getIdSexo() == null && sexo2.getIdSexo() == null) {
				diferenciasTmp.put("sexo", CambioComparacionEnum.NINGUNO);
			} else {
				diferenciasTmp.put("sexo", CambioComparacionEnum.ELIMINADO);
			}
		} else if (sexo1 == null && sexo2 != null) {
			diferenciasTmp.put("sexo", CambioComparacionEnum.NUEVO);
		} else if (sexo1 == null && sexo2 == null) {
			diferenciasTmp.put("sexo", CambioComparacionEnum.NINGUNO);
		} else {
			diferenciasTmp.put("sexo", CambioComparacionEnum.ELIMINADO);
		}
		
		if (fisica1.getFechaNacimiento() != null && fisica2.getFechaNacimiento() != null) {
			if (fisica1.getFechaNacimiento().compareTo(fisica2.getFechaNacimiento())==0) {
				diferenciasTmp.put("fechaNacimiento", CambioComparacionEnum.NINGUNO);
			} else {
				diferenciasTmp.put("fechaNacimiento", CambioComparacionEnum.CAMBIO);
			}
		} else if (fisica1.getFechaNacimiento() == null && fisica2.getFechaNacimiento() != null) {
			diferenciasTmp.put("fechaNacimiento", CambioComparacionEnum.NUEVO);
		} else if (fisica1.getFechaNacimiento() != null && fisica2.getFechaNacimiento() == null) {
			diferenciasTmp.put("fechaNacimiento", CambioComparacionEnum.ELIMINADO);
		} else {
			diferenciasTmp.put("fechaNacimiento", CambioComparacionEnum.NINGUNO);
		}
				
		if (fisica1.getLugarNacimiento() != null && fisica2.getLugarNacimiento() != null) {
			
			/*
			 * Si la clave del lugar de nacimiento viene nula, se considera como
			 * cadena vacía.
			 */
			String cveLugarNacimientoFisica = StringUtils.isBlank(fisica1
					.getLugarNacimiento().getClave()) ? "" : fisica1
					.getLugarNacimiento().getClave();
			
			String cveLugarNacimientoEntidad = StringUtils.isBlank(fisica2
					.getLugarNacimiento().getClave()) ? "" : fisica2
					.getLugarNacimiento().getClave();
			
			diferenciasTmp.put("lugarNacimiento", comparaDosCadenas(cveLugarNacimientoFisica, 
					cveLugarNacimientoEntidad));
		} else if (fisica1.getLugarNacimiento()==null && fisica2.getLugarNacimiento()==null) {
			diferenciasTmp.put("lugarNacimiento", CambioComparacionEnum.NINGUNO);
		} else if (fisica1.getLugarNacimiento()==null && fisica2.getLugarNacimiento()!=null) {
			diferenciasTmp.put("lugarNacimiento", CambioComparacionEnum.NUEVO);
		} else {
			diferenciasTmp.put("lugarNacimiento", CambioComparacionEnum.ELIMINADO);
		}
		
		if (fisica1.getPais() != null && fisica2.getPais() != null) {
			diferenciasTmp.put("nacionalidad", comparaDosInteger(fisica1.getPais().getIdPais(), fisica2.getPais().getIdPais()));
		} else if (fisica1.getPais() == null && fisica2.getPais() == null) {
			diferenciasTmp.put("nacionalidad", CambioComparacionEnum.NINGUNO);
		} else if (fisica1.getPais() == null && fisica2.getPais() != null) {
			diferenciasTmp.put("nacionalidad", CambioComparacionEnum.NUEVO);
		} else {
			diferenciasTmp.put("nacionalidad", CambioComparacionEnum.ELIMINADO);
		}
		
		countDiff = cantidadDiferencias(diferenciasTmp);
		diferencias.putAll(diferenciasTmp);
		
		return countDiff;
	}
	
	@Override
	public int compararDocumentosProbatorios(Fisica fisica1, Fisica fisica2,
			Map<String, CambioComparacionEnum> diferencias) {
		
		int countDiff = 0;
		boolean huboDocProbatorio = false;
		Map<String, CambioComparacionEnum> diferenciasTmp = null;
		
		if(fisica2.getActaNacimiento() != null){
			this.log.debug("El documento probatorio de RENAPO es un acta de nacimiento");
			
			int cambiosAux = 0;
			huboDocProbatorio = true;
			diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
			
			if (fisica1.getActaNacimiento() != null) {
				Nacimiento nacimiento1 = fisica1.getActaNacimiento();
				Nacimiento nacimiento2 = fisica2.getActaNacimiento();
				
				diferenciasTmp.put("actaNacimiento.anio", comparaDosInteger(nacimiento1.getAnio(), nacimiento2.getAnio()));
				diferenciasTmp.put("actaNacimiento.tomo", comparaDosCadenas(nacimiento1.getTomo(), nacimiento2.getTomo()));
				diferenciasTmp.put("actaNacimiento.crip", comparaDosCadenas(nacimiento1.getCrip(), nacimiento2.getCrip()));
				diferenciasTmp.put("actaNacimiento.foja", comparaDosCadenas(nacimiento1.getNoFoja(), nacimiento2.getNoFoja()));
				diferenciasTmp.put("actaNacimiento.libro", comparaDosCadenas(nacimiento1.getNoLibro(), nacimiento2.getNoLibro()));
				diferenciasTmp.put("actaNacimiento.acta", comparaDosCadenas(nacimiento1.getNoActa(), nacimiento2.getNoActa()));
				diferenciasTmp.put("actaNacimiento.municipio", comparaDosCadenas(nacimiento1.getMunicipio().getClave(), nacimiento2.getMunicipio().getClave()));
				diferenciasTmp.put("actaNacimiento.entidad", comparaDosCadenas(nacimiento1.getMunicipio().getEntidadFederativa().getClave(), nacimiento2.getMunicipio().getEntidadFederativa().getClave()));
				
				cambiosAux = cantidadDiferencias(diferenciasTmp);
				
				// Si la cuenta en cambiosAux aumentó significa que el doc probatorio tiene cambios
				if(cambiosAux > 0){
					diferenciasTmp.put("actaNacimiento", CambioComparacionEnum.CAMBIO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.CAMBIO);
				} else {
					diferenciasTmp.put("actaNacimiento", CambioComparacionEnum.NINGUNO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NINGUNO);
				}
			} else {
				cambiosAux ++;
				diferenciasTmp.put("actaNacimiento", CambioComparacionEnum.NUEVO);
				diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NUEVO);
			}
			
			diferencias.putAll(diferenciasTmp);
			countDiff += cambiosAux;
		} 
		
		if (fisica2.getDocumentoMigratorio() != null){
			this.log.debug("El documento probatorio de RENAPO es un Documento Migratorio");
			
			int cambiosAux = 0;
			huboDocProbatorio = true;
			diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
						
			if(fisica1.getDocumentoMigratorio() != null){				
				CURP docMigratorio1 = fisica1.getDocumentoMigratorio();
				CURP docMigratorio2 = fisica2.getDocumentoMigratorio();
				
				diferenciasTmp.put("documentoMigratorio.numRegExtranjeros", comparaDosCadenas(docMigratorio1.getNumFolioExtranjero(), docMigratorio2.getNumFolioExtranjero()));
				diferenciasTmp.put("documentoMigratorio.numExpediente", comparaDosCadenas(docMigratorio1.getNoActa(), docMigratorio2.getNoActa()));
				
				cambiosAux = cantidadDiferencias(diferenciasTmp);
				
				// Si la cuenta en cambiosAux aumentó significa que el doc probatorio tiene cambios
				if(cambiosAux > 0){
					diferenciasTmp.put("documentoMigratorio", CambioComparacionEnum.CAMBIO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.CAMBIO);
				} else {
					diferenciasTmp.put("documentoMigratorio", CambioComparacionEnum.NINGUNO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NINGUNO);
				}
			} else {
				cambiosAux++;
				diferenciasTmp.put("documentoMigratorio", CambioComparacionEnum.NUEVO);
				diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NUEVO);
			}
			
			diferencias.putAll(diferenciasTmp);
			countDiff += cambiosAux;
		} 
		
		if (fisica2.getCartaNaturalizacion() != null){
			this.log.debug("El documento probatorio de RENAPO es una Carta de Naturalizacion");
			
			int cambiosAux = 0;
			huboDocProbatorio = true;
			diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
			
			if(fisica1.getCartaNaturalizacion() != null){				
				CURP cartaIMSS = fisica1.getCartaNaturalizacion();
				CURP cartaExterna = fisica2.getCartaNaturalizacion();
				
				diferenciasTmp.put("cartaNaturalizacion.anio", comparaDosInteger(cartaIMSS.getAnioRegistro().intValue(), cartaExterna.getAnioRegistro().intValue()));
				diferenciasTmp.put("cartaNaturalizacion.folio", comparaDosCadenas(cartaIMSS.getNumFolioExtranjero(), cartaExterna.getNumFolioExtranjero()));
				
				cambiosAux = cantidadDiferencias(diferenciasTmp);
				
				// Si la cuenta en cambiosAux aumentó significa que el doc probatorio tiene cambios
				if(cambiosAux > 0){
					diferenciasTmp.put("cartaNaturalizacion", CambioComparacionEnum.CAMBIO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.CAMBIO);
				} else {
					diferenciasTmp.put("cartaNaturalizacion", CambioComparacionEnum.NINGUNO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NINGUNO);
				}
			} else {
				cambiosAux++;
				diferenciasTmp.put("cartaNaturalizacion", CambioComparacionEnum.NUEVO);
				diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NUEVO);
			}
			
			diferencias.putAll(diferenciasTmp);
			countDiff += cambiosAux;
		} 
		
		if (fisica2.getNumeroUnicoExtranjero() != null){
			this.log.debug("El documento probatorio de RENAPO es un Numero Unico de Extranjero");
			
			int cambiosAux = 0;
			huboDocProbatorio = true;
			diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
			
			if(fisica1.getNumeroUnicoExtranjero() != null){
				CURP numeroExtranjeroIMSS = fisica1.getNumeroUnicoExtranjero();
				CURP numeroExtranjeroExterna = fisica2.getNumeroUnicoExtranjero();
				
				diferenciasTmp.put("numUnicoExtranjero.folio", comparaDosCadenas(numeroExtranjeroIMSS.getNumFolioExtranjero(), numeroExtranjeroExterna.getNumFolioExtranjero()));
				
				cambiosAux = cantidadDiferencias(diferenciasTmp);
				
				// Si la cuenta en cambiosAux aumentó significa que el doc probatorio tiene cambios
				if(cambiosAux > 0){
					diferenciasTmp.put("numUnicoExtranjero", CambioComparacionEnum.CAMBIO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.CAMBIO);
				} else {
					diferenciasTmp.put("numUnicoExtranjero", CambioComparacionEnum.NINGUNO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NINGUNO);
				}
			} else {
				cambiosAux++;
				diferenciasTmp.put("numUnicoExtranjero", CambioComparacionEnum.NUEVO);
				diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NUEVO);
			}
			
			diferencias.putAll(diferenciasTmp);
			countDiff += cambiosAux;
		} 
			
		if (fisica2.getCertificadoNacionalidadMexicana() != null){
			this.log.debug("El documento probatorio de RENAPO es un Certificado de Nacionalidad Mexicana");
			
			int cambiosAux = 0;
			huboDocProbatorio = true;
			diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
			
			if(fisica1.getCertificadoNacionalidadMexicana() != null){
				CURP certificadoIMSS = fisica1.getCertificadoNacionalidadMexicana();
				CURP certificadoExterna = fisica2.getCertificadoNacionalidadMexicana();
				
				diferenciasTmp.put("certificadoNacionalidad.anio", comparaDosInteger(certificadoIMSS.getAnioRegistro().intValue(), certificadoExterna.getAnioRegistro().intValue()));
				diferenciasTmp.put("certificadoNacionalidad.folio", comparaDosCadenas(certificadoIMSS.getNumFolioExtranjero(), certificadoExterna.getNumFolioExtranjero()));
				
				cambiosAux = cantidadDiferencias(diferenciasTmp);
				
				// Si la cuenta en cambiosAux aumentó significa que el doc probatorio tiene cambios
				if(cambiosAux > 0){
					diferenciasTmp.put("certificadoNacionalidad", CambioComparacionEnum.CAMBIO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.CAMBIO);
				} else {
					diferenciasTmp.put("certificadoNacionalidad", CambioComparacionEnum.NINGUNO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NINGUNO);
				}
			} else {
				cambiosAux++;
				diferenciasTmp.put("certificadoNacionalidad", CambioComparacionEnum.NUEVO);
				diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NUEVO);
			}
			
			diferencias.putAll(diferenciasTmp);
			countDiff += cambiosAux;
		} 
		
		if (fisica2.getOficioSolicitanteRefugiado() != null){
			this.log.debug("El documento probatorio de RENAPO es un Oficio Solicitante de Refugiado");
			
			int cambiosAux = 0;
			huboDocProbatorio = true;
			diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
			
			if(fisica1.getOficioSolicitanteRefugiado() != null){
				CURP oficioIMSS = fisica1.getOficioSolicitanteRefugiado();
				CURP oficioExterna = fisica2.getOficioSolicitanteRefugiado();
				
				diferenciasTmp.put("oficioRefugiado.folio", comparaDosCadenas(oficioIMSS.getNumFolioExtranjero(), oficioExterna.getNumFolioExtranjero()));
				
				cambiosAux = cantidadDiferencias(diferenciasTmp);
				
				// Si la cuenta en cambiosAux aumentó significa que el doc probatorio tiene cambios
				if(cambiosAux > 0){
					diferenciasTmp.put("oficioRefugiado", CambioComparacionEnum.CAMBIO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.CAMBIO);
				} else {
					diferenciasTmp.put("oficioRefugiado", CambioComparacionEnum.NINGUNO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NINGUNO);
				}
			} else {
				cambiosAux++;
				diferenciasTmp.put("oficioRefugiado", CambioComparacionEnum.NUEVO);
				diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NUEVO);
			}
			
			diferencias.putAll(diferenciasTmp);
			countDiff += cambiosAux;
		} 
		
		if (fisica2.getFormaMigratoriaTurista() != null){
			this.log.debug("El documento probatorio de RENAPO es una Forma Migratoria Turista");
			
			int cambiosAux = 0;
			huboDocProbatorio = true;
			diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
			
			if(fisica1.getFormaMigratoriaTurista() != null){
				CURP formaIMSS = fisica1.getFormaMigratoriaTurista();
				CURP formaExterna = fisica2.getFormaMigratoriaTurista();
				
				diferenciasTmp.put("formaMigratoria.folio", comparaDosCadenas(formaIMSS.getNumFolioExtranjero(), formaExterna.getNumFolioExtranjero()));
				
				cambiosAux = cantidadDiferencias(diferenciasTmp);
				
				// Si la cuenta en cambiosAux aumentó significa que el doc probatorio tiene cambios
				if(cambiosAux > 0){
					diferenciasTmp.put("formaMigratoria", CambioComparacionEnum.CAMBIO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.CAMBIO);
				} else {
					diferenciasTmp.put("formaMigratoria", CambioComparacionEnum.NINGUNO);
					diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NINGUNO);
				}
			} else {
				cambiosAux++;
				diferenciasTmp.put("formaMigratoria", CambioComparacionEnum.NUEVO);
				diferenciasTmp.put("doctoProbatorio", CambioComparacionEnum.NUEVO);
			}
			
			diferencias.putAll(diferenciasTmp);
			countDiff += cambiosAux;
		} 
		
		if (!huboDocProbatorio) {
			this.log.warn("RENAPO NO DEVOLVIO NINGUN DOCUMENTO PROBATORIO");
		}
								
		return countDiff;
	}
	
	@Override
	public Map<String, Object> compararDatosBasicosSatPersonaFisica(Persona persona1,
			Persona persona2, Map<String, CambioComparacionEnum> diferencias) {
		
		Map<String, CambioComparacionEnum> diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
		Map<String, Object> resultado = new HashMap<String, Object>();
		int countDiff = 0;
		
		Fisica fisica1 = null;
		Fisica fisica2 = null;
		Moral moral1 = null;
		Moral moral2 = null;
		
		if (persona1 instanceof Fisica && persona2 instanceof Fisica) {
			fisica1 = (Fisica) persona1;
			fisica2 = (Fisica) persona2;
			
		} else if (persona1 instanceof Moral && persona2 instanceof Moral) {
			moral1 = (Moral) persona1;
			moral2 = (Moral) persona2;
		}
		
		/* se cambia la forma de validar el RFC para que haga el replace de la Ñ por # se hace en los dos elementos para homologar*/
		diferenciasTmp.put("rfc", comparaDosCadenas(StringUtils.replace(persona1.getRfc(), "Ñ", "#"), StringUtils.replace(persona2.getRfc(), "Ñ", "#")));
		
		if (fisica1 != null && fisica2 != null) {
			diferenciasTmp.put("nombre", comparaDosCadenas(fisica1.getNombre(), fisica2.getNombre()));		
			diferenciasTmp.put("primerApellido", comparaDosCadenas(fisica1.getPrimerApellido(), fisica2.getPrimerApellido()));		
			diferenciasTmp.put("segundoApellido", comparaDosCadenas(fisica1.getSegundoApellido(), fisica2.getSegundoApellido()));				
			diferenciasTmp.put("curp", comparaDosCadenas(fisica1.getCurp(), fisica2.getCurp()));
			
			compararDatosSAT(fisica1.getDatosPersonaSAT(), fisica2.getDatosPersonaSAT(), diferenciasTmp);
			
		} else if (moral1 != null && moral2 != null) {
			//diferenciasTmp.put("nombreRazonSocial", comparaDosCadenas(moral1.getRazonSocial() != null?moral1.getRazonSocial():"", moral2.getRazonSocial()!=null?moral2.getRazonSocial():""));
			
			//se valida que el tipo de sociedad en sat exista
			 if(moral2.getTipoSociedad()== null){
				 diferenciasTmp.put("tipoSociedadNullSat",CambioComparacionEnum.ELIMINADO);
				 diferenciasTmp.put("nombreRazonSocial", CambioComparacionEnum.CAMBIO);
			 }
			 
			if(moral1.getTipoSociedad() != null && moral1.getTipoSociedad().getDescripcionAbreviada() != null){
				diferenciasTmp.put("nombreRazonSocial", comparaDosCadenas(StringUtils.trim(moral1.getRazonSocial()) + " " + StringUtils.trim(moral1.getTipoSociedad().getDescripcionAbreviada() ) 
				, StringUtils.trim(moral2.getRazonSocial()) + " " + StringUtils.trim(moral2.getTipoSociedad().getDescripcionAbreviada() )));
				
			}else{
				diferenciasTmp.put("nombreRazonSocial", comparaDosCadenas(StringUtils.trim(moral1.getRazonSocial())  
				, StringUtils.trim(moral2.getRazonSocial()) + " " + StringUtils.trim(moral2.getTipoSociedad().getDescripcionAbreviada() )));
				
			}
			
			compararDatosSAT(moral1.getDatosPersonaSAT(), moral2.getDatosPersonaSAT(), diferenciasTmp);
		}
		
		countDiff = cantidadDiferencias(diferenciasTmp);
		diferencias.putAll(diferenciasTmp);
				
		resultado.put("COUNT_DIFF", countDiff);
		
		return resultado;
	}
	
	@Override
	public int compararDomicilioFiscal(Persona persona1, Persona persona2,
			Map<String, CambioComparacionEnum> diferencias) {
		
		int countDiff = 0;
		Map<String, CambioComparacionEnum> diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
		
		DomicilioFiscal domFiscal1 = persona1.getDomicilioFiscal();
		DomicilioFiscal domFiscal2 = persona2.getDomicilioFiscal();
		
		if (domFiscal1 != null && domFiscal2 != null) {			
			diferenciasTmp.put("codigoPostal", comparaDosCadenas(domFiscal1.getCodigoPostal().getCodigoPostal(), domFiscal2.getCodigoPostal().getCodigoPostal()));
			diferenciasTmp.put("calle", comparaDosCadenas(domFiscal1.getCalle(), domFiscal2.getCalle()));
			diferenciasTmp.put("colonia", comparaDosCadenas(domFiscal1.getColonia(), domFiscal2.getColonia()));
			diferenciasTmp.put("numeExt", comparaDosCadenas(domFiscal1.getNumExteriorAlf(), domFiscal2.getNumExteriorAlf()));
			diferenciasTmp.put("numeInt", comparaDosCadenas(domFiscal1.getNumInteriorAlf(), domFiscal2.getNumInteriorAlf()));
			
			if (domFiscal1.getVialidadReferenciaPrimaria()!=null && domFiscal2.getVialidadReferenciaPrimaria()!=null) {
				diferenciasTmp.put("entreCalle1", comparaDosCadenas(domFiscal1.getVialidadReferenciaPrimaria().getNombre(), domFiscal2.getVialidadReferenciaPrimaria().getNombre()));
			}else if(domFiscal1.getVialidadReferenciaPrimaria() == null && domFiscal2.getVialidadReferenciaPrimaria() != null) {
				if(StringUtils.isNotBlank(domFiscal2.getVialidadReferenciaPrimaria().getNombre())){
					diferenciasTmp.put("entreCalle1", CambioComparacionEnum.NUEVO);
				} else {
					diferenciasTmp.put("entreCalle1", CambioComparacionEnum.NINGUNO);
				}
			}else if(domFiscal1.getVialidadReferenciaPrimaria() != null && domFiscal2.getVialidadReferenciaPrimaria() == null) {
				if(StringUtils.isNotBlank(domFiscal1.getVialidadReferenciaPrimaria().getNombre())){
					diferenciasTmp.put("entreCalle1", CambioComparacionEnum.ELIMINADO);
				} else {
					diferenciasTmp.put("entreCalle1", CambioComparacionEnum.NINGUNO);
				}
			}else{
				diferenciasTmp.put("entreCalle1", CambioComparacionEnum.NINGUNO);
			}
			
			if (domFiscal1.getVialidadReferenciaSecundaria()!=null && domFiscal2.getVialidadReferenciaSecundaria()!=null) {
				diferenciasTmp.put("entreCalle2", comparaDosCadenas(domFiscal1.getVialidadReferenciaSecundaria().getNombre(), domFiscal2.getVialidadReferenciaSecundaria().getNombre()));
			}else if (domFiscal1.getVialidadReferenciaSecundaria() == null && domFiscal2.getVialidadReferenciaSecundaria() == null) {
				diferenciasTmp.put("entreCalle2", CambioComparacionEnum.NINGUNO);
			}else if (domFiscal1.getVialidadReferenciaSecundaria() == null && domFiscal2.getVialidadReferenciaSecundaria() != null) {
				if(StringUtils.isNotBlank(domFiscal2.getVialidadReferenciaSecundaria().getNombre())){
					diferenciasTmp.put("entreCalle2", CambioComparacionEnum.NUEVO);
				} else {
					diferenciasTmp.put("entreCalle2", CambioComparacionEnum.NINGUNO);
				}
			}else{
				if(StringUtils.isNotBlank(domFiscal1.getVialidadReferenciaSecundaria().getNombre())){
					diferenciasTmp.put("entreCalle2", CambioComparacionEnum.ELIMINADO);
				} else {
					diferenciasTmp.put("entreCalle2", CambioComparacionEnum.NINGUNO);
				}
			}
			
			if (StringUtils.isNotBlank(domFiscal1.getDescripcion()) && StringUtils.isNotBlank(domFiscal2.getDescripcion())) {
				diferenciasTmp.put("referencia", comparaDosCadenas(domFiscal1.getDescripcion(), domFiscal2.getDescripcion()));
			}else if (StringUtils.isBlank(domFiscal1.getDescripcion()) && StringUtils.isBlank(domFiscal2.getDescripcion())) {
				diferenciasTmp.put("referencia", CambioComparacionEnum.NINGUNO);
			}else if (StringUtils.isBlank(domFiscal1.getDescripcion()) && StringUtils.isNotBlank(domFiscal2.getDescripcion())) {
				diferenciasTmp.put("referencia", CambioComparacionEnum.NUEVO);
			}else{
				diferenciasTmp.put("referencia", CambioComparacionEnum.ELIMINADO);
			}
	
			Vialidad vialidad1 = domFiscal1.getVialidadPrimaria();
			Vialidad vialidad2 = domFiscal2.getVialidadPrimaria();
			
			if(vialidad1 != null && vialidad2 != null){
				diferenciasTmp.put("vialidad", comparaDosCadenas(vialidad1.getNombre(), vialidad2.getNombre()));
			}else if(vialidad1 == null && vialidad2 == null){
				diferenciasTmp.put("vialidad", CambioComparacionEnum.NINGUNO);
			}else if(vialidad1 == null && vialidad2 != null){
				diferenciasTmp.put("vialidad", CambioComparacionEnum.NUEVO);
			}else{
				diferenciasTmp.put("vialidad", CambioComparacionEnum.ELIMINADO);
			}
				
			Asentamiento asentamiento1 = domFiscal1.getAsentamiento();
			Asentamiento asentamiento2 = domFiscal2.getAsentamiento();
			
			if (asentamiento1 != null && asentamiento2 != null) {
				
				TipoAsentamiento tipoAsentamiento1 = asentamiento1.getTipoAsentamiento();
				TipoAsentamiento tipoAsentamiento2 = asentamiento2.getTipoAsentamiento();
				
				if(tipoAsentamiento1 != null && tipoAsentamiento2 != null){
					diferenciasTmp.put("inmueble", comparaDosCadenas(tipoAsentamiento1.getDescripcion(), tipoAsentamiento2.getDescripcion()));
				}else if(tipoAsentamiento1 == null && tipoAsentamiento2 != null){
					diferenciasTmp.put("inmueble", CambioComparacionEnum.NUEVO);
				}else if(tipoAsentamiento1 != null && tipoAsentamiento2 == null){
					diferenciasTmp.put("inmueble", CambioComparacionEnum.ELIMINADO);
				}else{
					diferenciasTmp.put("inmueble", CambioComparacionEnum.NINGUNO);
				}
				
				Localidad localidad1 = asentamiento1.getLocalidad();
				Localidad localidad2 = asentamiento2.getLocalidad();
								
				if (localidad1 != null && localidad2 != null) {
					diferenciasTmp.put("localidad", comparaDosCadenas(localidad1.getNombre(), localidad2.getNombre()));
					Municipio  municipio1 = localidad1.getMunicipio();
					Municipio  municipio2 = localidad2.getMunicipio();
					
					if(municipio1 != null && municipio2 != null){
						diferenciasTmp.put("municipio",comparaDosCadenas(municipio1.getNombre(), municipio2.getNombre()));
						EntidadFederativa entidad1 = municipio1.getEntidadFederativa();
						EntidadFederativa entidad2 = municipio2.getEntidadFederativa();
						
						if(entidad1 != null && entidad2 != null){
							diferenciasTmp.put("entidad",comparaDosCadenas(entidad1.getNombre(), entidad2.getNombre()));
						}else if(entidad1 == null && entidad2 == null){
							diferenciasTmp.put("entidad", CambioComparacionEnum.NINGUNO);
						}else if(entidad1 == null && entidad2 != null){
							diferenciasTmp.put("entidad", CambioComparacionEnum.NUEVO);
						}else{
							diferenciasTmp.put("entidad", CambioComparacionEnum.ELIMINADO);
						}
					}else if(municipio1 == null && municipio2 == null){
						diferenciasTmp.put("municipio", CambioComparacionEnum.NINGUNO);
						diferenciasTmp.put("entidad", CambioComparacionEnum.NINGUNO);
					}else if(municipio1 == null && municipio2 != null){
						diferenciasTmp.put("municipio", CambioComparacionEnum.NUEVO);
						diferenciasTmp.put("entidad", CambioComparacionEnum.NUEVO);
					} else{
						diferenciasTmp.put("municipio", CambioComparacionEnum.ELIMINADO);
						diferenciasTmp.put("entidad", CambioComparacionEnum.ELIMINADO);
					}
				} else if (localidad1 == null && localidad2 == null) {
					diferenciasTmp.put("entidad", CambioComparacionEnum.NINGUNO);
					diferenciasTmp.put("localidad", CambioComparacionEnum.NINGUNO);
					diferenciasTmp.put("municipio", CambioComparacionEnum.NINGUNO);
				} else if (localidad1 == null && localidad2 != null) {
					diferenciasTmp.put("entidad", CambioComparacionEnum.NUEVO);
					diferenciasTmp.put("localidad", CambioComparacionEnum.NUEVO);
					diferenciasTmp.put("municipio", CambioComparacionEnum.NUEVO);
				} else {
					diferenciasTmp.put("entidad", CambioComparacionEnum.ELIMINADO);
					diferenciasTmp.put("localidad", CambioComparacionEnum.ELIMINADO);
					diferenciasTmp.put("municipio", CambioComparacionEnum.ELIMINADO);
				}
			} else if (asentamiento1 == null && asentamiento2 == null) {
				// Si el asentamiento es nulo en ambos objetos se considera que no hay diferencias
				diferenciasTmp.put("entidad", CambioComparacionEnum.NINGUNO);
				diferenciasTmp.put("localidad", CambioComparacionEnum.NINGUNO);
				diferenciasTmp.put("municipio", CambioComparacionEnum.NINGUNO);
			} else if (asentamiento1 == null && asentamiento2 != null){
				// Si el objeto de la entidad fisica es nulo, por lo tanto se consideran que son nuevos
				diferenciasTmp.put("entidad", CambioComparacionEnum.NUEVO);
				diferenciasTmp.put("localidad", CambioComparacionEnum.NUEVO);
				diferenciasTmp.put("municipio", CambioComparacionEnum.NUEVO);
			}else{
				// Si el objeto de la entidad externa es nulo, por lo tanto se consideran que los valores fueron eliminados
				diferenciasTmp.put("entidad", CambioComparacionEnum.ELIMINADO);
				diferenciasTmp.put("localidad", CambioComparacionEnum.ELIMINADO);
				diferenciasTmp.put("municipio", CambioComparacionEnum.ELIMINADO);
			}
			
			countDiff = cantidadDiferencias(diferenciasTmp);
			
			if (countDiff > 0) {
				diferenciasTmp.put("domicilioFiscal", CambioComparacionEnum.CAMBIO);
			} else {
				diferenciasTmp.put("domicilioFiscal",CambioComparacionEnum.NINGUNO);
			}
			
		} else if (domFiscal1 == null && domFiscal2 == null) {
			diferenciasTmp.put("domicilioFiscal", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("codigoPostal", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("calle", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("colonia", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("numeExt", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("numeInt", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("entreCalle1", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("entreCalle2", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("inmueble", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("referencia", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("entidad", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("localidad", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("municipio", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("vialidad", CambioComparacionEnum.NINGUNO);
		} else if (domFiscal1 == null && domFiscal2 != null) {
			countDiff ++;
			diferenciasTmp.put("domicilioFiscal", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("codigoPostal", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("calle", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("colonia", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("numeExt", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("numeInt", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("entreCalle1", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("entreCalle2", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("inmueble", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("referencia", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("entidad", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("localidad", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("municipio", CambioComparacionEnum.NUEVO);
			diferenciasTmp.put("vialidad", CambioComparacionEnum.NUEVO);
		} else {
			countDiff ++;
			diferenciasTmp.put("domicilioFiscal", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("codigoPostal", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("calle", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("colonia", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("numeExt", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("numeInt", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("entreCalle1", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("entreCalle2", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("inmueble", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("referencia", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("entidad", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("localidad", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("municipio", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("vialidad", CambioComparacionEnum.ELIMINADO);
		}
				
		diferencias.putAll(diferenciasTmp);
				
		return countDiff;
	}
	
	@Override
	public int compararMediosFiscales(Persona persona1, Persona persona2,
			Map<String, CambioComparacionEnum> diferencias) {
		
		int countDiff = 0;
		Map<String, CambioComparacionEnum> diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
		
		if(persona1.getCorreoElectronicoFiscal() != null && persona2.getCorreoElectronicoFiscal() != null){
			diferenciasTmp.put("correoElectronico", comparaDosCadenas(persona1.getCorreoElectronicoFiscal().getCorreo(), persona2.getCorreoElectronicoFiscal().getCorreo()));
		}else if(persona1.getCorreoElectronicoFiscal() == null && persona2.getCorreoElectronicoFiscal() != null){
			diferenciasTmp.put("correoElectronico", CambioComparacionEnum.NUEVO);
		}else if(persona1.getCorreoElectronicoFiscal() != null && persona2.getCorreoElectronicoFiscal() == null){
			diferenciasTmp.put("correoElectronico", CambioComparacionEnum.ELIMINADO);
		}else{
			diferenciasTmp.put("correoElectronico", CambioComparacionEnum.NINGUNO);
		}
		
		if(persona1.getTelefonoFijoFiscal() != null && persona2.getTelefonoFijoFiscal() != null){
			Map<String, CambioComparacionEnum> diferenciasTelFijoFiscal = new HashMap<String, CambioComparacionEnum>();
			int cambiosAux = 0;
			
			diferenciasTelFijoFiscal.put("telefonoFijo.lada", comparaDosCadenas(persona1.getTelefonoFijoFiscal().getClaveLada(), persona2.getTelefonoFijoFiscal().getClaveLada()));
			diferenciasTelFijoFiscal.put("telefonoFijo.numero", comparaDosCadenas(persona1.getTelefonoFijoFiscal().getNumero(), persona2.getTelefonoFijoFiscal().getNumero()));
			diferenciasTelFijoFiscal.put("telefonoFijo.extension", comparaDosCadenas(persona1.getTelefonoFijoFiscal().getExtension(), persona2.getTelefonoFijoFiscal().getExtension()));
			
			cambiosAux = cantidadDiferencias(diferenciasTelFijoFiscal);
			
			if (cambiosAux > 0) {
				diferenciasTmp.put("telefonoFijo", CambioComparacionEnum.CAMBIO);
			} else {
				diferenciasTmp.put("telefonoFijo", CambioComparacionEnum.NINGUNO);
			}
			
			diferenciasTmp.putAll(diferenciasTelFijoFiscal);
		}else if(persona1.getTelefonoFijoFiscal() == null && persona2.getTelefonoFijoFiscal() != null){
			diferenciasTmp.put("telefonoFijo", CambioComparacionEnum.NUEVO);
			
			if (StringUtils.isNotBlank(persona2.getTelefonoFijoFiscal().getClaveLada())) {
				diferenciasTmp.put("telefonoFijo.lada", CambioComparacionEnum.NUEVO);
			} else {
				diferenciasTmp.put("telefonoFijo.lada", CambioComparacionEnum.NINGUNO);
			}
			if (StringUtils.isNotBlank(persona2.getTelefonoFijoFiscal().getNumero())) {
				diferenciasTmp.put("telefonoFijo.numero", CambioComparacionEnum.NUEVO);
			} else {
				diferenciasTmp.put("telefonoFijo.numero", CambioComparacionEnum.NINGUNO);
			}
			if (StringUtils.isNotBlank(persona2.getTelefonoFijoFiscal().getExtension())) {
				diferenciasTmp.put("telefonoFijo.extension", CambioComparacionEnum.NUEVO);
			} else {
				diferenciasTmp.put("telefonoFijo.extension", CambioComparacionEnum.NINGUNO);
			}
		}else if(persona1.getTelefonoFijoFiscal() != null && persona2.getTelefonoFijoFiscal() == null){
			diferenciasTmp.put("telefonoFijo", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("telefonoFijo.lada", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("telefonoFijo.numero", CambioComparacionEnum.ELIMINADO);
			diferenciasTmp.put("telefonoFijo.extension", CambioComparacionEnum.ELIMINADO);
		}else{
			diferenciasTmp.put("telefonoFijo", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("telefonoFijo.lada", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("telefonoFijo.numero", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("telefonoFijo.extension", CambioComparacionEnum.NINGUNO);
		}
		
		if(persona1.getTelefonoMovilFiscal() != null && persona2.getTelefonoMovilFiscal() != null){
			diferenciasTmp.put("telefonoMovil", comparaDosCadenas(persona1.getTelefonoMovilFiscal().getNumero(), persona2.getTelefonoMovilFiscal().getNumero()));
		}else if(persona1.getTelefonoMovilFiscal() == null && persona2.getTelefonoMovilFiscal() != null){
			diferenciasTmp.put("telefonoMovil", CambioComparacionEnum.NUEVO);
		}else if(persona1.getTelefonoMovilFiscal() != null && persona2.getTelefonoMovilFiscal() == null){
			diferenciasTmp.put("telefonoMovil", CambioComparacionEnum.ELIMINADO);
		}else{
			diferenciasTmp.put("telefonoMovil", CambioComparacionEnum.NINGUNO);
		}
		
		countDiff = cantidadDiferencias(diferenciasTmp);
		diferencias.putAll(diferenciasTmp);
				
		return countDiff;
	}
	
	@Override
	public int compararSituacionesSAT(SituacionSAT situacionSatImss,
			SituacionSAT situacionSatEntidadExterna,
			Map<String, CambioComparacionEnum> diferencias) {
		
		int countDiff = 0;
		Map<String, CambioComparacionEnum> diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
				
		if(situacionSatImss != null && situacionSatEntidadExterna != null){
			String cveSituacionSatImss = situacionSatImss.getCveSituacionSAT().trim();
			String cveSituacionSatEntidad = situacionSatEntidadExterna.getCveSituacionSAT().trim();
			
			diferenciasTmp.put("situacionSAT", comparaDosCadenas(cveSituacionSatImss, cveSituacionSatEntidad));
			
		} else if(situacionSatImss == null && situacionSatEntidadExterna != null){
			diferenciasTmp.put("situacionSAT", CambioComparacionEnum.NUEVO);
		} else if(situacionSatImss != null && situacionSatEntidadExterna == null){
			diferenciasTmp.put("situacionSAT", CambioComparacionEnum.ELIMINADO);
		} else {
			diferenciasTmp.put("situacionSAT", CambioComparacionEnum.NINGUNO);
		}
		
		countDiff = cantidadDiferencias(diferenciasTmp);
		diferencias.putAll(diferenciasTmp);
				
		return countDiff;
	}
	
	private int compararDatosSAT(DatosPersonaSAT datosSat1,
			DatosPersonaSAT datosSat2,
			Map<String, CambioComparacionEnum> diferencias) {
		
		int countDiff = 0;
		Map<String, CambioComparacionEnum> diferenciasTmp = new HashMap<String, CambioComparacionEnum>();
		
		if(datosSat1 != null && datosSat2 != null){
			if(datosSat1.getFechaConstitucion() != null && datosSat2.getFechaConstitucion() != null){
				if (!datosSat1.getFechaConstitucion().equals(datosSat2.getFechaConstitucion())) {
					diferenciasTmp.put("fechaConstitucion", CambioComparacionEnum.CAMBIO);
				} else {
					diferenciasTmp.put("fechaConstitucion", CambioComparacionEnum.NINGUNO);
				}
			} else if(datosSat1.getFechaConstitucion() == null && datosSat2.getFechaConstitucion() != null){
				diferenciasTmp.put("fechaConstitucion", CambioComparacionEnum.NUEVO);
			} else if(datosSat1.getFechaConstitucion() != null && datosSat2.getFechaConstitucion() == null){
				diferenciasTmp.put("fechaConstitucion", CambioComparacionEnum.ELIMINADO);
			} else {
				diferenciasTmp.put("fechaConstitucion", CambioComparacionEnum.NINGUNO);
			}
			
			if(datosSat1.getFechaInicioOperaciones() != null && datosSat2.getFechaInicioOperaciones() != null){
				if (!datosSat1.getFechaInicioOperaciones().equals(datosSat2.getFechaInicioOperaciones())) {
					diferenciasTmp.put("fechaInicioOperaciones", CambioComparacionEnum.CAMBIO);
				} else {
					diferenciasTmp.put("fechaInicioOperaciones", CambioComparacionEnum.NINGUNO);
				}
			} else if(datosSat1.getFechaInicioOperaciones() == null 
					&& datosSat2.getFechaInicioOperaciones() != null){
				diferenciasTmp.put("fechaInicioOperaciones", CambioComparacionEnum.NUEVO);
			} else if(datosSat1.getFechaInicioOperaciones() != null 
					&& datosSat2.getFechaInicioOperaciones() == null){
				diferenciasTmp.put("fechaInicioOperaciones", CambioComparacionEnum.ELIMINADO);
			} else {
				diferenciasTmp.put("fechaInicioOperaciones", CambioComparacionEnum.NINGUNO);
			}
		}else if(datosSat1 == null && datosSat2 != null){
			if (datosSat2.getFechaConstitucion() != null) {
				diferenciasTmp.put("fechaConstitucion",CambioComparacionEnum.NUEVO);
			} else {
				diferenciasTmp.put("fechaConstitucion",CambioComparacionEnum.NINGUNO);
			}
			if (datosSat2.getFechaInicioOperaciones() != null) {
				diferenciasTmp.put("fechaInicioOperaciones", CambioComparacionEnum.NUEVO);
			} else {
				diferenciasTmp.put("fechaInicioOperaciones", CambioComparacionEnum.NINGUNO);
			}
		}else if(datosSat1 != null && datosSat2 == null){
			if (datosSat1.getFechaConstitucion() != null) {
					diferenciasTmp.put("fechaConstitucion", CambioComparacionEnum.ELIMINADO);
				} else {
					diferenciasTmp.put("fechaConstitucion",CambioComparacionEnum.NINGUNO);
				}
				if (datosSat1.getFechaInicioOperaciones() != null) {
					diferenciasTmp.put("fechaInicioOperaciones", CambioComparacionEnum.ELIMINADO);
				} else {
					diferenciasTmp.put("fechaInicioOperaciones",CambioComparacionEnum.NINGUNO);
				}
		}else{
			diferenciasTmp.put("fechaConstitucion", CambioComparacionEnum.NINGUNO);
			diferenciasTmp.put("fechaInicioOperaciones",CambioComparacionEnum.NINGUNO);
		}
		
		countDiff = cantidadDiferencias(diferenciasTmp);
		diferencias.putAll(diferenciasTmp);		
		
		return countDiff;
	}
	
	private CambioComparacionEnum comparaDosCadenas(String cadena1,
			String cadena2) {

		cadena1 = cadena1 != null ? cadena1 : "";
		cadena2 = cadena2 != null ? cadena2 : "";
		
		CambioComparacionEnum diferencia = null;

		if (cadena1.trim().compareToIgnoreCase(cadena2.trim()) == 0) {
			diferencia = CambioComparacionEnum.NINGUNO;
		} else {
			diferencia = CambioComparacionEnum.CAMBIO;
		}

		return diferencia;
	}

	private CambioComparacionEnum comparaDosInteger(Integer int1,
			Integer int2) {

		int1 = int1 != null ? int1 : new Integer(0);
		int2 = int2 != null ? int2 : new Integer(0);
		
		CambioComparacionEnum diferencia = null;

		if (int1.equals(int2)) {
			diferencia = CambioComparacionEnum.NINGUNO;
		} else {
			diferencia = CambioComparacionEnum.CAMBIO;
		}

		return diferencia;
	}
	
	private boolean fueCambio(CambioComparacionEnum cambio) {

		boolean fueCambio = false;

		if (cambio.getId().longValue() != CambioComparacionEnum.NINGUNO.getId().longValue()) {
			fueCambio = true;
		}

		return fueCambio;
	}
	
	private int cantidadDiferencias(
			Map<String, CambioComparacionEnum> diferencias) {
		
		int countDiff = 0;
		
		if(diferencias != null && !diferencias.isEmpty()){
			for (Entry<String, CambioComparacionEnum> entry : diferencias.entrySet()) {
			    if(fueCambio(entry.getValue())) {
			    	countDiff ++;
			    }
			}
		}
		
		return countDiff;		
	}
}