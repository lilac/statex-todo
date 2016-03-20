//
//  AddTaskViewController.swift
//  ToDoApp
//
//  Created by James Deng on 12/03/2016.
//  Copyright © 2016 Rewen. All rights reserved.
//

import Foundation
import UIKit

class AddTaskViewController: UIViewController {
  
  @IBOutlet var doneButton: UIBarButtonItem!
  @IBOutlet var textField: UITextField!
  
  required init?(coder aDecoder: NSCoder) {
    super.init(coder: aDecoder)
  }
  
  @IBAction func done(sender: UIBarButtonItem) {
    let delegate = AppDelegate.getDelegate()
    let task = Task(title: self.textField.text!)

    delegate.bridge!.eventDispatcher.sendAppEventWithName("task/add", body: task.dict())
    performSegueWithIdentifier("done", sender: nil)
  }
  
  override func viewDidLoad() {
    super.viewDidLoad()
  }
  
  override func didReceiveMemoryWarning() {
    super.didReceiveMemoryWarning()
  }
}