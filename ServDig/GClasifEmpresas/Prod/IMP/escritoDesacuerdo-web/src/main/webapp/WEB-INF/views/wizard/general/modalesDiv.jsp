<%@ include file="../../general/taglibs.jsp" %>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/messages_es.js" htmlEscape="true" />"></script>

<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/comunesEscrito.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/desacuerdo/contenidoDesacuerdo.js" htmlEscape="true" />"></script>
<script type="text/javascript">
    $(document).ready(validarCierre.armarDlgModal2(800, 500));
</script>

<div id="contenido">
    <div id="dialogs">
        <div class="row" id="q1">
            <div class="radio col-md-6">
                <label for="quest1"><spring:message code="wizard.desacuerdo.label.contentPreg1"/></label><br>
            </div>
            <div class="col-md-6">
                <label for="opt1"><input type="radio" class="col-xs-0 radioMateria" name="radioOpt1" id="radioOpt1" value="1"><spring:message code="wizard.desacuerdo.opt.altap"/></label><br>
                <label for="opt1"><input type="radio" class="col-xs-0 radioMateria" name="radioOpt2" id="radioOpt2" value="2"><spring:message code="wizard.desacuerdo.opt.dpsrt"/></label><br>
                <label for="opt1"><input type="radio" class="col-xs-0 radioMateria" name="radioOpt3" id="radioOpt3" value="3"><spring:message code="wizard.desacuerdo.opt.msrt"/></label><br>
            </div>
        </div>

        <div id="q2" class="row">
            <div class="col-md-6">
                <label for="quest2"><spring:message code="wizard.desacuerdo.label.contentPreg2"/></label><br>
            </div>
            <div class="col-md-6">
                <div class="row">
                    <div class="col-md-2"><label>Clase: </label></div>
                    <div class="col-md-4"><input class="form-control" type="text" id="claseAnt" name="claseAnt"><br></div></div>
                <div class="row">
                    <div class="col-md-2"><label>Fracción: </label></div>
                    <div class="col-md-4"><input class="form-control" type="text" id="fracAnt" name="fracAnt"><br></div></div>
                <div class="row">
                    <div class="col-md-2"><label>Prima: </label></div>
                    <div class="col-md-4"><input class="form-control" type="text" id="primaAnt" name="primaAnt"><br></div></div>
            </div>
        </div>

        <div id="q3" class="row">
            <div class="col-md-6">
                <label for="quest3"><spring:message code="wizard.desacuerdo.label.contentPreg3"/></label><br>
            </div>
            <div class="col-md-6">
                <div class="row">
                    <div class="col-md-2"><label>Clase: </label></div>
                    <div class="col-md-4"><input class="form-control" type="text" id="claseDet" name="claseDet"><br></div></div>
                <div class="row">
                    <div class="col-md-2"><label>Fracción: </label></div>
                    <div class="col-md-4"><input class="form-control" type="text" id="fracDet" name="fracDet"><br></div></div>
                <div class="row">
                    <div class="col-md-2"><label>Prima: </label></div>
                    <div class="col-md-4"><input class="form-control" type="text" id="primaDet" name="primaDet"><br></div></div>
            </div>
        </div>
    </div>
</div>
