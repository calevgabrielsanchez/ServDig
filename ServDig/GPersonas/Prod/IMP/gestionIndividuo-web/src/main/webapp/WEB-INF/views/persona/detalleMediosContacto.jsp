<%@ include file="taglibs.jsp" %>
<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>

<form:label path="telefonoFijo.numero" cssClass="wide">N&uacute;mero tel&eacute;fono particular</form:label>
<form:input path="telefonoFijo.numero" id="numeroTelefonicoParticular" style="width: 200px;" maxlength="8" />
<br /><br /><br />

<form:label path="telefonoFijo.claveLada" cssClass="wide">Clave lada</form:label>
<form:input path="telefonoFijo.claveLada" id="claveLada" style="width: 100px;" maxlength="3" />
<br /><br /><br />

<form:label path="telefonoFijo.extension" cssClass="wide">Extensi&oacute;n</form:label>
<form:input path="telefonoFijo.extension" id="extension" style="width: 100px;" maxlength="5" />
<br /><br /><br />

<form:label path="telefonoMovil.numero" cssClass="wide">N&uacute;mero telef&oacute;nico movil</form:label>
<form:input path="telefonoMovil.numero" id="numeroTelefonicoMovil" style="width: 200px;" maxlength="10" />
<br /><br /><br />

<form:label path="correoElectronico.correo" cssClass="wide">Correo electr&oacute;nico</form:label>
<form:input path="correoElectronico.correo" id="correoElectronico" style="width: 200px;" maxlength="50" />
<br /><br /><br />