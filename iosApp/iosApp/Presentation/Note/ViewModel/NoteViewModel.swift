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
        
        Observable<[Note]>.fromSuspend {
            try await self.noteUseCases.getNotes.invoke()
        }
        .observe(on: MainScheduler.instance)
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
    
    func addNote(title: String, content: String) {
        let note = Note(id: "0", title: title, content: content)
        Observable<Void>.fromSuspend {
            try await self.noteUseCases.addNote.invoke(note: note)
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
