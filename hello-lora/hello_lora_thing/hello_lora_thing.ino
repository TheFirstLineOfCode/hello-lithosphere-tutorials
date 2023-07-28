#include <Arduino_BuiltIn.h>

#include <thing.h>
#include <tuxp.h>
#include <mcu_board_adaptation.h>
#include <radio_module_adaptation.h>

#define LED_PIN 12

void setup() {
  configureMcuBoard("HLT");
  configureRadioModule();
  
  // resetThing();
  registerThingIdLoader(generateThingIdUsingUniqueIdLibrary);
  registerRegistrationCodeLoader(loadRegistrationCode);

  registerThingProtocolsConfigurer(configureThingProtocolsImpl);
  
  pinMode(LED_PIN, OUTPUT);
  
  toBeAThing();
}

char *loadRegistrationCode() {
  return "abcdefghijkl";
}

void turnLedOn() {
  digitalWrite(LED_PIN, HIGH);
}

void turnLedOff() {
  digitalWrite(LED_PIN, LOW);
}

void flashLed() {
  digitalWrite(LED_PIN, HIGH);
  delay(200);
  digitalWrite(LED_PIN, LOW);
}

int8_t processFlash(Protocol *protocol) {
  int repeat;
  if (!getIntAttributeValue(protocol, 0x01, &repeat)) {
    repeat = 1;
  }

  if (repeat <= 0 || repeat > 8)
    return -1;

  for (int i = 0; i < repeat; i++) {
    flashLed();
    delay(500);
  }

  return 0;
}

int8_t processTurnOn(Protocol *protocol) {
  turnLedOn();
  return 0;
}

int8_t processTurnOff(Protocol *protocol) {
  turnLedOff();
  return 0;
}

void configureThingProtocolsImpl() {
  ProtocolName pnFlash = {0xf7, 0x01, 0x00};
  registerActionProtocol(pnFlash, processFlash, false);

  ProtocolName pnTurnOn = {0xf7, 0x01, 0x02};
  registerActionProtocol(pnTurnOn, processTurnOn, false);

  ProtocolName pnTurnOff = {0xf7, 0x01, 0x03};
  registerActionProtocol(pnTurnOff, processTurnOff, false);
}

void loop() {
  int result = doWorksAThingShouldDo();
  if (result != 0) {
#ifdef ENABLE_DEBUG
    Serial.print(F("Error occurred when the thing does the works it should do. Error number: "));
    Serial.print(result);
    Serial.println(F("."));    
#endif*
  }
}
