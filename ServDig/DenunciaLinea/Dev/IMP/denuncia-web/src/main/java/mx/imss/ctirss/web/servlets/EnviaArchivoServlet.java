package mx.imss.ctirss.web.servlets;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.imss.ctirss.catalogos.model.DlcTipodenunciante;
import mx.imss.ctirss.catalogos.model.DlcTipodocumento;
import mx.imss.ctirss.denuncia.vo.DenunciaVO;
import mx.imss.ctirss.denuncia.vo.MotivoDenVO;
import mx.imss.ctirss.session.ConstantesSession;
import mx.imss.ctirss.session.UserSession;
import mx.imss.ctirss.web.controller.DenunciaController;
import mx.imss.ctirss.web.utils.GeneraReporteUtil;
import org.apache.log4j.Logger;
import org.apache.soap.encoding.soapenc.Base64;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PRStream;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class EnviaArchivoServlet extends HttpServlet {
	
	/**
	 * Logger.
	 */
	private final static Logger logger = Logger.getLogger(EnviaArchivoServlet.class);
	private static final long serialVersionUID = 1L;
	
	/**
	 * Inicializa el servlet.
	 * 
	 * @param config
	 *            - Un objeto ServletConfig
	 * @throws ServletException
	 *             - Una excepcion general que un servlet lanza cuando encuentra
	 *             dificultades.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

	/**
	 * Destruye el servlet.
	 */
	public void destroy() {

	}

	/**
	 * Procesa los requests para los metodos HTTP <code>GET</code> y
	 * <code>POST</code> .
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             - Una excepcion general que un servlet lanza cuando encuentra
	 *             dificultades.
	 * @throws IOException
	 *             - Producida por operaciones de entrada y salida fallidas o
	 *             interrumpidas.
	 */
	@SuppressWarnings("unchecked")
	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("EnviaArchivoServlet.processRequest");

		ServletOutputStream out = null;
		try {
		
//			URL path = DenunciaController.class.getResource("");
//			String ruta = path.getPath();
//			ruta = ruta.replace("zip:", "");
//			StringTokenizer token = new StringTokenizer(ruta, "/");
//			String rutaOriginal = "";
//			while(token.hasMoreTokens()){
//				String temp = token.nextToken(); 
//				if(temp.equals("war")){
//					rutaOriginal = rutaOriginal + temp+ "/" ;
//					break;
//				}else{
//					rutaOriginal = rutaOriginal + temp + "/";  
//				}//r1963
//			}
//			rutaOriginal = rutaOriginal + "resources/";
//			System.out.println(path.getPath());
			
			//QA
			String rutaOriginal = "/ctirss/w/gcf/gestcasos/domains/d_casos/files/";
			//Produccion
//			rutaOriginal = "/share/oracle/denlinea_01_domain/app/files/";
			
//	deployar PDF en local rutaOriginal = "C:/Users/user/denuncia/03IMP/denuncia-web/src/main/webapp/resources;
			DenunciaVO denunciaVO = (DenunciaVO)request.getSession().getAttribute("denunciaVO");
			if(denunciaVO == null){
				denunciaVO = (DenunciaVO)request.getSession().getAttribute("denunciaVOAcuse");
			}
			DlcTipodenunciante denunciante = (DlcTipodenunciante) request.getSession().getAttribute("denunciante");
			DgDomicilioGeografico domTrab = (DgDomicilioGeografico)request.getSession().getAttribute("domicilioTrabajador");
			DgDomicilioGeografico domBen = (DgDomicilioGeografico)request.getSession().getAttribute("domicilioBeneficiario");
			DgDomicilioGeografico domRep = (DgDomicilioGeografico)request.getSession().getAttribute("domicilioRepresentante");
			DgDomicilioGeografico domPatFis = (DgDomicilioGeografico)request.getSession().getAttribute("domicilioPatron");
			DgDomicilioGeografico domCenTrab = (DgDomicilioGeografico)request.getSession().getAttribute("domicilioCentroTrabajo");
			
			String documentacionAdjunta = (String) request.getSession().getAttribute("documentacionAdjunta");
			
			DlcTipodocumento documentoTrab = (DlcTipodocumento) request.getSession().getAttribute("documentoTrab");
			DlcTipodocumento documentoBen =(DlcTipodocumento) request.getSession().getAttribute("documentoBen");
			DlcTipodocumento documentoRep =(DlcTipodocumento) request.getSession().getAttribute("documentoRep");
			
			String actividadEconomica = (String)request.getSession().getAttribute("actividadEconomica");
			String formaPago = (String)request.getSession().getAttribute("formaPago");
			String comprobantesPago = (String)request.getSession().getAttribute("comprobantePago");
			String periodoPago = (String)request.getSession().getAttribute("periodoPago");
			
			Map parameters = new HashMap();
//			rutaOriginal = "C:/Desarrollo/wrkspIndigo/Denuncia/010305_DenunciaLinea/Dev/01DST/03IMP/denuncia-web/src/main/webapp/resources/";
			parameters.put("rutaImagen",rutaOriginal + "images/logo_imss.JPG");
			
//			rutaOriginal = "C:/Desarrollo/wrkspIndigo/Denuncia/010305_DenunciaLinea/Dev/01DST/03IMP/denuncia-web/src/main/webapp/resources/archivos/";
			parameters.put("SUBREPORT_DIR", rutaOriginal);
			parameters.put("rutaReporte", rutaOriginal);
			parameters.put("nssTrabajador",  denunciaVO.getDatosTrabajadorVO().getTrabajador().getNss());
			parameters.put("numFolioDenuncia", denunciaVO.getFolioDenuncia());
			parameters.put("denunciante", denunciante.getDesDenunciante().trim());
			parameters.put("nombreTrabajador", denunciaVO.getDatosTrabajadorVO().getTrabajador().getNombre().toUpperCase().trim() + " "+ denunciaVO.getDatosTrabajadorVO().getTrabajador().getApellidoPaterno().toUpperCase().trim() + " " + denunciaVO.getDatosTrabajadorVO().getTrabajador().getApellidoMaterno().toUpperCase().trim());
			parameters.put("fecha", new Date().toString());
			parameters.put("numeroDocumentoIDTrabajador", denunciaVO.getDatosTrabajadorVO().getTrabajador().getNumeroDocumento());
			parameters.put("rfcTrabajador", denunciaVO.getDatosTrabajadorVO().getTrabajador().getRfc());
			parameters.put("curpTrabajador", denunciaVO.getDatosTrabajadorVO().getTrabajador().getCurp());
			parameters.put("correoTrabajador", denunciaVO.getDatosTrabajadorVO().getTrabajador().getEmail());
			parameters.put("sexoTrabajador", denunciaVO.getDatosTrabajadorVO().getTrabajador().getSexoTrabajador());
			if(domTrab!=null){
				parameters.put("calleTrabajador", domTrab.getDgVialidadByCveViaPrin()!=null?domTrab.getDgVialidadByCveViaPrin().getNomVia().trim():"");	
				parameters.put("codigoPostalTrabajador", (domTrab.getDgCodigosPostales()!=null && domTrab.getDgCodigosPostales().getId()!=null && domTrab.getDgCodigosPostales().getId().getCodigo()!=null )?domTrab.getDgCodigosPostales().getId().getCodigo().trim(): "");
				parameters.put("noExtTrabajador",  domTrab.getNumextnum()!=null ?domTrab.getNumextnum() +"": "");
				parameters.put("noIntTrabajador", domTrab.getNumintnum()!=null ? domTrab.getNumintnum()+"" : "");				
				parameters.put("municipioTrabajador", domTrab.getDgCatLocalidad().getDgCatMunicipio().getNomMun().trim());
				parameters.put("coloniaTrabajador",  (domTrab.getDgAsentamiento()!=null  && domTrab.getDgAsentamiento().getNomAsen()!=null) ?  domTrab.getDgAsentamiento().getNomAsen().trim(): "");
				parameters.put("estadoTrabajador",domTrab.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().trim());

			}
			parameters.put("telefonoTrabajador", denunciaVO.getDatosTrabajadorVO().getTrabajador().getTelefonoContacto()!=null ? denunciaVO.getDatosTrabajadorVO().getTrabajador().getTelefonoContacto().trim(): "");
			parameters.put("documentoIDTrabajador", documentoTrab.getDesDocumento());//documentoTrab.getDesDocumento().trim());
			parameters.put("numeroDocumentoIDTrabajador",( denunciaVO.getDatosTrabajadorVO().getTrabajador().getNumeroDocumento()!=null ? denunciaVO.getDatosTrabajadorVO().getTrabajador().getNumeroDocumento().trim():""));
			UserSession usrSession = (UserSession)request.getSession().getAttribute(ConstantesSession.USR_SESSION);
			if(denunciaVO.getDatosTrabajadorVO().getBeneficiario()!=null && denunciaVO.getDatosTrabajadorVO().getBeneficiario().getNombre()!=null){
				parameters.put("nombreDenunciante", denunciaVO.getDatosTrabajadorVO().getBeneficiario().getNombre() + " " + denunciaVO.getDatosTrabajadorVO().getBeneficiario().getApellidoPaterno() + " " + denunciaVO.getDatosTrabajadorVO().getBeneficiario().getApellidoMaterno());
			}else if(denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal()!=null && denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getNombre()!=null){
				parameters.put("nombreDenunciante", denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getNombre()+ " "+denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getApellidoPaterno() + " "+ denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getApellidoMaterno());
			}else{
				parameters.put("nombreDenunciante", denunciaVO.getDatosTrabajadorVO().getTrabajador().getNombre() + " " + denunciaVO.getDatosTrabajadorVO().getTrabajador().getApellidoPaterno() + " "+denunciaVO.getDatosTrabajadorVO().getTrabajador().getApellidoMaterno());
			}
			
			parameters.put("nombreFuncionario", usrSession.getNombreCompleto());
			parameters.put("cargoFuncionario", usrSession.getDescripcionRol());
			
			parameters.put("fechaPrestacionTrab", "");
			
			
			ArrayList listaMotivos = (ArrayList) denunciaVO.getDatosTrabajadorVO().getMotivosDenuncia();
			if(listaMotivos!=null){
				for(int i=0; listaMotivos.size()>i; i++){
					MotivoDenVO vo =(MotivoDenVO) listaMotivos.get(i);
					if(vo.getCveMotivoDenuncia()==1){
						parameters.put("motivo1", "X");
						parameters.put("fechaNoAfiliacionDel", vo.getFechaLabDelIngreso());
						parameters.put("fechaNoAfiliacionAl", vo.getFechaLabDejoLab());
					}else if(vo.getCveMotivoDenuncia()==2){
						parameters.put("fechaIngresoTrab", vo.getFechaLabDelIngreso());
						parameters.put("fechaAfiliacion", vo.getFechaLabDejoLab());
						parameters.put("motivo2", "X");
						
					}else if(vo.getCveMotivoDenuncia()==3){
						parameters.put("motivo3", "X");
						parameters.put("salarioReal", "$" + vo.getImpoSalarioReal());
						parameters.put("salarioImss", "$" + vo.getImpoSalarioReg());
					}else if(vo.getCveMotivoDenuncia()==4){
						parameters.put("motivo4", "X");
						parameters.put("fechaDejoLaborar", vo.getFechaLabDejoLab());
					}
				}
			}
			
			//Datos del beneficiario
			if(denunciaVO.getTipoDenunciante()==2){
			parameters.put("nombreBeneficiario", denunciaVO.getDatosTrabajadorVO().getBeneficiario().getNombre().toUpperCase().trim() + " "+ denunciaVO.getDatosTrabajadorVO().getBeneficiario().getApellidoPaterno().toUpperCase().trim() + " " + denunciaVO.getDatosTrabajadorVO().getBeneficiario().getApellidoMaterno().toUpperCase().trim());
				if(domBen!=null){
					parameters.put("calleBeneficiario", (domBen.getDgVialidadByCveViaPrin()!=null && domBen.getDgVialidadByCveViaPrin().getNomVia()!=null)? domBen.getDgVialidadByCveViaPrin().getNomVia().trim(): "");
					parameters.put("noExtBeneficiario",domBen.getNumextnum()!=null? domBen.getNumextnum()+"": "");
					parameters.put("noIntBeneficiario", domBen.getNumintnum()!=null?domBen.getNumintnum()+"":"");
					parameters.put("coloniaBeneficiario",(domBen.getDgAsentamiento()!=null && domBen.getDgAsentamiento().getNomAsen()!=null ) ? domBen.getDgAsentamiento().getNomAsen().trim():"");
					parameters.put("codigoPostalBeneficiario", (domBen.getDgCodigosPostales()!=null && domBen.getDgCodigosPostales().getId()!=null && domBen.getDgCodigosPostales().getId().getCodigo()!=null)?domBen.getDgCodigosPostales().getId().getCodigo().trim():"");
					parameters.put("municipioBeneficiario", domBen.getDgCatLocalidad().getDgCatMunicipio().getNomMun().trim());
					parameters.put("estadoBeneficiario", domBen.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().trim());
				}
		
			parameters.put("telefonoBeneficiario", denunciaVO.getDatosTrabajadorVO().getBeneficiario().getTelefonoContacto()!=null?denunciaVO.getDatosTrabajadorVO().getBeneficiario().getTelefonoContacto():"");
			parameters.put("documentoIDBeneficiario", documentoBen.getDesDocumento().trim());
			parameters.put("numeroDocumentoIDBeneficiario", denunciaVO.getDatosTrabajadorVO().getBeneficiario().getNumeroDocumento()!=null ?denunciaVO.getDatosTrabajadorVO().getBeneficiario().getNumeroDocumento().trim():"");
			}
			//Datos del representante legal
			if(denunciaVO.getTipoDenunciante()==3){
			parameters.put("nombreRepLeg", denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getNombre().toUpperCase().trim() + " "+ denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getApellidoPaterno().toUpperCase().trim() + " " + denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getApellidoMaterno().toUpperCase().trim());
				if(domRep!=null){
					parameters.put("calleRepLeg", (domRep.getDgVialidadByCveViaPrin()!=null &&  domRep.getDgVialidadByCveViaPrin().getNomVia()!=null)?domRep.getDgVialidadByCveViaPrin().getNomVia().trim():"");
					parameters.put("noExtRepLeg", domRep.getNumextnum()!=null?domRep.getNumextnum()+"":"");
					parameters.put("noIntRepLeg", domRep.getNumintnum()!=null?domRep.getNumintnum()+"":"");
					parameters.put("coloniaRepLeg",(domRep.getDgAsentamiento()!=null && domRep.getDgAsentamiento().getNomAsen()!=null)?domRep.getDgAsentamiento().getNomAsen().trim():"");
					parameters.put("codigoPostalRepLeg",(domRep.getDgCodigosPostales()!=null && domRep.getDgCodigosPostales().getId()!=null && domRep.getDgCodigosPostales().getId().getCodigo()!=null)? domRep.getDgCodigosPostales().getId().getCodigo().trim(): "");
					parameters.put("municipioRepLeg", domRep.getDgCatLocalidad().getDgCatMunicipio().getNomMun().trim());
					parameters.put("estadoRepLeg", domRep.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().trim());
				}
			parameters.put("telefonoRepLeg",(denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getTelefonoContacto()!=null) ?denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getTelefonoContacto():"");
			parameters.put("documentoIDRepLeg", documentoRep.getDesDocumento().trim());
			parameters.put("numDocumentoIDRepLeg",denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getNumeroDocumento()!=null ? denunciaVO.getDatosTrabajadorVO().getRepresentanteLegal().getNumeroDocumento().trim():"");
			}
			//Datos del patron
			if(denunciaVO.getDatosPatronVO()!=null){
				parameters.put("telefonoPatron", (denunciaVO.getDatosPatronVO().getTelefonoEmpresa()!=null)?denunciaVO.getDatosPatronVO().getTelefonoEmpresa():"");
				parameters.put("representantePatron", denunciaVO.getDatosPatronVO().getNombreRepresentanteLegal()!=null?denunciaVO.getDatosPatronVO().getNombreRepresentanteLegal():"");
				parameters.put("actividadPatron", actividadEconomica);
				parameters.put("telefonoPatronFis", denunciaVO.getDatosPatronVO().getTelefonoEmpresa()!=null ? denunciaVO.getDatosPatronVO().getTelefonoEmpresa(): "");
				parameters.put("numTrabEst", denunciaVO.getDatosPatronVO().getNumTrabajadores());
				parameters.put("diversosPatrones", "");
				parameters.put("rfcPatron", denunciaVO.getDatosPatronVO().getRfc()!=null ? denunciaVO.getDatosPatronVO().getRfc(): "");
				parameters.put("regPatronal", denunciaVO.getDatosPatronVO().getRegPat()!=null?denunciaVO.getDatosPatronVO().getRegPat():"");
				parameters.put("nombrePatron", denunciaVO.getDatosPatronVO().getRazonSocial());
			}
			
			if(domPatFis!=null){
				parameters.put("callePatronFis", (domPatFis.getDgVialidadByCveViaPrin()!=null && domPatFis.getDgVialidadByCveViaPrin().getNomVia()!=null)?domPatFis.getDgVialidadByCveViaPrin().getNomVia().trim(): "");
				parameters.put("noExtPatronFis", domPatFis.getNumextnum()!=null ? domPatFis.getNumextnum()+"": "");
				parameters.put("noIntPatronFis", domPatFis.getNumintnum()!=null ? domPatFis.getNumintnum()+"" : "");
				parameters.put("coloniaPatronFis",(domPatFis.getDgAsentamiento()!=null && domPatFis.getDgAsentamiento().getNomAsen()!=null)? domPatFis.getDgAsentamiento().getNomAsen().trim(): "");
				parameters.put("codigoPostalPatronFis", (domPatFis.getDgCodigosPostales()!=null && domPatFis.getDgCodigosPostales().getId()!=null && domPatFis.getDgCodigosPostales().getId().getCodigo()!=null)?domPatFis.getDgCodigosPostales().getId().getCodigo().trim(): "");
				parameters.put("municipioPatronFis", domPatFis.getDgCatLocalidad().getDgCatMunicipio().getNomMun().trim());
				parameters.put("estadoPatronFis", domPatFis.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().trim());
			}
			
			
			if(domCenTrab!=null){
				parameters.put("calleCenTrab", domCenTrab.getDgVialidadByCveViaPrin()!=null?domCenTrab.getDgVialidadByCveViaPrin().getNomVia().trim(): "");
				parameters.put("noExtCenTrab", domCenTrab.getNumextnum()!=null ? domCenTrab.getNumextnum()+"": "");
				parameters.put("noIntCenTrab", domCenTrab.getNumintnum()!=null ? domCenTrab.getNumintnum()+"": "");
				parameters.put("coloniaCenTrab",(domCenTrab.getDgAsentamiento()!=null && domCenTrab.getDgAsentamiento().getNomAsen()!=null)? domCenTrab.getDgAsentamiento().getNomAsen().trim():"");
				parameters.put("codigoPostalCenTrab",(domCenTrab.getDgCodigosPostales()!=null && domCenTrab.getDgCodigosPostales().getId()!=null && domCenTrab.getDgCodigosPostales().getId().getCodigo()!=null)?domCenTrab.getDgCodigosPostales().getId().getCodigo().trim(): "");
				parameters.put("municipioCenTrab", domCenTrab.getDgCatLocalidad().getDgCatMunicipio().getNomMun().trim());
				parameters.put("estadoCenTrab", domCenTrab.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt().trim());
			
			}
			if(denunciaVO.getDatosCentroTrabajoVO()!=null){
				
				
				parameters.put("telefonoCenTrab", denunciaVO.getDatosPatronVO().getTelefonoEmpresa()!=null ? denunciaVO.getDatosPatronVO().getTelefonoEmpresa():"");
				parameters.put("labores",  denunciaVO.getDatosCentroTrabajoVO().getActividadTrabajador()!=null ? denunciaVO.getDatosCentroTrabajoVO().getActividadTrabajador(): "");
				parameters.put("nombreJefeInmediato", denunciaVO.getDatosCentroTrabajoVO().getNombreJefeInm()!=null ? denunciaVO.getDatosCentroTrabajoVO().getNombreJefeInm(): "");
				parameters.put("horarioLabores", denunciaVO.getDatosCentroTrabajoVO().getHorarioLabores()!=null ? denunciaVO.getDatosCentroTrabajoVO().getHorarioLabores(): "");
				if(denunciaVO.getDatosCentroTrabajoVO().getNumeroContrato()==null){
					parameters.put("contratoSI", "");
					parameters.put("contratoNO", "X");
				}else{
					parameters.put("contratoSI", "X");
					parameters.put("contratoNO", "");
				}
				if(denunciaVO.getDatosCentroTrabajoVO().getFechaRiesgoTrabajo()==null){
					parameters.put("riesgoSI", "");
					parameters.put("riesgoNO", "X");
				}else{
					parameters.put("riesgoSI", "X");
					parameters.put("riesgoNO", "");
				}
				if(denunciaVO.getDatosCentroTrabajoVO().getPagoComprobantePago()!=null && denunciaVO.getDatosCentroTrabajoVO().getPagoComprobantePago().getCveFormaPago()!=0){
					parameters.put("recibeComprobanteSI", "X");
					parameters.put("recibeComprobanteNO", "");
					parameters.put("tipoComprobante", comprobantesPago);
				}else{
					parameters.put("recibeComprobanteSI", "");
					parameters.put("recibeComprobanteNO", "X");
				}
				parameters.put("sueldo", denunciaVO.getDatosCentroTrabajoVO().getSalario()!=null ? denunciaVO.getDatosCentroTrabajoVO().getSalario(): "");
				parameters.put("vacaciones", denunciaVO.getDatosCentroTrabajoVO().getVacaciones()!=null ? denunciaVO.getDatosCentroTrabajoVO().getVacaciones() : "");
				parameters.put("periodoPagos", periodoPago);
				parameters.put("diasVacaciones", denunciaVO.getDatosCentroTrabajoVO().getDiasVacaciones()!=null ? denunciaVO.getDatosCentroTrabajoVO().getDiasVacaciones(): "");
				parameters.put("aguinaldo", denunciaVO.getDatosCentroTrabajoVO().getAguinaldoAnual()!=null ? denunciaVO.getDatosCentroTrabajoVO().getAguinaldoAnual(): "");
				parameters.put("comisiones", denunciaVO.getDatosCentroTrabajoVO().getComisiones()!=null ? denunciaVO.getDatosCentroTrabajoVO().getComisiones(): "");
				parameters.put("baseComisiones", denunciaVO.getDatosCentroTrabajoVO().getBaseComision() !=null ? denunciaVO.getDatosCentroTrabajoVO().getBaseComision(): "");
				parameters.put("formaPago", formaPago!=null ? formaPago : "");
				
				
				
				String observaciones = "";
				if(denunciaVO.getDatosTrabajadorVO().getObservaciones()!=null){
					observaciones += "Datos Trabajador: " + denunciaVO.getDatosTrabajadorVO().getObservaciones() + "\n";
				}
				if(denunciaVO.getDatosPatronVO().getObservaciones()!=null ){
					observaciones += "Datos Patrón: " +denunciaVO.getDatosPatronVO().getObservaciones() + "\n";
				}
				if(denunciaVO.getDatosCentroTrabajoVO().getObservaciones()!=null ){
					observaciones += "Datos Generales del Trabajo: " + denunciaVO.getDatosCentroTrabajoVO().getObservaciones();
				}
				
				
				parameters.put("observaciones", observaciones);
				parameters.put("documentacionAdjunta", documentacionAdjunta);
			
			}
		
			
			
			
			ArrayList list = new ArrayList();
			
			Connection conn = getConexion();
			byte[] reporte = null;
			String acusePdf = null;
			String receivedPDF = (String)request.getSession().getAttribute("avisoPDF");
						
			String rutaArchivoJasper = rutaOriginal+ "denunciaLinea.jasper";
			reporte = GeneraReporteUtil.getInstance().generaReporte(rutaArchivoJasper, parameters);
			acusePdf = org.apache.soap.encoding.soapenc.Base64.encode(reporte);
			
			response.setContentType("application/pdf");
			out = response.getOutputStream();
	
			String archivo = acusePdf;
			
			response.setHeader("Content-disposition", "inline; filename=" + "nombreArchivo");
			byte[] bytesArch = Base64.decode(archivo);

			out.write(agregarWaterMark(bytesArch, out));
			out.close();

		} catch (Exception e) {
			System.out
					.println("EnviaArchivoServlet.processRequest: Excepcion al cerrar ServletOutputStream: "
							+ e.getMessage());
			e.printStackTrace();
		} finally {
			// Se cierra el ServletOutputStream
			try {
				if (out != null)
					out.close();
			} catch (IOException ioe) {
				System.out
						.println("EnviaArchivoServlet.processRequest: Excepcion al cerrar ServletOutputStream: "
								+ ioe.getMessage());
				ioe.printStackTrace();
			}

		}

	}

	private byte[] agregarWaterMark(byte[] bytesArchivoJasper,
			OutputStream out) throws DocumentException,
			Exception {
		byte[] bytes2 = null;

		PdfReader pdfReader = new PdfReader(bytesArchivoJasper);

		PdfStamper stamper = new PdfStamper(pdfReader, out);
		BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI,
				BaseFont.EMBEDDED);
		PdfContentByte over;

		int total = pdfReader.getNumberOfPages() + 1;
		for (int i = 1; i < total; i++) {
			if (i == 1) {
				over = stamper.getOverContent(i);
				over.saveState();
				over.beginText();
				over.setColorFill(Color.CYAN);

				over.setFontAndSize(bf, 2);
				over.endText();
				over.restoreState();
			}
		}
		stamper.close();

		PRStream stream = new PRStream(pdfReader, bytesArchivoJasper);

		bytes2 = stream.getBytes();

		return bytes2;
	}

	/**
	 * Maneja el metodo HTTP <code>GET</code> .
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             - Una excepcion general que un servlet lanza cuando encuentra
	 *             dificultades.
	 * @throws IOException
	 *             - Producida por operaciones de entrada y salida fallidas o
	 *             interrumpidas.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Maneja el metodo HTTP <code>POST</code> .
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             - Una excepcion general que un servlet lanza cuando encuentra
	 *             dificultades.
	 * @throws IOException
	 *             - Producida por operaciones de entrada y salida fallidas o
	 *             interrumpidas.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Regresa una descripcion corta del servlet.
	 * 
	 * @return una descripcion corta del servlet.
	 */
	public String getServletInfo() {
		return "Envia un archivo al browser";
	}

    public javax.sql.DataSource getDataSource() throws NamingException,SQLException{
        Context ctx = new InitialContext();
        //Tomar la conexión del data source definido en weblogic
        javax.sql.DataSource ds = (javax.sql.DataSource)ctx.lookup("ds_ora_denuncia_view");
        return ds;
    }	
	

    /**
     * Obtiene la conexión a base de datos de un datasource de weblogic
     */
    public java.sql.Connection getConexion() throws NamingException, SQLException {
        javax.sql.DataSource ds = getDataSource();
        Connection con = ds.getConnection(); 
     return con;
   } 


}
