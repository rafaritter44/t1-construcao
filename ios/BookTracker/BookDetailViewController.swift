import UIKit

class BookDetailViewController: UIViewController {

    @IBOutlet weak var readPagesLabel: UILabel!
    @IBOutlet weak var totalOfPagesLabel: UILabel!
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var authorTextField: UITextField!
    @IBOutlet weak var totalOfPagesStepper: UIStepper!
    var totalOfPages: Int = 0 {
        didSet {
            totalOfPagesLabel.text = "Total de paginas: \(totalOfPages)"
        }
    }
    var readPages: Int = 0 {
        didSet {
            readPagesLabel.text = "Paginas Lidas: \(readPages)"
        }
    }
    var updateBook: Book?
    var index: Int?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if let book = updateBook {
            titleTextField.text = book.title
            authorTextField.text = book.author
            totalOfPages = book.totalPages
            readPages = book.readPages
            
            authorTextField.isUserInteractionEnabled = false
            titleTextField.isUserInteractionEnabled = false
            totalOfPagesStepper.isHidden = true
        }
    }
    
    @IBAction func totalOfPageChanged(_ sender: UIStepper) {
        totalOfPages = Int(sender.value)
    }
    
    @IBAction func readPagesChanged(_ sender: UIStepper) {
        readPages = Int(sender.value)
    }
    
    @IBAction func saveButtonTouched(_ sender: UIButton) {
        guard let title = titleTextField.text else {
            return
        }
        
        guard let author = authorTextField.text else {
            return
        }
        
        let book = Book(title: title,
                        author: author,
                        totalPages: totalOfPages,
                        readPages: readPages)
        
        if updateBook == nil {
        
        BooksService.shared.register(book: book) { (result) in
            switch result {
            case .success:
                self.navigationController?.popViewController(animated: true)
            case .failure(let error):
                self.presentAlert(message: "Erro para inserir o livro")
            }
        }
            
        } else if let index = index {
            BooksService.shared.update(updatedBook: book, index: index) { (result) in
                switch result {
                case .success:
                    self.navigationController?.popViewController(animated: true)
                case .failure(let error):
                    self.presentAlert(message: "Erro para atualizar o livro")
                }
            }
        }
    }
}
