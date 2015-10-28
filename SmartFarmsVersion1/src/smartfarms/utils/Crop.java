package smartfarms.utils;

public class Crop {

	int id;
	String name;
	String lname;
	String cropcategory;

	public String getCropcategory() {
		return cropcategory;
	}

	public void setCropcategory(String cropcategory) {
		this.cropcategory = cropcategory;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	String image;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
