import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Api from './Api';

class App extends Component {
	
	fillfield(value){
		//debugger;
		this.setState({text:value});
	}
	
	fillCreateAccount(value){
		this.setState({account:value});
	}
	
	
	getAccountBalance(){
		var accountNumber= this.refs.accountNumber.value;
		console.log("acco",accountNumber);
		Api.getAccount(accountNumber,(account)=>{	
			this.setState({balance:account.balance});
		});
		
	}
	

	clickCreateAccount(){
		//debugger;
		Api.createAccount((account)=>{
			
			this.setState({account:account.accountNumber});
		});
	}
	
	Withdrawal(){
		Api.makeWithdrawal();
	}
	
	deposit(){
		Api.makeDeposit();
	}
	constructor(props) {
	    super(props);
	    this.state = {text:"construcssstor"};
	    this.fillfield = this.fillfield.bind(this);
	    this.fillCreateAccount = this.fillCreateAccount.bind(this);
	    this.clickCreateAccount=this.clickCreateAccount.bind(this);
	    this.getAccountBalance=this.getAccountBalance.bind(this);
	    
	    Api.getAccounts(this.fillfield);
	}
	
	
	handleTextChange(){

	    var newValue= this.refs.textField.value;
	    this.state={accountNummer:newValue};
	    this.link={linkfetch:"http://localhost:8080/bank/findAccount/"+this.state.accountNummer};
	    debugger;
	    console.log(this.state.accountNummer);
	    this.fetchgetBalance((account) => {
	      console.log(account.balance);
	      this.setState({balance: account});
	    });
	  }
	
	
	didComponentMount(){
		
	}
  
	  
  render() {
	  
	  
    return (
      <div className="App">
        <div className="App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <h2>Welcome to React</h2>
        </div>
        <p className="App-intro">
          {this.state.text}
        </p>
        
        <form id="contactForm">
        <fieldset>
          <ul id="todo-list">
            <input type="text" ref="accountNumber" id="kontoId" name="kontoId" placeholder="Kontonummer" />
        	 <input type="text" id="amount" name="amount" placeholder="Betrag" value={this.state.balance}/>
              <input type="button" onClick={this.withdrawal}  value="Withdrawal"/>
            	  <input type="button" onClick={this.deposit}  value="Deposit"/>
                  <input type="button" onClick={this.getAccountBalance}  value="Balance"/>
            	  <input type="button" onClick={this.clickCreateAccount} value="createAccount"/>
            	  </ul>
          </fieldset>
      </form>
     
      </div>
    );
  }
}

export default App;
