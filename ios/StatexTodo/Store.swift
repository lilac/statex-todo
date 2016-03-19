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
  
  func all() -> [Task] {
    let tasks = Table("tasks")
    let text = Expression<String>("text")
    
    if let rows = try? db.prepare(tasks) {
      return rows.lazy.map({ row in
        Task(title: row[text])
      })
    }
    return []
  }
}
