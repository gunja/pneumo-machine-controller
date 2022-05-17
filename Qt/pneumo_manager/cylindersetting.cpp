#include "cylindersetting.h"
#include "ui_cylindersetting.h"

CylinderSetting::CylinderSetting(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::CylinderSetting)
{
    ui->setupUi(this);
}

CylinderSetting::~CylinderSetting()
{
    delete ui;
}
