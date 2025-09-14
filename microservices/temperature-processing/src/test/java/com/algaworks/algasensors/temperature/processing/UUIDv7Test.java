package com.algaworks.algasensors.temperature.processing;

import com.algaworks.algasensors.temperature.processing.common.IdGenerator;
import java.time.OffsetDateTime;

//import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.junit.jupiter.api.Test;

public class UUIDv7Test {

  @Test
  void shouldGenerateUUIDv7() {
    UUID uuid = IdGenerator.generateTimeBaseUUID();

//    OffsetDateTime uuidDateTime = UUIDv7Utils.extractOffsetDateTime(uuid).truncatedTo());
    OffsetDateTime now = OffsetDateTime.now();
  }
}