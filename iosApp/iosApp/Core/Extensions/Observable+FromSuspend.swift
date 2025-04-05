//
//  Observables+FromSuspend.swift
//  iosApp
//
//  Created by Yudha Haris Purnama on 04/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import RxSwift
import shared

extension Observable {
    static func fromSuspend<T>(
        _ suspendFunction: @escaping () async throws -> T
    ) -> Observable<T> {
        return Observable<T>.create { observer in
            let task = Task {
                do {
                    let result = try await suspendFunction()
                    observer.onNext(result)
                    observer.onCompleted()
                } catch {
                    observer.onError(error)
                }
            }

            return Disposables.create {
                task.cancel()
            }
        }
    }
}

