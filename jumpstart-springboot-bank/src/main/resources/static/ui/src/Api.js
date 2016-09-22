class Api{
	

	static getAccounts(callback){
		
		 fetch('http://localhost:8080/test').then(function(res){
		      //debugger;
		      return res.text();
		    }).then(function(body) {
		     return callback(body);
		    });
		
	}	
	
}

export default Api;