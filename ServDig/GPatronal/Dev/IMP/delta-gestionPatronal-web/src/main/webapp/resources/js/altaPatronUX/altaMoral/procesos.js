var idTblProdcsServs= "#idTblProdcsServs";

var $formProdcsServs;
var $formTablaProdcsServs;

var contadorRegistrosProdcsServs = 0;

var tabla = {
    prodcsServs : null,
};

var aaDataProdcsServs = [];

var columnasTblProdcsServs = [ {
    mDataProp : "clave",
    sTitle : "",
    bVisible : false
}, {
    mDataProp : "prodServ",
    sTitle : "Producto/Servicio",
    bVisible : true,
    sWidth: "90%",
    size : "5"
},{
    sTitle : "Eliminar",
    bSortable : false,
    size:"5",
    sClass : "dt-center",
    fnRender : function ( objeto, val) {
        return generaBotonEliminarProdcsServs(objeto.aData.clave, 'idTblProdcsServs');
    }
} ];

$(document).ready(function() {

    $formProdcsServs= $("#idFrmAgregarProdcsServs");
    $formTablaProdcsServs= $("#idFrmTablaProdcsServs");

    initValidateProcesos();
    intReglaValidacionTablaProdcsServs();
    inicializaAltaPatronalUxProdcsServs();
    initValidatorProdcsServs();
    intValidacionTablaProdcsServs();

    $("#idBtnAgregarProdcsServs").click(function(event) {
        agregarColumnasTablaProdcsServs(event.currentTarget.id);
    });

    $("#idLinkAgregarProdcsServs").click(function(event) {
        if ($("#idPanelProdcsServs").hasClass('in')) {
            limpiarForm("idFrmAgregarProdcsServs");
        } else {
            if (tabla['prodcsServs'].fnGetData().length >= 10) {
                $("#idBtnAgregarProdcsServs").prop('disabled', true);
            } else {
                $("#idBtnAgregarProdcsServs").prop('disabled', false);
            }
        }
    });

    $("#atrasProcesos").on("click",function(){
        paginaPrevia();
    });

    $("#siguienteProcesos").on("click",siguienteProcesos);

} );

/**
 * funcion para iniciar el validador del formulario
 */
var initValidateProcesos = function() {

    $("#formProcesos1").validate($.extend({},DEFAULTS_VALIDATE,{
        verifyErrors: function(existError) {
            marcarAsteriscos($("#formProcesos1"),".errorDocs","div");
        },
        rules: {
            giroActividad:{
                required: true,
                maxlength: 300
            }
        }
    }));

    $("#formProcesos2").validate($.extend({},DEFAULTS_VALIDATE,{
        verifyErrors: function(existError) {
            marcarAsteriscos($("#formProcesos2"),".errorDocs","div");
        },
        rules: {
            procesosTrabajo: {
                required: true,
                maxlength: 1000
            }
        }
    }));
}

/**
 * Metodo para iniciarlizar el validador del formulario de productos/servicios
 */
var initValidatorProdcsServs = function() {
    $formProdcsServs.validate($.extend({}, DEFAULTS_VALIDATE, {
        verifyErrors: function(existError) {
            mostrarMensajeErrorGenerico($formProdcsServs.attr("id"), existError, MENSAJE_ERROR_FORM);
            marcarAsteriscos($formProdcsServs, ".errorDocs", ".col-sm-9");
        },
        rules: {
            txtNomProdServ: {
                required: true,
                maxlength: 50
            }
        }
    }));
}

function intValidacionTablaProdcsServs() {
    $formTablaProdcsServs.validate($.extend({}, DEFAULTS_VALIDATE, {
        verifyErrors: function(existError) {
            mostrarMensajeErrorProdcsServs($formTablaProdcsServs.attr("id"), existError, "<strong>Error en el formulario!</strong> no ha llenado todos los campos requeridos. Por favor verifique");
            marcarAsteriscos($formTablaProdcsServs,".errorDocs","form");
        },
        rules: {
            hdnValidaTablaProdcsServs: {
                validaTablaProdcsServs: true
            }
        },
        ignore: "",//esta propiedad se sobre escribe para que puedaas usar campos hidden
        errorElement: "span"
    }));

}

function intReglaValidacionTablaProdcsServs() {
    $.validator.addMethod("validaTablaProdcsServs", function(value, elem, param) {
        if (tabla['prodcsServs'].fnGetData( ).length > 0) {
            return true;
        } else {
            return false;
        }
    },"Productos/Servcios requeridos.");
}

var mostrarMensajeErrorProdcsServs= function(idForm, mostrar, mensaje) {
    if (mostrar) {
        if (idForm == "idFrmTablaProdcsServs") {
            $("#idErrorFormProdcsServs").html(mensaje).show();
        }
    } else {
        if (idForm == "idFrmTablaProdcsServs") {
            $("#idErrorFormProdcsServs").html("").hide();
        }
    }
}

function inicializaAltaPatronalUxProdcsServs() {
    tabla['prodcsServs'] = crearGridProdcsServs(idTblProdcsServs, columnasTblProdcsServs, aaDataProdcsServs);
}

var siguienteProcesos = function(){
    mostrarMensajeErrorGenerico('formProcesos1', false, MENSAJE_ERROR_FORM);
    var camposValidos1 = $("#formProcesos1").valid();
    var prodcsServsValids = $formTablaProdcsServs.valid();
    var camposValidos2 = $("#formProcesos2").valid();
    var continuar = (camposValidos1 && prodcsServsValids && camposValidos2);

    mostrarMensajeErrorGenerico('formProcesos1', !continuar, MENSAJE_ERROR_FORM);
    setColorAsteriscoPage(!continuar);

    if(continuar){
        solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.clasificacion.giro = $('#giroActividad').val().toUpperCase();

        var listaProds = solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.productos;
        var producto;
        $.each(tabla['prodcsServs'].fnGetData(), function (ind, elem) {

            producto = new Object();

            producto.idVista = ind;
            producto.sujetoObligado = null;
            producto.descripcion = elem.prodServ;
            listaProds.push(producto);
        });

        solicitudPrincipal.tramiteSujetoObligado.sujetoObligado.proceso.desInicial = $('#procesosTrabajo').val().toUpperCase();

        paginaSiguiente();
    }
}

function crearGridProdcsServs(idGrid, columModel, data) {
    var grid = $(idGrid).dataTable({
        aaData : data,
        bJQueryUI : false,
        bFilter : false,
        bInfo : false,
        bSort : false,
        bPaginate : false,
        bAutoWidth : false,
        bServerSide : false,
        bProcessing : false,
        oLanguage: {"sZeroRecords": "Dar clic en la opci&oacute;n 'Agregar' para capturar la informaci&oacute;n"},
        aoColumns : columModel
    });
    return grid;
}

function fnClickRemoveRowProdcsServs(idRow, idGrid) {
    var dataTabla;

    switch(idGrid) {
        case "#idTblProdcsServs" :
            dataTabla= tabla['prodcsServs'].fnGetData( );
            for(var t = 0; t < dataTabla.length; t++) {
                if (dataTabla[t].clave == idRow) {
                    tabla['prodcsServs'].fnDeleteRow(t);
                    if (tabla['prodcsServs'].fnGetData().length >= 10) {
                        $("#idBtnAgregarProdcsServs").prop('disabled', true);
                    } else {
                        $("#idBtnAgregarProdcsServs").prop('disabled', false);
                    }
                }
            }
            break;
    }
}

function agregarColumnasTablaProdcsServs(idEvento) {
    var objeto;
    switch(idEvento) {
        case "idBtnAgregarProdcsServs":
            if ($formProdcsServs.valid()) {
                objeto = {
                    "clave" : contadorRegistrosProdcsServs++,
                    "prodServ" : $("#txtNomProdServ").val().toUpperCase(),
                };
                tabla['prodcsServs'].fnAddData(objeto);
                limpiarForm("idFrmAgregarProdcsServs");
                $("#idPanelProdcsServs").removeClass('in');
                $formTablaProdcsServs.valid();
            }
            break;
    }
}

function generaBotonEliminarProdcsServs(idRow, idGrid) {
    var botonHTML='';

    botonHTML += ''
        +'<span class="glyphicon glyphicon-trash" onmouseover="" style="cursor: pointer;" aria-hidden="true" onclick="fnClickRemoveRowProdcsServs('+idRow+','+idGrid+')"></span>';

    return botonHTML;
}