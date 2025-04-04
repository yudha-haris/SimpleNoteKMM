//
//  UiState.swift
//  iosApp
//
//  Created by Yudha Haris Purnama on 04/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//

enum UiState<T> {
    case success(T)
    case error(String, Int)
    case loading
}
