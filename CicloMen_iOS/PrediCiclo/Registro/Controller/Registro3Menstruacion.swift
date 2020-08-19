//
//  Registro3Menstruacion.swift
//  PrediCiclo
//
//  Created by Daniel Barcenas on 1/20/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit

class Registro3Menstruacion: UIViewController {
    
    
    // MARK: Variables
    private var diasMenstruacion:[String] = []
    // MARK: Controls
    @IBOutlet weak var menstruacionPicker: UIPickerView!
    // MARK: Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationController?.navigationBar.isHidden = true
        // Do any additional setup after loading the view.
        menstruacionPicker.dataSource = self
        menstruacionPicker.delegate = self
        
        var i = 3;
        repeat{
            self.diasMenstruacion.append("\(i)")
            i += 1;
        }while(i<7)
                      
        menstruacionPicker.reloadAllComponents()
        menstruacionPicker.selectRow(2, inComponent:0, animated:true)
    }
    
    func pickerView(_ pickerView: UIPickerView, didSelectRow row: Int, inComponent component: Int) {
        // This method is triggered whenever the user makes a change to the picker selection.
        // The parameter named row and component represents what was selected.
           print("Dias menstruacion \(diasMenstruacion[row])")
           preferencias.setString(key:"diasMenstruacion",value:diasMenstruacion[row] )
    }
    
    // MARK: Actions
    @IBAction func btnAtras_click(_ sender: UIButton) {
        self.dismiss(animated: true, completion: nil)
        self.navigationController?.popViewController(animated: true)
    }
    
    

}

extension Registro3Menstruacion:UIPickerViewDelegate,UIPickerViewDataSource {
    
    func numberOfComponents(in pickerView: UIPickerView) -> Int {
          return 1
      }
    
    func pickerView(_ pickerView: UIPickerView, numberOfRowsInComponent component: Int) -> Int {
        return diasMenstruacion.count
    }
    
    
    func pickerView(_ pickerView: UIPickerView, titleForRow row: Int, forComponent component: Int) -> String? {
        return diasMenstruacion[row]
    }
    
    
    func pickerView(_ pickerView: UIPickerView, viewForRow row: Int, forComponent component: Int, reusing view: UIView?) -> UIView {
        var label = UILabel()
        if let v = view {
            label = v as! UILabel
        }
        label.font = UIFont (name: "Arial Hebrew", size: 60)
        label.text =  diasMenstruacion[row]
        label.textAlignment = .center
        label.textColor = UIColor.black
        return label
    }
    
    func pickerView(_ pickerView: UIPickerView, rowHeightForComponent component: Int) -> CGFloat
    {
        return 60
    }
    

    
    
}
