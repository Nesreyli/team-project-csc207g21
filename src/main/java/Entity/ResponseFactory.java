package Entity;

public class ResponseFactory {
    public static Response create(int message, Object entity){
        return new Response(message, entity);
    }
}
