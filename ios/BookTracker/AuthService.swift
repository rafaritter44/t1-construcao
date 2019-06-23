import Foundation

final class AuthService {
    static let shared = AuthService()
    static var currentName: String?
    let session: URLSession
    
    init(session: URLSession = .shared) {
        self.session = session
    }
    
    func register(user: User, completion: @escaping (Result<User, Error>) -> Void) {
        
        let url = BookTrackerURL.register()
        var urlRequest = URLRequest(url: url)
        urlRequest.httpMethod = "POST"
        urlRequest.httpBody =  try? JSONEncoder().encode(user)
        urlRequest.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        session.dataTask(with: urlRequest) { (data, response, error) in
            DispatchQueue.main.async {
                guard let data = data else {
                    completion(.failure(URLError(.badServerResponse)))
                    return
                }
                do {
                    let user = try (JSONDecoder().decode(User.self, from: data))
                    completion(.success(user))
                } catch {
                    completion(.failure(error))
                }
            }
        }.resume()
    }
    
    func login(user: User, completion: @escaping ((Result<Bool, Error>) -> Void)) {
        let url = BookTrackerURL.login()
        var urlRequest = URLRequest(url: url)
        urlRequest.httpMethod = "POST"
        urlRequest.addValue("application/json", forHTTPHeaderField: "Content-Type")
        urlRequest.httpBody =  try? JSONEncoder().encode(user)
        
        session.dataTask(with: urlRequest) { (data, response, error) in
            DispatchQueue.main.async {
                guard let data = data else {
                    completion(.failure(URLError(.badServerResponse)))
                    return
                }
                let string = String(decoding: data, as: UTF8.self)
                completion(.success(string == "true"))
            }
        }.resume()

    }
}
