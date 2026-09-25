/**
 * Clase singleton para el contexto de JAXB.
 */
package mx.gob.imss.distss.digital.jaxb.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import mx.gob.imss.ctirss.delta.beneficio.model.MovimientoRissType;
import mx.gob.imss.ctirss.delta.beneficio.model.TrabajadoresImssInfonavitType;
import mx.gob.imss.ctirss.delta.framework.base.model.EMailProducerMessageType;
import mx.gob.imss.ctirss.delta.framework.base.model.EmailPayloadType;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AltaDatosAsignacionNSSType;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CertificacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.Institucion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoNSSCorreccion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoRegularizacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoRegularizacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.ws.GetFotografiaAsegurado;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.ws.GetFotografiaAseguradoResponse;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.ws.GetFotografiaDerechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.ws.GetFotografiaDerechohabienteResponse;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.DomicilioEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.escritoDesacuerdo.TramiteEscritoDesacuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.Compra;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.Cotizacion;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.Comprobante;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.TimbreFiscalDigital;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdiV4.ComprobanteV4;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.pago.PagoType;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acta;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Acuerdo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Adimss;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CartillaMilitar;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CedulaProfesional;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CertificadoNacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CertificadoSituacionCritica;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ComprobanteDomicilio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.ConstanciaEstudio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DetalleNivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DictamenIntegranteIncapacitado;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Divorcio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Ife;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Matrimonio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.NivelEducativo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Obstetrico;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Pasaporte;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Reconocimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.VigenciaTemporal;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.asignacion.MovimientoAsignacionType;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.seguro.ProcesaSeguroProducerType;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.*;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.global.SolicitudProducerType;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.reingreso.MovimientoReingresoType;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.MovimientoAsignacionSIMEType;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovimientoPatronalType;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sindo.MovtoPatSujetoObligadoType;
import mx.gob.imss.ctirss.delta.model.sua.integracion.SUAPagoType;
import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;
import mx.gob.imss.digital.modelo.cobranza.CalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.DatosCalculoCuota;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.cobranza.Pagos;
import mx.gob.imss.digital.modelo.cobranza.SUAPago;
import mx.gob.imss.digital.modelo.cuestionario.PersonaCuestionario;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import mx.gob.imss.digital.modelo.medio.contacto.TipoMedioContacto;
import mx.gob.imss.digital.modelo.patron.RegistroPatronal;
import mx.gob.imss.digital.modelo.persona.Familiar;
import mx.gob.imss.digital.modelo.persona.Parentesco;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.satRiss.DatosRiss;
import mx.gob.imss.digital.modelo.satRiss.ValidaDerechoRifSatRequest;
import mx.gob.imss.digital.modelo.satRiss.ValidaDerechoRifSatResponse;
import mx.gob.imss.digital.modelo.seguros.DocumentoSeguro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.solicitud.Solicitud;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod33;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod40;
import mx.gob.imss.digital.modelo.util.ObtenerCurpPersonaResp;
import mx.gob.imss.digital.modelo.util.UmaResponse;
import mx.gob.imss.digital.modelo.util.obtenerSalarioMinimoVigenteDFResp;

import org.apache.log4j.Logger;

/**
 * @author Lucio Duran Silva
 * 
 * 
 * 
 */
public class JaxbContextHelper {

	private static Logger log = Logger.getLogger(JaxbContextHelper.class);

	// Contexto Jaxb
	private static volatile JAXBContext context;

	private static final Class[] classesToBeBound = new Class[] {
			Tramite.class,
			mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.Tramite.class,
			TramiteFisica.class, TramiteMoral.class,
			TramiteSujetoObligado.class, TramiteAsegurado.class, Fisica.class,
			Moral.class, SujetoObligado.class, Serie.class, Nacimiento.class,
			TramitePersonaAutorizada.class, TramiteBajaPersonaAutorizada.class,
			AsignacionNSS.class, MovimientoPatronalType.class,
			MovtoPatSujetoObligadoType.class, TramiteRepresentanteLegal.class,
			TramiteBajaDerechohabiente.class,
			TramiteCorreccionDerechohabiente.class,
			TramiteCircunscripcionForanea.class, TramiteProrroga.class,
			TramiteRegistroDerechohabiente.class,
			TramiteAsignacionMasiva.class, TramiteRiss.class,
			TramiteConsultaVigencia.class, TramiteSocios.class, Acta.class,
			Acuerdo.class, Adimss.class, CartillaMilitar.class,
			CedulaProfesional.class, CertificadoNacimiento.class,
			CertificadoSituacionCritica.class, ComprobanteDomicilio.class,
			ConstanciaEstudio.class, CURP.class, DetalleNivelEducativo.class,
			DictamenIntegranteIncapacitado.class, Divorcio.class, Ife.class,
			Matrimonio.class, NivelEducativo.class, Obstetrico.class,
			Pasaporte.class, Reconocimiento.class, VigenciaTemporal.class,
			Usuario.class, GrupoFamiliar.class, TramiteSeguroIvro.class,
			Compra.class, Cotizacion.class, Modalidad.class,
			PersonaCuestionario.class, Tramite32D.class,
			TramiteCambioInformacionPersona.class, Comprobante.class,
			TimbreFiscalDigital.class, PagoType.class,
			SolicitudProducerType.class, TrabajadoresImssInfonavitType.class,
			SUAPagoType.class, MovimientoRissType.class,
			MovimientoReingresoType.class, MovimientoPatronalType.class,
			MovimientoAsignacionType.class, MovimientoAsignacionSIMEType.class,
			MovCorreccionesDatosAseguradoType.class,
			EMailProducerMessageType.class, EmailPayloadType.class,
			AltaDatosAsignacionNSSType.class,
			TramiteActualizacionAsegurado.class, TramiteSeguroIvroMod33.class,
			TramiteSeguroIvroMod40.class, Familiar.class, Parentesco.class,
			SUAPago.class, CalculoCuota.class, Pagos.class, Pago.class,
			GetFotografiaAsegurado.class, GetFotografiaAseguradoResponse.class,
			GetFotografiaDerechohabiente.class,
			GetFotografiaDerechohabienteResponse.class,
			ValidaDerechoRifSatResponse.class, SegurosIvro.class,
			SeguroIvro.class, Persona.class, Solicitud.class,
			mx.gob.imss.digital.modelo.cobranza.Cotizacion.class,
			DatosCalculoCuota.class, Domicilio.class, RegistroPatronal.class,
			TipoMedioContacto.class, DocumentoSeguro.class, DatosRiss.class,
			ActualizacionCompra.class,
			mx.gob.imss.digital.modelo.tramite.Tramite.class,
			ValidaDerechoRifSatRequest.class,
			ValidaDerechoRifSatResponse.class,
			mx.gob.imss.digital.modelo.persona.Fisica.class,
			ObtenerCurpPersonaResp.class,
			obtenerSalarioMinimoVigenteDFResp.class, DatosLaborales.class,
			TramiteReactivacionDerechohab.class,
			CertificacionNSS.class,
			TipoRegularizacionNSS.class,
			TipoNSSCorreccion.class,
			TipoRegularizacion.class,
			MotivoAclaracion.class,
			Institucion.class,
			TramiteCorreccionCurp.class,
			UmaResponse.class, TramiteAcuerdoDh.class,
            ProcesaSeguroProducerType.class,
            TramiteEscritoDesacuerdo.class,
            TramiteDictamen.class,
			TramiteAclaracionSemanasCotizadas.class,
			DomicilioEscritoDesacuerdo.class,
			ComprobanteV4.class};

	/**
	 * Se implementa el patrón Double Checked Locking of Singleton
	 * 
	 * @return
	 */
	public static JAXBContext getInstance() {
		log.debug("Obteniendo el contexto de JAXB..");

		if (context == null) {
			synchronized (JAXBContext.class) {
				if (context == null) {
					try {
						log.debug("Se va a crear contexto nuevo de JAXB");
						context = JAXBContext.newInstance(classesToBeBound);
						log.debug("Se creo nuevo contexto nuevo de JAXB");
					} catch (JAXBException e) {
						log.error("Error al generar el contexto de Jaxb {"
								+ e.getMessage() + "}");
						e.printStackTrace();
					}
				}
			}
		} else {
			log.debug("Se recupera contexto de JAXB ya creado");
		}

		return context;
	}

}
