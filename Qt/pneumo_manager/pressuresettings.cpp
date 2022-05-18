#include "pressuresettings.h"
#include "ui_pressuresettings.h"
#include "headpressuresettings.h"

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

    ui->widget_1->setIndex(1);
    ui->widget_2->setIndex(2);
    ui->widget_3->setIndex(3);
    ui->widget_4->setIndex(4);
    ui->widget_5->setIndex(5);
    ui->widget_6->setIndex(6);
    ui->widget_7->setIndex(7);
    ui->widget_8->setIndex(8);

    connect(ui->widget_1, SIGNAL(clicked(int)), this, SLOT(on_widget_clicked(int)));
    connect(ui->widget_2, SIGNAL(clicked(int)), this, SLOT(on_widget_clicked(int)));
    connect(ui->widget_3, SIGNAL(clicked(int)), this, SLOT(on_widget_clicked(int)));
    connect(ui->widget_4, SIGNAL(clicked(int)), this, SLOT(on_widget_clicked(int)));
    connect(ui->widget_5, SIGNAL(clicked(int)), this, SLOT(on_widget_clicked(int)));
    connect(ui->widget_6, SIGNAL(clicked(int)), this, SLOT(on_widget_clicked(int)));
    connect(ui->widget_7, SIGNAL(clicked(int)), this, SLOT(on_widget_clicked(int)));
    connect(ui->widget_8, SIGNAL(clicked(int)), this, SLOT(on_widget_clicked(int)));
}

PressureSettings::~PressureSettings()
{
    if(cps[1]!=nullptr)
        delete cps[1];
    if (cps[0] != nullptr)
        delete cps[0];
    delete ui;
}

void PressureSettings::on_widget_clicked(int idx)
{
    qDebug()<<"on widget "<<idx<<" clicked";
    if (idx >= 0 && idx < 8)
    {
        auto hps = new HeadPressureSettings(this);
        hps->setName(names[idx]);
        auto rv = hps->exec();
        if (rv == QDialog::Rejected)
        {
            delete hps;
            return;
        }
        qDebug()<<"will apply "<<hps->getName()<<"  first calibr "<<hps->getFirstVal()<<"  second calibr "<<hps->getSecondVal();

        // TODO store settings for index i
        switch(idx)
        {
        case 1:
            ui->widget_1->setName(hps->getName());
            break;
        case 2:
            ui->widget_2->setName(hps->getName());
            break;
        case 3:
            ui->widget_3->setName(hps->getName());
            break;
        case 4:
            ui->widget_4->setName(hps->getName());
            break;
        case 5:
            ui->widget_5->setName(hps->getName());
            break;
        case 6:
            ui->widget_6->setName(hps->getName());
            break;
        case 7:
            ui->widget_7->setName(hps->getName());
            break;
        case 8:
            ui->widget_8->setName(hps->getName());
            break;
        }
        delete hps; hps = nullptr;
        return;

    };
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

void PressureSettings::on_widget_4_clicked()
{
    qDebug()<<"on widget _4 clicked";
}

void PressureSettings::on_widget_5_clicked()
{
    qDebug()<<"on widget _5 clicked";
}

void PressureSettings::on_widget_6_clicked()
{
    qDebug()<<"on widget _6 clicked";
}

void PressureSettings::on_widget_7_clicked()
{
    qDebug()<<"on widget _7 clicked";
}

void PressureSettings::on_widget_8_clicked()
{
    qDebug()<<"on widget _8 clicked";
}

