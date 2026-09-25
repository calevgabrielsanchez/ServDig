URL_AUTORIZAR_BAJA = context_path + "/derechohabiente/baja/autorizar";
URL_AUTORIZAR_CORRECCION = context_path + "/derechohabiente/correccion/tramite/autorizacion"; 
URL_AUTORIZAR_PRORROGA = context_path + "/prorroga/getProrroga"; 
URL_AUTORIZAR_REGISTRO = context_path +"/derechohabientes/registro/autorizar";

$(document).ready(function(){
	creaDataTableSolPenAut();
});

function creaDataTableSolPenAut() {
	dtSolicitudesPenAut = $('#solicitudesPenAutTable').dataTable({
		
			sScrollX: "100%",
			bJQueryUI : true,
	        bFilter : false,
	        bInfo:true,
	        bSort: false,
	        "bPaginate": true,
	        "bAutoWidth" : false,
	        "iDeferLoading" : 0,
	        "bServerSide" : true, 
			"bProcessing" : true,
			 "aoColumns" : [		
								{ 
									"sTitle" : "Fecha actualización",
								    "sDefaultContent": "",
								    sWidth: '10%',
									fnRender: function(obj) {
										 var fecha = new Date(obj.aData.fechaActualizacion);
										 var dia = fecha.getDate(); 
										 if(dia < 10){
											dia = "0"+dia;
										 }
										 var mes = fecha.getMonth() + 1;
										 if(mes < 10){
											 mes = "0"+mes;
										 }
										 var anio = fecha.getFullYear();
										 return ""+dia+"/"+mes+"/"+anio;
									}
								},
								{ 
			                        "sTitle" : "Tipo solicitud",
			                        "mDataProp" : "tipoSolicitud.descripcion",
			                        "sClass":"dtJustifyClassColumn",
			                        sWidth: '20%'
			                       },
		                       { 
		                           "sTitle" : "Nss",
		                           "mDataProp" : "numNss",
		                           sWidth: '10%'
		                       },
		                       
		                       
		                       
		                       {   "sTitle" : "Nombre solicitante",	
		                    	   sWidth: '26%',
		                    	   fnRender: function(obj) {
		                    		   var nombreSol = "";
		                    		   if(obj.aData.tramites[0].persona == null) {
		                    		   /*nombreSol += ""+ (obj.aData.solicitante.fisica.nombre != null ? obj.aData.solicitante.fisica.nombre : "" ) + " ";
		                    		   nombreSol += ""+ (obj.aData.solicitante.fisica.primerApellido != null ? obj.aData.solicitante.fisica.primerApellido : "" ) + " ";
		                    		   nombreSol += ""+ (obj.aData.solicitante.fisica.segundoApellido != null ? obj.aData.solicitante.fisica.segundoApellido : "" );
		                    		   */} else {
		                    		   nombreSol += ""+ (obj.aData.tramites[0].persona.nombre != null ? obj.aData.tramites[0].persona.nombre : "" ) + " ";
		                    		   nombreSol += ""+ (obj.aData.tramites[0].persona.primerApellido != null ? obj.aData.tramites[0].persona.primerApellido : "" ) + " ";
		                    		   nombreSol += ""+ (obj.aData.tramites[0].persona.segundoApellido != null ? obj.aData.tramites[0].persona.segundoApellido : "" );
		                    		   }
		                    		   return nombreSol;},
		                    		"aTargets": [ 0 ]
		                       },
		                       {   "sTitle" : "Parentesco",
		                    	   "sClass":"dtJustifyClassColumn",		
		                    	   sWidth: '10%',
		                    	   fnRender: function(obj) {
		                    		   var parentesco = "";
		                    		   if(obj.aData.parentesco != null){
		                    			   parentesco = ""+obj.aData.parentesco.descripcion;
		                    		   }
		                    		   return parentesco;},
		                    		"aTargets": [ 0 ]
		                       },
		                       { 
		                           "sTitle" : "Estado solicitud",
		                           "mDataProp" : "estadoSolicitud.descripcion",
		                           sWidth: '10%'
		                       },
		                       {   "sTitle" : "Proceso",
		                           sWidth: '7%',		                    	   
		                    	   fnRender: function(obj) {return "<button type='button' class='mboton' onclick='detalleSolicitud("+obj.aData.solicitudId+");'> Detalle </button>";},"aTargets": [ 0 ]
		                       },
		                       {   "sTitle" : "",
		                           sWidth: '7%',		                    	   
                 	      	       fnRender: function(obj) {return "<button type='button' class='mboton' onclick='completarSolPendAut("+obj.aData.tramites[0].tipoTramite.idTipoTramite+","+obj.aData.solicitudId+","+obj.aData.tramites[0].tramiteId+");'> Autorizar </button>";},"aTargets": [ 0 ]
		                       }
		       ],
		       "sAjaxSource" : context_path + '/solicitud/muestraSolicitudesPenAut',
			   "fnServerData" : function(sSource, aoData, fnCallback) {
	                aoData.push({
	                    "name" : "sSearch",
	                    "value" : ''
	                });
	            
	                var wrapper = new Object();
	                wrapper.aoData = aoData;
	                var oForm = {};
	                wrapper.oForm = oForm;
	                
	                $.postJSON(sSource, wrapper, function(data) {
	                    fnCallback(data);
	                    if(data.iTotalRecords==0){
	                    	sinSolicitudesDialog();
	                    }
	                }).success(function() {  }) 
            		.error(function(jqXHR, textStatus, errorThrown) {
            			error(errorThrown+" - "+'No fue posible obtener las solicitudes pendientes de autorizar.');
            		 }).complete(function() {  }); 
	             
	            }
	});
	dtSolicitudesPenAut.fnDraw();	
}	

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}
		   		
function sinSolicitudesDialog(){
	$decision = $('<div></div');
	$decision.dialog({
		autoOpen : false,
		resizable : false,
		height : 140,
		title : 'Alerta',
		modal : true,
		buttons : {
			
					"Aceptar" : function() {
						cierraDialogo($(this));
					}
		}
	}).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();

	$decision.text('No existen solicitudes pendientes de autorizaci\u00F3n para la b\u00FAsqueda');
	$decision.dialog('open');
}


//redirecciona para completar el tramite
function completarSolPendAut(idTipoTramite,idSolicitud,idTramite){
	var url="";

	fnAbrirMensajeEsperePorFavor();
	switch(idTipoTramite) {
		case BAJA_DEFUNCION:	url = URL_AUTORIZAR_BAJA;//colocar la direccion del tramite
				break;
		case BAJA_CONCUBINATO:	url = URL_AUTORIZAR_BAJA;//colocar la direccion del tramite
				break;
		case BAJA_DIVORCIO:	url = URL_AUTORIZAR_BAJA;//colocar la direccion del tramite
				break;
		case BAJA_DEPENDENCIA:	url = URL_AUTORIZAR_BAJA;//colocar la direccion del tramite
				break;
		case CORRECCION_DATOS_DERECHOHABIENTE: url=URL_AUTORIZAR_CORRECCION;
				break;
		case PRORROGA_ACUERDOS: 
		case PRORROGA_LAUDO:url=URL_AUTORIZAR_PRORROGA;
				break;			
		case PRORROGA_PERMANENTE:url=URL_AUTORIZAR_PRORROGA;
				break;			
		case PRORROGA_TEMPORAL:url=URL_AUTORIZAR_PRORROGA;
				break;			
		case PRORROGA_ENFERMEDAD:url=URL_AUTORIZAR_PRORROGA;
				break;
		case REGISTRO_CONCUBINARIO: url=URL_AUTORIZAR_REGISTRO;
				idSolicitud = idTramite;
				break;
		case REGISTRO_PADRES: url=URL_AUTORIZAR_REGISTRO;
				idSolicitud = idTramite;
				break;
	}
	
	location.href =url + "/" + idSolicitud;

}