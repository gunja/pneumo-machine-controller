#ifndef SETTINGSTAB_H
#define SETTINGSTAB_H

#include <QWidget>

namespace Ui {
class SettingsTab;
}

class SettingsTab : public QWidget
{
    Q_OBJECT

public:
    explicit SettingsTab(QWidget *parent = 0);
    ~SettingsTab();

public slots:
    void on_points_pushButton_clicked();
    void on_ap_pushButton_clicked();
    void on_pressure_pushButton_clicked();
    void on_password_pushButton_clicked();

private:
    Ui::SettingsTab *ui;
};

#endif // SETTINGSTAB_H
