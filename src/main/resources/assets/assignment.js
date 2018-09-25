function getIds(){
	
	var pageURL = window.location.search.substring(1);
	var urlVariables = pageURL.split('&');
	var assId, docId, catId;
	for(var i=0; i< urlVariables.length; i++){
		var paramName = urlVariables[i].split('=');
		if(paramName[0] == "assId"){
			assId = paramName[1];
		}else if(paramName[0] == "docId"){
			docId = paramName[1];
		}else if(paramName[0] == "catId"){
			catId = paramName[1];
		}
	}
	
	var output = {assId: assId, docId: docId, catId: catId};
	return output;
	
}

function read(assId){
	var appendString="";
	var ass;
	var document;
	var category;
	$.getJSON("../targetfunction/"+assId,function(json){
		ass=json;
	}).done(function(){		
		console.log(ass);
		console.log("../documents/"+ass.documentId);
		console.log("../categories/"+ass.categoryId);
		appendString+="<h3>Assignment "+assId+": ";
		$.getJSON("../documents/"+ass.documentId,function(json){
			console.log(json);
			document=json;
			appendString+="Document "+document.id+" ("+document.label+") is assigned to ";
		}).done(function(){
			$.getJSON("../categories/"+ass.categoryId,function(json){
				console.log(json);
				category=json;
				appendString+="category "+category.id+" ("+category.label+")</h3>";
			}).done(function(){
				$("#assList").empty();
				$("#assList").append(appendString);
				$("#assList").show();
			});
		});		
	}).fail(function(){
		
		appendString+="<h3>An assignment with ID "+assId+" does not exist. You can create it.</h3>";
		$("#assList").empty();
		$("#assList").append(appendString);
		$("#assList").show();
	});
	
}

function renderCreateForm(ids){
	$("#docId").empty();
	$("#catId").empty();
	var categoryJSON;
	var documentJSON;
	$.getJSON("../categories",function(json){
		categoryJSON = json;
		console.log(categoryJSON);
	}).done(function(){
		$.getJSON("../documents",function(json){
			documentJSON = json;
			console.log(documentJSON);
		}).done(function(){
			var appendString = "";			
			for(var j=0; j< documentJSON.documents.length; j++){				
				appendString = appendString +"<option value=\""+documentJSON.documents[j].id+"\"";
				if(documentJSON.documents[j].id==ids.docId){
					appendString+= " selected";
				}
				appendString+=">";
				appendString = appendString +documentJSON.documents[j].id+" (";
				appendString = appendString +documentJSON.documents[j].label+")</option>";				
			}
			$("#docId").append(appendString);
						
			appendString = "";			
			for(var j=0; j< categoryJSON.categories.length; j++){			
				appendString = appendString +"<option value=\""+categoryJSON.categories[j].id+"\"";
				if(categoryJSON.categories[j].id==ids.catId){
					appendString+= " selected";
				}
				appendString+=">";
				appendString = appendString +categoryJSON.categories[j].id+" (";
				appendString = appendString +categoryJSON.categories[j].label+")</option>";			
			}
			$("#catId").append(appendString);
			console.log("attempting id Setting: "+ids.assId);
			if(idIsSet(ids.assId)){
				console.log("Id is set");
				$("#id").val(ids.assId);
			}
			
		})
	});
}

$(document).ready(function(){
	
	$("#createForm").hide();
	$("#uploadForm").hide();
	$("#deleteConfirm").hide();
	
	var ids=getIds();
	console.log(ids);
	
	if(idIsSet(ids.assId)&&!idIsSet(ids.docId)&&!idIsSet(ids.catId)){
		console.log("I should read something");
		read(ids.assId);
	}else{
		console.log("I should create or update something");
		renderCreateForm(ids);
		$("#assList").hide("slow");
		$("#uploadForm").hide("slow");
		$("#deleteConfirm").hide("slow");
		$("#createForm").show("fast");
	}
	
	
	$("#read").click(function(){
		location.reload(true);
	});
	
	$("#delete").click(function() {
		$("#assList").hide("slow");
		$("#uploadForm").hide("slow");
		$("#createForm").hide("slow");
		$("#deleteConfirm").show("fast");
	});
	
	$("#deleteF").submit(function( event ) {
		event.preventDefault();
		//console.log("Deleting data");
		var form = $("#deleteF").serializeArray();
		//console.log(form);
		var json="";
		if(form[0].value=="delete"){
			var url="../targetfunction/"+ids.assId;	
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
			    	console.log('something worked');
					 	console.log('succes: '+data);
				}
				 });
		}
		
		
		
	});
	
	$("#create").click(function() {
		renderCreateForm(ids);
		$("#assList").hide("slow");
		$("#uploadForm").hide("slow");
		$("#deleteConfirm").hide("slow");
		$("#createForm").show("fast");
	});
	
	$("#createF").submit(function( event ) {
		event.preventDefault();
		//console.log("Uploading data");
		var form = $("#createF").serializeArray();
		//console.log(form);
		var json = "{\"id\":"+form[0].value+
			",\"documentId\":\""+form[1].value;
		json = json+"\",\"categoryId\":\"";
		json = json+form[2].value+"\"}";
		console.log(json);
		
		var url="../targetfunction/"+form[0].value;
		
		
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
		    	console.log('something worked');
				 	console.log('succes: '+data);
			}
			 });
		
	});
	
});
