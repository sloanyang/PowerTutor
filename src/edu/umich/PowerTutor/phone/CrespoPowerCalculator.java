package edu.umich.PowerTutor.phone;

import android.content.Context;

public class CrespoPowerCalculator extends DreamPowerCalculator {

  public CrespoPowerCalculator(Context context) {
    super(new CrespoConstants(context));
  }

  public CrespoPowerCalculator(PhoneConstants coeffs) {
    super(coeffs);
  }

}
