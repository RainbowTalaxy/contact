//
//  ChangeIPView.swift
//  Contact
//
//  Created by 陈大师 on 2020/10/24.
//

import SwiftUI

struct ChangeIPView: View {
    @ObservedObject var model: ContactModel
    
    @State private var newIP = ""
    
    var body: some View {
        List {
            TextField("输入服务器IP", text: $newIP)
        }
        .navigationTitle("更换服务器IP")
        .onAppear {
            newIP = model.IP
        }
        .onDisappear {
            model.changeIP(with: newIP)
        }
    }
}
