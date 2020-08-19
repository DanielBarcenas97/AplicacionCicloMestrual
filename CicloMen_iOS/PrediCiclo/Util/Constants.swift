//
//  Constants.swift
//  PrediCiclo
//
//  Created by Jason Sa on 2/5/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import Foundation
import UIKit

struct url_PrediCiclo {
    static let URL_CREA_CALENDARIO_SINO = "https://prediciclo.zublime.mx/wp-json/zublime/prediciclo/v1/calendar/create_if_not_exits"
    static let URL_OBTENER_CALENDARIO = "https://prediciclo.zublime.mx/wp-json/zublime/prediciclo/v1/calendar2"
}

struct aut_Prediciclo {
    static let CREDENCIALES_AUTH : [String : Any] = [
        "PHP_AUTH_USER":"carlos@de3.mx",
        "PHP_AUTH_PW":"abc123@#"
    ]
    
    static let CREDENCIALES_AUTH_PERIODOS : [String : Any] = [
        "PHP_AUTH_USER":"carlos@de3.mx",
        "PHP_AUTH_PW":"abc123@#",
        "num_periodos" : 3
    ]
}

struct CustomColors {
    static let pink = UIColor(named: "Pink")
    static let charcoalgrey = UIColor(named: "Charcoal-grey")
}

enum Visibility{
    case VISIBLE
    case INVISIBLE
}
