//
//  AppDelegate.swift
//  PrediCiclo
//
//  Created by Jason Sa on 1/9/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit

import CoreData
import Alamofire

let Valida_Status = AlamofireStatus()
let preferencias = Preferences()
let userName = "ck_5ecb44828673b02bbfaa886ad152e43f923c81f1"
let passwd = "cs_e3fb7fad4e633978005d39099d89c8118803e187"

let manager : SessionManager = {
    let config = URLSessionConfiguration.default
    config.timeoutIntervalForRequest = 45
    config.timeoutIntervalForResource = 45
    let mgr = Alamofire.SessionManager(configuration: config)
    
    return mgr
}()

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {


    /*Time for launchScreen */
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        Thread.sleep(forTimeInterval: 2.0)
        
        
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
    }
    
    // MARK: - Core Data stack

    lazy var persistentContainer: NSPersistentContainer = {
        let container = NSPersistentContainer(name: "Prediciclo")
        container.loadPersistentStores(completionHandler: { (storeDescription, error) in
            if let error = error as NSError? {
                fatalError("Unresolved error \(error), \(error.userInfo)")
            }
        })
        return container
    }()

    // MARK: - Core Data Saving support

    func saveContext () {
        let context = persistentContainer.viewContext
        if context.hasChanges {
            do {
                try context.save()
            } catch {
                let nserror = error as NSError
                fatalError("Unresolved error \(nserror), \(nserror.userInfo)")
            }
        }
    }
}

