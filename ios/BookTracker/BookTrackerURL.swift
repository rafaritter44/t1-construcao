import Foundation

struct BookTrackerURL {
    static let baseURL = "http://172.20.10.5:8080"
    
    static func register() -> URL {
        let url = URL(string: BookTrackerURL.baseURL)!
            .appendingPathComponent("api")
            .appendingPathComponent("auth")
        return url.appendingPathComponent("register")
    }
    
    static func login() -> URL {
        let url = URL(string: BookTrackerURL.baseURL)!
            .appendingPathComponent("api")
            .appendingPathComponent("auth")
        return url.appendingPathComponent("login")
    }
    
    static func registerBook(username: String) -> URL {
        let url = URL(string: BookTrackerURL.baseURL)!
            .appendingPathComponent("api")
            .appendingPathComponent("book")
            .appendingPathComponent("user")
        return url.appendingPathComponent(username)
    }
    
    static func getAllBooks(username: String) -> URL {
        let url = URL(string: BookTrackerURL.baseURL)!
            .appendingPathComponent("api")
            .appendingPathComponent("book")
            .appendingPathComponent("user")
        return url.appendingPathComponent(username)
    }
    
    static func delete(book: Book, username: String) -> URL {
        let url = URL(string: BookTrackerURL.baseURL)!
            .appendingPathComponent("api")
            .appendingPathComponent("book")
            .appendingPathComponent("user")
            .appendingPathComponent(username)
            .appendingPathComponent("author")
            .appendingPathComponent(book.author)
            .appendingPathComponent("title")
        return url.appendingPathComponent(book.title)
    }
}
