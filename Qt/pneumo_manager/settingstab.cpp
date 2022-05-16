#include "settingstab.h"
#include "ui_settingstab.h"
#include "pointsheader.h"
#include <QDebug>

SettingsTab::SettingsTab(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::SettingsTab)
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
    ui->widget = new PointsHeader(this);
    ui->verticalLayout->addWidget(ui->widget);
}

void SettingsTab::on_ap_pushButton_clicked()
{
    qDebug()<<"AP clicked";
    if(ui->widget != nullptr)
    {
        delete ui->widget;
        ui->widget = nullptr;
    }
}
void SettingsTab::on_pressure_pushButton_clicked()
{
    qDebug()<<"pressure clicked";
    if(ui->widget != nullptr)
    {
        delete ui->widget;
        ui->widget = nullptr;
    }
}
void SettingsTab::on_password_pushButton_clicked()
{
    qDebug()<<"password clicked";
    if(ui->widget != nullptr)
    {
        delete ui->widget;
        ui->widget = nullptr;
    }
}
