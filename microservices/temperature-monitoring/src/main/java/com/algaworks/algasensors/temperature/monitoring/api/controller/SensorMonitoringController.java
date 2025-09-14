package com.algaworks.algasensors.temperature.monitoring.api.controller;

import com.algaworks.algasensors.temperature.monitoring.api.model.SensorMonitoringOutput;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorId;
import com.algaworks.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.algaworks.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import io.hypersistence.tsid.TSID;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sensors/{sensorId}/monitoring")
@RequiredArgsConstructor
public class SensorMonitoringController {

  private final SensorMonitoringRepository sensorMonitoringRepository;

  @GetMapping
  public SensorMonitoringOutput getDetail(@PathVariable TSID sensorId) {

    SensorMonitoring sensorMonitoring = findByIdOrDefault(sensorId);

    return SensorMonitoringOutput.builder()
        .id(sensorMonitoring.getId().getValue())
        .enabled(sensorMonitoring.getEnabled())
        .lastTemperature(sensorMonitoring.getLastTemperature())
        .updatedAt(sensorMonitoring.getUpdatedAt())
        .build();
  }

  private SensorMonitoring findByIdOrDefault(TSID sensorId) {
    SensorMonitoring sensorMonitoring = sensorMonitoringRepository.findById(new SensorId(sensorId))
      .orElse(SensorMonitoring.builder()
          .id(new SensorId(sensorId))
          .enabled(false)
          .lastTemperature(null)
          .updatedAt(null)
          .build());
    return sensorMonitoring;
  }

  @PutMapping("/enable")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void enable(@PathVariable TSID sensorId) {
     SensorMonitoring sensorMonitoring =  findByIdOrDefault(sensorId);
     if (sensorMonitoring.getEnabled() ){
       throw  new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
    }
     sensorMonitoring.setEnabled(true);
      sensorMonitoringRepository.save(sensorMonitoring);
  }

  @DeleteMapping("/enable")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @SneakyThrows
  public void disable(@PathVariable TSID sensorId) {
    SensorMonitoring sensorMonitoring =  findByIdOrDefault(sensorId);
    if (!sensorMonitoring.getEnabled()) {
      Thread.sleep(Duration.ofSeconds(10));
    }
    sensorMonitoring.setEnabled(false);
    sensorMonitoringRepository.save(sensorMonitoring);
  }



}
