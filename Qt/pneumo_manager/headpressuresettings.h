#ifndef HEADPRESSURESETTINGS_H
#define HEADPRESSURESETTINGS_H

#include <QDialog>

namespace Ui {
class HeadPressureSettings;
}

class HeadPressureSettings : public QDialog
{
    Q_OBJECT

public:
    explicit HeadPressureSettings(QWidget *parent = 0);
    ~HeadPressureSettings();

    QString getName() const;
    QString getFirstVal() const;
    QString getSecondVal() const;

public slots:
    void setName(QString nm);

private:
    Ui::HeadPressureSettings *ui;
};

#endif // HEADPRESSURESETTINGS_H
