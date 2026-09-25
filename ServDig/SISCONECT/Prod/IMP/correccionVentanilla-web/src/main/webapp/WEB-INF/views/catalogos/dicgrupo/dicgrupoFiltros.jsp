<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<div id="wrapperFiltros" style="background-color: #f2fff2; ">
<form:form  method="post" id="dicgrupoFiltrosForm" action="/catalogo/dicgrupo/paginar.do">
    <table  style="width: 900px">
        <tr valign="middle">
               <td align="center" width="900px">  
                  <table class="tablaverde2" style="width: 900px" >
                    <thead>
                        <tr><td colspan="1">B&uacute;squeda en Cat&aacute;logo</td></tr>
                    </thead>
                    <tbody>
                        <tr valign="top" class="impar"><td align="left">&nbsp;</td></tr>
                        <tr valign="top" class="par">
                            <td align="right" width="200px">
                                <label for="desGrupo"> Descripci&oacute;n: </label>
                                <input name="desGrupo" id="desGrupo" size="60" maxlength="50" />
                            </td>
                        </tr>
                        <tr valign="top" class="impar"><td align="left">&nbsp;</td></tr>
                        <tr valign="top" class="impar"><td align="left">&nbsp;</td></tr>
                    </tbody>
                </table>
            </td>
        </tr>
    </table>
</form:form>
</div>

<!-- Tabla del menú -->
<table width="810px" border="0px" align="center">
    <tr valign="middle">
         <td align="center" width="100px"><a href="#" onclick="javascript:ayuda();"><span class="boton">Ayuda</span></a></td>
        <td align="center" width="100px"><a href="#" onclick="javascript:paginar();"><span class="boton">Buscar</span></a></td>
    </tr>
</table>
<br>
