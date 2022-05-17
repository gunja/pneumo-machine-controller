#ifndef PRESSURESETTINGS_H
#define PRESSURESETTINGS_H

#include <QWidget>
#include <cilynderpairsettings.h>

namespace Ui {
class PressureSettings;
}

class PressureSettings : public QWidget
{
    Q_OBJECT

public:
    explicit PressureSettings(bool, bool, QWidget *parent = 0);
    ~PressureSettings();

public slots:
    void on_widget_clicked();
    void on_widget_1_clicked();
    void on_widget_2_clicked();
    void on_widget_3_clicked();

private:
    Ui::PressureSettings *ui;
    bool has_third_pair;
    bool has_forth_pair;
    CilynderPairSettings *cps[2];
};

#endif // PRESSURESETTINGS_H
