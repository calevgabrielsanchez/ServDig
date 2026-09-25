/**
 * JS para el soporte del catalogo de dicfraccion.
 */


var idDataTable     = "#dtDicFraccion";
var idDgNuevo         = "#dgDicFraccionNuevo";
var idDgModificar     = "#dgDicFraccionModificar";
var idDgBorrar         = "#dgDicFraccionBorrar";
var idDgAyuda        = "#dgDicFraccionAyuda";

// Objeto del DataTable
var oDtDicFraccion;
// Dialogos
var oDgNuevo;
var oDgBorrar;
var oDgModificar;
var oDgAyuda;

var divisionCtrl = {
		cargarComboDivisiones: function() {
			var url = "dicdivision/cargarDivisionesActivas.do";
			$.getJSON(url , function (objData){
				 var options = '<option value="0" >--Por favor seleccione--</option>';
				for (var i = 0; i < objData.length; i++) {
			        options += '<option value="' + objData[i].cveIdDivision + '">' + objData[i].desDivision + '</option>';
			      }
				 $("select#dicGrupo\\.dicDivision\\.cveIdDivision").html(options);
			});
		}
}

var grupoCtrl = {
	cargarComboXDvision: function() {
		var selectDivision =$("select#dicGrupo\\.dicDivision\\.cveIdDivision").val(); 
		var url = "dicgrupo/cargarGrupos.do";
		$.getJSON(url ,  { claveConsultar: selectDivision } , function (objData){
			 var options = '<option value="0" >--Por favor seleccione--</option> ';
			for (var i = 0; i < objData.length; i++) {
		        options += '<option value="' + objData[i].cveGrupo + '">' + objData[i].desGrupo + '</option>';
		      }
			 $("select#dicGrupo\\.cveIdGrupo").html(options);
		});
	}
}

$(function(){
	$("select#dicGrupo\\.dicDivision\\.cveIdDivision").change(function(){
		  try{
			  grupoCtrl.cargarComboXDvision();
		  }catch (e) {
			alert(e);
		}
	  })
	})

/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */
$(document).ready(function() {

    /**
     * Inicializacion del data table
     */
    oDtDicFraccion = $(idDataTable).dataTable({
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
                oObj.aData['cveIdFraccion'] +'" id="radioTable" class="radioDicFraccion" name="radio" onclick=""/> ';
                return retVal;
            }, 
            aTargets: [0]
            },
                {
                "sTitle" : "Clave Fracción",
                "mDataProp" : "cveIdFraccion",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "Descripción",
                "mDataProp" : "desFraccion",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "Actividad",
                "mDataProp" : "desActividad",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "Grupo",
                "mDataProp" : "cveIdGrupo",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "Número de la Fracción",
                "mDataProp" : "numFraccion",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "Clase",
                "mDataProp" : "cveIdClase",
                "sClass": "dtCenterClassColumn"
                }
            ],"bProcessing" : true,
            "sAjaxSource" : 'dicfraccion/paginar.do',
            "fnServerData" : function(sSource, aoData, fnCallback) {
                aoData.push({
                    "name" : "sSearch",
                    "value" : $('#desFraccion').val()
                });

                var wrapper = new Object();
                wrapper.aoData = aoData;

                var oForm = $("#dicfraccionFiltrosForm").serializeObject(true);   
                wrapper.oForm = oForm;
                
                $.postJSON(sSource, wrapper, function(data) {
                    fnCallback(data);
   			 	}).error(function(datas){ 
					validarSesionExpirada(datas);                    
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
            limpiarFormulario("#dicfraccionForm");
        },
        buttons: {
            "Aceptar": function() { 
                var dicfraccion = $("#dicfraccionForm").serializeObject(true);
                $.postJSON("dicfraccion/agregar.do", dicfraccion, function(data) {
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
            limpiarFormulario("#dicfraccionFormModificar");
        },        
        buttons: {
            "Aceptar": function() { 
                var dicfraccion = $("#dicfraccionFormModificar").serializeObject(true);
                $.postJSON("dicfraccion/modificar.do", dicfraccion, function(data) { 
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
                var dicfraccion = $("#dicfraccionFormBorrar").serializeObject(true);
                $.postJSON("dicfraccion/eliminar.do", dicfraccion, function(data) {
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
    
    //Carga las divisiones
    divisionCtrl.cargarComboDivisiones();
    
     
});//$(document).ready(function()

//Inicializa el paginador
function inicializaPosicionPaginador(){
    oDtdicfraccion.fnDisplayStart(0);
}


function nuevo(){
    oDgNuevo.dialog('open');
}

function paginar(){
    oDtdicfraccion.fnDraw();
}

function modificar(){
    var idDicFraccion = $('#:checked').val();
    var sDicFraccion = '{"cveIdFraccion":'+idDicFraccion+'}';
    var dicfraccion = jQuery.parseJSON(sDicFraccion);
    // Buscamos el elemento
    $.postJSON("dicfraccion/consultaPorClave.do", dicfraccion, function(data) {
        $('#wrapperDialogModif,#dicfraccionFormModificar,#cveIdFraccion').val(data.cveIdFraccion);
        $('#wrapperDialogModif,#dicfraccionFormModificar,#desFraccion').val(data.desFraccion);
        $('#wrapperDialogModif,#dicfraccionFormModificar,#desActividad').val(data.desActividad);
        $('#wrapperDialogModif,#dicfraccionFormModificar,#cveIdGrupo').val(data.cveIdGrupo);
        $('#wrapperDialogModif,#dicfraccionFormModificar,#numFraccion').val(data.numFraccion);
        $('#wrapperDialogModif,#dicfraccionFormModificar,#cveIdClase').val(data.cveIdClase);
        oDgModificar.dialog('open');
    }).error(function(data){ 
        alert("error" + data);
    }).complete(function(){
        //Instrucciones para el 'complete'
    });
}

function borrar(){
    var idDicFraccion = $('#:checked').val();
    var sDicFraccion = '{"cveIdFraccion":'+idDicFraccion+'}';
    var dicfraccion = jQuery.parseJSON(sDicFraccion);
    
    // Buscamos el elemento
    $.postJSON("dicfraccion/consultaPorClave.do", dicfraccion, function(data) {
        $('#wrapperDialogBorrar,#dicfraccionFormBorrar,#cveIdFraccion').val(data.cveIdFraccion);
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
