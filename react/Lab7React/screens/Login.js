import React from 'react';
import { StyleSheet,
  Text,
  View,
  ScrollView,
  ListView,
  Button,
  Linking,
Alert} from 'react-native';
import { List, ListItem, FormLabel, FormInput, } from 'react-native-elements';


export default class LoginScreen extends React.Component {
 constructor(props) {
	super(props);
	this.state = {
	  name : '',
	  password : '',
	  users : [],
	}

	promise = getUsersFromApi();
	promise.then(data => {
	  for (var i = 0; i < JSON.parse(data).length; ++i) {
	  this.state.users.push(JSON.parse(data)[i]);
	//  console.log(JSON.parse(data)[i]);
	}
	});
	console.log(this.state.users);

//     var ws = new WebSocket('ws://192.168.100.2:3000');
//     ws.onopen = () => {
//   // connection opened
//   ws.send('something'); // send a message
// };
//     ws.onmessage = (e) => {
//       // a message was received
//     this.doAlert("User " + JSON.parse(e.data).name + " is now online!");
//   };

} // constructor

static navigationOptions = {
 title : 'login',
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


 sendEmail = () => {
	Linking.openURL('mailto:' + 'alexandru.niculae13@yahoo.com' +
	'?&subject=' + 'mail' +
	'&body=' + 'Hello from the app');
 }

 login = () => {
	console.log(this.state.users[0].name);
	const { navigate } = this.props.navigation;
	if (this.state.name == '') {
	  this.doAlert('Name must not be empty!')
	  return;
	}
	if (this.state.password == '') {
	  this.doAlert('Password is requiered!')
	}

	promise = login(this.state.name, this.state.password);
	promise.then(data => {
	  console.log(data.message);
	  if (data.message === 'false') {
		 this.doAlert("Wrong username or passowrd!");
	}
	  if (data.message === 'true') {
		 //this.doAlert("login succesful");
		 navigate('LandingPage', { user : this.state.name});

	}
	});

 }

 render() {
	//console.log(this.state.users);

	return (
	  <View style = {styles.container}>
	  <Text style = {styles.textStyle}>Dog Date</Text>
	  <FormLabel>Username:</FormLabel>
			   <FormInput onChangeText={(name) => this.setState({name})} value = {this.state.name} editable = {true}/>
		  <FormLabel>Password:</FormLabel>
					<FormInput secureTextEntry={true}  onChangeText={(password) => this.setState({password})} value = {this.state.password} editable = {true}/>
			 <Button
			   onPress = {this.login}
			   title="Login"
			   color="blue"
			   accessibilityLabel=""
			 />
	  <Button
		 onPress = {this.sendEmail}
		 title="Send email"
		 color="#841584"
		 accessibilityLabel=""
	  />
	  </View>
	);
 }
}
async function login(name ,password) {
 try {
	let response = await fetch(
	  'http://192.168.0.107:3000/login', {
		 method: 'GET',
		 headers: {
			username : name,
			password : password,
		 }
	  }
	);
	let responseJson = await response.json();
	return responseJson;
 }catch (error) {
	console.error(error);
 }
}

async function getUsersFromApi() {
 try {
	let response = await fetch(
	  'http://192.168.0.107:3000/users'
	);
	let responseJson = await response.json();
	return JSON.stringify(responseJson);

 } catch (error) {
	console.error(error);
 }
}

const styles = StyleSheet.create({
 container: {

 },
 textStyle: {
	paddingVertical: 20,
	fontWeight: 'bold',
	color: '#141823',
	textAlign: 'center'

 },
 contentContainer : {
	marginBottom: 200,
	marginTop: 0,
	flexGrow: 1
 }
});
