package mx.gob.imss.ctirss.delta.gestion.documento.probatorio.test;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acta;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CartillaMilitar;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CedulaProfesional;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CertificadoNacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CertificadoSituacionCritica;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ComprobanteDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DetalleNivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DictamenIntegranteIncapacitado;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Ife;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Obstetrico;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Pasaporte;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.VigenciaTemporal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;

public class DocumentoProbatorioHardData {

	private static final String LOCAL_FILE_IMAGE_URI = "C:/test/testInput.jpg";
	private static byte[] localImage;

	/**
	 * LLena la parte del tipo y captura del docProbatorio
	 * 
	 * @param dP
	 * @return
	 */
	public static DocumentoProbatorio getDocumentoProv(DocumentoProbatorio dP) {

		// se le setea el documento capturado

		dP.setDigitalizacion(DocumentoProbatorioHardData.getLocalImagen());
		dP.setCifrado("GIF");// tipo de imagen original	
		return dP;
	}

	/**
	 * Obtener una imagen local
	 * 
	 * @return
	 */

	public static byte[] getLocalImagen() {

		if (localImage == null) {
			// TODO Auto-generated method stub
			try {
				File file = new File(LOCAL_FILE_IMAGE_URI);
				byte[] b = new byte[(int) file.length()];
				FileInputStream fileInputStream = new FileInputStream(file);
				fileInputStream.read(b);
				localImage = b;

			} catch (Exception e) {
				System.out
						.println("*****SI quieren evitar este error pongan una imagen en: "
								+ LOCAL_FILE_IMAGE_URI
								+ "Ya que esta imagen es la que guardara como documento digitalizado");
				e.printStackTrace();
			}
			// image.setAbsolutePosition(450,650)

			// image.scaleAbsolute(200,200)

		}
		return localImage;
	}

	public static Acta getActa(int variant) {
		Acta acta = new Acta();
		acta.setFechaExpedicion(new Date());
		acta.setFechaSuceso(new Date());
		acta.setMunicipio(new Municipio());
		acta.getMunicipio().setClave("1");
		acta.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		acta.getMunicipio().getEntidadFederativa().setClave("1");
		acta.setNoActa("00" + variant);
		acta.setNoFoja("noFoja" + variant);
		acta.setNoJuzgado("" + variant);
		acta.setNoLibro("noLibro" + variant);
		acta.setDocumentoPorTipo(new DocumentoPorTipo());
		acta.getDocumentoPorTipo().setIdDocumentoPorTipo(4L);
		return acta;
	}

	public static DocumentoProbatorio getAcuerdo(int i) {
		Acuerdo acuerdo = new Acuerdo();
		acuerdo.setFechaExpedicion(new Date());
		acuerdo.setInstanciaEmiteRes("emite" + i);
		acuerdo.setNoAcuerdo("NoAcu" + i);
		acuerdo.setDocumentoPorTipo(new DocumentoPorTipo());
		acuerdo.getDocumentoPorTipo().setIdDocumentoPorTipo(17L);
		return acuerdo;
	}

	public static DocumentoProbatorio getCartillaMilitar(int i) {
		CartillaMilitar salida = new CartillaMilitar();
		salida.setFechaExpedicion(new Date());
		salida.setNoMatricula("noMat"+i);
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(37L);
		return salida;
	}

	public static DocumentoProbatorio getCedulaProf(int i) {
		CedulaProfesional salida = new CedulaProfesional();
		salida.setCedula("cedula"+i);
		salida.setFechaExpedicion(new Date());
		salida.setProfesion("prof"+i);
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(35L);
		return salida;
	}

	public static DocumentoProbatorio getCertificadoNacimiento(int i) {
		CertificadoNacimiento salida = new CertificadoNacimiento();
		salida.setNoFolio("folio"+i);
		salida.setFechaAlumbramiento(new Date());
		salida.setFechaExpedicion(new Date());
		salida.setDesLugarAlumbramiento("");
		salida.setSexo(new Sexo());
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(24L);
		return salida;
	}

	public static DocumentoProbatorio getCertCitCrit(int i) {
		CertificadoSituacionCritica salida = new CertificadoSituacionCritica();
		salida.setEnfermedadPadecida("enfer"+i);
		salida.setFechaExpedicion(new Date());
		salida.setFechaTerminoIncapacidad(new Date());
		salida.setMedicoFamiliar(new MedicoFamiliar());
		salida.getMedicoFamiliar().setIdMedicoFamiliar(1L);
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(21L);
		return salida;
	}

	public static DocumentoProbatorio getConstanciaEstudios(int i) {
		ConstanciaEstudio salida = new ConstanciaEstudio();
		salida.setClaveEscuela("cve"+i);
		salida.setFechaExpedicion(new Date());
		salida.setFechaFinPeriodo(new Date());
		salida.setFechaInicioPeriodo(new Date());
		salida.setGradoEscolar("grado"+i);
		salida.setNoIncorporacion("Inc"+i);
		salida.setNombreEscuela("Escuela"+i);
		salida.setDetalleNivelEducativo(new DetalleNivelEducativo());
		salida.getDetalleNivelEducativo().setIdDetalleNivelEducativo(1L);
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(19L);
		return salida;
	}

	public static DocumentoProbatorio getCurp(int i) {
		CURP salida = new CURP();
		salida.setCurp("CURP"+i);
		salida.setMunicipio(new Municipio());
		salida.getMunicipio().setClave("1");
		salida.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		salida.getMunicipio().getEntidadFederativa().setClave("1");
		salida.setFechaInscripcion(new Date());
		salida.setRefFolio("refFol"+i);
		salida.setAnioRegistro(new Long(i));
		salida.setNoLibro("noLib"+i);
		salida.setNoActa("noacta"+i);
		salida.setNoTomo("noTom"+i);
		salida.setCrip("crip"+i);
		salida.setNumFolioExtranjero("FolExt"+i);
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(34L);
		return salida;
	}

	public static DocumentoProbatorio getDictIntegranteInc(int i) {
		DictamenIntegranteIncapacitado salida = new DictamenIntegranteIncapacitado();
		salida.setFechaExpedicion(new Date());
		salida.setFechaInicioEnfermedad(new Date());
		salida.setMedicoFamiliar(new MedicoFamiliar());
		salida.getMedicoFamiliar().setIdMedicoFamiliar(1L);
		salida.setDiagnosticoPadecimiento("diagnosticoPad"+i);
		salida.setUnidadMedicaFamiliar(new UnidadMedicaFamiliar());
		salida.getUnidadMedicaFamiliar().setIdUMF(1L);
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(23L);
		return salida;
	}

	public static DocumentoProbatorio getIfe(int i) {
		Ife salida = new Ife();	
		
		//Folio
		salida.setFolio("folio" + i);
		//Año de registro
		salida.setAnioRegistro(new BigInteger(""+i));
		//Clave de elector
		salida.setClaveElector("clave"+i);
		//Entidad Federativa
		//Municipio
		//Localidad
		salida.setLocalidad(new Localidad());
		salida.getLocalidad().setClave("1");
		salida.getLocalidad().setMunicipio(new Municipio());
		salida.getLocalidad().getMunicipio().setClave("1");
		salida.getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
		salida.getLocalidad().getMunicipio().getEntidadFederativa().setClave("1");
		
		//Año de Emisión
		salida.setAnioRegistro(new BigInteger(""+i));
		//Código de seguridad
		salida.setCodigoSeguridad("codSeg"+i);
		salida.setEmision(""+i);
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(36L);
		return salida;
	}

	public static DocumentoProbatorio getNacimiento(int i) {
		Nacimiento salida = new Nacimiento();
		salida.setFechaExpedicion(new Date());
		salida.setFechaSuceso(new Date());
		salida.setMunicipio(new Municipio());
		salida.getMunicipio().setClave("1");
		salida.getMunicipio().setEntidadFederativa(new EntidadFederativa());
		salida.getMunicipio().getEntidadFederativa().setClave("1");
		salida.setNoActa("00" + i);
		salida.setNoFoja("noFoja" + i);
		salida.setNoJuzgado("" + i);
		salida.setNoLibro("noLibro" + i);
		salida.setCrip("crip"+i);
		salida.setAnio(i);
		salida.setTomo("tomo"+i);
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(1L);
		return salida;
	}

	public static DocumentoProbatorio getObstetrico(int i) {
		Obstetrico salida = new Obstetrico();
		salida.setFechaExpedicion(new Date());
		salida.setFechaCertificacionMedico(new Date());
		salida.setFechaParto(new Date());
		salida.setFechaProbableConcepcion(new Date());
		salida.setMedicoFamiliar(new MedicoFamiliar());
		salida.getMedicoFamiliar().setIdMedicoFamiliar(1l);
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(20L);
		return salida;
	}

	public static DocumentoProbatorio getPasaporte(int i) {
		Pasaporte salida = new Pasaporte();
		salida.setFechaCaducidad(new Date());
		salida.setFechaExpedicion(new Date());
		salida.setNoPasaporte("noPass"+i);
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(38L);
		return salida;
	}

	public static DocumentoProbatorio getVigTemp(int i) {
		VigenciaTemporal salida = new VigenciaTemporal();
		salida.setNoFolio("folio"+i);
		salida.setFechaExpedicion(new Date());
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(18L);
		return salida;
	}
	public static DocumentoProbatorio getComprobanteDomicilio(int i) {
		ComprobanteDomicilio salida = new ComprobanteDomicilio();
		salida.setFechaExpedicion(new Date());
		salida.setFolio("fol"+i);
		salida.setDocumentoPorTipo(new DocumentoPorTipo());
		salida.getDocumentoPorTipo().setIdDocumentoPorTipo(11L);//poner el correspondiente
		return salida;
	}

}
