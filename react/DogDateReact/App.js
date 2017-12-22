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
