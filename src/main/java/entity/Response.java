package entity;

public class Response {
    int status_code;
    Object entity;

    public Response(int code, Object entity){
        status_code = code;
        this.entity = entity;
    }

    public int getStatus_code() {
        return status_code;
    }

    public Object getEntity() {
        return entity;
    }
}
