#include "settingstab.h"
#include "ui_settingstab.h"
#include "pointsheader.h"
#include <QDebug>
#include "passwordchangedialog.h"
#include <QInputDialog>
#include "pressuresettings.h"

SettingsTab::SettingsTab(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::SettingsTab)
  , has_third_pair(false)
  , has_forth_pair(false)
{
    ui->setupUi(this);
}

SettingsTab::~SettingsTab()
{
    delete ui;
}

void SettingsTab::on_points_pushButton_clicked()
{
    if(ui->widget != nullptr)
            delete ui->widget;
    ui->widget = new PointsHeader(has_third_pair, has_forth_pair, this);
    ui->verticalLayout->addWidget(ui->widget);
    unset_title_buttons();
    QPalette Pal(palette());
    QColor llg(Qt::green);
    llg.setRed(30);
    llg.setGreen(240);
    llg.setBlue(100);
    Pal.setColor(QPalette::Button,llg);
    ui->points_pushButton->setPalette(Pal);
}

void SettingsTab::on_ap_pushButton_clicked()
{
    qDebug()<<"AP clicked";
    bool ok;
    auto text = QInputDialog::getText(this, "Введите новое имя точки доступа", "Access Point:", QLineEdit::Normal, QString(), &ok);
    if (not ok or text == "")
        return;
    else
    {
        qDebug()<<"new AP name will be "<<text;
        emit envoiAPname(text);
    }
    // TODO store new AP name into controller

}
void SettingsTab::on_pressure_pushButton_clicked()
{
    qDebug()<<"pressure clicked";
    if(ui->widget != nullptr)
    {
        delete ui->widget;
        ui->widget = nullptr;
    }
    unset_title_buttons();
    QPalette Pal(palette());
    QColor llg(Qt::green);
    llg.setRed(30);
    llg.setGreen(240);
    llg.setBlue(100);
    Pal.setColor(QPalette::Button,llg);
    ui->pressure_pushButton->setPalette(Pal);
    ui->widget = new PressureSettings(has_third_pair, has_forth_pair, this);
    ui->verticalLayout->addWidget(ui->widget);
}
void SettingsTab::on_password_pushButton_clicked()
{
    qDebug()<<"password clicked";
    bool ok;
    auto nP = PasswordChangeDialog::getNewPass(curPass, this, &ok);
    if (ok == QDialog::Accepted)
    {
        qDebug()<<"new password set to "<<nP;
        emit newPass(nP);
        curPass = nP;
    }
}

void SettingsTab::setPass(const QString cp)
{
    curPass = cp;
}

void SettingsTab::unset_title_buttons()
{
    QPalette Pal(palette());
    ui->pressure_pushButton->setPalette(Pal);
    ui->points_pushButton->setPalette(Pal);
    ui->ap_pushButton->setPalette(Pal);
    ui->password_pushButton->setPalette(Pal);
}

void SettingsTab::setPairsPresented(bool a, bool b)
{
    has_third_pair = a;
    has_forth_pair = b;
}
