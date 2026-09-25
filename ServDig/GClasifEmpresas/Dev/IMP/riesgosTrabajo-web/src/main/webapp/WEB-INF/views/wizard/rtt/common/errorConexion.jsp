<%@ include file="../../../general/taglibs.jsp"%>

<div class="contenedor col-sm-12" style="text-align: center;" >
    <br />
    <br />
</div>

<div class="modal fade" id="cerrarSesion">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="etiqueta.sesion"/></h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <spring:message code="label.mensaje.cerrarSesion"/>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/usuarios/finalizaSesion.js" htmlEscape="true" />">	</script>