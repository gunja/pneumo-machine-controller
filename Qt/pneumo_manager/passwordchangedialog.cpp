#include "passwordchangedialog.h"
#include "ui_passwordchangedialog.h"
#include <QMessageBox>

PasswordChangeDialog::PasswordChangeDialog(const QString &p, QWidget *parent) :
    QDialog(parent),
    ui(new Ui::PasswordChangeDialog)
  , currPass(p)
{
    ui->setupUi(this);
}

PasswordChangeDialog::~PasswordChangeDialog()
{
    delete ui;
}

QString PasswordChangeDialog::getNewPass(const QString &cur_pass, QWidget *parent, bool *ok)
{
    *ok = false;
    auto obj = new PasswordChangeDialog(cur_pass, parent);
    auto rv = obj->exec();
    if (rv != QDialog::Accepted)
        return QString("");
    else
        *ok = true;

    auto ret = obj->ui->newPass_lineEdit->text();
    delete obj;

    return ret;
}

void PasswordChangeDialog::on_buttonBox_accepted()
{
    if (ui->newPass_lineEdit->text() != ui->newPass2_lineEdit->text())
    {
        QMessageBox::information(this, "Новый пароль", "Новый пароль не совпадает");
        //msg.exec();
        return;
    }
    if (ui->curPass_lineEdit->text() != currPass)
    {
        QMessageBox::information(this, "Текущий пароль", "Введённый пароль не верен");
        //msg.exec();
        return;
    }
    emit accept();
}
