var getJSON = function(url) {
  return new Promise(function(resolve, reject) {
    var xhr = new XMLHttpRequest();
    xhr.open('get', url, true);
    xhr.responseType = 'json';
    xhr.onload = function() {
    var status = xhr.status;
     if (status == 200) {
        resolve(xhr.response);
      } else {
        reject(status);
      }
    };
    xhr.send();
  });
};

$(document).ready(function(){
	getJSON("../documents").then(function(data){
		var jsonData = data.result;
		
	}, function(status){
		alert('Something went wrong.');
	});
});