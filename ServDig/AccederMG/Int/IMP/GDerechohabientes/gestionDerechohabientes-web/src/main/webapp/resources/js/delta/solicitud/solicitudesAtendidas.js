$(document).ready(function(){

	creaDataTableSolAtendidas();
});

function creaDataTableSolAtendidas() {
	dtSolicitudesAtendidas = $('#solicitudesAtendidasTable').dataTable({
		
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
								    sWidth: '10%',
								    "sDefaultContent": "",
									   fnRender: function(obj) {
										   var fecha = new Date(obj.aData.fechaRegistroActalizado);
										   
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
			                         sWidth: '15%'
		                       },
		                       {   "sTitle" : "Nombre solicitante",
		                    	   "sClass":"dtJustifyClassColumn",		
		                    	   sWidth: '25%',	                    	   
		                    	   fnRender: function(obj) {
		                    		   var nombreSol = "";
		                    		   if(obj.aData.tramite[0].persona == null) {
		                    			  /* if(obj.aData.solicitante != null) {
				                    		   nombreSol += ""+ (obj.aData.solicitante.fisica.nombre != null ? obj.aData.solicitante.fisica.nombre : "" ) + " ";
				                    		   nombreSol += ""+ (obj.aData.solicitante.fisica.primerApellido != null ? obj.aData.solicitante.fisica.primerApellido : "" ) + " ";
				                    		   nombreSol += ""+ (obj.aData.solicitante.fisica.segundoApellido != null ? obj.aData.solicitante.fisica.segundoApellido : "" );
		                    			   }  */
		                    		   } else {
			                    		   nombreSol += ""+ (obj.aData.tramite[0].persona.nombre != null ? obj.aData.tramite[0].persona.nombre : "" ) + " ";
			                    		   nombreSol += ""+ (obj.aData.tramite[0].persona.primerApellido != null ? obj.aData.tramite[0].persona.primerApellido : "" ) + " ";
			                    		   nombreSol += ""+ (obj.aData.tramite[0].persona.segundoApellido != null ? obj.aData.tramite[0].persona.segundoApellido : "" );
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
		                       
		                       
		                       {   "sTitle" : "Detalle",
		                           sWidth: '10%',                    	   
		                    	   fnRender: function(obj) {return "<button type='button' class='mboton' onclick='detalleSolicitud("+obj.aData.idSolicitud+");'> Detalle </button>";},"aTargets": [ 0 ]
		                       }
		       ],
		       "sAjaxSource" : context_path + '/solicitud/muestraSolicitudesAtendidas',
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
            			error(errorThrown+" - "+'No fue posible obtener las solicitudes atendidas.');
            		 }).complete(function() {  }); 
	             
	            }
	});
	dtSolicitudesAtendidas.fnDraw();	
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

	$decision.text('No existen solicitudes atendidas');
	$decision.dialog('open');
}