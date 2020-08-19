//
//  PopUpCalendarViewController.swift
//  PrediCiclo
//
//  Created by Manuel Sanchez on 09/02/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit

class PopUpCalendarViewController: UIViewController {

    @IBAction func acceptButton(_ sender: UIButton) {
        print ( "no ma, funciono y se borro ")
    }
    
    
    @IBAction func cancelButtton(_ sender: UIButton) {
        self.presentingViewController?.dismiss(animated: true, completion: nil)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
