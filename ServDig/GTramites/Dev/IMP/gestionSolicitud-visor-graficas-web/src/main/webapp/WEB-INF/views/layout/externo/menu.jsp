<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/menu.js" htmlEscape="true" />"></script>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<div id="menu_principal" class="clear">
	<div class="menuboton">
		<a href="http://www.imss.gob.mx/">Inicio</a>
	</div>
	
	<div class="menuboton">
		<a href="http://www.imss.gob.mx/conoce-al-imss">Conoce
			al IMSS</a>
	</div>
	
	<div class="menuboton">
		<a
			href="http://www.imss.gob.mx/transparencia">Transparencia</a>
	</div>
	
	<div class="menuboton">
		<a href="http://201.144.108.20/imssdigital/directorio/Pages/home.aspx">Directorio</a>
	</div>
	
	<div class="menuboton">
		<a href="http://www.imss.gob.mx/contacto">Contacto</a>
	</div>
</div>