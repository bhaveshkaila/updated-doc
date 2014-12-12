

public class BaseResponse {

    @SerializedName("status")
    public Integer status;

	@SerializedName("statusDesc")
	public String message;

	public BaseResponse(){
		
	}
	
	public BaseResponse(Integer code, String msg) {
		this.status = code;
		this.message = msg;
	}

	public void setErrorInfo(Integer code, String msg) {
		this.status = code;
		this.message = msg;
	}
}
