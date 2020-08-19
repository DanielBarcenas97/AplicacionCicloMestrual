//
//  LoginModel.swift
//  PrediCiclo
//
//  Created by Daniel Barcenas on 1/26/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import Foundation
import ObjectMapper
struct respEmailExist:Mappable{
    var status:Int?
    var data:DataEmailExist?
    
    init?(map: Map) {
    }
    mutating func mapping(map: Map) {
        status <- map["status"]
        data <- map["data"]
    }
}
struct DataEmailExist :Mappable{
    var emailExists:Bool?
    init?(map: Map) {
    }
    mutating func mapping(map: Map) {
        emailExists <- map["email_exists"]
    }
}

struct respLogin:Mappable{
    var status:Int?
    var data:DataLogin?
    init?(map: Map) {}
    mutating func mapping(map: Map) {
        status <- map["status"]
        data <- map["data"]
    }
}

struct DataLogin :Mappable{
    var userId:Int?
    init?(map: Map) {}
    mutating func mapping(map: Map) {
        userId <- map["user_id"]
    }
}


struct ForgotPasword :Mappable{
    var status:Int?
    var data:Bool?
    var message:String?
    init?(map: Map) {}
    mutating func mapping(map: Map) {
        status <- map["status"]
        data <- map["data"]
        message <- map ["message"]
    }
}

struct respCalendarFinal :Mappable{
    var status:Int?
    var data:[DataCalendarFinal]?
    
    init?(map: Map) {
        
    }
    
    mutating func mapping(map: Map) {
        status <- map["status"]
        data <- map["data"]
    }
    
    
}
struct DataCalendarFinal :Mappable{
    var dia_num : Int?
    var fecha : String?
    var click : Bool?
    var click_event : String?
    var boy : Bool?
    var girl : Bool?
    var solicitar_prueba : Bool?
    var pico_fertilidad : Bool?
    var menstruacion : Bool?
    var reiniciar_sangrado : Bool?
    var caso : Int?

    init?(map: Map) {

    }

    mutating func mapping(map: Map) {

        dia_num <- map["dia_num"]
        fecha <- map["fecha"]
        click <- map["click"]
        click_event <- map["click_event"]
        boy <- map["boy"]
        girl <- map["girl"]
        solicitar_prueba <- map["solicitar_prueba"]
        pico_fertilidad <- map["pico_fertilidad"]
        menstruacion <- map["menstruacion"]
        reiniciar_sangrado <- map["reiniciar_sangrado"]
        caso <- map["caso"]
    }

}


struct resRegistro : Mappable {
    var code : String?
    var message : String?
    var data : statusRegistro?
    /**/
    var id : Int?
    var date_created : String?
    var date_created_gmt : String?
    var date_modified : String?
    var date_modified_gmt : String?
    var email : String?
    var first_name : String?
    var last_name : String?
    var role : String?
    var username : String?
    var billing : Billing?
    var shipping : Shipping?
    var is_paying_customer : Bool?
    var avatar_url : String?
    var meta_data : [String]?

    init?(map: Map) {

    }

    mutating func mapping(map: Map) {

        code <- map["code"]
        message <- map["message"]
        data <- map["data"]
        
        id <- map["id"]
        date_created <- map["date_created"]
        date_created_gmt <- map["date_created_gmt"]
        date_modified <- map["date_modified"]
        date_modified_gmt <- map["date_modified_gmt"]
        email <- map["email"]
        first_name <- map["first_name"]
        last_name <- map["last_name"]
        role <- map["role"]
        username <- map["username"]
        billing <- map["billing"]
        shipping <- map["shipping"]
        is_paying_customer <- map["is_paying_customer"]
        avatar_url <- map["avatar_url"]
        meta_data <- map["meta_data"]
    }

}


struct statusRegistro : Mappable {
    var status : Int?

    init?(map: Map) {

    }

    mutating func mapping(map: Map) {

        status <- map["status"]
    }

}

struct Billing : Mappable {
    var first_name : String?
    var last_name : String?
    var company : String?
    var address_1 : String?
    var address_2 : String?
    var city : String?
    var postcode : String?
    var country : String?
    var state : String?
    var email : String?
    var phone : String?

    init?(map: Map) {

    }

    mutating func mapping(map: Map) {

        first_name <- map["first_name"]
        last_name <- map["last_name"]
        company <- map["company"]
        address_1 <- map["address_1"]
        address_2 <- map["address_2"]
        city <- map["city"]
        postcode <- map["postcode"]
        country <- map["country"]
        state <- map["state"]
        email <- map["email"]
        phone <- map["phone"]
    }

}

struct Shipping : Mappable {
    var first_name : String?
    var last_name : String?
    var company : String?
    var address_1 : String?
    var address_2 : String?
    var city : String?
    var postcode : String?
    var country : String?
    var state : String?

    init?(map: Map) {

    }

    mutating func mapping(map: Map) {

        first_name <- map["first_name"]
        last_name <- map["last_name"]
        company <- map["company"]
        address_1 <- map["address_1"]
        address_2 <- map["address_2"]
        city <- map["city"]
        postcode <- map["postcode"]
        country <- map["country"]
        state <- map["state"]
    }

}


struct Collection : Mappable {
    var href : String?

    init?(map: Map) {

    }

    mutating func mapping(map: Map) {

        href <- map["href"]
    }

}

/*
struct selfi : Mappable {
    var href : String?

    init?(map: Map) {

    }

    mutating func mapping(map: Map) {

        href <- map["href"]
    }

}

struct _links : Mappable {
    var selfi : [Self]?
    var collection : [Collection]?

    init?(map: Map) {

    }

    mutating func mapping(map: Map) {

        selfi <- map["self"]
        collection <- map["collection"]
    }

}
*/
