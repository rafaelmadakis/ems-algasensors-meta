package com.algaworks.algasensors.device.management;

import io.hypersistence.tsid.TSID;
import org.junit.jupiter.api.Test;

public class TSIDTest {

  @Test
  void shouldGenerateTSID() {

    TSID tsidF = TSID.Factory.getTsid();

    TSID tsid = TSID.fast();
    System.out.println(tsid);
    System.out.println(tsid.toLong());
    System.out.println(tsid.getInstant());

    System.out.println(tsidF.getInstant());

  }

}
