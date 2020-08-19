//
//  Registro2Periodo.swift
//  PrediCiclo
//
//  Created by Daniel Barcenas on 1/20/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit

class Registro2Periodo: UIViewController {
    
    // MARK: Variables
    private var diasPeriodo:[String] = []
    
    // MARK: Controls
    @IBOutlet weak var DiasPeriodoPicker: UIPickerView!
    
    // MARK: Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.navigationBar.isHidden = true
    
        DiasPeriodoPicker.dataSource = self
        DiasPeriodoPicker.delegate = self
        
        
        var i = 21;
        repeat{
            self.diasPeriodo.append("\(i)")
            i += 1;
        }while(i<43)
                      
        DiasPeriodoPicker.reloadAllComponents()
        DiasPeriodoPicker.selectRow(7, inComponent:0, animated:true)
    }
    
    
    
    // MARK: Actions

    @IBAction func btnAtras_click(_ sender: UIButton) {
        self.dismiss(animated: true, completion: nil)
        self.navigationController?.popViewController(animated: true)
    
    }
    
    
    
    
    
    
    
}

 
// MARK: Extensions

extension Registro2Periodo:UIPickerViewDelegate,UIPickerViewDataSource {
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
          return 1
      }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return diasPeriodo.count
    }
    
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return diasPeriodo[row]
    }
    
    func pickerView(_ pickerView: UIPickerView, viewForRow row: Int, forComponent component: Int, reusing view: UIView?) -> UIView {
        var label = UILabel()
        if let v = view {
            label = v as! UILabel
        }
        label.font = UIFont (name: "Arial Hebrew", size: 60)
        label.text =  diasPeriodo[row]
        label.textAlignment = .center
        label.textColor = UIColor.black
        return label
    }
    
    func pickerView(_ pickerView: UIPickerView, rowHeightForComponent component: Int) -> CGFloat
    {
        return 60
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        // This method is triggered whenever the user makes a change to the picker selection.
        // The parameter named row and component represents what was selected.
           print("Dias periodo \( diasPeriodo[row])")
           preferencias.setString(key:"diasPeriodo",value:diasPeriodo[row] )
    }
    
    
}
