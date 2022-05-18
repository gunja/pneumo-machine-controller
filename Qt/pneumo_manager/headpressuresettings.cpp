#include "headpressuresettings.h"
#include "ui_headpressuresettings.h"

HeadPressureSettings::HeadPressureSettings(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::HeadPressureSettings)
{
    ui->setupUi(this);
}

HeadPressureSettings::~HeadPressureSettings()
{
    delete ui;
}

void HeadPressureSettings::setName(QString nm)
{
    ui->lineEdit->setText(nm);
}

QString HeadPressureSettings::getName() const
{
    return ui->lineEdit->text();
}
QString HeadPressureSettings::getFirstVal() const
{
    return ui->lineEdit_2->text();
}
QString HeadPressureSettings::getSecondVal() const
{
    return ui->lineEdit_3->text();
}
