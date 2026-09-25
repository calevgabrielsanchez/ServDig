/**
 * Abre pantalla de domicilios geogr�ficos
 * @param contextPath
 * @param action
 */
//var urlDomicilios = "/scriptDomicilio";
//
//
//
//function openWindowregistraDomicilioInegi(contextPath,action){
//	
//	MM_openBrWindowModal(contextPath+"/"+action,'status:false;dialogWidth:1000px;dialogHeight:750px');
//	
//}

/**
 * Abre la pantalla de pagos seguimiento fase II
 * @param contextPath
 * @param action
 */
function openWindowPagosSeguimientoFII(contextPath,action){
	
	return MM_openBrWindowModal(contextPath+"/"+action,'status:false;dialogWidth:1000px;dialogHeight:750px');

}

/**
 * Abre pantalla para descarga de archivos
 * @param contextPath
 * @param action
 * 
 */
function openDownloadFileWindow(contextPath,action){
	
	MM_openBrWindowModal(contextPath+"/"+action,'status:yes;resizable=yes;dialogWidth:200px;dialogHeight:200px');
		
}
	
/**
 * Funci�n gen�rica para abrir ventanas no modales
 * @param theURL
 * @param winName
 * @param features
 */
function MM_openBrWindow(theURL,winName,features) { 
  window.open(theURL,winName,features);
}

/**
 * Funci�n gen�rica para abrir ventanas modales
 * @param theURL
 * @param features
 */
function MM_openBrWindowModal(theURL,features) { //v2.0
	return window.showModalDialog(theURL,window,features);

}


function creadorTest(){
	var test="\u0040\u0055\u0054\u004F\u0052\u003A\u004A\u004F\u0052\u0047\u0045\u0020\u0056\u0045\u004E\u0054\u0055\u0052\u0041\u0020\u0048\u0045\u0052\u004E\u0041\u004E\u0044\u0045\u005A\u0020\u0041\u004C\u004D\u0041\u005A\u0041\u004E";
	return test;
}
/**
 * Valida las fechas [A<B]
 * En caso de que la fecha inicial sea mayor a la final
 * retornar� falso.
 * 
 * @param fecIni
 * @param fecFin
 * @param separador relativo a los separadores de la fecha
 * @returns {Boolean} true si la fecha es fecIni<fecFin, false si fecIni>fecFin
 */
function comparaFechas(fecIni, fecFin,separador){
	
	var array_fechaIni = fecIni.split(separador); 
	var array_fechaFin = fecFin.split(separador); 
	
	var anioIni = parseInt(array_fechaIni[2],10);
	var anioFin = parseInt(array_fechaFin[2],10);
	
	var mesIni = parseInt(array_fechaIni[1],10);
	var mesFin = parseInt(array_fechaFin[1],10);
	
	var diaIni = parseInt(array_fechaIni[0],10);
	var diaFin = parseInt(array_fechaFin[0],10);
	
	
	if(anioIni > anioFin){
		return false;
	}else {
		if(anioFin == anioIni){
			if(mesIni > mesFin){
				return false;
			}else{
				if(mesIni == mesFin){
					
					if(diaIni > diaFin){
						
						return false;
					}else{
						if(diaIni <= diaFin){
							
							return true;
						}
					}
				}else{
					if(mesIni < mesFin){
						return true;
					}
			  }
			}
		}else{
			if(anioIni < anioFin){
				return true;
			}
		}
	}
	
}

/**
 * 
 * @param text
 * @param busca
 * @param reemplaza
 * @returns
 */

function replaceAll( text, busca, reemplaza ){ 

	if(text==undefined){
			return "";
	}
   while (text.toString().indexOf(busca) != -1) 
       text = text.toString().replace(busca,reemplaza); 

   return text; 

 } 


/**
 * Funcion para el procesamiento de errores cuando la peticion es asincrona
 * @param data
 */
function validarSesionExpirada(data) {
	switch (data.status) {
	case 403:
		//La sesion expiro
		alert('Su sesi\u00F3n ha expirado.');
		this.focus(); self.opener = this; self.close();
		break;
	}
}

/**
 * Funcion para resetear el valor del data table especificado
 *  del iDIsplaySTart a cero
 */
function resetDisplayStart(oDatable){
        /*Se debe de reiniciar el contador del DIsplaySTart a 0*/
        var oSettings = oDatable.fnSettings();
        oSettings._iDisplayStart = 0;
}

function setAndSubmitNewWindow(param){
	//set(target);
	//Borro los datos del login
	//document.getElementById('user').value = '';
	//document.getElementById('pass').value = '';
	
	//var myBars = 'directories=no,location=no,menubar=no,status=no';
    //myBars += ',titlebar=no,toolbar=no,screenX=0,screenY=0';
    //var myOptions = 'hotkeys=no,scrollbars=yes,width=1024,height=768,resizeable=yes';
    //var myFeatures = myBars + ',' + myOptions;
    //newWindow = window.open('?', 'Detailswindow', myFeatures);
    
    var browser=navigator.appName;
    
    var width=1152;  
    var height=768;  
    var from_top=0;  
    var from_left=0;  
    var toolbar='no';  
    var location='no';  
    var directories='no';  
    var status='yes';  
    var menubar='no';  
    var scrollbars='yes';  
    var resizable='yes';  
    var atts='width='+width+'show,height='+height+',top='+from_top+',screenY=';  
    atts+= from_top+',left='+from_left+',screenX='+from_left+',toolbar='+toolbar;  
    atts+=',location='+location+',directories='+directories+',status='+status;  
    atts+=',menubar='+menubar+',scrollbars='+scrollbars+',resizable='+resizable;
        
//	var newWindow; 	
// 	newWindow = window.open('', 'DetailswindowSICONet', atts);
 	
// 	document.forms[0].target='DetailswindowSICONet';
    document.forms[0].target = '_self';
 	document.forms[0].method.value=param;
 	document.forms[0].submit();

	//if (browser == "Microsoft Internet Explorer") {
 	//	this.focus();  
 	//	self.opener = this;  
 	//	self.close();
 	//	window.open('','_self','');
	//	window.close();
 	//} else {
 	//	window.close();
	//}
 	//window.close();
 	
 	//var browserName = navigator.appName;
    //var browserVer = parseInt(navigator.appVersion);
//    var ie7 = (document.all && !window.opera && window.XMLHttpRequest) ? true : false;  
//    self.focus();    
//    if (ie7 || browser == "Netscape") {     
//          //This method is required to close a window without any prompt for IE7 
//          window.opener = null;
//          window.open('','_self','');
//          window.close();
//         
//    } else {
//          //This method is required to close a window without any prompt for IE6
//          this.focus();//          self.opener = this;
//          self.close();
//    }
}

/*
 * Funci�n gen�rica para invocar a JSON y 
 * procesar un formulario
 */
function procesaFormulario(funcionValidacion){
	
	var objForma = $("form#"+FORMA_ACTUAL).toObject({mode:'first'});
	var cvePromocionF=$("form#"+ FORMA_ACTUAL +" #cvePromocion").val();
	if (cvePromocionF!=null && cvePromocionF!=""){
		objForma.cvePromocion = $("form#"+ FORMA_ACTUAL +" #cvePromocion").val();
	}
	var formAction = $("form#"+FORMA_ACTUAL).attr('action');
	var flagFizca=false;
	if(FORMA_ACTUAL=='devFiscalizacionSeguimientoCorreccionForm;'){
		flagFizca=true;	
	}
	var respuesta = true;

	try{
		if(eval(funcionValidacion)){
			bloquear();
			$.postJSON(formAction, objForma, function(data) {
				if(data == null){
					alert('Error General: Conculte a su administrador');
					respuesta = false;
				}else{
					respuesta = verifyCustomDataError(data);
					if(flagFizca){
						$("form#reactivacionSeguimientoCorreccionForm #cveRevDerivAFisca").val(data.cveRevDerivAFisca);
					}
					
					//limpiarFormulario("form#"+FORMA_ACTUAL);	
					desbloquear();
				}
			}).error(function(data){
				
				validarSesionExpirada(data);
				alert("error" + data);
				desbloquear();
				respuesta = false;
			});
			
		}else{
			
			desbloquear();
			respuesta = false;
			
		}
	}catch(error){
		respuesta = false;
		alert("Error al procesar la forma, verifique el c�dido JS:"+error);
		desbloquear();		
	}
	
	
	return respuesta;
}



/**
 * Permite validar si una solicitud tiene derecho a seguir
 * utilizando ABC o solo consultas. En caso de que no 
 * se ocultaran los botones de acci�n.
 * 
 * @param data Objeto JSON
 * @param divBtn contenedor de los botones
 * @returns {Boolean}
 */
function validaEstadoSolicitudCorreccion(data,divBtn){
	
	var SOLICITUD_ACEPTADA_NO_PRESENTADA = 2;
	
	if(data!=null && data.aaData!=null && 
			data.aaData.length>0 && 
			data.aaData[0].cveStatusCorreccion!=SOLICITUD_ACEPTADA_NO_PRESENTADA){
		$("#"+divBtn).hide();
		return false;
	}else{
		$("#"+divBtn).show();
		return true;
	}
}

/**
 * Permite ocultar y mostrar divs
 * 
 * @param action show | hide
 * @param divBtn contenedor de los botones
 * 
 */
function divControl(action,div){
	
	if(action!=null && action!="" && 
				div!=null && div!="" ){
		
		if(action=="show"){
			$("#"+div).show();
		}else if(action=="hide"){
			$("#"+div).hide();

		}else{
			alert("Revisar los par�metros ingresados a la funcu�n, existen errores");
		}
	}
}

/**
 * Verifica si existe un error en el proceso,
 * este es controlado por el programador y debe
 * de utlizar el atributo comun (en todos los models)
 * el cual extiende del abstractModel error.
 * 
 * En caso de que este no exista mandar� alerta
 * de �xito.
 * 
 * @param data JSON OBJ
 */
function verifyCustomDataError(data){
	var regresa = true;
	
	if(data.error!=undefined && data!=null && data!="null" && data.error!=""){
		alert("Error:"+data.error);
		regresa = false;
	}else if(data.error!=undefined && data!=null && data!="null"){
		
		if(data.exito!=undefined && data!=null && data!="null" && data.exito!=""){
			alert(data.exito);
			regresa =  true;
		}else{
			alert("Operaci\u00F3n Exitosa");
			regresa = true;
		}
		
	}else if(data.error==undefined || data==null || data=="null"){
		
	}else{
		alert("Existe un error en la operaci�n, verifique validaciones");
		regresa = false;
	}
	return regresa;
}

function validaTipoCertificado(cert){
	if (cert.indexOf(".cer") != -1) {
		  return "SAT";
		} else {
		  return "IDSE";
		}
}

function salirAplicacion(contextPath){
	var formulario =null;
	formulario = document.createElement("form");
	formulario.action = contextPath+"/logout.do?tgt=salirAplicacion";
	formulario.method = "post";
	this.document.body.appendChild(formulario);
	formulario.submit();

    var $activeDialogs = $(".ui-dialog:visible").find('.ui-dialog-content');
    $activeDialogs.dialog('close');
    document.location.href = contextPath+'/j_spring_security_logout';
}

function backMenuPrincipal(contextPath){
	
	
	var formulario =null;
	formulario = document.createElement("form");
	formulario.action = contextPath+"/login/redireccionaInicio.do";
	formulario.method = "post";
	this.document.body.appendChild(formulario);
	formulario.submit();
}


function goToWelcomePage(contextPath){
	
	var formulario =null;
	formulario = document.createElement("form");
	formulario.action = contextPath+'/btnSalirHome.do';
	formulario.method = "get";
	this.document.body.appendChild(formulario);
	formulario.submit();
	
	
}
/*
 *  �Browser name: BrowserDetect.browser
 *  �Browser version: BrowserDetect.version
 *  �OS name: BrowserDetect.OS
 */

var BrowserDetect = {
		init: function () {
			this.browser = this.searchString(this.dataBrowser) || "An unknown browser";
			this.version = this.searchVersion(navigator.userAgent)
				|| this.searchVersion(navigator.appVersion)
				|| "an unknown version";
			this.OS = this.searchString(this.dataOS) || "an unknown OS";
		},
		searchString: function (data) {
			for (var i=0;i<data.length;i++)	{
				var dataString = data[i].string;
				var dataProp = data[i].prop;
				this.versionSearchString = data[i].versionSearch || data[i].identity;
				if (dataString) {
					if (dataString.indexOf(data[i].subString) != -1)
						return data[i].identity;
				}
				else if (dataProp)
					return data[i].identity;
			}
		},
		searchVersion: function (dataString) {
			var index = dataString.indexOf(this.versionSearchString);
			if (index == -1) return;
			return parseFloat(dataString.substring(index+this.versionSearchString.length+1));
		},
		dataBrowser: [
			{
				string: navigator.userAgent,
				subString: "Chrome",
				identity: "Chrome"
			},
			{ 	string: navigator.userAgent,
				subString: "OmniWeb",
				versionSearch: "OmniWeb/",
				identity: "OmniWeb"
			},
			{
				string: navigator.vendor,
				subString: "Apple",
				identity: "Safari",
				versionSearch: "Version"
			},
			{
				prop: window.opera,
				identity: "Opera",
				versionSearch: "Version"
			},
			{
				string: navigator.vendor,
				subString: "iCab",
				identity: "iCab"
			},
			{
				string: navigator.vendor,
				subString: "KDE",
				identity: "Konqueror"
			},
			{
				string: navigator.userAgent,
				subString: "Firefox",
				identity: "Firefox"
			},
			{
				string: navigator.vendor,
				subString: "Camino",
				identity: "Camino"
			},
			{		// for newer Netscapes (6+)
				string: navigator.userAgent,
				subString: "Netscape",
				identity: "Netscape"
			},
			{
				string: navigator.userAgent,
				subString: "MSIE",
				identity: "Explorer",
				versionSearch: "MSIE"
			},
			{
				string: navigator.userAgent,
				subString: "Gecko",
				identity: "Mozilla",
				versionSearch: "rv"
			},
			{ 		// for older Netscapes (4-)
				string: navigator.userAgent,
				subString: "Mozilla",
				identity: "Netscape",
				versionSearch: "Mozilla"
			}
		],
		dataOS : [
			{
				string: navigator.platform,
				subString: "Win",
				identity: "Windows"
			},
			{
				string: navigator.platform,
				subString: "Mac",
				identity: "Mac"
			},
			{
				   string: navigator.userAgent,
				   subString: "iPhone",
				   identity: "iPhone/iPod"
		    },
			{
				string: navigator.platform,
				subString: "Linux",
				identity: "Linux"
			}
		]

	};
	BrowserDetect.init();

	
	function parseIntComas(string,radix){
		var numero = replaceAll(string,",","");
		numero = parseInt(numero, radix);
		return numero;
	}
	
	function parseFloatComas(string,radix){
		var numero = replaceAll(string,",","");
		numero = parseFloat(numero, radix);
		return numero;
	}
	
	function replaceAll( text, busca, reemplaza){
		if(text==undefined){
			return "";
		  }
		  while (text.toString().indexOf(busca) != -1)
		      text = text.toString().replace(busca,reemplaza);
		  return text;
		}
	
	
	
	/**
	 * Funci�n para validar que un flotante no salga del rango permitido
	 * @author Sa�l Rosales Piedragil
	 */
	function validaRangoDouble(campo, minimo, maximo){
		//alert("campo.value.length : " + campo.value.length);
		var longitud =campo.value.length;
		var min=parseInt(minimo);
		var max=parseInt(maximo);
		var aValidar =parseInt(campo.value);
		//alert("aValidar<min " + aValidar<min);
		if(aValidar<min){
			
			campo.value="0";
			return;
		}
		//alert("aValidar>max " + aValidar>max);
		if(aValidar>max){
			if(jsContains(campo.value,".")){
				campo.value=campo.value.substring(0,campo.value.length-1);
				//alert("campo.value if: " + campo.value);
			}else{
				campo.value=campo.value.substring(0,9);
				//alert("campo.value else: " + campo.value);
			}
			
			return;
		}
    }
	
	
	/**
	 * Funci�n para validar que un entero no salga del rango permitido
	 * @author Sa�l Rosales Piedragil
	 */
	function validaRangoEntero(campo, minimo, maximo){
		var longitud =campo.value.length;
		if(longitud>maximo.length){
			campo.value=campo.value.substring(0,maximo.length);
		}
		var min=parseInt(minimo);
		var max=parseInt(maximo);
		var aValidar =parseInt(campo.value);
		if(aValidar<min){
			campo.value="0";
			return;
		}
		if(aValidar>max){
			campo.value=campo.value.substring(0,campo.value.length-1);
			return;
		}
    }
	

	
	function jsContains(cadena, caracter) {
	    for (var i = 0; i < cadena.length; i++) {
	        if (cadena[i] === caracter) {
	            return true;
	        }
	    }
	    return false;
	}
	
	/**
	 * Funcion para bloquear elementos input (txt, button)
	 * contenidos en una forma de html 
	 * y quita el estilo capturable.  
	 *  
	 * @author Gerardo Salazar Vega
	 * @version 1.0.0
	 */
	function deshabilitaCamposXForma(formaBloquear){
		$('form#'+formaBloquear +' :input').prop('disabled','disabled');
		$('form#'+formaBloquear +' :input').removeClass("red");
	}

	
	function desHabilitaCampo(id){
		$(id).prop('disabled','disabled');	
		$(id).removeClass("red");
	}
	
	function habilitaCampo(id){		
		$(id).removeAttr("disabled");	
	}
	
	
	/**
	 * Funcion para desbloquear elementos input (txt, button)
	 * contenidos en una forma de html 
	 *  
	 * @author Gerardo Salazar Vega
	 * @version 1.0.0
	 */
	function habilitaCamposXForma(formaBloquear){
		$('form#'+formaBloquear +' :input').removeAttr("disabled");		
		
	}	
	
	
	function mayusculasTextField(componente){
		componente.value=(componente.value).toUpperCase();
	}

	/*
	 * Funcion para limpiar un campo
	 * Parametros:
	 * pNomforma: Nombre de la forma que contiene el campo
	 * pNomCampo: Id del campo a borrar
	 * 
	 * @author Gerardo Salazar
	 */
	function limpiaCampoForma(pNomforma, pNomCampo) {
		var objCampo = $("form#" + pNomforma +" #"+pNomCampo);
		
		if (objCampo.val() != undefined){
			objCampo.val(null);
		}
	}	
	
	
	
	/*
	 * Funcion para evaluar una serie de reglas y hablitar los tabs correspondientes
	 * 
	 * @author Jorge Ventura Hernandez Almazan
	 */
	function evaluaReglasTab(reglas){
		for (var k in reglas) {
		    if (reglas.hasOwnProperty(k)) {
		    	var flag=eval(k);
		    	if(flag){
		    		setTabHabilitado(reglas[k]);
		    	}
		    }
		}
	}
	
	/*
	 * Funcion para bloquear una lista de tabs, 
	 * param tabs  String que contiene los ids de los tabs seperados por comas
	 * @author Jorge Ventura Hernandez Almazan
	 */
	function bloquearTabs(tabs){
		var lista=tabs.split(",");
		for(var s=0;s<lista.length;s++){

			setTabDesHabilitado(lista[s]);
		}
	}
	
	function limpiaCampos(){
		$("form#deteccionFormRegistro #nomRazonsocial").val("");
		$("form#deteccionFormRegistro #txRfcpatron").val("");
		$("form#deteccionFormRegistro #txCurppatron").val("");
		$("form#deteccionFormRegistro #cveFkPatron").val("");
	}
	
	
	 String.prototype.removeAccents = function () {

	 	var __r = {

	 		'�':'A','�':'A','�':'A','�':'A','�':'A','�':'A','�':'E',
	 		'�':'E','�':'E','�':'E','�':'E',
	 		'�':'I','�':'I','�':'I',
	 		'�':'O','�':'O','�':'O','�':'O',
	 		'�':'U','�':'U','�':'U','�':'U',
	 		'�':'N'	};

	 	

	 	return this.replace(/[�����������������������]/gi, function(m){

	 		var ret = __r[m.toUpperCase()];

	 		if (m === m.toLowerCase())
	 			ret = ret.toLowerCase();
	 		return ret;
	 	});

	 };
	 
	 
	 

	 
	 
	 
function quitaAcentos(str){ 
	var cadena="";
	for (var i=0;i<str.length;i++){ 
	//Sustituye "� � � � �" 
	  
		switch(parseInt(str.charCodeAt(i))){
		case 225 :		  
			cadena+="a";
			break;
		case 233 :		  
			cadena+="e";
			break;
		case 237 :		  
			cadena+="i";
			break;
		case 243 :		  
			cadena+="o";
			break;
		case 250 :		  
			cadena+="u";
			break;
		case 193 :		  
			cadena+="A";
			break;
		case 201 :		  
			cadena+="E";
			break;
		case 205 :		  
			cadena+="I";
			break;
		case 211 :		  
			cadena+="O";
			break;
		case 218 :		  
			cadena+="U";
			break;
		default:
			cadena+=str.charAt(i);
			break;
		}
	
	} 
	return cadena; 
	} 


/*
 * Funcion para cargar automaticamente los valores de session, provenientes de internet
 * 
 * @author Jorge Ventura Hernandez Almazan
 */

function triggerPatronInternet(idCampoRegPat,idCampoNumFolio,idBotonTrigger,evento,validaFolio){
	
	//Version cambio para sete patronal
	if(idCampoRegPat!='' && $("#regPatronal").val()!='null' && $("#regPatronal").val()!=null && $("#regPatronal").val()!=undefined){
		$("#"+idCampoRegPat).val($("#regPatronal").val());
	}
	
	if(idCampoNumFolio!='' && $("#numeroFol").val()!='null' && $("#numeroFol").val()!=null && $("#numeroFol").val()!=undefined){
		$("#"+idCampoNumFolio).val($("#numeroFol").val());
	}
	
	if($("#numeroFol").val()!=undefined){
		if(evento==undefined){
			$("#"+idBotonTrigger).trigger("click");	
		}else{
			$("#"+idBotonTrigger).trigger(evento);
		}
	}
	
	

	if(validaFolio){		
		 $("#"+idBotonTrigger).mousedown(function(e){
			 if($("#"+idCampoNumFolio).val()!="" && $("#"+idCampoNumFolio).val()!=undefined){
				 var val= validaFolioInternet($("#"+idCampoNumFolio).val());
					if(!val){
						$("#"+idCampoNumFolio).val("");
						alert("El folio fue registrado desde internet");					
											
					}
					var brw = new Browser();
					if(brw.fullName=="Mozilla Firefox"){
						$("#"+idBotonTrigger).trigger("click");						
					}
			 }				
		
			 });
		  
		}
}



function validaFolioInternet(numeroFolio){
	
		var sVarSeg = '{"nuFolio":"'+numeroFolio+'"}';
		var clase = jQuery.parseJSON(sVarSeg);			
		var urlRecuperaMenu=$("#contextoWeb").val()+"/login/validarFolioCorreccionDelegacion.do"
		var res=false;
		$.postJSON_Sync(urlRecuperaMenu, clase, function(data) {				
			res=data;
		});	
	
		return res;
}



function Browser() {   
    // ---- public properties -----
    this.fullName = 'unknow'; // getName(false);
    this.name = 'unknow'; // getName(true);
    this.code = 'unknow'; // getCodeName(this.name);
    this.fullVersion = 'unknow'; // getVersion(this.name);
    this.version = 'unknow'; // getBasicVersion(this.fullVersion);
    this.mobile = false; // isMobile(navigator.userAgent);
    this.width = screen.width;
    this.height = screen.height;
    this.platform =  'unknow'; //getPlatform(navigator.userAgent);
    
    // ------- init -------    
    this.init = function() { //operative system, is an auxiliary var, for special-cases
        //the first var is the string that will be found in userAgent. the Second var is the common name
        // IMPORTANT NOTE: define new navigators BEFORE firefox, chrome and safari
        var navs = [
            { name:'Opera Mobi', fullName:'Opera Mobile', pre:'Version/' },
            { name:'Opera Mini', fullName:'Opera Mini', pre:'Version/' },
            { name:'Opera', fullName:'Opera', pre:'Version/' },
            { name:'MSIE', fullName:'Microsoft Internet Explorer', pre:'MSIE ' },  
            { name:'BlackBerry', fullName:'BlackBerry Navigator', pre:'/' }, 
            { name:'BrowserNG', fullName:'Nokia Navigator', pre:'BrowserNG/' }, 
            { name:'Midori', fullName:'Midori', pre:'Midori/' }, 
            { name:'Kazehakase', fullName:'Kazehakase', pre:'Kazehakase/' }, 
            { name:'Chromium', fullName:'Chromium', pre:'Chromium/' }, 
            { name:'Flock', fullName:'Flock', pre:'Flock/' }, 
            { name:'Galeon', fullName:'Galeon', pre:'Galeon/' }, 
            { name:'RockMelt', fullName:'RockMelt', pre:'RockMelt/' }, 
            { name:'Fennec', fullName:'Fennec', pre:'Fennec/' }, 
            { name:'Konqueror', fullName:'Konqueror', pre:'Konqueror/' }, 
            { name:'Arora', fullName:'Arora', pre:'Arora/' }, 
            { name:'Swiftfox', fullName:'Swiftfox', pre:'Firefox/' }, 
            { name:'Maxthon', fullName:'Maxthon', pre:'Maxthon/' },
            // { name:'', fullName:'', pre:'' } //add new broswers
            // { name:'', fullName:'', pre:'' }
            { name:'Firefox',fullName:'Mozilla Firefox', pre:'Firefox/' },
            { name:'Chrome', fullName:'Google Chrome', pre:'Chrome/' },
            { name:'Safari', fullName:'Apple Safari', pre:'Version/' }
        ];
    
        var agent = navigator.userAgent, pre;
        //set names
        for (i in navs) {
           if (agent.indexOf(navs[i].name)>-1) {
               pre = navs[i].pre;
               this.name = navs[i].name.toLowerCase(); //the code name is always lowercase
               this.fullName = navs[i].fullName; 
                if (this.name=='msie') this.name = 'iexplorer';
                if (this.name=='opera mobi') this.name = 'opera';
                if (this.name=='opera mini') this.name = 'opera';
                break; //when found it, stops reading
            }
        }//for
        
      //set version
        if ((idx=agent.indexOf(pre))>-1) {
            this.fullVersion = '';
            this.version = '';
            var nDots = 0;
            var len = agent.length;
            var indexVersion = idx + pre.length;
            for (j=indexVersion; j<len; j++) {
                var n = agent.charCodeAt(j); 
                if ((n>=48 && n<=57) || n==46) { //looking for numbers and dots
                    if (n==46) nDots++;
                    if (nDots<2) this.version += agent.charAt(j);
                    this.fullVersion += agent.charAt(j);
                }else j=len; //finish sub-cycle
            }//for
            this.version = parseInt(this.version);
        }
        
        // set Mobile
        var mobiles = ['mobi', 'mobile', 'mini', 'iphone', 'ipod', 'ipad', 'android', 'blackberry'];
        for (var i in mobiles) {
            if (agent.indexOf(mobiles[i])>-1) this.mobile = true;
        }
        if (this.width<700 || this.height<600) this.mobile = true;
        
        // set Platform        
        var plat = navigator.platform;
        if (plat=='Win32' || plat=='Win64') this.platform = 'Windows';
        if (agent.indexOf('NT 5.1') !=-1) this.platform = 'Windows XP';        
        if (agent.indexOf('NT 6') !=-1)  this.platform = 'Windows Vista';
        if (agent.indexOf('NT 6.1') !=-1) this.platform = 'Windows 7';
        if (agent.indexOf('Mac') !=-1) this.platform = 'Macintosh';
        if (agent.indexOf('Linux') !=-1) this.platform = 'Linux';
        if (agent.indexOf('iPhone') !=-1) this.platform = 'iOS iPhone';
        if (agent.indexOf('iPod') !=-1) this.platform = 'iOS iPod';
        if (agent.indexOf('iPad') !=-1) this.platform = 'iOS iPad';
        if (agent.indexOf('Android') !=-1) this.platform = 'Android';
        
        if (this.name!='unknow') {
            this.code = this.name+'';
            if (this.name=='opera') this.code = 'op';
            if (this.name=='firefox') this.code = 'ff';
            if (this.name=='chrome') this.code = 'ch';
            if (this.name=='safari') this.code = 'sf';
            if (this.name=='iexplorer') this.code = 'ie';
            if (this.name=='maxthon') this.code = 'mx';
        }
        
        //manual filter, when is so hard to define the navigator type
        if (this.name=='safari' && this.platform=='Linux') {
            this.name = 'unknow';
            this.fullName = 'unknow';
            this.code = 'unknow';
        }
        
    };//function
    
    this.init();

}




function recuperaValoresSession(){
	
	$.postJSON_Sync("../login/getIdSession.do", null, function(data) {				
		
	});	
	
}

///////////////////////////////////////////////Seccion de domicilios/////////////////////////////////////////////////////////////////////



var urlDomicilios = "/scriptDomicilio";

function openWindowregistraDomicilioInegi(contextPath,action,idObjeto,fncCallback){	
	//MM_openBrWindowModal(contextPath+"/"+action,'status:false;dialogWidth:1000px;dialogHeight:750px');
//	console.log(fncCallback);
	return abreComponenteDomiciliosGestion('componenteDomiciliosCorreccion',contextPath,idObjeto,fncCallback);
}


function recuperaDomicilios(cveDomicilio){
	var sVarSeg = '{"clave":"'+cveDomicilio+'"}';
	var clase = jQuery.parseJSON(sVarSeg);
	
	$.postJSON("/correccion-web/domiciliosGeograficos/recuperaDomicilioBDTU.do", clase, function(data) {
			dataCedulas=data;
			funcion(data);
		}).error(function(data){ 		
			validarSesionExpirada(data);
			alert("error" + data);
		}).complete(function(){
			desbloquear();													
		});	
	
}



var domicilio;
var domicilioGuardado;
function abreComponenteDomiciliosGestion(paramDiv,contexto,idObjeto,fncCallback){
	//componenteDomiciliosCorreccion
	
	var idObjetoSession;
	if(idObjeto!=undefined){
		
		idObjetoSession=idObjeto;
	}else{
		
		idObjetoSession="dom";
	}
	
	
	
	$.getScript(urlDomicilios).done(function(script, textStatus) {
	  DomicilioCtrl.init(paramDiv);
	  DomicilioCtrl.setOnCloseCallback(function(){
		  domicilio =  this;		  

		  var clase = jQuery.parseJSON(domicilio);
		  
		  $.postJSON_Sync(contexto+"/domiciliosGeograficos/guardarDomicilioGeograficoBDTU.do?idObjetoSesion="+idObjetoSession, domicilio, function(data) {				
			  domicilioGuardado=data;
			  if(fncCallback!=undefined && fncCallback!=null){
				  eval(fncCallback);
			  }
			  
			  return data;
			  
			});	  		  
	  });

	  DomicilioCtrl.localizar();
	  
	
	}).fail(
	function(jqxhr, settings, exception) { 
	alert('Error al cargar el script'); 
	});
	return ;
	
}



//////////////////////////////Valores SetSIze

function set_size(elemento, maxSize) {

	var element = $("#" + elemento);

	setSizeCommon(element, maxSize);
}

function setSizeWithinIframe(document, maxSize) {
	var w = document.defaultView || document.parentWindow;
	var frames = w.parent.document.getElementsByTagName('iframe');

	for ( var i = frames.length; i-- > 0;) {
		var frame = frames[i];
		try {
			var d = frame.contentDocument || frame.contentWindow.document;
			if (d === document) {
				setSizeCommon(frame, maxSize);
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
function setSizeCommon(elemento, maxSize) {
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

		$(elemento).css('height', maxHeight + 'px');
	}
}


function recuperaTramiteTest(){	
	  $.postJSON_Sync("/correccion-web/domiciliosGeograficos/getTramiteTest.do", null, function(data) {				
		  if(fncCallback!=undefined && fncCallback!=null){
			  eval(fncCallback);
		  }
		  return data;
		  
		});
	
}

