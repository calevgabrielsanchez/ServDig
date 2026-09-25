package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acta;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ActaTerminoUnionCivil;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ActaUnionCivil;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Adimss;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CartillaMilitar;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CedulaProfesional;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CertificadoNacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CertificadoSituacionCritica;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ComprobanteDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DictamenIntegranteIncapacitado;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FormaMigratoria;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Ife;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.MatriculaConsular;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Obstetrico;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Pasaporte;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.VigenciaTemporal;
import mx.gob.imss.ctirss.delta.persistence.DitAcuerdo;
import mx.gob.imss.ctirss.delta.persistence.DitAdimss;
import mx.gob.imss.ctirss.delta.persistence.DitActaTerminoUnionCivil;
import mx.gob.imss.ctirss.delta.persistence.DitActaUnionCivil;
import mx.gob.imss.ctirss.delta.persistence.DitCartillaMilitar;
import mx.gob.imss.ctirss.delta.persistence.DitCedulaProfesional;
import mx.gob.imss.ctirss.delta.persistence.DitCertificadoNacimiento;
import mx.gob.imss.ctirss.delta.persistence.DitCertificadoSitCritica;
import mx.gob.imss.ctirss.delta.persistence.DitComprobanteDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DitConstanciaEstudio;
import mx.gob.imss.ctirss.delta.persistence.DitCredElector;
import mx.gob.imss.ctirss.delta.persistence.DitCurp;
import mx.gob.imss.ctirss.delta.persistence.DitDictBeneficiarioInca;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoPorTipo;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitFormaMigratoria;
import mx.gob.imss.ctirss.delta.persistence.DitMatriculaConsular;
import mx.gob.imss.ctirss.delta.persistence.DitNacimiento;
import mx.gob.imss.ctirss.delta.persistence.DitObstetrico;
import mx.gob.imss.ctirss.delta.persistence.DitPasaporte;
import mx.gob.imss.ctirss.delta.persistence.DitVigenciaTemporal;

public class DocumentoProbatorioParser {
	
	private static Log log = LogFactory.getLog(DocumentoProbatorioParser.class);
	
	public static Object modelToModelActa(DocumentoProbatorio entrada){
		Object salida = null;
		
		try{
			if (entrada != null) {
					Acta acta=(Acta) entrada;
					acta.setIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida=ActaParser.modelToPersist(acta);
					log.debug("ACTA_PRUEBA");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return salida;
	}
	

	public static Object modelToModelCaptura(DocumentoProbatorio entrada) throws DocumentoProbatorioException {
		Object salida = null;
		if (entrada != null) {
			try{
				if(entrada instanceof CartillaMilitar){
					DitCartillaMilitar ditCartillaMilitar = CartillaMilitarParser
					.modelToPersist((CartillaMilitar) entrada);
					ditCartillaMilitar
					.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditCartillaMilitar;
				}
				if(entrada instanceof CedulaProfesional){
					DitCedulaProfesional ditCedulaProfesional = CedulaProfecionalParser
					.modelToPersist((CedulaProfesional) entrada);
					ditCedulaProfesional
					.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditCedulaProfesional;
				}
				if(entrada instanceof ConstanciaEstudio){
					DitConstanciaEstudio ditConstanciaEstudio = ConstanciaEstudioParser
					.modelToPersist((ConstanciaEstudio) entrada);
					ditConstanciaEstudio
					.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditConstanciaEstudio;
				}
				if(entrada instanceof Ife){
					DitCredElector ditCredElector = IfeParser
					.modelToPersist((Ife) entrada);
					ditCredElector
					.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditCredElector;
				}
				if(entrada instanceof CURP){
					DitCurp ditCurp = CurpParser.modelToPersist((CURP) entrada);
					ditCurp.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditCurp;
				}
				if(entrada instanceof Pasaporte){
					DitPasaporte ditPasaporte = PasaporteParser
					.modelToPersist((Pasaporte) entrada);
					ditPasaporte.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditPasaporte;
				}
				if(entrada instanceof CertificadoSituacionCritica){
					DitCertificadoSitCritica ditCertificadoSitCritica = CertificadoSituacionCriticaParser
					.modelToPersist((CertificadoSituacionCritica) entrada);
					ditCertificadoSitCritica
					.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditCertificadoSitCritica;
				}
				if(entrada instanceof Acuerdo){
					DitAcuerdo ditAcuerdo = AcuerdoParser
					.modelToPersist((Acuerdo) entrada);
					ditAcuerdo.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditAcuerdo;
				}
				if(entrada instanceof CertificadoNacimiento){
					DitCertificadoNacimiento ditCertificadoNacimiento = CertificadoNacimientoParser
					.modelToPersist((CertificadoNacimiento) entrada);
					ditCertificadoNacimiento
					.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditCertificadoNacimiento;
				}
				if(entrada instanceof DictamenIntegranteIncapacitado){
					DitDictBeneficiarioInca dictBeneficiarioInca = DictamenIntegranteIncapacitadoParser
					.modelToPersist((DictamenIntegranteIncapacitado) entrada);
					dictBeneficiarioInca
					.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = dictBeneficiarioInca;
				}
				if(entrada instanceof Obstetrico){
					DitObstetrico ditObstetrico = ObstetricoParser
					.modelToPersist((Obstetrico) entrada);
					ditObstetrico
					.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditObstetrico;
				}
				if(entrada instanceof VigenciaTemporal){
					DitVigenciaTemporal ditVigenciaTemporal = VigenciaTemporalParser
					.modelToPersist((VigenciaTemporal) entrada);
					ditVigenciaTemporal
					.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditVigenciaTemporal;
				}
				if(entrada instanceof ComprobanteDomicilio){
					DitComprobanteDomicilio ditComprobanteDomicilio=ComprobanteDomicilioParser.modelToPersist((ComprobanteDomicilio)entrada);
					ditComprobanteDomicilio.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditComprobanteDomicilio;
				}
				if(entrada instanceof Adimss){
					DitAdimss ditAdimss = AdimssParser.modelToPersist((Adimss)entrada);
					ditAdimss.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditAdimss;
				}
				if(entrada instanceof MatriculaConsular){
					DitMatriculaConsular ditMatriculaConsular = MatriculaConsularParser.modelToPersist((MatriculaConsular)entrada);
					ditMatriculaConsular.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditMatriculaConsular;
				}
				if(entrada instanceof Nacimiento){
					DitNacimiento ditNacimiento = NacimientoParser.modelToPersist((Nacimiento) entrada);
					ditNacimiento.setDitDocumentoProbatorio(new DitDocumentoProbatorio());
					ditNacimiento.getDitDocumentoProbatorio().setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio().longValue());
					ditNacimiento.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditNacimiento;
				}
				if(entrada instanceof FormaMigratoria) {
					DitFormaMigratoria ditFormaMigratoria = FormaMigratoriaParser.modelToPersist((FormaMigratoria) entrada);
					ditFormaMigratoria.setDitDocumentoProbatorio(new DitDocumentoProbatorio());
					ditFormaMigratoria.getDitDocumentoProbatorio().setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio().longValue());
					salida = ditFormaMigratoria;
				}
				if(entrada instanceof ActaUnionCivil) {
					DitActaUnionCivil ditActaUnionCivil = ActaUnionCivilParser.modelToPersist((ActaUnionCivil) entrada);
					ditActaUnionCivil.setDitDocumentoProbatorio(new DitDocumentoProbatorio());
					ditActaUnionCivil.getDitDocumentoProbatorio().setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio().longValue());
					ditActaUnionCivil.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());

					salida = ditActaUnionCivil;
				}
				if(entrada instanceof ActaTerminoUnionCivil) {
					DitActaTerminoUnionCivil ditActaTerminoUnionCivil = ActaTerminoUnionCivilParser.modelToPersist((ActaTerminoUnionCivil) entrada);
					ditActaTerminoUnionCivil.setDitDocumentoProbatorio(new DitDocumentoProbatorio());
					ditActaTerminoUnionCivil.getDitDocumentoProbatorio().setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio().longValue());
					ditActaTerminoUnionCivil.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio());
					salida = ditActaTerminoUnionCivil;
				}
		
				
			}catch(Exception e){
				
				// ---------------------------------------
				// Lanza ClassCastException
				// ---------------------------------------
				e.printStackTrace();
				DocumentoProbatorioException.throwException(e.getMessage(), e.getMessage());
				
				
			}
		}
	return salida;
}
	
	
	
	
	
	public static final DitDocumentoProbatorio modelToPersist(DocumentoProbatorio entrada ){
		DitDocumentoProbatorio salida=null;
		DitDocumentoPorTipo ditDocumentoPorTipo=null;
		if(entrada!=null){
			
			salida=new DitDocumentoProbatorio();
			salida.setRefDocumentoDigitalizado(entrada.getDigitalizacion());
			salida.setRefCodEncriptado(entrada.getCifrado());
			if(entrada.getFechaExpedicion()==null){
				salida.setFecExpedicion(new Date());
			}else{
				salida.setFecExpedicion(entrada.getFechaExpedicion());
			}
			
			salida.setFecRegistroAlta(new Date());
			
		
			if(entrada.getDocumentoPorTipo()!=null){
				//aqui se optiene el tipo y documento especifico
				ditDocumentoPorTipo=new DitDocumentoPorTipo();
				//el id de documento por tipo No lo se
				ditDocumentoPorTipo.setCveIdDoctoProbPorTipo(entrada.getDocumentoPorTipo().getIdDocumentoPorTipo());
				salida.setDitDocumentoPorTipo(ditDocumentoPorTipo);
			}
		
			// Se checa si el model trae el id, si es así se settea al entity
			if(entrada.getIdDocumentoProbatorio() != null && entrada.getIdDocumentoProbatorio() > 0){
				salida.setCveIdDocumentoProbatorio(entrada.getIdDocumentoProbatorio().longValue());
			}
			salida.setNomNombreDocumento(entrada.getNomNombreDocumento());

			if(entrada.getBovedaDocId() != null && !entrada.getBovedaDocId().trim().isEmpty()){
				salida.setRefBovedaDocId(entrada.getBovedaDocId());
			}


		}
		return salida;
	}
/**
 * Trae todo y el documento especifico capturado
 * @param entradaList
 * @return
 * @throws DerechohabientesBusinessException 
 */
	public static List<DocumentoProbatorio> PersistToModelList(
			List<DitDocumentoProbatorio> entradaList) throws DocumentoProbatorioException {
		List<DocumentoProbatorio> salida=new ArrayList<DocumentoProbatorio>();
		for(DitDocumentoProbatorio entrada:entradaList){
			salida.add(PersistToModel(entrada));
		}
		return salida;
	}
	/**
	 * Solo trae el documento por tipo y el id
	 * @param entradaList
	 * @return
	 */
	public static List<DocumentoProbatorio> PersistToModelDocumentoProbatorioOnlyList(
			List<DitDocumentoProbatorio> entradaList) {
		List<DocumentoProbatorio> salida=new ArrayList<DocumentoProbatorio>();
		for(DitDocumentoProbatorio entrada:entradaList){
			salida.add(PersistToModelDocumentoProbatorioOnly(entrada));
		}
		return salida;
	}
/**
 * solo trae el documento digitalizado
 * @param entrada
 * @return
 */
	public static DocumentoProbatorio PersistToModelBytesOnly(DitDocumentoProbatorio entrada){
			DocumentoProbatorio salida=null;
			
			if(entrada!=null){
				salida=new DocumentoProbatorio();
				salida.setDigitalizacion(entrada.getRefDocumentoDigitalizado());
				salida.setCifrado(entrada.getRefCodEncriptado());
			}
			return salida;
	}
	/**
	 * Solo  trae el documento por tipo y el id
	 * @param entrada
	 * @return
	 */
	public static DocumentoProbatorio PersistToModelDocumentoProbatorioOnly(DitDocumentoProbatorio entrada){
		DocumentoProbatorio salida=null;
		
		if(entrada!=null){
			salida=new DocumentoProbatorio();
			salida.setIdDocumentoProbatorio(entrada.getCveIdDocumentoProbatorio().intValue());
			salida.setDocumentoPorTipo(DocumentoPorTipoParser.persistToModel(entrada.getDitDocumentoPorTipo()));
			salida.setNomNombreDocumento(entrada.getNomNombreDocumento());
			salida.setBovedaDocId(entrada.getRefBovedaDocId());
			salida.setFechaBaja(entrada.getFecRegistroBaja());
		}
		return salida;
	}
	
	 /**
	  * Trae todo y el documento especifico capturado
	  * @param entrada
	  * @return
	 * @throws DerechohabientesBusinessException 
	  */
	 
	public static DocumentoProbatorio PersistToModel(DitDocumentoProbatorio entrada ) throws DocumentoProbatorioException{
		DocumentoProbatorio salida=null;
	
		if(entrada!=null){
			salida=getClaseDocumentoProbatorio(entrada);
			salida.setDigitalizacion(entrada.getRefDocumentoDigitalizado());
			salida.setCifrado(entrada.getRefCodEncriptado());
			salida.setFechaExpedicion(entrada.getFecExpedicion());
			salida.setIdDocumentoProbatorio(entrada.getCveIdDocumentoProbatorio().intValue());
			salida.setDocumentoPorTipo(DocumentoPorTipoParser.persistToModel(entrada.getDitDocumentoPorTipo()));
			salida.setNomNombreDocumento(entrada.getNomNombreDocumento());
			salida.setBovedaDocId(entrada.getRefBovedaDocId());
		}
		return salida;
	}
	
	private static DocumentoProbatorio getClaseDocumentoProbatorio(DitDocumentoProbatorio entrada ) throws DocumentoProbatorioException{
		DocumentoProbatorio salida=null;
		//aumentar este numero si se agan mas parseos
		for(int caso=0;caso<=19;caso++){
			salida=obtenerDocumentoExistente(caso,entrada);
			//si ya encuentra documento salir
			if(salida!=null){
				
				break;
			}
		}
		//si no encontrro ninguno
		if(salida==null){
			salida=new DocumentoProbatorio();
		}
		return salida;
	}
	
	private static DocumentoProbatorio obtenerDocumentoExistente(int caso,DitDocumentoProbatorio entrada) throws DocumentoProbatorioException{
		DocumentoProbatorio salida=null;
		switch (caso){
		case 0:
			salida=NacimientoParser.persistToModel(entrada.getDitNacimiento());	
			break;
		case 1:
			salida=ActaParser.persistToModel(entrada.getDitActa());	
			break;
		case 2:
			salida=AcuerdoParser.persistToModel(entrada.getDitAcuerdo());
			break;
		case 3:
			salida=CartillaMilitarParser.persistToModel(entrada.getDitCartillaMilitar());
			break;
		case 4:
			salida=CedulaProfecionalParser.persistToModel(entrada.getDitCedulaProfesional());
			break;
		case 5:
			salida=CertificadoNacimientoParser.persistToModel(entrada.getDitCertificadoNacimiento());
			break;
		case 6:
			salida=CertificadoSituacionCriticaParser.persistToModel(entrada.getDitCertificadoSitCritica());
			break;
		case 7:
			salida=ConstanciaEstudioParser.persisToModel(entrada.getDitConstanciaEstudio());
			break;
		case 8:
			salida=CurpParser.persisToModel(entrada.getDitCurp());
			break;
		case 9:
			salida=IfeParser.persistToModel(entrada.getDitCredElector());
			break;
		case 10:
			salida=ObstetricoParser.persisToModel(entrada.getDitObstetrico());
			break;
		case 11:
			salida=PasaporteParser.persisToModel(entrada.getDitPasaporte());
			break;
		case 12:
			salida=VigenciaTemporalParser.persisToModel(entrada.getDitVigenciaTemporal());
			break;		
		case 13:
			salida=DictamenIntegranteIncapacitadoParser.persisToModel(entrada.getDitDictBeneficiarioInca());
			break;
		case 14:
			salida=ComprobanteDomicilioParser.persisToModel(entrada.getDitComprobanteDomicilio());
			break;
		case 15:
			salida = AdimssParser.persistToModel(entrada.getDitAdimss());
			break;
		case 16:
			salida= FormaMigratoriaParser.persistToModel(entrada.getDitFormaMigratoria());
			break;
		case 17:
			salida= MatriculaConsularParser.persistToModel(entrada.getDitMatriculaConsular());
			break;
		case 18:
			salida= ActaUnionCivilParser.persistToModel(entrada.getDitActaUnionCivil());
			break;
		case 19:
			salida= ActaTerminoUnionCivilParser.persistToModel(entrada.getDitActaTerminoUnionCivil());
			break;
		}
		return salida;
	}




}
