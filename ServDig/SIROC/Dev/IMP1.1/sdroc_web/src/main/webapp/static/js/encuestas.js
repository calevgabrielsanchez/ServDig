var root ='';
var questions;
var questionsList=[];
var localStg = "LOC_STG_ENC";
var homoclave = "";
var sending = false;

function loadModal() {
  if(document.getElementsByName("homoclave") !== null && document.getElementsByName("homoclave").length >0){
    homoclave = document.getElementsByName("homoclave")[0].value;
  }

  //window.document.body.innerHTML += '<div class="modal fade" id="modEncuestaSatisfaccion" name="modEncuestaSatisfaccion"></div>';
  var elemDiv = document.createElement('div');
  elemDiv.setAttribute("id","modEncuestaSatisfaccion");
  elemDiv.setAttribute("class","modal fade");
  elemDiv.setAttribute("name","modEncuestaSatisfaccion");

  document.body.appendChild(elemDiv);
  
  $('#modEncuestaSatisfaccion').on('hidden.bs.modal', function() {
    saveInformation();
  });
}

function getAnswer(answerView) {
  var selectedList = [];
  var nodeList = document.getElementsByName(answerView);
  for (var i = 0; i < nodeList.length; i++) {
    if(nodeList[i].type === 'radio' && nodeList[i].checked) {
      selectedList.push(nodeList[i].value);
    } else if(nodeList[i].type === 'checkbox' && nodeList[i].checked) {
      selectedList.push(nodeList[i].value);
    } else if(nodeList[i].type === 'input') {
      selectedList.push(nodeList[i].value);
    } else if(nodeList[i].type === 'submit') {
      for(var index = 0 ; index < nodeList[i].classList.length; index++) {
        if(nodeList[i].classList[index] === 'active') {
          selectedList.push(nodeList[i].value);
        }
      }
    }
  }
  return selectedList.length > 0 ? selectedList : null;
}

function goToView(questionView,answerView) {
  var answer;
  answer = getAnswer(answerView);

  if(questionView === "Q_A") {
    if(answer.length >0 && answer[0] === '1') {
      showQuestionView("Q_R");

      hideQuestionView("Q_B");
      removeListElement("Q_B");
      clearSelection("A_B");

      hideQuestionView("Q_C");
      removeListElement("Q_C");
      clearSelection("A_C");

      hideQuestionView("Q_D");
      removeListElement("Q_D");
      clearSelection("A_D");

      hideQuestionView("S_A");
    } else {
      hideQuestionView("Q_R");
      removeListElement("Q_R");
      clearSelection("A_R");

      if(answer.length > 0 && answer[0] === '2') {
        hideQuestionView("Q_B");
        removeListElement("Q_B");
        clearSelection("A_B");

        hideQuestionView("Q_C");
        removeListElement("Q_C");
        clearSelection("A_C");

        showQuestionView("Q_D");
        showQuestionView("S_A");
      } else {
        hideQuestionView("Q_B");
        removeListElement("Q_B");
        clearSelection("A_B");

        hideQuestionView("Q_C");
        removeListElement("Q_C");
        clearSelection("A_C");
        
        showQuestionView("Q_D");
        showQuestionView("S_A");
      }
    }
  } else {
    if(questionView === "Q_R") {
      if(answer.length >0 && answer[0] === '1') {
        showQuestionView("Q_B");
        showQuestionView("Q_C");
      } else{
        hideQuestionView("Q_B");
        removeListElement("Q_B");
        clearSelection("A_B");

        hideQuestionView("Q_C");
        removeListElement("Q_C");
        clearSelection("A_C");
      }
    }
  }
  addListElement(questionView,answer);
  setLocalStorage(localStg, parseToJSON(questionsList) );
}

function removeListElement(questionView) {
  for (var i = 0; i < questionsList.length; i++) {
    if(questionsList[i].question === questionView) {
      questionsList.splice(i,1);
    }
  }
}

function addListElement(questionView, answer) {
  var question = {};
  question.question = questionView;
  question.answer = answer;

  var exist = false;
  for (var i = 0; i < questionsList.length; i++) {
    if(questionsList[i].question === questionView) {
      questionsList[i] = question;
      exist = true;
    }
  }
  if(exist === false) {
    questionsList.push(question);
  }
}

function clearSelection(answerView){
  var elements = document.getElementsByName(answerView);
  for (var i = 0; i < elements.length; i++) {
    if(elements[i].type === 'radio' || elements[i].type === 'checkbox') {
      elements[i].checked = false;
    }
    if(elements[i].type === 'input') {
      elements[i].value = "";
    }
  }
}

function hideQuestionView(questionView) {
  document.getElementById(questionView).style.display = 'none';
}

function showQuestionView(questionView) {
  document.getElementById(questionView).style.display = 'block';
}

function selectRadio(radio) {
  document.getElementById(radio).checked = true;
}

function selectButton(button,group) {
  var buttons_group = document.getElementsByName(group);
  for(index = 0; index < buttons_group.length; index++) {
    if(document.getElementsByName(group)[index].id===button) {
      document.getElementsByName(group)[index].classList.add("active");
      var test = document.getElementsByName(group)[index];
      var replace = $(test).find('img').attr('src');
      reply = replace.split('.')[3].split('/')[3].split('-')[2];
      if(reply.toString() === 'good') {
        $(test).find('img').attr('src', 'https://framework-gb.cdn.gob.mx/assets/images/ico-satifation-good-2.svg');
      }else if(reply.toString() === 'regular') {
        $(test).find('img').attr('src', 'https://framework-gb.cdn.gob.mx/assets/images/ico-satifation-regular-2.svg');
      }else if(reply.toString() === 'bad') {
        $(test).find('img').attr('src', 'https://framework-gb.cdn.gob.mx/assets/images/ico-satifation-bad-2.svg');
      }
      //console.log('replace -> ' + replace.split('.')[3].split('/')[3].split('-')[2] )
    } else {
      for(var indexActive= 0 ; indexActive < document.getElementsByName(group)[index].classList.length;indexActive++){
        if(document.getElementsByName(group)[index].classList[indexActive]==='active') {
          document.getElementsByName(group)[index].classList.remove("active");
          var test = document.getElementsByName(group)[index];
          var replace = $(test).find('img').attr('src');
          reply = replace.split('.')[3].split('/')[3].split('-')[2];
          if(reply.toString() === 'good') {
            $(test).find('img').attr('src', 'https://framework-gb.cdn.gob.mx/assets/images/ico-satisfation-good.svg');
          } else if ( reply.toString() === 'regular' ) {
            $(test).find('img').attr('src', 'https://framework-gb.cdn.gob.mx/assets/images/ico-satisfation-regular.svg');
          } else if ( reply.toString() === 'bad' ) {
            $(test).find('img').attr('src', 'https://framework-gb.cdn.gob.mx/assets/images/ico-satisfation-bad.svg');
          }
        }
      }
    }
  }
}

function selectCheckbox(chk){
  if(document.getElementById(chk).checked === true) {
    document.getElementById(chk).checked = false;
  } else {
    document.getElementById(chk).checked = true;
  }
}

function loadQuestions(){
  var elements = [];

  $.ajaxSetup({
    async: false
  });

  $.getJSON('https://framework-gb.cdn.gob.mx/data/encuesta_v1.0/qa/encuesta.json', function(jd) {
  //$.getJSON('http://localhost:8888/qa/encuesta.json', function(jd) {
    elements=[].concat(jd);
  }).success(function() {
    // TODO
  }).error(function(error) {
    // TODO manejo de errores
  });

  $.ajaxSetup({
    async: true
  });

  this.questions = elements;

  var container = "";
  for(index = 0; index < this.questions.length; index++) {
    if(this.questions[index].visible) {
      container+="<div class='row' id='"+this.questions[index].idView+"' style='display:block; padding:10px;' align='left'>";
    } else {
      container+="<div class='row' id='"+this.questions[index].idView+"' style='display:none; padding:10px;' align='left'>";
    }

    container+="<fieldset>";
    container+="<legend class='label form-control clearfix' style='color: black; border: 0 none; width: 100%; white-space: normal; background-color: transparent; font-size: 18px; margin-bottom: 24px; font-weight:normal; box-shadow: inset 0 0 0 rgba(0,0,0,0)'>"+this.questions[index].description+"</legend>";

    if(questions[index].wrap !== undefined && questions[index].wrap !== "") {
      if(questions[index].wrap.type!== undefined && questions[index].wrap.type === "div") {
        var wrap_class = "class='"+questions[index].wrap.class+"'";
        var wrap_align = "align = '"+questions[index].wrap.align+"'";
        container+="<div "+wrap_class+" style='font-weight: normal; width: 100%; margin-top: 18px;'>";
      }
    }

    for (var indexInput = 0; indexInput < this.questions[index].typeInput.length;indexInput++) {
      var action = "";
      var colSpan = this.questions[index].typeInput[indexInput].span;
      var input_class = "";
      var img = "";
      var selected = "";

      if(this.questions[index].typeInput[indexInput].class!=="") {
        input_class = " class = '"+this.questions[index].typeInput[indexInput].class+"' ";
      }
      var align ="align = '"+this.questions[index].typeInput[indexInput].align+"'";
      container+="<div class='col-sm-"+colSpan+"' style='padding:5px;' "+align+">";

      if(this.questions[index].typeInput[indexInput].action) {
        action = " "+this.questions[index].typeInput[indexInput].do+" ";
      }
      if( this.questions[index].typeInput[indexInput].type === "input") {
        if(this.questions[index].typeInput[indexInput].label !== "") {
          container+="<label id='q_l_"+index+""+indexInput+"' >"+this.questions[index].typeInput[indexInput].label+"</label>";
        }
        container+="<input id='q_i_"+index+""+indexInput+"' name='"+this.questions[index].typeInput[indexInput].name+ "'"+action+" ></input>";
      } else if(this.questions[index].typeInput[indexInput].type === "radio") {
        if (this.questions[index].typeInput[indexInput].selected) {
          selected = "checked= 'true'";
        }

        var radio = "<input id='"+this.questions[index].typeInput[indexInput].id+"' type='"+this.questions[index].typeInput[indexInput].type+"' name='"+this.questions[index].typeInput[indexInput].name+"' value='"+this.questions[index].typeInput[indexInput].value+"' "+selected+action+ "><label style='margin-left: 8px; font-weight:normal;' for='"+this.questions[index].typeInput[indexInput].name+"'>"+this.questions[index].typeInput[indexInput].label+"</label>";
        if(this.questions[index].typeInput[indexInput].img !== "") {
          img = "<img id='q_ri_"+index+""+indexInput+"'  src='"+root+this.questions[index].typeInput[indexInput].img+"' />";
          container+= radio+img;
        } else {
          container+= radio;
        }
      } else if(this.questions[index].typeInput[indexInput].type === "checkbox") {
        if (this.questions[index].typeInput[indexInput].selected) {
          selected = "checked= 'true'";
        }

        var chk = "<label style='font-weight: normal;'></label><input id='"+this.questions[index].typeInput[indexInput].id+"'  type='"+this.questions[index].typeInput[indexInput].type+"' name='"+this.questions[index].typeInput[indexInput].name+"' value='"+this.questions[index].typeInput[indexInput].value+"' "+selected+action+ " > "+this.questions[index].typeInput[indexInput].label;
        container += chk;
      } else if ( this.questions[index].typeInput[indexInput].type === "href") {
        container += '<a href="'+this.questions[index].typeInput[indexInput].value+'"  target="_blank" title="Abre en nueva ventana" >'+ this.questions[index].typeInput[indexInput].label+'</a>';
      } else if ( this.questions[index].typeInput[indexInput].type === "label") {
        container+="<label id='l_l_"+index+""+indexInput+"' "+action+" style='font-weight: normal;' >"+this.questions[index].typeInput[indexInput].label+"</label>";
      } else if(this.questions[index].typeInput[indexInput].type === "img" && this.questions[index].typeInput[indexInput].img!=="") {
        img = "<img id='q_ri_"+index+""+indexInput+"' src='"+root+this.questions[index].typeInput[indexInput].img+"'"+action+" />";
        container+= img;
      } else if( this.questions[index].typeInput[indexInput].type === "button") {
        var button = "<button id='"+this.questions[index].typeInput[indexInput].id+"' title='"+this.questions[index].typeInput[indexInput].label+"' name='"+this.questions[index].typeInput[indexInput].name+"'  value='"+this.questions[index].typeInput[indexInput].value+"' "+action+input_class+ ">";
        if(this.questions[index].typeInput[indexInput].img !== "") {
          img = "<img id='q_ri_"+index+""+indexInput+"'  src='"+root+this.questions[index].typeInput[indexInput].img+"' />";
          container+= button+img;
        } else {
          container+= button;
        }
      }
      container+="</div>";
    }
    
    container+="</fieldset>";
    container+="</div>";
  }
  return  container;
}

function getEncuesta() {
  sending = false;

  if(window.document.getElementById("modEncuestaSatisfaccion") === null) {
    loadModal();
  }

  window.document.getElementById("modEncuestaSatisfaccion").innerHTML = '<div class="modal-dialog" >'+
  '<div class="modal-content">'+
  '<div class="modal-header"> '+
  '<h1 class="modal-title" style="font-size: 24px;">Encuesta de satisfacci&oacute;n</h1>'+
  '</div>'+
  '<div class="modal-body"><form onsubmit="event.preventDefault();">'+loadQuestions()+'</form>'+
  '<hr>'+
  '<div class="form-group">'+
  '<label class="clearfix col-md-12" style="text-align: center;">Suscr&iacute;bete al newsletter de gob.mx</label>'+
  '<div class="row"><div class="col-md-9 col-md-offset-1"><input type="text" class="form-control" style="height: 46px !important;" id="email" name="email" aria-label="Ingresa tu correo electr&oacute;nico" placeholder="usuario@ejemplo.com">'+
  '<button type="button" onclick="subscribeToGob()" class="btn blue-right" style="right: 20px;"><span class="icon-caret-right"></span></button></div></div>'+
  '<div class="col-md-12 respuesta"></div>'+
  '</div><hr class="col-md-12 clearfix"></div>'+
  '<div class="modal-footer">'+
  '<div class="row" style="padding:10px;" align="center">'+
  '<button type="button" class="btn btn-primary" onclick="saveInformation()" >Enviar encuesta</button>'+
  '</div>'+
  '<div class="row" style="padding:10px;" align="left">'+
  '<label id="msjEncuesta"></label>'+
  '</div>'+
  '</div>'+
  '</div>';

  var objectJson = {"homoclave": this.homoclave, "fecha": new Date().toLocaleString(), "questions": [ ] };
  localStorage.setItem(localStg,JSON.stringify(objectJson));

  $('#modEncuestaSatisfaccion').modal("show");
}

function getEncuestaHC(homoclave) {
  this.homoclave = homoclave;
  getEncuesta();
}

function subscribeToGob() {
  var subEmail = $('#email').val();
  $.ajax({
    url: 'https://www.gob.mx/subscribe',
    type: 'POST',
    async:false,
    headers: {'X-Client': window.location.href },
    dataType: 'json',
    data: {"email[address]": subEmail},
    success: function() {
      $('.respuesta').text('some text');
    },
    error: function() {
      $('.respuesta').text('Hubo un error, intenta más tarde');
    }
  });  
}

function saveInformation() {
  if(sending === false) {
    var ls = JSON.parse(localStorage.getItem(localStg));
    if(ls.questions !== undefined && ls.questions !== null && ls.questions.length> 0) {
      $.ajax({
        type: "POST",
        data: JSON.stringify(ls),
        //url: "https://www.gob.mx/EncuestaSatisfaccion/saveForm",
        url: "https://www.gob.mx/surveyStaging/saveForm",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async:false,
        headers: {
          'X-Client': window.location.href },
          success: function(data){
            document.getElementById('msjEncuesta').innerHTML = data.message;
            document.getElementById('msjEncuesta').setAttribute("class","alert alert-success");
            setTimeout(function() {
              $('#modEncuestaSatisfaccion').modal("hide");
            }, 2000);
          },
          error: function(data){
            document.getElementById('msjEncuesta').innerHTML = "El servicio no esta disponible, favor de intentar mas tarde.";
            document.getElementById('msjEncuesta').setAttribute("class","alert alert-danger");
            setTimeout(function() {
              $('#modEncuestaSatisfaccion').modal("hide");
            }, 2000);
          }
      });
    } else {
      setTimeout(function() {
        $('#modEncuestaSatisfaccion').modal("hide");
      }, 1000);
    }
    sending = true;
  }
}

function parseToJSON(list) {
  var objectJson = {"homoclave":this.homoclave,"fecha":new Date().toLocaleString(), "questions": [ ]};
  for (var i = 0; i < list.length; i++) {
    objectJson.questions.push({question:list[i].question,answer:list[i].answer});
  }
  return objectJson;
}

function setLocalStorage(localStg,objectJson) {
  localStorage.setItem(localStg,JSON.stringify(objectJson));
}

function startEncuesta(time) {
  setTimeout(function() {
    getEncuesta();
  }, time);
}

function startEncuestaHC(time,homoclave) {
  var hc = homoclave;
  setTimeout(function() {
    getEncuestaHC(hc);
  }, time);
}
