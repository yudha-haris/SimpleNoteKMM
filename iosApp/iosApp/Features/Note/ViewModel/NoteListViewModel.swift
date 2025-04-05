//
//  NoteListViewModel.swift
//  iosApp
//
//  Created by Yudha Haris Purnama on 05/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared
import RxSwift
import RxCocoa

class NoteListViewModel {
    
    private let _notes = BehaviorRelay<UiState<[Note]>>(value: UiState.loading)
    var notes: Observable<UiState<[Note]>> { _notes.asObservable() }
    
    
    private let noteUseCases: NoteUseCases
    private let disposeBag: DisposeBag = DisposeBag()
    
    
    init() {
        self.noteUseCases = Koin.shared.koinHelper.noteUseCases
    }
    
    func loadNotes() {
        self._notes.accept(.loading)
        
        Observable<[Note]>.fromSuspend {
            try await self.noteUseCases.getNotes.invoke()
        }
        .subscribe(on: ConcurrentDispatchQueueScheduler(qos: .background))
        .subscribe(
            onNext: { notes2 in
                self._notes.accept(.success(notes2))
            },
            onError: { error in
                self._notes.accept(.error(error.localizedDescription, 0))
            }
        )
        .disposed(by: disposeBag)
    }
    
    func deleteNote(id: String) {
        Single<Void>.fromSuspend {
            try await self.noteUseCases.deleteNote.invoke(id: id)
        }
        .subscribe(on: ConcurrentDispatchQueueScheduler(qos: .background))
        .subscribe(
            onSuccess: {
                self.loadNotes()
            }
        )
        .disposed(by: disposeBag)
    }
}
