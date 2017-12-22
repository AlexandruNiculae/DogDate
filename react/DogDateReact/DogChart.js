
import React, { Component } from 'react'
import { Platform, StyleSheet, Text, View, Image, ScrollView,Alert} from 'react-native';

export default class DogChart extends Component {
	constructor(props) {
		super(props);
	}

	render(){
		const {navigate} = this.props.navigation;

		return(
			<ScrollView>

			</ScrollView>
		)
	}
}


const styles = StyleSheet.create({
	container: {
		flex: 1,
		justifyContent: 'center',
		alignItems: 'center',
		backgroundColor: 'white',
	},
	chart: {
		width: 200,
		height: 200,
	},
});
