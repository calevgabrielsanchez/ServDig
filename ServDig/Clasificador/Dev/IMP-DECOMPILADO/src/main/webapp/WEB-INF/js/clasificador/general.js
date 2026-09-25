var timeOutOffMsgs = 3000;
var oFraccionSeleccionada;
var bEmbeded = false;
var $tabs ;
				var dataEmpty = {"sEcho":"undefined","iTotalRecords":0,"iTotalDisplayRecords":0,"sColumns":null,"aaData":[]};
				
				
var showClase = true;
var showAnterior = true;
var showVigente = true;

/**
 * Funcion para el manejo del Enter en los campos de busqueda
 * @param nombreDiv
 */


var sIdDivMsgAnt = '#wrapperMsgResultsAnterior';
var sIdMsgResultadosAnt = '#msgResultadosAnt';
var sIdMsgTotalAnt = '#msgTotal';
var nClassShow = 'showElement';
var nClassHidden = 'hiddenElement';		


function msgObject(){
	this.sDivWrapperTooMsg='';
	this.sDivWrapperMsg = '';
	this.sSpanDisplayResult =  '#msgResultados';
	this.sSpanTotalResult  = '#msgTotal';
	this.show  = function( intTotalResults, intDisplayResults ){
			//Validamos si el total de resultados no 
			// supera el maximo de resultados.
			if(intDisplayResults > 1000){
				//Mostramos el mensaje de error
				var ctx = $(this.sDivWrapperTooMsg);
				$(this.sSpanDisplayResult, ctx).text(intDisplayResults );
				$(this.sSpanTotalResult, ctx).text(intTotalResults);
				
				$(this.sDivWrapperTooMsg).removeClass(nClassHidden);
				$(this.sDivWrapperTooMsg).addClass(nClassShow);
				
				$(this.sDivWrapperMsg).removeClass(nClassShow);
				$(this.sDivWrapperMsg).addClass(nClassHidden);
			}else{
				//Mostramos el mensaje informativo
				var ctx = $(this.sDivWrapperMsg);
				$(this.sSpanDisplayResult, ctx).text(intDisplayResults );
				$(this.sSpanTotalResult, ctx).text(intTotalResults);
				/* Mostramos el div de mensajes*/
				// Quitamos la clase anterior
				$(this.sDivWrapperMsg).removeClass(nClassHidden);
				//Ponemos la clase nueva.
				$(this.sDivWrapperMsg).addClass(nClassShow);
				
				$(this.sDivWrapperTooMsg).removeClass(nClassShow);
				$(this.sDivWrapperTooMsg).addClass(nClassHidden);
				
			}
	},
	this.hidde = function(){
		/* Ocultamos los div de mensajes y errores*/
			
			$(this.sDivWrapperMsg).removeClass(nClassShow);
			$(this.sDivWrapperMsg).addClass(nClassHidden);
			
			$(this.sDivWrapperTooMsg).removeClass(nClassShow);
			$(this.sDivWrapperTooMsg).addClass(nClassHidden);
	}

}



function ctrlValidaDatos (){

/*Array con los campos que no han sido capturados.*/
this.camposInvalidos = new Array(),

/*Funcion para mostrar los errores en el dialogo*/
this.createErrors = function(){

$("ul#listaCamposInvalidos li").remove();

	for(var index = 0 ;index <  this.camposInvalidos.length ; index++ ){
 						var campo = this.camposInvalidos[index];
 							$("ul#listaCamposInvalidos").append("<li> <span>" + campo.nombre + "</span></li>");
 					}

},

/*Funcion para mostrar los errores en el dialogo*/
this.createErrorsAntAct = function(){

$("ul#listaCamposInvalidos li").remove();

	for(var index = 0 ;index <  this.camposInvalidos.length ; index++ ){
 						var campo = this.camposInvalidos[index];
 						if(index < this.camposInvalidos.length -1)
 							$("ul#listaCamposInvalidos").append("<li> <span>" + campo.nombre + " o</span></li>");
 						else
 							$("ul#listaCamposInvalidos").append("<li> <span>" + campo.nombre + "</span></li>");
 					}

},


/*Funcion que valida los campos requeridos*/
this.checkCapturaCompleta =    function ( arrayFieldsRequired , err){
	/*reiniciamos el array de campos invalidos.*/
	this.camposInvalidos = new Array();


var index = 0;

		var isValid = true;
		for(  index = 0 ; index < arrayFieldsRequired.length ; index++){
		
			var campo = arrayFieldsRequired[index];
			var tipo = campo.tipo;
			var value = $(campo.id).val();
			
			switch(tipo){
				case 'select' :
					if(/*value == 0 ||*/ value == -1){
						this.camposInvalidos.push(campo);
						isValid =  false;
					}
				case 'input':
					if(value == ''){
						this.camposInvalidos.push(campo);
						isValid =  false;
					}
			}//fin del switch
		}//fin del for
		
		
		if(!isValid && err == false){
			this.createErrors();
		}else if(!isValid && err == true){
			this.createErrorsAntAct();
		}
		
		return isValid;
		
	},// fin checkCapturaCompleta

	
this.checkFiltroCapturado = function (oCampo){
	var isValid = true;
	/*reiniciamos el array de campos invalidos.*/
	this.camposInvalidos = new Array();
	
	var campo =oCampo;
	var tipo = campo.tipo;
	var value = $(campo.id).val();
	
	
	switch(tipo){
	case 'select' :
		if(value == 0 || value == -1){
			this.camposInvalidos.push(campo);
			isValid =  false;
		}
	case 'input':
		if(value == ''){
			this.camposInvalidos.push(campo);
			isValid =  false;
		}
	}//fin del switch
	
	if(!isValid){
		this.createErrors();
	}
	
	return isValid;
	
}// fin checkFiltroCapturado

}// ctrlValidaDatos



handleEnter = function(e){
	
	var eTarget = e.target;
	
	var code = (e.keyCode ? e.keyCode : e.which);
	 if(code == 13) { //Enter keycode
			if(eTarget.id == 'txtPalabraAnt'){
				fraccionCtrl.buscarFraccionesByCatalogoAnterior();
			}else if(eTarget.id == 'txtNumAnt'){
				fraccionCtrl.buscarFraccionesByCatalogoAnterior();
			}
			
			if(eTarget.id == 'txtPalabraActual'){
				fraccionCtrl.buscarFraccionesByCatalogoActual();
			}else if(eTarget.id == 'txtNumActual'){
				fraccionCtrl.buscarFraccionesByCatalogoActual();
			}
			
			if(eTarget.id == 'txtPalabraEnAnt'){
				fraccionCtrl.buscarFraccionesInCatalogoAnterior();
			}else if(eTarget.id == 'txtNumEnAnt'){
				fraccionCtrl.buscarFraccionesInCatalogoAnterior();
			}
	 }
	
}


function hidediv(nombreDiv) {
				divResultados = document.getElementById(nombreDiv);
				divResultados.style.visibility = 'hidden';
		}

		function showdiv(nombreDiv) {
					divResultados = document.getElementById(nombreDiv);
					divResultados.style.visibility = 'visible';
		} 


		/**
		 * Funcion para el manejo del Evento de Seleccionar una
		 * pestania.
		 */
		handleShowTab = function(event , ui){
			
			if(ui.index == 0){
				/*Se debe de cargar el catalogo de divisiones*/
				//divisionCtrl.cargarComboDivisiones();
			}
			
		}


		var oTable;
		var dgFraccion;
		var dgConfirma;
		var dgFaltaDatos;
		var dgAyuda;



/**FUncion para seleccionar la pestana
indicada
**/
function selectTab(intIndex){

	$tabs.tabs('select' , intIndex);

}






/**
 * 
 * 
 * Iniciamos los UI de jQuery
 * 
 **/

		
		 $(document).ready(function() {

				// Tabs
    $tabs  = $('#tabs').tabs({
					show: handleShowTab
				});

				// Dialog		del detalle de la fraccion	
				dgFraccion = $('#dgFraccion').dialog({
					autoOpen: false,
					modal:true,
					resizable:false,
					width: 600,
					buttons: {
						"Aceptar": function() { 
						
							if(bEmbeded){
								/*Esto es en caso de ser invocado desde otra aplicacion*/
								 dgConfirma.dialog("open");
							}else{
								/*Si no es invocado desde otra aplicacion*/
								$(this).dialog("close"); 
							}
						
							
						}, 
						"Cancelar": function() { 
							$(this).dialog("close"); 
						} 
					}
					
					
					
					
					
				});
				
				// Dialog de confirm
				dgConfirma = $('#dgConfirma').dialog({
					autoOpen: false,
					modal:true,
					resizable:false,
					width: 600,
					buttons: {
						"Aceptar": function() { 
								/*Esto es en caso de ser invocado desde otra aplicacion*/
								window.returnValue  = oFraccionSeleccionada;
								$(this).dialog("close"); 
								window.close();
						}, 
						"Cancelar": function() { 
							$(this).dialog("close"); 
						} 
					}
				});
				
				
				dgFaltaDatos =$('#dgFaltaDatos').dialog({
					autoOpen: false,
					modal:true,
					resizable:false,
					width: 600,
					buttons: {
						"Aceptar": function() { 
								$(this).dialog("close"); 
						},
					}
				});
				
				dgAyuda =$('#dgAyuda').dialog({
					autoOpen: false,
					modal:true,
					resizable:false,
					width: 1050,
					buttons: {
						"Aceptar": function() { 
								$(this).dialog("close"); 
						},
					}
				});
				
		 /*Creamos la liga para el imprimir la fraccion seleccionada.*/
				
				$('#imprimir').click(function(event){
					
					var url = context_path + "/fraccion/imprimir.do" + "?desFraccion=" + oFraccionSeleccionada.desFraccion;
					var settings = "";
					window.open(url, '_blank', "location=1,status=1,scrollbars=1, width=700,height=900" );
					
				});
				
		 
	/*Se valida los datos de entrada en caso de ser invocado desde otra aplicacion*/	 
	validaDialogArguments();
	
	

		 
		 });
		 
		 
		 
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
		 
function muestraAyuda(){ 	
	 dgAyuda.dialog("open");
}

/**
Seccion de codigo para identificar el origen de donde
se manda a llamar el clasificador
**/
function validaDialogArguments(){
// Validamos si se invoco desde otra aplicacion.
if(window.dialogArguments != null){
	//Inicializamos la bandera.
	bEmbeded = true;
	var oParams = window.dialogArguments ;
	
	var origen = oParams.origen;
	var cveFraccionActual = oParams.numFraccion;
	var desFraccionActual = oParams.desFraccion;
	var tipoBusqueda = oParams.tipoBusqueda;
	var session = oParams.session;
	
	showAnterior = oParams.showAnterior;
	showClase = oParams.showClase;

	
	
		if(tipoBusqueda > -1 && tipoBusqueda < 3){
			selectTab(tipoBusqueda);
			if(tipoBusqueda == 1){
				// busqueda por catalogo anterior
				$('#radioNumAnt').trigger('click');
				$('#txtNumAnt').val(cveFraccionActual);
			}
			if(tipoBusqueda == '2'){
				// busqueda por catalogo actual
				// seteamos el valor de la fraccion.
				$('#radioNumActual').trigger('click');
				$('#txtNumActual').val(cveFraccionActual);
			}
			if (tipoBusqueda == '3') {
				//busqueda en catalago anterior
				$('#radioNumEnAnt').trigger('click');
				$('#txtNumEnAnt').val(cveFraccionActual);
			}
		}
		//quitamos el ultmimo tab elimina la pestania de descarga
		$tabs.tabs('remove', 4);
		
		if(showAnterior == false ){
			//elimina catalogo anterior 
			$tabs.tabs('remove', 1);
		}
		
	}
}



/**
 * Funcion para resetear el valor del data table especificado
 *  del iDIsplaySTart a cero
 */


function resetDisplayStart(oDatable){
	
        /*Se debe de reiniciar el contador del DIsplaySTart a 0*/
        var oSettings = oDatable.fnSettings();
        if(oSettings != null){
        	oSettings._iDisplayStart = 0;
        }
        
        
}

		
		
/**
 * Funciones para determinar las secciones subrayadas de las
 * descripciones de las fracciones. 
 */
function getSentences(data) {
	
	var lista = data.aaData;
	var resultados = new Array();
	
	for (var i = 0; i<lista.length;i++) {
		var fullDescription = data.aaData[i].desActividad;
		var position = getPosition(fullDescription);
		resultados[i] = fullDescription.substring(0, position);
	}
	return resultados;
}


function getPosition(fullDescription) {
	var cadena = '';
	cadena = fullDescription;
	var defPosition=0;
	var marks = new Array(cadena.indexOf("."),cadena.indexOf(","),cadena.indexOf(":"),cadena.indexOf(";"));
	for (var i=0; i<marks.length; i++) {
		var posTmp = marks[i];
		if (posTmp > 0) {
			defPosition = posTmp;
		}
		for (var j=1; j<marks.length; j++) {
			var pos1 = marks[j];
			if (defPosition>pos1 && pos1 > 0) {
					defPosition = pos1;
			}
		}
	}
	return defPosition;
}
	

/*
 * Funcion para permitir solo la captura de los caracteres permitidos
 */
$(document).ready(function() {
	
//	$('input.numerico').keypress(function(event) {
//		checkDataType(event);
//    });
//	
//	$('input.numerico').keydown(function(event) {
//		checkDataType(event);
//    });
	
	
	$('input.numerico').ForceNumericOnly();
	
});

		
var checkDataType = function(event){
	
	var charAt = String.fromCharCode(event.which);
	
	if( event.which != KEY_DELETE){
		if(charAt != null && charAt != ""){
			
			
//			
			var characterReg = /\d{1}/;
	        if(!characterReg.test(charAt)){
	            return event.preventDefault();
	        }
		}
	}
	
	
}


var KEY_DELETE = 8;




jQuery.fn.ForceNumericOnly =
	function()
	{
	    return this.each(function()
	    {
	        $(this).keydown(function(e)
	        {
	            var key = e.charCode || e.keyCode || 0;
	            // allow backspace, tab, delete, arrows, numbers and keypad numbers ONLY
	            return (
	                key == 8 ||
	                key == 9 ||
	                key == 46 ||
	                (key >= 37 && key <= 40) ||
	                (key >= 48 && key <= 57) ||
	                (key >= 96 && key <= 105));
	        });
	    });
	};