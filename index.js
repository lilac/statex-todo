/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  NativeAppEventEmitter,
  NativeModules
} from 'react-native';

import StateX from 'react-native-statex';

import * as Todo from './todo';
import * as Utils from './utils';

const NotificationManager = NativeModules.NotificationManager;

let dbPromise = Todo.init();

dbPromise.then( () => {
    //NotificationManager.postNotification("/dbInitialized");
    StateX.setItem("initialized", "true");
});

async function add(task) {
  let db = await dbPromise;
  await db.transaction(Todo.add(task));
  NotificationManager.postNotification("/task");
}

async function remove(id) {
  let db = await dbPromise;
  await db.transaction(Todo.remove(id));
  NotificationManager.postNotification(`/task/$id`);
  NotificationManager.postNotification("/task");
}

NativeAppEventEmitter.addListener("task/add", add);
NativeAppEventEmitter.addListener("task/delete", remove);
