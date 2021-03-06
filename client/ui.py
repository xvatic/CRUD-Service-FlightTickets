# -*- coding: utf-8 -*-

# Form implementation generated from reading ui file 'example.ui'
#
# Created by: PyQt5 UI code generator 5.15.6
#
# WARNING: Any manual changes made to this file will be lost when pyuic5 is
# run again.  Do not edit this file unless you know what you are doing.


from PyQt5 import QtCore, QtGui, QtWidgets


class Ui_MainWindow(object):
    def setupUi(self, MainWindow):
        MainWindow.setObjectName("MainWindow")
        MainWindow.resize(835, 647)
        self.centralwidget = QtWidgets.QWidget(MainWindow)
        self.centralwidget.setObjectName("centralwidget")
        self.groupBox = QtWidgets.QGroupBox(self.centralwidget)
        self.groupBox.setGeometry(QtCore.QRect(40, 0, 771, 551))
        self.groupBox.setObjectName("groupBox")
        self.groupBox_3 = QtWidgets.QGroupBox(self.groupBox)
        self.groupBox_3.setGeometry(QtCore.QRect(170, 60, 421, 311))
        self.groupBox_3.setObjectName("groupBox_3")
        self.loginButton = QtWidgets.QPushButton(self.groupBox_3)
        self.loginButton.setGeometry(QtCore.QRect(220, 220, 113, 32))
        self.loginButton.setObjectName("loginButton")
        self.registerButton = QtWidgets.QPushButton(self.groupBox_3)
        self.registerButton.setGeometry(QtCore.QRect(60, 220, 113, 32))
        self.registerButton.setObjectName("registerButton")
        self.passwordInput = QtWidgets.QLineEdit(self.groupBox_3)
        self.passwordInput.setGeometry(QtCore.QRect(110, 160, 191, 31))
        self.passwordInput.setObjectName("passwordInput")
        self.usernameInput = QtWidgets.QLineEdit(self.groupBox_3)
        self.usernameInput.setGeometry(QtCore.QRect(110, 100, 191, 31))
        self.usernameInput.setObjectName("usernameInput")
        self.idLineEdit = QtWidgets.QLineEdit(self.groupBox_3)
        self.idLineEdit.setGeometry(QtCore.QRect(110, 50, 191, 31))
        self.idLineEdit.setObjectName("idLineEdit")
        self.groupBox_2 = QtWidgets.QGroupBox(self.groupBox)
        self.groupBox_2.setGeometry(QtCore.QRect(70, 80, 651, 361))
        self.groupBox_2.setObjectName("groupBox_2")
        self.flightIdComboBox = QtWidgets.QComboBox(self.groupBox_2)
        self.flightIdComboBox.setGeometry(QtCore.QRect(80, 300, 141, 21))
        self.flightIdComboBox.setObjectName("flightIdComboBox")
        self.registerToFLightButton = QtWidgets.QPushButton(self.groupBox_2)
        self.registerToFLightButton.setGeometry(QtCore.QRect(260, 300, 141, 32))
        self.registerToFLightButton.setObjectName("registerToFLightButton")
        self.infoButton = QtWidgets.QPushButton(self.groupBox_2)
        self.infoButton.setGeometry(QtCore.QRect(430, 300, 113, 32))
        self.infoButton.setObjectName("infoButton")
        self.tableWidget = QtWidgets.QTableWidget(self.groupBox_2)
        self.tableWidget.setGeometry(QtCore.QRect(90, 50, 481, 201))
        self.tableWidget.setRowCount(0)
        self.tableWidget.setColumnCount(3)
        self.tableWidget.setObjectName("tableWidget")
        self.tableWidget.horizontalHeader().setCascadingSectionResizes(False)
        self.groupBox_4 = QtWidgets.QGroupBox(self.groupBox)
        self.groupBox_4.setGeometry(QtCore.QRect(40, 40, 701, 471))
        self.groupBox_4.setObjectName("groupBox_4")
        self.updateButton = QtWidgets.QPushButton(self.groupBox_4)
        self.updateButton.setGeometry(QtCore.QRect(380, 390, 141, 32))
        self.updateButton.setObjectName("updateButton")
        self.deleteButton = QtWidgets.QPushButton(self.groupBox_4)
        self.deleteButton.setGeometry(QtCore.QRect(190, 390, 161, 32))
        self.deleteButton.setObjectName("deleteButton")
        self.updateUsernameLineEdit = QtWidgets.QLineEdit(self.groupBox_4)
        self.updateUsernameLineEdit.setGeometry(QtCore.QRect(370, 200, 113, 21))
        self.updateUsernameLineEdit.setObjectName("updateUsernameLineEdit")
        self.updatePasswordLineEdit = QtWidgets.QLineEdit(self.groupBox_4)
        self.updatePasswordLineEdit.setGeometry(QtCore.QRect(370, 250, 113, 21))
        self.updatePasswordLineEdit.setObjectName("updatePasswordLineEdit")
        self.listFlights = QtWidgets.QListWidget(self.groupBox_4)
        self.listFlights.setGeometry(QtCore.QRect(60, 80, 256, 192))
        self.listFlights.setObjectName("listFlights")
        MainWindow.setCentralWidget(self.centralwidget)
        self.menubar = QtWidgets.QMenuBar(MainWindow)
        self.menubar.setGeometry(QtCore.QRect(0, 0, 835, 22))
        self.menubar.setObjectName("menubar")
        MainWindow.setMenuBar(self.menubar)
        self.statusbar = QtWidgets.QStatusBar(MainWindow)
        self.statusbar.setObjectName("statusbar")
        MainWindow.setStatusBar(self.statusbar)

        self.retranslateUi(MainWindow)
        QtCore.QMetaObject.connectSlotsByName(MainWindow)

    def retranslateUi(self, MainWindow):
        _translate = QtCore.QCoreApplication.translate
        MainWindow.setWindowTitle(_translate("MainWindow", "MainWindow"))
        self.groupBox.setTitle(_translate("MainWindow", "GroupBox"))
        self.groupBox_3.setTitle(_translate("MainWindow", "GroupBox"))
        self.loginButton.setText(_translate("MainWindow", "Log In"))
        self.registerButton.setText(_translate("MainWindow", "Register"))
        self.groupBox_2.setTitle(_translate("MainWindow", "GroupBox"))
        self.registerToFLightButton.setText(_translate("MainWindow", "Register to flight"))
        self.infoButton.setText(_translate("MainWindow", "Get Info"))
        self.groupBox_4.setTitle(_translate("MainWindow", "GroupBox"))
        self.updateButton.setText(_translate("MainWindow", "Update Info"))
        self.deleteButton.setText(_translate("MainWindow", "Delete Account"))


if __name__ == "__main__":
    import sys
    app = QtWidgets.QApplication(sys.argv)
    MainWindow = QtWidgets.QMainWindow()
    ui = Ui_MainWindow()
    ui.setupUi(MainWindow)
    MainWindow.show()
    sys.exit(app.exec_())
