<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgDicGrupoModificar" title="Modificar Elemento" style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
    <div id="wrapperDialogModif" style="background-color: #f2fff2;">
        <form:form modelAttribute="dicGrupo" action="/catalogo/dicgrupo/modificar.do" method="post" id="dicgrupoFormModificar">
            <fieldset>
                <legend>Datos del cat&aacute;logo a modificar:</legend>
                <p class="par">
                     <form:label id="cveIdGrupoLabel" for="cveIdGrupo" path="cveIdGrupo" cssErrorClass="error">Clave Grupo</form:label>
                     <br/>
                     <form:input path="cveIdGrupo" readonly="true" size="22" maxlength="22" />
                     <form:errors path="cveIdGrupo" />
                 </p>
                <p class="impar">
                     <form:label id="desGrupoLabel" for="desGrupo" path="desGrupo" cssErrorClass="error">Descripci�n</form:label>
                     <br/>
                     <form:input path="desGrupo" size="255" maxlength="255" />
                     <form:errors path="desGrupo" />
                 </p>
                <p class="par">
                     <form:label id="cveIdDivisionLabel" for="dicDivision.cveIdDivision" path="dicDivision.cveIdDivision" cssErrorClass="error">Clave Divisi�n</form:label>
                     <br/>
				     <form:select style="width:500px;" path="dicDivision.cveIdDivision">
				     	<option value="0">--Por favor seleccione--</option>
				     </form:select>                       
                     <form:errors path="dicDivision.cveIdDivision" />
                 </p>
                <p class="impar">
                     <form:label id="numGrupoLabel" for="numGrupo" path="numGrupo" cssErrorClass="error">N�mero de Grupo</form:label>
                     <br/>
                     <form:input path="numGrupo" size="50" maxlength="50" />
                     <form:errors path="numGrupo" />
                 </p>
            </fieldset>
        </form:form>
    </div>
</div>