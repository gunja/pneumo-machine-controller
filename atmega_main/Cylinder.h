#ifndef CYLINDER_H
#define CYLINDER_H

#include <stdint.h>

class Cylinder {
    int pinManaged, pinPermanent;
    const uint16_t &analogValue;
    uint16_t target;
    uint8_t reactionDirection;
    uint16_t f_position1;
    uint16_t f_position2;
    uint16_t b_position1;
    uint16_t b_position2;
    uint8_t prop_id;
  public:
    Cylinder(const int &id, const int pf,const int pb, const uint16_t &pa);
    void setPinLow(uint8_t);
    void performAction();
    void performAction(const uint8_t &md, const uint16_t &cntr);
    void setTarget(const uint16_t v);
    void setDirection(const uint16_t v);
    //void setPropId(const uint8_t i) { prop_id = i;};
};

#endif
