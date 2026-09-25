<%@ include file="../../general/taglibs.jsp"%>
			<tr id="muestraDigitalizacionRow">
				<c:if test="${fileReadVB.documentoProbatorio.cifrado!=null && fileReadVB.documentoProbatorio.cifrado!=''}">
					<td align="left">Documento digitalizado:</td>
					<td align="left">	
						<input type="button" onclick="showDoc(${fileReadVB.documentoProbatorio.idDocumentoProbatorio});" class="mboton" value="Ver Documento"></input>
					</td>
				</c:if>
			</tr>