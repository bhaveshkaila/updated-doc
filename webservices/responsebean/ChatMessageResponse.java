
public class ChatMessageResponse extends BaseResponse {

	@SerializedName("image_id")
	public Integer imageId;

	@SerializedName("image_url")
	public String ImageUrl;

	public Integer getImageId() {
		return imageId;
	}

	public String getImageUrl() {

		return ImageUrl;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public void setImageUrl(String imageUrl) {
		ImageUrl = imageUrl;
	}

}
