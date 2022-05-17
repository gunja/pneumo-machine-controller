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
    explicit PointsHeader(bool, bool, QWidget *parent = 0);
    ~PointsHeader();

public slots:
    void on_d1_pushButton_clicked();
    void on_d2_pushButton_clicked();
    void on_d3_pushButton_clicked();
    void on_d4_pushButton_clicked();
    void on_fwd_pushButton_clicked();
    void on_bwd_pushButton_clicked();

private:
    Ui::PointsHeader *ui;
    PairPoints *pps[4];
    bool has_third_pair;
    bool has_forth_pair;
    void unset_bg_D_buttons();
    void unset_wd_buttons();
};

#endif // POINTSHEADER_H
