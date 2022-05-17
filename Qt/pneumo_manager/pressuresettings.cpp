#include "pressuresettings.h"
#include "ui_pressuresettings.h"
#include <QDebug>

PressureSettings::PressureSettings(bool a, bool b, QWidget *parent) :
    QWidget(parent),
    ui(new Ui::PressureSettings)
  ,  has_third_pair(a)
  ,  has_forth_pair(b)
{
    ui->setupUi(this);
    cps[0] = nullptr;
    cps[1] = nullptr;
    if(has_third_pair)
    {
        cps[0] = new CilynderPairSettings(this);
        ui->verticalLayout->addWidget(cps[0]);
    }
    if(has_forth_pair)
    {
        cps[1] = new CilynderPairSettings(this);
        ui->verticalLayout->addWidget(cps[1]);
    }
}

PressureSettings::~PressureSettings()
{
    if(cps[1]!=nullptr)
        delete cps[1];
    if (cps[0] != nullptr)
        delete cps[0];
    delete ui;
}


void PressureSettings::on_widget_clicked()
{
    qDebug()<<"on widget __ clicked";
}

void PressureSettings::on_widget_1_clicked()
{
qDebug()<<"on widget _1 clicked";
}

void PressureSettings::on_widget_2_clicked()
{
qDebug()<<"on widget _2 clicked";
}

void PressureSettings::on_widget_3_clicked()
{
qDebug()<<"on widget _3  clicked";
}
