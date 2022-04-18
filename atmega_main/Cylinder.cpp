#include "Cylinder.h"
#include "Arduino.h"

Cylinder::Cylinder(const int &pf,const int &pb, const int &pa):
    pinForward(pf), pinBackwards(pb), pinAnalog(pa)
{
    pinMode(pinForward, OUTPUT);
    pinMode(pinBackwards, OUTPUT);

    pinMode(pinAnalog, INPUT);

    digitalWrite(pinForward, LOW);
    digitalWrite(pinBackwards, LOW);
}
