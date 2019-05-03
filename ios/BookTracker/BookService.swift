import Foundation

final class BooksService {
    static let shared = BooksService()
    var books: [Book] = []
    let session: URLSession
    
    init(session: URLSession = .shared) {
        self.session = session
    }
    
    func register(book: Book,
                  username: String,
                  completion: @escaping (Result<Void, Error>) -> Void) {
        
        let url = BookTrackerURL.registerBook(username: AuthService.currentName ?? "")
        var urlRequest = URLRequest(url: url)
        urlRequest.httpMethod = "POST"
        urlRequest.httpBody =  try? JSONEncoder().encode(book)
        urlRequest.addValue("application/json", forHTTPHeaderField: "Content-Type")
        
        session.dataTask(with: urlRequest) { (data, response, error) in
            DispatchQueue.main.async {
                guard let data = data else {
                    return
                }
                
                if let book = try? JSONDecoder().decode(Book.self, from: data) {
                    self.books.append(book)
                    completion(.success(()))
                }
            }
            }.resume()
    }
    
    func getAllBooks(completion: @escaping (Result<Void, Error>) -> Void) {
        let url = BookTrackerURL.getAllBooks(username: AuthService.currentName ?? "")
        let urlRequest = URLRequest(url: url)
        
        session.dataTask(with: urlRequest) { (data, response, error) in
            DispatchQueue.main.async {
                guard let data = data else {
                    return
                }
                
                self.books = (try? JSONDecoder().decode([Book].self, from: data)) ?? []
                completion(.success(()))
            }
            }.resume()
    }
    
    func delete(book: Book, username: String, completion: @escaping (Result<String, Error>) -> Void) {
        let url = BookTrackerURL.delete(book: book, username: username)
        var urlRequest = URLRequest(url: url)
        urlRequest.httpMethod = "DELETE"
        
        session.dataTask(with: urlRequest) { (data, response, error) in
            DispatchQueue.main.async {

            guard let data = data else { return }
            
            let message = String(decoding: data, as: UTF8.self)
            completion(.success(message))
        }
        }
    }
}

struct Book: Codable {
    let title: String
    let author: String
    let totalPages: Int
    let readPages: Int
}
