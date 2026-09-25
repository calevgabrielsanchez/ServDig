/**
* Java Script de los transportes
*
**/


//Objeto de datable de los transportes
var dtTransporte;
var sIdNameFormPaginarTransporte="#transporteFormPaginar";
var sIdNameFormNuevoTransporte="#transporteFormNuevo";



var sIdDialgoAgregarTransporte = "#dgNuevoTransporte";
var oDialogAgregarTransporte;


var sIdDialogEliminarTransporte = "#dgEliminarTransporte";
var oDialogEliminarTransporte;

var sIdDialogErrorSinSeleccionTransporte = "#dgErrorSinSeleccionTransporte";
var oDialogErrorSinSeleccionTransporte;


var sIdFormModificarTransporte = '#transporteFormModificar';



var sIdDialogModificarTransporte = "#dgModificarTransporte";
var oDialogModificarTransporte;


var pDialogHeigthTransporte = 450;
var pDialogWidthTransporte = 450;



/** Seccion de codigo a ejectuar cuando el DOM este listo **/
$(function() {
	
	
	
	// validamos que solo se ingresen numeros (al agregar nuevo elemento)
	    $("#transporteFormNuevo #numUnidades").keydown(function(event) {
	    	
	    	// Prevent shift key since its not needed
	        if (event.shiftKey == true) {
	            event.preventDefault();
	        }
	        // Allow Only: keyboard 0-9, numpad 0-9, backspace, tab, left arrow, right arrow, delete
	        if ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105) || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 46) {
	            // Allow normal operation
	        } else {
	            // Prevent the rest
	            event.preventDefault();
	        }
	        
	    });
	    
	 // validamos que solo se ingresen numeros (al modificar un elemento existente)
	    $("#transporteFormModificar #numUnidades").keydown(function(event) {
	    	
	    	// Prevent shift key since its not needed
	        if (event.shiftKey == true) {
	            event.preventDefault();
	        }
	        // Allow Only: keyboard 0-9, numpad 0-9, backspace, tab, left arrow, right arrow, delete
	        if ((event.keyCode >= 48 && event.keyCode <= 57) || (event.keyCode >= 96 && event.keyCode <= 105) || event.keyCode == 8 || event.keyCode == 9 || event.keyCode == 37 || event.keyCode == 39 || event.keyCode == 46) {
	            // Allow normal operation
	        } else {
	            // Prevent the rest
	            event.preventDefault();
	        }
	        
	    });	
	

		
		
		
    /*
		 * Configuracion de los submits de las formas
		 */
		
    $(sIdNameFormNuevoTransporte).submit(function(){
        agregarTransporte();
        return false;
    });
	
    $(sIdFormModificarTransporte).submit(function(){
        modificarTransporte();
        return false;
    });
		
		
		
    /*Configuracion del dialogo de agregar nuevo elemento*/
    oDialogAgregarTransporte = 	$( sIdDialgoAgregarTransporte).dialog({
        autoOpen:false,
        resizable: false,
        modal: true,
        height:pDialogHeigthTransporte,
        width:pDialogWidthTransporte,
        buttons: {
            "Aceptar": function() {
					
                agregarTransporte();
            },
            "Cancelar":function(){
                fnHideErrores('#transporteFormNuevo');
                limpiarFormulario('#transporteFormNuevo');
                $( this ).dialog( "close" );
            }
        },
        open: function(event, ui) { 
            fnHideErrores('#transporteFormNuevo');
            limpiarFormulario('#transporteFormNuevo');
        }

    });
		
		
    //fnHideErrores
		
		
    /*Configuracion del dialogo de confirmar*/
    oDialogEliminarTransporte = 	$( sIdDialogEliminarTransporte ).dialog({
        autoOpen:false,
        resizable: false,
        height:200,
        modal: true,
        buttons: {
            "Eliminar": function(data) {
                                    
                fnHideErrores(sIdDialogEliminarTransporte);
					
                /*Obtenemos el radio seleccionado*/
                var obRowSelected = fnGetRowSelected(dtTransporte);
                var idTransporte = obRowSelected.cveEquipoTransporte;
                
                // por alguna razon esto ya no funciona despues de la integracion
                //var idSolicitud = $('#transporteFormPaginar:hidden #cveIdSolicitud').val();
                var idSolicitud = $('#transporteFormPaginar #cveIdSolicitud').val();                        
					
                var sSource = 'transportes/eliminar';
                $.getJSON(sSource,{
                    idTransporte: idTransporte,  
                    idSolicitud: idSolicitud
                } , function(data) {
                    //refrescar el data table
                    dtTransporte.fnDraw();
                                                
                    oDialogEliminarTransporte.dialog("close");
                }).error(function(data) {
                    fnProcesarErrores(data, sIdDialogEliminarTransporte);
                });
            },
            'Cancelar': function() {
                fnHideErrores(sIdDialogEliminarTransporte);
                $( this ).dialog( "close" );
            }
        }
    });

    /*Configuracion del dialogo del mensaje de aviso de registro no seleccionado*/
		
    oDialogErrorSinSeleccionTransporte =  $( sIdDialogErrorSinSeleccionTransporte ).dialog({
        autoOpen:false,
        resizable: false,
        height:140,
        modal: true,
        buttons: {
            'Aceptar': function() {
                $( this ).dialog( "close" );
            }
        }
    });
		
		

		
    /*Configuracion del dialogo de modificar  elemento*/
    oDialogModificarTransporte = 	$( sIdDialogModificarTransporte).dialog({
        autoOpen:false,
        resizable: false,
        modal: true,	
        height:pDialogHeigthTransporte,
        width:pDialogWidthTransporte,
        buttons: {
            "Aceptar": function() {
                modificarTransporte();
            },
            "Cancelar":function(){
                fnHideErrores(sIdDialogModificarTransporte);
                $(this).dialog('close');
            }
        },
        open: function(event, ui) { 
            fnHideErrores(sIdDialogModificarTransporte);

        }
    });

    /* Configuracion del data table de transportes*/
    dtTransporte = $('#tbTransporte').dataTable({
        bJQueryUI : false,
        bFilter : false,
        bInfo:false,
        bSort: false,
        "bPaginate": false,
        "bAutoWidth" : false,
        "bServerSide" : true,
        //"sPaginationType": "full_numbers",
        "aoColumns" : [ 
        { 
            "sTitle" : "Descripcion",
            "mDataProp" : "desNombre",
            "sClass":"dtJustifyClassColumn"
        } ,
{ 
            "sTitle" : "Capacidad/Potencia",
            "mDataProp" : "desCapacidadPotencia",
            "sClass":"dtJustifyClassColumn"
        } ,
{ 
            "sTitle" : "Tipo Combustible",
            "mDataProp" : "desTipoCombustible",
            "sClass":"dtJustifyClassColumn"
        },
        { 
            "sTitle" : "Uso",
            "mDataProp" : "desUso",
            "sClass":"dtJustifyClassColumn"
        },
        { 
            "sTitle" : "Unidades",
            "mDataProp" : "numUnidades",
            "sClass":"dtJustifyClassColumn"
        },
        { 
            "sTitle" : "Solicitud",
            "mDataProp" : "cveIdSolicitud",
            "sClass":"dtJustifyClassColumn"
        }
        ],

        "bProcessing" : true,
        "sAjaxSource" : 'transportes/paginar',
        "fnServerData" : function(sSource, aoData, fnCallback) {
            aoData.push({
                "name" : "sSearch",
                "value" : ''
            });
						
            var wrapper = new Object();
            wrapper.aoData = aoData;
            var oForm = $(sIdNameFormPaginarTransporte).serializeObject(true);
            wrapper.oForm = oForm;
						
            $.postJSON(sSource, wrapper, function(data) {
                fnCallback(data);
            });
						
        }
    });

    /* Add a click handler to the rows - this could be used as a callback */
    $("#tbTransporte tbody").click(function(event) {
			
        $(dtTransporte.fnSettings().aoData).each(function (){ 
            $(this.nTr).removeClass('row_selected'); 
        });
			
        $(event.target.parentNode).addClass('row_selected');
    });

		
		
		
		
});
	
	
	
/*Funcion para agregar el elemento nuevo de transportes*/
var fnOpenDialogNuevoTransporte = function(){
    oDialogAgregarTransporte.dialog('open');
}
	
/*Funcion para abrir el dialogo de eliminar*/
var fnOpenDialogEliminarTransporte = function(){
    //Validamos que exista un elemento seleccionado.
    if(fnValidaRegistroSeleccionado(dtTransporte)){
        oDialogEliminarTransporte.dialog('open');
    }else{
        //Mostramos mensaje de error
        fnDialogErrorSinSeleccionTransporte();
    }
		
		
		
}
	

	

	
	
/*FUncion para mostrar el mensaje de error cuando no existe un registro seleccionado*/
var fnDialogErrorSinSeleccionTransporte = function(){
    oDialogErrorSinSeleccionTransporte.dialog('open');
}
	
/*Funcion para obtener el elemento seleccionado*/
var fnGetElementoTransporte = function(equipoTransporteCve){

    var sSource = 'transportes/get';
		
    $.getJSON(sSource,{
        equipoTransporteCve: equipoTransporteCve
    } , function(data) {
			
        $('#transporteFormModificar:hidden #cveIdSolicitud').val( data.cveIdSolicitud);			
        $('#transporteFormModificar  #desNombre').val( data.desNombre);
        $('#transporteFormModificar #numUnidades').val( data.numUnidades);
        $('#transporteFormModificar #desUso').val( data.desUso);
        $('#transporteFormModificar #desCapacidadPotencia').val( data.desCapacidadPotencia);
        $('#transporteFormModificar #cveIdTipoCombustible').val( data.cveIdTipoCombustible);
        $('#transporteFormModificar #cveEquipoTransporte').val( data.cveEquipoTransporte);
			 
        /*Abrimos el dialogo*/
        oDialogModificarTransporte.dialog('open');
			 
    });
		
		
}
	
	
/*Funcion para abrir el dialogo de modificar*/
	
	
var fnOpenDialogModificarTransporte = function(){
		
    //Validamos que exista un elemento seleccionado.
    if(fnValidaRegistroSeleccionado( dtTransporte )){
        /*Obtenemos el radio seleccionado*/
        var obRowSelected = fnGetRowSelected(dtTransporte);
        var equipoTransporteCve = obRowSelected.cveEquipoTransporte;
			
        fnGetElementoTransporte(equipoTransporteCve);
			
    }else{
        //Mostramos mensaje de error
        fnDialogErrorSinSeleccionTransporte();
    }
		
		
		

}

/*
 * funciones para navegacion
 */
function fnTransporteGoBack(){
    $("#form-transportes-back").submit();
}
	
function fnTransporteGoAhead(){
    $("#form-transportes-forward").submit();
}
	
	
	
function agregarTransporte( ){
		
		
    fnHideErrores(sIdNameFormNuevoTransporte);
		
    /*la invocacion a guardar un nuevo elemento*/
    var idSolicitud = $('#transporteFormPaginar:hidden #cveIdSolicitud').val();
    $('#transporteFormNuevo:hidden #cveIdSolicitud').val(idSolicitud);
                
    var oForm = $(sIdNameFormNuevoTransporte).serializeObject(true);
    var sSource = 'transportes/agregar';
		
    $.postJSON(sSource, oForm, function(data) {
			
        fnHideErrores(sIdNameFormNuevoTransporte);
        //refrescar el data table
        dtTransporte.fnDraw();
        //cerramos el dialogo
        oDialogAgregarTransporte.dialog( "close" );
			
    }).error(function(data) {
        fnProcesarErrores(data, sIdNameFormNuevoTransporte);
    });
		
}
	
	
	
function modificarTransporte(){
		
    fnHideErrores(sIdFormModificarTransporte);
		
    /*la invocacion a modificar el elemento*/
    var oForm = $(sIdFormModificarTransporte).serializeObject(true);
    var sSource = 'transportes/modificar';
		
    $.postJSON(sSource, oForm, function(data) {			
        //refrescar el data table
        dtTransporte.fnDraw();
                        
        fnHideErrores(sIdDialogModificarTransporte);
                        
        $( oDialogModificarTransporte ).dialog( "close" );
    }).error(function(data) {
        fnProcesarErrores(data, sIdDialogModificarTransporte);
    });
}
