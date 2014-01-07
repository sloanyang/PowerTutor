package edu.umich.PowerTutor.service;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import edu.umich.PowerTutor.phone.PhoneConstants;
import edu.umich.PowerTutor.util.BatteryStats;

public class CsvLogWriter implements LogWriter {
  private OutputStreamWriter osw;
  private static String SEP = ";";
  private static String NL = "\r\n";

  public CsvLogWriter(OutputStreamWriter osw) {
    this.osw = osw;
  }

  @Override
  public void writeIterationHeader(long iter, int totalPower) throws IOException {
  }

  @Override
  public void writeSingleDataPoint(long iteration, String name, int uid, PowerData powerData) throws IOException {
    writeCell(name);
    writeCell("" + iteration);
    writeCell("" + uid);
    if (powerData != null) {
      for (String value : powerData.getCsvLogDataInfo()) {
        writeCell(value);
      }
    }
    osw.write(NL);
    osw.flush();
  }

  public void writeCell(String string) throws IOException {
    osw.append("\"" + string + "\"").append(SEP);
  }

  @Override
  public void writeMemInfo(long[] memInfo) throws IOException {
  }

  @Override
  public void writeFirstIteration(BatteryStats bst, PhoneConstants phoneConstants, Map<Integer, String> uidAppIds)
      throws IOException {
  }

  @Override
  public void close() throws IOException {
    osw.close();
  }

}
