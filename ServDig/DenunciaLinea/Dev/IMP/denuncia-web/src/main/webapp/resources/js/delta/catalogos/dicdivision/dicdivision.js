/**
 * JS para el soporte del catalogo de dicdivision.
 */


var idDataTable     = "#dtDicDivision";
var idDgNuevo         = "#dgDicDivisionNuevo";
var idDgModificar     = "#dgDicDivisionModificar";
var idDgBorrar         = "#dgDicDivisionBorrar";
var idDgAyuda        = "#dgDicDivisionAyuda";

// Objeto del DataTable
var oDtDicDivision;
// Dialogos
var oDgNuevo;
var oDgBorrar;
var oDgModificar;
var oDgAyuda;

/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */
$(document).ready(function() {

     // Fecha de Inicio y Termino
     $( "#fecRegistroBaja" ).datepicker( { dateFormat: 'yy-mm-dd' });

    /**
     * Inicializacion del data table
     */
    oDtDicDivision = $(idDataTable).dataTable({
        bJQueryUI : true,
        bFilter : false,
        bInfo:true,
        bSort: false,
        "bPaginate": true,
        "bAutoWidth" : false,
        "bServerSide" :    true,
        "aoColumns" : [ {
            fnRender :function(oObj){
                var retVal = '<input type="radio" value="' +
                oObj.aData['cveIdDivision'] +'" id="radioTable" class="radioDicDivision" name="radio" onclick=""/> ';
                return retVal;
            }, 
            aTargets: [0]
            },
                {
                "sTitle" : "clave división",
                "mDataProp" : "cveIdDivision",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "Descripción División",
                "mDataProp" : "desDivision",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "Numero de división",
                "mDataProp" : "numDivision",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "Fecha de alta",
                "mDataProp" : "fecRegistroAlta",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "Fecha de baja",
                "mDataProp" : "fecRegistroBaja",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "Fecha de actualización",
                "mDataProp" : "fecRegistroActualizado",
                "sClass": "dtCenterClassColumn"
                }
            ],"bProcessing" : true,
            "sAjaxSource" : 'dicdivision/paginar.do',
            "fnServerData" : function(sSource, aoData, fnCallback) {
                aoData.push({
                    "name" : "sSearch",
                    "value" : $('#desDivision').val()
                });

                var wrapper = new Object();
                wrapper.aoData = aoData;

                var oForm = $("#dicdivisionFiltrosForm").serializeObject(true);   
                wrapper.oForm = oForm;
                
                $.postJSON(sSource, wrapper, function(data) {
                    fnCallback(data);
                });
            }
        });
    
    
    // Dialog de Elemento Nuevo            
     oDgNuevo = $(idDgNuevo).dialog({
        autoOpen: false,
        modal:true,
        resizable:false,
        width: 600,
        beforeClose :function(event,ui){
            limpiarFormulario("#dicdivisionForm");
        },
        buttons: {
            "Aceptar": function() { 
                var dicdivision = $("#dicdivisionForm").serializeObject(true);
                $.postJSON("dicdivision/agregar.do", dicdivision, function(data) {
                    alert("El nuevo registro ha sido agregado...");
                    inicializaPosicionPaginador();                    
                }).error(function(data){ 
                    alert("error" + data);
                }).complete(function(){
                    //Código para el complete
                });
                $(this).dialog("close"); 
            }, 
            "Cancelar": function() { 
                $(this).dialog("close"); 
            } 
        }
    });
    
    
    // Dialog de Elemento a Modificar            
     oDgModificar = $(idDgModificar).dialog({
        autoOpen: false,
        modal:true,
        resizable:false,
        width: 600,
        beforeClose :function(event,ui){
            limpiarFormulario("#dicdivisionFormModificar");
        },        
        buttons: {
            "Aceptar": function() { 
                var dicdivision = $("#dicdivisionFormModificar").serializeObject(true);
                $.postJSON("dicdivision/modificar.do", dicdivision, function(data) { 
                    alert("El registro ha sido modificado...");
                    inicializaPosicionPaginador();                    
                }).error(function(data){ 
                    alert("error" + data);
                }).complete(function(){
                
                //Código para el complete
                });
                $(this).dialog("close"); 
            }, 
            "Cancelar": function() { 
                $(this).dialog("close"); 
            } 
        }
    });
    

    // Dialog de Elemento a Borrar            
     oDgBorrar = $(idDgBorrar).dialog({
        autoOpen: false,
        modal:true,
        resizable:false,
        height: 140,
        buttons: {
            "Aceptar": function() { 
                var dicdivision = $("#dicdivisionFormBorrar").serializeObject(true);
                $.postJSON("dicdivision/eliminar.do", dicdivision, function(data) {
                    alert("El registro ha sido eliminado...");
                    inicializaPosicionPaginador();                    
                }).error(function(data){ 
                    alert("error" + data);
                }).complete(function(){
                    //Instrucciones para el complete
                });
                $(this).dialog("close"); 
            }, 
            "Cancelar": function() { 
                $(this).dialog("close"); 
            } 
        }
    });
     
     
    // Dialog de Elemento Ayuda        
    oDgAyuda = $(idDgAyuda).dialog({
        modal:        true,
        buttons: {
            "Aceptar": function() {
                $(this).dialog("close"); 
            }
        }
    });         
     
});//$(document).ready(function()

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtDicDivision.fnDisplayStart(0);
}


function nuevo(){
    oDgNuevo.dialog('open');
}

function paginar(){
	oDtDicDivision.fnDraw();
}

function modificar(){
    var idDicDivision = $('#:checked').val();
    var sDicDivision = '{"cveIdDivision":'+idDicDivision+'}';
    var dicdivision = jQuery.parseJSON(sDicDivision);
    // Buscamos el elemento
    $.postJSON("dicdivision/consultaPorClave.do", dicdivision, function(data) {
        $('#wrapperDialogModif,#dicdivisionFormModificar,#cveIdDivision').val(data.cveIdDivision);
        $('#wrapperDialogModif,#dicdivisionFormModificar,#desDivision').val(data.desDivision);
        $('#wrapperDialogModif,#dicdivisionFormModificar,#numDivision').val(data.numDivision);
        $('#wrapperDialogModif,#dicdivisionFormModificar,#fecRegistroAlta').val(data.fecRegistroAlta);
        $('#wrapperDialogModif,#dicdivisionFormModificar,#fecRegistroBaja').val(data.fecRegistroBaja);
        $('#wrapperDialogModif,#dicdivisionFormModificar,#fecRegistroActualizado').val(data.fecRegistroActualizado);
        oDgModificar.dialog('open');
    }).error(function(data){ 
        alert("error" + data);
    }).complete(function(){
        //Instrucciones para el 'complete'
    });
}

function borrar(){
    var idDicDivision = $('#:checked').val();
    var sDicDivision = '{"cveIdDivision":'+idDicDivision+'}';
    var dicdivision = jQuery.parseJSON(sDicDivision);
    
    // Buscamos el elemento
    $.postJSON("dicdivision/consultaPorClave.do", dicdivision, function(data) {
        $('#wrapperDialogBorrar,#dicdivisionFormBorrar,#cveIdDivision').val(data.cveIdDivision);
        oDgBorrar.dialog('open');
    }).error(function(data){ 
        alert("error" + data);
    }).complete(function(){
        //Instrucciones para el 'complete'
    });
}


function ayuda(){
    alert("ayuda");
    oDgAyuda.dialog('open');
}
