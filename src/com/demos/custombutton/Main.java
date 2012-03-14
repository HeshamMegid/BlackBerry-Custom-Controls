package com.demos.custombutton;

import net.rim.device.api.ui.UiApplication;

public class Main extends UiApplication {
	Main() {
		UiApplication.getUiApplication().pushScreen(new ScreenMain());
	}

	public static void main(String[] args) {
		Main appMain = new Main();
		appMain.enterEventDispatcher();
	}
}
