package edu.umich.PowerTutor.service;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;

import edu.umich.PowerTutor.phone.PhoneConstants;
import edu.umich.PowerTutor.util.BatteryStats;

public interface LogWriter extends Closeable {

  public abstract void writeIterationHeader(long iter, int totalPower) throws IOException;

  public abstract void writeSingleDataPoint(long iteration, String name, int uid, PowerData powerData) throws IOException;

  public abstract void writeMemInfo(long[] memInfo) throws IOException;

  public abstract void writeFirstIteration(BatteryStats bst, PhoneConstants phoneConstants,
      Map<Integer, String> uidAppIds) throws IOException;

}