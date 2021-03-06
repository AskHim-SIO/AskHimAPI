package fr.askhim.api.payload;

public class ApiResponse {
  private Boolean success;
  private String codeMsg;
  private String message;

  public ApiResponse(Boolean success, String codeMsg, String message) {
    this.success = success;
    this.codeMsg = codeMsg;
    this.message = message;
  }

  public Boolean getSuccess() {
    return success;
  }
  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public String getCodeMsg(){
    return codeMsg;
  }
  public void setCodeMsg(String codeMsg){
    this.codeMsg = codeMsg;
  }

  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
}
