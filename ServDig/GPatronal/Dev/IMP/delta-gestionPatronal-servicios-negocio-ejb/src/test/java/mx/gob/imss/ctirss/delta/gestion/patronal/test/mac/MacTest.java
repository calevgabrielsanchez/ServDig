package mx.gob.imss.ctirss.delta.gestion.patronal.test.mac;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.PersistenceException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClemVO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FirmaClemDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ResolucionVO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.DictamenServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.DatosClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.FirmaClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.ConfiguracionCe;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;


public class MacTest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(MacTest.class);
	}
	

//	@Test
	public void getClasificacionDictamen(){
		System.out.println("::: Inicio");
		try {
			DictamenServiceBusinessRemote ejb = EJBLocator.getDictamenServiceBusiness();
			Clasificacion cl = ejb.getClasificacionDictamen(new Long(2010), "C4811454105");
			System.out.println("::: Respuesta");
			if(cl != null) {
				System.out.println(cl.toString());
			}else {
				System.out.println(":: NO ENCONTRE LA CLASIFICACION");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}			
		System.out.println("::: Fin");		
	}
	
//	@Test
	public void ratificarDictamen(){
		System.out.println("::: Inicio");
		try {
			AnalisisServiceBusinessRemote ejb = EJBLocator.getAnalisisServiceBusiness();
			Usuario usuario = new Usuario();
			usuario.setCveIdUsuario("FEME700212MPLRRL03");
			ClasificacionDTO dto = new ClasificacionDTO();
			dto.setUsuario(usuario);
			dto.setCveIdAnalisis("481112");
			dto.setCveIdDelegacion("15");
			dto.setCveIdSubdelegacion("55");
			dto.setCveIdFraccionAct("303");
			dto.setCveIdFraccionPro("");
			dto.setCveIdFraccionAnt("303");
			dto.setPrimaSRTAct("2.5984");
//			dto.setPrimaSRTPro("");
//			dto.setPrimaSRTAnt("");
			dto.setComentarios("RATIFICADO");
			dto.setCveIdSolicitud("334028832");
			AnalisisClasificacionEmpresas modelo = ejb.ratificarPendienteDictamen(dto);
			System.out.println("::: Respuesta: ");
			System.out.println(modelo.toString());;
		} catch (Exception e) {
			e.printStackTrace();
		}			
		System.out.println("::: Fin");
	}		
	
	
	//@Test
	public void buscarDatosFirmaMasivaCMS(){
		
		FirmaClemServiceBusinessRemote ejb = EJBLocator.getFirmaBusiness();
		
		try {
			
			List<FirmaClemDTO> firmar = new ArrayList<FirmaClemDTO>();
			FirmaClemDTO f = new FirmaClemDTO();
			f.setCveIdAnalisis(new Long("19162"));
			firmar.add(f);
								
			List<ResolucionVO> res = ejb.buscarDatosFirmaMasivaCMS(firmar);
			System.out.println("Obtuve " + res.size() + " resultados");
			
			for (Iterator<ResolucionVO> iterator = res.iterator(); iterator.hasNext();) {
				ResolucionVO resolucionVO = iterator.next();
				ClemVO clem = resolucionVO.getClemVO();
				System.out.println("Motivos: " + clem.getMotivos());				
			}
			
			
			System.out.println("Termine");
			
		} catch (Exception e) {
			e.printStackTrace();
		}			

		
	}

//	@Test
//	public void testActDatosFirmaClem(){
//		System.out.println("Antes de actualizar");
//		DatosClemServiceBusinessRemote ejb = EJBLocator.getDatosClemServiceBusiness();
//		try {
//			System.out.println("Actualizando Clem");
//			ejb.actualizaDatosFirmaClem(new Long("18124"), "SAMJ810311JX6", "43b926db-593b-4b77-af4a-584fb76ef9b0", 
//					"http://firmadigitalqa.imss.gob.mx/firmaElectronicaWeb/chfecynAcuseApp/view?id=43b926db-593b-4b77-af4a-584fb76ef9b0", 
//					"K9+lj/tDhT1HW5LiSCSWGvAiN5TxzQHODdgycxij+q31k/MTwYx5hcD4UuyvaOHE+LE7MpHVW0puwrJX0PFMCByRVwphS7WMTfG6rAh/uePteyARyT2Y0mlnGvPeLATNPqoegTeCmofLwDnFXgAQfyl2XiOz0AeTXCJC78g/BbLhtoLy8PmlODX5WgiC34yq3kiz+SsjSn4dRMYTy3m9EK0ReUoSf12nadQJo5feA5YIme94WyR7AaFRRUXHjEXTglQhlsbRvX6WHdkD8pss4ZiK2KxPLKKXzzyO5T1gzKrRIGR3WO/CsL2/K3MBGuSgsXh0T8OMqmkmvmX8UYndlg==", 
//					"|Identificador_1234|NoFolioCE-17-27-17/02/2020/1111-S|Patron_MARTHA LOPEZ PEREZ|Registro patronal_C8726099103|Prima patron_1.13065|Prima Propuesta_ 4.65325|Delegacion_Regional Michoac�n|Subdelegacion_ L�zaro C�rdenas|Titular_CARLOS ALBERTO RAM�REZ RAM�REZ|");
//			System.out.println("Termine");
//			
//		} catch (DatosClemException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}			
//	}	

	//@Test
	public void testconfiguracionCe(){

		
		mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote ejb = EJBLocator
				.getServiceBusinessMAC();
		
		try {
			
			ConfiguracionCe configuracionCe = ejb.obtenerConfiguracionCe(
					18124L, null, CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().intValue());
			System.out.println(configuracionCe.toString());
			System.out.println("Termine");
			
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
		
	}	

	
	//@Test
	public void testModificarautorizacion(){

		System.out.println("Antes de modificar");
		
		AnalisisServiceBusinessRemote ejb = EJBLocator.getAnalisisServiceBusiness();
		
		try {
			
			AnalisisClasificacionEmpresas analisisClasificacionEmpresas = ejb.rechazarAutorizacion(this.generaClasificacionDTO(), null, false);
			System.out.println("analisisClasificacionEmpresas.getCveIdEstatus(): " + analisisClasificacionEmpresas.getCveIdEstatus());
			System.out.println("Termine");
			
		} catch (Exception e) {
			e.printStackTrace();
		}			
		
		
	}	
	

	//@Test
	public void testModificaClem(){

		System.out.println("Antes de actualizar");
		
		DatosClemServiceBusinessRemote ejb = EJBLocator.getDatosClemServiceBusiness();
		
		DatosClem dc;
		try {
			System.out.println("Generando Clem");
			//dc = ejb.insertaActualizacionClem(getDatosClem(), getReporteClemBean());
			
			Usuario usuario = new Usuario();
			usuario.setCveIdUsuario("12345");
			usuario.setUsuario("usuario");			
			ejb.generacionClem(getReporteClemBean(), usuario, "reportes/asimss-clem.jpg", null);
			
//			ByteArrayOutputStream b = ejb.generaReporteClem(getReporteClemBean(),"reportes/Clem04Delegacional.jasper", "reportes/asimss-clem.jpg");
			
			System.out.println("Termine");
			
			//System.out.println(dc.toString());
		} catch (DatosClemException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}			

	}
	
	
	@Test
	public void obtieneDetalleRP() {
		
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal("E1116946106"); //E1116946106 B6159799109
		sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);			

		try {
			log.debug(":::Obteniendo EJB");
			
			mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote sujetoObligadoB = EJBLocator.getServiceBusinessMAC();
			log.debug(":::Obtuve EJB");
			
			sujetoObligado = sujetoObligadoB.obtenerDetalleSolicitudDictamen(sujetoObligado);
			
//			sujetoObligado = sujetoObligadoB.obtenerDetalleSolicitud(sujetoObligado);
			
//			sujetoObligado = EJBLocator.getSujetoServiceBusiness().obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
			log.debug("sujetoObligado:::");
			log.debug(sujetoObligado.toString());
			log.debug("getDescSituacionBaja: " + sujetoObligado.getDescSituacionBaja());
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			e.fillInStackTrace();
			e.getCause();
			log.error("ERROR- " + e.getMessage());
		}
		log.debug("FIN PARA CONSULTAR LA SOLICITUD X CLAVE");
		
	}
	
	//@Test
	public void completaSolicitudDictamen(){

		String rp = "G6279734106";
		String folio = "1608241474805511218864";
		
		Solicitud solicitud = new Solicitud();		
		solicitud.setNoFolioSolicitud(folio);
		try {
			solicitud = EJBLocator.getSolicitudBusinessRemote().consultarFolio(solicitud);

			System.out.println("IdSolicitud: " + solicitud.getSolicitudId() + "-" + " : "
					+ solicitud.getTipoSolicitud().getDescripcion() + ", "
					+ solicitud.getEstadoSolicitud().getDescripcion() + ", " + solicitud.getFechaActualizacion());
			
			if(solicitud.getSujetoObligado() == null){
				System.out.println("EL SO es null");
				SujetoObligado so = obtieneSujetoObligado(solicitud.getTramites());
				if(so == null){
					System.out.println("Sigue null");
				}else{
					System.out.println("Ya no es null");
					solicitud.setSujetoObligado(so);

					if(solicitud.getSujetoObligado().getClasificacion() == null){
						System.out.println("La clas es null");
					}else{
						System.out.println("La clas NO es null");
					}
				
				}
				
			}else{
				
				if(solicitud.getSujetoObligado().getClasificacion() == null){
					System.out.println("La clas es null");
				}else{
					System.out.println("La clas NO es null");
				}
			}

			
		
			mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote ejb = EJBLocator.getServiceBusinessMAC();
		
			ejb.cancelarAnalisisPorRegistroPatronal(rp, EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave(), solicitud);
		
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		} catch (PersistenceException e) {
			e.printStackTrace();
		} 
		catch (ClasificacionException e) {
			e.printStackTrace();
		}
		
		System.out.println(":::: Termine");

		
	}	
	
//	@Test
	public void cancelarAnalisis(){
		
		//String folio = "165307809924774967004";
		Long idSol = new Long("74975404");//176017929 prod  -  74879492 stage
		
		Solicitud solicitud = new Solicitud();		
		//solicitud.setNoFolioSolicitud(folio);
		try {
			System.out.println(":::: Iniciando , obteniendo EJB");
			//solicitud = EJBLocator.getSolicitudBusinessRemote().consultarFolio(solicitud);
			solicitud = EJBLocator.getServiceBusiness().consultarSolicitudPorId(idSol);			
			System.out.println("IdSolicitud: " + solicitud.getSolicitudId() + "-" + " : "
					+ solicitud.getTipoSolicitud().getDescripcion() + ", "
					+ solicitud.getEstadoSolicitud().getDescripcion() + ", " + solicitud.getFechaActualizacion());
			SujetoObligado so = obtieneSujetoObligado(solicitud.getTramites());
			if(solicitud.getSujetoObligado() == null){
				System.out.println("::Agregando sujeto obligado");
				solicitud.setSujetoObligado(so);				
			}
			System.out.println("Registro patronal: " + so.getNumeroRegistroPatronal());
			mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote ejb = EJBLocator.getServiceBusinessMAC();
			ejb.cancelarAnalisisPorRegistroPatronal(so.getNumeroRegistroPatronal(), EstatusAnalisisEnum.CANCELADO_POR_NUEVO_TRAMITE_DE_GCE.getClave(), solicitud);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		System.out.println(":::: Termine");
	}	
	
	private SujetoObligado obtieneSujetoObligado(List<Tramite> tramites){
		Tramite tramite = null;
		TramiteSujetoObligado tso = null;
		for (Iterator<Tramite> iterator = tramites.iterator(); iterator.hasNext();) {
			tramite = iterator.next();
			System.out.println("IdTramite: " + tramite.getTramiteId() + " - " + tramite.getTipoTramite().getDescripcion() + ", Estado: " + tramite.getEstadoTramite().getDescripcion());
			System.out.println("Tramite: " + tramite.getTramiteId() + " - " + tramite.getTipoTramite().getDescripcion() + ", Estado: " + tramite.getEstadoTramite().getDescripcion());
			if(tramite instanceof TramiteSujetoObligado){
				tso = (TramiteSujetoObligado)tramite;
				System.out.println("IdTramite: " + tramite.getTramiteId() + " - " + tramite.getTipoTramite().getDescripcion() + ", Estado: " + tramite.getEstadoTramite().getDescripcion());
			}else{
				System.out.println("NO es instancia de TramiteSujetoObligado");
			}
		}		
		if(tso.getSujetoObligado() == null)
			this.log.debug("********** sujetoObligado es NULLLLLLLLLLLLLLL");
		
		return tso.getSujetoObligado();
	}
	
	public ClasificacionDTO generaClasificacionDTO() {

		final Usuario usuario = new Usuario();
		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion("Jefe de Oficina Del");
		pu.setIdPerfilUsuario(3L);
		usuario.setCveIdUsuario("12345");
		usuario.setUsuario("usuario");	
		usuario.setPerfilUsuario(pu);

		UsuarioFuncionario uf = new UsuarioFuncionario();
		uf.setDelegacion(new Delegacion());
		uf.getDelegacion().setId(7L);
		usuario.setCveIdSubdelegacion(22L);
		uf.setSubdelegacion(new Subdelegacion());
		uf.getSubdelegacion().setId(22L);
		uf.setUsuario(usuario);
		usuario.setUsuarioFuncionario(uf);


    	ClasificacionDTO dto = new ClasificacionDTO();
		dto.setUsuario(usuario);
	 	dto.setCveIdAnalisis("18124");
	 	dto.setCveIdDelegacion("7");
	 	dto.setCveIdSubdelegacion("22");
	 	dto.setCveIdFraccionAct("182");
	 	dto.setCveIdFraccionPro("182");
	 	dto.setCveIdFraccionAnt("76");
	 	dto.setPrimaSRTAct("1.13065");
	 	dto.setPrimaSRTPro("1.13065");
	 	dto.setPrimaSRTAnt("2.5984");
	 	dto.setComentarios("Preba modificacion autorizacion");
	 	dto.setIdEstatus("6");
	 	dto.setTipoPersona("1");
	 	dto.setRegPatronal("A6854365108");
	 	dto.setCveIdSolicitud("74834928");
	 	dto.setTTramite("0");
		
		
		return dto;
	}

	
	public ReporteClemBean getReporteClemBean(){
		ReporteClemBean clemBean=new ReporteClemBean();
		//clemBean.setRuta("");
		//clemBean.setCveIdClem("");
		clemBean.setIdAnalisis("18663");
		//clemBean.setFolio("");
		clemBean.setDelegacion("MUNICIPIO ORIENTE (EDO. M�X.)");
		clemBean.setSubdelegacion("CHIMALHUAC�N");
		clemBean.setRazonSocial("VENTA DE PRODUCTOS DE IMPORTACI�N");
		clemBean.setDomicilio("C. TONAMETL, COLONIA HERREROS");
		clemBean.setMunicipioDelegacion("CHIMALHUAC�N");
		clemBean.setRegPatronal("A403927010");
		clemBean.setFechaAviso("17/09/2012");
		clemBean.setIdDivisionPatron("53");
		clemBean.setIdGrupoPatron("21");
		clemBean.setIdFraccionPatron("210");
		clemBean.setDenominacionFraccion("VENTA DE PRODUCTOS DE IMPORTACI�N");
		clemBean.setClase("II");
		clemBean.setPrima("1.1306");
		//clemBean.setFechaTramite("");
		clemBean.setMotivos("CAMBIO DE GIRO DEL NEGOCIO");
		clemBean.setIdDivisionPropuesta("53");
		clemBean.setIdFraccionPropuesta("210");
		clemBean.setIdGrupoPropuesta("21");
		clemBean.setDivisionPropuesta("PIRATER�A");
		clemBean.setGrupoPropuesta("ALQUILER DE AUTOMOVILES");
		clemBean.setClasePropuesta("II");
		clemBean.setPrimaPropuesta("1.1306");
		clemBean.setFraccionPropuesta("ALQUILER DE CAMIONES DE CARGA SIN CHOFER");
		clemBean.setFraccionArticulo26("II");
		clemBean.setFraccion115("XXXIV");
		clemBean.setIncisio115("c)");
		clemBean.setTitular("H�CTOR LARA SR.");
		clemBean.setSuplente("H�CTOR LARA JR.");
		clemBean.setPuesto("Puesto puesto");
		clemBean.setLugarFechaExpedicion("LUNES 17 DE SEPTIEMBRE DEL 2012, CHIMALHUAC�N");
		//clemBean.setPsp("");
		//clemBean.setPspArt15A("");
		//clemBean.setPspArt19("");
		//clemBean.setFraccionArticulo20("");
		clemBean.setFraccionArticulo28("II");
		//clemBean.setTipoTramite("");
		//clemBean.setFechaSurteEfecto("");
		clemBean.setTipoPersona("2");
		clemBean.setRegPatronal("A5811382149");
		clemBean.setCveIdDelegacion("7");
		clemBean.setCveIdSubdelegacion("23");
		clemBean.setPspArt15A("1");
		
		clemBean.setFraccionArticulo20("1");
		clemBean.setFraccionArticulo26("1");
		clemBean.setFraccionArticulo28("1");
		clemBean.setFechaSurteEfecto("25/08/2020");
		clemBean.setMostrarComboArt155("1");
		clemBean.setIncisio155("I");
		clemBean.setIncisio115("II");
		clemBean.setInsMod("0");
		clemBean.setTipoTramite("120");
		clemBean.setCveTipoClem("1");
		clemBean.setBotonClem("Modificar CLEM");
		
		
		return clemBean;
	}	
	
	public DatosClem getDatosClem(){
		DatosClem clem=new DatosClem();
		//clem.setFolioResolucion("");
		clem.setDesTitular("H�CTOR LARA");
		clem.setDesSuplente("H�CTOR LARA JR.");
		clem.setPuesto("Secretario General del JEFE");
		clem.setDesMotivos("CAMBIO DE GIRO DEL NEGOCIO");
		clem.setDesLugarFechaExp("LUNES 17 DE SEPTIEMBRE DEL 2012, CHIMALHUAC�N");
		clem.setFecRegistroAlta(new Date());
		clem.setFecRegistroActualizado(new Date());
		clem.setCveAnalisis(new BigDecimal("18663"));
		//clem.setCveTipoDoc(new BigDecimal(""));
		//clem.setCveArticulo155(new BigDecimal(""));
		clem.setCveArticulo26("II");
		clem.setCveArticulo20("0");
		clem.setCveArticulo28("II");
		//clem.setCveSolicitud(new BigDecimal(""));
		//clem.setIndActivo(new BigDecimal(""));
		clem.setUltFechaActualizacion(new Date());
		clem.setCveTipoClem(new BigDecimal("2"));
		clem.setCveDelegacion(new BigDecimal("39"));
		clem.setCveSubdelegacion(new BigDecimal("138"));
		//clem.setCveDesDelegacion("");
		//clem.setCveDesSubdelegacion("");
		//clem.setErrorClem("");
		//clem.setMostrarComboArt155("");
		clem.setIncisoArticulo155("c)");
		//clem.setDescDelegacion("");
		//clem.setDescSubDelegacion("");
		clem.setDescFraccion115("XXXIV");
		//clem.setPsp15A("");
		//clem.setPsp19("");
		//clem.setArt20Clem("");
		clem.setTipoTramite("0");
		//clem.setFechaTramite("");
		//clem.setFecSurteEfecto("");
		
		return clem;
	}
	

	
}
