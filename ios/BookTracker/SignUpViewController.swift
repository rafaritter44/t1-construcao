import UIKit

class SignUpViewController: UIViewController {

    @IBOutlet weak var usernameTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    var authService = AuthService.shared
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    @IBAction func signUpButtonTouched(_ sender: UIButton) {
        guard let username = usernameTextField.text else {
            return
        }
        
        guard let password = passwordTextField.text else {
            return
        }
        
        let user = User(name: username, password: password)
        
        authService.register(user: user) { (result) in
            switch result {
            case .success:
                self.dismiss(animated: true, completion: nil)
                AuthService.currentName = user.name
            case .failure(let error):
                self.presentAlert(message: error.message)
            }
        }
    }
    
    @IBAction func cancelButtonTouched(_ sender: Any) {
        dismiss(animated: true, completion: nil)
    }

}
