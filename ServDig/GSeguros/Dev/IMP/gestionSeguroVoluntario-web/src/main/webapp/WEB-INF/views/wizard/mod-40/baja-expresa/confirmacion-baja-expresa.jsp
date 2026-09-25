<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<style>
    .container-confirmacion {
        max-width: 700px;
        margin: 50px auto;
        background: #fff;
        padding: 40px;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    }
    .logo-imss {
        text-align: center;
        margin-bottom: 30px;
    }
    /*.logo-imss img {
        max-width: 200px;
    }*/
    .titulo-confirmacion {
        text-align: center;
        color: #d32f2f;
        margin-bottom: 30px;
    }
    .alert-importante {
        background-color: #fff3cd;
        border-color: #ffc107;
        color: #856404;
    }
    .checkbox-confirmacion {
        font-size: 14px;
        margin: 20px 0;
    }
    .btn-confirmar {
        background-color: #d32f2f;
        color: white;
        font-size: 16px;
        padding: 12px 30px;
        border: none;
    }
    .btn-confirmar:hover {
        background-color: #b71c1c;
        color: white;
    }
    .btn-cancelar {
        background-color: #6c757d;
        color: white;
        font-size: 16px;
        padding: 12px 30px;
        border: none;
    }
    .footer-confirmacion {
        text-align: center;
        margin-top: 30px;
        font-size: 12px;
        color: #6c757d;
    }
    #mensajeValidacion {
        margin-top: 15px;
    }
</style>

<div class="container-confirmacion">

    <!-- Logo IMSS -->
<div class="logo-imss">
<img src="/delta/resources/imagenes/gobmx/logos/LogoIMSS_2026.PNG" htmlEscape='true'
             alt="IMSS" class="img-responsive" style="display: inline-block;"/>
</div>

    <!-- T&iacute;tulo -->
<h2 class="titulo-confirmacion">
<i class="fa fa-exclamation-triangle"></i>
        Confirme su baja de la Inscripci&oacute;n a la Continuaci&oacute;n Voluntaria al R&eacute;gimen Obligatorio.
</h2>

    <!-- Alerta importante -->
<div class="alert alert-importante">
<h4><i class="fa fa-warning"></i> <strong>&iexcl;Atenci&oacute;n!</strong></h4>

<p class="m-b-none">
            Al confirmar, <strong>se cancelar&aacute; su inscripci&oacute;n vigente</strong> y
            <strong>dejar&aacute; de cotizar voluntariamente</strong> para efectos de pensi&oacute;n
            (Invalidez y Vida; Retiro, Cesant&iacute;a en Edad Avanzada y Vejez).
</p>
</div>

    <!-- Formulario de confirmaci&oacute;n -->
<form id="formConfirmacionBaja" method="POST"
          action="<spring:url value='/baja-expresa/confirmar' htmlEscape='true'/>">

        <!-- Token hidden -->
<input type="hidden" name="token" value="${token}">

        <!-- Checkbox de confirmaci&oacute;n -->
<div class="checkbox checkbox-confirmacion">
<label>
<input type="checkbox" id="checkboxConfirmacion" required>
<strong>He le&iacute;do y comprendo que, al confirmar esta baja, se cancelar&aacute; mi inscripci&oacute;n
                        en la Continuaci&oacute;n Voluntaria al R&eacute;gimen Obligatorio (Modalidad 40) y dejar&eacute; de cotizar voluntariamente para efectos de pensi&oacute;n.</strong>
</label>
</div>

        <!-- Mensaje de validaci&oacute;n -->
<div id="mensajeValidacion" class="alert alert-danger" style="display:none;">
<i class="fa fa-exclamation-circle"></i>
            Debe aceptar la confirmaci&oacute;n para continuar.
</div>

        <!-- Botones -->
<div class="text-center" style="margin-top: 30px;">
<a href="<spring:url value='/' htmlEscape='true'/>" class="btn btn-cancelar">
<i class="fa fa-times"></i> Cancelar
</a>
<button type="submit" id="btnConfirmarBaja" class="btn btn-confirmar">
<i class="fa fa-check"></i> Confirmar Baja Definitivamente
</button>
</div>
</form>

    <!-- Footer -->
<div class="footer-confirmacion">
<hr>
<p>
            Instituto Mexicano del Seguro Social<br>
            IMSS Digital - Servicios en L&iacute;nea
</p>
</div>
</div>

<script type="text/javascript">
$(document).ready(function() {

    // Validar checkbox antes de enviar
    $('#formConfirmacionBaja').on('submit', function(e) {
        var checkboxConfirmacion = $('#checkboxConfirmacion');

        if (!checkboxConfirmacion.is(':checked')) {
            e.preventDefault();
            $('#mensajeValidacion').show();
            return false;
        }

        // Deshabilitar bot&oacute;n para evitar doble submit
        $('#btnConfirmarBaja')
            .prop('disabled', true)
            .html('<i class="fa fa-spinner fa-spin"></i> Procesando...');

        return true;
    });

    // Ocultar mensaje de validaci&oacute;n al marcar checkbox
    $('#checkboxConfirmacion').on('change', function() {
        if ($(this).is(':checked')) {
            $('#mensajeValidacion').hide();
        }
    });
});
</script>
