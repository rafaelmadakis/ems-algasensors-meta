package com.algaworks.algasensors.temperature.monitoring.domain.service;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.algaworks.algasensors.temperature.monitoring.domain.model.TemperatureLogId;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;
import jakarta.transaction.Transactional;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemperatureMonitoringService {

  private final SensorMonitoringRepository sensorMonitoringRepository;
  private final TemperatureLogRepository temperatureLogRepository;

  @Transactional
  public void processTemperatureReading(TemperatureLogData temperatureLogData) {
//    log.info("processTemperatureReading");
//    if (temperatureLogData.getValue().equals(10.5)) {
//      throw new RuntimeException("Teste 10.5");
//    }
    sensorMonitoringRepository.findById(new SensorId(temperatureLogData.getSensorId()))
      .ifPresentOrElse(sensor -> handleSensorMonitoring(temperatureLogData, sensor),

          () ->  logIgnoreTemperature(temperatureLogData));

  }

  private void handleSensorMonitoring(TemperatureLogData temperatureLogData, SensorMonitoring sensor) {
      if (sensor.isEnabled()) {
         sensor.setLastTemperature(temperatureLogData.getValue());
         sensor.setUpdatedAt(OffsetDateTime.now());
         sensorMonitoringRepository.save(sensor);

        TemperatureLog temperatureLog = TemperatureLog.builder()
            .id(new TemperatureLogId(temperatureLogData.getId()))
            .registeredAt(temperatureLogData.getRegisteredAt())
            .value(temperatureLogData.getValue())
            .sensorId(new SensorId(temperatureLogData.getSensorId()))
            .build();

        temperatureLogRepository.save(temperatureLog);
        log.info("Temperature updated: SensoId {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());

      } else {
        logIgnoreTemperature(temperatureLogData);
      }
  }

  private void logIgnoreTemperature(TemperatureLogData temperatureLogData) {
     log.info("Temperature ignored: SensoId {} Temp {}", temperatureLogData.getSensorId(), temperatureLogData.getValue());
  }

}
