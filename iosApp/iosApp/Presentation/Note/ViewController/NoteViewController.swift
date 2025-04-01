//
//  NoteViewController.swift
//  iosApp
//
//  Created by Yudha Haris Purnama on 01/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import UIKit
import shared

class NoteViewController: UIViewController, UITableViewDelegate {
    @IBOutlet weak var noteTableView: UITableView!
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var contentTextField: UITextField!
    @IBOutlet weak var addNoteButton: UIButton!
    
    private let viewModel: NoteViewModel = NoteViewModel()
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .white
        
        noteTableView.dataSource = self
        noteTableView.delegate = self
        
        noteTableView.register(
            UINib(nibName: "NoteTableViewCell", bundle: nil), forCellReuseIdentifier: "NoteCell")
        addNoteButton.addTarget(self, action: #selector(addNote), for: .touchUpInside)
    }
    
    @objc func addNote() {
        guard let title = titleTextField.text, !title.isEmpty,
              let content = contentTextField.text, !content.isEmpty else {
            return
        }
         viewModel.addNote(title: title, content: content)
        
        titleTextField.text = ""
        contentTextField.text = ""
        
        noteTableView.reloadData()
    }
}

extension NoteViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        if let cell = tableView.dequeueReusableCell(
          withIdentifier: "NoteCell",
          for: indexPath
        ) as? NoteTableViewCell {
     
            let note = viewModel.notes[indexPath.row]
            cell.configure(note: note)
            return cell
        } else {
     
          // MARK: Mengembalikan UITableViewCell ketika cell bernilai nil/null.
          return UITableViewCell()
        }
        
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return viewModel.notes.count
    }
}
