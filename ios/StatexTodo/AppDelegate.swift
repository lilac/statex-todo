//
//  AppDelegate.swift
//  StatexTodo
//
//  Created by James Deng on 19/03/2016.
//  Copyright © 2016 Rewen. All rights reserved.
//

import UIKit

@UIApplicationMain
public class AppDelegate: UIResponder, UIApplicationDelegate {
  public var window: UIWindow?
  var bridge: RCTBridge?
  
  class func getDelegate() -> AppDelegate {
    return UIApplication.sharedApplication().delegate as! AppDelegate
  }
  
  static let eventDispatcher = AppDelegate.getDelegate().bridge?.eventDispatcher

  public func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject : AnyObject]?) -> Bool {
    /**
    * Loading JavaScript code - uncomment the one you want.
    *
    * OPTION 1
    * Load from development server. Start the server from the repository root:
    *
    * $ npm start
    *
    * To run on device, change `localhost` to the IP address of your computer
    * (you can get this by typing `ifconfig` into the terminal and selecting the
    * `inet` value under `en0:`) and make sure your computer and iOS device are
    * on the same Wi-Fi network.
    */
    
    let url = NSURL(string: "http://localhost:8081/index.bundle?platform=ios&dev=true");
    
    /**
    * OPTION 2
    * Load from pre-bundled file on disk. The static bundle is automatically
    * generated by "Bundle React Native code and images" build step.
    */
    
    //   jsCodeLocation = [[NSBundle mainBundle] URLForResource:@"main" withExtension:@"jsbundle"];
    bridge = RCTBridge(bundleURL: url, moduleProvider: nil, launchOptions: launchOptions)
    return true
  }
}