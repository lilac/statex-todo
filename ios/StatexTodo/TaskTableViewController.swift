//
//  TaskTableViewController.swift
//  ToDoApp
//
//  Created by James Deng on 12/03/2016.
//  Copyright © 2016 Rewen.co. All rights reserved.
//

import Foundation

import UIKit
import SQLite

class TaskTableViewController: UITableViewController {
  
  var todoItems: [Task] = []
  let store = TaskStore.new()
  
  @IBAction func addTaskDone(segue: UIStoryboardSegue) {
//    let source = segue.sourceViewController as! AddTaskViewController
//    let todoItem:Task = source.todoItem
//    
//    if todoItem.title != "" {
//      do {
//        if try store?.add(todoItem) != nil {
//          //self.todoItems.append(todoItem)
//          self.loadInitialData()
//          self.tableView.reloadData()
//        }
//      } catch _ {
//        print("Save error")
//      }
//    }
    self.loadInitialData()
    self.tableView.reloadData()
  }
  
  @IBAction func addTaskCanceled(segue: UIStoryboardSegue) {
    
  }
  
  func loadInitialData() {
    if let r = store?.all() {
      todoItems = r;
    }
  }
  
  override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
    tableView.deselectRowAtIndexPath(indexPath, animated: false)
    
    let tappedItem = todoItems[indexPath.row] as Task
    tappedItem.completed = !tappedItem.completed
    
    tableView.reloadRowsAtIndexPaths([indexPath], withRowAnimation: UITableViewRowAnimation.None)
    
  }
  
  override func tableView(tableView: UITableView?, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
    let foo = tableView!.dequeueReusableCellWithIdentifier("ListPrototypeCell") as UITableViewCell?
    let tempCell = foo!
    let todoItem = todoItems[indexPath.row]
    
    // Downcast from UILabel? to UILabel
    let cell = tempCell.textLabel as UILabel!
    cell.text = todoItem.title
    
    if (todoItem.completed) {
      tempCell.accessoryType = UITableViewCellAccessoryType.Checkmark;
    } else {
      tempCell.accessoryType = UITableViewCellAccessoryType.None;
    }
    
    return tempCell
  }
  
  override func viewDidLoad() {
    super.viewDidLoad()
    loadInitialData()
  }
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
  }
  
  override func numberOfSectionsInTableView(tableView: UITableView) -> Int {
    return 1
  }
  
  override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
    return todoItems.count
  }
}