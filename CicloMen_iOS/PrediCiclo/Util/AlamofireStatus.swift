//
//  AlamofireStatus.swift
//  PrediCiclo
//
//  Created by Jason Sa on 2/5/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import Foundation
import Alamofire



class AlamofireStatus {

func Valida_Status(Status : Int, CurrentView : UIViewController,Mensaje: Error? = nil) -> Bool {
    if(Mensaje?.localizedDescription == "The request timed out.")
    {
        return false
    }
    else if (Mensaje?.localizedDescription == "The operation couldn’t be completed. ObjectMapper failed to serialize response.")
    {
        return false
    }
    else{
        switch Status {
        case 400:
            return false
        case 403:
            return false
        case 503:
            return false
        case 200:
            return true
        case 500:
            return false
        default:
            return false
        }
    }
    }
    
}
