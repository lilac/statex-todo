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
  let tasks = Table("tasks")
  let title = Expression<String>("title")
  let id = Expression<Int>("id")
  let status = Expression<Int>("status")
  
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
    if let rows = try? db.prepare(tasks) {
      return rows.lazy.map({ row in
        Task(id: row[id], title: row[title], completed: row[status] > 0)
      })
    }
    return []
  }
  
  func add(task: Task) throws {
    let stm = tasks.insert(or: .Replace, title <- task.title)
    let id = try db.run(stm)
    print("Insertion success with row id: \(id)")
  }
}
