<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgDicGrupoNuevo"  style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
    <div id="wrapperDialog" style="background-color: #f2fff2;">
        <form:form modelAttribute="dicGrupo" action="/catalogo/dicgrupo/agregar.do" method="post" id="dicgrupoForm">
            <fieldset>
                <legend>Capture los datos del nuevo registro</legend>
                <p class="impar">
                     <form:label id="desGrupoLabel" for="desGrupo" path="desGrupo" cssErrorClass="error">Descripción</form:label>
                     <br/>
                     <form:input path="desGrupo" size="255" maxlength="255" />
                     <form:errors path="desGrupo" />
                 </p>
                <p class="par">
                     <form:label id="cveIdDivisionLabel" for="dicDivision.cveIdDivision" path="dicDivision.cveIdDivision" cssErrorClass="error">Clave División</form:label>
                     <br/>
				     <form:select style="width:500px;" path="dicDivision.cveIdDivision">
				     	<option value="0">--Por favor seleccione--</option>
				     </form:select>                     
                     <form:errors path="dicDivision.cveIdDivision" />
                 </p>
                <p class="impar">
                     <form:label id="numGrupoLabel" for="numGrupo" path="numGrupo" cssErrorClass="error">Número de Grupo</form:label>
                     <br/>
                     <form:input path="numGrupo" size="50" maxlength="50" />
                     <form:errors path="numGrupo" />
                 </p>
            </fieldset>
        </form:form>
    </div>
</div>