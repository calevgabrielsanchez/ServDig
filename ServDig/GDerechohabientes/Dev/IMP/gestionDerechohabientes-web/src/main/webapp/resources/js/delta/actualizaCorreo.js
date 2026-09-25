
$(document).ready(function(){ 
	//creamos el datatable del grupo Det Actualiza correo
	creaDataTableActualizaCorreo();
	const $select = $("#estado");
	$("#fecha").datepicker({
		dateFormat: 'dd/mm/yy',
		changeMonth : true,
		changeYear : true,
		maxDate: new Date(), 
		yearRange : '-112:+0',
	    }).datepicker();
	
	$("#folio").keydown(function(event){
        //alert(event.keyCode);
        if((event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105) && event.keyCode !==190  && event.keyCode !==110 && event.keyCode !==8 && event.keyCode !==9  ){
            return false;
        }
    });
	
	
			
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


function creaDataTableActualizaCorreo() {
	
	console.log("Entro a generar la tabla");
	//verificamos cual consulta se va a realizar
	var urlConsulta = context_path + '/inicioVentanilla/listar/ActualizaCorreo';
	//obtenemos las columas de las tablas de acuerdo al tipo
	var columnasTabla = obtenerColumnasTabla();
	//creamos la tabla y cargamos los datos mediante el plugin
	
	var dataTableSolicitudes = $('#tablaSolicitudActualizacionCorreo').dataTable({
		sScrollX: "100%",
		bJQueryUI : true,
        bFilter : false,
        bInfo:true,
        bSort: false,
        "bDestroy":true,
        "bPaginate": true,
        "bAutoWidth" : false,
        "iDeferLoading" : 0,
        "bServerSide" : true, 
        "bProcessing" : true,
        "aoColumns" : columnasTabla,
        "sAjaxSource" : urlConsulta,
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
        		//redibujamos la tabla
        		//$("#"+IDS_ACCORDION[tipoTabla]).accordion('resize');
        	}).error(function(jqXHR, textStatus, errorThrown) {
        		error(errorThrown+" - "+'No existen solicitudes a mostrar.');
        	});
        }
	});
	
	//redibujamos la tabla
	dataTableSolicitudes.fnDraw();

}	




function creaDataTableActualizaCorreoFiltros() {
	
	var nss = $("#nss").val();
	var fechaRegistroAlta = $("#fecha").val();
	var idEstado = $("#estado").val();
	var folio = $("#folio").val();
	
	console.log('el nss es: ', nss);
	console.log('el fecha es: ', fechaRegistroAlta);
	console.log('el estado es: ', idEstado);
	console.log('el folio es: ', folio);
	
	console.log("Entro a generar la tabla por filtros");
	//verificamos cual consulta se va a realizar
	var urlConsulta = context_path + '/inicioVentanilla/listar/ActualizaCorreoFiltros';
	//obtenemos las columas de las tablas de acuerdo al tipo
	var columnasTabla = obtenerColumnasTabla();
	//creamos la tabla y cargamos los datos mediante el plugin
	
	var dataTableSolicitudes = $('#tablaSolicitudActualizacionCorreo').dataTable({
		sScrollX: "100%",
		bJQueryUI : true,
        bFilter : false,
        bInfo:true,
        bSort: false,
        "bDestroy":true,
        "bPaginate": true,
        "bAutoWidth" : false,
        "iDeferLoading" : 0,
        "bServerSide" : true, 
        "bProcessing" : true,
        "aoColumns" : columnasTabla,
        "sAjaxSource" : urlConsulta,
        "fnServerData" : function(sSource, aoData, fnCallback) {
        	aoData.push({
        		"name" : "sSearch",
        		"value" : ''
        	});

        	var wrapper = new Object();
        	wrapper.aoData = aoData;
        	var oForm = { nss: nss, fechaRegistroAlta: fechaRegistroAlta, idEstado: idEstado, folio: folio};
        	wrapper.oForm = oForm;

        	$.postJSON(sSource, wrapper, function(data) {
        		fnCallback(data);
        		//redibujamos la tabla
        		//$("#"+IDS_ACCORDION[tipoTabla]).accordion('resize');
        	}).error(function(jqXHR, textStatus, errorThrown) {
        		error(errorThrown+" - "+'No existen solicitudes a mostrar con los filtros seleccionados.');
        		limpiarCampos();
        		creaDataTableActualizaCorreo();
        	});
        }
	});
	
	//redibujamos la tabla
	dataTableSolicitudes.fnDraw();

}	


/**
 * Funcion que retorma las columas que se pintaran en la tabla dependiendo del tipo de tabla que se este pintando
 * @param tipoTabla
 * @returns
 */
function obtenerColumnasTabla() {
	
	/**
	 * Columnas que comparten todas la tablas
	 */
	var columnas = [
		{ 
			"sTitle" : "",
			"sDefaultContent": function(obj) {
				return "<img   src='"+ context_path +"/resources/imagenes/details_open.png'/>";
			}
		},
		{ 
            "sTitle" : "Folio",
            "mDataProp" : "folio"
        },
        { 
            "sTitle" : "Fecha",
            "mDataProp" : "fechaRegistroAlta"
        },
        { 
            "sTitle" : "Estado",
            "mDataProp" : "estadoTramite"
        },
        { 
            "sTitle" : "NSS",
            "mDataProp" : "nss"
        },
        { 
            "sTitle" : "Nombre (s)",
            "mDataProp" : "nombre"
        },
        { 
            "sTitle" : "Primer Apellido",
            "mDataProp" : "primerApellido"
        },
        { 
            "sTitle" : "Segundo Apellido",
            "mDataProp" : "segundoApellido"
        },
        { 
            "sTitle" : "CURP",
            "mDataProp" : "curp"
        },
        { 
            "sTitle" : "Detalle",
            "sDefaultContent": "",
            fnRender: function(obj) {								  
            	return "<button type='button' class='mboton' onclick='ejecutarConsultaSolicitudPorFolio(\""+obj.aData.folio+"\");'>Detalle</button>"
            }
        }        
        
	];
	
	return columnas;
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

function ejecutarConsultaSolicitudPorFolio (folio) {
	console.log("Folio de la solicitud a consultar: ", folio);
	if (folio == null || folio == '' || typeof folio === 'undefined') {
		return false;
	}
	
	DetalleSolicitudCtrl.init("detalleSolicitudComponent",folio);
	DetalleSolicitudCtrl.abrir();
	
}

function limpiar() {
    document.getElementById("nss").value = "";
    document.getElementById("fecha").value = "";
    document.getElementById("folio").value = "";
  $('#estado').val('-- SELECCIONE UNA OPCI&Oacute;N --');
    
    
}

