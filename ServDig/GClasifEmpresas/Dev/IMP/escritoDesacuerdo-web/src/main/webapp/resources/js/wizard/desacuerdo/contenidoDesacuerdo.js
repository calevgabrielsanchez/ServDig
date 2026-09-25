/**
 *
 */
var registroDesacuerdoCtrl = {
    materia: 2,
    causa: 3,
    motivosDesacuerdo: 0,
    motivoDesacuerdo: "",
    contextApp: '/${mvn.web.app.root}',
    origenApp: '${mvn.web.app.origin.id}',
    folImpugnado: "",
    intervalId: null,
    objFrimEscrito: null,
    validafechnot: true,
    minFechRes: "",
    validMtvoCamp: 0,
    causaDsdo:null,
    init: function () {

        if (registroDesacuerdoCtrl.folImpugnado != 0) {
            $("#modalDlg").addClass("hidden");
            $("#principal").removeClass("hidden");
        }

        $(".radioMateria").on("click", {attr: "materia"}, registroDesacuerdoCtrl.setCombo);
        $(".motivosDesacuerdo").on("change", {attr: "motivosDesacuerdo"}, registroDesacuerdoCtrl.setCombo);


        if ($("#guardarTramite").length) {
            $("#guardarTramite").on("click", registroDesacuerdoCtrl.guardarTramite);
        }
        $("#finalizarTramite").on("click", registroDesacuerdoCtrl.finalizar);

        $("#componenteBoveda").boveda({
            tipoTramite: 155,
            idTramite: $("#tramiteId").val(),
            folio: $("#noFolioSolicitud").val(),
            rutaBoveda: "/rtt",
            tipoDocumental: "D:RTT:escrito_desacuerdo"
        });

        $("#toolTipMotivo").tooltip();
        funcionesComunes.init();
        registroDesacuerdoCtrl.initValidator();
        registroDesacuerdoCtrl.procesarRetomar();
        registroDesacuerdoCtrl.vaidacorAnterior();

        /*
         * Funcion para validar que no se encuentre duplicado el registro
         * con el folio imúgnado y la vigencia
         */
        $('input[type="text"]#folioImpugnado').blur(function () {
            registroDesacuerdoCtrl.validaRegDuplicado();
        });

        $('input[type="number"]#anVigencia').blur(function () {
            registroDesacuerdoCtrl.validaRegDuplicado();
        });

        $('input[type="date"]#fechNotRes').blur(function () {
            document.getElementById("fechNotRes").removeAttribute("min");
        });
    },
    procesarRetomar: function () {
        var retomando = $("#retomandoSolicitud").val() == "1";
        if (retomando) {
            $.blockUI();
            $.postJSON(registroDesacuerdoCtrl.contextApp + "/escrito/wizard/retomar", null, function (data) {
                var escritoDesacuerdo = data.tramite, $formulario = $("#formEscrito");

                if (escritoDesacuerdo) {
                    //$formulario.find("#tramiteId").val(escritoDesacuerdo.tramiteId);

                    if (escritoDesacuerdo.mail != null) {
                        $formulario.find("#mail").val(escritoDesacuerdo.mail);
                    }

                    if (escritoDesacuerdo.anVigencia != null) {
                        $formulario.find("#anVigencia").val(escritoDesacuerdo.anVigencia);
                    }

                    if (escritoDesacuerdo.claseAnterior != null) {
                        $formulario.find("#claseAnterior").val(escritoDesacuerdo.claseAnterior);
                    }

                    if (escritoDesacuerdo.fechNotRes != null) {
                        $formulario.find("#fechNotRes").val(registroDesacuerdoCtrl.convertDateCorrectRetrn(escritoDesacuerdo.fechNotRes));
                    }

                    if (escritoDesacuerdo.folioImpugnado != null) {
                        registroDesacuerdoCtrl.folImpugnado == escritoDesacuerdo.folioImpugnado;
                        $formulario.find("#folioImpugnado").val(escritoDesacuerdo.folioImpugnado);

                        $formulario.find("#fracAnterior").val(escritoDesacuerdo.fracAnterior);
                        $formulario.find("#fracAnterior").val(escritoDesacuerdo.fracAnterior);
                        $formulario.find("#primAnterior").val(escritoDesacuerdo.primAnterior);
                    }

                    if (escritoDesacuerdo.trabajadorProm != null) {
                        $formulario.find("#trabajadorProm").val(escritoDesacuerdo.trabajadorProm);
                    }


                    if (escritoDesacuerdo.causaDesacuerdo) {
                        document.getElementById("lblRadioMateria").setAttribute("style", "display:none");
                        document.getElementById("lblRadioClasificacion").setAttribute("style", "display:none");

                        var materiaDes = escritoDesacuerdo.causaDesacuerdo.materiaDesacuerdo.idMateria,
                            causaDesacuerdo = escritoDesacuerdo.causaDesacuerdo.idCausaDes;
                        registroDesacuerdoCtrl.materia = materiaDes;

                        if (materiaDes == 1) {
                            $formulario.find("#radioClasificacion").prop("checked", true).click();
                            $('#lblRadioClasificacion').removeAttr("style");
                            $("#radioClasificacion").trigger("click");
                            $('#trabHide').addClass("hidden");
                        } else if (materiaDes == 2) {
                            const $radios = $('input:radio[name=causaDesacuerdo.materiaDesacuerdo.idMateria]');
                            $radios.filter("#radioMateria").prop('checked', true);
                            $('#lblRadioMateria').removeAttr("style");
                            $("#radioMateria").trigger("click");
                            $formulario.find("#materiaDeterminacion").val(causaDesacuerdo);
                            registroDesacuerdoCtrl.causa = causaDesacuerdo;
                        }
                    }

                    if (escritoDesacuerdo.motivosDesacuerdo.idMotivoDes != null) {
                        registroDesacuerdoCtrl.motivosDesacuerdo = escritoDesacuerdo.motivosDesacuerdo.idMotivoDes;
                        registroDesacuerdoCtrl.motivoDesacuerdo = escritoDesacuerdo.motivoDesacuerdo;

                        $('#motivosDesacuerdo.idMotivoDes').val(registroDesacuerdoCtrl.motivosDesacuerdo);
                        $(".motivosDesacuerdo").val(registroDesacuerdoCtrl.motivosDesacuerdo);
                        $("#motivoDesacuerdo").val(registroDesacuerdoCtrl.motivoDesacuerdo);

                        if (escritoDesacuerdo.motivoDesacuerdo1 != null) {
                            $("#motivoDesacuerdo1").val(escritoDesacuerdo.motivoDesacuerdo1);
                        }
                        if (escritoDesacuerdo.motivoDesacuerdo2 != null) {
                            $("#motivoDesacuerdo2").val(escritoDesacuerdo.motivoDesacuerdo2);
                        }
                        if (escritoDesacuerdo.motivoDesacuerdo3 != null) {
                            $("#motivoDesacuerdo3").val(escritoDesacuerdo.motivoDesacuerdo3);
                        }
                        if (escritoDesacuerdo.motivoDesacuerdo4 != null) {
                            $("#motivoDesacuerdo4").val(escritoDesacuerdo.motivoDesacuerdo4);
                        }
                        if (escritoDesacuerdo.motivoDesacuerdo5 != null) {
                            $("#motivoDesacuerdo5").val(escritoDesacuerdo.motivoDesacuerdo5);
                        }
                        if (escritoDesacuerdo.motivoDesacuerdo6 != null) {
                            $("#motivoDesacuerdo6").val(escritoDesacuerdo.motivoDesacuerdo6);
                        }
                        if (escritoDesacuerdo.motivoDesacuerdo7 != null) {
                            $("#motivoDesacuerdo7").val(escritoDesacuerdo.motivoDesacuerdo7);
                        }
                        if (escritoDesacuerdo.motivoDesacuerdo8 != null) {
                            $("#motivoDesacuerdo8").val(escritoDesacuerdo.motivoDesacuerdo8);
                        }
                        if (escritoDesacuerdo.motivoDesacuerdo9 != null) {
                            $("#motivoDesacuerdo9").val(escritoDesacuerdo.motivoDesacuerdo9);
                        }

                        registroDesacuerdoCtrl.motivosDesacuerdoChange();

                        registroDesacuerdoCtrl.intervalId = setInterval(registroDesacuerdoCtrl.verificarBoveda, 1000);
                    }

                }

                registroDesacuerdoCtrl.buscarDomicilio($("#tramiteId").val());

                if (escritoDesacuerdo.folioImpugnado != null) {
                    $("#principal").removeClass("hidden");
                    $("#modalDlg").addClass("hidden");
                }
                $.unblockUI();
            }).error(function (data) {
                console.log("ocurrio un error al retomar");
            });
        }
    },
    verificarBoveda: function () {
        if (typeof bovedaCtrl !== "undefined" && bovedaCtrl != null) {
            //console.log("ya existe el componente de boveda");
            if (bovedaCtrl.documentosTramite.length || bovedaCtrl.documentosTramiteCapturados.length) {
                //console.log("ya se cargaron los documentos")
                $("#componenteBoveda").boveda("optional", 1639, registroDesacuerdoCtrl.motivosDesacuerdo);
                //console.log("voy a limpiar el intervalo " + registroDesacuerdoCtrl.intervalId)
                clearInterval(registroDesacuerdoCtrl.intervalId);
            } /*else {
				console.log("aun no de cargan los documentos")
			}*/

        } /*else {
			console.log("aun no existe el componente de boveda");
		}*/
    },
    setCombo: function (event) {
        var attr = event.data.attr;
        registroDesacuerdoCtrl[attr] = parseInt(this.value, 10);
        if (registroDesacuerdoCtrl[attr + "Change"]) registroDesacuerdoCtrl[attr + "Change"]();
    },
    materiaChange: function () {
        var $folioResolucion = $("#folioImpugnado"),
            materiaSeleccionada = registroDesacuerdoCtrl.tiposMaterias[registroDesacuerdoCtrl.materia - 1];

        registroDesacuerdoCtrl.folImpugnado = $folioResolucion.val();
        registroDesacuerdoCtrl.causa = materiaSeleccionada.causaDefault;
        materiaSeleccionada.activar();
        $folioResolucion.attr("placeHolder", materiaSeleccionada.formatoFolio);
        $folioResolucion.attr("maxlength", materiaSeleccionada.maxlength);
    },
    initValidator: function () {

        jQuery.validator.addMethod("folioImpugnado", function (value, element) {
            expresionRegular = registroDesacuerdoCtrl.tiposMaterias[registroDesacuerdoCtrl.materia - 1].regExp;
            return this.optional(element) || expresionRegular.test(value);
        }, 'Formato del folio inv&aacute;lido.');

        jQuery.validator.addMethod("AnVigencia", function (value, element) {
            return this.optional(element) || ((value) != "" && (value) != null);
        }, 'Formato del folio inv&aacute;lido.');

        console.log("inicio el validador");
        $("#formEscrito").validate({
            errorClass: "errorDocs",
            errorElement: "span",
            rules: {
                "causaDesacuerdo.materiaDesacuerdo.idMateria": {
                    required: true
                },
                "causaDesacuerdo.idCausaDes": {
                    min: {
                        param: 1,
                        depends: function (element) {
                            return $("#radioMateria").is(":checked")
                        }
                    }
                },
                "folioImpugnado": {
                    required: true,
                    folioImpugnado: true
                },
                "mail": {
                    email: true,
                    maxlength: 50
                },
                "anVigencia": {
                    required: true,
                    maxlength: 4,
                    max: new Date().getFullYear(),
                    min: new Date().getFullYear() - 100
                },
                "trabajadorProm": {
                    required: true,
                    min: 1, max: 99999.9
                    //, maxlength: 3
                },
                "claseAnterior": {
                    required: true
                },
                "fracAnterior": {
                    required: true
                },
                "primAnterior": {
                    required: true
                },
                "motivoDesacuerdo": {
                    required: true
                }
            },
            messages: {
                "causaDesacuerdo.idCausaDes": {min: MENSAJE_CAMPO_OBLIGATORIO},
                "fechNotRes": {min: "Por favor, escribe una fecha valida."}            }
        });
    },
    tiposMaterias: [{
        name: "clasificacion",
        formatoFolio: "CE-NN-NN-NN/NN/NNNN/NNNN",
        maxlength: 24,
        regExp: /CE\-\d{2}\-\d{2}\-\d{2}\/\d{2}\/\d{4}\/\d{4}/,
        causaDefault: 4,
        activar: function () {
            registroDesacuerdoCtrl.mostrarDivs("hide", "show");
            $("#folioImpugnado").val(registroDesacuerdoCtrl.folImpugnado);
        }
    }, {
        name: "prima",
        formatoFolio: "NN/NN-NNNNN",
        maxlength: 11,
        regExp: /\d{2}\/\d{2}\-\d{5}/,
        causaDefault: 0,
        activar: function () {
            $("#materiaDeterminacion").val(registroDesacuerdoCtrl.causa);
            $("#materiaDeterminacion").trigger("click");
            registroDesacuerdoCtrl.mostrarDivs("show", "hide");
            $("#folioImpugnado").val(registroDesacuerdoCtrl.folImpugnado);

        }
    }],
    motivosDesacuerdoChange: function () {
        var newVal = registroDesacuerdoCtrl.motivosDesacuerdo;

        if (newVal != 0) {
            $("#divMotivoDesGroup").removeClass("hidden");
        } else {
            $("#divMotivoDesGroup").addClass("hidden");
        }

        if (typeof bovedaCtrl !== "undefined" && bovedaCtrl != null) {
            $("#componenteBoveda").boveda("optional", 1639, registroDesacuerdoCtrl.motivosDesacuerdo);
        }
    },
    mostrarDivs: function (divMateria, divClas) {
        $("#divMateria")[divMateria]();
        $("#divClasificacion")[divClas]();
    },
    finalizar: function () {
        if (registroDesacuerdoCtrl.validaAnVign()) {
            var $formulario = $("#formEscrito");

            if ($formulario.valid() & $("#componenteBoveda").boveda("valid")) {
                if (registroDesacuerdoCtrl.origenApp == "1") {
                    //	if (1==1){
                    var opciones = {
                        titulo: 'Confirmaci&oacute;n requerida',
                        mensaje: "Se proceder&aacute; con el registro del escrito, estas seguro de continuar?",
                        buttons: {
                            'No': dialogosCtrl.close,
                            'Si': function () {
                                $(this).dialog('close');
                                registroDesacuerdoCtrl.procesarFinalizado(true);
                            }
                        }
                    };

                    dialogosCtrl.abrirDialogo(opciones);
                } else {
                    registroDesacuerdoCtrl.invocarFirma();
                }
            }
        } else {
            dialogosCtrl.openDialAviso(dialogosComunes.valAnVigMsj);
        }
    },
    invocarFirma: function () {
        var componenteFirma = {
            rfc: parent.FirmanteCtrl.rfc,
            curp: parent.FirmanteCtrl.curp,
            fechaElectronica: new Date(),
            cad_original: $('#contenidoFirmar').val(),
            registroPatronal: $("#spanNRP").text(),
            nombreCompleto: parent.FirmanteCtrl.nombreRazonSocial,
            idTipoSolicitud: 59,
            descripcionTipoSolicitud: "ESCRITO DE DESACUERDO",
            idTipoTramite: [155]
        };

        if (typeof componenteFirmaElectronica !== "undefined") {
            componenteFirmaElectronica.callback = registroDesacuerdoCtrl.procesarFirmado;
            componenteFirmaElectronica.firmarTramite(componenteFirma);
        }
    },
    procesarFirmado: function (responseFirma) {
        $.postJSON(registroDesacuerdoCtrl.contextApp + "/escrito/wizard/procesarDatosFirma", responseFirma, function (data) {
            registroDesacuerdoCtrl.procesarFinalizado(true);
        }).error(function (data) {
            registroDesacuerdoCtrl.dialogos.errorFinalizar.mensaje = data.mensaje;
            dialogosCtrl.abrirDialogo(registroDesacuerdoCtrl.dialogos.errorFinalizar);
        });
    },
    guardarTramite: function () {
        if (registroDesacuerdoCtrl.validaAnVign()) {
            registroDesacuerdoCtrl.procesarFinalizado(false);
        } else {
            dialogosCtrl.openDialAviso(dialogosComunes.valAnVigMsj);
        }
    },
    validaAnVign: function () {
        var conFirm = false;
        var vlSiNo = $('#anVigencia').val();

        if (vlSiNo != "" && vlSiNo != null) {
            conFirm = true;
        }
        return conFirm;
    },
    procesarFinalizado: function (finalizar) {
        if (registroDesacuerdoCtrl.validafechnot) {
            const $objForm = registroDesacuerdoCtrl.crearForm();
            registroDesacuerdoCtrl.objFrimEscrito = $objForm;
            var escrito = registroDesacuerdoCtrl.objFrimEscrito,
                urlOperacion = registroDesacuerdoCtrl.contextApp + "/escrito/wizard/" + (finalizar ? "finalizar" : "guardar") + "Tramite";

            $.blockUI();
            $.postJSON(urlOperacion, escrito, function (data) {
                if (data.correcto) {
                    if ($("#calleInp").val() !== "") {
                        var respSaveDom = "";
                        if (finalizar) {
                            var idTramite = escrito.tramiteId, idEs = data.traEscDes.idEscrito,
                                folioRecep = data.solicitud.tramites[0].folioRecepcion;
                            respSaveDom = registroDesacuerdoCtrl.guardarDomicilio(idTramite, idEs, folioRecep);
                        } else {
                            var idTramite = escrito.tramiteId, idEs = "0", folioRecep = "guardar";
                            respSaveDom = registroDesacuerdoCtrl.guardarDomicilio(idTramite, idEs, folioRecep);
                        }
                    }

                    setTimeout(() => {
                        if (finalizar) {
                            var folioSolicitud = data.solicitud.noFolioSolicitud,
                                folioRecepcion = data.solicitud.tramites[0].folioRecepcion;
                            console.log("el folio de la solicitud es: " + folioSolicitud + " y el folio de recepcion es " + folioRecepcion);
                            if (registroDesacuerdoCtrl.origenApp == "1") {
                                location.href = registroDesacuerdoCtrl.contextApp + "/escrito/wizard/tramiteFinalizado";
                            } else {
                                $.unblockUI()
                                var opcionesMensaje = registroDesacuerdoCtrl.dialogos.tramiteFinalizado;
                                opcionesMensaje.mensaje += "Folio solicitud: <strong>" + folioSolicitud + "</strong><br>";
                                opcionesMensaje.mensaje += "Folio recepci&oacute;n: <strong>" + folioRecepcion + "</strong><br>";
                                dialogosCtrl.abrirDialogo(opcionesMensaje);
                            }
                        } else {
                            $.unblockUI()
                            dialogosCtrl.abrirDialogo(registroDesacuerdoCtrl.dialogos.guardado);
                        }
                    }, 3000);
                } else {
                    $.unblockUI()
                    console.log("ocurrio un error al finalizar el tramite");
                    registroDesacuerdoCtrl.dialogos.errorFinalizar.mensaje = data.mensaje;
                    dialogosCtrl.abrirDialogo(registroDesacuerdoCtrl.dialogos.errorFinalizar);
                }
            }).error(function () {
                $.unblockUI()
                console.log("ocurrio un error al finalizar el tramite");
                dialogosCtrl.abrirDialogo(registroDesacuerdoCtrl.dialogos.errorFinalizar);
            });
        } else {
            dialogosCtrl.openDialAviso(dialogosComunes.menFecNot);
        }
    }, guardarDomicilio: function (idTramite, idEscrito, folioRecepc) {

        const objForm = {
            "idDomEscrito": "", "idEscrito": "", "folioRecepcion": "", "desDomicilio": "",
            "domNumExterior": "", "domNumInterior": "", "refCodPostal": "", "desCiudad": "",
            "desEstado": ""
        }

        const domEscrito = Object.create(objForm);
        domEscrito.idDomEscrito = idTramite;
        domEscrito.idEscrito = idEscrito;
        domEscrito.folioRecepcion = folioRecepc;
        domEscrito.desDomicilio = $('#calleInp').val();
        domEscrito.domNumExterior = $('#numExt').val();
        domEscrito.domNumInterior = $('#numInt').val();
        domEscrito.refCodPostal = $('#cpInp').val();
        domEscrito.desCiudad = $('#ciudadInp').val();
        domEscrito.desEstado = $('#edoInp').val();

        var urlDomOper = registroDesacuerdoCtrl.contextApp + "/escrito/wizard/registraDomEscrito";

        $.postJSON(urlDomOper, domEscrito, function (data) {
            if (data.correcto) {
                console.log("Se ha registrado el domicilio con exito");
            } else {
                console.log("No se pudó registrar el domicilio de forma correcta");
            }
        }).error(function () {
            console.log("ocurrio un error al guardar el domicilio del tramite");
        });
    }, crearForm: function () {
        var vaList = [];
        var mtivoDes = '#motivoDesacuerdo';
        var numer = 0;

        for (i = 0; i < 10; i++) {
            if (i == 0) {
                if ($('#motivoDesacuerdo').val() != "" || $('#motivoDesacuerdo').val() != undefined)
                    vaList.push($('#motivoDesacuerdo').val());
            } else {
                if ($(mtivoDes + i).val() !== "" && $(mtivoDes + i).val() !== undefined && $(mtivoDes + i).val() !== null) {
                    vaList.push($(mtivoDes + i).val());
                    if(numer>0)
                        $(mtivoDes + (i-numer)).val($(mtivoDes + i).val());
                }else
                    numer+=1;
            }
        }

        if(numer>0 && vaList.length > 0){

            if((vaList.length-1) == 0){
                $('#motivoDesacuerdo').val('');
            }else{
                var vta = vaList.length;
                for(i=vta; i<(vta+numer); i++){
                    var hid = '#hideMotivoCamp'+i;
                    var vlors = "#motivoDesacuerdo"+i;
                    $(vlors).val('');
                    $(hid).addClass('hidden');
                }
            }
        }

        const objForm = {
            "tramiteId": "",
            "causaDesacuerdo": {"idCausaDes": "", "materiaDesacuerdo.idMateria": ""},
            "folioImpugnado": "", "anVigencia": "", "claseAnterior": "", "fracAnterior": "",
            "primAnterior": "", "trabajadorProm": "", "fechNotRes": "", "mail": "",
            "motivosDesacuerdo.idMotivoDes": "", "motivoDesacuerdo": "", "motivoDesacuerdo1": "",
            "motivoDesacuerdo2": "", "motivoDesacuerdo3": "", "motivoDesacuerdo4": "", "motivoDesacuerdo5": "",
            "motivoDesacuerdo6": "", "motivoDesacuerdo7": "", "motivoDesacuerdo8": "", "motivoDesacuerdo9": ""
        }

        if(registroDesacuerdoCtrl.materia == 1)
            registroDesacuerdoCtrl.causaDsdo = 4;
        else
            registroDesacuerdoCtrl.causaDsdo = $('#materiaDeterminacion').val();

        const me = Object.create(objForm);
        me.tramiteId = $('#tramiteId').val();
        me.causaDesacuerdo = {
            "idCausaDes": parseFloat(registroDesacuerdoCtrl.causaDsdo),
            materiaDesacuerdo: {"idMateria": registroDesacuerdoCtrl.materia}
        };
        me.folioImpugnado = $('#folioImpugnado').val();
        me.anVigencia = $('#anVigencia').val();
        me.claseAnterior = $('#claseAnterior').val();
        me.fracAnterior = $('#fracAnterior').val();
        me.primAnterior = $('#primAnterior').val();
        me.trabajadorProm = $('#trabajadorProm').val();
        me.fechNotRes = convertDateCorrect($('#fechNotRes').val());
        me.mail = $('#mail').val();
        me.motivosDesacuerdo = {"idMotivoDes": registroDesacuerdoCtrl.motivosDesacuerdo};

        for (i = 0; i < 10; i++) {
            if (i == 0) {
                $(mtivoDes).val("");
            } else {
                $(mtivoDes + i).val("");
            }
        }
        for (i = 0; i < vaList.length; i++) {
            $(mtivoDes + i).val(vaList[i]);
            switch (i) {
                case 0:
                    $(mtivoDes).val(vaList[i]);
                    me.motivoDesacuerdo = vaList[i];
                    break;
                case 1:
                    me.motivoDesacuerdo1 = vaList[i];
                    break;
                case 2:
                    me.motivoDesacuerdo2 = vaList[i];
                    break;
                case 3:
                    me.motivoDesacuerdo3 = vaList[i];
                    break;
                case 4:
                    me.motivoDesacuerdo4 = vaList[i];
                    break;
                case 5:
                    me.motivoDesacuerdo5 = vaList[i];
                    break;
                case 6:
                    me.motivoDesacuerdo6 = vaList[i];
                    break;
                case 7:
                    me.motivoDesacuerdo7 = vaList[i];
                    break;
                case 8:
                    me.motivoDesacuerdo8 = vaList[i];
                    break;
                case 9:
                    me.motivoDesacuerdo9 = vaList[i];
                    break;
            }
        }
        return me;
    }, buscarDomicilio: function (idDomicilio) {
        if (idDomicilio != null || idDomicilio != "") {
            const me = Object.create({"idDomEscrito": idDomicilio,});
            me.idDomEscrito = idDomicilio;

            var urlDomOper = registroDesacuerdoCtrl.contextApp + "/escrito/wizard/findDomEscrito";

            $.postJSON(urlDomOper, me, function (data) {
                if (data.correcto && data.domicilios != null) {
                    var $form = data.domicilios;

                    if ($form.desDomicilio != null) {
                        $('#calleInp').val($form.desDomicilio);
                    }
                    if ($form.domNumExterior != null) {
                        $('#numExt').val($form.domNumExterior);
                    }
                    if ($form.domNumInterior != null) {
                        $('#numInt').val($form.domNumInterior);
                    }
                    if ($form.refCodPostal != null) {
                        $('#cpInp').val($form.refCodPostal);
                    }
                    if ($form.desCiudad != null) {
                        $('#ciudadInp').val($form.desCiudad);
                    }
                    if ($form.desEstado != null) {
                        $('#edoInp').val($form.desEstado);
                    }

                    $("#mosBtnAdr").click();
                }
            }).error(function () {
                console.log("ocurrio un error al buscar el domicilio del tramite");
            });
        }
    },
    dialogos: {
        "guardado": {
            titulo: 'Guardado',
            mensaje: "Los cambios han sido guardados con exito.",
            buttons: {
                'Aceptar': function () {
                    $(this).dialog('close');
                }
            }
        },
        "errorFinalizar": {
            titulo: 'Error',
            mensaje: "Ocurri&oacute; un error al finalizar el tr&aacute;mite, intentalo mas tarde.",
            buttons: {
                'Aceptar': funcionesComunes.procesarSalirTramite
            }
        },
        "tramiteFinalizado": {
            titulo: 'Solicitud finalizada',
            mensaje: 'Tu solicitud ha finalizado correctamente.<br>',
            buttons: {
                'Aceptar': funcionesComunes.procesarSalirTramite,
                'Ver documentos': funcionesComunes.verDocumentos
            }
        }
    },
    validaFecNotificacion: function (fecNot) {
        registroDesacuerdoCtrl.validafechnot = true;
        $('#fechNotRes').removeAttr("min");
        if (registroDesacuerdoCtrl.materia == 2) {
            var fecRec = new Date(convertDateFechNot(fecNot));
            var listaFestivos = []
            var url = "/escrito/diasFestivos";

            $.ajax({
                url: registroDesacuerdoCtrl.contextApp + url,
                data: null,
                cache: false,
                type: 'GET',
                success: function (data) {
                    var num = data.actual.length;
                    for(var i = 0; i<num;i++ ){

                        var newDate = new Date(convertDateFestivo(data.actual[i].fecha));
                        listaFestivos.push(newDate)
                    }

                    var dateResta = new Date();
                    var inc=0;
                    var i = 0;
                    while (i <= 15) {
                        //fecRec.setTime(fecRec.getTime() + 24 * 60 * 60 * 1000); // Se añade 1 día
                        fecRec.setDate(fecRec.getDate()+1);
                        if (fecRec.getDay() != 6 && fecRec.getDay() != 0){
                            var val = 0;
                            for (var x=0; x < listaFestivos.length;x++){
                                var newDate = new Date(listaFestivos[x]);
                                //console.log('Inicia comparacion: newDate: '+newDate+' fecRec: '+fecRec);
                                if(fecRec.getTime() == newDate.getTime()){
                                    val++;
                                    break;
                                }
                            }
                            if(val==0)
                                i++;
                        }
                        inc++;
                    }

                    dateResta.setDate(dateResta.getDate()-inc);
                    var fecUpd = new Date(fecRec.getFullYear() + '-' + (fecRec.getMonth() + 1) + '-' + fecRec.getDate());
                    var fecHoy = new Date(new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate());

                    if (fecHoy > fecUpd) {
                        dialogosCtrl.openDialAviso(dialogosComunes.menFecNot);
                        registroDesacuerdoCtrl.validafechnot = false;
                        registroDesacuerdoCtrl.minFechRes = dateResta.getFullYear() +'-'+(dateResta.getMonth()+1)+'-'+dateResta.getDate();
                        jQuery.validator.addMethod("minDate", function (value, element) {
                            var check = false;
                            var min = new Date(registroDesacuerdoCtrl.minFechRes);
                            var segundo = new Date (value);
                            if (segundo >= min)
                                check=true;

                            return this.optional(element) || check;
                        }, 'Por favor, escribe una fecha mayor o igual a '+convertDateValidaFec(registroDesacuerdoCtrl.minFechRes));
                        document.getElementById("fechNotRes").setAttribute("minDate", registroDesacuerdoCtrl.minFechRes);
                        document.getElementById("fechNotRes").setAttribute("min", registroDesacuerdoCtrl.minFechRes);
                    }
                }
            });
        }
    },
    validaRegDuplicado: function () {
        const objForm = {"folioImpugnado": "", "anVigencia": ""}

        const $objForm = Object.create(objForm);
        $objForm.folioImpugnado = $('#folioImpugnado').val();
        $objForm.anVigencia = $('#anVigencia').val();

        var folio = $("#folioImpugnado").val(), vig = $('#anVigencia').val();

        if (folio !== "undefined" && folio != null && folio != "") {
            if (vig !== "undefined" && vig != null && vig != "") {
                    var urlOperacion = registroDesacuerdoCtrl.contextApp + "/escrito/wizard/tramiteDuplicado";

                    $.blockUI();
                    $.postJSON(urlOperacion, $objForm, function (data) {
                        if (data.correcto) {
                            $.unblockUI()
                            $("#trabajadorProm").focus();
                        } else {
                            $.unblockUI()
                            console.log("Existe un registro capturado con anterioridad, no puede haber dos registros bajo el mismo acto");
                            registroDesacuerdoCtrl.dialogos.errorFinalizar.mensaje = data.mensaje;
                            dialogosCtrl.abrirDialogo(registroDesacuerdoCtrl.dialogos.errorFinalizar);
                        }
                    }).error(function () {
                        $.unblockUI()
                        console.log("Ocurrio un error al consultar registros duplciados de actos");
                        dialogosCtrl.abrirDialogo(registroDesacuerdoCtrl.dialogos.errorFinalizar);
                    });
            } else {
                $("#anVigencia").focus();
            }
        } else {
            $("#folioImpugnado").focus();
        }
    },
    vaidacorAnterior: function () {
        console.log("inicio el validador");
        var numerValid = "El n&uacute;mero capturado debe ser entre 0.0005 y 15";
        var requiere = "Es necesario que se introdusca un n&uacute;mero";

        $("#forModalDlg").validate({
            errorClass: "errorDocs",
            errorElement: "span",
            rules: {
                "claseAnt": {
                    required: true
                },
                "fracAnt": {
                    required: true
                },
                "primaAnt": {
                    required: true,
                    minLength: 6,
                    maxlength: 7,
                    min: 0.0005,
                    max: 15
                },
                "claseDet": {
                    required: true
                },
                "fracDet": {
                    required: true
                },
                "primaDet": {
                    required: true,
                    minLength: 6,
                    maxlength: 7,
                    min: 0.0005,
                    max: 15
                }
            },
            messages: {
                "primaAnt": {min: numerValid, max: numerValid, required: requiere},
                "primaDet": {min: numerValid, max: numerValid, required: requiere}
            }
        });
    },
    convertDateCorrectRetrn: function (date) {
        const dats = date;
        const [day, month, year] = dats.split('/');
        const res = [year, month, day].join('-');
        return res;
    },
    agregarCampoParaMotivo: function () {
        var res = $('#motivoDesacuerdo').val();
        var conts = 0;

        if(registroDesacuerdoCtrl.validMtvoCamp == 0){
            if($('#motivoDesacuerdo').val() != "" && $('#motivoDesacuerdo').val() != null && $('#motivoDesacuerdo').val() != " "){
                $('#hideMotivoCamp1').removeClass('hidden');
            conts+=1;}
        }else{
            for(i=0;i<=registroDesacuerdoCtrl.validMtvoCamp;i++){
                var nombr = "#hideMotivoCamp" + (i+1);
                var vlors = "#motivoDesacuerdo" + i;
                if(i!=0 && i!=9){
                    if($(vlors).val() != "" && $(vlors).val() != null && $(vlors).val() != " "){
                        $(nombr).removeClass('hidden');
                        conts+=1;
                    }
                }
            }
        }
        if(conts!=0){
            registroDesacuerdoCtrl.validMtvoCamp += 1;
                if(registroDesacuerdoCtrl.validMtvoCamp == 9)
                    $('#divHideGroup').addClass('hidden');
        }
    }
};

function convertDateFestivo(date) {
    const dats = date;
    const [year, month, day] = dats.split('-');
    const res = [month, day, year].join('/');
    return res;
}

function convertDateFechNot(date) {
    const dats = date;
    const [day, month, year] = dats.split('/');
    const res = [month, day, year].join('/');
    return res;
}

function convertDateValidaFec(date) {
    const dats = date;
    const [year, month, day] = dats.split('-');
    const res = [day, month, year].join('/');
    return res;
}

function validaNumOnly(e) {
    var inicial = e.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');

    if (inicial.length > 0) {
        var respuesta;
        var $thisVal = inicial;
        var entero =$thisVal.substr(0, $thisVal.indexOf(".")>0? $thisVal.indexOf("."):$thisVal.indexOf(".")==0? "":$thisVal.length).substr(0, 2);

            if (parseInt(entero)<'0' || parseInt(entero)>'15')
                entero = entero.substring(0, entero.length - 1);

        respuesta = entero;

        if ($thisVal.indexOf(".")>=0) {
            var deci = $thisVal.substr($thisVal.indexOf("."), 6);
            if (deci.length > 1) {
                var limit = deci.substring(1,deci.length);
                if($thisVal.indexOf(".")>=0)
                    deci = "."+limit.replace(".","");

                if(entero == "" || entero == 0 && deci.length >= 2){
                    var extra = deci.substring(1,2);
                    if(extra == 0 || extra == 1 || extra == 2 || extra == 3 || extra == 4)
                        deci=".";
                }

                if(entero == '15' && deci.length >= 2){
                    deci="";
                }
            }
            respuesta = respuesta + deci;
        }
        e.value = respuesta;
    } else{
        e.value = inicial;}
}

function validaOnluTrab(e) {
    var inicial = e.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');

    if (inicial.length > 0) {
        var respuesta;
        var $thisVal = inicial;
        var entero =$thisVal.substr(0, $thisVal.indexOf(".")>0? $thisVal.indexOf("."):$thisVal.indexOf(".")==0? "":$thisVal.length).substr(0, 6);

        if (parseInt(entero)<'0' || parseInt(entero)>'99999')
            entero = entero.substring(0, entero.length - 1);

        respuesta = entero;
        if ($thisVal.indexOf(".")>=0) {
            var deci = $thisVal.substr($thisVal.indexOf("."), 2);
            if (deci.length > 1) {
                var limit = deci.substring(1,deci.length);
                if($thisVal.indexOf(".")>=0)
                    deci = "."+limit.replace(".","");
            }
            respuesta = respuesta + deci;
        }
        e.value = respuesta;
    } else{
        e.value = inicial;}
}

function validOnlyNum(e) {
    var inicial = e.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');

    if (inicial.length > 0) {
        var $thisVal = inicial;
        var entero =$thisVal.substr(0, $thisVal.indexOf(".")>0? $thisVal.indexOf("."):$thisVal.indexOf(".")==0? "":$thisVal.length).substr(0, 5);

        if (parseInt(entero)<'00001' || parseInt(entero)>'99999')
            entero = entero.substring(0, entero.length - 1);

        e.value = entero;
    } else{
        e.value = inicial;}
}

function cargarFraccion(ide) {
    var $fraccionList;
    var claSelct;
    if(ide == 1){
        $fraccionList = $('#fracAnt');
        claSelct = $('#claseAnt');
    }else{
        $fraccionList = $('#fracDet');
        claSelct= $('#claseDet');
    }

    $fraccionList.val($('option[value !=""]', $fraccionList).remove());
    $fraccionList.val("");
    var claseIn = claSelct.val();

    var sSource = registroDesacuerdoCtrl.contextApp + "/escrito/wizard/cargaFraccion";
    var request = $.ajax({
        url : sSource + "?clase=" + claseIn,
        async : false,
        type : "POST",
        dataType : "json",
        contentType : "application/json; charset=utf-8",
    });
    request.done(function(response) {

        var data = response.fracClaseLst;
        var select = $fraccionList;

        for (var index = 0; index < data.length; index++) {
            var opt = document.createElement('option');
            opt.value = data[index].descMotivoDes;
            opt.innerHTML = data[index].descMotivoDes;
            select.append(opt);
        }
    });
}

function inputChar(event) {
    if (event.keyCode == 13)
        document.getElementById('motivoDesacuerdo1').focus();
}
function inputChar1(event) {
    if (event.keyCode == 13)
        document.getElementById('motivoDesacuerdo2').focus();
}
function inputChar2(event) {
    if (event.keyCode == 13)
        document.getElementById('motivoDesacuerdo3').focus();
}
function inputChar3(event) {
    if (event.keyCode == 13)
        document.getElementById('motivoDesacuerdo4').focus();
}
function inputChar4(event) {
    if (event.keyCode == 13)
        document.getElementById('motivoDesacuerdo5').focus();
}
function inputChar5(event) {
    if (event.keyCode == 13)
        document.getElementById('motivoDesacuerdo6').focus();
}
function inputChar6(event) {
    if (event.keyCode == 13)
        document.getElementById('motivoDesacuerdo7').focus();
}
function inputChar7(event) {
    if (event.keyCode == 13)
        document.getElementById('motivoDesacuerdo8').focus();
}
function inputChar8(event) {
    if (event.keyCode == 13)
        document.getElementById('motivoDesacuerdo9').focus();
}


$(document).ready(registroDesacuerdoCtrl.init);
