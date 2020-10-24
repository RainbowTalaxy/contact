//
//  EditView.swift
//  Contact
//
//  Created by 陈大师 on 2020/10/23.
//

import SwiftUI

struct DetailView: View {
    let contact: ContactInfo
    
    @ObservedObject var model: ContactModel
    
    @State private var name = ""
    @State private var addr = ""
    @State private var tele = ""
    
    var valid: Bool {
        name != "" && addr != "" && tele != ""
    }
    
    @State private var canEdit = false
    
    var body: some View {
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
        .disabled(!canEdit)
        .navigationBarTitle("联系人", displayMode: .inline)
        .navigationBarBackButtonHidden(canEdit)
        .if(!canEdit, transform: { content in
            content
                .navigationBarItems(
                    trailing: Button("编辑") {
                        canEdit.toggle()
                    }
                )
        })
        .if(canEdit, transform: { content in
            content
                .navigationBarItems(
                    leading: Button("取消") {
                        name = contact.name
                        addr = contact.address
                        tele = contact.telephone
                        canEdit.toggle()
                    },
                    trailing: Button("保存") {
                        model.edit(with: ContactInfo(id: contact.id, name: name, address: addr, telephone: tele))
                        canEdit.toggle()
                    }
                    .disabled(!valid)
                )
        })
        .onAppear {
            name = contact.name
            addr = contact.address
            tele = contact.telephone
        }
    }
}

extension View {
    @ViewBuilder func `if`<T>(_ condition: Bool, transform: (Self) -> T) -> some View where T : View {
        if condition {
            transform(self)
        } else {
            self
        }
    }
}
