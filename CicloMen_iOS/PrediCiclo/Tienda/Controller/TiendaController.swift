//
//  TiendaController.swift
//  PrediCiclo
//
//  Created by Jason Sa on 2/10/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit
import Spring

class TiendaController: UIViewController {
    
    // MARK: Variables
    
    // MARK: Controls
    @IBOutlet weak var imgProducto: SpringImageView!
    @IBOutlet weak var lblProducto: SpringLabel!
    @IBOutlet weak var lblPrecio: SpringLabel!
    @IBOutlet weak var lblDescripcion: SpringLabel!
    @IBOutlet weak var btnComprar: SpringButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.setNavigationBarHidden(true, animated: false)
        self.StatusBarColorChange()
    }
    
    override func viewDidAppear(_ animated: Bool) {
        Animar()
    }
    
    // MARK: Functions
    
    
    // MARK: Actions
    
}

extension TiendaController {
    private func Animar()
    {
        imgProducto.animation = "fadeInDown"
        imgProducto.force = 1
        imgProducto.delay = 0
        imgProducto.duration = 2.0
        imgProducto.animate()
        
        lblProducto.animation = "fadeInRight"
        lblProducto.force = 1
        lblProducto.delay = 0
        lblProducto.duration = 2.0
        lblProducto.animate()
        
        lblPrecio.animation = "fadeInRight"
        lblPrecio.force = 1
        lblPrecio.delay = 0
        lblPrecio.duration = 2.0
        lblPrecio.animate()
        
        lblDescripcion.animation = "fadeInRight"
        lblDescripcion.force = 1
        lblDescripcion.delay = 0
        lblDescripcion.duration = 2.0
        lblDescripcion.animate()
        
        btnComprar.animation = "fadeInUp"
        btnComprar.force = 1
        btnComprar.delay = 0
        btnComprar.duration = 2.0
        btnComprar.animate()
    }
}
