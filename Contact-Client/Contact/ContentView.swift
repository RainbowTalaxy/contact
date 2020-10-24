//
//  ContentView.swift
//  Contact
//
//  Created by 陈大师 on 2020/10/22.
//

import SwiftUI

struct ContentView: View {
    @ObservedObject var model = ContactModel()
    
    @State private var sheetTrigger = false
    
    let timer = Timer.publish(every: 5, on: .main, in: .common).autoconnect()
    
    var body: some View {
        NavigationView {
            List {
                Section {
                    NavigationLink(destination: ChangeIPView(model: model)) {
                        Text("更换服务器IP")
                    }
                }
                
                Section(header: Text("所有联系人")) {
                    ForEach(model.contacts) { contact in
                        NavigationLink(destination: DetailView(contact: contact, model: model)) {
                            Text(contact.name)
                        }
                    }
                }

            }
            .listStyle(PlainListStyle())
            .onReceive(timer) { _ in
                model.update()
            }
            .navigationTitle(Text("通讯录"))
            .navigationBarItems(
                trailing: Button(action: {
                    sheetTrigger.toggle()
                }, label: {
                    Image(systemName: "plus")
                })
            )
        }
        .sheet(isPresented: $sheetTrigger) {
            AddContactView(model: model)
        }
    }
}
