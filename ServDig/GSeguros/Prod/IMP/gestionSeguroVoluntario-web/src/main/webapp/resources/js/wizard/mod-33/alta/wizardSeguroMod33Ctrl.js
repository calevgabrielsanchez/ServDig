var WizardAltaSeguroFamiliarCtrl = {

    setDatos: function (_idPersona, _nssCifrado) {
        this.config.idPersona = _idPersona;
        this.config.nssCifrado = _nssCifrado;
        this.init(this.config.container, this.config.idPersona,
                this.config.nssCifrado);
    },

    setMuestraWizard: function (muestraWiz) {
        this.config.muestraWizard = muestraWiz;
    },

    init: function (_container, _idPersona, _nssCifrado, _correo) {

        this.config.container = _container;
        this.config.idPersona = _idPersona;
        this.config.nssCifrado = _nssCifrado;
        this.config.correo = _correo;
        this.config.renovacion = false;

        var c = null;
        if ($('#' + _container).lenght == 0) {
            c = $('#' + _container, parent.document);
        } else {
            c = $('#' + _container);
        }

        this.dialogo = c.dialog({
            title: this.config.title,
            autoOpen: false,
            width: 900,
            modal: true,
            resizable: false,
            autoResize: true,
            overlay: {
                opacity: 0.5,
                background: 'black'
            },
            position: {
                my: 'top',
                at: 'top',
                of: window,
                offset: '0 10'
            },
            beforeClose: function (event, ui) {
                var windowFrame = window.frames['wizardAltaSeguroFamiliarFrame'].contentWindow;

                if (windowFrame === undefined || windowFrame == null) {
                    windowFrame = window.frames['wizardAltaSeguroFamiliarFrame'].frameElement.contentWindow;
                }

                var cancel = windowFrame.cancelable;
                if (cancel !== undefined && cancel == true) {
                    event.preventDefault();
                    windowFrame.cancelarDesdeBoton = false;
                    windowFrame.dialogoConfirmarCancelar.dialog('open');

                }
            },
            close: function (event, ui) {
                WizardAltaSeguroFamiliarCtrl.limpiarDatos();
                $(this).dialog('destroy').empty();
            }
        });
    },

    open: function () {
        this.dialogo.dialog('open');

        var url = this.config.baseUrl + '/alta/init/' + this.config.idPersona + '/'
                + this.config.nssCifrado;

        $('#' + this.config.container).html(
                '<iframe id="wizardAltaSeguroFamiliarFrame" src="'
                + url
                + '" width="100%" height="100%" frameborder="0"'
                + 'onload="set_size(\'wizardAltaSeguroFamiliarFrame\')" frameborder="0" />');
    },

    mostrarMensaje :function (mensaje) {
        var _wizard = this;
        $("#textoMensaje").html(mensaje);
        var objDialogo = $("#dialogoMensajes").dialog({
            autoOpen: false,
            resizable: false,
            modal: true,
            height: 'auto',
            width: 400,
            title: "Mensaje del sistema",
            buttons: [
                {
                    text: "Aceptar",
                    class: "btn btn-default",
                    click: function () {
                        $(this).dialog("close");
                    }
                }

            ]
        });
        objDialogo.dialog('open');
    },

    openListaSeguros: function () {

        this.dialogo.dialog('open');

        var url = this.config.baseUrl + '/lista/' + this.config.idPersona;

        $('#' + this.config.container).html(
                '<iframe id="wizardAltaSeguroFamiliarFrame" src="'
                + url
                + '" width="100%" height="100%" frameborder="0"'
                + 'onload="set_size(\'wizardAltaSeguroFamiliarFrame\')" frameborder="0" />');
        if (this.config.renovacion) {
            //Cambiando el titulo del dialogo
            $("#divWizardSeguroFamiliar").dialog('option', 'title', 'Renovaci\u00f3n al Seguro de Salud para la Familia');
        }else{
            $("#divWizardSeguroFamiliar").dialog('option', 'title', 'Incorporaci\u00F3n al Seguro de Salud para la Familia');
        }
    },

    close: function () {
        if (!$.isEmptyObject(this.dialogo)) {
            this.dialogo.dialog('close');
        } else {
            $('#wizardAltaSeguroFamiliarFrame').parent().dialog('close');
        }
    },

    abrir: function () {
        this.validarAccesoTramite();
    },

    cerrar: function () {
        this.close();
    },

    validarAccesoTramite: function () {
        var _wizard = this;

        $.blockUI();
        console.log("muestraWizard: "+this.config.muestraWizard);

        if(this.config.muestraWizard==true){
            $.unblockUI();
            _wizard.mostrarMensaje('El detalle de sus seguros se est&aacute; procesando. Se recomienda cerrar las ventanas e ingresar nuevamente para verificar sus seguros');

        }else {
            console.log("valida Acceso al Tramite");
        $.ajax({
            url: this.config.baseUrl + '/validarAccesoTramite/'
                    + this.config.idPersona + '/' + this.config.correo + '/',
            dataType: 'json',
            success: function (response) {

                if (response.tieneSeguro == true) {
                    _wizard.openListaSeguros();
                } else if (response.renovacion == true) {
                    _wizard.config.renovacion = true;
                    _wizard.openListaSeguros();
                } else if (response.extemporanea == true) {
                    _wizard.dialogoRenovacionExtemporanea("#dialogoMensajes",
                            "Mensaje de sistema", response.msgError);
                } else if (response.error == true) {
                    construirDialogo("#dialogoMensajes",
                            "Mensaje de sistema", response.msgError,
                            true, undefined, undefined, 250, 400);
                } else {
                    _wizard.open();
                }
                    $.unblockUI();
            },
            error: function (error) {
                $.unblockUI();

                var msgError = error.msgError;
                if (typeof msgError === 'undefined') {
                    msgError = 'Ocurri\u00f3 un error inesperado al validar el acceso al tr\u00e1mite.';
                }

                construirDialogo("#dialogoMensajes",
                        "Mensaje de sistema", msgError, true,
                        undefined, undefined, 250, 400);
            }
        });
        }
    },

    limpiarDatos: function (listener) {
        $.ajax(this.config.baseUrl + '/comunes/limpiarDatos').done(
                function () {
                    if ($.isFunction(listener)) {
                        listener();
                    }
                }
        );
    },

    config: {
        baseUrl: '/${mvn.web.app.root}/wizard/seguroFamiliar',
        title: 'Incorporaci\u00F3n al Seguro de Salud para la Familia',
        container: null,
        idPersona: null,
        muestraWizard: false
    },

    dialogo: {},

    dialogoRenovacionExtemporanea: function (divId, titulo, mensaje) {
        var _wizard = this;
        $("#textoMensaje").html(mensaje);
        $("#textoMensaje").removeAttr("style");
        $("#textoMensaje").attr("style", "color: blue;");
        var height = 250;
        var width = 400;
        var objDialogo = $(divId).dialog({
            autoOpen: false,
            resizable: false,
            modal: true,
            height: height,
            width: width,
            title: titulo,
            buttons: [
                {
                    text: "Cancelar",
                    class: "btn btn-default",
                    click: function () {
                        $(this).dialog("close");
                    }
                },
                {
                    text: "Siguiente",
                    class: "btn btn-primary",
                    click: function () {
                        $("#divWizardSeguroFamiliar").dialog('option', 'title', 'Incorporaci\u00f3n al Seguro de Salud para la Familia');
                        _wizard.open();
                        $(this).dialog("close");
                    }
                }
            ]
        });
        objDialogo.dialog('open');
    }

};