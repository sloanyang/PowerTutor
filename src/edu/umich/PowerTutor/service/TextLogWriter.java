package edu.umich.PowerTutor.service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import edu.umich.PowerTutor.phone.PhoneConstants;
import edu.umich.PowerTutor.util.BatteryStats;
import edu.umich.PowerTutor.util.NotificationService;
import edu.umich.PowerTutor.util.SystemInfo;

public class TextLogWriter implements LogWriter {
  private OutputStreamWriter logStream;

  public TextLogWriter(OutputStreamWriter logStream) {
    this.logStream = logStream;
  }

  @Override
  public void writeIterationHeader(long iter, int totalPower) throws IOException {
    logStream.write("begin " + iter + "\n");
    logStream.write("total-power " + (long) Math.round(totalPower) + '\n');
  }

  @Override
  public void writeSingleDataPoint(long iteration, String name, int uid, PowerData powerData) throws IOException {
    long roundedCachePower = (long) Math.round(powerData.getCachedPower());
    if (uid == SystemInfo.AID_ALL) {
      logStream.write(name + " " + roundedCachePower + "\n");
      logStream.write(powerData.getLogDataInfo());
    } else {
      logStream.write(name + "-" + uid + " " + roundedCachePower + "\n");
    }
  }

  @Override
  public void writeMemInfo(long[] memInfo) throws IOException {
    logStream.write("meminfo " + memInfo[0] + " " + memInfo[1] + " " + memInfo[2] + " " + memInfo[3] + "\n");
  }

  @Override
  public void writeFirstIteration(BatteryStats bst, PhoneConstants phoneConstants, Map<Integer, String> uidAppIds)
      throws IOException {

    logStream.write("time " + System.currentTimeMillis() + "\n");
    Calendar cal = new GregorianCalendar();
    logStream.write("localtime_offset " + (cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET)) + "\n");
    logStream.write("model " + phoneConstants.modelName() + "\n");
    if (NotificationService.available()) {
      logStream.write("notifications-active\n");
    }
    if (bst.hasFullCapacity()) {
      logStream.write("batt_full_capacity " + bst.getFullCapacity() + "\n");
    }
    synchronized (uidAppIds) {
      for (int uid : uidAppIds.keySet()) {
        if (uid < SystemInfo.AID_APP) {
          continue;
        }
        logStream.write("associate " + uid + " " + uidAppIds.get(uid) + "\n");
      }
    }
  }

  @Override
  public void close() throws IOException {
    logStream.close();
  }
}
