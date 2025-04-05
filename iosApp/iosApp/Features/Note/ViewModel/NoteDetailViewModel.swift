//
//  NoteDetailViewModel.swift
//  iosApp
//
//  Created by Yudha Haris Purnama on 05/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared
import RxSwift
import RxCocoa

class NoteDetailViewModel {
    
    private let _note = BehaviorRelay<UiState<Note>>(value: UiState.loading)
    var note: Observable<UiState<Note>> { _note.asObservable() }
    
    private let _onSuccess: PublishSubject<Void> = PublishSubject()
    var onSuccess: Single<Void> { _onSuccess.asSingle() }
    
    
    private let noteUseCases: NoteUseCases
    private let disposeBag: DisposeBag = DisposeBag()
    
    
    init() {
        self.noteUseCases = Koin.shared.koinHelper.noteUseCases
    }
    
    func loadNote(id: String) {
        self._note.accept(.loading)
        
        Observable<Note>.fromSuspend {
            try await self.noteUseCases.getNoteById.invoke(id: id)!
        }
        .subscribe(on: ConcurrentDispatchQueueScheduler(qos: .background))
        .subscribe(
            onNext: { notes2 in
                self._note.accept(.success(notes2))
            },
            onError: { error in
                self._note.accept(.error(error.localizedDescription, 0))
            }
        )
        .disposed(by: disposeBag)
    }
    
    func addNote(title: String, content: String) {
        let newNote = Note(id: "0", title: title, content: content)
        self._note.accept(.loading)
        Single<Void>.fromSuspend {
            try await self.noteUseCases.addNote.invoke(note: newNote)
        }
        .subscribe(on: ConcurrentDispatchQueueScheduler(qos: .background))
        .subscribe(
            onSuccess: {
                self._onSuccess.onCompleted()
            },
            onFailure: { it in
                self._onSuccess.onError(it)
            }
        )
        .disposed(by: disposeBag)
    }
}
