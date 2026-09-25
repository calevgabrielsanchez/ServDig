package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.utility;

import java.math.BigDecimal;
import java.util.Date;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.enums.DocumentosEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorioRenapoEnum;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipioPK;
import mx.gob.imss.ctirss.delta.persistence.DitActa;
import mx.gob.imss.ctirss.delta.persistence.DitCurp;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitNacimiento;

@Stateless
public class DocumentoProbatorioServiceUtility extends AbstractServiceUtility
		implements DocumentoProbatorioServiceUtilityLocal {

	@Override
	public DitDocumentoProbatorio transformarDocumentoProbatorio(
			DocumentoProbatorio modelo) throws TransformacionException {

		this.log.debug("Entrando a transformarDocumentoProbatorio(modelo)");
		this.log.debug("Modelo -> " + modelo.toString());

		DitDocumentoProbatorio ditDocumentoProbatorio = new DitDocumentoProbatorio();

		if (modelo instanceof Nacimiento) {

			this.log.debug("Es ACTA DE NACIMIENTO!");

			Nacimiento nacimiento = (Nacimiento) modelo;

			DitActa ditActa = new DitActa();
			DitNacimiento ditNacimiento = new DitNacimiento();

			// Se llena el objeto ditNacimiento
			ditNacimiento.setCveCrip(nacimiento.getCrip());
			if (nacimiento.getIdDocumentoProbatorio() != null) {
				ditNacimiento.setCveIdDocumentoProbatorio(nacimiento
						.getIdDocumentoProbatorio());
			}
			
		
			
			if(nacimiento.getAnio() != null){
				ditNacimiento.setNumAnio(nacimiento.getAnio());
			}else{
				this.log.warn("El dato Anio es nulo, se setea la default ");
				ditNacimiento.setNumAnio(1900);
			}
			
			if(nacimiento.getTomo() != null){
				ditActa.setRefNumTomo(nacimiento.getTomo());
			}else{
				this.log.warn("El dato RefNumTomo es nulo, se setea la default ");
				ditActa.setRefNumTomo("null");
			}
			
			
			//se cambio a acta el juzgado
			if(nacimiento.getNoJuzgado() != null){
				ditActa.setNumJuzgado(nacimiento.getNoJuzgado());
			}else{
				this.log.warn("El dat NumJuzgado es nulo, se setea la default ");
				ditActa.setNumJuzgado("00");
			}
			

			// Se llena el objeto ditActa
			if (nacimiento.getIdDocumentoProbatorio() != null) {
				ditActa.setCveIdDocumentoProbatorio(nacimiento
						.getIdDocumentoProbatorio());
			}
			if(nacimiento.getFechaSuceso() != null){
				ditActa.setFecSuceso(nacimiento.getFechaSuceso());
			}else{
				this.log.warn("La fecha de suceso del documento es nulo, se setea la default ");
				ditActa.setFecSuceso(new Date());
			}
			//TODO: Parche para no guardar el municipio
			if (nacimiento.getMunicipio() != null
					&& nacimiento.getMunicipio().getEntidadFederativa() != null) {
				
				DgCatMunicipioPK dgCatMunicipioPK = new DgCatMunicipioPK();
				DgCatMunicipio dgCatMunicipio = new DgCatMunicipio();
				DgCatEstado dgCatEstado = new DgCatEstado();
				
				dgCatMunicipioPK.setCveEnt(nacimiento.getMunicipio()
						.getEntidadFederativa().getClave());
				dgCatMunicipioPK.setCveMun(nacimiento.getMunicipio().getClave());
				dgCatMunicipio.setId(dgCatMunicipioPK);
				dgCatEstado.setCveEnt(nacimiento.getMunicipio()
						.getEntidadFederativa().getClave());
				dgCatMunicipio.setDgCatEstado(dgCatEstado);
				ditActa.setDgCatMunicipio(dgCatMunicipio);
			}
			
			if(nacimiento.getNoActa() != null){
				ditActa.setNumActa(nacimiento.getNoActa());	
			}else{
				this.log.warn("El dato No Acta es nulo, seteando el default");
				ditActa.setNumActa("0");
			}
			
			if(nacimiento.getNoFoja() != null){
				ditActa.setNumFoja(nacimiento.getNoFoja());	
			}else{
				this.log.warn("El dato No Foja es nulo, seteando el default");
				ditActa.setNumFoja("0");
			}
			if(nacimiento.getNoLibro() != null){
				ditActa.setNumLibro(nacimiento.getNoLibro());
				
			}else{
				this.log.warn("El dato Num Libro es nulo, seteando el default");
				ditActa.setNumLibro("0");
			}
			

			
			ditActa.setDitNacimiento(ditNacimiento);

			ditNacimiento.setDitActa(ditActa);

			// Se llena el objeto ditDocumentoAprobatorio
			if (nacimiento.getIdDocumentoProbatorio() != null) {
				ditDocumentoProbatorio.setCveIdDocumentoProbatorio(nacimiento
						.getIdDocumentoProbatorio().longValue() );
			}
			if(nacimiento
					.getFechaExpedicion() != null){
				ditDocumentoProbatorio.setFecExpedicion(nacimiento
						.getFechaExpedicion());
			}else{
				this.log.warn("La fecha de expedicion del documento es nulo, se setea la default ");
				ditDocumentoProbatorio.setFecExpedicion(new Date());
			}
			
			ditDocumentoProbatorio.setDitActa(ditActa);
			
			ditDocumentoProbatorio.setFecRegistroAlta(new Date());

			ditActa.setDitDocumentoProbatorio(ditDocumentoProbatorio);

		}else if( modelo instanceof CURP){
			
			this.log.debug("El documento es de tipo  CURP");
			
			
			CURP curp = (CURP) modelo;
			
			DitCurp ditCurp = new DitCurp();
			
			ditCurp.setCveCrip(curp.getCrip());
			ditCurp.setCveCurp(curp.getCurp());
			ditCurp.setFecInscripcion(curp.getFechaInscripcion());
			ditCurp.setNumActa(curp.getNoActa());
			if(curp.getAnioRegistro() != null ){
				ditCurp.setNumAnio( new BigDecimal( curp.getAnioRegistro()));
			}else{
				ditCurp.setNumAnio( BigDecimal.ZERO);
			}
			
			ditCurp.setNumFoja(curp.getNoFoja());
			ditCurp.setNumLibro(curp.getNoLibro());
			ditCurp.setNumTomo(curp.getNoTomo());
			ditCurp.setRefFolio(curp.getNumFolioExtranjero());
			ditCurp.setNumTipoDocRenapo(new Long(0));
			
			
			
			//TODO: Parche para no guardar el municipio
			if (curp.getMunicipio() != null
					&& curp.getMunicipio().getEntidadFederativa() != null) {
				
				DgCatMunicipioPK dgCatMunicipioPK = new DgCatMunicipioPK();
				DgCatMunicipio dgCatMunicipio = new DgCatMunicipio();
				DgCatEstado dgCatEstado = new DgCatEstado();
				
				dgCatMunicipioPK.setCveEnt(curp.getMunicipio()
						.getEntidadFederativa().getClave());
				dgCatMunicipioPK.setCveMun(curp.getMunicipio().getClave());
				dgCatMunicipio.setId(dgCatMunicipioPK);
				dgCatEstado.setCveEnt(curp.getMunicipio()
						.getEntidadFederativa().getClave());
				dgCatMunicipio.setDgCatEstado(dgCatEstado);
				
				//ditCurp.setDgCatEstado(dgCatEstado);
				
			}
			
			
			
			
			
			
			if(curp.getIdDocumentoProbatorio() != null){
				this.log.debug("El CURP ya tiene un ID de documento");
				ditDocumentoProbatorio.setCveIdDocumentoProbatorio( curp.getIdDocumentoProbatorio().longValue() );
			}
			
			if(curp
					.getFechaExpedicion() != null){
				ditDocumentoProbatorio.setFecExpedicion(curp
						.getFechaExpedicion());
			}else{
				this.log.warn("La fecha de expedicion del documento es nulo, se setea la default ");
				ditDocumentoProbatorio.setFecExpedicion(new Date());
			}
			
			
			ditDocumentoProbatorio.setFecRegistroAlta(new Date());
			ditDocumentoProbatorio.setDitCurp(ditCurp);

			ditCurp.setDitDocumentoProbatorio(ditDocumentoProbatorio);
			
			
			
			
		}

		this.log.debug("Se genero el siguiente objeto ditDocumentoProbatorio -> "
				+ ditDocumentoProbatorio.toString());

		return ditDocumentoProbatorio;
	}

	@Override
	public DocumentoProbatorio transformarDocumentoProbatorio(
			DitDocumentoProbatorio entity) throws TransformacionException {
	    if(entity == null) {
	        return null;
	    }
		DocumentoProbatorio documentoProbatorio = null;

		if (entity.getDitActa() != null) {
			// Acta nacimiento
			if (entity.getDitActa().getDitNacimiento() != null) {
				
				Nacimiento nacimiento = new Nacimiento();

				// Se llenan los atributos propios del objeto Nacimiento
				nacimiento.setCrip(entity.getDitActa().getDitNacimiento()
						.getCveCrip());
				nacimiento.setNoJuzgado(entity.getDitActa()
						.getNumJuzgado());
				nacimiento.setAnio(entity.getDitActa().getDitNacimiento().getNumAnio());
				nacimiento.setTomo(entity.getDitActa().getRefNumTomo());

				// Se llenan los atributos propios del objeto Acta
				nacimiento.setFechaSuceso(entity.getDitActa().getFecSuceso());
				Municipio municipio = new Municipio();
				
				//TODO: Probar que jale sin parche para no guardar el municipio
				
				EntidadFederativa entidadFederativa = new EntidadFederativa();
				
				DgCatMunicipio dgCatMunicipio =  entity.getDitActa().getDgCatMunicipio();
				municipio.setClave(dgCatMunicipio.getId()
						.getCveMun());
				municipio.setNombre(dgCatMunicipio.getNomMun());
				
				
				DgCatEstado dgCatEstado = dgCatMunicipio.getDgCatEstado();
				
				entidadFederativa.setClave(dgCatEstado.getCveEnt());
				entidadFederativa.setNombre(dgCatEstado.getNomEnt());
				
				municipio.setEntidadFederativa(entidadFederativa);
				
				nacimiento.setMunicipio(municipio);
				
				this.log.debug("Municipio seteado " + municipio.getClave() );
				
				nacimiento.setNoActa(entity.getDitActa().getNumActa());
				nacimiento.setNoFoja(entity.getDitActa().getNumFoja());
				nacimiento.setNoLibro(entity.getDitActa().getNumLibro());

				// Se llenan los atributos propios del objeto DocumentoProbatorio
				nacimiento.setIdDocumentoProbatorio(Long.valueOf(
						entity.getCveIdDocumentoProbatorio()).intValue());
				nacimiento.setFechaExpedicion(entity.getFecExpedicion());
				
				if(entity.getDitDocumentoPorTipo() != null){
					Documento documento = new Documento();
					documento.setCveIdDocumento(entity.getDitDocumentoPorTipo().getDicDocumento().getCveIdDocumento());
					documento.setDesDocumento(entity.getDitDocumentoPorTipo().getDicDocumento().getDesDocumento());
					
					TipoDocumentoProbatorio tipoDocumentoProbatorio = new TipoDocumentoProbatorio();
					tipoDocumentoProbatorio.setIdTipoDocumentoProbatorio(Long.valueOf(entity.getDitDocumentoPorTipo().getDicTipoDocumentoProbatorio().getCveIdTipoDocumentoProbator()).intValue());
					tipoDocumentoProbatorio.setDescripcion(entity.getDitDocumentoPorTipo().getDicTipoDocumentoProbatorio().getDesTipoDocumentoProbatorio());
					
					DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
					documentoPorTipo.setIdDocumentoPorTipo(entity.getDitDocumentoPorTipo().getCveIdDoctoProbPorTipo());
					documentoPorTipo.setTipoDocumentoProbatorio(tipoDocumentoProbatorio);
					documentoPorTipo.setDocumento(documento);
	
					nacimiento.setDocumentoPorTipo(documentoPorTipo);
				}
				
				return nacimiento;

			}
		} else if (entity.getDitCurp() != null) {
			DitCurp ditCurp = entity.getDitCurp();
			CURP curp = new CURP();

			curp.setNumTipoDocumento(ditCurp.getNumTipoDocRenapo());
			
			/*
			 * Se checa que se tenga el DocumentoPorTipo, si se tiene se settea
			 * de lo contrario se genera tomando en cuente el campo
			 * numTipoDocumento
			 */
			if (ditCurp.getDitDocumentoProbatorio() != null
					&& ditCurp.getDitDocumentoProbatorio()
							.getDitDocumentoPorTipo() != null) {

				Documento documento = new Documento();
				documento.setCveIdDocumento(ditCurp.getDitDocumentoProbatorio()
							.getDitDocumentoPorTipo().getDicDocumento().getCveIdDocumento());
				documento.setDesDocumento(ditCurp.getDitDocumentoProbatorio()
							.getDitDocumentoPorTipo().getDicDocumento().getDesDocumento());
								
				DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
				documentoPorTipo.setIdDocumentoPorTipo(ditCurp.getDitDocumentoProbatorio()
						.getDitDocumentoPorTipo().getCveIdDoctoProbPorTipo());
				documentoPorTipo.setDocumento(documento);
				
				curp.setDocumentoPorTipo(documentoPorTipo);
			} else if(curp.getNumTipoDocumento() != null) {
				
				Long cveIdDocumento = null;
				String descDocumento = null;
								
				Long idDocumentoPorTipo = null;
								
				if (curp.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO
						.getValor().intValue()) {
					cveIdDocumento = DocumentosEnum.ACTA_NACIMIENTO.getId();
					descDocumento = DocumentosEnum.ACTA_NACIMIENTO.getDescripcion();
					
					idDocumentoPorTipo = DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId();
				} else if (curp.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO
						.getValor().intValue()) {
					cveIdDocumento = DocumentosEnum.DOCUMENTO_MIGRATORIO.getId();
					descDocumento = DocumentosEnum.DOCUMENTO_MIGRATORIO.getDescripcion();
					
					idDocumentoPorTipo = DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId();
				} else if (curp.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION
						.getValor().intValue()) {
					cveIdDocumento = DocumentosEnum.CARTA_NATURALIZACION.getId();
					descDocumento = DocumentosEnum.CARTA_NATURALIZACION.getDescripcion();
					
					idDocumentoPorTipo = DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId();
				} else if (curp.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO
						.getValor().intValue()) {
					cveIdDocumento = DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getId();
					descDocumento = DocumentosEnum.NUMERO_UNICO_DE_EXTRANJERO.getDescripcion();
					
					idDocumentoPorTipo = DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId();
				} else if (curp.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA
						.getValor().intValue()) {
					cveIdDocumento = DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getId();
					descDocumento = DocumentosEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getDescripcion();
					
					idDocumentoPorTipo = DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId();
				} else if (curp.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO
						.getValor().intValue()) {
					cveIdDocumento = DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getId();
					descDocumento = DocumentosEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getDescripcion();
					
					idDocumentoPorTipo = DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId();
				} else if (curp.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA
						.getValor().intValue()) {
					cveIdDocumento = DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getId();
					descDocumento = DocumentosEnum.FORMA_MIGRATORIA_TURISTA.getDescripcion();
					
					idDocumentoPorTipo = DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId();
				}			
				
				Documento documento = new Documento();
				documento.setCveIdDocumento(cveIdDocumento);
				documento.setDesDocumento(descDocumento);
								
				DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
				documentoPorTipo.setIdDocumentoPorTipo(idDocumentoPorTipo);
				documentoPorTipo.setDocumento(documento);
				
				curp.setDocumentoPorTipo(documentoPorTipo);
			}
			
			curp.setIdDocumentoProbatorio((int)ditCurp.getCveIdDocumentoProbatorio());
			
			curp.setCrip(ditCurp.getCveCrip());
			curp.setCurp(ditCurp.getCveCurp());
			curp.setFechaInscripcion(ditCurp.getFecInscripcion());
			curp.setNoActa(ditCurp.getNumActa());
			
			if (ditCurp.getNumAnio() != null) {
				curp.setAnioRegistro(ditCurp.getNumAnio().longValue());
			}
			
			curp.setNoFoja(ditCurp.getNumFoja());
			curp.setNumFolioExtranjero(ditCurp.getNumFolioExtranjero());
			curp.setNoLibro(ditCurp.getNumLibro());
			curp.setNoTomo(ditCurp.getNumTomo());
			curp.setRefFolio(ditCurp.getRefFolio());

			Municipio municipio = new Municipio();
			DgCatMunicipio dgCatMunicipio = ditCurp.getDgCatMunicipio();

			if (dgCatMunicipio != null) {
				municipio.setClave(dgCatMunicipio.getId().getCveMun());
				municipio.setNombre(dgCatMunicipio.getNomMun());

				curp.setMunicipio(municipio);

				EntidadFederativa entidadFederativa = new EntidadFederativa();
				DgCatEstado dgCatEstado = dgCatMunicipio.getDgCatEstado();

				entidadFederativa.setClave(dgCatEstado.getCveEnt());
				entidadFederativa.setNombre(dgCatEstado.getNomEnt());

				municipio.setEntidadFederativa(entidadFederativa);
			}

			if (entity.getFecExpedicion() != null) {
				curp.setFechaExpedicion(entity.getFecExpedicion());
			}

			return curp;
		}

		return documentoProbatorio;
	}
}
