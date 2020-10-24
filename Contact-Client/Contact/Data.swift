//
//  Data.swift
//  Contact
//
//  Created by 陈大师 on 2020/10/23.
//

import Foundation
import Socket

struct ContactInfo: Identifiable {
    let id: Int
    let name: String
    let address: String
    let telephone: String
    
    static var IP = "localhost"
    private static let PORT: Int32 = 8078
    
    private static func connect(withCmd cmd: String, handler: @escaping (Socket) throws -> Void) {
        let socket = try! Socket.create()
        do {
            try socket.connect(to: IP, port: PORT)
            try socket.println(cmd)
            try handler(socket)
        } catch _ { }
    }
    
    private static let END = "end_identifier"
    
    static func test(withName name: String) {
        connect(withCmd: "ECHO") { socket in
            try socket.println(name)
            let message = socket.read(withEndedIdentifier: END)
            print(message)
        }
    }
    
    private static let DIVIDER = "info_divider"
    
    static func fetch(handler: @escaping ([ContactInfo]) -> Void) {
        connect(withCmd: "SELECT") { socket in
            var message = socket.read(withEndedIdentifier: END)
            let size = Int(message.removeFirst()) ?? 0
            var contacts = [ContactInfo]()
            for _ in 0..<size {
                if message.count >= 5 && message.removeFirst() == DIVIDER {
                    let id = Int(message.removeFirst()) ?? -1
                    let name = message.removeFirst()
                    let addr = message.removeFirst()
                    let tele = message.removeFirst()
                    contacts.append(ContactInfo(id: id, name: name, address: addr, telephone: tele))
                }
            }
            handler(contacts)
        }
    }
    
    static func insert(with contact: ContactInfo) {
        connect(withCmd: "INSERT") { socket in
            try socket.println(contact.name)
            try socket.println(contact.address)
            try socket.println(contact.telephone)
        }
    }
    
    static func update(with contact: ContactInfo) {
        connect(withCmd: "UPDATE") { socket in
            try socket.println(contact.id)
            try socket.println(contact.name)
            try socket.println(contact.address)
            try socket.println(contact.telephone)
        }
    }
    
    static func delete(with id: Int) {
        connect(withCmd: "DELETE") { socket in
            try socket.println(id)
        }
    }
}

extension Socket {
    func println(_ msg: CustomStringConvertible) throws {
        try self.write(from: msg.description + "\n")
    }
    
    func read(withEndedIdentifier identifier: String) -> [String] {
        var message = ""
        while self.isActive && !message.trimmingCharacters(in: .whitespacesAndNewlines).hasSuffix(identifier) {
            do {
                message += (try self.readString() ?? "")
            } catch _ { }
        }
        var result = message.split(separator: "\n").map { String($0) }
        result.removeLast()
        return result
    }
}
