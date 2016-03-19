//
//  AddTaskViewController.swift
//  ToDoApp
//
//  Created by James Deng on 12/03/2016.
//  Copyright © 2016 Facebook. All rights reserved.
//

import Foundation
import UIKit

class AddTaskViewController: UIViewController {
  var todoItem: Task = Task(title: "")
  let store = TaskStore.new()
  
  @IBOutlet var doneButton: UIBarButtonItem!
  @IBOutlet var textField: UITextField!
  
  required init?(coder aDecoder: NSCoder) {
    super.init(coder: aDecoder)
  }
  
  @IBAction func done(sender: UIBarButtonItem) {
    do {
      try store?.add(Task(title: self.textField.text!))
    } catch _ {
      print("Add task error")
    }
    performSegueWithIdentifier("done", sender: nil)
  }
  
  override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject!) {
    if (self.textField.text != "") {
      self.todoItem = Task(title: self.textField.text!)
    }
  }
  
  override func viewDidLoad() {
    super.viewDidLoad()
  }
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
  }
}