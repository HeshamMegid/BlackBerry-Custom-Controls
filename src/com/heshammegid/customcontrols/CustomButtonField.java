package com.heshammegid.customcontrols;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Ui;

public class CustomButtonField extends Field {
    
	// Set default size for button, used if no background image is set or the size is not explicitly set
	private int 		buttonWidth = 200; 
    private int		 	buttonHeight = 50;
    
    private boolean		roundedEdges = true;
    
    private String 		text;
    private Font		font;
    private int			textColor = -1;
    
    private Bitmap 		backgroundImage;
    private Bitmap		focusBackgroundImage;
    
    private int			backgroundColor = -1;
    private int			focusBackgroundColor = -1;
    
    // Arc width and height used for drawing the rounded edges of the button
    private static int 	ARC_WIDTH = 25;
    private static int 	ARC_HEIGHT = 25;
    
    /*
     * Constructors
     */
    
    public CustomButtonField(String text, long style) {
    	super(style);
    	this.text = text;
    	this.font = Font.getDefault().derive(Font.PLAIN, 7, Ui.UNITS_pt);
	}
    
    public CustomButtonField(String text, Bitmap backgroundImage, Bitmap focusBackgroundImage, long style) {
    	this(text, style);
    	this.backgroundImage = backgroundImage;
    	this.focusBackgroundImage = focusBackgroundImage;
    	
    	// Override the default size with the size of the background image
    	if (backgroundImage != null) {
	    	buttonWidth = backgroundImage.getWidth();
	    	buttonHeight = backgroundImage.getHeight();
    	}
    }
    
    /**
     * @param text
     * @param backgroundColor
     * @param focusBackgroundColor set to -1 to get default focus style
     * @param style
     */
    public CustomButtonField(String text, int backgroundColor, int focusBackgroundColor, long style) {
    	this(text, style);
    	this.backgroundColor = backgroundColor;
    	this.focusBackgroundColor = focusBackgroundColor;
    }
    
    public CustomButtonField(String text, Bitmap backgroundImage, Bitmap focusBackground, int backgroundColor, int focusBackgroundColor, long style) {
    	this(text, backgroundImage, focusBackground, style);
    	this.backgroundColor = backgroundColor;
    	this.focusBackgroundColor = focusBackgroundColor;    	
    }
    
	protected void layout(int width, int height) {
		setExtent(buttonWidth, buttonHeight);
	}

	protected void paint(Graphics graphics) {
		// Draw background/focus color
		if (isFocus() && focusBackgroundColor != -1) {
			graphics.setColor(focusBackgroundColor);
			
			if (roundedEdges)
				graphics.fillRoundRect(0, 0, buttonWidth, buttonHeight, ARC_WIDTH, ARC_HEIGHT);
			else
				graphics.fillRect(0, 0, buttonWidth, buttonHeight);
			
		} else if (!isFocus() && backgroundColor != -1) {
			graphics.setColor(backgroundColor);

			// If the button doesn't have a focus background color, force the drawing without rounded edges
			if (roundedEdges && focusBackgroundColor != -1)
				graphics.fillRoundRect(0, 0, buttonWidth, buttonHeight, ARC_WIDTH, ARC_HEIGHT);
			else
				graphics.fillRect(0, 0, buttonWidth, buttonHeight);
		}
		
		// Draw background/focus image
		if (isFocus() && focusBackgroundImage != null) {
			graphics.drawBitmap(buttonWidth / 2 - focusBackgroundImage.getWidth() / 2, 
					buttonHeight / 2 - focusBackgroundImage.getHeight() / 2,
					focusBackgroundImage.getWidth(),
					focusBackgroundImage.getHeight(),
					focusBackgroundImage, 0, 0);
		} else if (!isFocus() && backgroundImage != null) {
			graphics.drawBitmap(buttonWidth / 2 - backgroundImage.getWidth() / 2, 
					buttonHeight / 2 - backgroundImage.getHeight() / 2,
					backgroundImage.getWidth(),
					backgroundImage.getHeight(),
					backgroundImage, 0, 0);
		}
		
		if (font != null)
			graphics.setFont(font);
		
		if (textColor != -1)
			graphics.setColor(textColor);
		else
			graphics.setColor(Color.BLACK);
		
		// Draw button label
		if (text != null) {
			graphics.drawText(text, 0, buttonHeight / 2, DrawStyle.HCENTER | DrawStyle.VCENTER, buttonWidth);
		}
	}
	
    protected boolean navigationClick(int status, int time){
		 fieldChangeNotify(1);
		 return true;
   }
    
	public boolean isFocusable() {
		return true;
	}
	
	protected void drawFocus(Graphics graphics, boolean on) {
		// Only draw the default focus style if there isn't a focus background color or image set
		
		if (focusBackgroundImage == null && focusBackgroundColor == -1)
			super.drawFocus(graphics, on);
	}
	
   /*
    * Setters and getters
    */

	public int getButtonWidth() {
		return buttonWidth;
	}

	public void setButtonWidth(int buttonWidth) {
		this.buttonWidth = buttonWidth;
	}

	public int getButtonHeight() {
		return buttonHeight;
	}

	public void setButtonHeight(int buttonHeight) {
		this.buttonHeight = buttonHeight;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		invalidate();
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public Bitmap getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Bitmap backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public Bitmap getfocusBackgroundImage() {
		return focusBackgroundImage;
	}

	public void setfocusBackgroundImage(Bitmap focusBackgroundImage) {
		this.focusBackgroundImage = focusBackgroundImage;
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
	}

	public boolean isRoundedEdges() {
		return roundedEdges;
	}

	public void setRoundedEdges(boolean roundedEdges) {
		this.roundedEdges = roundedEdges;
	}

	public int getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(int backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public int getfocusBackgroundColor() {
		return focusBackgroundColor;
	}

	public void setfocusBackgroundColor(int focusBackgroundColor) {
		this.focusBackgroundColor = focusBackgroundColor;
	}
}
