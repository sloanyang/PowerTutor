package edu.umich.PowerTutor.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.InflaterInputStream;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

public class LogWriter {
  public static void writeLogAsync(final Context context) {
    new Thread() {
      public void start() {
        writeLogSync(context, "", FileType.LOG);
        writeLogSync(context, "", FileType.CSV);
      }
    }.start();
  }

  public static void writeLogSync(final Context context, String testDescription, FileType fileType) {
    String cleanTestDescription = testDescription.replaceAll("\\W+", "_");

    File writeFile = new File(Environment.getExternalStorageDirectory(), "PowerTrace-" + cleanTestDescription + "-"
        + System.currentTimeMillis() + "." + fileType.getExtension());
    try {
      InflaterInputStream logIn = new InflaterInputStream(
          context.openFileInput("PowerTrace." + fileType.getExtension()));
      BufferedOutputStream logOut = new BufferedOutputStream(new FileOutputStream(writeFile));

      byte[] buffer = new byte[20480];
      for (int ln = logIn.read(buffer); ln != -1; ln = logIn.read(buffer)) {
        logOut.write(buffer, 0, ln);
      }
      logIn.close();
      logOut.close();
      Toast.makeText(context, "Wrote log to " + writeFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
      return;
    } catch (java.io.EOFException e) {
      Toast.makeText(context, "Wrote log to " + writeFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
      return;
    } catch (IOException e) {
    }
    Toast.makeText(context, "Failed to write log to sdcard", Toast.LENGTH_SHORT).show();
  }

  public enum FileType {
    LOG("log"), CSV("csv");

    String extension = null;

    FileType(String extension) {
      this.extension = extension;
    }

    public String getExtension() {
      return extension;
    }

  }

}
