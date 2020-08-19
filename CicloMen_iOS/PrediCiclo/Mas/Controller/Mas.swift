//
//  Mas.swift
//  PrediCiclo
//
//  Created by Daniel Barcenas on 1/20/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit

class Mas: UIViewController, UITableViewDataSource, UITableViewDelegate {
  
    
    
    @IBOutlet weak var menuTableView: UITableView!
    
    @IBAction func editarButton(_ sender: UIButton) {
        
        let storyboard = UIStoryboard(name: "Editar", bundle: nil)
                   let vc = storyboard.instantiateViewController(withIdentifier: "Editar") as! EditarViewController
                   show(vc, sender: Any?.self)

        
    }
    
    // MARK: Variables
    var bundle: Bundle?
    var menu = [CellModel]()
    
    // MARK: Controls
    
    // MARK: Functions
    override func viewDidLoad() {
        super.viewDidLoad()
        
        menuTableView.register(UINib(nibName: "MenuTableViewCell", bundle: nil), forCellReuseIdentifier: "cell")
        
        menuTableView.separatorStyle = .none
        menuTableView.rowHeight = 70
        
        menuTableView.tableFooterView = UIView ()
        bundle = Bundle(for: type(of: self))
        
        if let url = bundle?.url(forResource: "MenuData", withExtension: "json") {
            do {
                let data = try Data(contentsOf: url)
                let decoder = JSONDecoder()
                menu = try decoder.decode([CellModel].self, from: data)
            } catch {
                print("No se encontro el arreglo")
            }
        }
    }
    
    // MARK: Actions

    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return menu.count
        
    }
      
      func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell : MenuTableViewCell! = tableView.dequeueReusableCell(withIdentifier: "cell" , for: indexPath) as? MenuTableViewCell
        for item in menu {
            if item.id == indexPath.row {
                cell.itemImage!.image = UIImage(named: item.image)
                cell.itemLabel!.text = item.label
                return cell
            }
        }
       return cell
      }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        switch indexPath.row {
        case 0:
            let storyboard = UIStoryboard(name: "Mas", bundle: nil)
            let vc = storyboard.instantiateViewController(withIdentifier: "PopUp") as! PopUpCalendarViewController
                    show(vc, sender: Any?.self)
            break
             
        case 1:
            let storyboard = UIStoryboard(name: "Tienda", bundle: nil)
            let vc = storyboard.instantiateViewController(withIdentifier: "Tienda") as! Tienda
            show(vc, sender: Any?.self)

            print("soy la celda 2 ")
            break
         case 2:
            let url = "https://prediciclo.zublime.mx/terminos-y-condiciones/"
            performSegue(withIdentifier: "webSegue", sender: nil)
            
            print("soy la celda 3 ")
            break
         case 3:
            let url = "https://prediciclo.zublime.mx/politica-privacidad/"
            performSegue(withIdentifier: "webSegue", sender: nil)
            print("soy la celda 3 ")
            break
         case 4:
            let url = "https://prediciclo.zublime.mx/centro-de-ayuda/"
            performSegue(withIdentifier: "webSegue", sender: nil)
            print("soy la celda 3 ")
            break
         case 5:
            
            print("soy la celda 3 ")
            break
            
        default:
            print("soy la celda 1 ")
            break
        }
        
        
       // performSegue(withIdentifier: "webSegue", sender: nil)
    }

    
    }
    
    
    
    
    

