 <%@ include file="../general/taglibs.jsp" %>
<h1>Cerrando Sesión</h1>
<div style="display: none;">
		<c:set var="contextpath" value="<%=request.getContextPath()%>" />
		<form id="formCerrarSesion2"  name="formCerrarSesion2"  action="${staticLogoutPath}" method="get">
		</form>
</div>
<script>
		document.formCerrarSesion2.submit();
</script>




