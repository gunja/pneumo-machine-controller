#ifndef HEADINGMEASURE_H
#define HEADINGMEASURE_H

#include <QWidget>

namespace Ui {
class HeadingMeasure;
}

class HeadingMeasure : public QWidget
{
    Q_OBJECT

public:
    explicit HeadingMeasure(QWidget *parent = 0);
    ~HeadingMeasure();

private:
    Ui::HeadingMeasure *ui;
};

#endif // HEADINGMEASURE_H
