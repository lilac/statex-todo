/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 */
'use strict';
import React, {
  NativeAppEventEmitter,
  NativeModules
} from 'react-native';

import * as Todo from './todo';
import * as Utils from './utils';

const NotificationManager = NativeModules.NotificationManager;
let dbPromise = Todo.init();

async function add(task) {
  let db = await dbPromise;
  await db.transaction(Todo.add(task));
  NotificationManager.postNotification("/task");
}

NativeAppEventEmitter.addListener("task/add", add);
