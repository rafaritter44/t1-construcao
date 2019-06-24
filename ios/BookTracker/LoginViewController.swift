import UIKit

final class LoginViewController: UIViewController {

    @IBOutlet weak var usernameTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    var authService = AuthService.shared
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func loginButtonTouched(_ sender: UIButton) {
        guard let username = usernameTextField.text else {
            return
        }
        
        guard let password = passwordTextField.text else {
            return
        }
        
        let user = User(name: username, password: password)
        
        authService.login(user: user) { (result) in
            switch result {
            case .success(let value):
                if value {
                    AuthService.currentName = user.name
                    self.performSegue(withIdentifier: "showBooks", sender: nil)
                } else {
                    self.presentAlert(message: "Login invalido")
                }
            case .failure(let error):
                print(error.localizedDescription)
            }
        }
    }
    
}
