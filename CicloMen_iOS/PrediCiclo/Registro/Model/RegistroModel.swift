//
//  RegistroModel.swift
//  PrediCiclo
//
//  Created by Daniel Barcenas on 1/28/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import Foundation
import ObjectMapper


struct respLoginR:Mappable{
    var status:Int?
    var data:DataLoginR?
    init?(map: Map) {}
    mutating func mapping(map: Map) {
        status <- map["status"]
        data <- map["data"]
    }
}

struct DataLoginR :Mappable{
    var userId:Int?
    init?(map: Map) {}
    mutating func mapping(map: Map) {
        userId <- map["user_id"]
    }
}
