import { Platform, StyleSheet, Text, View, Image, ScrollView,Alert} from 'react-native';
import React, { Component } from 'react';
import { FormLabel, FormInput, Button } from 'react-native-elements'


export default class DogView extends Component {

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
		this.setState({dogName: dog.name, dogRace: dog.race, dogPers: dog.personality, dogAge: dog.age})

	}

	componentWillMount() {
     if(this.props.navigation.state.params){
		var name = this.props.navigation.state.params.name;
	   var race = this.props.navigation.state.params.race;
	   var pers = this.props.navigation.state.params.pers;
	   var age = this.props.navigation.state.params.age;
	   var dog = { name: name, race: race, personality: pers, age: age};
 		this.setDog(dog);
 		}
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
				 <FormInput onChangeText={(dogAge) => this.setState({dogAge})} value = {this.state.dogAge} keyboardType = 'numeric'/>
				 <Button icon={{name: 'check'}}
				 			onPress={() =>{
								if(this.valid())
									navigate('Home', {action:'upd', name: this.state.dogName, race: this.state.dogRace, pers: this.state.dogPers, age: this.state.dogAge})}
								}
				 />
				 <FormLabel></FormLabel>
				 <Button icon={{name: 'delete'}}
				 			onPress={() =>{
								if(this.valid())
									navigate('Home', {action:'del', name: this.state.dogName, race: this.state.dogRace, pers: this.state.dogPers, age: this.state.dogAge})}
							}
				 />
			 </ScrollView>
		 );
	}
}
