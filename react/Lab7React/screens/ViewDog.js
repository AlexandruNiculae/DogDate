import { Platform, StyleSheet, Text, View, Image, ScrollView,Alert} from 'react-native';
import React, { Component } from 'react';
import { FormLabel, FormInput, Button } from 'react-native-elements'
import FormData from 'FormData'

export default class ViewDog extends React.Component {
 constructor(props) {
	  super(props);
	  this.state = {
		key: -1,
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

  setDog(dog){
		this.setState({key: dog.key, dogName: dog.name, dogRace: dog.race, dogPers: dog.personality, dogAge: dog.age})
	}

	componentWillMount() {
      if(this.props.navigation.state.params){
		var key = this.props.navigation.state.params.key;
 		var name = this.props.navigation.state.params.name;
 	   var race = this.props.navigation.state.params.race;
 	   var pers = this.props.navigation.state.params.pers;
 	   var age = this.props.navigation.state.params.age;
 	   var dog = {key:key, name: name, race: race, personality: pers, age: age};
  		this.setDog(dog);
  		}
    }

  updateDog = () => {
	 promise = save(this.state.key, this.state.dogName, this.state.dogRace, this.state.dogPers, this.state.dogAge);
	 promise.then(data => {
		console.log(data);

	 });
  }

  removeDog = () => {
	 promise = remove(this.state.key);
	 promise.then(data => {
		console.log(data);

	 });
  }

  render(){
		const {navigate} = this.props.navigation;

		return (
			<ScrollView>
				<FormLabel>Name:</FormLabel>
				<FormInput onChangeText={(dogName) => this.setState({dogName})} value = {this.state.dogName} editable = {false}/>
				<FormLabel>Race:</FormLabel>
				<FormInput onChangeText={(dogRace) => this.setState({dogRace})} value = {this.state.dogRace}/>
				<FormLabel>Personality:</FormLabel>
				<FormInput onChangeText={(dogPers) => this.setState({dogPers})} value = {this.state.dogPers}/>
				<FormLabel>Age:</FormLabel>
				<FormInput onChangeText={(dogAge) => this.setState({dogAge})} value = {String(this.state.dogAge)} keyboardType = 'numeric'/>
			   <Button icon={{name: 'check'}}
						  onPress={() =>{
							  if(this.valid())
					  				this.updateDog();
								 	navigate('LandingPage');}
							}

			   />
			   <FormLabel></FormLabel>
			   <Button icon={{name: 'delete'}}
						  onPress={() =>{
							  if(this.valid())
							  		this.removeDog()
								  	navigate('LandingPage');}
						  }

			   />
			</ScrollView>
		);
  }

}

async function remove(id) {
 try {
	let response = await fetch(
	  'http://192.168.0.107:3000/removeDog', {
		 method: 'POST',
		 headers: {
			key : id
		 }
	  }
	);
	let responseJson = await response.json();
	return responseJson;
 }catch (error) {
	console.error(error);
 }
}


async function save(key, name, race, pers, age) {

 //var formData = new FormData
 // formData.append('id', id);
 // formData.append('name', name);
 // formData.append('description', description);
 // formData.append('author', author);
 // formData.append('grade', grade);
 console.log(key + " " + name + " " + race + " " + pers + " " + age);
 try {
	let response = await fetch(
	  'http://192.168.0.107:3000/updateDog', {
		 method: 'POST',
		 headers: {
			Accept: 'application/json',
			'Content-Type': 'application/json',
		 },
		 body: JSON.stringify({
			 key: String(key),
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
