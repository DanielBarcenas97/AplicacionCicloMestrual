//
//  HomeController.swift
//  PrediCiclo
//
//  Created by Jason Sa on 2/5/20.
//  Copyright © 2020 Zublime. All rights reserved.
//

import UIKit
import JTAppleCalendar

class HomeController: UIViewController {
    
    //MARK: Variables
    var HomeApi_ = HomeApi()
    var calendarData = [Data]()
    struct CalendarIconoFecha {
        var caso = 0
        var fecha = ""
    }
    var calendarDataSource = [CalendarIconoFecha]()
    var formatter: DateFormatter {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        return formatter
    }
    var fechaDelCalendario = Date()
    var startDate = Date()
    var auxFechaMasMenos = 0
    
    //MARK: Controls
    @IBOutlet var calendarView: JTACMonthView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        calendarView.scrollDirection = .horizontal
        calendarView.scrollingMode   = .stopAtEachCalendarFrame
        calendarView.showsHorizontalScrollIndicator = false
        startDate = formatter.date(from: formatter.string(from: Date()))!
        ConsultaInfo()
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.StatusBarColorChange()
    }
    
    //MARK: Functions
    func ConsultaInfo() {
        
        self.LoadingStart(texto: "Consultando datos")
        
        HomeApi_.set_CreaCalendarioSiNoExiste(VC: self, callback: {(success, callback) in
            if success{
                self.HomeApi_.get_ObtenerCalendario(VC: self, callback: {(success, callback) in
                    if success{
                        self.OrganizaInfo(Info:callback,callback: {(success) in
                            if success{
                                self.calendarView.reloadData()
                                self.calendarView.scrollToDate(self.startDate)
                                self.LoadingStop()
                            }else{
                                self.LoadingStop()
                            }
                        })
                    }else{
                        self.LoadingStop()
                    }
                })
            }else{
                self.LoadingStop()
            }
        })
    }
    
    private func OrganizaInfo(Info:Calendar2_Model,callback: @escaping (_ success: Bool)->Void){
        guard let fechas = Info.data else { return }
        for items in fechas{
            calendarData.append(items)
            calendarDataSource.append(CalendarIconoFecha(caso: items.caso!, fecha: items.fecha!))
        }
        callback(true)
    }
    
    func configureCell(view: JTACDayCell?, cellState: CellState) {
        guard let cell = view as? DateCell  else { return }
        cell.lblFecha.text = cellState.text
        handleCellTextColor(cell: cell, cellState: cellState)
        handleCellEvents(cell: cell, cellState: cellState)
    }
    
    func handleCellTextColor(cell: DateCell, cellState: CellState) {
        if cellState.dateBelongsTo == .thisMonth {
            cell.lblFecha.textColor = CustomColors.charcoalgrey
        } else {
            cell.lblFecha.textColor = UIColor.gray
        }
    }
    
    func handleCellEvents(cell: DateCell, cellState: CellState) {
        let dateString = formatter.string(from: cellState.date)
        
        print(calendarDataSource.count)
        if calendarDataSource.count != 0{
            if let itemActual = calendarDataSource.first(where: {$0.fecha == dateString})
            {
                print(itemActual)
                switch itemActual.caso {
                case 0:
                    cell.imgStatus.image = UIImage(named: "")
                    break
                case 1:
                    cell.imgStatus.image = UIImage(named: "img_raindrop-close-up")
                    break
                case 2:
                    cell.imgStatus.image = UIImage(named: "img_pregnancy_test")
                    break
                case 3:
                    cell.imgStatus.image = UIImage(named: "img_caret-arrow-up")
                    break
                case 4:
                    cell.imgStatus.image = UIImage(named: "img_baby_boy")
                    break
                case 5:
                    cell.imgStatus.image = UIImage(named: "")
                    break
                case 6:
                    cell.imgStatus.image = UIImage(named: "img_baby_girl")
                    break
                case 7:
                    cell.imgStatus.image = UIImage(named: "")
                    break
                case 8:
                    cell.imgStatus.image = UIImage(named: "")
                    break
                case 9:
                    cell.imgStatus.image = UIImage(named: "")
                    break
                case 10:
                    cell.imgStatus.image = UIImage(named: "")
                    break
                default:
                    cell.imgStatus.image = UIImage(named: "")
                    break
                }
            }else{
                cell.imgStatus.image = UIImage(named: "")
            }
        }else{
            
        }
    }
    
    //MARK: Actions
    @IBAction func btnPrevious_Touch(_ sender: Any) {
        if auxFechaMasMenos == -3
        {
            print("Solo retrocedes tres meses a partir de la fecha actual")
        }
        else{
            let now = Calendar.current.date(byAdding: .month, value: -1, to: fechaDelCalendario)
            fechaDelCalendario = now!
            calendarView.scrollToDate(now!)
            auxFechaMasMenos -= 1
        }
    }
    
    @IBAction func btnNext_Touch(_ sender: Any) {
        if auxFechaMasMenos == 2
        {
            print("Solo avanzas dos meses a partir de la fecha actual")
        }
        else{
            let now = Calendar.current.date(byAdding: .month, value: 1, to: fechaDelCalendario)
            fechaDelCalendario = now!
            calendarView.scrollToDate(now!)
            auxFechaMasMenos += 1
        }
    }
}

extension HomeController: JTACMonthViewDataSource {
    func configureCalendar(_ calendar: JTACMonthView) -> ConfigurationParameters {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        let tresMesesAntesDelActual = Calendar.current.date(byAdding: .month, value: -3, to: Date())
        let dosMesesDespuesDelActual = Calendar.current.date(byAdding: .month, value: 2, to: Date())
        return ConfigurationParameters(startDate: tresMesesAntesDelActual!, endDate: dosMesesDespuesDelActual!)
    }
}

extension HomeController: JTACMonthViewDelegate {
    func calendar(_ calendar: JTACMonthView, cellForItemAt date: Date, cellState: CellState, indexPath: IndexPath) -> JTACDayCell {
        let cell = calendar.dequeueReusableJTAppleCell(withReuseIdentifier: "dateCell", for: indexPath) as! DateCell
        self.calendar(calendar, willDisplay: cell, forItemAt: date, cellState: cellState, indexPath: indexPath)
        return cell
    }
    
    func calendar(_ calendar: JTACMonthView, willDisplay cell: JTACDayCell, forItemAt date: Date, cellState: CellState, indexPath: IndexPath) {
        configureCell(view: cell, cellState: cellState)
    }
    
    func calendar(_ calendar: JTACMonthView, headerViewForDateRange range: (start: Date, end: Date), at indexPath: IndexPath) -> JTACMonthReusableView {

        let formatter = DateFormatter()
        formatter.dateFormat = "MMMM"
        let header = calendar.dequeueReusableJTAppleSupplementaryView(withReuseIdentifier: "DateHeader", for: indexPath) as! DateHeader
        let calendar = Calendar.current
        let components = calendar.dateComponents([.year], from: range.start)
        header.monthTitle.text = "\(Utils.MonthsTranslate(nombre: formatter.string(from: range.start))) \(components.year!)"
        
        return header
    }

    func calendarSizeForMonths(_ calendar: JTACMonthView?) -> MonthSize? {
        return MonthSize(defaultSize: 50)
    }
}
