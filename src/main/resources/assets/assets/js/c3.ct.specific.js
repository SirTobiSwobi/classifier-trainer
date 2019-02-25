/**
 * Configuration Functions. These are overwritten for each different classifier trainer
*/

function renderConfigurations(){
$("#list").empty();
$("#list").append("<h2>Available configurations:</h2>");
$.getJSON("../configurations",function(json){	
	if(json.configurations==null){
		$("#list").append("<h3>There are currently no configurations in this microservice. You can add one</h3>");
	}else{
		for (var i=0; i< json.configurations.length; i++){
			$("#list").append("<li><a href=\"configuration.html?confId="+json.configurations[i].id+"\">/configurations/"+json.configurations[i].id+"</a></li>");
		}
	}
});
}

function renderConfiguration(confId){
$("#list").empty();
$("#list").append("<h2>Available configuration:</h2>");
$.getJSON("../configurations/"+confId,function(json){
	if(json==null){
		$("#list").append("<h3>The configuration with id "+confId+" does not exist. You can create it</h3>");
	}else{
		$("#list").append("<h3>Id: "+json.id+"</h3><ul>");
		$("#list").append("<li>Folds: "+json.folds+"</li>");
		$("#list").append("<li>Include Implicits: "+json.includeImplicits+"</li>");
		$("#list").append("<li>Assignment Threshold: "+json.assignmentThreshold+"</li>");
		$("#list").append("<li>Selection Policy: "+json.selectionPolicy+"</li>");
		$("#list").append("</ul>");
	}
});
}

function createConfiguration(form){
	var json = "{ \"configurations\":[{\"id\":"+form[0].value+
				", \"folds\":"+form[1].value+
				", \"includeImplicits\":"+form[2].value+
				", \"assignmentThreshold\":"+form[3].value+
				", \"selectionPolicy\": \""+form[4].value+"\""+
				" }]}";
	console.log(json);
	
	var url="../configurations";
	
	
	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'POST',
	    dataType: 'json',
	    data: json,
	    success: function(data){
		 	console.log('succes: '+data);
		}
	 });
}

function updateConfiguration(form){
	var json = "{ \"id\":"+form[0].value+
				", \"folds\":"+form[1].value+
				", \"includeImplicits\":"+form[2].value+
				", \"assignmentThreshold\":"+form[3].value+
				", \"selectionPolicy\": \""+form[4].value+"\""+
				" }";
	console.log(json);
	
	var url="../configurations/"+form[0].value;
	
	
	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'PUT',
	    dataType: 'json',
	    data: json,
	    success: function(data){
			 console.log('succes: '+data);
		}
	 });
}

function renderConfigurationUpdate(confId){
	$.getJSON("../configurations/"+confId,function(json){
		if(json==null){
			//nothing but empty fields
		}else{
			$("#id").val(json.id);
			$("#folds").val(json.folds);
			$("#includeImplicits").empty();
			if(json.includeImplicits==true){
				$("#includeImplicits").append("<option value=\"true\" selected>true</selected>");
				$("#includeImplicits").append("<option value=\"false\">false</selected>");
			}else{
				$("#includeImplicits").append("<option value=\"true\">true</selected>");
				$("#includeImplicits").append("<option value=\"false\" selected>false</selected>");
			}
			$("#assignmentThreshold").val(json.assignmentThreshold);
			$("#selectionPolicy").empty();
			if(json.selectionPolicy=="MicroaverageF1"){
				$("#selectionPolicy").append("<option value=\"MicroaverageF1\" selected>Microaverage F1</option>");
			}else{
				$("#selectionPolicy").append("<option value=\"MicroaverageF1\">Microaverage F1</option>");
			}
			if(json.selectionPolicy=="MicroaveragePrecision"){
				$("#selectionPolicy").append("<option value=\"MicroaveragePrecision\" selected>Microaverage Precision</option>");
			}else{
				$("#selectionPolicy").append("<option value=\"MicroaveragePrecision\">Microaverage Precision</option>");
			}
			if(json.selectionPolicy=="MicroaverageRecall"){
				$("#selectionPolicy").append("<option value=\"MicroaverageRecall\" selected>Microaverage Recall</option>");
			}else{
				$("#selectionPolicy").append("<option value=\"MicroaverageRecall\">Microaverage Recall</option>");
			}
			if(json.selectionPolicy=="MacroaverageF1"){
				$("#selectionPolicy").append("<option value=\"MacroaverageF1\" selected>Macroaverage F1</option>");
			}else{
				$("#selectionPolicy").append("<option value=\"MacroaverageF1\">Macroaverage F1</option>");
			}
			if(json.selectionPolicy=="MacroaveragePrecision"){
				$("#selectionPolicy").append("<option value=\"MacroaveragePrecision\" selected>Macroaverage Precision</option>");
			}else{
				$("#selectionPolicy").append("<option value=\"MacroaveragePrecision\">Macroaverage Precision</option>");
			}
			if(json.selectionPolicy=="MacroaverageRecall"){
				$("#selectionPolicy").append("<option value=\"MacroaverageRecall\" selected>Macroaverage Recall</option>");
			}else{
				$("#selectionPolicy").append("<option value=\"MacroaverageRecall\">Macroaverage Recall</option>");
			}
			
		}
	});
}

function uploadConfigurationJSON(json){
	var url="../configurations";	
	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'POST',
	    dataType: 'json',
	    data: json,
	    success: function(data){
		 	console.log('succes: '+data);
		}
	 });
}

function deleteAllConfigurations(){
	var url="../configurations";	
	var json="";
	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'DELETE',
	    dataType: 'json',
	    data: json,
	    success: function(data){
		 	console.log('succes: '+data);
		}
	 });
}

function deleteConfiguration(confId){
var url="../configurations/"+confId;
var json="";

	$.ajax({
		url: url,
		headers: {
		    'Accept': 'application/json',
	        'Content-Type':'application/json'
	    },
	    method: 'DELETE',
	    dataType: 'json',
	    data: json,
	    success: function(data){
			 	console.log('succes: '+data);
		}
	 });
}


function renderConfigurationSelectionForm(){
	$("#configuration").empty();
	var configurationJSON;
	$.getJSON("../configurations",function(json){
		configurationJSON = json;
		console.log(configurationJSON);
	}).done(function(){
		
		var appendString = "";			
		for(var j=0; j< configurationJSON.configurations.length; j++){				
			appendString = appendString +"<option value=\""+configurationJSON.configurations[j].id+"\"";
			appendString+=">";
			appendString = appendString +configurationJSON.configurations[j].id+" (";
			appendString = appendString +configurationJSON.configurations[j].folds+" folds, includeImplicits: ";
			appendString = appendString +configurationJSON.configurations[j].includeImplicits+", ";
			appendString = appendString +configurationJSON.configurations[j].assignmentThreshold+" assignmentThreshold, ";
			appendString = appendString +configurationJSON.configurations[j].selectionPolicy+" selectionPolicy ";
			appendString = appendString +")</option>";				
		}
		$("#configuration").append(appendString);
		
	});
}

function renderModel(modId){
	$("#list").empty();
	$("#list").append("<h2>Available model:</h2>");
	$.getJSON("../models/"+modId,function(json){	
	
		$("#list").append("Id: "+json.id+"<br/>");
		$("#list").append("Configuration Id: "+json.configurationId+"<br/>");
		$("#list").append("Include implicits: "+json.includeImplicits+"<br/>");
		$("#list").append("Progress:"+json.progress+"<br/>");
		$("#list").append("TrainingLog:<br/> "+json.trainingLog+"<br/>");
		$("#list").append("<h4>Configuration:</h4>");
		$("#list").append("Id:"+json.configuration.id+"<br/>");
		$("#list").append("Folds:"+json.configuration.folds+"<br/>");
		$("#list").append("IncludeImplicits:"+json.configuration.includeImplicits+"<br/>");
		$("#list").append("SelectionPolicy:"+json.configuration.selectionPolicy+"<br/>");
		
	}).fail(function(){
		$("#list").empty();
		$("#list").append("<h3>There are currently no such model in this microservice. You can add one by starting a training process</h3>");
	});
}

function renderActiveModel(){
	$("#list").empty();
	$("#list").append("<h2>Active model:</h2>");
	$.getJSON("../model",function(json){	
	
		$("#list").append("Id: "+json.id+"<br/>");
		$("#list").append("Configuration Id: "+json.configurationId+"<br/>");
		$("#list").append("Include implicits: "+json.includeImplicits+"<br/>");
		$("#list").append("Progress:"+json.progress+"<br/>");
		$("#list").append("TrainingLog:<br/> "+json.trainingLog+"<br/>");
		$("#list").append("<h4>Configuration:</h4>");
		$("#list").append("Id:"+json.configuration.id+"<br/>");
		$("#list").append("Folds:"+json.configuration.folds+"<br/>");
		$("#list").append("IncludeImplicits:"+json.configuration.includeImplicits+"<br/>");
		$("#list").append("SelectionPolicy:"+json.configuration.selectionPolicy+"<br/>");
		
	}).fail(function(){
		$("#list").empty();
		$("#list").append("<h3>There is no active model in this microservice. You can assign one.</h3>");
	});
}
