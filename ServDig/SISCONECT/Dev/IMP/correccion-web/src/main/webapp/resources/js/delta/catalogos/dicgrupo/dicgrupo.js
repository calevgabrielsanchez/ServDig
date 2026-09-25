/**
 * JS para el soporte del catalogo de dicgrupo.
 */


var idDataTable     	= "#dtDicGrupo";
var idDgNuevo         	= "#dgDicGrupoNuevo";
var idDgModificar     	= "#dgDicGrupoModificar";
var idDgBorrar         	= "#dgDicGrupoBorrar";
var idDgAyuda        	= "#dgDicGrupoAyuda";

// Objeto del DataTable
var oDtDicGrupo;
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
				 $("select#dicDivision\\.cveIdDivision").html(options);
			});
		}
}

/**
 * Iniciamos la configuracion de los componentes visuales de jQuery.
 */
$(document).ready(function() {

    /**
     * Inicializacion del data table
     */
    oDtDicGrupo = $(idDataTable).dataTable({
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
                oObj.aData['cveIdGrupo'] +'" id="radioTable" class="radioDicGrupo" name="radio" onclick=""/> ';
                return retVal;
            }, 
            aTargets: [0]
            },
                {
                "sTitle" : "Clave Grupo",
                "mDataProp" : "cveIdGrupo",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "Descripci&oacute;n",
                "mDataProp" : "desGrupo",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "Divisi&oacute;n",
                "mDataProp" : "dicDivision.desDivision",
                "sClass": "dtCenterClassColumn"
                },
                {
                "sTitle" : "N&uacute;mero de Grupo",
                "mDataProp" : "numGrupo",
                "sClass": "dtCenterClassColumn"
                }
            ],"bProcessing" : true,
            "sAjaxSource" : 'dicgrupo/paginar.do',
            "fnServerData" : function(sSource, aoData, fnCallback) {
                aoData.push({
                    "name" : "sSearch",
                    "value" : $('#desGrupo').val()
                });

                var wrapper = new Object();
                wrapper.aoData = aoData;

                var oForm = $("#dicgrupoFiltrosForm").serializeObject(true);   
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
            limpiarFormulario("#dicgrupoForm");
        },
        buttons: {
            "Aceptar": function() { 
                
            	var dicgrupo = $("#dicgrupoForm").toObject({mode: 'first'});
                
                $.postJSON("dicgrupo/agregar.do", dicgrupo, function(data) {
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
            limpiarFormulario("#dicgrupoFormModificar");
        },        
        buttons: {
            "Aceptar": function() { 
                var dicgrupo = $("#dicgrupoFormModificar").toObject({mode: 'first'});
                $.postJSON("dicgrupo/modificar.do", dicgrupo, function(data) { 
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
                var dicgrupo = $("#dicgrupoFormBorrar").toObject({mode: 'first'});
                $.postJSON("dicgrupo/eliminar.do", dicgrupo, function(data) {
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
    
    //Carga el combo de divisiones
    divisionCtrl.cargarComboDivisiones();
    
     
});//$(document).ready(function()

//Inicializa el paginador
function inicializaPosicionPaginador(){
	oDtDicGrupo.fnDisplayStart(0);
}


function nuevo(){
    oDgNuevo.dialog('open');
}

function paginar(){
	oDtDicGrupo.fnDraw();
}

function modificar(){
    var idDicGrupo = $('#:checked').val();
    var sDicGrupo = '{"cveIdGrupo":'+idDicGrupo+'}';
    var dicgrupo = jQuery.parseJSON(sDicGrupo);
    // Buscamos el elemento
    $.postJSON("dicgrupo/consultaPorClave.do", dicgrupo, function(data) {
        $('#wrapperDialogModif,#dicgrupoFormModificar,#cveIdGrupo').val(data.cveIdGrupo);
        $('#wrapperDialogModif,#dicgrupoFormModificar,#desGrupo').val(data.desGrupo);
        $('#wrapperDialogModif,#dicgrupoFormModificar,#dicDivision\\.cveIdDivision').val(data.dicDivision.cveIdDivision);
        $('#wrapperDialogModif,#dicgrupoFormModificar,#numGrupo').val(data.numGrupo);
        oDgModificar.dialog('open');
    }).error(function(data){ 
        alert("error" + data);
    }).complete(function(){
        //Instrucciones para el 'complete'
    });
}

function borrar(){
    var idDicGrupo = $('#:checked').val();
    var sDicGrupo = '{"cveIdGrupo":'+idDicGrupo+'}';
    var dicgrupo = jQuery.parseJSON(sDicGrupo);
    
    // Buscamos el elemento
    $.postJSON("dicgrupo/consultaPorClave.do", dicgrupo, function(data) {
        $('#wrapperDialogBorrar,#dicgrupoFormBorrar,#cveIdGrupo').val(data.cveIdGrupo);
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


