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
    void setPass(const QString cp);
    void setPairsPresented(bool, bool);

signals:
    void newPass (QString);
    void envoiAPname(QString);

private:
    Ui::SettingsTab *ui;
    QString curPass;
    void unset_title_buttons();
    bool has_third_pair;
    bool has_forth_pair;
};

#endif // SETTINGSTAB_H
