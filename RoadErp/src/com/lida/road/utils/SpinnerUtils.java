package com.lida.road.utils;

import android.widget.Spinner;

public class SpinnerUtils {
	/**
	 * 根据给定的string设置spinner选中的位置
	 */
	public static void setPosition(Spinner spinner, String selectValue,
			String[] spinnerValues,SelectResult selectResult) {
		if (selectValue == null || selectValue.equals("")) {
			return;
		}
		if (spinnerValues == null || spinnerValues.length == 0) {
			return;
		}
		int position = -1;
		for (int i = 0; i < spinnerValues.length; i++) {
			if (selectValue.equals(spinnerValues[i])) {
				position = i;
			}
		}
		if (position != -1) {
			spinner.setSelection(position);
			if (selectResult!=null) {
				selectResult.refresh(position);
			}
		}
	}

	public static void setPosition(Spinner spinner, String selectValue,
			String[] spinnerValues){
		setPosition(spinner, selectValue, spinnerValues,null);
	}
	public interface SelectResult{
		public void refresh(int i);
	}
}
