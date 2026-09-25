<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/layout/taglibs.jsp"%>

<style>
    .container-error {
        max-width: 650px;
        margin: 80px auto;
        background: #fff;
        padding: 50px;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        text-align: center;
    }
    .icono-error {
        font-size: 80px;
        color: #dc3545;
        margin-bottom: 30px;
    }
    .titulo-error {
        color: #dc3545;
        font-size: 28px;
        margin-bottom: 20px;
    }
    .mensaje-error {
        font-size: 16px;
        color: #333;
        line-height: 1.6;
        margin-bottom: 30px;
    }
    .alert-error-detalle {
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
    .btn-reintentar {
        background-color: #28a745;
        color: white;
        font-size: 16px;
        padding: 12px 40px;
        border: none;
        margin-top: 20px;
    }
    .btn-reintentar:hover {
        background-color: #218838;
        color: white;
    }
    .footer-error {
        margin-top: 40px;
        font-size: 12px;
        color: #6c757d;
    }
</style>

<div class="container-error">
    <!-- T&iacute;tulo -->
    <div class="icono-error">
        <i class="fa fa-times-circle"></i>
    </div>

    <h2 class="titulo-error">
        Error al Procesar la Baja
    </h2>

    <!-- Mensaje principal -->
    <div class="mensaje-error">
        <p>
            No se pudo completar la confirmaci&oacute;n de su baja.
        </p>
        <p class="m-b-none">
            No se realizaron cambios en su inscripci&oacute;n. Si desea continuar, genere una nueva solicitud de baja desde IMSS Digital e intente nuevamente.
        </p>
    </div>

    <!-- Alerta con detalle del error -->
    <div class="alert alert-danger alert-error-detalle">
        <h4><i class="fa fa-exclamation-triangle"></i> <strong>Detalle del error:</strong></h4>
        <p>
            <c:choose>
                <c:when test="${not empty error}">
                    ${error}
                </c:when>
                <c:otherwise>
                    El enlace de confirmaci&oacute;n no es v&aacute;lido o ha expirado.
                </c:otherwise>
            </c:choose>
        </p>
    </div>

    <!-- Panel informativo -->
    <div class="panel panel-warning">
        <div class="panel-heading">
            <strong><i class="fa fa-lightbulb-o"></i> &iquest;Qu&eacute; puedo hacer?</strong>
        </div>
        <div class="panel-body" style="text-align: left;">
            <ul>
                <li><strong>Enlace expirado:</strong> Genere una nueva solicitud de baja desde IMSS Digital e intente nuevamente</li>
                <li><strong>Solicitud ya procesada:</strong> La baja ya fue confirmada anteriormente; verifique su estatus en IMSS Digital</li>
                <li><strong>Enlace inv&aacute;lido:</strong> Verifique que copi&oacute; correctamente la URL del correo</li>
                <li><strong>Problemas t&eacute;cnicos:</strong> Intente m&aacute;s tarde o contacte al soporte del IMSS</li>
            </ul>
        </div>
    </div>

    <!-- Bot&oacute;n -->
    <div>
        <a href="${urlPortalImss}" class="btn btn-regresar">                          
			<i class="fa fa-home"></i> Regresar a IMSS Digital                        
		</a>  
    </div>

    <!-- Footer -->
    <div class="footer-error">
        <hr>
        <p>
            Instituto Mexicano del Seguro Social<br>
            IMSS Digital - Servicios en L&iacute;nea<br>
            <small>Para soporte t&eacute;cnico: 800 XXX XXXX</small>
        </p>
    </div>
</div>
