/*
 * @flow weak
 * @author Junjun Deng
 */

import SQLite from 'react-native-sqlite-storage';
SQLite.DEBUG(true);
SQLite.enablePromise(true);

const openDatabase = SQLite.openDatabase;

export const ALL_TASK_SQL = 'SELECT * FROM tasks';
export const CREATE_TABLE_SQL = 'CREATE TABLE IF NOT EXISTS tasks (id INTEGER PRIMARY KEY, title TEXT, status INTEGER DEFAULT 0)';
export const ADD_TASK_SQL: string = 'INSERT INTO tasks (title) values (?)';
export const DELETE_TASK_SQL: string = 'DELETE FROM tasks WHERE id=?';

type Task = {
  id: ?string,
  title: string,
  status: number
}

//type Database = {transaction: (tx: any) => void}

export function all(db: Database) {
  // Note: The behaviour of db.executeSql is weird, as it wraps results in a list. Thus, we need to unwrap it.
  return db.executeSql(ALL_TASK_SQL, []).then(([res]) => res)
}

/**
 * Add a task to the table
 * @param task
 */
export const add = (task: Task) => (transaction: SQLTransaction): Promise => {
  return transaction.executeSql(ADD_TASK_SQL, [task.title])
};

export const remove = (transaction: SQLTransaction) => (id: string) => {
  transaction.executeSql(DELETE_TASK_SQL, [id]);
};

export const create = (transaction: SQLTransaction) => {
  transaction.executeSql(CREATE_TABLE_SQL, []);
};

export const init = async (): Promise<Database> => {
  let database = await openDatabase('todos', '1.0', 'todo list example db', 2 * 1024 * 1024);
  await database.transaction(create);
  return database;
};

export const test = async () => {
  let db = await init();
  db.transaction((tx) => {
    for (i of Array(7).keys()) {
      add(tx)(
        {id: i, title: i}
      );
    }
    remove(tx)('0');
  })
};