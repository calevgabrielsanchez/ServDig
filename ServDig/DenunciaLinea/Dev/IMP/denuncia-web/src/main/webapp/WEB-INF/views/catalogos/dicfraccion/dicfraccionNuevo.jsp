<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="dgDicFraccionNuevo" title="Agregar Elemento" style="background-color: white !important; opacity: .7 !important; filter: Alpha(Opacity = 70) !important;">
    <div id="wrapperDialog" style="background-color: #f2fff2;">
        <form:form modelAttribute="dicFraccion" action="/catalogo/dicfraccion/agregar.do" method="post" id="dicfraccionForm">
            <fieldset>
                <legend>Capture los datos del nuevo registro</legend>
                <p class="impar">
                     <form:label id="desFraccionLabel" for="desFraccion" path="desFraccion" cssErrorClass="error">Descripci�n</form:label>
                     <br/>
                     <form:input path="desFraccion" size="500" maxlength="500" />
                     <form:errors path="desFraccion" />
                 </p>
                <p class="par">
                     <form:label id="desActividadLabel" for="desActividad" path="desActividad" cssErrorClass="error">Actividad</form:label>
                     <br/>
                     <form:input path="desActividad" size="3000" maxlength="3000" />
                     <form:errors path="desActividad" />
                 </p>
                <p class="impar">
                     <form:label id="dicGrupo.dicDivision.cveIdDivisionLabel" for="dicGrupo.dicDivision.cveIdDivision" path="dicGrupo.dicDivision.cveIdDivision" cssErrorClass="error">Division</form:label>
                     <select style="width:500px;" path="dicGrupo.dicDivision.cveIdDivision">
			     	   <option value="0">--Por favor seleccione--</option>
			         </select>                      
                     <br/>
                     <form:errors path="dicGrupo.dicDivision.cveIdDivision" />
                 </p>                 
                <p class="par">
                     <form:label id="dicGrupo.cveIdGrupoLabel" for="dicGrupo.cveIdGrupo" path="dicGrupo.cveIdGrupo" cssErrorClass="error">Grupo</form:label>
                     <select style="width:500px;" path="dicGrupo.cveIdGrupo">
			     	   <option value="0">--Por favor seleccione--</option>
			         </select>                       
                     <br/>
                     <form:errors path="dicGrupo.cveIdGrupo" />
                 </p>
                <p class="impar">
                     <form:label id="numFraccionLabel" for="numFraccion" path="numFraccion" cssErrorClass="error">N�mero de la Fracci�n</form:label>
                     <br/>
                     <form:input path="numFraccion" size="255" maxlength="255" />
                     <form:errors path="numFraccion" />
                 </p>
                <p class="par">
                     <form:label id="dicClase.cveIdClaseLabel" for="dicClase.cveIdClase" path="dicClase.cveIdClase" cssErrorClass="error">Clase</form:label>
                     <select style="width:500px;" path="dicClase.cveIdClase">
			     	   <option value="0">--Por favor seleccione--</option>
			         </select>                         
                     <br/>
                     <form:errors path="dicClase.cveIdClase" />
                 </p>
            </fieldset>
        </form:form>
    </div>
</div>