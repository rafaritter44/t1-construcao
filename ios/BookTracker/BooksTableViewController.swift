//
//  BooksTableViewController.swift
//  BookTracker
//
//  Created by Homero Oliveira on 03/05/19.
//  Copyright © 2019 Homero Oliveira. All rights reserved.
//

import UIKit

class BooksTableViewController: UITableViewController {
    
    var bookService = BooksService.shared

    override func viewDidLoad() {
        super.viewDidLoad()

        bookService.getAllBooks { (result) in
            switch result {
            case .success:
                self.tableView.reloadData()
            case .failure(let error):
                print(error)
            }
        }
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        
        tableView.reloadData()
    }
    
    // MARK: - Table view data source

    override func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }

    override func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return bookService.books.count
    }
    
    override func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "bookCell", for: indexPath)
        
        cell.textLabel?.text = bookService.books[indexPath.row].title

        return cell
    }

    
    // Override to support conditional editing of the table view.
    override func tableView(_ tableView: UITableView, canEditRowAt indexPath: IndexPath) -> Bool {
        // Return false if you do not want the specified item to be editable.
        return true
    }
 

        override func tableView(_ tableView: UITableView, commit editingStyle: UITableViewCell.EditingStyle, forRowAt indexPath: IndexPath) {
        if editingStyle == .delete {
            bookService.delete(index: indexPath.row) { (result) in
                switch result {
                case .success:
                    self.tableView.deleteRows(at: [indexPath], with: .fade)
                case .failure:
                    print()
                }
            }
        }
    }

    
    @IBAction func closeButtonTouched(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        guard let selectedIndex = tableView.indexPathForSelectedRow?.row else { return }
        if let target = segue.destination as? BookDetailViewController {
            target.index = selectedIndex
            target.updateBook = BooksService.shared.books[selectedIndex]
        }
    }

}
