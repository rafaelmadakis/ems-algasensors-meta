package com.algaworks.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TemperatureLog {

  @Id
  @AttributeOverride(name = "value", column = @Column(name = "id", columnDefinition = "UUID"))
  private TemperatureLogId id;


  @Column(name = "\"value\"")
  private Double value;

  private OffsetDateTime registeredAt;

  @Embedded
  @AttributeOverride(name = "value", column = @Column(name = "sensor_id", columnDefinition = "BIGINT"))
  private SensorId sensorId;

}
