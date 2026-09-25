/**
* Java Script de maquinariaEquipos
*
**/


//Objeto de datable de maquinariaEquipos
var dtMaquinariaEquipos;
var sIdNameFormPaginarMaquinariaEquipos="#maquinariaEquipoFormPaginar";
var sIdNameFormNuevoMaquinariaEquipos="#maquinariaEquipoFormNuevo";



var sIdDialgoAgregarMaquinariaEquipos = "#dgNuevoMaquinariaEquipos";
var oDialogAgregarMaquinariaEquipos;


var sIdDialogEliminarMaquinariaEquipos = "#dgEliminarMaquinariaEquipos";
var oDialogEliminarMaquinariaEquipos;

var sIdDialogErrorSinSeleccionMaquinariaEquipos = "#dgErrorSinSeleccionMaquinariaEquipos";
var oDialogErrorSinSeleccionMaquinariaEquipos;


var sIdFormModificarMaquinariaEquipos = '#maquinariaEquipoFormModificar';



var sIdDialogModificarMaquinariaEquipos = "#dgModificarMaquinariaEquipos";
var oDialogModificarMaquinariaEquipos;


var pDialogHeigthMaquinariaEquipos = 450;
var pDialogWidthMaquinariaEquipos = 450;



/** Seccion de codigo a ejectuar cuando el DOM este listo **/
$(function() {
	
	// validamos que solo se ingresen numeros (al agregar nuevo elemento)
    $("#maquinariaEquipoFormNuevo #numUnidades").keydown(function(event) {
    	
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
    $("#maquinariaEquipoFormModificar #numUnidades").keydown(function(event) {
    	
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
		
    $(sIdNameFormNuevoMaquinariaEquipos).submit(function(){
        agregarMaquinariaEquipos();
        return false;
    });
	
    $(sIdFormModificarMaquinariaEquipos).submit(function(){
        modificarMaquinariaEquipos();
        return false;
    });
		
		
		
    /*Configuracion del dialogo de agregar nuevo elemento*/
    oDialogAgregarMaquinariaEquipos = 	$( sIdDialgoAgregarMaquinariaEquipos).dialog({
        autoOpen:false,
        resizable: false,
        modal: true,
        height:pDialogHeigthMaquinariaEquipos,
        width:pDialogWidthMaquinariaEquipos,
        buttons: {
            "Aceptar": function() {
					
                agregarMaquinariaEquipos();
            },
            "Cancelar":function(){
                fnHideErrores('#maquinariaEquipoFormNuevo');
                limpiarFormulario('#maquinariaEquipoFormNuevo');
                $( this ).dialog( "close" );
            }
        },
        open: function(event, ui) { 
            fnHideErrores('#maquinariaEquipoFormNuevo');
            limpiarFormulario('#maquinariaEquipoFormNuevo');
        }

    });
		
		
    //fnHideErrores
		
		
    /*Configuracion del dialogo de confirmar*/
    oDialogEliminarMaquinariaEquipos = 	$( sIdDialogEliminarMaquinariaEquipos ).dialog({
        autoOpen:false,
        resizable: false,
        height:200,
        modal: true,
        buttons: {
            "Eliminar": function(data) {
                                    
                fnHideErrores(sIdDialogEliminarMaquinariaEquipos);
					
                /*Obtenemos el radio seleccionado*/
                var obRowSelected = fnGetRowSelected(dtMaquinariaEquipos);
                var idMaquinariaEquipo = obRowSelected.cveIdMaquinariaEquipo;
                                        
                //var idSolicitud = $('#maquinariaEquipoFormPaginar:hidden #cveIdSolicitud').val();
                var idSolicitud = obRowSelected.cveIdSolicitud;
                                        
					
                var sSource = 'maquinariaEquipos/eliminar';
                $.getJSON(sSource,{
                    idMaquinariaEquipo: idMaquinariaEquipo,  
                    idSolicitud: idSolicitud
                } , function(data) {
                    //refrescar el data table
                    dtMaquinariaEquipos.fnDraw();
                                                
                    oDialogEliminarMaquinariaEquipos.dialog("close");
                }).error(function(data) {
                    fnProcesarErrores(data, sIdDialogEliminarMaquinariaEquipos);
                });
            },
            'Cancelar': function() {
                fnHideErrores(sIdDialogEliminarMaquinariaEquipos);
                $( this ).dialog( "close" );
            }
        }
    });

    /*Configuracion del dialogo del mensaje de aviso de registro no seleccionado*/
		
    oDialogErrorSinSeleccionMaquinariaEquipos =  $( sIdDialogErrorSinSeleccionMaquinariaEquipos ).dialog({
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
    oDialogModificarMaquinariaEquipos = 	$( sIdDialogModificarMaquinariaEquipos).dialog({
        autoOpen:false,
        resizable: false,
        modal: true,	
        height:pDialogHeigthMaquinariaEquipos,
        width:pDialogWidthMaquinariaEquipos,
        buttons: {
            "Aceptar": function() {
                modificarMaquinariaEquipos();
            },
            "Cancelar":function(){
                fnHideErrores(sIdDialogModificarMaquinariaEquipos);
                $(this).dialog('close');
            }
        },
        open: function(event, ui) { 
            fnHideErrores(sIdDialogModificarMaquinariaEquipos);

        }
    });

    /* Configuracion del data table de maquinariaEquipos*/
    dtMaquinariaEquipos = $('#tbMaquinariaEquipos').dataTable({
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
									"sTitle" : " Num. de unidades ",
									"mDataProp" : "numUnidades",
									"sClass" : "dtJustifyClassColumn"
								},
								{
									"sTitle" : " Nombre ",
									"mDataProp" : "desNombre",
									"sClass" : "dtJustifyClassColumn"
								},
								{
									"sTitle" : " Uso ",
									"mDataProp" : "desUso",
									"sClass" : "dtJustifyClassColumn"
								},
								{
									"sTitle" : " Tipo Maquinaria ",
									"mDataProp" : "desTipoMaquinariaEquipo",
									"sClass" : "dtJustifyClassColumn"
								}, {
									"sTitle" : " Capacidad/Potencia ",
									"mDataProp" : "desCapacidadPotencia",
									"sClass" : "dtJustifyClassColumn"
								} ],

        "bProcessing" : true,
        "sAjaxSource" : 'maquinariaEquipos/paginar',
        "fnServerData" : function(sSource, aoData, fnCallback) {
            aoData.push({
                "name" : "sSearch",
                "value" : ''
            });
						
            var wrapper = new Object();
            wrapper.aoData = aoData;
            var oForm = $(sIdNameFormPaginarMaquinariaEquipos).serializeObject(true);
            wrapper.oForm = oForm;
						
            $.postJSON(sSource, wrapper, function(data) {
                fnCallback(data);
            });
						
        }
    });

    /* Add a click handler to the rows - this could be used as a callback */
    $("#tbMaquinariaEquipos tbody").click(function(event) {
			
        $(dtMaquinariaEquipos.fnSettings().aoData).each(function (){ 
            $(this.nTr).removeClass('row_selected'); 
        });
			
        $(event.target.parentNode).addClass('row_selected');
    });

		
		
		
		
});
	
	
	
/*Funcion para agregar el elemento nuevo de maquinariaEquipos*/
var fnOpenDialogNuevoMaquinariaEquipos = function(){
    oDialogAgregarMaquinariaEquipos.dialog('open');
}
	
/*Funcion para abrir el dialogo de eliminar*/
var fnOpenDialogEliminarMaquinariaEquipos = function(){
    //Validamos que exista un elemento seleccionado.
    if(fnValidaRegistroSeleccionado(dtMaquinariaEquipos)){
        oDialogEliminarMaquinariaEquipos.dialog('open');
    }else{
        //Mostramos mensaje de error
        fnDialogErrorSinSeleccionMaquinariaEquipos();
    }
		
		
		
}
	

	

	
	
/*FUncion para mostrar el mensaje de error cuando no existe un registro seleccionado*/
var fnDialogErrorSinSeleccionMaquinariaEquipos = function(){
    oDialogErrorSinSeleccionMaquinariaEquipos.dialog('open');
}
	
/*Funcion para obtener el elemento seleccionado*/
var fnGetElementoMaquinariaEquipos = function(idMaquinariaEquipo){

    var sSource = 'maquinariaEquipos/get';
		
    $.getJSON(sSource,{
        idMaquinariaEquipo: idMaquinariaEquipo
    } , function(data) {
        
        $('#maquinariaEquipoFormModificar:hidden #cveIdSolicitud').val( data.cveIdSolicitud);
        $('#maquinariaEquipoFormModificar  #cveIdTipoMaquinariaEquipo').val( data.cveIdTipoMaquinariaEquipo);
        $('#maquinariaEquipoFormModificar  #numUnidades').val( data.numUnidades);
        $('#maquinariaEquipoFormModificar  #desNombre').val( data.desNombre);
        $('#maquinariaEquipoFormModificar  #desUso').val( data.desUso);
        $('#maquinariaEquipoFormModificar  #desCapacidadPotencia').val( data.desCapacidadPotencia);
        $('#maquinariaEquipoFormModificar:hidden #cveIdMaquinariaEquipo').val( data.cveIdMaquinariaEquipo);

			 
        /*Abrimos el dialogo*/
        oDialogModificarMaquinariaEquipos.dialog('open');
			 
    });
		
		
}
	
	
/*Funcion para abrir el dialogo de modificar*/
	
	
var fnOpenDialogModificarMaquinariaEquipos = function(){
		
    //Validamos que exista un elemento seleccionado.
    if(fnValidaRegistroSeleccionado( dtMaquinariaEquipos )){
        /*Obtenemos el radio seleccionado*/
        var obRowSelected = fnGetRowSelected(dtMaquinariaEquipos);
        var idMaquinariaEquipo = obRowSelected.cveIdMaquinariaEquipo;
			
        fnGetElementoMaquinariaEquipos(idMaquinariaEquipo);
			
    }else{
        //Mostramos mensaje de error
        fnDialogErrorSinSeleccionMaquinariaEquipos();
    }
		
		
		

}

/*
 * funciones para navegacion
 */
function fnMaquinariaEquiposGoBack(){
    $("#form-maquinaria-back").submit();
}
	
function fnMaquinariaEquiposGoAhead(){
    $("#form-maquinaria-forward").submit();
}
	
	
	
function agregarMaquinariaEquipos( ){
		
		
    fnHideErrores(sIdNameFormNuevoMaquinariaEquipos);
		
    /*la invocacion a guardar un nuevo elemento*/
    var idSolicitud = $('#maquinariaEquipoFormPaginar:hidden #cveIdSolicitud').val();
    $('#maquinariaEquipoFormNuevo:hidden #cveIdSolicitud').val(idSolicitud);
    var oForm = $(sIdNameFormNuevoMaquinariaEquipos).serializeObject(true);
    var sSource = 'maquinariaEquipos/agregar';
		
    $.postJSON(sSource, oForm, function(data) {
			
        fnHideErrores(sIdNameFormNuevoMaquinariaEquipos);
        //refrescar el data table
        dtMaquinariaEquipos.fnDraw();
        //cerramos el dialogo
        oDialogAgregarMaquinariaEquipos.dialog( "close" );
			
    }).error(function(data) {
        fnProcesarErrores(data, sIdNameFormNuevoMaquinariaEquipos);
    });
		
}
	
	
	
function modificarMaquinariaEquipos(){
		
    fnHideErrores(sIdFormModificarMaquinariaEquipos);
		
    /*la invocacion a modificar el elemento*/
    var oForm = $(sIdFormModificarMaquinariaEquipos).serializeObject(true);
    var sSource = 'maquinariaEquipos/modificar';
		
    $.postJSON(sSource, oForm, function(data) {			
        //refrescar el data table
        dtMaquinariaEquipos.fnDraw();
                        
        fnHideErrores(sIdDialogModificarMaquinariaEquipos);
                        
        $( oDialogModificarMaquinariaEquipos ).dialog( "close" );
    }).error(function(data) {
        fnProcesarErrores(data, sIdDialogModificarMaquinariaEquipos);
    });
}
