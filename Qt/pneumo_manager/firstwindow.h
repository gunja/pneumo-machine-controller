#ifndef FIRSTWINDOW_H
#define FIRSTWINDOW_H

#include <QDialog>

namespace Ui {
class FirstWindow;
}

class FirstWindow : public QDialog
{
    Q_OBJECT

public:
    explicit FirstWindow(QWidget *parent = 0);
    ~FirstWindow();

public slots:
    //void on_tabWidget_tabBarClicked(int);
    void on_tabWidget_currentChanged(int index);
    void on_tab_4_newPass(QString);
    void on_tab_4_envoiAPname(QString ap);

signals:
    void setTab(int);
    void setPresentedPairs(bool, bool);

private:
    Ui::FirstWindow *ui;
    int lastTabIndex;
    QString latestPassword;
};

#endif // FIRSTWINDOW_H
