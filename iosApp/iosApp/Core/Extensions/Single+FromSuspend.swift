//
//  Observables+FromSuspendToSingle.swift
//  iosApp
//
//  Created by Yudha Haris Purnama on 05/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//
import RxSwift
import shared

extension Single {
    static func fromSuspend<T>(
        _ suspendFunction: @escaping () async throws -> T
    ) -> Single<T> {
        return Single<T>.create { observer in
            let task = Task {
                do {
                    let result = try await suspendFunction()
                    observer(.success(result))
                } catch {
                    observer(.failure(error))
                }
            }

            return Disposables.create {
                task.cancel()
            }
        }
    }

}
