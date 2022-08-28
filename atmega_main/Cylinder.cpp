#include "Cylinder.h"
#include "Arduino.h"

#include "atm_esp_exchange.h"

Cylinder::Cylinder(const int &id, const int pf,const int pb, const int16_t &pa):
    pinManaged(pf), pinPermanent(pb), analogValue(pa), target(0), reactionDirection(0), prop_id(id)
{
    pinMode(pinManaged, OUTPUT);
    pinMode(pinPermanent, OUTPUT);

    digitalWrite(pinManaged, LOW);
    digitalWrite(pinPermanent, LOW);

    Serial.print("id="); Serial.print(prop_id); Serial.print("  pinManaged="); Serial.print(pinManaged); Serial.print("  pinPermanent="); Serial.println(pinPermanent);
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
  //Serial.print("performAction tgt="); Serial.print(target); Serial.print("  analogValue="); Serial.print(analogValue); Serial.print(" reactionDirection="); Serial.print((int)reactionDirection);
  //Serial.print("  pinPerm="); Serial.print(pinPermanent); Serial.print("  pinManaged="); Serial.print(pinManaged); 
    if (target > MIN_SIGNAL_ATMEGA)
    { //Serial.println("");
      //Serial.print("id="); Serial.print(prop_id); Serial.print("  MANAGING "); Serial.print(pinPermanent); Serial.print("  to HIGH");
        digitalWrite(pinPermanent, HIGH);
        int16_t vv = analogValue - target; vv *= (reactionDirection == 0 ? 1: -1);
        //Serial.print("   calculated dir value "); Serial.print(vv); 
        if( (analogValue - target ) * (reactionDirection == 0 ? 1: -1) > 0)
        {
          //Serial.print(" setting HIGH of "); Serial.print( pinManaged);
            digitalWrite(pinManaged, HIGH);
        }
        else {
          //Serial.print(" setting LOW of "); Serial.print( pinManaged);
            digitalWrite(pinManaged, LOW);
        }
        
    } else {
        digitalWrite(pinManaged, LOW);
        digitalWrite(pinPermanent, LOW);
    }
    //Serial.println("");
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

void Cylinder::setTarget(const uint16_t v)
{
  target = v;
    Serial.print("cyl #"); Serial.print((int)prop_id); Serial.print("  target="); Serial.print(target); Serial.print("  from v="); Serial.println(v);
};

void Cylinder::setDirection(const uint16_t v)
{
  reactionDirection = v;
  Serial.print("cyl #"); Serial.print((int)prop_id); Serial.print("  reactiondir="); Serial.print(reactionDirection); Serial.print("  from v="); Serial.println(v);
};
