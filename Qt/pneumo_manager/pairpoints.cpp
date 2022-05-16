#include "pairpoints.h"
#include "ui_pairpoints.h"


PairPoints::PairPoints(int base_index, QWidget *parent) :
    QWidget(parent),
    ui(new Ui::PairPoints)
{
    ui->setupUi(this);
    ui->label->setText(QString("Т%1").arg(base_index));
    ui->label_2->setText(QString("Т%1").arg(base_index + 1));
}

PairPoints::~PairPoints()
{
    delete ui;
}
