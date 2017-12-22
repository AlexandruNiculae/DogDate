import React, { Component } from 'react';
import { Platform, StyleSheet, Text, View, Linking, Image, ScrollView, Alert, AsyncStorage} from 'react-native';
import { Header, Icon, Button, List, ListItem } from 'react-native-elements';
import { MenuContext, Menu, MenuOptions, MenuOption, MenuTrigger } from 'react-native-popup-menu';
import {allDogs} from './App';



export default class DogList extends Component<{}> {
  constructor(props){
    super(props);
    this.state = {
      emailTo: "alexandru.niculae13@yahoo.com",
      //allDogs.data: [{name: 'Martha', race: 'Dobberman', personality: 'Calm', age: '3'}, {name: 'Arnold', race: 'Beagle', personality: 'Agressive', age: '2'}, {name: 'Steve', race: 'Dalmatian', personality: 'Clumsy', age: '6'}]
		//allDogs.data: []
    }
    screen: DogList;
  }

  static navigationOptions = {
    title: 'Home',
  };

  componentWillMount() {
    if(this.props.navigation.state.params){
		//this.loadMyData();
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
  }

  componentWillUnmount(){

  }

  saveMyData(){
	  if (allDogs.data.length !== 0) {
		  var copy1 = JSON.parse(JSON.stringify(allDogs.data));
   	  console.log("sssssss",JSON.stringify(copy1),JSON.stringify(allDogs.data));
   	  var names = [];
   	  var races = [];
   	  var perss = [];
   	  var ages = [];
   	  for(var key in copy1){
			  names.push(copy1[key].name);
			  races.push(copy1[key].race);
			  perss.push(copy1[key].personality);
			  ages.push(copy1[key].age);

   	  }

   	  this.store('@db-names',names);
   	  this.store('@db-races',races);
   	  this.store('@db-perss',perss);
   	  this.store('@db-ages',ages);
	  }
  }

  async store(key,data){
	 	try {
		  await AsyncStorage.setItem(key, JSON.stringify(data));
		  console.log('Stored @',key,"----",JSON.stringify(data));
  		}
		catch (error) {
 		  this.doAlert('Error','An error occured while trying to save data!');
 	   }
  }

  async retrieve(key){
	  try {
		 const value = await AsyncStorage.getItem(key);
		 if (value !== null){
			 console.log('Retrieved @',key,"----",JSON.parse(value));
			 let data = JSON.parse(value);
			 return data;
		 }
	  }
	  catch (error) {
		 this.doAlert('Error','An error occured while trying to load data!');
	  }
  }



  async loadMyData(){
	  var newDogs = allDogs.data.slice();
	  var names = this.retrieve('@db-names');
	  var races = this.retrieve('@db-races');
	  var perss = this.retrieve('@db-perss');
	  var ages = this.retrieve('@db-ages');
	  for(var i in names){
		  var dog = { name: names[i], race: races[i], personality: perss[i], age: ages[i]};
		  newDogs.push(dog);
	  }
	  //this.setState({allDogs.data:newDogs});
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
	  //console.log(JSON.stringify(allDogs.data));
	  // var newDogs = allDogs.data.slice();
	  // newDogs.push(dog);
	  // this.setState({allDogs.data:newDogs});
	  allDogs.addDog(dog);
  }

  delDog(dog){
	 //  var temp = allDogs.data.slice();
	 //  var newDogs = temp.filter(function(el){
		//   return el.name !== dog.name && el.race !== dog.race && el.personality !== dog.personality && el.age !== dog.age;
	 //  });
	 // allDogs.data = newDogs.slice();
	 allDogs.delDog(dog);
  }

  updDog(dog){
	 //  var newDogs = allDogs.data.slice();
	 //  for (var i in newDogs) {
	 //     if (newDogs[i].name == dog.name) {
	 //        newDogs[i].race = dog.race;
		// 	  newDogs[i].personality = dog.personality;
		// 	  newDogs[i].age = dog.age;
	 //        break; //Stop this loop, we found it!
	 //     }
   	// }
	 // allDogs.data = newDogs.slice();
	 allDogs.updDog(dog);
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
                <MenuOption onSelect={() =>{navigate('AddDog');}} text='Add a Dog' />
                <MenuOption onSelect={() => this.sendEmail()} text='Send me an email'/>
					 <MenuOption onSelect={() => navigate('Stats')} text='Statistics'/>
					 <MenuOption onSelect={() => this.saveMyData()} text='Save'/>
					 <MenuOption onSelect={() => this.loadMyData()} text='Load'/>
              </MenuOptions>
            </Menu>
          }
        />
        <ScrollView>
          <List>
          {
            allDogs.getDogs().map((dog, i) => (
              <ListItem key={i} title={dog.name +" ,"+ dog.age + " years old"}
				  subtitle={ dog.race + " " + dog.personality}
				  onPress={() => { navigate('ViewDog', {name: dog.name, race: dog.race, pers: dog.personality, age: dog.age});}}
				  />
            ))
          }
          </List>
        </ScrollView>
      </MenuContext>
    );}
}
