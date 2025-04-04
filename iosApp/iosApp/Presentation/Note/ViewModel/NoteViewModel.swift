//
//  NoteViewModel.swift
//  iosApp
//
//  Created by Yudha Haris Purnama on 30/03/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared
import RxSwift
import RxCocoa

class NoteViewModel {
    
    private let _notes = BehaviorRelay<UiState<[Note]>>(value: UiState.loading)
    var notes: Observable<UiState<[Note]>> { _notes.asObservable() }
    
    
    private let noteUseCases: NoteUseCases
    private let disposeBag: DisposeBag = DisposeBag()
    
    
    init() {
        self.noteUseCases = Koin.shared.koinHelper.noteUseCases
    }
    
    func loadNotes() {
        self._notes.accept(.loading)
        
        Observable.create { observer in
            Task {
                do {
                    let data = try await self.noteUseCases.getNotes.invoke()
                    observer.onNext(UiState.success(data))
                    observer.onCompleted()
                } catch {
                    observer.onNext(UiState.error(error.localizedDescription, 0))
                }
            }
            return Disposables.create()
        }
        .observe(on: MainScheduler.instance)
        .subscribe{ [weak self] notes2 in
            self?._notes.accept(notes2)
        }
        .disposed(by: disposeBag)
    }
    
    func addNote(title: String, content: String) {
        let note = Note(id: "0", title: title, content: content)
        
        Observable<Any>.create { observer in
            let task =
                Task {
                    do {
                        let _: Void = try await self.noteUseCases.addNote.invoke(note: note)
                        observer.onCompleted()
                    } catch {
                        observer.onNext(UiState<Any>.error(error.localizedDescription, 0))
                    }
                }
            return Disposables.create {
                task.cancel()
            }
        }
        .observe(on: MainScheduler.instance)
        .subscribe(
            onCompleted: { 
                self.loadNotes()
            }
        )
        .disposed(by: disposeBag)
    }
}
