
/** JS para las cedulas de construccion  */
var ESTATUS_EN_PROCESO=6;
var URL_SUBIDA_CEDULAS="/cedulasCorreccion/carga/archivo.do";
$(document).ready(function() {
	
	var validaCaptura = $("#monitoreoMainForm").validate({
		  rules: {
			  folioCorreccion: {
		      required: true,
		      alphanumeric: true
		  },
		    
		  },
		messages:{
			folioCorreccion:"Se requiere Ingresar un Folio de Correccion"
		  }
	});
	
	
	

//	$("a#btnMonitoreo").click(function(event){
//		event.preventDefault();
//
//		recuperaEstatusMonitor();
////		var contexto = $('#idContexto').val();
////		
////		if(validaPeriodo()){
////			if(validaCaptura.form())
////			{
////				
////				bloquear();
////				document.forms[0].action = contexto + "/cedulasCorreccion/monitor/monitor.do";
////				document.forms[0].submit();
////
////			}
////
////		}
//	
//     });



	
	triggerPatronInternet('','folioCorreccion','botonMonitorCedulas','click',false);
	
		
});//$(document).ready(function()

function obtenerPeriodos(){}	

function validaPeriodo(){
	
	if($('#periodo').val()<=0){
		$('#periodoLabel').html('Seleccione el ejercicio');
		return false;
	}else{
		$('#periodoLabel').html('');
		return true;
	}
	
}

function reloadPeriodos(){
	
	if($('#folioCorreccion').val()!=""){
		obtenerPeriodos();
	}
}

function generaTablaMonitor(data,bloqueo,refresco){
	
	
	 $("#tablaMonitorCedulas" ).dataTable( {
          "aaData":data,
		 	bFilter : false,
			bJQueryUI : true,
			bInfo:true,
			"bDestroy": true,
			bSort: false,
			//crollY: "200px",
			"bPaginate": false,
			"aoColumns" : [ {
						"sWidth": "15%",
						"sTitle" : "Descargar",
						"sClass": "dtCenterClassColumn",
						"fnRender":function(o,val){							
									var solicitud=o.aData['crtSolicitudcorr'];	
									var cedula=o.aData['crtNombreCedula'];
									var id=o.aData['id'];
									//return '<button type="button" onclick=descargaCedula("'+solicitud.nuFolio+'","'+cedula.cveCedula+'","'+id.cveEjercicio+'")>Descarga</button>';
									if(bloqueo){
										return '<img  src="'+getAppContextParaJS()+'/resources/images/monitor/cedulas/downloadExcel.png"  width="28" height="28" style="cursor: pointer;">';
									}else{
										return '<img onclick=descargaCedula("'+solicitud.nuFolio+'","'+cedula.cveCedula+'","'+id.cveEjercicio+'") src="'+getAppContextParaJS()+'/resources/images/monitor/cedulas/downloadExcel.png"  width="28" height="28" style="cursor: pointer;">';	
									}							
							}
						},{
						"sWidth": "15%",
						"sTitle" : "Cargar",
						"sClass": "dtCenterClassColumn",
						"fnRender":function(o,val){
							var solicitud=o.aData['crtSolicitudcorr'];	
							var cedula=o.aData['crtNombreCedula'];
							var id=o.aData['id'];
							if(bloqueo){
								return '<img  src="'+getAppContextParaJS()+'/resources/images/monitor/cedulas/uploadFileA.png"  width="28" height="28" style="cursor: pointer;">';
							}else{
								return '<img  id="botonCargaCedula'+solicitud.nuFolio+cedula.cveCedula+id.cveEjercicio+'" src="'+getAppContextParaJS()+'/resources/images/monitor/cedulas/uploadFileA.png"  width="28" height="28" style="cursor: pointer;">';
							}
							
							
						}
					},{
						"sWidth": "15%",
						"sTitle" : "Cedula",
						"mDataProp" : "crtNombreCedula.txNombre",
						"sClass": "dtCenterClassColumn"
					},{
						"sWidth": "15%",
						"sTitle" : "Ejercicio",
						"mDataProp" : "id.cveEjercicio",
						"sClass": "dtCenterClassColumn"
					},{
						"sWidth": "15%",
						"sTitle" : "Estatus",
						"mDataProp" : "crtEstatusFlujoCedula.txDescripcion",
						"sClass": "dtCenterClassColumn"
					},{
						"sWidth": "25%",
						"sTitle" : "Avance",
						"sClass": "dtCenterClassColumn",
						"fnRender":function(o,val){
							s=o.aData;
							var id=o.aData['id'];
							var cedula=o.aData['crtNombreCedula'];
							var solicitud=o.aData['crtSolicitudcorr'];								
								
							return '<div id="progressbar'+format(solicitud.cveSolicitudCorr+cedula.txNombre+id.cveEjercicio)+'"><div class="progress-label" id="labelProgress'+format(solicitud.cveSolicitudCorr+cedula.txNombre+id.cveEjercicio)+'"></div></div>';											
						}
					}	
				]
	    }); 

	 var id;
	 for(var t=0;t<data.length;t++){
		 id=format(data[t].crtSolicitudcorr.cveSolicitudCorr+data[t].crtNombreCedula.txNombre+data[t].id.cveEjercicio);
		 $("#progressbar"+id).progressbar({
			 	value: data[t].porcentajeAvance
			 });

		 if(data[t].porcentajeAvance!=null || data[t].porcentajeAvance!="null"){
			 $("#labelProgress"+id).text( data[t].porcentajeAvance + "%" );	 
		 }else{
			 $("#labelProgress"+id).text("0%" );
		 }
		 
	 }
	 if(!bloqueo){
		 convierteBotonesAdjuntos(data);	 
	 }
	 
	 if(refresco==undefined){
		 refrescaConsulta();	 
	 }
	 
}

function refrescaConsulta(){
	
	
	var sVarSeg = '{"folioCorreccion":"'+$("#folioCorreccion").val()+'"}';
	var clase = jQuery.parseJSON(sVarSeg);		
	$.postJSON(getAppContextParaJS()+"/cedulasCorreccion/monitor/recuperaEstatusMonitor.do",clase,function(data) { 
		 var id;
		 var flag=false;
		 for(var t=0;t<data.length;t++){
			 id=format(data[t].crtSolicitudcorr.cveSolicitudCorr+data[t].crtNombreCedula.txNombre+data[t].id.cveEjercicio);
			 $("#progressbar"+id).progressbar({
				 	value: data[t].porcentajeAvance
				 });

			 $("#labelProgress"+id).text( data[t].porcentajeAvance + "%" );
			 
			 if(data[t].crtEstatusFlujoCedula.cveEstatus==ESTATUS_EN_PROCESO){
			    flag=true;
			 }
			 
			 
		 }	
		 
        if(flag){
        	setTimeout(refrescaConsulta, 3000)
        }else{
        	//tERMINA LA RECURSIVIDAD
        	
        	actualizaEstatusMonitor();
        return;
        }

		
	});
}


function format(cadena){	
	return cadena.replace(" ","");
}

function recuperaEstatusMonitor(){
	
	var val=validarCedulaFolio()
	if(!val){
		return;
	}
	
	
	
	var flagBloqueo=false;
	var resValido=validaFolioInternet($("#folioCorreccion").val());
	if(!resValido){
		alert("El folio fue generado desde otra ubicacion");
		flagBloqueo=true;
		desbloquear();
	}
	
	
	
	var sVarSeg = '{"folioCorreccion":"'+$("#folioCorreccion").val()+'"}';
	var clase = jQuery.parseJSON(sVarSeg);		
	$.postJSON(getAppContextParaJS()+"/cedulasCorreccion/monitor/recuperaEstatusMonitor.do",clase,function(data) { 
		generaTablaMonitor(data,flagBloqueo);	
		desbloquear();
	});
		
}


function actualizaEstatusMonitor(){
	var flagBloqueo=false;
	var resValido=validaFolioInternet($("#folioCorreccion").val());
	if(!resValido){
		//alert("El folio fue generado desde Internet");
		flagBloqueo=true;
	}
	
	var sVarSeg = '{"folioCorreccion":"'+$("#folioCorreccion").val()+'"}';
	var clase = jQuery.parseJSON(sVarSeg);		
	$.postJSON(getAppContextParaJS()+"/cedulasCorreccion/monitor/recuperaEstatusMonitor.do",clase,function(data) { 
		generaTablaMonitor(data,flagBloqueo,false);			
	});
		
}

function descargaCedula(folio,idArchivoDescarga,periodo){
	
	
	
	var contexto = getAppContextParaJS();
	var sVarSeg = '{"folioCorreccion":"'+folio+'","idArchivoDescarga":"'+idArchivoDescarga+'","periodo":"'+periodo+'"}';
	
	var descargaCedula = jQuery.parseJSON(sVarSeg);
	bloquear();
	$.postJSON_Sync(contexto +"/cedulasCorreccion/descarga/validar.do", descargaCedula, function(data) {
		if(data==null){
		alert("No se ha encontrado informacion asociada con el Numero de Folio")	}
		else{
			$("form#formaDescargaCedula #idArchivoDescarga").val(idArchivoDescarga);
			$("form#formaDescargaCedula #folioCorreccion").val(folio);
			$("form#formaDescargaCedula #periodo").val(periodo);
			
//			document.forms[1].action = contexto + "/cedulasCorreccion/descarga/archivo.do";
//			document.forms[1].submit();			
			
			
//			$( "#formaDescargaCedula" ).attr('action',contexto + "/cedulasCorreccion/descarga/archivo.do");
//			//$( "#formaDescargaCedula" ).action = contexto + "/cedulasCorreccion/descarga/archivo.do";
//			$( "#formaDescargaCedula" ).submit();	
			
			
			openDownloadFileWindow(contexto,"cedulasCorreccion/descarga/descargaWindow.do?idArchivoDescarga="+idArchivoDescarga+"&folioCorreccion="+folio+"&periodo="+periodo+"");
			
			setTimeout(recuperaEstatusMonitor, 3000);
		}
	}).error(function(data){ 
		alert("La sesi\u00f3n es inv\u00e1lida");
	}).complete(function(){
		//Instrucciones para el 'complete'
		desbloquear();
	});
	
	
}






function validarCedulaFolio(){
	
	if($("#folioCorreccion").val()==""){
		return false;
	}
	var flag=true;
	var contexto = getAppContextParaJS();
	var sVarSeg = '{"folioCorreccion":"'+$("#folioCorreccion").val()+'"}';
	var descargaCedula = jQuery.parseJSON(sVarSeg);
	bloquear();
	$.postJSON_Sync(contexto + "/cedulasCorreccion/descarga/validaArchivo.do", descargaCedula, function(data) {
		desbloquear();
		if(data.msg=='true' || data.msg==true){			
			flag=true;
		}else{
			alert(data.msg);
			flag=false;
		}
		
	});
	
	return flag;
}








//upload
//upclick(generaObjetoArchivo('anexaDocumentos',getFuncionValida(),URL_SUBIDA_CEDULAS));


//funcion generica que genera la estructura del objeto para el envio del archivo, recibe el elemento(boton) y la funcion que valida el envio del archivo
function generaObjetoArchivo(elemento,funcionValida,url,cajaTexto){
	var archivo={
		      element: document.getElementById(elemento),
			  action: getAppContextParaJS() + url, 
		      valida:funcionValida,
		      onstart:
		        function(filename){	 		    	  
		        },
		      oncomplete:
		        function(response_data){
		    	  	setTimeout(recuperaEstatusMonitor, 1000);
		        }
		     };
	return archivo;
}






//funcion generica que construye una funcion para validar los componentes requeridos de un denunciante
function getFuncionValida(numeroFolio,idCedula,ejercicio){
	   var func=function(form){		   		
		   							
		   							var n=(form.action).split("?");
									form.action=n[0];
									form.action=form.action+'?numeroFolio='+numeroFolio+'&idCedula='+idCedula+'&ejercicio='+ejercicio+'';
									form.submit();
			   					
		   					
				}		
	   return func;
}


function convierteBotonesAdjuntos(data){
	 var id;
	 
//	 botonCargaCedula
	 for(var t=0;t<data.length;t++){
		 id=format(data[t].crtSolicitudcorr.nuFolio+data[t].crtNombreCedula.cveCedula+data[t].id.cveEjercicio);
		 
		 upclick(generaObjetoArchivo('botonCargaCedula'+id,getFuncionValida(data[t].crtSolicitudcorr.nuFolio,data[t].crtNombreCedula.cveCedula,data[t].id.cveEjercicio),URL_SUBIDA_CEDULAS));

	 }
	
	
	
}

