<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgDicDivisionModificar"  style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
    <div id="wrapperDialogModif" style="background-color: #f2fff2;">
        <form:form modelAttribute="dicDivision" action="/catalogo/dicdivision/modificar.do" method="post" id="dicdivisionFormModificar">
            <fieldset>
                <legend>Datos del cat&aacute;logo a modificar:</legend>
                <p class="par">
                     <form:label id="cveIdDivisionLabel" for="cveIdDivision" path="cveIdDivision" cssErrorClass="error">clave división</form:label>
                     <br/>
                     <form:input path="cveIdDivision" readonly="true" size="38" maxlength="38" />
                     <form:errors path="cveIdDivision" />
                 </p>
                <p class="impar">
                     <form:label id="desDivisionLabel" for="desDivision" path="desDivision" cssErrorClass="error">Descripción División</form:label>
                     <br/>
                     <form:input path="desDivision" size="255" maxlength="255" />
                     <form:errors path="desDivision" />
                 </p>
                <p class="par">
                     <form:label id="numDivisionLabel" for="numDivision" path="numDivision" cssErrorClass="error">Numero de división</form:label>
                     <br/>
                     <form:input path="numDivision" size="50" maxlength="50" />
                     <form:errors path="numDivision" />
                 </p>
                <p class="impar">
                     <form:label id="fecRegistroAltaLabel" for="fecRegistroAlta" path="fecRegistroAlta" cssErrorClass="error">Fecha de alta</form:label>
                     <br/>
                     <form:input path="fecRegistroAlta"  />
                     <form:errors path="fecRegistroAlta" />
                 </p>
                <p class="par">
                     <form:label id="fecRegistroBajaLabel" for="fecRegistroBaja" path="fecRegistroBaja" cssErrorClass="error">Fecha de baja</form:label>
                     <br/>
                     <form:input path="fecRegistroBaja"  />
                     <form:errors path="fecRegistroBaja" />
                 </p>
                <p class="impar">
                     <form:label id="fecRegistroActualizadoLabel" for="fecRegistroActualizado" path="fecRegistroActualizado" cssErrorClass="error">Fecha de actualización</form:label>
                     <br/>
                     <form:input path="fecRegistroActualizado"  />
                     <form:errors path="fecRegistroActualizado" />
                 </p>
            </fieldset>
        </form:form>
    </div>
</div>