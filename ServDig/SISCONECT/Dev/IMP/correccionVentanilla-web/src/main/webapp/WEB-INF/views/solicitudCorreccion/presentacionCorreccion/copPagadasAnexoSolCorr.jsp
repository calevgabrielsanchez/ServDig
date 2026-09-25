<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<html>
	<c:forEach var="anexo" items="anexos">
		<c:out value="${anexo.cveAnexoSolicitudCorrPat}"></c:out>
	</c:forEach>
</html>