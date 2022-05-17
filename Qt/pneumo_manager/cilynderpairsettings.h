#ifndef CILYNDERPAIRSETTINGS_H
#define CILYNDERPAIRSETTINGS_H

#include <QWidget>

namespace Ui {
class CilynderPairSettings;
}

class CilynderPairSettings : public QWidget
{
    Q_OBJECT

public:
    explicit CilynderPairSettings(QWidget *parent = 0);
    ~CilynderPairSettings();

private:
    Ui::CilynderPairSettings *ui;
};

#endif // CILYNDERPAIRSETTINGS_H
