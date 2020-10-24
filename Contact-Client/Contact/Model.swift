//
//  Model.swift
//  Contact
//
//  Created by 陈大师 on 2020/10/23.
//

import Foundation
import Socket

class ContactModel: ObservableObject {
    @Published var contacts = [ContactInfo]()
    
    init() {
        updateIP()
        update()
    }
    
    func changeIP(with ip: String) {
        UserDefaults.standard.setValue(ip, forKey: "data-ip")
        updateIP()
    }
    
    func updateIP() {
        if let ip = UserDefaults.standard.string(forKey: "data-ip") {
            ContactInfo.IP = ip
        }
        update()
    }
    
    var IP: String {
        ContactInfo.IP
    }
    
    func update() {
        contacts = []
        ContactInfo.fetch { contacts in
            self.contacts = contacts
        }
    }
    
    func add(with contact: ContactInfo) {
        ContactInfo.insert(with: contact)
    }
    
    func edit(with contact: ContactInfo) {
        ContactInfo.update(with: contact)
    }
    
    func delete(with id: Int) {
        ContactInfo.delete(with: id)
    }
}
