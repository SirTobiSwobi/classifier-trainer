$(document).ready(function(){
	
	$("#createForm").hide();
	$("#uploadForm").hide();
	$("#deleteConfirm").hide();
	
	var categoryJSON;
	var documentJSON;
	var relationshipJSON;
	var targetfunctionJSON;
	
	$.getJSON("../categories",function(json){
			categoryJSON = json;
			console.log(categoryJSON);
		}).done(function(){
			$.getJSON("../documents",function(json){
				documentJSON = json;
				console.log(documentJSON);
			}).done(function(){
					$.getJSON("../relationships",function(json){
					relationshipJSON = json;
					console.log(relationshipJSON);
				}).done(function(){
					$.getJSON("../targetfunction",function(json){
						targetfunctionJSON = json;
						console.log(targetfunctionJSON);
					}).done(function(){
						var rootId=0;
						var maxImplicit=0;
						
						for(var i=0;i<categoryJSON.categories.length;i++){
							console.log("category "+categoryJSON.categories[i].id+" - "+categoryJSON.categories[i].label);
							var implicits=0;
							for(var j=0; j<targetfunctionJSON.assignments.length; j++){
								if(targetfunctionJSON.assignments[j].categoryId==categoryJSON.categories[i].id&&targetfunctionJSON.assignments[j].id==-1){
									implicits++;
								}
							}
							console.log("has "+implicits+" implicit assignments. Previous Maximum: "+maxImplicit);
							if(implicits>maxImplicit){
								console.log("assigning root role to category "+categoryJSON.categories[i].id);
								maxImplicit=implicits;
								rootId = categoryJSON.categories[i].id;
							}
							
						}
						console.log("Root has categoryId: "+rootId);
						var categoryDisplay = new Array();
						var categoryDescendants = new Array();
						var categoryDisplayCounter = 0;
						if(relationshipJSON.relationships==null){
							console.log("There are no relationships. Display will be just for categories without their hierarchies");
							for(var i=0;i<categoryJSON.categories.length;i++){
								categoryDisplay[categoryDisplayCounter]=categoryJSON.categories[i];
								categoryDisplayCounter++;
							}
						}else{
							//implement BFS to find everything connected to the root. Repeat for all remaining nodes.
							var visited = new Array();
							var queue = new Array();
							//visited.push(rootId);
							queue.push(rootId);
							while(queue.length>0){
								var catId=queue.pop();
								visited.push(catId);
								console.log("Processing category "+catId);
								console.log(visited);
								for (var i=0;i<relationshipJSON.relationships.length;i++){
									var rel=relationshipJSON.relationships[i];
									if(rel.type=="Sub"&&rel.fromId==catId&&!visited.includes(rel.toId)){
										queue.push(rel.toId)
									}
								}
							}
							
							/*
							for(var i=0;i<categoryJSON.categories.length;i++){
								if(categoryJSON.categories[i].id==rootId){
									categoryDisplay[categoryDisplayCounter]=categoryJSON.categories[i];
									categoryDisplayCounter++;
								}
							}
							console.log("cdc: "+categoryDisplayCounter);
							console.log(categoryDisplay[0]);
							var a=0;
							var desc = new Array();
							for(var i=0; i<relationshipJSON.relationships.length;i++){
								if(relationshipJSON.relationships[i].type=="Sub"&&relationshipJSON.relationships[i].fromId==categoryDisplay[categoryDisplayCounter-1].id){
									desc[a]=relationshipJSON.relationships[i].toId;
									a++
								}
							}
							categoryDescendants[categoryDisplayCounter-1]=desc;
							console.log(categoryDescendants[categoryDisplayCounter-1]);
						}
						
						console.log(categoryDisplay);
						test();
						*/
					});
				});
				
			});
			
	});
	
	$("#read").click(function() {
		$("#createForm").hide("slow");
		$("#uploadForm").hide("slow");
		$("#deleteConfirm").hide("slow");
		$("#relList").empty();
		$("#relList").append("<h2>Available category relationships:</h2>");
		$.getJSON("../categories",function(json){
				categoryJSON = json;
			}).done(function(){
				$.getJSON("../relationships",function(json){
					
					if(json.relationships==null){
						$("#relList").append("<h3>There are currently no category relationships in this microservice. You can add one</h3>");
					}else{
						for (var i=0; i< json.relationships.length; i++){
							var fromLabel;
							var toLabel;
							for(var j=0; j< categoryJSON.categories.length; j++){
								if(json.relationships[i].fromId==categoryJSON.categories[j].id){
									fromLabel = categoryJSON.categories[j].label;
								}
								if(json.relationships[i].toId==categoryJSON.categories[j].id){
									toLabel = categoryJSON.categories[j].label;
								}
							}
							var appendString = "<li><a href=\"relationship.html?id=";
							appendString = appendString +json.relationships[i].id+"\">/relationships/"+json.relationships[i].id;
							appendString = appendString +" from: "+json.relationships[i].fromId;
							appendString = appendString +" ("+fromLabel+")";
							appendString = appendString +" to: "+json.relationships[i].toId;
							appendString = appendString +" ("+toLabel+")";
							appendString = appendString +" - Type: "+json.relationships[i].type;
							appendString = appendString +"</a></li>";
							$("#relList").append(appendString);
						}
					}
					
				})
		});
		$("#relList").show("fast");
	});

	$("#create").click(function() {
		$("#fromId").empty();
		$("#toId").empty();
		$.getJSON("../categories",function(json){
				
				var appendString = "";
				
				for(var j=0; j< json.categories.length; j++){
					
					appendString = appendString +"<option value=\""+json.categories[j].id+"\">";
					appendString = appendString +json.categories[j].id+" (";
					appendString = appendString +json.categories[j].label+")</option>";
					
					
				}
				$("#fromId").append(appendString);
				$("#toId").append(appendString);
						
			
		});
		$("#relList").hide("slow");
		$("#uploadForm").hide("slow");
		$("#deleteConfirm").hide("slow");
		$("#createForm").show("fast");
	});
	
	$("#upload").click(function() {
		$("#relList").hide("slow");
		$("#createForm").hide("slow");
		$("#deleteConfirm").hide("slow");
		$("#uploadForm").show("fast");
	});
	
	$("#delete").click(function() {
		$("#relList").hide("slow");
		$("#uploadForm").hide("slow");
		$("#createForm").hide("slow");
		$("#deleteConfirm").show("fast");
	});
	
	
	$("#createF").submit(function( event ) {
		event.preventDefault();
		console.log("Uploading data");
		
		
		var form = $("#createF").serializeArray();
		console.log(form);
		var json = "{ \"relationships\":[{\"id\":"+form[0].value;
		json = json + ", \"fromId\":"+form[1].value;
		json = json + ", \"toId\":"+form[2].value;
		json = json + ", \"type\": \""+form[3].value+"\"";
		json = json + "}]}";
		console.log(json);
		
		var url="../relationships";
		
		
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
		    	console.log('something worked');
			 	console.log('succes: '+data);
			}
		 });
		
	});
	
	$("#uploadF").submit(function( event ) {
		event.preventDefault();
		//console.log("Uploading data");
		var form = $("#uploadF").serializeArray();
		//console.log(form);
		var json = form[0].value;
		//console.log(json);
		
		var url="../relationships";
		
		
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
		    	console.log('something worked');
			 	console.log('succes: '+data);
			}
		 });
		
	});
	
	$("#deleteF").submit(function( event ) {
		event.preventDefault();
		//console.log("Deleting data");
		var form = $("#deleteF").serializeArray();
		//console.log(form);
		var json="";
		if(form[0].value=="delete"){
			var url="../relationships";
		
		
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
	

});