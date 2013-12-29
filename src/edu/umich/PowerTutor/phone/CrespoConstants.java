/*
Copyright (C) 2011 The University of Michigan

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Please send inquiries to powertutor@umich.edu
 */

package edu.umich.PowerTutor.phone;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class CrespoConstants extends DreamConstants {
  protected int screenWidth;
  protected int screenHeight;

  public CrespoConstants(Context context) {
    super(context);
    DisplayMetrics metrics = new DisplayMetrics();
    WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    windowManager.getDefaultDisplay().getMetrics(metrics);
    screenWidth = metrics.widthPixels;
    screenHeight = metrics.heightPixels;
  }

  public String modelName() {
    return "crespo";
  }

  public double maxPower() { /* WARNING: UNCHANGED */
    return 2800;
  }

  public double lcdBrightness() {
    return 1.72686; /* WARNING: UNCHANGED */
  }

  public double lcdBacklight() {
    return 340.8305; /* WARNING: UNCHANGED */
  }

  private static final double[] arrayCpuPowerRatios = { 0.554, 0.821, 1.137, 2.054, 2.59 };

  public double[] cpuPowerRatios() {
    return arrayCpuPowerRatios;
  }

  private static final double[] arrayCpuFreqs = { 100, 200, 400, 800, 1000 };

  public double[] cpuFreqs() {
    return arrayCpuFreqs;
  }

  public double audioPower() {
    return 88;
  }

  private static final double[] arrayGpsStatePower = { 0.0, 17.5, 50 };

  public double[] gpsStatePower() {
    return arrayGpsStatePower;
  }

  public double gpsSleepTime() {
    return 6.0;
  }

  public double wifiLowPower() {
    return 4;
  }

  public double wifiHighPower() {
    return 4;
  }

  public double wifiLowHighTransition() {
    return 8;
  }

  public double wifiHighLowTransition() {
    return 5;
  }

  /*
   * 
   * <-- from Fuelgauge: BIT RATE / private double getAverageDataCost() { final
   * long WIFI_BPS = 1000000; // TODO: Extract average bit rates from system
   * final long MOBILE_BPS = 200000; // TODO: Extract average bit rates from
   * system final double WIFI_POWER =
   * mPowerProfile.getAveragePower(PowerProfile.POWER_WIFI_ACTIVE) / 3600; final
   * double MOBILE_POWER =
   * mPowerProfile.getAveragePower(PowerProfile.POWER_RADIO_ACTIVE) / 3600;
   * final long mobileData = mStats.getMobileTcpBytesReceived(mStatsType) +
   * mStats.getMobileTcpBytesSent(mStatsType); final long wifiData =
   * mStats.getTotalTcpBytesReceived(mStatsType) +
   * mStats.getTotalTcpBytesSent(mStatsType) - mobileData; final long
   * radioDataUptimeMs = mStats.getRadioDataUptime() / 1000; final long
   * mobileBps = radioDataUptimeMs != 0 ? mobileData * 8 * 1000 /
   * radioDataUptimeMs : MOBILE_BPS;
   * 
   * double mobileCostPerByte = MOBILE_POWER / (mobileBps / 8); double
   * wifiCostPerByte = WIFI_POWER / (WIFI_BPS / 8); if (wifiData + mobileData !=
   * 0) { return (mobileCostPerByte * mobileData + wifiCostPerByte * wifiData) /
   * (mobileData + wifiData); } else { return 0; } }
   */
  private static final double[] arrayWifiLinkRatios = { 120 / (6.5 / 8 * 1000 * 1000) };

  public double[] wifiLinkRatios() {
    return arrayWifiLinkRatios;
  }

  /* ONLY ONE LINK SPEED */
  private static final double[] arrayWifiLinkSpeeds = { 65 };

  public double[] wifiLinkSpeeds() {
    return arrayWifiLinkSpeeds;
  }

  public String threegInterface() {
    return "rmnet0";
  }

  public double threegIdlePower(String oper) {
    if (OPER_TMOBILE.equals(oper)) {
      return 3.4;
    }
    return 3.4; // Return the worst case for unknown operators.
  }

  public double threegFachPower(String oper) {
    if (OPER_TMOBILE.equals(oper)) {
      return 185.0;
    }
    return 185.0; // Return the worst case for unknown operators.
  }

  public double threegDchPower(String oper) {
    if (OPER_TMOBILE.equals(oper)) {
      return 185.0;
    }
    return 185.0; // Return the worst case for unknown operators.
  }

  public double getMaxPower(String componentName) {
    if ("OLED".equals(componentName)) {
      double[] channel = oledChannelPower();
      return oledBasePower() + 255 * screenWidth * screenHeight
          * (channel[0] + channel[1] + channel[2] - oledModulation());
    }
    return super.getMaxPower(componentName);
  }
}
