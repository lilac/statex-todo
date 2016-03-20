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
  static let taskKey = "/task"
  static let DELETE_TASK_ACTION = "task/delete"
  var todoItems: [Task] = []
  let store = TaskStore.new()
  
  @IBAction func addTaskDone(segue: UIStoryboardSegue) {
    
  }
  
  @IBAction func addTaskCanceled(segue: UIStoryboardSegue) {
    
  }
  
  func loadInitialData() {
    if let r = store?.all() {
      todoItems = r;
    }
  }
  
  func reload() {
    loadInitialData()
    self.tableView.reloadData()
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
  
  override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) {
    if editingStyle == .Delete {
      let task = todoItems[indexPath.row]
      AppDelegate.eventDispatcher?.sendAppEventWithName(TaskTableViewController.DELETE_TASK_ACTION, body: task.id)
      
      tableView.beginUpdates()
      todoItems.removeAtIndex(indexPath.row)
      // Note that indexPath is wrapped in an array:  [indexPath]
      tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Automatic)
      tableView.endUpdates()
    }
  }
  
  override func viewDidLoad() {
    super.viewDidLoad()
    loadInitialData()
    NSNotificationCenter.defaultCenter().addObserver(self, selector: "reload", name: TaskTableViewController.taskKey, object: nil)
  }
  
  override func viewWillDisappear(animated: Bool) {
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
  
  deinit {
    NSNotificationCenter.defaultCenter().removeObserver(self)
  }
}