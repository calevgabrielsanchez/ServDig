
/*Variables de control globales la pantalla de pagos. INICIO*/
	/**Control de eventos*/
	window.onunload = exitPagos;

	/**Pago proveniente de la pantalla de cédula de recepción*/
	var TIPO_PAGO_RECEPCION = 1;
	/**Pago proveniente de la pantalla de cédula de validación*/
	var TIPO_PAGO_VALIDACION = 2;
	/**Pago proveniente de la pantalla de seguimiento promoción*/
	var TIPO_PAGO_PROMOCION = 3;
	
	/**Variable tipo Hidden dentro de pagosSeguimientoMain
	 * Indica el tipo de pago.
	 * */
	var IND_TIPO_PAGO = "indTipopago";
	
	/**Movimiento tipo C.O.P*/
	var MV_COP_TYPE = 1;
	/**Movimiento tipo R.C.V*/
	var MV_RCV_TYPE = 2;
	/**Movimiento tipo Movimiento Afiliatorio*/
	var MV_MA_TYPE = 3;
	/**Movimiento tipo C.O.P y R.C.V*/
	var MV_COPRCV_TYPE = 4;
	/**Movimiento tipo C.O.P y Movimiento Afiliatorio*/
	var MV_COPMA_TYPE = 5;
	/**Movimiento tipo C.O.P, R.C.V y Movimiento Afiliatorio*/
	var MV_COPRCVMA_TYPE = 6;
	
	/**Selección del Usuario del tipo de movimiento*/
	var MV_SELECTED = 0;

	/** ID del DIV que contiene la información de pagos para C.O.P*/
	var DIV_NAME_COP = "copDiv";
	/** ID del DIV que contiene la información de pagos para R.C.V*/
	var DIV_NAME_RCV = "rcvDiv";
	/** ID del DIV que contiene la información de pagos para Movimientos Afiliatorios*/
	var DIV_NAME_MA  = "maDiv";
	/** ID del DIV que contiene la información de los datos generales de la pantal*/
	var DIV_NAME_GENERAL_DATA  = "datosGeneralesDiv";
	
	/**Nombre del combobox que contiene los periodos para C.O.P*/
	var SELECT_PERIODO_NAME_COP = "copSelect";
		/**Nombre del combobox que contiene los periodos para R.C.V*/
	var SELECT_PERIODO_NAME_RCV = "rcvSelect";
	/**Nombre del combobox que contiene los periodos para Movimientos Afiliatorios*/
	var SELECT_PERIODO_NAME_MA  = "maSelect";

	/**Trabajadores regularizados en movimientos afiliatorios*/	
	var TEXT_TRAB_REG = "trabReg";
	/**Trabajadores dados de alta en movimientos afiliatorios*/	
	var TEXT_TRAB_ALTAS= "trabAltas";
	/**Trabajadores dados de baja en movimientos afiliatorios*/	
	var TEXT_TRAB_BAJAS = "trabBajas";
	/**Trabajadores con modificaciones en salarios en movimientos afiliatorios*/	
	var TEXT_TRAB_MOD_SALARIO = "modSalario";
	
	/**Numero de la orden de ingreso, es excluyente con folio SUA*/
	var TEXT_ORDEN_INGRESO = "oIngreso";
	/**Numero del folio SUA, es excluyente con orden de ingreso*/
	var TEXT_FOLIO_SUA = "sua";
	
	/**Registos patronales contenidos en la solicitud de la corrección*/
	var SELECT_REGISTRO_PATRONAL = "regitroPatronal";
	/**Tipo de movimiento seleccionado (COP, RCV, Mov. Afil y sus combinaciones*/
	var SELECT_TP_MOVIMIENTO = "tpMovimiento";
	/**Número del c´redito asociado al movimiento de pago*/
	var TEXT_NUM_CREDTO = "numCredito";
	/**Fecha en la que se realizó el pago*/
	var TEXT_FECHA_PAGO = "fechaPago";
	/**Tipo de documeto que aplica para el pago*/
	var TEXT_TP_DOCTO = "tpDocto";
	
	/** Pagos en COPS*/	
	var TEXT_SP_COP = "SP_COP";
	var TEXT_ACT_COP = "ACT_COP";
	var TEXT_REC_COP = "REC_COP";
	var TEXT_MULTAS_COP = "MULTA_COP";
	/**Pagos en R.C.V*/	
	var TEXT_SP_RCV = "SP_RCV";
	var TEXT_ACT_RCV = "ACT_RCV";
	var TEXT_REC_RCV = "REC_RCV";
	var TEXT_MULTAS_RCV = "MULTA_RCV";

	/** ID que identifica al tipo de pago SUA*/
	var IND_FOLIO_SUA = 1;
	/** ID que identifica al tipo de pago ORDEN_INGRESO*/
	var IND_ORDEN_INGRESO = 2;
	
	var MONTO_MAXIMO = 200000000;
	
/*Variables de control globales la pantalla de pagos. FIN*/

/**
	Permite controlar si los campos de un DIV estan habilitados o deshabilitados.
	
	@param divsContainerName: arreglo de DIVs a evaluar. ex:['A','B','...']
	@param deactiveFields: Si el valor viene en true los campos del DIV se deshabilitarán,
	                         false los habilitará		 
	@param resetFields: Si el valor viene en true, los campos del DIV se reiniciarán,
	                    false deja al campo con su valor actual.
						
	@Author: Marco A Nieto Plett
	
*/
	function handleDivFields(divsContainerName,deactiveFields,resetFields){

		if(divsContainerName==null || deactiveFields == null || resetFields == null){
			alert("::Existe por lo menos un valor NULL en los parámetros, valor inv\u00e1lido::");
			return;
		}
		
		try{
			for(var i=0;i<divsContainerName.length;i++){
				
				var arrayFields = document.getElementById(divsContainerName[i]).getElementsByTagName("*");


				for(var j=0;j<arrayFields.length;j++){

					if(arrayFields[j].type!= undefined){
					
						if(arrayFields[j].type=='select-one'){

							if(resetFields){ arrayFields[j].options.selectedIndex = 0; }
						
						}else if(arrayFields[j].type=='text'){
	
							if(resetFields){ arrayFields[j].value = ""; }
							
						}
						
					}
					
					arrayFields[j].disabled=deactiveFields;
				}
			}
		}catch(error){
			alert("::" + error+ "::");
		}
		
	}
	
	/**
		
		Permite seleccionar un mismo valor (value) en diferentes
		listas desplegables (combos).
		
		@param obj: Combo que realiza la petición (this)
		
		@param comboName: Arreglo con todos los  IDs de 
		       los combos a evaluar. ex: ['combo1','combo2','...']
			   
		@author: Marco A Nieto Plett
		
	*/
	function selectAllCombosSameValue(obj,comboNames){
		
		var currentComboList;
		var foundError = false;
		
		for(var i=0 ; i < comboNames.length ; i++){
			currentComboList = document.getElementById(comboNames[i]);
			//Jorge:se agrega valdacion && Number(MV_SELECTED)<4 para q pueda ocupar los dos combos cuando es combinado el tipo
			if(obj.name != currentComboList.name && Number(MV_SELECTED)<4){

				currentComboList.options.selectedIndex=0;
				currentComboList.options.value=obj.value;

				if(currentComboList.value=="" || currentComboList.value==undefined){
					foundError = true;
					currentComboList.options.selectedIndex=0;
				}

			}
			
		}
		
		if(foundError){
			var msg = "::Todas las listas desplegables deben corresponder al mismo periodo::\n";
							
			alert(msg);
			
		}
		
	}
	
	/**
		Permite habilitar/deshabilitar las secciones 
		COP,RCV y Mov. Afiliatorios(MA) según sea la
		selección del usuario.
		
		@Param obj: Objeto tipo select con el valor de la selección.
		
		@Author: Marco A Nieto Plett
	
	*/
	function movementType(obj){
		try{
			
			MV_SELECTED = obj.value;

			switch(Number(obj.value)){
				case MV_COP_TYPE:
					handleDivFields([DIV_NAME_COP],false,true);
					handleDivFields([DIV_NAME_RCV,DIV_NAME_MA],true,true);
					break;
				case MV_RCV_TYPE:
					handleDivFields([DIV_NAME_RCV],false,true);
					handleDivFields([DIV_NAME_COP,DIV_NAME_MA],true,true);
					break;
				case MV_MA_TYPE:
					handleDivFields([DIV_NAME_MA],false,true);
					handleDivFields([DIV_NAME_COP,DIV_NAME_RCV],true,true);
					break;
				case MV_COPRCV_TYPE:
					handleDivFields([DIV_NAME_COP,DIV_NAME_RCV],false,true);
					handleDivFields([DIV_NAME_MA],true,true);
					break;
				case MV_COPMA_TYPE:
					handleDivFields([DIV_NAME_COP,DIV_NAME_MA],false,true);
					handleDivFields([DIV_NAME_RCV],true,true);
					break;
				case MV_COPRCVMA_TYPE:
					handleDivFields([DIV_NAME_COP,DIV_NAME_RCV,DIV_NAME_MA],false,true);
					break;
				default:
					handleDivFields([DIV_NAME_COP,DIV_NAME_RCV,DIV_NAME_MA],true,true);
					MV_SELECTED = 0;
					if(obj.value > 0 ){
						alert("El valor ingresado en el combo de movimientos no es válido");		
					}
					
			}
		}catch(error){
			alert("::"+error+"::");
		}
	}
	
	/**
		Permite compartir la selección del combo padre (obj)
		con los combos hijos descritos en cada case.
		
		MV_SELECTED es evaluado e inicializado cuando
		            se selecciona el tipo de movimiento.
		
		@Param obj: Combo que invoca a la función en onchange			
		
		@Author: Marco A Nieto Plett
	*/
	function shareComboValue(obj){
		switch(Number(MV_SELECTED)){
			case MV_COP_TYPE:
				selectAllCombosSameValue(obj,[SELECT_PERIODO_NAME_COP]);
				break;
			case MV_RCV_TYPE:
				selectAllCombosSameValue(obj,[SELECT_PERIODO_NAME_RCV]);
				break;
			case MV_MA_TYPE:
				selectAllCombosSameValue(obj,[SELECT_PERIODO_NAME_MA]);
				break;
			case MV_COPRCV_TYPE:
				selectAllCombosSameValue(obj,[SELECT_PERIODO_NAME_COP,SELECT_PERIODO_NAME_RCV]);
				break;
			case MV_COPMA_TYPE:
				selectAllCombosSameValue(obj,[SELECT_PERIODO_NAME_COP,SELECT_PERIODO_NAME_MA]);
				break;
			case MV_COPRCVMA_TYPE:
				selectAllCombosSameValue(obj,[SELECT_PERIODO_NAME_COP,SELECT_PERIODO_NAME_RCV,SELECT_PERIODO_NAME_MA]);
				break;
		}
	}
	
	/**
		Controla el ingreso del tipo de pago, únicamente
		se puede ingresar un valor ya sea en SUA u 
		Orden de ingreso.
		
		@param typeOfReference: Indica de donde viene el valor ingresado
		
		@Author Marco A Nieto Plett
	*/
	function paymentReference(typeOfReference){
		
		if(document.getElementById(TEXT_ORDEN_INGRESO).value=="" &&
		     document.getElementById(TEXT_FOLIO_SUA).value==""){
			
			document.getElementById(TEXT_ORDEN_INGRESO).disabled = false;
			document.getElementById(TEXT_FOLIO_SUA).disabled = false; 
			
		}else{
			if(typeOfReference==IND_FOLIO_SUA){
			
			document.getElementById(TEXT_ORDEN_INGRESO).value = "";
			document.getElementById(TEXT_ORDEN_INGRESO).disabled = true;
			
			}else if(typeOfReference==IND_ORDEN_INGRESO){
				
				document.getElementById(TEXT_FOLIO_SUA).value = "";
				document.getElementById(TEXT_FOLIO_SUA).disabled = true;
				
			}else{
				alert("::ERROR : El valor ingresado en tipo de referencia es inv\u00e1lido::");
			}
		
		}
		
	}
	
	/**
		Habilita la sección de datos generales con el objetivo
		de insertar un nuevo registro.
		
		@Author Marco A Nieto Plett
	*/
	function newRegistry(){
		handleDivFields([DIV_NAME_COP,DIV_NAME_RCV,DIV_NAME_MA],true,true);
		handleDivFields([DIV_NAME_GENERAL_DATA],false,true);
		
		document.getElementById("cveRevpagos").value=0;
		document.getElementById("saveData").disabled=false;
	}	
	
	/**
		Permite generar las sumatorias de los los campos
		contenidos en un DIV. Dejando la mascara de dinero.
		
		@Param divContainerName: ID del DIV que contiene los campos
		                         que se van a sumar.
		
		@Param totalField: ID del elemento en donde se va a desplegar
		                   el resultado de la operación aritmética SUMA.
						   
		@Author: Marco A Nieto Plett
	*/
	function sumDivPayment(obj, divContainerName,totalField){
		
		var total =0.0;
		var currentValue = 0.0;
		try{

			var arrayFields = document.getElementById(divContainerName).getElementsByTagName("*");
			
			document.getElementById(totalField).value="";
			
			for(var j=0;j<arrayFields.length;j++){

				if(arrayFields[j].type!= undefined && arrayFields[j].type=='text'){
				
					currentValue = arrayFields[j].value.replace(/[\,]+/gi,"");
					total = total + Number(currentValue);					
				}
			}

			moneyMask(obj,2);
			
			document.getElementById(totalField).value = total;
			
			moneyMask(document.getElementById(totalField),2);
			
		}catch(error){
			alert("::" + error+ "::");
		}
		
	}
	
	
	/**
		Permite validar las reglas de negocio de los movimientos 
		afiliatorios. Una vez mandado el registro a la capa de
		JAVA se validarán las mismas reglas con el totalizado (Global).
		
		@Author Marco A Nieto Plett
	*/
	function validateMovAfil(){

		var trabReg = Number(document.getElementById(TEXT_TRAB_REG).value);
		var trabAltas = Number(document.getElementById(TEXT_TRAB_ALTAS).value);
		var trabBajas = Number(document.getElementById(TEXT_TRAB_BAJAS).value) ;
		var modSalario = Number(document.getElementById(TEXT_TRAB_MOD_SALARIO).value);
		var maPeriodo = Number(document.getElementById(SELECT_PERIODO_NAME_MA).value);
		
		if(maPeriodo<=0){
			alert("El periodo en movimientos afiliatorios es obligatorio");
			return false;
		}else if(trabReg <=0 && trabAltas <=0 && trabBajas <=0 && modSalario <=0){
			alert("Debe de capturar movimientos afiliatorios");
			return false;
		}else if(trabBajas>trabAltas){
			alert("Los trabajadores dados de baja no pueden ser mayor que los dados de alta");
			return false;
		}else if(trabReg >0 || trabAltas >0 ||  modSalario > 0 ){

			if(trabReg <= 0 && (trabAltas >0 ||  modSalario > 0)){
				alert("Los trabajadores regularizados son obligatorios");
				return false;
			}else{

				var totalTrabajadores = trabAltas + modSalario;
			
				if(totalTrabajadores>trabReg){
					alert("La suma de trabajadores dados de altas + trabajadores modificados \nno puede ser mayor a trabajadores regularizados");
					return false;
				}
			}
		}
		
		if(trabBajas >0 ){
			var permitirBajas = (Number(trabReg) + Number(totalLabel_TRAB_REG))-trabBajas;
			
			if(permitirBajas<0){
				alert("Las bajas no puede ser mayor al dato de altas de trabajadores");
				return false;
			}
		}
		
		return true;
	}
	
	/**
		Permite validar los datos generales.
		
		@Author Marco A Nieto Plett
	*/	
	function validateGeneralData(){
		
		var regitroPatronal = document.getElementById(SELECT_REGISTRO_PATRONAL).value;
		var tpMovimiento = document.getElementById(SELECT_TP_MOVIMIENTO).value;
		var sua = document.getElementById(TEXT_FOLIO_SUA).value;
		var oIngreso = document.getElementById(TEXT_ORDEN_INGRESO).value;
		var numCredito = document.getElementById(TEXT_NUM_CREDTO).value;
		var fechaPago = document.getElementById(TEXT_FECHA_PAGO).value;
		var tpDocto = document.getElementById(TEXT_TP_DOCTO).value;
	

		if(regitroPatronal<=0){alert("Favor de seleccionar un registro patronal");return false;}
		else if(tpMovimiento<=0){alert("Favor de seleccionar un el tipo de movimiento de pago");return false;}
		else if(tpMovimiento!=MV_MA_TYPE && sua=="" && oIngreso == ""){alert("La referencia de pago (SUA u Orden de Ingreso) es obligatoria");return false;}
		else if(numCredito==""){alert("El n\u00famero de cr\u00e9dito es obligatorio");return false;}
		else if(numCredito!="" && numCredito.length!=9){alert("La longitud del n\u00famero de cr\u00e9dito es 9 posiciones obligatorias");return false;}
		else if( fechaPago==""){alert("La fecha de pago es obligatoria");return false;}
		else if( tpMovimiento!=MV_MA_TYPE && tpDocto<=0){alert("El tipo de documento es obligatorio");return false;}

		return true;
		
	
	}
	
	/**
		Valida los campos obligatorios de la sección C.O.P
		
		@Author Marco A Nieto Plett
	
	*/
	function validateCOP(){
		
		var copPeriodo = document.getElementById(SELECT_PERIODO_NAME_COP).value;
		
		var sp_cop = document.getElementById(TEXT_SP_COP).value;
		var act_cop = document.getElementById(TEXT_ACT_COP).value;
		var rec_cop = document.getElementById(TEXT_REC_COP).value;
		var multas_cop = document.getElementById(TEXT_MULTAS_COP).value;
			
		
		if(copPeriodo<=0){
			alert("El periodo de C.O.P es obligatorio");
			return false;
		}else if(sp_cop=="" || sp_cop <=0){
			alert("La suerte principal de C.O.P es obligatoria");
			return false;
		}

		
		if(document.getElementById(IND_TIPO_PAGO).value == TIPO_PAGO_RECEPCION){
			if(sp_cop>MONTO_MAXIMO || act_cop>MONTO_MAXIMO  || rec_cop>MONTO_MAXIMO  || multas_cop>MONTO_MAXIMO ){
				alert("El monto limite en los pagos C.O.P por rubro es de "+ MONTO_MAXIMO);
				return false;
			}
		}
		
		
		 
		return true;
	}
	
	/**
		Valida los campos obligatorios de la sección R.C.V
		
		@Author Marco A Nieto Plett
	
	*/
	function validateRCV(){
		
		var rcvPeriodo = document.getElementById(SELECT_PERIODO_NAME_RCV).value;
		
		var sp_rcv = document.getElementById(TEXT_SP_RCV).value;
		var act_rcv = document.getElementById(TEXT_ACT_RCV).value;
		var rec_rcv = document.getElementById(TEXT_REC_RCV).value;
		var multas_rcv = document.getElementById(TEXT_MULTAS_RCV).value;
		
		 if(rcvPeriodo<=0){
			alert("La el periodo de R.C.V es obligatorio");
			return false;
		}else if(sp_rcv=="" || sp_rcv <=0){
			alert("La suerte principal de R.C.V es obligatoria");
			return false;
		}
		
				
		if(document.getElementById(IND_TIPO_PAGO).value == TIPO_PAGO_RECEPCION){
			 if(sp_rcv>MONTO_MAXIMO || act_rcv>MONTO_MAXIMO  || rec_rcv>MONTO_MAXIMO  || multas_rcv>MONTO_MAXIMO ){
					alert("El monto limite en los pagos RCV por rubro es de "+ MONTO_MAXIMO);
					return false;
				}
		}
		
		 
		return true;
	}
	
	/**
		Esta función valida la forma completa de
		la pantalla de pagos.
	*/

	function validateCompleteForm(){
	
		if(validateGeneralData()){
			if(MV_SELECTED == MV_COP_TYPE || MV_SELECTED == MV_COPRCV_TYPE
					|| MV_SELECTED == MV_COPMA_TYPE || MV_SELECTED == MV_COPRCVMA_TYPE){
					
					if(!validateCOP()) return false;
			}
			
			if(MV_SELECTED == MV_RCV_TYPE || MV_SELECTED == MV_COPRCV_TYPE
					|| MV_SELECTED == MV_COPRCVMA_TYPE){
					
					if(!validateRCV()) return false;
			}
			
			if(MV_SELECTED == MV_MA_TYPE || MV_SELECTED == MV_COPMA_TYPE
					|| MV_SELECTED == MV_COPRCVMA_TYPE){
					
					if(!validateMovAfil()) return false;
			}
		}else{return false;}

		return true;
	}

	function transformDataToModel(divsContainerName,modelRead){

		if(divsContainerName==null || modelRead == null){
			alert("::Existe en transformDataTo por lo menos un valor NULL en los parámetros, valor inv\u00e1lido::");
			return;
		}
		
		try{
			for(var i=0;i<divsContainerName.length;i++){
				
				var arrayFields = document.getElementById(divsContainerName[i]).getElementsByTagName("*");
				
				for(var j=0;j<arrayFields.length;j++){
					
					if(arrayFields[j].type!= undefined && arrayFields[j].type=='text'){
					
						if(modelRead){
							arrayFields[j].value = arrayFields[j].value.replace(/[\,]+/gi,"");;
						}else{
							moneyMask(arrayFields[j],2);
						}
						 
					}
				}
			}
		}catch(error){
			alert("::" + error+ "::");
		}
		
	}

	/**
	 * Controla los eventos de salida.
	 * Retorna 1 si existen pagos
	 *         0 en cualquier otro caso
	 */
	function exitPagos(){
		try{
			if(totalLabel_SPCOP>0 || totalLabel_SPRCV>0){
				window.returnValue =1;
			}else{
				window.returnValue =0;
			}
			window.close();
		}catch(error){
			alert("Error al momento de cerrar componente de pagos:"+error);
		}
		
	}
	
	