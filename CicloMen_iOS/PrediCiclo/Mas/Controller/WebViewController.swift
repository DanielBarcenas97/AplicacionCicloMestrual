//
//  WebViewController.swift
//  PrediCiclo
//
//  Created by Manuel Sanchez on 06/02/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit
import WebKit

class WebViewController: UIViewController {
    
    
    @IBOutlet weak var webView: WKWebView!
    
    //var getUrl = String()
    

    override func viewDidLoad() {
        super.viewDidLoad()
        
        let url = URL(string: "https://www.apple.com")
        let urlRequest = URLRequest(url: url!)
        
        webView.load(urlRequest)
        
        
    }
    

  

}
