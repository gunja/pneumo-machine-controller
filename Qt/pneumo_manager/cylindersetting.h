#ifndef CYLINDERSETTING_H
#define CYLINDERSETTING_H

#include <QWidget>

namespace Ui {
class CylinderSetting;
}

class CylinderSetting : public QWidget
{
    Q_OBJECT

public:
    explicit CylinderSetting(QWidget *parent = 0);
    ~CylinderSetting();

private:
    Ui::CylinderSetting *ui;
};

#endif // CYLINDERSETTING_H
