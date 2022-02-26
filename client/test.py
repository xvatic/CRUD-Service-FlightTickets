import sys
from PyQt5.sip import delete
import requests
import json
from PyQt5 import QtCore, QtGui, QtWidgets
from PyQt5.QtGui import QIcon
from ui import Ui_MainWindow

class FlightsApp(QtWidgets.QMainWindow):
    def __init__(self):
        super(FlightsApp, self).__init__()
        self.ui = Ui_MainWindow()
        self.ui.setupUi(self)

    def initUI(self):
        self.setWindowTitle('Flights App')

    def message(self, message):
        msg = QtWidgets.QMessageBox()
        msg.setWindowTitle('Warning')
        msg.setText(message)
        msg.exec()

    def switchToFlights(self):
        self.ui.groupBox_2.setVisible(True)
        self.ui.groupBox_4.setVisible(False)
        self.ui.groupBox_3.setVisible(False)
        self.ui.tableWidget.setColumnWidth(0, 100)
        self.ui.tableWidget.setColumnWidth(1, 100)
        self.ui.tableWidget.setColumnWidth(2, 100)
        self.ui.tableWidget.setHorizontalHeaderLabels(["id", "source", "destination"])
        r = requests.get('http://localhost:8080/flights/all')
    
        self.ui.tableWidget.setRowCount(len(r.json()))
        row = 0
        for flight in r.json():
            self.ui.tableWidget.setItem(row, 0, QtWidgets.QTableWidgetItem(str(flight['id'])))
            self.ui.tableWidget.setItem(row, 1, QtWidgets.QTableWidgetItem(flight['source']))
            self.ui.tableWidget.setItem(row, 2, QtWidgets.QTableWidgetItem(flight['destination']))
            self.ui.flightIdComboBox.addItem(str(flight['id']))
            row=row+1


    def switchToAccountInfo(self):
        self.ui.groupBox_2.setVisible(False)
        self.ui.groupBox_4.setVisible(True)
        self.ui.groupBox_3.setVisible(False)
        
        r = requests.get(f'http://localhost:8080/users?id={id}')
        print(r.json()["flightsList"])
        print(id)
        for flight in r.json()["flightsList"]:
            self.ui.listFlights.addItem(QtWidgets.QListWidgetItem(str(flight["id"])))

    def deleteAccount(self):
        r = requests.delete(f'http://localhost:8080/users/{id}')
        if r.status_code == 200:
            self.message("Succesfully deleted")
        else:
            self.message(r.text)    
        
        self.ui.groupBox_2.setVisible(False)
        self.ui.groupBox_4.setVisible(False)
        self.ui.groupBox_3.setVisible(True)
        

    def updateAccount(self):
        if self.ui.updatePasswordLineEdit.text() == '' or self.ui.updateUsernameLineEdit.text() == '':
            self.message('Missing username or password')
        else:
            headers = {'content-type': 'application/json'}
            payload = {'id':id,'username':self.ui.updateUsernameLineEdit.text(), 'password':self.ui.updatePasswordLineEdit.text()}
            r = requests.put('http://localhost:8080/users', data=json.dumps(payload), headers=headers)
            if r.status_code == 200:
                self.message("Succesfully updated")
            else:
                self.message(r.text) 
            
            self.switchToFlights()

    def registerToFlight(self):
        headers = {'content-type': 'application/json'}
        payload = {'id':id}
        flightId = int(self.ui.flightIdComboBox.currentText())
        
        r = requests.put(f"http://localhost:8080/flights/{flightId}", data=json.dumps(payload), headers=headers)
        if r.status_code == 200:
            self.message("Succesfully registered")
        else:
            self.message(r.text)
        

   
    def register(self):
        global id
        if self.ui.passwordInput.text() == '' or self.ui.username.text() == '':
            self.message('Missing username or password')
        else:
            headers = {'content-type': 'application/json'}
            payload = {'username':self.ui.usernameInput.text(), 'password':self.ui.passwordInput.text()}
            r = requests.post('http://localhost:8080/users', data=json.dumps(payload), headers=headers)
            if r.status_code == 200:
                id = int(r.text)
                print(id)
            else:
                self.message(r.text)

    def login(self):
        global id
        if self.ui.passwordInput.text() == '' or self.ui.idLineEdit.text() == '':
            self.message('Missing username or password')
        else:
            headers = {'content-type': 'application/json'}
            payload = {'id':self.ui.idLineEdit.text(), 'password':self.ui.passwordInput.text()}
            r = requests.post('http://localhost:8080/users/login', data=json.dumps(payload), headers=headers)
            if r.text == 'SUCCESS':
                id = int(self.ui.idLineEdit.text())
                print(id)
                self.switchToFlights()
            else:
                self.message(r.text)

        

app = QtWidgets.QApplication([])
application = FlightsApp()
application.ui.groupBox_2.setVisible(False)
application.ui.groupBox_4.setVisible(False)
application.ui.groupBox_3.setVisible(True)


id = 1
application.show()
application.ui.passwordInput.setEchoMode(QtWidgets.QLineEdit.Password)
application.ui.registerButton.clicked.connect(application.register)
application.ui.loginButton.clicked.connect(application.login)
application.ui.registerToFLightButton.clicked.connect(application.registerToFlight)
application.ui.infoButton.clicked.connect(application.switchToAccountInfo)
application.ui.updateButton.clicked.connect(application.updateAccount)
application.ui.deleteButton.clicked.connect(application.deleteAccount)

sys.exit(app.exec())

