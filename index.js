/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  AppRegistry,
  Component,
  StyleSheet,
  Text,
  View,
  ListView
} from 'react-native';

//import 'react-native-sqlite-storage/test/index.ios.promise';
import * as Todo from './todo';
import * as Utils from './utils';

//Todo.test();

class StatexTodo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      dataSource: new ListView.DataSource({
        rowHasChanged: (row1, row2) => row1 !== row2
      }),
      loaded: false
    };
  }

  componentDidMount() {
    return this.update();
  }

  async update() {
    let db = await Todo.init();
    db.transaction(async tx => {
      let [_, results] = await Todo.all(tx);
      this.setState({
        dataSource: /*Object.assign(this.state.dataSource, {
          getRowData: (data, sectionId, rowId) => results.rows.item(rowId)
        }),*/
        this.state.dataSource.cloneWithRows(Utils.sqlRowsToArray(results.rows)),
        loaded: true
      })
    });
  }

  renderLoadingView() {
    return (
      <View style={styles.container}>
        <Text>
          Loading...
        </Text>
      </View>
    );
  }

  render() {
    if (!this.state.loaded) {
      return this.renderLoadingView();
    }
    else return (
      <ListView
        dataSource={this.state.dataSource}
        renderRow={(rowData) => <Text style={styles.task}>{rowData.text}</Text>}
        renderSeparator={(sectionID, rowID) => <View key={`${sectionID}-${rowID}`} style={styles.separator} />}
        style={styles.listView}
      />
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  task: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  separator: {
    height: 1,
    backgroundColor: '#CCCCCC',
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('StatexTodo', () => StatexTodo);
