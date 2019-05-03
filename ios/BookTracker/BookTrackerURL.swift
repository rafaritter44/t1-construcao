import Foundation

struct BookTrackerURL {
    static let baseURL = "http://localhost:8080"
    
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
}
