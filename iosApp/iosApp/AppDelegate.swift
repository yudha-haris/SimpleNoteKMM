//
//  AppDeleegate.swift
//  iosApp
//
//  Created by Yudha Haris Purnama on 01/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import UIKit
import shared

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {
    var window: UIWindow?
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
        
        KoinHelperKt.doInitKoin()
        let storyboard = UIStoryboard(name: "main", bundle: nil)
        let rootViewController = storyboard.instantiateViewController(identifier: "NoteViewController")
        
        window = UIWindow(frame: UIScreen.main.bounds)
        window?.rootViewController = rootViewController
        window?.makeKeyAndVisible()
        return true
    }
}
