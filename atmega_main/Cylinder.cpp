#include "Cylinder.h"
#include "Arduino.h"

#include "atm_esp_exchange.h"

Cylinder::Cylinder(const int &pf,const int &pb, const uint16_t &pa):
    pinManaged(pf), pinPermanent(pb), analogValue(pa), target(0), reactionDirection(0)
{
    pinMode(pinManaged, OUTPUT);
    pinMode(pinPermanent, OUTPUT);

    digitalWrite(pinManaged, LOW);
    digitalWrite(pinPermanent, LOW);
}

void Cylinder::setPinLow(uint8_t val)
{
    switch(val)
    {
        case 0:
            digitalWrite(pinManaged, LOW);
            break;
        case 1:
            digitalWrite(pinPermanent, LOW);
            break;
    }
}

void Cylinder::performAction()
{
    if (target > MIN_SIGNAL_ATMEGA)
    {
        digitalWrite(pinPermanent, HIGH);
        if( (analogValue - target ) * (reactionDirection == 0 ? 1: -1) > 0)
            digitalWrite(pinManaged, HIGH);
        else
            digitalWrite(pinManaged, LOW);
        
    } else {
        digitalWrite(pinManaged, LOW);
        digitalWrite(pinPermanent, LOW);
    }
}

void Cylinder::performAction(const uint8_t &md, const uint16_t &cntr)
{
    uint16_t a,b;
    if(md > 0) {
        a = f_position1;
        b = f_position2;
    } else {
        a = b_position1;
        b = b_position2;
    }
    if (target > MIN_SIGNAL_ATMEGA)
    {
        //TODO check with actions on assignment
        if ( cntr < a || cntr > b)
            return;

        digitalWrite(pinPermanent, HIGH);
        if( (analogValue - target ) * (reactionDirection == 0 ? 1: -1) > 0)
            digitalWrite(pinManaged, HIGH);
        else
            digitalWrite(pinManaged, LOW);
        
    } else {
        digitalWrite(pinManaged, LOW);
        digitalWrite(pinPermanent, LOW);
    }
}

