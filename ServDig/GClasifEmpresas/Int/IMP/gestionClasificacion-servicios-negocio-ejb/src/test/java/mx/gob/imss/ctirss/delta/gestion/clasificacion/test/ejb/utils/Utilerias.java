package mx.gob.imss.ctirss.delta.gestion.clasificacion.test.ejb.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.ArticuloModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.Comentario;
import mx.gob.imss.ctirss.delta.model.clasificacion.DatosClem;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosAnalisisConsulta;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosAnalisisConsultaDataTable;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosBitacoras;
import mx.gob.imss.ctirss.delta.model.clasificacion.FiltrosReportes;
import mx.gob.imss.ctirss.delta.model.clasificacion.SolicitudConcluida;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoRegistroEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionPropuestaDTO;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UsuarioFuncionario;
import mx.gob.imss.ctirss.delta.model.derechohabiente.negocio.PerfilUsuario;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;

public class Utilerias {
	public AnalisisClasificacionEmpresas generaModelo(){
		
		Long cveIdAnalisis = new Long(6);
		Long cveIdDelegacion = new Long(1);
		Long cveIdSubdelegacion = new Long(1);
		Long cveIdSolicitud = 2472L;
		Long cveIdFraccionAct = 1L;
		Long cveIdFraccionAnt = 1L;
		Long cveIdFraccionPro = 1L;
		BigDecimal primaSRTAct = new BigDecimal(2.5854);
		BigDecimal primaSRTAnt = new BigDecimal(2.5854);
		BigDecimal primaSRTPro = new BigDecimal(2.5854);
		Long cveUsuario = 1L;
		String cveRol = "1";
		
		Clasificacion clasificacionActual = new Clasificacion();
		Fraccion fraccionAct = new Fraccion();
		fraccionAct.setId(cveIdFraccionAct);
		fraccionAct.setPrimaSRT(primaSRTAct);
		clasificacionActual.setFraccion(fraccionAct);
		
		Clasificacion clasificacionAnterior = new Clasificacion();
		Fraccion fraccionAnt = new Fraccion();
		fraccionAnt.setId(cveIdFraccionAnt);
		fraccionAnt.setPrimaSRT(primaSRTAnt);
		clasificacionAnterior.setFraccion(fraccionAnt);

		Clasificacion clasificacionPropuesta = new Clasificacion();
		Fraccion fraccionPro = new Fraccion();
		fraccionPro.setId(cveIdFraccionPro);
		fraccionPro.setPrimaSRT(primaSRTPro);
		clasificacionPropuesta.setFraccion(fraccionPro);

	 	Usuario usuario = new Usuario();
	 	usuario.setCveIdUsuario(cveUsuario + "");
	 	
	 	Solicitud solicitud = new Solicitud();
		solicitud.setId(cveIdSolicitud);
		
		Comentario comentarios=new Comentario();
		comentarios.setDescripcion("Comentario JUnit Test");
		List<Comentario> lstComentarios=new ArrayList<Comentario>();
		lstComentarios.add(comentarios);
		
		AnalisisClasificacionEmpresas modelo = new AnalisisClasificacionEmpresas();
			 	
		modelo.setCveIdAnalisis(cveIdAnalisis);
		modelo.setSolicitud(solicitud);
		
		//Se asigna la fecha
		modelo.setFechaAutorizacion(new Date());
	 	//Asignando el usuario
	 	modelo.setClaveUsuarioAsignado(usuario.getCveIdUsuario().toString());
	 	modelo.setCveIdRol(usuario.getPerfilUsuario().getIdPerfilUsuario());
	 	
	 	modelo.setCveIdDelegacion(cveIdDelegacion);
	 	modelo.setCveIdSubdelegacion(cveIdSubdelegacion);
	 	
	 	modelo.setClasificacionActual(clasificacionActual);
	 	modelo.setClasificacionAnterior(clasificacionAnterior);
	 	modelo.setClasificacionPropuesta(clasificacionPropuesta);
	 	
	 	modelo.setComentarios(lstComentarios);

	 	return modelo;
	}
	
	public FiltrosReportes generaFiltroReporte(){
		String cveIdDelegacion = "1";
		String cveIdSubdelegacion = "1";
		String periodoInicio = "2012-06-01";
		String periodoFin = "2012-07-30";
		
		FiltrosReportes filtrosReportes = new FiltrosReportes();
		filtrosReportes.setDelegacion(cveIdDelegacion);
//		filtrosReportes.setSubDelegacion(cveIdSubdelegacion);
//		filtrosReportes.setPeriodoFin(periodoFin);
		
		if(filtrosReportes.getTipoRegistro()!=null && filtrosReportes.getTipoRegistro().compareTo(TipoRegistroEnum.TODOS.getClave())==0){
			filtrosReportes.setTipoRegistro(null);
		}
		
		return filtrosReportes;
	}
	
	public ArticuloModel generaArticulo(){
		BigDecimal numArticulo = new BigDecimal(155);
		BigInteger cveIdDelegacion = BigInteger.valueOf(1l);
		BigInteger cveIdSubdelegacion = BigInteger.valueOf(1l);
		
		ArticuloModel articulo = new ArticuloModel(); 
		articulo.setNumArticulo(numArticulo);
		articulo.setCveIdDelegacion(cveIdDelegacion);
		articulo.setCveIdSubdelegacion(cveIdSubdelegacion);
		
		return articulo;
	}
	
	public FiltrosBitacoras generaFiltroBitacora(){
		Long cveIdAnalisis = new Long(22);
		Long cveIdDelegacion = new Long(1);
		Long cveIdSubdelegacion = new Long(1);
		String cveUsuario = "Angelique Boyer";
		String periodoInicial = "2012-06-01";
		String periodoFinal = "2012-07-30";
		
		FiltrosBitacoras filtrosBitacoras = new FiltrosBitacoras();
		filtrosBitacoras.setCveIdAnalisis(cveIdAnalisis);
		filtrosBitacoras.setDelegacion(cveIdDelegacion);
		filtrosBitacoras.setSubDelegacion(cveIdSubdelegacion);
		filtrosBitacoras.setUsuario(cveUsuario);
//		filtrosBitacoras.setPeriodoInicial(periodoInicial);
//		filtrosBitacoras.setPeriodoFinal(periodoFinal);
		
		return filtrosBitacoras;
	}
	
	public Clasificacion generaClasificacion(){
		Long cveIdDivision = 1L;
		Long cveIdGrupo = 1L;
		Long cveIdFraccion = 1L;

		Division division = new Division();
		division.setId(cveIdDivision);
		
		Grupo grupo = new Grupo();
		grupo.setId(cveIdGrupo);
		
		Fraccion fraccion = new Fraccion();
		fraccion.setId(cveIdFraccion);
		fraccion.setGrupo(grupo);
		fraccion.getGrupo().setDivision(division);
		
		Clasificacion clasificacion = new Clasificacion();
		clasificacion.setFraccion(fraccion);
		
		return clasificacion;
	}
	
	public Comentario generaComentario(){
		BigDecimal cveIdAnalisis = new BigDecimal(22);
		String cveUsuario = "Angelique Boyer";
		
		Comentario comentario = new Comentario();
		comentario.setCveIdAnalisis(cveIdAnalisis);
		comentario.setFecha(new Date());
		comentario.setUsuario(cveUsuario);
		return comentario;
	}
	
	public Solicitud generaSolicitud(){
		Long cveIdSolicitud = 1L;
		
		Solicitud solicitud = new Solicitud();
		solicitud.setId(cveIdSolicitud);
		return solicitud;
	}
	
	public SujetoObligado generaSujetoObligado(){
		String regPatronal = "F111350710";
		String tipoPersona = "2";
		
		SujetoObligado sujetoObligado = new SujetoObligado();
		sujetoObligado.setNumeroRegistroPatronal(regPatronal);
		sujetoObligado.setTipoPersonaFiscal(tipoPersona.equals("2") ? TipoPersonaFiscal.MORAL : TipoPersonaFiscal.FISICA);
		return sujetoObligado;
	}
	
	public SolicitudConcluida generaSolicitudConcluida(){
		Integer cveIdSolicitud = 2228;
		
		SolicitudConcluida solicitud  = new SolicitudConcluida();
		solicitud.setCveIdSolicitud(cveIdSolicitud);
		return solicitud;
	}
	
	public DatosEntradaPaginador<FiltrosAnalisisConsulta> generaDatosPaginador() throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Date dPeriodoInicio = sdf.parse("2012-06-01");
		Date dPeriodoFin = sdf.parse("2012-07-30");
		
		FiltrosAnalisisConsulta oForm = new FiltrosAnalisisConsulta();		
		oForm.setPeriodoInicio(dPeriodoInicio);
		oForm.setPeriodoFin(dPeriodoFin);
		
		FiltrosAnalisisConsultaDataTable aoData = new FiltrosAnalisisConsultaDataTable();
		aoData.setoForm(oForm);
		
		DatosEntradaPaginador<FiltrosAnalisisConsulta> parametrosPaginador = new DatosEntradaPaginador<FiltrosAnalisisConsulta>();
		parametrosPaginador.setModelo(aoData.getoForm());
		return parametrosPaginador;
	}
	
	public static ClasificacionDTO generaClasificacionDTO() {
		
		final Usuario usuario = new Usuario();
		PerfilUsuario pu = new PerfilUsuario();
		pu.setDescripcion("Jefe de Oficina Del");
		pu.setIdPerfilUsuario(3L);

		usuario.setCveIdUsuario("7L");
		UsuarioFuncionario uf = new UsuarioFuncionario();

		uf.setDelegacion(new Delegacion());
		uf.getDelegacion().setId(39L);

		usuario.setCveIdSubdelegacion(138L);
		uf.setSubdelegacion(new Subdelegacion());
		uf.getSubdelegacion().setId(138L);
		
		uf.setUsuario(usuario);
		usuario.setUsuarioFuncionario(uf);
		
		usuario.setPassword("prueba");
		usuario.setUsuario("prueba");
		
		final ClasificacionDTO clasificacionDTO = new ClasificacionDTO();
		clasificacionDTO.setCveIdSolicitud("1993");
		clasificacionDTO.setCveIdDelegacion("39");
		clasificacionDTO.setCveIdSubdelegacion("138");
		clasificacionDTO.setRegPatronal("D684580310");
		clasificacionDTO.setTipoPersona("2");
		clasificacionDTO.setUsuario(usuario);
		
		return clasificacionDTO;
	}
	
	public static ClasificacionPropuestaDTO getClasificacionPropuestaDTO(){
		
		ClasificacionPropuestaDTO clasificacionPropuestaDTO=new ClasificacionPropuestaDTO();
		clasificacionPropuestaDTO.setCveIdAnalisis("124");
		clasificacionPropuestaDTO.setRfc("AEG270427UE5");
		clasificacionPropuestaDTO.setTipoPersona("2");
		clasificacionPropuestaDTO.setRegPatronal("A403927010");
		clasificacionPropuestaDTO.setIndRegPatClase("");
		clasificacionPropuestaDTO.setCveIdDivision("19");
		clasificacionPropuestaDTO.setCveIdGrupo("225");
		clasificacionPropuestaDTO.setCveIdFraccionPro("1122");
		clasificacionPropuestaDTO.setClase("1");
		clasificacionPropuestaDTO.setActividadDetectada("Prueba Actividad Detectada");
		clasificacionPropuestaDTO.setComentarios("Comentarios de Rectificaciónj");
		clasificacionPropuestaDTO.setCveIdDelegacion("39");
		clasificacionPropuestaDTO.setCveIdSubdelegacion("138");
		clasificacionPropuestaDTO.setCveIdFraccionAct("767");
		clasificacionPropuestaDTO.setCveIdFraccionAnt("");
		clasificacionPropuestaDTO.setPrimaSRTAct("2.5984");
		clasificacionPropuestaDTO.setPrimaSRTPro("4.6532");
		clasificacionPropuestaDTO.setPrimaSRTAnt("");
		clasificacionPropuestaDTO.setIndModAut("1");
		
		Usuario usuario=new Usuario();
		usuario.setCveIdUsuario("1L");
		usuario.setUsuario("Usuario");
		clasificacionPropuestaDTO.setUsuario(usuario);
		clasificacionPropuestaDTO.setCveUsuarioAsignado("1");
		return clasificacionPropuestaDTO;
	}
	
	public static DatosClem getDatosClem(){
		DatosClem clem=new DatosClem();
		//clem.setFolioResolucion("");
		clem.setDesTitular("HÉCTOR LARA");
		clem.setDesSuplente("HÉCTOR LARA JR.");
		clem.setDesMotivos("CAMBIO DE GIRO DEL NEGOCIO");
		clem.setDesLugarFechaExp("LUNES 17 DE SEPTIEMBRE DEL 2012, CHIMALHUACÁN");
		clem.setFecRegistroAlta(new Date());
		clem.setFecRegistroActualizado(new Date());
		clem.setCveAnalisis(new BigDecimal("124"));
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
	
	public static ReporteClemBean getReporteClemBean(){
		ReporteClemBean clemBean=new ReporteClemBean();
		//clemBean.setRuta("");
		//clemBean.setCveIdClem("");
		clemBean.setIdAnalisis("124");
		//clemBean.setFolio("");
		clemBean.setDelegacion("MUNICIPIO ORIENTE (EDO. MÉX.)");
		clemBean.setSubdelegacion("CHIMALHUACÁN");
		clemBean.setRazonSocial("VENTA DE PRODUCTOS DE IMPORTACIÓN");
		clemBean.setDomicilio("C. TONAMETL, COLONIA HERREROS");
		clemBean.setMunicipioDelegacion("CHIMALHUACÁN");
		clemBean.setRegPatronal("A403927010");
		clemBean.setFechaAviso("17/09/2012");
		clemBean.setIdDivisionPatron("53");
		clemBean.setIdGrupoPatron("21");
		clemBean.setIdFraccionPatron("210");
		clemBean.setDenominacionFraccion("VENTA DE PRODUCTOS DE IMPORTACIÓN");
		clemBean.setClase("II");
		clemBean.setPrima("1.1306");
		//clemBean.setFechaTramite("");
		clemBean.setMotivos("CAMBIO DE GIRO DEL NEGOCIO");
		clemBean.setIdDivisionPropuesta("53");
		clemBean.setIdFraccionPropuesta("210");
		clemBean.setIdGrupoPropuesta("21");
		clemBean.setDivisionPropuesta("PIRATERÍA");
		clemBean.setGrupoPropuesta("ALQUILER DE AUTOMOVILES");
		clemBean.setClasePropuesta("II");
		clemBean.setPrimaPropuesta("1.1306");
		clemBean.setFraccionPropuesta("ALQUILER DE CAMIONES DE CARGA SIN CHOFER");
		clemBean.setFraccionArticulo26("II");
		clemBean.setFraccion115("XXXIV");
		clemBean.setIncisio115("c)");
		clemBean.setTitular("HÉCTOR LARA");
		clemBean.setSuplente("HÉCTOR LARA JR.");
		clemBean.setLugarFechaExpedicion("LUNES 17 DE SEPTIEMBRE DEL 2012, CHIMALHUACÁN");
		//clemBean.setPsp("");
		//clemBean.setPspArt15A("");
		//clemBean.setPspArt19("");
		//clemBean.setFraccionArticulo20("");
		clemBean.setFraccionArticulo28("II");
		//clemBean.setTipoTramite("");
		//clemBean.setFechaSurteEfecto("");
		clemBean.setTipoPersona("Moral");
		
		return clemBean;
	}
}