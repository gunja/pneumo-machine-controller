#include "firstwindow.h"
#include "ui_firstwindow.h"
#include <QDebug>
#include <QInputDialog>

FirstWindow::FirstWindow(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::FirstWindow)
  , lastTabIndex(0)
  , latestPassword("1111")
{
    ui->setupUi(this);
    ui->tabWidget->setCurrentIndex(lastTabIndex);
    connect(this, SIGNAL(setTab(int)), ui->tabWidget, SLOT(setCurrentIndex(int)));
    //connect(ui->tab_4, SIGNAL(newPass(QString)), this, SLOT(onNewPass(QString)));
    ui->tab_4->setPass(latestPassword);
    connect(this, SIGNAL(setPresentedPairs(bool, bool)), ui->tab_4, SLOT(setPairsPresented(bool,bool)));
    emit setPresentedPairs(true, true);
}

FirstWindow::~FirstWindow()
{
    delete ui;
}

/*void FirstWindow::on_tabWidget_tabBarClicked(int tabIndex)
{
    if (tabIndex == 3)
    {
        qDebug()<<"clicked settings";
        //ui->tabWidget->setCurrentIndex(0);
        emit setTab(0);
    } else
    {
        qDebug()<<"setting cut index to "<<tabIndex;
        //ui->tabWidget->setCurrentIndex(tabIndex);
    }
}
*/

void FirstWindow::on_tabWidget_currentChanged(int index)
{
    qDebug()<<"curr changed to "<<index;
    if (index == 3)
    {
        bool ok;
        auto text = QInputDialog::getText(this, "Введите пароль доступа", "Пароль:", QLineEdit::Password, QString(), &ok);
        qDebug()<<"Ok was "<<(ok?"OK": "not OK");
        qDebug()<<"text is "<<text;
        if(not (ok and text == latestPassword))
            emit setTab(lastTabIndex);
    }
    else
        lastTabIndex = index;
}

void FirstWindow::on_tab_4_newPass(QString np)
{
    latestPassword = np;
    qDebug()<<" FirstWindow::on_tab_4_newPass pass="<<latestPassword;
    //TODO store latestPassword to bundle settings
}

void FirstWindow::on_tab_4_envoiAPname(QString ap)
{
    qDebug()<<"re-transmitting AP name to controller"<<ap;
    //TODO implement sending of this data
}
