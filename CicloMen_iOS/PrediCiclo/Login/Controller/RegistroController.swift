//
//  RegistroController.swift
//  PrediCiclo
//
//  Created by Daniel Barcenas on 1/20/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit
import PasswordTextField
import NVActivityIndicatorView
import Lottie

class RegistroController: UIViewController {
    
    // MARK: Variables
    let waveAnimationView = AnimationView()
    let api = LoginApi()
    // MARK: Controls
    
    @IBOutlet weak var txtUserEmail: UITextField!
    @IBOutlet weak var txtUserPassword: PasswordTextField!
    @IBOutlet weak var btnRegister: UIButton!
    @IBOutlet weak var lblEmailInvalid: UILabel!
    @IBOutlet weak var lblFieldObligatory: UILabel!
    
    
    @IBOutlet weak var viewLottieR: UIView!
    @IBOutlet weak var viewError: UIView!
    @IBOutlet weak var viewSombra: UIView!
    
    @IBOutlet weak var lblTextoLabel: UILabel!
    
    
    // MARK: Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        
        navigationItem.hidesBackButton = true
        
        //txtUserPassword.isHidden = true
        lblEmailInvalid.isHidden = true
        lblFieldObligatory.isHidden = true
    
        viewError.isHidden = true
        viewSombra.isHidden = true
        
        txtUserEmail.textColor = UIColor.darkText
        txtUserEmail.layer.borderWidth = 1
        txtUserEmail.layer.borderColor = UIColor.darkGray.cgColor;
        txtUserEmail.layer.cornerRadius = 4
        txtUserEmail.attributedPlaceholder = NSAttributedString(string: "Correo Electrónico",attributes: [NSAttributedString.Key.foregroundColor: UIColor.darkGray])
        
        
        txtUserPassword.textColor = UIColor.darkText
        txtUserPassword.layer.borderWidth = 1
        txtUserPassword.layer.borderColor = UIColor.darkGray.cgColor;
        txtUserPassword.layer.cornerRadius = 4
        txtUserPassword.attributedPlaceholder = NSAttributedString(string: "Contraseña",attributes: [NSAttributedString.Key.foregroundColor: UIColor.darkGray])
        
        /*--- Animacion ---*/
        waveAnimationView.animation = Animation.named("wave")
        waveAnimationView.frame.size = viewLottieR.frame.size
        waveAnimationView.contentMode = .scaleToFill
        viewLottieR.addSubview(waveAnimationView)
        waveAnimationView.backgroundBehavior = .pauseAndRestore
        waveAnimationView.loopMode = .loop
        waveAnimationView.play()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.navigationBar.isHidden = false
    }
    
    /*--- Control del Teclado ---*/
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        self.view.endEditing(true)         //Al recibir un toque se oculta el teclado
    }
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        textField.resignFirstResponder()   //Boton de intro del teclado
        return true
    }
    
    // MARK: Actions
    
    @IBAction func btnInicio_click(_ sender: UIButton) {
        self.dismiss(animated: true, completion: nil)
        self.navigationController?.popViewController(animated: true)
    }
    @IBAction func btnOk_click(_ sender: Any) {
       viewError.isHidden = true
        viewSombra.isHidden = true
    }
    
    @IBAction func btnRegistro_click(_ sender: UIButton) {
        
        self.view.endEditing(true)
        var userEmailtxt = txtUserEmail.text
        userEmailtxt = userEmailtxt?.trimmingCharacters(in: .whitespacesAndNewlines)
        userEmailtxt = userEmailtxt?.lowercased()
        txtUserEmail.text = userEmailtxt
        
        let userPasswordTxt = txtUserPassword.text
        txtUserPassword.text = userPasswordTxt
        
        /* --Email Vacio--*/
        if txtUserEmail.text == "" {
            lblEmailInvalid.isHidden = false
            lblEmailInvalid.text = "Correo no válido"
            txtUserEmail.layer.borderColor = UIColor.red.cgColor;
            txtUserEmail.layer.borderWidth = 1.5
        }
        /* --Password Vacio--*/
        if txtUserPassword.text == "" {
            lblFieldObligatory.isHidden = false
            lblFieldObligatory.text = "Campo obligatorio *"
            txtUserPassword.layer.borderColor = UIColor.red.cgColor;
            txtUserPassword.layer.borderWidth = 1.5
        }
        
        /*--Validando Email--*/
        if isValidEmail(emailStr: userEmailtxt!){
            lblEmailInvalid.isHidden = true
            txtUserEmail.layer.borderColor = UIColor.lightGray.cgColor;
            txtUserEmail.layer.borderWidth = 1
            
            /* --Password Vacio--*/
            if txtUserPassword.text == "" {
                lblFieldObligatory.isHidden = false
                lblFieldObligatory.text = "Campo obligatorio *"
                txtUserPassword.layer.borderColor = UIColor.red.cgColor;
                txtUserPassword.layer.borderWidth = 1.5
            }else{
                /*--Campo de Contraseña con datos--*/
                if(userPasswordTxt!.count >= 6){
                    txtUserPassword.layer.borderColor = UIColor.lightGray.cgColor;
                    txtUserPassword.layer.borderWidth = 1
                    lblFieldObligatory.isHidden = true
                    NVActivityIndicatorPresenter.sharedInstance.startAnimating(ActivityData(), nil)
                    api.isEmailRegister(VC: self, email: userEmailtxt!) { (success, respEmailExist) in
                    
                    /* -- Email Valido --*/
                    if success{
                        NVActivityIndicatorPresenter.sharedInstance.stopAnimating(nil)
                        //self.crearAlertConfirmacion(title: "", texto: "")
                        self.viewError.isHidden = false
                        self.viewSombra.isHidden = false
                        self.viewError.layer.borderWidth = 0.2
                        self.viewError.layer.borderColor = UIColor.lightGray.cgColor;
                        self.viewError.layer.cornerRadius = 20
                        //self.btnAlertaError.setTitle("Aceptar",for: .normal)
                        self.lblTextoLabel.text = "Ya hay una cuenta registrada con tu correo eléctronico. Por favor, accede"
                        //self.imgAlerta.image = UIImage(named: "paloma")
                        self.txtUserPassword.text = ""
                    }else{
                        /*User no registrado*/
                        //NVActivityIndicatorPresenter.sharedInstance.stopAnimating(nil)
                        print("Registrando")
                        
                        /*--Registrando Correo y contrasena Validos--*/
                        self.api.registrarUser(VC: self, email: userEmailtxt!, pass: userPasswordTxt!) { (result, respEmailExist) in
                            print("adentro")
                            if result{
                                NVActivityIndicatorPresenter.sharedInstance.stopAnimating(nil)
                                self.performSegue(withIdentifier: "gotoRegistro", sender: nil)
                            }
                        }
                        //*/
                    }
                    }
                    
                }else{
                    self.crearAlertConfirmacion(title: "", texto: "Contraseña muy Corta.")

                }
            }
        }
    }
    
}
