$.getScript(context_path + "/static/resources/js/delta/validations.js");

const ALFANUMERICO_ESPACIOS = /^[a-zA-Z0-9\s \u00D1\u00F1 ¡·…ÈÕÌ”Û⁄˙—Ò]*$/;
const MESSAGE_ERROR = '<strong>Error: </strong>';
const CAMPO_REQUERIDO = 'El campo de motivos es requerido para continuar';
const MSG_CONFIRMACION = '<br><p>\u00BFEst\u00E1 seguro que desea _accion_ para consulta por Internet el NSS del Asegurado o Pensionado\u003F</p><br>';
const MSG_NSS_BLOQUEADO = '<br><p>El NSS capturado ya se encuentra bloqueado y no puede ser consultado desde Internet.</p><br>';
const MSG_NSS_NO_BLOQUEADO = '<br><p>El NSS capturado no se encuentra bloqueado.</p><br>';
const CONTENIDO = '<form id="formDerechosArco" onsubmit="openWindow(this);" method="post"><br><div id="divErrors" class="ui-state-error ui-corner-all" style="text-align: center" hidden>' +
    '<div class="ui-icon ui-icon-alert"></div>' +
    '<p id="msjError" class="ui-helper-reset ui-state-error-text"></p><br>' +
    '</div>' +
    '<br><br><table>' +
    '<tr>' +
    '<td><label class="control-label">Motivos:*</label></td>' +
    '</tr>' +
    '<tr>' +
    '<td><textarea class="form-control alfanumerico" id="observaciones" name="observaciones" onchange="DerechosArco.mostarOcultarMensaje(null, false);" ' +
    'cols="5" maxlength="500" style="resize:none; width: 770px; height: 200px"></textarea></td>' +
    '</tr>' +
    '<tr style="text-align: right">' +
    '<td><span class="required">*</span>&nbsp;Datos Requeridos</td>' +
    '</tr>' +
    '</table></form>';

let DerechosArco = {

    action: function ($isBloqueo) {

        let _this = this;
        let nss = $('#nss').val();
        let $dialogAction = $('#dialogAction');
        $dialogAction.html(CONTENIDO);
        $dialogAction.dialog({
                autoOpen: false,
                title: $isBloqueo === true ? 'Motivos de bloqueo' : "Motivos de desbloqueo",
                show: "blind",
                hide: "explode",
                resizable: false,
                modal: true,
                height: 500,
                width: 800,
                buttons: {
                    "Aceptar": function () {
                        if (_this.validarRequeridos($('#observaciones').val())) {
                            let form_data = new FormData();
                            form_data.append("nss", nss);
                            $.ajax({
                                url : context_path + '/derechosArco/validaNSS',
                                dataType: 'json',
                                cache: false,
                                contentType: false,
                                processData: false,
                                data: form_data,
                                type: 'POST',
                                method: 'POST',
                            }).done(function(response) {
                                if (response && $isBloqueo) {
                                    _this.crearDialogo(MSG_NSS_BLOQUEADO);
                                } else if (!response && !$isBloqueo) {
                                    _this.crearDialogo(MSG_NSS_NO_BLOQUEADO);
                                } else {
                                    _this.confirmacion($(this), nss, $isBloqueo);
                                }
                            }).fail(function () {
                                _this.mostarOcultarMensaje("Ocurrio un error inesperado", true);
                            });
                        } else {
                            _this.mostarOcultarMensaje(CAMPO_REQUERIDO, true);
                        }
                    },
                    "Cancelar": function () {
                        _this.cerrarDialogo($(this));
                    }
                }
            }
        ).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
        $dialogAction.parent().attr('id', 'dialogAction');

        validateForm.allowOnlyRegularExpression($('.alfanumerico'), ALFANUMERICO_ESPACIOS);
        $dialogAction.dialog('open');
    },

    confirmacion: function ($dialogo, $nss, $isBloqueo) {

        $dialogo.dialog('close');
        let $dialogoConfirmacion = $('<div></div>');
        let form = $('#formDerechosArco');
        form.attr('action', context_path + '/derechosArco/generarSolicitud/' + $nss + '/' + $isBloqueo);
        $dialogoConfirmacion.html(MSG_CONFIRMACION.replace("_accion_",($isBloqueo === true ? 'bloquear' : "desbloquear")));
        $dialogoConfirmacion.dialog({
                id: "dialogoConfirmacion",
                autoOpen: false,
                title: 'Confirmacion',
                show: "blind",
                hide: "explode",
                resizable: false,
                modal: true,
                height: 200,
                width: 500,
                buttons: {
                    "Aceptar": function () {
                        $dialogoConfirmacion.dialog('close');
                        $dialogoConfirmacion.dialog('destroy');
                        $dialogoConfirmacion.html('');
                        form.submit();
                        setTimeout(function () {
                            DerechosArco.cerrarDialogo($('#dialogAction'));
                            $.unblockUI();
                            DerechosArco.crearDialogo("El NSS del Asegurado o Pensionado se ha " + ($isBloqueo === true ? 'bloqueado' : "desbloqueado") + " correctamente.");
                        }, 5000);
                    },
                    "Cancelar": function () {
                        $dialogoConfirmacion.dialog('close');
                        $dialogoConfirmacion.dialog('destroy');
                        $dialogoConfirmacion.html('');
                        form.removeAttr('action');
                        $dialogo.dialog('open');
                    }
                }
            }
        ).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
        $dialogoConfirmacion.dialog('open');
    },

    crearDialogo: function ($mensaje) {

        let _this = this;
        _this.cerrarDialogo($('#dialogAction'));
        let $dialogoMensajes = $('<div></div>');
        $dialogoMensajes.html($mensaje);
        $dialogoMensajes.dialog({
                autoOpen: false,
                title: 'Finalizar',
                show: "blind",
                hide: "explode",
                resizable: false,
                modal: true,
                height: 200,
                width: 500,
                buttons: {
                    "Aceptar": function () {
                        $(this).dialog('close');
                        $(this).dialog('destroy');
                        $(this).html('');
                    }
                }
            }
        ).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
        $dialogoMensajes.dialog('open');
    },

    validarRequeridos: function ($campo) {
        return $campo !== undefined && $campo !== "" && $campo.length > 0 ;
    },

    mostarOcultarMensaje: function ($message, $mostar) {
        if ($mostar) {
            $('#msjError').html(MESSAGE_ERROR + $message);
            $('#divErrors').show();
            $('#observaciones').css("border-color", "red");
        } else {
            $('#msjError').html('');
            $('#divErrors').hide();
            $('#observaciones').css("border-color", "");
        }
    },

    cerrarDialogo: function ($dialogo) {
        $dialogo.dialog('close');
        $dialogo.dialog('destroy');
        $dialogo.html('');
    }
};

function openWindow(form) {
    window.open('', 'formpopup', 'width=800,height=800,resizeable,scrollbars');
    form.target = 'formpopup';
}

