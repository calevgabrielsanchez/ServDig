<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="mx.gob.imss.ctirss.correccion.session.ConstantesSession"%>
<%@page import="mx.gob.imss.ctirss.correccion.session.UserSession"%>	
<%
	 UserSession usrSession = request.getSession().getAttribute(ConstantesSession.USR_SESSION)!=null?
										(UserSession)request.getSession().getAttribute(ConstantesSession.USR_SESSION):null;
	
%>


<input type="hidden" id="regPatronal" value="<%=usrSession.getRegistroPatronal()%>"></input>
<input type="hidden" id="regPresentanteLegal" value="<%=usrSession.getRepresentateLegal()%>"></input>
<input type="hidden" id="numeroFol" value="<%=request.getSession().getAttribute("numeroFolio")%>"></input>
<input type="hidden" id="contextoWeb" value="<%=request.getContextPath()%>"></input>
<input type="hidden" id="sUidUsurio" value="<%=usrSession.getNomUsuarioSistema()%>"></input>

<table width="900" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="540px" align="left"><img
			src="<%=request.getContextPath()%>/resources/images/topLeft.jpg"
			width="540" height="81"></td>
		<td width="200px" bgcolor="#FFFFFF"><img
			src="<%=request.getContextPath()%>/resources/images/dot.gif"
			width="162" height="1" border="0"></td>
		<td width="429px" align="right"><img
			src="<%=request.getContextPath()%>/resources/images/topRight.jpg"
			width="429" height="81"></td>
	</tr>
</table>
<div class="menu_principal">
	<!--inicia menu principal-->
	<div align="center">
		<div>
			<table width="100%" align="right" cellspacing="2" cellpadding="2">
				<tbody>
					<tr align="right" valign="middle">
						<td align="right" valign="middle">
							<font style="color: white">

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
								 
								<a onclick="javascript:salirAplicacion('<%=request.getContextPath()%>');">Salir</a>
							</font>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!--fin centrado-->
	</div>
</div>
