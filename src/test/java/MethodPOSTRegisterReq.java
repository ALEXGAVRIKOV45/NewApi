public class MethodPOSTRegisterReq {
    private String email;
    private String password;

    public MethodPOSTRegisterReq(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
