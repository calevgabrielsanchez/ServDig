function validarFormato(regla, campos) {

    var str = "¡Error! ";
    var errorFormulario = str.bold();
    var validacion = "";
    var valor = $("#" + campos.campo).val();
    generarEstiloError(campos.campo, false);
    switch (regla) {
        case ('limiteClase'):
            if ((valor < 1 || valor > 5)) {
                validacion = errorFormulario + "Se debe captura un valor entre 1 y 5.";
                generarEstiloError(campos.campo, true);
            }
            break;
        case ('limitePrima'):
            if ((valor < 0.0005 || valor > 15)) {
                validacion = errorFormulario + "Se debe captura un valor entre 0.0005 y 15.";
                generarEstiloError(campos.campo, true);
            }
            break;
        case ('limiteDuplic'):
            var datNow = new Date();
            if ((valor > datNow.getFullYear || valor < (datNow.getFullYear - 100))) {
                validacion = errorFormulario + "Se debe captura un valor entre "+(datNow.getFullYear() - 100)+" y "+datNow.getFullYear();
                generarEstiloError(campos.campo, true);
            }
            break;
        case ('formatoCorreo'):
            valor = valor.toLowerCase();
            var expregCorreo = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.([a-zA-Z]{2,4})+$/;
            if (!expregCorreo.test(valor)) {
                validacion =errorFormulario + "Correo electr&oacute;nico incorrecto(a). Por favor verifique.";
                generarEstiloError(campos.campo, true);
            }
            break;
        default:

            break;

    }
    return validacion;
}

function validarCampos(regla, campos) {

    var str = "¡Error! ";
    var errorFormulario = str.bold();
    var validacion = "";
    var valor = $("#" + campos.campo).val();
    var valor2 = $("#" + campos.campo2).val();
    switch (regla) {
        case ('passwordIgual'):
            if (valor !== valor2) {
                validacion = "Tu Contraseña no coincide vuelve a intentarlo.";
                generarEstiloError(campos.campo, true);
            }
            break;
        case ('correoIgual'):
            if (valor !== valor2) {
                validacion = "Tu dirección de correo electrónico no coincide vuelve a intentarlo.";
                generarEstiloError(campos.campo, true);
            }
            break;
        case ('igual'):
            if (valor === valor2) {
                validacion = "La contraseña nueva es igual a la contrase�a actual, favor de verificar.";
                generarEstiloError(campos.campo, true);
            }
            break;
        case ('combos'):
            if (valor === "null"||valor === "" || valor === undefined || valor === null || valor === "0"|| valor === "-1") {
                validacion = errorFormulario + "No ha llenado todos los campos requeridos. Por favor verifique.";
                generarEstiloError(campos.campo, true);
            }
            break;

    }
    return validacion;
}

function validarCamposCompletos(regla, campos) {

    var str = "¡Error! ";
    var errorFormulario = str.bold();
    var validacion = "";
    var valor = $("#" + campos.campo).val();
    try {
        var div = $("#" + campos.campo)["0"].parentElement.parentElement.firstElementChild.firstElementChild;
        var lab = $("#" + campos.campo)["0"].parentElement.parentElement.children[2];
    } catch (e) {

    }

    switch (regla) {
        case ('campoObligatorio'):
            if ((valor === "" || valor === undefined || valor === null)) {
                validacion = errorFormulario + "No ha llenado todos los campos requeridos. Por favor verifique.";
                generarEstiloErrorCampo(campos.campo, true);
                generarEstiloSpan(div, true);
                labelCampoObligatorio(lab, true);
            } else {
                generarEstiloErrorCampo(campos.campo, false);
                generarEstiloSpan(div, false);
                labelCampoObligatorio(lab, false);
            }
            break;
        case ('combosCompletos'):
            if (valor === "null"||valor === "" || valor === undefined || valor === null || valor === "0"|| valor === "-1") {
                validacion = errorFormulario + "No ha llenado todos los campos requeridos. Por favor verifique.";
                generarEstiloErrorCampo(campos.campo, true);
                generarEstiloSpan(div, true);
                labelCampoObligatorio(lab, true);
            } else {
                generarEstiloErrorCampo(campos.campo, false);
                generarEstiloSpan(div, false);
                labelCampoObligatorio(lab, false);
            }
            break;
    }
    return validacion;
}

function validarCampo(reglas) {

    $('div#mensajesError').html();
    for (cont = 0; cont < reglas.length; cont++) {
        var mensaje = validarReglaCampos(reglas[cont].validacion, reglas[cont].campos);
        if (mensaje !== "") {
            $('div#mensajesError').html(generarMensajeError(mensaje));
            return false;
        }
    }
    return true;
}

function validarReglaCampos(regla, campos) {

    var comprobacion = "";
    for (var contador = 0; contador < campos.length; contador++) {
        var validacion = validarFormato(regla, campos[contador]);
        var validacion2 = validarCampos(regla, campos[contador]);
        var validacion3 = validarCamposCompletos(regla, campos[contador]);
        if (comprobacion === "" && validacion !== "" && validarFormato !== "") {
            comprobacion = validacion;
        } else if (comprobacion === "" && validacion2 !== "" && validarCampos !== "") {
            comprobacion = validacion2;
        } else if (comprobacion === "" && validacion3 !== "" && validarCamposCompletos !== "") {
            comprobacion = validacion3;
        }
    }
    return comprobacion;
}

function generarMensajeError(mensaje) {

    var html = '<div class="alert alert-danger">';
    html += '<p>';
    html += mensaje;
    html += '</p></div>';
    return html;
}

function generarEstiloSpan(campo, estado) {

    if (campo != null || campo != undefined || campo != "") {
        if (estado) {

            campo.className = "red";
        } else {
            campo.className = "";
        }
    }

}

function labelCampoObligatorio(campo, estado) {

    if (campo != null || campo != undefined || campo != "") {
        if (estado) {

            campo.style.display = "block";
        } else {
            campo.style.display = "none";
        }
    }

}

function generarEstiloErrorCampo(campo, estado) {

    if (estado) {

        if (campo === "claseAnt") {
            $("#claseAnt").attr('style', ' border:1px solid red;');
        } else if (campo === "nrp_2") {
            $("#nrp_2").attr('style', 'width: 100px ; border:1px solid red;');
        } else if (campo === "nrp_3") {
            $("#nrp_3").attr('style', 'width: 60px ; border:1px solid red;');
        } else if (campo === "nrp_4") {
            $("#nrp_4").attr('style', 'width: 40px ; border:1px solid red;');
        } else {
            $("#" + campo).attr('style', 'border:1px solid red; resize: none;');
        }
    } else {

        if (campo === "claseAnt") {
            $("#claseAnt").attr('style', ' border:1px solid #cccccc;');
        } else if (campo === "nrp_2") {
            $("#nrp_2").attr('style', 'width: 100px ; border:1px solid #cccccc;');
        } else if (campo === "nrp_3") {
            $("#nrp_3").attr('style', 'width: 60px ; border:1px solid #cccccc;');
        } else if (campo === "nrp_4") {
            $("#nrp_4").attr('style', 'width: 40px ; border:1px solid #cccccc;');
        } else {
            $("#" + campo).attr('style', 'border:1px solid #cccccc; resize: none;');
        }
    }

}

function generarMensajeSucces(mensaje) {

    var html = '<div class="alert alert-success">';
    html += '<p>';
    html += mensaje;
    html += '</p></div>';
    return html;
}

function generarEstiloError(campo, estado) {

    if (estado) {
        $("#" + campo).prop('style', 'border:1px solid red');
        if (campo === "claseAnt") {
            $("#claseAnt").prop('style', 'border:1px solid red');
        } else if (campo === "nrp_2") {
            $("#nrp_2").prop('style', 'width: 100px ; border:1px solid red');
        } else if (campo === "nrp_3") {
            $("#nrp_3").prop('style', 'width: 60px ; border:1px solid red');
        } else if (campo === "nrp_4") {
            $("#nrp_4").prop('style', 'width: 40px ; border:1px solid red');
        }
    } else {
        $("#" + campo).prop('style', 'border:1px solid #cccccc');
        if (campo === "claseAnt") {
            $("#claseAnt").prop('style', 'border:1px solid #cccccc');
        } else if (campo === "nrp_2") {
            $("#nrp_2").prop('style', 'width: 100px ; border:1px solid #cccccc');
        } else if (campo === "nrp_3") {
            $("#nrp_3").prop('style', 'width: 60px ; border:1px solid #cccccc');
        } else if (campo === "nrp_4") {
            $("#nrp_4").prop('style', 'width: 40px ; border:1px solid #cccccc');
        }
    }

}