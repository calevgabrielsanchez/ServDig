<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<style>
    .container-exitosa {
        max-width: 650px;
        margin: 80px auto;
        background: #fff;
        padding: 50px;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        text-align: center;
    }
    .icono-exito {
        font-size: 80px;
        color: #28a745;
        margin-bottom: 30px;
    }
    .titulo-exitosa {
        color: #28a745;
        font-size: 28px;
        margin-bottom: 20px;
    }
    .mensaje-exitosa {
        font-size: 16px;
        color: #333;
        line-height: 1.6;
        margin-bottom: 30px;
    }
    .panel-info-baja {
        text-align: left;
        margin: 30px 0;
    }
    .btn-regresar {
        background-color: #007bff;
        color: white;
        font-size: 16px;
        padding: 12px 40px;
        border: none;
        margin-top: 20px;
    }
    .btn-regresar:hover {
        background-color: #0056b3;
        color: white;
    }
    .footer-exitosa {
        margin-top: 40px;
        font-size: 12px;
        color: #6c757d;
    }
</style>

<div class="container-exitosa">
    <!-- Icono de &eacute;xito -->
    <div class="icono-exito">
        <i class="fa fa-check-circle"></i>
    </div>

    <!-- T&iacute;tulo -->
    <h2 class="titulo-exitosa">
        Baja Confirmada Exitosamente
    </h2>

    <!-- Mensaje principal -->
    <div class="mensaje-exitosa">
        <p>
            Su solicitud de baja de <strong>Continuaci&oacute;n Voluntaria al R&eacute;gimen
            Obligatorio (Modalidad 40)</strong> ha sido procesada correctamente.
        </p>
    </div>

    <!-- Panel informativo -->
    <div class="panel panel-info panel-info-baja">
        <div class="panel-heading">
            <strong><i class="fa fa-info-circle"></i> Informaci&oacute;n importante</strong>
        </div>
        <div class="panel-body">
            <ul>
                <li>Recibir&aacute; un correo de confirmaci&oacute;n con los detalles de la baja.</li>
            </ul>
        </div>
    </div>

	<a href="${urlPortalImss}" class="btn btn-regresar"> <i
		class="fa fa-home"></i> Regresar a IMSS Digital
	</a>

	<!-- Footer -->
    <div class="footer-exitosa">
        <hr>
        <p>
            Instituto Mexicano del Seguro Social<br>
            IMSS Digital - Servicios en L&iacute;nea<br>
            <small>Fecha de procesamiento:
                <fmt:formatDate value="<%= new java.util.Date() %>" pattern="dd/MM/yyyy HH:mm"/>
            </small>
        </p>
    </div>
</div>
