//
//  InformacionController.swift
//  PrediCiclo
//
//  Created by Jason Sa on 2/5/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit

class InformacionController: UIViewController {
    
    // MARK: Variables
    
    // MARK: Controls
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        
    }
    
    
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: false)
        self.tabBarController?.tabBar.isHidden = true
    }
    
    // MARK: Functions
    
    
    // MARK: Actions
    @IBAction func btnClose_Touch(_ sender: UIButton) {
        self.dismiss(animated: true, completion: nil)
    }
    
}
