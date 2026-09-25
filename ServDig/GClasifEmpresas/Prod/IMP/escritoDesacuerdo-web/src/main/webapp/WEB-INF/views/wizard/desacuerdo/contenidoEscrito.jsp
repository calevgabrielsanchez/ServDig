<%@ include file="../../general/taglibs.jsp" %>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/messages_es.js" htmlEscape="true" />"></script>
<c:if test="${idOrigenPeticion == 2}">
    <script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/componenteFielExterno.js" htmlEscape="true" />"></script>
</c:if>
<script type="text/javascript" src="/gestionDocumentoProbatorio-web/static/resources/js/boveda/boveda.js"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/common/validator.js" htmlEscape="true" />"></script> <script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/comunesEscrito.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/contenidoDesacuerdo.js" htmlEscape="true" />"></script>


<div class="contenedor col-sm-12 hidden" id="principal">
    <div class="contenido row">
        <div class="col-sm-12">
            <div class="alert alert-info">
                El folio de la solicitud que est&aacute;s
                <c:if test="${retomandoSolicitud}">retomando</c:if>
                <c:if test="${!retomandoSolicitud}">iniciando</c:if>
                es: <strong>${solicitudEscrito.noFolioSolicitud}</strong>
                <input type="hidden" id="noFolioSolicitud" value="${solicitudEscrito.noFolioSolicitud}"/>
            </div>
            <jsp:include page="../../common/datosPatron.jsp"></jsp:include>

            <h4 style="margin-top:20px"><spring:message code="wizard.desacuerdo.titulo"/></h4>
            <hr class="red" style="margin-bottom:10px"/>
            <form class="form form-horizontal" role="form" id="formEscrito">
                <input type="hidden" value="${cadenaOriginal}" id="contenidoFirmar"/>
                <input type="hidden" id="tramiteId" name="tramiteId" value="${idTramite}"/>
                <input type="hidden" id="retomandoSolicitud" value="${retomandoSolicitud?1:0}"/>
                <div class="form-group">
                    <label for="nrp" class="control-label col-sm-6">
                        <spring:message code="wizard.desacuerdo.label.materia"/> *:
                    </label>
                    <div class="col-sm-6">
                        <div class="radio">
                            <label for="radioMateria" id="lblRadioMateria">
                                <input type="radio" class="col-xs-0 radioMateria" name="causaDesacuerdo.materiaDesacuerdo.idMateria" id="radioMateria" value="2" disabled style="display:none">
                                <spring:message code="wizard.desacuerdo.label.materiaDet"/>
                            </label>

                            <label for="radioClasificacion" id="lblRadioClasificacion">
                                <input type="radio" class="col-xs-0 radioMateria" name="causaDesacuerdo.materiaDesacuerdo.idMateria" id="radioClasificacion"  value="1" disabled style="display:none">
                                <spring:message code="wizard.desacuerdo.label.clasificacion"/>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group" id="divMateria">
                    <label for="materiaDeterminacion" class="control-label col-sm-6">
                        <spring:message code="wizard.desacuerdo.label.tipoResolucion"/> *:
                    </label>
                    <div class="col-sm-6">
                        <select class="form-control" id="materiaDeterminacion" name="causaDesacuerdo.idCausaDes" required>
                            <option value="" selected="">-- Selecciona por favor --</option>
                            <c:forEach items="${keyCausasSession}" var="causa">
                                <option value="${causa.idCausaDes}">${causa.descCausaDes}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group" style="display:none" id="divClasificacion">
                    <label for="resolucion" class="control-label col-sm-6">
                        <spring:message code="wizard.desacuerdo.label.clasificacion"/>:
                    </label>
                    <div class="col-sm-6">
                        <div class="radio">
                            <label for="nombreRS">
                                <input type="radio" class="col-xs-0" id="resolucion" value="4" checked="checked">
                                <spring:message code="wizard.desacuerdo.label.resolRect"/>
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="folioImpugnado" class="control-label col-sm-6">
                        <spring:message code="wizard.desacuerdo.label.folio"/> *:
                        <a class="btn btn-xs icono-help" id="toolTipFolioImp" data-toggle="tooltip" data-placement="top" title="El folio se puede encontrar en la parte superior derecha de la resoluci&oacute;n"></a>
                    </label>
                    <div class="col-sm-6">
                        <input class="form-control" type="text" id="folioImpugnado" name="folioImpugnado" maxlength="11"
                               placeholder="NN/NN-NNNNN" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="anVigencia" class="control-label col-sm-6">
                        <spring:message code="wizard.desacuerdo.label.anVig"/> *:
                        <a class="btn btn-xs icono-help" id="toolTipAnVig" data-toggle="tooltip" data-placement="top" title="Se deber&aacute; capturar el a&ntilde;o que corresponde el inicio de la clasificaci&oacute;n determinada por el instituto en la resoluci&oacute;n"></a>
                    </label>
                    <div class="col-sm-2">
                        <input class="form-control" type="number" step="1" id="anVigencia" name="anVigencia" maxlength="10"
                               required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="claseAnterior" class="control-label col-sm-6">
                        <spring:message code="wizard.desacuerdo.txt.clas"/> anterior:
                    </label>
                    <div class="col-sm-2">
                        <input class="form-control" type="text" id="claseAnterior" name="claseAnterior" disabled required min="1" max="5" maxlength="1">
                    </div>
                </div>
                <div class="form-group">
                    <label for="fracAnterior" class="control-label col-sm-6">
                        <spring:message code="wizard.desacuerdo.txt.frac"/> anterior:
                    </label>
                    <div class="col-sm-2">
                        <input class="form-control" type="text" id="fracAnterior" name="fracAnterior" disabled required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="primAnterior" class="control-label col-sm-6">
                        <spring:message code="wizard.desacuerdo.txt.prim"/> anterior:
                    </label>
                    <div class="col-sm-2">
                        <input class="form-control" type="text" id="primAnterior" name="primAnterior" disabled required min="0.5" max="15.0000" maxlength="7">
                    </div>
                </div>
                <div class="form-group" id="trabHide">
                    <label for="trabajadorProm" class="control-label col-sm-6">
                        <spring:message code="wizard.desacuerdo.label.trabaj"/> *:
                    </label>
                    <div class="col-sm-2">
                        <input class="form-control" type="text" id="trabajadorProm" name="trabajadorProm" step="0.1" min="1" max="99999.9" required
                               placeholder="No 000000.0" oninput="validaOnluTrab(this)">
                    </div>
                </div>
                <div class="form-group">
                    <label for="fechNotRes" class="control-label col-sm-6">
                        <spring:message code="wizard.desacuerdo.label.fechNotRes"/> *:
                    </label>
                    <div class="col-sm-2">
                        <input class="form-control" type="date" id="fechNotRes" name="fechNotRes" placeholder="DD/MM/AAAA" required>
                    </div>
                </div>

                <div class="form-group">
                    <label for="mail" class="control-label col-sm-6">
                        <spring:message code="wizard.desacuerdo.label.correo"/>:
                        <a class="btn btn-xs icono-help" id="toolTipMail" data-toggle="tooltip" data-placement="top" title="Verifique que su correo est&eacute; bien escrito, por si se requiere contactarlo"></a>
                    </label>
                    <div class="col-sm-6">
                        <input class="form-control" type="text" id="mail" name="mail" maxlength="50"
                               placeholder="ejemplo@correo.com">
                    </div>
                </div>
                <div class="form-group" id="divMotivoLst">
                    <label for="motivosDesacuerdo.idMotivoDes" class="control-label col-sm-6">
                        <spring:message code="wizard.desacuerdo.label.motivo"/> *:
                        <a class="btn btn-xs icono-help" id="toolTipMotivo" data-toggle="tooltip" data-placement="top" title="Es necesario capturar al menos un motivo para poder registrar el  Escrito de desacuerdo"></a>
                    </label>
                    <div class="col-sm-6">
                        <select class="form-control motivosDesacuerdo" id="motivosDesacuerdo.idMotivoDes" name="motivosDesacuerdo.idMotivoDes" required>
                            <option value="" selected="">-- Selecciona por favor --</option>
                            <c:forEach items="${motivosLst}" var="motivo">
                                <option value="${motivo.idMotivoDes}">${motivo.descMotivoDes}</option>
                            </c:forEach>
                        </select><br>
                    </div>
                </div>
                <div class="form-group hidden" id="divMotivoDesGroup">
                    <div class="col-md-12" id="divHideGroup">
                        <a class="btn btn-xs glyphicon glyphicon-plus" style="color:black" data-toggle="tooltip" data-placement="top" title="Agregar un campo para la captura de un motivo" onclick="registroDesacuerdoCtrl.agregarCampoParaMotivo()"></a>
                    </div><br>
                    <div class="col-md-12">
                        <div class="col-sm-4" style="padding-right:0px;" id="hideMotivoCamp"><div class="col-sm-1"><p>1.-</p></div><div class="col-sm-11"><input class="form-control" id="motivoDesacuerdo" name="motivoDesacuerdo" maxlength="100" required/></div></div>
                        <div class="col-sm-4 hidden" style="padding-right:0px;" id="hideMotivoCamp1"><div class="col-sm-1"><p>2.-</p></div><div class="col-sm-11"><input class="form-control" id="motivoDesacuerdo1" name="motivoDesacuerdo1" maxlength="100"/></div></div>
                        <div class="col-sm-4 hidden" style="padding-right:0px;" id="hideMotivoCamp2"><div class="col-sm-1"><p>3.-</p></div><div class="col-sm-11"><input class="form-control" id="motivoDesacuerdo2" name="motivoDesacuerdo2" maxlength="100"/></div></div>
                    </div><br><br>
                    <div class="col-md-12">
                        <div class="col-sm-4 hidden" style="padding-right:0px;" id="hideMotivoCamp3"><div class="col-sm-1"><p>4.-</p></div><div class="col-sm-11"><input class="form-control" id="motivoDesacuerdo3" name="motivoDesacuerdo3" maxlength="100"/></div></div>
                        <div class="col-sm-4 hidden" style="padding-right:0px;" id="hideMotivoCamp4"><div class="col-sm-1"><p>5.-</p></div><div class="col-sm-11"><input class="form-control" id="motivoDesacuerdo4" name="motivoDesacuerdo4" maxlength="100"/></div></div>
                        <div class="col-sm-4 hidden" style="padding-right:0px;" id="hideMotivoCamp5"><div class="col-sm-1"><p>6.-</p></div><div class="col-sm-11"><input class="form-control" id="motivoDesacuerdo5" name="motivoDesacuerdo5" maxlength="100"/></div></div>
                    </div><br><br>
                    <div class="col-md-12">
                        <div class="col-sm-4 hidden" style="padding-right:0px;" id="hideMotivoCamp6"><div class="col-sm-1"><p>7.-</p></div><div class="col-sm-11"><input class="form-control" id="motivoDesacuerdo6" name="motivoDesacuerdo6" maxlength="100"/></div></div>
                        <div class="col-sm-4 hidden" style="padding-right:0px;" id="hideMotivoCamp7"><div class="col-sm-1"><p>8.-</p></div><div class="col-sm-11"><input class="form-control" id="motivoDesacuerdo7" name="motivoDesacuerdo7" maxlength="100"/></div></div>
                        <div class="col-sm-4 hidden" style="padding-right:0px;" id="hideMotivoCamp8"><div class="col-sm-1"><p>9.-</p></div><div class="col-sm-11"><input class="form-control" id="motivoDesacuerdo8" name="motivoDesacuerdo8" maxlength="100"/></div></div>
                    </div><br><br>
                    <div class="col-md-12">
                        <div class="col-sm-4 hidden" style="padding-right:0px;" id="hideMotivoCamp9"><div class="col-sm-1"><p>10.-</p></div><div class="col-sm-11"><input class="form-control" id="motivoDesacuerdo9" name="motivoDesacuerdo9" maxlength="100"/></div></div>
                    </div><br><br><br>
                </div>
            </form>
            <form class="form form-horizontal" role="form" id="formDomEscrito">
                <!--<input type="hidden" id="idEscrito" name="idEscrito" value="${idTramite}"/>-->
                <div class="form-group">
                    <div class="form-group" id="btnAdrees">
                        <label class="control-label col-sm-6">
                            <spring:message code="wizard.desacuerdo.label.domicilio"/> :
                        </label>
                        <div class="col-sm-6">
                            <button type="button" class="btn btn-primary" id="mosBtnAdr"><spring:message code="wizard.button.addAdrres"/></button>
                            <button type="button" class="btn btn-default hidden" id="hidBtnAdr"><spring:message code="wizard.button.hidAdrres"/></button>
                        </div>
                    </div>
                    <div class="form-group hidden col-md-12" id="addAdrees">
                        <div class="form-group">
                            <label class="control-label col-sm-1">
                                Calle:
                            </label>
                            <div class="col-sm-7">
                                <input class="form-control" type="text" id="calleInp" name="calleInp">
                            </div>
                            <label class="control-label col-sm-1">
                                Num Exterior:
                            </label>
                            <div class="col-sm-1">
                                <input class="form-control" type="number" id="numExt" name="numExt" min="1" max="9999" maxlength="4">
                            </div>
                            <label class="control-label col-sm-1">
                                Num Interior:
                            </label>
                            <div class="col-sm-1">
                                <input class="form-control" type="number" id="numInt" name="numInt" min="1" max="9999" maxlength="4">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-1">
                                CP:
                            </label>
                            <div class="col-sm-1">
                                <input class="form-control" type="text" id="cpInp" name="cpInp" min="1" max="99999" maxlength="5"
                                       step="1" placeholder="00000" oninput="validOnlyNum(this)">
                            </div>
                            <label class="control-label col-sm-1">
                                Ciudad:
                            </label>
                            <div class="col-sm-2">
                                <input class="form-control" type="text" id="ciudadInp" name="ciudadInp" >
                            </div>
                            <label class="control-label col-sm-1">
                                Estado:
                            </label>
                            <div class="col-sm-2">
                                <input class="form-control" type="text" id="edoInp" name="edoInp" >
                            </div>
                        </div>
                    </div>
                </div>
            </form>
            <div id="componenteBoveda"></div>
        </div>
    </div>

    <div class="pie row">
        <div class="col-sm-4">
            <div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposRequeridos"/></div>
        </div>
        <div class="col-sm-8">
            <div class="pull-right">
                <button class="btn btn-default" id="salirTramite">
                    <c:if test="${idOrigenPeticion==2}">
                        <spring:message code="wizard.button.cerrar"/>
                    </c:if>
                    <c:if test="${idOrigenPeticion!=2}">
                        <spring:message code="wizard.button.salir"></spring:message>
                    </c:if>
                </button>
                <c:if test="${empty error}">
                    <c:if test="${idOrigenPeticion==2}">
                        <div id="grupoBtn" class="btn-group dropup">
                            <a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones"/></a>
                            <a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span class="caret"></span></a>
                            <ul class="dropdown-menu pull-right">
                                <li><a id="finalizarTramite"><i class="glyphicon glyphicon-ok"></i><spring:message code="wizard.button.finalizarTramite"/></a></li>
                                <li><a id="guardarTramite"><i class="glyphicon glyphicon-download-alt"></i><spring:message code="wizard.button.guardarTramite"/></a></li>
                                <li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i><spring:message code="wizard.button.cancelarTramite"/></a></li>
                            </ul>
                        </div>
                    </c:if>
                    <c:if test="${idOrigenPeticion!=2}">
                        <button type="button" class="btn btn-danger" id="cancelarTramite"><spring:message code="wizard.button.cancelarTramite"/></button>
                        <button type="button" class="btn btn-primary" id="finalizarTramite"><spring:message code="wizard.button.finalizarTramite"/></button>
                    </c:if>
                </c:if>
            </div>
        </div>
    </div>
</div>

<div id="modalDlg" class="row col-sm-12">
<div id="dialogs">
    <h4 style="margin-top:20px"><spring:message code="wizard.desacuerdo.titulo.mat"/></h4>
    <hr class="red" style="margin-bottom:10px"/>
    <form id="forModalDlg">
        <div class="row" id="q1">
            <div class="col-md-6">
                <label for="quest1"><spring:message code="wizard.desacuerdo.label.contentPreg1"/></label><br>
            </div>
            <div class="radio col-md-6">
                <label for="opt1"><input type="radio" class="col-xs-0 materiaDesacuerdo.idMateria" name="radioOptMateria" id="radioOpt1" value="1"><spring:message code="wizard.desacuerdo.opt.altap"/></label><br>
                <label for="opt1"><input type="radio" class="col-xs-0 materiaDesacuerdo.idMateria" name="radioOptMateria" id="radioOpt2" value="2"><spring:message code="wizard.desacuerdo.opt.dpsrt"/></label><br>
                <label for="opt1"><input type="radio" class="col-xs-0 materiaDesacuerdo.idMateria" name="radioOptMateria" id="radioOpt3" value="3"><spring:message code="wizard.desacuerdo.opt.msrt"/></label><br>
            </div>
        </div>
        <div class="row">
            <div class="col-md-6"></div>
            <div class="col-md-2 hidden" id="campoObligatorioRadio">
                <label style="font-size: 12px; color: red;"><spring:message code="etiqueta.campoObligatorio"/></label>
            </div>
        </div>
    </form>
</div>
<div class="col-md-12"><br></div>

<form id="fomExtrs">
<div id="q2" class="row">
    <div class="col-md-6">
        <label for="quest2"><spring:message code="wizard.desacuerdo.label.contentPreg2"/></label><br>
    </div>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-2"><label><spring:message code="wizard.desacuerdo.txt.clas"/>*:</label></div>
            <div class="col-md-4">
                <select class="form-control" id="claseAnt" name=claseAnt" required onchange="cargarFraccion(1);">
                    <option value="" selected="">Selecciona</option>
                    <c:forEach var="mtivo1" begin="1" end="5">
                        <option value="${mtivo1}">${mtivo1}</option>
                    </c:forEach>
                </select><br><br> </div>
            <div class="col-md-4" hidden="true" id="campoObligatorioClaseAnt"><label style="font-size: 12px; color: red;"><spring:message code="etiqueta.campoObligatorio"/></label></div>
        </div>
        <div class="row">
            <div class="col-md-2"><label><spring:message code="wizard.desacuerdo.txt.frac"/>*:</label></div>
            <div class="col-md-4"><select class="form-control" id="fracAnt" name="fracAnt" required>
                <option value="" selected="">Selecciona</option>
            </select><br><br></div>
            <div class="col-md-4" hidden="true" id="campoObligatorioFracAnt"><label style="font-size: 12px; color: red;"><spring:message code="etiqueta.campoObligatorio"/></label></div>
        </div>
        <div class="row">
            <div class="col-md-2"><label><spring:message code="wizard.desacuerdo.txt.prim"/>*:</label></div>
            <div class="col-md-4"><input class="form-control" type="text" id="primaAnt" name="primaAnt" min="0.50000" max="15.0000"
                                         step="0.00001" placeholder="De 0.50000 a 15" required oninput="validaNumOnly(this)"><br><br> </div>
            <div class="col-md-4" hidden="true" id="campoObligatorioPrimaAnt"><label style="font-size: 12px; color: red;"><spring:message code="etiqueta.campoObligatorio"/></label></div>
        </div>
    </div>
</div>
<div class="col-md-12"><br></div>

<div id="q3" class="row">
    <div class="col-md-6">
        <label for="quest3"><spring:message code="wizard.desacuerdo.label.contentPreg3"/></label><br>
    </div>
    <div class="col-md-6">
        <div class="row">
            <div class="col-md-2"><label><spring:message code="wizard.desacuerdo.txt.clas"/>*:</label></div>
            <div class="col-md-4">
                <select class="form-control" id="claseDet" name=claseDet" required onchange="cargarFraccion(2);">
                    <option value="" selected="">Selecciona</option>
                    <c:forEach var="mtivo" begin="1" end="5">
                        <option value="${mtivo}">${mtivo}</option>
                    </c:forEach>
                </select><br><br> </div>
            <div class="col-md-4" hidden="true" id="campoObligatorioClaseDet"><label style="font-size: 12px; color: red;"><spring:message code="etiqueta.campoObligatorio"/></label></div>
        </div>
        <div class="row">
            <div class="col-md-2"><label><spring:message code="wizard.desacuerdo.txt.frac"/>*:</label></div>
            <div class="col-md-4"><select class="form-control" id="fracDet" name=fracDet" required>
                <option value="" selected="">Selecciona</option>
            </select><br><br></div>
            <div class="col-md-4" hidden="true" id="campoObligatorioFracDet"><label style="font-size: 12px; color: red;"><spring:message code="etiqueta.campoObligatorio"/></label></div>
        </div>
        <div class="row">
            <div class="col-md-2"><label><spring:message code="wizard.desacuerdo.txt.prim"/>*:</label></div>
            <div class="col-md-4"><input class="form-control" type="text" id="primaDet" name="primaDet" min="0.50000" max="15"
                                         step="0.00001" placeholder="De 0.50000 a 15" required oninput="validaNumOnly(this)"><br><br> </div>
            <div class="col-md-4" hidden="true" id="campoObligatorioPrimaDet"><label style="font-size: 12px; color: red;"><spring:message code="etiqueta.campoObligatorio"/></label></div>
        </div>
    </div>
</div>
</form>

<div class="row col-sm-4">
    <div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span><spring:message code="label.camposRequeridos"/></div>
</div>
<div class="col-sm-8">
    <div class="pull-right">
        <button class="btn btn-default" id="salirModal"><spring:message code="wizard.button.anterior"/> </button>
        <button class="btn btn-primary" id="avanzarModal"><spring:message code="wizard.button.siguiente"/> </button>
    </div>
</div>
</div>

<script type="text/javascript">
    document.getElementById('motivoDesacuerdo').addEventListener('keydown', inputChar);
    document.getElementById('motivoDesacuerdo1').addEventListener('keydown', inputChar1);
    document.getElementById('motivoDesacuerdo2').addEventListener('keydown', inputChar2);
    document.getElementById('motivoDesacuerdo3').addEventListener('keydown', inputChar3);
    document.getElementById('motivoDesacuerdo4').addEventListener('keydown', inputChar4);
    document.getElementById('motivoDesacuerdo5').addEventListener('keydown', inputChar5);
    document.getElementById('motivoDesacuerdo6').addEventListener('keydown', inputChar6);
    document.getElementById('motivoDesacuerdo7').addEventListener('keydown', inputChar7);
    document.getElementById('motivoDesacuerdo8').addEventListener('keydown', inputChar8);
</script>
