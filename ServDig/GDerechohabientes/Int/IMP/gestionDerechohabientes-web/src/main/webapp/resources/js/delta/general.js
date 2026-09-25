// imports
$.getScript(context_path + "/resources/common/rechazoTramite.js");



/*!
 * general.js
 * 
 * Archivo javascript que debera contener las funciones que son
 * generales a los largo de todo el sistema o modulo.
 * 
 */

var idDialogoCerrarSesion = "#dgCerrarSesion";
var oDialogoCerrarSesion;
var idTimer;
var validaAviso;
var dialogoConfirmarMsg;



/*Seccion de codigo a ejecutar en cuanto el
 * DOM envie la señar de que esta listo para procesar 
 * de modificaciones al DOM
 */
$(document).ready(function(){
	
	//alert("hola mundo TIME FORMAT" + fechaSistema);
	//alert("hola mundo TIME FINAL" + fechaFinSession);
	//alert("hola mundo AVISO" + fechaAvisoSession);
	//alert("intervalo session " +intervaloValidacionSession );
	
	validaAviso =$("#validaAviso").val();
	 var idTimer = setInterval( validaSessionSSO, 10000);
	
	
	
	$loaderDiv = $('<div id="loader"><img src="' + context_path + '/resources/imagenes/loading.gif" /></div>');
	$esperePorFavorDiv = $('<div id="esperePF"><img src="' + context_path + '/resources/imagenes/loading.gif" /></div>');
	
	$esperePorFavorDiv.dialog({
		autoOpen : false,
		position: "center",
		stack: true,
		title:"Cargando...",
		modal: true,
		height: 150,
		width: 300,
		resizable: false
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	$loaderDiv.dialog({
		autoOpen : false,
		position: "center",
		stack: true,
		title:"Cargando...",
		modal: true,
		height: 150,
		width: 300,
		resizable: false
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
	
	//$loaderDiv.dialog("moveToTop");
	
	
	$loaderDiv.html('<center><div><img src="' + context_path + '/resources/imagenes/loading.gif" /><div><br><div> Espere un momento por favor.</div></center>');
	$esperePorFavorDiv.html('<center><div><img src="' + context_path + '/resources/imagenes/loading.gif" /><div><br><div> Espere un momento por favor.</div></center>');
	
	//$(document).ajaxStart(function(){$.blockUI()}).ajaxStop(function() {$.unblockUI()});
	
	$('#loader').hide().ajaxStart(function() {
    	
    	// -----------------------------------------------------
    	// Si no esta activo el BlockUI se muestra el dialogo
    	// -----------------------------------------------------
    	
    	if( ($(window).data("blockUI.isBlocked") == undefined) || ( $(window).data("blockUI.isBlocked") == 0)){
           $(this).show();
	    	$loaderDiv.html('<center><div><img src="' + context_path + '/resources/imagenes/loading.gif" /><div><br><div> Espere un momento por favor.</div></center>');
	        $loaderDiv.dialog('open');
    	}
    	
    }).ajaxStop(function() {
    	$loaderDiv.dialog('close');
    	$(this).hide();
    }); 

	
	/*Configuracion de la funcion de submit de la forma del
	 * registro de nuevo usuario*/
	
	$('#formRegistroUsuarioNuevo').submit(function(){
		fnOpenRegistroNuevoUsuario();
		return false;
	});
	
	/*Inicializacion del dialogo
	 * de confirmacion de cerrar sesion*/
	 oDialogoCerrarSesion = $( idDialogoCerrarSesion ).dialog({
	        autoOpen:false,
	        resizable: false,
	        height:150,
	        modal: true,
	        buttons: {
	            "Aceptar": function(data) {
	            	$.postJSON(context_path + "/limpiarSesion",{},function(data) {
	            		$("#formCerrarSesion").submit();
	            	}).error(
	            		function(data) {
	            			$("#formCerrarSesion").submit();
	            		}
	            	);
	            	
	            },
	            'Cancelar': function() {
	                $( this ).dialog( "close" );
	            }
	        }
	    });

	 
	 // ----------------------------------------------------------------------------------------
	 // Plugin para captura cambios en los atributs de Textarea, Input[text] y selects
	 // En caso de modificar el atributo disabled se agregara o removera una clase 'disabled'
	 // ----------------------------------------------------------------------------------------
	 
	 $("input[disabled], select[disabled], textarea[disabled], input[readonly], select[readonly], textarea[readonly]").addClass("disabled");
	 
	 if( $.fn.attrchange ){
		$("input[type=text], textarea, select").attrchange({
			trackValues: true, 
			callback: function (e) {
				
				if( e.attributeName == 'disabled' ){
					if( e.newValue != "disabled" )
						$(this).removeClass("disabled");
					else
						$(this).addClass("disabled");
				}
				
				
				if( e.attributeName == 'readonly' ){
					if( e.newValue != "readonly" )
						$(this).removeClass("disabled");
					else
						$(this).addClass("disabled");
				}
				
			}
		});
	 }
	 
	 
		dialogoConfirmarMsg = $( "#dialog-Aviso-Session" ).dialog({
			resizable: false,
			height:200,
			modal: true,
			autoOpen: false,
			buttons: {
				"ACEPTAR": function() {
					//parent.registroUsuarioDatosBasicosWizard.cerrar();
			 	}
			 }
		 });
});

/*Funcion para abrir el dialogo cerrar sesion*/
var fnAbrirDialogoCerrarSesion = function(){
	oDialogoCerrarSesion.dialog('open');
}

var fnAbrirMensajeEsperePorFavor = function() {
	$esperePorFavorDiv.dialog('open');
}

var fnCerrarMensajeEsperePorFavor = function() {
	$esperePorFavorDiv.dialog('close');
}

var fnInicializarBlockUi = function () {
	$('form:not(.formNotBlock)').submit(function(){
        $.blockUI();
	 });
}
/*Funcion que abre la ventana alterna 
 * para el registro de usuario*/
var fnOpenRegistroNuevoUsuario = function(){
	var oSendData = new Object();
	var sUrl ="/gestionIndividuo-web/persona/fisica/registro/1/";
	var oReturn = window.showModalDialog( sUrl  , oSendData, 
	   "dialogWidth:1000px;dialogHeight:900px;status=yes,toolbar=no,menubar=no,location=no");
	
	
	
}

/*
 *  Errores para las llamada ajax
*/
function error(texto) {

	$decision = $('<div></div');
	var mensaje= '<div class="ui-widget-content ui-corner-all">';
	mensaje+= '<div class="ui-state-error ui-corner-all" align="center">';
	mensaje+= '<div class="ui-icon ui-icon-alert"></div>';
	mensaje+= '<p class="ui-helper-reset ui-state-error-text">'+texto+'</p>';
	mensaje+= '</div>';
	mensaje+= '</div>';
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		autoSize : true,
		title : 'Error',
		modal : true,
		buttons : {
			"Aceptar" : function() {
				cierraDialogoError($(this));
			}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.html(mensaje);
	$decision.dialog('open');
}

function cierraDialogoError($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
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
		if ($(this).attr('disabled') != 'disabled') {
			var t = this.type, tag = this.tagName.toLowerCase();
			if (t == 'text' || t == 'password' || tag == 'textarea')
				this.value = '';
			else if (t == 'checkbox' || t == 'radio')
				this.checked = false;
			else if (tag == 'select') {
				this.selectedIndex = 0;
			}
		}
	});
};

function limpiarFormulario(idForm) {

	$(idForm).clearForm();
};

function set_size(elemento, maxSize) {

	var element = $("#" + elemento);

	setSizeCommon(element, maxSize);
}

function setSizeWithinIframe(document, maxSize, minSize) {
	var w = document.defaultView || document.parentWindow;
	var frames = w.parent.document.getElementsByTagName('iframe');

	for ( var i = frames.length; i-- > 0;) {
		var frame = frames[i];
		try {
			var d = frame.contentDocument || frame.contentWindow.document;
			if (d === document) {
				setSizeCommon(frame, maxSize, minSize);
				break;
			}
		} catch (e) {
			alert(e);
		}
	}
}

/* Funcion para ajustar en automatico el tamanio del iframe
 * de acuerdo a su contenido recibe el id del iframe.
 */
function setSizeCommon(elemento, maxSize, minSize) {
	var rootElement;
	var maxHeight = 0;
	var incrementoHeight = 0;
	var maxSizeDefault = 900;
	var maxSizeToApply = 0;

	if ($.browser.msie) {
		rootElement = "body";
		incrementoHeight = 60;
	} else {
		rootElement = "html";
		incrementoHeight = 20;
	}

	if ($(elemento).contents().find("html").height() != 0) {

		maxHeight = $(elemento).contents().find(rootElement).height();

		maxHeight += incrementoHeight;

		if (maxSize != undefined) {
			if (maxSize > maxSizeDefault) {
				maxSizeToApply = maxSizeDefault;
			} else {
				maxSizeToApply = maxSize;
			}
		} else {
			maxSizeToApply = maxSizeDefault;
		}

		if (maxHeight > maxSizeToApply) {
			maxHeight = maxSizeToApply;
		}
		
		if (minSize != 'undefined' && minSize > maxHeight) {
			maxHeight = minSize;
		}

		$(elemento).css('height', maxHeight + 'px');
	}
}


/**
 * Limita el número de caracteres en el text area
 * 
 * @param idTextArea
 * @param opciones
 */
function asignartextAreaLimites(idTextArea,opciones){
	
	try{
		
	
		var noUnicodeCharsWhitelist = '!@#$%&*()+=\';,/{}":?.-_';
		var caracteresAceptadosAlfaNum = ';,.-/_:&()+=*@[]';
		var $textArea = $("#"+idTextArea);
		
		if( $textArea.length > 0){
			
			var settings = $.extend({
				events:["keyup","change","paste","input"],
				maxCharacters:255,
				status:true,
				statusClass:"status",
				statusText:"caracteres restantes.",
				notificationClass:"notification",
				showAlert:false,
				alertText:"Ha excedido el número máximo de caracteres permitidos.",
				slider:false,
				styles: {float:"right", display:"inline"}
			},opciones);
			
			$textArea.maxlength(settings);
			$textArea.alphanum({
			allow : caracteresAceptadosAlfaNum
			});
		}
		
	}catch(e){
		//console.log(e);
	}
	
}

/**
 * Limita el n�mero de caracteres en el text area
 * 
 * @param idTextArea
 * @param opciones
 */
function asignartextAreaLimitesProrrogas(idTextArea,opciones){
	
	try{
		
		var noUnicodeCharsWhitelist = '!@#$%&*()+=\';,/{}":?.-_';
		var caracteresAceptadosAlfaNum = ';,.-/_:&()+=*@[]';
		var $textArea = $("#"+idTextArea);
		
		if( $textArea.length > 0){
			
			var settings = $.extend({
				events:["keyup","change","paste","input"],
				maxCharacters:500,
				status:true,
				statusClass:"status",
				statusText:"caracteres restantes.",
				notificationClass:"notification",
				showAlert:false,
				alertText:"Ha excedido el n�mero m�ximo de caracteres permitidos.",
				slider:false,
				styles: {float:"right", display:"inline"}
			},opciones);
			
			$textArea.maxlength(settings);
			
			$textArea.alphanum({
				allow : caracteresAceptadosAlfaNum
			});
		
		}
		
	}catch(e){
		//console.log(e);
	}
	
}


/**
 * Evita que el boton back regrese a la pagina anterior y 
 * fastidie la solicitud
 * 
 */
function blockBackButton(){
	window.location.hash="acceder-uni";
	window.location.hash="acceder-uni";
	window.location.hash="acceder-uni";
	window.onhashchange=function(){window.location.hash="acceder-uni";};
}

function validaSessionSSO() {
	var fechaFinSession = new Date($("#fechaFinSession").val());
	var fechaAvisoSession =  new Date($("#fechaAvisoSession").val());
	var intervaloValidacionSession = $("#intervaloValidacionSession").val();
	
	//alert("llamada" + validaAviso);	
	if(fechaFinSession <= new Date() ){
		//clearTimeout(idTimer);//alert("Estimado usuario la sesión expirado el sistema lo redireccionara a la pagina de acceso ");
		mostrarMensajeFinSession("Te informamos que tu sesi\u00f3n expir\u00f3, deber\u00e1s ingresar tu usuario y contrase\u00f1a nuevamente");
		 validaAviso = "false";
		return;
	}
	
	if(fechaAvisoSession<= new Date() && validaAviso == "true"){
		//mostrarMensaje("Estimado usuario la sesi\u00f3n  concluir\u00e1 en 5 minutos, trasncurrido este tiempo el sistema lo redireccionar\u00e1 a la p\u00e1gina de acceso");
		 mostrarMensaje("Te informamos que tu sesi\u00f3n est\u00e1 por expirar, y deber\u00e1s ingresar tu usuario y contrase\u00f1a nuevamente");
		return;
	}
	
	
}

function marcaAvisoSession(){
	 $.postJSON(context_path + "/session/validar/session", null ,function(data) {
 	}).error(
 		function(data) {
 		}
 	);
	 validaAviso = "false";
	
}

function mostrarMensaje(mensaje) {
	dialogoConfirmarMsg.dialog("option", "buttons", [ 
		{
		text : 'Revalidar Sesi\u00f3n',
		click : function() {
			
			refreshToken();
			$(this).dialog('close');
			}
		},
		{
			text : 'Aceptar',
			click : function() {
				
				marcaAvisoSession();
				$(this).dialog('close');
				}
		}
	]);
	
	$("#mensajeDialogoSession").html(mensaje);
	dialogoConfirmarMsg.dialog('open');
}

function mostrarMensajeFinSession(mensaje) {
	dialogoConfirmarMsg.dialog("option", "buttons", [ {
		text : 'Continuar',
		click : function() {
			$.postJSON(context_path + "/limpiarSesion",{},function(data) {
	    		$("#formCerrarSesion").submit();
	    	}).error(
	    		function(data) {
	    			$("#formCerrarSesion").submit();
	    		}
	    	);
			$(this).dialog('close');
		}
	}]);
	
	$("#mensajeDialogoSession").html(mensaje);
	dialogoConfirmarMsg.dialog('open');
}


function refreshToken(){
	
	tokenid = getCookie("iPlanetDirectoryPro");
	url = '/openam_10.0.0/identity/isTokenValid';
	refresh = true;
	params = {'tokenid':tokenid , 'refresh':refresh};
	tSesion = 40;
	tNotificacion = 10;
	
	if (url !== null) {
	    $.ajax({
		    data : params,
		    type : 'GET',
		    url : 'http://ssoimssdigd.imss.gob.mx:8001' + url,
		    contentType: 'application/json; charset=utf-8',
		    crossDomain: true,
		    dataType: 'jsonp'
	    });
    }
	
	var fechaFinSession = new Date($("#fechaFinSession").val());
	var fechaAvisoSession =  new Date($("#fechaAvisoSession").val());
	var intervaloValidacionSession = $("#intervaloValidacionSession").val();
	
	fechaActual = new Date();
	fechaAvisoSesion = new Date(fechaActual.getTime() + (tSesion - tNotificacion)*60000);
	
	fechaFinSesion = new Date(fechaActual.getTime() + tSesion*60000);
	validaAviso = "true";
	$("#fechaAvisoSession").val(fechaAvisoSesion);
	$("#fechaFinSession").val(fechaFinSesion);
	$("#validaAviso").val(validaAviso);
	 $.postJSON(context_path + "/session/revalidar/session", null ,function(data) {
	 	}).error(
	 		function(data) {
	 		}
	 	);
	
	//alert("sali de la llamada");
}


function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
