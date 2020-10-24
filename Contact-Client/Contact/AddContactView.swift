//
//  AddContactView.swift
//  Contact
//
//  Created by 陈大师 on 2020/10/24.
//

import SwiftUI

struct AddContactView: View {
    @Environment(\.presentationMode) var presentation
    
    @ObservedObject var model: ContactModel
    
    @State private var name = ""
    @State private var addr = ""
    @State private var tele = ""
    
    var valid: Bool {
        name != "" && addr != "" && tele != ""
    }
    
    var body: some View {
        NavigationView {
            Form {
                HStack {
                    Text("姓名")
                    TextField("输入姓名", text: $name)
                }
                
                HStack {
                    Text("电话")
                    TextField("输入电话", text: $tele)
                }
                
                HStack {
                    Text("住址")
                    TextField("输入住址", text: $addr)
                }
            }
            .navigationTitle("新建联系人")
            .navigationBarItems(
                leading: Button("取消") {
                    presentation.wrappedValue.dismiss()
                },
                trailing: Button("保存") {
                    model.add(with: ContactInfo(id: -1, name: name, address: addr, telephone: tele))
                    presentation.wrappedValue.dismiss()
                }.disabled(!valid)
            )
        }
    }
}
