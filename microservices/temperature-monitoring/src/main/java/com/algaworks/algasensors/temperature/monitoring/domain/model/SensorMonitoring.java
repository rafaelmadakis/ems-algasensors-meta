package com.algaworks.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorMonitoring {

  @Id
  @AttributeOverride(name = "value", column = @jakarta.persistence.Column(name = "bigint"))
  private SensorId id;
  private Double lastTemperature;
  private OffsetDateTime updatedAt;
  private Boolean enabled;

  public boolean isEnabled() {
    return Boolean.TRUE.equals(enabled);
  }
}
