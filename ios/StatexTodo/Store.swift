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
  let db: Connection;
  
  static func new() -> TaskStore? {
    let docDir: String = NSSearchPathForDirectoriesInDomains(NSSearchPathDirectory.DocumentDirectory, NSSearchPathDomainMask.UserDomainMask, true)[0];
    
    let dbPath = (docDir as NSString).stringByAppendingPathComponent("todos")
    return try? TaskStore(con: Connection(dbPath))
  }
  
  init(con: Connection) {
    db = con
  }
  
  func all() throws -> AnySequence<Row> {
    let tasks = Table("tasks")
    
    return try db.prepare(tasks)
  }
}
