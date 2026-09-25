/**
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzalez
 *  @Proyecto: delta
 *  @Archivo: DocumentosAnalisisServiceBusiness.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.analisis
 *  @Fecha: 29/10/2012
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.clem;

import static mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes.CLAVE_DEL_VERACRUZ_SUR;
import static mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes.CLAVE_SUBDEL_COATZACOALCOS;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.springframework.core.io.ClassPathResource;

import mx.gob.imss.ctirss.delta.exception.clasificacion.AnalisisNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.PatronNoEncontradoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClemVO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FirmaClemDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ResolucionVO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.analisis.TipoCausaAnalisisServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.articulo.ArticuloServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.bitacora.BitacoraServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem.DatosClemServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.clem.FirmaClemServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio.DomicilioMigrServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionPropuestaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.Articulo155ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.DatosClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.FirmaClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.utility.clem.DatosClemServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.util.Constantes;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.Articulo155;
import mx.gob.imss.ctirss.delta.model.clasificacion.ArticuloModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoDatosClemEnum;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Stateless(name = "firmaClemServiceBusiness", mappedName = "firmaClemServiceBusiness")
public class FirmaClemServiceBusiness extends AbstractServiceBusiness implements
		FirmaClemServiceBusinessRemote {

	@EJB
	private FirmaClemServiceEntityLocal firmaClemEntity;

	@EJB
	private DatosClemServiceBusinessRemote datosClemServiceBusiness;

	@EJB
	private AnalisisServiceBusinessRemote analisisBusiness;

	@EJB
	private TipoCausaAnalisisServiceEntityLocal tipoCausaServiceEntity;

	@EJB
	private ArticuloServiceEntityLocal articuloService;

	@EJB
	private BitacoraServiceEntityLocal bitacoraServiceEntity;

	@EJB
	private ClasificacionPropuestaServiceBusinessRemote clasificacionPropuestaService;

	@EJB
	private Articulo155ServiceBusinessRemote articulo155ServiceBusiness;

	@EJB
	private DatosClemServiceEntityLocal datosClemEntity;

	@EJB
	private SolicitudServiceBusinessRemote solicitudServiceBusiness;

	@EJB
	private DomicilioMigrServiceEntityLocal domicilioMigrServiceEntity;

	@EJB
	private DatosClemServiceUtilityLocal datosClemUtility;

	@Override
	public DatosSalidaPaginador<FirmaClemDTO> buscaClemSinFirmaPaginado(
			DatosEntradaPaginador<FirmaClemDTO> datosEntrada) {
		return firmaClemEntity.consultarClemPaginado(datosEntrada);
	}

	@Override
	public DatosSalidaPaginador<FirmaClemDTO> buscaClemConFirmaPaginado(
			DatosEntradaPaginador<FirmaClemDTO> datosEntrada) {
		return firmaClemEntity.consultarClemCFPaginado(datosEntrada);
	}

	@Override
	@SuppressWarnings("unused")
	public List<ResolucionVO> buscarDatosFirmaMasivaCMS(List<FirmaClemDTO> patFirma) {

		List<Long> idAnalisis = new ArrayList<Long>();
		List<ResolucionVO> parametrosJSON = new ArrayList<ResolucionVO>();
		
		for(FirmaClemDTO f : patFirma){
			idAnalisis.add(f.getCveIdAnalisis());
		}
		
		List<ResolucionVO> firma = firmaClemEntity
				.obtenerDatosClemFirmaMasiva(idAnalisis);

		log.debug("::::: Encontre " + firma.size() + " Clem para firmar");
		for(ResolucionVO datosFirma : firma){
			
			ClemVO vo = new ClemVO();
			vo = datosFirma.getClemVO();
			
			boolean mostrarComboArt155 = false;
			List<Articulo155> articulo155s = new ArrayList<Articulo155>();
			String incisoArticulo155 = null;
			String cveArticulo155 = null;
			List<ArticuloModel> lstArticuloModel = null;
			String tipoTramiteMAC = "1";// MODIFICACION
					
			lstArticuloModel = articuloService.buscarPorIdClem(Long.parseLong(vo.getIdClem()));
			vacio: for (ArticuloModel art : lstArticuloModel) {
				if (art.getNumArticulo() == null
						|| art.getNumArticulo().toString().trim()
								.equals("")) {
					continue vacio;
				}

				if (art.getNumArticulo().longValue() == 28) {
					vo.setFraccionArticulo28("fracción " + art.getDesFraccion() + ", ");
				} else if (art.getNumArticulo().longValue() == 20) {
					vo.setFraccionArticulo20(art.getDesFraccion().trim().equals("1")?"20, ":"");
				} else if (art.getNumArticulo().longValue() == 26) {
					vo.setFraccionArticulo26("fracción " + art.getDesFraccion() + ", ");
				} else if (art.getNumArticulo().longValue() == 155) {
					if (mostrarComboArt155) {
						vo.setIncisio115(art.getDesInciso());
					}
				}
			}
			
			if (vo.getIdSubdelegacion() != null
					&& vo.getIdSubdelegacion().equals(CLAVE_SUBDEL_COATZACOALCOS)  
					&& vo.getIdDelegacion().equals(CLAVE_DEL_VERACRUZ_SUR)) {
				mostrarComboArt155 = true;
			} else {
				mostrarComboArt155 = false;
			}

			articulo155s = articulo155ServiceBusiness.getArticulo155(Long
					.parseLong(vo.getIdSubdelegacion()));
			
			coatzacoalcos: for (Articulo155 art155 : articulo155s) {
				vo.setFraccion115(art155.getDesFraccion());
				vo.setIncisio115(art155.getDesInciso());
				incisoArticulo155 = art155.getDesFraccion();
				cveArticulo155 = art155.getCveIdArticulo155().toString();

				if (mostrarComboArt155) {
					if (incisoArticulo155.equals(art155.getDesInciso().trim())) {
						vo.setIncisio115(vo.getIncisio115() + ")");																	
						break coatzacoalcos;
					}
				}
			}
			
			vo.setFechaSurteEfecto((String) modificaFormatoFecha(vo.getFechaSurteEfecto()));
			vo.setFechaTramite((String) modificaFormatoFecha(vo.getFechaTramite()));
			
			log.debug(":::::::::::::::: Datos de la clem a firmar: ");
			log.debug(vo.toString());
			log.debug("::::::::::::::::::::::::::::::::::");			
			
			ResolucionVO json = new ResolucionVO();
			json.setIdAnalisis(datosFirma.getIdAnalisis());
			json.setClemVO(vo);
			
			parametrosJSON.add(json);
			
		}

		return parametrosJSON;
	}


	private Object modificaFormatoFecha(String fechaTramite) {

		if (fechaTramite == null || fechaTramite.length() < 10) {
			DateFormat formatt = new SimpleDateFormat("dd/MM/yyyy");
			fechaTramite = formatt.format(new Date());
		}
		String dia = fechaTramite.substring(0, 2);
		int mesInt = Integer.parseInt(fechaTramite.substring(3, 5));
		String anio = fechaTramite.substring(6, 10);
		String mes = null;

		switch (mesInt) {
		case 1:
			mes = "enero";
			break;
		case 2:
			mes = "febrero";
			break;
		case 3:
			mes = "marzo";
			break;
		case 4:
			mes = "abril";
			break;
		case 5:
			mes = "mayo";
			break;
		case 6:
			mes = "junio";
			break;
		case 7:
			mes = "julio";
			break;
		case 8:
			mes = "agosto";
			break;
		case 9:
			mes = "septiembre";
			break;
		case 10:
			mes = "octubre";
			break;
		case 11:
			mes = "noviembre";
			break;
		case 12:
			mes = "diciembre";
			break;

		}
		return dia + " de " + mes + " de " + anio;
	}
}
