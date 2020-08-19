//
//  MenuApi.swift
//  PrediCiclo
//
//  Created by Manuel Sanchez on 06/02/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import Foundation
import Alamofire
import AlamofireObjectMapper




class MenuApi {
    
    let urlInfoUsuario = " https://prediciclo.zublime.mx/wp-json/zublime/prediciclo/v1/customer/info"
    
    public func DatosUsuario(VC : UIViewController,email:String,pass: String,callback: @escaping (_ success: Bool, _ callback:respEmailExist)->Void){
        
        
        let params = ["email":email, "pass":pass]
        manager.request(urlInfoUsuario, method: .post, parameters: params, encoding: URLEncoding.default)
    
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
    
}


