#include "pointsheader.h"
#include "ui_pointsheader.h"
#include <QDebug>

PointsHeader::PointsHeader(bool a, bool b, QWidget *parent) :
    QWidget(parent),
    ui(new Ui::PointsHeader)
  , has_third_pair(a)
  , has_forth_pair(b)
{
    ui->setupUi(this);

    pps[0]= new PairPoints(1, this);
    ui->verticalLayout->addWidget(pps[0]);
    pps[1]= new PairPoints(3, this);
    ui->verticalLayout->addWidget(pps[1]);

    if(has_third_pair)
    {
        pps[2]= new PairPoints(5, this);
        ui->verticalLayout->addWidget(pps[2]);
    }

    if(has_forth_pair)
    {
        pps[3]= new PairPoints(7, this);
        ui->verticalLayout->addWidget(pps[3]);
    }
}

PointsHeader::~PointsHeader()
{
    if(has_third_pair)
    {
        //ui->deleteWidget(pps[2]);
        delete pps[2];
    }

    if(has_forth_pair)
    {
        //ui->deleteWidget(pps[3]);
        delete pps[3];
    }
    delete pps[1];
    delete pps[0];
    delete ui;
}

void PointsHeader::on_d1_pushButton_clicked()
{
    unset_bg_D_buttons();
    QPalette Pal(palette());
    Pal.setColor(QPalette::Button, Qt::green);
    ui->d1_pushButton->setAutoFillBackground(true);
    ui->d1_pushButton->setPalette(Pal);
}

void PointsHeader::on_d2_pushButton_clicked()
{
    unset_bg_D_buttons();
    QPalette Pal(palette());
    Pal.setColor(QPalette::Button, Qt::green);
    ui->d2_pushButton->setAutoFillBackground(true);
    ui->d2_pushButton->setPalette(Pal);
}

void PointsHeader::on_d3_pushButton_clicked()
{
    unset_bg_D_buttons();
    QPalette Pal(palette());
    Pal.setColor(QPalette::Button, Qt::green);
    ui->d3_pushButton->setAutoFillBackground(true);
    ui->d3_pushButton->setPalette(Pal);
}

void PointsHeader::on_d4_pushButton_clicked()
{
    unset_bg_D_buttons();
    QPalette Pal(palette());
    Pal.setColor(QPalette::Button, Qt::green);
    ui->d4_pushButton->setAutoFillBackground(true);
    ui->d4_pushButton->setPalette(Pal);
}

void PointsHeader::on_fwd_pushButton_clicked()
{
    unset_wd_buttons();
    QPalette Pal(palette());
    QColor lg(Qt::green);
    lg.setGreen(150);
    lg.setRed(50);
    Pal.setColor(QPalette::Button, lg);
    ui->fwd_pushButton->setPalette(Pal);
}

void PointsHeader::on_bwd_pushButton_clicked()
{
    unset_wd_buttons();
    QPalette Pal(palette());
    QColor lg(Qt::green);
    lg.setGreen(150);
    lg.setRed(50);
    Pal.setColor(QPalette::Button, lg);
    ui->bwd_pushButton->setPalette(Pal);
}

void PointsHeader::unset_bg_D_buttons()
{
    QPalette Pal(palette());
    ui->d1_pushButton->setPalette(Pal);
    ui->d2_pushButton->setPalette(Pal);
    ui->d3_pushButton->setPalette(Pal);
    ui->d4_pushButton->setPalette(Pal);
}

void PointsHeader::unset_wd_buttons()
{
    QPalette Pal(palette());
    ui->fwd_pushButton->setPalette(Pal);
    ui->bwd_pushButton->setPalette(Pal);
}
