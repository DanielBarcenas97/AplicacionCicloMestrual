//
//  Registro1Periodo.swift
//  PrediCiclo
//
//  Created by Daniel Barcenas on 1/20/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit
import Toast_Swift

class Registro1Periodo: UIViewController {
    
    // MARK: Variables
    
    // MARK: Controls
    

    @IBOutlet weak var pickerCalendario: UIDatePicker!
    
    
    // MARK: Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.navigationBar.isHidden = true
        self.pickerCalendario.setValue(UIColor.black, forKey: "textColor")
        self.pickerCalendario.datePickerMode = UIDatePicker.Mode.date
        setDates()
        pickerCalendario.addTarget(self, action: #selector(dateChanged), for: .valueChanged)
        
    }
    
    @objc func dateChanged(_ sender: UIDatePicker) {
        /*Fecha Actual*/
        let currentDate = Date()
        let calendar = Calendar(identifier: .gregorian)
        var componentsActual = DateComponents()
        componentsActual.calendar = calendar
        //let dayCurrent = calendar.component(.day, from: currentDate)
        
        
        /*Fecha Seleccionada*/
        let components = Calendar.current.dateComponents([.year, .month, .day], from: sender.date)
        if let day = components.day, let month = components.month, let year = components.year {
            
            let dias = Set<Calendar.Component>([.day])
            let result = calendar.dateComponents(dias, from: sender.date as   Date,  to: currentDate as Date)
            
            if result.day! > 6 {
                //self.view.makeToast("No puedes seleccionar una fecha 6 dias antes del dia de hoy.", duration: 3.0, position: .bottom)
                self.view.makeToast("No puedes seleccionar una fecha 6 dias antes del dia de hoy.", duration: 2.0, point: CGPoint(x: 210.0, y: 750.0), title: "", image: UIImage(named: "")) { didTap in
                    if didTap {
                        print("completion from tap")
                    } else {
                        print("completion without tap")
                    }
                }
            }else{
                print("\(day) \(month) \(year)")
                let dateS:String = "\(year)-\(month)-\(day)"
                preferencias.setString(key:"comienzoPeriodo",value:dateS )
            }
            
            
            
            
            
        }
    }
    
    func setDates(){
        let currentDate = Date()
        let calendar = Calendar(identifier: .gregorian)
        var components = DateComponents()
        components.calendar = calendar
        
        components.day = 0
        let maxDate = calendar.date(byAdding: components, to: currentDate)!
        pickerCalendario.maximumDate = maxDate
        
        let initialDate = calendar.date(byAdding: components, to: currentDate)!
        pickerCalendario.setDate(initialDate, animated: true)
        
        components.day = -30
        let minDate = calendar.date(byAdding: components, to: currentDate)!
        pickerCalendario.minimumDate = minDate
        
        
        
    }
    
    // MARK: Actions
  
    @IBAction func btnAtras_click(_ sender: UIButton) {
        self.dismiss(animated: true, completion: nil)
        self.navigationController?.popViewController(animated: true)
    }
    
}


