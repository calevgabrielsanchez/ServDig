<%@ include file="taglibs.jsp" %>

<!-- Validaciones para poner el titulo -->
<c:if test="${not empty titulo}">
	<div class="titulo_sistema texto-centrado">
		<span><c:out value="${titulo}" /></span>
		
	</div>
</c:if>
<c:if test="${empty titulo}">
	<div class="titulo_sistema texto-centrado">
		<span>M&#x00f3;dulo de Gesti&#x00f3;n de Personas</span>
		
	</div>
</c:if>
<!--  -->

<!-- Validaciones para poner el subtitulo -->
<c:if test="${not empty subtitulo}">
	<div class="menu_holder">
		<ul class="menu">
			<li><a style="cursor: default;"><c:out value="${subtitulo}" /></a></li>
		</ul>
	</div>
</c:if>
<!--  -->