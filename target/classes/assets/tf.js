function findRootId(categories, assignments){
	var rootId=0;
	var maxImplicit=0;
	for(var i=0;i<categories.length;i++){
		console.log("category "+categories[i].id+" - "+categories[i].label);
		var implicits=0;
		if(assignments!=null){
			for(var j=0; j<assignments.length; j++){
				if(assignments[j].categoryId==categories[i].id&&assignments[j].id==-1){
					implicits++;
				}
			}
		}	
		console.log("has "+implicits+" implicit assignments. Previous Maximum: "+maxImplicit);
		if(implicits>maxImplicit){
			console.log("assigning root role to category "+categories[i].id);
			maxImplicit=implicits;
			rootId = categories[i].id;
		}
		
	}
	return rootId;
}

function getCategoryAssignmentArray(assignments, catId){
	var output = new Array();
	if(assignments!=null){
		for(var i=0;i<assignments.length;i++){
			if(assignments[i].categoryId==catId){
				output.push(assignments[i]);
			}
		}
	}
	return output;
}

function hideDesc(catId){
	console.log(catId);
	$("#cat"+catId+"desc").hide();
}

function showDesc(catId){
	console.log(catId);
	$("#cat"+catId+"desc").show();
}

function hideDocs(catId){
	console.log(catId);
	$("#cat"+catId+"docs").hide();
}

function showDocs(catId){
	console.log(catId);
	$("#cat"+catId+"docs").show();
}

function showEq(catId){
	$.getJSON("../relationships/ancestors/"+catId,function(json){
		console.log(json);
		for(var i=json.categories.length-1; i>=0; i--){
			showDesc(json.categories[i].id);
		}
	});
}



function generateTargetFunctionCategory(categories, descendants, equalities, assignments, documents, i, visited, depth){
	var appendString="";
	if(!visited.includes(categories[i])){
		visited.push(categories[i]);
		appendString = "<div id=\"cat"+categories[i].id+"\" style=\"padding-left: 10px\">";
		appendString += "Category "+categories[i].label;
		if(typeof equalities[i]!="undefined" && equalities[i].length>0){
				appendString += " equal to ";
			for(var j=0;j<equalities[i].length;j++){
				var eq = getObjectById(categories, equalities[i][j]);
				appendString += "<a href=\"#cat"+eq.id+"\" onclick=\"showEq("+eq.id+")\">"
				appendString += eq.label+"</a>";
				if(j<equalities[i].length-1){
					appendString += ", ";
				}
					
			}
		}		
		appendString += "<br/>";
		appendString += assignments[i].length+" assigned documents ";
		if(typeof descendants[i]!="undefined"){
			appendString += "/ "+descendants[i].length+" descendant categories";
		}
		appendString += "<br/>";
		appendString += "<button onclick=\"hideDesc("+categories[i].id+")\">Hide descendants</button>";
		appendString += "<button onclick=\"showDesc("+categories[i].id+")\">Show descendants</button>";
		appendString += "<button onclick=\"hideDocs("+categories[i].id+")\">Hide assignments</button>";
		appendString += "<button onclick=\"showDocs("+categories[i].id+")\">Show assignments</button>";
		appendString += "<br/>";
		appendString += "<a href=\"assignment.html?catId="+categories[i].id+"\"><button>Assign document</button></a>";
		if(assignments[i].length>0){
			appendString += "<div id=\"cat"+categories[i].id+"docs\">";
			appendString += "Documents: </br>";
			for(var j=0; j<assignments[i].length; j++){
				var document = getObjectById(documents,assignments[i][j].documentId);
				if(assignments[i][j].id==-1){
					appendString += "Implicit: ";
				}else{
					appendString += "Direct: ";
				}
				appendString += "<a href=\"document.html?id="+document.id+"\">"
				appendString += document.label+"</a>"
				appendString += "<a href=\"assignment.html?catId="+categories[i].id+"&docId="+document.id+"&assId="+assignments[i][j].id+"\"><button>Edit assignment</button></a>";
				appendString += "<br/>";
			}
			appendString += "</div>";
		}
		if(typeof descendants[i]!="undefined"&&descendants[i].length>0){
			appendString += "<div id=\"cat"+categories[i].id+"desc\">";
			appendString += "Descendants: </br>";
			for(var j=0; j<descendants[i].length; j++){
				console.log("category "+i+" "+categories[i].label+" descendant "+j+" is category: ")
				console.log(categories.indexOf(descendants[i][j]));
				var descIndex = categories.indexOf(descendants[i][j]); 
				var catResult=generateTargetFunctionCategory(categories, descendants, equalities, assignments, documents,descIndex,visited, depth+1);
				appendString += catResult.appendString;
				visited = catResult.visited;
			}
			appendString += "</div>";
		}
		appendString += "</div>";
	}
	var output = {appendString: appendString, visited: visited};
	return output;
	
	
}

function renderTargetFunction(categoryDisplay, categoryDescendants, categoryEqualities, categoryAssignments, documents){
	$("#assList").empty();
	$("#assList").append("<h2>Available assignments:</h2>");
	if(categoryDisplay.length==0){
		$("#assList").append("<h3>There is currently no target function in this microservice. You can add one</h3>");
	}else{
		var visited = new Array();
		for(var i=0;i<categoryDisplay.length;i++){
			
			var catResult = generateTargetFunctionCategory(categoryDisplay,categoryDescendants,categoryEqualities, categoryAssignments, documents, i, visited, 0);
			var appendString = catResult.appendString;
			visited = catResult.visited;
			
			$("#assList").append(appendString);
			$("#cat"+categoryDisplay[i].id+"desc").hide();
			$("#cat"+categoryDisplay[i].id+"docs").hide();
		}
	}
	
}

function readTargetFunction(){
	console.log("Reading the target function");
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
					
					var categoryDisplay = new Array();
					var categoryDescendants = new Array();
					var categoryEqualities = new Array();
					var categoryAssignments = new Array();
					
					var categoryDisplayCounter = 0;
					if(relationshipJSON.relationships==null){
						console.log("There are no relationships. Display will be just for categories without their hierarchies");
						for(var i=0;i<categoryJSON.categories.length;i++){
							categoryDisplay[categoryDisplayCounter]=categoryJSON.categories[i];
							categoryAssignments[categoryDisplayCounter]=getCategoryAssignmentArray(targetfunctionJSON.assignments,categoryJSON.categories[i].id);
							categoryDisplayCounter++;
						}
					}else{
						var rootId=findRootId(categoryJSON.categories, targetfunctionJSON.assignments);
						console.log("Root has categoryId: "+rootId);
						//implement BFS to find everything connected to the root. Repeat for all remaining nodes.
						var visited = new Array();
						var queue = new Array();
						var unvisited = getIds(categoryJSON.categories);
						//visited.push(rootId);
						queue.push(rootId);
						while(queue.length>0){
							var catId=queue.pop();
							visited.push(catId);
							unvisited=deleteFromArray(unvisited, catId);
							console.log("Processing category "+catId);
							console.log(visited);	
							console.log(unvisited)
							for(var j=0;j<categoryJSON.categories.length;j++){
								if(categoryJSON.categories[j].id==catId){
									categoryDisplay[categoryDisplayCounter]=categoryJSON.categories[j];
								}
							}
							categoryDescendants[categoryDisplayCounter]=new Array();
							categoryEqualities[categoryDisplayCounter]=new Array();
							for (var i=0;i<relationshipJSON.relationships.length;i++){
								var rel=relationshipJSON.relationships[i];
								if(rel.type=="Sub"&&rel.fromId==catId&&!visited.includes(rel.toId)){
									queue.push(rel.toId)
									for(var j=0;j<categoryJSON.categories.length;j++){
										var cat=categoryJSON.categories[j];
										if(cat.id==rel.toId){
											categoryDescendants[categoryDisplayCounter].push(cat);
										}
									}							
								}else if(rel.type=="Equality"&&rel.fromId==catId){
									categoryEqualities[categoryDisplayCounter].push(rel.toId);
								}else if(rel.type=="Equality"&&rel.toId==catId){
									categoryEqualities[categoryDisplayCounter].push(rel.fromId);
								}
							}
							categoryAssignments[categoryDisplayCounter]=getCategoryAssignmentArray(targetfunctionJSON.assignments,catId);
							categoryDisplayCounter++;
							if(queue.length==0&&unvisited.length>0){
								//check for alternative roots
								rootId=findRootId(buildArrayFromIds(categoryJSON.categories,unvisited), targetfunctionJSON.assignments);
								console.log("Found new root: "+rootId);
								queue.push(rootId);
							}
						}
						
						
					}
					
					console.log(categoryDisplay);
					console.log(categoryDescendants);
					console.log(categoryEqualities);
					console.log(categoryAssignments);
					renderTargetFunction(categoryDisplay, categoryDescendants, categoryEqualities, categoryAssignments, documentJSON.documents);
					
				});
			});
			
		});
		
	});
}

function renderCreateForm(){
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
				appendString = appendString +"<option value=\""+documentJSON.documents[j].id+"\">";
				appendString = appendString +documentJSON.documents[j].id+" (";
				appendString = appendString +documentJSON.documents[j].label+")</option>";				
			}
			$("#docId").append(appendString);
						
			appendString = "";			
			for(var j=0; j< categoryJSON.categories.length; j++){			
				appendString = appendString +"<option value=\""+categoryJSON.categories[j].id+"\">";
				appendString = appendString +categoryJSON.categories[j].id+" (";
				appendString = appendString +categoryJSON.categories[j].label+")</option>";			
			}
			$("#catId").append(appendString);			
		})
	});
}



$(document).ready(function(){
	
	$("#createForm").hide();
	$("#uploadForm").hide();
	$("#deleteConfirm").hide();
	
	readTargetFunction();
	
	$("#read").click(function(){
		location.reload(true);
	});
	
	$("#create").click(function() {
		renderCreateForm();
		$("#assList").hide("slow");
		$("#uploadForm").hide("slow");
		$("#deleteConfirm").hide("slow");
		$("#createForm").show("fast");
	});
	
	$("#upload").click(function() {
		$("#assList").hide("slow");
		$("#createForm").hide("slow");
		$("#deleteConfirm").hide("slow");
		$("#uploadForm").show("fast");
	});
	
	$("#createF").submit(function( event ) {
		event.preventDefault();
		console.log("Uploading data");
		
		
		var form = $("#createF").serializeArray();
		console.log(form);
		var json = "{ \"assignments\":[{\"id\":"+form[0].value;
		json = json + ", \"documentId\":"+form[1].value;
		json = json + ", \"categoryId\":"+form[2].value+"";
		json = json + "}]}";
		console.log(json);
		
		var url="../targetfunction";	
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
		location.reload(true);
	});
	
	$("#uploadF").submit(function( event ) {
		event.preventDefault();
		//console.log("Uploading data");
		var form = $("#uploadF").serializeArray();
		//console.log(form);
		var json = form[0].value;
		//console.log(json);
		var url="../targetfunction";	
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
			var url="../targetfunction";
		
		
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


