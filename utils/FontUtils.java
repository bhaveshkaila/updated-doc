

public class FontUtils {

	public static Typeface fontHelveticaNeueRegular;
	public static Typeface fontHelveticaNeueBold;
	public static Typeface fontQuartzMS;

    public interface FONTS {
        String FONT_HELVETICANEUE_BOLD = "HelveticaNeue-Bold";
        String FONT_HELVETICANEUE = "HelveticaNeue";
        String FONT_QUARTZMS= "QuartzMS";
    }

	public static Integer INPUT_FIELD_FONT_SIZE;

	public static void loadFonts(Context mContext) {

		fontHelveticaNeueRegular = Typeface.createFromAsset(
				mContext.getAssets(),
				FONTS.FONT_HELVETICANEUE+".ttf");

		fontHelveticaNeueBold = Typeface.createFromAsset(
				mContext.getAssets(),FONTS.FONT_HELVETICANEUE_BOLD+".ttf");
//		fontQuartzMS = Typeface.createFromAsset(
//				mContext.getAssets(),FONTS.FONT_QUARTZMS+".ttf");

		INPUT_FIELD_FONT_SIZE = Integer.valueOf(mContext.getResources()
				.getString(R.string.InputFieldFontSize));
	}

	public static void setLayoutFont(Typeface tf, Integer textSize,
			Integer color, View... params) {
		for (View view : params) {
			// button and editText extends textview
			if (view instanceof TextView) {
				TextView tv = (TextView) view;
				if (tf != null)
					tv.setTypeface(tf);
				if (textSize != null)
					tv.setTextSize(textSize);
				if (color != null)
					tv.setTextColor(color);
			}
		}
	}

	public static Typeface getFontType(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomText);
        CharSequence fontType = a.getString(R.styleable.CustomText_font_type);

//		String bold = attrs.getAttributeValue(
//				"http://schemas.android.com/apk/res/android", "textStyle");
	/*	
		String quartzMSStyle =  attrs.getAttributeValue(
				"http://schemas.android.com/apk/res/android", "textStyle");*/

		if (fontType != null) {
			if (fontType.equals(FONTS.FONT_HELVETICANEUE_BOLD)) {
				return fontHelveticaNeueBold;
			} else {
				return fontHelveticaNeueRegular;
			}
		} else {
			return fontHelveticaNeueRegular;
		} 
	}
	
	public static Typeface getQuartzFontType(Context context, AttributeSet attrs) {
		
		return fontQuartzMS;
	}
}
