<%-- 
<%@ include file="../general/taglibs.jsp"%>
<style>
	.iconos-pie span[class^="icon-"] {
		font-size: 48px;
		vertical-align: middle;
	}
	
	.iconos-pie span.icono-desc {
		font-size: 23px;
		margin-left: 5px;
	}
	
	.iconos-pie .glyphicon {
	    font-size: 48px;
	    vertical-align: middle;
	}
</style>

<c:if test="${param.tipoTramite}">
	<div class="iconos-pie m-t-xl">
		<hr class="red" style="margin-bottom: 35px;">
		<div class="row">
			<div class="col-md-4 m-b-md text-center-not-xs text-center-not-sm">
				<span class="icon-tramite" aria-hidden="true" /></span>
				<span class="icono-desc">Gu&iacute;a del usuario</span>
			</div>
			<div class="col-md-4 m-b-md text-center-not-xs text-center-not-sm">
				<span class="icon-phone" aria-hidden="true" /></span>
				<span class="icono-desc">Contacto</span>
			</div>
			<div class="col-md-4 m-b-md text-center-not-xs text-center-not-sm">
				<span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>
				<span class="icono-desc">Preguntas frecuentes</span>
			</div>
		</div>
	</div>
</c:if>
--%>
