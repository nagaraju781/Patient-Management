package com.nag.analyticsservice.kafka;

import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Service
public class KafkaConsumer {


    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "patient", groupId = "analytics-service")
    public void consumeEvent(byte[] event){

        try {
            PatientEvent patient = PatientEvent.parseFrom(event);

            log.info("Recieved patient event:[patientid:{} name:{} email: {}]", patient.getPatientId(), patient.getName(), patient.getEmail());
        } catch (InvalidProtocolBufferException e) {
            log.error("Error cannot convert event",event);
        }

    }
}
