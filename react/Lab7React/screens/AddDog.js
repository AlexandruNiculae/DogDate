import { Platform, StyleSheet, Text, View, Image, ScrollView, Alert} from 'react-native';
import React, { Component } from 'react';
import { FormLabel, FormInput, Button } from 'react-native-elements';

export default class DogForm extends Component {

  constructor(props) {
    super(props);
    this.state = {
      dogName: "",
      dogRace: "",
      dogPers: "",
      dogAge: -1,
    }
  }

  valid(){
	  if(this.state.dogName.length == 0 || this.state.dogRace.length == 0 || this.state.dogPers.length == 0 || this.state.dogAge.length == 0){
		  this.doAlert('Empty Fields','Cannot have empty fields!');
		  return false;
	  }
	  else{
		  if(this.state.dogAge > 20 || this.state.dogAge < 0){
			  this.doAlert('Invalid Age','Age must be between 0 and 20!');
			  return false;
		  }
	  }
	  return true;
  }

  addDog() {
	 promise = save(this.state.dogName, this.state.dogRace, this.state.dogPers, this.state.dogAge);
	 promise.then(data => {
	   console.log(data);
	 });
  }

  doAlert(title,msg){
	  Alert.alert(
		  title,
		  msg,
		  [
			 {text: 'OK', onPress: () => console.log('OK Pressed')},
		  ],
		  { cancelable: false }
		)
  }

  render(){
    const {navigate} = this.props.navigation;

    return (
      <ScrollView>
        <FormLabel>Name:</FormLabel>
        <FormInput onChangeText={(dogName) => this.setState({dogName})}/>
        <FormLabel>Race:</FormLabel>
        <FormInput onChangeText={(dogRace) => this.setState({dogRace})}/>
        <FormLabel>Personality:</FormLabel>
        <FormInput onChangeText={(dogPers) => this.setState({dogPers})}/>
        <FormLabel>Age:</FormLabel>
        <FormInput onChangeText={(dogAge) => this.setState({dogAge})} keyboardType = 'numeric'/>
        <Button icon={{name: 'add'}}
		  		onPress={() => {
					if(this.valid()){
						this.addDog();
						navigate('LandingPage'); //, {action:'add', name: this.state.dogName, race: this.state.dogRace, pers: this.state.dogPers, age: this.state.dogAge});
					}
				}}
				/>
      </ScrollView>
    );}
}

async function save(name, race, pers, age) {

 //var formData = new FormData
 // formData.append('id', id);
 // formData.append('name', name);
 // formData.append('description', description);
 // formData.append('author', author);
 // formData.append('grade', grade);
 console.log(name + " " + race + " " + pers + " " + age);
 try {
	let response = await fetch(
	  'http://192.168.0.107:3000/addDog', {
		 method: 'POST',
		 headers: {
			Accept: 'application/json',
			'Content-Type': 'application/json',
		 },
		 body: JSON.stringify({
			 name: String(name),
			 race: String(race),
			 personality: String(pers),
			 age: String(age),
		}),
	 }
	);
	let responseJson = await response.json();
	return responseJson;
 }catch (error) {
	console.error(error);
 }
}
