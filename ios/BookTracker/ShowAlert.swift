import UIKit

extension UIViewController {
    func presentAlert(message: String? = nil) {
        let alert = UIAlertController(title: "Error",
                                      message: message,
                                      preferredStyle: .alert)
        
        // add an action (button)
        alert.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        
        // show the alert
        present(alert, animated: true, completion: nil)
    }
}


