//
//  NoteTableViewCell.swift
//  iosApp
//
//  Created by Yudha Haris Purnama on 01/04/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import UIKit
import shared

class NoteTableViewCell: UITableViewCell {

    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var contentLabel: UILabel!
    
    
    func configure(note: Note) {
        titleLabel.text = note.title
        contentLabel.text = note.content
    }
}
