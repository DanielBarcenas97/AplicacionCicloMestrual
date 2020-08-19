//
//  LoginApi.swift
//  PrediCiclo
//
//  Created by Daniel Barcenas on 1/26/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import Foundation
import Alamofire
import AlamofireObjectMapper

class LoginApi {
    
    let URL_CHECK_EMAIL = "https://prediciclo.zublime.mx/wp-json/zublime/prediciclo/v1/email_exists"
    let URL_LOGIN = "https://prediciclo.zublime.mx/wp-json/zublime/prediciclo/v1/login"
    let URL_RECUPERAR_EMAIL = "https://prediciclo.zublime.mx/wp-json/zublime/prediciclo/v1/customer/lost_password"
    let URL_REGISTRAR_USUARIA = "https://prediciclo.zublime.mx/wp-json/wc/v3/customers"
    
    let UPDATE_INFO = "https://prediciclo.zublime.mx/wp-json/zublime/prediciclo/v1/update_info"
    
    public func isEmailRegister(VC : UIViewController,email:String,callback: @escaping (_ success: Bool, _ callback:respEmailExist)->Void){
        
        let params = ["email":email]
        manager.request(URL_CHECK_EMAIL, method: .post, parameters: params, encoding: URLEncoding.default)
            
            .responseObject{(response: DataResponse<respEmailExist>) in
                if let httpStatusCode = response.response?.statusCode {
                    let status_request = Valida_Status.Valida_Status(Status: httpStatusCode, CurrentView: VC, Mensaje: response.error ?? nil)
                    
                    switch(status_request){
                        case true:
                            switch response.result{
                            case .failure(let fail):
                                print("Consultar status fail \(fail)")
                                break
                            
                            case .success(let value):
                               //if(value.status == 500 || value.status == 400){
                               if value.status != 200 {
                                   callback(false,value)
                               }else{
                                callback(value.data?.emailExists ?? false,value)
                               }
                           }
                           break
                        
                       case false:
                           //VC.Muestra_Error_VC()
                            //callback(false,nil)
                           break
                    }
               }
           }
       }
    
    
    public func iniciarSesion(VC : UIViewController,email:String,pass:String, callback: @escaping (_ success: Bool, _ callback:respLogin)->Void){
        let params = ["username":email, "password":pass]
        manager.request(URL_LOGIN, method: .post,parameters: params, encoding: URLEncoding.default, headers: preferencias.headers)
            .responseObject{(response: DataResponse<respLogin>) in
                
                if let httpStatusCode = response.response?.statusCode {
                    let status_request = Valida_Status.Valida_Status(Status: httpStatusCode, CurrentView: VC, Mensaje: response.error ?? nil)
                    
                    switch(status_request){
                    
                    case true:
                        switch response.result{
                        
                        case .failure(let fail):
                            print("Consultar status fail \(fail)")
                            break
                        
                        case .success(let value):
                        //if(value.status == 500 || value.status == 400){
                            if value.status != 200 {
                                callback(false,value)
                                
                            }else{
                                callback(true,value)
                            }
                        }
                    break
                    
                    case false:
                    //VC.Muestra_Error_VC()
                    break
                }
                
            }
            
        }
    }
    
    
    public func recuperarEmail(VC : UIViewController,email:String,callback: @escaping (_ success: Bool, _ callback:ForgotPasword)->Void){
        
        let params = ["email":email]
        manager.request(URL_RECUPERAR_EMAIL, method: .post, parameters: params, encoding: URLEncoding.default)
            
            .responseObject{(response: DataResponse<ForgotPasword>) in
                if let httpStatusCode = response.response?.statusCode {
                    let status_request = Valida_Status.Valida_Status(Status: httpStatusCode, CurrentView: VC, Mensaje: response.error ?? nil)
                    
                    switch(status_request){
                        case true:
                            switch response.result{
                            case .failure(let fail):
                                print("Consultar status fail \(fail)")
                                break
                            
                            case .success(let value):
                               //if(value.status == 500 || value.status == 400){
                               if value.status != 200 {
                                   callback(false,value)
                                //callback((value.message != nil) ,value)
                               }else{
                                callback(true,value)
                                //callback((value.message != nil),value)
                               }
                           }
                           break
                        
                       case false:
                           //VC.Muestra_Error_VC()
                            //callback(false,nil)
                           break
                    }
               }
           }
        
    }
    
    
    public func registrarUser(VC : UIViewController,email:String,pass:String,callback: @escaping (_ success: Bool, _ callback:resRegistro)->Void){
        
        let first_name:String = ""
        let last_name:String = ""
        
        let params = ["username":email,"first_name": first_name,"last_name":last_name ,"password":pass]
        manager.request(URL_REGISTRAR_USUARIA, method: .post, parameters: params, encoding: URLEncoding.default).responseObject{(response: DataResponse<resRegistro>) in

            if let httpStatusCode = response.response?.statusCode {
                let status_request = Valida_Status.Valida_Status(Status: httpStatusCode, CurrentView: VC, Mensaje: response.error ?? nil)
                print(status_request)
                
                switch(status_request){
                case true:
                    switch response.result {
                    case .success(let value_success):
                        print(value_success)
                        
                    case .failure(let value_fail):
                        print(value_fail)
                        
                    }
                    
                    break
                
                
                case false:
                    switch response.result{
                        case .failure(let fail):
                            print("Consultar status fail \(fail)")
                        break
                        case .success(let value):
                            if value.data?.status != 400 {
                                callback(false,value)
                                print("regresa false")
                                print(value)
                            }else{
                                callback(true,value)
                                print("Regresa true")
                                print(value)
                            }
                    }
                    break
                }
        }
        }
        
    }
    
    
    public func updateInfo(VC : UIViewController, callback: @escaping (_ success: Bool, _ callback:respLogin)->Void){
        
        let params = ["user_id":preferencias.getUserId(),
                      "duracion_ciclo":preferencias.getString(key: "diasMenstruacion"),
                      "duracion_periodo":preferencias.getString(key: "diasPeriodo"),
                      "fecha_nacimiento":preferencias.getString(key: "cumple"),
                      "fecha_inicio_periodo":preferencias.getString(key: "comienzoPeriodo")] as [String : Any]
        manager.request(UPDATE_INFO, method: .post,parameters: params, encoding: URLEncoding.default, headers: preferencias.headers)
         
        .responseObject{(response: DataResponse<respLogin>) in
            
            
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
                        //if(value.status == 500 || value.status == 400){
                        if value.status != 200 {
                            callback(false,value)
                        }else{
                            //callback(true,value)
                         callback((value.data?.userId != nil),value)
                        }
                    }
                    break
                case false:
                    //VC.Muestra_Error_VC()
                    break
                }
                
            }
            
        }
    }
}




