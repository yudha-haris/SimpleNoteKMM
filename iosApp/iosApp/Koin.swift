//
//  Koin.swift
//  iosApp
//
//  Created by Yudha Haris Purnama on 01/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import Foundation
import shared

// A Swift-friendly singleton to access Koin components
class Koin {
    // Singleton instance
    static let shared = Koin()
    
    // Lazily initialized repository helper
    private(set) lazy var koinHelper: KoinHelper = {
        return KoinHelper()
    }()
    
    private init() {
        // Private initializer to enforce singleton pattern
    }
}
