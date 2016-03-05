/*
 * @flow weak
 * @author Junjun Deng
 */

import SQLite from 'react-native-sqlite-storage';
SQLite.DEBUG(true);
SQLite.enablePromise(true);

const openDatabase = SQLite.openDatabase;

export const ALL_TASK_SQL = 'SELECT * FROM tasks';
export const CREATE_TABLE_SQL = 'CREATE TABLE IF NOT EXISTS tasks (id REAL UNIQUE, text TEXT)';
export const ADD_TASK_SQL: string = 'INSERT INTO tasks (id, text) values (?, ?)';
export const DELETE_TASK_SQL: string = 'DELETE FROM tasks WHERE id=?';

type Task = {
  id: string,
  text: string
}

//type Database = {transaction: (tx: any) => void}

export async function all(transaction: SQLTransaction) {
  return transaction.executeSql(ALL_TASK_SQL, [])
}

export const add = (transaction: SQLTransaction) => async (task: Task): Promise => {
  return transaction.executeSql(ADD_TASK_SQL, [task.id, task.text])
};

export const remove = (transaction: SQLTransaction) => (id: string) => {
  transaction.executeSql(DELETE_TASK_SQL, [id]);
};

export const create = (transaction: SQLTransaction) => {
  transaction.executeSql(CREATE_TABLE_SQL, []);
};

export const init = async () => {
  let database = await openDatabase('todos', '1.0', 'todo list example db', 2 * 1024 * 1024);
  database.transaction(create);
  return database;
};

export const test = async () => {
  let db = await init();
  db.transaction((tx) => {
    for (i of Array(7).keys()) {
      add(tx)(
        {id: i, text: i}
      );
    }
    remove(tx)('0');
  })
};