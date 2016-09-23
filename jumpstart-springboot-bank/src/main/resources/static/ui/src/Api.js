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
	
	static makeWithdrawal(accountNumber,amount,callback){
		
		fetch('http://localhost:8080/accounts/book', {
			  method: 'POST',
			  headers: {
				    'Accept': 'application/json',
				    'Content-Type': 'application/json'
				  },
			  body:JSON.stringify({
				  accountNumber: accountNumber,
				  amount: amount,
				  operationType: 'withdraw'
			  })
		
		}).then(function(res){
		      //debugger;
		      return res.text();
		    }).then(function(body) {
		     return callback(body);
		    });
		  
	}
	
	static makeDeposit(accountNumber,amount,callback){
		fetch('http://localhost:8080/accounts/book', {
			  method: 'POST',
			  headers: {
				    'Accept': 'application/json',
				    'Content-Type': 'application/json'
				  },
			  body:JSON.stringify({
				  accountNumber: accountNumber,
				  amount: amount,
				  operationType: 'deposit'
			  })
		
		}).then(function(res){
		      //debugger;
		      return res.text();
		    }).then(function(body) {
		     return callback(body);
		    });
	
	}
	
	
}

export default Api;