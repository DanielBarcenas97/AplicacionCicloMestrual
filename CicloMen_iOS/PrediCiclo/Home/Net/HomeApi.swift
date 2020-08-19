//
//  HomeApi.swift
//  PrediCiclo
//
//  Created by Jason Sa on 2/5/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import Foundation
import Alamofire
import AlamofireObjectMapper

class HomeApi{
    
    
    public func set_CreaCalendarioSiNoExiste(VC : UIViewController, callback: @escaping (_ success: Bool, _ callback:Create_if_not_exits_Model)->Void){
       
        
        manager.request(url_PrediCiclo.URL_CREA_CALENDARIO_SINO, method: .post,parameters: aut_Prediciclo.CREDENCIALES_AUTH, encoding: URLEncoding.default, headers: preferencias.headers).responseObject{(response: DataResponse<Create_if_not_exits_Model>) in
            if let httpStatusCode = response.response?.statusCode {
            let status_request = Valida_Status.Valida_Status(Status: httpStatusCode, CurrentView: VC, Mensaje: response.error ?? nil)
                switch(status_request)
                {
                case true:
                    switch response.result{
                    case .failure(let fail):
                        print("Consultar status fail \(fail)")
                        break
                    case .success(let value):
                        if value.status != 200 {
                            callback(false,value)
                        }else{
                            callback(true,value)
                        }
                    }
                    break
                case false:
                    break
                }
            }
        }
    }
    
    public func get_ObtenerCalendario(VC : UIViewController, callback: @escaping (_ success: Bool, _ callback:Calendar2_Model)->Void){
        
        manager.request(url_PrediCiclo.URL_OBTENER_CALENDARIO, method: .post,parameters: aut_Prediciclo.CREDENCIALES_AUTH, encoding: URLEncoding.default, headers: preferencias.headers).responseObject{(response: DataResponse<Calendar2_Model>) in
            if let httpStatusCode = response.response?.statusCode {
            let status_request = Valida_Status.Valida_Status(Status: httpStatusCode, CurrentView: VC, Mensaje: response.error ?? nil)
                switch(status_request)
                {
                case true:
                    switch response.result{
                    case .failure(let fail):
                        print("Consultar status fail \(fail)")
                        break
                    case .success(let value):
                        if value.status != 200 {
                            callback(false,value)
                        }else{
                            callback(true,value)
                        }
                    }
                    break
                case false:
                    break
                }
            }
        }
    }
}
