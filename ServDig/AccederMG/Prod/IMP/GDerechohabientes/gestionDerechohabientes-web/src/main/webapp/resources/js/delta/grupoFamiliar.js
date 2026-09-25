var derechohabiente;
var HOMBRE = 1;
var MUJER = 2;
var PARENTESCO_HIJOS = 2;

$(document).ready(function(){ 
	
	//accordion de domicilio
	$( "#accordionDomicilio" ).accordion({
		collapsible: true,					
		active: false,
		header: 'div'		
	});
		
	//menaje para indicar si el asegurado esta o no registrado
	createMensajeAsegurado();
	
	//creamos el datatable del grupo familiar
	creaDataTableGrupoFamiliar();
		    
	//ponemos el formato de tabla a la de patrones
	createDatatable("patrones");

			
});

function createDatatable(idTabla) {
	$('#'+idTabla).dataTable( {
			sScrollX: "100%",
			bJQueryUI : true,
		    bFilter : false,
		    bInfo:true,
		    bSort: false,
		    "bPaginate": true,
		    "bAutoWidth" : true,
		    "iDeferLoading" : 0
		}
	);
}

function createMensajeAsegurado() {
	var aseg = $('#conAsegurado').val();  
	
	if(aseg == "2" || aseg == "3" || aseg == "4" || aseg == "5"){
		$("#msg00").dialog({
			modal: true,
			closeOnEscape: false,
			resizable: false,
		      buttons : {
		        "Aceptar" : function() {
		        	$(this).dialog("close");
		        }
		      }
		  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();		
	}	
}

function validarSolicitud(idSolicitud){
	$("#frmGrupoFamiliar").attr("action","/${mvn.web.app.root}/derechohabientes/registro/validar?solicitud="+idSolicitud);			          
	$("#frmGrupoFamiliar").submit();

}

function creaDataTableGrupoFamiliar() {
	
		dtIntegrantes = $('#tablaIntegrantesGrupoFamiliar').dataTable({
		
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
								    "sTitle" : "",
								    "sDefaultContent": "",
			                           fnRender : function(obj) {
			                    		   return "<img   src='"+ context_path +"/resources/imagenes/details_open.png'/>";
			                    	  }
								
								},
		                       { 
		                           "sTitle" : "Parentesco",
		                           "mDataProp" : "parentesco.descripcion"
		                       },
		                       { 
		                           "sTitle" : "Nombre (s)",
		                           "mDataProp" : "derechohabiente.nombre"
		                       },
		                       { 
		                           "sTitle" : "Primer Apellido",
		                           "mDataProp" : "derechohabiente.primerApellido"
		                       },
		                       { 
		                           "sTitle" : "Segundo Apellido",
		                           "mDataProp" : "derechohabiente.segundoApellido"
		                       },
		                       { 
		                           "sTitle" : "CURP",
		                           "mDataProp" : "derechohabiente.curp"
		                       },
		                       { 
		                           "sTitle" : "Sexo",
		                           "mDataProp" : "derechohabiente.sexo.descripcion"
		                       },
		                       { 
		                           "sTitle" : "Edad",
		                           "sDefaultContent": "",
		                           fnRender : function(obj) {
		                        	   
		                        	   var edadA= calcularEdad(obj.aData.derechohabiente.fechaNacimiento);
			                   			  
		                        	   return edadA;
			                   			 
	                    		   }
		                       },
		                       { 
		                    	   "sTitle" : "Situaci\u00F3n",
		                           "sDefaultContent": "",
		                           fnRender : function(obj) {
		                        	   if(obj.aData.estadoDerechohabiente != null){
		                        		   return obj.aData.estadoDerechohabiente.descripcion;
		                        	   } else {
		                        		   return "";
		                        	   }
		                           }
		                       },
		                       {   
		                    	  "sTitle" : "",
		                    	  "mDataProp" : "calidad",
		                    		fnRender:    function(obj) {
		                    		   return "<button type='button' class='mboton' onclick='detalleDerechohabiente("+obj.aData.derechohabiente.idPersona+");'> Ver Detalle </button>";
	                    		   },
	                    		   "aTargets": [ 0 ]
		                    	} 
		       ],
		       "sAjaxSource" : context_path + '/inicio/listar/grupoFamiliar',
			   "fnServerData" : function(sSource, aoData, fnCallback) {
	               
	                var wrapper = new Object();
	                wrapper.aoData = aoData;
	                var oForm = {"nssStr": $('#nss').val()};
	                wrapper.oForm = oForm;
	                
	                $.postJSON(sSource, wrapper, 
	                		function(data) {
	                    		fnCallback(data);
	                		}).success(function() {  }) 
	                		.error(function(jqXHR, textStatus, errorThrown) {
	                			error(errorThrown+" - "+'No fue posible obtener los integrantes del grupo familiar.');
	                		 }).complete(function() {  }); 
	                
	            }
	});
	
	$('#tablaIntegrantesGrupoFamiliar tbody > tr > td > img').live('click', function () {

		var nTr = $(this).parents('tr')[0];


		if ( dtIntegrantes.fnIsOpen(nTr) )
		{

			this.src =  context_path +"/resources/imagenes/details_open.png";
			dtIntegrantes.fnClose( nTr );
		}
		else
		{

			this.src = context_path +"/resources/imagenes/details_close.png";
			dtIntegrantes.fnOpen( nTr, fnFormatDetailsD(dtIntegrantes, nTr), 'Detalles' );
		}
	} 

	);

	dtIntegrantes.fnDraw();	
}						   		

/* Funcion que agrega el detalle a cada fila */
function fnFormatDetailsD ( oTable, nTr )
{
	     
	var aData = oTable.fnGetData( nTr );
	var vigencia = aData.fechaFinVigencia != null && aData.fechaFinVigencia != "" ? aData.fechaFinVigencia : '';
	var circunscripcion = aData.circunscripcionForaneaActiva;
	var permanente = aData.prorrogaPermanente == true ? 'Aplicaci\u00F3n del art\u00EDculo 93 LLS97' : '' ;
	var defuncion = aData.subEstadoDerechohabiente != null ? aData.subEstadoDerechohabiente.idSubEstadoDerechohabiente == 2 : false;
	var sOut = '<div class="innerDetails">';
	var derechohabiente = aData.derechohabiente;
	if(derechohabiente.asignacionNSS == undefined || derechohabiente.asignacionNSS == null) {
		derechohabiente.asignacionNSS = aData.asignacionNSS;
	}
	
	if(circunscripcion == undefined || circunscripcion == null) {
		$.ajax({
	    	url : context_path + '/derechohabiente/circunscripcion',
	        type: 'POST',
	        async: false,
	        contentType: 'application/json',
	        data: JSON.stringify(derechohabiente),
	        dataType: 'json',
	        success: function (result) {
	        	circunscripcion = result.circunscripcion;
	        	aData.circunscripcionForaneaActiva = circunscripcion;
	        }
	    });
	}
	
	sOut += '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px; background-color:#CCCCCC; width:100%; ">';
	sOut += '	<caption style="background-color:#999999; "><strong><font color="#000000">Datos de adscripci\u00F3n y vigencia</font></strong></caption>';
	sOut += '	<tr>';
	sOut += '	 	<td align="right">Situaci\u00F3n: </td>';
	sOut += '		<td>';
	sOut += '		<input readonly="readonly" type="text"';
	sOut += '		style="width: 140px"';
	sOut += '		value="'+(aData.estadoDerechohabiente != null ? aData.estadoDerechohabiente.descripcion : '')+'"/>';
	sOut += '		</td>';
			
	if(defuncion) {
		sOut += '		<td align="right">Detalle Situaci\u00F3n:</td>';
		sOut += '		<td>';
		sOut += '		<input readonly="readonly" type="text"';
		sOut += '		style="width: 140px"';
		sOut += '		value="'+(aData.subEstadoDerechohabiente != null ? aData.subEstadoDerechohabiente.descripcion: '' )+'"/>';
		sOut += '		</td>';
	} else {
		sOut += '		<td align="right"></td>';
		sOut += '<td></td>';
	}
	sOut += '		<td align="right">Fecha t\u00E9rmino de vigencia: </td>';
	sOut += '		<td>';
	sOut += '		<input readonly="readonly" type="text"';
	sOut += '		style="width: 140px"';
	sOut += '		value="'+ vigencia +'" />';
	sOut += '		</td>';
	sOut += '	</tr>';
	
			sOut += '	<tr>';
			sOut += '	 	<td align="right">UMF: </td>';
			sOut += '		<td colspan="3">';
			sOut += '		<input readonly="readonly" type="text"';
			sOut += '		style="width: 460px"'; 
			if(aData.medicoEnTurno != null && aData.medicoEnTurno != undefined) {
				sOut += '		value="'+aData.medicoEnTurno.unidadMedicaFamiliar.nombreCorto+'"/>';
			} else {
				sOut += '		value="Sin asignar"/>';
			}
			sOut += '		</td>';
					
					
			sOut += '		<td align="right">Consultorio: </td>';
			sOut += '		<td>';
			sOut += '		<input readonly="readonly" type="text"';
			sOut += '		style="width: 140px"';
			if(aData.medicoEnTurno != null && aData.medicoEnTurno != undefined) {
				sOut += '		value="'+aData.medicoEnTurno.consultorio.descripcion+'" />';
			} else {
				sOut += '		value="Sin asignar"/>';
			}
			sOut += '		</td>';
			sOut += '	</tr>';
			
			sOut += '	<tr>';
			sOut += '		<td align="right">Delegaci\u00F3n o Municipio: </td>';
			sOut += '       <td colspan="3">';
			sOut += '		 <input readonly="readonly" type="text"';
			sOut += '		 style="width: 460px"';
			if(aData.medicoEnTurno != null && aData.medicoEnTurno != undefined) {
				sOut += '		 value="'+aData.medicoEnTurno.unidadMedicaFamiliar.subdelegacion.delegacion.descripcion+'" />';
			} else {
				sOut += '		value="Sin asignar"/>';
			}
			sOut += '		</td>';
			sOut += '	 	<td colspan="6" align="center">';
			sOut += '		 <strong>Servicios en circunscripci\u00F3n for\u00E1nea: '+ (circunscripcion ? 'SI' : 'NO') +'</strong>';
			
			sOut += '	 	</td>';
			sOut += '	</tr>';
			
			sOut += '	<tr>';
	
	if(patronImss){
		sOut += '	 	<td colspan="2"><strong>Aplicaci\u00F3n Clausula 74 CCT<strong></td>';
	}else{
		sOut += '	 	<td><td colspan="2"><strong>'+permanente+'<strong></td><br></td>';
	}
	sOut += '	</tr>';
	
	sOut += '	<tr>';
	sOut += '	 	<td><br></td>';
	sOut += '	</tr>';

	sOut += '</table>';
	sOut +='</div>';
	
	return sOut;
}

function calcularEdad(fechaNacimiento) {
	
	var edad = 'SIN EDAD';
	
	if(fechaNacimiento != null && fechaNacimiento != "") {
		
		var fecha = fechaNacimiento.toString().split("/");
	    var dia = fecha[0];
	    var mes = fecha[1];
	    var anio = fecha[2];
	    
	    // cogemos los valores actuales
	    var fecha_hoy = new Date();
	    var ahora_anio = fecha_hoy.getFullYear();
	    var ahora_mes = fecha_hoy.getMonth()+1;
	    var ahora_dia = fecha_hoy.getDate();
	    
	    // realizamos el calculo
	    var edad = ahora_anio - anio;
	    if ( ahora_mes < mes ) {
	        edad--;
	    }else if ((mes == ahora_mes) && (ahora_dia < dia)){
	        edad--;
	    }
	}
	
    return edad;
}