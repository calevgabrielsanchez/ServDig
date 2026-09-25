<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="mx.gob.imss.ctirss.correccion.session.ConstantesSession"%>
<%@page import="mx.gob.imss.ctirss.correccion.session.UserSession"%>	
<script type="text/javascript">

function backMenuPrincipal(contextPath){
	
	
	var formulario =null;
	formulario = document.createElement("form");
	formulario.action = contextPath+"/login/redireccionaInicio.do";
	formulario.method = "post";
	this.document.body.appendChild(formulario);
	formulario.submit();
}

</script>

<%
	 UserSession usrSession = request.getSession().getAttribute(ConstantesSession.USR_SESSION)!=null?
										(UserSession)request.getSession().getAttribute(ConstantesSession.USR_SESSION):null;
	
%>


<input type="hidden" id="regPatronal" value="<%=usrSession.getRegistroPatronal()%>"></input>
<input type="hidden" id="regPresentanteLegal" value="<%=usrSession.getRepresentateLegal()%>"></input>
<input type="hidden" id="numeroFol" value="<%=request.getSession().getAttribute("numeroFolio")%>"></input>
<input type="hidden" id="contextoWeb" value="<%=request.getContextPath()%>"></input>
<input type="hidden" id="sUidUsurio" value="<%=usrSession.getNomUsuarioSistema()%>"></input>

<div class="header_top">
	<!--inicia menu principal-->
	<div align="center">
		<div>
			<table width="100%" align="right" cellspacing="2" cellpadding="2">
				<tbody>
					<tr align="right" valign="middle">
						<td align="right" valign="middle">
							<font style="color: gray">

								<c:out value="${sessionScope.USR_SESSION.nombreCompleto}" /> 
								<c:if test="${sessionScope.USR_SESSION.nombreDelegacion != null}">
								   | <c:out
										value="${sessionScope.USR_SESSION.nombreDelegacion}" />
								</c:if> 
								<c:if test="${sessionScope.USR_SESSION.nombreSubDelegacion != null}">
								   | <c:out
										value="${sessionScope.USR_SESSION.nombreSubDelegacion}" />
								</c:if> 
								| 
								<a href="javascript:backMenuPrincipal('<%=request.getContextPath()%>');"> <font color="#3B6858">Menu Principal</font></a>
								|
								<%-- <a href="javascript:salirAplicacion('<%=request.getContextPath()%>');"> <font color="#3B6858">Salir</font></a> --%>
							</font>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<!--fin centrado-->
	</div>
</div>
