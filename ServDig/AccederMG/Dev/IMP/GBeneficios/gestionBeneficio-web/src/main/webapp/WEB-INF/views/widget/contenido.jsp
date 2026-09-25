<!-- JSP Contenido del Widget de Beneficios. -->
<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.enums.EstadoBeneficioEnum"%>

<c:set var="beneficioActivo"
	value="<%=EstadoBeneficioEnum.ACTIVO.getClave()%>" scope="page"></c:set>
<c:set var="beneficioCancelado"
	value="<%=EstadoBeneficioEnum.CANCELADO.getClave()%>" scope="page"></c:set>
<c:set var="beneficioSuspendido"
	value="<%=EstadoBeneficioEnum.SUSPENDIDO.getClave()%>" scope="page"></c:set>


<style>
	address.beneficio {
		border-bottom: 1px solid #C2C3C9;
    	margin-bottom: 10px;
	}
	
	address.beneficio:last-child {
	    border-bottom: medium none;
	    margin-bottom: 0;
	}
</style>

<script type="text/javascript">
	var infoDatosGeneralesFisicaFiscal = '<p>En esta secci&oacute;n se muestra el detalle de los beneficios con los que se cuenta:</p>'
			+ '<address>'
			+ '<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span><strong>Nombre del beneficio </strong></span><br>'
			+ '<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Estado del beneficio</span><br>'
			+ '<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Porcentaje de descuento</span><br>'
			+ '<i class="icon-minus" style="margin-right: 3px; vertical-align: middle; line-height: 11px ! important; font-weight: lighter; font-size: 9px ! important;"></i><span>Periodo de vigencia del descuento</span><br>'
			+ '</address>';

	$('#idPopoverBeneficios').popover({
		animation : true,
		html : true,
		content : infoDatosGeneralesFisicaFiscal,
		trigger : 'hover',
		container : 'body'
	});
</script>

<div class="widget-section">
	<c:choose>
		<c:when test="${not empty LISTA_BENEFICIOS }">
			<div style="float: right;">
				<a class="btn btn-default btn-xs icono-help"
					id="idPopoverBeneficios" data-toggle="popover"
					title="Beneficios"> </a>
			</div>

			<c:forEach items="${LISTA_BENEFICIOS}" var="beneficio">
				<address class="beneficio">
					<strong>${beneficio.tipoBeneficio.descripcion}</strong>
					
					<c:choose>
						<c:when test="${beneficio.estadoBeneficio.idEstadoBeneficio eq beneficioActivo}">
							<c:set var="cssClassEdoBeneficio" value="label label-success" />
						</c:when>
						<c:when test="${beneficio.estadoBeneficio.idEstadoBeneficio eq beneficioCancelado}">
							<c:set var="cssClassEdoBeneficio" value="label label-danger" />
						</c:when>
						<c:when test="${beneficio.estadoBeneficio.idEstadoBeneficio eq beneficioSuspendido}">
							<c:set var="cssClassEdoBeneficio" value="label label-default" />
						</c:when>
						<c:otherwise>
							<c:set var="cssClassEdoBeneficio" value="label label-default" />
						</c:otherwise>
					</c:choose>
					
					<span class="${cssClassEdoBeneficio }" style="margin-left: 10px;">${beneficio.estadoBeneficio.descripcion}</span><br> 
					Descuento ${beneficio.descuentoActual.porcentajeDescuento} %<br>
					<fmt:formatDate value="${beneficio.descuentoActual.fecInicio}" pattern="dd/MM/yyyy" />	- 
					<fmt:formatDate value="${beneficio.descuentoActual.fechaFin}" pattern="dd/MM/yyyy" />
				</address>
			</c:forEach>
		</c:when>
		<c:otherwise>
			No cuentas con los beneficios del R&eacute;gimen de Incorporaci&oacute;n Fiscal dentro del Instituto Mexicano del Seguro Social.
		</c:otherwise>
	</c:choose>
</div>
