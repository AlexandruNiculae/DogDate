/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, Image, ScrollView} from 'react-native';
import { StackNavigator } from 'react-navigation';
import DogForm from './DogForm';
import DogList from './DogList';
import DogView from './DogView';
import DogChart from './DogChart';

class AllDogs {
	constructor(){
		this.data = [];
	}

	addDog(dog){
		this.data.push(dog);
	}

	delDog(dog){
		var temp = this.data.slice();
 	   var newDogs = temp.filter(function(el){
 		   return el.name !== dog.name && el.race !== dog.race && el.personality !== dog.personality && el.age !== dog.age;
 	   });
 	  this.data = newDogs.slice();
	}

	updDog(dog){
		var newDogs = this.data.slice();
 	   for (var i in newDogs) {
 	      if (newDogs[i].name == dog.name) {
 	         newDogs[i].race = dog.race;
 			   newDogs[i].personality = dog.personality;
 			   newDogs[i].age = dog.age;
 	         break; //Stop this loop, we found it!
 	      }
    	}
 	  this.data = newDogs.slice();
	}

	getDogs(){
		return this.data;
	}
}

const allDogs = new AllDogs();

export default class App extends Component {

  static navigationOptions = {
    title : 'Add a dog'
  };

  render() {
    return (
      <View style={{ flex: 1 }}>
        <MainNavigator/>
      </View>
    );
  }
}


const MainNavigator = StackNavigator({
  Home: { screen: DogList },
  AddDog: { screen: DogForm },
  ViewDog: { screen: DogView },
  Stats: {screen: DogChart},
},{headerMode: "none"});
