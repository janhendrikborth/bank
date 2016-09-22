import React, { Component } from 'react';
import logo from './logo.svg';
import './App.css';
import Api from './Api';

class App extends Component {
	
	fillfield(value){
		debugger;
		this.setState({text:value});
	}
	
	constructor(props) {
	    super(props);
	    this.state = {text:"construcssstor"};
	    this.fillfield = this.fillfield.bind(this);
	    
	    Api.getAccounts(this.fillfield);
	}
	
	didComponentMount(){
		
	}
  
	getInitialState() {
	    return {text: 'initial'};
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
      </div>
    );
  }
}

export default App;
