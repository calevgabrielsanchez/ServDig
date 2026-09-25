package mx.gob.imss.ctirss.correccion.web.controller.seguimiento.pagos.vo;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;
import mx.gob.imss.ctirss.correccion.seguimiento.estudioCorreccion.model.CrtRevPagos;
import mx.gob.imss.ctirss.correccion.utils.Functions;
import mx.gob.imss.ctirss.correccion.web.controller.seguimiento.pagos.PagosSeguimientoController;

/**
 * Clase / Objeto que nos permite controlar el JSP de la pantalla de 
 * Pagos (pagosSeguimientoMain.jsp).
 * 
 * @see PagosSeguimientoController
 * @version 1.0.0
 * @author Marco Antonio Nieto Plett
 */
@SuppressWarnings(value = { "rawtypes","unchecked" })
public class PagosVO {
	
	/**Constante que indica el pago por COP*/
	public static final int TIPO_PAGO_COP = 1;
	
	/**Constante que indica el pago por RCV*/
	public static final int TIPO_PAGO_RCV = 2;
	
	/**Constante que indica el pago por Movimiento Afiliatorio*/
	public static final int TIPO_PAGO_MA = 3;
	
	/**Constante que indica el pago por COP y RCV*/
	public static final int TIPO_PAGO_COPRCV = 4;
	
	/**Constante que indica el pago por COP y Movimiento Afiliatorio*/
	public static final int TIPO_PAGO_COPMA = 5;
	
	/**Constante que indica el pago por COP, RCV y Movimieto Afiliatorio*/
	public static final int TIPO_PAGO_COPRCVMA = 6;
	
	
	/**
	 * Modelo que nos permite realizar ABC en los pagos 
	 * relacionados con Cédula de Revisión y
	 * Cédula de Validación.
	 */
	private CrtRevPagos pagoModel;
	
	/**
	 * Control de los combos utilizados en la pantalla de pagos.<br><br>
	 * <b>Llaves de los mapas:</b><br><br>
	 * <b>mapPeriodoCOPMA:</b> Periodos disponibles para COP y MA <br>
	 * <b>mapPeriodoRCV:</b> Periodos disponibles para RCV ( Bimestrales)<br>
	 * <b>mapRegistrosPatronales:</b> Registros patronales asociados a la corrección<br>
	 * <b>mapTipoMovimiento:</b> Tipo de movimiento asociado al pago. <br>
	 * <b>mapTipoDocumento:</b> Tipo de documento sociado al pago.
	 */
	private Map mapCombos;
	
	/**
	 *Atributo auxiliar de pantalla que indica el tipo de 
	 *movimiento seleccionado por el usuario. <br><br>
	 *
	 *Ver constantes estáticas de esta clase.
	 *
	 */
	private Integer tipoMovimiento;
	
	/**Atributo auxiliar de pantalla que indica la selección
	 * en el periodo de las COP.
	 */
	private String copSelect;
	
	/**Atributo auxiliar de pantalla que indica la selección
	 * en el periodo de las RCV.
	 */
	private String rcvSelect;
	
	/**Atributo auxiliar de pantalla que indica la selección
	 * en el periodo de los movimientos afiliatorios.
	 */
	private String maSelect;
	
	/**Atributo auxiliar de pantalla que indica la fecha
	 * real del de pago realizado. 
	 */
	private String fechaPagoStr;
	
	/**
	 * Indica el periodo inicial de la solicitud de
	 * la corrección.
	 * @see CrtSolicitudcorr
	 */
	private Date periodoInicial;
	
	/**
	 * Indica el periodo final de la solicitud de
	 * la corrección.
	 * @see CrtSolicitudcorr
	 */
	private Date periodoFinal;
	
	/**
	 * Folio asignado al momento de que se realiza
	 * la solicitud de la corrección.
	 * @see CrtSolicitudcorr
	 */
	private String folioCorreccion;	
	
	/**
	 * Atributo que indica el resultado de una operación.
	 * Puede ser un éxito o un error. Este se alertará 
	 * al momento de terminar la petición JSON.
	 */
	private String msg;
	
	/**
	 * Indica la fecha máxima retroactiva
	 * para realizar el pago.
	 */
	private String fechaMinDateCalendar;

	/**
	 * Constructor por defecto inicializa el mapa
	 * de combos y el modelo asociado.
	 * 
	 * @see HashMap
	 * @see CrtRevPagos
	 * @author Marco Antonio Nieto Plett
	 */
	public PagosVO(){
		setMapCombos(new HashMap());
		setPagoModel(new CrtRevPagos());
	}
	
	/**
	 * Permite inicializar todos los elementos que se
	 * van a utilizar en la pantalla de pagos. Este se
	 * debe de ejecutar al momento de abrir la ventana.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @see RegistroPatronalVO
	 * @param rps (Lista de Rps)
	 */
	public void initComboBoxes(List<RegistroPatronalVO> rps){
		
		setMapCombos(new HashMap());
				
		initTipoDocumento();
		initTipoMovimiento();
		initRegistrosPatronales(rps);
		initPeriodos(this.periodoInicial,this.periodoFinal);
	}
	
	
	/**
	 * Inicializa el mapa de periodos para COP,RCV y 
	 * movimientos afiliatorio.
	 * 
	 * @param periodoIncial
	 * @param PeriodoFinal
	 * 
	 * @see java.util.Date
	 */
	private void initPeriodos(Date periodoIncial, Date PeriodoFinal){
		
		Map<String,String> periodoCOPMA = new LinkedHashMap<String,String>();
		Map<String,String> periodoRCV = new LinkedHashMap<String,String>();
		String periodo = "";
		String periodoAuxRCV = "";
		
		int mes = 0;
		int index = -1;
		Boolean ingresarRCVAux = false;
		Calendar cPeriodoInicial = Calendar.getInstance();
		Calendar cPeriodoFinal = Calendar.getInstance();
		
		cPeriodoInicial.setTime(periodoIncial);
		cPeriodoFinal.setTime(PeriodoFinal);
		
		periodoCOPMA.put("0", "-- Seleccione --");
		periodoRCV.put("0", "-- Seleccione --");
		
		while(cPeriodoInicial.compareTo(cPeriodoFinal)<=0){
			
			mes = cPeriodoInicial.get(Calendar.MONTH) + 1;
			periodo = String.valueOf(cPeriodoInicial.get(Calendar.YEAR))
					+ String.valueOf((mes<10) ?	("0" + mes) :	mes	);
			
			if(index<0) index = mes; //Sirve para posicinar el cursosr para COP o RCV
			
			mes = cPeriodoInicial.get(Calendar.MONTH) + 2;
			periodoAuxRCV = String.valueOf(cPeriodoInicial.get(Calendar.YEAR))
					+ String.valueOf((mes<10) ?	("0" + mes) :	mes	);
			
			
			if(index%2!=0){ //none
				periodoCOPMA.put(periodo, periodo);
			}else{//par
				periodoCOPMA.put(periodo, periodo);
				periodoRCV.put(periodo, periodo);
			}
			
			cPeriodoInicial.add(Calendar.MONTH, 1);
			
			if(cPeriodoInicial.compareTo(cPeriodoFinal)>=0){
				
				if(index%2!=0 && ingresarRCVAux){
					periodoRCV.put(periodoAuxRCV, periodoAuxRCV);
				}
				
				ingresarRCVAux = true;
			}
			
			index++;
			
		}
		
		//Correccion periodo RCV
		
		if(index%2==0){
			if(index>12){
				index=index%12;
			}
			
			String per=""+cPeriodoFinal.get(Calendar.YEAR);
			StringBuilder num=new StringBuilder();
			num.append(index);
			if(num.length()==1){
				per+="0"+num;
			}else{
				per+=num;
			}
			periodoRCV.put(per, per);
		}
		getMapCombos().put("mapPeriodoCOPMA", periodoCOPMA);
		getMapCombos().put("mapPeriodoRCV", periodoRCV);
	}
	
	/**
	 * Inicializa el mapa de registros patronales
	 * @author Marco Antonio Nieto Plett
	 * @see RegistroPatronalVO	
	 * @param rps
	 */
	private void initRegistrosPatronales(List<RegistroPatronalVO> rps){
		
		Map<Integer,String> tempMap = new LinkedHashMap<Integer,String>();
		
		tempMap.put(0, "--Seleccione --");
		
		for(RegistroPatronalVO currentRp :rps){
			tempMap.put(currentRp.getId(),currentRp.getDescripcion());
		}
		
		getMapCombos().put("mapRegistrosPatronales", tempMap);
		
	}
	
	/**
	 * Inicializa los tipos de movimientos o pagos disponibles
	 * en pantalla. Esta lista se forma directamente en 
	 * este método, no proviene de base de datos.
	 * 
	 * @author Marco Antonio Nieto Plett
	 */
	private void initTipoMovimiento(){
		Map<Integer,String> tempMap = new LinkedHashMap<Integer,String>();
		
		tempMap.put(0, "--Seleccione --");
		tempMap.put(1, "C.O.P");
		tempMap.put(2, "R.C.V");
		tempMap.put(3, "MA");
		tempMap.put(4, "C.O.P y R.C.V");
		tempMap.put(5, "C.O.P y MA");
		tempMap.put(6, "C.O.P, R.C.V y M.A");
		
		
		getMapCombos().put("mapTipoMovimiento", tempMap);

	}
	
	/**
	 * Inicializa los tipos de documentos disponibles
	 * en pantalla. Esta lista se forma directamente en 
	 * este método, no proviene de base de datos.
	 * 
	 * @author Marco Antonio Nieto Plett
	 */
	private void initTipoDocumento(){
		Map<Integer,String> tempMap = new LinkedHashMap<Integer,String>();
		
		tempMap.put(0, "--Seleccione --");
		tempMap.put(53, "53");
		tempMap.put(58, "58");
		
		
		getMapCombos().put("mapTipoDocumento", tempMap);

	}
	
	/**
	 * Permite recuperar el Pojo que controla a los
	 * pagos del estudio de corrección (Revisión y 
	 * validación)
	 * 
	 * @author Marco Antonio Nieto Plett
	 */
	public CrtRevPagos getPagoModel() {
		return pagoModel;
	}
	
	/**
	 * Permite ingresar el Pojo que controla los 
	 * pagos del estudio de corrección.
	 * (Revisión y validación)
	 * 
	 * @author Marco Antonio Nieto Plett
	 * 
	 * @param pagoModel
	 */
	public void setPagoModel(CrtRevPagos pagoModel) {
		this.pagoModel = pagoModel;
	}


	/**
	 * Otorga un mapa que contiene otros mapas
	 * los cuales son los listados que se 
	 * desplegarán en los combos de la pantalla
	 * de pagos.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Map (Key,Map)
	 */
	private Map getMapCombos() {
		return mapCombos;
	}

	/**
	 * Permite ingresar los mapas que se van a utilizar
	 * en la pantalla de pagos.
	 * @author Marco Antonio Nieto Plett
	 * @param mapCombos (Key,Map)
	 */
	private void setMapCombos(Map mapCombos) {
		this.mapCombos = mapCombos;
	}
	
	/**
	 * Otorga el mapa específico de los registros
	 * patronales asociados a la solicitud 
	 * de la corrección.
	 * @author Marco Antonio Nieto Plett   
	 * @return Map(ID AnexoSolcorrpat,Descripción)
	 */
	public Map getMapaRegistrosPatronales(){
		return (Map) mapCombos.get("mapRegistrosPatronales");
	}
	
	/**
	 * Otorga el mapa específico de los periodos disponibles
	 * para los combos de COP y Movimientos afiliatorios.<br>
	 * Formato: YYYYDD
	 * @author Marco Antonio Nieto Plett
	 * @return (Periodo,Periodo)
	 */
	public Map getMapaPeriodoCOPMA(){
		return (Map) mapCombos.get("mapPeriodoCOPMA");
	}
	
	/**
	 * Otorga el mapa específico de los periodos disponibles
	 * para el combo de RCV<br>
	 * Formato: YYYYDD
	 * @author Marco Antonio Nieto Plett
	 * @return (Periodo,Periodo)
	 */
	public Map getMapaPeriodoRCV(){
		return (Map) mapCombos.get("mapPeriodoRCV");
	}
	
	/**
	 * Otorga el mapa específico de los tipos de
	 * movimientos disponibles para el combo
	 * Tipo de movimiento.<br>
	 * @author Marco Antonio Nieto Plett
	 * @return (tpMovimiento,tpMovimiento)
	 */
	public Map getMapaTipoMovimiento(){
		return (Map) mapCombos.get("mapTipoMovimiento");
	}
	
	/**
	 * Otorga el mapa específico de los tipos de
	 * documentos disponibles para el combo
	 * Tipo de movimiento.<br>
	 * @author Marco Antonio Nieto Plett
	 * @return (tpDocumento,tpDocumento)
	 */
	public Map getMapaTipoDocumento(){
		return (Map) mapCombos.get("mapTipoDocumento");
	}

	/**
	 * Permite recuperar el tipo de movimiento
	 * seleccionado en pantalla.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Constante de tipo de movimiento 
	 * (ver descripción de atributos)
	 */
	public Integer getTipoMovimiento() {
		return tipoMovimiento;
	}

	/**
	 * Permite ingresar el tipo de movimiento
	 * seleccionado en pantalla.
	 * @author Marco Antonio Nieto Plett
	 * @param tipoMovimiento
	 */
	public void setTipoMovimiento(Integer tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	/**
	 * Permite recuperar el periodo seleccionado
	 * en pantalla de las COP.
	 * @author Marco Antonio Nieto Plett
	 * @return null, cero o mayor que cero
	 */
	public String getCopSelect() {
		return copSelect;
	}

	/**
	 * Permite ingresar el periodo seleccionado 
	 * en pantalla de la COP.
	 * @author Marco Antonio Nieto Plett
	 * @param copSelect (null, cero o mayor que cero)
	 */
	public void setCopSelect(String copSelect) {
		this.copSelect = copSelect;
		setModelPeriodo();
	}

	/**
	 * Permite recuperar el periodo seleccionado en 
	 * pantalla del pago RCV.
	 * @author Marco Antonio Nieto Plett
	 * @return null, cero o mayor que cero.
	 */
	public String getRcvSelect() {
		return rcvSelect;
	}

	/**
	 * Permite ingresar el periodo seleccionado
	 * en pantalla del pago RCV.
	 * @author Marco Antonio Nieto Plett
	 * @param rcvSelect (Null, cero o mayor que cero)
	 */
	public void setRcvSelect(String rcvSelect) {
		this.rcvSelect = rcvSelect;
		setModelPeriodo();
	}

	/**
	 * Permite recuperar el periodo seleccionado
	 * en pantalla donde se involucran los 
	 * movimientos afiliatorios.
	 * @author Marco Antonio Nieto Plett
	 * @return null, cero o mayor que cero
	 */
	public String getMaSelect() {
		return maSelect;
	}

	/**
	 * Permite ingresar el periodo seleccionado
	 * en pantalla donde se involucran los 
	 * movimientos afiliatorios.
	 * @author Marco Antonio Nieto Plett
	 * @param maSelect (Null, cero o mayor que cero)
	 */
	public void setMaSelect(String maSelect) {
		this.maSelect = maSelect;
		setModelPeriodo();
	}
	
	/**
	 * Nos permite identificar el tipo de pago que se realizó
	 * en pantalla. Siendo que los pagos de COP, RCV y MA
	 * son opcionales según la selección del usuario, este método
	 * permite recuperar el periodo correcto evitando valores NULL.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * 
	 */
	private void setModelPeriodo(){
		switch(this.getTipoMovimiento().intValue()){
		case PagosVO.TIPO_PAGO_COP:
			pagoModel.setNumPeriodo(Integer.parseInt(this.getCopSelect()));
			break;
		case PagosVO.TIPO_PAGO_RCV:
			pagoModel.setNumPeriodo(Integer.parseInt(this.getRcvSelect()));
			break;
		case PagosVO.TIPO_PAGO_MA:
			pagoModel.setNumPeriodo(Integer.parseInt(this.getMaSelect()));
			break;
		case PagosVO.TIPO_PAGO_COPRCV:
			pagoModel.setNumPeriodo(Integer.parseInt(this.getCopSelect()));
			break;
		case PagosVO.TIPO_PAGO_COPMA:
			pagoModel.setNumPeriodo(Integer.parseInt(this.getCopSelect()));
			break;
		case PagosVO.TIPO_PAGO_COPRCVMA:
			pagoModel.setNumPeriodo(Integer.parseInt(this.getCopSelect()));
			break;
		}	
	}

	/**
	 * Nos permite recuperar la fecha real de pago
	 * ingresada en la pantalla de pagos. 
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return DD-MM-YYYY
	 */
	public String getFechaPagoStr() {
		return fechaPagoStr;
	}

	/**
	 * Nos permite ingresar la fecha que el usuario
	 * selecciono en la pantalla de pagos. Adicional
	 * inicializa la fecha tipo DATE del POJO 
	 * CrtRevPagos.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param fechaPagoStr
	 */
	public void setFechaPagoStr(String fechaPagoStr) {
		pagoModel.setFecFechapago(Functions.stringToDate(fechaPagoStr));
		this.fechaPagoStr = fechaPagoStr;
	}

	/**
	 * Permite recuperar la fecha inicial que involucra
	 * la solicitud de la corrección.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Fecha inicio del periodo
	 */
	public Date getPeriodoInicial() {
		return periodoInicial;
	}

	/**
	 * Permite recuperar la fecha inicial que involucra
	 * la solicitud de la corrección.
	 * 
	 * Esta llega al momento de ejecutar la pantalla 
	 * a través de parámentros GET.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param periodoInicial
	 */
	public void setPeriodoInicial(Date periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	/**
	 * Permite recuperar la fecha final que involucra
	 * la solicitud de la corrección.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return fecha fin de periodo
	 */
	public Date getPeriodoFinal() {
		return periodoFinal;
	}

	/**
	 * Permite ingresar la fecha final que involucra
	 * la solicitud de la corrección.
	 * 
	 * Esta llega al momento de ejecutar la pantalla 
	 * a través de parámentros GET.
	 * 
	 * @author Marco Antonio Nieto Plett
	 */
	public void setPeriodoFinal(Date periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	/**
	 * Permite recuperar el folio asociado a la 
	 * solicitud de la corrección.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Folio de Corrección
	 */
	public String getFolioCorreccion() {
		return folioCorreccion;
	}

	/**
	 * Permite Ingresar el folio asociado a la 
	 * solicitud de la corrección.
	 * 
	 * Esta llega al momento de ejecutar la pantalla 
	 * a través de parámentros GET.
	 *
	 *@author Marco Antonio Nieto Plett
	 *@param folioCorreccion
	 */
	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}

	
	/**
	 * Otorga el mensaje de éxito o error
	 * al terminar un proceso de pago.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @return Mensaje de Exito o Error
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * Permite ingresar el resultado de una operación
	 * durante un proceso de pago. Este puede ser
	 * de éxito o error.
	 * 
	 * @author Marco Antonio Nieto Plett
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getFechaMinDateCalendar() {
		return fechaMinDateCalendar;
	}

	public void setFechaMinDateCalendar(String fechaMinDateCalendar) {
		this.fechaMinDateCalendar = fechaMinDateCalendar;
	}
	
}
