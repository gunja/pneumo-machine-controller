#include "pointsheader.h"
#include "ui_pointsheader.h"

PointsHeader::PointsHeader(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::PointsHeader)
{
    has_third_pair = true;
    //has_third_pair = false;
    has_forth_pair = true;
    //has_forth_pair = false;
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
