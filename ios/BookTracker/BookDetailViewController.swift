import UIKit

class BookDetailViewController: UIViewController {

    @IBOutlet weak var readPagesLabel: UILabel!
    @IBOutlet weak var totalOfPagesLabel: UILabel!
    @IBOutlet weak var nomeTextField: UITextField!
    @IBOutlet weak var autorTextField: UITextField!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    @IBAction func totalOfPageChanged(_ sender: UIStepper) {
        totalOfPagesLabel.text = "Total de paginas: \(Int(sender.value))"
    }
    
    @IBAction func readPagesChanged(_ sender: UIStepper) {
        readPagesLabel.text = "Paginas Lidas: \(Int(sender.value))"
    }
    
    @IBAction func saveButtonTouched(_ sender: UIButton) {
        guard let title = ti
        
        
        let book = Book(title: <#T##String#>, author: <#T##String#>, totalPages: <#T##Int#>, readPages: <#T##Int#>)
        
        BooksService.shared.register(book: <#T##Book#>, username: <#T##String#>, completion: <#T##(Result<Void, Error>) -> Void#>)
    }
}
