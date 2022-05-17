#include "headingmeasure.h"
#include "ui_headingmeasure.h"

HeadingMeasure::HeadingMeasure(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::HeadingMeasure)
{
    ui->setupUi(this);
}

HeadingMeasure::~HeadingMeasure()
{
    delete ui;
}
