package com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import static com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_ALERTING;
import static com.algaworks.algasensors.temperature.monitoring.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_PROCESS_TEMPERATURE;

import com.algaworks.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.algaworks.algasensors.temperature.monitoring.domain.service.SensorAlertService;
import com.algaworks.algasensors.temperature.monitoring.domain.service.TemperatureMonitoringService;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

  private  final TemperatureMonitoringService temperatureMonitoringService;
  private final SensorAlertService sensorAlertService;

  @RabbitListener(queues = QUEUE_PROCESS_TEMPERATURE, concurrency = "2-3")
  @SneakyThrows
  public void handleProcessTemperature(@Payload TemperatureLogData temperatureLogData) {
    temperatureMonitoringService.processTemperatureReading(temperatureLogData);
//    Thread.sleep(Duration.ofSeconds(5));
  }

  @RabbitListener(queues = QUEUE_ALERTING, concurrency = "2-3")
  @SneakyThrows
  public void handleAlerting(@Payload TemperatureLogData temperatureLogData) {
    sensorAlertService.handleAlert(temperatureLogData);

    Thread.sleep(Duration.ofSeconds(5));
  }



}
