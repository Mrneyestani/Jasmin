package fr.jasmin.utils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import fr.jasmin.common.IConstant;
import fr.jasmin.entity.Category;
import fr.jasmin.entity.Item;
import fr.jasmin.entity.Order;
import fr.jasmin.enums.Gender;
import fr.jasmin.enums.Profile;

public final class DataTest implements IConstant {

	private static List<String> firstnameList;
	private static List<String> lastnameList;
	private static List<String> numberTypeList;
	private static List<String> streetNameList;
	private static List<String> cityList;
	private static List<String> streetTypeList;
	private static List<String> domainList;
	private static List<String> domainExtensionList;
	private static List<String> articleList;
	private static List<String> commentList;
	private static List<String> categoryList;
	private static List<String> itemList;
	private static List<String> itemDescriptionList;

	// ---------------------------------------------------------------------------------------------------
	public static Category genCategory() {

		return new Category(
				DataTest.categoryName(), 
				Utils.randInt(5, 90), 
				Utils.randInt(0, 1) > 0 ? true : false,
				DataTest.picUrl());

	}

	// ---------------------------------------------------------------------------------------------------
	public static String crypto() {

		return String.format("0.3%S", Utils.randInt(0, 999));

	}
	// ---------------------------------------------------------------------------------------------------
	public static String orderNumber() {
		
		return String.format("JSM-%s-%05d-FR-%02d", 
				Utils.date2String(DATE_NOW, "yyyyMMdd"),
				Utils.randInt(1, 99999),
				Utils.randInt(1, 99));
	}
	// ---------------------------------------------------------------------------------------------------
	public static String bankCardNumber() {

		int bin = Utils.randInt(111111, 999999);
		int digit8 = Utils.randInt(0, 99999999);

		return (String.format("%06d%08d", bin, digit8));
	}

	// ---------------------------------------------------------------------------------------------------

	public static Date birthDate() {

		int nbDay = Utils.randInt(1, 100 * 365);

		return Utils.addDate(DATE_NOW, -nbDay);
	}

	// ---------------------------------------------------------------------------------------------------
	public static String email(String firstname, String lastname) {

		return Utils.stripAccent(String.format("%s.%s@%s.%s", firstname.toLowerCase(), lastname.toLowerCase(),
				DataTest.domain(), DataTest.domainExtension()));
	}

	// ---------------------------------------------------------------------------------------------------
	public static String picUrl() {

		return DataTest.url() + ".jpg";
	}

	// ---------------------------------------------------------------------------------------------------
	public static String videoUrl() {

		return DataTest.url() + ".mp4";
	}

	// ---------------------------------------------------------------------------------------------------
	public static String url() {

		return String.format("http://www.%s.%s/%s", DataTest.domain(), DataTest.domainExtension(),
				DataTest.article().replace(' ', '-'));
	}

//---------------------------------------------------------------------------------------------------
	public static String phone() {

		return String.format("%05d", Utils.randInt(0, 9999)) + String.format("%05d", Utils.randInt(0, 99999));
	}

	// ---------------------------------------------------------------------------------------------------
	public static String pass(String inString) {

		char[] charTab = inString.toCharArray();
		int length = inString.length();

		for (int index = 0; index < length; index++) {
			if ((index % 2) == 0) {
				charTab[length - index - 1] = Character.toUpperCase(inString.charAt(index));
			} else
				charTab[length - index - 1] = Character.toLowerCase(inString.charAt(index));
		}

		return new String(charTab);
	}

//---------------------------------------------------------------------------------------------------
	public static String firstname() {

		return DataTest.firstname(Gender.OTHER);
	}

// ---------------------------------------------------------------------------------------------------
	public static String domainExtension() {
		if (domainExtensionList == null) {
			domainExtensionList = new ArrayList<String>();

			ResourceBundle myResource = ResourceBundle.getBundle("testData"); // retreive data from Dbase.properties
			String propertieValue = myResource.getString("domain.extension");
			String propertieArray[] = propertieValue.split(";");

			for (String value : propertieArray) {

				domainExtensionList.add(value.toLowerCase());
			}
		}
		int position = Utils.randInt(0, domainExtensionList.size() - 1);

		return Utils.stripAccent(domainExtensionList.get(position));

	}

// ---------------------------------------------------------------------------------------------------
			public static String domain() {
		if (domainList == null) {
			domainList = new ArrayList<String>();

			ResourceBundle myResource = ResourceBundle.getBundle("testData"); // retreive data from Dbase.properties
			String propertieValue = myResource.getString("domain");
			String propertieArray[] = propertieValue.split(";");

			for (String value : propertieArray) {

				domainList.add(value.toLowerCase());
			}
		}
		int position = Utils.randInt(0, domainList.size() - 1);

		return Utils.stripAccent(domainList.get(position));

	}

	// ---------------------------------------------------------------------------------------------------
	public static String firstname(Gender gender) {

		if (firstnameList == null) {
			firstnameList = new ArrayList<String>();

			ResourceBundle myResource = ResourceBundle.getBundle("testData"); // retreive data from Dbase.properties
			String propertieValue = myResource.getString("FirstName." + gender.getValue());
			String propertieArray[] = propertieValue.split(";");

			for (String value : propertieArray) {

				firstnameList.add(Utils.firstToUpper(value));
			}
		}
		int position = Utils.randInt(0, firstnameList.size() - 1);

		return firstnameList.get(position);
	}

//---------------------------------------------------------------------------------------------------
	public static String article() {
		return DataTest.itemName();
	}

//---------------------------------------------------------------------------------------------------
	public static String categoryName() {
		if (categoryList == null) {
			categoryList = new ArrayList<String>();

			ResourceBundle myResource = ResourceBundle.getBundle("testData"); // retreive data from Dbase.properties
			String propertieValue = myResource.getString("category");
			String propertieArray[] = propertieValue.split(";");

			for (String value : propertieArray) {

				categoryList.add(value);
			}
		}
		int position = Utils.randInt(0, categoryList.size() - 1);

		return categoryList.get(position);
	}

//---------------------------------------------------------------------------------------------------
	public static String lastname() {
		if (lastnameList == null) {
			lastnameList = new ArrayList<String>();

			ResourceBundle myResource = ResourceBundle.getBundle("testData"); // retreive data from Dbase.properties
			String propertieValue = myResource.getString("LastName");
			String propertieArray[] = propertieValue.split(";");

			for (String value : propertieArray) {

				lastnameList.add(Utils.firstToUpper(value));
			}
		}
		int position = Utils.randInt(0, lastnameList.size() - 1);

		return lastnameList.get(position);
	}

//---------------------------------------------------------------------------------------------------
	public static String numberType() {

		if (numberTypeList == null) {
			numberTypeList = new ArrayList<String>();

			ResourceBundle myResource = ResourceBundle.getBundle("testData"); // retreive data from Dbase.properties
			String propertieValue = myResource.getString("NumberType");
			String propertieArray[] = propertieValue.split(";");
			for (String value : propertieArray) {

				numberTypeList.add(value);
			}
		}
		int position = Utils.randInt(0, numberTypeList.size() - 1);

		return numberTypeList.get(position);
	}

	// ---------------------------------------------------------------------------------------------------

	public static String street() {
		if (streetNameList == null) {
			streetNameList = new ArrayList<String>();

			ResourceBundle myResource = ResourceBundle.getBundle("testData"); // retreive data from Dbase.properties
			String propertieValue = myResource.getString("StreetName");
			String propertieArray[] = propertieValue.split(";");

			for (String value : propertieArray) {

				streetNameList.add(Utils.firstToUpper(value));
			}
		}
		int position = Utils.randInt(0, streetNameList.size() - 1);

		return streetNameList.get(position);
	}

	// ---------------------------------------------------------------------------------------------------

	public static String streetType() {
		if (streetTypeList == null) {
			streetTypeList = new ArrayList<String>();

			ResourceBundle myResource = ResourceBundle.getBundle("testData"); // retreive data from Dbase.properties
			String propertieValue = myResource.getString("StreetType");
			String propertieArray[] = propertieValue.split(";");

			for (String value : propertieArray) {

				streetTypeList.add(Utils.firstToUpper(value));
			}
		}
		int position = Utils.randInt(0, streetTypeList.size() - 1);

		return streetTypeList.get(position);
	}

	// ---------------------------------------------------------------------------------------------------
	public static String itemDescription() {
		if (itemDescriptionList == null) {
			itemDescriptionList = new ArrayList<String>();
			ResourceBundle myResource = ResourceBundle.getBundle("testData"); // retreive data from Dbase.properties
			String propertieValue = myResource.getString("item.description");
			String propertieArray[] = propertieValue.split(";");

			for (String value : propertieArray) {
				itemDescriptionList.add(Utils.firstToUpper(value));
			}
		}
		int position = Utils.randInt(0, itemDescriptionList.size() - 1);

		return itemDescriptionList.get(position);
	}

	// ---------------------------------------------------------------------------------------------------
	public static String itemName() {
		if (itemList == null) {
			itemList = new ArrayList<String>();
			ResourceBundle myResource = ResourceBundle.getBundle("testData"); // retreive data from Dbase.properties
			String propertieValue = myResource.getString("item.name");
			String propertieArray[] = propertieValue.split(";");

			for (String value : propertieArray) {
				itemList.add(Utils.firstToUpper(value));
			}
		}
		int position = Utils.randInt(0, itemList.size() - 1);

		return itemList.get(position);
	}

	
	// ---------------------------------------------------------------------------------------------------
	public static String comment() {
		if (DataTest.commentList == null) 
			DataTest.commentList= initList("comment");
		int position = Utils.randInt(0, commentList.size() - 1);
		return DataTest.commentList.get(position);
	}

	
	// ---------------------------------------------------------------------------------------------------
	public static String city() {
		
		if (DataTest.cityList == null) 
			DataTest.cityList = initList("CityName");
		int position = Utils.randInt(0, cityList.size() - 1);
		return DataTest.cityList.get(position);
	}

//---------------------------------------------------------------------------------------------------

	public static String zipcode() {

		return String.format("%05d", Utils.randInt(1000, 99999));
	}

//---------------------------------------------------------------------------------------------------
	public static Integer number() {
		return Utils.randInt(1, 999);
	}

//---------------------------------------------------------------------------------------------------
	public static String numberString() {

		String numberType = DataTest.numberType();

		if (numberType.equals("")) {
			return String.format("%d", Utils.randInt(0, 999));
		}

		return String.format("%d %s", Utils.randInt(0, 999), numberType);
	}

	// ---------------------------------------------------------------------------------------------------
	public static Date date(Date lowerDate, Date upperDate) {

		Date randomDate = null;
		LocalDate localDate = null;

		LocalDate loDate = Utils.date2localDate(lowerDate);
		LocalDate upDate = Utils.date2localDate(upperDate);
		long periode = loDate.until(upDate, ChronoUnit.DAYS);
		int nbDays = Utils.randInt(0, (int) periode);
		localDate = loDate.plusDays(nbDays);

		return Utils.localDate2Date(localDate);
	}

	// ---------------------------------------------------------------------------------------------------
	public static Profile profile() {

		Profile[] profileTab = Profile.values();

		int profileSize = Profile.values().length;

		int profileRandom = Utils.randInt(0, profileSize - 2); // do not user last value : Null

		return profileTab[profileRandom];
	}

	// ---------------------------------------------------------------------------------------------------
	public static Gender gender(int id) {

		Gender[] genderTab = Gender.values();

		if ((id >= 0) && (id <= genderTab.length - 2))
			return genderTab[id];
		else
			return genderTab[genderTab.length - 2];

	}

	// ---------------------------------------------------------------------------------------------------
	public static Gender gender() {

		Gender[] genderTab = Gender.values();
		int genderRandom = Utils.randInt(0, genderTab.length - 2); // do not user last value : Null

		return DataTest.gender(genderRandom);
	}

	// ---------------------------------------------------------------------------------------------------
	public static LocalDate localDate(LocalDate lowerDate, LocalDate upperDate) {
		long periode = lowerDate.until(upperDate, ChronoUnit.DAYS);

		int nbDays = Utils.randInt(0, (int) periode);

		Utils.trace(String.format("%s %s %d %d", upperDate.toString(), lowerDate.toString(), periode, nbDays));

		return lowerDate.plusDays(nbDays);
	}

//---------------------------------------------------------------------------------------------------
	public static List<String> initList(String key){
		
		List<String> returnList = new ArrayList<String>();

		ResourceBundle myResource = ResourceBundle.getBundle("testData"); // retreive data from Dbase.properties
		String propertieValue = myResource.getString(key);
		String propertieArray[] = propertieValue.split(";");

		for (String value : propertieArray) {

			returnList.add(value) ;
		}


		return returnList ;
	}
			
// ---------------------------------------------------------------------------------------------------

}