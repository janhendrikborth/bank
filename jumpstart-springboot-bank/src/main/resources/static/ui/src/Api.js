class Api{
	

	static getAccounts(callback){
		
		 fetch('http://localhost:8080/test').then(function(res){
		      //debugger;
		      return res.text();
		    }).then(function(body) {
		     return callback(body);
		    });
		
	}
	
	static getAccount(accountNumber,callback){
		
		 fetch('http://localhost:8080/accounts/findbynumber/'+accountNumber).then(function(res){
		      //debugger;
		      return res.json();
		    }).then(function(body) {
		     return callback(body);
		    });
		
	}
	
	
	static createAccount(callback){
		
		 fetch('http://localhost:8080/accounts', {
			  method: 'POST'}).then(function(res){
		      //debugger;
		      return res.json();
		    }).then(function(body) {
		     return callback(body);
		    });
		
	}
	
	static makeWithdrawal(callback){
		
	}
	
	static makeDeposit(callback){
		
	}
	
	
}

export default Api;