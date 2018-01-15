import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, Image, ScrollView} from 'react-native';
import { StackNavigator } from 'react-navigation';
import LoginScreen from './screens/Login';
import LandingPage from './screens/LandingPage';
import ViewDog from './screens/ViewDog';
import AddDog from './screens/AddDog';




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
 Login: { screen: LoginScreen },
 LandingPage: { screen: LandingPage },
 ViewDog: {screen: ViewDog},
 AddDog: {screen: AddDog},
},{headerMode: "none"});
