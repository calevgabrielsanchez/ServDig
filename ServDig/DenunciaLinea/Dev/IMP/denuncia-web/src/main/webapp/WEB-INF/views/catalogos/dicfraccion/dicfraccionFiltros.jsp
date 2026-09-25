<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="wrapperFiltros" style="background-color: #f2fff2; ">
<form:form  method="post" id="dicfraccionFiltrosForm" action="/catalogo/dicfraccion/paginar.do" modelAttribute="dicFraccion">
    <table  style="width: 900px">
        <tr valign="middle">
               <td align="center" width="900px">  
                  <table class="tablaverde2" style="width: 900px" >
                    <thead>
                        <tr><td colspan="2">B&uacute;squeda en Cat&aacute;logo</td></tr>
                    </thead>
                    <tbody>
                        <tr valign="top" class="impar"><td align="left" colspan="2">&nbsp;</td></tr>
                        <tr valign="top" class="par">
                            <td align="right" width="200px">
                                <label for="desFraccion"> Descripci&oacute;n: </label>
                            </td>
                            <td align="left">
                                <input name="desFraccion" id="desFraccion" size="60" maxlength="50" />
                            </td>
                        </tr>
                        <tr valign="top" class="impar"><td align="left" colspan="2">&nbsp;</td></tr>
                        <tr valign="top" class="par">
                          <td align="right">
                            <label for="dicGrupo.dicDivision.cveIdDivision"> Divisi&oacute;n: </label>
                          </td>
                          <td align="left">
	                        <form:select style="width:500px;" path="dicGrupo.dicDivision.cveIdDivision">
					     	  <option value="0">--Por favor seleccione--</option>
					        </form:select>  
						  </td>
						</tr>
                        <tr valign="top" class="impar"><td align="left" colspan="2">&nbsp;</td></tr>
                        <tr valign="top" class="par">
                          <td align="right">
                            <label for="dicGrupo.cveIdGrupo"> Grupo: </label>
                          </td>
                          <td align="left">
	                        <form:select style="width:500px;" path="dicGrupo.cveIdGrupo">
					     	  <option value="0">--Por favor seleccione--</option>
					        </form:select>  
						  </td>
						</tr>	
                        <tr valign="top" class="impar"><td align="left" colspan="2">&nbsp;</td></tr>
                    </tbody>
                </table>
            </td>
        </tr>
    </table>
</form:form>
</div>

<!-- Tabla del men� -->
<table width="810px" border="0px" align="center">
    <tr valign="middle">
         <td align="center" width="100px"><a href="#" onclick="javascript:ayuda();"><span class="boton">Ayuda</span></a></td>
        <td align="center" width="100px"><a href="#" onclick="javascript:paginar();"><span class="boton">Buscar</span></a></td>
    </tr>
</table>
<br>
