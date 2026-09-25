package mx.gob.imss.ctirss.delta.gestion.patronal.global.service.business;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.PersistenceException;

import junit.framework.Assert;
import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.exception.domicilio.LocalizarUmfException;
import mx.gob.imss.ctirss.delta.exception.individuo.CURPNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DiferenciasRENAPOContraSAT;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosRENAPOException;
import mx.gob.imss.ctirss.delta.exception.individuo.ErrorComparacionDatosSATException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.individuo.validacion.ErrorValidacionDatosConsultaEnEntidaExternaException;
import mx.gob.imss.ctirss.delta.framework.base.exception.AbstractException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.rule.RuleServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.afiliacion.AfiliacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.integracion.AltaPatronalIDSEIntegrador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.global.model.UnidadMedicaFamiliarTO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosConsulta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Regimen;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.patronal.RegistroIDSE;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoContactoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clase;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RolEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoCombustible;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.jfree.util.Log;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AfilicacionServiceTest {
	
	private static final Logger LOG;

	static {
		LOG = LoggerFactory.getLogger(AfilicacionServiceTest.class);
	}
	
	
	@Test
	public void concluirSolicitudDeAltaPatronal(){
//		<del:concluirAltaPatronal xmlns:del="http://delta.global.services/">
//		  <del:arg0>3137</del:arg0>
//		  <del:arg1>Z3020037134</del:arg1>
//		</del:concluirAltaPatronal>
		
//		 <del:arg0>859</del:arg0>
//		 <del:arg1>A8374693100</del:arg1>
		
		
//		 <del:concluirAltaPatronal xmlns:del="http://delta.global.services/">
//		   <del:arg0>9330</del:arg0>
//		   <del:arg1>C3728977109</del:arg1>
//		 </del:concluirAltaPatronal>
		//1415837826058729136
		
		//1415991006554729407
		Long idSolicitud = 729954l;//5364l se puede usar esta tambien
		String numeroRegistroPatronal="D1899990307";
		String numeroRegistroPatronal14="D1899990143";
		try {
			EJBLocator.getAfiliacionGlobalServiceBusiness().concluirAltaPatronal(idSolicitud, numeroRegistroPatronal, null);
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
			Assert.fail();
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void generarSolicitudMOCAlta(){
		SujetoObligado datosAfiliacion = generarSujetoMOC();
		Usuario usuario = new Usuario();
//		usuario.setCveIdUsuario("999L");
		usuario.setNomPaterno("Espinosa");
		usuario.setNomMaterno("Velasquez");
		usuario.setNomNombre("Ignacio");
		usuario.setPerfilUsuario(new PerfilUsuario());
		usuario.getPerfilUsuario().setIdPerfilUsuario(RolEnum.PATRON_SUJETO_OBLIGADO.getCodigo().longValue());
		Solicitud solicitud = null;
		OrigenSolicitudEnum origen = OrigenSolicitudEnum.INTERNET;
		try {
			solicitud = EJBLocator.getAfiliacionServiceBusiness().crearSolicitudDeAltaPatronal(datosAfiliacion, usuario, origen);
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
			Assert.fail();
		}
		
		LOG.error(solicitud.toString());
		
	}
	
	private SujetoObligado generarSujetoMOC(){
		SujetoObligado sujeto = new SujetoObligado();
		Long idPersona = 14482977L;
		
		ICADatosRespuesta datosICA = obtenerDatosRespuesta(idPersona);
		sujeto.setDatosICA(datosICA);
		sujeto.setFisica(datosICA.getPersonaFisicaIMSS());
		sujeto.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		
		sujeto = agregarDatosRP(sujeto);
		sujeto = agregarActividadEconomica(sujeto);
		sujeto = agregarCentrotrabajo(sujeto);
		sujeto = agregarRepresentanteLegal(sujeto);
		return sujeto;
	}

	private SujetoObligado agregarRepresentanteLegal(SujetoObligado sujetoTramite){
		RepresentanteLegal representante = new RepresentanteLegal();
		representante.setCveIdPersona(14515971l);
		representante.setIndActAdmonDominio(new BigDecimal(1));
		sujetoTramite.setRepresentantesLegales(new ArrayList<RepresentanteLegal>());
		sujetoTramite.getRepresentantesLegales().add(representante);
		return sujetoTramite;
	}
	
	private ICADatosRespuesta obtenerDatosRespuesta(Long idPersona){
		
		Object object = EJBLocator.getPersonaServiceBusiness();

		Assert.assertNotNull(object);
		Assert.assertTrue(object instanceof PersonaFisicaServiceBusinessRemote);

		final PersonaFisicaServiceBusinessRemote ejb = (PersonaFisicaServiceBusinessRemote) object;
		Assert.assertNotNull(ejb);
		ICADatosRespuesta icaDatosRespuesta = null;
		try {
			ICADatosConsulta datosConsulta = new ICADatosConsulta();
			datosConsulta.setIndicadorConsultaRENAPO(false);
			datosConsulta.setIndicadorConsultaSAT(true);
			datosConsulta.setPersonaFisica(new Fisica());
			datosConsulta.getPersonaFisica().setIdPersona(idPersona);
			icaDatosRespuesta = ejb.identificarCambios(datosConsulta);
			icaDatosRespuesta = ejb.integrarCambios(icaDatosRespuesta);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
		
		return icaDatosRespuesta;
	} 

	private SujetoObligado agregarDatosRP(SujetoObligado sujetoObligado){
		sujetoObligado.setModalidad(new Modalidad());
		sujetoObligado.getModalidad().setIdModalidad(1L);
		sujetoObligado.getModalidad().setNumModalidad("10");
		sujetoObligado.setPatronRelacionado(new Municipio());
		sujetoObligado.getPatronRelacionado().setClave("Y54");
		sujetoObligado.getPatronRelacionado().setNombre("POLANCO");
		return sujetoObligado;
	}
	private SujetoObligado agregarActividadEconomica(SujetoObligado sujetoObligado){
		sujetoObligado.setClasificacion(generarClasificacion());
		sujetoObligado.setProceso(generarProceso());
		sujetoObligado.setProductos(generarListaProductos());
		sujetoObligado.setMateriaPrimaMateriales(generarListaMateriaPrima());
		sujetoObligado.setEquipos(generarListaEquipo());
		sujetoObligado.setEquiposTransporte(generarListaTransporte());
		sujetoObligado.setPersonal(generarListaPersonal());
		sujetoObligado.setBienes(generarListaBienes());
		
		return sujetoObligado;
	}
	
	private SujetoObligado agregarCentrotrabajo(SujetoObligado so){
		so.setCntroTrabajo(generarCentroTrabajo());
		so.setSubdelegacion(new Subdelegacion());
		so.getSubdelegacion().setId(128L);
		so.getSubdelegacion().setClave("16");
		so.getSubdelegacion().setDelegacion(new Delegacion());
		so.getSubdelegacion().getDelegacion().setId(35l);
		so.getSubdelegacion().getDelegacion().setClave("35");
		so.getSubdelegacion().getDelegacion().setCiz(1);
		
		return so;
	}
	
	
	private List<Producto> generarListaProductos(){
		List<Producto> productos = new ArrayList<Producto>();
		Producto producto = new Producto();
		producto.setDescripcion("Confeti");
		producto.setIdVista(1l);
		productos.add(producto);
		
		return productos;
	}
	
	private List<MateriaPrima> generarListaMateriaPrima(){
		List<MateriaPrima> materias = new ArrayList<MateriaPrima>();
		MateriaPrima materia = new MateriaPrima();
		materia.setDescripcion("Papel");
		materia.setIdVista(1l);
		materias.add(materia);
		
		return materias;
	}
	
	private List<MaquinariaEquipo> generarListaEquipo(){
		List<MaquinariaEquipo> models = new ArrayList<MaquinariaEquipo>();
		MaquinariaEquipo model = new MaquinariaEquipo();
		model.setDesCapacidadPotencia("60hp");
		model.setDesNombre("Triturador");
		model.setDesUso("Cortar papel");
		model.setNumUnidades(new BigDecimal(3));
		model.setTipo(new TipoMaquinariaEquipo());
		model.getTipo().setId(1l);
		model.setIdVista(1l);
		
		models.add(model);
		return models;
	}
	
	private List<EquipoTransporte> generarListaTransporte(){
		List<EquipoTransporte> models = new ArrayList<EquipoTransporte>();
		EquipoTransporte model = new EquipoTransporte();
		model.setIdVista(1l);
		model.setDesCapacidadPotencia("140HP");
		model.setDesNombre("Combi");
		model.setDesUso("transporte de empleados");
		model.setNumUnidades(new BigDecimal(1));
		model.setTipoCombustible(new TipoCombustible());
		model.getTipoCombustible().setClave(1l);
		models.add(model);
		
		return models;
	}
	
	private List<Personal> generarListaPersonal(){
		List<Personal> models = new ArrayList<Personal>();
		Personal model = new Personal();
		model.setNumTrabajadores(new BigDecimal(12));
		model.setOficioOcupacion("Empacador");
		model.setIdVista(1l);
		
		models.add(model);
		
		return models;
	}
	
	private List<Bien> generarListaBienes(){
		List<Bien> models = new ArrayList<Bien>();
		Bien model = new Bien();
		model.setDesBienes("Taller");
		model.setNumCantidad(new BigDecimal(1));
		model.setIdVista(1l);
		
		models.add(model);
		
		return models;
	}
	
	private Proceso generarProceso(){
		Proceso proceso = new Proceso();
		proceso.setDesInicial("Primer Paso Alta");
		proceso.setDesIntermedio("Segundo Paso Alta");
		proceso.setDesFinal("Ultimo Paso Alta");
		return proceso;
	}
	
	private Clasificacion generarClasificacion(){
		Clasificacion cl = new Clasificacion();
		cl.setCveIdFraccionClase(196L);
		cl.setFecEfecto(Calendar.getInstance().getTime());
		cl.setFecPresentacion(Calendar.getInstance().getTime());
		cl.setFraccion(generaFraccion());
		cl.setGiro("Industria del Papel");
		cl.setIndDistribuyeEntrega(1);
		cl.setIndPrestaServicioPersonal(0);
		cl.setIndRegPatClase(0);
		cl.setIndServiciosATerceros(0);
		cl.setIndTransporteAjeno(0);
		cl.setIndTransportePropio(1);

		return cl;
	}
	
	private Fraccion generaFraccion(){
		Fraccion fr = new Fraccion();
		fr.setId(164L);
		fr.setDescripcion("Compraventa de artículos de uso personal, con transporte");
		fr.setDescripcionDetallada("Comprende a las empresas que se dedican a la compra, almacenamiento y venta al menudeo, medio mayoreo y/o mayoreo de artículos de uso personal, que cuenten con transporte para la distribución y/o equipo para el movimiento de las mercancías. Excepto prendas y accesorios de vestir,supermercados o tiendas de autoservicio y empresas que se dedican a prestar el servicio de transporte, clasificados por separado");
		fr.setNumFraccion("6");
		fr.setGrupo(new Grupo());
		fr.getGrupo().setId(35L);
		fr.getGrupo().setNumGrupo("2");
		fr.getGrupo().setDescripcion("Compraventa de prendas de vestir y otros artículos de uso personal");
		fr.getGrupo().setDivision(new Division());
		fr.getGrupo().getDivision().setId(7L);
		fr.getGrupo().getDivision().setNumDivision("6");
		fr.getGrupo().getDivision().setDescripcion("COMERCIO");
		fr.setClase(new Clase());
		fr.getClase().setClave(1l);
		fr.getClase().setDescripcion("0.54355");
		return fr;
	}
	
	private CentroTrabajo generarCentroTrabajo(){
		CentroTrabajo ct=new CentroTrabajo();
		ct.setAsentamiento(crearAsentamiento());
		ct.setCodigoPostal(new CodigoPostal());
		ct.setVialidadPrimaria(crearVialidadPrimaria());
		ct.setCodigoPostal(new CodigoPostal());
		ct.getCodigoPostal().setCodigoPostal("11530");
		ct.setCalle("AV DE LA AMARGURA");
		ct.setNumExterior1(25);
		ct.setColonia("Polanco 3 seccion");
		ct.setDescripcion("Domicilio trabajo");
		ct.setTipoDomicilio(new TipoDomicilio());
		ct.getTipoDomicilio().setClave(3);
		
		return ct;
	}
	
	private Asentamiento crearAsentamiento(){
		Asentamiento asent = new Asentamiento();
		
		asent.setClave("57");
		asent.setLocalidad(new Localidad());
		asent.getLocalidad().setMunicipio(new Municipio());
		asent.getLocalidad().getMunicipio().setEntidadFederativa(new EntidadFederativa());
		
		asent.getLocalidad().setClave("1");
		asent.getLocalidad().setNombre("MIGUEL HIDALGO");
		asent.getLocalidad().getMunicipio().setClave("16");
		asent.getLocalidad().getMunicipio().getEntidadFederativa().setClave("9");
		
		return asent;
	}
	
	private Vialidad crearVialidadPrimaria(){
		Vialidad vialidad = new Vialidad();
		vialidad.setNombre("ARQUIMIDES");
		vialidad.setTipoVialidad(new TipoVialidad());
		vialidad.setClave(143710);
		vialidad.getTipoVialidad().setClave(3);
		return vialidad;
	}
	
	@Test
	public void concluirCentroTrabajo(){
		try {
			
			EJBLocator.getAfiliacionServiceBusiness().concluirSolicitudAfiliacion(5530l);
		} catch (GestionPatronalBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void concluirRepresentanteLegal(){
		try {
			//13946473749889157 
			//13952685428739347
			//1409780565702710200
			//1413824214931727045
			EJBLocator.getAfiliacionGlobalServiceBusiness().concluirSolicitudDatosPatronales(729392l);
			//EJBLocator.getAfiliacionServiceBusiness().concluirSolicitudAfiliacion(710200l);
		} catch (GestionPatronalBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void concluirDatosGenerales(){
		try {
			//13946473749889157 
			//: 14072801404888259
			//13952685428739347   14074415152848300 
			//EJBLocator.getAfiliacionServiceBusiness().concluirSolicitudAfiliacion(710147l);
			//1409960834086710619 
			//1412209391089723598
			//1415831573716729104 1415831573716729104
			EJBLocator.getSolicitudPersonaServiceBusiness().finalizarSolicitudActualizacionDatos(729402l);
		} catch(Exception e){
			e.printStackTrace();
		}
//			catch (SolicitudNoEncontradaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SolicitudNoValidaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (AfectacionDatosPersonaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (PersonaNoEncontradaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TramiteNoEncontradoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	
	@Test
	public void concluirSolicitudPeersonasAutorizadas(){
		
			//13946473749889157 
			//: 14072801404888259
			//13952685428739347
			try {
				EJBLocator.getAfiliacionServiceBusiness().concluirSolicitudAfiliacion(8259l);
			} catch (GestionPatronalBusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//EJBLocator.getSolicitudPersonaServiceBusiness().finalizarSolicitudActualizacionDatos(8046l);
		
	}
	
	@Test
	public void concluirSolicitudSocios(){
		
			//13946473749889157 
			//: 14072801404888259
			//13952685428739347
		//1412197809389723546
		//1415837319518729133 
			try {
				EJBLocator.getAfiliacionServiceBusiness().concluirSolicitudAfiliacion(729393l);
			} catch (GestionPatronalBusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//EJBLocator.getSolicitudPersonaServiceBusiness().finalizarSolicitudActualizacionDatos(8046l);
		
	}
	
	@Test
	public void evaluarRIF(){
		boolean isRIF=EJBLocator.getRuleServiceBusiness().estaParametroRIFHabilitado();
		System.err.println("RIF: "+isRIF);
	}
	
	@Test
	public void validaRP(){
		try {
			EJBLocator.getRuleServiceBusiness().validarNumeroDeRegistroPatronal("A5110197107");
		} catch (GestionPatronalBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void isRegimenRIF(){
		List<Regimen> regimenes = new ArrayList<Regimen>();
		Regimen regimen = new Regimen();
		regimen.setClaveRegimen("607");
		regimenes.add(regimen);
		boolean isRIF=EJBLocator.getRuleServiceBusiness().isRegimenRIF(regimenes);
		System.err.println("RIF: "+isRIF);
	}
	
	@Test
	public void consultaPatronBasicoConMedios(){
		SujetoObligado sujeto=EJBLocator.getSujetoServiceBusiness().consultarPorRegistroPatronalBasic("B6199052329",null);
		System.err.println(sujeto);
	}
	
	
	public static void main(String args[]){
		int numColumnas=1;
		for(int i=0;i<=20;i++){
			for(int j=0;j<numColumnas;j++)
				System.out.print("*");
			System.out.print("\n");
			if(i<10){
				numColumnas++;
			}else{
				numColumnas--;
			}
		}
//		int i=0;
//		System.err.println("Inicio: "+Calendar.getInstance().getTime());
//		while(i<10000){
//			try {
//				
//				String strEncoded=Base64Cipher.cifrar("Armando");
//				System.err.println("Dato: "+i + "-" +strEncoded);
//			} catch (InvalidKeyException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IllegalBlockSizeException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (BadPaddingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			i++;
//		}
//		System.err.println("Fin: "+Calendar.getInstance().getTime());
//		Date fechaActual = Calendar.getInstance().getTime();
//		String cadenaFechaInicioRif="01/01/2013";
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//		try {
//			Date fechaInicioRIF = sdf.parse(cadenaFechaInicioRif);
//			if(fechaActual.after(fechaInicioRIF))
//				System.err.println("paso validacion");
//			else
//				System.err.println("no paso");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
	}
	
	@Test
	public void validarBajaPor251(){
		try {
			EJBLocator.getAfiliacionServiceBusiness().validarNoAdedudoPorBaja251("GAZA850214J59");;
		} catch (GestionPatronalBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void obtenerRL(){
		RepresentanteLegalServiceBusinessRemote ejb=EJBLocator.getRepresetanteLegalBusinessRemote();
		List<RepresentanteLegal> representantes = 
				ejb.obtenerRepresentanteLegalPorSujetoObligado(13734L);
				ejb.esRepresentanteDePersona(1l,1l, TipoPersonaEnum.MORAL);
				

		System.out.println(representantes);
		
	}
	
	@Test
	public void obtenerDetalleRPTest(){
		SujetoObligado so = new SujetoObligado();
//		so.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		so.setCveIdSujetoObligado(44786l);
		so = EJBLocator.getSujetoServiceBusiness().obtenerDetalleRP(so);
		System.out.println(so);
	}
	

	
	
	@Test
	public void obtenerDetalleRP(){
		SujetoObligado so = new SujetoObligado();
		so.setNumeroRegistroPatronal("Y9422117136");
		so.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		so = EJBLocator.getSujetoServiceBusiness().obtenerDetalleSujetoObligadoActividadEconomica(so);
		
		boolean test = EJBLocator.getServiceBusiness().existeTramitesClasificacionActivos(
				so.getCveIdSujetoObligado(),false);
		System.err.println(so);
	}

	
	
	
	@Test
	public void revisaWidgetCT(){
		SujetoObligado so = new SujetoObligado();
		so.setNumeroRegistroPatronal("B5022906107");
		so.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		
		SujetoObligado patron = 
				EJBLocator.getSujetoServiceBusiness().obtenerDetalleSujetoObligadoActividadEconomica(so);
		CentroTrabajo centroTrabajo = patron.getCntroTrabajo();
		List<MedioContacto> mediosCentroTrabajo = centroTrabajo!=null ? centroTrabajo.getMediosContacto() : null;
		
		if (centroTrabajo == null || centroTrabajo.getMediosContacto() == null
				|| centroTrabajo.getMediosContacto().isEmpty()) {
			if (centroTrabajo == null) {
				centroTrabajo = new CentroTrabajo();
			}
			Long idSujetoObligado = patron.getCveIdSujetoObligado();
			centroTrabajo.setCveIdPatronSujetoObligado(idSujetoObligado);

			mediosCentroTrabajo = null;
		}
		
		if(mediosCentroTrabajo != null ){
			for (MedioContacto medio : mediosCentroTrabajo) {
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_FIJO.getCodigo().longValue())){
					String descFormaContacto = medio.getDesFormaContacto();
					String telefonoFormateado = parseTelefono(descFormaContacto);
					medio.setDesFormaContacto(telefonoFormateado);
				}
				if(medio.getTipoMedioContacto().getIdTipoMedioContacto().equals(TipoContactoEnum.TELEFONO_MOVIL.getCodigo().longValue())){
					String descFormaContacto = medio.getDesFormaContacto();
					medio.setDesFormaContacto(descFormaContacto.replaceAll("\\|", " "));
				}

				System.err.println("Medio Titulo: " + medio.getTipoMedioContacto().getDescripcion());
				System.err.println("Medio Desc: " + medio.getDesFormaContacto());
			}
		}
		
		
		centroTrabajo.setMediosContacto(mediosCentroTrabajo);
	
	}
	
	
	private String parseTelefono(String telefono){
		if(telefono==null || (telefono !=null && telefono.equals("")) || (telefono !=null && telefono.equals("||")))
			return "";
		
		String[] telefonoSeccion = telefono.split("\\|");
		
		StringBuffer telefonoFormateado = new StringBuffer(); 
		telefonoFormateado.append("(").append(telefonoSeccion[0]).append(")");
		if(telefonoSeccion.length>1)
			telefonoFormateado.append(" ").append(telefonoSeccion[1]);
		if(telefonoSeccion.length>2)
			telefonoFormateado.append("-").append(telefonoSeccion[2]);
		
		return telefonoFormateado.toString();
	}
	
	@Test
	public void getPM(){
		Moral moral = new Moral();
		moral.setRfc("TCP0101023Z0");
		try {
			moral=EJBLocator.getConsultaPersonaMoralServiceBusinessRemote().consultarPersonaMoralPorRFCEnIMSSySAT(moral);
			System.err.println(moral);
		} catch (ClienteWebserviceSatRfcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersonasNoLocalizadasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void getPF(){
		Fisica moral = new Fisica();
		moral.setRfc("MOSA860605L26");
		moral.setCurp("MOSA860605MDFTLR09");
		try {
			moral=EJBLocator.getConsultaPersonaFisicaServiceBusinessRemote().getPersonaByCurpImssEntidadesExternas(moral);
			System.err.println(moral);
		} catch (ClienteWebserviceSatRfcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClienteWebserviceRenapoCurpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorComparacionDatosRENAPOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorComparacionDatosSATException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CURPNoLocalizadoEnEntidadExternaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RFCNoLocalizadoEnEntidadExternaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DiferenciasRENAPOContraSAT e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorValidacionDatosConsultaEnEntidaExternaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PersonaSinCalificacionesException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void consultaActa(){
		Long idActa=90565l;
		EscrituraConstitutiva ec=EJBLocator.getSujetoServiceBusiness().obtenerEscrituraConstitutivaPorId(idActa);
		System.err.println(ec);
	}
	
	@Test
	public void consultaFraccionObligatoria(){
		
		Clasificacion clasif = new Clasificacion();
		try {
			EJBLocator.getRuleServiceBusiness().validarFraccionObligatoria(clasif);
		} catch (GestionPatronalBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void listarRPActivos(){
		Persona persona = new Persona();
		persona.setIdPersona(37490878L);
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(1l);;
		try {
			List<SujetoObligado> rps = EjbLocator.getSujetoObligadoServiceBusiness().listarRegistrosPatronalesPorPersona(persona);
			System.err.println(rps.size());
		} catch (GestionPatronalBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	@Test
	public void escribeARP(){
		try {
			String ruta = "C:\\despliegues\\arpTest.pdf";
			byte[] reportaARP=EjbLocator.getARPBusiness().getArpPersona("14272503766753488353");
			FileOutputStream fos = new FileOutputStream(ruta);
			fos.write(reportaARP);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void escribeTIP(){
		try {
			String ruta = "C:\\despliegues\\arpTest.pdf";
			byte[] reportaARP=EjbLocator.getARPBusiness().getTipPersona("14272503766753488353");
			FileOutputStream fos = new FileOutputStream(ruta);
			fos.write(reportaARP);
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//12351
	//Y5846476108
	@Test
	public void generaArchivoSindo(){
		try {
			EjbLocator.getConcluirAltaBusiness().concluirAltaPatronal("Y5846476108", 712351l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void prepararDatosIDSE(){
		try {
			RegistroIDSE idseObj=EjbLocator.obtenerIDSEService().prepararDatosMovimientosIdse("1411786014081712977");
			System.err.println(idseObj);
		} catch (SolicitudNoEncontradaException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void generaAltaIdse(){
		AltaPatronalIDSEIntegrador altaIdseService = EJBLocator.getAltaIdseServiceBusiness();
		List<String> folio = new ArrayList<String>();	
		folio.add("Y5236342100");
		for(String nrp:folio)
			altaIdseService.obtenerDatosAltaPatronal(nrp, 1);
	}
	
	
	@Test
	public void generaSolicitudAltaModalidad14(){
		AfiliacionServiceBusinessRemote asbr = EJBLocator.getAfiliacionServiceBusiness();
		Usuario usuario = new Usuario();
		usuario.setPerfilUsuario(new PerfilUsuario());
		usuario.getPerfilUsuario().setIdPerfilUsuario(RolEnum.PATRON_SUJETO_OBLIGADO.getCodigo().longValue());
		usuario.setUsuario("MACH801112HDFRHG01");
		try {
			Solicitud solicitud = asbr.generarSolicitudDeAltaPatronalParaRegistroDeEventualesCaneros("A7210353309", TipoPersona.TIPO_PERSONA_FISICA,usuario);
			System.err.println("Solicitud: "+solicitud);
		} catch (GestionPatronalBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void obtenerRegistrosPatronalesMod30(){
		AfiliacionServiceBusinessRemote asbr = EJBLocator.getAfiliacionServiceBusiness();
		List<SujetoObligado> rps= asbr.obtenerNRPCanerosCandidatosParaAmpliacionEventuales(5433l);
		System.err.println(rps);
	}
	
	
	@Test
	public void obtenerNrpConvencional(){
		Persona persona = new Persona();
		persona.setIdPersona(37490878l);
		persona.setTipoPersona(new TipoPersona());
		persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
		String nrpConvencional=null;
		try {
			int i=0;
			while(i<100){
				System.err.println("inicio operación: "+Calendar.getInstance().getTime());
				nrpConvencional = EJBLocator.getRegistroPatronalService().obtenerNrpConvencionalPorDomicilioParticularYModalidad(persona.getIdPersona(), "43");
				System.err.println("fin operación: "+Calendar.getInstance().getTime());
				i++;
				System.err.println("NRP CONVENCIONAL: "+nrpConvencional);
			}
		} catch (GestionPatronalBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("NRP CONVENCIONAL: "+nrpConvencional);
		
	}
	
	
	@Test
	public void obtenerRegistroPatronal34(){
		Long idPersona = 1594433l;
		try {
			int i=0;
			while(i<100){
			String nrp =EJBLocator.getRegistroPatronalService().obtenerNrpModalidad34PersonaFisica(idPersona);
			i++;
			System.err.println(nrp);
			}
			
		} catch (GestionPatronalBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void crearRegistroPatronal34(){
		String nrp="C4295999343";
		Long idSolicitud=3488076l;
		Long idPersona=26791184l;
		
		try {
			EJBLocator.getRegistroPatronalService().crearNrpDomestico(nrp, idSolicitud, idPersona);
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void obtenerClaveMunicipioImss(){
		String cveMun="016";
		String cveEnt="07";
		String cp="29980";
		try {
			String munImss = EJBLocator.getRegistroPatronalService().obtenerClaveMunicipioImss(cveMun, cveEnt, cp);
			System.err.println(munImss);
		} catch (GestionPatronalBusinessException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testDomicilioServiceBusinessExterno() throws LocalizarUmfException{
		UnidadMedicaFamiliarTO[] umfs;
		try {
			umfs = EJBLocator.getDomicilioServices().consultarUMFPorCP("54080");
			for(UnidadMedicaFamiliarTO umf : umfs){
				System.err.println(umf);
			}
		} catch (LocalizarUmfException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	/*
	@Test
	public void actualizarCveIdSujetoObligadoEnXML() throws TramiteNoEncontradoException{
		String nrp="E5363720106";//6958623
		String folioOrigen="14218842592736325181";
		//--6958024
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folioOrigen);
		
		try {
			SujetoObligado sujetoObligado = new SujetoObligado();
			sujetoObligado.setNumeroRegistroPatronal(nrp);
			sujetoObligado = EJBLocator.getSujetoServiceBusiness().obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
			solicitud =EJBLocator.getSolicitudBusinessRemote().consultarFolio(solicitud);
			
			List<Tramite> tramites  = solicitud.getTramites();
			List<Tramite> tramitesAux  = new ArrayList<Tramite>();
			for(Tramite t:tramites){
				if(t instanceof TramiteSujetoObligado){
					TramiteSujetoObligado tso =(TramiteSujetoObligado) t;
					if(t.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT.getCodigo())){
						SujetoObligado so = tso.getSujetoObligado();
						so.setNumeroRegistroPatronal(nrp);
						so.setCveIdSujetoObligado(sujetoObligado.getCveIdSujetoObligado());
						agregarIdentificadoresAlTramite(sujetoObligado.getCveIdSujetoObligado(), sujetoObligado.getClasificacion().getId(), so);
						tso.setSujetoObligado(so);
					}
					tramitesAux.add(tso);
				}else{
					tramitesAux.add(t);
				}
				
			}
			solicitud.setTramites(tramitesAux);
			solicitud = EJBLocator.getSolicitudBusinessRemote().actualizarTramites(solicitud);
			
			System.err.println("Folio ACTUALIZADO: "+solicitud.getNoFolioSolicitud());
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
	
	@Test
	public void clonarSolicitudes(){
//		String nrp="A4826987109";
		String folioOrigen="14359630194713575537";
		//Long cveIdPatron=6918847L;
		System.err.println("Inicio operacion: "+Calendar.getInstance().getTime());
		
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folioOrigen);
		
		SolicitudBusinessRemote ssbr = EJBLocator.getSolicitudBusinessRemote();
		try {
			solicitud =ssbr.consultarFolio(solicitud);
		} catch (SolicitudNoEncontradaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i=0 ; i<500 ; i++){
		
			try {
//				System.err.println("Inicio consulta: "+Calendar.getInstance().getTime());
				
//				System.err.println("Fin consulta: "+Calendar.getInstance().getTime());
				
				solicitud.setNoFolioSolicitud(null);
				solicitud.setSolicitudId(null);
				
				List<Tramite> tramites  = solicitud.getTramites();
				List<Tramite> tramitesAux  = new ArrayList<Tramite>();
				for(Tramite t:tramites){
					t.setTramiteId(null);
					tramitesAux.add(t);					
				}
				solicitud.setTramites(tramitesAux);
				
//				System.err.println("Inicio crear: "+Calendar.getInstance().getTime());
				solicitud = ssbr.crear(solicitud);
//				System.err.println("Fin crear: "+Calendar.getInstance().getTime());
				
				
				
				System.err.println(solicitud.getNoFolioSolicitud());
				
//				System.err.println("Consulta folio nueva: "+Calendar.getInstance().getTime());
//				Solicitud solicitudNueva = ssbr.consultarFolio(solicitud);
//				System.err.println("Fin Consulta folio nueva: "+Calendar.getInstance().getTime());
//				
//				
//				List<Tramite> tramitesNuevos  = solicitudNueva.getTramites();
//				List<Tramite> tramitesAuxNuevos  = new ArrayList<Tramite>();
//				for(Tramite t:tramitesNuevos){
//					if(t instanceof TramiteFisica){
//						TramiteFisica tso =(TramiteFisica) t;
//						if(t.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT.getCodigo())){
//							Fisica fisica = tso.getFisica();
//							fisica.setUmf(new UnidadMedicaFamiliar());
//							fisica.getUmf().setDescripcion("NUEVA UMF PRUEBA ESTRES");
//						}
//						tramitesAuxNuevos.add(tso);
//					}else{
//						tramitesAuxNuevos.add(t);
//					}
//					
//				}
//				solicitud.setTramites(tramitesAux);
//				System.err.println("Inicio Actualiza folio nueva: "+Calendar.getInstance().getTime());
//				solicitud = EJBLocator.getSolicitudBusinessRemote().actualizarTramites(solicitud);
//				System.err.println("Fin Actualiza folio nueva: "+Calendar.getInstance().getTime());
			} 
//			catch (SolicitudNoEncontradaException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} 
		catch (SolicitudNoValidaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			catch (TramiteNoEncontradoException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	
	private SujetoObligado agregarIdentificadoresAlTramite(Long cveIdPatron, Long cveIdClasificacion ,SujetoObligado sujetoTramite){
		sujetoTramite.setCveIdSujetoObligado(cveIdPatron);
		sujetoTramite.getCntroTrabajo().setCveIdPatronSujetoObligado(cveIdPatron);
		sujetoTramite.getClasificacion().setId(cveIdClasificacion);
		sujetoTramite = agregarIdRegistroPatronalAClasificacion(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAProductos(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAMateriaPrima(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAMaquinaria(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAEquipoTransporte(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAPersonal(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalABienes(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAProceso(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalARepresentantesLegal(sujetoTramite);
		sujetoTramite = agregarIdRegistroPatronalAPersonasAutorizadas(sujetoTramite);
		
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalARepresentantesLegal(SujetoObligado sujetoTramite){
		if(sujetoTramite.getRepresentantesLegales()==null)
			return sujetoTramite;
		List<RepresentanteLegal> listaFinalRepresentantes = new ArrayList<RepresentanteLegal>();
		for(RepresentanteLegal rl:sujetoTramite.getRepresentantesLegales()){
			rl.setCveIdPatronSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinalRepresentantes.add(rl);
		}
		sujetoTramite.setRepresentantesLegales(listaFinalRepresentantes);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAPersonasAutorizadas(SujetoObligado sujetoTramite){
		if(sujetoTramite.getPersonasAutorizadas()==null)
			return sujetoTramite;
		List<PersonaAutorizada> listaPersonasAutorizadas = new ArrayList<PersonaAutorizada>();
		for(PersonaAutorizada pa:sujetoTramite.getPersonasAutorizadas()){
			SujetoObligado so = new SujetoObligado();
			so.setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			pa.setSujetoObligado(so);
			listaPersonasAutorizadas.add(pa);
		}
		sujetoTramite.setPersonasAutorizadas(listaPersonasAutorizadas);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAClasificacion(SujetoObligado sujetoTramite){
		sujetoTramite.getClasificacion().setSujetoObligado(new SujetoObligado());
		sujetoTramite.getClasificacion().getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAProductos(SujetoObligado sujetoTramite){
		
		List<Producto> listaFinalProducto = new ArrayList<Producto>();
		
		for(Producto producto : sujetoTramite.getProductos()){
			producto.setSujetoObligado(new SujetoObligado());
			producto.getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinalProducto.add(producto);
		}
		sujetoTramite.setProductos(listaFinalProducto);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAMateriaPrima(SujetoObligado sujetoTramite){
		
		List<MateriaPrima> listaFinal = new ArrayList<MateriaPrima>();
		
		for(MateriaPrima model : sujetoTramite.getMateriaPrimaMateriales()){
			model.setSujetoObligado(new SujetoObligado());
			model.getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinal.add(model);
		}
		sujetoTramite.setMateriaPrimaMateriales(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAMaquinaria(SujetoObligado sujetoTramite){
		
		List<MaquinariaEquipo> listaFinal = new ArrayList<MaquinariaEquipo>();
		
		for(MaquinariaEquipo model : sujetoTramite.getEquipos()){
			model.setSujetoObligado(new SujetoObligado());
			model.getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinal.add(model);
		}
		sujetoTramite.setEquipos(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAEquipoTransporte(SujetoObligado sujetoTramite){
		
		List<EquipoTransporte> listaFinal = new ArrayList<EquipoTransporte>();
		
		for(EquipoTransporte model : sujetoTramite.getEquiposTransporte()){
			model.setSujetoObligado(new SujetoObligado());
			model.getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinal.add(model);
		}
		sujetoTramite.setEquiposTransporte(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAPersonal(SujetoObligado sujetoTramite){
		
		List<Personal> listaFinal = new ArrayList<Personal>();
		
		for(Personal model : sujetoTramite.getPersonal()){
			model.setSujetoObligado(new SujetoObligado());
			model.getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinal.add(model);
		}
		sujetoTramite.setPersonal(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalABienes(SujetoObligado sujetoTramite){
		
		List<Bien> listaFinal = new ArrayList<Bien>();
		
		for(Bien model : sujetoTramite.getBienes()){
			model.setSujetoObligado(new SujetoObligado());
			model.getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
			listaFinal.add(model);
		}
		sujetoTramite.setBienes(listaFinal);
		return sujetoTramite;
	}
	
	private SujetoObligado agregarIdRegistroPatronalAProceso(SujetoObligado sujetoTramite){
		sujetoTramite.getProceso().setSujetoObligado(new SujetoObligado());
		sujetoTramite.getProceso().getSujetoObligado().setCveIdSujetoObligado(sujetoTramite.getCveIdSujetoObligado());
		return sujetoTramite;
	}

	
	

	@Test
	public void actualizarDetalleXML() throws TramiteNoEncontradoException{
		String folioOrigen="14261164584733487757";
		//--6958024
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folioOrigen);
		
		try {
			
			
			solicitud =EJBLocator.getSolicitudBusinessRemote().consultarFolio(solicitud);
			
			List<Tramite> tramites  = solicitud.getTramites();
			List<Tramite> tramitesAux  = new ArrayList<Tramite>();
			for(Tramite t:tramites){
				if(t instanceof TramiteSujetoObligado){
					TramiteSujetoObligado tso =(TramiteSujetoObligado) t;
					if(t.getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.ALTA_SRT.getCodigo())){
						SujetoObligado so = tso.getSujetoObligado();
//						Calendar fecEfecto=Calendar.getInstance();
//						fecEfecto.set(Calendar.DAY_OF_MONTH,1);
//						fecEfecto.set(Calendar.MONTH,1);
//						fecEfecto.set(Calendar.YEAR,2013);
//						so.getClasificacion().setFecEfecto(fecEfecto.getTime());
						tso.setSujetoObligado(so);
					}
					tramitesAux.add(tso);
				}else{
					tramitesAux.add(t);
				}
				
			}
			solicitud.setTramites(tramitesAux);
			solicitud = EJBLocator.getSolicitudBusinessRemote().actualizarTramites(solicitud);
			
			System.err.println("Folio ACTUALIZADO: "+solicitud.getNoFolioSolicitud());
		} catch (SolicitudNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	@Test
	public void testDomicilioCTAnalisisGCE(){
		String folioOrigen="14369162011183591143";
		//--6958024
		Solicitud solicitud = new Solicitud();
		solicitud.setNoFolioSolicitud(folioOrigen);
		SujetoObligado sujetoObligado= new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal("Y5846564101");
		sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		sujetoObligado=EJBLocator.getSujetoServiceBusiness().obtenerDetalleSujetoObligadoActividadEconomica(sujetoObligado);
		try {
			
			solicitud = EJBLocator.getSolicitudBusinessRemote().consultarFolio(solicitud);
			solicitud.setSujetoObligado(sujetoObligado);

			
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AbstractException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}
	
	
	@Test
	public void generaDocumentos(){
		
		List<String> folios = new ArrayList<String>();
//		folios.add("143636697744017691947");
//		folios.add("143639560925917789735");
		folios.add("143646987371017892967");
//		folios.add("143648713004517932005");
//		folios.add("143680447656418130158");
//		folios.add("143682484263518203437");
//		folios.add("143687361486518229212");
//		folios.add("143689245200918279793");
//		folios.add("143690891243218334658");
//		folios.add("143691314238818343903");
//		folios.add("143698680018818444826");
		
		
		try {
		for(String folio:folios){
			
			EJBLocator.getServiceBusiness().generarDocumentos(folio);
			Log.error("Se genera doctos de solicitud: "+folio);
		
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void generaSolicitudFirmaDigital(){
		
		Solicitud solicitud = new Solicitud();
		solicitud.setSolicitudId(18029766l);
		FirmaElectronica firma = new FirmaElectronica();
		
		firma.setCadenaOriginal("||Invocante:portalimssdigital|Trámite:INCORPORACIÓN VOLUNTARIA AL RÉGIMEN OBLIGATORIO|Fecha:10 de julio 2015, 15:21:45|Folio:143655970595618029766|NRP:M6299999442|RFC:|CODV860723JN4|Nombre o Razón  Social:VIRIDIANA LIZBET COTA DELGADO|CURP:CODV860723MBSTLR02|NSS:22058608674||");
		firma.setRecibo("nNnhsZ1lNgodqTi0nFyoYSsD4Qb+AWkwafzy+uDBW8VSCjkfBnj+XirLbbZYnStwTjhio+y1dpOE1rtxznu80vGdD31UoF+xijG3gNL9JjOl/NumltsEKGFwHqaS2k/fXO0zfrHoEWOiTE386PsxaKD1ODkzvC/HYoEXt33dYEbpNUQ4jqdu13S5sjC5tUtKogoIaZKp/b/tEE0iaDNlcIaDHMxc+ROeb84T4izfoWFJAyEiMBwZMnicoJ9y06jAlBpJt+0hULz2Qi/ng5SF95urbFF9JZjvNh00bc0gE3PajO2e8KAk5NyZYOm0jzWHd0MHfI21ZdThlD63VG6aLw==");
		firma.setReciboNotarial("4dddb0c8-ae68-4620-b0a7-8c173ce9eba3");
		firma.setSerialCertificado("00000000000000000001");
		firma.setIniciaVigenciaCertificado(Calendar.getInstance().getTime());
		firma.setFinVigenciaCertificado(Calendar.getInstance().getTime());
		
		
	}
}
