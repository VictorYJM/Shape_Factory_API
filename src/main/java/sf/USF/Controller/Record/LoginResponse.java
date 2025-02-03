package sf.USF.Controller.Record;

public record LoginResponse(String acessToken, Long expiresIn, String scope) {
}
