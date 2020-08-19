//
//  Utils.swift
//  PrediCiclo
//
//  Created by Daniel Barcenas on 1/29/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit

class Utils {
    
    func showAlert(payload: AlertPayload, parentViewController: UIViewController) {
        var customAlertController: CustomAlertController!;
        if (payload.buttons.count == 1) {
            customAlertController = self.instantiateViewController(storyboardName: "Alerts", viewControllerIdentifier: "OneButtonAlert") as? CustomAlertController;
        }else if (payload.buttons.count == 2) {
            customAlertController = self.instantiateViewController(storyboardName: "Alerts", viewControllerIdentifier: "TwoButtonAlert") as? CustomAlertController;
        }
        else if (payload.buttons.count == 2) {
            customAlertController = self.instantiateViewController(storyboardName: "Alerts", viewControllerIdentifier: "ThreeButtonAlert") as? CustomAlertController;
        }
        else {
            // Action not supported
            return;
        }
        customAlertController?.payload = payload;
        
        let alertController = UIAlertController(title: nil, message: nil, preferredStyle: .alert);
        alertController.setValue(customAlertController, forKey: "contentViewController");
        parentViewController.present(alertController, animated: true, completion: nil);
    }
    
    public func instantiateViewController(storyboardName: String, viewControllerIdentifier: String) -> UIViewController {
        let storyboard = UIStoryboard(name: storyboardName, bundle: Bundle.main);
        return storyboard.instantiateViewController(withIdentifier: viewControllerIdentifier);
    }
    
    static func MonthsTranslate(nombre: String) -> String
    {
        switch nombre {
        case "January":
            return "Enero"
        case "February":
            return "Febrero"
        case "March":
            return "Marzo"
        case "April":
            return "Abril"
        case "May":
            return "Mayo"
        case "June":
            return "Junio"
        case "July":
            return "Julio"
        case "August":
            return "Agosto"
        case "September":
            return "Septiembre"
        case "October":
            return "Octubre"
        case "November":
            return "Noviembre"
        case "December":
            return "Diciembre"
        default:
            return ""
        }
    }
}
