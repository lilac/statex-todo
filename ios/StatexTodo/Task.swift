//
//  Task.swift
//  ToDoApp
//
//  Created by James Deng on 12/03/2016.
//  Copyright © 2016 Rewen. All rights reserved.
//

import UIKit

class Task: NSObject {
  let id: Int
  let title: String
  var completed: Bool
  
//  init(title: String, completed: Bool = false) {
//    self.title = title
//    self.completed = completed
//  }
  
  init(id: Int = -1, title: String, completed: Bool = false) {
    self.id = id
    self.title = title
    self.completed = completed
  }
  
  func dict() -> [String: AnyObject] {
    return self.dictionaryWithValuesForKeys(["title", "completed"])
  }
}