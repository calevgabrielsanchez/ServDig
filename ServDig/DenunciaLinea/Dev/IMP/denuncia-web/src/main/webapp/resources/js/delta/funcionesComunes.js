/**
 * Abre pantalla de domicilios geográficos
 * @param contextPath
 * @param action
 */
function openWindowregistraDomicilioInegi(contextPath,action){
	
	MM_openBrWindowModal(contextPath+"/"+action,'status:false;dialogWidth:1000px;dialogHeight:750px');

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
 * Función genérica para abrir ventanas no modales
 * @param theURL
 * @param winName
 * @param features
 */
function MM_openBrWindow(theURL,winName,features) { 
  window.open(theURL,winName,features);
}

/**
 * Función genérica para abrir ventanas modales
 * @param theURL
 * @param features
 */
function MM_openBrWindowModal(theURL,features) { //v2.0
	window.showModalDialog(theURL,window,features);

}


/**
 * Valida las fechas [A<B]
 * En caso de que la fecha inicial sea mayor a la final
 * retornará falso.
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
        
	var newWindow; 	
 	newWindow = window.open('', 'DetailswindowSICONet', atts);
 	
 	document.forms[0].target='DetailswindowSICONet';
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
    var ie7 = (document.all && !window.opera && window.XMLHttpRequest) ? true : false;  
    self.focus();    
    if (ie7 || browser == "Netscape") {     
          //This method is required to close a window without any prompt for IE7 
          window.opener = null;
          window.open('','_self','');
          window.close();
         
    } else {
          //This method is required to close a window without any prompt for IE6
          this.focus();
          self.opener = this;
          self.close();
    }
}

/*
 * Función genérica para invocar a JSON y 
 * procesar un formulario
 */
function procesaFormulario(funcionValidacion){
	
	var objForma = $("form#"+FORMA_ACTUAL).toObject({mode:'first'});
	var formAction = $("form#"+FORMA_ACTUAL).attr('action');
	bloquear();
	
	try{
		if(eval(funcionValidacion)){
			$.postJSON(formAction, objForma, function(data) {
				if(data == null){
					alert('Error General: Conculte a su administrador');
				}else{
					verifyCustomDataError(data);
					limpiarFormulario("form#"+FORMA_ACTUAL);	
					desbloquear();
				}
			}).error(function(data){ 
				validarSesionExpirada(data);
				alert("error" + data);
				desbloquear();
			});
			
		}else{
			desbloquear();
			return false;
			
		}
	}catch(error){
		alert("Error al procesar la forma, verifique el códido JS:"+error);
		desbloquear();		
	}
	
	
}



/**
 * Permite validar si una solicitud tiene derecho a seguir
 * utilizando ABC o solo consultas. En caso de que no 
 * se ocultaran los botones de acción.
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
 * Verifica si existe un error en el proceso,
 * este es controlado por el programador y debe
 * de utlizar el atributo comun (en todos los models)
 * el cual extiende del abstractModel error.
 * 
 * En caso de que este no exista mandará alerta
 * de éxito.
 * 
 * @param data JSON OBJ
 */
function verifyCustomDataError(data){
	
	if(data.error!=undefined && data!=null && data!="null" && data.error!=""){
		alert("Error:"+data.error);
	}else if(data.error!=undefined && data!=null && data!="null"){
		
		if(data.exito!=undefined && data!=null && data!="null" && data.exito!=""){
			alert(data.exito);
		}else{
			alert("Operaci\u00F3n Exitosa");
		}
		
	}else if(data.error==undefined || data==null || data=="null"){
		
	}else{
		alert("Existe un error en la operación, verifique validaciones");
	}
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
 *  •Browser name: BrowserDetect.browser
 *  •Browser version: BrowserDetect.version
 *  •OS name: BrowserDetect.OS
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

	function cancelar(id){
		$('#'+id).attr("action",getAppContextParaJS() + "/denuncia/redireccionConsultaFunc.do" );
		$('#'+id).attr("method","post");
		$('#'+id).submit();
	}
