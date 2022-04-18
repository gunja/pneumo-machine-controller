#ifndef CYLINDER_PAIR_H
#define CYLINDER_PAIR_H
#include "Cylinder.h"

class CylinderPair {
    Cylinder & a ,b;
  public:
    CylinderPair(Cylinder &_a, Cylinder &_b): a(_a), b(_b)
        {};
};

#endif
