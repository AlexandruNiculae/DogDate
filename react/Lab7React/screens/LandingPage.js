import React from 'react';
import { StyleSheet,
  Text,
  View,
  ScrollView,
  ListView,
  Button,
  Linking,
Alert} from 'react-native';
import { List, ListItem , FormLabel, FormInput, TouchableOpacity } from 'react-native-elements';



export default class LandingPage extends React.Component {
 constructor(props) {
	super(props);

	const ds = new ListView.DataSource({
			  rowHasChanged: (r1, r2) => r1 !== r2
		 });

	this.state = {
	  dataSource: ds.cloneWithRows(['row 1', 'row 2']),
	  loaded : false,
	  allDogs : [],
	}
	promise = getDogsFromApi();
	promise.then(data => {
	  for (var i = 0; i < JSON.parse(data).length; ++i) {
	  this.state.allDogs.push(JSON.parse(data)[i]);
	  //console.log(JSON.parse(data)[i]);
	}
	});

	var ws = new WebSocket('ws://192.168.0.107:3000');
	ws.onopen = () => {
 	ws.send('get new dogs');
};
	ws.onmessage = (e) => {
	  if (JSON.parse(e.data).message === "New Dog added!") {
		 promise = getDogsFromApi();
		 promise.then(data => {
			console.log(JSON.parse(data));
			this.setState({
			  dataSource: this.state.dataSource.cloneWithRows(JSON.parse(data)),
			  loaded: true,
			})
		 });
		 return;
	  }
	  if (this.props.navigation.state.params.user != JSON.parse(e.data).name ) {
		  this.doAlert("User " + JSON.parse(e.data).name + " is now online!");
 		}
 };

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

 componentDidMount() {
	promise = getDogsFromApi();
	promise.then(data => {
	  //console.log(JSON.parse(data));
	  this.setState({
		 dataSource: this.state.dataSource.cloneWithRows(JSON.parse(data)),
		 loaded: true,
	  })
	});
 }

	fetchData = () => {
	  promise = getDogsFromApi();
	  promise.then(data => {
		 //console.log(JSON.parse(data));
		 this.setState({
			dataSource: this.state.dataSource.cloneWithRows(JSON.parse(data)),
			loaded: true,
		 })
	  });
	}

	render() {

	  if (!this.state.loaded) {
					  return (<View style={styles.container}>
								 <Text>Please wait ... </Text>

							</View>
					  );
	  }
	  const {navigate} = this.props.navigation;
	  return (
		 <ListView style={styles.container}
			  onScroll = {() => {
				 this.setState({
					loaded:false,
				 })
				 this.fetchData();
			  }}
			  enableEmptySections={true}
			  dataSource={this.state.dataSource}
			  renderRow={(dog,i) =>
						 <View>
						 <ListItem key={i} title={dog.name +" ,"+ dog.age + " years old"}
						 subtitle={ dog.race + " " + dog.personality}
						 onPress={() => { navigate('ViewDog', {key: dog.key, name: dog.name, race: dog.race, pers: dog.personality, age: dog.age});}}
						 />
						 </View>
			  }
			  renderSeparator={(sectionID, rowID, adjacentRowHighlighted) =>
					<View key={rowID} style={{height: 1, backgroundColor: 'lightgray'}}/>
			  }
		 />
	  );
	}
}

async function getDogsFromApi() {
 try {
	let response = await fetch(
	  'http://192.168.0.107:3000/dogs'
	);
	let responseJson = await response.json();
	return JSON.stringify(responseJson);

 } catch (error) {
	console.error(error);
 }
}

const styles = StyleSheet.create({
 container: {
	flex: 1,
	marginTop: 60,
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
