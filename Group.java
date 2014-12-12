

public class Group {

	@SerializedName("group_id")
	public String groupId;
	@SerializedName("group_name")
	public String name;
	@SerializedName("image")
	public String image;
	@SerializedName("group_users")
	public String totalUsers;
	@SerializedName("group_owner_id")
	public String groupOwnerId;
	@SerializedName("user_status")
	public String userStatus;
	@SerializedName("user_status_code")
	public String userStatusCode;
	
	public Group(String name, String totalUsers) {
		this.name = name;
		this.totalUsers = totalUsers;
	}
}
