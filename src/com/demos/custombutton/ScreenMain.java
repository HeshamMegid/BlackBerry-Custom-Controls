package com.demos.custombutton;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.XYEdges;
import net.rim.device.api.ui.container.MainScreen;

import com.heshammegid.customcontrols.CustomButtonField;

public class ScreenMain extends MainScreen {
	
	public ScreenMain() {
		XYEdges edges = new XYEdges(5, 0, 0, 5); 
		
		CustomButtonField customButton = new CustomButtonField("Button 1", Color.BLUE, Color.DARKCYAN, Field.FIELD_HCENTER);
		customButton.setTextColor(Color.WHITE);
		customButton.setButtonWidth(200);
		customButton.setButtonHeight(60);
		customButton.setPadding(edges);
		add(customButton);
				
		Bitmap background = Bitmap.getBitmapResource("button_background.png");
		Bitmap focusBackground = Bitmap.getBitmapResource("button_background_focus.png");
		
		CustomButtonField customButton2 = new CustomButtonField("Button 2", background, focusBackground, Field.FIELD_HCENTER);
		customButton2.setBackgroundColor(Color.RED);
		customButton2.setfocusBackgroundColor(Color.GREEN);
		customButton2.setRoundedEdges(true);
		customButton2.setFont(Font.getDefault().derive(Font.BOLD, 6, Ui.UNITS_pt));
		customButton2.setPadding(edges);
		add(customButton2);

		
		String backgroundImageUrl = "http://hesh.am/playground/button_background.png";
		String focusBackgroundImageUrl = "http://hesh.am/playground/button_background_focus.png";
		
		CustomButtonField customButton3 = new CustomButtonField("Button 3", backgroundImageUrl, focusBackgroundImageUrl, Field.FIELD_HCENTER);
		Bitmap defaultBackground = Bitmap.getBitmapResource("default_background_image.png");
		customButton3.setBackgroundImage(defaultBackground); // Place a default image till the background image is downloaded
		customButton3.setPadding(edges);
		add(customButton3);
	}
}