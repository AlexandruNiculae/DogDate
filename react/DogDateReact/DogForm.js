import { Platform, StyleSheet, Text, View, Image, ScrollView} from 'react-native';
import React, { Component } from 'react';
import { FormLabel, FormInput, Button } from 'react-native-elements'

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
        <FormInput onChangeText={(dogAge) => this.setState({dogAge})}/>
        <Button icon={{name: 'add'}} onPress={() => navigate('Home', {name: this.state.dogName, race: this.state.dogRace, pers: this.state.dogPers, age: this.state.dogAge})}/>
      </ScrollView>
    );}
}
