//
//  NoteDetailViewController.swift
//  iosApp
//
//  Created by Yudha Haris Purnama on 05/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import UIKit
import shared
import RxSwift
import RxCocoa

class NoteDetailViewController: UIViewController {
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var contentTextField: UITextField!
    @IBOutlet weak var saveButton: UIButton!
    
    private let disposeBag: DisposeBag = DisposeBag()
    private let viewModel: NoteDetailViewModel! = NoteDetailViewModel()
    
    private var note: UiState<Note> = UiState.loading
    
    var noteId: String = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()
        bind()
        viewModel.loadNote(id: noteId);
    }
    
    private func bind() {
        saveButton.addTarget(self, action: #selector(save), for: .touchUpInside)
        
        viewModel.note.observe(on: MainScheduler.instance)
            .subscribe(onNext: { [weak self] data in
                switch data {
                case .loading:
                    self?.saveButton.isEnabled = false
                case .success(let value):
                    self?.titleTextField.text = value.title
                    self?.contentTextField.text = value.content
                    self?.saveButton.isEnabled = true
                case .error(_, _):
                    break
                }
            })
            .disposed(by: disposeBag)
        
        viewModel.onSuccess.observe(on: MainScheduler.instance)
            .subscribe(onSuccess: {
                
            })
            .disposed(by: disposeBag)
    }
    
    @objc func save() {
        guard let title = titleTextField.text, !title.isEmpty,
              let content = contentTextField.text, !content.isEmpty else {
            return
        }
        viewModel.addNote(title: title, content: content)
        
    }
}
