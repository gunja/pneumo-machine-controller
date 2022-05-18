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
    void on_widget_1_clicked();
    void on_widget_2_clicked();
    void on_widget_3_clicked();
    void on_widget_4_clicked();
    void on_widget_5_clicked();
    void on_widget_6_clicked();
    void on_widget_7_clicked();
    void on_widget_8_clicked();

    void on_widget_clicked(int idx);


private:
    Ui::PressureSettings *ui;
    bool has_third_pair;
    bool has_forth_pair;
    CilynderPairSettings *cps[2];
    QString names[8];
};

#endif // PRESSURESETTINGS_H
