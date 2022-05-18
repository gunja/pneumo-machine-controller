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

signals:
    void clicked();
    void clicked(int);

public slots:
    void setIndex(int);
    void setName(QString);

private:
    Ui::HeadingMeasure *ui;
    bool wasClicked;
    int propertyIndex;
protected:
    void mousePressEvent(QMouseEvent *event);
    void mouseReleaseEvent(QMouseEvent *event);
};

#endif // HEADINGMEASURE_H
