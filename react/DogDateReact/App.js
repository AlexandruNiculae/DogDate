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
},{headerMode: "none"});
