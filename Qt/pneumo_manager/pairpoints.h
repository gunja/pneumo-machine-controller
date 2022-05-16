#ifndef PAIRPOINTS_H
#define PAIRPOINTS_H

#include <QWidget>

namespace Ui {
class PairPoints;
}

class PairPoints : public QWidget
{
    Q_OBJECT

public:
    explicit PairPoints(int base_index, QWidget *parent = 0);
    ~PairPoints();

private:
    Ui::PairPoints *ui;
};

#endif // PAIRPOINTS_H
