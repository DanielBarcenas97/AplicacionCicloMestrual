//
//  CustomAlertController.swift
//  PrediCiclo
//
//  Created by Daniel Barcenas on 1/29/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit

struct AlertButton {
    var title: String!;
    var action: (() -> Swift.Void)? = nil;
    var titleColor: UIColor?
    var backgroundColor: UIColor?
}

struct AlertPayload {
    var title: String!;
    var titleColor: UIColor?
    var message: String!;
    var messageColor: UIColor?
    var buttons: [AlertButton]!;
    var backgroundColor: UIColor?
}

class CustomAlertController: UIViewController {
    
    var payload: AlertPayload!;

    @IBOutlet weak var button1: UIButton!
    @IBOutlet weak var button2: UIButton!
    @IBOutlet weak var message: UILabel!
    
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        message.text = payload.message;
        
        if (payload.buttons.count == 1) {
            createButton(uiButton: button1, alertButton: payload.buttons[0]);
        }else if (payload.buttons.count == 2) {
            createButton(uiButton: button1, alertButton: payload.buttons[0]);
        }
        else if (payload.buttons.count == 3) {
            createButton(uiButton: button1, alertButton: payload.buttons[0]);
            createButton(uiButton: button2, alertButton: payload.buttons[1]);
        }
        
        if (payload.backgroundColor != nil) {
            view.backgroundColor = payload.backgroundColor;
        }
    }
    

    //MARK: Create custom alert buttons
    private func createButton(uiButton: UIButton, alertButton: AlertButton) {
        uiButton.setTitle(alertButton.title, for: .normal);
        
        if (alertButton.titleColor != nil) {
            uiButton.setTitleColor(alertButton.titleColor, for: .normal);
        }
        if (alertButton.backgroundColor != nil) {
            uiButton.backgroundColor = alertButton.backgroundColor;
        }
    }
    
    //MARK: UIButton Actions
    
    @IBAction func btnOk_click(_ sender: UIButton) {
        parent?.dismiss(animated: false, completion: nil);
        payload.buttons[1].action?();
    }
    
    @IBAction func btnCancel_click(_ sender: UIButton) {
        parent?.dismiss(animated: false, completion: nil);
        payload.buttons.first?.action?();
    }
    
}
