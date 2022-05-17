#include "cilynderpairsettings.h"
#include "ui_cilynderpairsettings.h"

CilynderPairSettings::CilynderPairSettings(QWidget *parent) :
    QWidget(parent),
    ui(new Ui::CilynderPairSettings)
{
    ui->setupUi(this);
}

CilynderPairSettings::~CilynderPairSettings()
{
    delete ui;
}
