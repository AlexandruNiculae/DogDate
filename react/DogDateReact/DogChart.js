
import React, { Component } from 'react'
import { Platform, StyleSheet, Text, View, Image, ScrollView,Alert} from 'react-native';
import { BarChart } from 'react-native-svg-charts';

export default class DogChart extends Component {
	constructor(props) {
		super(props);
	}

	render(){
		const {navigate} = this.props.navigation;
		const data    = [ 50, 10, 40, 95, -4, -24, 85, 91, 35, 53, -53, 24, 50, -20, -80 ];
      const barData = [
         {
             values: data,
				 positive: {
                    fill: 'rgb(134, 65, 244)',
                },
                negative: {
                    fill: 'rgba(134, 65, 244, 0.2)',
                },
         },
      ];
		return(
			<ScrollView>
				<BarChart
					 style={ { height: 200 } }
					 data={ barData }
					 contentInset={ { top: 30, bottom: 30 } }
				/>
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
