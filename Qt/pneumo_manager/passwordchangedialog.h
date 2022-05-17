#ifndef PASSWORDCHANGEDIALOG_H
#define PASSWORDCHANGEDIALOG_H

#include <QDialog>

namespace Ui {
class PasswordChangeDialog;
}

class PasswordChangeDialog : public QDialog
{
    Q_OBJECT

public:
    explicit PasswordChangeDialog(const QString &pass, QWidget *parent = 0);
    ~PasswordChangeDialog();
    static QString getNewPass(const QString &cur_pass, QWidget *parent = 0, bool *ok = nullptr);

public slots:
    void on_buttonBox_accepted();

private:
    Ui::PasswordChangeDialog *ui;
    QString currPass;
};

#endif // PASSWORDCHANGEDIALOG_H
