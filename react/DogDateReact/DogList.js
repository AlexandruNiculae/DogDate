import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, Linking, Image, ScrollView, Alert, AsyncStorage} from 'react-native';
import { Header, Icon, Button, List, ListItem } from 'react-native-elements';
import { MenuContext, Menu, MenuOptions, MenuOption, MenuTrigger } from 'react-native-popup-menu';

export default class DogList extends Component<{}> {
  constructor(props){
    super(props);
    this.state = {
      emailTo: "alexandru.niculae13@yahoo.com",
      //allDogs: [{name: 'Martha', race: 'Dobberman', personality: 'Calm', age: '3'}, {name: 'Arnold', race: 'Beagle', personality: 'Agressive', age: '2'}, {name: 'Steve', race: 'Dalmatian', personality: 'Clumsy', age: '6'}]
		allDogs: [],
    }
    screen: DogList;
  }

  static navigationOptions = {
    title: 'Home',
  };

  componentWillMount() {
    if(this.props.navigation.state.params){
		var op = this.props.navigation.state.params.action;
		var name = this.props.navigation.state.params.name;
		var race = this.props.navigation.state.params.race;
		var pers = this.props.navigation.state.params.pers;
		var age = this.props.navigation.state.params.age;
		var dog = { name: name, race: race, personality: pers, age: age};
		if( op == "add"){
			this.addDog(dog);
		}
		if( op == "del"){
			this.delDog(dog);
		}
		if(op == "upd"){
			this.updDog(dog);
		}
    }
  }

  componentDidMount(){
	  this.loadMyData();
  }

  componentWillUnmount(){
  		this.saveMyData();
  }

  async saveMyData(){
	  try {
		  await AsyncStorage.setItem('@dogdatereactdb', JSON.stringify(this.state.allDogs));
		  console.log('Saved ----',JSON.stringify(this.state.allDogs));
	  } catch (error) {
		  this.doAlert('Error','An error occured while trying to save data!');
	   }
  }

  async loadMyData(){
	  try {
		  const value = await AsyncStorage.getItem('@dogdatereactdb');
		  if (value !== null){
			 var old = JSON.parse(value);
			 var temp = this.state.allDogs.slice();
			 var newDogs = temp.concat(old);
			 this.setState({allDogs:newDogs});
		  }
		  console.log('Loaded ----',JSON.parse(value));
	  } catch (error) {
	  		this.doAlert('Error','An error occured while trying to load data!');
	  }
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

  addDog(dog){
	  var newDogs = this.state.allDogs.slice();
	  newDogs.push(dog);
	  this.setState({allDogs:newDogs});
  }

  delDog(dog){
	  var temp = this.state.allDogs.slice();
	  var newDogs = temp.filter(function(el){
		  return el.name !== dog.name && el.race !== dog.race && el.personality !== dog.personality && el.age !== dog.age;
	  });
	  this.setState({allDogs:newDogs})
  }

  updDog(dog){
	  var newDogs = this.state.allDogs.slice();
	  for (var i in newDogs) {
	     if (newDogs[i].name == dog.name) {
	        newDogs[i].race = dog.race;
			  newDogs[i].personality = dog.personality;
			  newDogs[i].age = dog.age;
	        break; //Stop this loop, we found it!
	     }
   	}
	 this.setState({allDogs:newDogs})
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
					 <MenuOption onSelect={() => navigate('Stats')} text='Statistics'/>
              </MenuOptions>
            </Menu>
          }
        />
        <ScrollView>
          <List>
          {
            this.state.allDogs.map((dog, i) => (
              <ListItem key={i} title={dog.name +" ,"+ dog.age + " years old"}
				  subtitle={ dog.race + " " + dog.personality}
				  onPress={() => navigate('ViewDog', {name: dog.name, race: dog.race, pers: dog.personality, age: dog.age})}
				  />
            ))
          }
          </List>
        </ScrollView>
      </MenuContext>
    );}
}
