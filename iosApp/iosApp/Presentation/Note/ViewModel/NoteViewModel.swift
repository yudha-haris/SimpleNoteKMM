//
//  NoteViewModel.swift
//  iosApp
//
//  Created by Yudha Haris Purnama on 30/03/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import shared

class NoteViewModel: ObservableObject {
    @Published var notes: [Note] = []
    
    private let noteUseCases: NoteUseCases
    
    init() {
        self.noteUseCases = Koin.shared.koinHelper.noteUseCases
    }
    
    func loadNotes(){
        notes = noteUseCases.getNotes.invoke()
    }
    
    func addNote(title: String, content: String) {
        var note = Note(id: UUID().uuidString, title: title, content: content)
        noteUseCases.addNote.invoke(note: note)
        loadNotes()
    }
    func deleteNote(id: String) {
        noteUseCases.deleteNote.invoke(id: id)
        loadNotes()
    }
}
