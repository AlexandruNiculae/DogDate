import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, Linking, Image, ScrollView} from 'react-native';
import { Header, Icon, Button, List, ListItem } from 'react-native-elements';
import { MenuContext, Menu, MenuOptions, MenuOption, MenuTrigger } from 'react-native-popup-menu';

export default class DogList extends Component<{}> {
  constructor(props){
    super(props);
    this.state = {
      emailTo: "alexandru.niculae13@yahoo.com",
      allDogs: [{name: 'Martha', race: 'Dobberman', personality: 'Calm', age: '3'}, {name: 'Arnold', race: 'Beagle', personality: 'Agressive', age: '2'}, {name: 'Steve', race: 'Dalmatian', personality: 'Clumsy', age: '6'}]
    }
    screen: DogList;

  }

  static navigationOptions = {
    title: 'Home',
  };

  componentWillMount() {
    if(this.props.navigation.state.params){
      var name = this.props.navigation.state.params.name;
      var race = this.props.navigation.state.params.race;
      var pers = this.props.navigation.state.params.pers;
      var age = this.props.navigation.state.params.age;
      var dog = { name: name, race: race, personality: pers, age: age};
      var newDogs = this.state.allDogs.slice();
      newDogs.push(dog)
      this.setState({allDogs:newDogs})
    }
  }

  sendEmail() {
    if (this.state.emailTo === "") {
      Alert.alert("Can not have empty email!")
    }
    else {
      let emailUrl = "mailto:?to=" + this.state.emailTo
      console.log(emailUrl)
      Linking.openURL(emailUrl)
    }
  }

  render(){
    const { navigate } = this.props.navigation;
    return (
      <MenuContext>
        <Header
          centerComponent={{ text: 'Dog Date', style: { color: '#fff' } }}
          rightComponent = {
            <Menu>
              <MenuTrigger>
                <Icon name='menu' color='#fff' />
              </MenuTrigger>
              <MenuOptions>
                <MenuOption onSelect={() => navigate('AddDog')} text='Add a Dog' />
                <MenuOption onSelect={() => this.sendEmail()} text='Send me an email'/>
              </MenuOptions>
            </Menu>
          }
        />
        <ScrollView>
          <List>
          {
            this.state.allDogs.map((dog, i) => (
              <ListItem key={i} title={dog.name +" ,"+ dog.age + " years old"} subtitle={ dog.race + " " + dog.personality} />
            ))
          }
          </List>
        </ScrollView>
      </MenuContext>
    );}
}
