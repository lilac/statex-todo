//
//  Store.swift
//  StatexTodo
//
//  Created by James Deng on 12/03/2016.
//  Copyright © 2016 Rewen. All rights reserved.
//

import UIKit
import SQLite

class TaskStore {
  // NSString *docs = [NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES) objectAtIndex: 0];
  static let docDir: String = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true)[0];

  static let dbPath = (docDir as NSString).stringByAppendingPathComponent("todos")
  /*
  typealias SqlitePtr = UnsafeMutablePointer<sqlite3>;
  
  static func open(path: NSString) -> SqlitePtr {
    var db: SqlitePtr;
    let name = path.UTF8String;
    if sqlite3_open(name, &db) != SQLITE_OK {
      return nil
    }
    else {
      return db;
    }
  }
  */
  
  static func all() throws -> AnySequence<Row> {
    let db = try Connection(dbPath)
    let tasks = Table("tasks")
    
    return try db.prepare(tasks)
  }
}
