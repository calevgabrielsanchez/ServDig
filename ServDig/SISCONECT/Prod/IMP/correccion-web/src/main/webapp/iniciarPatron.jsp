<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<title>Iniciar con un patron</title>
<body>
	<c:set var="contextpath" value="<%=request.getContextPath()    %>" />
	<form action="${contextpath}/iniciarPatron.do" method="post">
		<table cellpadding="2" cellspacing="2">
			<tr>
				<td><table cellpadding="2" cellspacing="2">
						<tr>
							<td>Registro Patronal:</td>
							<td><input type="text" name="patIDSEnpie" value="A321918310" /></td>
						</tr>
						<tr>
							<td>Serie:</td>
							<td><input type="text" name="senucePAT"
								value="00000200000200000605" /></td>
						</tr>
						<tr>
							<td>Tipo Certificado:</td>
							<td><input type="text" name="itipoCE" value="B" /><font
								color="gray">(A=SAT B=IMSS)</font></td>
						</tr>
					</table></td>
				<td>
					<button type="submit">Entrar</button>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>