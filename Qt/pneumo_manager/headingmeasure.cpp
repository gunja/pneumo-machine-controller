#include "headingmeasure.h"
#include "ui_headingmeasure.h"

HeadingMeasure::HeadingMeasure(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::HeadingMeasure)
  , wasClicked(false)
  , propertyIndex(-1)
{
    ui->setupUi(this);
}

HeadingMeasure::~HeadingMeasure()
{
    delete ui;
}

void HeadingMeasure::mousePressEvent(QMouseEvent *event)
{
    wasClicked = true;
    QWidget::mousePressEvent(event);
}
void HeadingMeasure::mouseReleaseEvent(QMouseEvent *event)
{
    if (wasClicked)
    {
        //emit clicked();
        emit clicked(propertyIndex);
    }
    wasClicked = false;
    QWidget::mouseReleaseEvent(event);
}

void HeadingMeasure::setIndex(int idx)
{
    propertyIndex = idx;
}

void HeadingMeasure::setName(QString nm)
{
    ui->label->setText(nm);
}
