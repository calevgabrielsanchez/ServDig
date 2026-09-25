/**
 * Java Script de las materias primas
 *
 **/


//Objeto de datable de las materias primas
var dtMateriaPrima;
var sIdNameFormPaginarMateriasPrimas="#materiaprimaFormPaginar";
var sIdNameFormNuevoMateriasPrimas="#materiaprimaFormNuevo";



var sIdDialgoAgregarMateriasPrimas = "#dgNuevoMateriasPrimas";
var oDialogAgregarMateriasPrimas;


var sIdDialogEliminarMateriasPrimas = "#dgEliminarMateriasPrimas";
var oDialogEliminarMateriasPrimas;

var sIdDialogErrorSinSeleccionMateriasPrimas = "#dgErrorSinSeleccionMateriasPrimas";
var oDialogErrorSinSeleccionMateriasPrimas;


var sIdFormModificarMateriasPrimas = '#materiaprimaFormModificar';



var sIdDialogModificarMateriasPrimas = "#dgModificarMateriasPrimas";
var oDialogModificarMateriasPrimas;


var pDialogHeigthMateriasPrimas = 450;
var pDialogWidthMateriasPrimas = 450;




/** Seccion de codigo a ejectuar cuando el DOM este listo **/
$(function() {
            
            
    /*
     * Configuracion de los submits de las formas
     */
		
    $(sIdNameFormNuevoMateriasPrimas).submit(function(){
        agregarMateriasPrimas();
        return false;
    });
	
    $(sIdFormModificarMateriasPrimas).submit(function(){
        modificarMateriasPrimas();
        return false;
    });

		
		
		
		
    /*Configuracion del dialogo de agregar nuevo elemento*/
    oDialogAgregarMateriasPrimas = 	$( sIdDialgoAgregarMateriasPrimas).dialog({
        autoOpen:false,
        resizable: false,
        modal: true,
        height: pDialogHeigthMateriasPrimas,
        width: pDialogWidthMateriasPrimas,
        buttons: {
            "Aceptar": function() {
                agregarMateriasPrimas();
            },
            "Cancelar": function() {
                fnHideErrores('#materiaprimaFormNuevo');
                limpiarFormulario('#materiaprimaFormNuevo');
                $( this ).dialog( "close" );
            }
        },
        open: function(event, ui) { 
            fnHideErrores('#materiaprimaFormNuevo');
            limpiarFormulario('#materiaprimaFormNuevo');
        }
    });
		
		
    /*Configuracion del dialogo de confirmar*/
    oDialogEliminarMateriasPrimas = 	$( sIdDialogEliminarMateriasPrimas ).dialog({
        autoOpen:false,
        resizable: false,
        height:200,
        modal: true,
        buttons: {
            "Eliminar": function() {
                                    
                /* limpiamos mensajes de error que pudieran estar presentes*/
                fnHideErrores(sIdDialogEliminarMateriasPrimas);
					
                /*Obtenemos la row seleccionada*/
                var obRowSelected = fnGetRowSelected(dtMateriaPrima);
                
                var idMateriaPrima = obRowSelected.cveIdMateriaPrimaMaterial;
                var idSolicitud = $('#materiaprimaFormPaginar #cveIdSolicitud').val();
                                         
                var sSource = 'materiasprimas/eliminar';
                $.getJSON(sSource,{
                    idMateriaPrima: idMateriaPrima,
                    idSolicitud: idSolicitud
                } , function(data) {
                    
                    //refrescar el data table antes de visualizarlo
                    dtMateriaPrima.fnDraw();
                                            
                    oDialogEliminarMateriasPrimas.dialog("close");
                    
                }).error(function(data) {
                    fnProcesarErrores(data, sIdDialogEliminarMateriasPrimas);
                });
					
            },
            'Cancelar': function() {
                fnHideErrores(sIdDialogEliminarMateriasPrimas);
                $( this ).dialog( "close" );
            }
        }
    });

    /*Configuracion del dialogo del mensaje de aviso de registro no seleccionado*/
		
    oDialogErrorSinSeleccionMateriasPrimas =  $( sIdDialogErrorSinSeleccionMateriasPrimas ).dialog({
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
    oDialogModificarMateriasPrimas = 	$( sIdDialogModificarMateriasPrimas).dialog({
        autoOpen:false,
        resizable: false,
        modal: true,
        height:pDialogHeigthMateriasPrimas,
        width:pDialogWidthMateriasPrimas,
        buttons: {
            "Aceptar": function() {
                /*la invocacion a modificar el elemento*/
                modificarMateriaPrima();
            },
            'Cancelar': function() {
                fnHideErrores(sIdDialogModificarMateriasPrimas);
                $( this ).dialog( "close" );
            }
        },
        open: function(event, ui) { 
            fnHideErrores(sIdDialogModificarMateriasPrimas);
        }
    });

    /* Configuracion del data table de materias primas*/
    dtMateriaPrima = $('#tbMateriasPrimas').dataTable({
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
            "sTitle" : "48. Principales materias primas y materiales utilizados.",
            "mDataProp" : "desMateriaPrimaMaterial",
            "sClass":"dtJustifyClassColumn"
        }
        ],

        "bProcessing" : true,
        "sAjaxSource" : 'materiasprimas/paginar',
        "fnServerData" : function(sSource, aoData, fnCallback) {
            aoData.push({
                "name" : "sSearch",
                "value" : ''
            });
						
            var wrapper = new Object();
            wrapper.aoData = aoData;
            var oForm = $(sIdNameFormPaginarMateriasPrimas).serializeObject(true);
            wrapper.oForm = oForm;
						
            $.postJSON(sSource, wrapper, function(data) {
                fnCallback(data);
            });
						
        }
    });
    
    
    /* Add a click handler to the rows - this could be used as a callback */
    $("#tbMateriasPrimas tbody").click(function(event) {
			
        $(dtMateriaPrima.fnSettings().aoData).each(function (){ 
            $(this.nTr).removeClass('row_selected'); 
        });
			
        $(event.target.parentNode).addClass('row_selected');
    });

		
		
		
		
		
});
	
	
	
/*Funcion para agregar el elemento nuevo de materias primas*/
var fnOpenDialogNuevoMateriasPrimas = function(){
    oDialogAgregarMateriasPrimas.dialog('open');
}
	
/*Funcion para abrir el dialogo de eliminar*/
var fnOpenDialogEliminarMateriasPrimas = function(){
		
		
    //Validamos que exista un elemento seleccionado.
    if(fnValidaRegistroSeleccionado(dtMateriaPrima)){
        oDialogEliminarMateriasPrimas.dialog('open');
    }else{
        //Mostramos mensaje de error
        fnDialogErrorSinSeleccionMateriasPrimas();
    }
		
		
		
}
	
	
/*FUncion para mostrar el mensaje de error cuando no existe un registro seleccionado*/
var fnDialogErrorSinSeleccionMateriasPrimas = function(){
    oDialogErrorSinSeleccionMateriasPrimas.dialog('open');
}
	
/*Funcion para obtener el elemento seleccionado*/
var fnGetElementoMateriasPrimas = function(idMateriaPrima){

    var sSource = 'materiasprimas/get';
		
    $.getJSON(sSource,{
        idMateriaPrima: idMateriaPrima
    } , function(data) {
			
        $('#materiaprimaFormModificar:hidden #cveIdSolicitud').val( data.cveIdSolicitud);
        $('#materiaprimaFormModificar  #desMateriaPrimaMaterial').val( data.desMateriaPrimaMaterial);
        $('#materiaprimaFormModificar:hidden #cveIdMateriaPrimaMaterial').val( data.cveIdMateriaPrimaMaterial);
			 
        /*Abrimos el dialogo*/
        oDialogModificarMateriasPrimas.dialog('open');
			 
    });
		
		
}
	
	
/*Funcion para abrir el dialogo de modificar*/
	
	
var fnOpenDialogModificarMateriasPrimas = function(){
		
		
		
    //Validamos que exista un elemento seleccionado.
    if(fnValidaRegistroSeleccionado( dtMateriaPrima)){
        
        var obRowSelected = fnGetRowSelected(dtMateriaPrima);
        var idMateriaPrima = obRowSelected.cveIdMateriaPrimaMaterial;
			
        fnGetElementoMateriasPrimas(idMateriaPrima);
        		
    }else{
        //Mostramos mensaje de error
        fnDialogErrorSinSeleccionMateriasPrimas();
    }
		
		
		
}
	
	
/*
* funciones para navegacion
*/
		
		
		
function fnMateriasPrimasGoBack(){
    $("#form-materiasprimas-back").submit();
}
		
function fnMateriasPrimasGoAhead(){
    $("#form-materiasprimas-forward").submit();
}

function agregarMateriasPrimas( ){
		
    fnHideErrores(sIdNameFormNuevoMateriasPrimas);
					
    /*la invocacion a guardar un nuevo elemento*/
    var idSolicitud = $('#materiaprimaFormPaginar:hidden #cveIdSolicitud').val();
    $('#materiaprimaFormNuevo:hidden #cveIdSolicitud').val(idSolicitud);
    var oForm = $(sIdNameFormNuevoMateriasPrimas).serializeObject(true);
    var sSource = 'materiasprimas/agregar';
    $.postJSON(sSource, oForm, function(data) {
        
        fnHideErrores('#materiaprimaFormNuevo');
        //refrescar el data table
        dtMateriaPrima.fnDraw();
        //cerramos el dialogo
        oDialogAgregarMateriasPrimas.dialog( "close" );
						
    }).error(function(data) {
        fnProcesarErrores(data, sIdNameFormNuevoMateriasPrimas);
    });
		
}

function modificarMateriaPrima(){

    fnHideErrores(sIdFormModificarMateriasPrimas);
    
    var oForm = $(sIdFormModificarMateriasPrimas).serializeObject(true);
    var sSource = 'materiasprimas/modificar';
					
    $.postJSON(sSource, oForm, function(data) {
        //refrescar el data table
        dtMateriaPrima.fnDraw();
        
        fnHideErrores(sIdDialogModificarMateriasPrimas);
        
        $(oDialogModificarMateriasPrimas).dialog("close");
    }).error(function(data) {
        fnProcesarErrores(data, sIdDialogModificarMateriasPrimas);
    });
					
					
    

}