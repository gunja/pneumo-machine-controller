#ifndef POINTSHEADER_H
#define POINTSHEADER_H

#include <QWidget>
#include "pairpoints.h"

namespace Ui {
class PointsHeader;
}

class PointsHeader : public QWidget
{
    Q_OBJECT

public:
    explicit PointsHeader(QWidget *parent = 0);
    ~PointsHeader();

private:
    Ui::PointsHeader *ui;
    PairPoints *pps[4];
    bool has_third_pair;
    bool has_forth_pair;
};

#endif // POINTSHEADER_H
