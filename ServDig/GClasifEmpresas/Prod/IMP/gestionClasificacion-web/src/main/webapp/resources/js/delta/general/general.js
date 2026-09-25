var pDialogHeigthProductos = 550;
var pDialogWidthProductos = 550;



function fnSetValues(){
	  var iHeight = '800';
	  var iWidth = '900';
	   var sFeatures="dialogwidth: " + iHeight + "px;" ;
	   return sFeatures;
	}

	function fnOpen(){
	   var sFeatures=fnSetValues();
	   
	   var oSendData = new Object();
//	   oSendData.numFraccion = 123;
//	   oSendData.desFraccion = 'Venta de Azufre';
	   oSendData.origen = 'delta';
//	   oSendData.tipoBusqueda = 2;
	   oSendData.tipoBusqueda = 3;
	   oSendData.showAnterior = true;//Esta l�nea no la ten�a el Clasificador Original 
	   oSendData.showClase=false;
	   oSendData.session = sessionId;
	   
	   
	   var oReturnClasificacion =  window.showModalDialog("/clasificador?JSESSIONID="+sessionId , oSendData, 
			   "dialogWidth:1024px;dialogHeight:500px;status=yes,toolbar=no,menubar=no,location=no");
			   
			   
		if(oReturnClasificacion != null){
			if(oReturnClasificacion.id != null){
						   alert(oReturnClasificacion.id.cveFraccion );			
			   			   alert(oReturnClasificacion.id.cveGrupo );
			  			   alert(oReturnClasificacion.id.cveDivision );
			}
		}		   
			   
		
			   
	}
	
	
	
	
	

	
	
	



	function fnSetValues(){
		  var iHeight = '800';
		  var iWidth = '900';
		   var sFeatures="dialogwidth: " + iHeight + "px;" ;
		   return sFeatures;	
			var fnValidaExisteSolicitudesEnProceso = function(){
				
				
				
			}
		}
/*
	function seleccionClasificador(msgAuxiliar) {
		$("#msgAuxiliar")[0].style.display='block';
		$("#msgAuxiliar")[0].innerHTML=msgAuxiliar;
		$("#msgAuxiliar").delay(3500).fadeOut("slow");
	}
	*/
	
/**
 * Funcion para invocar al clasificador. 
 */
var sIdDialogEliminarProductos='#dgEliminarProductos';

function fnOpenClasificador() {
	resetPrimaSugerida();
	console.log("::: fnOpenClasificador");
//	
//	if(navigator.userAgent.toLowerCase().indexOf('trident') > -1) {
//		console.log('Seleccionando clasificador IE');
//		seleccionarClasificadorIE();
//	} else {
//		console.log('Seleccionando clasificador Moderno')
//		seleccionarClasificadorModerno();
//	}
	
	clasificador2();
}

var valorClasificador = null;

function terminaClasificadorIE () {

	$('#idDivision').val(valorClasificador.id.cveDivision);
	$('#idGrupo').val(valorClasificador.id.cveGrupo);
	$('#idFraccion').val(valorClasificador.id.cveFraccion);
	$('#clase').val(valorClasificador.cveClase);

	/* debemos de obtener los datos de la clasificacion */

	//var sSource = context_path + '/solicitud/rectificarMovimiento';
	var sSource = context_path + '/rectificacion/0/get/clasificacion';

	$.getJSON(sSource, {
		'cveIdDivision' : valorClasificador.id.cveDivision,
		'cveIdGrupo' : valorClasificador.id.cveGrupo,
		'cveIdFraccion' : valorClasificador.id.cveFraccion
        , 'clase': valorClasificador.cveClase
	}, function(data) {
	
		//clasificacion.getFraccion().getGrupo().getDivision().getId()
		$('#cveFraccionId').html(data.fraccion.id);
		$('#cveDivisionId').html(data.fraccion.grupo.division.id);
		$('#cveGrupoId').html(data.fraccion.grupo.id);
		$('#cveClaseId').html(data.fraccion.clase.clave);
		
		$('#cvedivisionDes').html(data.fraccion.grupo.division.numDivision + " - " + data.fraccion.grupo.division.descripcion);
		$('#cvegrupoDes').html(data.fraccion.grupo.division.numDivision + data.fraccion.grupo.numGrupo + " - " + data.fraccion.grupo.descripcion);
		$('#cvefraccionDes').html(data.fraccion.grupo.division.numDivision + data.fraccion.grupo.numGrupo + data.fraccion.numFraccion + " - " + data.fraccion.descripcion);
		$('#cveclaseDes').html(data.fraccion.clase.descripcion);
		$('#cveprimaDes').html(data.fraccion.primaSRT);
	
		compararPrimaSugerida(data.fraccion.clase.clave, data.fraccion.id);
		//alert("indRegPatClase:: " + data.indRegPatClase);
		
		//$("#msgAuxiliar").html(data.indRegPatClase);
		
		//if(document.getElementById('cveIdFraccionAct').value==document.getElementById('cveFraccionId').innerHTML){
			//seleccionClasificador('<b>LA CLASIFICACI&Oacute;N PROPUESTA ES LA MISMA QUE EL PATR&Oacute;N HA DECLARADO</b>');
		//}/*else if($("#msgAuxiliar")[0].innerHTML=="1"){
			//seleccionClasificador('<b>APLICA VALIDACI&Oacute;N DE REGLA RPC Y SE RECHAZAR&Aacute; ESTA CLASIFICACI&Oacute;N</b>');
		//}*/
	}).error(function(data) {
		fnProcesarErrores(data, sIdDialogEliminarProductos);
	});
}

function terminaClasificador() {
	//clasificacion.getFraccion().getGrupo().getDivision().getId()
	console.log('valorClasificador',valorClasificador);
	
	if(valorClasificador != undefined && valorClasificador != null) {
		$('#idDivision').val(valorClasificador.id.cveDivision);
		$('#idGrupo').val(valorClasificador.id.cveGrupo);
		$('#idFraccion').val(valorClasificador.id.cveFraccion);
		$('#clase').val(valorClasificador.cveClase);

		/* debemos de obtener los datos de la clasificacion */

		//var sSource = context_path + '/solicitud/rectificarMovimiento';
		var sSource = context_path + '/rectificacion/0/get/clasificacion';

		$.getJSON(sSource, {
			'cveIdDivision' : valorClasificador.id.cveDivision,
			'cveIdGrupo' : valorClasificador.id.cveGrupo,
			'cveIdFraccion' : valorClasificador.id.cveFraccion
            , 'clase': valorClasificador.cveClase
		}, function(data) {
		
			//clasificacion.getFraccion().getGrupo().getDivision().getId()
			$('#cveFraccionId').html(data.fraccion.id);
			$('#cveDivisionId').html(data.fraccion.grupo.division.id);
			$('#cveGrupoId').html(data.fraccion.grupo.id);
			$('#cveClaseId').html(data.fraccion.clase.clave);
			
			$('#cvedivisionDes').html(data.fraccion.grupo.division.numDivision + " - " + data.fraccion.grupo.division.descripcion);
			$('#cvegrupoDes').html(data.fraccion.grupo.division.numDivision + data.fraccion.grupo.numGrupo + " - " + data.fraccion.grupo.descripcion);
			$('#cvefraccionDes').html(data.fraccion.grupo.division.numDivision + data.fraccion.grupo.numGrupo + data.fraccion.numFraccion + " - " + data.fraccion.descripcion);
			$('#cveclaseDes').html(data.fraccion.clase.descripcion);
			$('#cveprimaDes').html(data.fraccion.primaSRT);
		
			compararPrimaSugerida(data.fraccion.clase.clave, data.fraccion.id);
			//alert("indRegPatClase:: " + data.indRegPatClase);
			
			//$("#msgAuxiliar").html(data.indRegPatClase);
			
			//if(document.getElementById('cveIdFraccionAct').value==document.getElementById('cveFraccionId').innerHTML){
				//seleccionClasificador('<b>LA CLASIFICACI&Oacute;N PROPUESTA ES LA MISMA QUE EL PATR&Oacute;N HA DECLARADO</b>');
			//}/*else if($("#msgAuxiliar")[0].innerHTML=="1"){
				//seleccionClasificador('<b>APLICA VALIDACI&Oacute;N DE REGLA RPC Y SE RECHAZAR&Aacute; ESTA CLASIFICACI&Oacute;N</b>');
			//}*/
		}).error(function(data) {
			fnProcesarErrores(data, sIdDialogEliminarProductos);
		});
	}
	
	
}

		function resetPrimaSugerida(){
			var trPrimaSugerida = document.getElementById('trPrimaSugerida');
			if(trPrimaSugerida != null) {
				trPrimaSugerida.style.display = 'none';
				document.getElementById("primaSugerida").value=null;	
			}
		}
		
		function checkCaracterEspecial(e) {
			
			var tecla = (document.all) ? e.keyCode : e.which;
			
			if(tecla == 8) {
				return true;
			}
			
			if(tecla == 10) {
				return true;
			}
			
			if(tecla == 32) {
				return true;
			}
		
			var regex = /[A-Z-Aa-z0-9Ññ,.;:_áÁéÉíÍóÓúÚüñÑ¡¿?!]/
			var teclaFinal = String.fromCharCode(tecla);
			return regex.test(teclaFinal)
		}
		
		function compararPrimaSugerida(claseSeleccionada, fraccionSeleccionada) {
//			var cveIdClaseActTxt = document.getElementById("cveIdClaseAct");
//			var cveIdFraccionActTxt = document.getElementById("cveIdFraccionAct");
//			if(cveIdClaseActTxt != null && cveIdFraccionActTxt != null ) {
//		
//				if (cveIdClaseActTxt.value == claseSeleccionada && cveIdFraccionActTxt.value == fraccionSeleccionada) {
					var trPrimaSugerida = document.getElementById('trPrimaSugerida');
				
					if(trPrimaSugerida != null) {
						
						trPrimaSugerida.style.display = 'block';
						
						
					}
					
//				}
//			}
		}
		

	/** Seccion de codigo a ejectuar cuando el DOM este listo * */
	




	////////////////// Seccion de codigo a ejecutar when ready
	
	$(function() {
		
		
		
		
	});
	
	
	
	
	
	
	

	/**
	 * FUncion para el procesamiento de errores cuando la peticion es asincrona
	 * @param data
	 * @param contenedor
	 */
	function fnProcesarErrores(data , contenedor){

		switch(data.status)
		{
		case 403:
			//La sesion expiro
			window.location.reload(true);
		  break;
		case 412:
			//Existen errores de captura
		  fnProcesarErroresDeCaptura(data, contenedor);
		  break;
		case 500:
			//Existen errores de negocio
			fnProcesarErrorNegocio(data, contenedor);
			break;
		  
		}
	}
	
	
	
	

/**
 * Funcion para mostrar los errores de captura 
 * (campos invalidos o vacios) cuando es una invocacion asincrona
 * y response con JSON.
 * @param data
 * @param contenedor
 */	
function fnProcesarErroresDeCaptura(data, contenedor){
	  var objErrores = jQuery.parseJSON(data.responseText);
	  var form = $(contenedor);
	 
	  for( index = 0 ; index < objErrores.erroresCaptura.length ; index ++){
		  var campo = objErrores.erroresCaptura[index].campo;
		  var mensaje = objErrores.erroresCaptura[index].mensaje;
		  var filtroCampoError = contenedor +' #'+campo +'Error';
		  fnShowError(   filtroCampoError , mensaje  );
	  }
	
}



/**
 * 
 * @param data
 * @param contenedor
 * @param campo
 */
function fnProcesarErrorNegocio (data, contenedor){
	
	var campo = 'errorNegocio';
	
	var objError = jQuery.parseJSON(data.responseText);
	var mensaje = objError.erroresNegocio;
	  var filtroCampoError = contenedor +' #'+campo +'Label';
          
	  fnShowError(   filtroCampoError , mensaje  );
	
}

/**
 * 
 * @param mensaje
 * @param contenedor
 */
function fnSetErrorNegocio( mensaje, contenedor){
	var campo = 'errorNegocio';
	 var filtroCampoError = contenedor +' #'+campo +'Label';
	  fnShowError(   filtroCampoError , mensaje  );
	
}

var nClassShow = 'showElement';
var nClassHidden ='hiddenElement';		

/**
 * Funcion para mostrar el error
 * @param idCampoError
 * @param mensajeError
 */
function fnShowError(idCampoError , mensajeError){
	$(idCampoError).removeClass(nClassHidden);
	$(idCampoError).addClass(nClassShow);
	$(idCampoError).text(mensajeError);
}
	
/**
 * Funcion para ocultar los errores 
 * de un contenedor.
 * span con clase error
 * 
 * @param contenedor
 */
function fnHideErrores(contenedor){
	
	var filtroErrores;
	if(contenedor == ""){
		 filtroErrores ='span.error';
	}else{
		filtroErrores = contenedor + ' span.error';
	}
	
	
	$(filtroErrores).each(function(index) {
	    
		$(this).removeClass(nClassShow);
		$(this).addClass(nClassHidden);
		$(this).text();
	});
}
	

/**
 * Funcion para mostrar el error
 * @param idCampoError
 * @param mensajeError
 */
function fnShowElement(element){
	
	$(element).removeClass(nClassHidden);
	$(element).addClass(nClassShow);
}
	
/**
 * Funcion para ocultar los errores 
 * de un contenedor.
 * span con clase error
 * 
 * @param contenedor
 */
function fnHideElement(element){
	    
		$(element).removeClass(nClassShow);
		$(element).addClass(nClassHidden);
}
	


/**
 * Funcion para limpiar los campos de busqueda
 */		 
	$.fn.clearForm = function() {
    return this.each(function() {
        $('input,select,textarea', this).clearFields();
    });
};


/**
 * Clears the selected form elements.
 */
$.fn.clearFields = $.fn.clearInputs = function() {
    return this.each(function() {
        var t = this.type, tag = this.tagName.toLowerCase();
        if (t == 'text' || t == 'password' || tag == 'textarea')
            this.value = '';
        else if (t == 'checkbox' || t == 'radio')
            this.checked = false;
        else if (tag == 'select'){
            this.selectedIndex = 0;
        }
    });
};



function limpiarFormulario(idForm){
   
    $(idForm).clearForm();
};

/* Get the rows which are currently selected */
function fnGetRowSelected( oTableLocal )
{
    var obRowSelected = null;
		
    $(oTableLocal.fnSettings().aoData).each(function (){
        if( $(this.nTr).hasClass('row_selected')){
            obRowSelected = this._aData;
        }
    });
		
    return obRowSelected;
		
};
	
	
	
/*FUncion que valida que se encuentre seleccionado al menos un radio*/
	
var fnValidaRegistroSeleccionado = function( oTableLocal ){
		
		
    var obRowSelected = fnGetRowSelected(oTableLocal);
		
    if(obRowSelected == null){
        return false;
    }else{
        return true;
    }
	
};


function fnShowErrorBusiness(){
	fnShowElement('#errorBusiness');
}



function focusOnError() {
	var errores = $('span.error.showElement+textarea, span.error.showElement+input, span.error.showElement+a');
	
	if ( $.isEmptyObject(errores) ) {
		return false;
	} else {
		if( $.isArray(errores)){
			var error = errores[0];
			$(error).focus();

		}else{

			$(errores).focus();
		}
		return true;
	}
}
