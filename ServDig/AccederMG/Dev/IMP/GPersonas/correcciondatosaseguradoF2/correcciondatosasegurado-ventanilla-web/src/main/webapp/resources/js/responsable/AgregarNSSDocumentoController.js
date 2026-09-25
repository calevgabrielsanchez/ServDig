function AgregarNSSDocumentoController(module) {
    this.module = module;
}

AgregarNSSDocumentoController.prototype.bandeja = function () {
//  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 1);
    this.module.controller.transition("bandejaSolicitudesUI");
};

AgregarNSSDocumentoController.prototype.cancelar = function () {
//  this.module.updateModel("CardLayoutComponent","responsableCardLayout", 5);
    this.module.controller.transition("cancelarUI");
};


AgregarNSSDocumentoController.prototype.obtenerDatosNSS = function () {
    this.module.updateModel("AlertComponent", "nssMessage", {message: "", level: "info"});
    this.module.updateModel("AlertComponent", "alert", {message : "",level : "danger"});
    if ($("#nssBuscar").val() != "" && $("#nssBuscar").val() !== undefined && $("#nssBuscar").val() !== null) {
        // console.log("#nssBuscar");
        if (this.module.controller.agregarNssDocumentoController.model.nssEditado != null
                && this.module.controller.agregarNssDocumentoController.model.nssEditado.nss != null
                && this.module.controller.agregarNssDocumentoController.model.nssEditado.nss !== $("#nssBuscar").val()) {
            // console.log("se busca el nss que no es el modificado");
            var nssBuscar = $("#nssBuscar").val();
            var nssEditado = this.module.controller.agregarNssDocumentoController.model.nssEditado.nss
            this.module.service.limpiarCamposNssAgregados();
            this.module.controller.agregarNssDocumentoController.model.nssOculto = null;
            this.module.controller.agregarNssDocumentoController.reiniciarDocumentos();
            module.controller.agregarNssDocumentoController.model.documentosEliminados = new Array();
            $("#nssBuscar").val(nssBuscar);
            this.module.controller.agregarNssDocumentoController.model.documentosAgregados = new Array();
            this.module.controller.agregarNssDocumentoController.model.cveIdDocumento = null;
            this.module.controller.agregarNssDocumentoController.model.indiceDocumentoProbatorio = null;
            this.module.controller.agregarNssDocumentoController.model.registroDocumentoProbatorio = null;
            this.module.controller.agregarNssDocumentoController.model.tipoDocumento = null;
            this.module.controller.agregarNssDocumentoController.model.idDocumentoPorTipo = null;
            this.module.controller.agregarNssDocumentoController.model.nssEditado = null;
            this.module.controller.agregarNssDocumentoController.model.desDocumento = null;
            this.module.updateModel("AlertComponent", "nssMessage", {message: "Se cancela la edicion del N&uacute;mero de Seguridad Social " + nssEditado + "", level: "info "});
        }
        this.module.service.obtenerDatosporNSS();

    } else {
        this.module.updateModel("AlertComponent", "nssMessage", {message: "Debe ingresar un N&uacute;mero de Seguridad Social.", level: "danger "});  
    }    
};


AgregarNSSDocumentoController.prototype.obtenerCombosDocumentosNSS = function () {
    var module = this.module;
    /** Limite de nss que se puede agregar **/
    module.controller.agregarNssDocumentoController.model.nssLimite = 20;
    
    module.updateModel("AlertComponent", "nssMessage", {message: "", level: "info"});
    this.module.controller.agregarNssDocumentoController.model.idSolicitud = this.module.controller.consultaSolicitudController.model.consultaSolicitud.idSolicitud;
    this.module.controller.agregarNssDocumentoController.model.folio = this.module.controller.consultaSolicitudController.model.consultaSolicitud.folio;
    this.module.controller.agregarNssDocumentoController.model.nssAgregados = new Array();
    /** Se agrega un nss a editar **/
    this.module.controller.agregarNssDocumentoController.model.nssEditado = null;
    /** Lista de documentos temporales(al final se agrega a un nss) **/
    this.module.controller.agregarNssDocumentoController.model.documentosAgregados = new Array();
    /** Lista de documentos temporales(eliminados en temporal) **/
    this.module.controller.agregarNssDocumentoController.model.documentosEliminados = new Array();
    /** Indice de documentos temporales **/
    this.module.controller.agregarNssDocumentoController.model.indiceDocumentoProbatorio = 0;
    module.service.crearElementos();
    module.service.obtenerComboDocumentosNSS();
    /** Indice de documentos temporales **/
    var documnetoNss = this.module.controller.agregarNssDocumentoController.model.gridDocumentosNssOrigen.data;
    // console.log(documnetoNss);
    this.module.controller.agregarNssDocumentoController.model.nssExistentes = documnetoNss;
    module.service.obtenerGridNssVentanilla();
    module.service.obtenerGridNssPortal();





};

/**
 * Evento de cambio que revisa si se selecciono un tipo de documento e imagen 
 */
AgregarNSSDocumentoController.prototype.changeDocumentosProbatorios = function () {
    var module = this.module;
    if ($("#combodocumentosSelectField").val() !== "-1") {
        var cveIdDocumento = $("#combodocumentosSelectField").val();
        this.module.controller.agregarNssDocumentoController.model.cveIdDocumento = cveIdDocumento;
        this.module.controller.agregarNssDocumentoController.model.indiceDocumentoProbatorio = ($('#listadoDocumentosGrid tr').length - 1);
        this.module.controller.agregarNssDocumentoController.model.registroDocumentoProbatorio = $("#combodocumentosSelectField option:selected").text();
        this.module.controller.agregarNssDocumentoController.model.tipoDocumento = $("#combodocumentosSelectField :selected").parent()
                .attr("value");

        this.module.controller.agregarNssDocumentoController.model.idDocumentoPorTipo = $("#combodocumentosSelectField :selected").attr(
                "docportipo");

        this.module.controller.agregarNssDocumentoController.model.desDocumento = $("#combodocumentosSelectField option:selected").text();

        $("#combodocumentosSelectField").val("-1");
        $(function () {
            $('input[name=fileData]').change(function () {
//        	//inicio prueba
//        	var cveIdDocumento=this.module.controller.agregarNssDocumentoController.model.cveIdDocumento;
//        	//fin prueba
                var valor2 = $(this).val();
                if (cveIdDocumento !== "" && cveIdDocumento !== undefined && cveIdDocumento !== null && valor2 !== "-1") {
                    // console.log(module);
                    //Agregar validacion de documento                    
                    module.service.guardarDocumento();
                }
            });
        });
        $("#fileData").click();
    }
};

/**
 * Metodo que abre una pantalla modal que enviara a la consulta del tramite
 */
AgregarNSSDocumentoController.prototype.salirAconsulta = function () {
    this.module.service.salirAConsulta();
};



/**
 * Agrega la informacion  del nss y sus documentos 
 */
AgregarNSSDocumentoController.prototype.agregarNSS = function () {
    var module = this.module;
    $("#modal").modal();
    module.updateModel("AlertComponent", "alertListadoDocumentosGrid", {level: "info", message: ""});
    // console.log("**********Entra agregarNSS   ***************")
    var nssExistentes = module.controller.agregarNssDocumentoController.model.gridNssVentanilla;
    var nssAgregados = module.controller.agregarNssDocumentoController.model.nssAgregados;
    if (nssAgregados !== "undefined") {
        var nsstotales = nssExistentes.concat(nssAgregados);
    }

    // console.log("Total de nss: "+nsstotales.length);
    // console.log(nsstotales);
    var nssLimite = this.module.controller.agregarNssDocumentoController.model.nssLimite;
    var isRepetido = false;
    var nssOculto = this.module.controller.agregarNssDocumentoController.model.nssOculto;
    for (var i = 0; i < nsstotales.length; i++) {
        // console.log("Total de nss: "+nsstotales[i].nss);
        if (nsstotales[i].nss === nssOculto) {
            isRepetido = true;
            break;
        }
    }
    if (module.controller.agregarNssDocumentoController.model.nssEditado != null) {
        //if(isRepetido){

        if (nsstotales.length < nssLimite) {
            module.controller.agregarNssDocumentoController.persistirNss(nssOculto);
        } else {
            $("#modal").modal('hide');
            module.updateModel("AlertComponent", "alert", {
                level: "danger",
                message: "Se ha excedido el l\u00EDmite de registro de NSS, el l\u00EDmite m\u00E1ximo para asociar NSS a una solicitud por ventanilla es de " + nssLimite + "."});
        }
        /*}else{
         $("#modal").modal('hide');
         module.updateModel("AlertComponent", "alert", {
         level : "danger",
         message : "El nss ya fue agregado"});
         $('html, body').animate({scrollTop:0}, 'slow');
         }*/
    } else {
        if (nsstotales.length < nssLimite) {
            module.controller.agregarNssDocumentoController.persistirNss(nssOculto);
        } else {
            $("#modal").modal('hide');
            mensageConfirmacion("Se ha excedido el l\u00EDmite de registro de NSS, el l\u00EDmite m\u00E1ximo para asociar NSS a una solicitud por ventanilla es de " + nssLimite + ".");
        }
    }
};

AgregarNSSDocumentoController.prototype.persistirNss = function (nssOculto) {
	var reg = /^[A-Za-z\d\s]+$/;
    if (nssOculto != "" && nssOculto !== undefined && nssOculto !== null) {
        var temporalDocumentos = this.module.controller.agregarNssDocumentoController.model.documentosAgregados;
        // console.log("****** verificamos si tiene algo de documentos*********");
        // console.log(temporalDocumentos);

        if (typeof temporalDocumentos != "undefined" && temporalDocumentos != null && temporalDocumentos.length != null && temporalDocumentos.length > 0) {
            let observacion = $("#observacion").val().trim();
            if (observacion === "" || reg.test(observacion) === true) {
                $('.has-error').removeClass('has-error');
                //$('.alert').hide();
                module.service.guardarNss();
            } else {
                $("#modal").modal('hide');
                module.updateModel("AlertComponent", "alertListadoDocumentosGrid", {
                    level: "danger",
                    message: "No se permite capturar caracteres especiales"
                });
            }
        } else {
            $("#modal").modal('hide');
            module.updateModel("AlertComponent", "alertListadoDocumentosGrid", {
                level: "danger",
                message: "Para poder continuar, es necesario adjuntar un archivo por cada uno de los documentos probatorios marcados como obligatorios."});
        }

    } else {
        $("#modal").modal('hide');
        this.module.updateModel("AlertComponent", "nssMessage", {message: "Debe ingresar un N&uacute;mero de Seguridad Social.", level: "danger "});
        $('html, body').animate({scrollTop: 0}, 'slow');
    }
};

/**
 * Obtiene el nss  para editarlo, carga la informacion en la pantalla
 * @param index
 */
AgregarNSSDocumentoController.prototype.editarNss = function (index, idGrid, eliminar) {
    $("#fileData").val("");
    this.module.service.limpiarCamposNssAgregados();
    this.module.controller.agregarNssDocumentoController.model.nssOculto = null;
    this.module.controller.agregarNssDocumentoController.reiniciarDocumentos();
    this.module.controller.agregarNssDocumentoController.model.documentosEliminados = new Array();
    this.module.controller.agregarNssDocumentoController.model.documentosAgregados = new Array();
    this.module.controller.agregarNssDocumentoController.model.cveIdDocumento = null;
    this.module.controller.agregarNssDocumentoController.model.indiceDocumentoProbatorio = null;
    this.module.controller.agregarNssDocumentoController.model.registroDocumentoProbatorio = null;
    this.module.controller.agregarNssDocumentoController.model.tipoDocumento = null;
    this.module.controller.agregarNssDocumentoController.model.idDocumentoPorTipo = null;
    this.module.controller.agregarNssDocumentoController.model.desDocumento = null;

    var documnetoNss = this.module.controller.agregarNssDocumentoController.model[idGrid][index];
    // console.log(documnetoNss);
    this.module.controller.agregarNssDocumentoController.model.nssEditado = documnetoNss;
    $("#nssBuscar").val(documnetoNss.nss);
    $("#observacion").val(documnetoNss.observacion);
    var module = this.module;
    for (var i = 0; i < documnetoNss.documentosProbatorios.length; i++) {
        var indiceDocumentoProbatorio = i;
        var arrayCampos = [++indiceDocumentoProbatorio,
            documnetoNss.documentosProbatorios[i].desDocumento];
        var res = documnetoNss.documentosProbatorios[i].nombreArchivo.split("_");
        // console.log("nombreArchivo split _");
        // console.log(res);
        var arrayCamposHidden = [documnetoNss.documentosProbatorios[i].nombreArchivo,
            documnetoNss.documentosProbatorios[i].tipoDocumento, res[0]];
        // console.log("Id documento");
        // console.log(res[0]);
        arrayCamposHidden.push(documnetoNss.documentosProbatorios[i].idDocBoveda);
        var doctoVo = {'idDocumentoPorTipo': res[0], 'cveIdDocumento': 0, 'desDocumento': documnetoNss.documentosProbatorios[i].desDocumento, 'tipoDocumento': 11, 'idDocBoveda': documnetoNss.documentosProbatorios[i].idDocBoveda, nombre: documnetoNss.documentosProbatorios[i].nombreArchivo};
        module.service.agregaDocProTabla(arrayCampos, arrayCamposHidden,
                indiceDocumentoProbatorio, 'listadoDocumentosGrid', 0, 0, eliminar);
        module.controller.agregarNssDocumentoController.model.documentosAgregados.push(doctoVo);
    }
    // console.log("Se editan documentos del nss");
    // console.log(module.controller.agregarNssDocumentoController.model.documentosAgregados);
    module.service.obtenerDatosporNSS();
};


AgregarNSSDocumentoController.prototype.eliminarDocumento = function (nss, idBoveda) {

    // console.log("Se elimina el documento probatorio ");
    var folio = module.controller.model.consultaSolicitud.folio;
    module.service.eliminarDocumento(nss, idBoveda, folio);

};

AgregarNSSDocumentoController.prototype.eliminarNss = function (index) {
    var documnetoNss = this.module.controller.agregarNssDocumentoController.model.gridNssVentanilla[index];
    // console.log(documnetoNss);
    if (this.module.controller.agregarNssDocumentoController.model.nssEditado != null
            && this.module.controller.agregarNssDocumentoController.model.nssEditado.nss
            != null
            && this.module.controller.agregarNssDocumentoController.model.nssEditado.nss === documnetoNss.nss) {
        module.updateModel("AlertComponent", "alert", {
            level: "danger",
            message: "No se puede eliminar el nss, se esta editando"});
        $('html, body').animate({scrollTop: 0}, 'slow');
    } else {
        // console.log("Se elimina el nss ");
        module.service.eliminarNSS(documnetoNss.nss);
    }
};


/**
 * Elimina un documento en la vista, y se elimina de boveda, sí es edición de un nss solo lo elimina de la vista
 * @param idRow
 * @param idDocumento
 * @param idBoveda
 */
AgregarNSSDocumentoController.prototype.eliminarDocumentos = function (idRow, idDocumento, idBoveda) {
    // console.log("******** Eliminacion de documentos controller ************");
    var module = this.module;
    var nssEditado = this.module.controller.agregarNssDocumentoController.model.nssEditado;
    var documentos = this.module.controller.agregarNssDocumentoController.model.documentosAgregados;
    // console.log(documentos);
    // console.log(idBoveda);
    // console.log(idDocumento);
    for (var i = 0; i < documentos.length; i++) {
        if (idBoveda == documentos[i].idDocBoveda) {
            if (nssEditado != null) {
                // console.log("Se elimina el documento en edicion");
                module.controller.agregarNssDocumentoController.model.documentosEliminados.push({'idDocBoveda': idBoveda, 'cveIdDocumento': idDocumento});
                var item = $('#' + idRow);
                idRow = $("#listadoDocumentosGrid tr").index(item);
                idRow = idRow - 1;
                // console.log(module.controller.agregarNssDocumentoController.model.documentosEliminados);
                $(item).remove();
                documentos.splice(i, 1);
                module.service.indexarListaDocumento();
                // console.log("********** Se elimina del modelo ************");
                // console.log(documentos);
                module.controller.agregarNssDocumentoController.eliminarDocumento(nssEditado.nss, idBoveda);
            } else {
                // console.log("Se elimina el documento en boveda");
                module.service.eliminarDocumentoBoveda(idRow, idDocumento, idBoveda, i);
            }
            break;
        }
    }
};

/**
 * Limpia la lista de documentos y reinicia la lista de documentos temporales en el modelo 
 */
AgregarNSSDocumentoController.prototype.reiniciarDocumentos = function () {
    var htmls = "<table class='table' id='listadoDocumentosGrid' style=''>";
    htmls += "<tr></tr>";
    htmls += "</table>";
    $("#listadoDocumentosGrid").replaceWith(htmls);
};

/**
 * Limpia los campos de la vista y reicinia las variables en el modelo, si es edicion de nss existente se guarda en temporal
 * @param nss
 */
AgregarNSSDocumentoController.prototype.limpiarDocumentos = function (nss) {
    // console.log("******** Eliminacion de documentos controller ************");
    var nssEditado = this.module.controller.agregarNssDocumentoController.model.nssEditado;
    module.updateModel("AlertComponent", "nssMessage", {message: "", level: "info"});
    module.updateModel("AlertComponent", "alert", {message : "",level : "danger"});
    if (nssEditado == null) {
        // console.log("Se limpian los documentos (se eliminan de boveda)");
        var documentos = this.module.controller.agregarNssDocumentoController.model.documentosAgregados;
        // console.log(documentos);
        for (var i = 0; i < documentos.length; i++) {
            if (documentos.cveIdDocumento != 0) {
                this.module.service.eliminarByBoveda(documentos.idDocBoveda, documentos.cveIdDocumento, i);
            }
        }
    } else {
        // console.log("Se limpian los documentos (se agregan a la lista del modelo)");
        var arrayeliminar = this.module.controller.agregarNssDocumentoController.model.documentosEliminados;
        var arraybase = this.module.controller.agregarNssDocumentoController.model.documentosAgregados;
        this.module.controller.agregarNssDocumentoController.model.documentosEliminados = arrayeliminar.concat(arraybase);
        // console.log(this.module.controller.agregarNssDocumentoController.model.documentosEliminados);
    }
    this.module.service.limpiarCamposNssAgregados();
    this.module.controller.agregarNssDocumentoController.model.nssOculto = null;
    this.module.controller.agregarNssDocumentoController.reiniciarDocumentos();
};

/**
 * Coloca el conteo de los documentos probatorios
 */
AgregarNSSDocumentoController.prototype.indexarListaDocumento = function () {
    $('#listadoDocumentosGrid tr').each(
            function (index) {
                $(this).children(' td:first').html('<p style="font-size: 1.5em;">' + index + "</p>");
            });
}

AgregarNSSDocumentoController.prototype.mostrarDocumento = function (nombreArchivo, idBoveda) {
    idPersona = this.module.controller.model.userProfile.idPersona;
    folio = this.module.controller.consultaSolicitudController.model.consultaSolicitud.folio;

    document.forms['auxForm'].action = "atencionAutorizador/obtenerDocumento/" + idPersona + "/" + folio + "/ext/" + nombreArchivo + "/" + idBoveda;
    document.forms['auxForm'].submit();
};
