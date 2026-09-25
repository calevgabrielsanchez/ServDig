
package mx.gob.imss.dashboardServicios.Controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Operacion;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Servicio;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.BitacoraServicios;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote;
import mx.gob.imss.dashboardServicios.infraestructura.utilerias.EjbLocator;

import org.primefaces.model.chart.CartesianChartModel;  
import org.primefaces.model.chart.LineChartSeries;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @ autor Josue Hernandez Ramirez
 * @ version 1.0
 * 06/01/2012
 */
@ManagedBean(name = "bitacora")
@ViewScoped
public class BitacoraMB implements Serializable {
	private static final long serialVersionUID = 3369672292695040056L;

	private ConsultaBusinessRemote consulta;
	private BitacoraDTO filtroBitacoraDTO;
	private BitacoraServicios detalleSeleccionado;
	private BitacoraServicios detalleSeleccionadoLogEntrada;
	private BitacoraServicios detalleSeleccionadoLogSalida;
	private boolean muestraResultado = false;
	private boolean muestrafiltro = true;
	private boolean muestraGrafica=false;
    private CartesianChartModel linearModel;  


	public BitacoraMB() {
		consulta =EjbLocator.getEjbRemote();
		if (this.filtroBitacoraDTO == null) {
			this.filtroBitacoraDTO = new BitacoraDTO();
			detalleSeleccionado = new BitacoraServicios();
		}
		this.filtroBitacoraDTO.setLstAplicaciones(consulta.getSistemas(null));
		this.filtroBitacoraDTO.setLstServicios(new LinkedList<Servicio>());
		this.filtroBitacoraDTO.setLstOperaciones(new LinkedList<Operacion>());
	}
    
    private void generaGrafica() {
    	linearModel = new CartesianChartModel();
    	if (this.filtroBitacoraDTO.getLstBitacora() != null&&!this.filtroBitacoraDTO.getLstBitacora().isEmpty()){
    		List<BitacoraServicios> lista = this.filtroBitacoraDTO.getLstBitacora();
    		muestraGrafica=true;
    		String sServicio = "";
    		String sOperacion = "";
    		LineChartSeries series = null;
    		for(BitacoraServicios bitacora : lista ){
    			
    			if (sServicio.equals("")){
    				sServicio = bitacora.getCveServicio();
    				sOperacion = bitacora.getCveOperacion();
    				series = new LineChartSeries();
    				series.setLabel(sServicio + " - " + sOperacion);    				
    			}
    			
    			if (!sServicio.equals(bitacora.getCveServicio())){
    				sServicio = bitacora.getCveServicio();
    				sOperacion = bitacora.getCveOperacion();
    				
    				linearModel.addSeries(series);
    				
    				series = new LineChartSeries();
    				series.setLabel(sServicio + " - " + sOperacion);
    			}
    			
    			if (!sOperacion.equals(bitacora.getCveOperacion())){
    				sOperacion = bitacora.getCveOperacion();
    				
    				linearModel.addSeries(series);
    				
    				series = new LineChartSeries();
    				series.setLabel(sServicio + " - " + sOperacion);
    			}
    			series.set(bitacora.getIdBitacora(), bitacora.getTiempoMilisegundos());
    		}

			if (!sServicio.equals("")){
				linearModel.addSeries(series);
			}
    	}else{
    		muestraGrafica=false;
    	}
    }      

    public void buscaBitacoras() {	
		try{
			BitacoraServicios filtroBusqueda = new BitacoraServicios();
			if ( filtroBitacoraDTO.getCveSistema() != null){
				filtroBusqueda.setCveSistema(filtroBitacoraDTO.getCveSistema());
			}
			
			if ( filtroBitacoraDTO.getCveServicio() != null){
				filtroBusqueda.setCveServicio(filtroBitacoraDTO.getCveServicio());
			}

			if ( filtroBitacoraDTO.getCveOperacion() != null){
				filtroBusqueda.setCveOperacion(filtroBitacoraDTO.getCveOperacion());
			}
			
			//AGREGA LOS FILTROS DE FECHA
			String DATE_FORMAT_MINUTOS = "yyyy-MM-dd H:mm";
			String DATE_FORMAT_MILSEG = "yyyy-MM-dd H:mm:ss:SSS";
			
	    	SimpleDateFormat sdfm = new SimpleDateFormat(DATE_FORMAT_MINUTOS);
	    	SimpleDateFormat sdfms = new SimpleDateFormat(DATE_FORMAT_MILSEG);

			String sFechaInicial = sdfm.format(filtroBitacoraDTO.getFechaInico()) + ":00:000";
			String sFechaFinal = sdfm.format(filtroBitacoraDTO.getFechaFin()) + ":59:999";
			
	    	Date fechaInicial = sdfms.parse(sFechaInicial);
	    	Date fechaFinal = sdfms.parse(sFechaFinal);
	    	
	    	filtroBusqueda.setFhInicio(dateToGregorianCalendar(fechaInicial));
	    	filtroBusqueda.setFhFinal(dateToGregorianCalendar(fechaFinal));


			filtroBitacoraDTO.setLstBitacora(consulta.getBitacora(filtroBusqueda, ConsultaBusinessRemote.TIPO_ORDENAMIENTO_SERVICIO));
			muestraResultado = true;
			generaGrafica();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void seleccionaServicios(){
		if ( filtroBitacoraDTO.getCveSistema() != null){
			System.out.println("Obtiene la lista de servicios de la aplicacion: " + filtroBitacoraDTO.getCveSistema() );
			List<Servicio> listaServicios = consulta.getServicios(filtroBitacoraDTO.getCveSistema());
			
			for (Servicio servicio: listaServicios){
				System.out.println("servicio: " + servicio.getCveServicio());
			}
			this.filtroBitacoraDTO.setLstServicios( consulta.getServicios(filtroBitacoraDTO.getCveSistema()) );
			this.filtroBitacoraDTO.setLstOperaciones( new LinkedList<Operacion>() );
		}
		else{
			this.filtroBitacoraDTO.setLstServicios( new LinkedList<Servicio>() );
			this.filtroBitacoraDTO.setLstOperaciones( new LinkedList<Operacion>() );			
		}
	}

	public void seleccionaOperaciones(){
		if ( filtroBitacoraDTO.getCveSistema() != null){
			if ( filtroBitacoraDTO.getCveServicio() != null){
				System.out.println("Obtiene la lista de operaciones del servicio: " + filtroBitacoraDTO.getCveServicio() );
				List<Operacion> listaOperaciones = consulta.getOperaciones(filtroBitacoraDTO.getCveServicio());
				
				for (Operacion operacion: listaOperaciones){
					System.out.println("Operacion: " + operacion.getCveOperacion());
				}
				this.filtroBitacoraDTO.setLstOperaciones( consulta.getOperaciones(filtroBitacoraDTO.getCveServicio()) );
			}
			else{
				this.filtroBitacoraDTO.setLstOperaciones( new LinkedList<Operacion>() );
			}			
		}
		else{
			this.filtroBitacoraDTO.setLstOperaciones( new LinkedList<Operacion>() );
		}			

	}
	
	/*
	 * Seccion de getters y Setters
	 */
	public BitacoraDTO getFiltroBitacoraDTO() {
		return filtroBitacoraDTO;
	}

	public void setFiltroBitacoraDTO(BitacoraDTO filtroBitacoraDTO) {
		this.filtroBitacoraDTO = filtroBitacoraDTO;
	}

	public boolean getMuestraResultado() {
		return muestraResultado;
	}

	public void setMuestraResultado(boolean muestraResultado) {
		this.muestraResultado = muestraResultado;
	}

	public boolean getMuestrafiltro() {
		return muestrafiltro;
	}

	public void setMuestrafiltro(boolean muestrafiltro) {
		this.muestrafiltro = muestrafiltro;
	}

	public BitacoraServicios getDetalleSeleccionado() {
		return detalleSeleccionado;
	}

	public void setDetalleSeleccionado(BitacoraServicios detalleSeleccionado) {
		this.detalleSeleccionado = detalleSeleccionado;
	}
	
	public BitacoraServicios getDetalleSeleccionadoLogEntrada() {
		return detalleSeleccionadoLogEntrada;
	}

	public void setDetalleSeleccionadoLogEntrada(BitacoraServicios detalleSeleccionadoLogEntrada) {
		String sEntrada = consulta.getBitacoraEntrada(detalleSeleccionadoLogEntrada.getIdBitacora());
		System.out.println("bitacora entrada: " + sEntrada);
		detalleSeleccionadoLogEntrada.setEntrada(sEntrada);
		this.detalleSeleccionadoLogEntrada = detalleSeleccionadoLogEntrada;
	}
	
	public BitacoraServicios getDetalleSeleccionadoLogSalida() {
		return detalleSeleccionadoLogSalida;
	}

	public void setDetalleSeleccionadoLogSalida(BitacoraServicios detalleSeleccionadoLogSalida) {
		String sSalida=consulta.getBitacoraSalida(detalleSeleccionadoLogSalida.getIdBitacora());
		System.out.println("Bitacora salida: "+ sSalida);
		detalleSeleccionadoLogSalida.setSalida(sSalida);
		this.detalleSeleccionadoLogSalida = detalleSeleccionadoLogSalida;
	}

	public XMLGregorianCalendar dateToGregorianCalendar(java.util.Date fecha){
		XMLGregorianCalendar fechaXml = null;
		try{
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(fecha);
			fechaXml = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return fechaXml;	
	}

	public boolean isMuestraGrafica() {
		return muestraGrafica;
	}

	public void setMuestraGrafica(boolean muestraGrafica) {
		this.muestraGrafica = muestraGrafica;
	}

	public CartesianChartModel getLinearModel() {
		return linearModel;
	}

	public void setLinearModel(CartesianChartModel linearModel) {
		this.linearModel = linearModel;
	}	
}
